package todolist.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import todolist.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{

}
