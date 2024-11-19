package todolist.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import todolist.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{

    @Modifying
    @Query(value = "Update board Set status = 'N', update_time = current_timestamp where board_id = :board_id and user_id = :user_id and status = 'Y'", nativeQuery = true)
    void deleteByBoardUserId(@Param("board_id") Long board_id, @Param("user_id") Long user_id);

    @Modifying
    @Query(value ="Update board Set status = 'N', update_time = current_timestamp Where board_id in (:board_id_list) and user_id = :user_id and status = 'Y'", nativeQuery = true)
    void detailDelete(@Param("board_id") List<Long> board_id_list, @Param("user_id") Long user_id);
}
