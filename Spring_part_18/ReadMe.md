### Spring Boot lessons part 18 - Validation Starter

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку (первые 6-ть шагов из предыдущих частей, некоторые пропущены):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/build.gradle) добавим необходимые plugin-ы: 

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

Шаг 9. - Поскольку сейчас мы начинаем изучение Thymeleaf, то нам нужно его подключить:

    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'

Хотя мы уже не используем явно *.JSP страницы, мы все же оставим зависимость Jasper-a:

    implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'

________________________________________________________________________________________________________________________
#### Lesson 89 - Validation-Starter - Простой пример валидации получаемых данных.

Для подгрузки зависимостей Validation Starter-а прописываем его в нашем [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/build.gradle):

    implementation 'org.springframework.boot:spring-boot-starter-validation'

Если все прошло нормально, то в зависимостях Gradle-а мы увидим validation-api (Hibernate JSR303) см. [DOC/GradleDependencies.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/DOC/GradleDependencies.jpg)
совсем немного мы затронули данный вопрос в [Hibernate lessons part 12 - Practice](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_practice) урок [Lesson 47](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_practice/src/main/java/oldboy/lesson_47).
Если нам не нужны особые способы обработки ошибок при валидации, то обычно встроенных аннотаций из данной спецификации 
будет достаточно.

Документация см. (ENG):
- [Hibernate Validator 8.0.1.Final](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#preface);
- [Validation APIs](https://beanvalidation.org/2.0/spec/#validationapi-validatorapi);
- [Пакет javax.validation](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/validation/package-summary.html);
- [Java Bean Validation](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html);
- [Jakarta Bean Validation specification](https://beanvalidation.org/2.0/spec/);

Краткое описание валидации в статьях см.:
- [DOC/ArticleAboutValidation](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/DOC/ArticleAboutValidation);
- [DOC/JakartaBeanValidation](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/DOC/JakartaBeanValidation);

В качестве примера:

Шаг 1. - поставим некоторые аннотации над классами нашей модели, например, [UserCreateEditDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/dto/UserCreateEditDto.java) см. комментарии в коде 
класса;

Шаг 2. - запускаем процесс валидации аннотировав соответствующим образом аргумент UserCreateEditDto переданный в метод 
контроллера, в нашем случае это метод [*.update()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java#L225) из [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java):

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated UserCreateEditDto user) {
        // ... some code ...
    }

Тут мы могли применить либо [@Valid](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/valid) из [jakarta.validation](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/package-summary), либо, как в нашем случае [@Validated](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/Validated.html) из 
[org.springframework.validation.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/package-summary.html). Особенность аннотации [@Validated](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/Validated.html) в том, что она к основному 
функционалу добавляет возможность валидации по группам (хотя с jakarta.validation сие тоже возможно).

В данном случае мы сделали грубую валидацию, без каких либо обработок и подсказок на стороне фронтэнда, что легко 
проверить запустив приложение и в форме [templates/user/user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/resources/templates/user/user.html) умышленно внести ошибку, например, вместо email-a 
передать бессмысленную строку. Мы получим: Whitelabel Error Page с There was an unexpected error (type=Bad Request, 
status=400).

См. док.:
- [Пакет org.springframework.validation.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/package-summary.html) ;
- [Пакет jakarta.validation](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/package-summary) ;

________________________________________________________________________________________________________________________
#### Lesson 90 - Validation-Starter - Чуть более дружелюбный отклик приложения при валидации данных.

Первый пример валидации показал, что использование аннотаций при валидации данных, вроде бы простая процедура. Однако 
такой грубый вариант не дружественен к пользователю приложения и необходимо такие неверные запросы обрабатывать и 
подсказывать пользователю, что нужно сделать для исправления ситуации, т.к. это все же его ошибка. Чуть улучшим 
дружелюбие интерфейса на примере формы регистрации - [templates/user/registration.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/resources/templates/user/registration.html).

Шаг 1. - Добавим аннотацию [@Validated](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/Validated.html) к передаваемому аргументу ['UserCreateEditDto user' метода *.create()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java#L202) нашего 
контроллера;

Шаг 2. - Туда же в аргументы внедряем новый объект [BindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindingResult.html) из [org.springframework.validation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/package-summary.html) см. ниже;

    @PostMapping
    public String create(@ModelAttribute @Validated UserCreateEditDto user,
                                                    BindingResult bindingResult,                                         
                                                    RedirectAttributes redirectAttributes) {
        // ... some code ...
    }
________________________________________________________________________________________________________________________
    !!! Особенность !!! Как это ни странно, при добавлении BindingResult важно его местоположение в последовательности 
                        передаваемых аргументов метода - сразу после аргумента помеченного как @Validated !!! 
________________________________________________________________________________________________________________________

В данном случае мы указываем системе, что сами планируем обрабатывать ошибки и объект [BindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindingResult.html) будет их хранить. 
Естественно мы получая к ним доступ можем взаимодействовать, как с приложением, так и с пользователем см. 
[DOC/BindingResult.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/DOC/BindingResult.txt).

Шаг 3. - В форму регистрации [templates/user/registration.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/resources/templates/user/registration.html) добавляем 'обработчик ошибок' - соответствующий блок 
отображающий проблемы при валидации введенных данных.

    <div th:if="${errors}">
        <p style="color: red" th:each="error : ${errors}" th:text="${error.defaultMessage}">Error message</p>
    </div>

Запускаем приложение, переходим в форму регистрации, вносим ошибки при заполнении формы и смотрим, что получилось.

См. док.:
- [Пакет org.springframework.validation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/package-summary.html) ;

________________________________________________________________________________________________________________________
#### Lesson 91 - Custom-validator - Самописные валидаторы или самописные аннотации для валидации.

Предположим, что написанных за нас и для нас аннотаций из [validation пакетов Jakarta](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/package-summary) и [org.springframework.validation.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/package-summary.html) не хватает. 
Мы можем сами создать интерфейс валидатора:

Шаг 1. - Создадим папку validation, где будем хранить наши файлы ограничений (валидации).

Шаг 2. - В папке [validation](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/src/main/java/spring/oldboy/validation) создадим нашу первую самописную аннотацию для валидации [UserInfo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/validation/UserInfo.java), в которой должны 
быть обязательные поля см. ниже:
    
    String message() default "... некое сообщение об ошибке валидации ... "; // Задали выводимое сообщение
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

И аннотации нас самим классом:

    @Constraint(validatedBy = {  })
    @Target(ElementType.TYPE, ...)
    @Retention(RetentionPolicy.RUNTIME)

См. более подробные примеры в [DOC/ValidationWithSpringBoot/ValidationWithSpringBoot.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/DOC/ValidationWithSpringBoot/ValidationWithSpringBoot.txt) и [DOC/ArticleAboutValidation](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/DOC/ArticleAboutValidation).

Шаг 3. - Создаем сам валидатор, который будет обрабатывать нашу аннотацию [UserInfo](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/validation/UserInfo.java), это будет [UserInfoValidator.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/validation/impl/UserInfoValidator.java) и 
реализуем метод [*.isValid(UserCreateEditDto value, ConstraintValidatorContext context)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/validation/impl/UserInfoValidator.java#L41).

Шаг 4. - Применяем нашу аннотацию над классом [UserCreateEditDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/dto/UserCreateEditDto.java) и удаляем аннотации [@NotNull](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/notnull) с полей lastname и 
firstname, т.к. проверку валидности этих переменных будет отрабатывать наш самописный валидатор. 

Хороший пример, который описывает наши шаги приведен в [DOC/ValidationWithSpringBoot/ValidationWithSpringBoot.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/DOC/ValidationWithSpringBoot/ValidationWithSpringBoot.txt)

Шаг 5. - Запускаем наше приложение, обращаемся к форме регистрации, вносим ошибки при заполнении, и видим результат 
работы нескольких валидаций в классе UserCreateEditDto и нашего валидатора, в том числе.

См. док.:
- [Пакет jakarta.validation.constraints](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary) ;

________________________________________________________________________________________________________________________
#### Lesson 92 - Custom-validator - Использование групп проверки.

Используя аннотацию [@Validated](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/Validated.html) см. [DOC/InterfaceValidated.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/DOC/InterfaceValidated.txt) мы можем управлять группами см. 
[DOC/ValidationWithSpringBoot/ValidationWithSpringBoot.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/DOC/ValidationWithSpringBoot/ValidationWithSpringBoot.txt), как бы разделяя логику проверки на различные этапы и при 
различных условиях работы приложения.

Шаг 1. - Зайдем в наш [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java), и в методе [*.create()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java#L202) в аннотации @Validated добавлю группу Default.class,
она запускает все аннотации не использующие группы и предоставленные Spring. Если ее не указать в списке групп, то мы
переназначим настройки запускаемых аннотаций (лучше ее оставлять):

    @PostMapping
    public String create(@ModelAttribute @Validated ({Default.class, CreateAction.class}) UserCreateEditDto user,
                                                                                     BindingResult bindingResult,
                                                                           RedirectAttributes redirectAttributes) {
    }

Шаг 2. - Создадим интерфейс метку [CreateAction](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/validation/group/CreateAction.java), группа это обычно класс или интерфейс маркер и никакой программной 
логики в себе не несет. Наши интерфейсы метки поместим в папку [java/spring/oldboy/validation/group](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/src/main/java/spring/oldboy/validation/group). Туда же добавим
еще интерфейс [UpdateAction](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/validation/group/UpdateAction.java). Его мы добавим в аннотацию метода [*.update()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java#L225):

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) UserCreateEditDto user) {
    }

В данном случае мы как бы разделяем проверку на два отдельных направления первая будет активна на этапе создания User, 
а вторая на этапе обновления его.

Шаг 3. - Добавляем нашу метку (интерфейс маркер) или группу в аннотацию [@UserInfo](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/dto/UserCreateEditDto.java#L32), класса [UserCreateEditDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/dto/UserCreateEditDto.java):

    @UserInfo(groups = CreateAction.class)
    public class UserCreateEditDto {
    }

Применив (groups = CreateAction.class), мы как бы говорим, что проверку валидности нужно проводить на этапе создания и 
только. Если же мы применим (groups = UpdateAction.class), то проверок на этапе создания проводится не будет, только 
обновления. Это легко проверить изменяя маркерные интерфейсы в параметрах аннотации @UserInfo. При различных параметрах 
будут активироваться разные сообщения об ошибках валидности, в зависимости от примененного группы - интерфейса маркера.

________________________________________________________________________________________________________________________
#### Lesson 93 - ControllerAdvice и ExceptionHandler - Самописные обработчики исключений.

В уроке 90, мы пытались по своему обработать отклик стандартного Spring валидатора на умышленно внесенных ошибки, мы 
сделали страницу отображения более информативной для пользователя, показывая ему подсказки связанные с ошибками. Для 
этого мы использовали объект [BindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindingResult.html). В принципе это интересный вариант обработки ошибок валидации.

Однако существует другой вариант, когда мы применяем обработчики исключений - [ExceptionHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html). Мы также можем пойти 
двумя путями и в первом случае написать метод обработчик исключений прямо в теле контроллера, например, UserController.

Данный способ более универсальный и часто используется в REST API.
________________________________________________________________________________________________________________________
REST (от англ. REpresentational State Transfer — «передача репрезентативного состояния» или «передача „самоописываемого“ 
состояния») — архитектурный стиль взаимодействия компонентов распределённого приложения в сети. Другими словами, REST - 
это набор правил того, как программисту организовать написание кода серверного приложения, чтобы все системы легко 
обменивались данными и приложение можно было масштабировать. 

REST представляет собой согласованный набор ограничений, учитываемых при проектировании распределённой гипермедиа-
системы. В определённых случаях (интернет-магазины, поисковые системы; прочие системы, основанные на данных) это 
приводит к повышению производительности и упрощению архитектуры. 
________________________________________________________________________________________________________________________

Попробуем реализовать наш обработчик исключений валидации:

Шаг 1. - Создадим локальный [обработчик ошибок - метод в UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java#L68), и аннотируем наш контроллер @Slf4j:

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }

Теперь наш обработчик ловит исключения и отображает некую страницу с ошибкой.

Шаг 2. - В папке где хранятся наши *.HTML страницы создадим еще папку ['error'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/src/main/resources/templates/error), где будут храниться страницы отображения 
ошибок, возвращаемые пользователю при бросках исключений.

Шаг 3. - Создадим страницу отображения в папке ['error'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/src/main/resources/templates/error) для показа пользователю, в нашем методе [*.handleExceptions()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java#L69) она 
уже прописана как возвращаемая на запрос в случае броска исключения - [templates/error/error500.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/resources/templates/error/error500.html).

Щаг 4. - Проверим работу обработчика - запустим приложение и обратимся к методу *.update(). Поскольку в него мы не 
передаем BindingResult, то ошибка валидации при работе этого метода подхватится нашим глобальным обработчиком исключений
и отобразится страница - [error500.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/resources/templates/error/error500.html).

При запуске приложения в режиме DEBUG мы можем наглядно отследить, как происходит работа метода обработчика исключений и
какие объекты созданы, какие объекты сгенерировали ошибки, какие ошибки отловлены см. [DOC/MyHandleExceptionsWork.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/DOC/MyHandleExceptionsWork.jpg)
Фактически мы снова получили в распоряжение BindingResult завернутый в исключение.

Естественно так будет вести себя наш обработчик только внутри нашего UserController-а, а что если мы хотим подобным 
образом обрабатывать ошибки валидации во всех контроллерах. 

Сделаем это.

________________________________________________________________________________________________________________________
#### Lesson 94 - ControllerAdvice и ExceptionHandler - Глобальный самописный обработчик исключений.

Для решения вопроса обработки исключения в процессе валидации поучаемых данных, мы вынесем ранее разработанный метод
*.handleExceptions() из нашего контроллера UserController в отдельный класс, чтобы и другие контроллеры могли к нему 
обращаться минуя прежние неудобства (сам метод закомментируем).

Шаг 1. - Создадим папку ['handler'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_18/src/main/java/spring/oldboy/http/handler), а в ней создадим класс контроллер-обработчик ошибок - [ControllerExceptionHandler.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/handler/ControllerExceptionHandler.java).

Шаг 2. - Из класса [UserController.java перенесем метод *.handleExceptions()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/controller/UserController.java#L69) в [ControllerExceptionHandler.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_18/src/main/java/spring/oldboy/http/handler/ControllerExceptionHandler.java#L21).

Шаг 3. - Для того чтобы другие контроллеры смогли использовать новый класс обработчик помечаем его аннотацией 
[@ControllerAdvice](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html) см.:

    @Slf4j
    @ControllerAdvice
    public class ControllerExceptionHandler {
     . . . some method . . .
    }

Шаг 4. - Запускаем приложение для тестирования работы обработчика-валидации.

См. док.:
- [Пакет org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html) ;
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