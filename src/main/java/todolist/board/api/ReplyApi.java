package todolist.board.api;

import org.springframework.web.bind.annotation.RestController;

import todolist.board.dto.reply.ReplyDto;
import todolist.board.service.ReplyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ReplyApi {
    
    @Autowired
    private ReplyService replyService;

    @GetMapping("/api/reply/{board_id}")
    public List<ReplyDto> getMethodName(@PathVariable("board_id") Long board_id) {

        List<ReplyDto> replyList = replyService.select(board_id);
        return replyList;
    }
    
}
