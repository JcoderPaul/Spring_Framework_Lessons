### JPA и Spring Data JPA

**Java Persistence API – это стандартная технология, позволяющая "отображать" объекты на реляционные базы данных.**
POM-модель spring-boot-starter-data-jpa позволяет быстро начать работу. Она обеспечивает следующие ключевые
зависимости:

- Hibernate одна из самых популярных реализаций JPA.
- Spring Data JPA: помогает реализовать репозитории на основе JPA.
- Spring ORM: основное средство поддержки ORM из Spring Framework.

Традиционно **классы "сущностей" JPA задаются в файле persistence.xml**. В **Spring Boot этот файл не требуется**, вместо
него **используется сканирование сущностей (Entity Scanning)**. По умолчанию **поиск выполняется во всех пакетах,
расположенных ниже основного конфигурационного класса (того, который аннотирован @EnableAutoConfiguration или
@SpringBootApplication)**.

**Учитываются любые классы, аннотированные @Entity, @Embeddable или @MappedSuperclass.** Типичный класс сущностей
похож на следующий пример:

on Java:
```Java
    import java.io.Serializable;
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.Id;
    
    @Entity
    public class City implements Serializable {
    
        @Id
        @GeneratedValue
        private Long id;
    
        @Column(nullable = false)
        private String name;
    
        @Column(nullable = false)
        private String state;
        // ... дополнительные члены, часто содержащие отображения аннотации @OneToMany
        protected City() {
            // конструктор без аргументов, требуемый спецификацией JPA
            // protected, поскольку не предназначен для использования напрямую
        }
    
        public City(String name, String state) {
            this.name = name;
            this.state = state;
        }
    
        public String getName() {
            return this.name;
        }
    
        public String getState() {
            return this.state;
        }
        // ... и т.д.
    }
```

on Kotlin:
```Kotlin
    import java.io.Serializable
    import javax.persistence.Column
    import javax.persistence.Entity
    import javax.persistence.GeneratedValue
    import javax.persistence.Id
    
    @Entity
    class City : Serializable {
    
        @Id
        @GeneratedValue
        private val id: Long? = null
    
        @Column(nullable = false)
        var name: String? = null
            private set
        // ... и т.д.
    
        @Column(nullable = false)
        var state: String? = null
            private set
        // ... дополнительные члены, часто содержащие отображения аннотации @OneToMany
        protected constructor() {
            // конструктор без аргументов, требуемый спецификацией JPA
            // protected, поскольку не предназначен для использования напрямую
        }
        constructor(name: String?, state: String?) {
            this.name = name
            this.state = state
        }
    }
```

---
**!!! Внимание !!!** 

Можно настроить местоположения, которые нужно просканировать на предмет наличия сущностей, с
помощью аннотации **@EntityScan, с параметром указывающим путь**.

---
### Репозитории Spring Data JPA

Репозитории **Spring Data JPA – это интерфейсы, которые можно определять для получения доступа к данным**.
**JPA-запросы создаются автоматически на основе имен методов**. Например, интерфейс CityRepository может
объявить метод findAllByState(String state) для поиска всех городов в данном штате.

В случае более сложных запросов можно аннотировать метод с помощью аннотации Query из Spring Data.

Репозитории Spring Data обычно расширяются за счет интерфейсов Repository или CrudRepository. Если вы
используете автоконфигурацию, поиск в репозиториях ведется от пакета, содержащего основной конфигурационный
класс (тот, который аннотирован @EnableAutoConfiguration или @SpringBootApplication), и вниз по иерархии.

В следующем примере показано типичное определение интерфейса взаимодействия с репозиторием Spring Data:

on Java:

```Java
    import org.springframework.boot.docs.data.sql.jpaandspringdata.entityclasses.City;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.repository.Repository;
    
    public interface CityRepository extends Repository<City, Long> {
        Page<City> findAll(Pageable pageable);
        City findByNameAndStateAllIgnoringCase(String name, String state);
    }
```

on Kotlin:

```Kotlin
    import org.springframework.boot.docs.data.sql.jpaandspringdata.entityclasses.City
    import org.springframework.data.domain.Page
    import org.springframework.data.domain.Pageable
    import org.springframework.data.repository.Repository
    
    interface CityRepository : Repository<City?, Long?> {
        fun findAll(pageable: Pageable?): Page<City?>?
        fun findByNameAndStateAllIgnoringCase(name: String?, state: String?): City?
    }
```

Репозитории Spring Data JPA поддерживают три различных режима начальной загрузки:

- **по умолчанию**;
- **с задержкой**;
- **отложенная**;

Чтобы активировать загрузку с задержкой или отложенную загрузку, установите свойство
**spring.data.jpa.repositories.bootstrap-mode в значение deferred или lazy соответственно**. При отложенной загрузке
или загрузке с задержкой, автоконфигурируемый EntityManagerFactoryBuilder будет использовать AsyncTaskExecutor
контекста, если таковой имеется, в качестве исполнителя загрузки. Если существует более одного исполнителя, будет
использоваться тот, который назван applicationTaskExecutor.

---
**!!! Внимание !!!** 

При загрузке с задержкой или отложенной загрузке следует убедиться, что доступ к инфраструктуре
JPA предоставляется с задержкой после этапа начальной загрузки контекста приложения. Можно
использовать SmartInitializingSingleton для вызова любой инициализации, которой требуется
инфраструктура JPA. Для компонентов JPA (таких, как конвертеры), которые создаются в виде
bean-ов Spring, используйте ObjectProvider, чтобы отсрочить разрешение зависимостей, если
таковые имеются.

---
### Репозитории Spring Data Envers

Если **доступен Spring Data Envers, JPA-репозитории будут автоматически конфигурироваться для обеспечения поддержки
типичных Envers-запросов**.

Чтобы использовать Spring Data Envers, **убедитесь, что репозиторий расширяется из RevisionRepository**, как показано
в ниже:

on Java:
```Java
    import org.springframework.boot.docs.data.sql.jpaandspringdata.entityclasses.Country;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.repository.Repository;
    import org.springframework.data.repository.history.RevisionRepository;
    
    public interface CountryRepository extends RevisionRepository<Country, Long, Integer>, Repository<Country, Long> {
        Page<Country> findAll(Pageable pageable);
    }
```

on Kotlin:
```Kotlin
    import org.springframework.boot.docs.data.sql.jpaandspringdata.entityclasses.Country
    import org.springframework.data.domain.Page
    import org.springframework.data.domain.Pageable
    import org.springframework.data.repository.Repository
    import org.springframework.data.repository.history.RevisionRepository
    
    interface CountryRepository :
            RevisionRepository<Country?, Long?, Int>,
            Repository<Country?, Long?> {
        fun findAll(pageable: Pageable?): Page<Country?>?
    }
```

---
### Создание и удаление баз данных JPA

По умолчанию базы данных JPA создаются автоматически, только если вы используете встроенную базу данных
(H2, HSQL или Derby). Можно явным образом сконфигурировать параметры JPA с помощью свойств spring.jpa.*.

Например, для создания и удаления таблиц можно добавить следующую строку в application.properties:

in Properties:

```
    spring.jpa.hibernate.ddl-auto=create-drop
```

in Yaml:

```YAML
spring:
  jpa:
    hibernate.ddl-auto: "create-drop"
```

---
**!!! Внимание !!!** 

Собственное внутреннее имя свойства Hibernate для этой процедуры (если вы, конечно, хорошо
запомните его) – hibernate.hbm2ddl.auto. Можете установить его наряду с другими нативными
свойствами Hibernate, используя spring.jpa.properties.* (префикс удаляется перед добавлением
в диспетчер сущностей).

В следующей строке показан пример установки свойств JPA для Hibernate:

in Properties:

```
    spring.jpa.properties.hibernate[globally_quoted_identifiers]=true
```

in Yaml:

```YAML
spring:
  jpa:
    properties:
      hibernate:
        "globally_quoted_identifiers": "true"
```

Строка в предыдущем примере передает значение true для свойства hibernate.globally_quoted_identifiers диспетчеру
сущностей Hibernate.

По умолчанию DDL-выполнение (или валидация) откладывается до запуска ApplicationContext. Существует также флаг
spring.jpa.generate-ddl, но он не используется, если активна автоконфигурация Hibernate, поскольку настройки
ddl-auto более точные.

---
### Открытие EntityManager в представлении

Если вы запускаете веб-приложение, Spring Boot по умолчанию регистрирует OpenEntityManagerInViewInterceptor для
применения шаблона открытия EntityManager в представлении (Open EntityManager in View), чтобы обеспечить
возможность отложенной загрузки в веб-представлениях. Если вам не требуется такая логика работы, то следует
установить spring.jpa.open-in-view в false в application.properties.
