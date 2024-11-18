package todolist.board.service.impl.reply;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import todolist.board.domain.Reply;
import todolist.board.dto.reply.ReplyDto;
import todolist.board.repository.ReplyRepository;
import todolist.board.service.ReplyService;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    @Override
    @KafkaListener
    (
        topics = "reply-insert", 
        groupId = "board",
        containerFactory = "replyDtoKafkaListenerContainerFactory"
    )
    public void insert(ReplyDto replyDto)
    {
        Reply insReply = Reply.builder()
                              .board_id(replyDto.getBoard_id())
                              .user_id(replyDto.getUser_id())
                              .build();
        replyRepository.save(insReply);
    }
    
    @Transactional
    @Override
    @KafkaListener
    (
        topics = "reply-update", 
        groupId = "board",
        containerFactory = "replyDtoKafkaListenerContainerFactory"
    )
    public void update(ReplyDto replyDto)
    {
        Short reply_depth = replyDto.getReply_depth();
        Reply insReply = Reply.builder()
                              .reply_id(replyDto.getReply_id())
                              .content(replyDto.getContent())
                              .reply_depth(reply_depth++)
                              .update_time(LocalDateTime.now())
                              .build();
        replyRepository.save(insReply);
    }

    @Transactional
    @Override
    @KafkaListener
    (
        topics = "reply-delete", 
        groupId = "board",
        containerFactory = "longKafkaListenerContainerFactory"
    )
    public void delete(Long board_id)
    {
        replyRepository.deleteByBoardId(board_id);
    }

    @Transactional
    @Override
    @KafkaListener
    (
        topics = "reply-delete-detail", 
        groupId = "board",
        containerFactory = "longKafkaListenerContainerFactory"
    )
    public void delete(List<Long> reply_id_list)
    {
        replyRepository.deleteDetail(reply_id_list);
    }

    @Override
    public List<ReplyDto> select(Long board_id)
    {
        List<ReplyDto> replyList = new ArrayList<>();
        return replyList;
    }
}
