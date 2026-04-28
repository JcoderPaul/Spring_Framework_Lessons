- [См. исходник (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html)

---
### Class QPageRequest

**Пакет:** [org.springframework.data.querydsl](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/package-summary.html)

```
    java.lang.Object
        org.springframework.data.domain.AbstractPageRequest
            org.springframework.data.querydsl.QPageRequest
```

**Все реализуемые интерфейсы:**
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html),
- [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html)

```java
    public class QPageRequest 
            extends AbstractPageRequest
```

Базовая реализация Java Bean [Pageable](../PageFilterSort/PageableInterface.md) с поддержкой [QueryDSL](http://querydsl.com/).

---
#### Конструкторы (устарели)

- `QPageRequest(int pageNumber, int pageSize)` - Устарело начиная с версии 2.1, используйте `of(int, int)` вместо этого.
- `QPageRequest(int pageNumber, int pageSize, com.querydsl.core.types.OrderSpecifier<?>... orderSpecifiers)` - Устарело, начиная с версии 2.1, используйте `of(int, int, OrderSpecifier...)` вместо этого.
- `QPageRequest(int pageNumber, int pageSize, QSort sort)` - Устарело, начиная с версии 2.1, используйте `of(int, int, QSort)` вместо этого.

---
#### Методы

- `Pageable first()` - Возвращает [Pageable](../PageFilterSort/PageableInterface.md) запрашивающую первую страницу.
- `Sort getSort()` - Возвращает параметры сортировки.
- `Pageable next()` - Возвращает [Pageable](../PageFilterSort/PageableInterface.md) запрос следующего [Page](../PageFilterSort/PageInterface.md).
- `static QPageRequest of(int pageNumber, int pageSize)` - Создает новый [QPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html).
- `static QPageRequest of(int pageNumber, int pageSize, com.querydsl.core.types.OrderSpecifier<?>... orderSpecifiers)` - Создает новый [QPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html) с применением заданного [OrderSpecifiers](http://querydsl.com/static/querydsl/4.4.0/apidocs/com/querydsl/core/types/OrderSpecifier.html).
- `static QPageRequest of(int pageNumber, int pageSize, QSort sort)` - Создает новый объект [QPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html) с примененными параметрами сортировки.
- `static QPageRequest ofSize(int pageSize)` - Создает новую [QPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html) для первой страницы (номер страницы 0), заданной pageSize.
- `Pageable previous()` - Возвращает [Pageable](../PageFilterSort/PageableInterface.md) запрашивающий предыдущий [Page](../PageFilterSort/PageInterface.md).
- `QPageRequest withPage(int pageNumber)` - Создает новый [QPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html) с заданным `pageNumber`.
- `QPageRequest withSort(QSort sort)` - Создает новый [QPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html) с заданным [QSort](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QSort.html).

---
- Методы, унаследованные от класса [AbstractPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/AbstractPageRequest.html#method-summary): equals, getOffset, getPageNumber, getPageSize, hashCode, hasPrevious, previousOrFirst
- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, finalize, getClass, notify, notifyAll, toString, wait
- Методы, унаследованные от интерфейса [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html#method-summary) (Постраничный): getSortOr, isPaged, isUnpaged, toLimit, toOptional, toScrollPosition

---
**Доп. материалы:**
- [QPageRequest on GitHub](https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/querydsl/QPageRequest.java)
- [Uses of Class org.springframework.data.querydsl.QPageRequest](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/querydsl/class-use/QPageRequest.html)
- [Uses of Class org.springframework.data.querydsl.QSort](https://docs.spring.io/spring-data/data-commons/docs/3.3.6/api/org/springframework/data/querydsl/class-use/QSort.html)
- [Using Spring Data JPA with QueryDsl, paginated queries break sorting?](https://stackoverflow.com/questions/27392382/using-spring-data-jpa-with-querydsl-paginated-queries-break-sorting)
- [Examples of QPageRequest](https://www.massapi.com/class/qp/QPageRequest.html)
- [How to do a paged QueryDSL query with Spring JPA?](https://stackoverflow.com/questions/11673213/how-to-do-a-paged-querydsl-query-with-spring-jpa)

---
### Пример использования

Класс QPageRequest является частью Spring Data Querydsl. Он позволяет создавать запросы пагинации, используя специфичную для Querydsl сортировку (QSort или OrderSpecifier).

---
#### Базовый пример

Начиная со Spring Data 2.1, рекомендуется использовать статический фабричный метод of() вместо конструктора.

```java
    import org.springframework.data.querydsl.QPageRequest;
    import static com.example.model.QUser.user; // Сгенерированный тип Querydsl
    
    /* Запрос страницы 0 (первая) по 10 результатов, сортировка по имени (asc) */
    QPageRequest pageRequest = QPageRequest.of(0, 10, user.name.asc());
    
    /* Передача в репозиторий с поддержкой Querydsl */
    Page<User> page = userRepository.findAll(predicate, pageRequest);
```

---
#### Варианты реализации

Вы можете настраивать сортировку разными способами:

* Простая пагинация: Используйте of(pageNumber, pageSize) для результатов без сортировки.
* Сортировка по полю: Используйте user.createdAt.desc() для сортировки по конкретным полям.
* Множественная сортировка: Передайте несколько аргументов OrderSpecifier для сортировки по нескольким полям.

```java
    /* Сортировка по нескольким полям */
    QPageRequest multiSort = QPageRequest.of(0, 20, 
        user.lastName.asc(), 
        user.firstName.asc()
    );
    
    /* Использование готового объекта QSort */
    QSort customSort = new QSort(user.email.desc());
    QPageRequest qSortRequest = QPageRequest.of(0, 50, customSort);
```

---
#### Ключевые отличия от PageRequest

В то время как обычный PageRequest использует строковые имена свойств (например, "name"), QPageRequest обеспечивает типобезопасность.

* `PageRequest`: `PageRequest.of(0, 10, Sort.by("name"))` (есть риск опечатки).
* `QPageRequest`: `QPageRequest.of(0, 10, user.name.asc())` (проверяется при компиляции).

---
**Важно:** Наш репозиторий должен расширять QuerydslPredicateExecutor<T>, чтобы эффективно использовать эти запросы вместе с предикатами.
