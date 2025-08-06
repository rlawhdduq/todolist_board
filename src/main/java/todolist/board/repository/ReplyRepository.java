package todolist.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;
import todolist.board.domain.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{

    @Modifying
    @Query(value = "Update reply Set status = 'N', update_time = current_timestamp Where reply_id = :reply_id and board_id = :board_id and status = 'Y'", nativeQuery = true)
    void deleteByReplyIdBoardId(@Param("reply_id") Long reply_id, @Param("board_id") Long board_id);
    @Modifying
    @Query(value = "Update reply Set status = 'N', update_time = current_timestamp Where board_id = :board_id", nativeQuery = true)
    void deleteByBoardId(@Param("board_id") Long board_id);
    
    @Modifying
    @Query(value = "Update reply Set status = 'N', update_time = current_timestamp Where board_id = :board_id and reply_id in (:reply_id_list) and status = 'Y'", nativeQuery = true)
    void detailDelete(@Param("board_id") Long board_id,@Param("reply_id_list") List<Long> reply_id_list);
    @Modifying
    @Query(value = "Update reply Set status = 'N', update_time = current_timestamp Where board_id in (:board_id_list)", nativeQuery = true)
    void detailDelete(@Param("board_id_list") List<Long> board_id_list);
}
