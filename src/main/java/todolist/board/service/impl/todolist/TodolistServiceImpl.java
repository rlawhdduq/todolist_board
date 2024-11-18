package todolist.board.service.impl.todolist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;
import todolist.board.domain.Todolist;
import todolist.board.dto.todolist.TodolistDto;
import todolist.board.repository.TodolistRepository;
import todolist.board.service.TodolistService;

public class TodolistServiceImpl implements TodolistService{
    
    @Autowired
    private TodolistRepository todolistRepository;

    @Transactional
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

    @Transactional
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

    @Transactional
    @Override 
    public void delete(Long board_id)
    {
        todolistRepository.deleteByBoardId(board_id);
    }

    @Transactional
    @Override
    public void delete(List<Long> todolist_id_list)
    {
        todolistRepository.deleteDetail(todolist_id_list);
    }

    @Override
    public List<TodolistDto> select(Long board_id)
    {
        List<TodolistDto> todolists = new ArrayList<>();
        return todolists;
    }
}
