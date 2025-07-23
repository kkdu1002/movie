package movie.movie.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.movie.domain.User;
import movie.movie.dto.CreateUserDto;
import movie.movie.dto.UpdateUserDto;
import movie.movie.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //ログイン処理
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
            redirectAttributes.addFlashAttribute("loginErr" , "入力されたID、PWが違います。");
            return "redirect:/home";
        }
    }

    //会員登録画面表示
    @GetMapping("/home/insertUser")
    public String createUser(Model model) {

        model.addAttribute("createUserDto",new CreateUserDto());

        return "insertUser";
    }

    //会員登録処理
    @PostMapping("/home/insertUser")
    public String createUserForm(@ModelAttribute @Valid CreateUserDto dto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        //validation
        if (bindingResult.hasErrors()) {
            return "insertUser";
        }
        User user = User.builder()
                .username(dto.getUsername())
                .passwd(dto.getPasswd())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();

        redirectAttributes.addFlashAttribute("createUser","会員登録完了");

        userService.save(user);

        return "redirect:/home";
    }

    //ユーザーID、役割検索表示
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

    //更新ユーザー情報表示
    @GetMapping("/home/user/{id}")
    public String userUpdate(@PathVariable("id") Long id , Model model) {

        User user = userService.findOne(id);

        model.addAttribute("user" , user);

        return "userUpdate";
    }

    //ユーザー情報更新処理
    @PostMapping("/home/user/{id}")
    public String updateUserForm(@PathVariable("id") Long id , @ModelAttribute UpdateUserDto dto) {

        userService.updateUser(id,dto);

        return "redirect:/home/user";
    }
}
