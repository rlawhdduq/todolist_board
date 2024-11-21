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
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
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
    @Column(insertable = false, updatable = false)
    private LocalDateTime create_time;
    @Column(insertable = false, updatable = true)
    private LocalDateTime fulfillment_time;
    @Column
    private String content;
    @Column(insertable = false, updatable = false)
    private Character status;
    @Column(insertable = false, updatable = true)
    private LocalDateTime update_time;

}
