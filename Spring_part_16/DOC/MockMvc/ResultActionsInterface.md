- [См. исходник(ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultActions.html)

---
### Interface ResultActions

**Пакет:** [org.springframework.test.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/package-summary.html)

```java
public interface ResultActions
```

Позволяет применять действия, например ожидания, к результату выполненного запроса.

См. статические фабричные методы:
- [MockMvcResultMatchers](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultMatchers.html)
- [MockMvcResultHandlers](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultHandlers.html)

---
#### Методы

---
- `ResultActions andDo(ResultHandler handler)` - Выполните общее действие.

**Пример:**

```java
 static imports: MockMvcRequestBuilders.*, MockMvcResultMatchers.*

 mockMvc.perform(get("/form")).andDo(print());
```

**Исключения:** [Exception](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Exception.html)

---
- `ResultActions andExpect(ResultMatcher matcher)` - Оправдать ожидание.

Вы можете вызывать `andExpect()` несколько раз, как показано в примере ниже:

```java
 static imports: MockMvcRequestBuilders.*, MockMvcResultMatchers.*

 mockMvc.perform(get("/person/1"))
   .andExpect(status.isOk())
   .andExpect(content().mimeType(MediaType.APPLICATION_JSON))
   .andExpect(jsonPath("$.person.name").equalTo("Jason"));

 mockMvc.perform(post("/form"))
   .andExpect(status.isOk())
   .andExpect(redirectedUrl("/person/1"))
   .andExpect(model().size(1))
   .andExpect(model().attributeExists("person"))
   .andExpect(flash().attributeCount(1))
   .andExpect(flash().attribute("message", "success!"));
```

- **Исключения:** [Exception](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Exception.html)
- **См. так же:** [andExpectAll(ResultMatcher...)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultActions.html#andExpectAll(org.springframework.test.web.servlet.ResultMatcher...))

---
- `default ResultActions andExpectAll(ResultMatcher... matchers)` - Выполняйте несколько ожиданий с гарантией того, что все ожидания оправдаются, даже если одно или несколько ожиданий с исключением окажутся неудачными.

Если выброшен одиночный Error или Exception, он будет выброшен повторно. Если выдается несколько исключений, этот метод выдаст AssertionError сообщение об ошибке, представляющее собой сводку всех исключений. Кроме того, каждое исключение будет добавлено как подавленное исключение в файл AssertionError.

Эта функция аналогична поддержке SoftAssertions в AssertJ и assertAll() поддержке в JUnit Jupiter.

Вместо andExpect() многократного вызова вы можете вызвать andExpectAll(), как показано в следующем примере:

```java
 static imports: MockMvcRequestBuilders.*, MockMvcResultMatchers.*

 mockMvc.perform(get("/person/1"))
   .andExpectAll(status().isOk(),
       content().contentType(MediaType.APPLICATION_JSON),
       jsonPath("$.person.name").value("Jason")
   );
```

- **Исключения:** [Exception](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Exception.html)
- **См. так же:** [andExpect(ResultMatcher)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultActions.html#andExpect(org.springframework.test.web.servlet.ResultMatcher))

---
- `MvcResult andReturn()` - Вернуть результат выполненного запроса для прямого доступа к результатам.

---
**Доп.материал:**
- [ResultActions](https://github.com/spring-projects/spring-framework/blob/main/spring-test/src/main/java/org/springframework/test/web/servlet/ResultActions.java)
- [Uses of Interface org.springframework.test.web.servlet.ResultActions](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/class-use/ResultActions.html)
- [How to extract explicit content from org.springframework.test.web.servlet.ResultActions](https://stackoverflow.com/questions/34479906/how-to-extract-explicit-content-from-org-springframework-test-web-servlet-result)
- [Concurrency Control In REST API With Spring Framework](https://dzone.com/articles/concurrency-control-in-rest-api-with-spring-framew)
- [Java Examples for org.springframework.test.web.servlet.ResultActions](https://www.javatips.net/api/org.springframework.test.web.servlet.resultactions)
- [Spring Boot Unit Testing CRUD REST API with JUnit and Mocki](https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html)
- [Spring Boot Integration Testing MySQL CRUD REST API Tutorial](https://www.springcloud.io/post/2022-03/spring-boot-integration-testing-mysql-crud-rest-api-tutorial/#gsc.tab=0)
- [Integration Testing in Spring](https://www.baeldung.com/integration-testing-in-spring)
- [Mock testing of Spring MVC interfaces](https://www.springcloud.io/post/2022-02/mockmvc/#gsc.tab=0)
- [Writing Tests for Spring MVC Controllers: Test Case 101](https://www.petrikainulainen.net/programming/testing/writing-tests-for-spring-mvc-controllers-test-case-101/)
- [Testing a Spring Boot RESTful Service](https://medium.com/@tbrouwer/testing-a-spring-boot-restful-service-c61b981cac61)
