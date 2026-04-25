- [См. исходник(ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageRequest.html)

---
### Class PageRequest

**Пакет:** [org.springframework.data.domain]()

```
java.lang.Object
    org.springframework.data.domain.AbstractPageRequest
        org.springframework.data.domain.PageRequest
```

**Все реализуемые интерфейсы:** 
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html), 
- [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html)

```java
    public class PageRequest 
            extends AbstractPageRequest
```

Базовая реализация Java Bean Pageable.

---
#### Конструктор

- `protected PageRequest(int pageNumber, int pageSize, Sort sort)` - Создает новый объект PageRequest с примененными параметрами сортировки.

---
#### Методы

- `boolean equals(Object obj)`
- `PageRequest first()` - Возвращает Pageable запрашивающую первую страницу.
- `Sort getSort()` - Возвращает параметры сортировки.
- `int hashCode()`
- `PageRequest next()` - Возвращает Pageable запрос следующего Page.
- `static PageRequest of(int pageNumber, int pageSize)` - Создает новый несортированный файл PageRequest.
- `static PageRequest of(int pageNumber, int pageSize, Sort sort)` - Создает новый объект PageRequest с примененными параметрами сортировки.
- `static PageRequest of(int pageNumber, int pageSize, Sort.Direction direction, String... properties)` - Создает новый объект PageRequest с направлением сортировки и примененными свойствами.
- `static PageRequest ofSize(int pageSize)` - Создает новую PageRequest для первой страницы (номер страницы 0), заданной pageSize.
- `PageRequest previous()` - Возвращает Pageable запрашивающий предыдущий Page.
- `String toString()`
- `PageRequest withPage(int pageNumber)` - Создает новый PageRequest с переданным pageNumber.
- `PageRequest withSort(Sort sort)` - Создает новый PageRequest с параметром Sort.
- `PageRequest withSort(Sort.Direction direction, String... properties)` - Создает новый PageRequest с помощью полученных Sort.Direction и properties.

---
- Методы, унаследованные от класса [AbstractPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/AbstractPageRequest.html#method-summary): getOffset, getPageNumber, getPageSize, hasPrevious, previousOrFirst
- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, finalize, getClass, notify, notifyAll, wait
- Методы, унаследованные от интерфейса [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html#method-summary): getSortOr, isPaged, isUnpaged, toLimit, toOptional, toScrollPosition

---
**Доп. материалы:**
- [Spring Jpa Data , Pageable , Pagerequest](https://stackoverflow.com/questions/41930627/spring-jpa-data-pageable-pagerequest)
- [Getting Started with Pagination in Spring Data](https://medium.com/@subhashyarram2/understanding-pagination-in-spring-data-794953d2bd5e)
- [Pagination and Sorting using Spring Data JPA](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
- [Pagination and Sorting with Spring Data JPA](https://www.geeksforgeeks.org/advance-java/pagination-and-sorting-with-spring-data-jpa/)
- [Paging and Sorting in Spring Data JPA](https://www.codingshuttle.com/spring-boot-handbook/paging-and-sorting-in-spring-data-jpa/)
- [Spring MVC - Pagination with Example](https://www.geeksforgeeks.org/java/spring-mvc-pagination-with-example/)
- [Spring MVC – Building Pagination and Sorting in Web Applications](https://www.geeksforgeeks.org/advance-java/spring-mvc-building-pagination-and-sorting-in-web-applications/)
- [Paging in Spring Data](https://mbukowicz.github.io/java/spring-data/2020/05/12/paging-in-spring-data.html)
- [Spring Data JPA Pagination and Sorting example](https://examples.javacodegeeks.com/spring-data-jpa-pagination-and-sorting-example/)
