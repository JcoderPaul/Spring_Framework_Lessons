package spring.oldboy.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /*
    Lesson 103: метод из Lesson 75 удален, оставили
    только нужное, GET метод с указанием того -
    "где ее искать" на http://localhost:8080/login
    */
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

}
