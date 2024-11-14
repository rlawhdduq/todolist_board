package todolist.board.api.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import todolist.board.dto.redis.RedisUserListDto;
import todolist.board.service.RedisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class RedisApi {

    @Autowired
    private RedisService redisService;

    @PostMapping("/api/internal/redis")
    public void postMethodName(@RequestBody RedisUserListDto redisUserListDto) {
        redisService.setRedis(redisUserListDto.getUser_id().toString(), (Object)redisUserListDto.getUserList());
    }

}
