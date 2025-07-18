package movie.movie.repository;


import jakarta.persistence.EntityManager;
import movie.movie.domain.Movie;
import movie.movie.domain.Reservation;
import movie.movie.domain.Schedule;
import movie.movie.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@SpringJUnitConfig
@Transactional
class ReservationRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    User dummyUser;

    Schedule dummySchedule;

    Movie dummyMovie;

    Reservation dummyReservation;

    @BeforeEach
    void init() {
        User dummyUser1 = User.builder()
                .username("dummyUser")
                .passwd("dummyPw1!")
                .email("dummy@User.com")
                .build();

        userRepository.save(dummyUser1);

        dummyUser = dummyUser1;

        Movie dummyMovie1 = Movie.builder()
                .title("dummyTitle")
                .genre("dummyGenre")
                .director("dummyDirector")
                .runtime(123)
                .build();

        movieRepository.save(dummyMovie1);

        dummyMovie = dummyMovie1;

        Schedule dummySchedule1 = Schedule.builder()
                .theater("dummyTheater")
                .movie(dummyMovie)
                .screenTime(LocalDateTime.now())
                .availableSeats(180)
                .build();

        scheduleRepository.save(dummySchedule1);

        dummySchedule = dummySchedule1;

        Reservation initReservation = Reservation.builder()
                .user(dummyUser)
                .seatNumber("F15")
                .schedule(dummySchedule)
                .build();

        reservationRepository.save(initReservation);

        dummyReservation = initReservation;
    }

    @Test
    void save() {
        Reservation saveReservation = Reservation.builder()
                .user(dummyUser)
                .seatNumber("B13")
                .schedule(dummySchedule)
                .build();

        reservationRepository.save(saveReservation);

        Reservation checkReservation = reservationRepository.findOne(saveReservation.getId());

        assertThat(checkReservation).isNotNull();
    }

    @Test
    void findOne() {
        Reservation reservation = reservationRepository.findOne(dummyReservation.getId());

        System.out.println("Seat number : " + reservation.getSeatNumber());

        assertThat(reservation).isNotNull();

    }

    @Test
    void findAll() {
        Reservation reservation = Reservation.builder()
                .user(dummyUser)
                .schedule(dummySchedule)
                .seatNumber("K9")
                .build();

        reservationRepository.save(reservation);

        List<Reservation> findAll = reservationRepository.findAll();

        assertThat(findAll).isNotEmpty();
        assertThat(findAll).hasSize(2);
    }

    @Test
    void findUser() {
        List<Reservation> findUser = reservationRepository.findUser("dummyUser");

        assertThat(findUser).isNotEmpty();
        assertThat(findUser).hasSize(1);
    }

    @Test
    void cancel() {
        reservationRepository.cancelReservation(dummyReservation.getId());

        List<Reservation> findAll = reservationRepository.findAll();

        assertThat(findAll).isEmpty();
        assertThat(findAll).hasSize(0);
    }
}