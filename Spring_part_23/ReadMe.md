### Spring Boot lessons part 23 - Интернационализация и локализация в Spring.

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_23/DOC) и др. полезные файлы.

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

Для начала проведем предварительную подготовку (подгрузим зависимости в [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/build.gradle)):

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

---------------------------------------------------------------------------------------------------------------

    !!! НЕ ЗАБЫВАЕМ !!! У нас есть классы (см. ConnectionPool.java и комментарии), где мы пытаемся внедрить параметры в 
    поля через аннотации, с использованием аннатационного же конструктора @RequiredArgsConstructor. Фокус не пройдет без 
    создания и настройки файла конфигурации: lombok.config - 'контекст' просто завалится. 

    Либо все делаем руками от начала и до конца, либо помним какие вспомогательные средства используем и какие их особенности
    могут повлиять на работу приложения.

---------------------------------------------------------------------------------------------------------------

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
#### Lesson 116 - i18n-MessageSource.

Как и в [Servlet-ах](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/tree/master/MVCPracticeAdvanced) процесс 
интернационализации идет через [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html).
И конечно в Spring-e это реализуется чуть по-другому, все bundle имеют зарезервированные названия и настройку можно 
провести за 3-и шага:

- Шаг 1. - В [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/application.yml) добавляем префикс messages из [MessageSourceAutoConfiguration](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/context/MessageSourceAutoConfiguration.html), это
конфигурационный файл для [MessageSource](https://docs.spring.io/spring-framework/docs/6.1.5/javadoc-api/org/springframework/context/MessageSource.html), а значит и для
[ResourceBundle](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ResourceBundle.html):

        messages:
            basename: messages
            fallback-to-system-locale: false

- Шаг 2. - Создаем файлы message-ей для разных языков в папке [resources](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_23/src/main/resources) нашего приложения:
  - [messages.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/messages.properties) - с default настройками (в нашем варианте язык EN);
  - [messages_ru.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/messages_ru.properties) - с настройками для русского языка (_ru - указан только язык, если добавить _RU, то мы 
добавляем намек на регион, хотя это и необязательно и т.д.);

По умолчанию приложение Spring Boot будет искать файлы messages, содержащие ключи и значения интернационализации, в папке 
[src/main/resources](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_23/src/main/resources). Обычно файлы для каждой локали называются messages_XX.properties, где XX — код локали. Мы также можем 
определить резервный файл messages.properties.

!!! Однако, резервный файл не следует считать связанным с локалью по умолчанию. Это два отдельных понятия !!!

Языковой стандарт по умолчанию - это языковой стандарт, используемый по умолчанию, когда запрошенный языковой стандарт 
недоступен или имеет значение NULL. С другой стороны, резервный файл — это место для поиска свойств в случае сбоя 
перевода локали.

Если ключ не существует в определенном файле локали, приложение просто вернется к резервному файлу. Ключи для значений, 
которые будут локализованы, должны быть одинаковыми в каждом файле, а значения должны соответствовать языку выбранной 
локали.

IDEA сразу объединила оба свойства в ассоциированный комплект: Resource Bundle 'messages'. Теперь необходимо удостовериться,
что в настройках среды разработки, кодирование файлов идет в UTF-8 см. [DOC/IDEASettingFileEncodingUTF-8.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/DOC/IDEASettingFileEncodingUTF-8.jpg)

- Шаг 3. - Фактически уже все готово, но нам нужно убедиться, что интернационализация работает. Для этого создадим REST
endpoint позволяющий тестировать работу нашей - Internationalization (i18n) - [MessageRestController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/java/spring/oldboy/http/rest/MessageRestController.java) см. комментарии
в коде класса.

Теперь проверяем работу наших настроек на REST контроллере, запускаем приложение, переходим по [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
и тестируем [*.getMessage()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/java/spring/oldboy/http/rest/MessageRestController.java#L34) метод. Например, 'key' у нас это 'login.username', тогда 'lang' это 'ru', 'fr' или 'en', см.
[DOC/message-rest-controller-ru-test.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/DOC/message-rest-controller-ru-test.jpg) и [DOC/message-rest-controller-fr-test.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/DOC/message-rest-controller-fr-test.jpg).

________________________________________________________________________________________________________________________
#### Lesson 117 - i18n и внедрение его в HTML страницы с Thymeleaf.

На HTML страницах для использования интернационализации применяется тег #{.....} см. пример:

    <span th:text="#{login.username}">Username:</span>

Т.е. нам необходимо по данному шаблону интернационализировать все наши страницы. Однако у нас нет возможности выбора 
языка ни на одной странице, нужно это исправить. Добавим блок выбора языка в папку [fragment](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_23/src/main/resources/templates/fragments) - [language_selection.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/templates/fragments/language_selection.html).

И так повторим, что необходимо сделать для внедрения интернационализации и локализации в Spring приложение:
- Шаг 1. - Настроить [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/application.yml) см. выше предыдущий Lesson 116.
- Шаг 2. - Создать файлы message-ей для разных языков в папке [resources](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_23/src/main/resources) нашего приложения см. выше предыдущий Lesson 116.
- Шаг 3. - Предоставить пользователю возможность изменять язык интерфейса, желательно, на всех доступных страницах. Для 
этого создаем отдельную страницу [language_selection.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/templates/fragments/language_selection.html), которая будет интегрироваться в другие HTML страницы. 
- Шаг 4. - Используя синтаксис Thymeleaf описанный выше внесем изменения в файлы: [language_selection.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/templates/fragments/language_selection.html), [logout.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/templates/fragments/logout.html) и
[user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/templates/user/user.html) (только в них, другие можно изменить по данному же принципу, но тут мы этого делать не будем)

И вот тут начинается самое интересное. Нам необходимо отслеживать запрос генерируемый [language_selection.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/templates/fragments/language_selection.html) на нашем
сервере (в нашем приложении). Т.е. наше приложение должно увидеть что параметр lang=en изменился на lang=ru или другой 
язык. Для этого нам нужно внести изменения в [WebConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/java/spring/oldboy/config/WebConfiguration.java):

- Шаг 5. - Создаем bean метод перехватчик изменений локали [*.localeChangeInterceptor()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/java/spring/oldboy/config/WebConfiguration.java#L73), который будет возвращать [LocaleChangeInterceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/LocaleChangeInterceptor.html).
Однако Spring не внедряет LocaleChangeInterceptor автоматически, мы должны это прописать в коде сами.
- Шаг 6. - Создаем метод [*.addInterceptors()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/java/spring/oldboy/config/WebConfiguration.java#L57) и уже через него добавляем наш перехватчик изменений локали в реестр 
перехватчиков - [InterceptorRegistry](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/InterceptorRegistry.html).
Только в этом случае, каждый запрос на смену локали (языка) будет обрабатываться приложением (сервером), еще раз см. [language_selection.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/resources/templates/fragments/language_selection.html).
- Шаг 7. - Теперь нам нужно хранить выбранную пользователем локаль (в healers, в cookies, т.е. в параметрах сессии). Для 
этого нам нужен еще один bean метод [*.localeResolver()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/src/main/java/spring/oldboy/config/WebConfiguration.java#L63), который будет возвращать реализацию [LocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleResolver.html), а их несколько, т.е. мы
можем выбрать, где хранить состояние локали (языка), например:
  - [AcceptHeaderLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AcceptHeaderLocaleResolver.html);
  - [CookieLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/CookieLocaleResolver.html);
  - [SessionLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/SessionLocaleResolver.html);

В cookies мы храним сессию, поэтому (на данном этапе), затолкаем туда и сведение о выбранном языке. Теперь можно проверить 
работу локализации в приложении - запускаем и смотрим что получилось см. [SelectLanguageMenu.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_23/DOC/SelectLanguageMenu.jpg).

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