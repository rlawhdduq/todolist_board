package todolist.board.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import todolist.board.dto.redis.RedisUserListDto;

@SpringBootTest
public class RedisServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RedisServiceTest.class);
    
    @Autowired
    private RedisService redisService;
    private String user_id = "100";
    // @Test
    public void setRedisTest()
    {
        List<Long> userList = LongStream.range(1, 100)
                                        .boxed()
                                        .collect(Collectors.toList());

        redisService.setRedis(user_id, userList);
    }

    // @Test
    public void getRedisTest()
    {
        log.info("테스트시작");
        RedisUserListDto userList = new RedisUserListDto();
        userList.setUserList((List<Long>) redisService.getRedis(user_id));
        userList.setUser_id(Long.parseLong(user_id));
        for(Long users : userList.getUserList())
        {
            log.info("볼까? " + users);
        }
        log.info("테스트종료");
    }
}
