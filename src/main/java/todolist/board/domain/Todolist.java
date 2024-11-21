package todolist.board.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "todolist")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Todolist {

    @Id
    @Column(name = "todolist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todolist_id;

    @Column
    private Long board_id;
    @Column(insertable = false, updatable = false)
    private LocalDateTime create_time;
    @Column(insertable = true)
    private String todo_type;
    @Column(insertable = true)
    private String todo_type_detail;
    @Column(insertable = true)
    private String todo_unit;
    @Column(insertable = true)
    private Short todo_number;
    @Column(insertable = false, updatable = true)
    private Character fulfillment_or_not;
    @Column(insertable = false, updatable = false)
    private Character status;
    @Column(insertable = false, updatable = true)
    private LocalDateTime update_time;
}
