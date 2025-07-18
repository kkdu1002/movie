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

    //상영관 이름
    private String theater;

    //상영 시작 시간
    @Column(name = "screen_time")
    private LocalDateTime screenTime;

    //현재 예약 가능한 좌석 수
    private int availableSeats;

    @OneToMany(mappedBy = "schedule")
    private List<Reservation> reservationList = new ArrayList<>();

    public void updateTheater(String theater) {
        if(StringUtils.hasText(theater)) {
            this.theater = theater;
        }
    }

    public void updateScreenTime(LocalDateTime screenTime) {
        if(screenTime != null) {
            this.screenTime = screenTime;
        }
    }

    public void updateAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats - reservationList.size();
    }
}
