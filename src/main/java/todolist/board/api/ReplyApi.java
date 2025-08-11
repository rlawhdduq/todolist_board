package todolist.board.api;

import org.springframework.web.bind.annotation.RestController;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.reply.ReplyDto;
import todolist.board.service.ReplyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ReplyApi {
    
    @Autowired
    private ReplyService replyService;

    @PostMapping("/api/board/reply")
    public void insertReply(@RequestBody ReplyDto replyDto) {
        replyService.insert(replyDto);
        return;
    }
    @PutMapping("/api/board/reply")
    public void updateReply(@RequestBody ReplyDto replyDto) {
        replyService.update(replyDto);
    }
    @DeleteMapping("/api/board/reply")
    public void deleteReply(@RequestBody DeleteDto deleteDto)
    {
        replyService.delete(deleteDto);
        return;
    }
    @DeleteMapping("/api/board/reply/detail")
    public void detailDeleteReply(@RequestBody DetailDeleteDto detailDeleteDto)
    {
        replyService.detailDelete(detailDeleteDto);
        return;
    }
    
    // 개발 X 사용여부 불확실
    @GetMapping("/api/board/reply")
    public List<ReplyDto> getReply(@RequestParam Long board_id) {

        List<ReplyDto> replyList = replyService.select(board_id);
        return replyList;
    }
    
}
