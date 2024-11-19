package todolist.board.service;

import java.util.List;

import org.springframework.kafka.support.Acknowledgment;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.reply.ReplyDto;

public interface ReplyService {

    void insert(ReplyDto replyDto, Acknowledgment ack);                         // 댓글 등록
    void update(ReplyDto replyDto, Acknowledgment ack);                         // 댓글 수정
    void detailDelete(DetailDeleteDto detailDeleteDto, Acknowledgment ack);     // 댓글 일부 삭제
    void detailDelete(List<Long> board_id_list);                                // 댓글 일부 삭제
    void delete(Long board_id);                                                 // 댓글 전체 삭제
    void delete(DeleteDto deleteDto, Acknowledgment ack);                       // 댓글 전체 삭제
    List<ReplyDto> select(Long board_id);                                       // 게시글의 댓글 조회

}
