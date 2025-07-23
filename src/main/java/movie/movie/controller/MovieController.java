package movie.movie.controller;

import lombok.RequiredArgsConstructor;
import movie.movie.domain.Movie;
import movie.movie.dto.CreateMovieDto;
import movie.movie.repository.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;

    //映画データ表示
    @GetMapping("/home/movie")
    public String MovieForm(@RequestParam(required = false) String director,
                            @RequestParam(required = false) String genre,
                            @RequestParam(required = false) String title,
                            Model model ,
                            RedirectAttributes redirectAttributes) {
        List<Movie> movieData;

        if((director == null || director.isBlank()) &&
           (genre == null || genre.isBlank()) &&
           (title == null || title.isBlank())) {
            movieData = movieRepository.findAll();
        }
        else {
            movieData = movieRepository.findMovieData(director, genre, title);
        }
        model.addAttribute("movieData",movieData);
        return "movie";
    }

    //映画登録画面（管理者用）
    @GetMapping("/home/movieTrk")
    public String adminMovie(Model model) {

        model.addAttribute("createMovieDto", new CreateMovieDto());

        List<Movie> findAllMovie = movieRepository.findAll();

        model.addAttribute("movieData",findAllMovie);
        return "movieTrk";
    }

    //映画登録処理（管理者用）
    @PostMapping("/home/movieTrk")
    public String MovieForm(@ModelAttribute CreateMovieDto dto) {

        Movie movie = Movie.builder()
                .director(dto.getDirector())
                .genre(dto.getGenre())
                .title(dto.getTitle())
                .runtime(dto.getRuntime())
                .build();

        movieRepository.save(movie);

        return "redirect:/home/movieTrk";
    }

    //映画削除処理（管理者用）
    @PostMapping("/home/movieTrk/delete")
    public String delMovie(@RequestParam("id") Long id
            , RedirectAttributes redirectAttributes) {

        movieRepository.deleteMovie(id);

        redirectAttributes.addFlashAttribute("deleteUser" , "削除されました。");

        return "redirect:/home/movieTrk";
    }
}
