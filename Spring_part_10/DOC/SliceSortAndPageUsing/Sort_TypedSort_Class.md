### Class Sort.TypedSort<T>

Расширение сортировки для использования дескрипторов методов для определения свойств для сортировки.

---
**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

```
    java.lang.Object
        org.springframework.data.domain.Sort
            org.springframework.data.domain.Sort.TypedSort<T>
```

**Реализованные интерфейсы:** 
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html),
- [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html)<[Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html)>,
- [Supplier](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html)<[Stream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html)<[Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html)>>,
- [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html)<[Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html)>

**Включающий класс:** 
- [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html)

---
```java
    public static class Sort.TypedSort<T> extends Sort
```

---
Вложенные классы унаследованные от класса [org.springframework.data.domain.Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html):
- [Sort.Direction](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Direction.html),
- [Sort.NullHandling](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.NullHandling.html),
- [Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html),
- [Sort.TypedSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.TypedSort.html)<T>

---
Поля, унаследованные от класса [org.springframework.data.domain.Sort: DEFAULT_DIRECTION](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html#DEFAULT_DIRECTION)

---
### Методы

- `Sort ascending()` - Возвращает новый Sort с текущими настройками, но в возрастающем порядке.

- `<S> Sort.TypedSort<S> by(Function<T,S> property)`

- `<S> Sort.TypedSort<S> by(MethodInvocationRecorder.Recorded.ToCollectionConverter<T,S> collectionProperty)`

- `<S> Sort.TypedSort<S> by(MethodInvocationRecorder.Recorded.ToMapConverter<T,S> mapProperty)`

- `Sort descending()` - Возвращает новое значение `Sort` с текущими настройками, но в порядке убывания.

- `boolean isEmpty()` - Возвращает, является ли текущий `Streamable` пустой.

- `Iterator<Sort.Order> iterator()`

- `String toString()`

---
- Методы, унаследованные от класса org.springframework.data.domain.Sort: and, by, doReverse, equals, getOrderFor, hashCode, isSorted, isUnsorted, reverse, sort, unsorted;
- Методы, унаследованные от класса java.lang.Object: clone, finalize, getClass, notify, notifyAll, wait;
- Методы, унаследованные от интерфейса java.lang.Iterable: forEach, spliterator;
- Методы, унаследованные от интерфейса org.springframework.data.util.Streamable: and, filter, flatMap, get, map, stream, toList, toSet;

---
[Оригинал (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.TypedSort.html)
