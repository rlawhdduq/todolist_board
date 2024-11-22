package todolist.board.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetDto {
    private Long user_id;
    private Integer limit;
    private Integer offset; 
}
