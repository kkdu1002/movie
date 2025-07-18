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

    // movie title
    private String title;

    // genre
    private String genre;

    // director
    private String director;

    // runtime
    private int runtime;

    // Title update
    public void updateTitle(String title) {
        if(StringUtils.hasText(title)) {
            this.title = title;
        }
    }

    // Genre update
    public void updateGenre(String genre) {
        if(StringUtils.hasText(genre)) {
            this.genre = genre;
        }
    }

    // director update
    public void updateDirector(String director) {
        if(StringUtils.hasText(director)) {
            this.director = director;
        }
    }
}
