### Spring Boot lessons part 20 - Security Starter - PART 1

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_20/DOC) и др. полезные файлы.

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
Для начала проведем предварительную подготовку (подгрузим зависимости в [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/build.gradle)):

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

    implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'

________________________________________________________________________________________________________________________
#### Security-Starter. Введение, определения, понятия (теория).

**Аутентификация** - (англ. authentication, греч. "реальный, подлинный") - процедура проверки подлинности, например:
- проверка подлинности пользователя, путём сравнения введённого им пароля (для указанного логина) с паролем, сохранённым 
в базе данных пользовательских логинов;
- подтверждение подлинности электронного письма, путём проверки цифровой подписи письма по открытому ключу отправителя;
- проверка контрольной суммы файла на соответствие сумме, заявленной автором этого файла.

В русском языке термин применяется, в основном, в области информационных технологий. Фактически, это документарный или 
бездокументарный процесс подтверждения личности, по запросу. Например, при обращении к некоему ресурсу, если требуется 
подтверждение, что "мы" это действительно "мы", мы предоставляем некий "гарант подлинности": пароль, контрольную сумму, 
паспорт, смс-код и т.п. 

Учитывая степень доверия и политику безопасности систем, проводимая проверка подлинности может быть односторонней или 
взаимной. Обычно она проводится с помощью криптографических способов.

Аутентификацию не следует путать с авторизацией (процедурой предоставления субъекту определённых прав) и идентификацией
(процедурой распознавания субъекта по его идентификатору).

См. [https://ru.wikipedia.org/wiki/Аутентификация](https://ru.wikipedia.org/wiki/%D0%90%D1%83%D1%82%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%86%D0%B8%D1%8F)

**Авторизация** - (англ. authorization «разрешение; уполномочивание») - предоставление определённому лицу или группе лиц 
прав (разрешений) на выполнение определённых действий; а также процесс проверки (подтверждения) данных прав при попытке 
выполнения этих действий. В том числе путем передачи таких прав другому лицу.

Часто можно услышать выражение, что какой-то человек «авторизован» для выполнения данной операции — это значит, что он 
имеет право на выполнение таковой. Авторизацию не следует путать с аутентификацией — процедурой проверки легальности 
пользователя или данных, например, проверки соответствия введённого пользователем пароля к учётной записи паролю в базе 
данных, или проверка цифровой подписи письма по ключу шифрования, или проверка контрольной суммы файла на соответствие 
заявленной автором этого файла. 

Авторизация производит контроль доступа к различным ресурсам системы в процессе работы легальных пользователей после 
успешного прохождения ими аутентификации.

См. [https://ru.wikipedia.org/wiki/Авторизация](https://ru.wikipedia.org/wiki/%D0%90%D0%B2%D1%82%D0%BE%D1%80%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F)

Другими словами, аутентификация - подтверждает / опровергает нашу подлинность в некой системе, а авторизация - 
разрешает / запрещает нам что-либо делать внутри этой системы с ее ресурсами. В пределах абстрактного сервиса-приложения
аутентификация определяет конкретного пользователя (обычно зарегистрированного), а авторизация определяет, имеет ли 
аутентифицированный пользователь права на получение доступа к ресурсам приложения (для web-сервиса, доступ к конкретному
endpoint-у или набору endpoint-ов сервиса), манипуляции с ресурсами и т.п.

В курсе по [HTTP_Servlets_Java_EE](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/tree/master), мы рассмотрели каким образом происходит аутентификация и авторизация пользователей 
приложения см. [MVCPracticeAdvanced](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/tree/master/MVCPracticeAdvanced) и [ServletFilter.jpg](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPracticeAdvanced/DOC/ServletFilter.jpg) 
В данном процессе нам помогают фильтры, которые 'докручивают' запрос до момента пока он не попал в DispatcherServlet, а 
могут и вовсе не позволить запросу до него добраться сразу вернув ответ. Каждый фильтр имеет свой жизненный цикл, а 
набор фильтров составляют - цепочку фильтров (filter chain). Однако, вся эта технологическая цепочка, со всеми жизненными
циклами находится внутри сервлет контейнера сервера (например TomCat). А мы помним, что у сущностей Spring-а свои 
жизненные циклы и нам необходимо, чтобы жизненные циклы фильтров совпадали с жизненными циклами сущностей Spring-a.
Т.е. нам нужно, чтобы фильтры стали bean-ами Spring, чтобы было просто ими манипулировать. 

Схематично реализация этой задачи выглядит следующим образом см. [DOC/SpringFilterProxyForAuth.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/SpringFilterProxyForAuth.jpg) или см. док. [Spring Security Architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html). В стандартной цепочке 
фильтров появляется фильтр [DelegatingFilterProxy](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/DelegatingFilterProxy.html), который содержит цепочку фильтров от Spring-a (фактически его bean) 
[FilterChainProxy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/FilterChainProxy.html). Данная цепочка фильтров (самых обычных фильтров), теперь уже подчиняется жизненному циклу Spring-a, и 
самое главное упорядочена определенным образом. Тут, как и в случае сервлет фильтров, фильтры в [SecurityFilterChain](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/SecurityFilterChain.html) 
вызываются в заранее заданной последовательности (т.е. например, без обязательной аутентификации не может запуститься 
процесс авторизации - нельзя субъекту дать права, если не знаешь, что это за субъект).

Теперь у нас есть [DelegatingFilterProxy](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/DelegatingFilterProxy.html), который подчиняется жизненным циклам фильтров Servlet контейнера сервера. В 
свою очередь он содержит в себе Spring-овый bean - [FilterChainProxy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/FilterChainProxy.html), который уже подчиняется жизненным циклам сущностей
Spring-a. Именно он - FilterChainProxy - содержит цепочку вызовов фильтров отвечающих за безопасность в Spring приложении.
Т.е. тут все необходимые Security фильтры Spring-a, как бы вынесены за пределы Servlet контейнера и его жизненного цикла.

См. док.:
- [Spring Security/Servlet Applications/Architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html) ;
- [Пакет org.springframework.web.filter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/package-summary.html) ;
- [Пакет org.springframework.security.web](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/package-summary.html) ;

________________________________________________________________________________________________________________________
#### Security-Starter. [Подключение зависимости](https://docs.spring.io/spring-security/reference/getting-spring-security.html) (теория).

Для подключения системы безопасности Spring-a нам нужна зависимость:

    implementation 'org.springframework.boot:spring-boot-starter-security'

В разделе зависимостей мы видим полученные связи см. [DOC/SpringSecurityDependencies.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/SpringSecurityDependencies.jpg). Поскольку мы реализуем 
web-приложение, то нам как раз и нужен пакет - [org.springframework.security.web](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/package-summary.html). Именно тут реализуется методика 
безопасности с применением фильтров (фильтры не единственная технология позволяющая организовать Security процесс).  

При подключении Security-Starter в работу по конфигурированию безопасности приложения включается  
[SecurityFilterAutoConfiguration](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/security/servlet/SecurityFilterAutoConfiguration.html) см. [код на GitHub](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/security/servlet/SecurityFilterAutoConfiguration.java):

    @AutoConfiguration(after = SecurityAutoConfiguration.class)
    @ConditionalOnWebApplication(type = Type.SERVLET)
    @EnableConfigurationProperties(SecurityProperties.class)
    @ConditionalOnClass({ AbstractSecurityWebApplicationInitializer.class, SessionCreationPolicy.class })
    public class SecurityFilterAutoConfiguration {
        ...
    }

Тут мы видим общие свойства безопасности [SecurityProperties.class](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/security/SecurityProperties.html), как параметр аннотации [@EnableConfigurationProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/EnableConfigurationProperties.html):

    @ConfigurationProperties(prefix = "spring.security")
    public class SecurityProperties {
        . . . 	
	private final Filter filter = new Filter();
        . . .
	private final User user = new User();
        . . .
    public static class Filter {
        . . . 
		private int order = DEFAULT_FILTER_ORDER;
		private Set<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
        . . .
        }
    public static class User {
        . . .
		private String name = "user";
		private String password = UUID.randomUUID().toString();
        . . .
        }
    }

Используя данный класс мы можем настраивать User-a, его пароль, так же фильтры, их порядок и ситуации при которых они 
будут срабатывать. Конечно [SecurityFilterAutoConfiguratio](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/security/servlet/SecurityFilterAutoConfiguration.html)n содержит метод - securityFilterChainRegistration, по созданию
цепочки фильтров безопасности см. [DOC/SpringFilterProxyForAuth.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/SpringFilterProxyForAuth.jpg):

    public class DelegatingFilterProxyRegistrationBean 
                                extends AbstractFilterRegistrationBean<DelegatingFilterProxy>
                                        implements ApplicationContextAware {

        . . . 

	    @Override
	    public DelegatingFilterProxy getFilter() {
		    return new DelegatingFilterProxy(this.targetBeanName, getWebApplicationContext()) {

			    @Override
			    protected void initFilterBean() throws ServletException {
		    		// Don't initialize filter bean on init()
	    		}
    
		    };
	    }
        . . .

    }

Именно этот класс, см. выше, содержит метод создающий фильтр живущий в жизненном цикле Spring-а. В данном случае это 
авто-конфигурация для фильтра безопасности - security filter. Но у нас есть так же класс авто-конфигурации, который  
должен быть загружен в первую очередь - [@AutoConfiguration](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/AutoConfiguration.html)(after = SecurityAutoConfiguration.class):  

    @AutoConfiguration(before = UserDetailsServiceAutoConfiguration.class)
    @ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
    @EnableConfigurationProperties(SecurityProperties.class)
    @Import({ SpringBootWebSecurityConfiguration.class, SecurityDataConfiguration.class })
    public class SecurityAutoConfiguration {
        . . .
    }

В данный класс импортируется важная для нашего web-приложения конфигурация, которая и создает цепочку фильтров 
безопасности [SpringBootWebSecurityConfiguration](https://docs.spring.io/spring-boot/docs/2.0.6.RELEASE/api/org/springframework/boot/autoconfigure/security/servlet/SpringBootWebSecurityConfiguration.html) см. [код на GitHub](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/security/servlet/SpringBootWebSecurityConfiguration.java):

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = Type.SERVLET)
    class SpringBootWebSecurityConfiguration {
    
        @Configuration(proxyBeanMethods = false)
        @ConditionalOnDefaultWebSecurity
        static class SecurityFilterChainConfiguration {
    
            @Bean
            @Order(SecurityProperties.BASIC_AUTH_ORDER)
            SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
                http.formLogin(withDefaults());
                http.httpBasic(withDefaults());
                return http.build();
            }
    
        }
        . . .
    }

Наша задача в дальнейшем будет связана с настройкой конфигурации цепочки фильтров безопасности - [SecurityFilterChain](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/SecurityFilterChain.html),
т.е. настройка разрешений на доступ к ресурсам, проверка ролей пользователей и т.д. 

Ниже мы рассмотрим настройку каждого фильтра безопасности в цепочке (Security Filter Chain) по-отдельности.

________________________________________________________________________________________________________________________
#### Security-Starter. Authentication фильтр (теория).

См. док. : [Servlet Authentication Architecture](https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html) ;

Пользователь, который прошел аутентификацию (в сервисе с использованием средств Spring Security) будет упакован в объект
[Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html), тот в свою очередь хранит три значения: 
- Principal (см. [DOC/Authentication/AuthenticatedPrincipal.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/AuthenticatedPrincipal.txt) и [DOC/Authentication/UserDetails.txt)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/UserDetails.txt), ну или наш UserDTO; 
- Credentials (см. [DOC/Authentication/CredentialsContainer.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/CredentialsContainer.txt));
- GrantedAuthority-ы (см. [DOC/Authentication/GrantedAuthority.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/GrantedAuthority.txt)) или роли, которых может быть много и они должны 
реализовывать интерфейс [GrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/GrantedAuthority.html), в нашем приложении это Enum;

Весь объект [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) обернут в [SecurityContext](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContext.html) см. [DOC/SecurityContext/SecurityContextInterface.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/SecurityContext/SecurityContextInterface.txt), фактически 
этот объект хранит всю информацию о нашем авторизированном пользователе. Для того чтобы получить доступ к данным в 
SecurityContext-е мы будем использовать класс [SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html), см. [Spring_part_21/DOC/SecurityContext/SecurityContextHolder.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_21/DOC/SecurityContext/SecurityContextHolder.txt).
Данный класс позволяет определять различные подходы по хранению контекста, но по умолчанию применяется - MODE_THREADLOCAL.

Каждый раз когда нам будет необходимо получить SecurityContext (данные текущего пользователя) в HTTP Request-e, мы будем 
обращаться к [SecurityContextHolder-у](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html). Как только приходит запрос после удачной аутентификации пользователя мы помещаем 
данные о нем в ThreadLocal переменную, и как только мы создадим ответ (response) и отправим пользователю приложения, мы
должны будем почистить эту переменную в массиве ThreadLocal<SecurityContext> context-ы.

Схематично фильтр аутентификации работает следующим образом см. [DOC/FilterFunctionality.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/FilterFunctionality.jpg):
- Шаг 1. - создается объект аутентификации см. [DOC/SecurityContext.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/SecurityContext.jpg), того объекта, который хранит данные значения: 
Principal, Credentials, Authority. См. [DOC/Authentication/AuthenticationInterface.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/AuthenticationInterface.txt);
- Шаг 2. - получаем менеджер аутентификации см. [DOC/Authentication/AuthenticationManager.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/AuthenticationManager.txt);
- Шаг 3. - передаем объект аутентификации в метод [AuthenticationManager.authenticate(Authentication)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html#authenticate(org.springframework.security.core.Authentication));
- Шаг 4. - менеджер аутентификации в цикле перебирает все доступные провайдеры аутентификации см.
[DOC/Authentication/AuthenticationProvider.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/AuthenticationProvider.txt), пока не будет подобран подходящий, если в приложении их настроено 
несколько;

См.док.:
- [Constant Field Values (of org.springframework.*)](https://docs.spring.io/spring-security/site/docs/current/api/constant-values.html) ;
- [Пакет org.springframework.security.core](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/package-summary.html) ;
- [Пакет org.springframework.security.authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/package-summary.html) ;

________________________________________________________________________________________________________________________
#### Security-Starter. Запуск приложения и изучение Authentication фильтров по умолчанию (теория).

И так мы установили систему аутентификации, пока по-умолчанию, но уже понятно, что любой запрос к нашему приложению и 
переход от endpoint-a, к endpoint-у, точно потребует предварительно аутентифицироваться. Запустим наше приложение в 
режиме отладки и посмотрим что получим.  
- При запуске приложения мы получили сгенерированный пароль для дефолтного пользователя "user" (например):

        Using generated security password: 212379aa-f9d5-4bc5-b428-72707ef4c523

- Пробуем загрузить список записей из таблицы users нашей БД - [http://localhost:8080/users](http://localhost:8080/users) и получаем доступ к списку 
фильтров в цепочке загруженных фильтров аутентификации см. [DOC/DefaultAppFilterChain.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/DefaultAppFilterChain.jpg). Так же мы видим наш 
единственный DispatcherServlet, который будет обрабатывать запросы. 
- Наконец мы видим DelegatingFilterProxy см. [DOC/SpringFilterProxyForAuth.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/SpringFilterProxyForAuth.jpg), в котором находится цепочка фильтров 
безопасности от Spring-a см. [DOC/springSecurityFilterChain.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/springSecurityFilterChain.jpg), названия оных говорят сами за себя. В списке приведенных 
фильтров мы можем заметить, например, те из них, что генерируют страницы LogIn и LogOut по умолчанию.
- Продолжим шаг за шагом двигаться по фильтрам и видим, что запрос - [http://localhost:8080/users](http://localhost:8080/users) - не прошел, как это 
было без использования системы безопасности. Произошла переадресация на страницу аутентификации по-умолчанию см. 
[DOC/AppSecurityDefaultPage/AppSecurityDefaultLogInPage.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/AppSecurityDefaultPage/AppSecurityDefaultLogInPage.jpg).
- И так, у нас есть логин по-умолчанию "user" и сгенерированный пароль. Вводим их в форму аутентификации и вуаля.
- Все мы аутентифицированы и попали на нужную нам страницу. Мы даже можем прыгать по страницам приложения, как и раньше.

Поскольку мы имеем и страницу LogOut, мы можем ей воспользоваться - [http://localhost:8080/logout](http://localhost:8080/logout) и получаем см. 
[DOC/AppSecurityDefaultPage/AppSecurityDefaultLogOutPage.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/AppSecurityDefaultPage/AppSecurityDefaultLogOutPage.jpg). Подтверждаем действие и снова теряем доступ к приложению см.
[DOC/AppSecurityDefaultPage/AppSecurityDefaultSignedOutPage.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/AppSecurityDefaultPage/AppSecurityDefaultSignedOutPage.jpg).

Попробуем умышленно ввести неверные данные в поля данных аутентификации, любые и получим ответ о неверных данных 
пользователя или "Bad credentials".

Тут мы можем увидеть, что текущая сессия аутентификации пользователя приложения привязана к cookies и самое главное к
JSESSIONID и если мы ее умышленно удалим после аутентификации из браузера, то снова потеряем доступ к нашему приложению,
т.е. придется заново проходить аутентификацию, хотя мы и не делали Logout (зато почистили руками состояние cookies).

Все эти cookies отправляется на наш сервер приложения и там в виде MAP хранится список аутентифицированных пользователей.

________________________________________________________________________________________________________________________
#### Lesson 102 - [Dao Authentication Provider](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html).

Посмотрим на внутреннюю структуру [DaoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html) см. [код на GitHub](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authentication/dao/DaoAuthenticationProvider.java):

    public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

        private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    
        /* Наши пароли будут закодированы*/
        private PasswordEncoder passwordEncoder;
    
        private volatile String userNotFoundEncodedPassword;
    
        /* Детализированные данные по нашим записям в БД */
        private UserDetailsService userDetailsService;
    
        private UserDetailsPasswordService userDetailsPasswordService;
    
        . . . some methods
    }

Из структуры класса мы видим, что вводимые пароли user-ов будут закодированы - '[PasswordEncoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/password/PasswordEncoder.html)', нам необходимо будет 
реализовать - '[UserDetailsService](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.html)', данный интерфейс имеет один метод позволяющий загружать данные пользователей:

    public interface UserDetailsService {
         /*
         Находит пользователя по 'имени пользователя'. В реальной реализации поиск
         может быть чувствительным к регистру или нечувствительным к регистру в 
         зависимости от того, как экземпляр реализации настроен. 

         В этом случае <code>UserDetails</code> объект, который возвращается, может 
         иметь имя пользователя, которое имеет другой регистр, чем то, что действительно 
         был запрошен.

         @param username — имя пользователя, идентифицирующее пользователя, данные 
                           которого требуются.
         @return - полностью заполненная запись пользователя (никогда <code>null</code>)
         @throws UsernameNotFoundException - если пользователь не найден или у него нет
                                             GrantedAuthority
        */
        UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
    }

Т.е. мы передаем в метод String username, а получаем [UserDetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html) или наш UserDTO. Это [интерфейс UserDetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html) содержит все
необходимые методы для получения информации о user-ах см. [DOC/Authentication/UserDetails.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/UserDetails.txt). При этом в Spring уже есть 
реализация класса User см. [DOC/Authentication/UserClass.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/UserClass.txt) которой мы можем воспользоваться (но не будем, пока).

Реализуем аутентификацию наших пользователей при помощи провайдера - [DaoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html):
- Шаг 1. - Добавим в наши таблицы users и users_aud поля для хранения паролей - password. Делаем это через нашу систему
миграции БД - [db/changelog/db.changelog-4.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/db/changelog/db.changelog-4.0.sql). Прописываем изменения в файле - [db/changelog/db.changelog-master.yaml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/db/changelog/db.changelog-master.yaml);
- Шаг 2. - В файле [db/changelog/db.changelog-4.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/db/changelog/db.changelog-4.0.sql) каждому полю password зададим значение пароля по-умолчанию - 
[DEFAULT '{noop}123'](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/db/changelog/db.changelog-4.0.sql#L5), но, пока, префикс показывает, что мы не используем шифрацию паролей - {noop};
- Шаг 3. - Добавляем поле '[password](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/database/entity/User.java#L63)' именно в наш класс User - [spring/oldboy/database/entity/User.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/database/entity/User.java);
- Шаг 4. - Наш интерфейс Role - [spring/oldboy/database/entity/Role.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/database/entity/Role.java), должен реализовывать интерфейс [GrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/GrantedAuthority.html),
см. [DOC/Authentication/GrantedAuthority.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/Authentication/GrantedAuthority.txt). Хотя мы могли, как и в случае с User использовать уже готовую реализацию 
этого интерфейса;
- Шаг 5. - Реализуем [UserDetailsService](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.html). Это мы будем делать в нашем [spring/oldboy/service/UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/service/UserService.java). Для этого 
наш сервисный класс должен имплементировать интерфейс UserDetailsService, что и делает, а так же реализовать метод -
[loadUserByUsername(String username)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.html#loadUserByUsername(java.lang.String)), как было описано выше. Основные комментарии смотреть в классе - [UserService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/service/UserService.java);
- Шаг 6. - В интерфейсе [UserRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java) создаем метод [*.findByUsername(username)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java#L61);

Для проверки работы предоставленного нами UserDetailsService-a запускаем приложение и заходим под одной из уже готовых 
записей в таблице users БД, через default Login страницу - [http://localhost:8080/login](http://localhost:8080/login). Пароль по умолчанию мы задали: 
'123'. И если все пройдет нормально мы не увидим default пользователя и пароля при загрузке Spring-a.

________________________________________________________________________________________________________________________
#### Lesson 103 - Form-Login ([фильтр Username and Password](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html)).

Для входа в систему безопасности нашего приложения мы заменим предоставленную нам Spring Security Starter-ом (default) 
страницу аутентификации на свою. Её мы должны предоставить системе вместо существующей. Для этого изучим структуру 
исходной HTML страницы предоставленной Spring-ом:

    <div class="container">
          <form class="form-signin" method="post" action="/login">
            <h2 class="form-signin-heading">Please sign in</h2>
            <p>
              <label for="username" class="sr-only">Username</label>
              <input type="text" 
                     id="username" 
                     name="username" 
                     class="form-control" 
                     placeholder="Username" 
                     required="" autofocus="">
            </p>
            <p>
              <label for="password" class="sr-only">Password</label>
              <input type="password" 
                     id="password" 
                     name="password" 
                     class="form-control" 
                     placeholder="Password" 
                     required="">
            </p>
            . . .
          </form>
    </div>

Мы имеем форму, которая методом POST отправляется на endpoint "/login". И самое главное, поля ввода или input-поля с 
именами, именно теми 'name', что ждем система безопасности, а точнее класс [UsernamePasswordAuthenticationFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/UsernamePasswordAuthenticationFilter.html) см. [код на GitHub](https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/authentication/UsernamePasswordAuthenticationFilter.java):

    public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
        public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    
        public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
        
        . . . some parameter and method . . .
    }

И так создаем самописный вход (Login) в приложение:
- Шаг 1. - Создать или переделать предложенную Spring-ом форму Login. У нас уже была своя форма аутентификации 
совмещенная с регистрацией см. [templates/user/login.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/templates/user/login.html). Доработаем ее, добавим немного поясняющего текста и блок 
предупреждающий о неверно введенных данных.
- Шаг 2. - В нашем [spring/oldboy/http/controller/LoginController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/http/controller/LoginController.java). Метод *.login() из Lesson 75 нам не нужен, 
удаляем.
- Шаг 3. - Создаем свой конфигурационный файл безопасности [SecurityConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java). Как я и подозревал ранее для 
новичка эта горка с наскока не далась, см. [DOC/NewWebSecurityConfigurer](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_20/DOC/NewWebSecurityConfigurer). Применяем аннотации, прописываем метод 
[*.filterChain()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java#L55) и конфигурируем в нем [HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html) или см. [DOC/NewWebSecurityConfigurer/HttpSecurityClass.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/NewWebSecurityConfigurer/HttpSecurityClass.txt).

В данном случае применена простая настройка (без изысков) с перенаправлением на нашу страничку [Login](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/templates/user/login.html). 

________________________________________________________________________________________________________________________
#### Lesson 104 - [HTTP-Basic-Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html).

В прошлом уроке и уроках ранее мы изучили вариант аутентификации через форму Login (нашу самописную или предложенную 
Spring-ом). Это один из видов фильтров безопасности см. [DOC/AuthenticationProcess.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/AuthenticationProcess.jpg). Теперь рассмотрим вариант 
стандартного [HTTP-Basic-Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html) фильтра, 
краткая схема см. [DOC/HttpBasicAuthentication/HTTPBasicAuthentication.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/HttpBasicAuthentication/HTTPBasicAuthentication.jpg).

В случае аутентификации с использованием HTTPBasic фильтра вся информация передается в специальном хедере - Authentication,
в котором передается префикс Basic. В значении 'value' этой переменной передается пароль и логин разделенные двоеточием - 
(username:password). Данное сочетание после конкатенации шифруется при помощи - base64, т.е. отправляется в специальный
класс шифратор. Полученная закодированная строка отправляется в хедере Authentication нашего HTTP запроса.

Мы можем изучить (попробовать, в качестве демонстрации), как выглядит строка закодированная или раскодированная в коде 
base64 используя сайты:
- [https://www.base64encode.org/](https://www.base64encode.org/) - для кодирования;
- [https://www.base64decode.org/](https://www.base64decode.org/) - для декодирования;

Попробуем закодировать: ivanov_vanya@gmail.com:123 
Получим на выходе закодированную в base64 строку: aXZhbm92X3ZhbnlhQGdtYWlsLmNvbToxMjM=

Т.е. после получения декодер развернет код в обратную сторону, распарсит данные с разделением по двоеточию и проведет 
аутентификацию. Для изучения варианта HTTP аутентификации сделаем следующее:
- Шаг 1. - Отключим (закомментируем) режим аутентификации через [нашу Login форму](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/templates/user/login.html) в нашем же [SecurityConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java);
- Шаг 2. - Подключим дефолтный HTTP кастомайзер:

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                     .authorizeHttpRequests(authorize -> authorize.anyRequest()
                                                                  .authenticated())
                     .httpBasic(Customizer.withDefaults());
        
        return http.build();
        
        }

Хотя, как мы помним, у нас есть возможность использовать цепочку фильтров. Теперь мы можем проверить работу этого 
фильтра безопасности - запускаем наше приложение и обращаемся к странице '/users'. Форму Login мы отключили и поэтому
ловим другую форму см. [DOC/HttpBasicAuthentication/HttpBasicAuthenticationForm.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/HttpBasicAuthentication/HttpBasicAuthenticationForm.jpg). В браузере в режиме разработчика мы 
видим адрес запроса и ответ 401 - (Unauthorized), это означает, что мы пытаемся получить доступ к странице, на которую 
нужно сначала войти, используя действительный ID пользователя и пароль для просмотра. А так же, можем увидеть хедер 
Www-Authenticate: Basic. Данный хедер разъяснил браузеру, что мы используем в качестве механизма аутентификации и 
браузер сгенерировал всплывающую форму запроса пароль/логин.

- Шаг 3. - Вводим доступные нам данные логин - ivanov_vanya@gmail.com и пароль - 123 (естественно таковой должен быть в БД);

Все мы вошли в наше приложение см. [DOC/HttpBasicAuthentication/HttpBasicAuthenticationLogIn.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/DOC/HttpBasicAuthentication/HttpBasicAuthenticationLogIn.jpg). Мы видим результат 
работы страницы '/users', а самое главное, ответ и хедер - Authorization: Basic aXZhbm92X3ZhbnlhQGdtYWlsLmNvbToxMjM=

Что легко сравнить с работой энкодера на сайте [https://www.base64encode.org/](https://www.base64encode.org/) см. выше.

Для изучения работы декодирования нужно изучить внутреннюю структуру метода (создание токена, кодирование и т.д.):
*.doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) из класса 
[BasicAuthenticationFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/www/BasicAuthenticationFilter.html) 

И конечно реализацию метода *.convert() интерфейса [AuthenticationConverter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/AuthenticationConverter.html).

В классе [SecurityConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java) возвращаем форму аутентификации через Login, *.httpBasic(Customizer.withDefaults())
комментируем и переходим к следующему примеру.

________________________________________________________________________________________________________________________
#### Lesson 105 - PasswordEncoder.

Вполне логично, что поле пароль (password) таблицы users нашей БД должно быть зашифровано. На данный момент часть паролей
имеют префикс {noop}, т.е. пароль хранится 'как есть', а это не приемлемо. Поэтому обычно при заполнении формы регистрации
пароль перед отправкой в БД шифруется или проходит через некий энкодер. Обычно происходит сравнение зашифрованных паролей.
Если мы посмотрим внутреннюю структуру класса [DaoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html), то увидим методы позволяющие задать энкодер см. [на GitHub](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authentication/dao/DaoAuthenticationProvider.java):

    public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    
        private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
        private PasswordEncoder passwordEncoder;
        private volatile String userNotFoundEncodedPassword;
        private UserDetailsService userDetailsService;
        private UserDetailsPasswordService userDetailsPasswordService;
    
        public DaoAuthenticationProvider() {
            this(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        }
    
        /*
         Создает новый экземпляр, используя предоставленный {@link PasswordEncoder}.
         @parampasswordEncoder — {@link PasswordEncoder}, который нужно использовать. 
         Не может быть нулевым. С версии 6.0.3
        */
        public DaoAuthenticationProvider(PasswordEncoder passwordEncoder) {
            setPasswordEncoder(passwordEncoder);
        }

      . . . some code . . .

        /*
        Устанавливает экземпляр PasswordEncoder, который будет использоваться для 
        кодирования и проверки паролей. Если не установлено, пароль будет сравниваться 
        с помощью PasswordEncoderFactories.createDelegatingPasswordEncoder().
        
        Параметры: passwordEncoder – должен быть экземпляром одного из типов PasswordEncoder.
        */
        public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
            Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
            this.passwordEncoder = passwordEncoder;
            this.userNotFoundEncodedPassword = null;
	    }

      . . . some code . . . 
    
    }

Именно это поле - passwordEncoder, будет использоваться для сравнения паролей [см. метод этого же класса](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authentication/dao/DaoAuthenticationProvider.java#L127):

      @Override
      protected Authentication createSuccessAuthentication(Object principal, 
                                                           Authentication authentication,
                                                           UserDetails user) {
          boolean upgradeEncoding = this.userDetailsPasswordService != null
          && this.passwordEncoder.upgradeEncoding(user.getPassword());
              if (upgradeEncoding) {
                  String presentedPassword = authentication.getCredentials().toString();
                  String newPassword = this.passwordEncoder.encode(presentedPassword);
                  user = this.userDetailsPasswordService.updatePassword(user, newPassword);
              }
          return super.createSuccessAuthentication(principal, authentication, user);
      }

Кодировщики паролей создаются (берутся) из класса см. [PasswordEncoderFactories](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/factory/PasswordEncoderFactories.html), список этих энкодеров достаточно широк.

Обычно по-умолчанию используется см. [BCryptPasswordEncoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html)

Особенность энкодера паролей в том, что он не представлен в виде bean-a. Однако мы хотим его использовать в нашем Spring
приложении, значит нам придется его сделать. Т.е. мы создадим bean, который будет инициализировать интересующий нас 
энкодер, чтобы мы могли использовать шифрование паролей в наших формах взаимодействия с пользователями. 

Начнем:
- Шаг 1. - Добавим в [SecurityConfiguration метод возвращающий](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java#L94) см. [PasswordEncoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/password/PasswordEncoder.html);
- Шаг 2. - Редактируем нашу страницу регистрации - [resources/templates/user/registration.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/templates/user/registration.html), под текущие задачи, 
добавляем [input поля пароль](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/templates/user/registration.html#L26) (rowPassword);
- Шаг 3. - В класс [UserCreateEditDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/dto/UserCreateEditDto.java) добавляем поле пароля - [rawPassword](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/dto/UserCreateEditDto.java#L51), см. комментарии в классе.
- Шаг 4. - В классе преобразователе [UserCreateEditMapper](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/mapper/UserCreateEditMapper.java), в методе [*.copy()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/mapper/UserCreateEditMapper.java#L47) добавляем работу с паролем:

        Optional.ofNullable(object.getRawPassword())
                .filter(str -> StringUtils.hasText(str))
                .map(rawPassword -> passwordEncoder.encode(rawPassword))
                .ifPresent(password -> user.setPassword(password));

- Шаг 5. - В классе [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/http/controller/UserController.java) в методе [*.create()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/http/controller/UserController.java#L173) добавляем строку создающую user-a и возвращаем страницу Login-a, 
т.е. если все прошло успешно и пользователь зарегистрировался в системе он должен пройти аутентификацию;

Проверяем работу, пока только с уже ранее созданным пользователем (кнопка Registration у нас пока не работает), т.е. 
входим в приложение под уже существующим user-ом и заходим на [http://localhost:8080/users/registration](http://localhost:8080/users/registration), далее 
регистрируем нового user-a с новым username и password (вносим запись в БД) и после регистрации попадаем на страницу 
Login. Теперь просто вводим вновь созданные данные в форму и логинимся.  

В нашей БД мы видим нашего нового user-a (в таблице users) с зашифрованным паролем с префиксом {bcrypt}.

________________________________________________________________________________________________________________________
#### Lesson 106 - [Logout](https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html).

Уроки выше показали, как настроить формы и фильтры чтобы пользователь нашего приложения-сервиса смог 
аутентифицироваться (залогиниться) для доступа к тем эндпоинтам (endpoint), что у нас есть на данный момент. 
Теперь разберем, как нам настроить фильтр позволяющий дезавуировать предыдущую аутентификацию (разлогинить) 
пользователя приложения (не используя предложенную Spring-ом форму Logout).

Для этого используется класс [LogoutFilter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/logout/LogoutFilter.html)
метод *.doFilter() этого класса удаляет данные о нашем пользователе из MAP-ы сервера, хранящей сведения о JSESSIONID
(то, что мы проделывали вручную, когда тестировали работу LogIn формы), т.е. чистит сессию.

Данный фильтр можно добавить в нашу цепочку в методе *.filterChain() класса [SecurityConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java) и донастроить:
- Шаг 1. - Добавляем метод *.logout() в построитель цепочки фильтров:

        .logout(logout -> logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .deleteCookies("JSESSIONID"))

- Шаг 2. - Создадим кнопку LogOut как универсальный элемент всех наших страниц (это может быть, как хедер, так и футер).
см. [templates/fragments/logout.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/templates/fragments/logout.html)
- Шаг 3. - Для проверки внедрим нашу кнопку в одну из страниц, например [users.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/templates/user/users.html). Это будет отдельный блок с нотацией
Thymeleaf:
  
        <div th:insert="~{fragments/logout :: header}"></div>

Запускаем приложение, логинимся и попадаем на страницу с пагинацией (так настроено), видим кнопку Logout. Можно перейти 
на любую другую страницу и там эта кнопка, как внедренный элемент (фрагмент) будет доступна. Нажав на нее мы 
разлогиневаемся и автоматически (так настроена [цепочка фильтров в методе *.filterChain()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java#L67) класса [SecurityConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java)) 
попадаем на страницу [Login](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/resources/templates/user/login.html).

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