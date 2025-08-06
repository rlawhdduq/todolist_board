package todolist.board.service;

import java.util.List;

import todolist.board.dto.board.BoardDetailDto;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.board.BoardListDto;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;

public interface BoardService {

    /**
     * 카프카로 처리하니까 매개변수 없어도 된다.
     * -> Dto가 여러개일 수 있고, 그에맞춰 컨슈머도 오버로딩해줘야 하기때문에
     * 명시적으로 구분하기 위해서 Dto 다시 설정한다.
     */
    // 비동기 메시지 큐 방식(카프카, 레디스 사용)에서 RestApi 방식으로 구현방향을 변경함에 따라 기존에 사용하던 의존성들을 주석처리한다.
	// 비동기 메시지 큐 방식은 RestApi 방식으로 구현을 마친 후 성능개선을 위해 해당방식으로 변경할 때 다시 주석을 풀고 사용하자.
    // void insert(BoardDto boardDto);
    // void update(BoardDto boardDto);
    // void delete(DeleteDto deleteDto);
    // void detailDelete(DetailDeleteDto detailDeleteDto);
    BoardDetailDto getDetailBoard(Long board_id, Long user_id);  // 게시글 상세
    List<BoardListDto> getBoard(Long user_id, Integer limit, Long board_id);        // 특정 유저가 볼 수 있는 게시글 조회

}
