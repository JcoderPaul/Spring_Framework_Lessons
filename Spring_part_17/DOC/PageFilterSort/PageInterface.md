- [См. исходник (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html)

---
### Interface Page<T>

**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

**Типы параметров:** T - сущность список коих будет выводиться на странице.

Все супер-интерфейсы:
- [Iterable<T>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html);
- [Slice<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html);
- [Streamable<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html);
- [Supplier<Stream<T>>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html);

Все известные реализующие классы:
- [GeoPage](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/geo/GeoPage.html);
- [PageImpl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageImpl.html);

```
  public interface Page<T> extends Slice<T>
```

Страница - это подсписок полного списка объектов. Реализация интерфейса позволяет получить информацию о положении
станицы в полном списке таковых.

---
#### Методы

- `static <T> Page<T> empty()` - Создает новый пустой Page.
- `static <T> Page<T> empty(Pageable pageable)` - Создает новый пустой объект Page для данного Pageable.
- `long getTotalElements()` - Возвращает общее количество элементов.
- `int getTotalPages()` - Возвращает общее количество страниц.
- `<U> Page<U> map(Function<? super T,? extends U> converter)` - Возвращает новый элемент Page с содержимым текущего, сопоставленным данным Function.

---
- Методы, унаследованные от интерфейса [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html#method-summary): forEach, iterator, spliterator
- Методы, унаследованные от интерфейса [Slice (срез)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html#method-summary): getContent, getNumber, getNumberOfElements, getPageable, getSize, getSort, hasContent, hasNext, hasPrevious, isFirst, isLast, nextOrLastPageable, nextPageable, previousOrFirstPageable, previousPageable
- Методы, унаследованные от интерфейса [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html#method-summary): and, filter, flatMap, get, isEmpty, stream, toList, toSet

---
**Доп.материал:**
- [Paging and Sorting](https://docs.spring.io/spring-data/rest/reference/paging-and-sorting.html)
- [Spring Data JPA Paging and Sorting Examples](https://www.codejava.net/frameworks/spring-boot/spring-data-jpa-paging-and-sorting-examples)
- [REST Pagination in Spring](https://www.baeldung.com/rest-api-pagination-in-spring)
- [Pagination Links in Spring Boot APIs That Work with Frontends](https://medium.com/@AlexanderObregon/pagination-links-in-spring-boot-apis-that-work-with-frontends-eb27b7ed1b1c)
- [Easy Pagination With Spring Data and MVC](https://codeboje.de/pagination-spring-data-mvc/)
- [Paging in Spring Data](https://mbukowicz.github.io/java/spring-data/2020/05/12/paging-in-spring-data.html)
- [Spring Boot Pagination & Filter example overview](https://www.bezkoder.com/spring-boot-pagination-filter-jpa-pageable/)
