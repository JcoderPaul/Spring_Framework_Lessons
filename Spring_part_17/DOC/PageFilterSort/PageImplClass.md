- [См. исходник (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageImpl.html)

---
### Class PageImpl<T>

**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

```
java.lang.Object
    org.springframework.data.domain.PageImpl<T>
```

**Тип параметра:** T - тип, из которого состоит страница.

**Все реализуемые интерфейсы:** 
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html),
- [Iterable<T>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html),
- [Supplier<Stream<T>>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html),
- [Page<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html),
- [Slice<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html),
- [Streamable<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html)

**Прямой подкласс:** GeoPage

```java
public class PageImpl<T>
            extends Object
                implements Page<T>
```

Базовая реализация Page.

---
#### Конструктор

- `PageImpl(List<T> content)` - Создает новый PageImpl с заданным содержимым.
- `PageImpl(List<T> content, Pageable pageable, long total)` - Конструктор PageImpl.

---
#### Методы

- `boolean equals(Object obj)`
- `List<T> getContent()` - Возвращает содержимое страницы в формате List.
- `protected <U> List<U> getConvertedContent(Function<? super T,? extends U> converter)` - Применяет данное значение Function к содержимому файла Chunk.
- `int getNumber()` - Возвращает номер текущего Slice.
- `int getNumberOfElements()` - Возвращает количество элементов, находящихся в данный момент в этом Slice.
- `Pageable getPageable()` - Возвращает объект Pageable, который использовался для запроса текущего файла Slice.
- `int getSize()` - Возвращает размер Slice.
- `Sort getSort()` - Возвращает параметры сортировки для файла Slice.
- `long getTotalElements()` - Возвращает общее количество элементов.
- `int getTotalPages()` - Возвращает общее количество страниц.
- `boolean hasContent()` - Возвращает, Slice есть ли вообще контент.
- `int hashCode()`
- `boolean hasNext()` - Возвращает, если есть следующий Slice.
- `boolean hasPrevious()` - Возвращает, если существует предыдущий Slice.
- `boolean isFirst()` - Возвращает, является ли текущий Slice первым.
- `boolean isLast()` - Возвращает, является ли текущий текущий Slice последним.
- `Iterator<T> iterator()`
- `<U> Page<U> map(Function<? super T,? extends U> converter)` - Возвращает новый элемент Page с содержимым текущего, сопоставленным данным Function.
- `Pageable nextPageable()` - Возвращает Pageable запрос следующего Slice.
- `Pageable previousPageable()` - Возвращает Pageable запрос предыдущего Slice.
- `String toString()`

---
- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, finalize, getClass, notify, notifyAll, wait
- Методы, унаследованные от интерфейса [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html#method-summary): forEach, iterator, spliterator
- Методы, унаследованные от интерфейса [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html#method-summary): getContent, getNumber, getNumberOfElements, getPageable, getSize, getSort, hasContent, hasPrevious, isFirst, nextOrLastPageable, nextPageable, previousOrFirstPageable, previousPageable
- Методы, унаследованные от интерфейса [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html#method-summary): and, filter, flatMap, get, isEmpty, stream, toList, toSet

---
**Доп. материал:**
- [PageImpl on GitHub](https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/domain/PageImpl.java)
- [Java Examples for org.springframework.data.domain.PageImpl](https://www.javatips.net/api/org.springframework.data.domain.pageimpl)
- [Пагинация. Нестандартное использование Spring’овых Page и Pageable](https://habr.com/ru/companies/beeline_cloud/articles/795723/)
- [Converting List to Page Using Spring Data JPA](https://www.baeldung.com/spring-data-jpa-convert-list-page)
- [PageImpl JSON serialization with Spring Boot 2](https://www.wimdeblauwe.com/blog/2018/2018-06-10-pageimpl-json-serialization-with-spring-boot-2/)
- [Implement Pagination in your Spring Boot application](https://ardijorganxhi.medium.com/implement-pagination-at-your-spring-boot-application-a540270b5f60)
- [Spring Data JPA Paging and Sorting example](https://mkyong.com/spring-boot/spring-data-jpa-paging-and-sorting-example/)
- [Pagination in Spring Webflux and Spring Data Reactive](https://www.baeldung.com/spring-data-webflux-pagination)
- [How to set TotalPages of a PageImpl class in spring boot](https://stackoverflow.com/questions/68412308/how-to-set-totalpages-of-a-pageimpl-class-in-spring-boot)
- [How to return paginated data in Spring Boot](https://dev.to/brunbs/how-to-return-paginated-data-in-spring-boot-11cm)
- [Spring Data JPA Tutorial: Pagination](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-seven-pagination/)
