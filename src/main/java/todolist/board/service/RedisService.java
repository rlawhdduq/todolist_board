package todolist.board.service;


public interface RedisService {
    
    void setRedis(String key, Object value);
    Object getRedis(String key);
    Boolean existKey(String key);
    void newBoardMessage(String scope_of_disclosure, Long key, Object msg);
}
