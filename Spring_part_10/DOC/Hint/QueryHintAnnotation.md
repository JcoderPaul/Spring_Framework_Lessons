### Annotation Type QueryHint ******

Используется для предоставления свойства запроса или подсказки для аннотации NamedQuery или NamedNativeQuery.
Подсказки, специфичные для поставщика, которые не распознаются поставщиком, игнорируются.

---
**Пакет (библиотека):** `javax.persistence`

---
```Java
  @Target(value={})
  @Retention(value=RUNTIME)
  public @interface QueryHint
```

---
### Параметры 

- String name - Название хинта.
- String value - Описание (значение) хинта.

---
**См. оригинал (ENG):**
- [QueryHint (by hibernate.org)](https://docs.hibernate.org/jpa/2.1/api/javax/persistence/QueryHint.html);
- [QueryHint (by eclipse.org)](https://docs.oracle.com/middleware/1221/toplink/java-reference/org/eclipse/persistence/config/QueryHints.html);
- [QueryHint (by springframework.org](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/QueryHints.html);

**Статьи:**
- [Query Hints in Spring Data JPA](https://www.baeldung.com/spring-data-jpa-query-hints);
- [Spring Data JPA |Query Hints for Better Performance](https://medium.com/@javatechie/spring-data-jpa-query-hints-for-better-performance-1144b089ba03);
- [11 JPA and Hibernate query hints every developer should know](https://thorben-janssen.com/11-jpa-hibernate-query-hints-every-developer-know/);
