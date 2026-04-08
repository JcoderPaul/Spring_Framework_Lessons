- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/Controller.html)

---
### Interface Controller

**Пакет:** [org.springframework.web.servlet.mvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/package-summary.html)

**Все реализующие классы:**
- [AbstractController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/AbstractController.html),
- [AbstractUrlViewController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/AbstractUrlViewController.html),
- [ParameterizableViewController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/ParameterizableViewController.html),
- [ServletForwardingController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/ServletForwardingController.html),
- [ServletWrappingController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/ServletWrappingController.html),
- [UrlFilenameViewController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/UrlFilenameViewController.html)

**Функциональный интерфейс:** Это функциональный интерфейс, поэтому его можно использовать в качестве цели назначения для лямбда-выражения или ссылки на метод.

**См. так же:** 
- [SimpleControllerHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/SimpleControllerHandlerAdapter.html),
- [AbstractController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/AbstractController.html),
- [MockHttpServletRequest](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mock/web/MockHttpServletRequest.html),
- [MockHttpServletResponse](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mock/web/MockHttpServletResponse.html),
- [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContextAware.html),
- [ResourceLoaderAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ResourceLoaderAware.html),
- [ServletContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ServletContextAware.html),
- [WebApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/WebApplicationObjectSupport.html)

```java
  @FunctionalInterface
  public interface Controller
```

---
Интерфейс базового контроллера, представляющий компонент, который получает экземпляры HttpServletRequest и
HttpServletResponse, как и HttpServlet, но может участвовать в рабочем процессе MVC. Контроллеры можно сравнить с
понятием действия в [Struts](https://ru.wikipedia.org/wiki/Apache_Struts).

```
  В контексте фреймворка Struts (как первой, так и второй версии) Action (действие) — это основной компонент бизнес-логики.
  Простыми словами: это «команда» или связующее звено между запросом пользователя и результатом.
  Вот как это работает:
  
     1. Запрос: Пользователь нажимает кнопку или переходит по ссылке.
     2. Действие (Action): Контроллер передает управление соответствующему Action-классу. Этот класс обрабатывает данные
                           (например, сохраняет информацию в базу данных).
     3. Результат: Action возвращает строку-код (например, success или error), на основе которой Struts решает, какую
                   страницу показать пользователю дальше.
  
  В Struts 2 Action — это обычный Java-объект (POJO), который содержит свойства для данных формы и метод execute()
  для выполнения логики.
```

Любая реализация интерфейса контроллера должна представлять собой многоразовый, потокобезопасный класс, способный
обрабатывать несколько HTTP-запросов на протяжении всего жизненного цикла приложения. Чтобы иметь возможность легко
настроить контроллер, рекомендуется, чтобы реализации контроллера были (и обычно являются) JavaBeans.

#### Рабочий процесс

После того как [DispatcherServlet](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html) получил запрос и выполнил свою работу по разрешению локалей, тем и т. д., он пытается
разрешить контроллер, используя [HandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html). Когда будет найден контроллер для обработки запроса, будет вызван метод [handleRequest](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/Controller.html#handleRequest(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse)) обнаруженного контроллера; затем обнаруженный контроллер отвечает за обработку фактического запроса и, если применимо, за возврат соответствующего [ModelAndView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ModelAndView.html).

Фактически этот метод является основной точкой входа для [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html), который делегирует запросы контроллерам.

Таким образом, в основном любая прямая реализация интерфейса контроллера просто обрабатывает HttpServletRequests и должна возвращать ModelAndView для дальнейшей интерпретации DispatcherServlet. Любые дополнительные функции, такие как необязательная проверка, обработка форм и т. д., должны быть получены путем расширения [AbstractController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/AbstractController.html) или одного из его подклассов.

#### Примечания по проектированию и тестированию

Интерфейс контроллера явно предназначен для работы с объектами HttpServletRequest и HttpServletResponse, как и
HttpServlet. Он не стремится отделиться от API сервлетов, в отличии, например, от WebWork, JSF или Tapestry. Вместо
этого доступна вся мощь API сервлетов, что позволяет контроллерам быть универсальными: контроллер способен не только
обрабатывать запросы веб-интерфейса пользователя, но также обрабатывать протоколы удаленного взаимодействия или
генерировать отчеты по требованию.

Контроллеры можно легко протестировать, передав фиктивные объекты для объектов HttpServletRequest и HttpServletResponse
в качестве параметров методу handleRequest. Для удобства Spring поставляется с набором макетов API сервлетов, которые
подходят для тестирования любых веб-компонентов, но особенно подходят для тестирования веб-контроллеров Spring.

В отличие от действия Struts, нет необходимости имитировать (mock) ActionServlet или любую другую инфраструктуру;
достаточно mocking (имитации) HttpServletRequest и HttpServletResponse.

Если контроллерам необходимо знать о конкретных ссылках на среду, они могут реализовать определенные интерфейсы
осведомленности, как это может сделать любой другой компонент в контексте Spring (веб-приложения), например:
- [org.springframework.context.ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContextAware.html)
- [org.springframework.context.ResourceLoaderAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ResourceLoaderAware.html)
- [org.springframework.web.context.ServletContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ServletContextAware.html)

Такие ссылки на среду можно легко передавать в средах тестирования через соответствующие установщики, определенные в
соответствующих интерфейсах осведомленности. В общем, рекомендуется минимизировать зависимости: например, если вам нужна
только загрузка ресурсов, реализуйте только ResourceLoaderAware. В качестве альтернативы можно получить производный
класс от базового класса WebApplicationObjectSupport, который предоставляет все эти ссылки через удобные средства
доступа, но требует ссылку на ApplicationContext при инициализации.

Контроллеры могут использовать методы checkNotModified в [WebRequest](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/request/WebRequest.html) для поддержки HTTP-кэширования.

---
#### Метод

- `ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)` - Обработайте запрос и верните объект ModelAndView, который будет отображен DispatcherServlet.

Где, 
- *Параметры:*
  - request- текущий HTTP-запрос;
  - response- текущий HTTP-ответ;
- *Возвращает:* ModelAndView для рендеринга или null при прямой обработке;
- *Исключения:* Exception - в случае ошибок;

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/Controller.html)

---
**Доп. материалы:**
- [Interface Driven Controllers in Spring](https://www.baeldung.com/spring-interface-driven-controllers)
- [@Controller](https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann.html)
- [Using Interface for Controller Requests in Java Spring Framework](https://whyjun.github.io/blog/Using-Interface-for-Controller-Requests-in-Java%20Spring-Framework)
- [Why It’s a Best Practice to Use Interfaces for Services in Spring](https://medium.com/@persolenom/why-its-a-best-practice-to-use-interfaces-for-services-in-spring-db0764139a0e)
- [Spring Controllers Implementing Interfaces?](https://dzone.com/articles/spring-controllers-implementing-interfaces)
- [Spring MVC - Model Interface](https://www.geeksforgeeks.org/java/spring-mvc-model-interface/)
- [Java Services and Interfaces in a Spring Boot Application](https://davidgiard.com/java-services-and-interfaces-in-a-spring-boot-application)
- [A Controller, Service and DAO Example with Spring Boot and JSF](https://www.baeldung.com/jsf-spring-boot-controller-service-dao)
- [Spring Controller Interface](https://www.java4coding.com/contents/spring/controller-interface)
