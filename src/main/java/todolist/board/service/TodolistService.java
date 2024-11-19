package todolist.board.service;

import java.util.List;

import org.springframework.kafka.support.Acknowledgment;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.todolist.TodolistDto;

public interface TodolistService {

    void insert(TodolistDto todolistDto);                                       // todolist 등록
    void insert(TodolistDto todolistDto, Acknowledgment ack);                   // todolist 등록(카프카)
    void update(TodolistDto todolistDto);                                       // todolist 수정
    void update(TodolistDto todolistDto, Acknowledgment ack);                   // todolist 수정(카프카)
    void detailDelete(DetailDeleteDto detailDeleteDto, Acknowledgment ack);     // todolist 일부 삭제
    void detailDelete(List<Long> board_id_list);                                // todolist 일부 삭제
    void delete(Long board_id);                                                 // todolist 전체 삭제
    void delete(DeleteDto deleteDto, Acknowledgment ack);                       // todolist 전체 삭제(카프카)
    List<TodolistDto> select(Long board_id);                                    // 게시글의 todolist 조회

}
