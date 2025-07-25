    package movie.movie.service;

    import com.querydsl.jpa.impl.JPAQueryFactory;
    import jakarta.persistence.EntityManager;
    import lombok.RequiredArgsConstructor;
    import movie.movie.domain.Movie;
    import movie.movie.dto.CreateMovieDto;
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

        //格納
        @Transactional(readOnly = false)
        public void save(Movie movie) {
            movieRepository.save(movie);
        }

        //１件検索
        public Movie findOne(Long id) {
            return movieRepository.findOne(id);
        }

        // 全件検索
        public List<Movie> findAll() {
            return movieRepository.findAll();
        }

        //監督、ジャンル、タイトル検索
        public List<Movie> findMovieData(String director  , String genre , String title) {
            return movieRepository.findMovieData(director , genre, title);
        }

        //更新
        @Transactional(readOnly = false)
        public Movie updateMovie(Long id , UpdateMovieDto dto) {
            return movieRepository.updateMovie(id, dto);
        }

        //削除
        @Transactional(readOnly = false)
        public void deleteMovie(Long id) {
            movieRepository.deleteMovie(id);
        }

        public Movie createMovie(CreateMovieDto dto) {
            return Movie.builder()
                    .director(dto.getDirector())
                    .genre(dto.getGenre())
                    .title(dto.getTitle())
                    .runtime(dto.getRuntime())
                    .build();
        }
    }
