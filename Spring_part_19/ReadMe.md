### Spring Boot lessons part 19 - REST

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_19/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
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

    implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'

________________________________________________________________________________________________________________________
#### REST - Введение.

В качестве введения в тему REST собрал, на скорую руку, материал ознакомительный и более специфичный на данном этапе:
- [DOC/SimpleAboutREST.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/SimpleAboutREST.txt) - Самая полезная из возможных статей для ознакомления с принципами REST;
- [DOC/ShortAboutREST.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/ShortAboutREST.txt) - Коротко о REST из WIKI и HABR-a;
- [DOC/RESTs_Mythology.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/RESTs_Mythology.txt) - Очень вдумчивая и подробная статья о REST, есть ссылка на GitHub с книгой по API;
- [DOC/REST_SERVICE_ON_JAVA.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/REST_SERVICE_ON_JAVA.txt) - Пример написания REST сервиса на Java, немного текста и кода;
- [DOC/REST_OVER_SPRING_PROBLEMS_URL.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/REST_OVER_SPRING_PROBLEMS_URL.txt) - Рассмотрена проблематика отображения URL в Spring REST контроллерах;
- [DOC/ImportantAspectsOfRESTfulAPI.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/ImportantAspectsOfRESTfulAPI.txt) - Рассмотрена методология и идеология создания REST сервисов;
- [DOC/RPCvsRESTvsMQ](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_19/DOC/RPCvsRESTvsMQ);
- [DOC/REST_vs_SOAP](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_19/DOC/REST_vs_SOAP);
- [DOC/REST_API](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_19/DOC/REST_API) - Обширный переводной материал с примерами, рассматриваются: REST, SWAGGER, HATEOAS и т.д.;
- [DOC/JAX-RS](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_19/DOC/JAX-RS);
- [DOC/Java_Kotlin_RestController](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_19/DOC/Java_Kotlin_RestController) - Рассмотрен пример взаимодействия Lombok-a, Spring-a и Kotlin при написании REST 
контроллеров;

Ссылки на исходные материалы содержаться в статьях, можно изучить познавательные комментарии и полезные ссылки не 
указанные по ходу изложения.

________________________________________________________________________________________________________________________
#### Lesson 95 - Практика ч.1 - первый REST контроллер и его простой метод.

Наше приложение написанное по шагам в прошлых уроках имеет некий недостаток. Если изучить документацию по принципам REST,
приведенную выше, и заглянуть в наш контроллер - [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/controller/UserController.java), мы увидим, что использовали только два вида запросов
Get и Post. И делали это для всех типов действий CRUD, что противоречит принципам REST. Все взаимодействие нашего 
приложения с внешним миром происходило по HTTP протоколу, и это хорошо, но возвращались на все запросы HTML страницы с 
ответами (Response). 

Происходило следующее см. [DOC/OurPastApplication/OurAppRestrictions.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/OurPastApplication/OurAppRestrictions.jpg): 
- пользователь делал через браузер запрос;
- запрос подхватывал интегрированный (embedded) в приложение TomCat;
- сервер TomCat передавал запрос на DispatcherServlet;
- DispatcherServlet перенаправлял запрос на контроллеры;

И это работало, наше приложение - 'сервис' взаимодействовало с пользователем и только. Исходя из материалов по REST, 
предполагается, что наш сервис будет взаимодействовать не только с живым пользователем, но и с другими сервисами, а 
значит наше приложение-сервис должно уметь работать с HTTP запросами всех типов и возвращать информацию например в JSON,
XML и т.п. по 'желанию' стороннего сервиса см. потрясающую статью - [DOC/SimpleAboutREST.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/SimpleAboutREST.txt). 

Реализуем. Трансформация нашего приложения выглядит так, см. [DOC/OurPastApplication/ReconstructionAppToREST.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/OurPastApplication/ReconstructionAppToREST.jpg). Теперь 
наши контроллеры будут выстроены исходя из принципов REST API, но это те же HTTP запросы, и ответы будут обрабатываться 
на стороне пользователя, например, в браузере по средствам [JavaScript](https://ru.wikipedia.org/wiki/JavaScript). И тогда схема приложения будет см.
[DOC/OurPastApplication/REST_Interaction_Diagram.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/OurPastApplication/REST_Interaction_Diagram.jpg)

- Шаг. 1 - Мы явным образом должны отключить ModelAndView (установить в null), тогда резолверы занимающиеся отрисовкой
Model будут дезактивированы и мы сможем вернуть в методе данные 'так как они есть'. Это делается расстановкой аннотации
[@ResponseBody](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html) над методами (или классом).
- Шаг. 2 - Создаем папку [rest в папке http](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_19/src/main/java/spring/oldboy/http/rest) нашего приложения, туда мы поместим наш первый Rest контроллер.
- Шаг. 3 - Создаем наш первый [Rest контроллер на базе UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java), т.е. он будет реализовывать тоже CRUD функционал, но
с 'REST размахом'.
- Шаг. 4. - Реализуем простой метод [*.simpleStringResponse()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L50), в нем уже нет Model, а значит и загрузки атрибутов для 
ответа на запрос, в идеале - те данные которые возвращает метод будут возвращены пользователю, тут это будет обычный 
String. 

Запускаем наше приложение [SpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/SpringAppRunner.java) и в браузере вводим [http://localhost:8080/api/v1/users/string](http://localhost:8080/api/v1/users/string),
ответом будет простая строка - 'String response', Content type определен как text, см. 
[DOC/OurRestAppStepByStep/findAllStringResponse.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/OurRestAppStepByStep/findAllStringResponse.jpg) браузер сам преобразовал возвращенные данные в удобочитаемый вид,
и HTML код страницы прост:

    <html>
        <head></head>
        <body>String response</body>
    </html>

И так мы аннотировали метод [@ResponseBody](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html) и он возвращает String или обычный текст, что мы и получили. Если же мы 
захотим вернуть объект, то клиент запросивший данные скорее всего получит JSON. Проверим это.

- Шаг. 5 - Реализуем метод [*.findAll() возвращающий PagePaginationResponse<UserReadDto>](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L63), т.е. список User-ов, в метод 
передавать ничего не будем, т.е. и фильтрация и пагинация будут - 'by default'. Запускаем приложение и обращаемся к:
[http://localhost:8080/api/v1/users](http://localhost:8080/api/v1/users)

На экране мы видим JSON ответ см. [DOC/OurRestAppStepByStep/http_localhost_8080_api_v1_users.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/OurRestAppStepByStep/http_localhost_8080_api_v1_users.jpg), и конечно Content type:
JSON, что мы и видим на экране в HTML кодировке:   

    <html>
        <head><meta name="color-scheme" content="light dark"></head>
        <body><pre style="word-wrap: break-word; white-space: pre-wrap;">
        {"content":[{"id":1,"username":"ivanov_vanya@gmail.com","birthDate":"1990-01-10","firstname":"Ivan",
                            "lastname":"Ivanov","role":"USER","company":{"id":2,"name":"Meta"}},
                    
                    /* ... полный список записей из таблицы users БД в формате JSON ... */

                    {"id":9,"username":"kretyru@gmail.com","birthDate":"2013-07-25","firstname":"Lakdi",
                            "lastname":"Karib","role":"USER","company":{"id":1,"name":"Google"}}],
                    "metadata":{"page":0,"size":20,"totalElements":9}}
              </pre>
        </body>
    </html>
    
Естественно мы можем управлять Content-type и при запросе (request) и при ответе (response), для этого в наши аннотации
над методами, например, [@GetMapping мы передаем параметры, в нашем случае - produces = MediaType.APPLICATION_JSON_VALUE](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L62),
это вариант управления ответом (или produces), получаемые данные (или consumes).

См. док.:
- [Пакет org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html) ;


________________________________________________________________________________________________________________________
#### Lesson 96 - Практика ч.2 - расширенный REST контроллер и его CRUD методы.

Переносим оставшиеся в [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/controller/UserController.java) методы в [UserRestController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java) и преобразуем в полноценные REST методы.

- Шаг. 1 - При копировании мы могли сразу опустить (или удалить после вставки) метод [*.registration()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/controller/UserController.java#L173), т.к. основная 
цель REST контроллера обмен данными между сервисами. Для регистрации user-ов у нас есть обычные контроллеры, они никуда 
не делись.
- Шаг. 2 - Корректируем метод [*.findById()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L73), удаляем модель, лишние аддеры в модель, наш метод будет возвращать 
[UserReadDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/dto/UserReadDto.java) или в случае отсутствия записи с предложенной ID просто вернем 404 статус (без изысков).
- Шаг. 3 - Метод [*.create()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L87) возвращает просто созданную в БД запись или UserReadDto. Из метода полностью удалям 
BindingResult и RedirectAttributes (мы никуда не перенаправляем ответ, а возвращаем данные при удачном их создании).

Особенности метода в том, что данные в него придут НЕ с внешней формы, а в теле запроса с внешнего сервиса в формате JSON.
Для этого в аннотацию [@PostMapping метода передаем параметр - consumes = MediaType.APPLICATION_JSON_VALUE](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L79), т.е. явно 
указываем, что 'ожидаем запрос с JSON в теле'. Параметр самого метода также аннотируем, как [@RequestBody](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestBody.html).

- Шаг. 4 - Корректируем метод [*.update()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L94), теперь он как и положено аннотирован [@PutMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PutMapping.html) см. 
[DOC/REST_API/6_Rest_API_Best_Practices.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/REST_API/6_Rest_API_Best_Practices.txt) или [DOC/ShortAboutREST.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/ShortAboutREST.txt). Аргумент метода user получил аннотацию 
[@RequestBody](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L96), как и в предыдущем методе, т.е. запрос на обновление будет приходить в теле запроса в JSON формате с 
какого либо внешнего сервиса.

Тут мы снова никуда не перенаправляем нашего пользователя (или сервис обратившийся к нашему приложению), а возвращаем 
данные 'как есть' в формате [UserReadDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/dto/UserReadDto.java). Если запрос нужно как-то преобразовать в некий удобочитаемый формат, то это
происходит на стороне клиента (т.е. того кто сделал запрос)

- Шаг. 5 - Изменяем метод (пишем с нуля, кому как нравится) [*.delete()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L104). Он получает, как и положено аннотацию 
[@DeleteMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/DeleteMapping.html) в параметры аннотации идет ID удаляемой записи. В случае удачной операции мы ничего не возвращаем,
поэтому метод становится void и получает аннотацию [@ResponseStatus(HttpStatus.NO_CONTENT)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L103). Естественно если запрошенного 
ID в базе не существует мы возвращаем NOT_FOUND или 404 статус. 

Мы явно не аннотировали наши методы, как [@ResponseBody](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html), сам класс не пометили как [@Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html). Зато мы применили 
аннотацию [@RestController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html) объединяющую в себе эти две аннотации важные для нас ([можно глянуть внутреннюю структуру
этой аннотации для наглядности](https://github.com/spring-projects/spring-framework/blob/main/spring-web/src/main/java/org/springframework/web/bind/annotation/RestController.java)).

________________________________________________________________________________________________________________________
#### Lesson 97 - Практика ч.3 - обработчик ошибок для REST контроллеров.

Мы уже писали обработчик ошибок для наших обычных контроллеров [ControllerExceptionHandler.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/handler/ControllerExceptionHandler.java), немного изменим его,
добавив параметр в аннотацию [@ControllerAdvice(basePackages = "spring.oldboy.http.controller")](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/handler/ControllerExceptionHandler.java#L18).

Шаг. 1 - Создадим обработчик ошибок нашего REST контроллера. В папку spring/oldboy/http/handler добавим наш обработчик
ошибок REST контролера - [RestControllerExceptionHandler.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/handler/RestControllerExceptionHandler.java).

Особенности данного обработчика это аннотация [@RestControllerAdvice](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestControllerAdvice.html), тут мы явно видим намек на то, что идет обработка
REST методов, в параметрах явно указываем связь с папкой 'rest' - [basePackages = "spring.oldboy.http.rest"](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/handler/RestControllerExceptionHandler.java#L6)

См. док. (код):
- [Пакет org.springframework.web.bind.annotation (doc)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html) ;
- [Пакте org.springframework.web.bind.annotation (на GitHub)](https://github.com/spring-projects/spring-framework/tree/main/spring-web/src/main/java/org/springframework/web/bind/annotation) ;

________________________________________________________________________________________________________________________
#### Lesson 98 - Ручное тестирование REST методов, SWAGGER API DOCs.

При работе с ранними версиями Spring применялись другие библиотеки и прописывались другие зависимости, например:

    implementation 'io.springfox:springfox-swagger-ui:3.0.0'

или 

    implementation "org.springdoc:springdoc-openapi-ui:1.7.0"

Изучая примеры 2-х летней давности понял, что с текущей версией Spring Boot, при подключении их, могут возникнуть 
определенные затруднения, изучив вопрос на stackoverflow понял, как новичку, эти горы мне не взять. Как я понял, при 
наличии Spring Security внедрение Swagger-a будет такой же веселой, но это позже. Поэтому пока, подключим в Spring 
зависимость для Swagger немного по-другому ([build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/build.gradle)):

    implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:${versions.springdoc}"

Пропишем версию библиотеки в ([version.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/version.gradle)):

    ext {
        versions = [
            'testcontainers': '1.17.6',
            'postgres': '42.5.4',
            'springdoc': '2.3.0'
        ]
    }

Судя по подключенным зависимостям мы подключили те же библиотеки (или очень похожие), что были бы в первых двух случаях.

Основное назначение [Swagger](https://swagger.io/) это автоматическая генерация документации нашего Rest API. После подключения Swagger-а легко 
может предоставить пользователю схему приложения: методы, конечные точки, сущности и т.д. все, что касается нашего 
приложения. Причем, при внесении нами изменений в структуру методов и т.п. нашего приложения Swagger может 'на лету' 
дополнить/изменить документацию.

Так же, Swagger имеет свой пользовательский интерфейс - UI, который позволяем нам взаимодействовать с нашим приложением
посредствам его REST методов. Это позволяет, в нашем 'web-сервисе', провести грубое ручное тестирование всех наших 
методов.

Особенность подключенного Swagger-a в том, что он реализован, как spring boot starter. Это позволяем управлять 
настройками Swagger-а через наш [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/application.yml) используя префикс "springdoc".

Для получения описание нашего приложения Swagger-ом (наше приложение должно быть запущено!), нам нужно обратиться по - [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs). Тут мы 
получим JSON одной строкой, скопируем в созданный нами файл - [resources/test.json](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/test.json) (применим к файлу Ctrl+Alt+Shift+L) и 
получим удобочитаемый JSON файл. К интерфейсу Swagger-a обратимся по - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Теперь мы имеем наглядную схему API нашего приложения [resources/test.json](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/test.json), подробно описаны методы, их входные и 
выходные параметры, наша модель и т.д. В web-интерфейсе нам доступны методы приложения и мы можем делать те запросы для
которых они были написаны. Т.е. мы можем попробовать в действии запросы GET, PUT, DELETE, POST и т.д. При этом, можем 
передавать и json формат в тех запросах, где это нужно и сразу видеть результаты, и Request URL и Response Body и код 
ответа от сервера и Response Headers. Мы так же можем специально передавать ошибки и видеть результаты.

________________________________________________________________________________________________________________________
#### Lesson 99 - Upload-image - 'загрузка картинок' в таблицу users БД.

Особенность нашего (и не только) REST контроллера в том, что он возвращает данные 'как есть', это удобно если мы 
пытаемся загрузить на сервер или считать с сервера 'не текстовые данные' - фото, видео, и т.д. Реализуем на наших
*.HTML страницах данный функционал:
- сначала загрузим аватарку для user-a;
- затем отобразим ее на странице user-a;

- Шаг. 1 - Создадим в таблицах users и users_aud поля для ссылок на загруженные картинки. Для этого мы создаем файл - 
[resources/db/changelog/db.changelog-3.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/db/changelog/db.changelog-3.0.sql) в котором прописываем скрипты для изменения таблиц. Мы не храним сами 
картинки в БД, а только ссылки на них. Реальные картинки положим в корень нашего приложения, а в сети они могут 
храниться, как на площадях хостинг провайдера, так и в облаке.
- Шаг. 2 - Добавляем наш новый [resources/db/changelog/db.changelog-3.0.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/db/changelog/db.changelog-3.0.sql) в [db.changelog-master.yaml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/db/changelog/db.changelog-master.yaml). Запускаем 
приложение и проверяем, внедрились ли наши изменения в таблицы БД:

      INFO 9848 --- [main] liquibase.database    : Set default schema name to public
      INFO 9848 --- [main] liquibase.changelog   : Reading from public.databasechangelog
      INFO 9848 --- [main] liquibase.lockservice : Successfully acquired change log lock
      INFO 9848 --- [main] liquibase.command     : Using deploymentId: 9106388316
      INFO 9848 --- [main] liquibase.changelog   : Reading from public.databasechangelog
                                                   Running Changeset: db/changelog/db.changelog-3.0.sql::1::oldboy
      INFO 9848 --- [main] liquibase.changelog   : Custom SQL executed
      INFO 9848 --- [main] liquibase.changelog   : ChangeSet db/changelog/db.changelog-3.0.sql::1::oldboy 
                                                   ran successfully in 18ms
                                                   Running Changeset: db/changelog/db.changelog-3.0.sql::2::oldboy
      INFO 9848 --- [main] liquibase.changelog   : Custom SQL executed
      INFO 9848 --- [main] liquibase.changelog   : ChangeSet db/changelog/db.changelog-3.0.sql::2::oldboy 
                                                   ran successfully in 9ms
      INFO 9848 --- [main] liquibase.util        : UPDATE SUMMARY
      INFO 9848 --- [main] liquibase.util        : Run:                          2
      INFO 9848 --- [main] liquibase.util        : Previously run:              12
      INFO 9848 --- [main] liquibase.util        : Filtered out:                 0
      INFO 9848 --- [main] liquibase.util        : -------------------------------
      INFO 9848 --- [main] liquibase.util        : Total change sets:           14
      INFO 9848 --- [main] liquibase.util        : Update summary generated
      INFO 9848 --- [main] liquibase.command     : Update command completed successfully.
                                                   Liquibase: Update has been successful. Rows affected: 2
      INFO 9848 --- [main] liquibase.lockservice : Successfully released change log lock
      INFO 9848 --- [main] liquibase.command     : Command execution complete

- Шаг. 3 - Создадим сервис для загрузки-сохранения картинок на жесткий диск нашего компьютера см. комментарии в 
[spring/oldboy/service/ImageService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/service/ImageService.java)
- Шаг. 4 - В форму регистрации - [resources/templates/user/registration.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/templates/user/registration.html), добавим [кнопку загрузить аватарку](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/templates/user/registration.html#L31) и 
поменяем атрибут в [enctype="multipart/form-data"](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/templates/user/registration.html#L16).
- Шаг. 5 - В форму отображения user-ов так же добавляем возможность отображать и изменять аватар, это файл - 
[resources/templates/user/user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/templates/user/user.html). В нем нужно провести те же изменения, что и на шаге 4, т.е. добавить атрибут
[enctype="multipart/form-data"](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/templates/user/user.html#L10)
- Шаг. 6 - В нашем обычном контроллере [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/controller/UserController.java) в методах [*.create()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/controller/UserController.java#L215) и [*.update()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/controller/UserController.java#L240) мы очень удачно используем 
один и тот же DTO - [UserCreateEditDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/dto/UserCreateEditDto.java), который мы немного подправим. Для загрузки и отображения img - аватарки, 
нам нужно еще поле. В [org.springframework.web.multipart](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/package-summary.html) есть специальный класс реализующий [MultipartFile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html), его и добавляем. Имя поля 'image' в 
UserCreateEditDto:

      @Value
      @FieldNameConstants
      @UserInfo(groups = CreateAction.class)
      public class UserCreateEditDto {
      
          /* ... другие поля/other fields ... */
          
          MultipartFile image;
      }

Должно совпадать с именем переменной 'name' в теге 'input' формы:

      <label for="image">Image:
          <input id="image" type="file" name="image">
      </label><br>

- Шаг. 8 - Добавим в нашу сущность [User](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/database/entity/User.java) - поле [String image](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/database/entity/User.java#L68);
- Шаг. 9 - Необходимо отредактировать наш преобразователь сущностей или маппер - [UserCreateEditMapper.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/mapper/UserCreateEditMapper.java). Поскольку у
нас, в БД, могут быть записи без аватарок, то желательно это учесть, как в самом методе, так и в формах. Нам понадобится
'Кот Шредингера' - Optional, см. код и комментарии [UserCreateEditMapper](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/mapper/UserCreateEditMapper.java).
- Шаг. 10 - Редактируем методы на уровне сервисов, чтобы иметь возможность загрузить картинку-аватарку. В 
классе [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/service/UserService.java) корректируем методы [*.create()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/service/UserService.java#L125) и [*.update()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/service/UserService.java#L150) см. комментарии в коде методов.

Запускаем, проверяем.

См. док.:
- [Пакет org.springframework.web.multipart](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/package-summary.html) ;

________________________________________________________________________________________________________________________
#### Lesson 100 - Get-image - отображение картинок в 'профиле user-ов'.

В предыдущем примере мы научились 'загружать файл-картинку' в таблицу users нашей БД, конечно только ссылку на нее. 
Теперь нам хочется отображать картинку-аватар в 'профиле пользователя', т.е. на странице отображения данных о нем.

- Шаг. 1 - Начнем с [ImageService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/service/ImageService.java), реализуем метод [*.getAvatar()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/service/ImageService.java#L62) для чтения (получения) уже загруженной (установленной) 
картинки у user-a.

Мы помним, что при получении HTML страницы происходит не один запрос, а такое их количество, которое позволяет загрузить
все содержимое страницы, т.е. ссылки на картинки, файлы css и т.п. Значит на слое сервисов нам нужен метод позволяющий 
извлечь картинку из БД. У нас в записи пользователя хранится только название картинки, значит нужно получить ее.

- Шаг. 2 - В классе [UserService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/service/UserService.java) добавляем метод извлекающий картинку из БД - [*.findAvatar()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/service/UserService.java#L207). К этому методу мы будем 
обращаться с уровня контроллеров, но на этот раз из [UserRestController-а](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java). 
- Шаг. 3 - В [UserReadDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/dto/UserReadDto.java) добавим поле [String image](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/dto/UserReadDto.java#L15), чтобы можно было использовать его в HTML форме.
- Шаг. 4 - В [UserReadMapper](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/mapper/UserReadMapper.java) добавим [object.getImage()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/mapper/UserReadMapper.java#L36), в метод [*.map()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/mapper/UserReadMapper.java#L20).
- Шаг. 5 - В REST контроллере [UserRestController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java) создаем метод для поиска аватарок - [*.findAvatar()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L111) с окончательным 
endpoint-ом: /api/v1/users/[{id}/avatar](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L110), см. аннотации над классом [@RequestMapping("/api/v1/users")](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java#L37) и методом 
@GetMapping(value = "/{id}/avatar", . . .). К нему мы и будем обращаться когда захотим получить картинку - аватарку.
- Шаг. 6 - На странице отображения [resources/templates/user/user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/templates/user/user.html) добавляем [блок с отображением картинки](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/resources/templates/user/user.html#L33):

      <div th:if="${user.image}">
          <img th:src="@{/api/v1/users/{userId}/avatar(userId=${user.id})}" alt="User image">
      </div>

Теперь, в случае, если user имеет картинку она будет динамически отображаться на его странице. При этом запрос на поиск
аватарки пойдет не в UserController, а в [UserRestController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestController.java), т.к. запрос из блока картинки идет на адрес (endpoint) - 
/api/v1/users/{userId}/avatar. Если же у user-a вовсе нет картинки, то блок с аватаркой не активируется, th:if - false.  

Запускаем, проверяем.

________________________________________________________________________________________________________________________
#### Lesson 101 - Использование ResponseEntity в методах контроллеров.

Для демонстрации кода с методами возвращающими [ResponseEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html) создадим отдельный контроллер - [UserRestControllerV2](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestControllerV2.java), в
котором реализуем только 4-и метода с использованием [ResponseEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)

В качестве ознакомительной документации см. [DOC/ResponseEntity](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_19/DOC/ResponseEntity) :
- Достоинства и недостатки ResponseEntity - [DOC/ResponseEntity/ResponseEntityExploring.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/ResponseEntity/ResponseEntityExploring.txt) ;
- Использование Spring ResponseEntity для управления HTTP-ответом - [DOC/ResponseEntity/ResponseEntityExample.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/ResponseEntity/ResponseEntityExample.txt) ;
- Описание класса ResponseEntity<T> (офф. док.) - [DOC/ResponseEntity/ResponseEntityClass.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/ResponseEntity/ResponseEntityClass.txt) ;
- Интерфейс ResponseEntity.HeadersBuilder (офф. док.) - [DOC/ResponseEntity/ResponseEntity.HeadersBuilder.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/ResponseEntity/ResponseEntity.HeadersBuilder.txt) ;
- Интерфейс ResponseEntity.BodyBuilder (офф. док.) - [DOC/ResponseEntity/ResponseEntity.BodyBuilder.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/DOC/ResponseEntity/ResponseEntity.BodyBuilder.txt) ;

Проверить работоспособность методов в [UserRestControllerV2](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_19/src/main/java/spring/oldboy/http/rest/UserRestControllerV2.java) можно, как через Swagger, так и через HTTP запрос (для POST 
и GET методов) см. комментарии к методам в самом классе.

См. (при запущенном приложении-сервисе): [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

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