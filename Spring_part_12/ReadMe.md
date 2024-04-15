### Spring Boot lessons part 12 - Тестирование приложения при работе с InMemory БД H2

Док. (ссылки) для изучения:
- [H2 Database](https://www.h2database.com/html/main.html) ;
- [Using H2’s Web Console](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#data.sql.h2-web-console) ;
- [Spring Boot features](https://docs.spring.io/spring-boot/docs/1.3.0.RC1/reference/html/boot-features-sql.html) ;
________________________________________________________________________________________________________________________
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

---------------------------------------------------------------------------------------------------------------
Для начала проведем предварительную подготовку (первые 3-и шага из предыдущих частей):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_12/build.gradle) добавим необходимые plugin-ы: 

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

---------------------------------------------------------------------------------------------------------------

    !!! НЕ ЗАБЫВАЕМ !!! У нас есть классы (см. ConnectionPool.java и комментарии), где мы пытаемся внедрить параметры в 
    поля через аннотации, с использованием аннатационного же конструктора @RequiredArgsConstructor. Фокус не пройдет без 
    создания и настройки файла конфигурации: lombok.config - 'контекст' просто завалится. 

    Либо все делаем руками от начала и до конца, либо помним какие вспомогательные средства используем и какие их особенности
    могут повлиять на работу приложения.

---------------------------------------------------------------------------------------------------------------

Шаг 6. - Для использования средств подобных Hibernate ENVERS подключим такую же поддержку от Spring:

    implementation 'org.springframework.data:spring-data-envers'

---------------------------------------------------------------------------------------------------------------
#### [Lesson 62](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_12/src/test/java/spring/oldboy/integration/database/repository/lesson_62) - H2 Base for tests.

В [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_12/build.gradle) пропишем соответствующую тестовую зависимость:

    testImplementation 'com.h2database:h2'

Настраиваем подключение к тестовой базе данных в [...\src\test\resources\application-test.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_12/src/test/resources/application-test.yml):

    db:
      ...  
    spring:
      datasource:
        url: jdbc:h2:mem:test
        username:
        password:
        driver-class-name: org.h2.Driver
      jpa:
        ...

H2 удобен для тестирования, но нужно помнить, что у разных БД могут быть разные семантко-синтаксическо-технологическое 
ядра, т.е. отличие может быть не только в написании SQL запроса, в формате принимаемых данных, но и во внутренней 
структуре данных. Отсюда единственным верным вариантом тестирования Spring (вообще любого) приложения работающего с БД,
это использовать в качестве тестовой БД именно ту, под которую заточен ее код.

Однако, мы изучаем H2, продолжим. В качестве тестового класса используем [UserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_12/src/test/java/spring/oldboy/integration/database/repository/lesson_62/UserRepositoryTest.java). При запуске тестов
все необходимые таблицы будут созданы автоматически, что видно в консоли при debug прогоне. И эти таблицы желательно
заполнить данными. Для этого у нас есть соответствующие SQL скрипты см. [resources/sql_scripts/data.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_12/src/test/resources/sql_scripts/data.sql). Поскольку это
тестирование нашего приложения, то и тестовые SQl скрипты (создание таблиц, заполнение таблиц и т.д.) должны лежать в 
тестовых ресурсах.

Теперь, чтобы заполнить нашу H2 базу, нам необходимо обратиться к нашему тестовому скрипту с данными [sql_scripts/data.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_12/src/test/resources/sql_scripts/data.sql)
При каждом запуске теста данные будут заполнять соответствующие таблицы БД, тестовые методы будут вносить изменения, а 
затем они будут откатываться (NoCommit). Для заполнения таблиц БД мы должны аннотировать наш тестовый класс и указать в 
параметрах аннотации месторасположение SQL скрипта:

    @Sql({
        "classpath:sql/data.sql"
    })

Обычно, при тестировании используется следующий алгоритм:
- DDL команды (создание или монтаж БД) генерируются 'миграционным' фреймворком (в нашем случае его роль выполняет Hibernate),
данная процедура (создание таблиц и настройка InMemory БД) запускается один раз перед запуском тестов;
- Перед выполнением каждого теста (тестового метода) вызывается наш SQL скрипт заполняющий БД, через аннотацию [@SQL](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/jdbc/Sql.html). Данный
процесс будет повторяться перед каждым из тестов. Если же мы хотим, чтобы SQL скрипт запустился единожды, перед каким-либо
тестовым методом, то именно этот метод и следует аннотировать как [@Sql](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/jdbc/Sql.html), а не класс;
    
        @Sql({
            "classpath:sql_scripts/data.sql"
        })
        @Test
        void checkJdbcTemplateTest() {
        ... some test code ... 
        }

- После выполнения всех тестов БД будет размонтирована;

Все же наиболее корректным вариантом остается использование Docker контейнера с копией нашей БД для тестирования.