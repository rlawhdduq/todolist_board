package todolist.board.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        Map<String, Object> userList = (Map<String, Object>) redisService.getRedis("100");
        List<Long> getKeyA = (List<Long>) Optional.ofNullable(userList.get("A")).orElse(new ArrayList<>());
        List<Long> getKeyF = (List<Long>) Optional.ofNullable(userList.get("F")).orElse(new ArrayList<>());
        List<Long> getKeyC = (List<Long>) Optional.ofNullable(userList.get("C")).orElse(new ArrayList<>());
        log.info("Get Key A => " +getKeyA.toString());
        log.info("Get Key F => " +getKeyF.toString());
        log.info("Get Key C => " +getKeyC.toString());
        log.info("테스트종료");
    }
}
