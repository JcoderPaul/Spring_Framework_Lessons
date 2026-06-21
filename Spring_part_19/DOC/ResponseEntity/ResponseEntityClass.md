- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)

---
### Class `ResponseEntity<T>`

**Пакет:** [org.springframework.http](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/package-summary.html)

```
java.lang.Object
    org.springframework.http.HttpEntity<T>
        org.springframework.http.ResponseEntity<T>
```

**Тип параметра:** T - тип возвращаемого класса

```java
    public class ResponseEntity<T> extends HttpEntity<T>
```

Расширение [HttpEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html), добавляющее код состояния [HttpStatusCode](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatusCode.html). Используется в RestTemplate, а также в методах
@Controller. В RestTemplate этот класс возвращается методами [getForEntity()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html#getForEntity(java.lang.String,java.lang.Class,java.lang.Object...)) и [exchange()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html#exchange(java.lang.String,org.springframework.http.HttpMethod,org.springframework.http.HttpEntity,java.lang.Class,java.lang.Object...)):

```java
     ResponseEntity<String> entity = template.getForEntity("https://example.com", String.class);
     String body = entity.getBody();
     MediaType contentType = entity.getHeaders().getContentType();
     HttpStatus statusCode = entity.getStatusCode();
```

Это также можно использовать в Spring MVC как возвращаемое значение метода @Controller:

```java
    @RequestMapping("/handle")
     public ResponseEntity<String> handle() {
       URI location = ...;
       HttpHeaders responseHeaders = new HttpHeaders();
       responseHeaders.setLocation(location);
       responseHeaders.set("MyResponseHeader", "MyValue");
       return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
     }
```

Или, используя сборщик, доступный через статические методы:

```java
    @RequestMapping("/handle")
     public ResponseEntity<String> handle() {
       URI location = ...;
       return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
     }
```

См. так же:
- [getStatusCode()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html#getStatusCode());

См. в [Interface RestOperations](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestOperations.html):
- [RestOperations.getForEntity(String, Class, Object...)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestOperations.html#getForEntity(java.lang.String,java.lang.Class,java.lang.Object...));
- [RestOperations.getForEntity(String, Class, java.util.Map)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestOperations.html#getForEntity(java.lang.String,java.lang.Class,java.util.Map));
- [RestOperations.getForEntity(URI, Class)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestOperations.html#getForEntity(java.net.URI,java.lang.Class));

См. так же:
- [RequestEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/RequestEntity.html)

---
#### Вложенные классы

- `static interface ResponseEntity.BodyBuilder` - Определяет построитель, который добавляет тело к сущности ответа.
- `static interface ResponseEntity.HeadersBuilder<B extends ResponseEntity.HeadersBuilder<B>>` - Определяет построитель, который добавляет заголовки к объекту ответа.

---
#### Поля

Поля, унаследованные от класса [HttpEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html) - EMPTY

---
#### Конструкторы

- `ResponseEntity(HttpStatusCode status)` - Создайте ResponseEntity только код состояния.

Где, параметры: status - код состояния;

- `ResponseEntity(MultiValueMap<String,String> headers, HttpStatusCode status)` - Создайте файл ResponseEntity с заголовками и кодом состояния.

Где, параметры: 
    - headers - заголовки объектов; 
    - status - код состояния;

- `ResponseEntity(T body, HttpStatusCode status)` - Создайте файл ResponseEntity с телом и кодом состояния.

Где, параметры: 
    - body - тело сущности @Nullable;
    - status - код состояния;

- `ResponseEntity(T body, MultiValueMap<String,String> headers, int rawStatus)` - Создайте файл ResponseEntity с телом, заголовками и необработанным кодом состояния.

Где, параметры: 
    - body - тело сущности @Nullable;
    - headers - заголовки объектов; status - код состояния;

- `ResponseEntity(T body, MultiValueMap<String,String> headers, HttpStatusCode statusCode)` - Создайте файл ResponseEntityс телом, заголовками и кодом состояния.

Где, параметры: 
    - body - тело сущности @Nullable;
    - headers - заголовки объектов;
    - statusCode - код состояния;

---
#### Методы

- `static ResponseEntity.BodyBuilder accepted()` - Создает застройщик со статусом ПРИНЯТО (ACCEPTED).
- `static ResponseEntity.BodyBuilder badRequest()` - Создает сборщик со статусом BAD_REQUEST .
- `static ResponseEntity.BodyBuilder created(URI location)` - Создает новый построитель со статусом CREATED и заголовком местоположения, установленным для данного URI.
- `boolean equals(Object other)`
- `int hashCode()`
- `String toString()`
- `HttpStatusCode getStatusCode()` - Верните код состояния HTTP ответа.
- `int getStatusCodeValue()` - **Устарело.** Начиная с версии 6.0, в пользу getStatusCode(). Планируется удалить в версии 7.0
- `static ResponseEntity.BodyBuilder internalServerError()` - Создает построитель со статусом INTERNAL_SERVER_ERROR .
- `static ResponseEntity.HeadersBuilder<?> noContent()` - Создает конструктор со статусом NO_CONTENT .
- `static ResponseEntity.HeadersBuilder<?> notFound()` - Создает построитель со статусом NOT_FOUND .
- `static <T> ResponseEntity<T> of(Optional<T> body)` - Ярлык для создания ResponseEntity с заданным телом и статусом ОК или пустым телом и статусом НЕ НАЙДЕНО (NOT_FOUND) в случае параметра Optional.empty() .
- `static ResponseEntity.HeadersBuilder<?> of(ProblemDetail body)` - Создает новый файл ResponseEntity.HeadersBuilder со статусом ProblemDetail.getStatus() и телом ProblemDetail.
- `static <T> ResponseEntity<T> ofNullable(T body)` - Ярлык для создания ResponseEntity с заданным телом и статусом ОК или пустым телом и статусом НЕ НАЙДЕНО (NOT_FOUND) в случае null параметра.
- `static ResponseEntity.BodyBuilder ok()` - Создает построитель со статусом ОК.
- `static <T> ResponseEntity<T> ok(T body)` - Ярлык для создания ResponseEntity с заданным телом и статусом OK .
- `static ResponseEntity.BodyBuilder status(int status)` - Создает застройщика с заданным статусом.
- `static ResponseEntity.BodyBuilder status(HttpStatusCode status)` - Создает застройщика с заданным статусом.
- `static ResponseEntity.BodyBuilder unprocessableEntity()` - Создает построитель со статусом UNPROCESSABLE_ENTITY .

---
Методы, унаследованные от класса [HttpEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html): getBody, getHeaders, hasBody
Методы, унаследованные от класса [Object](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html): clone, finalize, getClass, notify, notifyAll, wait

---
**См. доп.:**
- [Using Spring ResponseEntity to Manipulate the HTTP Response](https://www.baeldung.com/spring-response-entity) / [Использование Spring ResponseEntity для управления HTTP-ответом](../ResponseEntity/ResponseEntityExample.md)
- [Spring ResponseEntity best practice](https://stackoverflow.com/questions/49732262/spring-responseentity-best-practice)
- [Using ResponseEntity for Spring HTTP Responses ](https://codingnomads.com/spring-responseentity)
- [Изучаем ResponseEntity<?> и избавляемся от него в контроллерах Spring (by habr.com)](https://habr.com/ru/articles/675716/?ysclid=mqlsj66lle817340281)
- [How to Use Spring ResponseEntity to Manipulate the HTTP Response (by geeksforgeeks.org)](https://www.geeksforgeeks.org/advance-java/how-to-use-spring-responseentity-to-manipulate-the-http-response/?ysclid=mqlsjw4177443509240)
- [Mastering ResponseEntity and Controller in Spring Boot](https://dev.to/devcorner/mastering-responseentity-and-controller-in-spring-boot-agc?ysclid=mqlsjyeo6o251646097)
- [Spring Boot ResponseEntity](https://zetcode.com/springboot/responseentity/)
- [Spring Boot @ResponseStatus Annotation](https://www.javaguides.net/2019/08/spring-boot-responsestatus-annotation.html)
- [Setting Response Status with Spring MVC](https://javabyexamples.com/setting-response-status-with-spring-mvc)
