package todolist.board.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements MessageListener{
    
    private final SimpMessagingTemplate messageTemplate;
    
    @Autowired
    public RedisMessageListener(SimpMessagingTemplate messagingTemplate)
    {
        this.messageTemplate = messagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern)
    {
        String topic = new String(pattern);
        String payload = new String(message.getBody());

        messageTemplate.convertAndSend("/board/"+topic, payload);
    }
}
