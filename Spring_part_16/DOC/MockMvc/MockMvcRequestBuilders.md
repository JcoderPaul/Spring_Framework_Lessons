- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockMvcRequestBuilders.html)

---
### Class MockMvcRequestBuilders

**Пакет:** [org.springframework.test.web.servlet.request](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/request/package-summary.html)

```
java.lang.Object
    org.springframework.test.web.servlet.request.MockMvcRequestBuilders
```

```java
public abstract class MockMvcRequestBuilders 
                                extends Object
```

---
Статические фабричные методы для [RequestBuilders](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/RequestBuilder.html).

Интеграция с Spring TestContext Framework:
Методы этого класса будут повторно использовать объект [MockServletContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mock/web/MockServletContext.html), созданный [Spring TestContext Framework](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework.html).

---
#### Методы
- `static RequestBuilder asyncDispatch(MvcResult mvcResult)` - Создайте RequestBuilder для асинхронной отправки запрос MvcResult, который начал асинхронную обработку.
- `static MockHttpServletRequestBuilder delete(String urlTemplate, Object... uriVariables)` - Создайте MockHttpServletRequestBuilder запрос на удаление.
- `static MockHttpServletRequestBuilder delete(URI uri)` - Создайте MockHttpServletRequestBuilder запрос на удаление.
- `static MockHttpServletRequestBuilder get(String urlTemplate, Object... uriVariables)` - Создайте MockHttpServletRequestBuilder запрос GET.
- `static MockHttpServletRequestBuilder get(URI uri)` - Создайте MockHttpServletRequestBuilderзапрос GET.
- `static MockHttpServletRequestBuilder head(String urlTemplate, Object... uriVariables)` - Создайте MockHttpServletRequestBuilder запрос HEAD.
- `static MockHttpServletRequestBuilder head(URI uri)` - Создайте MockHttpServletRequestBuilder запрос HEAD.
- `static MockMultipartHttpServletRequestBuilder multipart(String urlTemplate, Object... uriVariables)` - Создайте MockMultipartHttpServletRequestBuilder составной запрос, используя POST в качестве метода HTTP.
- `static MockMultipartHttpServletRequestBuilder multipart(URI uri)` - Вариант multipart(String, Object...)с URI.
- `static MockMultipartHttpServletRequestBuilder multipart(HttpMethod httpMethod, String urlTemplate, Object... uriVariables)` - Вариант multipart(String, Object...) этого также принимает HttpMethod.
- `static MockMultipartHttpServletRequestBuilder multipart(HttpMethod httpMethod, URI uri)` - Вариант multipart(String, Object...) с URI и HttpMethod.
- `static MockHttpServletRequestBuilder options(String urlTemplate, Object... uriVariables)` - Создайте MockHttpServletRequestBuilder запрос OPTIONS.
- `static MockHttpServletRequestBuilder options(URI uri)` - Создайте MockHttpServletRequestBuilder запрос OPTIONS.
- `static MockHttpServletRequestBuilder patch(String urlTemplate, Object... uriVariables)` - Создайте MockHttpServletRequestBuilder запрос PATCH.
- `static MockHttpServletRequestBuilder patch(URI uri)` - Создайте MockHttpServletRequestBuilder запрос PATCH.
- `static MockHttpServletRequestBuilder post(String urlTemplate, Object... uriVariables)` - Создайте MockHttpServletRequestBuilder запрос POST.
- `static MockHttpServletRequestBuilder post(URI uri)` - Создайте MockHttpServletRequestBuilder запрос POST.
- `static MockHttpServletRequestBuilder put(String urlTemplate, Object... uriVariables)` - Создайте MockHttpServletRequestBuilder запрос PUT.
- `static MockHttpServletRequestBuilder put(URI uri)` - Создайте MockHttpServletRequestBuilder запрос PUT.
- `static MockHttpServletRequestBuilder request(String httpMethod, URI uri)` - Альтернативный фабричный метод, позволяющий использовать собственные HTTP-глаголы.
- `static MockHttpServletRequestBuilder request(HttpMethod method, String urlTemplate, Object... uriVariables)` - Создайте MockHttpServletRequestBuilder запрос с указанным методом HTTP.
- `static MockHttpServletRequestBuilder request(HttpMethod httpMethod, URI uri)` - Создайте MockHttpServletRequestBuilder запрос с указанным методом HTTP.

---
**Методы унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary):** clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait, wait, wait

---
**Доп. материал:**
- [Spring TestContext Framework](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework.html)
- [Performing Requests](https://docs.spring.io/spring-framework/reference/testing/mockmvc/hamcrest/requests.html)
- [Spring Boot MockMVC Example](https://www.geeksforgeeks.org/springboot/spring-boot-mockmvc-example/)
- [Testing Spring's @RequestBody using Spring MockMVC](https://stackoverflow.com/questions/20504399/testing-springs-requestbody-using-spring-mockmvc)
- [Get JSON Content as Object Using MockMVC](https://www.baeldung.com/spring-mockmvc-fetch-json)
- [A tool for Spring MockMvcRequestBuilder to post form objects easily](https://blog.florianlopes.io/tool-for-spring-mockmvcrequestbuilder-forms-tests/)
- [Unit Testing Spring REST Controllers Via Mock MVC](https://medium.com/@phoenixrising_93140/unit-testing-spring-rest-controllers-via-mock-mvc-eb74490ee7f0)
- [A Practical Guide to Testing Spring Controllers With MockMvcTester](https://blog.jetbrains.com/idea/2025/04/a-practical-guide-to-testing-spring-controllers-with-mockmvctester/)
- [Testing Spring Boot Applications With MockMvc and @WebMvcTest](https://rieckpil.de/guide-to-testing-spring-boot-applications-with-mockmvc/)
- [Writing Unit Tests for Spring MVC Controllers: Rendering a Single Item](https://www.petrikainulainen.net/programming/testing/writing-unit-tests-for-spring-mvc-controllers-rendering-a-single-item/)
- [Testing REST Endpoints Using MockMvc](https://medium.com/@akshatakanaje08/testing-rest-endpoints-using-mockmvc-25e9023e9db3)
- [Integration Testing in Spring](https://www.baeldung.com/integration-testing-in-spring)
