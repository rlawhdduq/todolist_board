package todolist.board.dto.reply;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {
    private Long reply_id;
    private Long board_id;
    private Long user_id;
    private String content;
    private Short reply_depth;
    private LocalDateTime create_time;
    private Character status;
    private LocalDateTime update_time;
}
