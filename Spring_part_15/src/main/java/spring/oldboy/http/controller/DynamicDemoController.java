package spring.oldboy.http.controller;

/*
Part 15: Lesson 75 - Придадим нашему приложению немного динамики.
                     Используем аннотацию @SessionAttributes над
                     классом контроллером.

*/

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import spring.oldboy.dto.UserReadDto;

/* Помечаем специальной аннотацией см. док. DOC/SpringWebServlet/ControllerInterface.txt */
@Controller
@RequestMapping("/api/v1")
/* Добавляем атрибут сессии, их может быть больше одного */
@SessionAttributes({"user"})
public class DynamicDemoController {

    @GetMapping("/dynamic_hello")
    public ModelAndView hello(ModelAndView modelAndView,
                              HttpServletRequest request) {
        modelAndView.setViewName("dynamic_view/dynamic_hello");
        /*
        Руками добавляем атрибут 'user' в сессионный массив
        атрибутов, откуда в дальнейшем мы сможем его извлечь.
        */
        modelAndView.addObject("user", new UserReadDto(1L, "Paul"));
        /* Возвращаем отображение */
        return modelAndView;
    }

    /*
    В параметрах метода мы передаем нашу modelAndView и самое главное сессионный
    атрибут 'user'. Передаем его как объект UserReadDto. Естественно, если такого
    атрибута не будет, т.е. в нашем демонстрационном варианте мы сразу обратимся
    к страничке http://localhost:8080/api/v1/dynamic_bye минуя обращение к
    http://localhost:8080/api/v1/dynamic_hello где мы устанавливаем атрибут,
    возникнет 4** ошибка.
    */
    @GetMapping("/dynamic_bye")
    public ModelAndView bye(ModelAndView modelAndView,
                            @SessionAttribute("user") UserReadDto user) {
        /* Задаем отображение на запрос BYE, так же без префикса и суффикса */
        modelAndView.setViewName("dynamic_view/dynamic_bye");
        /* Возвращаем отображение */
        return modelAndView;
    }
}