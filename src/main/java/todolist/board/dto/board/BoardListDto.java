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
}
