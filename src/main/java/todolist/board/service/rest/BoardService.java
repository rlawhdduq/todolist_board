package todolist.board.service.rest;

import java.util.List;

import todolist.board.dto.board.BoardDetailDto;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.board.BoardListDto;
import todolist.board.dto.board.GetBoardDto;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;

public interface BoardService {

    void insert(BoardDto boardDto);
    void update(BoardDto boardDto);
    void delete(DeleteDto deleteDto);
    void detailDelete(DetailDeleteDto detailDeleteDto);
    BoardDetailDto getDetailBoard(Long board_id);  // 게시글 상세
    List<BoardListDto> getBoard(GetBoardDto getBoardDto);        // 특정 유저가 볼 수 있는 게시글 조회

}
