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
    @Query(value = "Update todolist set status = 'N' where board_id = :board_id", nativeQuery = true)
    void deleteByBoardId(@Param("board_id") Long board_id);

    @Modifying
    @Query(value = "Update todolist Set status = 'N' Where todolist_id in (:todolist_id_list)", nativeQuery = true)
    void deleteDetail(@Param("todolist_id_list") List<Long> todolist_id_list);
}
