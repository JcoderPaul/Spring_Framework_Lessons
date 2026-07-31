- [См. исходник (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html)

---
### Interface Pageable

**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

**Известные реализующие классы:**
- [AbstractPageRequest](../PageFilterSort/AbstractPageRequestClass.md);
- [PageRequest](../PageFilterSort/PageRequestClass.md);
- [QPageRequest](../PageFilterSort/QPageRequestClass.md);

```java
public interface Pageable
```

Абстрактный интерфейс для работы с информацией о страницах.

---
#### Методы

- `Pageable first()` - Возвращает [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) запрашивающую первую страницу.
- `long getOffset()` - Возвращает смещение, которое будет принято в соответствии с базовой страницей и размером страницы.
- `int getPageNumber()` - Возвращает страницу, которую необходимо вернуть (получить).
- `int getPageSize()` - Возвращает количество возвращаемых элементов на страницу.
- `Sort getSort()` - Возвращает параметры сортировки.
- `default Sort getSortOr(Sort sort)` - Возвращает текущий [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) или заданный, если текущий не отсортирован.
- `boolean hasPrevious()` - Возвращает, есть ли предыдущая версия, [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) у которой мы можем получить доступ из текущей.
- `default boolean isPaged()` - Возвращает, содержит ли текущий [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) информацию о разбивке на страницы.
- `default boolean isUnpaged()` - Возвращает, не содержит ли текущий [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) информацию о нумерации страниц.
- `Pageable next()` - Возвращает [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) запрос следующего [Page](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html).
- `static Pageable ofSize(int pageSize)` - Создает новую Pageable для первой страницы (номер страницы 0), заданной в pageSize.
- `Pageable previousOrFirst()` - Возвращает предыдущий [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) или первый Pageable, если текущий уже является первым.
- `default Limit toLimit()` - Возвращает [Limit](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Limit.html) из этой страницы, если [запрос страницы выгружается](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html#isPaged()), или [Limit.unlimited()](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Limit.html#unlimited()) в противном случае.
- `default Optional<Pageable> toOptional()` - Возвращает объект [Optional](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Optional.html), чтобы его можно было легко сопоставить.
- `default OffsetScrollPosition toScrollPosition()` - Возвращает [OffsetScrollPosition](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/OffsetScrollPosition.html) из этой страницы, если запрос страницы [is paged, т.е. возвращает](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html#isPaged()), содержит ли текущий [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) информацию о разбивке на страницы.
- `static Pageable unpaged()` - Возвращает [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) экземпляр, не представляющий настройки нумерации страниц.
- `static Pageable unpaged(Sort sort)` - Возвращает Pageable экземпляр, представляющий отсутствие настройки нумерации страниц и имеющий определенный [результат order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html).
- `Pageable withPage(int pageNumber)` - Создает новый Pageable с заданным pageNumber.

---
**Доп. материалы:**
- [Paging and Sorting](https://docs.spring.io/spring-data/rest/reference/paging-and-sorting.html)
- [Pagination and Sorting with Spring Data JPA](https://www.geeksforgeeks.org/advance-java/pagination-and-sorting-with-spring-data-jpa/)
- [Spring MVC - Pagination with Example](https://www.geeksforgeeks.org/java/spring-mvc-pagination-with-example/)
- [Spring MVC – Building Pagination and Sorting in Web Applications](https://www.geeksforgeeks.org/advance-java/spring-mvc-building-pagination-and-sorting-in-web-applications/)
- [Spring - REST Pagination](https://www.geeksforgeeks.org/java/spring-rest-pagination/)
- [Implement Pagination in your Spring Boot application](https://ardijorganxhi.medium.com/implement-pagination-at-your-spring-boot-application-a540270b5f60)
- [Spring Data JPA Paging and Sorting example](https://mkyong.com/spring-boot/spring-data-jpa-paging-and-sorting-example/)
- [GitHub spring-pageable-example](https://github.com/brunbs/spring-pageable-example/tree/main)
- [Get All Results at Once in a Spring Boot Paged Query Method](https://www.baeldung.com/spring-boot-paged-query-all-results)
- [A Comprehensive Guide to Pagination and Sorting in Spring Boot Applications](https://blog.stackademic.com/a-comprehensive-guide-to-pagination-and-sorting-in-spring-boot-applications-ec4311576f94)
- [Paging and Sorting in Spring Data JPA](https://www.codingshuttle.com/spring-boot-handbook/paging-and-sorting-in-spring-data-jpa/)
- [Implementing Pagination in Spring Boot: A Complete Guide](https://medium.com/@gururajnhegde07/implementing-pagination-in-spring-boot-a-complete-guide-783c9e3894a5)
