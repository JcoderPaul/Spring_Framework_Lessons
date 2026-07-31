- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExceptionResolver.html)

---
### Interface HandlerExceptionResolver

**Пакет:** [org.springframework.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/package-summary.html)

**Все реализующие классы:** 
- [AbstractHandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerExceptionResolver.html),
- [AbstractHandlerMethodExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMethodExceptionResolver.html),
- [DefaultHandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html),
- [ExceptionHandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ExceptionHandlerExceptionResolver.html),
- [HandlerExceptionResolverComposite](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/HandlerExceptionResolverComposite.html),
- [ResponseStatusExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/annotation/ResponseStatusExceptionResolver.html),
- [SimpleMappingExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/SimpleMappingExceptionResolver.html)

```java
  public interface HandlerExceptionResolver
```

---
Интерфейс, который должен быть реализован объектами, которые могут разрешать исключения, возникающие во время
сопоставления или выполнения обработчика, в типичном случае для представлений (view) ошибок. Реализация обычно
регистрируется как bean-компоненты в контексте приложения.

Представления (view) ошибок аналогичны страницам ошибок JSP, но могут использоваться с любым типом исключений,
включая любое проверенное исключение, с потенциально детальными сопоставлениями для конкретных обработчиков.

---
### Методы

`@Nullable ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, `@Nullable Object handler, Exception ex)` - Попробуйте разрешить данное исключение, возникшее во время выполнения обработчика, вернув ModelAndView, который представляет конкретную страницу ошибки, если это необходимо.

Возвращенный ModelAndView может быть пустым, чтобы указать, что исключение было успешно разрешено, но представление не должно отображаться, например, путем установки кода состояния.

Где, параметры: 
- request - текущий HTTP-запрос;
- response - текущий HTTP-ответ;
- handler - выполненный обработчик, или null если он не был выбран во время исключения (например, если не удалось выполнить многочастное разрешение);
- ex - исключение, возникшее во время выполнения обработчика;

Возвращает: соответствующий ModelAndView пересылке или null обработке по умолчанию в цепочке разрешения;

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExceptionResolver.html)
- [HandlerExceptionResolver.java](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/HandlerExceptionResolver.java)

---
**Сопутствующий материал:**
- [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [Spring MVC Interview Questions](https://www.baeldung.com/spring-mvc-interview-questions)
- [Spring Boot Exception Handling — HandlerExceptionResolver](https://farzinpashaeee.medium.com/spring-boot-exception-handling-handlerexceptionresolver-11c75ffdf5d6)
- [Spring MVC Exception Handling - @ControllerAdvice, @ExceptionHandler, HandlerExceptionResolver](https://www.digitalocean.com/community/tutorials/spring-mvc-exception-handling-controlleradvice-exceptionhandler-handlerexceptionresolver)
- [Exceptions](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/exceptionhandlers.html)
- [What is the HandlerExceptionResolver](https://medium.com/@osmanseyban/what-is-the-handlerexceptionresolver-edf6187503ec)
- [Spring MVC - HandlerExceptionResolver Examples](https://www.logicbig.com/how-to/code-snippets/jcode-spring-mvc-handlerexceptionresolver.html)
