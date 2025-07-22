package movie.movie.service;

import movie.movie.domain.Movie;
import movie.movie.dto.CreateMovieDto;
import movie.movie.dto.UpdateMovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MovieServiceTest {

    @Autowired
    MovieService movieService;

    Movie initMovie;

    @BeforeEach
    public void initData() {
        CreateMovieDto dto = new CreateMovieDto();

        dto.setTitle("initTestMovie");
        dto.setGenre("initTest");
        dto.setDirector("initTestDirector");
        dto.setRuntime(33);

        Movie saveMovie = Movie.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .director(dto.getDirector())
                .runtime(dto.getRuntime())
                .build();

        movieService.save(saveMovie);

        initMovie = saveMovie;
    }

    public Movie initdata() {
        CreateMovieDto dto = new CreateMovieDto();

        dto.setTitle("initDataMovie");
        dto.setGenre("initData");
        dto.setDirector("initDataDirector");
        dto.setRuntime(102);

        Movie saveMovie = Movie.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .director(dto.getDirector())
                .runtime(dto.getRuntime())
                .build();

        movieService.save(saveMovie);

        return saveMovie;
    }

    @Test
    public void save() {

        CreateMovieDto dto = new CreateMovieDto();

        dto.setTitle("TestMovie");
        dto.setGenre("Test");
        dto.setDirector("TestDirector");
        dto.setRuntime(113);

        Movie saveMovie = Movie.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .director(dto.getDirector())
                .runtime(dto.getRuntime())
                .build();

        movieService.save(saveMovie);

        Movie movie = movieService.findOne(saveMovie.getId());

        assertThat(movie).isNotNull();
    }

    @Test
    public void findOne() {
        Movie initMovie = initdata();

        Movie movie = movieService.findOne(initMovie.getId());

        assertThat(movie).isNotNull();
    }

    @Test
    public void findAll() {

        List<Movie> movieList = movieService.findAll();

        assertThat(movieList).isNotEmpty();

    }

    @Test
    public void findMovie() {
        List<Movie> movieData = movieService.findMovieData("initTestDirector", "initTest", "initTestMovie");

        assertThat(movieData).isNotEmpty();
    }

    @Test
    public void update() {
        Movie movie = movieService.findOne(initMovie.getId());

        UpdateMovieDto dto = new UpdateMovieDto();

        dto.setGenre("dtoGenre");
        dto.setTitle("dtoTitle");
        dto.setDirector("dtoDirector");

        assertThat(movie.getTitle()).isNotEqualTo(dto.getTitle());
        assertThat(movie.getGenre()).isNotEqualTo(dto.getGenre());
        assertThat(movie.getDirector()).isNotEqualTo(dto.getDirector());

        movieService.updateMovie(movie.getId() , dto);

        assertThat(movie.getTitle()).isEqualTo(dto.getTitle());
        assertThat(movie.getGenre()).isEqualTo(dto.getGenre());
        assertThat(movie.getDirector()).isEqualTo(dto.getDirector());
    }

    @Test
    public void delete() {
        Movie movie = movieService.findOne(initMovie.getId());

        movieService.deleteMovie(movie.getId());

        Movie delMovie = movieService.findOne(movie.getId());

        assertThat(delMovie).isNull();
    }
}