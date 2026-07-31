- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/annotation/ResponseStatusExceptionResolver.html)

---
### Class ResponseStatusExceptionResolver

**Пакет:** [Package org.springframework.web.servlet.mvc.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/annotation/package-summary.html)

```
java.lang.Object
  org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver
    org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver
```

**Все реализованные интерфейсы:** 
- [Aware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/Aware.html),
- [MessageSourceAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/MessageSourceAware.html),
- [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html),
- [HandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExceptionResolver.html)

**См. так же:** 
- [ResponseStatus](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html),
- [ResponseStatusException](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/server/ResponseStatusException.html)

```java
public class ResponseStatusExceptionResolver
      extends AbstractHandlerExceptionResolver
          implements MessageSourceAware
```

---
[HandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExceptionResolver.html), который использует аннотацию [@ResponseStatus](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html) для сопоставления исключений с кодами состояния HTTP. Этот преобразователь исключений включен по умолчанию в [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html), конфигурации Java MVC и пространстве имен MVC.

Начиная с версии 4.2, этот преобразователь также рекурсивно ищет @ResponseStatus, присутствующий в исключениях причин, а начиная с версии 4.2.2 этот преобразователь поддерживает переопределение атрибутов для @ResponseStatus в настраиваемых аннотациях.

Начиная с версии 5.0 этот преобразователь также поддерживает [ResponseStatusException](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/server/ResponseStatusException.html).

---
- *Поля, унаследованные от класса [AbstractHandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerExceptionResolver.html#field-summary):* [logger](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerExceptionResolver.html#logger)
- *Поля, унаследованные от интерфейса [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#field-summary):* [HIGHEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#HIGHEST_PRECEDENCE), [LOWEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#LOWEST_PRECEDENCE)

---
#### Методы

- `protected ModelAndView applyStatusAndReason(int statusCode, String reason, HttpServletResponse response)` - Примените разрешенный код состояния и причину к ответу.
- `protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)` - Фактически разрешите данное исключение, возникшее во время выполнения обработчика, вернув ModelAndView при необходимости значение, представляющее конкретную страницу ошибки.
- `protected ModelAndView resolveResponseStatus(ResponseStatus responseStatus, HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)` - Метод шаблона, обрабатывающий @ResponseStatus аннотацию.
- `protected ModelAndView resolveResponseStatusException(ResponseStatusException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Метод шаблона, который обрабатывает файл ResponseStatusException.
- `void setMessageSource(MessageSource messageSource)` - Установите параметр MessageSource, в котором работает этот объект.

---
- *Методы, унаследованные от класса [AbstractHandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerExceptionResolver.html#method-summary):* buildLogMessage, getOrder, hasHandlerMappings, logException, prepareResponse, preventCaching, resolveException, setMappedHandlerClasses, setMappedHandlers, setOrder, setPreventResponseCaching, setWarnLogCategory, shouldApplyTo
- *Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary):* clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/annotation/ResponseStatusExceptionResolver.html)

---
**Дополнительные материалы:**
- [Spring ResponseStatusException](https://www.baeldung.com/spring-response-status-exception)
- [ResponseStatusExceptionResolver custom logging](https://stackoverflow.com/questions/77122634/responsestatusexceptionresolver-custom-logging)
- [Spring boot Exception Handling](https://medium.com/@sumit.dev2148/spring-boot-exception-handling-28deb3382f38)
- [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [Configuring global exception-handling in Spring MVC](https://steveliles.github.io/configuring_global_exception_handling_in_spring_mvc.html)
- [Spring MVC Error Handling Example](https://www.javacodegeeks.com/2012/11/spring-mvc-error-handling-example.html)
- [Spring @ResponseStatus Annotation](https://www.geeksforgeeks.org/advance-java/spring-responsestatus-annotation/)
- [SpringMVC exception handling system in-depth analysis](https://www.springcloud.io/post/2022-02/springmvc-exception/#gsc.tab=0)
- [Complete Guide to Exception Handling in Spring Boot](https://reflectoring.io/spring-boot-exception-handling/)
- [Spring Rest - Exception Handling](https://dev.to/noelopez/spring-rest-exception-handling-part-1-1jj2)
- [Using Spring @ResponseStatus to Set HTTP Status Code](https://www.baeldung.com/spring-response-status)
- [Spring Boot Exception Handling — HandlerExceptionResolver](https://farzinpashaeee.medium.com/spring-boot-exception-handling-handlerexceptionresolver-11c75ffdf5d6)
