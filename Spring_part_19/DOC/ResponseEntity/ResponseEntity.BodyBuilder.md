- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.BodyBuilder.html)

---
### Interface ResponseEntity.BodyBuilder

**Пакет:** [org.springframework.http](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/package-summary.html)

**Все супер-интерфейсы:** [ResponseEntity.HeadersBuilder<ResponseEntity.BodyBuilder>](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html)

**Включающий класс:** [ResponseEntity<T>](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)

```java
  public static interface ResponseEntity.BodyBuilder
                              extends ResponseEntity.HeadersBuilder<ResponseEntity.BodyBuilder>
```

Определяет построитель, который добавляет тело к сущности ответа (response entity).

---
#### Методы

- `<T> ResponseEntity<T> body(T body)` - Устанавливает тело объекта ответа и возвращает его.

- `ResponseEntity.BodyBuilder contentLength(long contentLength)` - Устанавливает длину тела в байтах, указанную в заголовке Content-Length.

- `ResponseEntity.BodyBuilder contentType(MediaType contentType)` - Устанавливает тип носителя тела, указанный в Content-Type заголовке.

---
Методы, унаследованные от интерфейса [ResponseEntity.HeadersBuilder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html): 
- [allow](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#allow(org.springframework.http.HttpMethod...)),
- [build](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#build()),
- [cacheControl](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#cacheControl(org.springframework.http.CacheControl)),
- [eTag](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#eTag(java.lang.String)),
- [header](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#header(java.lang.String,java.lang.String...)),
- [headers](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#headers(java.util.function.Consumer)),
- [headers](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#headers(org.springframework.http.HttpHeaders)),
- [lastModified](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#lastModified(long)),
- [lastModified](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#lastModified(java.time.Instant)),
- [lastModified](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#lastModified(java.time.ZonedDateTime)),
- [location](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#location(java.net.URI)),
- [varyBy](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html#varyBy(java.lang.String...))

---
**См. доп. материал:**
- [Using Spring ResponseEntity to Manipulate the HTTP Response (by baeldung.com)](https://www.baeldung.com/spring-response-entity)
- [Using Spring ResponseEntity to Manipulate the HTTP Response (by medium.com)](https://mnachit.medium.com/using-spring-responseentity-to-manipulate-the-http-response-ed7d478023e8)
- [How to Use Spring ResponseEntity to Manipulate the HTTP Response (by geeksforgeeks.org)](https://www.geeksforgeeks.org/advance-java/how-to-use-spring-responseentity-to-manipulate-the-http-response/)
- [Java Examples for org.springframework.http.ResponseEntity (by javatips.net)](https://www.javatips.net/api/org.springframework.http.responseentity)
- [Spring Boot ResponseEntity Explained and Practiced: From Structure to Business Application (by blog.stackademic.com)](https://blog.stackademic.com/spring-boot-responseentity-explained-and-practiced-from-structure-to-business-application-69aced868711)
- [Spring Response Entity - How to customize the response in Spring Boot](https://www.danvega.dev/blog/spring-response-entity)
- [Spring: Returning empty HTTP Responses with ResponseEntity<Void> doesn't work](https://stackoverflow.com/questions/26550124/spring-returning-empty-http-responses-with-responseentityvoid-doesnt-work)
