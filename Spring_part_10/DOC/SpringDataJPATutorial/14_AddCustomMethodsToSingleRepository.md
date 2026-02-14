- [Исходник статьи (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-eight-adding-functionality-to-a-repository/)
- [Исходный код для статьи GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Adding Custom Methods to a Single Repository - Добавление пользовательских методов в один репозиторий

Хотя Spring Data JPA обеспечивает отличную поддержку для реализации операций CRUD и создания запросов к базе данных,
иногда нам нужно делать вещи, которые не поддерживаются им. Например, Spring Data JPA не предоставляет встроенной
поддержки запросов DTO с помощью SQL.

К счастью для нас, мы можем «расширить» наши репозитории Spring Data JPA, добавив в них собственные методы.

Тут мы разберемся, как мы можем добавлять собственные методы в единый репозиторий Spring Data JPA.

---
#### Создание пользовательского интерфейса репозитория

Когда мы хотим добавить пользовательские методы в репозиторий Spring Data JPA, первое, что нам нужно сделать - это
создать интерфейс, который объявляет пользовательские методы. Однако, поскольку мы хотим создать метод, возвращающий
список пользовательских объектов DTO, нам необходимо создать возвращаемый класс DTO, прежде чем мы сможем создать
пользовательский интерфейс репозитория. Поскольку страница результатов поиска нашего примера приложения использует
только значения полей id и title, нам необходимо создать класс DTO, который имеет два поля: id и title.

Исходный код класса `TodoSearchResultDTO` выглядит следующим образом:

```java
public final class TodoSearchResultDTO {

    private Long id;

    private String title;

    public TodoSearchResultDTO() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
```

Теперь мы можем создать собственный интерфейс репозитория, выполнив следующие шаги:
- **Шаг 1.** - **Создать интерфейс** CustomTodoRepository.
- **Шаг 2.** - **Добавить метод findbySearchTerm() в созданный интерфейс**. Этот метод принимает поисковый запрос в качестве параметра метода и возвращает список объектов TodoSearchResultDTO.

Исходный код интерфейса `CustomTodoRepository` выглядит следующим образом:

```java
import java.util.List;

interface CustomTodoRepository {

    List<TodoSearchResultDTO> findBySearchTerm(String searchTerm);
}
```

После того, как мы создали собственный интерфейс репозитория, нам необходимо его реализовать. Давайте выясним, как
создать класс репозитория, который извлекает результаты поиска с помощью JDBC.

---
#### Реализация пользовательского интерфейса репозитория

Поскольку мы хотим создать запрос SQL, использующий именованные параметры, и хотим вызвать этот запрос с помощью JDBC,
нам **необходимо настроить bean-компонент `NamedParameterJdbcTemplate`**, прежде чем мы сможем реализовать наш собственный
интерфейс репозитория. Мы можем настроить этот bean-компонент, внеся следующие изменения в класс конфигурации контекста
приложения, который настраивает уровень персистентности нашего "тестового" приложения:
- Добавим метод `jdbcTemplate()` в класс конфигурации и добавим к нему аннотацию `@Bean`. Этот метод принимает объект `DataSource` в качестве параметра метода и возвращает объект `NamedParameterJdbcTemplate`.
- Реализуем метод, создав новый объект `NamedParameterJdbcTemplate` и вернув созданный объект.

Соответствующая часть класса PersistenceContext выглядит следующим образом:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(basePackages = {"net.petrikainulainen.springdata.jpa.todo"})
@EnableTransactionManagement
class PersistenceContext {

    @Bean
    NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    /* Другие компоненты (bean) опущены для ясности */
}
```

---
**Дополнительное чтение:**
- [Справочное руководство Spring Framework: 14. Доступ к данным с помощью JDBC](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#jdbc)
- [Javadoc класса NamedParameterJdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/NamedParameterJdbcTemplate.html)

---
Теперь мы можем реализовать наш собственный интерфейс репозитория, выполнив следующие шаги:
- **Шаг 1.** Создадим собственный класс репозитория, реализующий интерфейс `CustomTodoRepository`. По умолчанию имя пользовательского класса репозитория должно соответствовать следующему синтаксису:
[Имя интерфейса репозитория]Impl. Поскольку имя нашего интерфейса репозитория - `TodoRepository`, то имя нашего пользовательского класса репозитория должно быть `TodoRepositoryImpl`.
- **Шаг 2.** Аннотируем созданный класс аннотацией `@Repository`.
- **Шаг 3.** Создадим запрос SQL, который возвращает идентификатор и заголовок записей задач, заголовок или описание которых содержит заданный поисковый запрос, и сортирует результаты запроса в порядке возрастания, используя значение столбца заголовка. Установим этот SQL-запрос как значение статического конечного поля.
- **Шаг 4.** Добавим последнее поле `NamedParameterJdbcTemplate` в класс репозитория и введем значение этого поля с помощью внедрения конструктора.
- **Шаг 5.** Реализуем метод `findBySearchTerm()`, выполнив следующие действия:
  - Добавим к методу аннотацию `@Transactional` и отметим транзакцию как доступную только для чтения. Это гарантирует, что наш SQL-запрос всегда будет вызываться внутри транзакции, доступной только для чтения.
  - Создадим объект Map, который содержит параметры нашего SQL-запроса, и поместим поисковый запрос, указанный в качестве параметра метода, в созданную коллекцию Map.
  - Вызовем запрос SQL и преобразуем результаты запроса в список объектов `TodoSearchResultDTO` с помощью класса `BeanPropertyRowMapper`. Мы можем использовать этот метод, если псевдонимы выбранных столбцов              совпадают с именами свойств «целевого класса».
  - Вернем результаты запроса.

Исходный код класса `TodoRepositoryImpl` выглядит следующим образом:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
final class TodoRepositoryImpl implements CustomTodoRepository {

    private static final String SEARCH_TODO_ENTRIES = "SELECT id, title FROM todos t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%')) " +
            "ORDER BY t.title ASC";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    TodoRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TodoSearchResultDTO> findBySearchTerm(String searchTerm) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("searchTerm", searchTerm);

        List<TodoSearchResultDTO> searchResults =
          jdbcTemplate.query(SEARCH_TODO_ENTRIES, queryParams, new BeanPropertyRowMapper<>(TodoSearchResultDTO.class));

        return searchResults;
    }
}
```

Если мы хотим изменить постфикс, который используется, когда Spring Data JPA ищет реализации пользовательских
репозиториев, нам необходимо использовать один из этих двух методов:
- Если мы используем конфигурацию Java, мы можем настроить постфикс, задав значение атрибута репозитория `ImplementationPostfix` аннотации `@EnableJpaRepository`.
- Если мы используем конфигурацию XML, мы можем настроить постфикс, установив значение атрибута (repository element's repository-impl-postfix) элемента репозитория.

---
**Дополнительное чтение:**
- [Javadoc класса BeanPropertyRowMapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/BeanPropertyRowMapper.html)

---
Давайте добавим наш собственный метод в интерфейс нашего репозитория `TodoRepository`.

---
### Добавление пользовательских методов в интерфейс репозитория

Мы можем добавить собственные методы в интерфейс репозитория, расширив собственный интерфейс репозитория, который
объявляет пользовательские методы. Другими словами, нам нужно изменить интерфейс `TodoRepository`, чтобы расширить
интерфейс `CustomTodoRepository`.

После того, как мы изменили интерфейс `TodoRepository`, его исходный код выглядит следующим образом:

```java
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface TodoRepository extends Repository<Todo, Long>, CustomTodoRepository {

    void delete(Todo deleted);

    List<Todo> findAll();

    Optional<Todo> findOne(Long id);

    void flush();

    Todo save(Todo persisted);
}
```

---
### Подведем итог:
- Если мы хотим запросить DTO с помощью SQL, нам нужно добавить собственный метод в интерфейс нашего репозитория Spring Data JPA.
- Мы можем сопоставить результаты нашего запроса с объектами, используя класс `BeanPropertyRowMapper`.
- Мы можем изменить постфикс, который используется для идентификации наших пользовательских реализаций репозитория.
