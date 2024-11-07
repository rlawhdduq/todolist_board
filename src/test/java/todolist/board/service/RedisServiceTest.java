package todolist.board.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RedisServiceTest.class);
    
    @Autowired
    private RedisService redisService;
    private String user_id = "user_id:5162553";
    @Test
    public void setRedisTest()
    {
        List<Long> userList = LongStream.range(1, 100)
                                        .boxed()
                                        .collect(Collectors.toList());

        redisService.setRedis(user_id, userList);
    }

    @Test
    public void getRedisTest()
    {
        List<Long> userList = (List<Long>)redisService.getRedis(user_id);
        for(Long users : userList)
        {
            log.info("볼까? " + users);
        }
    }

    @Test
    public void isThereKeyTest()
    {
        if(redisService.isThereKey(user_id))
        {
            log.info("있네요~");
        }
        else
        {
            log.info("없네요~");
        }
    }
}
