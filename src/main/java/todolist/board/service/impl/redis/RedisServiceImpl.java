package todolist.board.service.impl.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import todolist.board.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService{

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
    public Boolean isThereKey(String key)
    {
        return redisTemplate.hasKey(key);
    }
}
