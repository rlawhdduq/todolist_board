package todolist.board.api.rest;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import todolist.board.dto.board.BoardDetailDto;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.board.BoardListDto;
import todolist.board.dto.board.GetBoardDto;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.service.rest.BoardService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/board")
public class BoardRestApi {

    @Autowired
    private final BoardService boardService;

    /*
     * 전체 게시글 조회
     * 유형별 조회 가능한 공개범위
     * NC : A, 나와 친구인 F
     * CC : C
     * GC : A, 나와 친구인 F, C
     * 
     * 로그인 시 세션에 user_id, user_type을 넣어두기 때문에 저거 두개로 조회쿼리를 제어하면 될 것 같다.
     * 지금 드는 생각은 쿼리가 좀 복잡하게 짜여질 것 같은데 어떻게 해보면 되겠지 머...
     * 25.09.27(rest로 구현)
     *  1. 게시글 목록을 가져올 때 서비스에선 user_id만 가져온다.
     *  2. user_id를 가지고 follow 서비스를 호출하여 현재 들어온 user_id의 친구 목록을 가져온다.
     *  => 이렇게되면 게시글과 친구목록의 결합도가 상승하겠지만 방법이 없다. 추후 mq를 도입하게 되면 이부분은 캐싱처리가 될 것이기때문에 아키텍쳐간 결합도가 줄어들 것이다.
     */
    @RequestMapping(method=RequestMethod.GET)
    public List<BoardListDto> getBoard(GetBoardDto getBoardDto)
    {
        List<BoardListDto> boardList = boardService.getBoard(getBoardDto);
        return boardList;
    }
    
    /*
     * 이것도 전체 게시글 조회에서 만들어진 로직에 + board_id만 넣으면 된다.
     */
    @RequestMapping(path="/detail", method=RequestMethod.GET)
    public BoardDetailDto getBoardDetail(@RequestParam Long user_id, @RequestParam Long board_id)
    {
        BoardDetailDto boardDetailDto = boardService.getDetailBoard(board_id);
        return boardDetailDto;
    }

    @RequestMapping(method=RequestMethod.POST)
    public void insertBoard(@RequestBody BoardDto boardDto) {
        boardService.insert(boardDto);
        return;
    }
    @RequestMapping(method=RequestMethod.PUT)
    public void updateBoard(@RequestBody BoardDto boardDto)
    {
        boardService.update(boardDto);
        return;
    }
    @RequestMapping(method=RequestMethod.DELETE)
    public void deleteBoard(@RequestBody DeleteDto deleteDto)
    {
        boardService.delete(deleteDto);
        return;
    }
    @RequestMapping(path="/detail", method=RequestMethod.DELETE)
    public void detailDeleteBoard(@RequestBody DetailDeleteDto detailDeleteDto)
    {
        boardService.detailDelete(detailDeleteDto);
        return;
    }
}
