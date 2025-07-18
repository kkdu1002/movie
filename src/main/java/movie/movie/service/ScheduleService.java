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

    //格納
    @Transactional(readOnly = false)
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    //１件検索
    public Schedule findOne(Long id) {
        return scheduleRepository.findOne(id);
    }

    //全件検索
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    //シアター検索
    public List<Schedule> findTheater(String theater) {
        return scheduleRepository.findTheater(theater);
    }

    //更新
    @Transactional(readOnly = false)
    public void updateSchedule(Long id , UpdateScheduleDto dto) {
        scheduleRepository.updateSchedule(id , dto);
    }

    //削除
    @Transactional(readOnly = false)
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteSchedule(id);
    }
}
