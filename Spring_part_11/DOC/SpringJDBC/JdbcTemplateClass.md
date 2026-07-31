### Class JdbcTemplate

Это центральный класс основного пакета JDBC. Он упрощает использование JDBC и помогает избежать распространенных ошибок.
Он выполняет основной рабочий процесс JDBC, оставляя код приложения для предоставления SQL и извлечения результатов.
Этот класс выполняет SQL-запросы или обновления, инициируя итерацию по наборам результатов, перехватывая исключения JDBC
и переводя их в общую [org.springframework.dao](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/dao/package-summary.html) иерархию исключений.

Коду, использующему этот класс, достаточно реализовать интерфейсы обратного вызова (callback), предоставив им четко
определенный контракт. Интерфейс [PreparedStatementCreator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/PreparedStatementCreator.html) обратного вызова создает подготовленный оператор с учетом соединения, предоставляя SQL и все необходимые параметры. Интерфейс [ResultSetExtractor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/ResultSetExtractor.html) извлекает значения из [ResultSet](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/ResultSet.html).

См. также [PreparedStatementSetter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/PreparedStatementSetter.html) и [RowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowMapper.html) два популярных альтернативных интерфейса обратного вызова (callback).

Экземпляр этого класса шаблона после настройки становится потокобезопасным. Может использоваться в реализации службы
путем прямого создания экземпляра со ссылкой на источник данных или быть подготовленным в контексте приложения и
передаваться службам в качестве ссылки на компонент.

**!!! Примечание !!! Источник данных всегда следует настраивать как компонент в контексте приложения, в первом случае
                   передавая непосредственно сервису, во втором случае — подготовленному шаблону.**

Поскольку этот класс параметризуется интерфейсами обратного вызова и [SQLExceptionTranslator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/support/SQLExceptionTranslator.html) интерфейсом, нет необходимости создавать его подклассы.

Все операции SQL, выполняемые этим классом, регистрируются на уровне отладки с использованием [org.springframework.jdbc.core.JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html) в качестве категории журнала.

---
Пакет: [org.springframework.jdbc.core](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/package-summary.html)

---
  java.lang.Object
      org.springframework.jdbc.support.JdbcAccessor
          org.springframework.jdbc.core.JdbcTemplate

**Реализованные интерфейсы:** 
- [InitializingBean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/InitializingBean.html),
- [JdbcOperations](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcOperations.html)

---
```java
  public class JdbcTemplate extends JdbcAccessor implements JdbcOperations
```

---
См. также: 
- [JdbcOperations](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcOperations.html),
- [PreparedStatementCreator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/PreparedStatementCreator.html),
- [PreparedStatementSetter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/PreparedStatementSetter.html),
- [CallableStatementCreator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/CallableStatementCreator.html),
- [PreparedStatementCallback](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/PreparedStatementCallback.html),
- [CallableStatementCallback](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/CallableStatementCallback.html),
- [ResultSetExtractor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/ResultSetExtractor.html),
- [RowCallbackHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowCallbackHandler.html),
- [RowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowMapper.html),
- [SQLExceptionTranslator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/support/SQLExceptionTranslator.html),
- [NamedParameterJdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/NamedParameterJdbcTemplate.html)

---
### Конструкторы

- `JdbcTemplate()` - Создает новый JdbcTemplate для использования компонента.
- `JdbcTemplate(DataSource dataSource)` - Создает новый JdbcTemplate с учетом источника данных для получения соединений.
- `JdbcTemplate(DataSource dataSource, boolean lazyInit)` - Создает новый JdbcTemplate с учетом источника данных для получения соединений.

---
### Методы

- `protected void applyStatementSettings(Statement stmt)` - Подготовьте данный оператор JDBC (или `ReadableStatement` или `CallableStatement`), применив такие параметры оператора, как                                      размер выборки, максимальное количество строк и время ожидания запроса.
- `int[] batchUpdate(String... sql)` - Выполните несколько обновлений SQL для одного оператора JDBC, используя пакетную обработку.
- `<T> int[][] batchUpdate(String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss)` - Выполнить несколько пакетов, используя предоставленный оператор SQL со сбором предоставленных аргументов.
- `int[] batchUpdate(String sql, List<Object[]> batchArgs)` - Выполните пакет, используя предоставленный оператор SQL с пакетом предоставленных аргументов.
- `int[] batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes)` - Выполните пакет, используя предоставленный оператор SQL с пакетом предоставленных аргументов.
- `int[] batchUpdate(String sql, BatchPreparedStatementSetter pss)` - Выполните несколько операторов обновления для одного `ReadupStatement`, используя пакетные обновления и `BatchPreparedStatementSetter` для установки значений.
- `Map<String,Object> call(CallableStatementCreator csc, List<SqlParameter> declaredParameters)` - Выполните вызов SQL, используя `CallableStatementCreator`, чтобы предоставить SQL и все необходимые параметры.
- `protected Connection createConnectionProxy(Connection con)` - Создайте прокси-сервер с подавлением закрытия для данного соединения JDBC.
- `protected Map<String,Object> createResultsMap()` - Создайте экземпляр карты, который будет использоваться в качестве карты результатов.
- `void execute(String sql)` - Выполните одно выполнение SQL, обычно это оператор DDL.
- `<T> T execute(String callString, CallableStatementCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее с `JDBC CallableStatement`.
- `<T> T execute(String sql, PreparedStatementCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее с подготовленным оператором JDBC.
- `<T> T execute(CallableStatementCreator csc, CallableStatementCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее с `JDBC CallableStatement`.
- `<T> T execute(ConnectionCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее над соединением JDBC.
- `<T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее с подготовленным оператором JDBC.
- `<T> T execute(StatementCallback<T> action)` - Выполнить операцию доступа к данным JDBC, реализованную как действие обратного вызова, работающее с оператором JDBC.
- `protected Map<String,Object> extractOutputParameters(CallableStatement cs, List<SqlParameter> parameters)` - Извлеките выходные параметры из завершенной хранимой процедуры.
- `protected Map<String,Object> extractReturnedResults(CallableStatement cs, List<SqlParameter> updateCountParameters, List<SqlParameter> resultSetParameters, int updateCount` - Извлеките возвращенные наборы результатов из завершенной хранимой процедуры.
- `protected RowMapper<Map<String,Object>> getColumnMapRowMapper()` - Создайте новый RowMapper для чтения столбцов как пар ключ-значение.
- `int getFetchSize()` - Верните размер выборки, указанный для этого `JdbcTemplate`.
- `int getMaxRows()` - Возвращает максимальное количество строк, указанное для этого `JdbcTemplate`.
- `int getQueryTimeout()` - Возвращает время ожидания запроса для операторов, которые выполняет этот `JdbcTemplate`.
- `protected <T> RowMapper<T> getSingleColumnRowMapper(Class<T> requiredType)` - Создайте новый `RowMapper` для чтения объектов результатов из одного столбца.
- `protected void handleWarnings(SQLWarning warning)` - Выдайте, `SQLWarningException` если столкнулись с реальным предупреждением.
- `protected void handleWarnings(Statement stmt)` - Обработать предупреждения для данного оператора JDBC, если таковые имеются.
- `protected void handleWarnings(Statement stmt, SQLException ex)` - Обрабатывайте предупреждения перед распространением первичного объекта `SQLException` от выполнения данного оператора.
- `boolean isIgnoreWarnings()` - Возвращает, игнорируем ли мы `SQLWarnings`.
- `boolean isResultsMapCaseInsensitive()` - Укажите, будет ли выполнение `CallableStatement` возвращать результаты в Map, который использует имена без учета регистра для параметров.
- `boolean isSkipResultsProcessing()` - Возвращает значение, следует ли пропустить обработку результатов.
- `boolean isSkipUndeclaredResults()` - Возвращает значение, следует ли пропускать необъявленные результаты.
- `protected PreparedStatementSetter newArgPreparedStatementSetter(Object[] args)` - Создайте новый `AcceptedStatementSetter` на основе аргументов, используя переданные аргументы.
- `protected PreparedStatementSetter newArgTypePreparedStatementSetter(Object[] args, int[] argTypes)` - Создайте новый объект `ReadyStatementSetter` на основе типа аргумента, используя переданные аргументы и типы.
- `protected Map<String,Object> processResultSet(ResultSet rs, ResultSetSupportingSqlParameter param)` - Обработать данный `ResultSet` из хранимой процедуры.
- `<T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, считывая `ResultSet` с помощью `ResultSetExtractor`.
- `void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, считывая ResultSet для каждой строки с помощью `RowCallbackHandler`.
- `<T> List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя каждую строку с объектом результата через `RowMapper`.

---
- `<T> T query(String sql, Object[] args, ResultSetExtractor<T> rse)` - Устарело.
- `void query(String sql, Object[] args, RowCallbackHandler rch)` - Устарело.
- `<T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper)` - Устарело.

---
- `<T> T query(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse)` - Запрос с использованием подготовленного оператора, чтение `ResultSet` с помощью `ResultSetExtractor`.
- `void query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch)` - Запросить заданный SQL для создания подготовленного оператора из SQL и реализации `ReadedStatementSetter`, которая знает, как привязывать значения к запросу, считывая `ResultSet` для каждой строки с помощью `RowCallbackHandler`.
- `<T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и реализации ReadedStatementSetter, которая знает, как привязывать значения к запросу, сопоставляя каждую строку с объектом результата через `RowMapper`.
- `<T> T query(String sql, ResultSetExtractor<T> rse)` - Выполните запрос с использованием статического SQL, прочитав `ResultSet` с помощью `ResultSetExtractor`.
- `<T> T query(String sql, ResultSetExtractor<T> rse, Object... args)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, считывая `ResultSet` с помощью `ResultSetExtractor`.
- `void query(String sql, RowCallbackHandler rch)` - Выполните запрос с использованием статического SQL, считывая `ResultSet` для каждой строки с помощью `RowCallbackHandler`.
- `void query(String sql, RowCallbackHandler rch, Object... args)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, считывая `ResultSet` для каждой строки с помощью `RowCallbackHandler`.
- `<T> List<T> query(String sql, RowMapper<T> rowMapper)` - Выполните запрос с использованием статического SQL, сопоставив каждую строку с объектом результата с помощью `RowMapper`.
- `<T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя каждую строку с объектом результата через `RowMapper`.
- `<T> T query(PreparedStatementCreator psc, PreparedStatementSetter pss, ResultSetExtractor<T> rse)` - Запрос с использованием подготовленного оператора, позволяющего использовать `ReadedStatementCreator` и `ReaderStatementSetter`.
- `<T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse)` - Запрос с использованием подготовленного оператора, чтение `ResultSet` с помощью `ResultSetExtractor`.
- `void query(PreparedStatementCreator psc, RowCallbackHandler rch)` - Запрос с использованием подготовленного оператора, считывающий `ResultSet` для каждой строки с помощью `RowCallbackHandler`.
- `<T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper)` - Запрос с использованием подготовленного оператора, сопоставляющий каждую строку с объектом результата с помощью `RowMapper`.
- `List<Map<String,Object>> queryForList(String sql)` - Выполните запрос списка результатов, учитывая статический SQL.
- `<T> List<T> queryForList(String sql, Class<T> elementType)` - Выполните запрос списка результатов, учитывая статический SQL.
- `<T> List<T> queryForList(String sql, Class<T> elementType, Object... args)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием списка результатов.
- `List<Map<String,Object>> queryForList(String sql, Object... args)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием списка результатов.
- `List<Map<String,Object>> queryForList(String sql, Object[] args, int[] argTypes)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием списка результатов.
- `<T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием списка результатов.
- `<T> List<T> queryForList(String sql, Object[] args, Class<T> elementType)` - Устарело.
- `Map<String,Object> queryForMap(String sql)` - Выполните запрос для карты результатов, учитывая статический SQL.
- `Map<String,Object> queryForMap(String sql, Object... args)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием карты результатов.
- `Map<String,Object> queryForMap(String sql, Object[] args, int[] argTypes)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу с ожиданием карты результатов.
- `<T> T queryForObject(String sql, Class<T> requiredType)` - Выполнить запрос к объекту результата, учитывая статический SQL.
- `<T> T queryForObject(String sql, Class<T> requiredType, Object... args)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, ожидающего объекта результата.
- `<T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)` - Запрос данного SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, ожидающего объекта результата.
- `<T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя одну строку результата с объектом результата через RowMapper.
- `<T> T queryForObject(String sql, Object[] args, Class<T> requiredType)` - Устарело.
- `<T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper)` - Устарело.
- `<T> T queryForObject(String sql, RowMapper<T> rowMapper)` - Выполните запрос с использованием статического SQL, сопоставив одну строку результата с объектом результата с помощью `RowMapper`.
- `<T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя одну строку результата с объектом результата через RowMapper.
- `SqlRowSet queryForRowSet(String sql)` - Выполните запрос для `SqlRowSet`, учитывая статический SQL.
- `SqlRowSet queryForRowSet(String sql, Object... args)` - Запросить заданный SQL, чтобы создать подготовленный оператор из SQL и список аргументов для привязки к запросу, ожидая `SqlRowSet`.
- `SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes)` - Запросить заданный SQL, чтобы создать подготовленный оператор из SQL и список аргументов для привязки к запросу, ожидая `SqlRowSet`.
- `<T> Stream<T> queryForStream(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper)` - Выполните запрос SQL для создания подготовленного оператора из SQL и реализации `ReadedStatementSetter`, которая знает, как привязывать значения к запросу, сопоставляя каждую строку с объектом результата через RowMapper и превращая его в итерируемый и закрывающийся поток `Stream`.
- `<T> Stream<T> queryForStream(String sql, RowMapper<T> rowMapper)` - Выполните запрос с использованием статического SQL, сопоставив каждую строку с объектом результата с помощью `RowMapper` и превратив его в итерируемый и закрывающийся поток.
- `<T> Stream<T> queryForStream(String sql, RowMapper<T> rowMapper, Object... args)` - Запросить заданный SQL для создания подготовленного оператора из SQL и списка аргументов для привязки к запросу, сопоставляя каждую строку с объектом результата через RowMapper и превращая его в итерируемый и закрывающийся поток `Stream`.
- `<T> Stream<T> queryForStream(PreparedStatementCreator psc, PreparedStatementSetter pss, RowMapper<T> rowMapper)` - Запрос с использованием подготовленного оператора, позволяющего использовать `ReadedStatementCreator` и `ReaderStatementSetter`.
- `<T> Stream<T> queryForStream(PreparedStatementCreator psc, RowMapper<T> rowMapper)` - Запрос с использованием подготовленного оператора, сопоставление каждой строки с объектом результата с помощью `RowMapper` и превращение его в итерируемый и закрывающийся поток Stream.
- `void setFetchSize(int fetchSize)` - Установите размер выборки для этого `JdbcTemplate`.
- `void setIgnoreWarnings(boolean ignoreWarnings)` - Установите, хотим ли мы игнорировать предупреждения операторов JDBC (SQLWarning).
- `void setMaxRows(int maxRows)` - Установите максимальное количество строк для этого `JdbcTemplate`.
- `void setQueryTimeout(int queryTimeout)` - Установите время ожидания запроса для операторов, которые выполняет этот `JdbcTemplate`.
- `void setResultsMapCaseInsensitive(boolean resultsMapCaseInsensitive)` - Укажите, будет ли выполнение `CallableStatement` возвращать результаты в Map, в котором для параметров используются имена без учета регистра.
- `void setSkipResultsProcessing(boolean skipResultsProcessing)` - Установите, следует ли пропускать обработку результатов.
- `void setSkipUndeclaredResults(boolean skipUndeclaredResults)` - Установите, следует ли пропускать необъявленные результаты.
- `protected DataAccessException translateException(String task, String sql, SQLException ex)` - Переведите данное `SQLException` в общий вид `DataAccessException`.
- `int update(String sql)` - Выполните одну операцию обновления SQL (например, оператор вставки, обновления или удаления).
- `int update(String sql, Object... args)` - Выполните одну операцию обновления SQL (например, оператор вставки, обновления или удаления) через подготовленный оператор, связывая заданные аргументы.
- `int update(String sql, Object[] args, int[] argTypes)` - Выполните одну операцию обновления SQL (например, оператор вставки, обновления или удаления) через подготовленный оператор, связывая заданные аргументы.
- `int update(String sql, PreparedStatementSetter pss)` - Выполните оператор обновления, используя `ReadedStatementSetter`, чтобы установить параметры привязки с заданным SQL.
- `int update(PreparedStatementCreator psc)` - Выполните одну операцию обновления SQL (например, оператор вставки, обновления или удаления) с помощью `ReaderStatementCreator`, чтобы предоставить SQL и все необходимые параметры.
- `protected int update(PreparedStatementCreator psc, PreparedStatementSetter pss)`
- `int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder)` - Выполните оператор обновления, используя ReadedStatementCreator, чтобы предоставить SQL и все необходимые параметры.

---
Методы, унаследованные от класса [org.springframework.jdbc.support.JdbcAccessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/support/JdbcAccessor.html): afterPropertiesSet, getDataSource, getExceptionTranslator, isLazyInit, obtainDataSource, setDatabaseProductName, setDataSource, setExceptionTranslator, setLazyInit

Методы, унаследованные от класса [java.lang.Object](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait, wait, wait

---
[См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/6.0.13/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)
