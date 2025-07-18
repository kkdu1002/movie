package movie.movie.service;

import lombok.RequiredArgsConstructor;
import movie.movie.domain.Reservation;
import movie.movie.domain.Schedule;
import movie.movie.dto.UpdateReservationDto;
import movie.movie.repository.ReservationRepository;
import movie.movie.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = false)
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public Reservation findOne(Long id) {
        return reservationRepository.findOne(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findUser(String username) {
        return reservationRepository.findUser(username);
    }

    // 예약 변경
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

    @Transactional(readOnly = false)
    public void deleteReservation(Long id) {
        reservationRepository.cancelReservation(id);
    }
}
