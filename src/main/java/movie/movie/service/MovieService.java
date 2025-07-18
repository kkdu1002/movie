    package movie.movie.service;

    import com.querydsl.jpa.impl.JPAQueryFactory;
    import jakarta.persistence.EntityManager;
    import lombok.RequiredArgsConstructor;
    import movie.movie.domain.Movie;
    import movie.movie.dto.UpdateMovieDto;
    import movie.movie.repository.MovieRepository;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;

    @Service
    @Transactional(readOnly = true)
    @RequiredArgsConstructor
    public class MovieService {

        private final MovieRepository movieRepository;

        @Transactional(readOnly = false)
        public void save(Movie movie) {
            movieRepository.save(movie);
        }

        public Movie findOne(Long id) {
            return movieRepository.findOne(id);
        }

        public List<Movie> findAll() {
            return movieRepository.findAll();
        }

        public List<Movie> findMovieData(String director  , String genre , String title) {
            return movieRepository.findMovieData(director , genre, title);
        }


        @Transactional(readOnly = false)
        public Movie updateMovie(Long id , UpdateMovieDto dto) {
            return movieRepository.updateMovie(id, dto);
        }

        @Transactional(readOnly = false)
        public void deleteMovie(Long id) {
            movieRepository.deleteMovie(id);
        }
    }
