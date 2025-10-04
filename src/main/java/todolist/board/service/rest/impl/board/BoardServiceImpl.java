package todolist.board.service.rest.impl.board;

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
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import todolist.board.domain.Board;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.board.BoardDetailDto;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.board.BoardListDto;
import todolist.board.dto.board.GetBoardDto;
import todolist.board.dto.reply.ReplyDto;
import todolist.board.dto.todolist.TodolistDto;
import todolist.board.repository.rest.BoardRepository;
import todolist.board.service.rest.BoardService;
// import todolist.board.service.RedisService;
import todolist.board.service.rest.ReplyService;
import todolist.board.service.rest.TodolistService;
// import todolist.board.service.KafkaProducer;

@Service("restBoardService")
public class BoardServiceImpl implements BoardService{
    
    private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);

    @Autowired
    private TodolistService todolistService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private WebClient webClient;
    @Value("${service.url}")
    private String followUrl;
    @Value("${service.url.gt}")
    private String gatewayUrl;
    
    @Override
    public Long insert(BoardDto boardDto)
    {
        Board insBoard = Board.builder()
                              .user_id(boardDto.getUser_id())
                              .scope_of_disclosure(boardDto.getScope_of_disclosure())
                              .content(boardDto.getContent())
                              .build();
        Board returnBoard = repoINS(insBoard, boardDto);
        webClient.post().uri(gatewayUrl+"/noti").bodyValue(returnBoard).retrieve().bodyToMono(String.class).block();
        return returnBoard.getBoard_id();
    }
    @Override
    public Long update(BoardDto boardDto)
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
        return updBoard.getBoard_id();
    }
    @Override
    public void delete(Long boardId, Long userId)
    {
        repoDel(boardId, userId);
    }
    @Override
    public void detailDelete(List<Long> boardIds, Long userId)
    {
        repoDetailDel(boardIds, userId);
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
    public List<BoardListDto> getBoard(GetBoardDto getBoardDto)
    {
        List<BoardListDto> boardDto = boardRepository.getBoardList(getBoardDto.getUserId(), getBoardDto.getFollowIds(), getBoardDto.getGroupIds());

        return boardDto;
    }

    @Override
    public List<BoardListDto> getAllBoard()
    {
        List<BoardListDto> allBoard = boardRepository.getAllBoardList();
        return allBoard;
    }

    @Override
    public BoardDetailDto getDetailBoard(Long boardId)
    {
        BoardDetailDto boardList    = boardRepository.getDetailBoard(boardId);
        List<TodolistDto> todolist  = boardRepository.getTodolist(boardId);
        List<ReplyDto> reply        = boardRepository.getReply(boardId);

        boardList.setTodolist(todolist);
        boardList.setReply(reply);

        return boardList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Board repoINS(Board board, BoardDto boardDto)
    {
        Board returnBoard = boardRepository.save(board);
        if( !Optional.ofNullable(boardDto.getTodolist()).orElse(Collections.emptyList()).isEmpty() )
        {
            for(TodolistDto todolist : boardDto.getTodolist())
            {
                todolist.setBoard_id(board.getBoard_id());
                todolistService.insertFromBoard(todolist);
            }
        }
        return returnBoard;
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
    private void repoDel(Long boardId, Long userId)
    {
        todolistService.deleteFromBoard(boardId);
        replyService.deleteFromBoard(boardId);
        boardRepository.deleteByBoardUserId(boardId, userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void repoDetailDel(List<Long> boardIds, Long userId)
    {
        todolistService.detailDeleteFromBoard(boardIds);
        replyService.detailDeleteFromBoard(boardIds);
        boardRepository.detailDelete(boardIds, userId);
    }

}