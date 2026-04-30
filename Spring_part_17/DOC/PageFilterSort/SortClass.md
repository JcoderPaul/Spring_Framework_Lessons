- [См. исходник (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html)

---
### Class Sort

**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html)

```
java.lang.Object
    org.springframework.data.domain.Sort
```

**Все реализуемые интерфейсы:** 
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html),
- [Iterable<Sort.Order>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html),
- [Supplier<Stream<Sort.Order>>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html),
- [Streamable<Sort.Order>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html)

**Прямые подклассы:** 
- [QSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QSort.html),
- [RevisionSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/history/RevisionSort.html),
- [Sort.TypedSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.TypedSort.html)

```java
    public class Sort
            extends Object
                implements Streamable<Sort.Order>, Serializable
```

Возможность (опция) сортировки запросов. Вы должны предоставить как минимум список свойств для сортировки, который не
должен включать нулевые или пустые строки. По умолчанию используется направление DEFAULT_DIRECTION.

---
#### Вложенные классы

- `static enum Sort.Direction` - Перечисление направлений сортировки.
- `static enum Sort.NullHandling` - Перечисление подсказок по обработке значений NULL, которые можно использовать в [Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html) выражениях.
- `static class Sort.Order` - PropertyPath реализует объединение свойства [Sort.Direction](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Direction.html) и свойства.
- `static class Sort.TypedSort<T>` - Расширение сортировки для использования дескрипторов методов для определения свойств для сортировки.

---
#### Поля

- `static final Sort.Direction` - DEFAULT_DIRECTION

---
#### Конструкторы

`protected Sort([List](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html)<[Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html)> orders)`

---
#### Методы

- `Sort and(Sort sort)` - Возвращает новое [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html), состоящее из [Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html) текущих [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) в сочетании с заданными.
- `Sort ascending()` - Возвращает новый [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) с текущими настройками, но в возрастающем порядке.
- `static Sort by(String... properties)` - Создает новый [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) для заданных свойств (properties).
- `static Sort by(List<Sort.Order> orders)` - Создает новый [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) для данного [Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html).
- `static Sort by(Sort.Direction direction, String... properties)` - Создает новое [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) по заданным [Sort.Direction](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Direction.html) и свойствам.
- `static Sort by(Sort.Order... orders)` - Создает новый [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) для данных [Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html).
- `Sort descending()` - Возвращает новое значение [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) с текущими настройками, но в порядке убывания.
- `protected List<Sort.Order> doReverse()`
- `boolean equals(Object obj)`
- `Sort.Order getOrderFor(String property)` - Возвращает тип сортировки, зарегистрированный для данного свойства.
- `int hashCode()`
- `boolean isEmpty()` - Возвращает, является ли текущий [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html) пустой.
- `boolean isSorted()`
- `boolean isUnsorted()`
- `Iterator<Sort.Order> iterator()`
- `Sort reverse()` - Возвращает новый порядок [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) сортировки с обратной сортировкой [Sort.Order](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.Order.html), которая фактически переходит в порядок сортировки по возрастанию и наоборот.
- `static <T> Sort.TypedSort<T> sort(Class<T> type)` - Создает новый [Sort.TypedSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.TypedSort.html) для данного типа.
- `String toString()`
- `static Sort unsorted()` - Возвращает [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) экземпляры, вообще не представляющие настройки сортировки.

---
- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, finalize, getClass, notify, notifyAll, wait
- Методы, унаследованные от интерфейса [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html#method-summary): forEach, spliterator
- Методы, унаследованные от интерфейса [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html#method-summary): and, filter, flatMap, get, map, stream, toList, toSet

---
**"Доп. материалы:"**
- [Sort on GitHub](https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/domain/Sort.java)
- [Uses of Class org.springframework.data.domain.Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/class-use/Sort.html)
- [Paging and Sorting](https://docs.spring.io/spring-data/rest/reference/paging-and-sorting.html)
- [Sorting Query Results with Spring Data](https://www.baeldung.com/spring-data-sorting)
- [Implementing Pagination, Sorting, and Filtering in Spring Boot](https://medium.com/devxtalks/implementing-pagination-sorting-and-filtering-in-spring-boot-42615dbd74a7)
- [How to sort data in spring jpa?](https://stackoverflow.com/questions/61690161/how-to-sort-data-in-spring-jpa)
- [How to Sort a Spring Boot Rest API with SQL database](https://medium.com/@miguel.duque7/how-to-sort-a-spring-boot-rest-api-with-sql-database-ef8856e4f032)
- [Pagination and sorting in spring boot](https://medium.com/@ppurusottam1/pagination-and-sorting-in-spring-boot-jpa-213ef045ebf1)
- [Pagination and Sorting with Spring Data JPA](https://www.geeksforgeeks.org/advance-java/pagination-and-sorting-with-spring-data-jpa/)
- [Spring Data JPA Pagination and Sorting example](https://examples.javacodegeeks.com/spring-data-jpa-pagination-and-sorting-example/)
- [Spring Data JPA Tutorial: Sorting](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-six-sorting/)
- [Spring Reusable Pagination and Sorting](https://dev.to/raevilman/spring-reusable-pagination-and-sorting-17ki)

---
### Примеры использования

И еще раз - Класс [Sort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Sort.html) используется в [Spring Data](https://spring.io/projects/spring-data) для настройки сортировки результатов запросов к базе данных. Он позволяет указывать поле, по которому нужно сортировать, и направление (по возрастанию или убыванию).

**Пример создания объекта Sort:**

Мы можем создать простые или составные правила сортировки:
- По одному полю:
```java
    Sort sort = Sort.by("lastName").ascending();
```

- По нескольким полям:
```java
    Sort sort = Sort.by("lastName").ascending()
                    .and(Sort.by("firstName").descending());
```

- Статический метод (короткая запись):
```java
    Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
```

---
#### Использование в приложении:

Обычно Sort передается в методы репозитория, которые наследуются от JpaRepository или PagingAndSortingRepository.

- **1. Определение в репозитории:**

Метод должен принимать Sort в качестве аргумента:
```java
    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
        /* Spring автоматически подставит логику сортировки */
        List<User> findByActiveTrue(Sort sort);
    }
```

- **2. Вызов в сервисе:**
```java
    @Service
    public class UserService {
    
        @Autowired
        private UserRepository userRepository;
    
        public List<User> getSortedUsers() {
            /* Сортируем активных пользователей по фамилии (А-Я) */
            Sort sort = Sort.by("lastName").ascending();
            return userRepository.findByActiveTrue(sort);
        }
    }
```

---
**Ключевые моменты:**

- **Безопасность типов**: Названия полей передаются как строки. Если мы опечатаемся в названии поля (например, "last_name" вместо "lastName"), Spring выбросит ошибку `PropertyReferenceException`.
- **Регистронезависимость**: Можно добавить .ignoreCase(), чтобы сортировка не зависела от регистра букв.
- **Null-значения**: С помощью .nullsLast() или .nullsFirst() можно управлять тем, где будут находиться пустые значения.
- **Сочетание с пагинацией**: Если нам нужна и пагинация, и сортировка, используем `PageRequest.of(page, size, sort)`.
