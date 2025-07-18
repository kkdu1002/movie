package movie.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMovieDto {

    //タイトル
    private String title;
    //監督
    private String director;
    //ジャンル
    private String genre;
}
