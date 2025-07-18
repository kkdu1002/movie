package movie.movie.service;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.User;
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

    @Transactional(readOnly = false)
    public void save(User user) {
        userRepository.save(user);
    }

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User checkPassword(String userid , String passwd) {

        return userRepository.findUserName(userid , passwd);
    }

    public List<User> findUserNameRole(String username , String role) {
        return userRepository.findUserNameRole(username , role);
    }

    @Transactional(readOnly = false)
    public User updateUser(Long upd_id , UpdateUserDto dto) {
        return userRepository.updateUser(upd_id , dto);
    }

    @Transactional(readOnly = false)
    public void deleteUser(Long del_id) {
        userRepository.deleteUser(del_id);
    }
}
