### Annotation Interface Lock

**Аннотация, используемая для указания параметра, который LockModeType будет использоваться при выполнении запроса.** Он
будет оцениваться при использовании Query метода запроса или при получении запроса из имени метода.

---
**Пакет:** [org.springframework.data.jpa.repository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/package-summary.html)

---
```Java
  @Target({METHOD,ANNOTATION_TYPE})
  @Retention(RUNTIME)
  @Documented
  public @interface Lock
```

---
#### Параметр

- jakarta.persistence.LockModeType value - LockModeType будет использоваться при выполнении аннотированного запроса или метода CRUD.

---
- См. оригинал (ENG): [Lock](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Lock.html)
- См. about LockModeType (ENG): [LockModeType](https://docs.oracle.com/javaee/7/api/javax/persistence/LockModeType.html)
