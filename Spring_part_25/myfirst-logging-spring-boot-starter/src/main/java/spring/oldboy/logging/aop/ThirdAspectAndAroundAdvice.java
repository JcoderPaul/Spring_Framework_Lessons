package spring.oldboy.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/* Логируем процесс */
@Slf4j
/* Указываем что это aspect */
@Aspect
public class ThirdAspectAndAroundAdvice {

    /*
    Lesson 122:

    Данный метод должен вернуть Object, т.к. он будет оборачивать своей логикой метод *.findById(),
    и это некая сущность вынутая из нашей БД. Поскольку мы планируем полностью управлять методом
    *.findById(), нам нужно внедрить точку присоединения JoinPoint.

    !!! НО JoinPoint не содержит методов, которые бы позволили вызвать функционал следующего в цепочке
    перехватчика (Interceptor) или целевой объект !!!

    Поэтому нам нужен его наследник ProceedingJoinPoint из org.aspectj.lang -
    https://www.javadoc.io/doc/org.aspectj/aspectjrt/1.7.2/org/aspectj/lang/ProceedingJoinPoint.html
    Именно у него есть метод *.proceed(), который позволяет перейдите к следующему advice-e или вызову
    целевого метода. И именно это же метод возвращает Object.

    Далее, вся учебная логика нашего Around advice повторяет логику всех advice-ов по отдельности из
    SecondAspectAndFirstAdvice.java, т.е. логика 4-х advice-ов реализована в одном, но нужно помнить -
    "С большой силой приходит большая ответственность"
    */
    @Around("spring.oldboy.logging.aop.FirstAspect.anyFindByIdServiceMethod() && target(service) && args(id)")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint,
                                   Object service,
                                   Object id) throws Throwable {
        log.info("AROUND before - invoked findById method in class {}, with id {}", service
                                                                                  , id);
        try {
            Object result = joinPoint.proceed();
            log.info("AROUND after returning - invoked findById method in class {}, result {}", service
                                                                                              , result);
            return result;
        } catch (Throwable ex) {
            log.info("AROUND after throwing - invoked findById method in class {}, exception {}: {}", service
                                                                                                    , ex.getClass()
                                                                                                    , ex.getMessage());
            throw ex;
        } finally {
            log.info("AROUND after (finally) - invoked findById method in class {}", service);
        }
    }

}
