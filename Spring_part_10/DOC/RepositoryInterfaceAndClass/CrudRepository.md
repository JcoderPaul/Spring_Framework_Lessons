### Interface CrudRepository<T,ID>

Интерфейс для общих операций CRUD в репозитории определенного типа.

---
**Пакет:** [org.springframework.data.repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/package-summary.html)

**Суперинтерфейсы:** [Repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html)<T,ID>

**Субинтерфейсы:** [ListCrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/ListCrudRepository.html)<T,ID>

---
  @NoRepositoryBean
  public interface CrudRepository<T,ID> extends Repository<T,ID>

---
### Методы

  - `long count()` - Возвращает количество доступных объектов.
  - `void delete(T entity)` - Удаляет заданный объект.
  - `void deleteAll()` - Удаляет все объекты, управляемые репозиторием.
  - `void deleteAll(Iterable<? extends T> entities)` - Удаляет указанные объекты.
  - `void deleteAllById(Iterable<? extends ID> ids)` - Удаляет все экземпляры типа Tс указанными идентификаторами.
  - `void deleteById(ID id)` - Удаляет объект с заданным идентификатором.
  - `boolean existsById(ID id)` - Возвращает, существует ли сущность с данным идентификатором.
  - `Iterable<T> findAll()` - Возвращает все экземпляры типа.
  - `Iterable<T> findAllById(Iterable<ID> ids)` - Возвращает все экземпляры типа Tс заданными идентификаторами.
  - `Optional<T> findById(ID id)` - Получает объект по его идентификатору.
  - `<S extends T> S save(S entity)` - Сохраняет заданный объект.
  - `<S extends T> Iterable<S> saveAll(Iterable<S> entities)` - Сохраняет все заданные объекты.

---
- [Оригинал (ENG)](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)
