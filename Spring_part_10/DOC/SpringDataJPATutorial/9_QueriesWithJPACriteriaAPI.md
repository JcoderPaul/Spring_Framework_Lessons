- [Исходник всего материала (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-four-jpa-criteria-queries/)
- [Исходники кода GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Creating Database Queries With the JPA Criteria API

В предыдущих разделах мы уже научились создавать статические запросы к базе данных с помощью Spring Data JPA. Однако
когда мы пишем реальные приложения, нам также необходимо иметь возможность создавать динамические запросы к базе данных.

В данном разделе мы изучим, как можно создавать динамические запросы к базе данных с помощью API критериев JPA.

---
### Создание классов статической мета-модели JPA

Статическая мета-модель состоит из классов, описывающих сущность, и встраиваемых классов, найденных в нашей модели
предметной области. Эти классы мета-модели обеспечивают статический доступ к метаданным, которые описывают атрибуты
наших классов модели предметной области.

Мы хотим использовать эти классы, потому что они дают нам возможность создавать типобезопасные `criteria queries`
(criteria запросы), но мы не хотим создавать их вручную.

К счастью, мы можем создавать эти классы автоматически, используя плагин процессора Maven и [генератор статических
мета-моделей JPA](https://docs.jboss.org/hibernate/orm/4.3/topical/html/metamodelgen/MetamodelGenerator.html). Мы
можем настроить эти инструменты, выполнив следующие шаги:
- Шаг 1. - Добавим объявление подключаемого модуля процессора Maven (версия ...) в раздел подключаемых модулей файла `pom.xml`.
- Шаг 2. - Настраиваем зависимости этого плагина и добавляем зависимость генератора статической мета-модели JPA (версия ...) в раздел зависимостей плагина.
- Шаг 3. - Создаем исполнителя, который вызывает цель обработчика плагина на этапе генерации источников жизненного цикла Maven по умолчанию.
- Шаг 4. - Убеждаемся, что плагин запускает только `org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor`. Этот
           обработчик аннотаций сканирует наши сущности и встраиваемые классы и создает статические классы
           мета-модели.

Конфигурация плагина процессора Maven выглядит следующим образом:

```xml
<plugin>
    <groupId>org.bsc.maven</groupId>
    <artifactId>maven-processor-plugin</artifactId>
    <version>2.2.4</version>
    <executions>
        <execution>
            <id>process</id>
            <goals>
                <goal>process</goal>
            </goals>
            <phase>generate-sources</phase>
            <configuration>
                <processors>
                    <processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
                </processors>
            </configuration>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-jpamodelgen</artifactId>
            <version>4.3.8.Final</version>
        </dependency>
    </dependencies>
</plugin>
```

---
**Дополнительное чтение:**
- [Hibernate Entity Manager 3.6 Справочное руководство: Глава 4 Мета-модель](https://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html/metamodel.html)
- [Введение в жизненный цикл сборки Maven](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- [Плагин процессора Maven — использование](http://bsorrentino.github.io/maven-annotation-plugin/usage.html)
- [Генератор статической мета-модели JPA](https://docs.jboss.org/hibernate/orm/4.3/topical/html/metamodelgen/MetamodelGenerator.html)

---
Когда мы компилируем наш проект, вызванный обработчик аннотаций создает классы статической мета-модели JPA в каталоге
`target/generated-sources/apt`. Поскольку наша модель предметной области (domain model) имеет только одну сущность, 
обработчик аннотаций создает только один класс с именем `Todo_`.

Исходный код класса Todo_ выглядит следующим образом:

```java
package net.petrikainulainen.springdata.jpa.todo;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Todo.class)
public abstract class Todo_ {

    public static volatile SingularAttribute<Todo, ZonedDateTime> creationTime;
    public static volatile SingularAttribute<Todo, String> createdByUser;
    public static volatile SingularAttribute<Todo, ZonedDateTime> modificationTime;
    public static volatile SingularAttribute<Todo, String> modifiedByUser;
    public static volatile SingularAttribute<Todo, String> description;
    public static volatile SingularAttribute<Todo, Long> id;
    public static volatile SingularAttribute<Todo, String> title;
    public static volatile SingularAttribute<Todo, Long> version;

}
```

Давайте выясним, как мы можем создавать запросы к базе данных с помощью JPA criteria API.

---
### Создание запросов к базе данных с помощью API criteria JPA

Мы **можем создавать запросы к базе данных** с помощью *API criteria JPA*, **выполнив следующие действия**:
- Шаг 1. **Изменим интерфейс репозитория для поддержки запросов**, использующих API criteria JPA.
- Шаг 2. **Укажем условия вызванного запроса** к базе данных.
- Шаг 3. **Вызовем запрос** к базе данных.

---
#### Шаг 1. - Изменение интерфейса репозитория

Интерфейс `JpaSpecificationExecutor <T>` объявляет методы, которые можно использовать для вызова запросов к базе данных,
использующих `API criteria JPA`. Этот интерфейс имеет один параметр типа `<T>`, который описывает тип запрашиваемой
сущности.

Другими словами, если нам нужно изменить интерфейс нашего репозитория для поддержки запросов к базе данных,
использующих API criteria JPA, мы должны выполнить следующие шаги:
- Шаг 1.1. - **Расширь интерфейс `JpaSpecificationExecutor<T>`**.
- Шаг 1.2. - **Установить тип управляемого объекта**.

**Пример:** Единственный репозиторий `Spring Data JPA` нашего примера приложения **TodoRepository** управляет объектами `Todo`. После того, как мы модифицировали этот репозиторий для поддержки `criteria queries`, его исходный код выглядит следующим образом:

```java
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

interface TodoRepository extends Repository<Todo, Long>, JpaSpecificationExecutor<Todo> {
}
```

После того как мы расширили интерфейс `JpaSpeciticationExecutor`, классы, использующие интерфейс нашего репозитория, получают доступ к следующим методам:
- `long count(Specification<T> spec)` - метод возвращает количество объектов, которые удовлетворяют условиям, заданным объектом `Specification <T>`, заданным в качестве параметра метода.
- `List <T> findAll(Specification<T> spec)` - метод возвращает объекты, которые удовлетворяют условиям, заданным объектом `Specification <T>`, заданным в качестве параметра метода.
- `T findOne(Specification<T> spec)` - метод возвращает объект, который удовлетворяет условиям, заданным объектом `Specification <T>`, заданным в качестве параметра метода.

Интерфейс `JpaSpecificationExecutor <T>` также объявляет два других метода, которые используются для сортировки и
разбиения на страницы объектов, соответствующих условиям, заданным объектом `Specification <T>`. Мы поговорим об этих
методах подробнее, когда научимся сортировать и разбивать на страницы результаты запросов.

---
**Дополнительное чтение:**
- [Javadoc интерфейса JpaSpecificationExecutor <T>](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaSpecificationExecutor.html)

---
Давайте выясним, как мы можем указать условия вызываемого запроса к базе данных.

---
#### Шаг 2. - Указание условий вызванного запроса к базе данных

Мы можем **указать условия вызванного запроса** к базе данных, **выполнив следующие шаги**:
- Шаг 2.1 - Создать новый объект Specification<T>.
- Шаг 2.2 - Установить тип запрашиваемой сущности как значение параметра типа `<T>`.
- Шаг 2.3 - Указать условия, реализовав метод `toPredicate()` интерфейса `Specification <T>`.

---
**Пример 1:** Если нам нужно создать `criteria` запрос, который возвращает объекты `Todo`, нам нужно создать следующую спецификацию:

```java
new Specification<Todo>() {
    @Override
    public Predicate toPredicate(Root<Todo> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {
        /* Create the query by using the JPA Criteria API */
    }
}
```

---
**Дополнительное чтение:**
- [Динамические типобезопасные запросы в JPA 2.0](https://developer.ibm.com/articles/j-typesafejpa/)
- [Javadoc интерфейса CriteriaBuilder](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/CriteriaBuilder.html)
- [Javadoc интерфейса CriteriaQuery](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/CriteriaQuery.html
- [Javadoc интерфейса Predicate](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/Predicate.html)
- [Javadoc интерфейса Root<X>](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/Root.html)
- [Javadoc интерфейса Specification<T>](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/domain/Specification.html)

---
Очевиден следующий вопрос: **Где нам следует создавать эти объекты `Specification <T>`?**

Существует мнение, что нам следует создавать объекты `Specification <T>`, используя классы `specification builder`, потому что:
- Мы можем поместить нашу логику генерации запросов в одно место. Другими словами, мы не засоряем исходный код наших сервисных классов (или других компонентов) логикой генерации запросов.
- Мы можем создавать многократно используемые спецификации и объединять их в классах, которые вызывают запросы к нашей базе данных.

---
**Пример 2:** Если нам нужно создать класс построитель спецификаций `specification builder class`, который конструирует объекты спецификации `<Todo>`, мы должны выполнить следующие шаги:
- Шаг 2.1 - Создать `final` класс `TodoSpecifications`. Имя этого класса не важно, но мы используем соглашение об именовании: [Имя запрашиваемого класса сущности]Specifications.
- Шаг 2.2 - Добавить `private` конструктор созданного класса. Это гарантирует, что никто не сможет создать экземпляр нашего класса строителя спецификаций `specification builder class`.
- Шаг 2.3 - Добавить в этот класс методы построения статических спецификаций. В нашем случае мы добавим в этот класс только один метод построения спецификаций `*.hasTitle(String title)` и реализуем его, возвращая новый объект `Specification <Todo>`.

Исходный код класса `TodoSpecifications` выглядит следующим образом:

```java
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

final class TodoSpecifications {

    private TodoSpecifications() {}

    static Specification<Todo> hasTitle(String title) {
        return new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                /* Create the query here */
            }
        }
    }
}
```

Если мы используем Java 8, мы можем очистить реализацию метода `hasTitle(String title)`, используя лямбда-выражения.

Исходный код нашего нового класс builder-a спецификаций выглядит следующим образом:

```java
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

final class TodoSpecifications {

    private TodoSpecifications() {}

    static Specification<Todo> hasTitle(String title) {
        return (root, query, cb) -> {
            /* Create query here */
        };
    }
}
```

Давайте выясним, как мы можем вызвать созданный запрос к базе данных.

---
#### Шаг 3. -  Вызов созданного запроса к базе данных

После того как мы указали условия вызванного запроса к базе данных, создав новый объект `Specification <T>`, мы можем
вызвать запрос к базе данных, используя методы, предоставляемые интерфейсом `JpaSpecificationExecutor <T>`.

---
Следующие примеры демонстрируют, как мы можем вызывать различные запросы к базе данных:

**Пример 1:** Если мы **хотим получить количество объектов `Todo`** с заголовком «foo», нам нужно создать и вызвать запрос к базе данных, используя этот код:

```java
Specification<Todo> spec = TodoSpecifications.hasTitle("foo");
long count =  repository.count(spec);
```

**Пример 2:** Если мы **хотим получить список объектов `Todo`** с заголовком «foo», нам нужно создать и вызвать запрос к базе данных, используя этот код:

```java
Specification<Todo> spec = TodoSpecifications.hasTitle("foo");
List<Todo> todoEntries =  repository.findAll(spec);
```

**Пример 3:** Если мы **хотим получить объект Todo** с заголовком «foo», нам нужно создать и вызвать запрос к базе данных, используя этот код:

```java
Specification<Todo> spec = TodoSpecifications.hasTitle("foo");
List<Todo> todoEntries =  repository.findOne(spec);
```

Если нам нужно создать новую спецификацию, которая объединяет существующие спецификации, нам не нужно добавлять новый
метод в наш класс построителя спецификаций. Мы можем просто объединить существующие спецификации, используя класс
`Specifications <T>`.

Следующие примеры демонстрируют, как мы можем использовать этот класс:

**Пример 4:** Если у нас есть спецификации *A* и *B*, и мы **хотим создать запрос** к базе данных, **который возвращает объекты Todo**,
          соответствующие спецификации *A* **И** спецификации *B*, мы можем объединить эти спецификации, используя следующий код:

```java
Specification<Todo> specA = ...
Specification<Todo> specB = ...
List<Todo> todoEntries =  repository.findAll(
    Specifications.where(specA).and(specB)
);
```

**Пример 5:** Если у нас есть спецификации *A* и *B*, и мы **хотим создать запрос** к базе данных, **который возвращает объекты Todo**,
          соответствующие спецификации *A* **ИЛИ** спецификации *B*, мы можем объединить эти спецификации, используя следующий код:

```java
Specification<Todo> specA = ...
Specification<Todo> specB = ...
Lis<Todo> todoEntries =  repository.findAll(
    Specifications.where(specA).or(specB)
);
```

**Пример 6:** Если у нас есть спецификации *A* и *B*, и мы **хотим создать запрос** к базе данных, **который возвращает объекты Todo**,
          соответствующие спецификации *A*, **НО НЕ** спецификации *B*, мы можем объединить эти спецификации, используя следующий код:

```java
Specification<Todo> specA = ...
Specification<Todo> specB = ...
List<Todo> searchResults = repository.findAll(
    Specifications.where(specA).and(
        Specifications.not(specB)
    )
);
```

---
**Дополнительное чтение:**
- [Javadoc класса Specifications<T>](https://docs.spring.io/spring-data/jpa/docs/1.4.3.RELEASE/api/org/springframework/data/jpa/domain/Specifications.html)
- [Справочное руководство Spring Data JPA: 4.5 Спецификации](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#specifications)

---
Давайте двинемся дальше и выясним, как можно реализовать функцию поиска.

---
### Реализация функции поиска

Мы можем реализовать нашу функцию поиска, выполнив следующие шаги:
- Шаг 1. - Изменим интерфейс нашего репозитория для поддержки `criteria queries`.
- Шаг 2. - Создадим класс строитель спецификаций, который создает объекты `Specification<Todo>`.
- Шаг 3. - Реализуем метод службы, который использует наш класс строитель спецификаций и вызывает созданные запросов к базе данных, используя наш интерфейс репозитория.

Начнем с изменения интерфейса нашего репозитория.

------------------------------------------------------------------------------------------------------------------------
#### Шаг 1. - Изменение интерфейса нашего репозитория

Мы можем внести необходимые изменения в интерфейс нашего репозитория, выполнив следующие шаги:
- Шаг 1.1 - Расширим интерфейс `JpaSpecificationExecutor<T>`.
- Шаг 1.2 - Зададим тип запрашиваемой сущности в `Todo`.

Исходный код интерфейса нашего репозитория выглядит следующим образом:

```java
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface TodoRepository extends Repository<Todo, Long>, JpaSpecificationExecutor<Todo> {

    void delete(Todo deleted);

    List<Todo> findAll();

    Optional<Todo> findOne(Long id);

    void flush();

    Todo save(Todo persisted);
}
```

Давайте создадим класс строитель **builder** спецификаций.

------------------------------------------------------------------------------------------------------------------------
#### Шаг 2. - Создание класса построителя спецификаций

Мы можем создать класс строитель спецификаций, который соответствует требованиям нашей функции поиска, выполнив следующие шаги:
- Шаг 2.1 - Создаем `final` класс строитель **builder** спецификаций и убеждаемся, что его экземпляр не может быть создан.
- Шаг 2.2 - Создаем `private` статический метод `getContainsLikePattern(String searchTerm)` и реализуйте его, следуя этим правилам:
            - Если `searchTerm` имеет **значение NULL или пустое**, вернем строку - `«%»`. Это гарантирует, что если поисковый 
              запрос не указан, наш класс строителя спецификаций создаст спецификацию, которая возвращает все записи задач.
            - Если **результат поиска не является нулевым или пустым**, преобразуем поисковый запрос в нижний регистр и
              **вернем аналогичный шаблон**, соответствующий требованиям нашей функции поиска.
- Шаг 2.3 - Добавим статический метод `titleOrDescriptionContainsIgnoreCase(String searchTerm)` в класс строитель
           спецификаций и установим для него тип возвращаемого значения `Specification<Todo>`.
- Шаг 2.4 - Реализуем этот метод, выполнив следующие действия:
            - Создадим объект `Specification <Todo>`, который выбирает записи задач, заголовок или описание которых содержит заданный поисковый запрос.
            - Вернем созданный объект `Specification <Todo>`.

**Исходный код** или наш класс **построителя спецификаций** выглядит следующим образом:

```java
import org.springframework.data.jpa.domain.Specification;

final class TodoSpecifications {

    private TodoSpecifications() {}

    static Specification<Todo> titleOrDescriptionContainsIgnoreCase(String searchTerm) {
        return (root, query, cb) -> {
            String containsLikePattern = getContainsLikePattern(searchTerm);
            return cb.or(
                    cb.like(cb.lower(root.<String>get(Todo_.title)), containsLikePattern),
                    cb.like(cb.lower(root.<String>get(Todo_.description)), containsLikePattern)
            );
        };
    }

    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }
}
```

Теперь реализуем метод службы, который создает и вызывает запрос к базе данных.

---
#### Шаг 3. - Реализация метода обслуживания

Первое, что нам нужно сделать, это создать интерфейс под названием `TodoSearchService`. Этот интерфейс объявляет один
метод под названием `findBySearchTerm()`. Этот метод принимает поисковый запрос в качестве параметра метода и возвращает
список объектов `TodoDTO`.

**Исходный код** интерфейса `TodoSearchService` выглядит следующим образом:

```java
import java.util.List;

public interface TodoSearchService {

    List<TodoDTO> findBySearchTerm(String searchTerm);
}
```

Мы можем **реализовать этот интерфейс**, выполнив следующие шаги:
- Шаг 3.1 - Создаем класс `RepositoryTodoSearchService`, реализуем интерфейс `TodoSearchService` и добавляем к классу аннотацию `@Service`.
- Шаг 3.2 - Добавляем `private final` поле `TodoRepository` в созданный класс.
- Шаг 3.3 - Создаем конструктор, который внедряет объект `TodoRepository` в созданное поле с помощью внедрения конструктора.
- Шаг 3.4 - Переопределяем метод `findBySearchTerm()`. Добавляем к методу аннотацию `@Transactional` и убеждаемся, что транзакция доступна только для чтения.
- Шаг 3.5 - Реализуем метод `findBySearchTerm()`, выполнив следующие действия:
            - Получим объект `Specification <Todo>`, вызвав статический метод `titleOrDescriptionContainsIgnoreCase()` класса `TodoSpecifications`.
            - Получим записи задач, заголовок или описание которых содержит заданный поисковый запрос, вызвав метод `findAll()` интерфейса `JpaSpecificationExecutor`. Передадим созданный объект `Specification<Todo>` в качестве
              параметра метода.
            - Преобразуем список объектов `Todo` в список объектов `TodoDTO` и вернем созданный список.

Исходный код нашего класса обслуживания выглядит следующим образом:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    public List<TodoDTO> findBySearchTerm(String searchTerm) {
        Specification<Todo> searchSpec = titleOrDescriptionContainsIgnoreCase(searchTerm);
        List<Todo> searchResults = repository.findAll(searchSpec);
        return TodoMapper.mapEntitiesIntoDTOs(searchResults);
    }
}
```

---
**Дополнительное чтение:**
- [Javadoc аннотации @Autowired](https://docs.spring.io/spring-framework/docs/4.1.x/javadoc-api/org/springframework/beans/factory/annotation/Autowired.html)
- [Javadoc аннотации @Service](https://docs.spring.io/spring-framework/docs/4.1.x/javadoc-api/org/springframework/stereotype/Service.html)
- [Справочное руководство Spring Framework: 12.5.6 Использование @Transactional](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#transaction-declarative-annotations)
- [Javadoc аннотации @Transactional](https://docs.spring.io/spring-framework/docs/4.1.x/javadoc-api/org/springframework/transaction/annotation/Transactional.html)

---
Давайте продолжим и **выясним, когда нам следует создавать запросы** к базе данных с помощью JPA Criteria API.

---
### Почему нам следует использовать JPA Criteria API?

Мы уже научились создавать запросы к базе данных, используя имена наших методов запроса, аннотацию `@Query` и именованные
запросы. Проблема этих методов генерации запросов заключается в том, что мы не можем использовать их, если нам нужно
создавать динамические запросы (т. е. запросы, которые не имеют постоянного количества условий).

Если нам нужно создать динамические запросы, нам придется создавать эти запросы программно, и использование
`JPA Criteria API` — один из способов сделать это.

**Плюсы** использования `JPA Criteria API`:
- Он **поддерживает динамические запросы**.
- Если у нас есть существующее приложение, которое использует JPA Criteria API, его легко реорганизовать для использования Spring Data JPA (если мы захотим).
- Это стандартный способ создания динамических запросов с помощью Java Persistence API (это не всегда имеет значение, но иногда важно).

К сожалению, у `JPA Criteria API` есть **одна большая проблема**:
- Очень **сложно реализовать сложные запросы и еще труднее их читать**.

Существует мнение, что мы должны использовать критериальные запросы только тогда, когда это абсолютно необходимо (и мы не можем использовать `QueryDsl`).

---
**Давайте подведем итог всему выше описанному:**
- Мы можем создать классы статической мета-модели JPA с помощью плагина процессора Maven.
- Если мы хотим вызывать запросы, использующие `JPA Criteria API`, интерфейс нашего репозитория должен расширять интерфейс `JpaSpecificationExecutor<T>`.
- Мы можем указать условия запросов к базе данных, создав новые объекты `Specification<T>`.
- Нам следует создать объекты `Specification<T>`, используя классы строителя спецификаций `specification builder classes`.
- Мы можем комбинировать объекты `Specification<T>`, используя методы, предоставляемые классом `Specications<T>`.
- Нам следует использовать `criteria queries` только тогда, когда у нас нет выбора.

[В следующей части описывается, как мы можем создавать запросы к базе данных с помощью Querydsl →](./10_QueriesWithQueryDsl.md)
