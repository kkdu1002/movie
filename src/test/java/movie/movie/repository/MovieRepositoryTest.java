package movie.movie.repository;

import jakarta.persistence.EntityManager;
import movie.movie.domain.Movie;
import movie.movie.dto.UpdateMovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@SpringJUnitConfig
class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    EntityManager em;

    Movie savedMovie;

    @BeforeEach
    void setup() {
        Movie movie = Movie.builder()
                .title("영화제목A")
                .genre("액션")
                .director("감독A")
                .runtime(120)
                .build();

        movieRepository.save(movie);

        savedMovie = movie;
    }

    @Test
    void save() {
        Movie movie = Movie.builder()
                .title("영화제목B")
                .genre("액션1")
                .director("감독B")
                .runtime(124)
                .build();

        movieRepository.save(movie);
        em.flush();
        em.clear();

        assertThat(movie.getId()).isNotNull();
    }

    @Test
    void findOne() {

        Movie movie = Movie.builder()
                .title("영화제목C")
                .genre("액션C")
                .director("감독C")
                .runtime(127)
                .build();

        movieRepository.save(movie);

        em.flush();
        em.clear();

        Movie found = movieRepository.findOne(movie.getId());
        assertThat(found).isNotNull();
        assertThat(found.getDirector()).isEqualTo("감독C");
    }

    @Test
    void findAll(){
        Movie movie = Movie.builder()
                .title("영화제목C")
                .genre("액션C")
                .director("감독C")
                .runtime(127)
                .build();

        movieRepository.save(movie);

        em.flush();
        em.clear();

        List<Movie> findAll = movieRepository.findAll();

        assertThat(findAll).isNotNull();
        assertThat(findAll).hasSize(2);
    }

    @Test
    void findMovieData() {
        List<Movie> findMovieDate = movieRepository.findMovieData("감독A","액션","영화제목A");

        assertThat(findMovieDate).isNotEmpty();
        assertThat(findMovieDate.size()).isGreaterThan(0);
    }

    @Test
    void updateMovie() {

        UpdateMovieDto dto = new UpdateMovieDto();

        dto.setDirector("감독X");
        dto.setTitle("타이틀 변경");
        dto.setGenre("스릴러");

        movieRepository.updateMovie(savedMovie.getId(), dto);

        em.flush();
        em.clear();

        List<Movie> movieData = movieRepository.findMovieData("감독X", "스릴러", "타이틀 변경");

        assertThat(movieData).isNotEmpty();
        assertThat(movieData.size()).isGreaterThan(0);

        List<Movie> movieData2 = movieRepository.findMovieData("감독B", "영화", "타이틀");

        assertThat(movieData2).isEmpty();
    }

    @Test
    void deleteMovie() {

        Movie movie5 = Movie.builder()
                .title("영화제목P")
                .genre("장르")
                .director("감독P")
                .runtime(120)
                .build();

        movieRepository.save(movie5);
        em.flush();
        em.clear();

        movieRepository.deleteMovie(movie5.getId());

        em.flush();
        em.clear();

        Movie checkMovie = movieRepository.findOne(movie5.getId());

        assertThat(checkMovie).isNull();

    }
}