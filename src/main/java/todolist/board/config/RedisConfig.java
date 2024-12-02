package todolist.board.config;

import org.springframework.context.annotation.Configuration;
import org.aspectj.bridge.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import todolist.board.component.RedisMessageListener;

@Configuration
public class RedisConfig {
    
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private RedisMessageListener redisMessageListener;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // 키는 문자열로 직렬화시킴
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 값은 JSON 직렬화
        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer container()
    {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(new MessageListenerAdapter(redisMessageListener), new PatternTopic("all"));
        container.addMessageListener(new MessageListenerAdapter(redisMessageListener), new PatternTopic("friends:*"));
        container.addMessageListener(new MessageListenerAdapter(redisMessageListener), new PatternTopic("community:*"));
        return container;
    }
}
