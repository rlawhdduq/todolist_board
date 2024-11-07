package todolist.board.dto.todolist;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodolistDto {
    private Long todolist_id;
    private Long board_id;
    private LocalDateTime create_time;
    private String todo_type;
    private String todo_type_detail;
    private Short todo_number;
    private String todo_unit;
    private Character fulfillment_or_not;
    private Character status;
    private LocalDateTime update_time;
}
