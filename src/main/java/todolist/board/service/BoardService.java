package todolist.board.service;

import java.util.List;

import todolist.board.dto.board.BoardDto;

public interface BoardService {

    Long insert(BoardDto boardDto);                     // 게시글 등록
    void update(BoardDto boardDto);                     // 게시글 수정
    void delete(Long board_id);                         // 게시글 삭제
    List<BoardDto> selectAll();                         // 전체 게시글 조회
    List<BoardDto> selectUserAll(Long user_id);         // 특정 유저의 게시글 조회
    BoardDto selectUserOne(BoardDto boardDto);          // 특정 유저의 특정 게시글 조회 
}
