- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/SimpleControllerHandlerAdapter.html)

---
### Class SimpleControllerHandlerAdapter

**Пакет:** [org.springframework.web.servlet.mvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/package-summary.html)

```
java.lang.Object
  org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter
```

**Реализованные интерфейсы:** [HandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerAdapter.html)

**Так же см.:** 
- [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html),
- [Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/Controller.html),
- [HttpRequestHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/HttpRequestHandlerAdapter.html)

```java
public class SimpleControllerHandlerAdapter
                                extends Object
                                      implements HandlerAdapter
```

Адаптер для использования простого Controller интерфейса рабочего процесса с общим DispatcherServlet. Поддерживает
обработчики, реализующие LastModified интерфейс.

Это класс SPI, который не используется напрямую кодом приложения.

---
### Методы

- `long getLastModified(HttpServletRequest request, Object handler)` - Тот же контракт, что и для метода HttpServlet getLastModified.
- `ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)` - Используйте данный обработчик для обработки этого запроса.
- `boolean supports(Object handler)` - Учитывая экземпляр обработчика, сообщите, HandlerAdapter может ли он его поддерживать.

---
Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/SimpleControllerHandlerAdapter.html)
- [HandlerAdapters in Spring MVC](https://www.baeldung.com/spring-mvc-handler-adapters)
- [Spring MVC Controller Design](https://stackoverflow.com/questions/5265751/spring-mvc-controller-design)
- [Spring MVC - Mapping a Controller implementation with SimpleControllerHandlerAdapter strategy](https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/simple-controller-handler-adapter.html)
- [An Intro to the Spring DispatcherServlet](https://www.baeldung.com/spring-dispatcherservlet)
- [Request Handling by Spring](https://support.ptc.com/help/windchill/r13.1.2.0/de/index.html#page/Windchill_Help_Center/customization/WCCG_UICust_MVC_Overview_RequestHandlingBySpring.html)
