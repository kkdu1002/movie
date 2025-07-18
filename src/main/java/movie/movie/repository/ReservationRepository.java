package movie.movie.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.QReservation;
import movie.movie.domain.Reservation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    //格納
    @Transactional(readOnly = false)
    public void save(Reservation reservation) {
        em.persist(reservation);
    }

    // 予約１件検索
    public Reservation findOne(Long reservation_id) {
        return em.find(Reservation.class , reservation_id);
    }

    // 予約全件検索
    public List<Reservation> findAll() {
        return em.createQuery("select r From Reservation r",Reservation.class)
                .getResultList();
    }

    // 予約ユーザーID検索
    public List<Reservation> findUser(String username) {
        QReservation qReservation = QReservation.reservation;

        return queryFactory.selectFrom(qReservation)
                .where(searchUser(username))
                .fetch();
    }

    private BooleanExpression searchUser(String username) {
        return StringUtils.hasText(username) ? QReservation.reservation.user.username.eq(username) : null;
    }

    // 予約キャンセル
    @Transactional(readOnly = false)
    public void cancelReservation(Long cancel_id) {
        Reservation cancelReservation = findOne(cancel_id);
        if(cancelReservation != null) {
            em.remove(cancelReservation);
        }
    }
}
