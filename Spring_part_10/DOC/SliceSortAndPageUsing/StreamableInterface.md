### Interface Streamable<T>

Это функциональный интерфейс, поэтому его можно использовать в качестве цели назначения для лямбда-выражения или
ссылки на метод. Простой интерфейс для облегчения потоковой передачи Iterables.

---
**Пакет:** [org.springframework.data.util](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/package-summary.html)

**Суперинтерфейсы:**
- [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html)<T>,
- [Supplier](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html)<[Stream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html)<T>>

**Субинтерфейсы:** 
- [Page](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html)<T>,
- [PersistentPropertyPath](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/mapping/PersistentPropertyPath.html)<P>,
- [PersistentPropertyPaths](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/mapping/PersistentPropertyPaths.html)<T,P>,
- [PropertyPath](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/core/PropertyPath.html)
- [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html)<T>,
- [Window](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Window.html)<T>

**Реализующие классы:** 
- [DefaultParameters](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/DefaultParameters.html),
- [EntityProjection](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/projection/EntityProjection.html),
- [EntityProjection.ContainerPropertyProjection](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/projection/EntityProjection.ContainerPropertyProjection.html),
- [EntityProjection.PropertyProjection](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/projection/EntityProjection.PropertyProjection.html),
- [ExpressionDependencies](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/spel/ExpressionDependencies.html),
- [GeoPage](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/geo/GeoPage.html),
- [PageImpl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageImpl.html),
- [Parameters](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/Parameters.html),
- [PartTree](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/parser/PartTree.html),
- [PartTree.OrPart](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/parser/PartTree.OrPart.html),
- [PersistentEntities](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/mapping/context/PersistentEntities.html),
- [QSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QSort.html),
- [RepositoryComposition.RepositoryFragments](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/core/support/RepositoryComposition.RepositoryFragments.html),
- [Revisions](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/history/Revisions.html),
- [RevisionSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/history/RevisionSort.html),
- [SliceImpl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/SliceImpl.html),
- [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html),
- [Sort.TypedSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.TypedSort.html)

---
```java
  @FunctionalInterface
  public interface Streamable<T> extends Iterable<T>, Supplier<Stream<T>>
```

---
### Методы

- `default Streamable<T> and(Iterable<? extends T> iterable)` - Создает новый `Streamable` из текущего и объединенного данного `Iterable`.
- `default Streamable<T> and(Supplier<? extends Stream<? extends T>> stream)` - Создает новый `Streamable` из текущего и объединенного данного `Stream`.
- `default Streamable<T> and(Streamable<? extends T> streamable)` - Удобный метод, позволяющий добавлять в `Streamable` напрямую, поскольку в противном случае вызов между `and(Iterable)` и `and(Supplier)`.
- `default Streamable<T> and(T... others)` - Создает новый `Streamable` из текущего и объединенных заданных значений.
- `static <T> Streamable<T> empty()` - Возвращает пустой `Streamable`.
- `default Streamable<T> filter(Predicate<? super T> predicate)` - Возвращает новый фильтр `Streamable`, который применит данный фильтр `Predicate` к текущему.
- `default <R> Streamable<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)` - Возвращает новое значение `Streamable`, которое будет применять данное значение `Function` к текущему.
- `default Stream<T> get()`
- `default boolean isEmpty()` - Возвращает, является ли текущий `Streamable` пустой.
- `default <R> Streamable<R> map(Function<? super T,? extends R> mapper)` - Возвращает новое значение `Streamable`, которое будет применять данное значение `Function` к текущему.
- `static <T> Streamable<T> of(Iterable<T> iterable)` - Возвращает `Streamable` для данного `Iterable`.
- `static <T> Streamable<T> of(Supplier<? extends Stream<T>> supplier)`
- `static <T> Streamable<T> of(T... t)` - Возвращает `Streamable` с заданными элементами.
- `default Stream<T> stream()` - Создает непараллельный `Stream` базовый файл `Iterable`.
- `default List<T> toList()` - Создает новый неизменяемый файл `List`.
- `default Set<T> toSet()` - Создает новый неизменяемый файл `Set`.
- `static <S> Collector<S,?,Streamable<S>> toStreamable()` - Коллектор, который можно легко изготовить `Streamable` из готового изделия `Stream`, используя `Collectors.toList()` в качестве промежуточного коллектора.
- `static <S,T extends Iterable<S>> Collector<S,?,Streamable<S>> toStreamable(Collector<S,?,T> intermediate)` - Коллектор, позволяющий легко изготовить `Streamable` из `Stream` и заданный промежуточный коллектор.

---
Методы, унаследованные от интерфейса java.lang.Iterable: forEach, iterator, spliterator;

---
[Оригинал см. (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html)
