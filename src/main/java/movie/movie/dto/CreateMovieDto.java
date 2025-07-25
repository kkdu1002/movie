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

    @NotBlank(message = "タイトルは必須")
    private String title;
    @NotBlank(message = "映画監督は必須")
    private String director;
    @NotNull(message = "上映時間必須")
    @Min(value = 1, message = "上映時間は１分以上です。")
    private Integer runtime;
    @NotBlank(message = "ジャンル必須")
    private String genre;
}
