### Spring (Boot) lessons part 6 - [Introduction to Spring-Boot](https://spring.io/projects/spring-boot).

Перед началом изучения Spring Boot желательно прочитать официальную документацию или краткие статьи.

В [папке DOC](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_6/DOC) sql-скрипты и др. полезные файлы:
- [краткая статья о SpringBoot](./DOC/SpringBootArticleShort.md));
- [кратко о SpringBoot стартерах](./DOC/SpringBootStarters.md));

Док. для изучения:
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку (в последствии, она существенно изменится):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/build.gradle) добавим необходимые зависимости: 

    /* Подключим Spring-core и Spring-context. */
    implementation 'org.springframework:spring-core:5.3.22'
    implementation 'org.springframework:spring-context:5.3.22'

Шаг 2. - подключаем Jakarta Annotation API и стандартные аннотации JSR 330:

    implementation 'jakarta.annotation:jakarta.annotation-api:1.3.5'
    implementation 'javax.inject:javax.inject:1'

Шаг 3. - создаем ([переносим из прошлого проекта](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_5)) файл настроек [ApplicationConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/ApplicationConfiguration.java),
в котором используя JAVA и аннотации настраиваем наши bean-ы.

Ниже и далее по мере углубления в Spring Boot будет происходить трансформация нашего build.gradle файла. 

________________________________________________________________________________________________________________________
#### Lesson 24 - [@Conditional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Conditional.html)

Самым распространенным способом управления контекстом Spring являются [@Profile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Profile.html). Они позволяют быстро и 
просто регулировать создание bean-ов. Но иногда может потребоваться более тонкая настройка и это позволяет
аннотирование классов, интерфейсов и методов через @Conditional - аннотация, которая указывает, что компонент имеет 
право на регистрацию только в том случае, если все указанные условия соответствуют.

Примеры (аннотирование класса):

    @Configuration
    @Conditional(IsDevEnvCondition.class)
    class DevEnvLoggingConfiguration {
        // ...
    }

Или отдельного метода:

    @Configuration
    class DevEnvLoggingConfiguration {

        @Bean
        @Conditional(IsDevEnvCondition.class)
        LoggingService loggingService() {
            return new LoggingService();
            }
    }

Условие (condition) - это любое состояние, которое может быть определено программно до того, как определение компонента 
должно быть зарегистрировано (см. [Interface Condition](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Condition.html)).

Интерфейс Condition - функциональный, поэтому его можно использовать в качестве цели назначения для лямбда-выражения или 
ссылки на метод. Единичное условие, которое должно быть выполнено для регистрации компонента. Условия проверяются 
непосредственно перед регистрацией определения компонента и имеют право наложить вето на регистрацию на основании любых 
критериев, которые могут быть определены на этом этапе.

Условия должны соответствовать тем же ограничениям, что и [BeanFactoryPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html), 
и не должны взаимодействовать с экземплярами компонента. Для более детального контроля над условиями, которые 
взаимодействуют с bean-компонентами @Configuration, рассмотрите возможность реализации интерфейса [ConfigurationCondition](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ConfigurationCondition.html).

Несколько условий для данного класса или данного метода будут упорядочены в соответствии с семантикой интерфейса 
[Ordered Spring](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html) и аннотации [@Order](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/Order.html). Подробности см. в разделе [AnnotationAwareOrderComparator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/AnnotationAwareOrderComparator.html).

Аннотация [@Conditional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Conditional.html) может использоваться любым из следующих способов:
- как аннотация уровня типа для любого класса, прямо или косвенно аннотированного с помощью [@Component](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html), включая классы [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html);
- как мета-аннотации, с целью создания пользовательских стереотипных аннотаций;
- как аннотация уровня метода для любого метода [@Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html);

Если класс [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) помечен как @Conditional, все методы @Bean, аннотации [@Import](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Import.html) и аннотации [@ComponentScan](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ComponentScan.html), связанные с этим классом, будут подчиняться этим условиям.

________________________________________________________________________________________________________________________
#### ПРИМЕЧАНИЕ. 
- Наследование аннотаций [@Conditional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Conditional.html) не поддерживается.
- Любые условия супер-классов или переопределенных методов учитываться не будут. 
- Чтобы обеспечить соблюдение этой семантики, сам [@Conditional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Conditional.html) не объявляется как [@Inherited](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/annotation/Inherited.html),
кроме того, любая составленная пользователем аннотация, мета-аннотированная с помощью @Conditional, не должна объявляться как [@Inherited](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/annotation/Inherited.html).
________________________________________________________________________________________________________________________

Краткая документация: [DOC/ConditionalAnnotationSpring.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/DOC/ConditionalAnnotationSpring.txt) и [DOC/ConditionalONTable.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/DOC/ConditionalONTable.txt)

Создадим свои условия:
- Шаг 1. - Создадим конфигурационный файл для управления bean-ами при взаимодействии с БД (имитация) - [JpaConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/JpaConfiguration.java);
- Шаг 2. - Создадим файл условий [condition/JpaCondition.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/condition/JpaCondition.java);
- Шаг 3. - Реализуем метод *.matches() в нашем классе условий JpaCondition, из которого получим: true - в случае 
удачной загрузки драйверов PostgreSQL (и тогда сработает наш класс конфигурации JpaConfiguration), false - в случае
неудачной загрузки драйвера БД PostgreSQL (и тогда не будет применена конфигурация JpaConfiguration);
- Шаг 4. - Чтобы все прошло нормально пропишем необходимую зависимость с PostgreSQL в build.gradle (без нее приложение 
не найдет драйвер БД и мы не сможем запустить JpaConfiguration исходя из наших же условий):
      
        implementation 'org.postgresql:postgresql'

И так, в итоге, у нас есть:
- [JpaConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/JpaConfiguration.java) - некий класс конфигурации для связи с БД через JPA. Он аннотирован как [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) - 
конфигурационный bean, а также мы пометили его как @Conditional(JpaCondition.class).   
- [JpaCondition.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/condition/JpaCondition.java) - класс, который определяет, будет ли активирован конфигурационный bean в зависимости от 
выполнения условий, в нашем случае это - загружен ли драйвер PostgreSQL или нет.

Оба класса снабжены простой логикой для отображения на экране результата работы демо-приложения [AppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/lesson_24/AppRunner.java).
Т.е. мы сможем увидеть находится ли bean [JpaConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/JpaConfiguration.java) в контексте или нет.

________________________________________________________________________________________________________________________
#### Lesson 25 - Как сделать (сконфигурировать) простое Spring Boot приложение.

Считается, что это довольно просто и для этого нужно:
- Выбрать версию Spring Boot;
- Добавить или создать Spring Boot Starter;
- Добавить нужные свойства;
- Добавить или удалить нужные Spring Boot Starters Bean-ы;
Хотя шаги примерно одинаковые, реализовать их можно несколькими способами.

#### Вариант 1:

*** Шаг 1 - Настраиваем версию Spring ***

Идем в официальную документацию - [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/). 

Для настройки нашего Gradle нам нужен plugin: id 'org.springframework.boot' version '3.1.3' - 
он добавляет необходимые задачи в Gradle и имеет обширную взаимосвязь с другими plugin-ами.
Так же нам понадобится менеджер зависимостей: 'io.spring.dependency-management', который 
позволяет решать проблемы несовместимости различных версий и модулей Spring-а (см. [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/build.gradle),
замечу, могут быть подключены не последние версии).

    plugins {
        id 'org.springframework.boot' version '3.1.3'
        id "io.spring.dependency-management" version '1.0.11.RELEASE'
    }

После подключения plugin-a в Gradle Tasks появились новые задачи с префиксом 'boot' - это 
вспомогательные задачи для создания, build-а и запуска нашего Spring Boot приложения.

Документация по менеджеру зависимостей Spring - [Dependency Management Plugin](https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/).

*** Шаг 2 - Добавляем наш первый Spring Starter ***

Заглянем в официальную документацию - [Spring Boot Reference Documentation (6.1.5. Starters)](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.build-systems.starters)

Видим, что основным корневым (основным, Core) стартером является - 'spring-boot-starter' - он 
включающий поддержку авто-конфигурации, логирование и YAML (так же см. [DOC/SpringBootStarters.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/DOC/SpringBootStarters.txt)).

Подключим его в нашем [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/build.gradle):

    implementation 'org.springframework.boot:spring-boot-starter'

При этом версии мы не указываем, все необходимые данные о совместимых версиях будут браться из плагинов
загруженных на первом шаге.

Уже на этом этапе многие зависимости уходят из build.gradle, т.к. они будут транзитивно подтягиваться 
при помощи заданных плагинов и SpringBoot Starter-a. Также наиболее подходящая версия зависимости для 
подключенной БД, тоже будет получена из плагинов Spring. Так же нам хватит аннотаций самого Spring-a.

И так по сравнению в предыдущими версиями [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/build.gradle) слегка похудел.

*** Шаг 3 - Создаем точку входа в Spring приложение ***

В корне нашего проекта создаем [FirstSpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/FirstSpringAppRunner.java). Сам класс должен быть аннотирован - 
[@SpringBootApplication](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html). Все, можем его запустить и посмотреть, как ведут себя наши bean-ы созданные
и настроенные совершенно под другие нужны.

#### Вариант 2:
Второй вариант создания Spring Boot приложения реализуется, либо средствами IDE, например, IntelliJ IDEA 
версии Ultimate, либо через сайт [https://start.spring.io/](https://start.spring.io/), который использует та же 
среда разработки (кому как удобно).

________________________________________________________________________________________________________________________
#### Lesson 26 - Spring Boot Application (Spring приложение, особенности).

В прошлом уроке мы сделали наше первое приложение. Первая особенность - точка входа должна находится в рутовой 
директории. Т.е. наш проект, например, находится в 'spring.oldboy', тут же мы ищем все наши bean-ы если в 
конфигурационных файлах нет указания на другие параметры, и тут же в корне находится 'запускаемый файл' нашего 
приложения - [FirstSpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/FirstSpringAppRunner.java). 

Далее, все остальные пакеты, классы и т.д., дополняющие и расширяющие наш проект, которые будут использоваться 
в нашем приложении в будущем, и которые могут быть bean-ами, должны находится внутри зависимого пакета, в корне 
которого лежит наш запускающий приложение класс.

Нужно помнить, что наше приложение решает конкретные задачи и ограничено СВОИМ ПАКЕТОМ и только о нем идет речь,
не о неком глобальном проекте, где пакетов и приложений соответственно может быть несколько и точек входа тоже 
несколько. 

________________________________________________________________________________________________________________________
### !!! 
1. Аннотация [@SpringBootApplication](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html), который помечен наш 'запускающий приложение класс' (у нас это 
[FirstSpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/FirstSpringAppRunner.java)) должна быть единственной на все наше приложение. Она может ставиться только 
над классом. 
2. Важно соблюдать структуру приложения и расположения классов bean-ов внутри пакета где располагается 
'точка входа в приложение', поскольку именно в этом пакете ComponentScan будет проводить поиск bean-ов.
3. Автоматически будут загружаться файлы с именем [application.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_6/src/main/resources/properties_for_lesson_24) ([*.yaml или *.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/resources/application.yml)) 

### !!!
________________________________________________________________________________________________________________________

Если заглянуть в @SpringBootApplication:
    
    /* Может помечать только классы */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    
    /* Т.е. это фактически метка файла конфигурации, с особнностью - уникальность на весь проект */
    @SpringBootConfiguration
    
    /* 
    Автоматическое конфигурирование нашего приложения, одноко используя методы *.exclude() или 
    *.excludeName() мы можем изьят из конфигурирования ненужные нам классы.
    */
    @EnableAutoConfiguration
    
    /* 
    Настройка CompanentScan-а по-умолчанию нас устраивает и мы легко можем убрать ее из других 
    классов помеченных как @Configuration. Еще раз, классов с аннотацией @Configuration может быть 
    несколько и только с @SpringBootConfiguration один единственный. 

    В данном случае сканирование bean-ов будет происходит внутри того пакета, в котором расположен
    наш класс 'точка входа', в данном примере это FirstSpringAppRunner.java.
    */
    @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
    public @interface SpringBootApplication {
    }

И так, мы имеем небольшой запускаемый файл и большой набор папок и настроек, которые определяют логику работы 
нашего приложения.

________________________________________________________________________________________________________________________
#### Lesson 27 - Lombok (аннотации).

Для подключения аннотаций Lombok мы можем воспользоваться зависимостями для Gradle:

    dependencies {
        compileOnly 'org.projectlombok:lombok:1.18.28'
        annotationProcessor 'org.projectlombok:lombok:1.18.28'
    
        testCompileOnly 'org.projectlombok:lombok:1.18.28'
        testAnnotationProcessor 'org.projectlombok:lombok:1.18.28'
    }

С официального сайта разработчика - [https://projectlombok.org/setup/gradle](https://projectlombok.org/setup/gradle), либо же мы можем
подтянуть plugin, чтобы не интегрировать 4-и строки в наш [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/build.gradle):

    plugins {
      id "io.freefair.lombok" version "8.3"
    }

С сайта Gradle - [https://plugins.gradle.org/plugin/io.freefair.lombok](https://plugins.gradle.org/plugin/io.freefair.lombok).

Теперь мы можем убрать 'ванильный' Java код из некоторых наших классов и использовать Lombok аннотации
для создания конструкторов, геттеров, сеттеров и т.п. 

В других учебных проектах мы уже касались работы с Lombok (краткий обзор) - [Fast_Lombok.txt](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/Fast_Lombok.txt)

Если с основной массой простых классов расстановка аннотаций решает вопрос сокращения рутинного кода,
то в более сложных конфигурациях связанного кода придется донастроить работу Lombok используя файл
конфигурации [lombok.config](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/lombok.config) (см. [https://projectlombok.org/features/configuration](https://projectlombok.org/features/configuration)). Данный файл создается 
нами и помещается в корень всего Gradle проекта, а не только Spring приложения.

В нем мы используя специальный: config.stopBubbling = true - ключ, сообщающий Lombok, что это наш корневой 
каталог. Затем мы можем создать lombok.config файлы в любых подкаталогах (обычно представляющих проекты 
или исходные пакеты) с различными настройками. Но, пока, обойдемся корневым. В данном файле указываем:
    
    lombok.copyableannotations += org.springframework.beans.factory.annotation.Value
    lombok.copyableannotations += org.springframework.beans.factory.annotation.Qualifier

Тут мы указали какие аннотации мы хотим, перенести с полей в сгенерированные конструкторы. У нас
это аннотации от Spring ([@Value](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Value.html) и [@Qualifier](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Qualifier.html)). Важно указать полный путь к используемым Spring аннотациям.

Док. для изучения:
- [Пакет org.springframework.beans.factory.annotation ](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/package-summary.html);
- [Lombok features](https://projectlombok.org/features/) ;
- [Lombok javadoc](https://projectlombok.org/api/) ;

________________________________________________________________________________________________________________________
#### Lesson 28 - Properties - внешние файлы свойств.

В Spring-e есть масса возможностей передавать свойства и настройки при помощи файлов 'properties' имеющих 
зарезервированное название или расширение, см. [External Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.files)

Создадим файл свойств (с тестовым параметром) - [resources/spring.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/resources/spring.properties), используя не хитрый код в 
[FirstSpringAppRunner.java]():

    String sp = SpringProperties.getProperty("test.message");
    System.out.println(sp);

Получим в консоли то, что поместили в параметр "test.message". См. док. [Class SpringProperties](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/SpringProperties.html).

Spring Boot использует особый PropertySource порядок, предназначенный для разумного переопределения значений. 
Более поздние источники свойств могут переопределять значения, определенные в более ранних. Т.е. например, 
настройки переданные через аргументы командной строки (пункт 11) затрут (будут применены преимущественно) 
настройки переданные через данные конфигурационного файла - application.properties (пункт 3) см. ниже.

Нужно четко понимать, ни один из параметров не игнорируется, он просто меняется (при совпадении ключа) в 
соответствии с приоритетом передающего источника.
 
Источники по приоритету рассматриваются в следующем порядке см. [Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config):

1. Свойства по умолчанию (задаются настройкой SpringApplication.setDefaultProperties).
2. @PropertySource аннотации к нашим @Configuration классам. !!! Внимание, такие источники свойств не добавляются 
в Environment до тех пор, пока контекст приложения не будет обновлен. Слишком поздно настраивать определенные 
свойства, например logging.* и spring.main.* которые считываются до начала обновления !!!
3. Данные конфигурации (например, application.properties файлы).
4. A RandomValuePropertySource, имеющий свойства только в random.*.
5. Переменные среды ОС.
6. Свойства системы Java ( System.getProperties()).
7. Атрибуты JNDI из java:comp/env.
8. ServletContext параметры инициализации.
9. ServletConfig параметры инициализации.
10. Свойства из SPRING_APPLICATION_JSON (встроенный JSON, встроенный в переменную среды или системное свойство).
11. Аргументы командной строки.
12. properties атрибут в наших тестах. Доступны @SpringBootTest и тестовые аннотации для тестирования 
определенной части нашего приложения.
13. @DynamicPropertySource аннотации в наших тестах.
14. @TestPropertySource аннотации к нашим тестам.
15. Свойства глобальных настроек Devtools в $HOME/.config/spring-boot каталоге, когда devtools активен.

Файлы конфигурационных данных рассматриваются в следующем порядке:
1. Свойства приложения, упакованные внутри нашего jar (application.properties и варианты YAML).
2. Свойства приложения, специфичные для профиля, упакованные внутри вашего jar 
(application-{profile}.properties и варианты YAML).
3. Свойства приложения за пределами упакованного jar-файла (application.properties и варианты YAML).
4. Свойства приложения, специфичные для профиля, за пределами упакованного jar-файла 
(application-{profile}.properties и варианты YAML).

Исследуем поведение системы (например, настройку параметра db.pool.size). У нас есть обычный 
[application.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/resources/properties_for_lesson_24/application.properties) и в порядке приоритетности он имеет 'рейтинг' = 1. Создадим дополнительно
[application-qa.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/resources/properties_for_lesson_24/application-qa.properties), в данном случае это файл свойств специфичный для профиля, а он имеет
'рейтинг' = 2 см. выше. И значит данные из более приоритетного источника заменят данные из менее
приоритетного с тем же ключом. Запускаем и проверяем.

Док. для изучения:
- [Spring Properties](https://docs.spring.io/spring-framework/reference/appendix.html) ;
- [Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config) ;
- [Common Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html) ;

________________________________________________________________________________________________________________________
#### Lesson 29 - Yaml format.

Смотреть краткий обзор: [DOC/YAML_SHORT_REVIEW.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/DOC/YAML_SHORT_REVIEW.txt)

При добавлении: 

    implementation 'org.springframework.boot:spring-boot-starter' 

в наш [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/build.gradle), мы автоматом подхватили и зависимость 'org.yaml: snakeyaml: 1.33' - библиотека для работы 
с файлами *.yaml или *.yml. 

На момент прохождения данного урока у нас есть два файла расширения *.properties со следующим содержанием:
application.properties ->
    
    db.username=postgres
    db.password=pass
    db.pool.size=18
    db.driver=PostgresDriver
    db.url=postgres:5432
    db.hosts=localhost,127.0.0.1
    spring.profiles.active=development,qa

application-qa.properties ->

    db.pool.size=36

Перепишем их в YAML формате, т.е. кроме смены расширения они естественно изменять и внутренний код см. 
[application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/resources/application.yml) и [application-qa.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/resources/application-qa.yml). Для проверки работоспособности настроек с использованием YAML
можно запустить [FirstSpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/FirstSpringAppRunner.java) в любом из режимов.

Однако не стоит заблуждаться насчет файла [ApplicationConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/ApplicationConfiguration.java) и работоспособности его аннотаций: 
    
    @Configuration
    @PropertySource("classpath:properties_for_lesson_24/application.properties")
    @PropertySource("classpath:properties_for_lesson_24/application-qa.properties")
    @ComponentScan(basePackages = "spring.oldboy")

Он продолжает влиять на формирование контекста и на работоспособность не только [AppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/lesson_24/AppRunner.java), но и 
[FirstSpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/FirstSpringAppRunner.java). Так же, существует мнение, что использовать 'винегрет' из файлов настроек 
различного расширения *.properties и *.yaml в одном проекте, вещь весьма сомнительная - т.е. лучше, 
либо то, либо другое и не нарушать принятую структуру Spring Boot приложения.

________________________________________________________________________________________________________________________
#### Lesson 30 - [@ConfigurationProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/ConfigurationProperties.html).

Spring Boot предоставляет нам возможность представлять содержимое файлов *.yaml или *.yml не как примитивы,
а как объекты или 'конфигурационные объекты', и далее превратить его в bean:
- [DatabaseProperties.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/DatabaseProperties.java) - в таком виде мы можем передавать его в любое место нашего приложения и получать из
него данные.

________________________________________________________________________________________________________________________
И так, первый вариант, как сделать из нашего класса свойств базы данных BEAN прост и уже описан: 
- Создаем сам класс (см. [DatabaseProperties.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/DatabaseProperties.java));
- Делаем его POJO, содержащим геттеры и сеттеры, а так же конструктор без параметров;
- В одном из конфигурационных файлов (например, [JpaConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/JpaConfiguration.java)) создаем метод возвращающий наш класс и 
аннотируем его как @Bean, а так же проводим соответствие с [application.yml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/resources/application.yml) (файл параметров содержащих настройки 
нашей БД и т.д.) используя аннотацию @ConfigurationProperties(prefix = "db")

Тестируем получившуюся конфигурацию контейнера в [FirstSpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/FirstSpringAppRunner.java).

Но, существует еще способы!

________________________________________________________________________________________________________________________
Второй вариант, мы НЕ БУДЕМ реализовывать в коде, а опишем тут. И так, для начала мы удалим наш bean из
JpaConfiguration.java. Но bean из DatabaseProperties нам все еще нужен - заставим Spring просканировать 
наш класс - аннотируем его @Component, естественно аннотация @ConfigurationProperties(prefix = "db")
нам тоже понадобится:

    @Data
    @NoArgsConstructor
    @Component
    @ConfigurationProperties(prefix = "db")
    public class DatabaseProperties {
        private String username;
        private String password;
        private String driver;
        private String url;
        private String hosts;
        private PoolProperties pool;
        private List<PoolProperties> pools;
        private Map<String, Object> properties;

        @Data
        @NoArgsConstructor
        public static class PoolProperties {
            /* Нужные нам поля */
            private Integer size;
            private Integer timeout;
        }
    }

В таком варианте мы также получим необходимый нам bean.

________________________________________________________________________________________________________________________
Третий вариант, очень похож на второй с той лишь разницей, что мы убираем из нашего класса DatabaseProperties
аннотацию @Component. А поскольку мы 'удалили метод аннотированный как @Bean' и из JpaConfiguration.java:

    @Bean
    @ConfigurationProperties(prefix = "db")
    public DatabaseProperties databaseProperties() {
        return new DatabaseProperties();
    }

И не разместили его ни в одном из доступных configuration классов, но, нам все еще нужно, чтобы Spring его 
просканировал и сделал из него BEAN. Для этого нам придется аннотировать наш класс 'точку входа в приложение' -
FirstSpringAppRunner.java как @ConfigurationPropertiesScan:

    package spring.oldboy;

    @SpringBootApplication
    @ConfigurationPropertiesScan
    public class FirstSpringAppRunner {
        public static void main(String[] args) {
            ConfigurableApplicationContext context =
                    SpringApplication.run(FirstSpringAppRunner.class, args);
            
                /* some learning code */ 
        
        }
    }

И тогда Spring будет сканировать в пакете 'spring.oldboy' все классы помеченные как @ConfigurationProperties

________________________________________________________________________________________________________________________
Все три способа создания BEAN-a из нашего класса DatabaseProperties работают, однако конфигурация класса в
исходном виде делает его изменяемым (immutable). Если же мы захотим сделать его неизменяемым то, нам придется
применить обычный класс 'record' см. [ImmutableDatabaseProperties.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_6/src/main/java/spring/oldboy/config/ImmutableDatabaseProperties.java)
