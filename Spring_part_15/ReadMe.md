### Spring Boot lessons part 15 - Web Starter - Part 1

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку (первые 6-ть шагов из предыдущих частей, некоторые пропущены):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/build.gradle) добавим необходимые plugin-ы: 

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

________________________________________________________________________________________________________________________
#### Lesson 69 - Web-starter (теория)

#### Java EE
Немного истории. Вспомним структуру Web-приложения из раздела [HTTP_Servlets_Java_EE](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE) схематично это выглядело так см. [MVC_Chart_with_comment.jpeg](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/MVC_Chart_with_comment.jpeg), 
как работает эта концепция хорошо описано в статье [MVC_Concept.txt](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/MVC%20Concept.txt).
Структура web-приложения с применением сервера TomCat выглядела следующим образом см. [Web_app_with_servlets_n_tomcat.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Web_app_with_servlets_n_tomcat.jpg),
т.е. все наше приложения упаковывалось в root_name_app.war (war-архив) с подобными архивами очень хорошо работает TomCat,
а сама внутренняя структура архива проста см.:

    /index.html
    /guestbook.jsp
    /images/logo.png
    /WEB-INF/web.xml
    /WEB-INF/classes/org/wikipedia/Util.class
    /WEB-INF/classes/org/wikipedia/MainServlet.class
    /WEB-INF/lib/util.jar
    /META-INF/MANIFEST.MF

Обратите внимание, что в каталоге «WEB-INF» находится так называемый дескриптор развёртывания (Deployment Descriptor) 
по имени «web.xml», определяющий все сервлеты и другие свойства Web-приложения. Если приложение содержит только JSP-файлы, 
этот файл 'не строго' обязателен. 

После создания war-архива он перемещался в директорию webapps сервера TomCat. В данном случае web-сервер TomCat являлся 
основным (запускаемым) JAVA приложением см. [Main_web_app_with_tomcat.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Main_web_app_with_tomcat.jpg). Наше приложение, в свою очередь, 
распаковывалось из папки webapps и мы могли обращаться к нему, из вне, по заранее известному web-адресу, например: 
https://my_web_app.com:8080/, т.е. адрес_хоста:порт_доступа. Но из приведенной структуры [Main_web_app_with_tomcat.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Main_web_app_with_tomcat.jpg)
видно, что TomCat, в любом случае, являлся основным приложением в котором мы запускаем метод MAIN нашего web-приложения.

#### Spring Boot Web
Поскольку мы изучаем работу со Spring-ом, и в частности, со Spring Boot посмотрим как все описанное выше реализовано в 
этом фреймворке. Начнем со схемы паттерна MVC см. [Spring_MVC.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Spring_MVC.jpg), в которой реализован front controller и все общение
пользователя и приложения происходит через единственный servlet - [Dispatcher Servlet](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC/SpringWebServlet/DispatcherServlet). Т.е. получая запрос от пользователя
через web-средства взаимодействия (формы, прямые запросы и т.п.) Dispatcher Servlet будет перенаправлять эти запросы 
конкретному контроллеру (controller), которые их обрабатывают (т.е. наша задача как разработчика, в частности, будет в 
написании этих контроллеров).

Основная особенность в том, что в Spring Boot приложении TomCat идет как внешняя зависимость и при сборке нашего 
web-приложения в JAR архив его встраиваемая (неполная) версия интегрируется внутрь сборки см. [Spring_boot_web_app.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Spring_boot_web_app.jpg).

Из плюсов такой технологии:
- не надо отдельно устанавливать сервер TomCat;
- не надо отдельно запускать TomCat для запуска приложения;
- не надо отдельно монтировать war-архив и интегрировать его с TomCat;
- мы собираем наше Spring Boot web-приложение в JAR и оно готово к запуску;

'Подрезанная' версия или встроенная версия TomCat-a содержит: сервлет-контейнер Catalina и HTTP коннектор Coyote, см. 
[Embedded_tomcat.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Embedded_tomcat.jpg) и [WebServer.txt](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/HttpServlets/DOC/WebServer.txt). Если нам понадобится обработчик JSP страниц мы можем отдельно подключить Jasper.
Однако при текущем уровне развития Java template engine мы скорее всего воспользуемся не Jasper-ом, а Thymeleaf.

Подключим Web Starter:

    implementation 'org.springframework.boot:spring-boot-starter-web'

После подключения зависимости мы можем просто запустить наш [SpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/SpringAppRunner.java) и посмотреть, что отобразится в консоли.
Мы видим, что приложение запустилось и продолжает работать, мы видим запущенный TomCat на порту 8080, см. [SpringWebAppWorking.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebAppWorking.jpg).

И вот они особенности о которых говорилось ранее:
1. Как только мы подключили Web-starter и запустили наше приложение, запустился отдельный не daemon tread см. [TomcatWebServerThread.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/TomcatWebServerThread.jpg),
чтобы наше приложение продолжало работать и не останавливалось (это как ни как web-приложение).
2. Самое главное - это простота первоначальной настройки и реализации web-приложения (мы не ставили TomCat, не создавали war-архив и т.п.).

Все это стало возможным за счет авто-конфигурации, в данном случае за счет класса [WebMvcAutoConfiguration](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration.html) из пакета [org.springframework.boot.autoconfigure.web.servlet](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/web/servlet/package-summary.html)
Если заглянуть в его код, то становится примерно понятно, что от чего зависит и какие имеет связи:
    
    @AutoConfiguration(after = { DispatcherServletAutoConfiguration.class, 
                                 TaskExecutionAutoConfiguration.class,
                                 ValidationAutoConfiguration.class })
    @ConditionalOnWebApplication(type = Type.SERVLET)
    @ConditionalOnClass({ Servlet.class, 
                          DispatcherServlet.class, 
                          WebMvcConfigurer.class })
    @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
    @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
    @ImportRuntimeHints(WebResourcesRuntimeHints.class)
    public class WebMvcAutoConfiguration {
     /* Code .... */ 
    }

При необходимости мы можем настраивать свойства и параметры конфигурации нашего приложения (сервер и его свойства и т.п.).

См. док.:
- [Пакет org.springframework.boot.autoconfigure.web.servlet](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/web/servlet/package-summary.html) ;
- [Spring Boot Web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html) ;
- [Spring Boot Reference Documentation Web](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web) ;

________________________________________________________________________________________________________________________
#### Lesson 70 - [Dispatcher servlet](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html) (теория)

Жизненный цикл работы [Dispatcher Servlet-a](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html) кратко отображен на рис. [DispatcherServletLifeCycle.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/DispatcherServlet/DispatcherServletLifeCycle.jpg):
- Шаг 1. - Приложение получает запрос от пользователя по HTTP (request);
- Шаг 2. - Запрос принимает Coyote (HTTP Connector);
- Шаг 3. - Coyote преобразует запрос в соответствующий класс (coyote.Request) и передает его в сервлет контейнер;
- Шаг 4. - coyote.Request запрос попадает в Catalina (Servlet Container), и если при изучении HTTP Servlet-ов мы видели, 
что полученный запрос перенаправлялся на соответствующий сервлет, то в случае Spring Web Starter-a у нас один Dispatcher
Servlet, который проинициализируется единожды. Но нам все еще нужно перенаправить полученный запрос на соответствующий 
сервлет, т.е. сформировать некий ответ (некую реакцию) на полученный запрос. А поскольку сервлет у нас один, то всей 
работой по обработке и перенаправлению запросов занимается именно он и если взглянуть на [Spring_MVC.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Spring_MVC.jpg), то видно, 
что любой запрос через Dispatcher Servlet перенаправляется на соответствующий контроллер (Controller). 

Для инициализации этого сервлета будет сделано следующее (см. [DispatcherServletLifeCycle.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/DispatcherServlet/DispatcherServletLifeCycle.jpg)):
  - Первое: загружаем класс сервлета в память JVM (Load Servlet Class), далее, через Reflection API создается экземпляр 
сервлета (new Servlet Instance), далее, происходит инициализация или вызов метода *.init() (invoke Servlet.init()).
  - Второе: после инициализации сервлет может принимать и обрабатывать запросы (invoke Servlet.service(req,resp)), т.е. 
все запросы проходят через метод *.service(req,resp), и до тех, пор пока приложение работает запросы будут обрабатываться;
  - Третье: в момент завершения нашего web-приложения вызывается 'уничтожающий' метод (invoke Servlet.destroy());

Отсюда видно, что вся логика по обработке запросов будет осуществляться в [Dispatcher Servlet-e](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) в методе *.service() вплоть 
до остановки приложения. Звучит 'просто', а выглядит 'просто кошмар' см. [DispatcherServletWorkingScheme.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/DispatcherServlet/DispatcherServletWorkingScheme.jpg). 

Вот, что мы имеем при работе [Dispatcher Servlet-a](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html):
- Шаг 1. - при получении конкретного URL запроса мы должны определить соответствующий (этому URL-у) обработчик (handler) 
запроса (если [раньше см. Java EE](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE) при получении запроса 'мы определяли'
какой сервлет займется обработкой оного, то теперь у нас только Dispatcher Servlet).
- Шаг 2. - после определения обработчика запроса (handler-a), в работу включается конкретный контроллер, а точнее его 
метод, который и будет обрабатывать полученный запрос. Handler в свою очередь состоит из Controller-a и перехватчиков 
(Interceptor).

Стоит отметить, что Controller сложнее Servlet-a (только один метод doGet, doPost и т.д.), т.к. может включать в себя 
в отличие от Servlet-a множество get и post методов для обработки запросов. Как уже отмечалось выше, обработчик (handler) 
запроса, так же включает в себя Interceptor-ы - это аналоги фильтров, которые срабатывают перед и после обращения к 
методам контроллера (controller) (см. раздел по сервлет фильтрам: 
[MVCPracticeAdvanced - часть 3](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/tree/master/MVCPracticeAdvanced), 
[WebFilter.txt](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPracticeAdvanced/DOC/WebFilter.txt), 
[ServletFilter.jpg](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPracticeAdvanced/DOC/ServletFilter.jpg)). 
После того как был определен handler (обработчик запроса), далее, определен метод controller-a и набор interceptor-ов, 
нужно определить handler adapter. 

- Шаг 3. - определяем handler adapter - класс, включающий в себя (имеющий зависимости и ссылки) другие ключевые для работы 
приложения классы, в частности, ссылку на [WebApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/WebApplicationContext.html) (не классический ApplicationContext), так же [HandlerMethodArgumentResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodArgumentResolver.html), 
который отвечает за процесс внедрения необходимых методов в текущий контроллер (Controller), и наконец, [HandlerMethodReturnValueHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodReturnValueHandler.html),
который обрабатывает возвращенные контроллером значения. 

При работе с [HandlerMethodArgumentResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodArgumentResolver.html) мы можем получить для нашего Controller-a практически любые bean-ы и данные, 
а затем внедрить их в соответствующий (отвечающий за обработку конкретного запроса) метод контроллера. В свою очередь
[HandlerMethodReturnValueHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodReturnValueHandler.html) знает как обработать полученные из Controller-a данные, и самое главное, как вернуть эти
данные на полученный запрос, будь то, например, простые картинки, некие отображения (view), JSON объекты и т.д. 

Иными словами [HandlerAdaptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerAdapter.html) берет на себя сложную логику обработки запросов, чтобы она не была реализована в Dispatcher Servlet.
Когда на полученные запрос [HandlerAdaptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerAdapter.html) был определен, вызывается его метод *.handle(req, resp, handler).

- Шаг 4. - вызывается метод myAppHandlerAdaptor.handle(req, resp, handler), куда передаются request, response, и самое 
главное сам handler, который уже содержит нужные controller-ы и interceptor-ы, далее все происходит согласно схеме см. 
[Spring_MVC.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Spring_MVC.jpg)

- Шаг 5. - запрос, пройдя 'по всем уровням приложения' инициирует ответ в виде неких чистых данных [ModelAndView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ModelAndView.html), в случае
ошибки, при обработке данных, или невозможности вернуть корректный ответ, в работу включается [HandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExceptionResolver.html), 
который может вернуть, например, 400 или 500 ошибка см. [response_codes.txt](https://github.com/JcoderPaul/FTPClient-with-TestNG/blob/master/DOC/FTP_Handbook/FTP_response_codes.txt) и т.д.

Обычно на практике программисту приходится реализовывать Controller-ы, отображения View, обработчики ошибок и реже Interceptor-ы.

См. [раздел DOC](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC):
- [DispatcherServlet](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC/SpringWebServlet/DispatcherServlet) - описание центрального диспетчера для обработчиков/контроллеров HTTP-запросов; 
- [Handlers](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC/SpringWebServlet/Handlers) - описание основных обработчиков, интерфейсов и адаптеров;
- [Initializers](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC/SpringWebServlet/Initializers) - классы и интерфейсы для настройки контейнера сервлетов;
- [Resolvers](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC/SpringWebServlet/Resolvers) - описание основных resolver-ов;
- [Translators](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC/SpringWebServlet/Translators) - описание трансляторов;
- [ContextLoaderListener.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/ContextLoaderListener.txt) - описание слушателя загрузки, запуска и завершения работы корневого WebApplicationContext Spring.
- [ControllerInterface.txt ](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/ControllerInterface.txt) 
- [ConfigurationInterfaceAnnotation.txt ](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Configuration/ConfigurationInterfaceAnnotation.txt)

См. док.:
- [Пакет org.springframework.web.method.support](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/method/support/package-summary.html) ;
- [Пакет org.springframework.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/package-summary.html) ;
- [DispatcherServlet](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html) ;

________________________________________________________________________________________________________________________
#### Lesson 71 - [Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html)

Напишем первый наш контроллер (controller).

Пока мы не используем [Thymeleaf](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/DOC/ThymeleafManual) (Thymeleaf — это современный серверный механизм шаблонов Java как для веб-, так и для 
автономных сред.), посему нам нужно подключить обработчик JSP страниц, мы будем использовать их как элементы VIEW. 

Нам нужно настроить ViewResolver через свойства и, пока, на данном этапе, нужно добавить несколько JSP файлов, а значит 
придется подключить JASPER, ведь мы используем Embedded Tomcat (см. Embedded_tomcat.jpg)

- ШАГ 1. - Прописываем зависимость JASPER в build.gradle:
    
      implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'

Теперь настраиваем свойства нашего приложения в application.yml, а точнее его MVC раздел. Для того чтобы увидеть префикс
настроек, мы можем зайти в [WebMvcProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/web/servlet/WebMvcProperties.html) (где мы [можем найти массу свойств](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/servlet/WebMvcProperties.java) пригодных для настройки):

      @ConfigurationProperties(prefix = "spring.mvc")
      public class WebMvcProperties {
      ...
      }

Как [ранее мы могли увидеть](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/orm/jpa/JpaProperties.java), префикс при настройке JPA:

      @ConfigurationProperties(prefix = "spring.jpa"    )
      public class JpaProperties{
      ...
      }

Или [свойства нашего TomCat сервера](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/ServerProperties.java) с префиксом "server", где мы можем настроить порт, адрес сервера и т.д. (но делать 
пока этого не будем, пока порт нашего сервера 8080, что видно в консоли при запуске приложения):

      @ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
      public class ServerProperties {
      
      /* Server HTTP port */
      private Integer port;
  
      /* Network address to which the server should bind */
      private InetAddress address;

- ШАГ 2. - Добавляем настройки наших JSP будущих страниц отображения, в [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/resources/application.yml) это будет настройка нашего ViewResolver:

      spring:
        mvc:
        view:
          prefix: /WEB-INF/jsp/
          suffix: .jsp

В данном случае 'prefix' (у нас это /WEB-INF/jsp/) это путь или то, что будет перед названием нашей страницы отображения,
в 'suffix' (у нас будут JSP страницы в качестве отображающих ответ на запрос, поэтому и суффикс будет .jsp), т.е. наша 
страничка отображения будет иметь вид, например: /WEB-INF/jsp/... some dir .../example.jsp

- ШАГ 3. - Создаем папку ['webapp'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/src/main/webapp/WEB-INF/jsp) в корне папки 'main', данное название зарезервировано. В ней создаем уже знакомые нам
'/WEB-INF' и '/jsp', см. [HTTP_Servlets_Java_EE](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE), это префикс на 
шаге 2. И теперь создаем папку ['greeting'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/greeting), там мы расположим наши файлы приветствия и прощания.

- ШАГ 4. - Создаем файлы приветствия - [hello.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/greeting/hello.jsp) и прощания - [bye.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/greeting/bye.jsp). Теперь нам необходимо их вернуть на полученный 
запрос. В текущей ситуации мы должны создать слой контролеров, который будет обрабатывать полученные запросы и возвращать 
соответствующие JSP страницы.

- ШАГ 5. - Создаем слой контроллеров. Создаем папку ['http'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/src/main/java/spring/oldboy/http/), в которой будет храниться все относящееся к работе с 
WEB HTTP (контроллеры, фильтры, перехватчики). И в частности папка ['controller'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/src/main/java/spring/oldboy/http/controller) с нашим первым контроллером - 
[GreetingController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/GreetingController.java).

Данный класс необходимо пометить специальным образом, чтобы Spring воспринимал его как компонент связанный с рабочим 
процессом MVC. Для этого мы помечаем его аннотацией [@Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html) см. док.[ ControllerInterface.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/ControllerInterface.txt). Фактически это 
мета-информация, которая указываем на слой (в данном случае слой контроллеров см. [Spring_MVC.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Spring_MVC.jpg), как и [@Repository](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html), 
как [@Service](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Service.html)), он в свою очередь помечен, как [@Component](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html):

      @Target(ElementType.TYPE)
      @Retention(RetentionPolicy.RUNTIME)
      @Documented
      @Component
      public @interface Controller {
      
          /**
            * Значение может указывать на предложенное логическое имя компонента, для 
            * преобразования в bean-компонент Spring в случае автоопределения компонента.
            * @return предложенное имя компонента, если оно есть (или пустая строка в 
            * противном случае)
            */
          /**
          @AliasFor(annotation = Component.class)
          String value() default "";
      
      }

Именно эта аннотация после сканирования Spring-ом инициирует процесс создания Spring bean-ов.

- ШАГ 6. - Добавляем в [GreetingController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/GreetingController.java) методы, которые позволят обработать HTTP запрос (request) и вернуть ответ 
(response). 

Теперь вернемся к рис. [DispatcherServletWorkingScheme.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/DispatcherServlet/DispatcherServletWorkingScheme.jpg), мы видим, что когда в DispatcherServlet будет вызван метод 
*.handle(req, resp, handler), будет возвращен [ModelAndView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ModelAndView.html) объект, поэтому наши методы должны возвращать объекты этого 
типа. Т.е. это будет комплект из данных (Model) и (And) страницы отображения (View), у нас это *.jsp страницы. Хотя на 
данном этапе наши *.jsp страницы очень статичны (простой HTML) и не содержат динамических структур (пока, у нас нет 
динамических данных для подстановки в страницы отображения).

Поэтому пусть наши методы в [GreetingController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/GreetingController.java) вернут просто 'статику' или наши две *.jsp страницы с приветствием и
прощанием: public ModelAndView hello() и public ModelAndView bye().

- ШАГ 7. - Методы обработчики созданы, странички отображения созданы - теперь мы должны картировать (сопоставить) 
соответствующие запросы на методы обработчики этих запросов, наши *.hello() и *.bye(). Другими словами, мы должны 
указать 'URl path' см. [DispatcherServletWorkingScheme.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/DispatcherServlet/DispatcherServletWorkingScheme.jpg), чтобы определить, какой метод контролера будет обрабатывать 
полученный запрос. 

Но это уже следующий урок.

См. док.:
- [Пакет org.springframework.boot.autoconfigure.web.servlet](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/web/servlet/package-summary.html) ;
- [Spring-projects на GitHub](https://github.com/spring-projects) ; 
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html) ;

________________________________________________________________________________________________________________________
#### Lesson 72 - [RequestMapping](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html) (Картирование запросов - "сопоставление запросов")

И так, нам необходимо указать соответствие запросов и страниц обрабатывающих эти запросы, т.е ответы. Еще раз вспомним, 
как выглядят запрос (request) и ответ (response) в строке браузера, например, см. [DOC/URL_Request_Response.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/URL_Request_Response.jpg). Как вариант, 
пример расшифровки ответов (статус ответа) от FTP сервера рассмотрены тут: [FTP_response_codes.txt](https://github.com/JcoderPaul/FTPClient-with-TestNG/blob/master/DOC/FTP_Handbook/FTP_response_codes.txt),
т.е. чтобы получить соответствующие ответы от FTP сервера, как пример, мы должны сгенерировать некие GET, POST, PUT, 
DELETE и т.д. запросы.

В представленной схеме видно, что и request (запрос) и response (ответ) состоят из 3-х частей, в запросе мы имеем
структуру: стартовая строка "запроса" (URL запроса, где мы указываем адрес и параметры запроса, или HTTP метода GET
, PUT и т.д.), headers - метаинформация, дающая серверу инструкции о том, как обработать тело запроса, и тело сообщения 
или body (при этом тело запроса часто может быть пустым, т.к. параметры передаются в URL); в "ответе" структура та же, 
но, стартовая строка обычно не содержит URL, а содержит код ответа от сервера (или его статус, см. выше), так же есть 
headers и самое главное, в большинстве случаев, это body - тело ответа в виде HTML - подобной страницы.

URL состоит из 5-и основных компонентов см. [DOC/URL_Request_Response.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/URL_Request_Response.jpg): 
- protocol - протокол связи (http, https, ftp и т.д.);
- domain - домен к которому мы обращаем наш запрос (состоит из host-a и порта, например, www.mytest.ru:432) если 
обращение идет по стандартному протоколу, то обычно адрес порта опускают, предполагая, что используется стандартный порт
(для http - это 8080, для https - это 443, для ftp - это 21);
- path - или путь, по которому идет обращение к серверу, именно он указывает деспатчер-сервлету на какой контроллер 
необходимо картировать ('смапить', перенаправить) наш запрос. Т.е. это путь к ресурсу который нас на этом сервере 
интересует и мы хотим, например, его изменить, или получить данные и т.д.;  
- parameters - те самые параметры переданные после знака '?', которые передаются на сервер для конкретизации запроса, эти 
параметры (их может быть много) объединены знаком '&' и представлены в формате: ключ=значение;
- fragment - отделен от параметров знаком # (используется редко), но удобен при чтении большого документа, набора данных, 
и позволяет разбить страницу на части для удобства отображения информации и перенаправления к нужному фрагменту страницы.

Память освежили! 

Картируем наши запросы:

- ВАРИАНТ 1 - общая аннотация [@RequestMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html) см. [DOC/RequestAnnotation/RequestMappingAnnotation.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/RequestAnnotation/RequestMappingAnnotation.txt): 

Для этого мы наши методы в контроллере [GreetingController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/GreetingController.java): public ModelAndView hello() и public 
ModelAndView bye() аннотируем специальной аннотацией [@RequestMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html) с параметрами см. комментарии в файле контроллере.

Для проверки работоспособности нашего микро-приложения запускаем нашу базу данных если она установлена локально на 
машине, или DOCKER контейнер с базой (как вариант), а затем [SpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/SpringAppRunner.java) и в браузере обращаемся к нашему 
приложению по адресу локального хоста, например:
- [http://localhost:8080/hello](http://localhost:8080/hello) и видим ответ - "Hello World!";
- [http://localhost:8080/bye](http://localhost:8080/bye) и видим в браузере отклик сервера - "Bye World!";

Аннотация [@RequestMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html) слегка громоздка вместо нее можно использовать аннотацию [@GetMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html), т.е. в самой аннотации
мы сразу указали тип запроса GET. 

- ВАРИАНТ 2 - аннотация с указанием метода запроса, например, [@GetMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html): 

Для наглядной демонстрации работы данной аннотации создадим еще пару *.jsp файлов с префиксом 'another_', 
а также еще один контроллер [SecondGreetingController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/SecondGreetingController.java), где в соответствующих уже знакомых нам методах заменим 
аннотации на @GetMapping и пути запросов и ответов нашего spring-web приложения, см. файлы и комментарии в них. 

Теперь запускаем приложение и проверяем запросы:
- [http://localhost:8080/another_bye](http://localhost:8080/another_bye) и видим в ответ - "Bye World from another_bye.jsp!";
- [http://localhost:8080/another_hello](http://localhost:8080/another_hello) и получаем на экране - "Hello World from another_hello.jsp!" 

Т.е. в данном варианте мы применили специализированную аннотацию, несложно догадаться, что существуют и другие типы
аннотаций, для других вариантов запросов, например, [@PostMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PostMapping.html), [@PutMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PutMapping.html) и т.д. 

См. док.:
- [Пакет org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html) ;
- [Mapping Requests](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html) ;

________________________________________________________________________________________________________________________
#### Lesson 73 - Использование RequestMapping как общий префикс к запросу.

Если мы в наших контроллерах используем специализированные аннотации для указания типов обрабатываемых запросов, то
аннотация [@RequestMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html) может использоваться для определения общего префикса для всего набора некоторых запросов.

Для демонстрации создадим еще контроллер [ThirdGreetingController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/ThirdGreetingController.java). Этот класс мы аннотируем как 
@RequestMapping("/api/v1"), т.е. тут в параметрах мы передали префикс пути по которому необходимо обращаться к нашему
приложению и он общий для всех типов запросов этого конкретного контроллера. Далее для наглядности отображения 
возвращаемой страницы для каждого метода обработчика создадим *.jsp файлы ([hello_for_prefix_demo.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/greeting/hello_for_prefix_demo.jsp) и 
[bye_for_prefix_demo.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/greeting/bye_for_prefix_demo.jsp)).

И снова для проверки результатов запускаем наше spring-web приложение [SpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/SpringAppRunner.java) и делаем запрос в браузере:
- [http://localhost:8080/api/v1/prefix_hello](http://localhost:8080/api/v1/prefix_hello) и видим ответную страницу с текстом - "Hello World from Prefix demo!";
- [http://localhost:8080/api/v1/prefix_bye](http://localhost:8080/api/v1/prefix_bye) и в браузере отображается строка - "Bye World from Prefix demo!"

________________________________________________________________________________________________________________________
#### Lesson 74 - Извлечение parameters, headers и cookies из полученного запроса.

И так, запрос, который приходит к нашему серверу может содержать массу информации, как в строке самого URL, так и в теле
запроса в виде header-ов и cookies. Их (parameters, headers и cookies) мы должны уметь извлекать, хотя бы так же, как мы
это делали в уроках по [HttpServlets](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/tree/master/HttpServlets).

В данном случае мы будем использовать *.get... методы позволяющие извлекать нужные нам данные из запроса см. метод
public ModelAndView hello() из [GetParamController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/GetParamController.java) контроллера. В сам метод-обработчик передаются, специальным 
образом, аннотированные параметры [@RequestParam](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html), [@RequestHeader](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestHeader.html), [@CookieValue](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/CookieValue.html), [@PathVariable](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PathVariable.html) и т.д см. краткое описание
в [DOC/RequestAnnotation](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_15/DOC/RequestAnnotation).

Для проверки работы приложения его можно запустить в режиме отладки и в браузере сделать запрос, например,
[http://localhost:8080/api/v1/hello_and_get_param/100?age=24](http://localhost:8080/api/v1/hello_and_get_param/100?age=24), и при текущих настройках метода-обработчика: 

      @GetMapping("/hello_and_get_param/{id}")
      public ModelAndView hello(ModelAndView modelAndView, HttpServletRequest request,
                                @RequestParam ("age") Integer age,
                                @RequestHeader ("accept") String accept,
                                @CookieValue("JSESSIONID") String JSESSIONID,
                                @PathVariable("id") Integer id)

Запрос будет сопоставлен с [hello.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/greeting/hello.jsp). При этом, если имя переданного параметра и запрошенного совпадают, например,
@RequestParam ("age") Integer age, мы можем его опустить в параметрах аннотации и получим: @RequestParam () Integer age.

Структура запроса приведенная выше содержит:
- /hello_and_get_param/100 - путь 'hello_and_get_param' и pathVariable '/100';
- '?' - знак начала параметров;
- переданные в запросе параметры - 'age=24' (в формате ключ=значение);

См. док.:
- [Пакет org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html) ;
- [Handler Methods](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods.html) ;

________________________________________________________________________________________________________________________
#### Lesson 75 - Работа с Model и установка атрибутов.

У нас есть контроллеры (Controller), которые обрабатывают запрос от пользователя и перенаправляют его на нужную страницу 
отображения (View). У нас это пока *.jsp страницы и пока статические, в которые нужно добавить динамическую составляющую.
Фактически у нас 3-и уровня расположения атрибутов откуда мы их можем брать см. [3_Level_of_Attributes_and_Annotation.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/3_Level_of_Attributes_and_Annotation.jpg)
ту же схему мы видели при изучении Servlets см. [Attributes.jpg](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/Attributes.jpg), но в первой схеме выделены аннотации, которыми мы можем
воспользоваться при разработке Spring приложения.

У нас есть глобальные атрибуты (ассоциативный массив) на все приложение - это ServletContext, у нас есть атрибуты текущей
сессии, т.е. данные привязанные к конкретной HttpSession сессии, и есть атрибуты привязанные к запросу или к 
HttpServletRequest-у. В Spring-e для установки атрибутов на каждом конкретном уровне существуют свои аннотации.

Для демонстрации работы с аттрибутами создадим динамические *.jsp странички ([dynamic_hello.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/dynamic_view/dynamic_hello.jsp) и [dynamic_bye.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/dynamic_view/dynamic_bye.jsp)) и 
новый контроллер [DynamicDemoController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/DynamicDemoController.java). Так же добавим новую DTO - [UserReadDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/dto/UserReadDto.java).
 
У ModelAndView есть методы для добавления атрибутов, либо сразу ассоциативным массивом *.addAllObjects(), либо по 
штучно *.addObject(). С имитируем вход пользователя в систему добавив атрибут вручную, назовем его 'user':
- Задаем наш сессионный атрибут для всего контроллера при помощи аннотации @SessionAttributes({"user"}) см. [DynamicDemoController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/DynamicDemoController.java);
- В методе public ModelAndView hello() контроллера установим этот атрибут через *.addObject();
- Чтобы установить атрибут в сессию обратимся к приложению по адресу [http://localhost:8080/api/v1/dynamic_hello](http://localhost:8080/api/v1/dynamic_hello), мы получим
отображение в виде Hello Paul!;
- Чтобы проверить, что атрибут зафиксирован сессией обратимся к приложению по адресу [http://localhost:8080/api/v1/dynamic_bye](http://localhost:8080/api/v1/dynamic_bye),
в браузере мы увидим Bye Paul!

Так же мы можем запустить приложение в режиме отладки и следить за состоянием атрибутов из среды разработки.

________________________________________________________________________________________________________________________
#### Lesson 75 - Model и установка атрибутов через параметры в URL или теле сообщения (без обращения к ModelAndView).

Если обратить внимание на метод:

      @GetMapping("/another_bye")
      public ModelAndView bye() {
          ModelAndView modelAndView = new ModelAndView();
              modelAndView.setViewName("greeting/another_bye");
          return modelAndView;
      }

То можно заметить, что страница, которая вернется пользователю - статична, и оказывается этот метод можно упростить. Он
примет вид:

      @GetMapping("/another_bye")
      public String bye() {
          return "greeting/another_bye";
      }

Мы знаем что за работой нашего приложения приглядывает [DispatcherServlet](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html) и обслуживают всякого рода обработчики из 
[HandlerAdapter-а](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods.html), а точнее [ReturnValueHandler](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/return-types.html) см. [DOC/SpringWebServlet/DispatcherServlet/DispatcherServletWorkingScheme.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/SpringWebServlet/DispatcherServlet/DispatcherServletWorkingScheme.jpg).
Именно он при обработке метода видит, что возвращается не ModelAndView объект, а String, и значит этот объект String должен
быть обработан, как View с названием "greeting/another_bye". В итоге вернется статическая страница.

Мы помним, что можем передавать параметры запроса, как в URL, после знака '?', так и в форме или в теле сообщения. И Spring
отслеживает все переданные, тем или иным способом, параметры и картирует (Map-ит) их на соответствующие объекты. Чтобы 
проверить это, мы сделаем следующее:
- Создадим контроллер [LoginController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/LoginController.java) и добавим в него метод public String hi();
- Cоздадим [hi_there.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/user/hi_there.jsp) страничку в которой отобразим параметры переданные внутри метода и параметры переданные в URL запросе;
- Запустим приложение и обратимся к нему по адресу [http://localhost:8080/hi?id=10&username=Slava](http://localhost:8080/hi?id=10&username=Slava);

В ответ мы получим страницу с переданными параметрами. Причем если один из объектов мы именовали сами - 'user' 
(дали ему ключ), то второй 'userReadDto' Spring именовал сам, как и было сказано выше. Т.е. мы либо именуем наши ключи, 
которым будут соответствовать объекты, сами, либо Spring делает это за нас. Естественно мы можем задать имя ключа через
аннотацию и в параметрах передать имя - [@ModelAttribute](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ModelAttribute.html)("userReadDto"). 

Или было:

      @GetMapping("/hi")
      public String hi(Model model,
                       HttpServletRequest request,
                       UserReadDto userReadDto) {
      }

Стало:

      @GetMapping("/hi")
      public String hi(Model model,
                       HttpServletRequest request,
                       @ModelAttribute("userReadDto") UserReadDto userReadDto)

Аннотация может применяться как в параметрах метода, так же и над самим методом, когда мы хотим, чтобы данные 
возвращаемые методом использовались как атрибуты:

      @ModelAttribute("roles")
      public List<Role> roles() {
          return Arrays.asList(Role.values());
      }

В таком случае, если разместить данный метод в контроллере, например [GreetingController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/GreetingController.java), каждый раз при обращении 
к методу *.hello() или *.bye() (т.е. вызов приложения через браузер, как вариант) будет происходить обращение к методу
*.roles() и установка параметров 'roles'. И это будут атрибуты модели - Model.

И так, продолжим. Отправка параметров через поле или в теле (body) запроса:
- Создадим [login.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/user/login.jsp) с формой в которую внесем данные (они же передаваемые параметры), форма с этой страницы будет 
отправлять запрос на URL '/login' методом POST;
- В контроллере [LoginController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/LoginController.java) добавим методы public String loginPage() и public String login();
- Первый из них loginPage() аннотируется [@GetMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html), а вот второй login() уже, как [@PostMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PostMapping.html);
- Создадим [LoginDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/dto/LoginDto.java) поля которой 'username' и 'password', должны совпадать с именами полей формы [login.jsp](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/webapp/WEB-INF/jsp/user/login.jsp);

________________________________________________________________________________________________________________________
#### Lesson 76 - Варианты перенаправления запросов Forward, Include, Redirect.

Из курса по сервлетам [HTTP_Servlets_Java_EE](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE) мы помним, что вариантов перенаправления запроса как минимум 3-и 
см. рис. [DispatcherServlet.jpg](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/DispatcherServlet.jpg) все они работают через DispatcherServlet в Java EE, а уж в Spring-e тем более, т.к. он тут
один. Но наиболее универсальный из вариантов перенаправления запросов в HTTP - это 'redirect'.

В нашем приложении, когда мы обращаемся к форме и обрабатываем запрос методом:

      @PostMapping("/login")
      public String login(Model model, 
                          @ModelAttribute("login") LoginDto loginDto) {
          return "user/login";
      }

В строке return мы можем применить два ключевых слова: 

- forward - и тогда после него мы должны прописать полный путь к сервлету или *.jsp странице, т.к. у нас единственный 
сервлет, который обрабатывает любые запросы, то нам придется прописать полный путь на требуемую страницу, например так:
"forward:/WEB-INF/jsp/user/login.jsp". Тут, как только в возвращаемой строке возникает ключевое слово "forward:", Spring 
понимает, что не надо использовать ViewResolver, а нужно взять страницу отображения (View) как есть, т.е. адрес к *.jsp
странице;

- redirect - а вот тут нужно указать не страницу отображения View, а URL страницы на которую нужно перейти, например,
"redirect:https://yandex.com";

В качестве примера в [LoginController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/src/main/java/spring/oldboy/http/controller/LoginController.java) мы создали метод: 

      @GetMapping("/redirect_to_ya")
      public String testRedirect(Model model) {
          return "redirect:https://yandex.com";
      }

Который при обращении к странице - [http://localhost:8080/redirect_to_ya](http://localhost:8080/redirect_to_ya), перебрасывает пользователя на поисковую страницу
Яндекса. На рис. [DOC/Example_Redirect_to_Ya.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Example_Redirect_to_Ya.jpg) видно, что сначала идет обращение на [http://localhost:8080/redirect_to_ya](http://localhost:8080/redirect_to_ya),
далее выбрасывается код 302 и идет перенаправление на https://yandex.com/. Т.е. именно так, как это рассмотрено на 
[DispatcherServlet.jpg](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/DispatcherServlet.jpg) для схемы перенаправления 'redirect'.

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