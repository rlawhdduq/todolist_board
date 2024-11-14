package todolist.board.dto.redis;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisUserListDto {
    
    private Long user_id;
    private List<Long> userList;

}
