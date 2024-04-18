package spring.oldboy.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

/* Подключаем логгер */
@Slf4j
/*
Дабы сделать наш обработчик исключений при валидации глобальным
помечаем его как @ControllerAdvice, данная аннотация если посмотреть
ее код помечена как @Component, т.е. она является bean-ом.
*/
@ControllerAdvice
/* Применив extends ResponseEntityExceptionHandler получим широкий диапазон отлова исключений */
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }
}
