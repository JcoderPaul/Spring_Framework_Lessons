- [Исходник всего материала (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-creating-database-queries-with-the-query-annotation/)
- [Исходники на GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Creating Database Queries With Named Queries

В этом разделе рассмотрим как создавать запросы к базе данных, используя именованные запросы.

Прежде чем мы сможем реализовать нашу функцию поиска, мы должны понять, как мы можем использовать именованные запросы с
Spring Data JPA. Другими словами, нам предстоит найти ответы на следующие вопросы:
- **Как мы можем создавать именованные запросы?**
- **Как мы можем создать методы запроса, которые будут вызывать наши именованные запросы?**

---
#### Создание именованных запросов

Мы можем указать именованные запросы с помощью Spring Data JPA, используя файл свойств, аннотации или файл `orm.xml`.

Прежде чем более подробно рассмотреть эти методы, нам необходимо изучить несколько правил, которым мы должны следовать
при указании имен наших именованных запросов.

Эти **правила следующие**:
- Если мы **хотим использовать стратегию именования Spring Data JPA по умолчанию**, нам нужно указать имя именованного
  запроса, используя **следующий синтаксис: [имя класса сущности].[имя вызванного метода запроса]**.
- Если мы **хотим использовать другой синтаксис**, нам **необходимо настроить имя именованного запроса при создании метода
  запроса**, который его вызывает. Мы можем сделать это, **используя атрибут name аннотации @Query**.

Теперь мы готовы создавать именованные запросы с помощью Spring Data JPA. Начнем с добавления именованных запросов в
файл свойств.

---
#### Использование файла свойств

Мы можем объявить именованные запросы, добавив их в файл `jpa-named-queries.properties`, который находится **в папке
META-INF** нашего пути к классам.

Если мы хотим использовать другой файл свойств или переместить файл `jpa-named-queries.properties` в другой каталог,
мы **можем настроить его местоположение, используя два параметра**:
- Если мы настроим контекст нашего приложения **с помощью конфигурации Java**, мы можем настроить расположение файла свойств,
  установив значение атрибута `NameQueriesLocation` аннотации [`@EnableJpaRepositories`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/config/EnableJpaRepositories.html#namedQueriesLocation--).
- Если мы настраиваем контекст нашего приложения **с помощью файлов конфигурации XML**, мы можем настроить расположение
  файла свойств, установив значение атрибута именованного-запросов-локации элемента репозитория [или the repository
  element’s named-queries-location attribute см.](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#populator.namespace-dao-config)

Мы можем **объявить новый именованный запрос**, выполнив следующие шаги:
- **Установить имя именованного запроса** в качестве имени свойства.
- **Установить вызванный запрос** как значение свойства.

Другими словами, наш файл свойств должен иметь следующий формат:

```properties
name=query
```

---
**Пример:**

Мы хотим создать именованный запрос с именем `Todo.findByTitleIs`. Он возвращает все записи задач с заголовком `«title»`.

Если мы **хотим использовать JPQL**, нам нужно добавить следующую строку в наш файл свойств:

```
Todo.findByTitleIs=SELECT t FROM Todo t WHERE t.title = 'title'
```

Если мы **хотим использовать SQL**, нам нужно добавить следующую строку в наш файл свойств:

```
Todo.findByTitleIs=SELECT * FROM todos t WHERE t.title = 'title'
```

Давайте продолжим и выясним, как мы можем объявлять именованные запросы с помощью аннотаций.

---
#### Использование аннотаций

Мы можем **объявить именованные запросы, аннотируя наши объекты** следующими аннотациями:
- Если мы **хотим создать запрос JPQL**, нам **нужно аннотировать нашу сущность аннотацией @NamedQuery**.
- Если мы **хотим создать SQL-запрос**, нам **нужно пометить нашу сущность аннотацией @NamedNativeQuery**.
- Если мы **хотим создать более одного именованного запроса**, нам **нужно обернуть наши запросы в аннотацию
  @NamedQueries или @NamedNativeQueries**.

---
**Пример:**

Мы хотим создать именованный запрос с именем `Todo.findByTitleIs`. Он возвращает все записи задач с заголовком `«title»`.

Если мы **хотим создать запрос JPQL**, мы должны выполнить следующие шаги:
- Добавить к объекту аннотацию `@NamedQuery`.
- Задать имя именованного запроса "Todo.findByTitleIs" в качестве значения атрибута имени аннотации `@NamedQuery`.
- Установить запрос JPQL - **SELECT t FROM Todo t WHERE t.title = 'title'** в качестве значения атрибута запроса аннотации
  `@NamedQuery`.

Соответствующая часть нашей сущности выглядит следующим образом:

```java
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Todo.findByTitleIs",
            query = "SELECT t FROM Todo t WHERE t.title = 'title'")
@Table(name = "todos")
final class Todo {
 /* some code . . . */
}
```

---
**Дополнительное чтение:**
- [Javadoc аннотации @NamedQuery](https://docs.oracle.com/javaee/7/api/javax/persistence/NamedQuery.html)
- [Javadoc аннотации @NamedQueries](https://docs.oracle.com/javaee/7/api/javax/persistence/NamedQueries.html)
  
---
Если **мы хотим создать SQL-запрос**, мы должны выполнить следующие шаги:
- Добавить к объекту аннотацию `@NamedNativeQuery`.
- Задать имя именованного запроса "Todo.findByTitleIs" в качестве значения атрибута имени аннотации `@NamedNativeQuery`.
- Установить запрос SQL - **SELECT * FROM todos t WHERE t.title = 'title'** в качестве значения атрибута имени аннотации `@NamedNativeQuery`.
- Установить возвращаемый класс сущности "Todo.class", как значение атрибута `resultClass` аннотации `@NamedNativeQuery`.

Соответствующая часть нашей сущности выглядит следующим образом:

```java
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@NamedNativeQuery(name = "Todo.findByTitleIs”,
                  query="SELECT * FROM todos t WHERE t.title = 'title'",
                  resultClass = Todo.class)
@Table(name = "todos")
final class Todo {
     /* some code . . . */
}
```

---
Дополнительное чтение:
- [Javadoc аннотации @NamedNativeQuery](https://docs.oracle.com/javaee/7/api/javax/persistence/NamedNativeQuery.html)
- [Javadoc аннотации @NamedNativeQueries](https://docs.oracle.com/javaee/7/api/javax/persistence/NamedNativeQueries.html)

Если нам нужно создать сложные SQL-запросы, возможно, нам придется сопоставить результаты вашего SQL-запроса с помощью
аннотаций `@SqlResultSetMapping` и `@SqlResultSetMappings`. 

Если мы хотим получить дополнительную информацию об этом см.:

- [Javadoc аннотации @SqlResultSetMapping](https://docs.oracle.com/javaee/7/api/javax/persistence/SqlResultSetMapping.html)
- [Javadoc аннотации @SqlResultSetMappings](https://docs.oracle.com/javaee/7/api/javax/persistence/SqlResultSetMappings.html)
- [Сопоставление набора результатов: основы](https://thorben-janssen.com/result-set-mapping-basics/)
- [Сопоставление результирующего набора: сложные сопоставления](https://thorben-janssen.com/result-set-mapping-complex-mappings/)
- [Сопоставление результирующего набора: Сопоставление результатов конструктора](https://thorben-janssen.com/result-set-mapping-constructor-result-mappings/)

---
Давайте выясним, как создавать именованные запросы с помощью файла `orm.xml`.

---
#### Использование файла orm.xml

Мы можем объявить именованные запросы, добавив их в файл `orm.xml`, который находится **в папке META-INF** нашего пути к
классам. Нам нужно **использовать один из этих двух XML-элементов**:
- Если мы хотим создать запрос JPQL, нам нужно использовать элемент именованного запроса `named-query`.
- Если мы хотим создать SQL-запрос, нам нужно использовать элемент `named-native-query`.

---
**Пример:**

Мы хотим создать именованный запрос с именем `Todo.findByTitleIs`. Он возвращает все записи задач с заголовком `«title»`.

Если мы **хотим создать запрос JPQL**, мы должны выполнить **следующие шаги**:
- Добавить элемент именованного запроса в файл `orm.xml`.
- Установить имя именованного запроса `Todo.findByTitleIs` в качестве значения атрибута имени элемента именованного запроса.
- Добавить элемент запроса в качестве дочернего элемента именованного запроса `named-query` и установите вызванный
  запрос JPQL - **SELECT t FROM Todo t WHERE t.title = 'title'** в качестве значения элемента запроса `value of the query`.

Соответствующая часть файла `orm.xml` выглядит следующим образом:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings
        xmlns="http://java.sun.com/xml/ns/persistence/orm"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/persistence/orm http://java.sun.com/xml/ns/persistence/orm_2_0.xsd"
        version="2.0">

    <named-query name="Todo.findByTitleIs">
        <query>SELECT t FROM Todo t WHERE t.title = 'title'</query>
    </named-query>
</entity-mappings>
```

Если мы **хотим создать SQL-запрос**, мы должны выполнить **следующие шаги**:
- Добавить элемент именованного нативного запроса `named-native-query` в файл `orm.xml`.
- Задать имя именованного запроса `Todo.findByTitleIs` в качестве значения атрибута имени (name) элемента именованного запроса `named-native-query`.
- Установить тип возвращаемого объекта, пример: **net.petrikainulainen.springdata.jpa.todo.Todo** как значение атрибута `result-class` элемента именованного запроса `named-native-query`.
- Добавить элемент запроса в качестве дочернего элемента именованного нативного запроса и установить вызванный SQL-запрос - **SELECT * FROM todos t WHERE t.title = 'title'**) в качестве значения элемента запроса.

Соответствующая часть файла `orm.xml` выглядит следующим образом:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings
        xmlns="http://java.sun.com/xml/ns/persistence/orm"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/persistence/orm http://java.sun.com/xml/ns/persistence/orm_2_0.xsd"
        version="2.0">

    <named-native-query name="Todo.findByTitleIs"
                        result-class="net.petrikainulainen.springdata.jpa.todo.Todo">
        <query>SELECT * FROM todos t WHERE t.title = 'title'</query>
    </named-native-query>
</entity-mappings>
```

---
Дополнительное чтение:

Если нам нужно создать сложные запросы SQL, возможно, нам придется сопоставить результаты нашего запроса SQL с помощью
элемента `sql-result-set-mapping`. Если мы хотим получить дополнительную информацию о сопоставлении наборов результатов
SQL, нам следует прочитать эти статьи в блоге:
- [Сопоставление набора результатов: основы](https://thorben-janssen.com/result-set-mapping-basics/)
- [Сопоставление результирующего набора: сложные сопоставления](https://thorben-janssen.com/result-set-mapping-complex-mappings/)
- [Сопоставление результирующего набора: Сопоставление результатов конструктора](https://thorben-janssen.com/result-set-mapping-constructor-result-mappings/)

---
Давайте продолжим и выясним, как мы можем создавать методы запросов, которые вызывают наши именованные запросы.

---
#### Создание методов запроса

Мы можем создать метод запроса, который вызывает определенный именованный запрос, выполнив следующие действия:
- Добавим метод запроса в интерфейс нашего репозитория следуя этим правилам:
    - Если наш именованный запрос использует стратегию именования Spring Data JPA по умолчанию, мы должны убедиться, что имя метода запроса идентифицирует вызванный именованный запрос.
    - Если наш именованный запрос не использует стратегию именования по умолчанию, нам необходимо пометить метод запроса аннотацией `@Query` и настроить имя вызываемого именованного запроса, используя атрибут `name` аннотации `@Query`.
    - Если вызванный именованный запрос является запросом SQL, мы должны пометить метод запроса аннотацией `@Query` и установить значение его атрибута `ownQuery` равным **true**.
- Добавим правильные параметры метода в метод запроса.
- Укажем тип возвращаемого значения метода запроса.

---
Дополнительное чтение:
- [Javadoc аннотации @Query](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Query.html)
- [Справочное руководство Spring Data JPA: 4.3.3 Использование JPA NamedQueries](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.query-methods.named-queries)

---
**Пример 1:** Мы хотим создать метод запроса, который вызывает именованный запрос с именем: `Todo.findByTitleIs`. Поскольку этот именованный запрос возвращает записи задач с заголовком `title`, он не имеет никаких параметров.

Если вызванный именованный запрос является запросом JPQL, нам необходимо добавить следующий метод запроса в интерфейс нашего репозитория:

```java
import org.springframework.data.repository.Repository;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    public List<Todo> findByTitleIs();
}
```

Если вызванный именованный запрос является запросом SQL, нам необходимо добавить следующий метод запроса в интерфейс нашего репозитория:

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    @Query(nativeQuery = true)
    public List<Todo> findByTitleIs();
}
```

---
**Пример 2:** Мы хотим создать метод запроса, который вызывает именованный запрос с именем: `Todo.findByDesc`. Этот именованный запрос имеет один именованный параметр, называемый `description`.

Если вызванный запрос является запросом JPQL, нам необходимо добавить следующий метод запроса в интерфейс нашего репозитория:

```java
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    public List<Todo> findByDesc(@Param("description") String description);
}
```

Если вызванный запрос является запросом SQL, нам необходимо добавить следующий метод запроса в интерфейс нашего репозитория:

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    @Query(nativeQuery=true)
    public List<Todo> findByDesc(@Param("description") String description);
}
```

**Теперь мы готовы реализовать нашу функцию поиска. Давайте выясним, как мы можем это сделать.**

---
### Реализация функции поиска

Мы можем **реализовать нашу функцию поиска, выполнив следующие шаги**:
- Шаг 1. Создаем запросы JPQL и SQL, нечувствительные к регистру, и возвращаем записи задач "TODO", заголовок или описание которых содержит заданный поисковый запрос.
- Шаг 2. Создаем именованные запросы "named queries", которые вызывают созданные запросы JPQL и SQL.
- Шаг 3. Создаем методы запроса, которые вызывают наши именованные запросы.

**Начнем!!!**

---
#### Шаг 1. - Создание запросов к базе данных

Наша функция поиска должна отвечать двум требованиям:
- Она должна возвращать записи задач, заголовок или описание которых содержит заданный поисковый запрос.
- Она должна быть нечувствительной к регистру.

В этом разделе описаны запросы JPQL и SQL, соответствующие этим требованиям.
- **ВО-ПЕРВЫХ**, запрос JPQL, соответствующий нашим требованиям, выглядит следующим образом:

```sql
SELECT t FROM Todo t WHERE
    LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR
    LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))
```

- **ВО-ВТОРЫХ**, SQL-запрос, соответствующий нашим требованиям, выглядит следующим образом:

```sql
SELECT * FROM todos t WHERE
    LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR
    LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))
```

Давайте двинемся дальше и создадим именованные запросы.

---
#### Шаг 2. - Создание именованных запросов

Тут описывается, как мы можем указать необходимые именованные запросы с помощью файла свойств, аннотаций и файла `orm.xml`.

Нам нужно создать два именованных запроса:
- **Todo.findBySearchTermNamed** — это именованный запрос, **использующий JPQL**.
- **Todo.findBySearchTermNamedNative** — это именованный запрос, **использующий SQL**.

---
**Использование файла свойств**

После того, как мы добавили оба именованных запроса `Todo.findBySearchTermNamed` и `Todo.findBySearchTermNamedNative` в
файл `META-INF/jpa-named-queries.properties`, его содержимое выглядит следующим образом:

```xml
Todo.findBySearchTermNamed=SELECT t FROM Todo t
                           WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
                           OR
                           LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
Todo.findBySearchTermNamedNative=SELECT * FROM todos t
                           WHERE LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%'))
                           OR
                           LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))
```

Теперь объявим эти именованные запросы с помощью аннотаций.

---
**Использование аннотаций**

После того, как мы создали оба именованных запроса `Todo.findBySearchTermNamed` и `Todo.findBySearchTermNamedNative` с
помощью аннотаций `@NamedQuery` и `@NamedNativeQuery`, соответствующая часть нашего класса сущности выглядит следующим
образом:

```java
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedNativeQuery(name = "Todo.findBySearchTermNamedNative",
        query="SELECT * FROM todos t WHERE " +
                "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
                "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))",
        resultClass = Todo.class
)
@NamedQuery(name = "Todo.findBySearchTermNamed",
        query = "SELECT t FROM Todo t WHERE " +
                "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
)
@Table(name = "todos")
final class Todo {

}
```

Теперь, мы можем объявить эти именованные запросы, используя файл `orm.xml`.

---
**Использование файла `orm.xml`**

После того, как мы создали оба именованных запроса `Todo.findBySearchTermNamed` и `Todo.findBySearchTermNamedNative` с
использованием элементов `Named-query` и `Named-native-query`, файл `META-INF/orm.xml` выглядит следующим образом:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings
        xmlns="http://java.sun.com/xml/ns/persistence/orm"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/persistence/orm http://java.sun.com/xml/ns/persistence/orm_2_0.xsd"
        version="2.0">

    <named-query name="Todo.findBySearchTermNamedOrmXml">
        <query>SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))</query>
    </named-query>

    <named-native-query name="Todo.findBySearchTermNamedNativeOrmXml"
                        result-class="net.petrikainulainen.springdata.jpa.todo.Todo">
        <query>SELECT * FROM todos t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))</query>
    </named-native-query>
</entity-mappings>
```

Теперь создадим методы запросов, которые вызывают эти именованные запросы.

---
#### Шаг 3. - Создание методов запроса

Мы можем создать методы запроса, которые вызывают наши именованные запросы, выполнив следующие шаги:
- Шаг 1. - Создаем метод запроса, который вызывает запрос JPQL - `Todo.findBySearchTermNamed`, выполнив следующие действия:
            - Создаем метод запроса **findBySearchTermNamed()**.
            - Устанавливаем тип возвращаемого значения метода запроса `List<Todo>`.
            - Добавляем один параметр метода в метод запроса и настраиваем имя именованного параметра `searchTerm`, добавив к параметру метода аннотацию `@Param`.
- Шаг 2. - Создаем метод запроса, который вызывает запрос SQL - `Todo.findBySearchTermNamedNative`, выполнив следующие действия:
            - Создаем метод запроса **findBySearchTermNamedNative()**.
            - Добавляем к методу аннотацию `@Query` и устанавливаем для ее атрибут `nativeQuery` значение **true**.
            - Устанавливаем тип возвращаемого значения метода запроса `List<Todo>`.
            - Добавляем один параметр метода в метод запроса и настраиваем имя именованного параметра `searchTerm`, добавив к параметру метода аннотацию `@Param`.

Исходный код интерфейса нашего репозитория выглядит следующим образом:

```Java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    List<Todo> findBySearchTermNamed(@Param("searchTerm") String searchTerm);

    @Query(nativeQuery = true)
    List<Todo> findBySearchTermNamedNative(@Param("searchTerm") String searchTerm);
}
```

---
### Когда нам следует использовать именованные запросы?

Именованные запросы имеют следующие преимущества:
- Именованные запросы поддерживают как JPQL, так и SQL.
- Если у нас есть существующее приложение, использующее именованные запросы, его легко реорганизовать для использования Spring Data JPA (если мы этого захотим).
- Именованные запросы обеспечивают поддержку сопоставления наборов результатов SQL. Это означает, что мы можем писать сложные SQL-запросы и отображать результаты запросов в объекты.

Именованные запросы имеют следующие недостатки:
- Мы не можем увидеть вызванный запрос к базе данных из интерфейса репозитория.
- Нет поддержки динамических запросов.
- Если мы указываем именованные запросы с помощью аннотаций, они «засоряют» исходный код наших классов сущностей.

Если мы подумаем о плюсах и минусах именованных запросов, станет ясно, что методы запроса, использующие именованные
запросы, не так легко читать или писать, как методы запроса, которые используют либо [генерацию запроса на основе стратегии имени метода](./6_QueriesFromMethodNames.txt), 
либо [аннотацию запроса](./7_QueriesWithAnnotation.md).

Однако именованные запросы имеют два преимущества:
- Мы можем писать сложные SQL-запросы и отображать результаты запросов в объекты.
- Если нам нужно провести рефакторинг существующего приложения для использования Spring Data JPA, нам не нужно перемещать его именованные запросы в интерфейсы нашего репозитория.

И все же есть мнение, что нам следует создавать методы запроса, используя генерацию запросов на основе стратегии имени метода или аннотации `@Query`.

Однако если мы не можем создать метод запроса, используя эти два метода, мы можем использовать именованные запросы.

Кроме того, если мы проводим рефакторинг существующего приложения для использования Spring Data JPA, использование
существующих именованных запросов не составит труда, поскольку это может сэкономить нам много работы.

---
**Итог того, что мы рассмотрели**:
- Мы **можем создавать именованные запросы, используя**: *файл свойств*, *аннотации* или файл *orm.xml*.
- Именованные запросы поддерживают как JPQL, так и SQL.
- Если нам нужно создать сложные запросы SQL, мы можем сопоставить результаты запроса с объектами, используя сопоставление набора результатов SQL.
- Нам следует использовать именованные запросы только в том случае, если у нас нет выбора ИЛИ мы проводим рефакторинг существующего приложения для использования Spring Data JPA.

[В следующей части мы посмотрим на то, как можем создавать динамические запросы с помощью API критериев JPA →](./9_QueriesWithJPACriteriaAPI.md)
