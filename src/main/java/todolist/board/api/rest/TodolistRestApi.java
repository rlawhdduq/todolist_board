package todolist.board.api.rest;

import org.springframework.web.bind.annotation.RestController;

import todolist.board.dto.todolist.TodolistDto;
import todolist.board.service.rest.TodolistService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/rest/todo")
public class TodolistRestApi {
    @Autowired
    private TodolistService todolistService;
    
    @RequestMapping(path="/{boardId}", method=RequestMethod.GET)
    public List<TodolistDto> getTodolist(@PathVariable Long boardId)
    {
        List<TodolistDto> todolists = todolistService.getTodolist(boardId);
        return todolists;
    }
    @RequestMapping(method=RequestMethod.POST)
    public String insertTodolist(@RequestBody TodolistDto todolistDto)
    {
        todolistService.insert(todolistDto);
        return "Todolist 등록 성공";
    }
    @RequestMapping(method=RequestMethod.PUT)
    public String updateTodolist(@RequestBody TodolistDto todolistDto)
    {
        todolistService.update(todolistDto);
        return "Todolist 수정 성공";
    }
    @RequestMapping(path="/{boardId}", method=RequestMethod.DELETE)
    public String deleteTodolist(@PathVariable Long boardId)
    {
        // 이거 board용 서비스이긴한데,,, 일단 쓰자,,,
        todolistService.deleteFromBoard(boardId);
        return "Todolist 삭제 성공";
    }
    @RequestMapping(path="/detail/{todoId}", method=RequestMethod.GET)
    public String detailDeleteTodolist(@PathVariable Long todoId)
    {
        // todolistService.detailDelete(??); -> todoId로 삭제하는 repo를 하나 만들어줘야 한다...
        return "특정 Todolist 삭제 성공";
    }
    
}
