- [Исходник статьи (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-auditing-part-one/)
- [Исходники кода GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Auditing - Part One - Аудит сущностей - часть 1

Когда мы слышим слово «аудит», первое, что приходит на ум - это журнал аудита, содержащий каждую версию проверяемого
объекта. Внедрение журнала аудита - сложная задача, требующая много времени. К счастью, в большинстве случаев нам не
нужно этого делать.

Однако довольно часто **мы должны быть в состоянии ответить** на следующие вопросы:
- **Когда объект X был создан** и/или изменен?
- **Кто создал и/или изменил** сущность X?

Инфраструктура аудита Spring Data JPA помогает нам ответить на эти вопросы. Разберемся, как мы можем добавлять поля
времени создания и изменения в наши сущности и обновлять их с помощью инфраструктуры аудита Spring Data JPA.

Начнем с создания службы, возвращающей текущую дату и время.

---
#### Получение текущей даты и времени

Есть **две причины**, по которым нам следует **создать интерфейс**, который можно использовать **для получения текущей даты и
времени**. Этими причинами являются:
1. Мы хотим **создать две разные реализации для этого интерфейса**:
        - **Первая реализация используется нашим приложением** и возвращает текущую дату и время.
        - **Вторая реализация используется в наших интеграционных тестах** и всегда возвращает одну и ту же дату и время.
2. Если мы реализуем реальное приложение, скорее всего, другим нашим компонентам также потребуется получать текущую дату и время.

**Интерфейс DateTimeService** объявляет только один метод:
- `getCurrentDateAndTime()` - метод возвращает объект ZonedDateTime.

Исходный код интерфейса DateTimeService выглядит следующим образом:

```java
import java.time.ZonedDateTime;

public interface DateTimeService {

    ZonedDateTime getCurrentDateAndTime();
}
```

**Класс CurrentTimeDateTimeService реализует интерфейс DateTimeService**. Его метод `getCurrentDateAndTime()` просто
возвращает текущую дату и время.

Исходный код CurrentTimeDateTimeService выглядит следующим образом:

```java
import java.time.ZonedDateTime;

public class CurrentTimeDateTimeService implements DateTimeService {

    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        return ZonedDateTime.now();
    }
}
```

---
**Дополнительное чтение:**
- [Javadoc класса ZonedDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/ZonedDateTime.html)

---
Давайте выясним, как мы можем интегрировать наш сервис с инфраструктурой аудита Spring Data JPA.

---
#### Интеграция нашего сервиса с инфраструктурой аудита Spring Data JPA

Инфраструктура аудита `Spring Data JPA` использует интерфейс `DateTimeProvider`, когда ей необходимо получить текущую дату
и время. Это означает, что если мы хотим интегрировать нашу `DateTimeService` со Spring Data JPA, нам необходимо реализовать 
этот интерфейс.

Мы можем сделать это, выполнив следующие шаги:
- Шаг 1. **Создадим класс AuditingDateTimeProvider** и реализуем интерфейс `DateTimeProvider`.
- Шаг 2. **Добавим поле `DateTimeService` в созданный класс** и внедрим его с помощью конструктора.
- Шаг 3. **Реализуем метод `getNow()`**. Нам нужно получить текущую дату и время с помощью объекта `DateTimeService` и вернуть новый объект `GregorianCalendar`.

Исходный **код класса `AuditingDateTimeProvider` выглядит** следующим образом:

```java
import org.springframework.data.auditing.DateTimeProvider;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AuditingDateTimeProvider implements DateTimeProvider {

    private final DateTimeService dateTimeService;

    public AuditingDateTimeProvider(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @Override
    public Calendar getNow() {
        return GregorianCalendar.from(dateTimeService.getCurrentDateAndTime());
    }
}
````

---
**Дополнительное чтение:**
- [Javadoc интерфейса DateTimeProvider](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/auditing/DateTimeProvider.html)
- [Javadoc класса GregorianCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/GregorianCalendar.html)

---
Наш **следующий шаг — настроить контекст нашего приложения**. Давайте выясним, как мы можем это сделать.

---
### Настройка контекста приложения (Application Context)

- **ВО-ПЕРВЫХ**, нам нужно **создать bean-компонент DateTimeService**, который будет использоваться при запуске нашего приложения.
Мы должны объявить этот компонент в классе конфигурации контекста корневого приложения (или файле конфигурации XML). Поскольку он,
вероятно, используется более чем одним компонентом, и нам кажется, что класс конфигурации контекста корневого приложения (или файл
конфигурации XML) является естественным местом для такого bean-a.

Мы **можем создать этот компонент**, выполнив следующие шаги:
- Шаг 1. **Создадим метод currentTimeDateTimeService() и реализуем его**, вернув новый объект CurrentTimeDateTimeService.
- Шаг 2. **Аннотируем созданный метод** как `@Bean`.
- Шаг 3. **Добавим к методу аннотацию @Profile и установим для него значение Profiles.APPLICATION**. Это гарантирует, что этот компонент будет создан только при запуске нашего приложения.

Соответствующая часть класса `exampleApplicationContext` выглядит следующим образом:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan("net.petrikainulainen.springdata.jpa")
@Import({WebMvcContext.class, PersistenceContext.class})
public class ExampleApplicationContext {

    @Profile(Profiles.APPLICATION)
    @Bean
    DateTimeService currentTimeDateTimeService() {
        return new CurrentTimeDateTimeService();
    }
}
```

**Profiles - это простой класс, который определяет «легальные» профили Spring** нашего примера приложения.

---
**Дополнительное чтение:**
- [Spring Profiles](https://www.baeldung.com/spring-profiles)
- [Справочное руководство Spring Framework: 5.13.1 Профили определения компонентов (Bean Definition Profiles)](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#beans-definition-profiles)
- [Javadoc аннотации @Bean](https://docs.spring.io/spring-framework/docs/4.1.x/javadoc-api/org/springframework/context/annotation/Bean.html)
- [Javadoc аннотации @Profile](https://docs.spring.io/spring-framework/docs/4.1.x/javadoc-api/org/springframework/context/annotation/Profile.html)

---
- **ВО-ВТОРЫХ**, нам **нужно создать bean-компонент DateTimeProvider** и включить поддержку аудита Spring Data.

Мы **можем сделать это, внеся следующие изменения в класс конфигурации**, который настраивает уровень персистентности нашего примера приложения:
- Шаг 1. **Создадим метод dateTimeProvider()**, который возвращает объект DateTimeProvider и принимает объект DateTimeService в качестве параметра метода.
- Шаг 2. **Реализуем метод**, создав новый объект AuditingAwareDateTimeProvider.
- Шаг 3. **Аннотируем созданный метод** как @Bean.
- Шаг 4. **Добавим к классу конфигурации аннотацию `@EnableJpaAuditing`** и установите имя bean-компонента DateTimeProvider (dateTimeProvider) в качестве значения его атрибута dataTimeProviderRef.

Соответствующая часть класса PersistenceContext выглядит следующим образом:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(basePackages = {"net.petrikainulainen.springdata.jpa.todo"})
@EnableTransactionManagement
class PersistenceContext {

    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
        return new AuditingDateTimeProvider(dateTimeService);
    }
}
```

---
**Дополнительное чтение:**
- [Javadoc аннотации @EnableJpaAuditing](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/config/EnableJpaAuditing.html)

---
Давайте внесем необходимые изменения в наш класс сущности.

---
### Изменение нашего класса сущности

Нам нужно внести следующие изменения в наш класс сущности - **Todo**:
- Нам **необходимо убедиться, что значение поля CreationTime установлено**, когда наша **сущность сохраняется в первый раз**.
- Нам **необходимо убедиться, что значение поля modificationTime установлено**, когда наша **сущность сохраняется в первый раз, и обновляется** при обновлении информации о нашей сущности.

Мы можем внести эти изменения, выполнив следующие шаги:
- Шаг 1. **Добавим в поле CreationTime аннотацию @CreatedDate**. Это идентифицирует поле, значение которого устанавливается при первом сохранении объекта в базе данных.
- Шаг 2. **Добавим к полю модификаций аннотацию @LastModifiedDate**. Это идентифицирует поле, значение которого устанавливается, когда объект сохраняется в первый раз, и обновляется при обновлении информации об объекте.
- Шаг 3. **Аннотируем класс сущности аннотацией @EntityListeners** и установим для него значение `AuditingEntityListener.class`. Класс **`AuditingEntityListener` - это слушатель объектов JPA**, который обновляет информацию аудита объекта при его сохранении и обновлении.

Соответствующая **часть класса Todo выглядит** следующим образом:

```Java
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.ZonedDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "todos")
final class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @CreatedDate
    private ZonedDateTime creationTime;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "modification_time")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @LastModifiedDate
    private ZonedDateTime modificationTime;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Version
    private long version;
}
```

Обычно **рекомендуется добавить поля аудита в абстрактный базовый класс и пометить** его аннотацией **@EntityListener**.
Причина, по которой тут это не сделано, заключается в том, что в нашем примере приложения есть только одна сущность,
и мы хотели сделать все как можно проще.

Если бы мы переместили эту информацию в абстрактный базовый класс, его исходный код выглядел бы следующим образом:

```java
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperClass
import java.time.ZonedDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperClass
public abstract class BaseEntity {

    @Column(name = "creation_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @CreatedDate
    private ZonedDateTime creationTime;

    @Column(name = "modification_time")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @LastModifiedDate
    private ZonedDateTime modificationTime;
}
```

Если мы не хотим использовать аннотации, наши сущности должны либо реализовать интерфейс Auditable, либо расширить
класс AbstractAuditable. Интерфейс Auditable объявляет методы получения и установки для всех полей аудита. Класс
AbstractAuditable предоставляет реализации этих методов, но его недостатком является то, что он создает связь между
нашим сущностями и данными Spring.

---
**Дополнительное чтение:**
- [Javadoc интерфейса Auditable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Auditable.html)
- [Javadoc класса AbstractAuditable](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/domain/AbstractAuditable.html)
- [Javadoc класса AuditingEntityListener](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/domain/support/AuditingEntityListener.html)
- [Javadoc аннотации @CreatedDate](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/CreatedDate.html)
- [Javadoc аннотации @EntityListeners](https://docs.oracle.com/javaee/7/api/javax/persistence/EntityListeners.html)
- [Javadoc аннотации @LastModifiedDate](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/LastModifiedDate.html)
- [Javadoc аннотации @MapperSuperClass](https://docs.oracle.com/javaee/5/api/javax/persistence/MappedSuperclass.html)

---
Давайте выясним, почему нам следует использовать поддержку аудита Spring Data JPA вместо методов обратного вызова - **callback**, указанных в Java Persistence API.

---
### Почему нам следует использовать поддержку аудита Spring Data JPA?

Если нам нужно добавить поля времени создания и изменения в наши сущности, нам не нужно использовать Spring Data JPA.
Мы можем установить значения этих полей, создав методы обратного вызова, которые привязаны к событиям жизненного цикла
сущности.

Исходный код абстрактного базового класса, использующего этот метод, выглядит следующим образом:

```java
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperClass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

@MappedSuperClass
public abstract class BaseEntity {

    @Column(name = "creation_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    private ZonedDateTime creationTime;

    @Column(name = "modification_time")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    private ZonedDateTime modificationTime;

    @PrePersist
    public void prePersist() {
        ZonedDateTime now = ZonedDateTime.now();
        this.creationTime = now;
        this.modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationTime = ZonedDateTime.now();
    }
}
```

---
**Дополнительное чтение:**
- [События жизненного цикла JPA (JPA Lifecycle Events)](https://www.objectdb.com/java/jpa/persistence/event)
- [Javadoc аннотации @PrePersist](https://docs.oracle.com/javaee/7/api/javax/persistence/PrePersist.html)
- [Javadoc аннотации @PreUpdate](https://docs.oracle.com/javaee/7/api/javax/persistence/PreUpdate.html)

---
Этот метод намного проще, чем решение, использующее инфраструктуру аудита Spring Data JPA.

Возникает очевидный вопрос: Есть ли смысл использовать более сложное решение?

Есть **две причины, почему это имеет смысл**:
- Если нам нужно написать тесты, гарантирующие правильность времени создания и изменения, нам придется использовать
  инфраструктуру аудита Spring Data JPA, поскольку она дает нам возможность использовать DateTimeProvider, который
  всегда возвращает одну и ту же дату и время.
- Если нам нужно сохранить информацию о пользователе, который создал и/или изменил объект, мы должны также использовать
  Spring Data для установки времени создания и изменения. Просто нет смысла устанавливать информацию аудита объекта с
  помощью двух разных механизмов.

---
#### Подведем итог:
- **Мы можем создать собственный поставщик даты и времени**, реализовав интерфейс DateTimeProvider. Это полезно, поскольку дает нам возможность использовать другого поставщика для целей тестирования.
- **Мы можем идентифицировать поля времени создания и изменения**, используя аннотации, устанавливать значения, **реализуя интерфейс `Auditable`**, или **расширять класс `AbstractAuditable`**.
- Проще установить значения полей времени создания и изменения, используя события жизненного цикла объекта и методы обратного вызова - "callback", но бывают ситуации, когда нам следует использовать
  инфраструктуру аудита Spring Data JPA (хотя это более сложное решение).
