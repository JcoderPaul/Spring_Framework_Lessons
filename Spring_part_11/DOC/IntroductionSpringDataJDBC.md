### Introduction to Spring Data JDBC - Введение в Spring Data JDBC

#### 1. Обзор

**Spring Data JDBC** — это платформа сохранения данных, которая не так сложна, как `Spring Data JPA`. Она не обеспечивает кэш,
отложенную загрузку, отложенную запись и многие другие функции JPA. Тем не менее, он имеет собственный ORM и предоставляет
большинство функций, которые мы используем с Spring Data JPA, таких как сопоставленные сущности, репозитории, аннотации
запросов и `JdbcTemplate`.

**!!! Важно помнить, что Spring Data JDBC не предлагает генерацию схемы !!!**

В результате мы несем ответственность за явное создание схемы.

---
#### 2. Добавление Spring Data JDBC в проект

Spring Data JDBC доступен для приложений Spring Boot с помощью средства запуска зависимостей JDBC. Однако этот стартер
зависимостей не включает в себя драйвер базы данных. Это решение должен принять разработчик. Давайте добавим стартер
зависимостей для Spring Data JPA (Maven):

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
```

**build.gradle**:

```
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
```

Как мы упоминали ранее, `Spring Data JDBC` не предлагает генерацию схемы. В таком случае мы можем создать собственный файл
`Schema.sql`, который будет содержать команды *SQL DDL* для создания объектов схемы. Spring Boot автоматически выберет этот
файл и будет использовать его для создания объектов базы данных. Для тестирования основного кода обычно используют H2.

---
#### 3. Добавление объектов

Как и в других проектах Spring Data, мы используем аннотации для сопоставления POJO с таблицами базы данных. В Spring
Data JDBC сущность должна иметь `@Id`. Spring Data JDBC использует аннотацию `@Id` для идентификации сущностей. Подобно
Spring Data JPA, Spring Data JDBC по умолчанию использует стратегию именования, которая сопоставляет объекты Java с
таблицами реляционной базы данных, а атрибуты — с именами столбцов. По умолчанию имена сущностей и атрибутов в 'camelCase'
регистре сопоставляются с именами таблиц и столбцов в 'snakeCase' регистре соответственно.

Например, объект Java с именем *AddressBook* сопоставляется с таблицей базы данных с именем *Address_book*.

Кроме того, мы можем явно сопоставлять сущности и атрибуты с таблицами и столбцами, используя аннотации `@Table` и `@Column`.
Например, ниже мы определили сущность, которую собираемся использовать в этом примере:

```java
public class Person {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    // constructors, getters, setters
}
```

Нам не нужно использовать аннотацию `@Table` или `@Column` в классе Person. Стратегия именования Spring Data JDBC по умолчанию
выполняет все сопоставления между сущностью и таблицей неявно.

---
#### 4. Объявление репозиториев JDBC

Spring Data JDBC использует синтаксис, аналогичный Spring Data JPA. Мы можем создать репозиторий Spring Data JDBC,
расширив интерфейс `Repository`, `CrudRepository` или `PagingAndSortingRepository`. Реализуя `CrudRepository`, мы получаем
реализацию наиболее часто используемых методов, таких как *save, delete* и *findById*, среди прочих.

Давайте создадим репозиторий JDBC, который мы будем использовать в нашем примере:

```java
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
```

Если нам нужны функции нумерации страниц и сортировки, лучшим выбором будет расширение интерфейса `PagingAndSortingRepository`.

---
#### 5. Настройка репозиториев JDBC

Несмотря на встроенные методы `CrudRepository`, нам необходимо создавать методы для конкретных случаев.

Теперь давайте настроим наш `PersonRepository` с помощью не модифицирующего запроса и модифицирующего запроса:

```java
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByFirstName(String firstName);

    @Modifying
    @Query("UPDATE person SET first_name = :name WHERE id = :id")
    boolean updateByFirstName(@Param("id") Long id, @Param("name") String name);
}
```

Начиная с версии 2.0, Spring Data JDBC поддерживает методы запросов. То есть, если мы назовем наш метод запроса, включая
ключевые слова, например, findByFirstName, Spring Data JDBC автоматически сгенерирует объект запроса.

Однако для модифицирующего запроса мы используем аннотацию `@Modifying` для аннотации метода запроса, который изменяет
объект. Также мы помечаем его аннотацией `@Query`.

Внутри аннотации `@Query` мы добавляем нашу команду SQL. В **Spring Data JDBC мы пишем запросы на простом SQL**. Мы не
используем язык запросов более высокого уровня, такой как JPQL. В результате приложение становится тесно связанным с
поставщиком базы данных.

**По этой причине также становится сложнее перейти на другую базу данных.**

**!!! Нам следует иметь в виду одну вещь: Spring Data JDBC не поддерживает ссылки на параметры с помощью индексных номеров.
Мы можем ссылаться на параметры только по имени !!!**

---
#### 6. Заполнение базы данных

Наконец, нам нужно заполнить базу данных данными, которые будут служить для тестирования репозитория Spring Data JDBC,
который в свою очередь мы создали выше. Итак, мы собираемся создать 'сеялку базы данных', которая будет вставлять
фиктивные данные в БД. Давайте добавим реализацию системы раздачи базы данных для этого примера:

```java
@Component
public class DatabaseSeeder {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insertData() {
        jdbcTemplate.execute("INSERT INTO Person(first_name,last_name) VALUES('Victor', 'Hugo')");
        jdbcTemplate.execute("INSERT INTO Person(first_name,last_name) VALUES('Dante', 'Alighieri')");
        jdbcTemplate.execute("INSERT INTO Person(first_name,last_name) VALUES('Stefan', 'Zweig')");
        jdbcTemplate.execute("INSERT INTO Person(first_name,last_name) VALUES('Oscar', 'Wilde')");
    }
}
```

Как видно выше, мы используем Spring JDBC для выполнения операторов INSERT. В частности, Spring JDBC обрабатывает
соединение с базой данных и позволяет нам выполнять команды SQL с помощью JdbcTemplates. Это решение очень гибкое,
поскольку мы имеем полный контроль над выполняемыми запросами.

---
### 7. Заключение

Подводя итог, Spring Data JDBC предлагает решение, которое так же просто, как использование Spring JPA — в этом нет
никакой магии. Тем не менее, он также предлагает большинство функций, к которым мы привыкли при использовании Spring
Data JPA.

*Одним из самых больших преимуществ Spring Data JDBC является улучшенная производительность при доступе к базе данных
по сравнению с Spring Data JPA. Это связано с тем, что Spring Data JDBC напрямую взаимодействует с базой данных.
Spring Data JDBC не содержит магической составляющей Spring Data при запросе к базе данных.*

**Одним из самых больших недостатков использования Spring Data JDBC является зависимость от поставщика базы данных.**
Если мы решим сменить базу данных с MySQL на Oracle, нам, возможно, придется столкнуться с проблемами, возникающими
из-за того, что базы данных имеют разные SQL диалекты.

---
- [См. исходную статью (ENG)](https://www.baeldung.com/spring-data-jdbc-intro)
- [См. исх. код](https://github.com/eugenp/tutorials/tree/master/persistence-modules/spring-data-jdbc)

**Похожее:**
- [GitHub введение в Spring data JDBC](https://github.com/lumberjackdev/getting-started-spring-data-jdbc)
