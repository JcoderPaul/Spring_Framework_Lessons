### Spring Boot lessons part 14 - Database Migrations

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_14/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [Liquibase Documentation](https://docs.liquibase.com/home.html) ;
- [PostgreSQL Documentation (Manuals) 12-16](https://www.postgresql.org/docs/) ;
- [Docker Documentation](https://docs.docker.com/) ;
________________________________________________________________________________________________________________________
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку:

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/build.gradle) добавим необходимые plugin-ы: 

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

Шаг 6. - Для использования средств подобных Hibernate ENVERS подключим такую же поддержку от Spring:

    implementation 'org.springframework.data:spring-data-envers'

________________________________________________________________________________________________________________________
#### Lesson 66 - Миграционный фреймворк Liquibase (теория)

В ранних уроках можно было заметить, как при запуске тестов Hibernate формирует таблицы БД ('накатывает БД'). Делает он 
это сам и мы не можем повлиять на это процесс. Т.е. есть некий SQL скрипт и ORM фреймворк все делает жестко и 
безапелляционно. Однако, свойство востребованного программного продукта - постоянное изменение. Т.е. структура таблиц БД
(или schema), а значит и скриптов формирующих или изменяющих структуру таблиц будет меняться. 

Любое изменение, что в коде программы, что в структуре БД может внести турбулентность в работу продукта, значит у нас 
должна быть возможность откатить 'сомнительные' изменения в коде. И так, мы должны иметь возможность влиять на процесс 
создания и изменения схемы (schema) БД, а так же в любой момент времени должны иметь возможность вернуться к некому 
'удовлетворительному' состоянию БД в прошлом (и не только в случае фатальных ошибок); в ходе разработки софта у нас 
может измениться набор сущностей или их внутренняя структура и т.п.

Кроме того, в реальных больших проектах над продуктом работает не один человек, а целая команда. Значит, кроме прочего, 
необходимо отслеживать не только процесс критических изменений в схемах БД, но и тех, кто эти изменения вносил и когда.

Для реализации подобного функционала можно заморочиться и в структуру своей программы зашить код реализующий GIT подобные
методы для отслеживания и отката изменений в схемах БД. Либо можно использовать т.н. миграционные фреймворки, например, 
Flyway или Liquibase (см. [https://www.liquibase.org/](https://www.liquibase.com/)). 

Миграционные фреймворки используют похожий принцип (и схожие схемы реализации своего функционала), в случае Liquibase см. 
[DOC/Pictures/MigrationFrameworkStructure.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/Pictures/MigrationFrameworkStructure.jpg), имеются две таблицы: databasechangelog и databasechangeloglock. В процессе 
работы Liquibase отправляет запрос к БД, чтобы заблокировать доступ к БД для всех процессов способных внести изменения 
в схему базы. Т.е. в данной ситуации у других процессов (микросервисов), если их несколько, которые пытаются внести 
изменения в БД ('накатить' какие-либо скрипты), такой возможности не будет. 

Далее фреймворк lock-кирует базу, вносит изменения, и информация о текущих изменениях (примененном скрипте) заносится в 
таблицу databasechangelog. Именно в этой талице хранится информация о том какие изменения были совершены и кем, а также 
хэш скрипта внесшего изменения, чтобы повторно не 'накатывать' уже отработавший скрипт, а также для отслеживания того, 
были ли внесены какие-либо изменения в отработавшие свои изменения скрипты. Если вдруг в скрипт, который уже есть в 
таблице databasechangelog внесены изменения он не накатывается повторно, а выбрасывается исключение. 

Т.е. основная забота миграционного фреймворка отследить все изменения (скрипты), от начала до конца, каковые были 
применены к БД и не допустить нарушения порядка этих изменений, дабы сохранить возможность максимально корректного отката
внесенных изменений. 

Основные понятия в Liquibase (см. [https://docs.liquibase.com/concepts/home.html](https://docs.liquibase.com/concepts/home.html) и [DOC/Pictures/LiquibaseConcepts.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/Pictures/LiquibaseConcepts.jpg)):  
- changelog (журнал изменений) - текстовый файл журнала изменений для последовательного перечисления всех изменений, 
                                 внесенных в нашу базу данных. Этот реестр помогает Liquibase проверять нашу БД и 
                                 выполнять любые изменения, которые еще не применены. Мы можем хранить и редактировать 
                                 наш журнал изменений в любом инструменте контроля версий.
- changeset (набор изменений) - это базовая единица изменения в Liquibase, мы сохраняем все свои наборы изменений в 
                                нашем сhangelog-е (журнале изменений). Наш набор изменений содержит типы изменений, 
                                которые определяют, что делает каждое изменение, например: создание новой таблицы или 
                                добавление столбца в существующую таблицу.

    
    /* Обязательный комментарий в SQL скрипте помечающий 'набор изменений' как (автор изменений:id изменений) */
    --changeset nvoxland:1
    create table company (
    id int primary key,
    address varchar(255)
    );

Набор изменений помечен author и id, как уникальным атрибутом (author:id), так и путем к файлу журнала изменений. ID - 
это просто идентификатор, он не определяет порядок выполнения изменений и необязательно должен быть целым числом. Обычно 
набор изменений проходят в одной транзакции.

!!! ID уникален для конкретного автора (author) только в пределах одного журнала изменений (changelog-a) см. 
[DOC/Pictures/LiquibaseConcepts.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/Pictures/LiquibaseConcepts.jpg) и [DOC/Pictures/LiquibaseChangelogs.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/Pictures/LiquibaseChangelogs.jpg) !!!

- change (минимальное изменение) - минимальное изменение БД (создать таблицу, добавить/удалить столбец в таблице и т.д.)
                                   в пределах одного набора изменений.

!!! Несложно заметить см. [DOC/Pictures/LiquibaseConcepts.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/Pictures/LiquibaseConcepts.jpg), что в пределах одного набора изменений (changeset) может
находиться как один change, так и некое количество, но сам набор этих изменений находится в пределах одной транзакции. 
Хорошей практикой считается 'условная атомарность изменений' - когда в пределах одного набора изменений находится одно
изменение, т.е. одна транзакция - одно изменение в БД. Это позволяет проводить эффективный rollback (откат) изменений !!!

- checksums (контрольная сумма) - когда Liquibase во время выполнения достигает набора изменений (changeset) в нашем 
                                  журнале изменений (changelog), он вычисляет контрольную сумму и сохраняет ее в MD5SUM 
                                  столбце таблицы DATABASECHANGELOG. Это говорит Liquibase, был ли changeset изменен с 
                                  момента его запуска.

Поскольку файлов содержащих changelog-и может быть много ими тоже нужно управлять и обычно структура всех изменений в БД
под управлением Liquibase выглядит следующим образом см. [DOC/Pictures/StructureMigrationScriptFiles.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/Pictures/StructureMigrationScriptFiles.jpg) или [DOC/Pictures/LiquibaseChangelogs.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/Pictures/LiquibaseChangelogs.jpg).
Советы по оптимизации работы с миграционными фреймворками описаны в [DOC/ArticlesAboutLiquibase/Liquibase_10_advice_to_no_headache.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/ArticlesAboutLiquibase/Liquibase_10_advice_to_no_headache.txt).

См. статьи по Liquibase: 
- [DOC/ArticlesAboutLiquibase/DBMigrationManagement.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/ArticlesAboutLiquibase/DBMigrationManagement.txt);
- [DOC/ArticlesAboutLiquibase/Liquibase_10_advice_to_no_headache.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/ArticlesAboutLiquibase/Liquibase_10_advice_to_no_headache.txt);
- [DOC/ArticlesAboutLiquibase/LiquibaseShortTutorial.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/ArticlesAboutLiquibase/LiquibaseShortTutorial.txt);

См. статьи о миграции БД (как облегчить взаимодействие с миграционным фреймворком):
- [DOC/DatabaseMigration/DatabaseMigration.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/DatabaseMigration/DatabaseMigration.txt);
- [DOC/DatabaseMigration/DBMigrationFeatureFlags.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/DatabaseMigration/DBMigrationFeatureFlags.txt);
- [DOC/DatabaseMigration/FeatureFlag.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/DOC/DatabaseMigration/FeatureFlag.txt);

См. офф. документацию:
- [Liquibase](https://docs.liquibase.com/home.html) ;
- [Flyway](https://documentation.red-gate.com/fd) ;

________________________________________________________________________________________________________________________
#### Lesson 67 - Миграционный фреймворк Liquibase (практика)

- Шаг 1. - Для подключения Liquibase к нашему проекту вносим изменения в build.gradle (добавляем зависимость):

        implementation 'org.liquibase:liquibase-core'

Если заглянуть в код класса [LiquibaseProperties](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/liquibase/LiquibaseProperties.java), то можно обнаружить массу настроек которые в случае чего мы модем 
скорректировать если default-ные нас не устраивают. В частости, там мы можем увидеть где должен находиться управляющий
наборами изменений файл:

        private String changeLog = "classpath:/db/changelog/db.changelog-master.yaml";

- Шаг 2. - Создадим папку для хранения мастер-changelog файла см. [db.changelog-master.yaml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/main/resources/db/changelog/db.changelog-master.yaml) (и его 'подопечных' см. 
[db.changelog-1.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/main/resources/db/changelog/db.changelog-1.0.sql) и т.д.), согласно значениям по-умолчанию приведенным выше. Для удобства и простоты положим все файлы
журналов изменений в папку ресурсов: [resources/db/changelog](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_14/src/main/resources/db/changelog). Структура мастер-файла всех changelog-ов:

        databaseChangeLog: #параметр в котором находятся миграции
         - include: # список активных миграций
             file: db/changelog/***.yaml (***.sql) # путь к файлу миграции из classpath

- Шаг 3. - Заполняем файлы db.changelog-[number_of_changelog_version].sql (sql скриптами) предстоящими изменениями нашей 
БД. Это может быть большой sql script с множеством sql функций, например тут это создание таблиц, которые могут быть 
объединены в один changeset, но как было описано выше, чем меньше changeset, тем проще его откатывать, поэтому здесь 
одна sql-команда на один changeset (см. [db/changelog/db.changelog-1.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/main/resources/db/changelog/db.changelog-1.0.sql)):

        --liquibase formatted sql /* Специальная метка указывающая на changelog sql файл */
        
        --changeset oldboy:1 /* Специальная метка указывающая на changeset с ключом [имя_создателя:ID_changeset-a] */
        CREATE TABLE IF NOT EXISTS company
        (
        id SERIAL PRIMARY KEY ,
        name VARCHAR(64) NOT NULL UNIQUE
        );
        
        /* Метка отката предыдущего SQL запроса (команды), один changeset - одна команда - один откат */
        --rollback DROP TABLE company; 
        
        --changeset oldboy:2
        CREATE TABLE IF NOT EXISTS company_locales
        (
        company_id INT REFERENCES company (id),
        lang VARCHAR(2),
        description VARCHAR(255) NOT NULL ,
        PRIMARY KEY (company_id, lang)
        );

В нашем файле [db.changelog-1.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/main/resources/db/changelog/db.changelog-1.0.sql) мы имеем 6-ть changeset-ов, а значит будет запущено 6-ть транзакций, которые будет 
проще откатить, нежели сделай мы один большой changeset из 6-и sql запросов втиснув их в одну транзакцию. 

И так, мы имеем 3-и sql-файла с changelog-ами (т.е. весь процесс создания БД делаем мы 'руками', а не средствами Hibernate):
1. [db.changelog-1.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/main/resources/db/changelog/db.changelog-1.0.sql) - создание таблиц БД;
2. [db.changelog-2.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/main/resources/db/changelog/db.changelog-2.0.sql) - внесение изменений (добавили поля) в таблицу users для аудирования БД;
3. [db.changelog-2.1.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/main/resources/db/changelog/db.changelog-2.1.sql) - добавляем таблицы для аудита;

- Шаг 4. - Для проверки работы Liquibase почистим от таблиц базу в Docker контейнере. Далее запускаем наше 
приложение [SpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/main/java/spring/oldboy/SpringAppRunner.java) и смотрим, что произойдет. Если ошибок нет, то в нашей БД появятся все таблицы из
db.changelog-1.0.sql, в таблице users появятся столбцы из db.changelog-2.0.sql и будут созданы таблицы для аудита из
db.changelog-2.1.sql. И самое главное появятся две таблицы созданные Liquibase: databasechangelog и databasechangeloglock.

При этом в таблице databasechangelog уже будут записи проведенных миграций:

    id	author	filename	                        dateexecuted	          orderexecuted	  exectype	md5sum	                            description	comments	tag	  liquibase	 contexts	labels	deployment_id
    1	oldboy	db/changelog/db.changelog-1.0.sql	5 ноя 23 'г'., 17:44:56	  1	          EXECUTED      8:6885701ee92d04fb09bef0325c8914de	sql			          4.20.0			        9195495519
    2	oldboy	db/changelog/db.changelog-1.0.sql	5 ноя 23 'г'., 17:44:56	  2	          EXECUTED      8:671c8d8c96f99dacb448f1ae1f3e4e24	sql			          4.20.0			        9195495519
    3	oldboy	db/changelog/db.changelog-1.0.sql	5 ноя 23 'г'., 17:44:56	  3	          EXECUTED      8:71f5eff24e711bdce23cca836da4b4f8	sql			          4.20.0			        9195495519
    4	oldboy	db/changelog/db.changelog-1.0.sql	5 ноя 23 'г'., 17:44:56	  4	          EXECUTED      8:8a7750aad01fc073048e4674b72b3ca6	sql			          4.20.0			        9195495519
    5	oldboy	db/changelog/db.changelog-1.0.sql	5 ноя 23 'г'., 17:44:56	  5	          EXECUTED      8:8363e57f7ba2724ce28e30b70b518bc5	sql			          4.20.0			        9195495519
    6	oldboy	db/changelog/db.changelog-1.0.sql	5 ноя 23 'г'., 17:44:56	  6	          EXECUTED      8:8f612557be86189820ba52b52a321b03	sql			          4.20.0			        9195495519
    1	oldboy	db/changelog/db.changelog-2.0.sql	5 ноя 23 'г'., 17:44:56	  7	          EXECUTED      8:a214acf16f900e5b7b1c316fe0459bcd	sql			          4.20.0			        9195495519
    1	oldboy	db/changelog/db.changelog-2.1.sql	5 ноя 23 'г'., 17:44:56	  8	          EXECUTED      8:340ecd45e2f311ec6736346bf9371a0f	sql			          4.20.0			        9195495519
    2	oldboy	db/changelog/db.changelog-2.1.sql	5 ноя 23 'г'., 17:44:56	  9	          EXECUTED      8:4be0aeb5e449a3461e1e549e177c8ecb	sql			          4.20.0			        9195495519

________________________________________________________________________________________________________________________
#### Lesson 68 - Миграционный фреймворк Liquibase (практика) ч.2 - Liquibase в тестах

#### Особенность 1:

Как уже было написано выше все примененные миграции имеют свой рассчитанный хэш см. таблицу выше столбец md5sum и если 
внести изменения в код changeset-а, то значение контрольной суммы изменится, и мы словим исключение. Это легко проверить,
умышленно изменим скрипт в db.changelog-2.1.sql (заменим SERIAL на BIGSERIAL):

    --changeset oldboy:1
    CREATE TABLE IF NOT EXISTS revision
    (
    id BIGSERIAL PRIMARY KEY ,
    timestamp BIGINT NOT NULL
    );

Запустим наше приложение еще раз, в результате на экране:
    
    Caused by: liquibase.exception.ValidationFailedException: Validation Failed:
                   1 changesets check sum
                     db/changelog/db.changelog-2.1.sql::1::oldboy 
                            was: 8:340ecd45e2f311ec6736346bf9371a0f but is 
                            now: 8:9e3d00afcdd2f5bb1a327ab26ee08cc5

Свою задачу по контролю последовательности вносимых изменений Liquibase блюдет четко. Конечно мы можем умышленно удалить 
из выше показанной таблицы databasechangelog вторую снизу запись, относящуюся к "changeset oldboy:1" из db.changelog-2.1.sql.
И тогда при запуске приложения Liquibase не найдя этих изменений в контрольной таблице снова их применит ('накатит').

#### Особенность 2:

Поскольку теперь мы используем миграционный фреймворк для работы с нашей БД, т.е. сами заботимся о создании и изменений 
всех таблиц и записей, а так же еще и отслеживаем эти изменения, то не можем допустить некие неконтролируемые манипуляции
с БД, например, изменения вносимые Hibernate. Изменим настройку в файле тестовых свойств [application-test.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_14/src/test/resources/application-test.yml):

    spring:
      datasource:
        ...
      jpa:
        properties.hibernate:
          hbm2ddl.auto: update /* меняем на validate */ 

Т.е. теперь, ни в самом приложении, ни в тестах, мы не пользуемся автоматической генерацией от Hibernate. Мы должны 
отслеживать все что происходит с БД и с этого момента используем миграционный фреймворк. Ну и напоследок, при текущей 
настройке нашего проекта мы можем запустить все интеграционные тесты разом и убедиться, что все работает.

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