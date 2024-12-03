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

    public BoardDto(Long board_id, Long user_id, String scope_of_disclosure, Character fulfillment_of_not, 
        LocalDateTime create_time, LocalDateTime fulfillment_time, String content, LocalDateTime update_time)
    {
        this.board_id = board_id;
        this.user_id = user_id;
        this.scope_of_disclosure = scope_of_disclosure;
        this.fulfillment_or_not = fulfillment_of_not;
        this.create_time = create_time;
        this.fulfillment_time = fulfillment_time;
        this.content = content;
        this.update_time = update_time;
    }
}
