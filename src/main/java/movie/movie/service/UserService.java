package movie.movie.service;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.User;
import movie.movie.dto.CreateUserDto;
import movie.movie.dto.UpdateUserDto;
import movie.movie.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    //格納
    @Transactional(readOnly = false)
    public void save(User user) {
        userRepository.save(user);
    }

    //１件検索
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    //全件検索
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //ログイン情報チェック
    public User checkPassword(String userid , String passwd) {

        return userRepository.findUserName(userid , passwd);
    }

    //ユーザーID、役割検索
    public List<User> findUserNameRole(String username , String role) {
        return userRepository.findUserNameRole(username , role);
    }

    //ユーザー情報更新
    @Transactional(readOnly = false)
    public User updateUser(Long upd_id , UpdateUserDto dto) {
        return userRepository.updateUser(upd_id , dto);
    }

    //削除
    @Transactional(readOnly = false)
    public void deleteUser(Long del_id) {
        userRepository.deleteUser(del_id);
    }

    public User createUser(CreateUserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .passwd(dto.getPasswd())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
    }
}
