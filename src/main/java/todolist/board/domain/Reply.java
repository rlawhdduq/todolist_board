package todolist.board.domain;

import java.time.LocalDateTime;

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
@Table(name = "reply")
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reply_id;

    @Column
    private Long board_id;
    @Column
    private Long user_id;
    @Column
    private String content;
    @Column(insertable = false, updatable = true)
    private Short reply_depth;
    @Column(insertable = false, updatable = false)
    private LocalDateTime create_time;
    @Column(insertable = false, updatable = false)
    private Character status;
    @Column(insertable = false, updatable = true)
    private LocalDateTime update_time;
}
