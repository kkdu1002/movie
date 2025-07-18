package movie.movie.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.data.annotation.Persistent;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reservation {

    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String seatNumber;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    public void updateSchedule(Schedule schedule) {
        if(schedule != null) {
            this.schedule = schedule;
        }
    }

    public void updateSeatNumber(String seatNumber) {
        if(seatNumber != null) {
            this.seatNumber = seatNumber;
        }
    }

    public void updateTime() {
        this.reservationTime = LocalDateTime.now();
    }

    // init
    @PrePersist
    public void prePersist() {
        this.reservationTime = LocalDateTime.now();
    }
}
