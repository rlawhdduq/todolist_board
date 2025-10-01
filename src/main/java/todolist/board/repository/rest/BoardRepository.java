package todolist.board.repository.rest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;
import todolist.board.domain.Board;
import todolist.board.dto.board.BoardDetailDto;
import todolist.board.dto.board.BoardListDto;
import todolist.board.dto.reply.ReplyDto;
import todolist.board.dto.todolist.TodolistDto;

@Repository("RestBoardRepo")
public interface BoardRepository extends JpaRepository<Board, Long>{

    @Modifying
    @Query(value = "Update board Set status = 'N', update_time = current_timestamp where board_id = :board_id and user_id = :user_id and status = 'Y'", nativeQuery = true)
    void deleteByBoardUserId(@Param("board_id") Long board_id, @Param("user_id") Long user_id);

    @Modifying
    @Query(value ="Update board Set status = 'N', update_time = current_timestamp Where board_id in (:board_id_list) and user_id = :user_id and status = 'Y'", nativeQuery = true)
    void detailDelete(@Param("board_id") List<Long> board_id_list, @Param("user_id") Long user_id);

    @Query( 
            "Select new todolist.board.dto.board.BoardListDto(b.board_id, b.user_id, b.scope_of_disclosure, b.create_time, b.fulfillment_time, b.content) "+
            "From Board as b "+
            "Where "+
            "status = 'Y' and " +
            "( board_id = :board_id or user_id in (:followIds) or user_id in (:groupIds) ) " +
            "Order by board_id desc " +
            "Limit 20 "
            )
    List<BoardListDto> getBoardList(@Param("board_id") Long board_id, @Param("followIds") List<Long> followIds, @Param("groupIds") List<Long> groupIds);

    @Query("Select new todolist.board.dto.board.BoardDetailDto(b.board_id, b.user_id, b.scope_of_disclosure, b.fulfillment_or_not, b.create_time, b.fulfillment_time, b.content) From Board as b Where b.board_id = :board_id and status = 'Y'")
    BoardDetailDto getDetailBoard(@Param("board_id") Long board_id);
    @Query("Select new todolist.board.dto.todolist.TodolistDto(t.todolist_id, t.board_id, t.create_time, t.todo_type, t.todo_type_detail, t.todo_unit, t.todo_number, t.fulfillment_or_not) From Todolist as t Where t.board_id = :board_id and status = 'Y'")
    List<TodolistDto> getTodolist(@Param("board_id") Long board_id);
    @Query("Select new todolist.board.dto.reply.ReplyDto(r.reply_id, r.board_id, r.user_id, r.parent_id, r.content, r.reply_depth, r.create_time) From Reply as r Where r.board_id = :board_id and status = 'Y'")
    List<ReplyDto> getReply(@Param("board_id") Long board_id);
}
