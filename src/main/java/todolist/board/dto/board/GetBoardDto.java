package todolist.board.dto.board;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardDto {

    private Long userId;
    private List<Long> followIds;
    private List<Long> groupIds;
    
}
