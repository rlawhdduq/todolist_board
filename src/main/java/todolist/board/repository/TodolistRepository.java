package todolist.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import todolist.board.domain.Todolist;

@Repository
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
}
