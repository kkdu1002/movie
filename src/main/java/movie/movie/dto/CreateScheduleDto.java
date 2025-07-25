package movie.movie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateScheduleDto {

    // 映画
    private Long movieId;
    //シアター
    @NotBlank(message = "シアターは必須")
    private String theater;

    // 映画上映時間
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "上映時間は必須")
    private LocalDateTime screenTime;

    // 使用可能座席数
    @NotNull(message = "使用可能座席数は必須")
    private Integer availableSeats;
}
