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
    @Query(value = "Update todolist set status = 'N', update_time = current_timestamp where board_id = :board_id and status = 'Y'", nativeQuery = true)
    void deleteByBoardId(@Param("board_id") Long board_id);

    @Modifying
    @Query(value = "Update todolist Set status = 'N', update_time = current_timestamp Where board_id in (:board_id_list) and status = 'Y'", nativeQuery = true)
    void detailDeleteByBoardId(@Param("board_id_list") List<Long> board_id_list);
}
