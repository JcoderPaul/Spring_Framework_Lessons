package spring.oldboy.http.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* Подключаем логгер */
@Slf4j
/*
Дабы сделать наш обработчик исключений при валидации глобальным
помечаем его как @ControllerAdvice, данная аннотация если посмотреть
ее код помечена как @Component, т.е. она является bean-ом.

Lesson 97: Указываем ошибки какого пакета контроллеров обрабатывает
это хандлер, в данном случае обычных не REST.
*/
@ControllerAdvice(basePackages = "spring.oldboy.http.controller")
/* Применив extends ResponseEntityExceptionHandler получим широкий диапазон отлова исключений */
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }
}
