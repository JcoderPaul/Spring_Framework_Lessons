### Annotation Interface QueryHints

Аннотация-оболочка, позволяющая QueryHint привязывать аннотации к методам. Он будет оцениваться при использовании Query
метода запроса или при получении запроса из имени метода. Если вы полагаетесь на именованные запросы, используйте способ
объявления QueryHints на основе XML или аннотаций в сочетании с фактическим объявлением именованного запроса.

---
**Пакет:** `org.springframework.data.jpa.repository`

---
```Java
  @Target({METHOD,ANNOTATION_TYPE})
  @Retention(RUNTIME)
  @Documented
  public @interface QueryHints
```

---
### Параметры

- boolean forCounting QueryHint - Определяет, следует ли применять настроенные параметры для запросов подсчета во время разбиения на страницы. По-умолчанию - true.
- jakarta.persistence.QueryHint[] value - QueryHint, который будет применяться при выполнении запроса.

---
См. оригинал (ENG): [QueryHints](https://docs.spring.io/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/QueryHints.html)
