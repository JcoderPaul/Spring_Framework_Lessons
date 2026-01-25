### Class PageRequest

Базовая реализация [Java Bean Pageable](./PageableInterface.md)

---
**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

```
    java.lang.Object
        org.springframework.data.domain.AbstractPageRequest
            org.springframework.data.domain.PageRequest
```

**All Implemented Interfaces:** 
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html);
- [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html);

---
```java
    public class PageRequest extends AbstractPageRequest
```

---
#### Конструктор

    - protected PageRequest(int pageNumber, int pageSize, Sort sort) - Создает новый объект PageRequest с примененными
                                                                       параметрами сортировки.

Где параметры:
- `pageNumber` - номер страницы, отсчитываемый от нуля, не должен быть отрицательным.
- `pageSize` - размер возвращаемой страницы должен быть больше 0.
- `sort` - не должно быть нулевым, используйте Sort.unsorted() вместо него.

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
- `PageRequest withPage(int pageNumber)` - Создает новый PageRequest с pageNumber применением.
- `PageRequest withSort(Sort sort)` - Создает новый PageRequest с Sort применением.
- `PageRequest withSort(Sort.Direction direction, String... properties)` - Создает новый PageRequest с помощью Sort.Direction и properties применяет.

---
- Методы, унаследованные от класса org.springframework.data.domain.AbstractPageRequest: getOffset, getPageNumber, getPageSize, hasPrevious, previousOrFirst;
- Методы, унаследованные от класса java.lang.Object: clone, finalize, getClass, notify, notifyAll, wait;
- Методы, унаследованные от интерфейса org.springframework.data.domain.Pageable: getSortOr, isPaged, isUnpaged, toOptional, toScrollPosition;

---
[Оригинал (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageRequest.html)
