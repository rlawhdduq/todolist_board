package todolist.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import todolist.board.domain.Todolist;
import todolist.board.dto.board.BoardDto;
import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.todolist.TodolistDto;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private KafkaProducer kafka;

    private Long user_id_long = 100L;
    private String user_id = "user_id:5162553";
    private static final Logger log = LoggerFactory.getLogger(BoardServiceTest.class);

    // @Test
    public void selectBoardAllTest()
    {
        log.info("테스트 코드 시작");
        try{

            List<BoardDto> boardList = boardService.selectBoardAll(user_id_long);
        }
        catch(Exception e)
        {
            for(StackTraceElement err: e.getStackTrace())
            {
                log.info(err.getClassName());
            }
        }
        
        log.info("테스트 코드 종료");
    }

    // @Test
    public void insertBoard()
    {
        String topic = "board-insert";
        BoardDto boardDto = new BoardDto();

        boardDto.setUser_id(113729L);
        boardDto.setScope_of_disclosure("A");
        boardDto.setContent("Kafka Test55");

        kafka.sendMessage(topic, (Object) boardDto);
    }

    // @Test
    public void updateBoard()
    {
        String topic = "board-update";
        String ScopeOfDisclosure = "C"; // A, C, F
        Character FulfillmentOrNot = 'F'; // C, F
        Long boardId = 1L;
        Long userId = 1730L;
        BoardDto boardDto = new BoardDto();

        boardDto.setBoard_id(boardId);
        boardDto.setUser_id(userId);
        boardDto.setScope_of_disclosure(ScopeOfDisclosure);
        boardDto.setFulfillment_or_not(FulfillmentOrNot);
        boardDto.setFulfillment_time(null);
        // boardDto.setContent("크크크 교체되어라 얍!");
        kafka.sendMessage(topic, (Object) boardDto);
    }

    // @Test
    public void deleteBoard()
    {
        String topic = "board-delete";
        Long board_id = 44L;
        Long user_id = 5358L;
        DeleteDto deleteDto = new DeleteDto();
        deleteDto.setKey(board_id);
        deleteDto.setForeign_key(user_id);
        kafka.sendMessage(topic, (Object) deleteDto);
    }

    // @Test
    public void detailDeleteBoard()
    {
        String topic = "board-delete-detail";
        Long user_id = 1729L;
        List<Long> board_id_list = new ArrayList<>();
        board_id_list.add(1L);
        board_id_list.add(2L);
        board_id_list.add(8L);
        board_id_list.add(5L);
        DetailDeleteDto detailDeleteDto = new DetailDeleteDto();
        detailDeleteDto.setForeign_key(user_id);
        detailDeleteDto.setKey_list(board_id_list);

        kafka.sendMessage(topic, (Object) detailDeleteDto);
    }

    // @Test
    public void totalInsertBoard()
    {
        String topic = "board-insert";
        BoardDto boardDto = new BoardDto();
        Long user_id = 5358L;
        TodolistDto todolistDto = new TodolistDto();
        Short todoNumber = 1, todoNumber2 = 3;
        todolistDto.setTodo_type("운동");
        todolistDto.setTodo_type_detail("데드리프트/60kg/1세트");
        todolistDto.setTodo_unit("회");
        todolistDto.setTodo_number(todoNumber);
        List<TodolistDto> todolistsDto = new ArrayList<>();
        todolistsDto.add(todolistDto);

        TodolistDto todolistDto2 = new TodolistDto();
        todolistDto2.setTodo_type("운동");
        todolistDto2.setTodo_type_detail("데드리프트/50kg/3세트");
        todolistDto2.setTodo_unit("회");
        todolistDto2.setTodo_number(todoNumber2);
        todolistsDto.add(todolistDto2);

        TodolistDto todolistDto3 = new TodolistDto();
        todolistDto3.setTodo_type("운동");
        todolistDto3.setTodo_type_detail("데드리프트/40kg/2세트");
        todolistDto3.setTodo_unit("회");
        todolistDto3.setTodo_number(todoNumber);
        todolistsDto.add(todolistDto3);

        boardDto.setUser_id(user_id);
        boardDto.setContent("totalInsertBoardTest 1 입니다~ \n 이스케이프 문자는 어떻게 들어갈지 궁금하네요 \r\n 오늘의 데드리프트 기록~");
        boardDto.setScope_of_disclosure("A");
        boardDto.setTodolist(todolistsDto);

        kafka.sendMessage(topic, (Object) boardDto);
    }
}
