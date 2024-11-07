package todolist.board.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "todolist")
public class Todolist {

    @Id
    @Column(name = "todolist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todolist_id;

    @Column
    private Long board_id;
    @Column(insertable = false, updatable = true)
    private LocalDateTime create_time;
    @Column(nullable = false)
    private String todo_type;
    @Column(nullable = false)
    private String todo_type_detail;
    @Column(nullable = false)
    private Short todo_unit;
    @Column(insertable = false, updatable = true)
    private Character fulfillment_or_not;
    @Column(insertable = false, updatable = true)
    private Character status;
    @Column(insertable = false, updatable = true)
    private LocalDateTime update_time;
}
