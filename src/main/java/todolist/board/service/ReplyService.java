package todolist.board.service;

import java.util.List;

import todolist.board.dto.reply.ReplyDto;

public interface ReplyService {

    void detailDelete(List<Long> board_id_list);                                // 댓글 일부 삭제
    void delete(Long board_id);                                                 // 댓글 전체 삭제
    List<ReplyDto> select(Long board_id);                                       // 게시글의 댓글 조회

}
