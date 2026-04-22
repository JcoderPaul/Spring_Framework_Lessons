- [См. исходник (ENG)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/AbstractPageRequest.html)

---
### Class AbstractPageRequest

**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

```
java.lang.Object
    org.springframework.data.domain.AbstractPageRequest
```

**Все реализуемые интерфейсы:** 
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html);
- [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html);

**Прямые известные подклассы:** 
- [PageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageRequest.html);
- [QPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html);

```java
public abstract class AbstractPageRequest 
            extends Object 
                implements Pageable, Serializable
```

Абстрактная реализация Pageable в Java Bean.

---
#### Конструктор

- AbstractPageRequest(int pageNumber, int pageSize) - Создает новый AbstractPageRequest.

---
#### Методы

- `boolean equals(Object obj)`
- `abstract Pageable first()` - Возвращает Pageable запрашивающую первую страницу.
- `long getOffset()` - Возвращает смещение, которое будет принято в соответствии с базовой страницей и размером страницы.
- `int getPageNumber()` - Возвращает страницу, которую необходимо вернуть.
- `int getPageSize()` - Возвращает количество возвращаемых элементов.
- `int hashCode()`
- `boolean hasPrevious()` - Возвращает, есть ли предыдущая версия, Pageable к которой мы можем получить доступ из текущей.
- `abstract Pageable next()` - Возвращает Pageable запрос следующего Page.
- `abstract Pageable previous()` - Возвращает Pageable запрашивающий предыдущий Page.
- `Pageable previousOrFirst()` - Возвращает предыдущий Pageable или первый Pageable, если текущий уже является первым.

---
- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, finalize, getClass, notify, notifyAll, toString, wait
- Методы, унаследованные от интерфейса [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html#method-summary): getSort, getSortOr, isPaged, isUnpaged, toLimit, toOptional, toScrollPosition, withPage

---
**Доп. материалы:**
- [AbstractPageRequest code example (on Javatips.net)](https://www.javatips.net/api/spring-data-commons-master/src/main/java/org/springframework/data/domain/AbstractPageRequest.java)
- [AbstractPageRequest code example (on GitHub)](https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/domain/AbstractPageRequest.java)
- [The easiest way to implement Pagination in Spring Boot Application](https://elhjuojye.medium.com/the-easiest-way-to-implement-pagination-in-spring-boot-application-6d65fe6008bf)
- [Uses of Class org.springframework.data.domain.AbstractPageRequest](https://docs.spring.io/spring-data/relational/reference/data-commons/4.1/api/java/org/springframework/data/domain/class-use/AbstractPageRequest.html)
- [JPA's Pageable and PageRequest source code](https://programmersought.com/article/58318828403/)
