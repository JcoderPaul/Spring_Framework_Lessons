### Spring Boot lessons part 25 - Custom Spring Boot Starter.

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;
- [Документация по Spring Boot (архив)](https://docs.spring.io/spring-boot/docs/) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку (подгрузим зависимости в [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/build.gradle)):

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
#### Custom Spring Boot Starter - Самописный стартер.

В качестве стартера мы возьмем и вынесем наш блок АОП с функционалом логирования:

- **Шаг 1.** - Создадим отдельный модуль Gradle - [myfirst-logging-spring-boot-starter](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter). У этого отдельного модуля свой [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/build.gradle) (со своими зависимостями):

        plugins {
            id 'java'
        }
        
        group 'spring.oldboy'
        version '1.0-SNAPSHOT'
        
        repositories {
            mavenCentral()
        }
        
        dependencies {
            /* Добавляем AOP зависимость поскольку мы ее используем */
            implementation 'org.springframework.boot:spring-boot-starter-aop:2.6.2'

            /* Добавляем JPA стартер т.к. мы используем транзакции и работу с БД */
            implementation 'org.springframework.boot:spring-boot-starter-data-jpa:2.6.2'

            /* Добавляем зависимости от Lombok т.к. мы используем его аннотации */
            compileOnly 'org.projectlombok:lombok:1.18.22'
            annotationProcessor 'org.projectlombok:lombok:1.18.22'
        
            /*
            Позволяет генерировать файл подсказок JSON для конфигурационых файлов,
            чтобы получлось сгенерировать качественные подсказки, нужно делать 
            качественные комментарии в формате Java Doc см. LoggingProperties.java
            */
            annotationProcessor "org.springframework.boot:spring-boot-configuration-processor:2.6.2"
        
            /* Добавляем тестовую зависимость, т.к. любой код (в т.ч. и starter) нужно тестировать */
            testImplementation 'org.springframework.boot:spring-boot-starter-test:2.6.2'
        }
        
        test {
            useJUnitPlatform()
        }

Либо мы можем использовать Spring Dependency Management, как и в основном проекте, так же см. описание варианта 
конфигурации в [DOC/HandMade_SB_Starter/BuildingSpringBootStarter.md](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/DOC/HandMade_SB_Starter/BuildingSpringBootStarter.md)

У нашего отдельного модуля свой [src](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter/src) пакет в который мы перенесем нашу [АОП логику](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/aop) из прошлого проекта, чтобы выделить в 
отдельный стартер.

- **Шаг 2.** - Создаем файл свойств (настроек стартера) см. подробнее статьи из [папки DOC](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/DOC). Выделяем отдельно [конфигурационную 
папку](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config) для нашего модуля, куда будут помещаться все файлы связанные с настройкой нашего starter-a: [java/spring/oldboy/logging/config](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config).
В этой папке создаем файл - [LoggingProperties.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingProperties.java) см. код и комментарии.

- **Шаг 3.** - Создадим файл авто-конфигурации для нашего Custom Starter-a, в той же папке config - [LoggingAutoConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingAutoConfiguration.java),
именно он будет запускаться первым для конфигурации всего стартера. Он сразу помечается аннотацией:

  - [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) -
Указывает, что класс объявляет один или несколько методов [@Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html) и может обрабатываться [контейнером Spring](https://docs.spring.io/spring-framework/reference/core/beans.html) для создания 
определений компонентов и запросов на обслуживание для этих компонентов во время выполнения; 

  - [EnableConfigurationProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/EnableConfigurationProperties.html) -
Включает поддержку аннотированных bean-компонентов [@ConfigurationProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/ConfigurationProperties.html). Bean @ConfigurationProperties можно 
зарегистрировать стандартным способом (например, с помощью методов @Bean) или, для удобства, указать непосредственно в 
этой аннотации см. [LoggingAutoConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingAutoConfiguration.java)

  - [@ConditionalOnClass](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnClass.html) -
@ConditionOn... условие, которое соответствует только тогда, когда указанные классы находятся в пути к классам. Значение 
класса можно безопасно указать в классах [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html), поскольку метаданные аннотации анализируются с помощью ASM перед
загрузкой класса. Если ссылку на класс нельзя использовать, можно использовать атрибут строки имени.
    
  - [@ConditionalOnProperty](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.html) (см. [на GitHub](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.java)) -
[@Conditional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Conditional.html), который проверяет, имеют ли указанные свойства определенное значение. По умолчанию свойства должны 
присутствовать в среде и не быть равными false. Атрибуты [havingValue()](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.html#havingValue()) и [matchIfMissing()](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.html#matchIfMissing()) допускают дальнейшую настройку.
Атрибут [havingValue()](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.java#L132) можно использовать для указания значения, которое должно иметь свойство. Если свойство вообще не 
содержится в среде, используется атрибут [matchIfMissing()](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.java#L139). По умолчанию отсутствующие атрибуты не совпадают. Это условие 
нельзя надежно использовать для сопоставления свойств коллекции. Для таких случаев лучше использовать собственное условие.

  В нашем случае, если в качестве параметра аннотации [@ConditionalOnClass()](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnClass.html) используется LoggingProperties.class, это 
значит, что некто пытается использовать наш starter для своих нужд.

  Так же мы через параметры преданные в эту аннотацию [@ConditionalOnProperty()](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.html) устанавливаем [поле 'enabled'](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingAutoConfiguration.java#L43) для 
[LoggingProperties.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingProperties.java) в - 'true', иначе наш стартер не будет запущен.

  Т.е. чтобы подключился [LoggingAutoConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingAutoConfiguration.java), необходимо соблюдение двух условий переданных в [@ConditionalOnClass ](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnClass.html)
и в [@ConditionalOnProperty](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.html).

- **Шаг 4.** - Нам необходимо как-то просканировать LoggingAutoConfiguration.java, чтобы увидеть аннотацию @Configuration (чтобы Spring
приложение увидело эту аннотацию и поместило класс и его bean-ы в контекст) см. [DOC/HandMade_SB_Starter/BuildingSpringBootStarter.md](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/DOC/HandMade_SB_Starter/BuildingSpringBootStarter.md)
Для этого мы в папке 'resources' создаем папку '[META-INF](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/resources/META-INF)' и добавляем в нее [spring.factories](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/resources/META-INF/spring.factories) (с содержимым):
      
      org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
      spring.oldboy.logging.config.LoggingAutoConfiguration
 
Мы указываем путь к нашей авто-конфигурации через специальный ключ см. выше. Именно так оформляются все стартеры, с 
указанием пути к авто-конфигурационному файлу. Всегда (конечно можно и через @Bean, но настройка будет громоздкой) 
необходимо указывать точку входа в авто-настройку starter-a.

- **Шаг 5.** - Создаем папку [spring/oldboy/logging/aop](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/aop) в разделе [myfirst-logging-spring-boot-starter](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter), куда переносим всю 
нашу [АОП (AOP) логику](https://docs.spring.io/spring-framework/reference/core/aop.html) из старого варианта приложения, т.е. из папки [spring/oldboy/aop](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_24/src/main/java/spring/oldboy/aop) основного приложения.

- **Шаг 6.** - Все bean-ы нашего стартера должны создаваться через авто-конфигурацию, поэтому из всех наших классов 
перенесенных из прошлого проекта в папку 'aop', удаляем аннотацию @Component, т.е. все классы из папки 'aop' лишаются 
аннотаций @Component и @Order если таковые были.

- **Шаг 7.** - Чтобы все перенесенные классы потерявшие аннотации @Component и @Order смогли нормально функционировать в 
контексте приложения (т.е. стать bean-ами), которое подключит наш starter как зависимость, мы добавляем соответствующие
методы и аннотации в файл [LoggingAutoConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingAutoConfiguration.java).

- **Шаг 8.** - Необходимо подключить наш custom starter к нашему же проекту. Это делается через основной [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/build.gradle#L18):

      implementation project(':myfirst-logging-spring-boot-starter')

И конечно добавляем его свойства в [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/src/main/resources/application.yml#L14) основного проекта:

      app.myfirst.logging:
        enabled: true
        level: INFO

При настройке application.yml, обычно вводимые параметры имеют подсказки, но в данном случае они отсутствуют (либо у нас
IDE community edition, либо нет нужных настроек).

- **Шаг 9.** - Используя средства Gradle мы build-им классы нашего starter-a и смотрим содержимое [build/classes/java/main/META-INF/spring-configuration-metadata.json](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/build/classes/java/main/META-INF/spring-configuration-metadata.json)
созданного в разделе build нашего стартера, этот файл можно перенести в папку [resources/META-INF](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/resources/META-INF) и тогда при работе с application.yml среда разработки будет генерировать подсказки (хотя эксперименты показали, что и без него таковые есть, возможно я ошибаюсь).

Запускаем приложение и видим в консоли:

      ...
      2024-04-05T20:30:09.330+05:00  INFO 10296 --- [main] s.o.l.config.LoggingAutoConfiguration : LoggingAutoConfiguration initialized
      2024-04-05T20:30:10.418+05:00  INFO 10296 --- [main] s.o.logging.config.LoggingProperties : Logging properties initialized: LoggingProperties(enabled=true, level=INFO)
      2024-04-05T20:30:11.004+05:00  INFO 10296 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer : Tomcat started on port 8080 (http) with context path ''
      2024-04-05T20:30:11.018+05:00  INFO 10296 --- [main] spring.oldboy.SpringAppRunner : Started SpringAppRunner in 9.903 seconds (process running for 10.445)

Наш logger-starter запустился, инициализировались файлы конфигурации, и даже загрузились свойства из application.yml.

Если мы обратимся к нашему приложению через браузер, аутентифицируемся и сделаем пару любых стандартных действий, например,
запрос user-a с конкретным ID, то в консоли увидим как сработали наши advice-ы. Но на этот раз не из приложения, а из
подключенного stater-a.

________________________________________________________________________________________________________________________
#### Итог:

Стартер позволяет выделить определенный функционал в отдельный самостоятельный программный блок для использования его в 
любом приложении Spring Boot. 

Для этого нам необходимо: 
- создать файл свойств (Properties) с уникальным префиксом (пример, [LoggingProperties.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingProperties.java) и [prefix = "app.myfirst.logging"](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingProperties.java#L13)) 
- создать автоматическую конфигурацию custom starter-a или специальный класс (пример, [LoggingAutoConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingAutoConfiguration.java)) с 
которого будет начинаться конфигурация custom starter-a;
- необходимо избегать @Component аннотаций и создавать компоненты через аннотацию [@Bean в файле авто-конфигурации](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/myfirst-logging-spring-boot-starter/src/main/java/spring/oldboy/logging/config/LoggingAutoConfiguration.java#L54), по возможности;
- сделайте конфигурацию starter-a настраиваемой;
- дополнить ее автоматически сгенерированными метаданными для повышения производительности и удобства использования;

________________________________________________________________________________________________________________________
#### Документация по классам и интерфейсам:

- [Spring Core Features](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html);
- [Annotation Interface @Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html);
- [Annotation Interface @Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html);
- [Spring Boot 3.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide);
- [Annotation Interface EnableConfigurationProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/EnableConfigurationProperties.html);
- [Annotation Interface @ConfigurationProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/ConfigurationProperties.html);
- [Annotation Interface @ConditionalOnProperty](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.html);
- [Annotation Interface @ConditionalOnBean](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnBean.html);
- [Annotation Interface @ConditionalOnMissingBean](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnMissingBean.html);
- [Annotation Interface @ConditionalOnClass](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnClass.html);
- [Annotation Interface @AutoConfigureAfter](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/AutoConfigureAfter.html);
- [Annotation Interface @AutoConfigureBefore](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/AutoConfigureBefore.html);
- [Class ProxyConfig](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/framework/ProxyConfig.html);
- [Interface ObjectProvider<T>](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/ObjectProvider.html);

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