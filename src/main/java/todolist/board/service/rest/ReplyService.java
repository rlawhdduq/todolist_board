package todolist.board.service.rest;

import java.util.List;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.reply.ReplyDto;

public interface ReplyService {

    void insert(ReplyDto replyDto);
    void update(ReplyDto replyDto);
    void delete(DeleteDto deleteDto);
    void detailDelete(DetailDeleteDto detailDeleteDto);
    List<ReplyDto> select(Long board_id);

    void deleteFromBoard(Long board_id);
    void detailDeleteFromBoard(List<Long> board_id_list);

}

