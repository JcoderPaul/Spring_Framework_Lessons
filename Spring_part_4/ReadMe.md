### Spring (Core) lessons part 4 - [Java-based Configuration](https://docs.spring.io/spring-framework/reference/core/beans/java.html).

В [папке DOC](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_4/DOC) sql-скрипты и др. полезные файлы.

Док. для изучения:
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Пакет org.springframework.context.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/package-summary.html) - Поддержка аннотаций для контекста приложения, включая «общие» аннотации JSR-250, сканирование компонентов и метаданные на основе Java для создания объектов, управляемых Spring;
- [Пакет org.springframework.stereotype](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/package-summary.html) - Аннотации, обозначающие роли типов или методов в общей архитектуре (на концептуальном, а не на уровне реализации);
  Предназначен для использования инструментами и аспектами ([идеальная target для pointcut](https://docs.spring.io/spring-framework/reference/core/aop.html));
- [Пакет org.springframework.beans.factory](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/package-summary.html) - Базовый пакет, реализующий облегченный контейнер Spring Inversion of Control (IoC);
  Предоставляет альтернативу шаблонам проектирования Singleton и Prototype, включая согласованный подход к управлению конфигурацией. Создан на основе пакета [org.springframework.beans](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/package-summary.html);
- [Пакет org.springframework.context](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/package-summary.html) - Этот пакет основан на пакете bean-компонентов и добавляет поддержку источников сообщений и шаблона проектирования Observer, а также возможность объектам приложения получать ресурсы с использованием согласованного API;
  Приложениям Spring нет необходимости явно зависеть от ApplicationContext или даже от функциональности BeanFactory. Одной из сильных сторон архитектуры Spring является то, что объекты приложений часто можно настраивать без какой-либо зависимости от API-интерфейсов Spring;
- [Пакет org.springframework.core.env](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/package-summary.html) - Абстракция среды (окружения) Spring, состоящая из профиля определения компонента и поддержки иерархического источника свойств;

---
**!!! При чтении учебного кода рекомендуется внимательно изучать комментарии к оному и запускать приложение в режиме DEBUG для более эффективного наблюдения за процессами происходящими в приложении !!!** 

---
Для начала проведем предварительную подготовку:

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/build.gradle) добавим необходимые нам зависимости: 

    /* Подключим Spring-core и Spring-context. */
    implementation 'org.springframework:spring-core:5.3.22'
    implementation 'org.springframework:spring-context:5.3.22'

Шаг 2. - подключаем Jakarta Annotation API:

    implementation 'jakarta.annotation:jakarta.annotation-api:1.3.5'

Шаг 3. - для того, чтобы обрабатывать аннотации, мы добавляем в application.xml нужные строки 
и удаляем все лишнее (указываем Sprig-у какую папку сканировать на наличие аннотаций @Component, 
@Controller, @Repository, @Service:  

    <context:component-scan base-package="spring.oldboy"/> 

________________________________________________________________________________________________________________________

В Spring lessons ([part 1](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_1) - [part 2](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_2) - [part 3](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_3)) мы настраивали bean-компоненты Spring с помощью файла конфигурации *.XML
(см. resources/application.xml) и немного при помощи аннотаций. Теперь приступим к настройке наших bean-ов на основе 
Java конфигурирования.

Конфигурация на основе Java позволяет настроить работу Spring приложения без использования *.XML, а с помощью нескольких 
аннотаций на основе Java.

---
### **[@Configuration и @Bean-аннотации](https://docs.spring.io/spring-framework/reference/core/beans/java/basic-concepts.html)**

Аннотация класса с помощью [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) указывает, что класс может использоваться контейнером 
[Spring IoC](https://docs.spring.io/spring-framework/reference/core/beans.html) в качестве источника определений bean-компонентов. Аннотация [@Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html) сообщает Spring-у, 
что метод, помеченный [@Bean](https://docs.spring.io/spring-framework/reference/core/beans/java/bean-annotation.html), вернет объект, который должен быть зарегистрирован как bean-компонент 
в контексте приложения Spring-а. Самый простой возможный класс @Configuration будет следующим:

    package spring.oldboy;
    import org.springframework.context.annotation.*;
    
    @Configuration
    public class HelloWorldConfig {
        @Bean
        public HelloWorld helloWorld(){
            return new HelloWorld();
        }
    }

Приведенный выше код будет эквивалентен следующей конфигурации *.XML:

    <beans>
       <bean id = "helloWorld" class = "spring.oldboy.HelloWorld" />
    </beans>

В данном примере - имя метода помечено @Bean, и работает как идентификатор компонента, создает и 
возвращает фактический компонент. Наш класс конфигурации может иметь объявление более чем для 
одного @Bean-а. После того как наши классы конфигурации определены, мы можем загрузить и предоставить 
их в контейнер Spring с помощью AnnotationConfigApplicationContext:

    public static void main(String[] args) {
        ApplicationContext appContext = 
                        new AnnotationConfigApplicationContext(HelloWorldConfig.class);
    
        HelloWorld helloWorld = appContext.getBean(HelloWorld.class);
        
        helloWorld.setMessage("Hello World!");
        helloWorld.getMessage();
    }

Мы можем загрузить различные классы конфигураций следующим образом:

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = 
                                new AnnotationConfigApplicationContext();
    
        appContext.register(AppConfig.class, OtherConfig.class);
        appContext.register(AdditionalConfig.class);
        appContext.refresh();
    }

Доп. док. для изучения:
- [Class ConfigurationClassBeanDefinitionReader](https://docs.spring.io/spring-framework/docs/3.2.0.M1_to_3.2.0.M2/Spring%20Framework%203.2.0.M1/org/springframework/context/annotation/ConfigurationClassBeanDefinitionReader.html) ;
- [Annotation Interface Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) ;
- [Spring JavaConfig Reference Guide](https://docs.spring.io/spring-javaconfig/docs/1.0.0.M4/reference/htmlsingle/spring-javaconfig-reference.html) ;

---
### **Внедрение зависимостей компонента**

Когда @Bean-ы имеют зависимости друг от друга, выразить эту зависимость просто:

    package spring.oldboy;
    import org.springframework.context.annotation.*;
    
    @Configuration
    public class AppConfig {
        @Bean
        public Foo foo() {
            return new Foo(bar());
        }
        @Bean
        public Bar bar() {
            return new Bar();
        }
    }

Здесь компонент foo получает ссылку на bar через внедрение в конструктор.

Доп. док. для изучения:
- [Annotation Interface Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) ;
- [Annotation Interface Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html) ;
- [Class AnnotationConfigApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/AnnotationConfigApplicationContext.html) ;
- [Class AnnotationConfigWebApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/AnnotationConfigWebApplicationContext.html) ;
- [Class ConfigurationClassPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ConfigurationClassPostProcessor.html) ;
- [Interface Environment](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/Environment.html) ;

---
#### Lesson 19 - [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html)

Создадим папку для хранения всех наших конфигураций - [config](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_4/src/main/java/spring/oldboy/config). 

Фактически для работы Spring приложения, обычно используют несколько файлов конфигураций, 
для логического разделения функциональности:
- [управление БД](https://spring.io/projects/spring-data);
- управление web-частью приложения ([Web Services](https://spring.io/projects/spring-ws), [Web Flow](https://spring.io/projects/spring-webflow), [Session](https://spring.io/projects/spring-session) и т.д.);
- управление работой message broker (integration broker, interface engine) (ActiveMQ, [Kafka](https://spring.io/projects/spring-kafka), Qpid, 
[RabbitMQ](https://spring.io/projects/spring-amqp));
- общий конфигурационный файл, для управления всем набором конфигураторов;

В папке config создаем [ApplicationConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/config/ApplicationConfiguration.java) и аннотируем его [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html). Далее перетащим 
в него из [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/resources/application.xml) максимально возможный набор управляющих конфигурацией настроек.

Погнали!

1. Аннотация [@PropertySource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/PropertySource.html) является повторяемой, т.е. сама аннотирована как [@Repeatable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/annotation/Repeatable.html), что означает
для нас возможность размещать ее над аннотируемыми классами по нескольку штук и указывать необходимые пути к файлам 
настроек.

Из файла [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/resources/application.xml) стока с указанием пути к файлу свойств:
    
    <context:property-placeholder location="classpath:application.properties"/>

Превращается в аннотацию в файле [ApplicationConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/config/ApplicationConfiguration.java):

    @PropertySource("classpath:application.properties")

На данном этапе наш [JavaConfigAndXmlDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/lesson_19/JavaConfigAndXmlDemo.java) все еще работает и выдает результат.

2. Аннотация [@ComponentScan](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ComponentScan.html) над [ApplicationConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/config/ApplicationConfiguration.java) и ее параметры, практически полностью 
повторяют настройки из [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/resources/application.xml):

    
    <context:component-scan base-package="spring.oldboy"
                                annotation-config="true"
                                resource-pattern="**/*.class"
                                scoped-proxy="no"
                                use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Component"/>
        <context:include-filter type="assignable" expression="spring.oldboy.repository.CrudRepository"/>
        <context:include-filter type="regex" expression="spring.oldboy\..+Repository"/>
    </context:component-scan>

И выглядят как:

    @ComponentScan(basePackages = "spring.oldboy",
                            useDefaultFilters = false,
        includeFilters = {
                    @Filter(type = FilterType.ANNOTATION, value = Component.class),
                    @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
                    @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
    })

На данном этапе в [IoC контейнере](https://docs.spring.io/spring-framework/reference/core/beans.html) находятся [bean-ы](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) созданные согласно нашей Java-конфигурации и
[OnlyJavaConfigDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/lesson_19/OnlyJavaConfigDemo.java) прекрасно это демонстрирует. Мы не видим часть bean-ов которые создавали
ранее используя [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/resources/application.xml).

---
#### Lesson 20 - [@ImportResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ImportResource.html) и [@Import](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Import.html)

Spring предоставляет аннотацию [@ImportResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ImportResource.html), которая используется для загрузки bean-компонентов 
из файла applicationContext.xml в контекст приложения. Применительно к нашему [ApplicationConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/config/ApplicationConfiguration.java)
мы получаем комбинированный способ создания 'контекста' используя и XML конфигурирование ([Groovy скрипты](https://github.com/JcoderPaul/Groovy_Lessons))
и аннотации и Java настройку будущего Spring приложения.

Аннотация [@Import](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Import.html) позволяет группировать классы конфигураций (например):

    @Configuration
    @Import({ DogConfig.class, CatConfig.class })
    class MammalConfiguration {
    }

См. наш: 
- [ApplicationConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/config/ApplicationConfiguration.java);
- [Annotation Interface Import](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/DOC/ImportAnnotationInterface.md);
- [Annotation Interface ImportResource](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/DOC/ImportResourceAnnotationInterface.md);

---
#### Lesson 21 - [@Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)

[@Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html) - это аннотация уровня метода и прямой аналог элемента XML <bean/>. Аннотация поддерживает большинство атрибутов, 
предлагаемых , <bean/> например: init-method, destroy-method, autowiring, lazy-init, dependency-check, depends-on and 
scope.

Объявление bean - компонента происходит через добавление аннотации @Bean к методу. Когда JavaConfig обнаруживает такой 
метод, он выполнит этот метод и зарегистрирует возвращаемое значение как компонент внутри [BeanFactory](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/BeanFactory.html) реализации. По умолчанию 
имя компонента будет таким же, как имя метода. Ниже приведен простой пример объявления @Bean метода:

    @Configuration
    public class AppConfig {
        @Bean
        public TransferService transferService() {
            return new TransferServiceImpl();
        }
    }

Для сравнения, приведенная выше конфигурация в точности эквивалентна следующему Spring XML:

    <beans>
        <bean name="transferService" class="com.acme.TransferServiceImpl"/>
    </beans>

Оба варианта приведут к тому, что bean-компонент с именем transferService будет доступен в 
BeanFactory / ApplicationContext и привязан к экземпляру объекта типа TransferServiceImpl.
 
Внедрение зависимостей тоже упрощается. Когда @Bean-ы имеют зависимости друг от друга, выразить эту зависимость так же 
просто, как вызвать один метод компонента другим:

    @Configuration
    public  class AppConfig {
        @Bean 
        public Foo foo() {
            return new Foo(bar());
        }
        @Bean 
        public Bar bar() {
            return new Bar();
        }
    }

- [ApplicationConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/config/ApplicationConfiguration.java) - пример файла конфигурации, в котором созданы bean используя аннотацию @Bean
- [BeanJavaConfigDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/lesson_21/BeanJavaConfigDemo.java) - простая демонстрация работоспособности контекста на основе Java конфигурации. 

---
#### Lesson 22 - [@Profile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Profile.html)

Профили Spring предоставляют возможность разделить части конфигурации нашего приложения и сделать 
их доступными только в определенных средах. Любой [@Component](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html) или [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) может быть помечен 
значком [@Profile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Profile.html) для ограничения при загрузке:

    @Configuration
    @Profile("production")
    public class ProductionConfiguration {
            // some code ...
    }

В обычном случае в Spring мы можем использовать spring.profiles.active Environment свойство, чтобы 
указать, какие профили активны. Мы можем указать свойство любым из обычных способов, например, мы 
можем включить его в свой [application.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/resources/application.properties):

    spring.profiles.active=dev,hsqldb

или указать в командной строке с помощью переключателя: --spring.profiles.active=dev,hsqldb.

- [application.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/resources/application.properties) - в последней строке приведен пример профилирования, и следовательно, 
активации того или иного профиля через файл свойств.

Мы также можем программно установить активные профили, вызвав их SpringApplication.setAdditionalProfiles(...) 
перед запуском приложения. Также можно активировать профили с помощью ConfigurableEnvironment интерфейса 
Spring.

- [BeanDefinitionNamesDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/oldboy/lesson_22/BeanDefinitionNamesDemo.java) - демонстрация работы профилирования запуска bean-ов. 

Поскольку в BeanDefinitionNamesDemo мы прописали только один профиль:
    
    myContext.getEnvironment().setActiveProfiles("prod");

А в файле [application.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/resources/application.properties) задан только: spring.profiles.active=production, то в 
результирующем списке bean-в мы не найдем: [spring.web_demo_config.WebConfiguration](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_4/src/main/java/spring/web_demo_config/WebConfiguration.java)
