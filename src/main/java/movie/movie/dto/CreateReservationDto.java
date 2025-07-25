package movie.movie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @NotBlank(message = "座席は必須")
    private String seatNumber;
}
