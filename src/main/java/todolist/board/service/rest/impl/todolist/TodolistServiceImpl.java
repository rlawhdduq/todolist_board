package todolist.board.service.rest.impl.todolist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import todolist.board.domain.Todolist;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.todolist.TodolistDto;
import todolist.board.repository.rest.TodolistRepository;
// import todolist.board.service.KafkaProducer;
import todolist.board.service.rest.TodolistService;

@Service("restTodoService")
public class TodolistServiceImpl implements TodolistService{
    
    private static final Logger log = LoggerFactory.getLogger(TodolistServiceImpl.class);
    @Autowired
    private TodolistRepository todolistRepository;

    @Override 
    public void insert(TodolistDto todolistDto)
    {
        Todolist insTodolist = Todolist.builder()
                                       .board_id(todolistDto.getBoard_id())
                                       .todo_type(todolistDto.getTodo_type())
                                       .todo_type_detail(todolistDto.getTodo_type_detail())
                                       .todo_unit(todolistDto.getTodo_unit())
                                       .todo_number(todolistDto.getTodo_number())
                                       .build();
        repoSave(insTodolist);
    }
    @Override 
    public void update(TodolistDto todolistDto)
    {
        Todolist insTodolist = Todolist.builder()
                                       .todolist_id(todolistDto.getTodolist_id())
                                       .board_id(todolistDto.getBoard_id())
                                       .todo_type(todolistDto.getTodo_type())
                                       .todo_type_detail(todolistDto.getTodo_type_detail())
                                       .todo_unit(todolistDto.getTodo_unit())
                                       .todo_number(todolistDto.getTodo_number())
                                       .fulfillment_or_not(todolistDto.getFulfillment_or_not())
                                       .update_time(LocalDateTime.now())
                                       .build();
        repoSave(insTodolist);
    }
    @Override 
    public void delete(DeleteDto deleteDto)
    {
        repoDel(deleteDto);
    }
    @Override 
    public void detailDelete(DetailDeleteDto detailDeleteDto)
    {
        repoDetailDel(detailDeleteDto);
    }

    @Override
    public void insertFromBoard(TodolistDto todolistDto)
    {
        Todolist insTodolist = Todolist.builder()
                                .board_id(todolistDto.getBoard_id()) // board 인서트 후 이 값을 채워줘야함.
                                .todo_type(todolistDto.getTodo_type())
                                .todo_type_detail(todolistDto.getTodo_type_detail())
                                .todo_unit(todolistDto.getTodo_unit())
                                .todo_number(todolistDto.getTodo_number())
                                .build();
        repoSave(insTodolist);
    }

    @Override
    public void updateFromBoard(TodolistDto todolistDto)
    {
        Todolist updTodolist = Todolist.builder()
                                       .todolist_id(todolistDto.getTodolist_id())
                                       .board_id(todolistDto.getBoard_id())
                                       .todo_type(todolistDto.getTodo_type())
                                       .todo_type_detail(todolistDto.getTodo_type_detail())
                                       .todo_number(todolistDto.getTodo_number())
                                       .todo_unit(todolistDto.getTodo_unit())
                                       .fulfillment_or_not(todolistDto.getFulfillment_or_not())
                                       .update_time(LocalDateTime.now())
                                       .build();
        repoSave(updTodolist);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFromBoard(Long board_id)
    {
        todolistRepository.deleteByBoardId(board_id);
        return;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void detailDeleteFromBoard(List<Long> board_id_list)
    {
        todolistRepository.detailDelete(board_id_list);
        return;
    }

    @Override
    public List<TodolistDto> getTodolist(Long board_id)
    {
        List<TodolistDto> todolists = todolistRepository.getTodolist(board_id);
        return todolists;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void repoSave(Todolist todolist)
    {
        todolistRepository.save(todolist);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoDel(DeleteDto deleteDto) 
    {
        todolistRepository.deleteByTodolistIdBoardId(deleteDto.getKey(), deleteDto.getForeign_key());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void repoDetailDel(DetailDeleteDto detailDeleteDto)
    {
        todolistRepository.detailDelete(detailDeleteDto.getForeign_key(), detailDeleteDto.getKey_list());
    }
}