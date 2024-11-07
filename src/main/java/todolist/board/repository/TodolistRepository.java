package todolist.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import todolist.board.domain.Todolist;

@Repository
public interface TodolistRepository extends JpaRepository<Todolist, Long>{

}
