- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.HeadersBuilder.html)

---
### Interface `ResponseEntity.HeadersBuilder<B extends ResponseEntity.HeadersBuilder<B>>`

**Пакет:**  [org.springframework.http](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/package-summary.html)

**Типовые параметры:** B - подкласс строителя (Builder)

**Все известные субинтерфейсы:** [ResponseEntity.BodyBuilder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.BodyBuilder.html)

**Включающий класс:** [ResponseEntity<T>](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)

```java
  public static interface ResponseEntity.HeadersBuilder<B extends ResponseEntity.HeadersBuilder<B>>
```

Определяет построитель (builder), который добавляет заголовки к объекту ответа (response entity).

---
#### Методы:

---
- `B allow(HttpMethod... allowedMethods)` - Установливает набор разрешенных HTTP methods, как указано в Allow заголовке.

**См. так же:** [HttpHeaders.setAllow(Set)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpHeaders.html)

---
- `<T> ResponseEntity<T> build()` - Создает объект ответа без тела.

---
- `B cacheControl(CacheControl cacheControl)` - Устанавливет директивы кэширования для ресурса, как указано в Cache-Control заголовке HTTP 1.1. Экземпляр CacheControl может быть построен как CacheControl.maxAge(3600).cachePublic().noTransform().

**Параметры:** cacheControl- построитель заголовков HTTP-ответов, связанных с кешем.

---
- `B eTag(String etag)` - Устанавливает тег сущности тела, как указано в ETag заголовке.

**См. так же:** [HttpHeaders.setETag(String)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpHeaders.html)

---
- `B header(String headerName, String... headerValues)` - Добавляет заданное одно значение заголовка под заданным именем.

**Параметры:** 
- headerName - название заголовка;
- headerValues - значение(я) заголовка;

**См. так же:** [HttpHeaders.add(String, String)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpHeaders.html)

---
- `B headers(Consumer<HttpHeaders> headersConsumer)` - Управляет заголовками этого объекта с помощью данного потребителя.

Заголовки, предоставляемые потребителю, являются «живыми», поэтому потребитель может использоваться для перезаписи существующих значений заголовков, удаления значений или использования любых других HttpHeaders методов.

**Параметры:** 
- headersConsumer- функция, которая потребляет HttpHeaders;

---
- `B headers(HttpHeaders headers)` - Скопирует данные заголовки в карту заголовков объекта.

**Параметры:** 
- headers - существующие HttpHeaders для копирования;

**См. так же:** [HttpHeaders.add(String, String)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpHeaders.html)

---
- `B lastModified(long lastModified)` - Устанавливает время последнего изменения ресурса, как указано в Last-Modified заголовке. Дата должна быть указана как количество миллисекунд с 1 января 1970 г. по Гринвичу.

**Параметры:** 
- lastModified - дата последнего изменения;

**См. так же:** [HttpHeaders.setLastModified(long)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpHeaders.html)

---
- B lastModified(Instant lastModified) - Устанавливает время последнего изменения ресурса, как указано в Last-Modified заголовке.

**Параметры:**
- lastModified - дата последнего изменения;

**См. так же:** [HttpHeaders.setLastModified(Instant)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpHeaders.html)

---
- `B lastModified(ZonedDateTime lastModified)` - Устанавливает время последнего изменения ресурса, как указано в Last-Modified заголовке.

**Параметры:** 
- lastModified - дата последнего изменения;

**См. так же:** [HttpHeaders.setLastModified(ZonedDateTime)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpHeaders.html)

---
- `B location(URI location)` - Устанавливает расположение ресурса, указанное в заголовке Location.

**Параметры:** 
- location - Местоположение (локаль);

**См. так же:** [HttpHeaders.setLocation(URI)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpHeaders.html)

---
- `B varyBy(String... requestHeaders)` - Настраивает одно или несколько имен заголовков запроса (например, «Accept-Language»), чтобы добавить их к заголовку ответа «Vary», чтобы информировать клиентов о том, что ответ подлежит согласованию содержимого и отклонениям в зависимости от значения заданных заголовков запроса. Настроенные имена заголовков запроса добавляются, только если они еще не присутствуют в заголовке ответа «Vary».

**Параметры:** 
- requestHeaders - запросить имена заголовков;

---
**Доп. материал:**
- [How to Set a Header on a Response with Spring](https://www.baeldung.com/spring-response-header)
- [Add a new Header to Spring ResponseEntity](https://stackoverflow.com/questions/45792318/add-a-new-header-to-spring-responseentity)
- [ResponseEntity with builder methods](https://gist.github.com/philwebb/64260a6cc3bc813a3f57)
- [Using Spring ResponseEntity to Manipulate the HTTP Response](https://www.baeldung.com/spring-response-entity)
- [Mastering ResponseEntity and Controller in Spring Boot](https://dev.to/devcorner/mastering-responseentity-and-controller-in-spring-boot-agc)
- [Using ResponseEntity for Spring HTTP Responses](https://codingnomads.com/spring-responseentity)
- [ResponseEntity Usage Tips](https://www.springcloud.io/post/2022-02/responseentity-tips/#gsc.tab=0)
- [Introduction to Response Entity in Spring Boot](https://medium.com/@mvinodnayak46/introduction-to-responseentity-in-spring-boot-b51cf9eba597)
