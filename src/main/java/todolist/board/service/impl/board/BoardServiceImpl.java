package todolist.board.service.impl.board;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import todolist.board.domain.Board;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.redis.RedisUserListDto;
import todolist.board.dto.todolist.TodolistDto;
import todolist.board.repository.BoardRepository;
import todolist.board.service.BoardService;
import todolist.board.service.RedisService;
import todolist.board.service.ReplyService;
import todolist.board.service.TodolistService;

@Service
public class BoardServiceImpl implements BoardService{
    
    private RedisService redisService;
    private TodolistService todolistService;
    private ReplyService replyService;

    @Autowired
    private BoardRepository boardRepository;

    private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
    @Autowired
    private WebClient webClient;

    @Value("${service.url}")
    private String followUrl;

    @Override
    @KafkaListener
    (
        topics = "board-insert", 
        groupId = "board",
        containerFactory = "boardDtoKafkaListenerContainerFactory"
    )
    public void insert(BoardDto boardDto)
    {
        log.info("insert 진입");
        Board insBoard = Board.builder()
                              .user_id(boardDto.getUser_id())
                              .scope_of_disclosure(boardDto.getScope_of_disclosure())
                              .content(boardDto.getContent())
                              .build();
        boardRepository.save(insBoard);
        // 여기서 pk추출후 todolist 호출
        Long insBoardPk = insBoard.getBoard_id();

        log.info("insert 완료 : " + insBoardPk.toString());
        for(TodolistDto todolist : boardDto.getTodolist())
        {
            todolist.setBoard_id(insBoardPk);
            todolistService.insert(todolist);
        }
        log.info("insert 퇴장");
    }

    @Override
    @KafkaListener
    (
        topics = "board-update",
        groupId = "board",
        containerFactory = "boardDtoKafkaListenerContainerFactory"
    )
    public void update(BoardDto boardDto)
    {
        Board updBoard = Board.builder()
                              .board_id(boardDto.getBoard_id())
                              .scope_of_disclosure(boardDto.getScope_of_disclosure())
                              .fulfillment_or_not(boardDto.getFulfillment_or_not())
                              .content(boardDto.getContent())
                              .update_time(LocalDateTime.now())
                              .build();
        boardRepository.save(updBoard);

        for(TodolistDto todolist : boardDto.getTodolist())
        {
            todolist.setBoard_id(boardDto.getBoard_id());
            todolistService.update(todolist);
        }
    }

    @Override
    @KafkaListener
    (
        topics = "board-delete", 
        groupId = "board",
        containerFactory = "longKafkaListenerContainerFactory"
    )
    public void delete(Long board_id)
    {
        /**
         *  Todo먼저 지우고 댓글 지우고 게시글
         */
        todolistService.delete(board_id);
        replyService.delete(board_id);
        Board delBoard = Board.builder()
                              .board_id(board_id)
                              .status('N')
                              .build();

        boardRepository.save(delBoard);
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
    public List<BoardDto> selectBoardAll(Long user_id)
    {
        isThereCache(user_id);
        List<BoardDto> boardList = new ArrayList<>();
        return boardList;
    }

    @Override
    public BoardDto detailBoard(Long user_id)
    {
        BoardDto boardDto = new BoardDto();
        return boardDto;
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
        RedisUserListDto userList = webClient.get()
                                        .uri(followUrl+"/{user_id}", user_id)
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<RedisUserListDto>() {}).block();
        redisService.setRedis(userList.getUser_id().toString(), (List<Long>) userList.getUserList());
    }

}
