package movie.movie.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieDto {

    @NotBlank(message = "제목 필수")
    private String title;
    @NotBlank(message = "감독 필수")
    private String director;
    @NotNull(message = "상영시간 필수")
    @Min(value = 1, message = "상영시간은 1분 이상이어야 합니다.")
    private int runtime;
    @NotBlank(message = "장르 필수")
    private String genre;
}
