package movie.movie.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Movie {

    @Id @GeneratedValue
    @Column(name = "movie_id")
    private Long id;

    // タイトル
    private String title;

    // ジャンル
    private String genre;

    // 監督
    private String director;

    // 上映時間
    private int runtime;

    // タイトル更新
    public void updateTitle(String title) {
        if(StringUtils.hasText(title)) {
            this.title = title;
        }
    }

    // ジャンル更新
    public void updateGenre(String genre) {
        if(StringUtils.hasText(genre)) {
            this.genre = genre;
        }
    }

    // 監督更新
    public void updateDirector(String director) {
        if(StringUtils.hasText(director)) {
            this.director = director;
        }
    }
}
