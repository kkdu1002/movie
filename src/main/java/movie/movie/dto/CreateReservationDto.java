package movie.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationDto {

    //　ユーザー
    private Long userId;
    // スケジュール
    private Long scheduleId;
    // 座席
    private String seatNumber;
}
