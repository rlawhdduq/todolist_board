// package todolist.board.api.internal;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RestController;

// import todolist.board.dto.redis.RedisUserListDto;
// import todolist.board.service.RedisService;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;


// @RestController
// public class RedisApi {

//     @Autowired
//     private RedisService redisService;

//     @PostMapping("/api/internal/redis")
//     public void postMethodName(@RequestBody RedisUserListDto redisUserListDto) {
//         redisService.setRedis(redisUserListDto.getUser_id().toString(), (Object)redisUserListDto.getUserList());
//     }

// }

// 비동기 메시지 큐 방식(카프카, 레디스 사용)에서 RestApi 방식으로 구현방향을 변경함에 따라 기존에 사용하던 의존성들을 주석처리한다.
// 비동기 메시지 큐 방식은 RestApi 방식으로 구현을 마친 후 성능개선을 위해 해당방식으로 변경할 때 다시 주석을 풀고 사용하자.