**!!! Статья весьма специфична !!!**

- [Исходник материала (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-integration-testing/)
- [Код на GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Integration Testing - Интеграционное тестирование

[Предыдущие разделы](./) научил нас тому, что мы можем создавать запросы к базе данных и сохранять
объекты в базе данных, используя специальные интерфейсы репозитория.

**Возникает интересный вопрос:** Как мы можем писать интеграционные тесты для наших репозиториев Spring Data JPA, ведь это всего лишь интерфейсы?

Разберемся в этом вопросе тут, и напишем интеграционные тесты для JPA-репозитория Spring Data, который управляет
информацией записей задач - объектов *Todo*. Если быть более конкретным, мы напишем интеграционные тесты для метода
`findBySearchTerm()` интерфейса `TodoRepository`. Этот метод игнорирует регистр и возвращает записи задач, заголовок
или описание которых содержит заданный поисковый запрос.

Начнем с получения необходимых зависимостей с помощью Maven.

---
#### Получение необходимых зависимостей с помощью Maven

Мы можем получить необходимые зависимости с помощью Maven, объявив следующие зависимости в нашем файле **pom.xml**:
- **JUnit** (версия 4.11).
- **AssertJ Core (версия 3.2.0)** - Мы используем AssertJ, чтобы гарантировать, что тестируемый метод возвращает правильную информацию.
- **Spring Test** (версия 4.1.6.RELEASE).
- **DbUnit** (версия 2.5.1) - Не забудем исключить зависимость JUnit. Мы используем DbUnit для инициализации нашей базы данных в известном состоянии перед вызовом каждого тестового примера.
- **Spring Test DbUnit** (версия 1.2.1) - интегрирует DbUnit с платформой Spring Test.

Соответствующая **часть нашего файла pom.xml** выглядит следующим образом:

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.11</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.2.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>4.1.6.RELEASE</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.dbunit</groupId>
    <artifactId>dbunit</artifactId>
    <version>2.5.1</version>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <artifactId>junit</artifactId>
            <groupId>junit</groupId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>com.github.springtestdbunit</groupId>
    <artifactId>spring-test-dbunit</artifactId>
    <version>1.2.1</version>
    <scope>test</scope>
</dependency>
```

После того, как мы настроили необходимые зависимости в файле pom.xml, мы можем настроить наши интеграционные тесты.

---
#### Настройка наших интеграционных тестов

Мы можем **настроить наши интеграционные тесты**, выполнив следующие шаги:
- **Шаг 1.** Запустим интеграционные тесты с помощью класса `SpringJUnit4ClassRunner`. Это специальный инструмент запуска JUnit, который интегрирует среду Spring Test с JUnit. Мы можем настроить используемый исполнитель JUnit, аннотировав наш тестовый класс аннотацией `@RunWith`.
- **Шаг 2.** Настроим класс конфигурации контекста приложения (или файл конфигурации XML), который настраивает контекст приложения, используемый нашими интеграционными тестами. Мы можем настроить используемый класс конфигурации контекста приложения (или файл конфигурации XML), аннотируя наш тестовый класс аннотацией `@ContextConfiguration`.
- **Шаг 3.**** Настроим слушателя выполнения тестов** - *test execution listeners*, которые реагируют на события выполнения тестов, публикуемые средой Spring Test. Нам необходимо настроить следующие слушатели выполнения тестов:
  - `DependencyInjectionTestExecutionListener` - обеспечивает внедрение зависимостей для тестового объекта.
  - `TransactionalTestExecutionListener` - добавляет поддержку транзакций (с семантикой отката по умолчанию) в наши интеграционные тесты.
  - `DbUnitTestExecutionListener` - добавляет поддержку функций, предоставляемых библиотекой Spring Test DbUnit.

После того, как мы добавили эту конфигурацию в наш класс интеграционного теста, ее исходный код выглядит следующим образом:

```java
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
public class ITFindBySearchTermTest {
}
```

---
**Дополнительное чтение:**
- [Справочная документация Spring Framework: 11.3 Интеграционное тестирование](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#integration-testing)
- [Справочная документация Spring Framework: 14.5.2 Конфигурация TestExecutionListener](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#testcontext-tel-config)
- [Javadoc аннотации @RunWith](https://junit.org/junit4/javadoc/latest/org/junit/runner/RunWith.html)
- [Javadoc класса SpringJUnit4ClassRunner](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit4/SpringJUnit4ClassRunner.html)
- [Javadoc аннотации @ContextConfiguration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ContextConfiguration.html)
- [Javadoc аннотации @TestExecutionListeners](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestExecutionListeners.html)
- [Javadoc интерфейса TestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestExecutionListener.html)
- [Javadoc класса DependencyInjectionTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/support/DependencyInjectionTestExecutionListener.html)
- [Javadoc класса TransactionalTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/transaction/TransactionalTestExecutionListener.html)

---
После того, как мы настроили наш класс интеграционных тестов, мы можем начать писать интеграционные тесты для нашего репозитория Spring Data JPA.

---
#### Написание интеграционных тестов для нашего репозитория

Мы можем написать интеграционные тесты для нашего репозитория, выполнив следующие шаги:
- **ВО-ПЕРВЫХ, нам нужно внедрить тестируемый репозиторий в наш тестовый класс.** Поскольку мы пишем интеграционные тесты для интерфейса `TodoRepository`, нам необходимо внедрить его в наш тестовый класс. Исходный код нашего тестового класса выглядит следующим образом:

```java
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
public class ITFindBySearchTermTest {

    @Autowired
    private TodoRepository repository;
}
```

- **ВО-ВТОРЫХ, нам нужно создать набор данных DbUnit**, который инициализирует нашу базу данных в известном состоянии перед вызовом наших тестовых примеров. Мы будем использовать простой формат набора данных XML, поскольку он менее подробный, чем исходный формат набора данных DbUnit. Это означает, что мы можем создать наш набор данных, следуя этим правилам:
  - Каждый элемент XML содержит информацию об одной строке таблицы.
  - Имя элемента XML идентифицирует имя таблицы базы данных, в которую вставляется его информация.
  - Атрибуты элемента XML определяют значения, которые вставляются в столбцы таблицы базы данных.

Тестируемый репозиторий - *TodoRepository* запрашивает информацию из таблицы задач, которая имеет следующие столбцы:
*id, created_by_user, creation_time, description, modified_by_user, modification_time, title, version*.

Поскольку мы пишем интеграционные тесты для метода, который возвращает список объектов *Todo*, мы хотим вставить две
строки в таблицу *todos*. Мы можем сделать это, создав файл набора данных DbUnit - **todo-entries.xml**, который выглядит
следующим образом:

```Java
<dataset>
    <todos id="1"
           created_by_user="createdByUser"
           creation_time="2014-12-24 11:13:28"
           description="description"
           modified_by_user="modifiedByUser"
           modification_time="2014-12-25 11:13:28"
           title="title"
           version="0"/>
    <todos id="2"
           created_by_user="createdByUser"
           creation_time="2014-12-24 11:13:28"
           description="tiscription"
           modified_by_user="modifiedByUser"
           modification_time="2014-12-25 11:13:28"
           title="Foo bar"
           version="0"/>
</dataset>
```

---
**Дополнительное чтение:**
- [Документация DbUnit предоставляет дополнительную информацию о различных форматах наборов данных DbUnit](https://dbunit.sourceforge.net/project-info.html)

---
- **В-ТРЕТЬИХ, мы можем написать интеграционные тесты для метода `findBySearchTerm()` интерфейса `TodoRepository`**. Давайте напишем интеграционные тесты, которые проверят, что метод findBySearchTerm() работает правильно, когда заголовок одной записи задачи содержит заданный поисковый запрос. Мы можем написать эти интеграционные тесты, выполнив следующие шаги:
  - **Шаг 1.** Настроим используемый файл набора данных, добавив к классу интеграционного теста аннотацию `@DatabaseSetup`.
  - **Шаг 2.** Напишем интеграционный тест, который гарантирует, что метод `findBySearchTerm()` **возвращает одну запись задачи**, когда поисковый запрос `«iTl»` передается в качестве параметра метода.
  - **Шаг 3.** Напишем интеграционный тест, который гарантирует, что метод `findBySearchTerm()` **возвращает «первую» запись задачи**, когда поисковый запрос «iTl» передается в качестве параметра метода.

Исходный код класса `ITFindBySearchTerm` выглядит следующим образом:

```java
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@DatabaseSetup("todo-entries.xml")
public class ITFindBySearchTermTest {

    @Autowired
    private TodoRepository repository;

    @Test
    public void findBySearchTerm_TitleOfFirstTodoEntryContainsGivenSearchTerm_ShouldReturnOneTodoEntry() {
        List<Todo> searchResults = repository.findBySearchTerm("iTl");
        assertThat(searchResults).hasSize(1);
    }

    @Test
    public void findBySearchTerm_TitleOfFirstTodoEntryContainsGivenSearchTerm_ShouldReturnFirstTodoEntry() {
        List<Todo> searchResults = repository.findBySearchTerm("iTl");

        Todo found = searchResults.get(0);
        assertThat(found.getId()).isEqualTo(1L);
    }
}
```

---
При использовании аннотации `@DatabaseSetup` необходимо соблюдать следующие правила:

- Если все методы тестирования нашего тестового класса используют один и тот же набор данных, мы можем настроить
  его, аннотировав наш тестовый класс аннотацией `@DatabaseSetup`. Однако если все методы тестирования нашего тестового
  класса не используют один и тот же набор данных, нам необходимо пометить свои методы тестирования аннотацией
  `@DatabaseSetup`.
- Если файл набора данных находится в том же пакете, что и класс интеграционного теста, его можно настроить, используя
  имя файла набора данных. С другой стороны, если файл набора данных не находится в том же пакете, что и тестовый класс,
  нам необходимо настроить полный путь к файлу набора данных.

  Например, если наш файл набора данных - *todo-entries.xml* находится в пакете `foo.bar`, мы можете настроить его полный
  путь, используя строку: */foo/bar/todo-entries.xml*.

---
**Дополнительное чтение:**
- [Написание тестов для кода доступа к данным - это руководство из пяти частей, в котором описывается, как писать тесты для кода доступа к данным и обеспечивать чистоту и простоту обслуживания ваших тестов.](https://www.petrikainulainen.net/writing-tests-for-data-access-code/)
- [Использование нулевых значений в наборах данных DbUnit - описывает, почему нам следует использовать нулевые значения в наборах данных DbUnit, и объясняет, как их можно использовать.](https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-using-null-values-in-dbunit-datasets/)
- [Сброс столбцов автоматического увеличения перед каждым методом тестирования - описывает, почему нам следует сбрасывать столбцы автоматического увеличения перед каждым методом тестирования, и объясняет, как это можно сделать.](https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-resetting-auto-increment-columns-before-each-test-method/)

---
#### Краткий итог:
- Мы можем интегрировать DbUnit со средой Spring Test, используя Spring Test DbUnit.
- Мы можем интегрировать Spring Test DbUnit с платформой Spring Test, используя класс DbUnitTestExecutionListener.
- Нам следует использовать простой формат базы данных XML, поскольку он менее многословен, чем исходный формат набора данных DbUnit.
- Мы можем использовать аннотацию @DatabaseSetup на уровне класса или на уровне метода.

---
### Современное интеграционное тестирование с БД.

Интеграционное тестирование в Spring-приложениях, особенно тех, что взаимодействуют с базами данных, направлено на проверку взаимодействия компонентов приложения (например, репозиториев, сервисов и контроллеров) с реальными или эмулируемыми внешними зависимостями, такими как БД. Это позволяет выявить проблемы в SQL-запросах, маппингах сущностей JPA/Hibernate и общей интеграции с данными. Современные подходы фокусируются на балансе между скоростью выполнения тестов, их реалистичностью и простотой настройки. Spring Framework и Spring Boot предоставляют мощные инструменты для этого, включая модуль `spring-test` и аннотации вроде `@SpringBootTest`.

#### Основные современные методы

1. **Встроенные (embedded) базы данных**  
   Для быстрых тестов часто используются in-memory БД, такие как H2, HSQLDB или Derby. Они запускаются в памяти JVM и не требуют внешних контейнеров. В Spring Boot это реализуется через аннотацию `@DataJpaTest`, которая загружает только компоненты, связанные с данными (репозитории, сущности), и автоматически настраивает embedded БД.  
   - Преимущества: Быстрые, изолированные тесты без сетевых вызовов.  
   - Недостатки: Не всегда точно имитируют поведение реальной БД (например, PostgreSQL или MySQL), что может привести к ложным положительным результатам. Рекомендуется для простых сценариев, но для более точных тестов лучше использовать реальные БД.  
   Пример конфигурации в `application-test.properties`:  
   ```
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driverClassName=org.h2.Driver
   ```

2. **Testcontainers: Контейнеризация реальных БД**  
   Это один из самых популярных современных методов для интеграционных тестов с БД. Testcontainers позволяет запускать реальные экземпляры БД (PostgreSQL, MySQL, Oracle и т.д.) в Docker-контейнерах прямо во время выполнения тестов. Это обеспечивает высокую точность, так как тесты работают с той же БД, что и в продакшене, но в изолированной среде.  
   - Интеграция с Spring Boot: С версии 3.1 аннотация `@ServiceConnection` упрощает подключение контейнеров. Для тестов используйте `@SpringBootTest` или `@DataJdbcTest`.  
   - Управление: Контейнеры автоматически стартуют и останавливаются. Для миграций схемы интегрируйте Flyway или Liquibase.  
   
   Пример теста:  

   ```java
   @SpringBootTest
   @Testcontainers
   class MyIntegrationTest {
       @Container
       @ServiceConnection
       static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
       
       @Autowired
       private MyRepository repository;
       
       @Test
       void testSaveAndRetrieve() {
           // Тестовые операции с БД
       }
   }
   ```  
   - Преимущества: Реалистичность, простота настройки, поддержка облачных runtime (например, Testcontainers Cloud).  
   - Недостатки: Требует Docker, тесты медленнее embedded-вариантов.

4. **Аннотации Spring Boot для слоев тестирования**  
   Spring Boot предлагает специализированные аннотации для фокуса на данных:  
   - `@DataJpaTest`: Для тестирования JPA-репозиториев с автоматической настройкой транзакций и rollback после теста.  
   - `@JdbcTest` или `@DataJdbcTest`: Для JDBC- или R2DBC-интеграций.  
   - `@SpringBootTest`: Полный контекст приложения для end-to-end тестов, включая БД. Для оптимизации используйте профили (profiles) и свойства для тестов.  
   Чтобы управлять состоянием БД:  
   - `@Sql`: Выполняет SQL-скрипты перед/после теста для инициализации или очистки данных.  
   - Flyway/Liquibase: Автоматические миграции схемы в тестах, интегрированные в Spring Boot starter. Это решает проблему управления состоянием БД между тестами.

5. **Комбинированные подходы и лучшие практики**  
   - **Изоляция зависимостей**: Для внешних сервисов (не БД) используйте моки (например, WireMock), но для БД предпочитайте реальные инстансы.  
   - **Оптимизация скорости**: Кэшируйте контекст Spring с `@DirtiesContext` только когда нужно, или используйте срезы (slices) вроде `@WebMvcTest` для веб-части без полной БД.  
   - **Инструменты для веб-интеграции**: Если приложение включает API, комбинируйте с MockMvc (для не-реактивных), TestRestTemplate или WebTestClient (для reactive), проверяя взаимодействие с БД через эндпоинты.  
   - **Дополнительные инструменты**: Для сложных сценариев интегрируйте Spock (с Kotlin) или RestAssured для валидации ответов.  

В целом, переход к Testcontainers и реальным БД в контейнерах — это тренд для повышения надежности тестов, особенно в микросервисах. Для начала рекомендуется изучить официальную документацию Spring и примеры на GitHub. Если нужны конкретные кодовые примеры, уточните!

---
### Примеры использования Testcontainers

