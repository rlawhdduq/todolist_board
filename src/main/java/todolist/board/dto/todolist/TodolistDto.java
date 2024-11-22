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
    private String todo_unit;
    private Short todo_number;
    private Character fulfillment_or_not;
    private Character status;
    private LocalDateTime update_time;

    public TodolistDto(Long todolist_id, Long board_id, LocalDateTime create_time,
    String todo_type, String todo_type_detail, String todo_unit, Short todo_number, Character fulfillment_or_not)
    {
        this.todolist_id = todolist_id;
        this.board_id = board_id;
        this.create_time = create_time;
        this.todo_type = todo_type;
        this.todo_type_detail = todo_type_detail;
        this.todo_unit = todo_unit;
        this.todo_number = todo_number;
        this.fulfillment_or_not = fulfillment_or_not;
    }
}
