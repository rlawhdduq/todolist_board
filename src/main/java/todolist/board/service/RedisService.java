package todolist.board.service;

public interface RedisService {
    
    void setRedis(String key, Object value);
    Object getRedis(String key);
    Boolean existKey(String key);

}
