### Class NamedParameterJdbcTemplate

Класс шаблона с базовым набором операций JDBC, позволяющий использовать именованные параметры вместо традиционного
знака «?» заполнителя.

Этот класс делегирует обертку JdbcTemplate после замены именованных параметров на стиль JDBC '?' заполнители выполняются
во время выполнения. Это также позволяет расширить число List значений до соответствующего количества заполнителей.

Экземпляр этого класса шаблона после настройки становится потокобезопасным. Базовая информация [JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html) открыта для обеспечения удобного доступа к традиционным JdbcTemplate методам.

---
**Пакет:** [org.springframework.jdbc.core.namedparam](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/NamedParameterJdbcTemplate.html)

    java.lang.Object
        org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

**Реализованные интерфейсы:** [NamedParameterJdbcOperations](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/NamedParameterJdbcOperations.html)

```java
    public class NamedParameterJdbcTemplate extends Object implements NamedParameterJdbcOperations
```

---
**Так же см.:** 
- [NamedParameterJdbcOperations](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/NamedParameterJdbcOperations.html),
- [SqlParameterSource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/SqlParameterSource.html),
- [ResultSetExtractor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/ResultSetExtractor.html),
- [RowCallbackHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowCallbackHandler.html),
- [RowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowMapper.html),
- [JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)

---
#### Поля

- `static final int DEFAULT_CACHE_LIMIT` - Максимальное количество записей по умолчанию для кэша SQL этого шаблона: 256.

---
#### Конструкторы

- `NamedParameterJdbcTemplate(DataSource dataSource)` - Создает новый `NamedParameterJdbcTemplate` для данного `DataSource`.
- `NamedParameterJdbcTemplate(JdbcOperations classicJdbcTemplate)` - Создает новый `NamedParameterJdbcTemplate` для данного классического Spring JdbcTemplate.

---
#### Методы

- `int[] batchUpdate(String sql, Map<String,?>[] batchValues)` - Выполняет пакет, используя предоставленный оператор SQL с пакетом предоставленных аргументов.
- `int[] batchUpdate(String sql, SqlParameterSource[] batchArgs)` - Выполните пакет, используя предоставленный оператор SQL с пакетом предоставленных аргументов.
- `<T> T execute(String sql, Map<String,?> paramMap, PreparedStatementCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее с подготовленным оператором JDBC.
- `<T> T execute(String sql, SqlParameterSource paramSource, PreparedStatementCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее с подготовленным оператором JDBC.
- `<T> T execute(String sql, PreparedStatementCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее с подготовленным оператором JDBC.
- `int getCacheLimit()` - Возвращает максимальное количество записей для кэша SQL этого шаблона.
- `JdbcOperations getJdbcOperations()` - Предоставьте классические операции Spring JdbcTemplate, чтобы разрешить вызов менее часто используемых методов.
- `JdbcTemplate getJdbcTemplate()` - Предоставьте сам классический Spring JdbcTemplate, если он доступен, в частности, для передачи его другим JdbcTemplate потребителям.
- `protected ParsedSql getParsedSql(String sql)` - Получите проанализированное представление данного оператора SQL.
- `protected PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource)` - Постройте `PreparedStatementCreator` на основе заданного SQL и именованных параметров.
- `protected PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource, Consumer<PreparedStatementCreatorFactory> customizer)` - Постройте PreparedStatementCreator на основе заданного SQL и именованных параметров.
- `protected PreparedStatementCreatorFactory getPreparedStatementCreatorFactory(ParsedSql parsedSql, SqlParameterSource paramSource)` - Постройте `PreparedStatementCreatorFactory` на основе заданного SQL и именованных параметров.
- `<T> T query(String sql, Map<String,?> paramMap, ResultSetExtractor<T> rse)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, считывая `ResultSet` с помощью `ResultSetExtractor`.
- `void query(String sql, Map<String,?> paramMap, RowCallbackHandler rch)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, считывая `ResultSet` для каждой строки с помощью `RowCallbackHandler`.
- `<T> List<T> query(String sql, Map<String,?> paramMap, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя каждую строку с объектом Java через `RowMapper`.
- `<T> T query(String sql, SqlParameterSource paramSource, ResultSetExtractor<T> rse)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, считывая `ResultSet` с помощью `ResultSetExtractor`.
- `void query(String sql, SqlParameterSource paramSource, RowCallbackHandler rch)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, считывая `ResultSet` для каждой строки с помощью `RowCallbackHandler`.
- `<T> List<T> query(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя каждую строку с объектом Java через `RowMapper`.
- `<T> T query(String sql, ResultSetExtractor<T> rse)` - Запросить заданный SQL для создания подготовленного оператора из SQL, считывая `ResultSet` с помощью `ResultSetExtractor`.
- `void query(String sql, RowCallbackHandler rch)` - Запросите заданный SQL для создания подготовленного оператора из SQL, считывая `ResultSet` для каждой строки с помощью `RowCallbackHandler`.
- `<T> List<T> query(String sql, RowMapper<T> rowMapper)` - Запросите заданный SQL, чтобы создать подготовленный оператор из SQL, сопоставляя каждую строку с объектом Java через `RowMapper`.
- `List<Map<String,Object>> queryForList(String sql, Map<String,?> paramMap)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием списка результатов.
- `<T> List<T> queryForList(String sql, Map<String,?> paramMap, Class<T> elementType)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием списка результатов.
- `List<Map<String,Object>> queryForList(String sql, SqlParameterSource paramSource)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием списка результатов.
- `<T> List<T> queryForList(String sql, SqlParameterSource paramSource, Class<T> elementType)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием списка результатов.
- `Map<String,Object> queryForMap(String sql, Map<String,?> paramMap)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием результата Map.
- `Map<String,Object> queryForMap(String sql, SqlParameterSource paramSource)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием результата Map.
- `<T> T queryForObject(String sql, Map<String,?> paramMap, Class<T> requiredType)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, ожидающего объекта результата.
- `<T> T queryForObject(String sql, Map<String,?> paramMap, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя одну строку результата с объектом Java через `RowMapper`.
- `<T> T queryForObject(String sql, SqlParameterSource paramSource, Class<T> requiredType)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, ожидающего объекта результата.
- `<T> T queryForObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя одну строку результата с объектом Java через `RowMapper`.
- `SqlRowSet queryForRowSet(String sql, Map<String,?> paramMap)` - Запросить заданный SQL, чтобы создать подготовленный оператор из SQL и список аргументов для привязки к запросу, ожидая `SqlRowSet`.
- `SqlRowSet queryForRowSet(String sql, SqlParameterSource paramSource)` - Запросить заданный SQL, чтобы создать подготовленный оператор из SQL и список аргументов для привязки к запросу, ожидая `SqlRowSet`.
- `<T> Stream<T> queryForStream(String sql, Map<String,?> paramMap, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя каждую строку с объектом Java через RowMapper и превращая его в итерируемый и закрывающийся поток Stream.
- `<T> Stream<T> queryForStream(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя каждую строку с объектом Java через RowMapper и превращая его в итерируемый и закрывающийся поток Stream.
- `void setCacheLimit(int cacheLimit)` - Задает максимальное количество записей для кэша SQL этого шаблона.
- `int update(String sql, Map<String,?> paramMap)` - Выполняет обновление с помощью подготовленного запроса, связав заданные аргументы.
- `int update(String sql, SqlParameterSource paramSource)` - Выполняет обновление с помощью подготовленного запроса, связав заданные аргументы.
- `int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder)` - Выполняет обновление с помощью подготовленного запроса, связав заданные аргументы и вернув сгенерированные ключи.
- `int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder, String[] keyColumnNames)` - Выполняет обновление с помощью подготовленного запроса, связав заданные аргументы и вернув сгенерированные ключи.

---
Методы, унаследованные от класса [java.lang.Object](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait, wait, wait

---
[См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/NamedParameterJdbcTemplate.html)
