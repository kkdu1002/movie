package movie.movie.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Schedule {

    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    //シアター
    private String theater;

    //上映時間
    @Column(name = "screen_time")
    private LocalDateTime screenTime;

    //予約可能座席数
    private int availableSeats;

    @OneToMany(mappedBy = "schedule")
    private List<Reservation> reservationList = new ArrayList<>();

    //シアター変更
    public void updateTheater(String theater) {
        if(StringUtils.hasText(theater)) {
            this.theater = theater;
        }
    }

    //上映時間変更
    public void updateScreenTime(LocalDateTime screenTime) {
        if(screenTime != null) {
            this.screenTime = screenTime;
        }
    }

}
