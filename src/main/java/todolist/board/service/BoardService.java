package todolist.board.service;

import java.util.List;

import todolist.board.dto.board.BoardDto;

public interface BoardService {

    void insert(BoardDto boardDto);                     // 게시글 등록
    void update(BoardDto boardDto);                     // 게시글 수정
    void delete(Long board_id);                         // 게시글 삭제
    BoardDto detailBoard(Long user_id);                 // 게시글 상세
    List<BoardDto> selectBoardAll(Long user_id);        // 특정 유저가 볼 수 있는 게시글 조회
}
