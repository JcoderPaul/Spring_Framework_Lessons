package spring.oldboy.http.controller;

/* Lesson 103 - метод из Lesson 75 удален, оставили только нужное */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

}
