package todolist.board.api;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import todolist.board.dto.board.BoardDto;
import todolist.board.repository.BoardRepository;
import todolist.board.service.BoardService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequiredArgsConstructor
public class BoardApi {
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
    public String getAllMethodName(@RequestParam String param) {
        return new String();
    }

    /*
     * 이것도 전체 게시글 조회에서 만들어진 로직에 + board_id만 넣으면 된다.
     */
    @GetMapping("/api/board/{board_id}")
    public String getOneMethodName(@PathVariable Long board_id, @RequestBody String param) {
        return new String();
    }
    @PostMapping("/api/board")
    public Long insertBoard(@RequestBody BoardDto boardDto) {
        Long board_id = boardService.insert(boardDto);
        return board_id;
    }
    /*
     * 수정은 2가지 경우가 있을 수 있다.
     * 1. 단순 내용 수정
     *  - 단순 수정의 경우 게시글 수정과 ToDo 수정이 있을 수 있다.
     *  - ToDo를 수정할 수 있게 할것인가도 확실하게 정해야할거같다.
     * 2. ToDo완료로 이행 완료 처리
     */
    @PutMapping("/api/board/{board_id}")
    public Long putMethodName(@PathVariable Long board_id, @RequestBody BoardDto boardDto) {
        boardService.update(boardDto);
        return board_id;
    }

    @DeleteMapping("/api/board/{board_id}")
    public void deleteMethodName(@PathVariable Long board_id)
    {
        boardService.delete(board_id);
        return;
    }
}
