### Spring Boot lessons part 11 - [JDBC](https://spring.io/projects/spring-data-jdbc) Starter

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [PostgreSQL Documentation](https://www.postgresql.org/docs/) ;
- [Docker Documentation](https://docs.docker.com/) ;
- [Spring Data JDBC](https://spring.io/projects/spring-data-jdbc) ;
________________________________________________________________________________________________________________________
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку (первые 3-и шага из предыдущих частей):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/build.gradle) добавим необходимые plugin-ы: 

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

Шаг 2. - подключаем Spring Boot starter:

    /* 
       Подключим Spring Boot Starter он включает поддержку 
       авто-конфигурации, логирование и YAML
    */
    implementation 'org.springframework.boot:spring-boot-starter'

Шаг 3. - подключаем блок тестирования (Spring Boot Starter Test) 
(он будет активен на этапе тестирования):

    testImplementation 'org.springframework.boot:spring-boot-starter-test'

Шаг 4. - автоматически Gradle создал тестовую зависимость на Junit5
(мы можем использовать как Junit4, так и TestNG):

    test {
        useJUnitPlatform()
    }

Шаг 5. - подключим блок для работы с БД (Spring Boot Starter Data Jpa):

    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

________________________________________________________________________________________________________________________

    !!! НЕ ЗАБЫВАЕМ !!! У нас есть классы (см. ConnectionPool.java и комментарии), где мы пытаемся внедрить параметры в 
    поля через аннотации, с использованием аннатационного же конструктора @RequiredArgsConstructor. Фокус не пройдет без 
    создания и настройки файла конфигурации: lombok.config - 'контекст' просто завалится. 

    Либо все делаем руками от начала и до конца, либо помним какие вспомогательные средства используем и какие их особенности
    могут повлиять на работу приложения.

________________________________________________________________________________________________________________________

Шаг 6. - Для использования средств подобных Hibernate ENVERS подключим такую же поддержку от Spring ([начиная с Lesson_59](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10#lesson-59---hibernate-envers-%D0%B2-spring-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B8)):

    implementation 'org.springframework.data:spring-data-envers'

________________________________________________________________________________________________________________________
#### [Lesson 60](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_11/src/test/java/spring/oldboy/integration/database/repository/lesson_60) - JDBC-Starter.

Кратко начало работы со Spring JDBC описано в: [DOC/IntroductionSpringDataJDBC.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/DOC/IntroductionSpringDataJDBC.txt)
Для понимая различий между технологиями и их особенностей см. таб. Преимущества Spring Boot JBDC перед Spring JDBC:

| Spring Boot JDBC                                                                                                                                                                                       | Spring JDBC                                                                                                                                                              |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Требуется только зависимость Spring-boot-starter-jdbc.                                                                                                                                                 | В Spring JDBC необходимо настроить несколько зависимостей, таких как Spring-jdbc и Spring-context.                                                                       |
| Он автоматически настраивает компонент Datasource, если не поддерживается явно. Если мы не хотим использовать компонент, мы можем установить для свойства Spring.datasource.initialize значение false. | В Spring JDBC необходимо создать компонент базы данных либо с использованием XML , либо с помощью javaconfig .                                                           |
| Нам не нужно регистрировать bean-компоненты шаблона, поскольку Spring Boot автоматически регистрирует bean-компоненты.                                                                                 | Компоненты шаблона, такие как PlatformTransactionManager, JDBCTemplate, NamedParameterJdbcTemplate, должны быть зарегистрированы.                                        |
| Любые сценарии инициализации базы данных, хранящиеся в файле .sql, выполняются автоматически.                                                                                                          | Если в файле SQL создаются какие-либо сценарии инициализации базы данных, такие как удаление или создание таблиц, эту информацию необходимо указать явно в конфигурации. |

Различия JDBC и Hibernate:

| JDBC                                                                         | Hibernate                                                                          | 
|------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| JDBC — это технология.                                                       | Hibernate — это фреймворк (платформа) ORM .                                        |
| В JDBC пользователь несет ответственность за создание и закрытие соединений. | В Hibernate система времени выполнения заботится о создании и закрытии соединений. |
| Не поддерживает отложенную загрузку.                                         | Поддерживает отложенную загрузку, что обеспечивает лучшую производительность.      |
| Не поддерживает ассоциации (связь между двумя отдельными классами).          | Поддерживает ассоциации.                                                           |                                                        

Работой и авто-конфигурированием нашего соединения с БД управляет [JdbcTemplateAutoConfiguration](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/jdbc/JdbcTemplateAutoConfiguration.html)
(см. [DOC/SpringJDBC/JdbcTemplateAutoConfiguration.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/DOC/SpringJDBC/JdbcTemplateAutoConfiguration.txt)), который, в свою очередь, зависит от [DataSource](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/javax/sql/DataSource.html) - или пул 
соединений, и [JdbcTemplate](https://docs.spring.io/spring-framework/docs/6.1.5/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html) - основной класс через который происходит взаимодействие с БД 
(или см. [DOC/SpringJDBC/JdbcTemplateClass.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/DOC/SpringJDBC/JdbcTemplateClass.txt)). Также подгружается зависимость от [NamedParameterJdbcTemplateConfiguration](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jdbc/NamedParameterJdbcTemplateConfiguration.java)
(см. [DOC/SpringJDBC/NamedParameterJdbcTemplate.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/DOC/SpringJDBC/NamedParameterJdbcTemplate.txt))

Примеры работы: 

В наш [FilterUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepository.java) добавим метод *.findAllByCompanyIdAndRole(), который вернет список наших 
DTO List<[PersonalInfo](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/java/spring/oldboy/dto/PersonalInfo.java)>, а в его имплементации [FilterUserRepositoryImpl.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java) реализуем данный метод с использованием 
технологии JDBC.

- [CompanyRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/test/java/spring/oldboy/integration/database/repository/lesson_60/CompanyRepositoryTest.java) - тестовый класс для проверки работы методов запроса к БД по технологии JDBC;

При выводе на экран мы уже не видим наши SQL запросы, т.к. это не Hibernate, а прямой запрос к БД через JDBC. Для того
чтобы отобразить прошедший от приложения запрос, настроим наш файл свойств [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/resources/application.yml), докрутим логгер, чтобы он
отображал работу JDBC, в режиме трассировки ([уровень логирования TRACE](https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_2/DOC/Slf4j_and_Log4j.txt)):

    logging:
        # Зададим уровень логирования корневого логера
      level:
        # ... другие настройки логгера ...

        org.springframework.jdbc.core: TRACE

        # Записываем лог в файл
      file.name: Logs/oldboy.log

И тогда в консоли мы можем видеть:

    2023-10-23T17:14:41.323+03:00 DEBUG 13560 --- [Test worker] o.s.jdbc.core.JdbcTemplate : Executing prepared SQL query
    2023-10-23T17:14:41.324+03:00 DEBUG 13560 --- [Test worker] o.s.jdbc.core.JdbcTemplate : Executing prepared SQL statement 
    [SELECT
        firstname,
        lastname,
        birth_date
    FROM users
    WHERE company_id = ?
    AND role = ?
    ]
    2023-10-23T17:14:41.353+03:00 TRACE 13560 --- [Test worker] o.s.jdbc.core.StatementCreatorUtils: 
                 Setting SQL statement parameter value: column index 1, 
                                                        parameter value [1], 
                                                        value class [java.lang.Integer], SQL type unknown
    2023-10-23T17:14:41.359+03:00 TRACE 13560 --- [Test worker] o.s.jdbc.core.StatementCreatorUtils: 
                 Setting SQL statement parameter value: column index 2, 
                                                        parameter value [USER], 
                                                        value class [java.lang.String], SQL type unknown

И теперь мы видим и сам запрос и его параметры, что были переданы в нем.

См. док.:
- [Пакет org.springframework.boot.autoconfigure.jdbc](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/jdbc/package-summary.html) ;
- [Пакет org.springframework.jdbc.core](https://docs.spring.io/spring-framework/docs/6.1.5/javadoc-api/org/springframework/jdbc/core/package-summary.html) ;
- [Модуль java.sql](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/module-summary.html) ;
- [Пакет javax.sql](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/javax/sql/package-summary.html) ;
- [GitHub org.springframework.boot.autoconfigure.jdbc](https://github.com/spring-projects/spring-boot/tree/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jdbc) ;

________________________________________________________________________________________________________________________
#### [Lesson 61](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_11/src/test/java/spring/oldboy/integration/database/repository/lesson_61) - Batch-size и Fetch-size.

Пакетная обработка SQL (DML) операторов (BATCH): INSERT, UPDATE, DELETE, одно из возможностей достигнуть увеличения 
производительности приложения при работе через сеть см. [DOC/BATCH_for_DB_COMMUNICATION.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/DOC/BATCH_for_DB_COMMUNICATION.jpg). В отличие от 
последовательного выполнения каждого SQL запроса, пакетная обработка даёт возможность отправить целый набор запросов 
(пакет - batch) за один вызов, тем самым уменьшая количество требуемых сетевых подключений и позволяя БД выполнять 
какое-то количество запросов параллельно, что может значительно увеличить скорость выполнения. Однако, заметный эффект 
от подобной операции можно ощутить при вставке, обновлении или удалении только больших объёмов данных в БД. См. статью:
[DOC/Batch_JDBC_HIBERNATE.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/DOC/Batch_JDBC_HIBERNATE.txt).

Реализуем batch запрос:
- Шаг 1. - в классе [FilterUserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepository.java) добавим метод *.updateCompanyAndRole(List<User> users);
- Шаг 2. - реализуем его в [FilterUserRepositoryImpl.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java);
  - Шаг 2.1 - в классе реализующем наш интерфейс [FilterUserRepositoryImpl](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java), создадим SQL PreparedStatement запрос;
  - Шаг 2.2 - пропишем код метода, который обновит данные USER-ов переданных в метод в качестве списка аргументов;
    
        /* Шаг 2.1 */
        private static final String UPDATE_COMPANY_AND_ROLE = """
          UPDATE users
          SET company_id = ?,
              role = ?
          WHERE id = ?
        """;
    
        /* Шаг 2.2 */
        @Override
        public void updateCompanyAndRole(List<User> users) {
            List<Object[]> args = users
                  .stream()
                  .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                  .toList();
    
            jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
        }

- [CompanyRepositoryBatchTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/test/java/spring/oldboy/integration/database/repository/lesson_61/CompanyRepositoryBatchTest.java) - тестовый метод для проверки работы пакетного (batch) запроса.

Применение именованных параметров в batch запросах, это когда в запросе передаваемые параметры не обозначены знаком '?', 
а имеют конкретные имена см. реализацию метода - *.updateCompanyAndRoleNamed() из [FilterUserRepositoryImpl.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java).

В предыдущих двух примерах размер пакета (batch) в JDBC определялся автоматически и определялся особенностью драйвера БД,
естественно в Hibernate мы так же можем применить пакетную передачу данных. Размер пакета мы можем настроить через файл 
[application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/resources/application.yml):

    spring:
      datasource:
        ...
      jpa:
        properties.hibernate:
          batch_size: 50
          ...

Так же для Hibernate мы можем настроить количество получаемых (возвращаемых из БД) строк (записей) через Fetch size см.
[DOC/FETCH_SIZE_for_DB_ANSWER.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/DOC/FETCH_SIZE_for_DB_ANSWER.jpg). В файле [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_11/src/main/resources/application.yml) мы можем настроить количество возвращаемых записей также через
параметр fetch_size:

    spring:
      datasource:
        ...
      jpa:
        properties.hibernate:
          fetch_size: 50
          ...

________________________________________________________________________________________________________________________
См. официальные [Guides](https://spring.io/guides):
- [Getting Started Guides](https://spring.io/guides) - Эти руководства, рассчитанные на 15–30 минут, содержат быстрые
  практические инструкции по созданию «Hello World» для любой задачи разработки с помощью Spring. В большинстве случаев
  единственными необходимыми требованиями являются JDK и текстовый редактор.
- [Topical Guides](https://spring.io/guides#topicals) - Тематические руководства предназначенные для прочтения и
  понимания за час или меньше, содержит более широкий или субъективный контент, чем руководство по началу работы.
- [Tutorials](https://spring.io/guides#tutorials) - Эти учебники, рассчитанные на 2–3 часа, обеспечивают более глубокое
  контекстное изучение тем разработки корпоративных приложений, что позволяет вам подготовиться к внедрению реальных
  решений.
________________________________________________________________________________________________________________________
- [Spring Projects на GitHub](https://github.com/spring-projects) ;
________________________________________________________________________________________________________________________