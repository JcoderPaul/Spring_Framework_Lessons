### Interface Page<T>

Страница — это подсписок списка объектов. Это позволяет получить информацию о положении его во всем содержащем списке.

---
**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

**Параметр:** T -

**Cуперинтерфейсы:** 
- [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html)<T>,
- [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html)<T>,
- [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html)<T>,
- [Supplier<Stream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html)<T>>

**Реализующие классы:** 
- [GeoPage](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/geo/GeoPage.html),
- [PageImpl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageImpl.html)

---
```java
  public interface Page<T> extends Slice<T>
```

---
### Методы

  - static <T> Page<T> empty() - Создает новый пустой Page.
  - static <T> Page<T> empty(Pageable pageable) - Создает новый пустой объект Pageдля данного Pageable.
  - long getTotalElements() - Возвращает общее количество элементов.
  - int getTotalPages() - Возвращает общее количество страниц.
  - <U> Page<U> map(Function<? super T,? extends U> converter) - Возвращает новый элемент Page с содержимым текущего,
                                                                 сопоставленным данным Function.

---
- Методы, унаследованные от интерфейса java.lang.Iterable: forEach, iterator, spliterator
- Методы, унаследованные от интерфейса org.springframework.data.domain.Slice: getContent, getNumber, getNumberOfElements, getPageable, getSize, getSort, hasContent, hasNext, hasPrevious, isFirst, isLast, nextOrLastPageable, nextPageable, previousOrFirstPageable, previousPageable
- Методы, унаследованные от интерфейса org.springframework.data.util.Streamable: and, filter, flatMap, get, isEmpty, stream, toList, toSet

---
[См. оригинал (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html)
