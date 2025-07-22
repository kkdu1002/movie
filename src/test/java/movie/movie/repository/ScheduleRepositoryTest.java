package movie.movie.repository;


import jakarta.persistence.EntityManager;
import movie.movie.domain.Movie;
import movie.movie.domain.Schedule;
import movie.movie.dto.UpdateScheduleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringJUnitConfig
@Transactional
class ScheduleRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    MovieRepository movieRepository;

    private Movie dummyMovie;

    Schedule testDummySchedule;

    @BeforeEach
    void initData() {
        dummyMovie = Movie.builder()
                .title("dummyMovie")
                .genre("dummy")
                .runtime(120)
                .director("dummy director")
                .build();

        movieRepository.save(dummyMovie);

        Schedule dummySchedule = Schedule.builder()
                .movie(dummyMovie)
                .theater("dummy theater")
                .screenTime(LocalDateTime.now())
                .availableSeats(150)
                .build();

        scheduleRepository.save(dummySchedule);

        testDummySchedule = dummySchedule;

    }

    @Test
    void save() {

        Schedule dummySchedule = Schedule.builder()
                .movie(dummyMovie)
                .theater("dummy theater")
                .screenTime(LocalDateTime.now())
                .availableSeats(150)
                .build();

        scheduleRepository.save(dummySchedule);

        Schedule schedule = scheduleRepository.findOne(dummySchedule.getId());

        System.out.println("Movie Director : " + schedule.getMovie().getDirector());
        System.out.println("Movie Genre : " + schedule.getMovie().getGenre());
        System.out.println("Movie title : " + schedule.getMovie().getTitle());
        System.out.println("Movie Runtime : " + schedule.getMovie().getRuntime());
        System.out.println("AvailableSeat : " + schedule.getAvailableSeats());
        System.out.println("Theater : " + schedule.getTheater());
        System.out.println("Screen time : " + schedule.getScreenTime());

        assertThat(schedule).isNotNull();
    }

    @Test
    void findOne() {
        Schedule schedule = scheduleRepository.findOne(testDummySchedule.getId());

        assertThat(schedule).isNotNull();
    }

    @Test
    void findAll() {

        Schedule scheduleBuild = Schedule.builder()
                .theater("findAll")
                .movie(dummyMovie)
                .availableSeats(110)
                .screenTime(LocalDateTime.now().plusDays(2))
                .build();

        scheduleRepository.save(scheduleBuild);

        List<Schedule> all = scheduleRepository.findAll();

        assertThat(all).isNotEmpty();
    }

    @Test
    void findTheater() {

        List<Schedule> dummyTheater = scheduleRepository.findTheater("dummy theater");

        assertThat(dummyTheater).isNotEmpty();
        assertThat(dummyTheater).hasSize(1);

    }

    @Test
    void updateSchedule() {
        UpdateScheduleDto dto = new UpdateScheduleDto();

        dto.setTheater("updateDummyTheater");
        dto.setScreenTime(LocalDateTime.now().plusDays(7));

        System.out.println("no update dummy theater : " + testDummySchedule.getTheater());
        System.out.println("no update dummy screenTime : " + testDummySchedule.getScreenTime());

        scheduleRepository.updateSchedule(testDummySchedule.getId(),dto);

        em.flush();
        em.clear();

        List<Schedule> updateDummyTheater = scheduleRepository.findTheater("updateDummyTheater");
        List<Schedule> dummyTheater = scheduleRepository.findTheater("dummy theater");

        assertThat(updateDummyTheater).isNotEmpty();
        assertThat(updateDummyTheater).hasSize(1);
        assertThat(dummyTheater).isEmpty();
        assertThat(dummyTheater).hasSize(0);
    }

    @Test
    void deleteSchedule() {
        scheduleRepository.deleteSchedule(testDummySchedule.getId());

        Schedule schedule = scheduleRepository.findOne(testDummySchedule.getId());

        assertThat(schedule).isNull();
    }

}