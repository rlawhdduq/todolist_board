package todolist.board.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.todolist.TodolistDto;

@SpringBootTest
@AutoConfigureMockMvc
public class TodolistServiceTest {
    
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    
    String request;

    // @Test
    public void insertTodolist()
    throws Exception
    {
        TodolistDto dto = new TodolistDto();
        Long board_id = 46L;
        String todo_type = "헬스";
        String todo_type_detail = "벤치프레스40kg/3세트";
        String todo_unit = "회";
        Short todo_number = 1;
        dto.setBoard_id(board_id);
        dto.setTodo_type(todo_type);
        dto.setTodo_type_detail(todo_type_detail);
        dto.setTodo_unit(todo_unit);
        dto.setTodo_number(todo_number);
        int num = 0;
        while(num < 10)
        {
            request = objectMapper.writeValueAsString(dto);
            mockMvc.perform(post("/api/board/todolist").contentType(MediaType.APPLICATION_JSON).content(request));
            num++;
        }
    }

    // @Test
    public void updateTodolist()
    throws Exception
    {
        TodolistDto dto = new TodolistDto();
        Long board_id = 46L;
        Long todolist_id = 13L;
        String todo_type = "운동";
        String todo_type_detail = "벤치프레스40kg/3세트";
        String todo_unit = "개";
        Short todo_number = 10;
        dto.setTodolist_id(todolist_id);
        dto.setBoard_id(board_id);
        dto.setTodo_type(todo_type);
        dto.setTodo_type_detail(todo_type_detail);
        dto.setTodo_unit(todo_unit);
        dto.setTodo_number(todo_number);
        dto.setFulfillment_or_not('R');
        request = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/board/todolist").contentType(MediaType.APPLICATION_JSON).content(request));
    }

    // @Test
    public void deleteTodolist()
    throws Exception
    {
        DeleteDto dto = new DeleteDto();
        dto.setForeign_key(46L);
        dto.setKey(13L);
        request = objectMapper.writeValueAsString(dto);
        mockMvc.perform(delete("/api/board/todolist").contentType(MediaType.APPLICATION_JSON).content(request));
    }

    // @Test
    public void detailDeleteTodolist()
    throws Exception
    {
        DetailDeleteDto dto = new DetailDeleteDto();
        dto.setForeign_key(46L);
        List<Long> list = new ArrayList<>();
        list.add(12L);
        list.add(16L);
        list.add(20L);
        dto.setKey_list(list);
        request = objectMapper.writeValueAsString(dto);
        mockMvc.perform(delete("/api/board/todolist/detail").contentType(MediaType.APPLICATION_JSON).content(request));
    }
}
