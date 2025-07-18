package movie.movie.controller;

import lombok.RequiredArgsConstructor;
import movie.movie.dto.CreateUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    //初期画面
    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("createUserDto",new CreateUserDto());

        return "home";
    }

    //会員
    @GetMapping("/home/main")
    public String main() {

        return "main";
    }

    //管理者
    @GetMapping("/home/admin")
    public String admin() {

        return "admin";
    }
}
