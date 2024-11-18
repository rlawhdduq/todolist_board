package todolist.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import todolist.board.domain.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{

    @Modifying
    @Query(value = "Update reply set status = 'N' Where board_id = :board_id", nativeQuery = true)
    void deleteByBoardId(@Param("board_id") Long board_id);

    @Modifying
    @Query(value = "Update reply Set status = 'N' Where reply_id in (:reply_id_list)", nativeQuery = true)
    void deleteDetail(@Param("reply_id_list") List<Long> reply_id_list);
}
