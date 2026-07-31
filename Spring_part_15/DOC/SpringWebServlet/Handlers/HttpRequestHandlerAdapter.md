- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/HttpRequestHandlerAdapter.html)

---
### Class HttpRequestHandlerAdapter

**Пакет:** [org.springframework.web.servlet.mvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/package-summary.html)

```
java.lang.Object
  org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter
```

**Реализованные интерфейсы:** [HandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerAdapter.html)

**Смотрите также:** 
- [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html),
- [HttpRequestHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/HttpRequestHandler.html),
- [SimpleControllerHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/SimpleControllerHandlerAdapter.html)

```java
  public class HttpRequestHandlerAdapter
    extends Object
      implements HandlerAdapter
```
---
Адаптер для использования простого [HttpRequestHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/HttpRequestHandler.html) интерфейса с общим [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html). Поддерживает обработчики, реализующие LastModified интерфейс.

Это класс SPI, который не используется напрямую кодом приложения.

---
### Методы

- `long getLastModified(HttpServletRequest request, Object handler)` - Тот же контракт, что и для метода HttpServlet getLastModified.
- `ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)` - Используйте данный обработчик для обработки этого запроса.
- `boolean supports(Object handler)` - Учитывая экземпляр обработчика, сообщите, HandlerAdapter может ли он его поддерживать.

---
Методы, унаследованные от класса [java.lang.Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/HttpRequestHandlerAdapter.html)

---
**Сопутствующие материалы:**
- [HandlerAdapters in Spring MVC](https://www.baeldung.com/spring-mvc-handler-adapters)
- [The Mechanics of Request Mapping in Spring Boot](https://medium.com/@AlexanderObregon/the-mechanics-of-request-mapping-in-spring-boot-92d1065cc0ad)
