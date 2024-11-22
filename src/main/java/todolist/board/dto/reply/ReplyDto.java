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
    private Long parent_id;
    private String content;
    private Short reply_depth;
    private LocalDateTime create_time;
    private Character status;
    private LocalDateTime update_time;

    public ReplyDto(Long reply_id, Long board_id, Long user_id, Long parent_id, String content,
    Short reply_depth, LocalDateTime create_time)
    {
        this.reply_id = reply_id;
        this.board_id = board_id;
        this.user_id = user_id;
        this.parent_id = parent_id;
        this.content = content;
        this.reply_depth = reply_depth;
        this.create_time = create_time;
    }
}
