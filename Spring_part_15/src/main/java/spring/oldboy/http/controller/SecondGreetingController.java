package spring.oldboy.http.controller;

/*
Part 15: Lesson 72 - Для мапинга (картирования) наших методов на соответствующие *.jsp страницы
            применим не @RequestMapping аннотацию, которая несколько громоздка, а @GetMapping
            с указанием параметра. При этом несложно заметить, что в самой аннотации, в
            ее названии присутствует уточняющее предназначение оной (GET - метод).
*/

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import spring.oldboy.database.repository.company_repository.CompanyRepository;

/* Помечаем специальной аннотацией см. док. DOC/SpringWebServlet/ControllerInterface.txt */
@Controller
public class SecondGreetingController {

    /*
    Part 15: Lesson 72:
    Используем аннотацию @GetMapping() с параметрами, где указываем обработчик
    ответа (*.jsp страницу). Еще раз, в названии аннотации уже разъясняется
    тип метода запроса GET. Несложно догадаться, что существуют и другие типы
    аннотаций, для других вариантов запросов, например, @PostMapping, @PutMapping
    и т.д.
    */
    @GetMapping("/another_hello")
    public ModelAndView hello(ModelAndView modelAndView,
                              HttpServletRequest request,
                              CompanyRepository companyRepository) {
        modelAndView.setViewName("greeting/another_hello");
        /* Возвращаем отображение */
        return modelAndView;
    }

    @GetMapping("/another_bye")
    public ModelAndView bye() {
        ModelAndView modelAndView = new ModelAndView();
        /* Задаем отображение на запрос BYE, так же без префикса и суффикса */
        modelAndView.setViewName("greeting/another_bye");
        /* Возвращаем отображение */
        return modelAndView;
    }
}
