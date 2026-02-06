- [Исходник всего материала (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-creating-database-queries-with-the-query-annotation/)
- [Код на GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Creating Database Queries With the @Query Annotation

Создание запросов к базе данных с помощью аннотации `@Query`

Хотя стратегия создания методов запроса к БД имеет свои преимущества, она также имеет и свои слабые стороны. Тут мы
рассмотрим, как можно избежать некоторых недостатков, используя аннотацию `@Query`.

Мы можем настроить вызываемый запрос к базе данных, аннотируя метод запроса аннотацией `@Query`. Он поддерживает запросы
`JPQL` и `SQL`, а запрос, указанный с помощью аннотации `@Query`, предшествует всем другим стратегиям создания запросов.

Другими словами, если мы создадим метод запроса с именем `findbyId()` и аннотируем его аннотацией `@Query`, `Spring Data JPA`
не будет (обязательно) находить объект, свойство ID которого равно заданному параметру метода. Он вызывает запрос,
настроенный с помощью аннотации @Query.

Предположим, что интерфейс нашего репозитория выглядит следующим образом:

```java
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface TodoRepository extends Repository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.title = 'title'")
    public List<Todo> findById();
}
```

Несмотря на то, что метод `findById()` следует соглашению об именах, которое используется для создания запросов к базе
данных (см. [предыдущий раздел](./6_QueriesFromMethodNames.md) на основе имени метода запроса, метод `findById()`
возвращает записи задач с заголовком 'title', поскольку это запрос помечем с помощью аннотации @Query.

---
**Дополнительное чтение:**
- [Javadoc аннотации @Query](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Query.html)
- [Справочное руководство Spring Data JPA: 4.3 Использование @Query](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.query-methods.at-query)
- [Поддержка SpEL в определениях Spring Data JPA @Query](https://spring.io/blog/2014/07/15/spel-support-in-spring-data-jpa-query-definitions)

---
Давайте выясним, как мы можем создавать запросы `JPQL` и `SQL` с помощью аннотации `@Query`.

---
### Создание запросов JPQL

Мы можем создать запрос JPQL с аннотацией @Query, выполнив следующие действия:
- Добавим метод запроса в интерфейс нашего репозитория.
- Добавим к методу запроса аннотацию @Query и укажем вызываемый запрос, установим его в качестве значения аннотации @Query.

Исходный код интерфейса нашего репозитория выглядит следующим образом:

```java
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface TodoRepository extends Repository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.title = 'title'")
    public List<Todo> findByTitle();
}
```

---
**Дополнительное чтение:**
- [Учебное пособие по Java EE 7: Язык запросов сохраняемости Java](https://docs.oracle.com/javaee/7/tutorial/persistence-querylanguage.htm#BNBTG)

---
### Создание SQL-запросов

Мы можем создать SQL-запрос с аннотацией `@Query`, выполнив следующие действия:
- Добавим метод запроса в интерфейс нашего репозитория.
- Добавим к методу запроса аннотацию `@Query` и укажем вызываемый запрос, установим его в качестве значения атрибута `value` аннотации `@Query`.
- Установим для атрибута `NativeQuery` аннотации `@Query` значение **true**.

Исходный код интерфейса нашего репозитория выглядит следующим образом:

```java
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface TodoRepository extends Repository<Todo, Long> {

    @Query(value = "SELECT * FROM todos t WHERE t.title = 'title'",
           nativeQuery=true
    )
    public List<Todo> findByTitle();
}
```

---
**Дополнительное чтение:**
- [Руководство для начинающих по SQL](https://blog.udemy.com/what-is-sql/)
- [W3schools.com: Учебник по SQL](https://www.w3schools.com/sql/)


Давайте продолжим и выясним, как создать метод запроса, отвечающий требованиям нашей функции поиска.

---
### Реализация функции поиска

В этом разделе описывается, как реализовать функцию поиска без учета регистра, которая возвращает все записи задач,
заголовок или описание которых содержит заданный поисковый запрос.

---
#### Реализация функции поиска с помощью JPQL

Мы можем создать запрос JPQL, отвечающий требованиям нашей функции поиска, выполнив следующие шаги:
- Шаг 1. - Создаем метод запроса, который возвращает список объектов `Todo`.
- Шаг 2. - Аннотируем метод аннотацией `@Query`.
- Шаг 3. - Создаем *запрос JPQL*, который использует именованные параметры и возвращает записи задач, заголовок или
           описание которых содержит заданный поисковый запрос (не забываем игнорировать регистр). Устанавливаем
           созданный запрос как значение аннотации `@Query`.
- Шаг 4. - Добавим один параметр в метод запроса и настроим имя именованного параметра, добавив к параметру метода
           аннотацию `@Param`.

Исходный код интерфейса нашего репозитория выглядит следующим образом:

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    List<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm);
}
```

---
**Дополнительное чтение:**
- [CONCAT - конкатенация строк](https://www.objectdb.com/java/jpa/query/jpql/string#CONCAT_-_String_Concatenation_)
- [LOWER и UPPER - изменение регистра строк](https://www.objectdb.com/java/jpa/query/jpql/string#LOWER_and_UPPER_-_Changing_String_Case_)
- [Javadoc аннотации @Param](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/Param.html)
- [Справочное руководство Spring Data JPA: 4.3.5 Использование именованных параметров](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.named-parameters)

Давайте выясним, как мы можем реализовать нашу функцию поиска с помощью SQL.

---
#### Реализация функции поиска с помощью SQL

Мы можем **создать SQL-запрос**, отвечающий требованиям нашей функции поиска, **выполнив следующие шаги**:
- Шаг 1. - Создаем метод запроса, который возвращает список объектов `Todo`.
- Шаг 2. - Аннотируем метод аннотацией `@Query`.
- Шаг 3. - Создаем вызванный SQL-запрос, выполнив следующие действия:
            - Шаг 3.1. - Создаем запрос SQL, который использует именованные параметры и возвращает записи задач,
                         заголовок или описание которых содержит заданный поисковый запрос (не забываем игнорировать
                         регистр). Устанавливаем созданный запрос в качестве значения атрибута `value` аннотации `@Query`.
            - Шаг 3.2. - Устанавливаем значение атрибута `NativeQuery` аннотации `@Query` равным **true**.
- Шаг 4. - Добавим один параметр метода в метод запроса и настроим имя именованного параметра, добавив к параметру
           метода аннотацию `@Param`.

Исходный код интерфейса нашего репозитория выглядит следующим образом:

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    @Query(value = "SELECT * FROM todos t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))",
            nativeQuery = true
    )
    List<Todo> findBySearchTermNative(@Param("searchTerm") String searchTerm);
}
```

---
**Дополнительное чтение:**
- [SQL — функция CONCAT](https://www.tutorialspoint.com/sql/sql-concat-function.htm)
- [SQL — функция LOWER](https://www.tutorialspoint.com/sql/sql-string-functions.htm#function_lower)
- [Javadoc аннотации @Param](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/Param.html)
- [Справочное руководство Spring Data JPA: 4.3.5 Использование именованных параметров](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.named-parameters)

---
#### Когда нам следует использовать аннотацию @Query?

Аннотация `@Query` **имеет следующие преимущества**:
- Он **поддерживает как JPQL, так и SQL**.
- Вызванный запрос находится над методом запроса. Другими словами, **легко узнать, что делает метод запроса**.
- Для имен методов запроса не существует соглашения об именовании.

Аннотация `@Query` **имеет следующие недостатки**:
- Нет поддержки динамических запросов.
- Если мы **используем SQL-запросы, мы не можем изменить используемую базу данных**, не проверив, что наши SQL-запросы работают должным образом.

Когда мы задумываемся о плюсах и минусах аннотации @Query, становится ясно, что методы запроса, использующие эту
стратегию, не так легко писать или читать, как методы запроса, использующие генерацию запроса из стратегии имени
метода.

Однако, эти методы запроса имеют **два важных преимущества**:
- Если нам **нужно выяснить, какой запрос к базе данных вызывается** нашим методом запроса, **мы можем найти** вызванный запрос **над методом запроса**.
- Код, использующий наши методы запроса, легко читается, поскольку нам не нужно использовать длинные имена методов.

Таким образом, если мы **не хотим использовать генерацию запроса на основе стратегии имени метода**, поскольку имена наших
методов запроса будут слишком длинными, нам **следует использовать аннотацию `@Query`**.

И так **мы выяснили три вещи**:
- Запросы к базе данных, указанные с помощью аннотации @Query, предшествуют всем другим стратегиям создания запросов.
- Аннотация @Query поддерживает запросы как JPQL, так и SQL.
- Если мы не можем создавать запросы к базе данных на основе имен методов наших запросов, поскольку имена методов будут
  слишком длинными, нам следует создать их с помощью аннотации @Query.

[В следующей части посмотрим, как мы можем создавать запросы к базе данных с помощью именованных запросов:](./8_QueriesWithNamedQueries.md)
