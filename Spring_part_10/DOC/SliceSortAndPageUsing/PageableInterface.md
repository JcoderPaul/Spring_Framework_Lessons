### Interface Pageable

Абстрактный интерфейс для информации "о страницах".

---
**Пакет:** [org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html)

**Реализующие классы:**
- [AbstractPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/AbstractPageRequest.html);
- [PageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/AbstractPageRequest.html);
- [QPageRequest](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QPageRequest.html);

```java
  public interface Pageable
```
---
### Методы

- `Pageable first()` - Возвращает `Pageable` запрашивающую первую страницу.
- `long getOffset()` - Возвращает смещение, которое будет принято в соответствии с базовой страницей и размером страницы.
- `int getPageNumber()` - Возвращает страницу, которую необходимо вернуть.
- `int getPageSize()` - Возвращает количество возвращаемых элементов.
- `Sort getSort()` - Возвращает параметры сортировки.
- `default Sort getSortOr(Sort sort)` - Возвращает текущий `Sort` или заданный, если текущий не отсортирован.
- `boolean hasPrevious()` - Возвращает, есть ли предыдущая версия, к которой `Pageable` мы можем получить доступ из текущей.
- `default boolean isPaged()` - Возвращает, содержит ли текущий `Pageable` информацию о разбивке на страницы.
- `default boolean isUnpaged()` - Возвращает, не содержит ли текущий `Pageable` информацию о нумерации страниц.
- `Pageable next()` - Возвращает `Pageable` запрос следующего `Page`.
- `static Pageable ofSize(int pageSize)` - Создает новую `Pageable` для первой страницы (номер страницы 0), заданной `pageSize`.
- `Pageable previousOrFirst()` - Возвращает предыдущий `Pageable` или первый `Pageable`, если текущий уже является первым.
- `default Optional<Pageable> toOptional()` - Возвращает объект Optional, чтобы его можно было легко сопоставить.
- `default OffsetScrollPosition toScrollPosition()` - Возвращает `OffsetScrollPosition` из этой страницы, если запрос страницы `is paged`.
- `static Pageable unpaged()` - Возвращает `Pageable` экземпляр, не представляющий настройки нумерации страниц.
- `Pageable withPage(int pageNumber)` - Создает новый `Pageable` с `pageNumber` применением.

---
[См. оригинал (ENG)](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Pageable.html)
