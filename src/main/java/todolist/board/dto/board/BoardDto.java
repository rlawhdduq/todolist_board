package todolist.board.dto.board;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import todolist.board.dto.todolist.TodolistDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long board_id;
    private Long user_id;
    private String scope_of_disclosure;
    private Character fulfillment_or_not;
    private LocalDateTime create_time;
    private LocalDateTime fulfillment_time;
    private String content;
    private Character status;
    private LocalDateTime update_time;
    private List<TodolistDto> todolist;
}
