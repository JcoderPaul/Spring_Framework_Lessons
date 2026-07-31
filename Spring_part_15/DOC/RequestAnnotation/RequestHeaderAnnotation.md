[См. исходную инф. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestHeader.html)

---
### Annotation Interface RequestHeader

**Пакет:** [org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html)

```java
  @Target(PARAMETER)
  @Retention(RUNTIME)
  @Documented
  public @interface RequestHeader
```

---
Аннотация, указывающая, что параметр метода должен быть привязан к заголовку веб-запроса.

Поддерживается для аннотированных методов-обработчиков в Spring MVC и Spring WebFlux.

Если параметр метода равен Map<String, String>, MultiValueMap<String, String> или, HttpHeaders то Map заполняется всеми
именами и значениями заголовков.

---
### Дополнительные элементы (но необязательные)

- `String defaultValue` - Значение по умолчанию, которое будет использоваться в качестве запасного варианта. Предоставление значения по умолчанию неявно устанавливает required()значение false;
- `String name` - Имя заголовка запроса, к которому осуществляется привязка;
- `boolean required` - Требуется ли заголовок. По умолчанию — true, что приводит к выдаче исключения, если заголовок отсутствует в запросе. Переключите это значение, в false если вы предпочитаете null значение,
если заголовок отсутствует в запросе. В качестве альтернативы укажите defaultValue(), который неявно устанавливает этот флаг в false;
- `String value` - Псевдоним для name();

---
- [См. исходную инф. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestHeader.html)
- [@RequestHeader](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/requestheader.html)
- [How to Read HTTP Headers in Spring REST Controllers](https://www.baeldung.com/spring-rest-http-headers)
- [Working With HTTP Headers in Spring MVC: A Guide to @RequestHeader and @ResponseHeader](https://www.geeksforgeeks.org/springboot/working-with-http-headers-in-spring-mvc-requestheader-and-responseheader/)
