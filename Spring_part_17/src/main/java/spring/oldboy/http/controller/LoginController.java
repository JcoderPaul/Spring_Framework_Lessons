package spring.oldboy.http.controller;

/* Lesson 75 - Пример передачи параметров запроса в его теле */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.oldboy.dto.LoginDto;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    /*
    Lesson 75:
    Аннотацией @ModelAttribute("login") задаем имя ключа параметра, сами
    параметры при отправке формы логина будут инжектированы в loginDto.
    */
    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("login") LoginDto loginDto) {
        return "user/login";
    }

}
