package todolist.board.service.rest;

import java.util.List;

import todolist.board.dto.board.BoardDetailDto;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.board.BoardListDto;
import todolist.board.dto.board.GetBoardDto;

public interface BoardService {

    Long insert(BoardDto boardDto);
    Long update(BoardDto boardDto);
    void delete(Long boardId, Long userId);
    void detailDelete(List<Long> boardIds, Long userId);
    List<BoardListDto> getBoard(GetBoardDto getBoardDto);        // 특정 유저가 볼 수 있는 게시글 조회
    List<BoardListDto> getAllBoard();                            // 전체 유저가 볼 수 있는 게시글 조회
    BoardDetailDto getDetailBoard(Long board_id);  // 게시글 상세

}
