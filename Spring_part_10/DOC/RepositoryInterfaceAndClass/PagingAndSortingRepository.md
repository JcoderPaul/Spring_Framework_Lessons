### Interface PagingAndSortingRepository<T,ID>

Фрагмент репозитория, предоставляющий методы для извлечения сущностей с использованием абстракции разбиения на
страницы и сортировки. Во многих случаях это будет сочетаться с CrudRepository аналогичными методами или добавленными
вручную методами для обеспечения функциональности CRUD.

---
**Пакет:** [org.springframework.data.repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/package-summary.html)

**Суперинтерфейсы:** [Repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html)<T,ID>

**Субинтерфесы:** [ListPagingAndSortingRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/ListPagingAndSortingRepository.html)<T,ID>

**Смотрите также:** 
- [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html), 
- [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html), 
- [Page](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html), 
- [CrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)

---
```java
  @NoRepositoryBean
  public interface PagingAndSortingRepository<T,ID> extends Repository<T,ID>
```
---
#### Методы

- Page<T> findAll(Pageable pageable) - Возвращает несколько Page сущностей, удовлетворяющих ограничению подкачки,
                                       предусмотренному в Pageable объекте.

- Iterable<T> findAll(Sort sort) - Возвращает все объекты, отсортированные по заданным параметрам.

---
[См. оригинал (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html)
