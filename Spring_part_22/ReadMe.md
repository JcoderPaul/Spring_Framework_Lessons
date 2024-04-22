### Spring Boot lessons part 22 - Security Starter - PART 3

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_22/DOC) и др. полезные файлы.

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
Для начала проведем предварительную подготовку (подгрузим зависимости в [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/build.gradle)):

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
        Автоматически Gradle создал тестовую зависимость на Junit5, мы можем 
        использовать как Junit4, так и TestNG
    */
    test {
        useJUnitPlatform()
    }

    /*
    Подключим Spring Boot Starter он включает поддержку
    авто-конфигурации, логирование и YAML
    */
    implementation 'org.springframework.boot:spring-boot-starter'

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

    /* Зависимости необходимые при тестировании Spring Boot приложения */
    testImplementation 'org.springframework.boot:spring-boot-starter-test'

    /* Зависимости позволяет тестировать приложение с подключенным Spring Security */
    testImplementation 'org.springframework.security:spring-security-test'

    testImplementation "org.testcontainers:postgresql:${versions.testcontainers}"

________________________________________________________________________________________________________________________
#### OAuth-2.0 (Теория)

**OAuth 2.0** — протокол авторизации, позволяющий выдать одному сервису (приложению) права на доступ к ресурсам 
пользователя на другом сервисе (приложении, сервере данных). В OAuth 2.0 используются токены (это часть данных, 
которая является авторизацией для доступа от имени конечного пользователя). OAuth 2.0 не определяет конкретный 
формат токенов. Однако часто используется формат JSON Web Token (JWT), что позволяет эмитентам включать данные 
в сам токен. Кроме того, по соображениям безопасности токены могут иметь ограниченный срок действия.

**OpenID Connect (OIDC)** — это протокол аутентификации, основанный на протоколе OAuth2 (который используется для 
авторизации). Для предоставления служб удостоверений OIDC использует стандартизованные потоки сообщений от OAuth2.
OIDC — это безопасный механизм, позволяющий приложению связаться со службой идентификации, чтобы получить необходимые 
данные о пользователе и вернуть их обратно в приложение, обеспечив полную защиту данных.

Особенности OAuth 2.0:
- Разделение сущности пользователя и приложения, запрашивающего доступ. Благодаря этому разделению мы можем управлять 
правами приложения отдельно от прав пользователя.
- Вместо привычных логина и пароля, которые имеют определенный набор прав и время жизни, мы получаем доступ к ресурсам 
с помощью случайно сгенерированных строк — токенов.
- Можно выдавать права максимально точечно, опираясь на собственные пожелания, а не на заранее определённый набор прав.

Мы сталкиваемся с этим протоколом, когда (например):
- авторизуемся на сторонних площадках через аккаунты соцсетей;
- устанавливаем себе на мобильное устройство приложение, взаимодействующее с нашими данными в облачных сервисах типа 
Google или Яндекс;
- используем сторонние приложения (боты в Telegram и других мессенджерах) для уведомлений и пр.

Доступ может быть ограничен правами пользователя или же областями видимости, что повышает гибкость использования 
протокола. Например, стороннее приложение может только читать наши данные, а не изменять их, либо же только изменять.

________________________________________________________________________________________________________________________
!!! Еще раз!!! Основное различие между протоколами OIDC и OAuth состоит в цели использования: 
- OpenID служит для аутентификации, то есть подтверждения личности пользователя в клиентском сервисе (если грубо 
человек - to - сервис). Например, вы можете авторизоваться в любом из сервисов Google под своим аккаунтом и работать с 
ними от своего имени, со своими данными. 
- OAuth служит для авторизации, то есть выдачи клиентскому сервису прав на выполнение действий с ресурсами пользователя 
(если грубо сервис - to - сервис, как правило, на чтение данных, но иногда и на изменение) от его имени.
________________________________________________________________________________________________________________________

Для верификации пользователя OpenID использует ID учетной записи у провайдера, а OAuth — авторизационные ключи (токены) 
с предопределенным сроком действия и правами доступа.

Более подробно см. док.: [DOC/OAuth_2_0](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_22/DOC/OAuth_2_0)

И так мы разобрались, что OAuth это не сколько про взаимодействие User-Service, сколько про взаимодействие Service-Service 
и теперь наше приложение становится клиентом (Client) и будет взаимодействовать с Authorization Service см. 
[DOC/OAuth_In_Our_App.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/OAuth_In_Our_App.jpg). 

Как было упомянуто в статье [DOC/OAuth_2_0/AuthorizationWithOAuth_2_0.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/OAuth_2_0/AuthorizationWithOAuth_2_0.txt), нам необходимо зарегистрировать наше 
приложение на сервисе авторизации. После регистрации сервис предоставит приложению client_id и client_secret (некий 
аналог username и password с помощью которого приложение будет обращаться к серверу авторизации). 

Для того чтобы наше приложение смогло реализовать выше описанный процесс нам необходимо подключить две зависимости:

    /* Набор классов для взаимодействия приложения и сервиса авторизации */
    implementation 'org.springframework.security:spring-security-oauth2-client'

    /* Набор классов для работы токенами, в частности JWT */
    implementation 'org.springframework.security:spring-security-oauth2-jose'


Хотя в статье [DOC/OAuth_2_0/AuthorizationWithOAuth_2_0.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/OAuth_2_0/AuthorizationWithOAuth_2_0.txt) процесс взаимодействия 'User-Application-AuthService' разобран
подробно во всех деталях и предложены наглядные примеры [DOC/OAuth_2_0/images](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_22/DOC/OAuth_2_0/images), разберем это процесс еще раз на схеме см.
[DOC/AuthorizationCodeFlow.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/AuthorizationCodeFlow.jpg) (в нашем случае):
- Шаг 1. - Пользователь обращается к нашему приложению через браузер (в статье о которой упоминалось ранее, говорится о
том, что пользователь при взаимодействии с приложением использует некий агент могущий совершать перенаправления ссылок,
без такой возможности вся схема сыпется). В форме аутентификации пользователь вместо ввода стандартного пароля и логина
выбирает вход через сервис авторизации (например Google) и приложение перенаправляет его на сервер авторизации.

Мы уже упомянули, что ПРИЛОЖЕНИЕ ДОЛЖНО быть зарегистрировано на сервисе авторизации, ПОЛЬЗОВАТЕЛЬ тоже ДОЛЖЕН быть
зарегистрирован на сервисе авторизации, в самом начале статьи [DOC/OAuth_2_0/AuthorizationWithOAuth_2_0.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/OAuth_2_0/AuthorizationWithOAuth_2_0.txt) это момент
особо выделен.

Вместе с переадресацией передаются различные параметры, в том числе и регистрационные данные приложения (client_id, 
redirect_uri - на который будет возвращен пользователь после удачной аутентификации на сервисе авторизации, фактически,
он вернется обратно к приложению, scope, response_type - какой flow будет осуществлен). Браузер получив ответ 302 
перенаправляет запрос на сервер Google (в нашем примере).

- Шаг 2. - Сервис авторизации возвращает пользователю свою форму аутентификации с логином и паролем и САМОЕ ВАЖНОЕ 
указывает информацию о том какие SCOPE запросило приложение на котором пользователь планировал авторизоваться. 
- Шаг 3. - Пользователь вводит свой логин и пароль, если они не верны сервис авторизации предлагает ввести их еще раз,
до тех пор, пока не будут введены верные данные (good credentials).
- Шаг 4. - После того как пользователь удачно аутентифицировался на сервисе авторизации его браузер получает редирект на
адрес приложения, т.е. тот самый redirect_URI, что был получен сервисом авторизации на первом шаге. 
- Шаг 5.1 - Вместе с переадресацией на redirect_URI приложению передаются (code - код авторизации, scope, state). 

Далее взаимодействие приложения и сервиса авторизации происходит незаметно для пользователя.

- Шаг 6. - Теперь приложению необходимо получить у сервиса авторизации ключи доступа (access token и refresh token). Оно
авторизируется на сервисе передавая свои client_id и client_secret, код авторизации и если все прошло нормально, то
получает требуемые token-ы (фактически разрешения для взаимодействия с неким другим сервисом, но у нас это не 
реализовано, хотя такой вариант аутентификации тоже нормален).
- Шаг 5.2 - Приложение возвращает приветственную страницу или меню (в нашем случае отображает список user-ов).

В данном случае приложение не получает никакой информации о пользователе только token-ы, в случае же когда приложению
все таки нужны минимальные данные по пользователе применяется протокол OIDC. И тогда схема описанная выше остается без 
изменений, НО на первом шаге вместе с переадресацией передается параметр scope=openid, И на последнем шаге вместе с 
access_token, refresh_token передается id_token в формате JWT.

________________________________________________________________________________________________________________________
#### Lesson 113 - Настройка OAuth-2.0 в web-приложении (Google as Authorization service).

Мы помним, что и пользователь и приложение (Client) должны быть зарегистрированы на сервисе авторизации, чтобы появилась 
возможность реализовать любую из схем OAuth авторизации, например, Authorization Code Flow. 

**Регистрация приложения на сервисе авторизации**

Для примера будем использовать мощности Google. Если у нас уже есть, некий - our_name@gmail.com, значит мы можем 
авторизоваться на сервисе и перейти на [Google Identity](https://developers.google.com/identity/) или еще точнее на
[Использование OAuth 2.0 для приложений веб-сервера](https://developers.google.com/identity/protocols/oauth2?hl=ru) и 
изучив документацию зарегистрировать наше приложение в сервисе авторизации Google (см. описание в разделе 
"Создать учетные данные для авторизации"):
- Переходим на [Credentials page](https://console.cloud.google.com/apis/credentials).
- В разделе "Select a project" создаем новый проект, даем ему имя и ждем пока Google сконфигурирует его.
- Теперь, в том же разделе "Select a project" выбираем только что созданный проект (например, My Spring App Project) и 
переходим в пункт меню "+CREATE CREDENTIALS".
- Выбираем пункт "OAuth client ID", и теперь, нам необходимо сконфигурировать consent.
- Нажимаем отдельную кнопку "CONFIGURE CONSENT SCREEN" и попадаем в меню из двух пунктов: 
  - Internal - Доступно только пользователям внутри вашей организации. Вам не нужно будет отправлять свое приложение 
  на проверку (verification). 
  - External - Доступно любому тестовому пользователю с учетной записью Google. Ваше приложение запустится в режиме 
  тестирования и будет доступно только пользователям, которых вы добавите в список тестовых пользователей. Когда ваше 
  приложение будет готово к выпуску в рабочую среду, вам может потребоваться проверить (verify) его.
- Выбираем External, жмем "CREATE" и попадаем в раздел "Edit app registration".
- Даем нашему приложению пафосное название, например "OldBoy First Spring App", выбираем почту в "User support email", 
будет предложен один из наших gmail адресов, и наконец в поле "Developer contact information" добавляем тот же email, 
все жмем "SAVE AND CONTINUE" несколько раз не внося никаких изменений в предложенные настройки (пока только учимся, 
делаем все проще), наконец жмем "BACK TO DASHBOARD".
- Возвращаемся в раздел меню "Credentials" и жмем на "+CREATE CREDENTIALS", далее выбираем "OAuth client ID" и наконец в
выпадающем меню выбираем "Web application", даем ему имя (снова?).
- И вот именно тут, в разделе "Create OAuth client ID", есть очень важные настройки, мы должны ввести redirect_URI см. 
[DOC/AuthorizationCodeFlow.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/AuthorizationCodeFlow.jpg). В разделе "Authorized redirect URIs" добавляем, в нашем случае: [http://localhost:8080/login/oauth2/code/google](http://localhost:8080/login/oauth2/code/google). 
Этот адрес можно найти в документации по [Spring Security](https://docs.spring.io/spring-security/reference/), а именно [Core Configuration](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/core.html).
Конечно мы его можем изменить сообразно настройкам нашего приложения, именно на этот адрес будет возвращаться код 
авторизации - Authorization Code. Тут же добавим и [http://localhost:8080/swagger-ui/oauth2-redirect.html](http://localhost:8080/swagger-ui/oauth2-redirect.html),
поскольку планируем подключить OAuth2.0 на Swagger-e для тестирования REST. Все, жмем "CREATE".
- Сервис сгенерировал для нас Client ID и Client secret (аналоги логина и пароля для пользователя). Именно с ними наше 
приложение будет обращаться к сервису авторизации, когда пользователь выберет аутентификацию с помощью Google.

**Настройка OAuth2.0 протокола в нашем web-приложении**

- Шаг 1. - Настройка файла свойств [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/resources/application.yml):

Все полученные параметры мы можем прописать в файле настройки нашего приложение [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/resources/application.yml), префикс и другие названия 
можно посмотреть в классе [OAuth2ClientProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/security/oauth2/client/OAuth2ClientProperties.html):

    @ConfigurationProperties(prefix="spring.security.oauth2.client")
    public class OAuth2ClientProperties 
                              extends Object
                                    implements InitializingBean

Так же нам понадобятся два вложенных класса [Provider](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/security/oauth2/client/OAuth2ClientProperties.Provider.html) и [Registration](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/security/oauth2/client/OAuth2ClientProperties.Registration.html),
на данном этапе нам понадобится класс Registration и ТОЧНЫЕ названия его полей: clientId, clientSecret, redirectUri, 
scope, которые мы используем в [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/resources/application.yml):

    security:
      oauth2:
        client:
          registration:
            google:
              clientId: ... Client ID полученный от Google ...
              clientSecret: ... Client secret полученный от Google ...
              redirectUri: http://localhost:8080/login/oauth2/code/google
              scope: openid,email,profile

Далее нам нужно настроить Provider и тут нам на помощь приходит [CommonOAuth2Provider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/oauth2/client/CommonOAuth2Provider.html) в 
котором уже есть четыре константных сервиса авторизации: FACEBOOK, GITHUB, GOOGLE, OKTA. Т.е. если мы будем использовать 
свой провайдер сервиса авторизации, то нам придется самим настраивать параметры описанные в классе Provider. А на данном
этапе, поскольку мы используем уже готовый сервис (провайдер сервиса) мы его и указали в application.yml - google см. 
выше.

- Шаг 2. - Настройка файла конфигураций безопасности [SecurityConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/java/spring/oldboy/config/SecurityConfiguration.java):

Еще раз смотрим [Core Configuration](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/core.html) и
добавляем в цепочку фильтров новый метод:

    ... some code ...

    .formLogin(login -> login.loginPage("/login")
                             .defaultSuccessUrl("/users", true))
    /* Добавляем авторизацию через Google */
    .oauth2Login(withDefaults());

    ... some code ...

Запускаем приложение и пытаемся залогиниться. Нам возвращается форма от Google см. [DOC/GoogleLoginFormWithOAuth2.0.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/GoogleLoginFormWithOAuth2.0.jpg).

- Шаг 3. - Настройка нашей Login формы ([login.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/resources/templates/user/login.html)), так, чтобы совмещать два вида входа в наше приложение:

Мы помним (см. [DOC/AuthorizationCodeFlow.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/AuthorizationCodeFlow.jpg)), что когда пользователь приложения выбирает вариант авторизации через 
сторонний сервис (Google), приложение должно отправить его именно на тот сервис, что и происходит, если заглянуть в 
HTML код предложенной Spring-ом формы:

    <div class="container">
      <h2 class="form-signin-heading">Login with OAuth 2.0</h2><table class="table table-striped">
        <tbody><tr><td><a href="/oauth2/authorization/google">Google</a></td></tr>
      </tbody></table>
    </div>

Значит нам нужно добавить соответствующий код в нашу форму [login.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/resources/templates/user/login.html):

    <div>
        <div>Login with:</div>
        <a href="/oauth2/authorization/google">Google</a>
    </div>

- Шаг 4. - Повторная настройка файла конфигурации [SecurityConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/java/spring/oldboy/config/SecurityConfiguration.java):

У нас в файле конфигурации до сих пор стоит настройка OAuth2.0 формы как withDefaults(), это нужно изменить и желательно,
чтобы у нас была возможность использовать обычную авторизацию через пароль/логин и конечно с использованием сервиса Google.
Значит нам необходимо, как в случае с настройкой .formLogin() явно указать [нашу самописную страницу login.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/java/spring/oldboy/config/SecurityConfiguration.java#L72) и страницу
куда переходить в случае успешной авторизации:

     ... some code ...

    .oauth2Login(oauthConfig -> oauthConfig.loginPage("/login")
                                           .defaultSuccessUrl("/users", true));
     ... some code ...

Теперь можно снова запустить и проверить работу нашей Login формы, а самое главное, варианта авторизации через сервис Google.
Да, теперь при выборе Google мы видим redirect на https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=... ,
и даже можем посмотреть какие response_type, client_id, scope и redirect_URI были зашиты в адрес. При попадании на сам
сервис Google мы видим scope который запросило приложение (Приложению "OldBoy First Spring App" будет предоставлен доступ 
к вашим данным: имени, адресу электронной почты, языковым настройкам и фото профиля.). 

Теперь можем выбрать пользователя, который будет авторизован в нашем web-приложении... но не все так гладко... 

Когда Google вернет пользователя обратно в наше приложение мы получим - Bad credentials! Почему см. ниже!

________________________________________________________________________________________________________________________
**!!! Неожиданная проблема !!!** 

Как оказалось недостатки в коде не всегда бывают источником проблемы. Если использовать форму
"Login with OAuth2.0" предоставленную Spring-ом при определенных условиях можно получить сообщение на красном фоне:

**"[invalid_id_token] An error occurred while attempting to decode the Jwt: Jwt expired at"**

Как оказалось: Утверждение iat - это время выдачи токена (time the token is issued). Если у вас возникла эта ошибка, 
возможно, проблема с перекосом часов между сервером-эмитентом (Auth) и вашим приложением Spring Boot.

Наиболее очевидным решением является подтверждение того, что на сервере, на котором работает ваше Spring Boot приложение,
установлено правильное время.

В качестве альтернативы вы можете передать опцию свободы действий при запросе токена, и это допустит некоторое отклонение 
в тактовой частоте сервера.

________________________________________________________________________________________________________________________
#### Lesson 114 - Преобразование Google Authentication-Principle в понятный нашему приложению UserDetails.

После того как пользователь авторизовался на сервисе Google и был перенаправлен обратно в приложение, вместе с response
вернулись данные пользователя, но не в том формате, к которому мы "привыкли", ну или другими словами, приложение не понимает
"кто стучится в дверь", см. [DOC/GoogleAuthenticationPrincipal.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/GoogleAuthenticationPrincipal.jpg). Вместо классического UserDetails прилетает DefaultOidcUser,
хотя если капнуть глубже мы сможем извлечь нужную нам информацию. 

Т.е. нам нужно сопоставить полученный от Google DefaultOidcUser и привычный нашему приложению UserDetails.

- Шаг 1. - Изменим UserInfo endpoint в контроллере безопасности [SecurityConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/java/spring/oldboy/config/SecurityConfiguration.java). Этот endpoint отвечает за 
создание объекта Authentication. Нам нужен метод oidcUserService из класса [OAuth2LoginConfigurer](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configurers/oauth2/client/OAuth2LoginConfigurer.html), точнее из его вложенного
класса [OAuth2LoginConfigurer.UserInfoEndpointConfig](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configurers/oauth2/client/OAuth2LoginConfigurer.UserInfoEndpointConfig.html), который
устанавливает службу OpenID Connect 1.0, используемую для получения пользовательских атрибутов конечного пользователя из 
конечной точки UserInfo:

        /* Добавляем авторизацию через Google */
        .oauth2Login(oauthConfig -> oauthConfig.loginPage("/login")
                                               .defaultSuccessUrl("/users")
        /* Мы должны предоставить реализацию OAuth2UserService */
                                               .userInfoEndpoint(userInfo -> 
                                                        userInfo.oidcUserService(oidcUserService())));

- Шаг 2. - Мы должны реализовать сервис [OAuth2UserService](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/userinfo/OAuth2UserService.html), чтобы вернуть его в *.oidcUserService().
Он принимает на вход [OAuth2UserRequest](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/userinfo/OAuth2UserRequest.html), а возвращает [OAuth2User](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/user/OAuth2User.html).
В OidcUserRequest есть idToken см. [DOC/GoogleAuthenticationPrincipal.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/GoogleAuthenticationPrincipal.jpg) или [DOC/OidcUserRequest.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/OidcUserRequest.jpg) именно в нем содержится
информация о пользователе полученном из Google сервиса, самое главное эта информация уже преобразована в удобочитаемую и
мы можем ее использовать. Нам нужен email пользователя, чтобы извлечь с его помощью user-a из нашей БД см. [SecurityConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/java/spring/oldboy/config/SecurityConfiguration.java)
- Шаг 3. (вне алгоритма действий) - При текущей реализации [SecurityConfiguration (см. предыдущие уроки)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_20/src/main/java/spring/oldboy/config/SecurityConfiguration.java) мы поймали цикл см. [DOC/SecurityConfigurationCycle.jpg ](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/SecurityConfigurationCycle.jpg)
и поэтому вынесем декодер паролей в отдельный класс [PasswordEncoderConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/java/spring/oldboy/config/PasswordEncoderConfiguration.java).

Все, настройка приложения закончена, можно его запустить и проверить, что у нас одновременно работают два фильтра 
Authorization и OAuth2.0 фильтр, т.е. используя нашу форму [login.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/resources/templates/user/login.html) мы можем входить в приложение, как через сервис 
Google, так и через обычный ввод логина и пароля.

________________________________________________________________________________________________________________________
#### Lesson 115 - Swagger-Authorization.

С момента внедрения Spring security у нас возникает некое неудобство при взаимодействии со Swagger - мы не можем сразу 
обратиться к нему без обязательной аутентификации. Т.е. при запущенном приложении мы конечно сможем обратиться по адресу:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) и увидим интерфейс Swagger-a,
но вот протестировать методы в должном объеме у нас не получится. 

Вариантов решения данного вопроса несколько:

- Вариант 1. - Мы можем подключить HTTP-Basic аутентификацию в цепочке фильтров безопасности, как в Lesson 104:
      
              http.csrf(. . . code . . .)
                     .authorizeHttpRequests(. . . code . . .)
                     .httpBasic(Customizer.withDefaults());
                  
              return http.build();

Как мы помним, в этом случае браузер сам будет генерировать окно ввода пароля и логина при обращении к нашему приложению.

- Вариант 2. - Создать отдельный REST endpoint для логина в Swagger-e, чтобы аутентифицировать пользователя и работать со
Swagger-ом.

- Вариант 3. - Настроить OAuth2.0 фильтр и его свойства для аутентификации в Swagger-e:
  - Шаг 1. - Вносим коррективы в [application.yaml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/resources/application.yml#L63):

        springdoc:
          swagger-ui:
            oauth:
              client-id: . . . Google client_id . . .
              client-secret: . . . Google client_secret . . .
            scopes: openid,email,profile
              # Его мы указали при создании Credentials в сервисе Google
            oauth2-redirect-url: http://localhost:8080/swagger-ui/oauth2-redirect.html

  - Шаг 2. - Создаем отдельный файл конфигурации для Swagger-a, назовем его - [OpenApiConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/java/spring/oldboy/config/OpenApiConfiguration.java) см. класс и 
комментарии в нём, извлекаем нужные URL из [CommonOAuth2Provider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/oauth2/client/CommonOAuth2Provider.html) так же см.
[Spring Core Configuration](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/core.html).
  - Шаг 3. - Запускаем приложение и сразу идем на [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html),
в правом верхнем углу Swagger-a мы увидим кнопку авторизации см. [DOC/SwaggerOAuth2.0Authorize.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/SwaggerOAuth2.0Authorize.jpg). Нажимаем на нее и видим
следующее окно см. [DOC/AvailableSwaggerAuthorization.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/DOC/AvailableSwaggerAuthorization.jpg). Если все прошло нормально, то сведения из [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_22/src/main/resources/application.yml#L63) попадут в
эту форму и мы сможем авторизоваться через Google сервис.

Все, теперь мы можем аутентифицироваться сразу в Swagger-e и тестировать конечные точки нашего REST контроллера.

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