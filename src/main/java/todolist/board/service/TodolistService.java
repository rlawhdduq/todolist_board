package todolist.board.service;

import java.util.List;

import todolist.board.dto.todolist.TodolistDto;

public interface TodolistService {

    void insert(TodolistDto todolistDto);   // todolist 등록
    void update(TodolistDto todolistDto);   // todolist 수정
    void delete(Long todolist_id);          // todolist 삭제
    List<TodolistDto> select(Long board_id);// 게시글의 todolist 조회

}
