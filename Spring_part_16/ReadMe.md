### Spring Boot lessons part 16 - Web Starter - Part 2

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

---------------------------------------------------------------------------------------------------------------
Для начала проведем предварительную подготовку (первые 6-ть шагов из предыдущих частей, некоторые пропущены):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/build.gradle) добавим необходимые plugin-ы: 

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

Для работы с PostgreSQL подключим и его зависимости:

    implementation 'org.postgresql:postgresql'

Шаг 6. - Для использования средств подобных Hibernate ENVERS:

    implementation 'org.springframework.data:spring-data-envers'

Шаг 7. - Подключим миграционный фреймворк Liquibase:

    implementation 'org.liquibase:liquibase-core'

Шаг 8. - Подключаем Wed - Starter:

    implementation 'org.springframework.boot:spring-boot-starter-web'

Шаг 9. - Подключим Jasper, пока мы не используем Thymeleaf:

    implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'

---------------------------------------------------------------------------------------------------------------
#### Lesson 77 - CRUD - API Design на уровне Controller.

Существует некий негласный стандарт или Best Practices при разработке своего API (естественно необязательный, но все же 
лучше его придерживаться), для примера можно рассмотреть вариант предложенный в статье: [RESTful API Design](https://phauer.com/2015/restful-api-design-best-practices/).
или [DOC/API_Design/API_Design_Best_Practices.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/DOC/API_Design/API_Design_Best_Practices.txt). 

Попробуем реализовать 'нашу API' согласно предложенному материалу. Создадим еще один контроллер для работы с сущностями
[User](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/database/entity/User.java), который будет реализовывать CRUD операции - [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java). Для создания и редактирования полей User-ов нам
нужен DTO - [UserCreateEditDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/UserCreateEditDto.java).

Еще раз освежим в памяти ['послойную схему MVC'](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Spring_MVC.jpg).

---------------------------------------------------------------------------------------------------------------
#### Lesson 78 - CRUD - API Design на уровне Service.

На уровне контроллеров мы создали каркас методов реализующих наш API для класса User - [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java). Теперь нам 
нужно реализовать уровень сервисов, т.е. его CRUD методы - [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java): 
- На уровне сервисов у нас уже есть UserService доставшийся нам с первых уроков. Изменим его (естественно если бы его 
не было мы бы его создали).
- Дополним полями [UserReadDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/UserReadDto.java), тот, что мы создали на прошлом уроке;
- Добавим к 'id' еще один принимаемый параметр в [CompanyReadDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/CompanyReadDto.java), это 'name' - название компании;
- Временно подправим методы в других классах (и в тестах, в том числе) на которые влияет обновленный CompanyReadDto, 
просто в параметры вместо названия компании внесем пока 'null'. 
- Нам понадобятся преобразователи одного класса в другой, или ['мапперы' - mappers](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/src/main/java/spring/oldboy/mapper), для примера можно вернуться к урокам
по [HTTP_Servlets_Java_EE/MVCPractice/](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/tree/master/MVCPractice). 
Для этого создаем папку [mapper](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/src/main/java/spring/oldboy/mapper) и сразу основной интерфейс [Mapper<F, T>](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/Mapper.java), в дальнейшем, все классы 'мапперы' будут 
реализовывать его методы *.map().
- Создаем первый маппер [UserReadMapper.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/UserReadMapper.java) в котором метод *.map() см. ниже, преобразует объект [User](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/database/entity/User.java) в [UserReadDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/UserReadDto.java):

        @Override
        public UserReadDto map(User object) {
            return new UserReadDto(
                      object.getId(),
                      object.getUsername(),
                      object.getBirthDate(),
                      object.getFirstname(),
                      object.getLastname(),
                      object.getRole(),
                      company
            );
        }

И вот тут вместо компании, в качестве параметра наш [UserReadDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/UserReadDto.java) получает [CompanyReadDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/CompanyReadDto.java), а значит нам понадобится еще 
один преобразователь.
- Создаем второй маппер-преобразователь [CompanyReadMapper.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/CompanyReadMapper.java), который реализуя метод *.map() и из Company сделает 
CompanyReadDto.
- Оба маппера мы аннотируем как компонент Spring или [@Component](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html). Еще раз вспомним, что [@Component](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html) в Spring была создана 
для того, чтобы избавить разработчиков от необходимости определения bean-ов в XML. Вместо этого, Spring может 
автоматически находить классы с этой аннотацией при сканировании classpath и автоматически регистрировать их как bean-ы.
- Теперь у нас появилась возможность внедрять наши классы мапперы туда куда это необходимо.
- В текущей ситуации нам в [UserReadMapper](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/UserReadMapper.java) нужно инжектировать (внедрить) [CompanyReadMapper](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/CompanyReadMapper.java), что мы и делаем см. код. 
[UserReadMapper.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/UserReadMapper.java).

Теперь в нашем [UserService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java) мы можем использовать наш [UserReadMapper](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/UserReadMapper.java).

- У нас уже есть [UserRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java) аннотированный как [@Repository](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html) и он уже внедрен в [UserService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java). Теперь для реализации 
метода [*.findAll()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java#L38) у нас все есть, прописываем его код.
- Возвращаемся на уровень контроллеров - в наш [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java) и придаем методу [*.findAll(Model model)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java#L50) рабочий вид.

И так первый метод на уровне контроллера и сервиса реализован, продолжаем, теперь займемся методом *.findById():
-  Метод *.findById() в интерфейсе [UserRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java) в явном виде отсутствует, однако он есть в интерфейсе 
[CrudRepository<T, ID> extends Repository<T, ID>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html) пакета [org.springframework.data.repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/package-summary.html).
- Однако данный метод вернет Optional<User>, а нам нужен Optional<UserReadDto> и мы снова используем наш [UserReadMapper](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/UserReadMapper.java),
чтобы преобразовать один класс в другой в пределах класса Optional [см. метод и комментарии к нему](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java#L49) в [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java).
- На уровне контроллеров [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java) на запрос - найти User-a по ID мы должны вернуть станицу с данными, либо
вернуть 404 статус, если пользователя с нужным ID не было найдено.

Теперь займемся созданием User-a или реализуем метод *.create():
- В данном случае метод в качестве параметра получает объект класса [UserCreateEditDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/UserCreateEditDto.java), который мы должны преобразовать
в [User](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/database/entity/User.java) и передать в БД (или сохранить в БД), [см. более подробно метод *.create()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java#L100) в [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java).
- Для преобразования UserCreateEditDto в User создаем [UserCreateEditMapper.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/mapper/UserCreateEditMapper.java), так же помечаем его как @Component и 
внедряем [CompanyRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/database/repository/company_repository/CompanyRepository.java), чтобы получать данные о компаниях с которыми связан User по ее ID.
- На уровне контроллеров, в [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java) дописываем [метод *.create()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java#L97).
- Поскольку после создания новой записи в БД, или после создания некоего ресурса, мы должны об этом сообщить, применяем
аннотацию [@ResponseStatus](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html)(HttpStatus.CREATED) над [нашим методом *.created()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java#L96).

ТЕПЕРЬ НУЖНО ВСПОМНИТЬ О ТРАНЗАКЦИЯХ! 

Поскольку мы делаем запросы к БД, то мы используем транзакции. Пока на уровне сервисов мы их не использовали, или явно 
не задействовали. Основная их часть приходится на [уровень репозитория или UserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/src/main/java/spring/oldboy/database/repository). Однако на уровне 
сервисов могут проходить запросы, которые могут подтягивать lazy initialization сущности, что может привести к броску 
исключения если такую сущность не подтянуть из базы при запросе. Поэтому на уровне сервисов у нас тоже нужны транзакции.
Тогда транзакция будет открываться в момент обращения к методу и закрываться автоматически после его завершения. Это 
можно реализовать при помощи аннотации [@Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html) над всеми классами слоя сервисов, у нас это пока [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java).
Так же следует отметить, что правила хорошего тона рекомендуют в параметрах аннотации передавать 'readOnly = true'.

Поскольку на уровне сервисов у нас есть методы в которых мы не просто читаем информацию, а вносим ее, изменяем, именно
их (помимо самого класса) мы отдельно (каждый, где необходимо) аннотируем той же [@Transactional](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java), но уже без параметров,
см. [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java). Это позволит вносить изменения без бросков исключений в методах типа *.create(), *.edit() и т.д.

И так с транзакциями разобрались, продолжаем дорабатывать слой сервисов и контроллеров, реализуем метод *.update():
- Поскольку мы обращаемся к БД (к User) по ID, то снова можем столкнуться с ситуацией когда не нашли требуемую запись и 
значит должны вернуть (отобразить) Optional объект - Optional<UserReadDto> см. [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java) в методе [*.update()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java#L119).
- На уровне контроллеров у нас может быть ситуация, когда мы отображаем изменения внесенные в данные user-a по 
введенному ID (в случае если он есть) или возвращаем статус HTTP 404 - NOT FOUND (в случае если user с нужным ID не 
найден), что мы и реализуем [см. *.update()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java#L112) в [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java).

Теперь займемся удалением user-ов, это метод *.delete():
- На уровне сервисов данный метод однозначно транзакционный и вносит изменения в БД, по-этому он помечается аннотацией 
[@Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html). Для того чтобы на уровне контроллеров иметь выбор и возможность вернуть страницу отображения или страницу
с кодом 404 - NOT FOUND (в случае успеха или неуспеха соответственно) данный метод [*.delete()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/service/UserService.java#L135) на уровне сервисов 
возвращает булеву переменную.
- На [уровне контроллеров](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/src/main/java/spring/oldboy/http/controller) есть свой одноименный [метод *.delete()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/http/controller/UserController.java#L123), который обрабатываем результат работы метода удаляющего
user-a на уровне сервисов и возвращает результат удаления в качестве страницы отображения.

См. коды HTTP статусов: [DOC/HTTP_Status](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/DOC/HTTP_Status)
См. док.: 
- [Пакет org.springframework.data.repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/package-summary.html) ;
- [Пакет org.springframework.stereotype](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/package-summary.html) ;
- [Пакет org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html) ;
- [Пакет org.springframework.transaction.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/package-summary.html) ;

---------------------------------------------------------------------------------------------------------------
#### Lesson 79 - Тестирование разработанного CRUD API на уровне СЕРВИСОВ (Service).

Небольшое напоминание. Мы используем БД развернутую в Docker контейнере, настройки доступа к ней прописаны в файле 
[application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/resources/application.yml) (путь к базе, логин и пароль), настраиваем нашу IDE. Теперь, в [разделе Test](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/src/test), создаем файл для проведения 
интеграционных тестов - [UserServiceIT.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/test/java/spring/oldboy/integration/service/UserServiceIT.java), данный класс будет наследником [IntegrationTestBase](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/test/java/spring/oldboy/integration/IntegrationTestBase.java).

На уровне сервисов у нас 4-и основных метода (создать запись - CREATE, найти запись - READ, изменить запись - UPDATE, 
удалить запись - DELETE) отвечающих стандарту и естественно есть и другие расширяющие возможности нашего WEB приложения, 
пока всех методов 5-ть. Это значит, что в разделе тестов у нас будет как минимум 5-ть тестов для проверки наших CRUD 
операций. Хотя все тесты помечены аннотацией [@Test](https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Test.html) и нынче не требуется в названии тестового метода явно прописывать 
слово Test (как это было в JUnit 4), все же пропишем его, чтобы визуально отделять методы из разных классов (слоев, 
разделов).

См. комментарии в: [src\test\java\spring\oldboy\integration\service\UserServiceIT.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/test/java/spring/oldboy/integration/service/UserServiceIT.java)
См. док.:
- [Пакет org.junit.jupiter.api](https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/package-summary.html) - JUnit Jupiter API для написания тестов ;

---------------------------------------------------------------------------------------------------------------
#### Lesson 80 - Тестирование разработанного CRUD API на уровне КОНТРОЛЛЕРОВ (Controller).

Для начала тестирования слоя контроллеров в нашем приложении создадим [UserControllerIT.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/test/java/spring/oldboy/integration/http/controller/UserControllerIT.java), в нем и будут находиться
тестовые методы. Тут нам придется имитировать запрос по HTTP протоколу и значит мы будем использовать инструментарий [Mockito](https://site.mockito.org/). Для 
авто-конфигурирования Mockito мы помечаем наш тестовый класс аннотацией [@AutoConfigureMockMvc](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/web/servlet/AutoConfigureMockMvc.html).

Особенность пакета [Spring-Boot-Autoconfigure-API](https://github.com/spring-projects/spring-boot/tree/main/spring-boot-project/spring-boot-test-autoconfigure/src/main/java/org/springframework/boot/test/autoconfigure) в том, что он содержит большой набор инструментов для тестирования 
отдельных частей (слоев) нашего приложения, а самое главное фреймворков, которые мы можем применять при разработке
нашего сервиса, что значительно экономит время.

Теперь в нашем тестовом классе [UserControllerIT](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/test/java/spring/oldboy/integration/http/controller/UserControllerIT.java) появилась возможность внедрить объект класса [MockMvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html) и использовать его
возможности см. код класса, комментарии и док. [DOC/MockMvc](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/DOC/MockMvc). 

Мы уже отмечали, что на уровне контроллеров у нас открываются транзакции, но по-хорошему так делать не надо, для этого 
мы внесем изменения в файл свойств - сделаем параметр open-in-view как false в свойствах JPA (см. [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/resources/application.yml)):

    # Настроим свойства Hibernate
    jpa:
      properties.hibernate:
        batch_size: 50
        fetch_size: 50
        show_sql: true
        format_sql: true
        hbm2ddl.auto: validate
      open-in-view: false

Это позволит открывать транзакции только на уровне сервисов, это более правильный подход.

См. комментарии в: [src\test\java\spring\oldboy\integration\http\controller\UserControllerIT.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/test/java/spring/oldboy/integration/http/controller/UserControllerIT.java)
См.док.:
- [Пакет org.springframework.boot.test.autoconfigure.web.servlet](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/web/servlet/package-summary.html) ;
- [Пакет org.springframework.test.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/package-summary.html) ;

---------------------------------------------------------------------------------------------------------------
#### Lesson 81 - Конвертор дат.

При тестировании нашего слоя контроллеров мы столкнулись с проблемой отправки дат, причем явно эта проблема обрисовалась
только на этапе тестирования в методе [*.createControllerTest()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/test/java/spring/oldboy/integration/http/controller/UserControllerIT.java#L57), где мы имитируем HTTP POST запрос и передаем 
[UserCreateEditDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/UserCreateEditDto.java) полем которого является LocalDate birthDate. Для решения этой задачи существует 3-и решения:

- Решение 1: Настройка файла свойств [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/resources/application.yml) добавим в раздел spring подраздел format:

      spring:
        mvc:
          view:
            prefix: /WEB-INF/jsp/
            suffix: .jsp
        format:
          date: iso  

В параметр date устанавливаем значение iso. Теперь при тестировании нашего слоя контроллеров мы сможем передавать даты,
либо (просто времени) сочетание даты и времени. И обычно рекомендуется использовать данный способ, как состыкованный с 
авто-конфигурацией Spring-a.

- Решение 2: Данный вариант может понадобиться, когда клиент отправляет специальным образом отформатированную дату. Для
этого мы используем аннотацию [@DateTimeFormat](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/annotation/DateTimeFormat.html)(pattern = "yyyy-MM-dd") из пакета [org.springframework.format.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/annotation/package-summary.html). 
Она ставится над одним из полей, которое требует специального преобразования см. [UserCreateEditDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/dto/UserCreateEditDto.java), в параметрах 
передается паттерн преобразования полученных данных.

- Решение 3: Сей вариант тяжел и мощен, т.к. требует вмешательство в конфигурацию Spring-a и создания своих 
конфигурационных классов, в нашем случае - [WebConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/src/main/java/spring/oldboy/config/WebConfiguration.java). В нем мы переопределим *.addFormatters()
метод, интерфейса [WebMvcConfigurer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html) см. [WebMvcConfigurer.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_16/DOC/WebMvcConfiguration/WebMvcConfigurer.txt).

В принципе все три решения реализованные одновременно будут работать, но лучше выбрать первый вариант.

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