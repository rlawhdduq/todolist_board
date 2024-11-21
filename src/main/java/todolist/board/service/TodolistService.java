package todolist.board.service;

import java.util.List;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.todolist.TodolistDto;

public interface TodolistService {

    void insert(TodolistDto todolistDto);
    void update(TodolistDto todolistDto);
    void delete(DeleteDto deleteDto);
    void detailDelete(DetailDeleteDto detailDeleteDto);
    List<TodolistDto> select(Long board_id);
    
    void insertFromBoard(TodolistDto todolistDto);
    void updateFromBoard(TodolistDto todolistDto);
    void deleteFromBoard(Long board_id);
    void detailDeleteFromBoard(List<Long> board_id_list);
}
