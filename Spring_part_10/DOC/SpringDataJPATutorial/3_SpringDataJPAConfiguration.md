- [Исходник всего материала (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-one-configuration/)
- [GitHub example](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Configuration (Конфигурирование)

После того, как **мы объявили необходимые зависимости в нашем POM-файле (build.gradle)**, нам **необходимо настроить контекст
нашего приложения Spring**. Так же мы должны **настроить** уровень персистентности приложения Spring, которое использует
**Spring Data JPA и Hibernate**.

---
### Настройка уровня персистентности приложения Spring

Мы можем **создать класс конфигурации**, который **настраивает уровень персистентности приложения Spring**, выполнив следующие
шаги:
- Создаем файл свойств, содержащий свойства, используемые классом конфигурации контекста (context configuration class) нашего приложения ([*.properties / application.yml](https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/main/resources/application.properties)).
- Настраиваем компонент источника данных ([Datasource bean](https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/main/java/net/petrikainulainen/spring/datajpa/config/ApplicationContext.java#L61)).
- Настраиваем фабричный компонент менеджера объектов ([Entity manager factory bean](https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/main/java/net/petrikainulainen/spring/datajpa/config/ApplicationContext.java#L82)).
- Настраиваем компонент менеджера транзакций ([Transaction manager bean](https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/main/java/net/petrikainulainen/spring/datajpa/config/ApplicationContext.java#L73)).
- Включаем управление транзакциями на основе аннотаций ([@EnableTransactionManagement](https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/main/java/net/petrikainulainen/spring/datajpa/config/ApplicationContext.java#L37)).
- Настраиваем Spring Data JPA.

Но прежде чем мы сможем начать, нам нужно создать класс конфигурации, который настраивает уровень персистентности нашего
приложения. Исходный код класса PersistenceContext выглядит следующим образом:

```java
@Configuration
class PersistenceContext {
    /* Configure the required beans here */
}
```

Начнем с создания файла свойств.

---
### Создание файла свойств

Часто нам хочется использовать немного отличающуюся конфигурацию в разных средах. Хороший способ сделать это - переместить
конфигурацию в файл свойств и использовать разные файлы свойств в разных средах.

Файл [application.properties](https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/main/resources/application.properties) 
содержит конфигурацию, которая используется для настройки нашего примера приложения. Мы можем создать этот файл свойств, выполнив следующие действия:

1. Настраиваем подключение к базе данных нашего приложения. Нам необходимо настроить имя класса драйвера JDBC, URL-адрес JDBC, имя пользователя базы данных и пароль пользователя базы данных.
2. Настраиваем Hibernate, выполнив следующие действия:
      - Настраиваем используемый диалект базы данных (h2, MySQL, PostgreSQL и т.д.).
      - Убеждаемся, что Hibernate создает базу данных при запуске нашего приложения и удаляет ее при закрытии нашего приложения.
      - Настраиваем стратегию именования, которая будет использоваться, когда Hibernate создает новые объекты базы данных и элементы схемы.
      - Настраиваем Hibernate так, чтобы НЕ записывать вызванные операторы SQL на консоль.
      - Убеждаемся, что если Hibernate записывает операторы SQL в консоль, он будет использовать Prettyprint.

Файл `application.properties` может выглядеть следующим образом:

```properties
#Database Configuration
db.driver=org.h2.Driver
db.url=jdbc:h2:mem:datajpa
db.username=sa
db.password=

#Hibernate Configuration
hibernate.dialect=org.hibernate.dialect.H2Dialect
hibernate.hbm2ddl.auto=create-drop
hibernate.ejb.naming_strategy=org.hibernate.cfg.ImprovedNamingStrategy
hibernate.show_sql=false
hibernate.format_sql=true
```

---
### Настройка компонента источника данных

Мы можем [настроить компонент источника данных](https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/custom-method-single-repo/src/main/java/net/petrikainulainen/springdata/jpa/config/PersistenceContext.java), выполнив следующие шаги:
- Убедится, что метод close() созданного объекта DataSource вызывается при закрытии контекста приложения.
- Настроить подключение к базе данных. Нам нужно установить имя класса драйвера JDBC, URL-адрес JDBC, имя пользователя базы данных и пароль пользователя базы данных.
- Создать новый объект HikariDataSource и вернуть созданный объект.

Метод, настраивающий компонент источника данных, выглядит следующим образом:

```java
@Configuration
class PersistenceContext {

    @Bean(destroyMethod = "close")
    DataSource dataSource(Environment env) {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("db.url"));
        dataSourceConfig.setUsername(env.getRequiredProperty("db.username"));
        dataSourceConfig.setPassword(env.getRequiredProperty("db.password"));

        return new HikariDataSource(dataSourceConfig);
    }

    /* Add the other beans here */
}
```

---
**!!! Помним !!! Что данная статья (информация) слегка устарела.**

---
**Дополнительное чтение:**
- [Javadoc интерфейса DataSource](https://docs.oracle.com/javase/7/docs/api/javax/sql/DataSource.html)
- [Справочное руководство Spring Framework: 14.3.1 Источник данных](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#jdbc-datasource)
- [Hikari CP Initialization](https://github.com/brettwooldridge/HikariCP#initialization)
- [Свойства конфигурации HikariCP](https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby)

---
### Настройка фабричного компонента Entity Manager

Мы можем **настроить фабричный компонент менеджера объектов (Entity Manager Factory Bean)**, выполнив следующие шаги:
- Создать новый объект `LocalContainerEntityManagerFactoryBean`. Нам нужно создать этот объект, потому что он создает JPA `EntityManagerFactory`.
- Настроить используемый источник данных.
- Настроить конкретную реализацию интерфейса `JpaVendorAdapter` для `Hibernate`. Он инициализирует нашу конфигурацию с настройками по умолчанию, совместимыми с `Hibernate`.
- Настроить пакеты, которые сканируются на наличие классов сущностей.
- Настроить свойства `JPA`, которые используются для предоставления дополнительной конфигурации используемому поставщику JPA.

Метод, который настраивает фабричный компонент менеджера объектов, выглядит следующим образом:

```java
@Configuration
class PersistenceContext {

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("net.petrikainulainen.springdata.jpa.todo");

        Properties jpaProperties = new Properties();

        /*
        Настраиваем используемый диалект базы данных. Это позволяет Hibernate
        создавать SQL запрос который оптимизирован для используемой базы данных.
        */
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));

        /*
        Указываем действие, которое вызывается к базе данных при переходе
        в режим гибернации. SessionFactory создается или закрывается.
        */
        jpaProperties.put("hibernate.hbm2ddl.auto",
                env.getRequiredProperty("hibernate.hbm2ddl.auto")
        );

        /*
        Настраиваем стратегию именования, которая используется при создании
        Hibernate новых объектов базы данных и элементов схемы
        */
        jpaProperties.put("hibernate.ejb.naming_strategy",
                env.getRequiredProperty("hibernate.ejb.naming_strategy")
        );

        /*
        Если значение этого свойства истинно, Hibernate
        записывает все SQL. Вывод в консоль.
        */
        jpaProperties.put("hibernate.show_sql",
                env.getRequiredProperty("hibernate.show_sql")
        );

        /*
        Если значение этого свойства истинно, Hibernate
        отформатирует SQL и это пишется в консоль.
        */
        jpaProperties.put("hibernate.format_sql",
                env.getRequiredProperty("hibernate.format_sql")
        );

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    /* Add the other beans here */
}
```

---
**Дополнительное чтение:**
- [Javadoc интерфейса EntityManagerFactory](https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManagerFactory.html)
- [Javadoc класса LocalContainerEntityManagerFactoryBean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/LocalContainerEntityManagerFactoryBean.html)
- [Javadoc интерфейса JpaVendorAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/JpaVendorAdapter.html)
- [Javadoc класса HibernateJpaVendorAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/vendor/HibernateJpaVendorAdapter.html)
- [Справочное руководство Spring Framework: 15.5.1 Три варианта установки JPA в среде Spring](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#orm-jpa-setup)
- [Справочное руководство Hibernate ORM: 3.4. Дополнительные свойства конфигурации](https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#configuration-optional)

---
### Настройка компонента диспетчера транзакций

Поскольку **мы используем JPA**, нам **необходимо создать компонент менеджера транзакций (Transaction Manager Bean)**, который интегрирует поставщика JPA
с механизмом транзакций Spring. Мы можем сделать это, используя класс `JpaTransactionManager` в качестве менеджера транзакций нашего приложения.

Мы можем настроить компонент менеджера транзакций, выполнив следующие шаги:
- Создать новый объект `JpaTransactionManager`.
- Настроить фабрику менеджера сущностей (entity manager factory), транзакции которой управляются созданным объектом `JpaTransactionManager`.

Метод, настраивающий компонент менеджера транзакций, выглядит следующим образом:

```java
@Configuration
class PersistenceContext {

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /* Add the other beans here */
}
```

---
**Дополнительное чтение:**
- [Javadoc класса JpaTransactionManager](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/JpaTransactionManager.html)
- [Справочное руководство Spring Framework: 15.5.3 Управление транзакциями](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#orm-jpa-tx)

---
### Включение управления транзакциями на основе аннотаций

Мы можем **включить управление транзакциями на основе аннотаций**, аннотируя класс `PersistenceContext` аннотацией
**@EnableTransactionManagement**. Соответствующая часть класса `PersistenceContext` выглядит следующим образом:

```java
@Configuration
@EnableTransactionManagement
class PersistenceContext {

    /* The beans are configured here */
}
```

---
**Дополнительное чтение:**
- [Javadoc аннотации @EnableTransactionManagement](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/EnableTransactionManagement.html)
- [Справочное руководство Spring Framework: 12.5.6 Использование @Transactional](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#transaction-declarative-annotations)

---
### Настройка Spring Data JPA

Мы можем настроить `Spring Data JPA`, выполнив следующие шаги:
- Включим `Spring Data JPA`, добавив к классу `PersistenceContext` аннотацию **@EnableJpaRepositories**.
- Настроим базовые пакеты, которые сканируются, когда `Spring Data JPA` создает реализации для интерфейсов нашего репозитория.

Соответствующая часть класса `PersistenceContext` выглядит следующим образом:

```java
@Configuration
@EnableJpaRepositories(basePackages = {
        "net.petrikainulainen.springdata.jpa.todo"
})
@EnableTransactionManagement
class PersistenceContext {

    /* The beans are configured here */
}
```

---
Дополнительное чтение:
- [Javadoc аннотации @EnableJpaRepositories](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/config/EnableJpaRepositories.html)
- [Справочное руководство Spring Data JPA: 3.5. Создание экземпляров репозитория](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.create-instances)

---
Мы настроили уровень персистентности нашего примера приложения.

**В итоге:**

- Если нам нужно использовать другую конфигурацию в другой среде, нам следует переместить эту конфигурацию в файл свойств.
- Мы научились настраивать уровень персистентности приложения Spring (в Spring Boot немного по-другому), которое использует Spring Data JPA и Hibernate.

[Как мы можем создать JPA-репозиторий Spring Data, который предоставляет операции CRUD для простой сущности см. →](./4_SpringDataJPACRUD.md)

---
[Пример приложения из этой статьи можно получить на Github](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master/query-methods)
