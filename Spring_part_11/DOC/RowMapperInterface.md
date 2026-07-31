### Interface `RowMapper<T>`

**Функциональный интерфейс**: Это функциональный интерфейс, поэтому его можно использовать в качестве цели назначения для
                          лямбда-выражения или ссылки на метод.

Интерфейс, используемый `JdbcTemplate` для сопоставления строк `ResultSet` построчно. Реализации этого интерфейса выполняют
фактическую работу по сопоставлению каждой строки с результирующим объектом, но не нужно беспокоиться об обработке
исключений.

`SQLExceptions` будет перехвачен и обработан вызывающим `JdbcTemplate`.

Обычно используется либо для `JdbcTemplate` методов запроса, либо для `out` параметров хранимых процедур. `RowMapper` объекты
обычно не имеют состояния и, следовательно, могут использоваться повторно; они являются идеальным выбором для реализации
логики сопоставления строк в одном месте.

В качестве альтернативы рассмотрите возможность создания подклассов [MappingSqlQuery](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/object/MappingSqlQuery.html) из [org.springframework.jdbc.object](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/object/package-summary.html) пакета: вместо работы с отдельными объектами [JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html) и [RowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowMapper.html) вы можете создавать исполняемые объекты запроса (содержащие логику сопоставления строк) в этом стиле.

---
**Пакет:** [org.springframework.jdbc.core](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/package-summary.html)

`Interface RowMapper<T>`

**Параметр:** `T` - результирующий тип;

**Реализующие классы:** 
- [BeanPropertyRowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/BeanPropertyRowMapper.html),
- [ColumnMapRowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/ColumnMapRowMapper.html),
- [DataClassRowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/DataClassRowMapper.html),
- [MappingSqlQueryWithParameters](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/object/MappingSqlQueryWithParameters.RowMapperImpl.html).
- [RowMapperImpl](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/object/MappingSqlQueryWithParameters.RowMapperImpl.html),
- [SingleColumnRowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/SimplePropertyRowMapper.html),
- [UpdatableSqlQuery](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/SingleColumnRowMapper.html).
- [RowMapperImpl](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/object/UpdatableSqlQuery.RowMapperImpl.html)

---
**См. также:** 
- [JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html),
- [RowCallbackHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowCallbackHandler.html),
- [ResultSetExtractor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/ResultSetExtractor.html),
- [MappingSqlQuery](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/object/MappingSqlQuery.html)

---
### Метод

- `T mapRow(ResultSet rs, int rowNum)` - Реализации должны реализовать этот метод для сопоставления каждой строки данных
                                       в файле `ResultSet`. Этот метод не должен вызывать `next()` в `ResultSet`,
                                       предполагается, что он отображает только значения текущей строки.

**Параметры:** 
- `rs` - `ResultSet` map (предварительно инициализирована для текущей строки);
- `rowNum` - номер текущей строки;

**Возвращает:** объект результата для текущей строки (может быть null);

**Исключения:** `SQLException` - если при получении значений столбца обнаружено SQLException (то есть нет необходимости перехватывать SQLException);

---
- [См. оригинал](https://docs.spring.io/spring-framework/docs/6.0.13/javadoc-api/org/springframework/jdbc/core/RowMapper.html)
