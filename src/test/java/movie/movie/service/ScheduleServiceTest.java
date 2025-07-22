package movie.movie.service;

import movie.movie.domain.Movie;
import movie.movie.domain.Schedule;
import movie.movie.dto.CreateMovieDto;
import movie.movie.dto.CreateScheduleDto;
import movie.movie.dto.UpdateScheduleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@Transactional
class ScheduleServiceTest {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    MovieService movieService;

    Schedule initSchedule;

    @BeforeEach
    public void init() {
        CreateMovieDto movieDto = new CreateMovieDto();

        movieDto.setTitle("initTitle");
        movieDto.setGenre("initGenre");
        movieDto.setDirector("initDirector");
        movieDto.setRuntime(111);

        Movie initMovie = Movie.builder()
                .title(movieDto.getTitle())
                .genre(movieDto.getGenre())
                .director(movieDto.getDirector())
                .runtime(movieDto.getRuntime())
                .build();

        movieService.save(initMovie);

        CreateScheduleDto scheduleDto = new CreateScheduleDto();

        scheduleDto.setTheater("initTheater");
        scheduleDto.setScreenTime(LocalDateTime.parse("2025-07-22 14:37:12", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduleDto.setAvailableSeats(130);

        Schedule initMovieSchedule = Schedule.builder()
                .movie(initMovie)
                .theater(scheduleDto.getTheater())
                .screenTime(scheduleDto.getScreenTime())
                .availableSeats(scheduleDto.getAvailableSeats())
                .build();

        scheduleService.save(initMovieSchedule);

        initSchedule = initMovieSchedule;
    }

    @Test
    public void save() {

        CreateMovieDto movieDto = new CreateMovieDto();

        movieDto.setTitle("scheduleTitle");
        movieDto.setGenre("scheduleGenre");
        movieDto.setDirector("scheduleDirector");
        movieDto.setRuntime(123);

        Movie buildMovie = Movie.builder()
                .title(movieDto.getTitle())
                .genre(movieDto.getGenre())
                .director(movieDto.getDirector())
                .runtime(movieDto.getRuntime())
                .build();

        movieService.save(buildMovie);

        CreateScheduleDto scheduleDto = new CreateScheduleDto();

        scheduleDto.setTheater("saveTheater");
        scheduleDto.setScreenTime(LocalDateTime.parse("2025-07-22 14:37:12", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduleDto.setAvailableSeats(170);

        Schedule buildSchedule = Schedule.builder()
                .movie(buildMovie)
                .theater(scheduleDto.getTheater())
                .screenTime(scheduleDto.getScreenTime())
                .availableSeats(scheduleDto.getAvailableSeats())
                .build();

        scheduleService.save(buildSchedule);

    }

    @Test
    public void findOne() {

        Schedule schedule = scheduleService.findOne(initSchedule.getId());

        assertThat(schedule).isNotNull();
        assertThat(schedule.getMovie().getTitle()).isEqualTo("initTitle");
        assertThat(schedule.getMovie().getGenre()).isEqualTo("initGenre");
        assertThat(schedule.getMovie().getDirector()).isEqualTo("initDirector");
        assertThat(schedule.getMovie().getRuntime()).isEqualTo(111);
        assertThat(schedule.getTheater()).isEqualTo("initTheater");
        assertThat(schedule.getAvailableSeats()).isEqualTo(130);
    }

    @Test
    public void findAll() {
        List<Schedule> allSchedule = scheduleService.findAll();

        assertThat(allSchedule).isNotEmpty();
    }

    @Test
    public void findTheater() {
        List<Schedule> findTheater = scheduleService.findTheater("initTheater");

        assertThat(findTheater).isNotEmpty();

    }

    @Test
    public void update() {
        UpdateScheduleDto dto = new UpdateScheduleDto();

        dto.setTheater("updateTheater");
        dto.setScreenTime(LocalDateTime.now());

        assertThat(initSchedule.getTheater()).isEqualTo("initTheater");

        System.out.println("initSchedule.getScreenTime() : " + initSchedule.getScreenTime());

        scheduleService.updateSchedule(initSchedule.getId(), dto);

        Schedule schedule = scheduleService.findOne(initSchedule.getId());

        assertThat(schedule).isNotNull();
        assertThat(schedule.getTheater()).isEqualTo(dto.getTheater());

        System.out.println("updateSchedule.getScreenTime() : " + schedule.getScreenTime());

        assertThat(schedule.getScreenTime()).isSameAs(dto.getScreenTime());
    }

    @Test
    public void delete() {
        Schedule schedule = scheduleService.findOne(initSchedule.getId());

        scheduleService.deleteSchedule(schedule.getId());

        Schedule delSchedule = scheduleService.findOne(schedule.getId());

        assertThat(delSchedule).isNull();

    }
}