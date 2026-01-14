### Class QueryHints

Объединение подсказок, доступных для запросов Hibernate JPA. В основном используется для определения функций, доступных
в запросах Hibernate, которые не имеют следствия в запросах JPA.

---
**Пакет (библиотека):** `org.hibernate.annotations`

java.lang.Object
    org.hibernate.annotations.QueryHints

---
public class QueryHints extends Object

---
#### Поля

- static String CACHE_MODE - Используемый режим кэша.
- static String	CACHE_REGION - Используемая область кэша.
- static String	CACHEABLE - Кэшируются ли результаты запроса?
- static String CALLABLE - Можно ли вызвать запрос? Примечание. Действительно только для именованных нативных запросов sql.
- static String	COMMENT - Определяет комментарий, который будет применен к SQL-запросу, отправленному в базу данных.
- static String	FETCH_SIZE - Определяет размер выборки JDBC, который будет использоваться.
- static String	FETCHGRAPH - Устарело. (начиная с версии 5.4) Вместо этого используйте GraphSemantic.FETCH's GraphSemantic.getJpaHintName()
- static String	FLUSH_MODE - Режим очистки, связанный с выполнением запроса.
- static String	FOLLOW_ON_LOCKING - Подсказка по включению/отключению механизма последующей блокировки, предоставляемого Dialect.useFollowOnLocking(QueryParameters).
- static String	LOADGRAPH - Устарело. (начиная с версии 5.4) Вместо этого используйте GraphSemantic.LOAD's GraphSemantic.getJpaHintName()
- static String	NATIVE_LOCKMODE - Доступно для применения режима блокировки к собственному SQL-запросу, поскольку JPA требует, чтобы Query.setLockMode (javax.persistence.LockModeType) при вызове собственного запроса выдавалось исключение IllegalStateException.
- static String	NATIVE_SPACES - Подсказка по указанию пространств запросов, которые будут применяться к собственному (SQL) запросу.
- static String	PASS_DISTINCT_THROUGH - Подсказка по включению/отключению механизма сквозного прохода.
- static String	READ_ONLY - Должны ли объекты, возвращаемые из запроса, быть установлены в режиме только для чтения?
- static String	TIMEOUT_HIBERNATE - Примените тайм-аут запроса Hibernate, который определяется в секундах .
- static String	TIMEOUT_JAKARTA_JPA - Примените тайм-аут запроса JPA, который определяется в миллисекундах .
- static String	TIMEOUT_JPA - Примените тайм-аут запроса JPA, который определяется в миллисекундах .

---
См. оригинал (ENG): [QueryHints](https://javadoc.io/doc/org.hibernate/hibernate-core/5.5.0.Final/org/hibernate/annotations/QueryHints.html)
