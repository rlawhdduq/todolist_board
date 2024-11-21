package todolist.board.api;

import org.springframework.web.bind.annotation.RestController;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.todolist.TodolistDto;
import todolist.board.service.TodolistService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class TodolistApi {
    @Autowired
    private TodolistService todolistService;
    
    @PostMapping("/api/board/todolist")
    public void insertTodolist(@RequestBody TodolistDto todolistDto) {
        todolistService.insert(todolistDto);
        return;
    }
    @PutMapping("/api/board/todolist")
    public void updateTodolist(@RequestBody TodolistDto todolistDto) {
        todolistService.update(todolistDto);
        return;
    }
    @DeleteMapping("/api/board/todolist")
    public void deleteTodolist(@RequestBody DeleteDto deleteDto)
    {
        todolistService.delete(deleteDto);
        return;
    }
    @DeleteMapping("/api/board/todolist/detail")
    public void detailDeleteTodolist(@RequestBody DetailDeleteDto detailDeleteDto)
    {
        todolistService.detailDelete(detailDeleteDto);
        return;
    }
}
