### Interface Repository<T,ID>

Интерфейс маркер центрального репозитория. Захватывает тип домена, которым нужно управлять, а также тип идентификатора
типа домена. Общая цель — хранить информацию о типе, а также иметь возможность обнаруживать интерфейсы, расширяющие
этот интерфейс, во время сканирования пути к классам для упрощения создания bean-компонентов Spring.

Репозитории домена, расширяющие этот интерфейс, могут выборочно предоставлять методы CRUD, просто объявляя методы с той
же сигнатурой, что и те, которые объявлены в CrudRepository.

---
**Пакет:** [org.springframework.data.repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/package-summary.html)

**Тип параметров:** 
- *T* - тип домена, которым управляет репозиторий. По факту тип сущности которой происходит управление.
- *ID* - тип идентификатора объекта, которым управляет репозиторий.

**Субинтерфейсы:** 
- [CrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)<T,ID>,
- [ListCrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/ListCrudRepository.html)<T,ID>,
- [ListPagingAndSortingRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/ListPagingAndSortingRepository.html)<T,ID>,
- [PagingAndSortingRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html)<T,ID>,
- [ReactiveCrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/reactive/ReactiveCrudRepository.html)<T,ID>,
- [ReactiveSortingRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/reactive/ReactiveSortingRepository.html)<T,ID>,
- [RevisionRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/history/RevisionRepository.html)<T,ID,N>,
- [RxJava3CrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/reactive/RxJava3CrudRepository.html)<T,ID>,
- [RxJava3SortingRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/reactive/RxJava3SortingRepository.html)<T,ID>

**Смотреть также:** [CrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)

---
```java
  @Indexed
  public interface Repository<T,ID>
```

---
[Оригинал (ENG)](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/Repository.html)
