package todolist.board.service.impl.board;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import todolist.board.dto.board.BoardDto;
import todolist.board.dto.redis.RedisUserListDto;
import todolist.board.service.BoardService;
import todolist.board.service.RedisService;

@Service
public class BoardServiceImpl implements BoardService{
    
    @Autowired
    private RedisService redisService;
    private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
    @Autowired
    private WebClient webClient;

    @Value("${service.url}")
    private String followUrl;

    @Override
    @KafkaListener(topics = "board-insert", groupId = "board")
    public void insert(BoardDto boardDto)
    {

    }

    @Override
    @KafkaListener(topics = "board-update", groupId = "board")
    public void update(BoardDto boardDto)
    {

    }

    @Override
    @KafkaListener(topics = "board-update", groupId = "board")
    public void delete(Long user_id)
    {

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
