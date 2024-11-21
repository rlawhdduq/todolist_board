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
import todolist.board.service.KafkaProducer;
import todolist.board.service.ReplyService;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private KafkaProducer kafka;
    
    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public void insert(ReplyDto replyDto)
    {
        callKafka("reply-insert", replyDto);
        return;
    }
    @Override
    public void update(ReplyDto replyDto)
    {
        callKafka("reply-update", replyDto);
        return;
    }
    @Override
    public void delete(DeleteDto deleteDto)
    {
        callKafka("reply-delete", deleteDto);
        return;
    }
    @Override
    public void detailDelete(DetailDeleteDto detailDeleteDto)
    {
        callKafka("reply-delete-detail", detailDeleteDto);
        return;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFromBoard(Long board_id)
    {
        replyRepository.deleteByBoardId(board_id);
        return;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void detailDeleteFromBoard(List<Long> board_id_list)
    {
        replyRepository.detailDelete(board_id_list);
        return;
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
                              .parent_id(replyDto.getParent_id())
                              .reply_depth(replyDto.getReply_depth())
                              .content(replyDto.getContent())
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
        Reply insReply = Reply.builder()
                              .reply_id(replyDto.getReply_id())
                              .board_id(replyDto.getBoard_id())
                              .user_id(replyDto.getUser_id())
                              .parent_id(replyDto.getParent_id())
                              .content(replyDto.getContent())
                              .reply_depth(replyDto.getReply_depth())
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
        repoDel(deleteDto);
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
        repoDetailDel(detailDeleteDto);
        ack.acknowledge();
    }

    @Override
    public List<ReplyDto> select(Long board_id)
    {
        List<ReplyDto> replyList = new ArrayList<>();
        return replyList;
    }

    // Private Method
    private void callKafka(String topic, Object dto)
    {
        kafka.sendMessage(topic, dto);
        return;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoSave(Reply reply)
    {
        replyRepository.save(reply);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoDel(DeleteDto deleteDto)
    {
        replyRepository.deleteByReplyIdBoardId(deleteDto.getKey(), deleteDto.getForeign_key());
    }
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoDetailDel(DetailDeleteDto detailDeleteDto)
    {
        replyRepository.detailDelete(detailDeleteDto.getForeign_key(), detailDeleteDto.getKey_list());
    }
}
