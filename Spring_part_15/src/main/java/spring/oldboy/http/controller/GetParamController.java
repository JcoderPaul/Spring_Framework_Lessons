package spring.oldboy.http.controller;

/*
Part 15: Lesson 74 - Извлечение parameters, headers и cookies из запроса. В данном
                     примере картируем все запросы через общий префикс "/api/v1" на
                     самые первые *.jsp страницы (hello и bye). Описание аннотаций см.
                     в DOC/RequestAnnotation.
*/

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/* Помечаем специальной аннотацией см. док. DOC/SpringWebServlet/ControllerInterface.txt */
@Controller
@RequestMapping("/api/v1")
public class GetParamController {

    /*
    Part 15: Lesson 74:
    В @RequestParam ("age") и @RequestHeader ("accept") название запрашиваемых параметров
    можно опустить до @RequestParam и @RequestHeader соответственно, сопоставление все равно
    произойдет и извлечь данные мы сможем.

    Помним, что параметр required во всех аннотациях переданных в метод установлен по-умолчанию
    в true и любой запрос к http://localhost:8080/api/v1//hello_and_get_param без параметров вернет
    ошибку 4хх. Т.е. мы при запросе к нашему приложению должны передать все требуемые параметры и
    запрос может выглядеть так: http://localhost:8080/api/v1/hello_and_get_param/100?age=24, где
    @RequestParam подхватит данные из 'age=24', а @PathVariable из '/100', ну, а две остальные
    аннотации заберут данные из шапки.
    */
    @GetMapping("/hello_and_get_param/{id}")
    public ModelAndView hello(ModelAndView modelAndView,
                              HttpServletRequest request,
                              @RequestParam ("age") Integer age,
                              @RequestHeader ("accept") String accept,
                              @CookieValue("JSESSIONID") String JSESSIONID,
                              @PathVariable("id") Integer id) {
        /* Извлекаем параметры при этом Spring сам конвертирует одни типы данных в другие необходимые нам */
        String ageParamValue = request.getParameter("age");
        /* Извлекаем headers */
        String acceptHeader = request.getHeader("accept");
        /* Извлекаем cookies */
        Cookie[] cookies = request.getCookies();

        modelAndView.setViewName("greeting/hello");
        /* Возвращаем отображение */
        return modelAndView;
    }

    @GetMapping("/bye_and_get_param")
    public ModelAndView bye() {
        ModelAndView modelAndView = new ModelAndView();
        /* Задаем отображение на запрос BYE, так же без префикса и суффикса */
        modelAndView.setViewName("greeting/bye");
        /* Возвращаем отображение */
        return modelAndView;
    }
}
