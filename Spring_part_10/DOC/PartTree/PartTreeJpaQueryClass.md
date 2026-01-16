### Class PartTreeJpaQuery

Реализация AbstractJpaQuery, основанная на PartTree.

---
**Пакет:** [org.springframework.data.jpa.repository.query](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/query/package-summary.html)

---
    java.lang.Object
        org.springframework.data.jpa.repository.query.AbstractJpaQuery
            org.springframework.data.jpa.repository.query.PartTreeJpaQuery

---
**Реализованные интерфейсы:** [RepositoryQuery](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/RepositoryQuery.html)

---
    public class PartTreeJpaQuery extends AbstractJpaQuery

---
### Методы

- jakarta.persistence.TypedQuery<Long> doCreateCountQuery(JpaParametersParameterAccessor accessor) - Создает объект
                                                         TypedQuery для подсчета с использованием заданных значений.

- jakarta.persistence.Query doCreateQuery(JpaParametersParameterAccessor accessor) - Создает Query экземпляр для
                                                                                     заданных значений.

- protected JpaQueryExecution getExecution()

---
- Методы, унаследованные от класса org.springframework.data.jpa.repository.query.AbstractJpaQuery: applyHints, applyQueryHint, createBinder, createCountQuery, createQuery, execute, getEntityManager, getMetamodel,
getQueryMethod, getTypeToRead
- Методы, унаследованные от класса java.lang.Object: clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait, wait, wait

---
Оригинал (ENG): [PartTreeJpaQuery](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/query/PartTreeJpaQuery.html)

---
Статьи по теме:
- [JPA Query Methods](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html);
- [Spring Data JPA @Query](https://www.baeldung.com/spring-data-jpa-query);
- [Spring Data JPA @Query Annotation with Example](https://www.geeksforgeeks.org/java/spring-data-jpa-query-annotation-with-example/);
