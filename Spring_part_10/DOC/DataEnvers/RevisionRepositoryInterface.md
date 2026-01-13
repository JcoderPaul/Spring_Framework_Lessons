### Interface RevisionRepository

**Interface RevisionRepository<T,ID,N extends Number & Comparable<N>>**

Репозиторий, который может получить доступ к объектам, хранящимся в различных файлах Revisions.

---
**Пакет:** [org.springframework.data.repository.history](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/history/package-summary.html)

---
**Суперинтерфейсы:** [Repository<T,ID>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html)

---
```Java
  @NoRepositoryBean
  public interface RevisionRepository<T,ID,N extends Number & Comparable<N>> extends Repository<T,ID>
```

---
### Методы

- Optional<Revision<N,T>> findLastChangeRevision(ID id) - Возвращает ревизию объекта, в котором он был изменен в последний раз, id - не должно быть нулевым.

- Optional<Revision<N,T>> findRevision(ID id, N revisionNumber) - Возвращает объект с заданным идентификатором в заданном номере версии. Обратите внимание: не гарантируется, что реализации будут поддерживать
сортировку по всем свойствам.

Параметры:
- *id* - не должно быть нулевым.
- *revisionNumber* - не должно быть нулевым.

Возвращает: объекта Revision с данным идентификатором в данном номере версии.

- Revisions<N,T> findRevisions(ID id) - Возвращает всю Revisions сущность с заданным идентификатором.

- Page<Revision<N,T>> findRevisions(ID id, Pageable pageable) - Возвращает количество Page ревизий объекта с заданным идентификатором.

Параметры: 
- *id* - не должно быть нулевым.
- *pageable* - страница для запроса постраничного результата может быть Pageable.unpaged(), не должна быть нулевой.

---
См. офф. док.: [RevisionRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/history/RevisionRepository.html)
