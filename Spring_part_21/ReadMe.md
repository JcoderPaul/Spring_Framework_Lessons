### Spring Boot lessons part 21 - Security Starter - PART 2

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_21/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html) ;
- [Security with Spring (by www.baeldung.com)](https://www.baeldung.com/security-spring) ;
- [SWAGGER DOC](https://swagger.io/solutions/api-documentation/) (может понадобится прокси);
________________________________________________________________________________________________________________________
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку (подгрузим зависимости в build.gradle):

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

    /* 
        Подключим Spring Boot Starter он включает поддержку 
        авто-конфигурации, логирование и YAML
    */
    implementation 'org.springframework.boot:spring-boot-starter'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'

    /* 
        Автоматически Gradle создал тестовую зависимость на Junit5, мы можем 
        использовать как Junit4, так и TestNG
    */
    test {
        useJUnitPlatform()
    }

    /* Подключим блок для работы с БД (Spring Boot Starter Data Jpa) */
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    /* Для работы с PostgreSQL подключим и его зависимости */
    implementation 'org.postgresql:postgresql'

    implementation 'org.springframework.data:spring-data-envers'

    /* Подключим миграционный фреймворк Liquibase */
    implementation 'org.liquibase:liquibase-core'

    /* Подключаем Wed - Starter */
    implementation 'org.springframework.boot:spring-boot-starter-web'

    /* Подключим Thymeleaf */
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    
    /* Подключим валидацию */
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    
    /* Подключим стартер безопаности */
    implementation 'org.springframework.boot:spring-boot-starter-security'

    implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'

________________________________________________________________________________________________________________________
#### Lesson 107 - Authorization фильтр.

Выше мы рассмотрели несколько фильтров позволяющих аутентифицировать пользователя и дезавуировать его аутентификацию, 
теперь рассмотрим вопрос авторизации ([определение см. выше](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/ReadMe.md#security-starter-%D0%B2%D0%B2%D0%B5%D0%B4%D0%B5%D0%BD%D0%B8%D0%B5-%D0%BE%D0%BF%D1%80%D0%B5%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F-%D0%BF%D0%BE%D0%BD%D1%8F%D1%82%D0%B8%D1%8F-%D1%82%D0%B5%D0%BE%D1%80%D0%B8%D1%8F)). В цепочке фильтров, Authorization фильтр идет последним и
определяет права пользователей приложения на доступ к ресурсам, см. [DOC/AuthenticationProcess.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/AuthenticationProcess.jpg). Т.е. проверяются 
Authorities объекта [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) см. [DOC/SecurityContext.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/SecurityContext.jpg) и [DOC/Authentication](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_20/DOC/Authentication).

Заглянем в класс [AuthorizationFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/access/intercept/AuthorizationFilter.html),
где основной ['фильтрующий' метод - *.doFilter()](https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/access/intercept/AuthorizationFilter.java#L76), проводит проверку авторизации доступа. Основную 'работу' проделывает 
метод [*.check()](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authorization/AuthorizationManager.java#L55) интерфейса [AuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/AuthorizationManager.html), 
т.е. фильтр авторизации, ограничивает доступ к URL-адресу с помощью методов этого интерфейса. Т.е. менеджер авторизации 
определяет, имеет ли [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) 
пользователь доступ к определенному объекту (ресурсу). Да, вот такое 'масло в маргарине'. 

Фактически методу - [AuthorizationDecision check(Supplier<Authentication> authentication, T object)](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authorization/AuthorizationManager.java#L55) - передается два 
объекта: объект аутентификации и объект с которым происходит его сверка, а результатом проверки будет возврат объекта 
класса [AuthorizationDecision](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/AuthorizationDecision.html) 
см. [документацию по Authorization Architecture](https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html),
с [единственным boolean полем granted](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authorization/AuthorizationDecision.java#L25) (доступ разрешен/запрещен).

При работе фильтра авторизации аутентифицированный пользователь может получить/не получить, в зависимости от прав, 
доступ к тем или иным ресурсам, а соответственно может увидеть разные сообщения (401, 403, и т.д.)  

У [AuthorizationManager-а](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/AuthorizationManager.html) 
есть широкий список реализующих классов:
- [AuthenticatedAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/AuthenticatedAuthorizationManager.html) - 
Менеджер авторизации, определяющий, прошел ли текущий пользователь аутентификацию; 

- [AuthoritiesAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/AuthoritiesAuthorizationManager.html) - 
Менеджер авторизации, который определяет, авторизован ли текущий пользователь, путем 
оценки того, содержит ли Authentication какие-либо из указанных полномочий;

- [AuthorityAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/AuthorityAuthorizationManager.html) - 
Менеджер авторизации, который определяет, авторизован ли текущий пользователь, путем 
оценки того, содержит ли Authentication указанные полномочия;
 
- [Jsr250AuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/method/Jsr250AuthorizationManager.html) - 
Менеджер авторизации, который может определить, может ли аутентификация вызывать вызов 
метода, оценивая, содержит ли аутентификация указанные полномочия из аннотаций безопасности JSR-250;
 
- [MessageMatcherDelegatingAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/messaging/access/intercept/MessageMatcherDelegatingAuthorizationManager.html);
 
- [MethodExpressionAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/method/MethodExpressionAuthorizationManager.html) - 
Менеджер авторизации на основе выражения, которое определяет доступ путем 
сравнения предоставленного выражения с вызовом метода;
 
- [ObservationAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/ObservationAuthorizationManager.html) - 
Менеджер авторизации, который наблюдает за авторизацией;
 
- [PostAuthorizeAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/method/PostAuthorizeAuthorizationManager.html) - 
Менеджер авторизации, который может определить, может ли [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) 
вернуть результат вызванного метода у MethodInvocation, оценивая выражение из аннотации [PostAuthorize](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PostAuthorize.html);
 
- [PreAuthorizeAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/method/PreAuthorizeAuthorizationManager.html) - 
Менеджер авторизации, который может определить, может ли [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) 
вызывать y MethodInvocation, оценивая выражение из аннотации [PreAuthorize](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PreAuthorize.html);
 
- [RequestMatcherDelegatingAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/access/intercept/RequestMatcherDelegatingAuthorizationManager.html) - 
Менеджер авторизации, который делегирует полномочия конкретному [AuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/AuthorizationManager.html) на основе оценки [RequestMatcher](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/util/matcher/RequestMatcher.html);

- [SecuredAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/method/SecuredAuthorizationManager.html) - 
Менеджер авторизации, который может определить, может ли аутентификация вызывать вызов метода, оценивая, содержит ли аутентификация указанные полномочия из аннотации [Secured Spring Security](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/annotation/Secured.html);

- [WebExpressionAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/access/expression/WebExpressionAuthorizationManager.html) - 
Менеджер авторизации на основе выражений, который определяет доступ путем оценки предоставленного выражения; 

Теперь попробуем настроить нашу собственную авторизацию:
- Шаг 1. - В классе [SecurityConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/config/SecurityConfiguration.java) в методе [*.filterChain()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/config/SecurityConfiguration.java#L21) изменим структуру *.authorizeHttpRequests(), добавив 
новые методы соответствия ресурсов (путей) и разрешений пользователей;

Очень показателен пример из документации [Spring](https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html):

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {
    
        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests((authz) -> authz
                    .requestMatchers(antMatcher("/user/**")).hasRole("USER")
                    .requestMatchers(antMatcher(HttpMethod.POST, "/user/**")).hasRole("ADMIN")
                    .requestMatchers(regexMatcher(".*\\?x=y")).hasRole("SPECIAL") // matches /any/path?x=y
                    .anyRequest().authenticated()
                );
            return http.build();
        }
    }

!!! Правильная последовательность доступа ресурсов и разрешений важна !!! То, в каком порядке они создаются, в том и 
проверяются в фильтре. Принятая практика такова, что только ограниченное количество страниц (точек доступа) имеют
permitAll, все остальные обычно имеют denyAll, в том числе и вновь созданные !!! 

- Шаг 2. - Удаляем .permitAll() из настройки формы Login - .formLogin(), теперь все разрешения определяются [в блоке 
авторизации](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/config/SecurityConfiguration.java#L28);

Док. для изучения:
- [Authorize HttpServletRequests](https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html);
- [HttpSecurity](https://docs.spring.io/spring-security/reference/servlet/configuration/java.html#jc-httpsecurity); 
- [Authorize HttpServletRequests with AuthorizationFilter](https://docs.spring.io/spring-security/reference/6.0/servlet/authorization/authorize-http-requests.html); 
- [Configuration Migrations](https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html);

Запускаем приложение и проверяем работу фильтра авторизации, получим отличия разрешений у ADMIN и USER.

________________________________________________________________________________________________________________________
#### Lesson 108 - Pre и Post методы Security (теория).

Как мы видели выше, классов, которые реализуют методы менеджера авторизации много, чуть ближе рассмотрим:
- [PostAuthorizeAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/method/PostAuthorizeAuthorizationManager.html) - Менеджер авторизации, который может определить, может ли [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) вернуть результат вызванного метода у MethodInvocation, оценивая выражение из аннотации [PostAuthorize](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PostAuthorize.html);
- [PreAuthorizeAuthorizationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/method/PreAuthorizeAuthorizationManager.html) - Менеджер авторизации, который может определить, может ли [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) вызывать y MethodInvocation, оценивая выражение из аннотации [PreAuthorize](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PreAuthorize.html);

Данные классы позволяют использовать аннотации при дополнительном разграничении прав доступа к ресурсам, а также 
фильтровать получаемую информацию согласно правам доступа переданным в аннотацию. Так же есть специальные фильтры,
которые просеивают результаты запросов (результаты работы методов уровня контроллеров или сервисов) - это [@PreFilter](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-prefilter) и 
[@PostFilter](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-postfilter).

В качестве демонстрации работы этих классов и фильтров немного изменим код в нашем [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/http/controller/UserController.java#L105). Пусть метод [*.findById()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/http/controller/UserController.java#L110)
будет доступен только пользователям с Role = ADMIN:

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String findById(@PathVariable("id") Long id, Model model) {
       . . . method code . . .
    }

Параметр переданный в аннотацию @PreAuthorize является методом утилитного класса [SecurityExpressionRoot](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/expression/SecurityExpressionRoot.html),
который определяет, [SecurityExpressionOperations.getAuthentication()](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/expression/SecurityExpressionOperations.html#getAuthentication()) имеет ли объект определенные полномочия внутри 
[Authentication.getAuthorities()](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html#getAuthorities()).
Данный класс имеет массу удобных методов для работы с Authentication объектами. Особенность аннотации @PreAuthorize в том,
что в нее можно передать SPEL выражение с AND, OR и другой подобной логикой создавая хитрые сочетания значений для 
обеспечения требуемого результат по безопасности. По названию аннотации можно догадаться, что доступ к аннотированному
методу ограничивается еще 'до' - 'PRE' обращения к нему.

Чтобы наши @PreAuthorize и @PostAuthorize аннотации сработали нам необходимо донастроить наш [SecurityConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/config/SecurityConfiguration.java#L17), 
добавив аннотацию [@EnableMethodSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/method/configuration/EnableMethodSecurity.html):

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class SecurityConfiguration {
       . . . class code . . .
    }

Проверим работу аннотации, запустим приложение и зайдем сначала под любым из ADMIN-ов, а затем под любым USER-ом, на 
страницу извлекающую запись из таблицы users БД, например - [http://localhost:8080/users/5](http://localhost:8080/users/5). Мы либо увидим форму EDIT,
либо страницу Error (в зависимости от ROLE, как и настроили аннотацию @PreAuthorize метода *.findById()).

Данную страницу Error возвращает наш обработчик исключений [ControllerExceptionHandler](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/http/handler/ControllerExceptionHandler.java), мы можем его отключить и увидим 
403 ответ, в случае отсутствия доступа к требуемой странице.

Именно [@EnableMethodSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/method/configuration/EnableMethodSecurity.html),
включает [метод безопасности Spring Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html) и, в частности, определяет, следует ли включить аннотации:
- [@PreAuthorize](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PreAuthorize.html);
- [@PostAuthorize](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PostAuthorize.html); 
- [@PreFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PreFilter.html); 
- [@PostFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PostFilter.html);
- и конечно и другие аннотации см. док.;

Так же аннотация [@EnableMethodSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/method/configuration/EnableMethodSecurity.html)
над нашей конфигурацией безопасности позволяет аннотировать не только уровень контроллеров, но и уровень сервисов, что 
делает настройки безопасности еще жестче, поскольку, дополнительная проверка прав доступа уже не зависит от конкретного
класса контроллера, будь это [REST](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_21/src/main/java/spring/oldboy/http/rest) или обычный [Controller](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_21/src/main/java/spring/oldboy/http/controller).

Перенесем аннотацию на уровень сервисов: 

    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<UserReadDto> findById(Long id) {
        . . . method code . . .
    }

Аннотации [@PreFilter](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-prefilter) и [@PostFilter](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-postfilter) обычно ставятся над 'набором данных' - коллекции, потоки и т.д., чтобы ограничить
(отфильтровать) итоговый 'набор' по заданным в фильтре параметрам. Например, так:

    @PostFilter("filterObject.role.name().equals('ADMIN')")
    @PostFilter("@companyService.findById(filterObject.company.id()).isPresent()")
    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
     . . . method code . . . 
    }

Однако, хорошей практикой, считается фильтрация по правам доступа зашитая в JAVA код метода или в SQL запрос, это позволяет 
экономить ресурсы и увеличивает быстродействие системы.

Рекомендовано к изучению (ENG):
- [Spring Method Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html); 
- [Introduction to Spring Method Security](https://www.baeldung.com/spring-security-method-security);
- [Spring Security – @PreFilter and @PostFilter](https://www.baeldung.com/spring-security-prefilter-postfilter);
- [Annotation Interface PreFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PreFilter.html);
- [Annotation Interface PostFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/prepost/PostFilter.html);
- [Expression-Based Access Control](https://docs.spring.io/spring-security/reference/5.6-SNAPSHOT/servlet/authorization/expression-based.html);
- [Annotation Interface EnableMethodSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/method/configuration/EnableMethodSecurity.html);
- [Expression-Based Access Control](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html);

________________________________________________________________________________________________________________________
#### Lesson 109 - Доступ к аутентифицированному пользователю.

В одном из уроков, точнее в [Lesson 58 - "Spring JPA Auditing - Аудит работы нашего приложения"](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_10#lesson-58---spring-jpa-auditing---%D0%B0%D1%83%D0%B4%D0%B8%D1%82-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B-%D0%BD%D0%B0%D1%88%D0%B5%D0%B3%D0%BE-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F), мы изучали принципы 
аудита. Исходя из которых мы уяснили важность фиксации любых изменений происходящих в БД, а самое главное, подробную 
запись того: кто, когда, создал, изменил и т.п. соответствующую запись в таблицах БД. В первой версии конфигурации 
нашей системы аудита - [AuditConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/config/AuditConfiguration.java), мы 'хардкодом' назначили себя 'виновником всех бед'. Поэтому в поле 
'created_by' таблицы 'users' у всех записей одинаковое значение, прописанное в старой версии метода [*.auditorAware()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_10/src/main/java/spring/oldboy/config/AuditConfiguration.java#L34):

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("spring/oldboy");
    }

Именно этот метод мы будем переделывать, чтобы в БД фиксировать того пользователя, который в действительности внес 
изменения. Это стало возможно именно по тому что мы подключили систему аутентификации. И так делаем:
- Шаг 1. - В классе [AuditConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/config/AuditConfiguration.java) в методе [*.auditorAware()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/config/AuditConfiguration.java#L36): 
  - Шаг 1.1 - Получаем доступ к хранителю контекста безопасности - [SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html)
  - Шаг 1.2 - Из которого извлекаем [SecurityContext](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContext.html), 
  - Шаг 1.3 - Уже из SecurityContext получаем объект [аутентификации](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) 
  см. ссылки на документацию и комментарии в методе класса. 

Объект аутентификации может быть null и вот тут нам и помогает Optional.
 
- Шаг 2. - Из объекта [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) получаем [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html) методом [*.getPrincipal()](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html#getPrincipal()), т.е. личность аутентифицируемого принципала.
  - Шаг 2.1 - Полученный объект приводим к [UserDetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html), 
хотя мы могли бы привести полученный Object к любому классу представляющему нашего user-a;
  - Шаг 2.2 - Из UserDetails получаем 'username' аутентифицированного пользователя;

- Шаг 3. - Полученное имя пользователя - 'username' будет сохранено в поле 'created_by' таблицы 'users' при следующем 
создании/изменении данных в нашей БД.

Кроме такого способа получения контекста безопасности мы можем использовать аннотации в параметрах метода, например:
- [@CurrentSecurityContext](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/annotation/CurrentSecurityContext.html) - Аннотация, которая используется для разрешения SecurityContext в качестве аргумента метода;
- [@AuthenticationPrincipal](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/annotation/AuthenticationPrincipal.html) - Аннотация, которая используется для преобразования Authentication.getPrincipal() в аргумент метода. 
Данная аннотация, скорее всего, будет использоваться чаще в силу удобства получения данных о принципале;

Добавим обе аннотации в метод [*.findById()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/http/controller/UserController.java#L110) нашего [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/http/controller/UserController.java), в качестве параметров, и в режиме пошаговой отладки 
посмотрим на доступные переменные и объекты. 

Войдем в наше приложение как rocky@yahoo.com (в БД такая запись естественно есть). Добравшись до метода *.findById() мы
видим, что установлен и securityContext и userDetails см. [DOC/AnnotationAccessSecurityContext/UserControllerAnotationSecurityParam.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/AnnotationAccessSecurityContext/UserControllerAnotationSecurityParam.jpg),
а если их развернуть, то мы видим один и тот же объект аутентификации в разном представлении см. [DOC/AnnotationAccessSecurityContext/UserDetailsAndSecurityContext.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/AnnotationAccessSecurityContext/UserDetailsAndSecurityContext.jpg).
Естественно в обоих случаях поле password = null, т.к. пароль после аутентификации чиститься.

Последний шаг - проверка нашего аудита действий. Под тем же логином администратора редактируем любого пользователя в 
таблице users и смотрим на результат. В поле 'modified_by' попал 'username' - rocky@yahoo.com, в поле 'modified_at' 
попала дата и время модификации.

________________________________________________________________________________________________________________________
#### Lesson 110 - Доступ к аутентифицированному пользователю на HTML страницах с применением Thymeleaf.

Мы так же можем получить доступ к данным нашего пользователя из контекста и внедрить их (отобразить) на наших HTML
страницах. Spring при помощи Thymeleaf позволяет проделать это. Для этого нам понадобится внедрить новую зависимость:

    implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.2.RELEASE'

Теперь, на странице с внедренным Thymeleaf, мы получили доступ к объектам аутентификации и авторизации и можем 
отобразить аутентифицированного пользователя рядом с кнопкой Logout - [resources/templates/fragments/logout.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/resources/templates/fragments/logout.html):

    <span th:text="${#authentication.principal.username}">Username</span>

По факту, поскольку у нас есть доступ к объекту Authentication, мы можем получить все данные из нашего UserDTO.

Проверяем как username отображается на всех страницах вместе с кнопкой Logout.

________________________________________________________________________________________________________________________
#### Lesson 111 - CSRF-Filter (CSRF атаки).

Сборник статей по CSRF - [DOC/CSRF](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_21/DOC/CSRF) раскрывает вопрос безопасности передачи HTTP запросов и их уязвимость см. 
[DOC/CSRF/Image/CSRF_Attack_Scheme.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/CSRF/Image/CSRF_Attack_Scheme.jpg). Простая атака выглядит именно так:
1. - Добрый человек проходит этап аутентификации на сайте (в примере банка) хранящем важные данные, возвращается ответ
200 и данные попадают в cookie отслеживающие сессию;
2. - Добрый человек отправляет форму на сайт банка методом POST, при этом данные о текущей сессии и самом пользователе
системы, которые хранятся в cookie и идентифицируют его (на сайте банка) уходят на сервер;
3. - Плохой человек (очень нуждающийся в деньгах) создает фишинговый сайт и размещает на нем некую кнопку (картинку, 
скрипт) со ссылкой на сайт банка, но уже со своими данными и уваровывает у доброго человека деньги (важные данные), 
поскольку cookie привязаны к host-у, то они при переходе по скрытой ссылке подхватываются автоматически и происходит 
транзакция;

Кратко данный процесс описан тут (и методы защиты):
- [DOC/CSRF/ABOUT_CSRF.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/CSRF/ABOUT_CSRF.txt) ;
- [DOC/CSRF/CSRF_FROM_SPRING_DOC.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/CSRF/CSRF_FROM_SPRING_DOC.txt) ;
- [DOC/CSRF/CSRFGuideProtection.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/CSRF/CSRFGuideProtection.txt) ;

Spring предоставляет два механизма защиты от атак CSRF:
- Шаблон токена синхронизатора (Synchronizer Token Pattern);
- Указание атрибута SameSite в файле cookie сеанса;

В качестве защиты от CSRF атак мы будем использовать в нашем уроке настройки Synchronizer Token Pattern-a схема см.
[DOC/CSRF/Image/SynchronizeTokenPattern.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/CSRF/Image/SynchronizeTokenPattern.jpg). Все важные формы изменяющие состояние записей в БД защищаются скрытым 
полем с name = "_csrf" и случайным токен-кодом, который генерируется и может храниться на сервере. Токен присваивается 
при аутентификации, он случаен и имеет срок жизни, сервер при получении существенных запросов POST, DELETE и т.д., 
сверяет токены полученный с хранящимся у него и дает добро на выполнение команды или нет. Злой хакер в своей подставной 
форме не может проделать (хотелось бы верить) ту же операцию, т.к., например, сгенерированный (подставленный) его 
формой токен будет отличаться от хранящейся на сервере (или его вовсе не будет), и его форма вылетает с 403 статусом.

Настроим наше приложение, by default защита CSRF в Spring включена, но мы ее отключили в цепочке фильтров класса 
SecurityConfiguration:

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
        . . . some code . . .
    }            

Меняем разрешение на - enable, т.е. просто комментируем запрет на CSRF защиту. Отсюда выходит, что теперь в нашей 
цепочке фильтров появится [CsrfFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/csrf/CsrfFilter.html),
если заглянуть в код метода [*.doFilterInternal()](https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/csrf/CsrfFilter.java#L107) данного класса, то можно увидеть как генерируется csrf токен. Изучая
класс [HttpSessionCsrfTokenRepository](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/csrf/HttpSessionCsrfTokenRepository.html) 
мы можем увидеть [как и куда сохраняется токен](https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/csrf/HttpSessionCsrfTokenRepository.java#L50), а так же структуру полей:

    public final class HttpSessionCsrfTokenRepository implements CsrfTokenRepository {

        private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    
        private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    
        @Override
        public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
            if (token == null) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute(this.sessionAttributeName);
                }
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute(this.sessionAttributeName, token);
            }
        }

    . . . class code . . .

    }

Токен попадает в атрибут сессии, а дефолтные имена которые будут отслеживаться для формы - "_csrf", и для хэдера -
"X-CSRF-TOKEN". Добавить токен в нашу форму можно двумя способами: руками (хардкод) или используя интеграцию с Thymeleaf.
Вариант ручного добавления в форму Login:

      <!DOCTYPE html>
      <html lang="en" xmlns:th="http://www.thymeleaf.org">
      <head>
          <meta charset="UTF-8">
          <title>Login</title>
      </head>
      <body>
      <form action="/login" method="post">

          <!-- Поле _csrf должно быть скрыто -->     

          <input type="hidden"
                 th:name="${_csrf.parameterName}"
                 th:value="${_csrf.token}">
      
          <div style="color: red" th:if="${param.error}">
              Bad credentials
          </div>
            . . .   
      </form>
      </body>
      </html>
      
Однако руками добавлять код на все страницы (с методом POST) - накладно. Поэтому тут приходит на помощь Thymeleaf 
интегрированный со Spring-ом. Класс [CsrfRequestDataValueProcessor](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/servlet/support/csrf/CsrfRequestDataValueProcessor.html),
Spring-а, добавляет к POST формам скрытое поле методом [*.getExtraHiddenFields()](https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/servlet/support/csrf/CsrfRequestDataValueProcessor.java#L63). Поскольку он у нас установлен и 
настроен, достаточно в форме использовать нотацию 'th:' и синтаксис Thymeleaf-а:

      <form th:action="@{/login}" method="post">

Данный синтаксис позволяет автоматически внедрить скрытый токен '_csrf' в форму, отправляемую методом POST. Теперь нам 
нужно только переписать все формы где method="post" по данному шаблону и проверить работу приложения. Запускаем и видим
в режиме разработчика браузера см. [DOC/CSRF/Image/HiddenCsrfToken.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/CSRF/Image/HiddenCsrfToken.jpg), а так же два токена на одной странице для двух 
POST форм см. [DOC/CSRF/Image/2HiddenCsrfTokenInOnePage.jpg ](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/CSRF/Image/2HiddenCsrfTokenInOnePage.jpg)

________________________________________________________________________________________________________________________
#### Lesson 112 - Security-Testing.

Если мы просто запустим наши [UserControllerIT](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/test/java/spring/oldboy/integration/http/controller/UserControllerIT.java) тесты, то сразу поймаем ошибку, т.к. в [UserServiceIT](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/test/java/spring/oldboy/integration/service/UserServiceIT.java) мы не исправили 
структуру U[serCreateEditDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/main/java/spring/oldboy/dto/UserCreateEditDto.java) - нам нужно добавить туда пароль, иначе все методы где применяется UserCreateEditDto будут 
сигнализировать об ошибках. Нам нужно отредактировать код тестов в соответствии с обновленным кодом приложения. Запустим 
тест еще раз и получим:

      MockHttpServletRequest:
            HTTP Method = GET
            Request URI = /users
            Parameters = {}
            Headers = []
            Body = null
            Session Attrs = {SPRING_SECURITY_SAVED_REQUEST=DefaultSavedRequest [http://localhost/users?continue]}
      
      . . .
      
      MockHttpServletResponse:
                  Status = 302
                  Error message = null
                  Headers = [Vary:"Origin", "Access-Control-Request-Method", 
                                            "Access-Control-Request-Headers", 
                             X-Content-Type-Options:"nosniff", 
                             X-XSS-Protection:"0", 
                             Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", 
                             Pragma:"no-cache", 
                             Expires:"0", 
                             X-Frame-Options:"DENY", 
                             Location:"http://localhost/login"]
                       Content type = null
                  Body =
                  Forwarded URL = null
                  Redirected URL = http://localhost/login
                  Cookies = []

Тест завалился, т.к. ожидали - Expected :SUCCESSFUL, а получили - Actual :REDIRECTION. И тут не подкопаешься, если 
пользователь у нас в системе не аутентифицирован, то он перенаправляется на страницу Login-a. Для того чтобы тестировать
наши методы, нам нужно аутентифицировать тестового пользователя. Для реализации такого функционала нам понадобится 
зависимость позволяющая тестировать приложение с подключенной системой безопасности:

      testImplementation 'org.springframework.security:spring-security-test'

После подключения этой зависимости у нас есть возможность подставить в контекст безопасности тестового пользователя, 
рассмотрим несколько способов:

- Способ 1. - Ручная установка SecurityContext:


      @AutoConfigureMockMvc
      @RequiredArgsConstructor
      class UserControllerIT extends IntegrationTestBase {

          private final MockMvc mockMvc;

      /* Перед каждым тестовым методом мы будем инициировать тестового пользователя */
      @BeforeEach
      void initTestUser() {
         /* Шаг 1 - Создаем нашего тестового User-a и коллекцию его Role-ей */
         List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
         User testUser = new User("test@gmail.com",
                                  "test",
                                  roles);
         /*
         Шаг 3. - Создаем тестовый аутентификационный токен, в его
         параметры загружаем нашего тестового пользователя,
         именно его будет отслеживать тестовый фильтр.
         */
         TestingAuthenticationToken authenticationToken =
                 new TestingAuthenticationToken(testUser,
                                                testUser.getPassword(),
                                                roles);
  
         /* 
         Шаг 2. - Создаем пустой контекст безопасности используя 
         SecurityContextHolder см. DOC/SecurityContext.jpg
         */
         SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
         /* Шаг 4. - Загружаем в контекст наш тестовый токен */
         securityContext.setAuthentication(authenticationToken);
         /* Шаг 5. - Загружаем получившийся контекст в хранителя контекста безопасности */
         SecurityContextHolder.setContext(securityContext);
      }
      . . . test methods . . .
    }

- Шаг 1. - Создаем тестового User-a;
- Шаг 2. - Создаем [SecurityContext](https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/core/context/SecurityContext.html) используя [SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/core/context/SecurityContextHolder.html);
- Шаг 3. - Создаем [TestingAuthenticationToken](https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/authentication/TestingAuthenticationToken.html) 
и передаем в него через конструктор тестового User-a (тоже заранее созданного);
- Шаг 4. - В SecurityContext помещаем наш TestingAuthenticationToken;
- Шаг 5. - Через SecurityContextHolder связываем новый поток SecurityContext с текущим потоком выполнения;

Теперь мы получили тестового пользователя в контексте безопасности и можем проводить тесты см. 
[UserControllerSecurityTestIT](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/src/test/java/spring/oldboy/integration/http/controller/UserControllerSecurityTest.java).

- Способ 2. - Аннотации: в данном случае мы используем специальные аннотации для установки 'тестового пользователя', 
например - [@WithMockUser](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/test/context/support/WithMockUser.html). В параметры аннотации мы передаем тестовые данные и запускаем тест:

        @Test
        @WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
        void findAllControllerTest() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(view().name("user/users"))
                    .andExpect(model().attributeExists("users"));
        }

Естественно использовать эту аннотацию над каждым тестом накладно и лучше вынести ее сразу над нашим тестовым классом 
или, что еще более интересно аннотировать наш абстрактный класс - IntegrationTestBase, и тогда все наши интеграционные 
тесты (и текущие, и будущие) окажутся с предустановленным тестовым пользователем:

        @IT
        @Sql({
        "classpath:sql_scripts/data.sql"
        })
        @WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
        public abstract class IntegrationTestBase {
          . . . somу code . . .
        }

- Способ 3. - Передача параметров в тестовый метод, тут используется класс [SecurityMockMvcRequestPostProcessors](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/test/web/servlet/request/SecurityMockMvcRequestPostProcessors.html):

        @Test
        void findAllControllerWithInnerTestUser() throws Exception {
            mockMvc.perform(get("/users").with(user("test@gmail.com")
                                                   .authorities(Role.ADMIN)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(view().name("user/users"))
                    .andExpect(model().attributeExists("users"));
        }


Док. для изучения (ENG): 
- [Testing - Servlet Applications](https://docs.spring.io/spring-security/site/docs/5.2.x/reference/html/test.html);
- [Class SecurityMockMvcRequestPostProcessors](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/test/web/servlet/request/SecurityMockMvcRequestPostProcessors.html);
- [Annotation Interface WithMockUser](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/test/context/support/WithMockUser.html);

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