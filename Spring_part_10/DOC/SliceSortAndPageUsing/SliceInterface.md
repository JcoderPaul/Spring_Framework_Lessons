### Interface Slice<T>

Срез данных, указывающий, доступен ли следующий или предыдущий срез. Позволяет получить Pageable запрос предыдущего или
следующего Slice.

---
**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

**Суперинтерфейсы:** 
- [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html)<T>,
- [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html)<T>,
- [Supplier](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html)<[Stream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html)<T>>

**Субинтерфейс:** [Page](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html)<T>

**Реализующие классы:** 
- [GeoPage](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/geo/GeoPage.html),
- [PageImpl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageImpl.html),
- [SliceImpl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/SliceImpl.html)
  
---
```Java
  public interface Slice<T> extends Streamable<T>
```

---
### Методы

- `List<T> getContent()` - Возвращает содержимое страницы в формате List.
- `int getNumber()` - Возвращает номер текущего Slice.
- `int getNumberOfElements()` - Возвращает количество элементов, находящихся в данный момент в этом Slice.
- `default Pageable getPageable()` - Возвращает объект Pageable, который использовался для запроса текущего файла Slice.
- `int getSize()` - Возвращает размер Slice.
- `Sort getSort()` - Возвращает параметры сортировки для файла Slice.
- `boolean hasContent()` - Возвращает, Slice есть ли вообще контент.
- `boolean hasNext()` - Возвращает, если есть следующий Slice.
- `boolean hasPrevious()` - Возвращает, если существует предыдущий Slice.
- `boolean isFirst()` - Возвращает, является ли текущий Slice первым.
- `boolean isLast()` - Возвращает, является ли текущий текущий Slice последним.
- `<U> Slice<U> map(Function<? super T,? extends U> converter)` - Возвращает новый элемент Slice с содержимым текущего, сопоставленным данным Converter.
- `default Pageable nextOrLastPageable()` - Возвращает Pageable описание следующего фрагмента или описание текущего фрагмента, если он последний.
- `Pageable nextPageable()` - Возвращает Pageable запрос следующего Slice.
- `default Pageable previousOrFirstPageable()` - Возвращает Pageable описание предыдущего фрагмента или описание текущего фрагмента, если он первый.
- `Pageable previousPageable()` - Возвращает Pageable запрос предыдущего Slice.

---
- Методы, унаследованные от интерфейса `java.lang.Iterable`: forEach, iterator, spliterator;
- Методы, унаследованные от интерфейса `org.springframework.data.util.Streamable`: and, filter, flatMap, get, isEmpty, stream, toList, toSet

---
[Оригинал см. (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html)
