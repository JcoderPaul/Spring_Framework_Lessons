- [Исходник статьи (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-six-sorting/)
- [Исходник кода на GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Pagination - Нумерация результатов запросов

На данном этапе мы знаем, как создавать запросы к базе данных и сортировать результаты запросов с помощью
Spring Data JPA. Мы также знаем, как реализовать функцию поиска, которая игнорирует регистр и возвращает
некий результат (в нашем случае - записи задач `Todo`), заголовок или описание которых содержит заданный поисковый
запрос. Эта функция поиска сортирует возвращаемые записи задач в порядке возрастания, используя заголовок
возвращенной записи задачи.

Однако мы еще не закончили. В нашем примере приложения есть один серьезный недостаток: Он возвращает ВСЕ записи
задач - `Todo`, найденные в базе данных, и это проблема с производительностью. 

Необходимо устранить этот недостаток.

---
### Разбиение на страницы результатов запросов нашей базы данных

Мы можем **разбить результаты запросов** нашей базы данных на страницы, **выполнив следующие шаги**:
- Шаг 1. - **Получим объект Pageable**, который указывает информацию о запрошенной странице.
- Шаг 2. - **Передадим объект Pageable правильному методу репозитория** в качестве параметра метода.

Давайте начнем с выяснения того, как мы можем получить объект Pageable.

---
#### Шаг 1. - Получение Pageable объекта

Мы можем **получить объект Pageable**, используя эти два метода:
- Вариант 1. - Мы **можем создать его вручную**.
- Вариант 2. - Мы **можем использовать веб-поддержку Spring Data**.

Начнем с создания объекта Pageable вручную.

---
#### → Вариант 1. - Создание Pageable объекта вручную.

Если мы хотим создать объект `Pageable` вручную, сервисный класс (или другой компонент), который хочет разбить на страницы
результаты запроса, возвращаемые репозиторием `Spring Data JPA`, должен создать объект `Pageable` и передать его вызываемому
методу репозитория.

Исходный код класса `RepositoryTodoSearchService`, использующего этот метод, выглядит следующим образом:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
final class RepositoryTodoSearchService implements TodoSearchService {

    private final TodoRepository repository;

    @Autowired
    public RepositoryTodoSearchService(TodoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TodoDTO> findBySearchTerm(String searchTerm) {
        Pageable pageRequest = createPageRequest()

        /* Получение результатов поиска путем вызова предпочтительного метода репозитория. */
        Page<Todo> searchResultPage = ...

        return TodoMapper.mapEntityPageIntoDTOPage(pageRequest, searchResultPage);
    }

    private Pageable createPageRequest() {
        /* Здесь создаем новый объект Pageable. */
    }
}
```

Следующие примеры демонстрируют, как мы можем реализовать частный метод `createPageRequest()`:

**Пример 1.** Если мы **хотим получить первую страницу, используя размер страницы 10**, нам нужно создать объект `Pageable`, используя следующий код:

```java
private Pageable createPageRequest() {
    return new PageRequest(0, 10);
}
```

**Пример 2.** Нам **нужно отсортировать результаты запроса в порядке возрастания, используя значения полей заголовка и
          описания**. Если **мы хотим получить вторую страницу, используя размер страницы 10**, нам нужно создать объект
          `Pageable`, используя следующий код:

```java
private Pageable createPageRequest() {
    return new PageRequest(1, 10, Sort.Direction.ASC, "title", "description");
}
```

**Пример 3.** Нам **нужно отсортировать результаты запроса в порядке убывания, используя значение поля описания, и в порядке
          возрастания, используя значение поля заголовка**. Если **мы хотим получить вторую страницу, используя размер
          страницы 10**, нам нужно создать объект `Pageable`, используя следующий код:

```
private Pageable createPageRequest() {
    return new PageRequest(1, 10, new Sort(Sort.Direction.DESC, "description").and(new Sort(Sort.Direction.ASC, "title"));
}
```

---
**Дополнительное чтение:**
- [Javadoc интерфейса Pageable](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Pageable.html)
- [Javadoc класса PageRequest](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/PageRequest.html)
- [Javadoc интерфейса Page](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Page.html)

---
Давайте **выясним, как мы можем получить** объекты `Pageable` с **помощью веб-поддержки Spring Data**.

---
#### → Вариант 2. - Использование веб-поддержки Spring Data.

Мы **можем включить веб-поддержку Spring Data, аннотируя наш класс конфигурации** контекста приложения аннотацией
`@EnableSpringDataWebSupport`. Соответствующая часть класса `PersistenceContext`, которая настраивает уровень
персистентности нашего примера приложения, выглядит следующим образом:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(basePackages = {"net.petrikainulainen.springdata.jpa.todo"})
@EnableTransactionManagement
@EnableSpringDataWebSupport
class PersistenceContext {
}
```

При этом **регистрируются два объекта** `HandlerMethodArgumentResolver`, которые описаны ниже:
- `SortHandlerMethodArgumentResolver` - **может извлекать информацию о сортировке из запроса** или из аннотации `@SortDefault`.
- `PageableHandlerMethodArgumentResolver` - **извлекает информацию о запрошенной странице** из запроса.

Теперь мы можем указать информацию запрошенной страницы и настроить параметры сортировки вызванного запроса к базе
данных, установив значения следующих параметров запроса:
- Параметр запроса страницы указывает номер запрошенной страницы. Номер первой страницы равен 0, и значение этого параметра запроса по умолчанию также равно 0.
- Параметр запроса размера указывает размер запрошенной страницы. Значение по умолчанию для этого параметра запроса - 20.
- Параметр запроса сортировки указывает параметры сортировки вызванного запроса. **Справочная документация Spring Data JPA описывает содержимое этого параметра** запроса следующим образом:
```
           "Свойства, по которым следует сортировать в свойстве property,property(,ASC|DESC). Направление сортировки
           по умолчанию - по возрастанию. Если хотите, используйте несколько параметров сортировки, для переключения
           направления, например ?sort=firstname&sort=lastname,asc."
```
После того, как мы включили веб-поддержку `Spring Data`, мы можем внедрить объекты `Pageable` в методы обработчика
контроллера. Исходный код класса `TodoSearchController`, использующего веб-поддержку `Spring Data`, выглядит следующим
образом:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
final class TodoSearchController {

    private final TodoSearchService searchService;

    @Autowired
    public TodoSearchController(TodoSearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/api/todo/search", method = RequestMethod.GET)
    public Page<TodoDTO> findBySearchTerm(@RequestParam("searchTerm") String searchTerm,
                                          Pageable pageRequest) {
        return searchService.findBySearchTerm(searchTerm, pageRequest);
    }
}
```

---
**Дополнительное чтение:**
- [Javadoc класса PageableHandlerMethodArgumentResolver](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/web/PageableHandlerMethodArgumentResolver.html)

---
`TodoSearchController` получает информацию о возвращенных записях задач из объекта `TodoSearchService`. Класс
`RepositoryTodoSearchService` реализует интерфейс `TodoSearchService`, а его метод `*.findBySearchTerm()` просто
передает поисковый запрос и объект Pageable вызываемому методу репозитория.

Исходный код класса `RepositoryTodoSearchService` выглядит следующим образом:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
final class RepositoryTodoSearchService implements TodoSearchService {

    private final TodoRepository repository;

    @Autowired
    public RepositoryTodoSearchService(TodoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TodoDTO> findBySearchTerm(String searchTerm, Pageable pageRequest) {
        /* Получение результатов поиска путем вызова предпочтительного метода репозитория. */
        Page<Todo> searchResultPage = ...

        return TodoMapper.mapEntityPageIntoDTOPage(pageRequest, searchResultPage);
    }
}
```

Давайте выясним, как можно разбить результаты запроса на страницы с помощью объектов Pageable.

---
#### Разбивка результатов запроса на страницы с помощью `Pageable` объекта

После того, как мы создали объект `Pageable` вручную или получили его с помощью веб-поддержки `Spring Data`, нам необходимо
создать запрос к базе данных, который разбивает результаты запроса на страницы с помощью объекта `Pageable`.

Давайте начнем с выяснения того, как мы можем разбить на страницы все объекты, найденные в базе данных.

---
#### Разбиение на страницы всех объектов

Если мы **хотим разбить на страницы все объекты, найденные в БД**, мы можем использовать один из следующих методов:
- **ВО-ПЕРВЫХ**, если мы создали интерфейс нашего репозитория путем расширения интерфейса `CrudRepository`, нам придется изменить его, чтобы расширить только интерфейс `PagingAndSortingRepository`.

Соответствующая часть интерфейса нашего репозитория выглядит следующим образом:

```java
import org.springframework.data.repository.PagingAndSortingRepository;

interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {

}
```

Интерфейс `PagingAndSortingRepository` объявляет один метод, который мы можем использовать, когда хотим разбить на страницы результаты запроса, извлекающего все объекты из базы данных:
- `Page <T> findAll(Pageable pageRequest)` - метод возвращает страницу сущностей, которые удовлетворяют ограничениям, заданным объектом `Pageable`.

Другими словами, если мы хотим разбить на страницы результаты запроса к базе данных, который извлекает все объекты
из базы данных, мы должны использовать метод `Page <T> findAll(Pageable pageRequest)` вместо метода `Iterable<T> findAll()`.

- **ВО-ВТОРЫХ**, если мы создали интерфейс репозитория путем расширения интерфейса `Repository`, мы можем объявить метод `Page<T> findAll(Pageable pageRequest)` в интерфейсе нашего репозитория.

Соответствующая часть интерфейса нашего репозитория выглядит следующим образом:

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface TodoRepository extends Repository<Todo, Long> {

    void delete(Todo deleted);

    Page<Todo> findAll(Pageable pageRequest);

    Optional<Todo> findOne(Long id);

    void flush();

    Todo save(Todo persisted);
}
```

Теперь **мы можем получить конкретную страницу, вызвав метод `Page<T> findAll(Pageable pageRequest)`** и передав объект
`Pageable` в качестве параметра метода.

Давайте выясним, как мы можем разбивать на страницы результаты запросов к базе данных, которые используют генерацию
запросов на основе стратегии имени метода.

---
### Разбиение на страницы результатов запросов, использующих генерацию запроса на основе стратегии имени метода

Если мы создаем запросы к БД на основе имени метода нашего метода запроса, мы можем разбить результаты запроса на страницы, выполнив следующие шаги:
- Шаг 1. - **Удалим логику сортировки из имени метода**.
- Шаг 2. - **Добавим новый параметр метода (объект `Pageable`)** в метод запроса.
- Шаг 3. - **Определим возвращаемый тип.** Мы можем возвращать объекты `List<T>` , `Slice<T>` или `Page<T>`.

Поскольку функция поиска в нашем примере приложения нечувствительна к регистру и возвращает записи задач, заголовок или
описание которых содержит заданный поисковый запрос, исходный код интерфейса нашего репозитория выглядит следующим
образом:

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.Repository;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    List<Todo> findByDescriptionContainsOrTitleContainsAllIgnoreCase(String descriptionPart,
                                                                     String titlePart,
                                                                     Pageable pageRequest);

    Page<Todo> findByDescriptionContainsOrTitleContainsAllIgnoreCase(String descriptionPart,
                                                                     String titlePart,
                                                                     Pageable pageReguest);

    Slice<Todo> findByDescriptionContainsOrTitleContainsAllIgnoreCase(String descriptionPart,
                                                                      String titlePart,
                                                                      Pageable pageRequest);
}
```

---
**Дополнительное чтение:**
- [Javadoc интерфейса Slice](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Slice.html)

---
Давайте выясним, **как мы можем разбивать на страницы результаты именованных запросов**, использующих **JPQL**.

---
#### Разбиение на страницы результатов именованных запросов, использующих JPQL

Мы можем **разбить на страницы результаты именованных запросов, использующих JPQL**, выполнив следующие шаги:
- Шаг 1. - **Укажем логику сортировки в запросе JPQL**.
- Шаг 2. - **Добавим новый параметр метода (объект `Pageable`)** в метод запроса.
- Шаг 3. - **Определим возвращаемый тип**. Мы можем возвращать объекты `List<T>` , `Slice<T>` или `Page<T>`.

Если мы хотим разбить результаты именованного запроса на страницы: `Todo.findBySearchTermNamed`, исходный код интерфейса нашего репозитория будет выглядеть следующим образом:

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.Repository;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    List<Todo> findBySearchTermNamed(@Param("searchTerm") String searchTerm, Pageable pageRequest);

    Page<Todo> findBySearchTermNamed(@Param("searchTerm") String searchTerm, Pageable pageRequest);

    Slice<Todo> findBySearchTermNamed(@Param("searchTerm") String searchTerm, Pageable pageRequest);
}
```

При разбиении на страницы результатов именованных запросов **мы должны помнить две вещи**:
- Если мы **хотим разбить на страницы и отсортировать** результаты именованных запросов, **использующих JPQL**, мы должны **указать логику сортировки в запросе JPQL**.
- Мы **не можем разбивать на страницы результаты собственных именованных запросов**, поскольку не существует надежного способа управления запросами SQL.

Давайте выясним, как мы можем разбивать на страницы результаты запросов **JPQL**, созданных с использованием аннотации `@Query`.

---
#### Разбиение на страницы результатов запроса `JPQL`, использующих аннотацию `@Query`

Если мы **создаем запросы `JPQL` с использованием аннотации `@Query`**, мы **можем разбивать результаты запроса** на страницы, выполнив следующие шаги:

- Шаг 1. - **Удалим логику сортировки** из запроса JPQL.
- Шаг 2. - **Добавим новый параметр метода** (объект Pageable) в метод запроса.
- Шаг 3. - **Определим возвращаемый тип.** Мы можем возвращать объекты `List<T>`, `Slice<T>` или `Page<T>`.

Поскольку функция поиска в нашем примере приложения нечувствительна к регистру и возвращает записи задач, заголовок
или описание которых содержит заданный поисковый запрос, **исходный код интерфейса нашего репозитория** выглядит следующим
образом:

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

interface TodoRepository extends Repository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    List<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm,
                                Pageable pageRequest);

    @Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm,
                                Pageable pageRequest);

    @Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Slice<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm,
                                 Pageable pageRequest);
}
```

Мы **не можем разбивать на страницы результаты нативных запросов, использующих аннотацию `@Query`**, поскольку не существует
надежного способа управления запросами SQL.

Давайте выясним, как мы можем разбивать на страницы результаты **JPA criteria queries.**

---
#### Разбиение на страницы результатов запросов используя `JPA Criteria Queries`

Если мы создаем запросы к БД с помощью JPA Criteria API, наш интерфейс репозитория должен расширять интерфейс
`JpaSpecificationExecutor<T>`. Этот интерфейс объявляет один метод, который мы можем использовать, когда хотим разбить на
страницы результаты запросов критериев JPA:

- `Page<T> findAll(Specification<T> spec, Pageable pageRequest)` - метод возвращает страницу сущностей, соответствующих объекту спецификации и удовлетворяющих ограничениям, заданным объектом `Pageable`.

Другими словами, мы можем разбить результаты запросов критериев JPA на страницы, используя метод
`Page<T> findAll(Specification<T> spec, Pageable pageRequest)` вместо метода `List<T> findAll(Specification<T> spec)`.

Исходный код класса `RepositoryTodoSearchService`, который разбивает результаты нашего запроса на страницы с помощью
объекта `Pageable`, выглядит следующим образом:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.petrikainulainen.springdata.jpa.todo.TodoSpecifications.titleOrDescriptionContainsIgnoreCase;

@Service
final class RepositoryTodoSearchService implements TodoSearchService {

    private final TodoRepository repository;

    @Autowired
    public RepositoryTodoSearchService(TodoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TodoDTO> findBySearchTerm(String searchTerm, Pageable pageRequest) {
        Specification<Todo> searchSpec = titleOrDescriptionContainsIgnoreCase(searchTerm);
        Page<Todo> searchResultPage = repository.findAll(searchSpec, pageRequest);

        return TodoMapper.mapEntityPageIntoDTOPage(pageRequest, searchResultPage);
    }
}
```

Давайте выясним, как мы можем разбивать на страницы результаты запросов к базе данных, созданных с помощью **QueryDsl**.

---
#### Разбиение на страницы результатов запроса `QueryDsl`

Если мы создаем запросы к базе данных с помощью `QueryDsl`, наш интерфейс репозитория должен расширять интерфейс `QueryDslPredicateExecutor<T>`.
Этот интерфейс объявляет один метод, который мы можем использовать, когда хотим разбить на страницы результаты запросов к базе данных, использующих Querydsl:

- `Page<T> findAll(Predicate predicate, Pageable pageRequest)` - метод возвращает страницу сущностей, соответствующих объекту `Predicate` и удовлетворяющих ограничениям,
заданным объектом `Pageable`.

Другими словами, мы можем разбивать результаты запросов QueryDsl на страницы, используя метод
`Page<T> findAll(Predicate predicate, Pageable pageRequest)` вместо метода `List<T> findAll(Predicate predicate)`.

Исходный код класса `RepositoryTodoSearchService`, который разбивает результаты нашего запроса на страницы с помощью объекта `Pageable`, выглядит следующим образом:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.petrikainulainen.springdata.jpa.todo.TodoPredicates.titleOrDescriptionContainsIgnoreCase;

@Service
final class RepositoryTodoSearchService implements TodoSearchService {

    private final TodoRepository repository;

    @Autowired
    public RepositoryTodoSearchService(TodoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TodoDTO> findBySearchTerm(String searchTerm, Pageable pageRequest) {
        Predicate searchPred = titleOrDescriptionContainsIgnoreCase(searchTerm);
        Page<Todo> searchResultPage = repository.findAll(searchPred, pageRequest);
        return TodoMapper.mapEntityPageIntoDTOPage(pageRequest, searchResultPage);
    }
}
```

---
### Подведем итог:
- Мы **можем создавать объекты** `Pageable` вручную или получать их с помощью веб-поддержки Spring Data.
- Мы **можем настроить веб-поддержку Spring Data**, аннотируя наш класс конфигурации контекста приложения аннотацией `@EnableSpringDataWebSupport`.
- Мы **можем разбивать на страницы результаты запросов** методов запроса, запросов `JPA Criteria API` и запросов `QueryDdsl`, используя объект `Pageable`.
- Мы **не можем разбивать результаты запросов SQL** на страницы с помощью объекта `Pageable`, поскольку **не существует надежного способа манипулирования существующими запросами SQL**.
- Если **мы хотим разбить на страницы результаты именованного запроса, использующего JPQL**, нам **необходимо добавить логику сортировки в запрос JPQL**.
