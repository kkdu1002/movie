package movie.movie.service;

import lombok.RequiredArgsConstructor;
import movie.movie.domain.Reservation;
import movie.movie.domain.Schedule;
import movie.movie.domain.User;
import movie.movie.dto.CreateReservationDto;
import movie.movie.dto.UpdateReservationDto;
import movie.movie.repository.ReservationRepository;
import movie.movie.repository.ScheduleRepository;
import movie.movie.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;

    //格納
    @Transactional(readOnly = false)
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    //１件検索
    public Reservation findOne(Long id) {
        return reservationRepository.findOne(id);
    }

    //全件検索
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    //ユーザーID検索
    public List<Reservation> findUser(String username) {
        return reservationRepository.findUser(username);
    }

    // 予約変更
    @Transactional(readOnly = false)
    public Reservation updReservation(Long upd_id , UpdateReservationDto dto) {
        Reservation updReservation = findOne(upd_id);
        if(updReservation != null) {
            Schedule schedule = scheduleRepository.findOne(dto.getScheduleId());
            if(schedule != null) {
                updReservation.updateSchedule(schedule);
            }
            if(dto.getSeatNumber() != null && !dto.getSeatNumber().isEmpty()) {
                updReservation.updateSeatNumber(dto.getSeatNumber());
            }
            if(schedule != null || (dto.getSeatNumber() != null && !dto.getSeatNumber().isEmpty())) {
                updReservation.updateTime();
            }
        }
        return updReservation;
    }

    //予約キャンセル
    @Transactional(readOnly = false)
    public void deleteReservation(Long id) {
        reservationRepository.cancelReservation(id);
    }

    public Reservation createReservation(CreateReservationDto dto) {
        User user = userRepository.findOne(dto.getUserId());
        Schedule schedule = scheduleRepository.findOne(dto.getScheduleId());

        return Reservation.builder()
                .user(user)
                .schedule(schedule)
                .seatNumber(dto.getSeatNumber())
                .build();
    }
}
