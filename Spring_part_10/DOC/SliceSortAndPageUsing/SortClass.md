### Class Sort

Возможность сортировки запросов. Вы должны предоставить как минимум список свойств для сортировки, который не должен
включать нулевые или пустые строки. По умолчанию используется направление `DEFAULT_DIRECTION`.

---
**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

```
    java.lang.Object
        org.springframework.data.domain.Sort
```

**Реализованные интерфейсы:** 
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html),
- [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html)<[Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html)>,
- [Supplier](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html)<[Stream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html)<[Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html)>>,
- [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html)<[Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html)>

**Прямые подклассы:** 
- [QSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QSort.html),
- [RevisionSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/history/RevisionSort.html),
- [Sort.TypedSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.TypedSort.html)
  
```java
public class Sort extends Object implements Streamable<Sort.Order>,
                                            Serializable
```

---
### Вложенные классы

- static enum [Sort.Direction](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Direction.html) - Перечисление направлений сортировки.
- static enum [Sort.NullHandling](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.NullHandling.html) - Перечисление подсказок по обработке значений NULL, которые можно использовать в Sort.Order выражениях.
- static class [Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html) - PropertyPath реализует объединение свойства Sort.Direction и свойства.
- static class [Sort.TypedSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.TypedSort.html)<T> - Расширение сортировки для использования дескрипторов методов для определения свойств для сортировки.

---
### Поля

- static final [Sort.Direction](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Direction.html) - [DEFAULT_DIRECTION](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html#DEFAULT_DIRECTION)

---
### Конструктор

- protected [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html#%3Cinit%3E(java.util.List))([List](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html)<[Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html)> orders)

---
### Методы

- `Sort and(Sort sort)` - Возвращает новое Sort, состоящее из Sort.Order текущих Sort в сочетании с заданными.
- `Sort ascending()` - Возвращает новый Sort с текущими настройками, но в возрастающем порядке.
- `static Sort by(String... properties)` - Создает новый Sort для заданных свойств.
- `static Sort by(List<Sort.Order> orders)` - Создает новый Sort для данного Sort.Orders.
- `static Sort by(Sort.Direction direction, String... properties)` - Создает новое Sort по заданным Sort.Direction свойствам.
- `static Sort by(Sort.Order... orders)` - Создает новый Sort для данного Sort.Orders.
- `Sort descending()` - Возвращает новое значение Sort с текущими настройками, но в порядке убывания.
- `protected List<Sort.Order> doReverse()`
- `boolean equals(Object obj)`
- `Sort.Order getOrderFor(String property)` - Возвращает заказ, зарегистрированный для данного свойства.
- `int hashCode()`
- `boolean isEmpty()` - Возвращает, является ли текущий Streamable пустой.
- `boolean isSorted()`
- `boolean isUnsorted()`
- `Iterator<Sort.Order> iterator()`
- `Sort reverse()` - Возвращает новый порядок Sort сортировки с обратной сортировкой Sort.Order, которая фактически переходит в порядок сортировки по возрастанию и наоборот.
- `static <T> Sort.TypedSort<T> sort(Class<T> type)` - Создает новый Sort.TypedSort для данного типа.
- `String toString()` -
- `static Sort unsorted()` - Возвращает Sort экземпляры, вообще не представляющие настройки сортировки.

---
- Методы, унаследованные от класса java.lang.Object: clone, finalize, getClass, notify, notifyAll, wait, wait, wait
- Методы, унаследованные от интерфейса java.lang.Iterable: forEach, spliterator
- Методы, унаследованные от интерфейса org.springframework.data.util.Streamable: and, and, and, and, filter, flatMap, get, map, stream, toList, toSet

---
[См. оригинал (ENG)](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Sort.html)
