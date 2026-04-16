- [См. исходный (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultMatchers.html)

---
### Class MockMvcResultMatchers

```
java.lang.Object
    org.springframework.test.web.servlet.result.MockMvcResultMatchers
```

**Пакет:** [org.springframework.test.web.servlet.result](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/package-summary.html)

```java
public abstract class MockMvcResultMatchers
                                    extends Object
```

---
Статические фабричные методы для действий с результатами на основе [ResultMatcher см.](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultMatcher.html)

---
#### Конструктор

public [MockMvcResultMatchers()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultMatchers.html#%3Cinit%3E())

---
#### Методы

- `static ContentResultMatchers content()` - Доступ к утверждениям тела ответа.
- `static CookieResultMatchers cookie()` - Доступ к ответным утверждениям файлов cookie.
- `static FlashAttributeResultMatchers flash()` - Доступ к утверждениям флэш-атрибутов.
- `static ResultMatcher forwardedUrl(String expectedUrl)` - Утверждает, что запрос был перенаправлен на указанный URL-адрес.
- `static ResultMatcher forwardedUrlPattern(String urlPattern)` - Утверждает, что запрос был перенаправлен на указанный URL-адрес.
- `static ResultMatcher forwardedUrlTemplate(String urlTemplate, Object... uriVars)` - Утверждает, что запрос был перенаправлен на данный шаблон URL.
- `static HandlerResultMatchers handler()` - Доступ к утверждениям обработчика, обработавшего запрос.
- `static HeaderResultMatchers header()` - Доступ к утверждениям заголовка ответа.
- `static JsonPathResultMatchers jsonPath(String expression, Object... args)` - Доступ к утверждениям тела ответа с использованием выражения JsonPath для проверки определенного подмножества тела ответа.
- `static <T> ResultMatcher jsonPath(String expression, Matcher<? super T> matcher)` - Оцените данное выражение JsonPath по телу ответа и утвердите полученное значение с помощью данного Hamcrest Matcher.
- `static <T> ResultMatcher jsonPath(String expression, Matcher<? super T> matcher, Class<T> targetType)` - Оцените данное выражение JsonPath по телу ответа и утвердите полученное значение с помощью данного Hamcrest Matcher, приведя полученное значение к заданному целевому типу перед применением средства сопоставления.
- `static ModelResultMatchers model()` - Доступ к утверждениям, связанным с моделью.
- `static ResultMatcher redirectedUrl(String expectedUrl)` - Утверждает, что запрос был перенаправлен на указанный URL-адрес.
- `static ResultMatcher redirectedUrlPattern(String urlPattern)` - Утверждает, что запрос был перенаправлен на указанный URL-адрес.
- `static ResultMatcher redirectedUrlTemplate(String urlTemplate, Object... uriVars)` - Утверждает, что запрос был перенаправлен на данный шаблон URL.
- `static RequestResultMatchers request()` - Доступ к утверждениям, связанным с запросом.
- `static StatusResultMatchers status()` - Доступ к утверждениям статуса ответа.
- `static ViewResultMatchers view()` - Доступ к утверждениям в выбранном представлении.
- `static XpathResultMatchers xpath(String expression, Object... args)` - Доступ к утверждениям тела ответа с использованием выражения XPath для проверки определенного подмножества тела ответа.
- `static XpathResultMatchers xpath(String expression, Map<String,String> namespaces, Object... args)` - Доступ к утверждениям тела ответа с использованием выражения XPath для проверки определенного подмножества тела ответа.

---
- Методы, унаследованные от класса java.lang.Object: clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
**Доп. материал:**
- [MockMvcResultMatchers on GitHub](https://github.com/spring-projects/spring-framework/blob/main/spring-test/src/main/java/org/springframework/test/web/servlet/result/MockMvcResultMatchers.java)
- [How to use MockMvcResultMatchers.jsonPath](https://stackoverflow.com/questions/42725199/how-to-use-mockmvcresultmatchers-jsonpath)
- [Integration Testing in Spring](https://www.baeldung.com/integration-testing-in-spring)
- [Interface ResultMatcher](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultMatcher.html)
- [Тестирование контроллеров с помощью MockMvc](https://habr.com/ru/articles/171911/)
- [Spring Boot MockMVC Tutorial](https://examples.javacodegeeks.com/spring-boot-mockmvc-tutorial/)
- [Mock testing of Spring MVC interfaces](https://www.springcloud.io/post/2022-02/mockmvc/#gsc.tab=0)
- [Testing Spring Boot Applications With MockMvc and @WebMvcTest](https://rieckpil.de/guide-to-testing-spring-boot-applications-with-mockmvc/)
- [Spring MVC - Unit Testing](https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-mvc-unit-testing.html)
- [Spring Boot MockMvc Example with @WebMvcTest](https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/)
- [Java Examples for org.springframework.test.web.servlet.result.MockMvcResultMatchers](https://www.javatips.net/api/org.springframework.test.web.servlet.result.mockmvcresultmatchers)
- [SecurityMockMvcResultMatchers](https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/result-matchers.html)
- [How to Write Tests with MockMvc in Java Spring](https://medium.com/@HasanGurel/how-to-write-tests-with-mockmvc-in-java-spring-03e5930a1374)
- [API Testing with MockMvc and JSONPath](https://codingnomads.com/api-testing-mockmvc-jsonpath-example)
