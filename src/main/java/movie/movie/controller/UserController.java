package movie.movie.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.movie.domain.User;
import movie.movie.dto.CreateUserDto;
import movie.movie.dto.UpdateUserDto;
import movie.movie.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/home")
    public String loginForm(@RequestParam String username ,
                            @RequestParam String passwd,
                            RedirectAttributes redirectAttributes ,
                            HttpSession session) {
        User user = userService.checkPassword(username, passwd);
        if(user != null) {
            session.setAttribute("userid",user.getId());
            session.setAttribute("userRole",user.getRole());
            if(user.getRole().equals("2")) {
                return "admin";
            }
            else {
                return "main";
            }
        }
        else {
            redirectAttributes.addFlashAttribute("loginErr" , "입력된 아이디 , 비밀번호가 다릅니다.");
            return "redirect:/home";
        }
    }

    @GetMapping("/home/insertUser")
    public String createUser(Model model) {

        model.addAttribute("createUserDto",new CreateUserDto());

        return "insertUser";
    }

    @PostMapping("/home/insertUser")
    public String createUserForm(@ModelAttribute CreateUserDto dto,
                                 RedirectAttributes redirectAttributes) {
        User user = User.builder()
                .username(dto.getUsername())
                .passwd(dto.getPasswd())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();

        redirectAttributes.addFlashAttribute("createUser","회원등록 완료");

        userService.save(user);

        return "redirect:/home";
    }

    @GetMapping("/home/user")
    public String findUserNameRole(@ModelAttribute User user , Model model) {
        List<User> userNameRole;
        if(StringUtils.hasText(user.getRole()) || StringUtils.hasText(user.getUsername())) {
            userNameRole = userService.findUserNameRole(user.getUsername(), user.getRole());
        }
        else {
            userNameRole = userService.findAll();
        }

        model.addAttribute("userList",userNameRole);

        return "user";
    }

    @GetMapping("/home/user/{id}")
    public String userUpdate(@PathVariable("id") Long id , Model model) {

        User user = userService.findOne(id);

        model.addAttribute("user" , user);

        return "userUpdate";
    }

    @PostMapping("/home/user/{id}")
    public String updateUserForm(@PathVariable("id") Long id , @ModelAttribute UpdateUserDto dto) {

        userService.updateUser(id,dto);

        return "redirect:/home/user";
    }
}
