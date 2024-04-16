package spring.oldboy.http.controller;

/* Part 15: Lesson 75 - Пример передачи параметров запроса в его теле */

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.oldboy.dto.LoginDto;
import spring.oldboy.dto.UserReadDto;

@Controller
public class LoginController {

    @GetMapping("/hi")
    public String hi(Model model,
                     HttpServletRequest request,
                     @ModelAttribute("userReadDto") UserReadDto userReadDto) {

        model.addAttribute("user", new UserReadDto(1L, "Ivan"));

        return "user/hi_there";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    /*
    Part 15: Lesson 75:
    Аннотацией @ModelAttribute("login") задаем имя ключа параметра, сами
    параметры при отправке формы логина будут инжектированы в loginDto.
    */
    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("login") LoginDto loginDto) {
        return "user/login";
    }

    /* Part 15: Lesson 76: Вариант использования ключевого слова "redirect" в возвращаемом значении */
    @GetMapping("/redirect_to_ya")
    public String testRedirect(Model model) {
        return "redirect:https://yandex.com";
    }
}
