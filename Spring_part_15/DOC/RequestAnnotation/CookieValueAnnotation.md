- [См. исходную инф. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/CookieValue.html)

---
### Annotation Interface CookieValue

---
**Пакет:** [org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html)

```java
  @Target(PARAMETER)
  @Retention(RUNTIME)
  @Documented
  public @interface CookieValue
```

---
Аннотация, указывающая, что параметр метода привязан к файлу cookie HTTP.

Параметр метода может быть объявлен как тип Cookie или как тип значения cookie (String, int и т. д.).

Обратите внимание, что в Spring-webmvc 5.3.x и более ранних версиях значение cookie представляет собой декодированный
URL-адрес. Это будет изменено в версии 6.0, но тем временем приложения также могут объявлять параметры типа Cookie для
доступа к необработанному значению.

---
### Дополнительные элементы

- `String defaultValue` - Значение по умолчанию, которое будет использоваться в качестве запасного варианта. Предоставление значения по умолчанию неявно устанавливает required() значение - false;
- `String name` - Имя cookie для привязки;
- `boolean required` - Требуется ли cookie. По умолчанию — true, что приводит к выдаче исключения, если в запросе отсутствует cookie. Переключите это значение в false если вы предпочитаете null значение,
если значение cookie отсутствует в запросе. В качестве альтернативы укажите defaultValue(), который неявно устанавливает этот флаг в false;
- `String value` - Псевдоним для name();

---
- [См. исходную инф. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/CookieValue.html)
