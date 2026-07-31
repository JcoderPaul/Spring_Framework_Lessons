- [См. источник (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html)

---
### Interface PagingAndSortingRepository

```
Interface PagingAndSortingRepository<T,ID>
```

**Пакет:** [org.springframework.data.repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/package-summary.html)

**Все супер-интерфейсы:** [Repository<T,ID>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html)

**Все суб-интерфейсы:** [ListPagingAndSortingRepository<T,ID>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/ListPagingAndSortingRepository.html)

```java
  @NoRepositoryBean
  public interface PagingAndSortingRepository<T,ID>
                                  extends Repository<T,ID>
```

Фрагмент репозитория, предоставляющий методы для извлечения сущностей с использованием абстракции разбиения на страницы
и сортировки. Во многих случаях это будет сочетаться с CrudRepository или аналогичным или с добавленными вручную
методами для обеспечения функциональности CRUD.

---
#### Методы

- `Page<T> findAll(Pageable pageable)` - Возвращает несколько [Page](../PageFilterSort/PageInterface.md) сущностей, удовлетворяющих ограничению подкачки, предусмотренному в [Pageable](../PageFilterSort/PageableInterface.md) объекте, где объект Pageable - реализует абстрактный интерфейс для работы с информацией о страницах.
- `Iterable<T> findAll(Sort sort)` - Возвращает все объекты, отсортированные по заданным параметрам, где объект [Sort](../PageFilterSort/SortClass.md) - определяет возможность сортировки запросов.

---
**См. так же:** 
- [Interface Page (RUS)](../PageFilterSort/PageInterface.md);
- [Interface Slice (RUS)](../PageFilterSort/SliceInterface.md);
- [Interface Pageable (RUS)](../PageFilterSort/PageableInterface.md);
- [Class PageImpl (RUS)](../PageFilterSort/PageImplClass.md);
- [Class PageRequest (RUS)](../PageFilterSort/PageRequestClass.md);
- [Class Sort (RUS)](../PageFilterSort/SortClass.md);
- [Interface CrudRepository (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html);

---
**Доп. материал:**
- [PagingAndSortingRepository on GitHub](https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/repository/PagingAndSortingRepository.java)
- [CrudRepository, JpaRepository, and PagingAndSortingRepository in Spring Data](https://www.geeksforgeeks.org/advance-java/crudrepository-jparepository-and-pagingandsortingrepository-in-spring-data/)
- [Pagination and Sorting with Spring Data JPA](https://www.geeksforgeeks.org/advance-java/pagination-and-sorting-with-spring-data-jpa/)
- [Pagination and Sorting using Spring Data JPA](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
- [Spring Boot Pagination and Sorting Example](https://howtodoinjava.com/spring-data/pagination-sorting-example/)
- [Spring Data JPA Paging and Sorting example](https://mkyong.com/spring-boot/spring-data-jpa-paging-and-sorting-example/)
- [Pagination in Spring Data Repositories](https://medium.com/@swapnildalimbkar01/pagination-in-spring-data-repositories-23da6d3a22ad)
- [CrudRepository, JpaRepository, and PagingAndSortingRepository in Spring Data](https://www.baeldung.com/spring-data-repositories)
- [(YouTube) Spring Boot | Pagination & Sorting | Introduction to PagingAndSortingRepository](https://www.youtube.com/watch?v=ePANG4iWvfI)
- [(YouTube) JpaRepository vs CrudRepository: Spring Boot Pagination & Sorting Explained](https://www.youtube.com/watch?v=6HRu8vBFX74)
- [(YouTube) Chapter-4 | Pagination and Sorting | Spring Data JPA | Spring Boot | 2023](https://www.youtube.com/watch?v=rous9SCn_MA)
- [Spring Data Repositories Guide: Overview and Setup](https://medium.com/@cat.edelveis/spring-data-repositories-guide-overview-and-setup-93246df6f834)
- [Implementation of PagingAndSortingRepository using JdbcTemplate](https://gist.github.com/GDownes/4aef8427bfde72335bb4f0e7f72960a6)
- [Working with Spring Data Repositories](https://docs.spring.io/spring-data/jpa/docs/1.6.4.RELEASE/reference/html/repositories.html)
- [Spring Data JPA Pagination](https://www.danvega.dev/blog/spring-data-jpa-pagination)
