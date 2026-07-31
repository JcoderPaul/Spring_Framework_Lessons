- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/HttpRequestHandler.html)

---
### Interface HttpRequestHandler

**Пакет:** [org.springframework.web](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/package-summary.html)

**Все реализующие классы:** 
- [DefaultServletHttpRequestHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/resource/DefaultServletHttpRequestHandler.html),
- [ResourceHttpRequestHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/resource/ResourceHttpRequestHandler.html),
- [SockJsHttpRequestHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/sockjs/support/SockJsHttpRequestHandler.html),
- [WebSocketHttpRequestHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/server/support/WebSocketHttpRequestHandler.html)

**Функциональный интерфейс:** Это функциональный интерфейс, поэтому его можно использовать в качестве цели назначения для лямбда-выражения или ссылки на метод.

**Смотреть также:** 
- [HttpRequestHandlerServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/HttpRequestHandlerServlet.html),
- [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html),
- [ModelAndView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ModelAndView.html),
- [Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/Controller.html),
- [HttpRequestHandlerAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/HttpRequestHandlerAdapter.html)

---
```java
  @FunctionalInterface 
  public interface HttpRequestHandler
```

Простой интерфейс обработчика для компонентов, обрабатывающих HTTP-запросы, аналогичный сервлету. Объявляет только
ServletException и [IOException](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/IOException.html), 
чтобы разрешить использование в любом HttpServlet. Этот интерфейс по сути является
прямым эквивалентом HttpServlet, сведенным к центральному методу дескриптора.

Самый простой способ представить bean-компонент HttpRequestHandler в стиле Spring — определить его в корневом контексте
веб-приложения Spring и определить [HttpRequestHandlerServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/HttpRequestHandlerServlet.html)
в файле web.xml, указывая на целевой bean-компонент HttpRequestHandler через его имя сервлета, которое должно совпадать 
с именем целевого bean-компонента.

Поддерживается как тип обработчика в Spring [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html), 
позволяя взаимодействовать с расширенными средствами отображения и перехвата диспетчера. Это рекомендуемый способ предоставления 
HttpRequestHandler, сохраняя при этом реализации обработчика свободными от прямых зависимостей от среды DispatcherServlet.

Обычно реализуется для прямой генерации двоичных ответов без использования отдельного ресурса представления. Это
отличает его от контроллера в среде Spring Web MVC. Отсутствие возвращаемого значения [ModelAndView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ModelAndView.html) дает более четкую подпись для вызывающих сторон, отличных от [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html), указывая на то, что представление для рендеринга никогда не будет.

Обратите внимание, что HttpRequestHandlers может дополнительно реализовывать интерфейс LastModified, как и контроллеры,
при условии, что они запускаются в Spring DispatcherServlet. Однако обычно в этом нет необходимости, поскольку
HttpRequestHandlers обычно изначально поддерживают только POST-запросы. Альтернативно, обработчик может реализовать
обработку HTTP-заголовка «If-Modified-Since» вручную в своем методе дескриптора.

---
### Методы

- `void handleRequest(HttpServletRequest request, HttpServletResponse response)` - Обработать данный запрос, генерируя ответ.

Где, параметры: 
- request - текущий HTTP-запрос;
- response - текущий HTTP-ответ;

Исключения: 
- ServletException- в случае общих ошибок;
- IOException- в случае ошибок ввода/вывода;

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/HttpRequestHandler.html)
