package todolist.board.service;

import java.util.List;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.reply.ReplyDto;

public interface ReplyService {

    // 비동기 메시지 큐 방식(카프카, 레디스 사용)에서 RestApi 방식으로 구현방향을 변경함에 따라 기존에 사용하던 의존성들을 주석처리한다.
	// 비동기 메시지 큐 방식은 RestApi 방식으로 구현을 마친 후 성능개선을 위해 해당방식으로 변경할 때 다시 주석을 풀고 사용하자.
    // void insert(ReplyDto replyDto);
    // void update(ReplyDto replyDto);
    // void delete(DeleteDto deleteDto);
    // void detailDelete(DetailDeleteDto DetailDeleteDto);

    void insert(ReplyDto replyDto);
    void update(ReplyDto replyDto);
    void delete(DeleteDto deleteDto);
    void detailDelete(DetailDeleteDto detailDeleteDto);
    List<ReplyDto> select(Long board_id);

    void deleteFromBoard(Long board_id);
    void detailDeleteFromBoard(List<Long> board_id_list);

}

