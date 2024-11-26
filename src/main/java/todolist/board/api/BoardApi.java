package todolist.board.api;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import todolist.board.dto.board.BoardDetailDto;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.board.BoardListDto;
import todolist.board.dto.board.BoardGetDto;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.service.BoardService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class BoardApi {

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
     */
    @GetMapping("/api/board")
    public List<BoardListDto> getBoard(@RequestBody BoardGetDto boardGetDto) {

        List<BoardListDto> boardList = boardService.getBoard(boardGetDto);
        return boardList;
    }
    
    /*
     * 이것도 전체 게시글 조회에서 만들어진 로직에 + board_id만 넣으면 된다.
     */
    @GetMapping("/api/board/{board_id}")
    public BoardDetailDto getBoardDetail(@PathVariable Long board_id, @RequestBody Long user_id) {
        BoardDetailDto boardDetailDto = boardService.getDetailBoard(board_id, user_id);
        return boardDetailDto;
    }
    @PostMapping("/api/board")
    public void insertBoard(@RequestBody BoardDto boardDto) {
        boardService.insert(boardDto);
        return;
    }
    @PutMapping("/api/board")
    public void updateBoard(@RequestBody BoardDto boardDto)
    {
        boardService.update(boardDto);
        return;
    }
    @DeleteMapping("/api/board")
    public void deleteBoard(@RequestBody DeleteDto deleteDto)
    {
        boardService.delete(deleteDto);
        return;
    }
    @DeleteMapping("/api/board/detail")
    public void detailDeleteBoard(@RequestBody DetailDeleteDto detailDeleteDto)
    {
        boardService.detailDelete(detailDeleteDto);
        return;
    }
}
