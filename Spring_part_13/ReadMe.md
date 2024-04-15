### Spring Boot lessons part 13 - Тестирование приложения при работе с БД через DOCKER

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_13/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [PostgreSQL Documentation (Manuals) 12-16](https://www.postgresql.org/docs/) ;
- [Docker Documentation](https://docs.docker.com/) ;
________________________________________________________________________________________________________________________
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку (первые 3-и шага из предыдущих частей):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/build.gradle) добавим необходимые plugin-ы: 

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

Шаг 6. - Для использования средств подобных Hibernate ENVERS подключим такую же поддержку от Spring ([начиная с Lesson_59](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10/src/test/java/spring/oldboy/integration/database/repository/lesson_59)):

    implementation 'org.springframework.data:spring-data-envers'

________________________________________________________________________________________________________________________
#### Lesson 63 - Docker container for Tests.

Все тесты до этого мы проводили на БД установленной на локальной машине, пришло время изучить особенности работы с 
Docker образами и контейнерами (см. [DOC/docker_images_command.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/DOC/docker_images_command.txt) и [DOC/docker_container_command.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/DOC/docker_container_command.txt)). 

[Docker](https://www.docker.com/) — это система управления контейнерами. Контейнеры же представляют собой логическое эволюционное продолжение 
виртуальных машин. Это изолированная среда для разработки и тестирования программного обеспечения.

Контейнер Docker потребляет мало ресурсов и быстро запускается, а еще его легко переносить с одного устройство на другое.

Естественно, тестирование работоспособности приложения лучше всего проводить на полигоне имитирующем рабочую среду и 
окружение. В нашем случае, приложение работает с [PostgreSQL](https://www.postgresql.org/) базой данных. У нас есть вариант использовать уже 
работающую базу (установленную на локальной машине) или создать 'песочницу' в которой будет развернута точно такая же 
база загруженная теми же данными (или тестовыми данными). 

Для этого используем инструментарий Docker-a. Переходим на сайт разработчика [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop/), 
качаем и устанавливаем Docker для своей операционной системы (у меня Win 10, для старых, например, Win 7 операционных систем
данный дистрибутив не подходит см. [https://github.com/docker-archive/toolbox](https://github.com/docker-archive/toolbox))

Используя консоль (PowerShell) или Bash после установки Docker-a проверяем, что получилось:
- $ docker version - команда проверки версии Docker-a во время его работы;
- $ docker images - проверяем есть ли у нас образы (качаем с оф. сайтов, т.к. нам нужен образ PostgreSQL, то см. 
[https://hub.docker.com/_/postgres](https://hub.docker.com/_/postgres)), естественно локальный репозиторий образов пуст, т.к. все ставится 'с нуля';
- $ docker ps - выводим список запущенных контейнеров, в столбце «CONTAINER ID» будет указан ID контейнера, собранного 
из образа, указанного в столбце «IMAGE»;
- $ docker pull - скачать образ с удаленного репозитория без создания и запуска контейнера из него;

На данном этапе у нас все чисто и теперь нам нужно скачать образ БД и тут же создать и запустить контейнер, в консоли 
вводим команду:

    docker run --name my-postgres -e POSTGRES_PASSWORD=pass -p 5433:5432 -d postgres

Т.к. у нас чистая установка Docker-a, и до этого на машине он не стоял, то и нет ни одного образа и запущенного контейнера,
после запуска вышеописанной команды начнется скачивание образа и его развертывание в контейнер. После повторного ввода 
команды:

    $ docker images
    
    REPOSITORY   TAG       IMAGE ID       CREATED       SIZE
    postgres     latest    f7d9a0d4223b   6 weeks ago   417MB  

Мы видим, что скачан образ PostgerSQL БД (последней версии - latest), теперь введем еще одну команду и увидим: 

    $ docker ps

    CONTAINER ID   IMAGE      COMMAND                  CREATED          STATUS          PORTS                    NAMES
    5657620e4ed1   postgres   "docker-entrypoint.s…"   50 seconds ago   Up 48 seconds   0.0.0.0:5433->5432/tcp   my-postgres

Т.е. мы создали контейнер из скачанного образа и он запущен, см. статус 'UP'. Порт для связи с БД переназначен (стандартный
порт для PostgreSQL - 5432, но это внутри контейнера, а нам нужно достучаться до самого созданного контейнера, поэтому 
используем порт 5433), пароль задан, имя образа задано (все это было проделано командой '$ docker run' с параметрами, 
см. выше).

Теперь подключимся из среды разработки к БД, указав порт - 5433 и заданный пароль - pass (логин стандартный - postgres).

________________________________________________________________________________________________________________________
#### Lesson 64 - Настройка дополнительных зависимостей и плагинов в Gradle при использовании Docker контейнера с БД.

Нам необходимо 'поднимать' нашу PostgreSQL БД при тестировании в Docker контейнере, использовать, а затем отключаться от 
нее и останавливать контейнер с БД. Для того чтобы динамически 'будить' и 'усыплять' нашу контейнерную БД будем использовать
библиотеку Testcontainers (см. сайт разработчика [https://testcontainers.com/](https://testcontainers.com/)), для этого, заменим в нашем [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/build.gradle)
строку с H2 зависимостью на другую:

    testImplementation "org.testcontainers:postgresql:1.19.1"

Однако, и эту строку можно чуть улучшить, убрав хард-код версии библиотеки. Данную процедуру можно проделать для большинства 
строк в которых явным образом присутствуют версии зависимостей. Для этого создадим вешний файл (хотя можно проделать нечто 
подобное и в коде build.gradle) [version.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/version.gradle):

    ext {
        versions = [
            'testcontainers': '1.19.1',
            'postgres': '42.6.0'
        ]
    }

В данном файле мы можем прописать версии зависимостей, которых нет в плагине 'spring.dependency-management'. Для того 
чтобы подхватить в [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/build.gradle) этот файл необходимо указать на него, добавим строку (это еще один способ подключения 
внешних плагинов):

    apply from: 'version.gradle'

И естественно, добавляем динамику (ключ - значение) в build.gradle вместо явного указания версий зависимостей:

    implementation "org.postgresql:postgresql:${versions.postgres}"
   
    testImplementation "org.testcontainers:postgresql:${versions.testcontainers}"

________________________________________________________________________________________________________________________
#### Lesson 65 - Тестирование PostgreSQL БД развернутой в Docker контейнере.

- Шаг 1. - Создадим абстрактный класс [IntegrationTestBase.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/IntegrationTestBase.java) и аннотируем его как [@IT](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/annotation/IT.java), теперь все его наследники 
получат эту аннотацию. Именно в этом классе мы задействуем подключенную библиотеку TestContainers:

      private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

- Шаг 2. - Перенастроим наш [application-test.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/resources/application-test.yml) файл свойств для тестов под текущую БД. URL и порт будут определяться 
динамически через метод - *.getJdbcUrl(), из нашего объекта 'container', пароль и логин оставим дефолтные 'test' из кода
самого класса [PostgreSQLContainer](https://javadoc.io/static/org.testcontainers/postgresql/1.9.1/index.html?org/testcontainers/containers/PostgreSQLContainer.html) (см. [внутреннюю структуру](https://github.com/testcontainers/testcontainers-java/blob/main/modules/postgresql/src/main/java/org/testcontainers/containers/PostgreSQLContainer.java)):

        spring:
          datasource:
            username: test
            password: test
          jpa:
            properties.hibernate:
              hbm2ddl.auto: update

- Шаг 3. - Теперь унаследуем наши тестовые классы [CompanyRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/database/repository/CompanyRepositoryTest.java) и [UserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/database/repository/UserRepositoryTest.java) от 
[IntegrationTestBase.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/IntegrationTestBase.java). Аннотации [@IT](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/annotation/IT.java) и [@Sql](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/jdbc/Sql.html) в наших тестовых классах не нужны, т.к. они наследуются от родителя.

Все наши тесты до этого момента были написаны для работы со 'статичной' БД установленной на выделенную машину и при 
тестировании одни методы могли вносить неоткатываемые изменения в БД (использование аннотации @Commit), а другие 
тесты могли использовать эти изменения для своих нужд. 

Однако, при тестировании нашего приложения с использованием Docker контейнера и библиотеки TestContainers у нас при 
запуске каждого теста БД динамически 'поднимается', заполняется данными из [resources/sql_scripts/data.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/resources/sql_scripts/data.sql), и после 
прохождения теста 'обнуляется'. Т.е. каждый новый тест получает заново заполненную исходными данными БД. 

В такой ситуации некоторые тесты, использующие изменения внесенные другими тестами, как это было реализовано для 
локальной 'статичной' БД, будут заведомо провальными, т.к. при работе с 'динамической' БД изменения в ней не 
коммитятся. Поэтому такие тесты, либо придется переписать под текущую ситуацию, либо вывести из тестового потока 
применив аннотацию [@Disabled](https://junit.org/junit5/docs/5.0.0-M2/api/org/junit/jupiter/api/Disabled.html).

- Шаг 4. - Необходимо настроить наши тестовые методы так, чтобы их действия гарантированно откатывались после прохождения 
теста, т.е. недопустимо использование ручного аннотирования класса целиком или отдельного метода, как @Commit - иначе это
может нарушить тестовую последовательность и приведет к FAIL-у отдельных тестов или всех сразу. И так, убираем аннотацию 
@Commit у тестовых методов и классов.
- Шаг 5. - Применяем аннотацию [@Disabled](https://junit.org/junit5/docs/5.0.0-M2/api/org/junit/jupiter/api/Disabled.html) к методам логика выполнения которых не стыкуется с применяемой технологией 
тестирования, либо переписываем их сообразно оной.

Особенность динамического 'поднятия' БД в том, что она запускается один раз и далее переиспользуется перед каждым тестом,
см. комментарии над методом *.runContainer() в классе [IntegrationTestBase.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/IntegrationTestBase.java). 

Логика происходящего такова: 
1. создание таблиц БД происходит средствами Hibernate;
2. перед каждым тестовым методом таблицы заполняются из [data.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/resources/sql_scripts/data.sql) скрипта, указанного в параметрах @Sql класса [IntegrationTestBase.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/IntegrationTestBase.java);
3. после каждого тестового метода таблицы очищаются, т.е. происходит откат DML-операций; 

И т.д. по всей цепочке тестов до конца. Отсюда может возникнуть проблема и некоторые тесты (кроме самого первого в цепочке 
тестов), использующие в своем коде жестко фиксированные значения ID могут 'завалиться', т.к. при заполнении БД теми же 
самыми данными после 'отката' предыдущей DML-операции (база данных все та же) ID записи в таблице БД будет уже другим 
(т.к. значения sequence таблицы никто не отменял). 

Для решения данного недочета нам придется скорректировать наш data.sql, который хорошо работал для 'статической' БД, т.к. 
запускался единожды, и далее база подвергалась тестированию с внесением изменений.

- Шаг 6. - Добавим в SQL скрипт заранее заданные значения ID, в те таблицы, где это необходимо. Например, таблица users, 
см. методы *.checkAuditing() или *.checkUpdate() из тестового класса [UserRepositoryTest.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_13/src/test/java/spring/oldboy/integration/database/repository/UserRepositoryTest.java).
    
        -- Заполняем таблицу    
        INSERT INTO users (id, birth_date, firstname, lastname, role, username, company_id)
        VALUES (1, '1990-01-10', 'Ivan', 'Ivanov', 'ADMIN', 'ivan@gmail.com', (SELECT id FROM company WHERE name = 'Google')),
               (2, '1995-10-19', 'Petr', 'Petrov', 'USER', 'petr@gmail.com', (SELECT id FROM company WHERE name = 'Google')),
               (3, '2001-12-23', 'Sveta', 'Svetikova', 'USER', 'sveta@gmail.com', (SELECT id FROM company WHERE name = 'Meta')),
               . . .;
    
        -- Задаем следующее значение последовательности для таблицы users (Решаем проблему дублирования ключей)
        SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

В данном скрипте мы используем функцию [PostgreSQL: setval(regclass, bigint)](https://www.postgresql.org/docs/current/functions-sequence.html). Этой функцией мы решаем проблему дублирования 
ключей, т.к. sequence таблицы начинается с 1, а она с применением обновленного скрипта уже занята. Функция SETVAL() - 
устанавливает текущее (заданное) значение последовательности sequence. Мы извлекаем максимальное значение ID из таблицы 
и передаем его в функцию - ключи ID при внесении новых записей не будут дублироваться. Название sequence для конкретной 
таблицы обычно выглядит как: 'название таблицы'_id_seq 

Теперь, при каждом новом тесте, мы получим одни и те же значения ID (после 'отката' и нового 'наката' DML команд) для 
записей в БД. Тесты с фиксированными (хард-код) ID не будут 'заваливаться', см. пример:

    @Test
    void checkFirstTop() {
        Optional<User> topUser = userRepository.findTopByOrderByIdDesc();
        assertTrue(topUser.isPresent());
        topUser.ifPresent(user -> assertEquals(5L, user.getId()));
    }

См. док.:
- [PostgreSQL 16.2 Documentation](https://www.postgresql.org/docs/current/index.html) ;
- [Пакет org.junit.jupiter.api](https://junit.org/junit5/docs/5.0.0-M2/api/org/junit/jupiter/api/package-summary.html) ;
- [Пакет org.testcontainers.containers](https://javadoc.io/static/org.testcontainers/postgresql/1.9.1/index.html?org/testcontainers/containers/PostgreSQLContainer.html) ;
- [GitHub TestContainers](https://github.com/testcontainers) ;
- [GitHub JUnit](https://github.com/junit-team) ;
________________________________________________________________________________________________________________________
#### Динамическое развертывание PostgreSQL БД в Docker контейнере средствами TestContainers.

Чтобы наглядно увидеть, что же происходит при запуске наших тестов необходимо обратиться к Docker-у: до, вовремя, и после 
запуска тестов. Смотрим, что у нас получится.

Проверяем наличие скачанных образов (сам я скачал лишь один образ - postgres, и создал из него контейнер с именем my-postgres, 
который остановлен):

    $ docker images
    
    REPOSITORY            TAG       IMAGE ID       CREATED        SIZE
    postgres              latest    f7d9a0d4223b   6 weeks ago    417MB
    testcontainers/ryuk   0.5.1     ec913eeff75a   5 months ago   12.7MB

И так, мы видим, что при подключении зависимости и запуске тестов, судя по всему, был загружен еще один образ с 
IMAGE ID = ec913eeff75a. Теперь проверим наличие развернутых контейнеров, в данный момент приложение и тесты не 
запущены:

    $ docker ps -a

    CONTAINER ID   IMAGE      COMMAND                  CREATED      STATUS                          PORTS     NAMES
    5657620e4ed1   postgres   "docker-entrypoint.s…"   2 days ago   Exited (0) About a minute ago             my-postgres

Пока все в норме, мы имеем один незапущенный контейнер. Теперь проверим ситуацию в момент работы тестов, когда БД должна
динамически 'подниматься' при их запуске и 'гаситься' средствами TestContainers после их завершения: 

    $ docker ps -a
    
    CONTAINER ID   IMAGE                       COMMAND                  CREATED         STATUS                      PORTS                     NAMES
    49a7d48557f4   postgres:latest             "docker-entrypoint.s…"   3 seconds ago   Up 2 seconds                0.0.0.0:61448->5432/tcp   youthful_banach
    67f8bc7f865e   testcontainers/ryuk:0.5.1   "/bin/ryuk"              4 seconds ago   Up 3 seconds                0.0.0.0:61446->8080/tcp   testcontainers-ryuk-e7368060-4e36-40dd-aea8-e82b26a6d7b4
    5657620e4ed1   postgres                    "docker-entrypoint.s…"   2 days ago      Exited (0) 41 seconds ago                             my-postgres

И вот, мы видим, что в момент запуска тестов развертываются два контейнера, один из образа 'postgres:latest', а второй из 
образа 'testcontainers/ryuk:0.5.1' и самое важное и интересное это динамически созданные URL-ы:
- postgres:latest, имеет PORT в данной генерации 0.0.0.0:61448->5432/tcp (в предыдущей 0.0.0.0:62749->5432/tcp);
- testcontainers/ryuk:0.5.1, имеет PORT в текущей генерации 0.0.0.0:61446->8080/tcp (в предыдущей 0.0.0.0:62747->8080/tcp);

Один из контейнеров нужен для работы с тестовой PostgreSQL БД, второй лишь для того, чтобы 'поднять' первый перед запуском
тестов и 'погасить' его после окончания тестов. Это легко можно увидеть, если повторить команду просмотра существующих 
контейнеров после окончания тестов, буквально спустя пару секунд:

    $ docker ps -a
    
    CONTAINER ID   IMAGE      COMMAND                  CREATED      STATUS                      PORTS     NAMES
    5657620e4ed1   postgres   "docker-entrypoint.s…"   2 days ago   Exited (0) 17 minutes ago             my-postgres

Перед запуском тестов контейнеры создаются, отрабатывают положенный функционал, и после окончания тестов не просто 
останавливаются, а удаляются полностью. Перед запуском нового цикла тестирования приложения (даже одного тестового 
метода) процесс создания 'тестовых' контейнеров, их запуска, работы, останова и удаления повторяется.

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