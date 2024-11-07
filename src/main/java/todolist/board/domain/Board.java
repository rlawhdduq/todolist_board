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
@Table(name = "board")
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    @Column(nullable = false)
    private Long user_id;
    @Column(nullable = false)
    private String scope_of_disclosure;
    @Column(insertable = false, updatable = true)
    private Character fulfillment_or_not;
    @Column(insertable = false, updatable = true)
    private LocalDateTime create_time;
    @Column(insertable = false, updatable = true)
    private LocalDateTime fulfillment_time;
    @Column
    private String content;
    @Column(insertable = false, updatable = true)
    private Character status;
    @Column(insertable = false, updatable = true)
    private LocalDateTime update_time;

}
