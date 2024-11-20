package todolist.board.service.impl.todolist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import todolist.board.domain.Todolist;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.todolist.TodolistDto;
import todolist.board.repository.TodolistRepository;
import todolist.board.service.TodolistService;

@Service
public class TodolistServiceImpl implements TodolistService{
    
    @Autowired
    private TodolistRepository todolistRepository;

    @Override
    public void insert(TodolistDto todolistDto)
    {
        Todolist insTodolist = Todolist.builder()
                                .board_id(todolistDto.getBoard_id()) // board 인서트 후 이 값을 채워줘야함.
                                .todo_type(todolistDto.getTodo_type())
                                .todo_type_detail(todolistDto.getTodo_type_detail())
                                .todo_unit(todolistDto.getTodo_unit())
                                .todo_number(todolistDto.getTodo_number())
                                .build();
        todolistRepository.save(insTodolist);
    }

    @Override
    public void update(TodolistDto todolistDto)
    {
        Todolist updTodolist = Todolist.builder()
                                       .todolist_id(todolistDto.getTodolist_id())
                                       .todo_type(todolistDto.getTodo_type())
                                       .todo_type_detail(todolistDto.getTodo_type_detail())
                                       .todo_number(todolistDto.getTodo_number())
                                       .todo_unit(todolistDto.getTodo_unit())
                                       .fulfillment_or_not(todolistDto.getFulfillment_or_not())
                                       .update_time(LocalDateTime.now())
                                       .build();
        todolistRepository.save(updTodolist);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long board_id)
    {
        todolistRepository.deleteByBoardId(board_id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void detailDelete(List<Long> board_id_list)
    {
        todolistRepository.detailDeleteByBoardId(board_id_list);
    }

    @KafkaListener
    (
        topics = "todolist-insert",
        groupId = "board",
        containerFactory = "todolistDtoKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(TodolistDto todolistDto, Acknowledgment ack)
    {
        Todolist insTodolist = Todolist.builder().build();
        repoSave(insTodolist);
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "todolist-update",
        groupId = "board",
        containerFactory = "todolistDtoKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(TodolistDto todolistDto, Acknowledgment ack)
    {
        Todolist insTodolist = Todolist.builder().build();
        repoSave(insTodolist);
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "todolist-delete",
        groupId = "board",
        containerFactory = "delKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(DeleteDto deleteDto, Acknowledgment ack)
    {
        // todolistRepository.deleteDetail(todolist_id_list);
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "todolist-delete-detail",
        groupId = "board",
        containerFactory = "detailDelKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void detailDelete(DetailDeleteDto detailDeleteDto, Acknowledgment ack)
    {
        ack.acknowledge();
    }

    @Override
    public List<TodolistDto> select(Long board_id)
    {
        List<TodolistDto> todolists = new ArrayList<>();
        return todolists;
    }

    // Private Method
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoSave(Todolist todolist)
    {
        todolistRepository.save(todolist);
    }
}
