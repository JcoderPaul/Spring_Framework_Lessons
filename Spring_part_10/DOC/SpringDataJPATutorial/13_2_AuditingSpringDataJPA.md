- [Исходник всего материала (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-auditing-part-two/)
- [Исходник кода GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
- [См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial:Auditing - Part Two - Аудит - часть 2

[В предыдущей части](./13_1_AuditingSpringDataJPA.md) было описано, как мы можем использовать
инфраструктуру аудита Spring Data JPA для поиска ответа на вопрос: Когда объект X был создан и/или изменен?

В этой части описывается, как мы можем найти ответ на вопрос: **Кто создал и/или изменил сущность X?**

Мы изменим наш пример приложения, чтобы он хранил имя пользователя - аутентифицированного пользователя, который создал
новую запись задачи и обновил информацию в существующей записи задачи.

Начнем с создания компонента, который возвращает информацию об аутентифицированном пользователе.

---
#### Получение информации аутентифицированного пользователя

Инфраструктура аудита Spring Data JPA использует интерфейс `AuditorAware<T>`, когда ей необходимо получить информацию об
аутентифицированном пользователе. Интерфейс `AuditorAware` имеет один параметр типа `<T>`, который описывает тип поля
сущности, содержащего информацию аудита.

Поскольку нам **нужно создать класс**, который **возвращает имя аутентифицированного пользователя**, мы должны выполнить следующие шаги:
- **Шаг 1.** - **Создадим класс `UsernameAuditorAware` и реализуем интерфейс `AuditorAware`**. Поскольку мы хотим сохранить имя аутентифицированного пользователя `<String>`, мы должны установить значение параметра типа String.
- **Шаг 2.** - **Реализуем метод `getCurrentAuditor()`**, выполнив следующие действия:
            - Получим объект аутентификации из SecurityContext.
            - Возвращаем значение null, если аутентификация не найдена или найденная аутентификация не аутентифицирована.
            - Возвращаем имя пользователя, прошедшего проверку подлинности.

**Исходный код класса `UsernameAuditorAware`** выглядит следующим образом:

```java
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UsernameAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((User) authentication.getPrincipal()).getUsername();
    }
}
```

---
**Дополнительное чтение:**
- [Javadoc интерфейса AuditorAware<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/AuditorAware.html)
- [Javadoc интерфейса Authentication](https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/core/Authentication.html)
- [Javadoc интерфейса SecurityContext](https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/core/context/SecurityContext.html)
- [Javadoc класса SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/core/context/SecurityContextHolder.html)
- [Javadoc класса User](https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/core/userdetails/User.html)

---
Давайте выясним, как мы можем настроить контекст нашего примера приложения.

---
#### Настройка контекста приложения (Application Context)

Мы можем настроить контекст нашего приложения, внеся следующие изменения в класс конфигурации, который настраивает уровень персистентности нашего приложения:
- **Создадим метод `AuditorProvider()`**, который **возвращает объект `AuditorAware<String>`**.
- **Реализуем метод**, создав новый объект `UsernameAuditorAware`.
- **Аннотируем метод** аннотацией `@Bean`.
- **Включим поддержку аудита Spring Data JPA**, добавив к классу конфигурации аннотацию `@EnableJpaAuditing`.

Соответствующая часть класса `PersistenceContext` выглядит следующим образом:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(basePackages = {"net.petrikainulainen.springdata.jpa.todo"})
@EnableTransactionManagement
class PersistenceContext {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }

    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
        return new AuditingDateTimeProvider(dateTimeService);
    }
}
```

Поскольку мы объявили только один компонент `AuditorAware`, инфраструктура аудита находит его автоматически и использует,
когда ей необходимо установить информацию аутентифицированного пользователя в поля сохраненного или обновленного объекта
сущности. Если мы объявляем несколько компонентов `AuditorAware`, мы можем настроить используемый компонент, установив
значение атрибута `AuditorAwareRef` аннотации `@EnableJpaAuditing`.

Если мы **хотим использовать конфигурацию XML**, мы **можем включить поддержку аудита с помощью элемента аудита - "auditing
element"**. Взгляните на пример приложения, в котором есть работающий файл конфигурации XML.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa-1.0.xsd
http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">

    <!-- Настраиваем подключение к базе данных. -->
    <bean id="hikariConfig" class="com.zaxxer.hikari.HikariConfig">
        <property name="driverClassName" value="${db.driver}"/>
        <property name="jdbcUrl" value="${db.url}"/>
        <property name="username" value="${db.username}"/>
        <property name="password" value="${db.password}"/>
    </bean>

    <!-- Создаем компонент источника данных (datasource bean) -->
    <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
        <constructor-arg index="0" ref="hikariConfig"/>
    </bean>

    <!--
        Создаем компонент менеджера транзакций, который интегрирует используемый
        поставщик JPA с механизмом транзакций Spring.
    -->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <!-- Включаем управление транзакциями на основе аннотаций. -->
    <tx:annotation-driven/>

    <!-- Указываем настройки по умолчанию, совместимые с Hibernate. -->
    <bean id="hibernateJpaVendorAdapter" class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/>

    <!-- Создаем компонент (bean), который создает фабрику менеджера сущностей JPA. -->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="jpaVendorAdapter" ref="hibernateJpaVendorAdapter"/>
        <property name="packagesToScan" value="net.petrikainulainen.springdata.jpa.todo"/>
        <property name="jpaProperties">
            <props>
                <!--
                    Настраиваем используемый диалект базы данных. Это позволяет Hibernate
                    создавать SQL оптимизированный для используемой базы данных.
                -->
                <prop key="hibernate.dialect">${hibernate.dialect}</prop>

                <!--
                    Указываем действие, которое вызывается к базе данных когда
                    SessionFactory создается или закрывается.
                -->
                <prop key="hibernate.hbm2ddl.auto">${hibernate.hbm2ddl.auto}</prop>

                <!--
                    Настраиваем стратегию именования, которая используется при создании
                    Hibernate-ом новых объектов базы данных и элементов схемы.
                -->
                <prop key="hibernate.ejb.naming_strategy">${hibernate.ejb.naming_strategy}</prop>

                <!--
                    Если значение этого свойства истинно, Hibernate записывает
                    все SQL заявления на консоль.
                -->
                <prop key="hibernate.show_sql">${hibernate.show_sql}</prop>

                <!--
                   Если значение этого свойства истинно, Hibernate будет использовать
                   Prettyprint когда он записывает SQL в консоль.
                -->
                <prop key="hibernate.format_sql">${hibernate.format_sql}</prop>
            </props>
        </property>
    </bean>

    <bean id="auditingProvider" class="net.petrikainulainen.springdata.jpa.common.UsernameAuditorAware"/>

    <bean id="dateTimeProvider" class="net.petrikainulainen.springdata.jpa.common.AuditingDateTimeProvider">
        <constructor-arg index="0" ref="dateTimeService"/>
    </bean>

    <jpa:auditing auditor-aware-ref="auditingProvider" set-dates="true"/>
    <jpa:repositories base-package="net.petrikainulainen.springdata.jpa.todo"/>
</beans>
```

- [См. исходник на GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/query-methods/src/main/resources/applicationContext-persistence.xml)

---
**Дополнительное чтение:**
- [Справочное руководство Spring Data JPA: 4.8 Аудит](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#auditing)

---
Давайте двинемся дальше и внесем необходимые изменения в наш класс сущности.

---
#### Изменение нашего класса сущности

Нам **нужно внести следующие изменения в наш класс сущности - `<Todo>`**:
- Нам необходимо **убедиться, что значение поля createByUser установлено**, когда наша **сущность сохраняется в первый раз**.
- Нам необходимо **убедиться, что значение поля ModifiedByUser установлено**, когда наша **сущность сохраняется в первый раз, и обновляется** при обновлении информации о нашей сущности.

Мы **можем внести** эти изменения, **выполнив следующие шаги**:
- **Шаг 1.** - Добавим поле *createdByUser* в класс сущности, установим его тип `<String>` и выполним следующие действия:
            - Аннотируем поле аннотацией `@Column`. Настроим имя столбца базы данных - *create_by_user* и убедимся, что значение этого столбца не может быть нулевым.
            - Аннотируем поле аннотацией `@CreatedBy`. Это идентифицирует поле, содержащее информацию о пользователе, создавшем сущность.
- **Шаг 2.** - Добавим поле *modifiedByUser* в класс сущности, установим его тип `<String>` и выполним следующие действия:
            - Аннотируем поле аннотацией `@Column`. Настроим имя столбца базы данных - *modified_by_user* и убедимся, что значение этого столбца не может быть нулевым.
            - Аннотируем поле аннотацией `@LastModifiedBy`. Это идентифицирует поле, содержащее информацию о пользователе, который внес последние изменения в сущность.

Соответствующая часть класса *Todo* выглядит следующим образом:

```java
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
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

    @Column(name = "created_by_user", nullable = false)
    @CreatedBy
    private String createdByUser;

    @Column(name = "creation_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @CreatedDate
    private ZonedDateTime creationTime;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "modified_by_user", nullable = false)
    @LastModifiedBy
    private String modifiedByUser;

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

Обычно рекомендуется добавить поля аудита в абстрактный базовый класс. Причина, по которой мы не сделали этого здесь,
заключается в том, что в нашем примере приложения есть только одна сущность, и мы хотели сделать все как можно проще.

Если бы мы переместили эту информацию в абстрактный базовый класс, его исходный код выглядел бы следующим образом:

```java
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.MappedSuperClass

@EntityListeners(AuditingEntityListener.class)
@MappedSuperClass
public abstract class BaseEntity {

    @Column(name = "created_by_user", nullable = false)
    @CreatedBy
    private String createdByUser;

    @Column(name = "creation_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @CreatedDate
    private ZonedDateTime creationTime;

    @Column(name = "modified_by_user", nullable = false)
    @LastModifiedBy
    private String modifiedByUser;

    @Column(name = "modification_time")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @LastModifiedDate
    private ZonedDateTime modificationTime;
}
```

Если мы НЕ хотим использовать аннотации, наши сущности должны либо реализовать интерфейс `Auditable`, либо расширять класс `AbstractAuditable`:
- **Интерфейс `Auditable` объявляет методы** получения и установки для всех полей аудита.
- **Класс `AbstractAuditable` предоставляет реализации этих методов**, но его **недостатком является** то, что он создает **связь между нашими сущностями и данными Spring**.

---
**Дополнительное чтение:**
- [Javadoc аннотации @CreatedBy](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/CreatedBy.html)
- [Javadoc аннотации @LastModifiedBy](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/LastModifiedBy.html)

---
Давайте выясним, почему нам следует использовать поддержку аудита Spring Data JPA вместо методов обратного вызова - *callback*, указанных в `Java Persistence API`.

---
#### Почему нам следует использовать инфраструктуру аудита Spring Data JPA?

Если **мы хотим сохранить информацию о пользователе, который создал и обновил нашу сущность**, нам НЕ обязательно
использовать Spring Data JPA. Мы можем установить значения этих полей - *createByUser* и *modifiedByUser*, создав
методы обратного вызова *callback*, которые привязаны к событиям жизненного цикла сущности.

Исходный **код абстрактного базового класса, использующего этот метод**, выглядит следующим образом:

```java
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;
import javax.persistence.MappedSuperClass
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperClass
public abstract class BaseEntity {

    @Column(name = "created_by_user", nullable = false)
    @CreatedBy
    private String createdByUser;

    @Column(name = "modified_by_user", nullable = false)
    @LastModifiedBy
    private String modifiedByUser;


    @PrePersist
    public void prePersist() {
        String createdByUser = getUsernameOfAuthenticatedUser();
        this.createdByUser = createdByUser;
        this.modifiedByUser = createdByUser;
    }

    @PreUpdate
    public void preUpdate() {
        String modifiedByUser = getUsernameOfAuthenticatedUser();
        this.modifiedByUser = modifiedByUser;
    }

    private String getUsernameOfAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((User) authentication.getPrincipal()).getUsername();
    }
}
```

Несмотря на то, что **этот метод немного проще и понятнее**, чем использование инфраструктуры аудита Spring Data JPA, **есть две причины**, по которым нам следует рассмотреть возможность использования более сложного решения:
- **ВО-ПЕРВЫХ**, использование методов обратного вызова создает связь между нашим базовым классом (или классами сущностей) и Spring Security, но мы хотим этого избежать.
- **ВО-ВТОРЫХ**, если [нам нужно установить значения полей](./13_1_AuditingSpringDataJPA.md) времени создания и изменения, **И** мы решили использовать для этой цели инфраструктуру аудита Spring Data JPA, мы должны использовать ее для установки значений полей *createByUser* и *modifiedByUser*. В итоге, это не имеет смысла - "задавать информацию аудита, используя два разных механизма".

---
#### Подведем итог:
- Интерфейс `AuditorAware<T>` объявляет метод, который предоставляет информацию об аутентифицированном пользователе инфраструктуре аудита Spring Data JPA.
- Мы можем идентифицировать поля аудита, используя аннотации, устанавливать значения, реализуя интерфейс `Auditable`, ИЛИ расширять класс `AbstractAuditable`.
- Значения полей аудита проще установить с помощью методов обратного вызова, прикрепленных к событиям жизненного цикла сущности. Недостатком этого метода является то, что он создает связь между нашим абстрактным базовым классом (или классами сущностей) и Spring Security.

---
### Пояснения к статье и выводам в частности:

Использование аннотаций Spring Security (например, `@CreatedBy` или `@LastModifiedBy`) напрямую в базовых классах или сущностях — популярное, 
но спорное решение. На первый взгляд это удобно, но с точки зрения архитектуры такая связь создает несколько серьезных проблем.

Вот основные причины, почему жесткая привязка к Spring Security в слое доменных моделей считается «антипаттерном»:

#### 1. Нарушение принципа инверсии зависимостей (DIP)

Наш **Domain Layer** - (сущности) должен быть «чистым» и не знать о деталях реализации инфраструктуры:
- **Проблема:** Когда мы импортируем `org.springframework.security`, наша бизнес-логика начинает зависеть от фреймворка безопасности.
- **Последствие:** Если мы захотим сменить `Spring Security` на другое решение или использовать эти сущности в другом контексте (например, 
в консольной утилите или миграторе данных), нам придется тащить за собой весь контекст безопасности.

#### 2. Сложность модульного тестирования (Unit Testing)

Сущности должны легко тестироваться простым созданием объекта через `new`:
- **Проблема:** При аудите через `Spring Security` механизмы заполнения полей ожидают наличия `SecurityContextHolder`.
- **Последствие:** Чтобы **проверить простое сохранение сущности в тесте**, нам придется настраивать моки для `SecurityContext`, имитировать 
аутентифицированного пользователя и поднимать часть контекста Spring, что **превращает быстрый Unit-тест в тяжелый интеграционный**.

#### 3. Ограниченность контекста (The "System" User Problem)

Spring Security ориентирован на пользователя, пришедшего через HTTP-запрос или метод:
- **Проблема:** Что делать, если сущность обновляется системным процессом, фоновым планировщиком (Job) или при импорте из Kafka?
- **Последствие:** В таких случаях `SecurityContext` пуст. Нам придется либо «хакать» систему, искусственно создавая фейковых пользователей в контексте, либо смириться с тем, что поля аудита останутся пустыми.

#### 4. Смешивание ответственностей (SRP)

Сущность отвечает за хранение данных и бизнес-правила:
- **Проблема:** Определение того, **кто именно сейчас находится в системе** и как достать его ID — это **внешняя инфраструктурная задача**.
- **Последствие:** Логика получения пользователя «протекает» в модель данных, усложняя поддержку кода.

---
#### Как сделать лучше? (Рекомендуемый подход)

Вместо прямой связи лучше использовать интерфейс-прослойку. 

`Spring Data JPA` предоставляет для этого отличный механизм — `AuditorAware`:
            1. Создаем бин, реализующий `AuditorAware<T>`.
            2. Внутри этого бина обращаемся к `Spring Security`.
            3. Сущности будут знать только о том, что им нужно поле «Автор», но не будут знать, откуда оно берется.

Характеристика	Прямая связь (Bad)	AuditorAware (Good)
Зависимости сущности	Spring Security JAR	Только Spring Data JPA
Тестирование	Сложное (нужен SecurityContext)	Легкое (через мок бина)
Гибкость	Привязана к Web/Security	Работает в любом окружении
Хотите, я покажу пример реализации AuditorAware, который позволит вам отвязать сущности от Spring Security, сохранив при этом автоматический аудит?
