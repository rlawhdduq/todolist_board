package todolist.board.dto.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDto {
    private Long board_id;
    private Long user_id;
    private String scope_of_disclosure;
    private LocalDateTime create_time;
    private LocalDateTime fulfillment_time;
    private String content;
    private LocalDateTime update_time;

    public BoardListDto(Long board_id, Long user_id, String scope_of_disclosure, 
        LocalDateTime create_time, LocalDateTime fulfillment_time, String content)
    {
        this.board_id = board_id;
        this.user_id = user_id;
        this.scope_of_disclosure = scope_of_disclosure;
        this.create_time = create_time;
        this.fulfillment_time = fulfillment_time;
        this.content = content;
    }
}
