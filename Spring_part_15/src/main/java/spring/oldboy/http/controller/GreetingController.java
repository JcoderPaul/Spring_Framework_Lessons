package spring.oldboy.http.controller;

/*
Part 15: Lesson 71 - Создаем наш первый контроллер для работы со Spring MVC;
Part 15: Lesson 72 - Мапим (картируем, указываем соответствие запрос-метод ответа),
            наши методы на соответствующие *.jsp страницы.
*/

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import spring.oldboy.database.repository.company_repository.CompanyRepository;

/* Помечаем специальной аннотацией см. док. DOC/SpringWebServlet/ControllerInterface.txt */
@Controller
public class GreetingController {

    /*
    Part 15: Lesson 71:
    В примитивном варианте мы бы просто создали ModelAndView объект через конструктор
    см. ниже метод *.bye():

        ModelAndView modelAndView = new ModelAndView();

    Но, мы уже знаем, что Spring, прекрасно все сделает за нас, если мы его попросим
    внедрить необходимую зависимость. Что мы и делаем через параметры метода. Теперь,
    осталось только задать имя отображения (наш dynamic_hello.jsp). Но сделать это нужно без
    указания префикса (prefix: /WEB-INF/jsp/) и суффикса (suffix: .jsp) указанных в
    application.yml. Что мы и делаем в *.setViewName("greeting/hello").

    В схеме DOC/SpringWebServlet/DispatcherServlet/DispatcherServletWorkingScheme.jpg
    мы видели, что у нас есть масса HandlerMethodArgumentResolver, которые могут
    подставить в наши методы нужные объекты, например, HttpServletRequest или
    CompanyRepository, пусть в данном примере, пока, мы их не используем.

    Part 15: Lesson 72:
    Используем аннотацию @RequestMapping() с параметрами, где указываем обработчик
    ответа (*.jsp страницу и метод GET) на запрос для каждого из написанных методов
    нашего контроллера.
    */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView hello(ModelAndView modelAndView,
                              HttpServletRequest request,
                              CompanyRepository companyRepository) {
        modelAndView.setViewName("greeting/hello");
        /* Возвращаем отображение */
        return modelAndView;
    }

    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    public ModelAndView bye() {
        ModelAndView modelAndView = new ModelAndView();
        /* Задаем отображение на запрос BYE, так же без префикса и суффикса */
        modelAndView.setViewName("greeting/bye");
        /* Возвращаем отображение */
        return modelAndView;
    }
}
