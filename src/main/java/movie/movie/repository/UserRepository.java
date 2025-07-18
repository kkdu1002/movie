package movie.movie.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.QUser;
import movie.movie.domain.User;
import movie.movie.dto.UpdateUserDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    // 格納
    @Transactional(readOnly = false)
    public void save(User user) {
        em.persist(user);
    }

    // ユーザー１件検索
    public User findOne(Long user_id) {
        return em.find(User.class , user_id);
    }

    // ユーザー全件検索
    public List<User> findAll() {
        return em.createQuery("select u From User u",User.class)
                .getResultList();
    }

    //ユーザーID、パスワード検索（ログイン情報確認）
    public User findUserName(String userName , String passwd) {
        QUser qUser = QUser.user;

        return queryFactory.selectFrom(qUser)
                .where(qUser.username.eq(userName) ,
                        qUser.passwd.eq(passwd))
                .fetchOne();
    }

    //ユーザーID、役割検索
    public List<User> findUserNameRole(String username , String Role) {
        QUser qUser = QUser.user;

        return queryFactory.selectFrom(qUser)
                .where(qUser.username.like("%" + username + "%") ,
                        qUser.role.eq(Role))
                .fetch();
    }

    // ユーザー情報更新
    @Transactional(readOnly = false)
    public User updateUser(Long upd_id , UpdateUserDto dto) {
        User updUser = findOne(upd_id);
        if(updUser != null) {
            if(StringUtils.hasText(dto.getEmail())) {
                updUser.updEmail(dto.getEmail());
            }
            if(StringUtils.hasText(dto.getPasswd())) {
                updUser.updPasswd(dto.getPasswd());
            }
            if(StringUtils.hasText(dto.getRole())) {
                updUser.updRole(dto.getRole());
            }
        }
        return updUser;
    }

    // ユーザー削除
    @Transactional(readOnly = false)
    public void deleteUser(Long del_id) {
        User delUser = findOne(del_id);
        if(delUser != null) {
            em.remove(delUser);
        }
    }
}
