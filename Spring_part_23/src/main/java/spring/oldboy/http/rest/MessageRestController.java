package spring.oldboy.http.rest;

/* Lesson 116 - i18n-MessageSource */

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/* REST контроллер */
@RestController
/* Стандартная аннотация для картирования */
@RequestMapping("/api/v1/messages")
/* Аннотация для конструкторов */
@RequiredArgsConstructor
public class MessageRestController {

    /* Это зависимость есть у любого Spring приложения т.к. принадлежит Spring Core */
    private final MessageSource messageSource;

    /*
    Метод принимает два параметра которые мы должны передать в ResourceBundle,
    ну или как мы уже знаем в MessageSource. Внедряем (inject) MessageSource
    выше по коду и используем тут.

    Ключами или key у нас являются login.username, login.password из файлов
    messages.properties,
    */
    @GetMapping
    public String getMessage(@RequestParam("key") String key,
                             @RequestParam("lang") String language) {
        /* Чтобы метод не падал с исключением мы передаем в него null-вые аргументы */
        return messageSource.getMessage(key, null, new Locale(language));
    }
}
