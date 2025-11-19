package todolist.board.repository.rest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;
import todolist.board.domain.Todolist;
import todolist.board.dto.todolist.TodolistDto;

@Repository("RestTodoRepo")
public interface TodolistRepository extends JpaRepository<Todolist, Long>{

    @Modifying
    @Query(value = "Update todolist set status = 'N', update_time = current_timestamp Where todolist_id = :todolist_id and board_id = :board_id and status = 'Y'", nativeQuery = true)
    void deleteByTodolistIdBoardId(@Param("todolist_id") Long todolist_id,@Param("board_id") Long board_id);
    @Modifying
    @Query(value = "Update todolist Set status = 'N', update_time = current_timestamp Where board_id = :board_id", nativeQuery = true)
    void deleteByBoardId(@Param("board_id") Long board_id);

    @Modifying
    @Query(value = "Update todolist Set status = 'N', update_time = current_timestamp Where todolist_id in (:todolist_id_list) and board_id = :board_id and status = 'Y'", nativeQuery = true)
    void detailDelete(@Param("board_id") Long board_id, @Param("todolist_id_list") List<Long> todolist_id_list);
    @Modifying
    @Query(value = "Update todolist Set status = 'N', update_time = current_timestamp Where board_id in (:board_id_lists)", nativeQuery = true)
    void detailDelete(@Param("board_id_list") List<Long> board_id_list);

    @Query(
        "Select new todolist.board.dto.todolist.TodolistDto(t.todolist_id, t.board_id, t.create_time, t.todo_type, t.todo_type_detail, t.todo_number, t.todo_unit, t.fulfillment_or_not, t.update_time) " +
        "From Todolist as t "+
        "Where "+
        "status = 'Y' and "+
        "board_id = :board_id "+
        "Order by create_time desc"
    )
    List<TodolistDto> getTodolist(@Param("board_id") Long board_id);
}
