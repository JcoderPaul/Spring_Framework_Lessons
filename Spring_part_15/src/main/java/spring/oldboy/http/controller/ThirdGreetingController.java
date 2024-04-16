package spring.oldboy.http.controller;

/*
Part 15: Lesson 73 - Применение общего префикса для набора запросов при использовании @RequestMapping
            над классом контроллером в целом.
*/

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import spring.oldboy.database.repository.company_repository.CompanyRepository;

/* Помечаем специальной аннотацией см. док. DOC/SpringWebServlet/ControllerInterface.txt */
@Controller
@RequestMapping("/api/v1")
public class ThirdGreetingController {

    @GetMapping("/prefix_hello")
    public ModelAndView hello(ModelAndView modelAndView,
                              HttpServletRequest request,
                              CompanyRepository companyRepository) {
        modelAndView.setViewName("greeting/hello_for_prefix_demo");
        /* Возвращаем отображение */
        return modelAndView;
    }

    @GetMapping("/prefix_bye")
    public ModelAndView bye() {
        ModelAndView modelAndView = new ModelAndView();
        /* Задаем отображение на запрос BYE, так же без префикса и суффикса */
        modelAndView.setViewName("greeting/bye_for_prefix_demo");
        /* Возвращаем отображение */
        return modelAndView;
    }
}
