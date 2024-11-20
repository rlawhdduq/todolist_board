package todolist.board.service.impl.reply;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import todolist.board.domain.Reply;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.reply.ReplyDto;
import todolist.board.repository.ReplyRepository;
import todolist.board.service.ReplyService;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long board_id)
    {
        replyRepository.deleteByBoardId(board_id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void detailDelete(List<Long> board_id_list)
    {
        replyRepository.detailDeleteByBoardId(board_id_list);
    }

    @KafkaListener
    (
        topics = "reply-insert", 
        groupId = "board",
        containerFactory = "replyDtoKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(ReplyDto replyDto, Acknowledgment ack)
    {
        Reply insReply = Reply.builder()
                              .board_id(replyDto.getBoard_id())
                              .user_id(replyDto.getUser_id())
                              .build();
        repoSave(insReply);
        ack.acknowledge();
    }
    
    @KafkaListener
    (
        topics = "reply-update", 
        groupId = "board",
        containerFactory = "replyDtoKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(ReplyDto replyDto, Acknowledgment ack)
    {
        Short reply_depth = replyDto.getReply_depth();
        Reply insReply = Reply.builder()
                              .reply_id(replyDto.getReply_id())
                              .content(replyDto.getContent())
                              .reply_depth(reply_depth++)
                              .update_time(LocalDateTime.now())
                              .build();
        repoSave(insReply);
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "reply-delete", 
        groupId = "board",
        containerFactory = "delKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(DeleteDto deleteDto, Acknowledgment ack)
    {
        // replyRepository.deleteByBoardId(board_id);
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "reply-delete-detail", 
        groupId = "board",
        containerFactory = "detailDelKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void detailDelete(DetailDeleteDto detailDeleteDto, Acknowledgment ack)
    {
        // replyRepository.deleteDetail(reply_id_list);
        ack.acknowledge();
    }

    @Override
    public List<ReplyDto> select(Long board_id)
    {
        List<ReplyDto> replyList = new ArrayList<>();
        return replyList;
    }

    // Private Method
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoSave(Reply reply)
    {
        replyRepository.save(reply);
    }
}
