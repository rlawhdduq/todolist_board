package todolist.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import todolist.board.domain.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{

}
