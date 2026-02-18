### Spring Boot lessons part 10 - Data JPA Repositories

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/DOC) и др. полезные файлы.

---
**Док. (ссылки) для изучения:**
- [PostgreSQL Documentation](https://www.postgresql.org/docs/) ;
- [Docker Documentation](https://docs.docker.com/) ;
- [Hibernate](https://hibernate.org/) ;
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) ;

---
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

---
Для начала проведем предварительную подготовку (первые 3-и шага из предыдущих частей):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/build.gradle) добавим необходимые plugin-ы: 
```
    /* 
       Плагин Spring Boot добавляет необходимые задачи в Gradle 
       и имеет обширную взаимосвязь с другими plugin-ами.
    */
    id 'org.springframework.boot' version '3.1.3'
    /* 
       Менеджер зависимостей позволяет решать проблемы несовместимости 
       различных версий и модулей Spring-а
    */
    id "io.spring.dependency-management" version '1.0.11.RELEASE'
    /* Подключим Lombok */
    id "io.freefair.lombok" version "8.3"
```
Шаг 2. - подключаем Spring Boot starter:
```
    /* 
       Подключим Spring Boot Starter он включает поддержку 
       авто-конфигурации, логирование и YAML
    */
    implementation 'org.springframework.boot:spring-boot-starter'
```
Шаг 3. - подключаем блок тестирования (Spring Boot Starter Test) 
(он будет активен на этапе тестирования):
```
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
```
Шаг 4. - автоматически Gradle создал тестовую зависимость на Junit5
(мы можем использовать как Junit4, так и TestNG):
```
    test {
        useJUnitPlatform()
    }
```
Шаг 5. - подключим блок для работы с БД (Spring Boot Starter Data Jpa):
```
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
```

---

    !!! НЕ ЗАБЫВАЕМ !!! У нас есть классы (см. ConnectionPool.java и комментарии), где мы пытаемся внедрить параметры в 
    поля через аннотации, с использованием аннатационного же конструктора @RequiredArgsConstructor. Фокус не пройдет без 
    создания и настройки файла конфигурации: lombok.config - 'контекст' просто завалится. 

    Либо все делаем руками от начала и до конца, либо помним какие вспомогательные средства используем и какие их особенности
    могут повлиять на работу приложения.

---

Шаг 6. - Для использования средств подобных Hibernate ENVERS подключим такую же поддержку от Spring (начиная с Lesson_59):
```
    implementation 'org.springframework.data:spring-data-envers'
```
Еще раз повторим что такое Spring Data - [Spring Data](./DOC/SpringData.md)

---
#### [Lesson 47](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_47) - Repository.

[Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html) предоставляет кроме авто-конфигурации и 
управления транзакциями еще и автоматическое предоставление DAO-слоя через интерфейс Repository. 

[Repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html) - это интерфейс-маркер центрального репозитория. Он не имеет методов и говорит нам о том, что некие классы,
в нашем случае это [AutoConfiguration](https://docs.spring.io/spring-boot/docs/current/reference/html/auto-configuration-classes.html#appendix.auto-configuration-classes.core) при встрече с Repository Interface будет обрабатывать его особым образом. В данном случае это создание слоя Repository. 

И так, [Repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html) параметризирован (захватывает) тип области (сущности), которой нужно управлять, а также тип идентификатора той самой области (сущности, т.е. в нашем случае см. [CompanyRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/company_repository/CompanyRepository.java) - у нас есть сущность [Company](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/Company.java) и у нее есть ее - ID). 
Общая цель - хранить информацию о типе, а также иметь возможность обнаруживать интерфейсы, расширяющие этот интерфейс, во время сканирования пути к классам для упрощения создания bean-компонентов Spring.

Репозитории области, расширяющие этот интерфейс, могут выборочно предоставлять методы CRUD, просто объявляя методы с той 
же сигнатурой, что и те, которые объявлены в [CrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html).
```
        Пакет: org.springframework.data.repository
        
        Interface Repository<T,ID>
        
        Типы параметров:
        T - тип области управляемой репозиторием (у нас, например: Company, User и т.д.);
        ID - тип ID управляемой репозиторием области (у нас, например, ID Company - Integer);
```
Теперь перепишем наш [CompanyRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/company_repository/CompanyRepository.java), который стал интерфейсом.

Фактически реализацию всех его методов берет на себя Spring. И тогда нам уже не нужен наш собственный 
[CrudRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_9/src/main/java/spring/oldboy/database/repository/CrudRepository.java) - его мы удаляем и все упоминания (зависимости от) о нем в других классах (тестах).

Чтобы проверить наш новый [CompanyRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/company_repository/CompanyRepository.java) и как он работает, создадим тестовый метод deleteCompanyTest() в 
[CompanyRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_47/CompanyRepositoryTest.java) (см. комментарии).

Всей этой магией занимается [JpaRepositoriesAutoConfiguration](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/data/jpa/JpaRepositoriesAutoConfiguration.java), который создан, для того чтобы искать все интерфейсы
(классы) Repository и на их основании создавать конкретные прокси реализации.
```java    
        /* Автоматическая настройка репозиториев JPA Spring Data.
    
        Активируется, когда в контексте настроен bean-компонент типа DataSource, 
        тип Spring Data JPA JpaRepository находится в пути к классам и нет другого 
        существующего настроенного JpaRepository.
    
        После вступления в силу, автоматическая настройка эквивалентна включению 
        репозиториев JPA с использованием аннотации - @EnableJpaRepositories.
        */
    
        @AutoConfiguration(after = { HibernateJpaAutoConfiguration.class, TaskExecutionAutoConfiguration.class })
        @ConditionalOnBean(DataSource.class)
        @ConditionalOnClass(JpaRepository.class)
        @ConditionalOnMissingBean({ JpaRepositoryFactoryBean.class, JpaRepositoryConfigExtension.class })
        /* Настройки который мы можем менять */
        @ConditionalOnProperty(prefix = "spring.data.jpa.repositories", 
                               name = "enabled", 
                               havingValue = "true",
                               matchIfMissing = true) 
        @Import(JpaRepositoriesImportSelector.class)
        public class JpaRepositoriesAutoConfiguration {
            /* code */
        }
```
Поскольку мы используем Spring Boot, то и не используем явно аннотацию @EnableJpaRepositories.

На основании авто-конфигурации создание Bean зависимостей происходит в [AbstractRepositoryConfigurationSourceSupport](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/data/AbstractRepositoryConfigurationSourceSupport.java)
в котором есть нужные методы registerBeanDefinitions(). И как уже ранее описывалось все bean-ы в цикле проходят процесс
создания и внедрения всех нужных зависимостей.

Несложно догадаться, что работающие 'пустые' методы работают не просто так, весь код реализуется Spring-ом через прокси 
объекты с использованием элементов АОП - аспектно ориентированного программирования.

---
**Док. для изучения:**
- [Пакет org.springframework.data.repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/package-summary.html) ;
- [Working with Spring Data Repositories](https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html) ;
- [Class JpaRepositoriesAutoConfiguration](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/data/jpa/JpaRepositoriesAutoConfiguration.html) ;
- [Class AbstractRepositoryConfigurationSourceSupport](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/data/AbstractRepositoryConfigurationSourceSupport.html) ;

---
#### [Lesson 48](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_48) - Создание запросов к базе данных исходя из имен методов.

Принципы работы при создании имен методов эквивалентных генерируемым запросам к БД в наших репозиториях описан в -
[Creating Database Queries From Method Names](./DOC/SpringDataJPATutorial/6_QueriesFromMethodNames.md), и естественно в официальной документации к Spring-у:
[JPA Repositories Query methods](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.query-methods).

- [MyCompanyRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/company_repository/MyCompanyRepository.java) - пример репозитория использующего имена методов для формирования запросов к БД.
- [MyCompanyRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_48/MyCompanyRepositoryTest.java) - интеграционный тест для проверки работы запросов нашего репозитория. 

Результат теста, а точнее то, что мы увидим в консоли зависит от настроек запроса к БД. Если наша сущность
[Company](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/Company.java) получит именованный запрос с именем один в один, как в методе репозитория см. ниже, то вид самих запросов сгенерированных Hibernate изменятся - можно на время закомментировать аннотацию [@NamedQuery](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/persistence/NamedQuery.html) в
сущности Company.

---
**См. док.:**
- [Пакет javax.persistence](https://docs.oracle.com/javaee/7/api/index.html) ;

---
#### Lesson 49 - NamedQuery (теория).

Стратегия генерации запросов описанная выше, проста, изящна, но имеет следующие недостатки:
- Особенности парсера имен методов определяют, какие запросы мы можем создавать. Если анализатор имени метода не
  поддерживает необходимое ключевое слово, мы не сможем использовать эту стратегию.
- Имена методов сложных запросов длинные и некрасивые.
- Нет поддержки динамических запросов.

[См. "Creating Database Queries From Method Names"](./DOC/SpringDataJPATutorial/6_QueriesFromMethodNames.md)

Применение именованных запросов тоже не лишено недостатков, но это еще один способ создания запросов к БД.
См. ["Creating Database Queries With Named Queries"](./DOC/SpringDataJPATutorial/8_QueriesWithNamedQueries.md)

При этом мы помним, что Spring JPA используем весь функционал Hibernate, а значит мы можем им воспользоваться.

Особенность именованных запросов в том что они имеют преимущество перед PartTreeJpaQuery, рассмотренные выше,
т.е. если у двух запросов будет одинаковое название, то первым в работу пойдет именованный запрос (NamedQuery).

Внесем изменения в нашу сущность Company см. [Company.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/Company.java) - добавим аннотацию @NamedQuery и настроим ее.

Поскольку мы внесли изменения в сущность Company, то при запуске тестов, а они точно такие же что и в предыдущем
задании отработают, но вид запросов Hibernate будет другим в части запроса 'findCompanyByName':
```text    
        Hibernate:
          select
            c1_0.id,
            c1_0.name
          from
            company c1_0
          where
            lower(c1_0.name)=lower(?)
```
Еще пример именованных запросов: 
[Hibernate_part_4/src/main/java/oldboy/lesson_19/entity_19/Employee.java](https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_4/src/main/java/oldboy/lesson_19/entity_19/Employee.java)

В сущности Company в запросе 'findCompanyByNameWithParam' и соответственно у одноименного запроса в 
`MyCompanyRepository.java` применяется аннотация [@Param](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/Param.html).

---
#### [Lesson 50](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_50) - Аннотация [@Query](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Query.html).

Создадим копию нашего CompanyRepository и назовем его [SecondCompanyRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/company_repository/SecondCompanyRepository.java) в нем мы применим аннотацию 
с прописанным в ней HQL запросом:
```java
        @Query("select c from Company c " +
               "join fetch c.locales cl " +
               "where c.name = :name")
```
Данная аннотация над методом `*.findCompanyByName(String name)`, который может работать на основе генерации запроса по его 
имени или за счет именованного запроса, сразу захватывает приоритет и запрос внутри нее выполняется безоговорочно, даже 
если имя запроса в репозитории совпадает с именем именованного запроса в классе, в нашем случае Company.

- [SecondCompanyRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_50/SecondCompanyRepositoryTest.java) - тестовый класс для проверки методов репозитория [SecondCompanyRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/company_repository/SecondCompanyRepository.java).

- [UserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java) - переписали наш класс в интерфейс и расширили JpaRepository, именно в нем мы еще раз применим аннотацию @Query и распишем HQL и нативные SQL запросы:
```java
        @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",
        nativeQuery = true)
```
Еще больше примеров и разъяснений cм. ["Creating Database Queries With the @Query Annotation"](./DOC/SpringDataJPATutorial/7_QueriesWithAnnotation.md)

---
#### [Lesson 51](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_51) - Модификация данных через запросы и использование аннотации @Modifying.

И так мы внесли изменения в [UserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java), как и в примерах выше сделали из него интерфейс и расширили [JpaRepository<User, Long>](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html),
но если в первом случае мы в качестве сущности управления использовали Company - JpaRepository<Company, Integer> и ID - Integer, в случае с User это ID - Long, см. ["Interface JpaRepository<T,ID>"](./DOC/RepositoryInterfaceAndClass/JpaRepository.md)

- [UserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java) - интерфейс в котором мы опробуем функционал модификации данных в БД на примере метода и аннотаций: 
```java
        @Modifying
        @Query("update User u " +
               "set u.role = :role " +
               "where u.id in (:ids)")
        int updateRole(Role role, Long... ids);
```
Более подробные комментарии см. внутри самого репозитория. В данном случае применялась аннотация [@Modifying](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Modifying.html) без параметров.

В простых случаях этого обычно достаточно, однако если у нас возникнет, например, потребность в проверке измененных данных
возникают некие затруднения при работе с персистентным контекстом, которые мы попытались обойти в следующем варианте данного 
метода во второй версии нашего репозитория [SecondUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/SecondUserRepository.java).

- [SecondUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/SecondUserRepository.java) - интерфейс в котором аннотация [@Modifying](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Modifying.html) метода *.updateRole(Role role, Long... ids) приобретает
два параметра см. ["Annotation Interface Modifying"](./DOC/ModifyingAnnotationInterface.md), что позволяет нам в некоторой степени управлять состоянием персистивного контекста и избежать большинства исключений:
```java
        @Modifying(clearAutomatically = true,
                   flushAutomatically = true)
        @Query("update User u " +
               "set u.role = :role " +
               "where u.id in (:ids)")
        int updateRole(Role role, Long... ids);
```
Тестовые классы с пояснениями в комментариях:
- [UserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_50/UserRepositoryTest.java)
- [SecondUserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_51/SecondUserRepositoryTest.java)

См. док.:
- [Пакет org.springframework.data.jpa.repository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/package-summary.html) ;

________________________________________________________________________________________________________________________
#### [Lesson 52](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_52) - Специальные параметры в запросах, интерфейс [Pageable](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Pageable.html) и [класс Sort](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Sort.html).

При создании запросов способом парсинга названия метода самого запроса, для опоры при составлении имени метода,
приходится использовать две важных таблицы, из документации по [Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/):
- [Таблица 4. Поддерживаемые ключевые слова внутри имен методов, раздел 4.3.2. Создание запроса](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.query-methods.query-creation) ;
- [Таблица 8. Ключевые слова запроса, приложение C. Ключевые слова запроса к репозиторию](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repository-query-keywords) ;
  
Т.е. мы должны использовать некие легко обрабатываемые регулярными выражениями ключевые слова. И естественно, мы можем в
названиях запросов передавать еще и параметры:
- Например, мы хотим найти первый элемент из нашей выборки (в зависимости от БД мы можем использовать ключевые слова 
First или Top), а также хотим отсортировать нашу выборку (OrderBy) по убыванию (Desc), и тогда, название запроса будет 
выглядеть как-то так - findTopByOrderByIdDesc, см. [SecondUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/SecondUserRepository.java) и тестовый метод [UserRepositoryThirdTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_52/UserRepositoryThirdTest.java)
- Например, мы хотим получить уже не один результат запроса, а коллекцию из нескольких значений на наш запрос, допустим
найти первые три User сущности, у которых день рождения был ранее некой даты - findTop3ByBirthDateBefore, далее мы снова
хотим отсортировать набор данных по-убыванию - OrderByBirthDateDesc. И мы получаем очень длинное имя *.findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate birthDate), 
которое, в принципе, легко читается и примерно понятно, что должен делать данный метод, но хочется сделать его название 
короче. В параметре метода мы передаем ограничивающую запрос дату. И в консоли мы видим:
```text
        Hibernate:
        select
          u1_0.id,
          u1_0.birth_date,
          u1_0.company_id,
          u1_0.firstname,
          u1_0.lastname,
          u1_0.role,
          u1_0.username
        from
          users u1_0
        where
          u1_0.birth_date<? /* ? - переданный параметр даты */
        order by
          u1_0.birth_date desc fetch first ? rows only
```
- Естественно при таких запросах нам бы хотелось придать некую динамику нашему запросу, чтобы каждый раз не писать новый
метод под новый запрос. Например, в нашем предыдущем запросе мы могли бы передать в метод не один, а два параметра, одним
из которых будет параметр класса [Sort](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Sort.html) или см. [Class Sort](./DOC/SliceSortAndPageUsing/SortClass.md). Это параметр будет определять, каким образом мы будем обрабатывать результат нашего запроса. Перепишем наш предыдущий метод и добавим ключевые параметры: `List<User> findTop2ByBirthDateBefore(LocalDate birthDate, Sort sort)`. В тестовом методе `*.checkDynamicSortTest()` мы используем два варианта создания объектов данного класса, напрямую:
```java
        Sort sortById = Sort.by("id").and(Sort.by("firstname").and(Sort.by("lastname")));
```
А так же с использованием вложенного класса:
```java
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        Sort sort = sortBy.by(User::getFirstname).and(sortBy.by(User::getLastname));
```
- Наконец, есть интерфейс [Pageable](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Pageable.html) или см. ["Interface Pageable"](./DOC/SliceSortAndPageUsing/PageableInterface.md), который позволяет работать с выборками, как со страницами (отсортированными). У данного интерфейса есть реализующий класс [PageRequest](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/PageRequest.html), который более удобен при работе со страницами (см. ["Class PageRequest"](./DOC/SliceSortAndPageUsing/PageRequestClass.md).
  - `List<User> findAllUserBy(Pageable pageable)` - метод использующий объект интерфейса `Pageable` (см. ["PageableInterface"](./DOC/SliceSortAndPageUsing/PageableInterface.md)) 
в качестве параметра в интерфейсе [SecondUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/SecondUserRepository.java);
  - `void checkPageableTest()` - метод тестирующий `*.findAllUserBy(Pageable pageable)` и применяющий реализацию интерфейса Pageable - PageRequest см. ["PageRequestClass"](./DOC/SliceSortAndPageUsing/PageRequestClass.md);

---
**См. док.:**
- [Пакет org.springframework.data.domain](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/package-summary.html) ;

---
#### [Lesson 53](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_53) - Интерфейс [Pageable](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Pageable.html) и возвращаемые значения [Page](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Page.html) и [Slice](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Slice.html).

- [FourthUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/FourthUserRepository.java) - интерфейс репозитория демонстрирующий работу [Slice](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Slice.html) и [Pageable](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Pageable.html). Возьмем одноименный метод из прошлого урока `*.findAllUserBy(Pageable pageable)` и, вместо `List<User>`, вернем в нем `Slice<User>`. Проверим результат в тестовом методе класса [ForthUserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_53/ForthUserRepositoryTest.java).
```java    
        /* 
        Нужно помнить, что, хотя, у нас в БД 5-ть записей, но запрос PageRequest 
        делается с 1-ой стр. (не с 0 - ой), поэтому результаты Slice на экран идут 
        начиная с 3-го ID. Сортировка прямая, т.е. по возрастанию ID. 
        */
        @Test
        void checkSliceTest() {
          PageRequest myPageable = PageRequest.of(1, 2, Sort.by("id"));
          Slice<User> myFirstSlice = fourthUserRepository.findAllUserBy(myPageable);
    
          while (myFirstSlice.hasNext()) {
            myFirstSlice.forEach(user -> System.out.println(user.getId()));
            myFirstSlice = fourthUserRepository.findAllUserBy(myFirstSlice.nextPageable());
            myFirstSlice.forEach(user -> System.out.println(user.getId()));
          }
        }
```

```text    
        /* На экране результат работы демо-теста, сначала 1-ый Slice, затем 2-ой */
        Hibernate:
          select
            u1_0.id,
            u1_0.birth_date,
            u1_0.company_id,
            u1_0.firstname,
            u1_0.lastname,
            u1_0.role,
            u1_0.username
          from
            users u1_0
          order by
            u1_0.id offset ? rows fetch first ? rows only
        3
        4
        
        Hibernate:
          select
            u1_0.id,
            u1_0.birth_date,
            u1_0.company_id,
            u1_0.firstname,
            u1_0.lastname,
            u1_0.role,
            u1_0.username
          from
            users u1_0
          order by
            u1_0.id offset ? rows fetch first ? rows only
        5
```
- У объектов интерфейса [Slice](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Slice.html) масса преимуществ, однако при работе с сайтами (и не только), нам очень часто бывает нужна т.н. пагинация - вывод необходимых элементов запроса на отдельной странице (страницах), при этом с возможностью подсчета количества выводимых страниц (их общего числа). В данном случае нам поможет интерфейс [Page](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Page.html), наследник [Slice](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Slice.html), а значит он имеет все достоинства родителя и так же привносит свой функционал см. ["PageInterface"](./DOC/SliceSortAndPageUsing/PageInterface.txt).
- Применим интерфейс [Page](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Page.html) в методах *.findAllUserPagesBy() интерфейса [FourthUserRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/FourthUserRepository.java) и *.checkPaginationTest() 
тестового класса [ForthUserRepositoryTest](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_53/ForthUserRepositoryTest.java) см. результаты запросов к БД:
```text
        Hibernate:
          select
            u1_0.id,
            u1_0.birth_date,
            u1_0.company_id,
            u1_0.firstname,
            u1_0.lastname,
            u1_0.role,
            u1_0.username
          from
            users u1_0
          order by
            u1_0.id offset ? rows fetch first ? rows only
        Hibernate:
          select
            count(u1_0.id)
          from
            users u1_0
        
        1
        2
        
        Hibernate:
          select
            u1_0.id,
            u1_0.birth_date,
            u1_0.company_id,
            u1_0.firstname,
            u1_0.lastname,
            u1_0.role,
            u1_0.username
          from
            users u1_0
          order by
            u1_0.id offset ? rows fetch first ? rows only
        Hibernate:
          select
            count(u1_0.id)
          from
            users u1_0
        
        3
        4
        
        Hibernate:
          select
            u1_0.id,
            u1_0.birth_date,
            u1_0.company_id,
            u1_0.firstname,
            u1_0.lastname,
            u1_0.role,
            u1_0.username
          from
            users u1_0
          order by
            u1_0.id offset ? rows fetch first ? rows only
        
        5
```
Очень просто обнаружить дополнительный запрос с подсчетом - count, который в свою очередь мы можем также скорректировать при помощи [@Query](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Query.html) (см. [FourthUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/FourthUserRepository.java) метод `*.findAllUserPagesWithCountBy()`):
```java
        @Query(value = "select u from User u",
               countQuery = "select count(distinct u.firstname) from User u")
        Page<User> findAllUserPagesWithCountBy(Pageable pageable);
```
В структуру кода тестового метода *.checkPaginationWithQueryCountTest() мы не вносим ни каких изменений в сравнении с
*.checkPaginationTest() кроме использования аннотированного метода *.findAllUserPagesWithCountBy(Pageable pageable) и 
в консоли мы видим изменения дополнительных запросов к БД:
```test
        select
          count(distinct u1_0.firstname)
        from
          users u1_0
```
---
#### [Lesson 54](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_54) - Аннотация [@EntityGraph](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/EntityGraph.html) в запроса репозиториев.

Тут мы рассмотрим вкратце методики применения аннотаций [@EntityGraph](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/EntityGraph.html) и [@NamedEntityGraph](https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/namedentitygraph). Как бы мы не старались написать код приложения наиболее компактно, проблема N+1 при формировании запросов к БД остается. Поэтому, как и при изучении Hibernate см. [Hibernate_part_7](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_7) по этому вопросу, мы прибегнем к помощи EntityGraph.

- [FifthUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/FifthUserRepository.java) - репозиторий в котором мы применяем два способа работы с аннотацией [@EntityGraph](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/EntityGraph.html), это именованный
[@NamedEntityGraph](https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/namedentitygraph) (см. код и комментарии в [User.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/User.java)) - метод `*.findAllUserWithNamedEntityGraphBy()`, и атрибуты аннотации
[@EntityGraph](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/EntityGraph.html) - метод `*.findAllUserWithAttributeEntityGraphBy()`;
- [FifthUserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_54/FifthUserRepositoryTest.java) - тестовый класс для изучения поведения обоих выше описанных метода репозитория;

```
    !!! Внимание !!! При использовании сущностных графов @EntityGraph над методами репозитория, когда нам важна пагинация и
    подсчет страниц, мы получим проблемы чисто технического плана, поскольку будет происходить разделение запросов для 
    получения LAZY сущностей (т.е. для одной Page получим перегруз данными, например для одной Company мы получим две локали 
    и т.д.) и наше приложение либо может работать неправильно, либо посыпется при отображении (возврате) данных.
```

Всегда когда мы используем картирование OneToMany или ManyToMany и пытаемся воспользоваться Page нужно помнить о возможных
проблемах с получением и отображением данных.  

---
#### [Lesson 55](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_55) - Аннотации [@Lock](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Lock.html) и [@QueryHints](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/QueryHints.html) в запросах репозиториев.

Вопросы локирования (блокировки) транзакций подробно рассмотрены в см. [Hibernate_part_8](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_8).

- [SixthUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/SixthUserRepository.java) - демонстрационный репозиторий в котором мы применим аннотацию [@Lock](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Lock.html) к методу `List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort)`, в качестве параметров в аннотацию можно передать тип блокировки (см. описание ["Enum LockModeType"](./DOC/LockModeAndQueryAnnotation/LockModeTypeEnum.txt)). Применение различных типов блокировок
более подробно рассмотрено в [Hibernate_part_8](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_8).

```
    !!! Помним, что !!! Оптимистические блокировки обрабатываются на уровне нашего приложения, в то время, как 
    пессимистические блокировки обрабатываются (задаются) на уровне нашей БД, т.е. в наших запросах (в случае 
    PostgreSQL: PESSIMISTIC_READ - select for share, PESSIMISTIC_WRITE - select for update).
```

- [SixthUserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_55/SixthUserRepositoryTest.java) - тестовый класс. Если мы применим к нашему методу параметр PESSIMISTIC_READ, то в консоли увидим запрос к БД с ключевым словосочетанием "for share":
```
        Hibernate:
          select
            u1_0.id,
            u1_0.birth_date,
            u1_0.company_id,
            u1_0.firstname,
            u1_0.lastname,
            u1_0.role,
            u1_0.username
          from
            users u1_0
          where
            u1_0.birth_date<?
          order by
            u1_0.lastname,
            u1_0.lastname fetch first ? rows only for share
```
Для того чтобы разобраться, какие блокировки относятся к 'for share' или 'for update' см. документацию к своей БД ([PostgreSQL](https://www.postgresql.org/docs/current/explicit-locking.html)).

Пример применения аннотации [@QueryHints](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/QueryHints.html) приведен в том же тесте [SixthUserRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_55/SixthUserRepositoryTest.java), краткая документация см.
["Hint"](./DOC/Hint).

---
**См. док.:**
- [Пакет org.springframework.data.jpa.repository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/package-summary.html) ;

---
#### [Lesson 56](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_56) - Hibernate (проекция, DTO) Projection.

Для разъяснения понятия проекция (projection) можно изучить статьи из см. ["Статьи по проекциям"](./DOC/ArticleAboutProjection), понятие DTO -
[MVCPractice/DOC/DTO.txt](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/DTO.txt) и

![MVCPractice/DOC/DTO_example.png](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/DTO_example.png)

- [PersonalInfo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/dto/PersonalInfo.java) - простая DTO проекция, неизменяемая, т.к. нам нужно просто получить информацию из БД. Содержит имя, фамилию и дату рождения. Метод `List<PersonalInfo> findAllByCompanyId(Integer companyId)` в 
[ProjectionUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/ProjectionUserRepository.java);

- [PersonalRole.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/dto/PersonalRole.java) - простая DTO проекция, созданная для демонстрации работы динамических проекций запросов, чуть отличается от предыдущей, описание принципа см. ["Spring Data JPA Projections"](./DOC/ArticleAboutProjection/SpringDataJPAProjections.md). Метод
`<T> List<T> findAllByCompanyId(Integer companyId, Class<T> type)` в [ProjectionUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/ProjectionUserRepository.java);

- [PersonalInfoTwo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/dto/PersonalInfoTwo.java) - проекция через интерфейс (см. открытые и закрытые проекции в ["Spring Data JPA Projections"](./DOC/ArticleAboutProjection/SpringDataJPAProjections.md)), в интерфейсе будут методы возвращающие необходимые нам значения полей сущностей через геттеры. Важным моментом в данном
случае будет жесткое соответствие имен геттеров и имен полей сущностей в ResultSet.

- [UserRepositoryProjectionsTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_56/UserRepositoryProjectionsTest.java) - тестовый класс для демонстрации работы наших проекционных методов см. комментарии.

---
#### [Lesson 57](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_57) - Spring Custom Repository Implementation - Самописные репозитории.

В статьях ["Добавление пользовательских методов в один репозиторий"](./DOC/SpringDataJPATutorial/14_AddCustomMethodsToSingleRepository.md) и ["Добавление пользовательских методов во все репозитории"](./DOC/SpringDataJPATutorial/15_AddCustomMethodsToAllRepositories.txt) вкратце описано, каким образом мы можем создавать и интегрировать наши собственные кастомные (самописные) репозитории и 
методы в наш проект. 

Рассмотрим как это реализовано у нас:
- **Шаг 1.** Создадим [UserFilterDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/dto/UserFilterDto.java) - некая проекция нашей сущности [User](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/User.java);
- **Шаг 2.** Создадим интерфейс [FilterUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepository.java) - свою кастомную имплементацию репозитория (для работы со всем доступным из JPA и Hibernate инструментарием), и создадим в нем метод (используем доступную из Spring-a именование по ключевым словам), принимающий параметр в качестве фильтра и возвращающий список User-ов подпадающих под переданный фильтр;
- **Шаг 3.** Создаем класс [FilterUserRepositoryImpl.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java), реализующий наш кастомный интерфейс и переопределяющий его метод;
- **Шаг 4.** Указываем Spring-у, что он должен использовать нашу рукописную имплементацию при обращении к UserRepository, для этого он должен расширять наш рукописный репозиторий, т.е.

```java
    @Repository
    public interface UserRepository extends JpaRepository<User, Long>, FilterUserRepository {
      /* method code */
    }
```

В данном случае Spring при обращении к UserRepository и методу *.findAllByFilter(UserFilterDto filter) будет искать имплементацию его родителя [FilterUserRepository с постфиксом Impl](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java) (вот почему важно следовать декларации имен 
см. интерфейс [EnableJpaRepositories метод *.repositoryImplementationPostfix()](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/config/EnableJpaRepositories.html#repositoryImplementationPostfix())).

- **Шаг 5.** Напишем тест для нашего кастомного (самописного) метода - [CustomUserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_57/CustomUserRepositoryTest.java) и проверим 
его работоспособность, см. вывод Hibernate запроса к БД на экран:
```text
        Hibernate:
          select
            u1_0.id,
            u1_0.birth_date,
            u1_0.company_id,
            u1_0.firstname,
            u1_0.lastname,
            u1_0.role,
            u1_0.username
          from
            users u1_0
          where
            u1_0.lastname like ? escape ''
            and u1_0.birth_date<?
```
И так, мы прибегли к помощи Criteria API, но при этом спокойно обратились к репозиторию и получили нужный нам результат,
т.е. мы можем, при необходимости, писать еще более сложные запросы используя и другие методы создания запросов к БД.

---
#### [Lesson 58](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_58) - [Spring JPA Auditing](https://docs.spring.io/spring-data/jpa/reference/auditing.html) - Аудит работы нашего приложения.

Вопросы аудита наших записей в БД средствами Spring рассмотрен в статьях: ["Аудит сущностей - часть 1"](./DOC/SpringDataJPATutorial/13_1_AuditingSpringDataJPA.md)
и ["Аудит сущностей - часть 2"](./DOC/SpringDataJPATutorial/13_2_AuditingSpringDataJPA.md). Аудит средствами Hibernate рассмотрены в [Hibernate_part_10](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_10). Но в сравнении с Hibernate, Spring очень сильно упростил нам жизнь.

**Реализуем аудит в нашем приложении (будем отслеживать изменения в записях User)**:
- **Шаг 1.** Создадим аудирующую (фиксирующую изменения) сущность [AuditingEntity.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/AuditingEntity.java), это абстрактный класс, все его наследники, смогут использовать его поля для фиксации изменений (создание и модификация, кем и когда), для этого:
  - **Шаг 1.1** Добавим поля [класса Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html) для когда (createdAt, modifiedAt) и для кто/кем (createdBy, modifiedBy) класса String, которые фиксируют соответствующие изменения в БД ;
  - **Шаг 1.2** Либо прямым кодом, либо через аннотации [@Getter и @Setter](https://projectlombok.org/features/GetterSetter), создаем геттеры и сеттеры для полей;
  - **Шаг 1.3** Для того чтобы наследники могли использовать поля родителя добавим аннотацию [@MappedSuperclass](https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/mappedsuperclass);
  - **Шаг 1.4** Для того чтобы мы фиксировали изменения в сущностях нам нужны слушатели (listeners), поэтому добавляем аннотацию [@EntityListeners](https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/entitylisteners)([AuditingEntityListener](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/domain/support/AuditingEntityListener.html)) в параметры которой передаем, не наш самописный слушатель, а уже готовый Spring-овый слушатель AuditingEntityListener;
  - **Шаг 1.5** Указываем Spring-овому слушателю (listener-у) какие поля нужно обновлять, аннотируя их соответствующими аннотациями ([@CreatedDate](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/CreatedDate.html), [@LastModifiedDate](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/LastModifiedDate.html), [@CreatedBy](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/CreatedBy.html), [@LastModifiedBy](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/LastModifiedBy.html)) см. [AuditingEntity.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/AuditingEntity.java);
- **Шаг 2.** Поскольку наш класс аудита реализует нашу же [BaseEntity](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/BaseEntity.java), мы легко можем изменить класс [User](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/User.java). Поскольку мы запланировали вести аудит его изменений, унаследуемся от [AuditingEntity](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/AuditingEntity.java). И конечно, дабы избежать исключений добавим еще одно поле в аннотацию `@EqualsAndHashCode(callSuper=false)` в сущности `User`;
- **Шаг 3.** Внесем соответствующие изменения в БД, добавим поля, которые будут фиксировать все изменения в записях сущностей `User` (см. SQL скрипты ["UserTableUpdate.sql"](./DOC/SQL_Update_Base_Table/UserTableUpdate.sql));
- **Шаг 4.** Запускаем механизм аудирования изменений данных в БД. Для этого мы должны использовать аннотацию `@EnableJpaAuditing`, которой можем аннотировать, например основной класс нашего приложения [SpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/SpringAppRunner.java) (но мы этого делать не будем), либо создать свой класс конфигурации аудита и пометить данной аннотацией его, что и сделаем. Создаем [AuditConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/config/AuditConfiguration.java) и естественно аннотируем его еще и как [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html).
- **Шаг 5.** Создаем метод провайдер данных *.auditorAware() для наших полей в классе конфигурации аудита [AuditConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/config/AuditConfiguration.java), т.е. мы должны предоставить Spring-у BEAN [интерфейса AuditorAware](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/AuditorAware.html);
- **Шаг 6.** Создаем тестовый класс [UserAuditingTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_58/UserAuditingTest.java) и проверяем работу аудита;

---
**См. док.:**
- [Пакет org.springframework.data.annotation](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/annotation/package-summary.html) ;
- [Пакет org.springframework.data.jpa.domain.support](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/domain/support/package-summary.html) ;
- [Пакет javax.persistence](https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/package-summary) ;
- [Lombok Features](https://projectlombok.org/features/) ;

---
#### [Lesson 59](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_59) - [Hibernate-Envers в Spring](https://docs.spring.io/spring-data/envers/docs/current/reference/html/) приложении.

Как было описано выше, для данного раздела, подключим Spring зависимость, чтобы использовать механизм Envers: 
```
        implementation 'org.springframework.data:spring-data-envers'
```
Для сравнения с Hibernate функционалом и возможностями см. [Hibernate_part_10](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_10)
и оф. документация (ENG) [Hibernate ORM Envers](https://hibernate.org/orm/envers/) или [Hibernate Envers](https://docs.jboss.org/hibernate/core/4.3/devguide/en-US/html/ch15.html)

Освежим в памяти, **для чего нужен функционал Envers**:
- *Аудит всех сопоставлений*, определенных спецификацией JPA.
- *Аудит некоторых сопоставлений Hibernate, которые расширяют JPA*, например, пользовательские типы и коллекции/карты «простых» типов, таких как строки, целые числа.
- *Регистрация данных для каждой ревизии* с использованием «объекта ревизии».
- *Запрос исторических снимков объекта* и его ассоциаций.

Для работы с механизмом отслеживания версий (ревизий) наших сущностей проделаем следующие шаги:
- **Шаг 1.** Создадим отслеживающую изменения версий сущность (некий, Git-подобный механизм) [Revision.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/Revision.java), содержащих два поля: id - номер проведенного изменения и timestamp - время, когда было проведено изменение.
- **Шаг 2.** Над всеми сущностями, которые мы планируем отслеживать, мы должны проставить аннотацию [@Audited](https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/envers/Audited.html), например,
наша сущность User см. [User.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/entity/User.java).
- **Шаг 3.** Настраиваем (задаем) параметры отслеживания. В случае нашей сущности `User` мы не хотим отслеживать связные сущности, например `Company`, а так же любые коллекции внутри User. Поэтому в аннотацию [@Audited](https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/envers/Audited.html) передаем параметр `targetAuditMode = RelationTargetAuditMode.NOT_AUDITED`, а коллекцию `List<UserChat> userChats` - помечаем, как [@NotAudited](https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/envers/NotAudited.html).
- **Шаг 4.** Необходимо создать в нашей БД соответствующие таблицы, которые будут отслеживать изменения аудируемой сущности. Настроим данный функционал на автоматическое создание. Поскольку мы будем проверять работоспособность кода в наших тестах, внесем необходимые изменения в [resources/application-test.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/resources/application-test.yml):

```yaml
        spring:
          jpa:
            properties.hibernate:
              hbm2ddl.auto: update
```

В данном случае при запуске теста, который вносит изменения в отслеживаемую сущность User, в нашей БД автоматически будет создано две таблицы: revision и users_aud. В них будет внесена информация об изменениях сущности см. БД. 
- **Шаг 5.** - Запустим механизм аудирования. Для этого в файле авто-конфигурации нашего приложения [AuditConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/config/AuditConfiguration.java)
добавим аннотацию [@EnableEnversRepositories](https://docs.spring.io/spring-data/envers/docs/current/api/org/springframework/data/envers/repository/config/EnableEnversRepositories.html) или см. ["Annotation Type EnableEnversRepositories"](./DOC/DataEnvers/EnableEnversRepositories.txt). Передадим в параметры аннотации наш корневой пакет для сканирования отслеживаемых объектов: `basePackageClasses = SpringAppRunner.class`
- **Шаг 6.** - Для наглядности работы механизма Hibernate Envers, мы запустим тест и внесем изменения в БД, чтобы изменения были зафиксированы необходимо аннотировать тестовый метод, как [@Commit](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/annotation/Commit.html) (см. [UserAuditingTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_58/UserAuditingTest.java)).

Фактически, реализовав данные шаги мы получим в нашей БД необходимые таблицы для каждой отслеживаемой сущности (у нас это User). Естественно мы не только фиксируем изменения, но и можем к ним обращаться, а так же откатывать внесенные изменения. Для этого мы не будем создавать новую сущность, а просто расширим наш уже существующий [UserRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java), добавив еще один интерфейс - [RevisionRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/history/RevisionRepository.html), как расширяемый. И тогда нам будут доступны все методы данного интерфейса для работы с внесенными изменениями, т.е. с ревизиями см. ["Interface RevisionRepository"](./DOC/DataEnvers/RevisionRepositoryInterface.md).

- [FindRevisionTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_59/FindRevisionTest.java) - тестовый класс для проверки работы методов полученных UserRepository при расширении RevisionRepository см. тестовый метод `checkFindLastRevisionTest()`.

---
**См. док.:**
- [Пакет org.springframework.data.repository.history](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/history/package-summary.html) ;
- [Пакет org.hibernate.envers](https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/envers/package-summary.html) ;

---
**См. официальные [Guides](https://spring.io/guides):**
- [Getting Started Guides](https://spring.io/guides) - Эти руководства, рассчитанные на 15–30 минут, содержат быстрые
  практические инструкции по созданию «Hello World» для любой задачи разработки с помощью Spring. В большинстве случаев
  единственными необходимыми требованиями являются JDK и текстовый редактор.
- [Topical Guides](https://spring.io/guides#topicals) - Тематические руководства предназначенные для прочтения и
  понимания за час или меньше, содержит более широкий или субъективный контент, чем руководство по началу работы.
- [Tutorials](https://spring.io/guides#tutorials) - Эти учебники, рассчитанные на 2–3 часа, обеспечивают более глубокое
  контекстное изучение тем разработки корпоративных приложений, что позволяет вам подготовиться к внедрению реальных
  решений.

---
- [Spring Projects на GitHub](https://github.com/spring-projects) ;

---
#### Рекомендованные для изучения материалы не вошедшие в папку DOC:

- [Spring Data — примеры Java 8](https://github.com/spring-projects/spring-data-examples/tree/main/jpa)

---
- [Javadoc интерфейса DataSource](https://docs.oracle.com/javase/7/docs/api/javax/sql/DataSource.html)
- [Javadoc интерфейса EntityManagerFactory](https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManagerFactory.html)
- [Javadoc интерфейса JpaVendorAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/JpaVendorAdapter.html) 
- [Javadoc интерфейса CrudRepository](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)
- [Javadoc интерфейса Repository](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/Repository.html)
- [Javadoc интерфейса Stream<T>](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html)
- [Javadoc интерфейса Future<V>](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html)

---
- [Javadoc класса LocalContainerEntityManagerFactoryBean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/LocalContainerEntityManagerFactoryBean.html)
- [Javadoc класса HibernateJpaVendorAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/vendor/HibernateJpaVendorAdapter.html)
- [Javadoc класса JpaTransactionManager](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/JpaTransactionManager.html)
- [Javadoc класса Optional<T>](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)

---
- [Javadoc аннотации @EnableTransactionManagement](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/EnableTransactionManagement.html)
- [Javadoc аннотации @EnableJpaRepositories](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/config/EnableJpaRepositories.html)
- [Javadoc аннотации @NoRepositoryBean](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/NoRepositoryBean.html)
- [Javadoc аннотации @Async](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/Async.html)
- [Javadoc аннотации @Param](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/Param.html)

---
- [Справочное руководство Spring Framework: 12.5.6 Использование @Transactional](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#transaction-declarative-annotations)
- [Справочное руководство Spring Framework: 14.3.1 Источник данных](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#jdbc-datasource)
- [Справочное руководство Spring Framework: 15.5.1 Три варианта установки JPA в среде Spring](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#orm-jpa-setup)  
- [Справочное руководство Spring Framework: 15.5.3 Управление транзакциями](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#orm-jpa-tx)
- [Справочное руководство Spring Framework: 28.4.3 Аннотация @Async](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#scheduling-annotation-support-async)

---
- [Справочная документация Hibernate: 6.4 Пользовательские типы](https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#types-custom)
- [Справочное руководство Hibernate ORM: 3.4. Дополнительные свойства конфигурации](https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#configuration-optional)

---
- [Справочное руководство Spring Data JPA: 3.3 Определение интерфейсов репозитория](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.definition)
- [Справочное руководство Spring Data JPA: 3.3.1 Определение точной настройки репозитория](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.definition-tuning)
- [Справочное руководство Spring Data JPA: 3.4.2 Создание запроса](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.query-methods.query-creation)
- [Справочное руководство Spring Data JPA: 3.4.3 Выражения свойств](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.query-methods.query-property-expressions)
- [Справочное руководство Spring Data JPA: 3.4.4 Обработка специальных параметров](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.special-parameters)
- [Справочное руководство Spring Data JPA: 3.4.5 Ограничение результатов запроса](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.limit-query-result)
- [Справочное руководство Spring Data JPA: 3.4.6 Потоковая передача результатов запроса](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.query-streaming)
- [Справочное руководство Spring Data JPA: 3.5 Создание экземпляров репозитория](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.create-instances)
- [Справочное руководство Spring Data JPA: 4.3.5 Использование именованных параметров](https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.named-parameters)  

---
- [Hikari CP Initialization](https://github.com/brettwooldridge/HikariCP#initialization)
- [Свойства конфигурации HikariCP](https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby)

---
- [Что нового в Spring Data Dijkstra — асинхронный вызов метода репозитория](https://spring.io/blog/2014/05/21/what-s-new-in-spring-data-dijkstra#asynchronous-repository-method-invocations)
- [Что нового в Spring Data Dijkstra (поиск «поддержка типов-оболочек в качестве возвращаемых значений»)](https://spring.io/blog/2014/05/21/what-s-new-in-spring-data-dijkstra)
- [Что нового в Spring Data Dijkstra — поддержка типов-оболочек в качестве возвращаемых значений](https://spring.io/blog/2014/05/21/what-s-new-in-spring-data-dijkstra#support-for-wrapper-types-as-return-values)
- [Минимальный интерфейс](https://martinfowler.com/bliki/MinimalInterface.html)
- [Код, раскрывающий намерения, с новым типом Java 8 'Optional'](https://nipafx.dev/intention-revealing-code-java-8-optional/)
- [Устали от NullPointerExceptions? Рассмотрите возможность использования Java SE 8 'Optional'](https://www.oracle.com/technical-resources/articles/java/java8-optional.html)
- [Дизайн с использованием Optional (статья удалена, интересный блог)](https://nipafx.dev/ )
