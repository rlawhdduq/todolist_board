package todolist.board.service;

import java.util.List;

import todolist.board.dto.board.BoardDto;

public interface BoardService {

    /**
     * 카프카로 처리하니까 매개변수 없어도 된다.
     * -> Dto가 여러개일 수 있고, 그에맞춰 컨슈머도 오버로딩해줘야 하기때문에
     * 명시적으로 구분하기 위해서 Dto 다시 설정한다.
     */
    BoardDto detailBoard(Long user_id);                 // 게시글 상세
    List<BoardDto> selectBoardAll(Long user_id);        // 특정 유저가 볼 수 있는 게시글 조회

}
