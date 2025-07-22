package movie.movie.service;

import movie.movie.domain.Movie;
import movie.movie.domain.Reservation;
import movie.movie.domain.Schedule;
import movie.movie.domain.User;
import movie.movie.dto.*;
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
class ReservationServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    MovieService movieService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ReservationService reservationService;

    Reservation initReservation;

    @BeforeEach
    public void init() {
        CreateUserDto createUserDto = new CreateUserDto();

        createUserDto.setUsername("initUserName");
        createUserDto.setPasswd("initPasswd1!");
        createUserDto.setEmail("initUser@Email.com");

        User initUser = User.builder()
                .username(createUserDto.getUsername())
                .passwd(createUserDto.getPasswd())
                .email(createUserDto.getEmail())
                .build();

        userService.save(initUser);

        CreateMovieDto createMovieDto = new CreateMovieDto();

        createMovieDto.setTitle("initTitle");
        createMovieDto.setGenre("initGenre");
        createMovieDto.setDirector("initDirector");
        createMovieDto.setRuntime(222);

        Movie initMovie = Movie.builder()
                .title(createMovieDto.getTitle())
                .genre(createMovieDto.getGenre())
                .director(createMovieDto.getDirector())
                .runtime(createMovieDto.getRuntime())
                .build();

        movieService.save(initMovie);

        CreateScheduleDto createScheduleDto = new CreateScheduleDto();

        createScheduleDto.setMovieId(initMovie.getId());
        createScheduleDto.setTheater("initTheater");
        createScheduleDto.setScreenTime(LocalDateTime.parse("2025-07-22 16:01:08", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        createScheduleDto.setAvailableSeats(333);

        Schedule initSchedule = Schedule.builder()
                .movie(initMovie)
                .theater(createScheduleDto.getTheater())
                .screenTime(createScheduleDto.getScreenTime())
                .availableSeats(createScheduleDto.getAvailableSeats())
                .build();

        scheduleService.save(initSchedule);

        CreateReservationDto createReservationDto = new CreateReservationDto();

        createReservationDto.setUserId(initUser.getId());
        createReservationDto.setScheduleId(initSchedule.getId());
        createReservationDto.setSeatNumber("I21");

        Reservation reservation = Reservation.builder()
                .user(initUser)
                .schedule(initSchedule)
                .seatNumber(createReservationDto.getSeatNumber())
                .build();

        reservationService.save(reservation);

        initReservation = reservation;
    }

    @Test
    public void save() {

        CreateUserDto createUserDto = new CreateUserDto();

        createUserDto.setUsername("saveUserName");
        createUserDto.setPasswd("savePasswd1!");
        createUserDto.setEmail("saveUser@Email.com");

        User createUser = User.builder()
                .username(createUserDto.getUsername())
                .passwd(createUserDto.getPasswd())
                .email(createUserDto.getEmail())
                .build();

        userService.save(createUser);

        CreateMovieDto createMovieDto = new CreateMovieDto();

        createMovieDto.setTitle("saveTitle");
        createMovieDto.setGenre("saveGenre");
        createMovieDto.setDirector("saveDirector");
        createMovieDto.setRuntime(111);

        Movie createMovie = Movie.builder()
                .title(createMovieDto.getTitle())
                .genre(createMovieDto.getGenre())
                .director(createMovieDto.getDirector())
                .runtime(createMovieDto.getRuntime())
                .build();

        movieService.save(createMovie);

        CreateScheduleDto createScheduleDto = new CreateScheduleDto();

        createScheduleDto.setMovieId(createMovie.getId());
        createScheduleDto.setTheater("saveTheater");
        createScheduleDto.setScreenTime(LocalDateTime.parse("2025-07-22 15:42:57", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        createScheduleDto.setAvailableSeats(111);

        Schedule createSchedule = Schedule.builder()
                .movie(createMovie)
                .theater(createScheduleDto.getTheater())
                .screenTime(createScheduleDto.getScreenTime())
                .availableSeats(createScheduleDto.getAvailableSeats())
                .build();

        scheduleService.save(createSchedule);

        CreateReservationDto createReservationDto = new CreateReservationDto();

        createReservationDto.setUserId(createUser.getId());
        createReservationDto.setScheduleId(createSchedule.getId());
        createReservationDto.setSeatNumber("F13");

        Reservation createReservation = Reservation.builder()
                .user(createUser)
                .schedule(createSchedule)
                .seatNumber(createReservationDto.getSeatNumber())
                .build();

        reservationService.save(createReservation);

        Reservation reservation = reservationService.findOne(createReservation.getId());

        assertThat(reservation).isNotNull();

        System.out.println("userName : " + reservation.getUser().getUsername());
        System.out.println("passwd : " + reservation.getUser().getPasswd());
        System.out.println("Email : " + reservation.getUser().getEmail());
        System.out.println("Title : " + reservation.getSchedule().getMovie().getTitle());
        System.out.println("Genre : " + reservation.getSchedule().getMovie().getGenre());
        System.out.println("Director : " + reservation.getSchedule().getMovie().getDirector());
        System.out.println("RunTime : " + reservation.getSchedule().getMovie().getRuntime());
        System.out.println("Theater : " + reservation.getSchedule().getTheater());
        System.out.println("ScreenTime : " + reservation.getSchedule().getScreenTime());
        System.out.println("AvailableSeat : " + reservation.getSchedule().getAvailableSeats());
        System.out.println("SeatNumber : " + reservation.getSeatNumber());
    }

    @Test
    public void findOne() {
        Reservation reservation = reservationService.findOne(initReservation.getId());

        assertThat(reservation).isNotNull();
        assertThat(reservation.getUser().getUsername()).isEqualTo("initUserName");
        assertThat(reservation.getUser().getEmail()).isEqualTo("initUser@Email.com");
        assertThat(reservation.getSchedule().getMovie().getTitle()).isEqualTo("initTitle");
        assertThat(reservation.getSchedule().getMovie().getGenre()).isEqualTo("initGenre");
        assertThat(reservation.getSchedule().getMovie().getDirector()).isEqualTo("initDirector");
        assertThat(reservation.getSchedule().getMovie().getRuntime()).isEqualTo(222);
        assertThat(reservation.getSchedule().getTheater()).isEqualTo("initTheater");
        assertThat(reservation.getSchedule().getAvailableSeats()).isEqualTo(333);
        assertThat(reservation.getSeatNumber()).isEqualTo("I21");
    }

    @Test
    public void findAll() {
        save();

        List<Reservation> reservationList = reservationService.findAll();

        assertThat(reservationList).isNotEmpty();

        System.out.println("reservation size : " + reservationList.size());
    }

    @Test
    public void findUser() {
        List<Reservation> initUserName = reservationService.findUser("initUserName");

        assertThat(initUserName).isNotEmpty();
        assertThat(initUserName).hasSize(1);
    }

    @Test
    public void update() {

        UpdateReservationDto updateReservationDto = new UpdateReservationDto();

        Reservation reservation = reservationService.findOne(initReservation.getId());

        updateReservationDto.setScheduleId(reservation.getSchedule().getId());
        updateReservationDto.setSeatNumber("G13");
        updateReservationDto.setReservationTime(LocalDateTime.now());

        System.out.println("initReservation : " + initReservation.getSchedule().getId());
        System.out.println("reservation : " + reservation.getSchedule().getId());

        assertThat(reservation.getSeatNumber()).isEqualTo(initReservation.getSeatNumber());

        reservationService.updReservation(reservation.getId() , updateReservationDto);

        assertThat(reservation.getSeatNumber()).isEqualTo(updateReservationDto.getSeatNumber());
    }

    @Test
    public void delete() {
        reservationService.deleteReservation(initReservation.getId());

        Reservation reservation = reservationService.findOne(initReservation.getId());

        assertThat(reservation).isNull();
    }
}