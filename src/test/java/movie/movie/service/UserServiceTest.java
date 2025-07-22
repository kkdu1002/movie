package movie.movie.service;

import movie.movie.domain.User;
import movie.movie.dto.CreateUserDto;
import movie.movie.dto.UpdateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    User initUser;

    @BeforeEach
    public void init() {
        CreateUserDto dto = new CreateUserDto();

        dto.setUsername("initUsername");
        dto.setPasswd("initPasswd1!");
        dto.setEmail("initEmail@mail.com");

        User user = User.builder()
                .username(dto.getUsername())
                .passwd(dto.getPasswd())
                .email(dto.getEmail())
                .build();

        userService.save(user);

        initUser = user;
    }

    @Test
    public void save() {
        CreateUserDto dto = new CreateUserDto();

        dto.setUsername("saveUser");
        dto.setPasswd("save1234!");
        dto.setEmail("save@dto.com");

        User buildUser = User.builder()
                .username(dto.getUsername())
                .passwd(dto.getPasswd())
                .email(dto.getEmail())
                .build();

        userService.save(buildUser);

        User user = userService.findOne(buildUser.getId());

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(dto.getUsername());
        assertThat(user.getPasswd()).isEqualTo(dto.getPasswd());
        assertThat(user.getEmail()).isEqualTo(dto.getEmail());
    }

    @Test
    public void findOne() {
        User user = userService.findOne(initUser.getId());

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(initUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(initUser.getEmail());
        assertThat(user.getPasswd()).isEqualTo(initUser.getPasswd());
    }

    @Test
    public void findAll() {
        List<User> userList = userService.findAll();

        assertThat(userList).isNotEmpty();
    }

    @Test
    public void update() {
        User user = userService.findOne(initUser.getId());

        System.out.println("email : " + user.getEmail());
        System.out.println("passwd : " + user.getPasswd());

        UpdateUserDto dto = new UpdateUserDto();

        dto.setEmail("updUserEmail@mail.com");
        dto.setPasswd("updatePasswd!");

        assertThat(user.getEmail()).isEqualTo("initEmail@mail.com");
        assertThat(user.getPasswd()).isEqualTo("initPasswd1!");

        userService.updateUser(user.getId() , dto);

        System.out.println("update email : " + user.getEmail());
        System.out.println("update passwd : " + user.getPasswd());

        assertThat(user.getEmail()).isEqualTo(dto.getEmail());
        assertThat(user.getPasswd()).isEqualTo(dto.getPasswd());
    }

    @Test
    public void delete() {
        User user = userService.findOne(initUser.getId());

        userService.deleteUser(user.getId());

        User delUser = userService.findOne(user.getId());

        assertThat(delUser).isNull();
    }
}