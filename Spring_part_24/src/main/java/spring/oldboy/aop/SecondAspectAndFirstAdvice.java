package spring.oldboy.aop;

/*
Lesson 119 - Изучаем AOP - Before Advice.
Lesson 120 - Изучаем AOP - Внедрение параметров целевых методов в advice-ы.
Lesson 121 - Изучаем работу @AfterReturning, @AfterThrowing, @After advice-ов.
*/

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
@Order(0)
public class SecondAspectAndFirstAdvice {

    /*
    Lesson 119:
    Аннотация с параметрами - @Before("execution(public * spring.oldboy.service.*Service.findById(*))"),
    равносильна той, что применена ниже, но менее наглядна.

    И так, мы планируем, что данный Advice будет внедрен перед каждым запуском методов .findById(*) из
    пакета service, согласно нами же заданному срезу - pointcut.

    Тут и далее мы будем доставать один и тот же pointcut метод из FirstAspect.java
    */
    @Before("FirstAspect.anyFindByIdServiceMethod()")
    public void addLogging() {
        log.info("BEFORE invoked findById method without param");
    }

    /* Lesson 120: Используя точки среза напрямую в параметрах аннотации получаем нужные нам параметры */
    @Before(value = "FirstAspect.anyFindByIdServiceMethod() " +
                    "&& args(id) " +
                    "&& target(service) " +
                    "&& this(serviceProxy)" +
                    "&& @within(transactional)",
            argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLoggingWithJoinPointAndParam(JoinPoint joinPoint,
                                                Object id,
                                                Object service,
                                                Object serviceProxy,
                                                Transactional transactional) {
        log.info("BEFORE invoked findById method in class {}, with id {}", service, id);
    }

    /*
    Свойство 'returning' определяет наш возвращаемый объект используя
    стандартный синтаксис pointcut-ов мы этого сделать не можем.
    */
    @AfterReturning(value = "FirstAspect.anyFindByIdServiceMethod() " +
                            "&& target(service)",
                    returning = "result")
    public void addLoggingAfterReturning(Object result, Object service) {
        log.info("AFTER RETURNING - invoked findById method in class {}, result {}", service, result);
    }

    /*
    В данном advice-е у нас есть доступ к исключениям 'throwing',
    передаем его, как параметр в метод и используем для записи в
    лог.
    */
    @AfterThrowing(value = "FirstAspect.anyFindByIdServiceMethod() " +
                           "&& target(service)",
                   throwing = "exception")
    public void addLoggingAfterThrowing(Throwable exception, Object service) {
        log.info("AFTER THROWING - invoked findById method in class {}, exception {}: {}", service,
                                                                                           exception.getClass(),
                                                                                           exception.getMessage());
    }

    /*
    В данном advice-е у нас нет доступа ни к исключениям, ни к
    возвращаемым методом объектам, их может просто не быть.
    */
    @After("FirstAspect.anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterFinally(Object service) {
        log.info("AFTER (FINALLY) - invoked findById method in class {}", service);
    }
}
