- [См. источник (ENG)](https://docs.spring.io/spring-framework/docs/6.1.2/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)

---
### Class MockMvc

**Пакет:** [org.springframework.test.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/package-summary.html)

```
java.lang.Object
    org.springframework.test.web.servlet.MockMvc
```

```java
public final class MockMvc extends Object
```

Основная точка входа для поддержки тестирования Spring MVC на стороне сервера.

**Пример:**

```java
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

 // ...

 WebApplicationContext wac = ...;

 MockMvc mockMvc = webAppContextSetup(wac).build();

 mockMvc.perform(get("/form"))
        .andExpectAll(
            status().isOk(),
            content().contentType("text/html"),
            forwardedUrl("/WEB-INF/layouts/main.jsp")
        );
```

---
#### Методы

- `DispatcherServlet getDispatcherServlet()` - Верните базовый DispatcherServlet экземпляр, с помощью которого это MockMvc было инициализировано.

Предназначен для использования в сценарии пользовательской обработки запросов, где компонент обработки запросов делегируется во время [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) выполнения и поэтому его необходимо внедрить вместе с ним.

Для большинства сценариев обработки просто используйте perform(org.springframework.test.web.servlet.RequestBuilder),
а если вам нужно настроить DispatcherServlet, укажите [DispatcherServletCustomizer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/DispatcherServletCustomizer.html) для [MockMvcBuilder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvcBuilder.html).

- `ResultActions perform(RequestBuilder requestBuilder)` - Выполните запрос и верните тип, который позволяет связывать дальнейшие действия, такие как утверждение ожиданий, с результатом.

**Параметры:** requestBuilder - используется для подготовки запроса к исполнению; см. статические фабричные методы в [MockMvcRequestBuilders](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockMvcRequestBuilders.html).
**Возвращает:** экземпляр [ResultActions](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultActions.html) (никогда null)
**Исключения:** [Exception](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Exception.html)

---
**См. также:**
- [MockMvcRequestBuilders](https://docs.spring.io/spring-framework/docs/6.1.2/javadoc-api/org/springframework/test/web/servlet/request/MockMvcRequestBuilders.html)
- [MockMvcResultMatchers](https://docs.spring.io/spring-framework/docs/6.1.2/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultMatchers.html)

---
**См. доп. материалы:**
- [Testing the Web Layer](https://spring.io/guides/gs/testing-web)
- [Using MockMvc With SpringBootTest vs. Using WebMvcTest](Using MockMvc With SpringBootTest vs. Using WebMvcTest)
- [MockMvc](https://docs.spring.io/spring-framework/reference/testing/mockmvc.html)
- [Spring Boot MockMVC Example](https://www.geeksforgeeks.org/springboot/spring-boot-mockmvc-example/)
- [A Practical Guide to Testing Spring Controllers With MockMvcTester](https://blog.jetbrains.com/idea/2025/04/a-practical-guide-to-testing-spring-controllers-with-mockmvctester/)
- [WebTestClient vs MockMvc in Spring Boot](https://codefarm0.medium.com/webtestclient-vs-mockmvc-in-spring-boot-4e7354e1d1ab)
- [Configuring MockMvc](https://docs.spring.io/spring-framework/reference/testing/mockmvc/hamcrest/setup.html)
- [Spring Boot MockMVC Testing with Example Project](https://www.geeksforgeeks.org/springboot/spring-boot-mockmvc-testing-with-example-project/)
- [Testing CRUD Endpoint Code HTTP with Spring Boot using MockMvc](https://medium.com/@rayanabonfanti/testing-crud-endpoint-code-http-with-spring-boot-using-mockmvc-5a87d8aa2542)
- [Spring MVC Test Integration](https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/test-mockmvc.html)
- [MockMvc – Spring MVC testing framework introduction: Testing Spring endpoints](https://blog.marcnuri.com/mockmvc-spring-mvc-framework)
