package todolist.board.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import todolist.board.dto.board.BoardDto;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    private Long user_id_long = 100L;
    private String user_id = "user_id:5162553";
    private static final Logger log = LoggerFactory.getLogger(BoardServiceTest.class);

    @Test
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
}
