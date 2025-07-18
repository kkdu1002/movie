package movie.movie.repository;

import jakarta.persistence.EntityManager;
import movie.movie.domain.User;
import movie.movie.dto.UpdateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@SpringJUnitConfig
class UserRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;

    User initUser;

    @BeforeEach
    void init() {
        User user = User.builder()
                .username("init")
                .passwd("123456!")
                .email("bcv@asd.com")
                .build();

        userRepository.save(user);

        initUser = user;
    }

    @Test
    void save() {
        User user = User.builder()
                .username("admin")
                .passwd("1234!")
                .email("asd@asd.com")
                .build();

        userRepository.save(user);

        User createUser = userRepository.findOne(user.getId());

        assertThat(createUser).isNotNull();
    }

    @Test
    void findOne() {
        User findOneUser = userRepository.findOne(initUser.getId());

        assertThat(findOneUser).isNotNull();
        assertThat(findOneUser.getUsername()).isEqualTo("init");
    }

    @Test
    void findAll() {
        User findAll = User.builder()
                .username("findAll")
                .passwd("find1!")
                .email("find@All.com")
                .build();

        userRepository.save(findAll);

        List<User> findAllUser = userRepository.findAll();

        assertThat(findAllUser).isNotEmpty();
        assertThat(findAllUser).hasSize(2);
    }

    @Test
    void updUser() {
        User initData = userRepository.findOne(initUser.getId());

        if(initData != null) {

            System.out.println("init Email : " + initData.getEmail());
            System.out.println("init Passwd : " + initData.getPasswd());
            System.out.println("init Role : " + initData.getRole());

            UpdateUserDto dto = new UpdateUserDto();

            dto.setEmail("initUpdate@update.com");
            dto.setPasswd("updPasswd1!");
            dto.setRole("2");

            userRepository.updateUser(initUser.getId() ,dto);

            em.flush();
            em.clear();

            User updateEndUser = userRepository.findOne(initUser.getId());

            if(updateEndUser != null) {
                System.out.println("Email : " + updateEndUser.getEmail());
                System.out.println("Passwd : " + updateEndUser.getPasswd());
                System.out.println("Role : " + updateEndUser.getRole());
            }
        }
    }

    @Test
    void deleteUser() {
        User initData = userRepository.findOne(initUser.getId());

        if(initData != null) {
            userRepository.deleteUser(initUser.getId());

            em.flush();
            em.clear();

            User delData = userRepository.findOne(initUser.getId());

            assertThat(delData).isNull();
        }
    }
}