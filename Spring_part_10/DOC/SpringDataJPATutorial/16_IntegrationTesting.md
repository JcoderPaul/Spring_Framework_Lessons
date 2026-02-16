**!!! Статья весьма специфична !!!**

- [Исходник материала (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-integration-testing/)
- [Код на GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Integration Testing - Интеграционное тестирование

[Предыдущие разделы](../SpringDataJPATutorial) научил нас тому, что мы можем создавать запросы к базе данных и сохранять
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

Переход к Testcontainers и реальным БД в контейнерах — это тренд для повышения надежности тестов, особенно в микросервисах.

---
**Сопутствующий материал:**
- [Integration Testing (from Spring)](https://docs.spring.io/spring-framework/reference/testing/integration.html)
- [Spring into Integration Testing: Building Robust Spring Boot Applications](https://medium.com/@priyan.prabhu/spring-into-integration-testing-building-robust-spring-boot-applications-6820aa8dc28e)
- [A Case for Integration Tests: Implementing effective integration tests on Spring Boot – Databases](https://coderstower.com/2024/07/02/a-case-for-integration-tests-implementing-effective-integration-tests-on-spring-boot-databases/)
- [Spring Boot Testing: A Comprehensive Best Practices Guide](https://dev.to/ankitdevcode/spring-boot-testing-a-comprehensive-best-practices-guide-1do6)
- [Optimizing Spring Integration Tests](https://www.baeldung.com/spring-tests)
- [Spring Boot Testcontainers - Integration Testing made easy! (from YouTube)](https://www.youtube.com/watch?v=erp-7MCK5BU)
- [Integration Testing Deep Dive Part I](https://schibsted-vend.pl/blog/integration-testing-deep-dive-part-i/)

---
### Примеры использования Testcontainers

Несколько примеров использования **Testcontainers** в Spring Boot приложениях для интеграционного тестирования с базой данных (в основном PostgreSQL, как самая популярная комбинация).

### 1. Самый современный и рекомендуемый способ (Spring Boot 3.1+): `@ServiceConnection`

Добавьте зависимости в `pom.xml` (Maven):

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>1.20.4</version>  <!-- или новее -->
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>1.20.4</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-testcontainers</artifactId>
    <scope>test</scope>
</dependency>
```

Пример теста репозитория:

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldSaveAndFindBook() {
        Book book = new Book(null, "Clean Code", "Robert C. Martin");
        Book saved = bookRepository.save(book);

        assertThat(saved.getId()).isNotNull();
        assertThat(bookRepository.findById(saved.getId())).isPresent();
    }
}
```

- `@ServiceConnection` автоматически подставляет `spring.datasource.url`, `username`, `password` и т.д.
- Контейнер стартует один раз на класс (static) → быстрее
- Работает с `@DataJpaTest`, `@JdbcTest`, `@SpringBootTest`

### 2. Классический способ с `@DynamicPropertySource` (до сих пор используется)

```java
@SpringBootTest
@Testcontainers
class FullContextIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withInitScript("init.sql");  // можно инициализировать схему/данные

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private BookService bookService;

    @Test
    void testBusinessLogicWithRealDb() {
        // ...
    }
}
```

### 3. Тест с несколькими контейнерами (PostgreSQL + Redis например)

```java
@SpringBootTest
@Testcontainers
class MultiContainerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

    @Container
    @ServiceConnection
    static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:7-alpine"));

    // или через @Bean в @TestConfiguration
}
```

### 4. Повторное использование контейнера между тестами (перезапуск не нужен)

Вариант с `@TestConfiguration` (очень удобно для CI / большого количества тестов):

```java
@TestConfiguration(proxyBeanMethods = false)
class TestContainersConfig {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:17-alpine")
                .withReuse(true);  // сохраняет контейнер между запусками (нужен testcontainers.reuse.enable=true в ~/.testcontainers.properties)
    }
}
```

Затем в тестах:

```java
@SpringBootTest(classes = {Application.class, TestContainersConfig.class})
class SomeTest { ... }
```

### 5. Полезные дополнения

- Инициализация данных через Flyway/Liquibase — просто работает автоматически
- Скрипт инициализации: `.withInitScript("db/init.sql")`
- Переиспользование контейнеров (ускоряет локальные тесты в 5–10 раз):

Создайте файл `~/.testcontainers.properties`:

```
testcontainers.reuse.enable=true
```

- Для `@DataJpaTest` + Testcontainers часто добавляют:

```java
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
```

Это отключает авто-конфигурацию embedded БД.

Эти подходы — *state-of-the-art*. Самый чистый и рекомендуемый сейчас — `@ServiceConnection` + `@Testcontainers`.

---
**Сопутствующий материал:**
- [Testcontainers (from Spring)](https://docs.spring.io/spring-boot/reference/testing/testcontainers.html)
- [Improved Testcontainers Support in Spring Boot 3.1 (from Spring)](https://spring.io/blog/2023/06/23/improved-testcontainers-support-in-spring-boot-3-1)
- [Testcontainers for Java (from java.testcontainers.org)](https://java.testcontainers.org/)
- [Getting started with Testcontainers in a Java Spring Boot Project (from testcontainers.com)](https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/)
- [Testcontainers SpringBoot Quickstart (from GitHub)](https://github.com/testcontainers/testcontainers-java-spring-boot-quickstart)
- [Complex example how to use Testcontainers with various development scenarios (from GitHub)](https://github.com/bedla/spring-boot-postgres-testcontainers)
- [Testcontainers Spring Boot (from GitHub)](https://github.com/PlaytikaOSS/testcontainers-spring-boot/tree/develop)
- [Proof of concept for using the DynamicPropertySource spring annotation in tests requiring PostgreSQL (from GitHub)](https://github.com/findinpath/postgres-spring-boot-dynamicpropertysource)
- [Spring Boot Testing with Testcontainers: Real Integration, Simplified](https://medium.com/@alxkm/spring-boot-testing-with-testcontainers-real-integration-simplified-476e30b6045f)
- [Integration Tests on Spring Boot with PostgreSQL and Testcontainers](https://dev.to/mspilari/integration-tests-on-spring-boot-with-postgresql-and-testcontainers-4dpc)
- [Testing Spring Boot Applications Using Testcontainers](https://blog.jetbrains.com/idea/2024/12/testing-spring-boot-applications-using-testcontainers/)
- [GitHub testcontainers-java-spring-boot-quickstart](https://github.com/testcontainers/testcontainers-java-spring-boot-quickstart)
- [Integration Testing with Spring Boot and Testcontainers](https://www.blip.pt/blog/posts/integration-testing-with-spring-boot-and-testcontainers)
- [Testing Spring Boot Microservices with Testcontainers](https://medium.com/but-it-works-on-my-machine/testing-spring-boot-microservices-with-testcontainers-bd8dc289d581)
- [Spring Tidbits - Understanding Testcontainers Integration Features](https://developer.mamezou-tech.com/en/blogs/2025/06/23/testcontainers-with-springboot/)
- [Combine Testcontainers and Spring Boot with multiple containers](https://www.wimdeblauwe.com/blog/2025/05/14/combine-testcontainers-and-spring-boot-with-multiple-containers/)
- [Building Spring Boot’s ServiceConnection for Testcontainers WireMock](https://www.docker.com/blog/building-spring-boots-serviceconnection-for-testcontainers-wiremock/)
- [Built-in Testcontainers Support in Spring Boot](https://www.baeldung.com/spring-boot-built-in-testcontainers)
- [Simplifying Development: Spring Boot 3.1 and Testcontainers Desktop](https://medium.com/@edemircan/simplifying-development-spring-boot-3-1-and-testcontainers-desktop-9b2ea465f21b)
- [Spring Boot @ServiceConnection Example](https://mkyong.com/spring-boot/spring-boot-serviceconnection-example)
- [Everything you need to know about Testcontainers integration on Spring Boot 3.1](https://rabobank.jobs/en/techblog/everything-about-testcontainers-on-spring-boot-3-1/)
- [DB Integration Tests with Spring Boot and Testcontainers](https://www.baeldung.com/spring-boot-testcontainers-integration-test)
- [Spring Boot integration tests with TestContainers (PostgreSQL) (from GitHub)](https://github.com/PraveenGNair/spring-boot-test-container)
- [Embedded PostgreSQL for Spring Boot Tests](https://www.baeldung.com/spring-boot-embed-postgresql-testing)
- [Streamlining Spring Boot Integration Tests with Testcontainers](https://master-spring-ter.medium.com/streamlining-spring-boot-integration-tests-with-testcontainers-bb744e184fd1)
- [Spring boot multiple tests with testcontainers](https://stackoverflow.com/questions/77368679/spring-boot-multiple-tests-with-testcontainers)
