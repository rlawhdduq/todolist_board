package todolist.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedis(String key, Object value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getRedis(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean isThereKey(String key)
    {
        return redisTemplate.hasKey(key);
    }
}
