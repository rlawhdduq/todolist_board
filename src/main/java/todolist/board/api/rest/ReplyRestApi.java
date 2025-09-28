package todolist.board.api.rest;

import org.springframework.web.bind.annotation.RestController;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.reply.ReplyDto;
import todolist.board.service.rest.ReplyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/rest/reply")
public class ReplyRestApi {
    
    @Autowired
    private ReplyService replyService;

    @RequestMapping(path="/{boardId}",method=RequestMethod.GET)
    public List<ReplyDto> getReply(@PathVariable long boardId)
    {
        List<ReplyDto> replyList = replyService.select(boardId);
        return replyList;
    }
    @RequestMapping(method=RequestMethod.POST)
    public String insertReply(@RequestBody ReplyDto replyDto)
    {
        replyService.insert(replyDto);
        return "댓글 등록 성공";
    }
    @RequestMapping(method=RequestMethod.PUT)
    public String updateReply(@RequestBody ReplyDto replyDto)
    {
        replyService.update(replyDto);
        return "댓글 업데이트 성공";
    }
    @RequestMapping(path="/{boardId}", method=RequestMethod.DELETE)
    public String deleteReply(@PathVariable Long boardId)
    {
        // 이거 board용 서비스이긴한데,,, 일단 쓰자,,,
        replyService.deleteFromBoard(boardId); 
        return "댓글 삭제 성공";
    }
    @RequestMapping(path="/detail/{replyId}", method=RequestMethod.DELETE)
    public String detailDeleteReply(@PathVariable Long replyId)
    {
        // replyService.detailDelete(??); -> replyId로 삭제하는 repo를 하나 만들어줘야한다...
        return "특정댓글 삭제 성공";
    }

}
