package movie.movie.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.QSchedule;
import movie.movie.domain.Schedule;
import movie.movie.dto.UpdateScheduleDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // 格納
    @Transactional(readOnly = false)
    public void save(Schedule schedule) {
        em.persist(schedule);
    }

    // スケジュール１件検索
    public Schedule findOne(Long id) {
        return em.find(Schedule.class , id);
    }

    // スケジュール全件検索
    public List<Schedule> findAll() {
        return em.createQuery("select s From Schedule s" , Schedule.class)
                .getResultList();
    }

    // シアタースケジュール検索
    public List<Schedule> findTheater(String theater) {
        QSchedule qSchedule = QSchedule.schedule;
        
        return queryFactory.selectFrom(qSchedule)
                .where(searchTheater(theater))
                .fetch();
    }
    
    private BooleanExpression searchTheater(String theater) {
        return StringUtils.hasText(theater) ? QSchedule.schedule.theater.eq(theater) : null;
    }

    // スケジュール削除
    @Transactional(readOnly = false)
    public void deleteSchedule(Long del_id) {
        Schedule delSchedule = findOne(del_id);
        if(delSchedule != null) {
            em.remove(delSchedule);
        }
    }

    // スケジュール変更
    @Transactional(readOnly = false)
    public void updateSchedule(Long upd_id , UpdateScheduleDto dto) {
        Schedule updSchedule = findOne(upd_id);
        if(updSchedule != null) {
            if(StringUtils.hasText(dto.getTheater())) {
                updSchedule.updateTheater(dto.getTheater());
            }
            if(dto.getScreenTime() != null) {
                updSchedule.updateScreenTime(dto.getScreenTime());
            }
        }
    }
}
