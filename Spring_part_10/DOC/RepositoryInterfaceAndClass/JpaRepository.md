### Interface JpaRepository<T,ID>

Специальное расширение JPA для Repository.

---
Типовые параметры:
<T> - тип домена, которым управляет репозиторий;
<ID> - тип идентификатора объекта, которым управляет репозиторий;

---
**Пакет:** [org.springframework.data.jpa.repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/package-summary.html)

**Суперинтерфейсы:** 
- CrudRepository<T,ID>,
- ListCrudRepository<T,ID>,
- ListPagingAndSortingRepository<T,ID>,
- PagingAndSortingRepository<T,ID>,
- QueryByExampleExecutor<T>,
- Repository<T,ID>

**Субинтерфейсы:** 
- EnversRevisionRepository<T,ID,N>,
- JpaRepositoryImplementation<T,ID>

**Реализующие классы:** 
- QuerydslJpaRepository,
- SimpleJpaRepository

---
  @NoRepositoryBean
  public interface JpaRepository<T,ID> extends ListCrudRepository<T,ID>,
                                               ListPagingAndSortingRepository<T,ID>,
                                               QueryByExampleExecutor<T>

---
### Методы

- `void deleteAllByIdInBatch(Iterable<ID> ids)` - Удаляет объекты, идентифицированные указанными идентификаторами, с помощью одного запроса.
- `void deleteAllInBatch()` - Удаляет все объекты в пакетном вызове.
- `void deleteAllInBatch(Iterable<T> entities)` - Удаляет заданные объекты в пакете, что означает, что будет создан один запрос.
- `default void deleteInBatch(Iterable<T> entities)` - Устарело. Использовать deleteAllInBatch(Iterable) вместо данного.
- `<S extends T> List<S> findAll(Example<S> example)`
- `<S extends T> List<S> findAll(Example<S> example, Sort sort)`
- `void flush()` - Сбрасывает все ожидающие изменения в базе данных.
- `T getById(ID id)` - Устарело. Использовать getReferenceById(ID) вместо данного.
- `T getOne(ID id)` - Устарело. Использовать getReferenceById(ID) вместо данного.
- `T getReferenceById(ID id)` - Возвращает ссылку на сущность с заданным идентификатором.
- `<S extends T> List<S> saveAllAndFlush(Iterable<S> entities)` - Сохраняет все объекты и мгновенно удаляет изменения.
- `<S extends T> S saveAndFlush(S entity) ` - Сохраняет объект и мгновенно удаляет изменения.

---
- Методы, унаследованные от интерфейса org.springframework.data.repository.CrudRepository: count, delete, deleteAll, deleteAll, deleteAllById, deleteById, existsById, findById, save
- Методы, унаследованные от интерфейса org.springframework.data.repository.ListCrudRepository: findAll, findAllById, saveAll
- Методы, унаследованные от интерфейса org.springframework.data.repository.ListPagingAndSortingRepository: findAll
- Методы, унаследованные от интерфейса org.springframework.data.repository.PagingAndSortingRepository: findAll
- Методы, унаследованные от интерфейса org.springframework.data.repository.query.QueryByExampleExecutor: count, exists, findAll, findBy, findOne

---
[Оригинал стр. см.](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
