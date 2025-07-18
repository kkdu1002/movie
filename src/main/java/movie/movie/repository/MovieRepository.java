package movie.movie.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.Movie;
import movie.movie.domain.QMovie;
import movie.movie.dto.UpdateMovieDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    //格納
    @Transactional(readOnly = false)
    public void save(Movie movie) {
        em.persist(movie);
    }

    // 映画１件検索
    public Movie findOne(Long movie_id) {
        return em.find(Movie.class , movie_id);
    }

    // 映画全件検索
    public List<Movie> findAll() {
        return em.createQuery("select m From Movie m" ,Movie.class)
                .getResultList();
    }

    // 映画検索(監督、ジャンル、タイトル)
    public List<Movie> findMovieData(String director , String genre , String title) {
        QMovie qMovie = QMovie.movie;

        return queryFactory.selectFrom(qMovie)
                .where(searchDirector(director),
                        searchGenre(genre),
                        searchTitle(title))
                .fetch();
    }

    // 監督名検索
    private BooleanExpression searchDirector(String director) {
        return StringUtils.hasText(director) ? QMovie.movie.director.like("%" + director + "%") : null;
    }
    // ジャンル検索
    private BooleanExpression searchGenre(String genre) {
        return StringUtils.hasText(genre) ? QMovie.movie.genre.eq(genre) : null;
    }
    // タイトル検索
    private BooleanExpression searchTitle(String title) {
        return StringUtils.hasText(title) ? QMovie.movie.title.like("%" + title + "%") : null;
    }

    // データ更新
    @Transactional(readOnly = false)
    public Movie updateMovie(Long upd_id , UpdateMovieDto dto) {
        Movie updMovie = findOne(upd_id);
        if(updMovie != null) {
            if(StringUtils.hasText(dto.getDirector())) {
                updMovie.updateDirector(dto.getDirector());
            }
            if(StringUtils.hasText(dto.getGenre())) {
                updMovie.updateGenre(dto.getGenre());
            }
            if(StringUtils.hasText(dto.getTitle())) {
                updMovie.updateTitle(dto.getTitle());
            }
        }
        return  updMovie;
    }

    // 削除
    @Transactional(readOnly = false)
    public void deleteMovie(Long del_id) {
        Movie delMovie = findOne(del_id);
        if(delMovie != null) {
            em.remove(delMovie);
        }
    }
}
