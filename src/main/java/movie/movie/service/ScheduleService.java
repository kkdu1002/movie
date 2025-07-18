package movie.movie.service;

import lombok.RequiredArgsConstructor;
import movie.movie.domain.Schedule;
import movie.movie.dto.UpdateScheduleDto;
import movie.movie.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = false)
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public Schedule findOne(Long id) {
        return scheduleRepository.findOne(id);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findTheater(String theater) {
        return scheduleRepository.findTheater(theater);
    }

    @Transactional(readOnly = false)
    public void updateSchedule(Long id , UpdateScheduleDto dto) {
        scheduleRepository.updateSchedule(id , dto);
    }

    @Transactional(readOnly = false)
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteSchedule(id);
    }
}
