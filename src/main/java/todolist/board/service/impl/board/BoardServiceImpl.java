package todolist.board.service.impl.board;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import todolist.board.dto.board.BoardDto;
import todolist.board.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{
    
    @Override
    public Long insert(BoardDto boardDto)
    {
        return 0L;
    }

    @Override
    public void update(BoardDto boardDto)
    {

    }

    @Override
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
    public List<BoardDto> selectAll()
    {
        List<BoardDto> boardList =  new ArrayList<>();
        return boardList;
    }

    @Override
    public List<BoardDto> selectUserAll(Long user_id)
    {
        List<BoardDto> boardList = new ArrayList<>();
        return boardList;
    }

    @Override
    public BoardDto selectUserOne(BoardDto boardDto)
    {
        return boardDto;
    }

    private void isThereCache(Long user_id)
    {
        /*
         * user_id에 해당하는 친구목록 캐시가 있는지 조회
         */
    }

    private List<Long> userList()
    {
        List<Long> userList = new ArrayList<>();
        /*
         * 캐시 조회 후 저장 
         */
        return userList;
    }

}
