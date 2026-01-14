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
