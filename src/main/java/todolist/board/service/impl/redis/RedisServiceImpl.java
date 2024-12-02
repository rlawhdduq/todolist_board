package todolist.board.service.impl.redis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import todolist.board.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService{

    private static final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setRedis(String key, Object value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object getRedis(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean existKey(String key)
    {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void newBoardMessage(String scope_of_disclosure, Long key, Object msg)
    {
        final Map<String, String> scope = new HashMap<String, String>(){{
            put("A", "all");
            put("F", "friends:");
            put("C", "community:");
        }};
        
        String topic = scope.get(scope_of_disclosure);
        if( !scope_of_disclosure.equals("A") )
        {
            topic += key;
        }
        log.info("redis topic = " + topic);
        redisTemplate.convertAndSend("board/"+topic, msg);
    }
}
