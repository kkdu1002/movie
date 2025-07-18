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

    //ユーザー
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //映画のスケジュール
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    // 座席
    private String seatNumber;

    // 予約時間
    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    // スケジュール変更
    public void updateSchedule(Schedule schedule) {
        if(schedule != null) {
            this.schedule = schedule;
        }
    }

    //　座席変更
    public void updateSeatNumber(String seatNumber) {
        if(seatNumber != null) {
            this.seatNumber = seatNumber;
        }
    }

    // 予約時間変更
    public void updateTime() {
        this.reservationTime = LocalDateTime.now();
    }

    // 予約時間初期設定
    @PrePersist
    public void prePersist() {
        this.reservationTime = LocalDateTime.now();
    }
}
