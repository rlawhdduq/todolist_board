package todolist.board.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.board.dto.delete.DeleteDto;
import todolist.board.dto.delete.DetailDeleteDto;
import todolist.board.dto.reply.ReplyDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
public class ReplyServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    String request;

    // @Test
    public void insertReply()
    throws Exception
    {
        ReplyDto dto = new ReplyDto();
        Short depth = 1;
        dto.setBoard_id(45L);
        dto.setUser_id(127L);
        dto.setContent("댓글 테슽흐333");
        dto.setParent_id(5L);
        dto.setReply_depth(depth);
        request = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/board/reply").contentType(MediaType.APPLICATION_JSON).content(request));
    }

    // @Test
    public void updateReply()
    throws Exception
    {
        Short depth = 0;
        ReplyDto dto = new ReplyDto();
        dto.setReply_id(10L);
        dto.setBoard_id(45L);
        dto.setUser_id(127L);
        dto.setContent("댓글 수정이요");
        dto.setReply_depth(depth);
        request = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/board/reply").contentType(MediaType.APPLICATION_JSON).content(request));
    }

    // @Test
    public void deleteReply()
    throws Exception
    {
        DeleteDto dto = new DeleteDto();
        dto.setForeign_key(45L);
        dto.setKey(10L);
        request = objectMapper.writeValueAsString(dto);
        mockMvc.perform(delete("/api/board/reply").contentType(MediaType.APPLICATION_JSON).content(request));
    }

    // @Test
    public void detailDeleteReply()
    throws Exception
    {
        DetailDeleteDto dto = new DetailDeleteDto();
        List<Long> arr = new ArrayList<>();

        arr.add(4L);
        arr.add(7L);
        arr.add(10L);
        dto.setForeign_key(45L);
        dto.setKey_list(arr);
        request = objectMapper.writeValueAsString(dto);
        mockMvc.perform(delete("/api/board/reply/detail").contentType(MediaType.APPLICATION_JSON).content(request));
    }
}
