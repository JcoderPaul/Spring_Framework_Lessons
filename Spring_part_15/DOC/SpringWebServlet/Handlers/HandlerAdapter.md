- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerAdapter.html)

---
### Interface HandlerAdapter

**Пакет:** [org.springframework.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/package-summary.html)

**Реализующие классы:**
- [AbstractHandlerMethodAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/AbstractHandlerMethodAdapter.html),
- [HandlerFunctionAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/function/support/HandlerFunctionAdapter.html),
- [HttpRequestHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/HttpRequestHandlerAdapter.html),
- [RequestMappingHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerAdapter.html),
- [SimpleControllerHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/SimpleControllerHandlerAdapter.html),
- [SimpleServletHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/SimpleServletHandlerAdapter.html)

**Смотреть также:**
- [SimpleControllerHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/SimpleControllerHandlerAdapter.html),
- [SimpleServletHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/SimpleServletHandlerAdapter.html)

---
```
  public interface HandlerAdapter
```

SPI инфраструктуры MVC, позволяющий параметризировать основной рабочий процесс MVC.

Интерфейс, который должен быть реализован для каждого типа обработчика для обработки запроса. Этот интерфейс используется для [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) неограниченного расширения. Доступ DispatcherServlet ко всем установленным обработчикам осуществляется через этот интерфейс, что означает, что он не содержит кода, специфичного для какого-либо типа обработчика.

Обратите внимание, что обработчик может иметь тип Object. Это сделано для того, чтобы позволить обработчикам из других
платформ интегрироваться с этой платформой без специального кодирования, а также для того, чтобы объекты-обработчики,
управляемые аннотациями, не подчинялись какому-либо конкретному интерфейсу Java.

Этот интерфейс не предназначен для разработчиков приложений. Он доступен для обработчиков, которые хотят разработать
собственный рабочий веб-процесс.

**ПРИМЕЧАНИЕ.** HandlerAdapter разработчики могут реализовать [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html) интерфейс, чтобы иметь возможность указать порядок сортировки (и, следовательно, приоритет) для применения DispatcherServlet. Неупорядоченные экземпляры имеют самый низкий приоритет.

---
### Методы

- `long getLastModified(HttpServletRequest request, Object handler)` - Устарело. Начиная с версии 5.3.9 вместе с LastModified.
- `ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)` - Используйте данный обработчик для обработки этого запроса.
- `boolean supports(Object handler)` - Учитывая экземпляр обработчика, сообщите, HandlerAdapter может ли он его поддерживать.

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerAdapter.html)
- [HandlerAdapters in Spring MVC](https://www.baeldung.com/spring-mvc-handler-adapters)
