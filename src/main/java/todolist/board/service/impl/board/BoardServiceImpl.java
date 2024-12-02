package todolist.board.service.impl.board;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import todolist.board.domain.Board;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.board.BoardDetailDto;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.board.BoardListDto;
import todolist.board.dto.redis.RedisUserListDto;
import todolist.board.dto.reply.ReplyDto;
import todolist.board.dto.todolist.TodolistDto;
import todolist.board.repository.BoardRepository;
import todolist.board.service.BoardService;
import todolist.board.service.RedisService;
import todolist.board.service.ReplyService;
import todolist.board.service.TodolistService;
import todolist.board.service.KafkaProducer;

@Service
public class BoardServiceImpl implements BoardService{
    
    private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private TodolistService todolistService;
    @Autowired
    private ReplyService replyService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private WebClient webClient;
    @Autowired
    private KafkaProducer kafka;
    @Autowired
    private SimpMessagingTemplate messageTemplate;

    @Value("${service.url}")
    private String followUrl;

    @Override
    public void insert(BoardDto boardDto)
    {
        callKafka("board-insert", (Object) boardDto);
    }
    @Override
    public void update(BoardDto boardDto)
    {
        callKafka("board-update", (Object) boardDto);
    }
    @Override
    public void delete(DeleteDto deleteDto)
    {
        callKafka("board-delete", (Object) deleteDto);
    }
    @Override
    public void detailDelete(DetailDeleteDto detailDeleteDto)
    {
        callKafka("board-delete-detail", (Object) detailDeleteDto);
    }
    
    // Kafka
    @KafkaListener
    (
        topics = "board-insert", 
        groupId = "board",
        containerFactory = "boardDtoKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(BoardDto boardDto, Acknowledgment ack)
    {
        Board insBoard = Board.builder()
                              .user_id(boardDto.getUser_id())
                              .scope_of_disclosure(boardDto.getScope_of_disclosure())
                              .content(boardDto.getContent())
                              .build();
        repoINS(insBoard, boardDto);
        redisService.newBoardMessage(boardDto.getScope_of_disclosure(), boardDto.getUser_id(), (Object) boardDto);
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "board-update",
        groupId = "board",
        containerFactory = "boardDtoKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(BoardDto boardDto, Acknowledgment ack)
    {
        Board updBoard = Board.builder()
                              .board_id(boardDto.getBoard_id())
                              .user_id(boardDto.getUser_id())
                              .scope_of_disclosure(boardDto.getScope_of_disclosure())
                              .fulfillment_or_not(boardDto.getFulfillment_or_not())
                              .fulfillment_time(boardDto.getFulfillment_time())
                              .content(boardDto.getContent())
                              .update_time(LocalDateTime.now())
                              .build();
        repoUPD(updBoard, boardDto);

        
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "board-delete", 
        groupId = "board",
        containerFactory = "delKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(DeleteDto deleteDto, Acknowledgment ack)
    {
        /**
         *  Todo -> reply -> board
         */
        repoDel(deleteDto);
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "board-delete-detail",
        groupId = "board",
        containerFactory = "detailDelKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void detailDelete(DetailDeleteDto detailDeleteDto, Acknowledgment ack)
    {
        /**
         * Todo -> reply -> board
         */
        repoDetailDel(detailDeleteDto);
        ack.acknowledge();
    }
    /*
     * 게시글 조회의 경우 로직이 복잡하다.
     * 회원유형과 친구관계가 얽혀있기때문이다.
     * 
     * 유형별 조회 가능한 공개범위
     * NC : A, 나와 친구인 F
     * CC : C
     * GC : A, 나와 친구인 F, C
     * 
     * 캐시를 사용하여 특정 회원들의 조회가능한 user_id들을 저장해 두자.
     * NC
     * - NC : 나와 친구인 user_id들
     * CC
     * - CC : C의 user_id들
     * GC
     * - NC : 나와 친구인 user_id들
     * - CC : C의 user_id들
     * Q. 위에서 A가 없는이유? 전체공개라서, CC의 경우 A를 볼 수 없기떄문에 친구목록 조회 시 거를 것임
     */
    @Override
    public List<BoardListDto> getBoard(Long user_id, Integer limit, Long board_id)
    {
        limit = limit <= 0 ? 0 : limit;
        board_id = board_id == null ? Long.MAX_VALUE : board_id;
        
        isThereCache(user_id);
        
        Map<String, Object> userIdList = (Map<String, Object>) Optional.ofNullable(redisService.getRedis(user_id.toString())).orElse(new HashMap());
        // List<Long> aUserList = (List<Long>) Optional.ofNullable(userIdList.get("A")).orElse(new ArrayList<>());
        List<Long> fUserList = (List<Long>) Optional.ofNullable(userIdList.get("F")).orElse(new ArrayList<>());
        List<Long> cUserList = (List<Long>) Optional.ofNullable(userIdList.get("C")).orElse(new ArrayList<>());
        // List<BoardListDto> boardDto = boardRepository.getBoardList(aUserList, fUserList, cUserList, board_id, limit);
        List<BoardListDto> boardDto = boardRepository.getBoardList(fUserList, cUserList, board_id, limit);

        return boardDto;
    }

    @Override
    public BoardDetailDto getDetailBoard(Long board_id, Long user_id)
    {
        isThereCache(user_id);
        List<Long> user_id_list = (List<Long>) redisService.getRedis(user_id.toString());

        BoardDetailDto boardList    = boardRepository.getDetailBoard(board_id, user_id_list);
        List<TodolistDto> todolist  = boardRepository.getTodolist(board_id);
        List<ReplyDto> reply        = boardRepository.getReply(board_id);

        boardList.setTodolist(todolist);
        boardList.setReply(reply);

        return boardList;
    }

    // Private Method
    private void callKafka(String topic, Object dto)
    {
        kafka.sendMessage(topic, dto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void repoINS(Board board, BoardDto boardDto)
    {
        boardRepository.save(board);
        if( !Optional.ofNullable(boardDto.getTodolist()).orElse(Collections.emptyList()).isEmpty() )
        {
            for(TodolistDto todolist : boardDto.getTodolist())
            {
                todolist.setBoard_id(board.getBoard_id());
                todolistService.insertFromBoard(todolist);
            }
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoUPD(Board board, BoardDto boardDto)
    {
        boardRepository.save(board);
        if( !Optional.ofNullable(boardDto.getTodolist()).orElse(Collections.emptyList()).isEmpty() )
        {
            for(TodolistDto todolist : boardDto.getTodolist())
            {
                todolist.setBoard_id(boardDto.getBoard_id());
                todolistService.updateFromBoard(todolist);
            }
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoDel(DeleteDto deleteDto)
    {
        todolistService.deleteFromBoard(deleteDto.getKey());
        replyService.deleteFromBoard(deleteDto.getKey());
        boardRepository.deleteByBoardUserId(deleteDto.getKey(), deleteDto.getForeign_key());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void repoDetailDel(DetailDeleteDto detailDeleteDto)
    {
        todolistService.detailDeleteFromBoard(detailDeleteDto.getKey_list());
        replyService.detailDeleteFromBoard(detailDeleteDto.getKey_list());
        boardRepository.detailDelete(detailDeleteDto.getKey_list(), detailDeleteDto.getForeign_key());
    }

    private void isThereCache(Long user_id)
    {
        if(!redisService.existKey(user_id.toString()))
        {
            userList(user_id);
        }
    }

    private void userList(Long user_id)
    {
        /*
         * 캐시 조회 후 저장 
         * Restful
         */
        Map<String, Object> userList = webClient.get()
                                        .uri(followUrl+"?user_id={user_id}", user_id)
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {}).block();
        redisService.setRedis(user_id.toString(), userList.get("Body"));
    }

}
