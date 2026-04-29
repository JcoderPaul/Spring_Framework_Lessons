- [См. исходник (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html)

---
### Interface Slice<T>

**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

**Все супер-интерфейсы:** 
- [Iterable<T>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html);
- [Streamable<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html);
- [Supplier<Stream<T>>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html);

**Известные под-интерфейсы:** [Page<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html)

**Известные реализующие классы:** 
- [GeoPage](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/geo/GeoPage.html);
- [PageImpl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageImpl.html);
- [SliceImpl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/SliceImpl.html);

```java
  public interface Slice<T>
              extends Streamable<T>
```

Срез данных, указывающий, доступен ли следующий или предыдущий срез. Позволяет получить Pageable для запроса
предыдущего или следующего фрагмента.

---
#### Методы

- `List<T> getContent()` - Возвращает содержимое страницы в формате [List](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html).
- `int getNumber()` - Возвращает номер текущего [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).
- `int getNumberOfElements()` - Возвращает количество элементов, находящихся в данный момент в этом [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).
- `default Pageable getPageable()` - Возвращает объект [Pageable](../PageFilterSort/PageableInterface.md), который использовался для запроса текущего файла [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).
- `int getSize()` - Возвращает размер [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).
- `Sort getSort()` - Возвращает параметры сортировки для файла [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).
- `boolean hasContent()` - Возвращает, [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html) есть ли вообще контент.
- `boolean hasNext()` - Возвращает, если есть следующий [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).
- `boolean hasPrevious()` - Возвращает, если существует предыдущий [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).
- `boolean isFirst()` - Возвращает, является ли текущий [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html) первым.
- `boolean isLast()` - Возвращает, является ли текущий текущий [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html) последним.
- `<U> Slice<U> map(Function<? super T,? extends U> converter)` - Возвращает новый элемент [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html) с содержимым текущего, сопоставленным данным Converter.
- `default Pageable nextOrLastPageable()` - Возвращает [Pageable](../PageFilterSort/PageableInterface.md) описание следующего фрагмента или описание текущего фрагмента, если он последний.
- `Pageable nextPageable()` - Возвращает [Pageable](../PageFilterSort/PageableInterface.md) запрос следующего [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).
- `default Pageable previousOrFirstPageable()` - Возвращает [Pageable](../PageFilterSort/PageableInterface.md) описание предыдущего фрагмента или описание текущего фрагмента, если он первый.
- `Pageable previousPageable()` - Возвращает [Pageable](../PageFilterSort/PageableInterface.md) запрос предыдущего [Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html).

---
- Методы, унаследованные от интерфейса [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html#method-summary): forEach, iterator, spliterator
- Методы, унаследованные от интерфейса [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html#method-summary): and, filter, flatMap, get, isEmpty, stream, toList, toSet

---
**Доп. материалы:**
- [Can I use Slice with findAll method : Spring Data](https://stackoverflow.com/questions/40238918/can-i-use-slice-with-findall-method-spring-data)
- [Patterns for Iterating Over Large Result Sets With Spring Data JPA](https://www.baeldung.com/spring-data-jpa-iterate-large-result-sets)
- [Paging vs Slice: Performance and Use Cases](https://medium.com/but-it-works-on-my-machine/paging-vs-slice-performance-and-use-cases-b896680ff728)
- [Paging and Sorting](https://docs.spring.io/spring-data/rest/reference/paging-and-sorting.html)
- [Spring Test Slices on GitHub](https://github.com/marios-code-path/spring-slices-of-test)
- [Pagination and Sorting using Spring Data JPA](https://www.baeldung.com/spring-data-jpa-pagination-sorting)

---
**Немного о тестировании:**
- [Testing in Spring Boot applications](https://www.innoq.com/en/articles/2023/10/spring-boot-testing/)
- [Test Slices in Spring-Boot](https://dev.to/hamzajvm/test-slices-in-spring-boot-3l12)
- [Spring Boot Test Slices: Overview and Usage](https://www.diffblue.com/resources/spring-boot-test-slices-overview-and-usage/)

---
### Пример использования

Еще раз Slice<T> в Spring Data — это облегченная версия Page<T>, которая используется для постраничного получения данных, когда нам не нужно знать общее количество записей в базе данных.

**Главное отличие**

- **Page**: Выполняет два запроса — один за данными, второй за общим количеством элементов (COUNT). Это нужно для отображения кнопок "1, 2, 3... 100".
- **Slice**: Выполняет только один запрос. Он запрашивает на один элемент больше (limit + 1), чтобы просто понять, есть ли следующая страница. Идеально подходит для "бесконечного скролла" (Infinite Scroll).

**Пример реализации** 

- **1. Репозиторий:**
Просто указываем Slice<T> в качестве возвращаемого типа. Spring Data JPA сам поймет, что COUNT-запрос делать не нужно.

```java
@Repositorypublic interface UserRepository extends JpaRepository<User, Long> {
    // Возвращает срез данных без вычисления total elements
    Slice<User> findByLastName(String lastName, Pageable pageable);
}
```

- **2. Сервис:**
Используем PageRequest, чтобы задать номер страницы и размер.

```java
@Servicepublic class UserService {
    @Autowired
    private UserRepository userRepository;

    public void processUsers(String lastName) {
        /* Запрашиваем первую страницу (0), размер 10 */
        Slice<User> usersSlice = userRepository.findByLastName(lastName, PageRequest.of(0, 10));

        List<User> content = usersSlice.getContent(); // Сами данные
        boolean hasNext = usersSlice.hasNext();      // Есть ли следующая страница?
        
        if (hasNext) {
            /* Можно подгрузить еще */
        }
    }
}
```
---
#### Когда использовать Slice?

- **Высокая нагрузка**: COUNT на таблицах с миллионами строк может сильно тормозить базу.
- **Мобильные приложения**: Где используется бесконечная лента (scroll), а не точная навигация по номерам страниц.
- **Оптимизация**: Когда нам достаточно кнопок "Вперед" и "Назад".

**Внимание:** Если мы использум Slice, в SQL логах мы увидим запрос с LIMIT ? OFFSET ?, но без сопутствующего SELECT COUNT(*).
