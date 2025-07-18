package movie.movie.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.User;
import movie.movie.dto.CreateUserDto;
import movie.movie.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("createUserDto",new CreateUserDto());

        return "home";
    }

    @GetMapping("/home/main")
    public String main() {

        return "main";
    }

    @GetMapping("/home/admin")
    public String admin() {

        return "admin";
    }
}
