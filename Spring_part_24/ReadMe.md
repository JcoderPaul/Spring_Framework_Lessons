### Spring Boot lessons part 24 - AOP в Spring.

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_24/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html) ;
- [Spring AOP APIs](https://docs.spring.io/spring-framework/reference/core/aop-api.html) ;
- [AspectJ Documentation and Resources](https://eclipse.dev/aspectj/doc/released/index.html) ;
- [Intro to AspectJ (articles from https://www.baeldung.com/)](https://www.baeldung.com/aspectj) ;
________________________________________________________________________________________________________________________
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку (подгрузим зависимости в [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/build.gradle)):

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
#### AOP (Аспектно-ориентированное программирование)

**Основные понятия.**

[Аспектно-ориентированное программирование (АОП)](https://en.wikipedia.org/wiki/Aspect-oriented_programming) — это 
парадигма программирования являющейся дальнейшим развитием процедурного и объектно-ориентированного программирования 
(ООП). Идея АОП заключается в выделении так называемой сквозной функциональности.

[Сквозные задачи (Cross-cutting concerns, сквозная логика)](https://en.wikipedia.org/wiki/Cross-cutting_concern) - это 
части программы, которые зависят от многих других частей системы или должны влиять на них. Они составляют основу для 
развития аспектов. Подобные сквозные задачи не вписываются в [объектно-ориентированное программирование](https://en.wikipedia.org/wiki/Object-oriented_programming) 
или [процедурное программирование](https://en.wikipedia.org/wiki/Procedural_programming).

Сквозные задачи могут быть прямой причиной запутанности или системных взаимозависимостей внутри программы. Поскольку 
процедурные и функциональные конструкции языка полностью состоят из вызова процедур, не существует семантики, с помощью 
которой две цели (возможность реализации и соответствующая сквозная задача) могут быть решены одновременно. В результате 
код, решающий сквозную задачу, должен быть разбросан или дублирован в различных связанных местах, что приводит к потере 
модульности.

Аспектно-ориентированное программирование направлено на инкапсуляцию сквозных задач в [АСПЕКТЫ](https://en.wikipedia.org/wiki/Aspect_(computer_programming)) для сохранения модульности. 
Это позволяет полностью изолировать и повторно использовать код, решающий сквозную задачу (сквозную логику). Если 
проектирование основано на сквозных задачах, преимущества разработки программного обеспечения могут включать модульность 
и упрощенное обслуживание.

**Основные термины.**

- Aspect: некий код, который актуален для несколько классов. Управление транзакциями, или логирование процессов являются 
хорошими примерами сквозных аспектов в корпоративных Java-приложениях. В Spring AOP аспекты реализуются с помощью 
аннотации [@Aspect](https://eclipse.dev/aspectj/doc/latest/index.html) (стиль [@AspectJ](https://en.wikipedia.org/wiki/AspectJ)) 
или XML-конфигурации для класса.

- Join point (точка присоединения): точка во время выполнения программы, такая как выполнение метода или обработка 
исключения. В Spring AOP точка соединения всегда представляет собой выполнение метода.

- Advice (совет): действие, предпринимаемое аспектом в определенной точке соединения. Advice можно разделить на те, 
которые выполняются только "до" - "before" основной логики метода либо "после" - "after" либо "вокруг" - "around" 
(т.е. сразу и до и после). Многие AOP-фреймворки, включая Spring, моделируют advice как перехватчик (interceptor), 
который поддерживает цепочку других перехватчиков вокруг точки соединения.

- Pointcut (точка среза): предикат, который соответствует join point. Advice ассоциируется с выражением pointcut и 
запускается в любой точке соединения, совпадающей с указателем (например, выполнение метода с определенным именем). 
Концепция точек соединения (join point), сопоставляемых выражениями pointcut, является центральной в AOP, и Spring 
по-умолчанию использует язык выражений AspectJ pointcut.

- Introduction: объявление дополнительных методов или полей от имени типа. Spring AOP позволяет нам вводить новые 
интерфейсы (и соответствующую реализацию) в любой рекомендуемый объект. Например, мы можем использовать introduction, 
чтобы заставить bean реализовать интерфейс IsModified, чтобы упростить кэширование.

- Target object (целевой объект): объект, который советуется одним или несколькими аспектами. Также известен как 
"advised object". Поскольку Spring AOP реализуется с помощью прокси во время выполнения, этот объект всегда является 
проксированным объектом.

- AOP proxy: объект, созданный AOP-фреймворком для реализации аспектов. В Spring Framework прокси AOP - это динамический 
прокси JDK или прокси CGLIB.

- Weaving (вплетание, плетение): связывание аспектов с другими типами приложений или объектами для создания нужной 
логики. Это может быть сделано во время компиляции (например, с помощью компилятора AspectJ), во время загрузки или во 
время выполнения. Spring AOP, как и другие чисто Java AOP-фреймворки, выполняет weaving во время выполнения.

Документация: 
- [AspectJ Documentation and Resources](https://eclipse.dev/aspectj/doc/latest/index.html#documentation) ;
- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html) ;
- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/docs/4.3.15.RELEASE/spring-framework-reference/html/aop.html) ;
- [@AspectJ support](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj.html) ;
- [Intro to AspectJ](https://www.baeldung.com/aspectj) ;
- [Статьи по АОП](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_24/DOC/AOP_Articles) ;

________________________________________________________________________________________________________________________
#### Lesson 118 - AOP - Pointcut (АОП - точка среза).

Аспект (aspect) — под аспектом понимают комбинацию, состоящую из среза (pointcut) и реализующего сквозную
функциональность совета (advice). Фактически это кусок кода, который будет внедряться в другой кусок кода
при совпадении определенных условий.

Создадим наш первый аспект [FirstAspect.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/aop/FirstAspect.java). 

Первая аннотация которой мы его помечаем это @Aspect. При включенной поддержке [@AspectJ](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj.html) любой bean-компонент (в нашем 
случае это вторая аннотация [@Component](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html), т.е. наш аспект должен быть bean-ом), определенный в контексте нашего приложения 
с классом, который является аспектом [@AspectJ](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj.html), автоматически обнаруживается [Spring](https://docs.spring.io/spring-framework/reference/index.html) и используется для настройки 
[Spring AOP](https://docs.spring.io/spring-framework/reference/core/aop.html). 

Аспекты (классы, помеченные [@Aspect](https://www.javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/Aspect.html)) могут иметь методы и поля, как и любой другой класс. Они также могут содержать 
объявления pointcut (что мы и будем тут изучать), advice (совет) и introduction (введение), см. [DOC/AOP_Articles](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_24/DOC/AOP_Articles).

Создав класс помеченный как [@Aspect](https://www.javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/Aspect.html) мы создаем методы, которые уже будут точками среза [Pointcut](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/Pointcut.html) -
это базовая абстракция Spring-а. Pointcut состоит из [ClassFilter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/ClassFilter.html) и [MethodMatcher](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/MethodMatcher.html). 
Pointcut можно комбинировать для создания комбинаций (например, с помощью [ComposablePointcut](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/support/ComposablePointcut.html)).

Конечно, у нас есть возможность реализовать класс имплементирующий Pointcut (и руками создать объект этого класса), но 
гораздо проще использовать аннотацию [@Pointcut](https://www.javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/Pointcut.html), и пометить ей нужный нам метод. Фактически pointcut это предикат и 
должен вернуть boolean значение в случае выполнения/не выполнения заданного условия. Отсюда, хорошим тоном является 
негласное правило называть помеченные [@Pointcut](https://www.javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/Pointcut.html) методы с вопрошающего префикса, например, 'есть ли' - is или 
'имеет ли' - has и т.п. Как [объявлять 'точки среза' - pointcut](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html) мы разобрались.
Теперь необходимо определиться, как передать в аннотацию предикативное выражение. Это делается с помощью соответствующего
синтаксиса [AspectJ](https://eclipse.dev/aspectj/doc/released/index.html):

- execution: для сопоставления точек соединения выполнения метода. Это основной указатель точки, который можно 
использовать при работе с Spring AOP.

- within: ограничивает сопоставление joinpoint внутри определенных типов (выполнение метода, объявленного 
внутри соответствующего типа при использовании Spring AOP).

- this: ограничивает сопоставление joinpoint (выполнение методов при использовании Spring AOP), где ссылка на 
компонент (прокси-объект Spring AOP) является экземпляром заданного типа.

- target: ограничивает сопоставление с joinpoint (выполнение методов при использовании Spring AOP), где целевой 
объект (проксируемый объект приложения) является экземпляром заданного типа.

- args: ограничивает сопоставление с joinpoint (выполнение методов при использовании Spring AOP), где 
аргументами являются экземпляры заданных типов.

- @target: ограничивает сопоставление joinpoint (выполнение методов при использовании Spring AOP), где класс 
исполняемого объекта имеет аннотацию заданного типа.

- @args: ограничивает соответствие joinpoint (выполнение методов при использовании Spring AOP), где тип 
выполнения фактических переданных аргументов имеет аннотации заданных типов.

- @within: ограничивает соответствие joinpoint внутри типов, имеющих данную аннотацию (выполнение методов, 
объявленных в типах с данной аннотацией, при использовании Spring AOP).

- @annotation: ограничивает сопоставление joinpoint, в которых субъект joinpoint (метод, запускаемый в 
Spring AOP) имеет данную аннотацию.

См. док.: 
- [Пакет org.aspectj.lang.annotation](https://www.javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/package-summary.html) ;

________________________________________________________________________________________________________________________
#### Lesson 119 - Before - Advice (@Before).

Совет, рекомендация (advice) — класс, реализующий сквозную функциональность. Существуют различные типа советов:
выполняемые до точки соединения, после или вместо неё, см. [DOC/AOP_Articles](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_24/DOC/AOP_Articles)

Создадим наш первый advice, который должен привнести сквозную логику по логгированию определенных событий при работе
нашего приложения, в частности при обращении к методу *.findById() слоя сервисов - [SecondAspectAndFirstAdvice.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/aop/SecondAspectAndFirstAdvice.java)

Начнем с аннотации [@Before](https://www.javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/Before.html). В данном случае мы планируем внедрить запись событий до исполнения целевого метода, что 
видно из названия аннотации. В параметры этой аннотации мы должны передать нужный pointcut это можно сделать двумя 
способами передав название метода, помеченного как [@Pointcut](https://www.javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/Pointcut.html):

    @Before("anyFindByIdServiceMethod()")

Или сразу передать условие pointcut-a см. [SecondAspectAndFirstAdvice.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/aop/SecondAspectAndFirstAdvice.java):

    @Before("execution(public * spring.oldboy.service.*Service.findById(*))")

Но до тех пор, пока у меня в голове четко не уляжется схема взаимосвязи Aspect-Pointcut-Advice лучше их четко разделять,
все же нагляднее получается.

Расставляем контрольные точки для Debug-a и запускаем приложение, аутентифицируемся и переходим на стр. любого user-a:
- Контрольная точка 1. - Мы попадаем в слой контроллеров [UserController в метод *.findById(id)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/http/controller/UserController.java#L110) см. [DOC/CglibAopProxy.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/CglibAopProxy.jpg)
и мы видим, что обращение идет не к нашему [UserService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/service/UserService.java), а к объекту CglibAopProxy.

- Контрольная точка 2. - Теоретически мы должны были бы сразу скакнуть на слой сервисов, но мы попадаем в наш
[SecondAspectAndFirstAdvice](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/aop/SecondAspectAndFirstAdvice.java), т.е. запустилась сквозная логика до (Before) запуска целевого метода. 

Если посмотреть, какие параметры прилетели в текущий метод, то мы увидим, что находится внутри CglibAopProxy обертки, 
это наш UserService целевой класс см. [DOC/UserServiceInCglibAopProxy.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/UserServiceInCglibAopProxy.jpg). Мы можем увидеть вызываемый метод, переданные 
аргументы и т.д. Но самое главное это список перехватчиков, где мы находим MethodBeforeAdviceInterceptor, там же мы 
видим TransactionalInterceptor, который занимается открытием и закрытием транзакций на уровне сервисов см. 
[@Transactional(readOnly = true) над UserService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/service/UserService.java#L38). Т.е. мы видим, что не только мы внедрили некую сквозную логику, а если 
раскомментировать аннотацию @PreAuthorize("hasAuthority('ADMIN')") над методом *.findById(Long id), то в списке 
перехватчиков появится еще и AuthorizationManagerBeforeMethodInterceptor.

Продолжим...

- Контрольная точка 3. - И только на данном шаге мы попадаем на уровень сервисов в целевой метод и видим в браузере 
искомого user-a.

Пошаговая работа механизма АОП приведена на см. [DOC/AdviceSchemeWorking.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/AdviceSchemeWorking.jpg). Получается следующий алгоритм: целевой 
объект оборачивается в proxy сущность, которая обладаем списком всех применяемых к ней Advice-ов (порядком этих 
Advice-ов можно управлять через порядок следования). Только отработав весь список Advice-ов будет вызван целевой метод,
реального объекта см. [DOC/StartAdviceBeforeTargetMethod.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/StartAdviceBeforeTargetMethod.jpg) 
(результат перед запросом findById() в консоли, в виде имитации запуска сквозной логики, текстовое сообщение 
"invoked findById method").

Заглянем в класс авто-конфигурации AOP ([код класса на GitHub](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/aop/AopAutoConfiguration.java)):

    @AutoConfiguration
    @ConditionalOnProperty(prefix = "spring.aop", name = "auto", havingValue = "true", matchIfMissing = true)
    public class AopAutoConfiguration {

        @Configuration(proxyBeanMethods = false)
        @ConditionalOnClass(Advice.class)
        static class AspectJAutoProxyingConfiguration {
    
            @Configuration(proxyBeanMethods = false)
            @EnableAspectJAutoProxy(proxyTargetClass = false)
            @ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "false")
            static class JdkDynamicAutoProxyConfiguration {
    
            }
    
            @Configuration(proxyBeanMethods = false)
            @EnableAspectJAutoProxy(proxyTargetClass = true)
            @ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
                    matchIfMissing = true)
            static class CglibAutoProxyConfiguration {
    
            }
        }
    ... code ...
    }

Мы видим что данная конфигурация вызывается автоматически, т.к. установлен havingValue = "true", только если мы 
принудительно установим данный параметр в false, то отключим функционал [Spring AOP](https://docs.spring.io/spring-framework/reference/core/aop.html). Так же мы видим, что по-умолчанию
настроен вызов CglibProxy, и тут снова, только наше жаркое желание и настройка руками позволят использовать 
JdkDynamicProxy.

Основное ограничение тут таково, что если бы мы не использовали Spring Boot и его AutoConfiguration, то нам пришлось бы 
явно использовать [@EnableAspectJAutoProxy](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/EnableAspectJAutoProxy.html)(proxyTargetClass = false/true)

См. док.:
- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html) ;
- [@AspectJ support (Spring Framework Doc)](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj.html) ;
- [Пакет org.springframework.boot.autoconfigure.aop](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/aop/package-summary.html) ;

________________________________________________________________________________________________________________________
#### Lesson 120 - JoinPoint - Params.

В предыдущем примере мы внедрили некое подобие сквозной логики, но она не информативна, поэтому возникает задача 
извлечения параметров (информации) из точек внедрения сквозной логики. Так же есть еще момент, мы не всегда можем
знать, в какую точку, в данный момент, был внедрен сквозной код. И объясняется это просто, если мы используем при 
формировании точки среза, например, знак '*', т.е. даже в нашем случае на уровне тех же контроллеров или сервисов 
мы имеем по три класса на слой, и куда произошло внедрение мы можем и не знать. Для этого мы просто попросим предоставить 
нам эту точку внедрения [advice-a](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/advice.html), 
передав соответствующий класс в метод advice-a:

        @Before("anyFindByIdServiceMethod()")
        public void addLogging(JoinPoint joinPoint) {
            log.info("invoked findById method");
        }

Мы хотим передать в параметры [JoinPoint](https://eclipse.dev/aspectj/doc/released/runtime-api/org/aspectj/lang/JoinPoint.html) 
из пакета [org.aspectj.lang](https://eclipse.dev/aspectj/doc/released/runtime-api/org/aspectj/lang/package-summary.html) и уже
из него мы можем получить и целевой-таргет класс и proxy обертку и т.д. [см. список методов в документации](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/JoinPoint.html) и снимок экрана
см. [DOC/JoinPointOptions.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/JoinPointOptions.jpg)

Так же мы можем напрямую внедрять в текущую аннотацию advice-a дополнительные [точки среза (pointcut-ы)](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html), не как названия
конкретных методов (что мы уже попробовали), а как синтаксис точек среза напрямую в аннотацию advice-a, а так же передать
его в аргументы методу: 

        @Before(value = "anyFindByIdServiceMethod() &&" + 
                        "args(id)")
        public void addLoggingWithJoinPointAndParam(JoinPoint joinPoint, 
                                                    Object id) {
            log.info("invoked findById method with id {}", id);
        }

Т.е. тут мы не только указали аргумент, но и передали его в метод, и теперь можем использовать. Таким же образом мы можем
получить и внедрить в метод нужные данные извлекая их из точек среза. Тут мы используем синтаксис точек среза (pointcut-ов)
и тут же внедряем их как параметры метода и также указываем ожидаемые типы получаемых параметров. Однако не со всеми 
синтаксическими структурами pointcut-ов такой подход будет работать, например с 'execution' мы не сможем ничего сделать,
т.к. не просим отдать нам определенные параметры, как с теми же args, target или this и т.д.

Перезапустим приложение и проследим за ходом выполнения запросов к БД. До выполнения такового мы можем заметить, как 
сработали оба наших advice-a, т.к. у них одинаковые точки среза, но во втором мы четко видим в каком классе произошло 
присоединений сквозной логики и какой ID был передан в процессе работы приложения см. [DOC/AdviceWithParam.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/AdviceWithParam.jpg)
________________________________________________________________________________________________________________________
!!! Особенность !!! Существует вероятность, что среда разработки выдаст предупреждение о том что нужно определить имена
                    атрибутов, если мы применим следующий синтаксис (обычно для старых проектов):

                    @Before("anyFindByIdServiceMethod() " +
                            "&& args(id) " +
                            "&& target(service) " +
                            "&& this(serviceProxy)" +
                            "&& @within(transactional)")

И тогда мы можем исправить ситуацию явно их прописав:

                    @Before(value = "anyFindByIdServiceMethod() " +
                                    "&& args(id) " +
                                    "&& target(service) " +
                                    "&& this(serviceProxy)" +
                                    "&& @within(transactional)",
                            argNames = "joinPoint,id,service,serviceProxy,transactional")

Это связанно с тем, что до Java 8 не было возможности сохранять названия параметров после компиляции, и нам приходилось
бы все перечисленные параметры указывать в отдельном параметре - 'argNames' 

!!! Дополнительно !!! Если нам необходим объект [JoinPoint](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/JoinPoint.html) в качестве параметра переданного в метод advice он должен 
                      идти всегда первым. 

См. док.:
- [Aspect Oriented Programming with Spring (old doc)](https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html) ;
- [Пакет org.aspectj.lang](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/package-summary.html) ;

________________________________________________________________________________________________________________________
#### Lesson 121 - After - Advices (@AfterThrowing, @AfterReturning и @After)

Если вспомнить схему, рассмотренную ранее, см. [DOC/AdviceSchemeWorking.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/AdviceSchemeWorking.jpg), то видно, что все advice-ы хранятся в некой 
коллекции. Схематично последовательность вызовов advice-ов можно описать через схему блока try-catch-finally см. 
[DOC/AdviceCallSequence.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/AdviceCallSequence.jpg): 

- перед блоком try срабатывает [@Before](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/annotation/Before.html) advice; 
- в самом блоке try располагаются целевой объект и его вызываемый метод (у нас это service.findById, для примера), 
и конечно, если мы захотим как-то по-особому обработать возвращаемые методом данные внедряется [@AfterReturning](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/annotation/AfterReturning.html) advice 
со своей сквозной логикой; 
- далее в блоке catch помещается [@AfterThrowing](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/annotation/AfterThrowing.html) advice обрабатывающий возможные исключения;
- наконец, в блоке finally размещается [@After](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/annotation/After.html) advice, который будет запущен в любом случае если он есть.

Особенность такой схемы в том, что если мы в целевом методе поймаем исключение, то [@AfterReturning](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/annotation/AfterThrowing.html) advice внедрен не 
будет, а если все пройдет ок, то не сработает [@AfterThrowing](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/annotation/AfterThrowing.html), а вот настоенные @Before advice и @After advice будут 
срабатывать всегда.

Поэтому проверим работу @After* advice-ов, напишем их и внедрим в код см. [SecondAspectAndFirstAdvice.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/aop/SecondAspectAndFirstAdvice.java). Запускаем 
приложение, делаем запрос и видим результат в консоли:

    2024-03-30T23:42:38.634+05:00  INFO 3464 --- [nio-8080-exec-5] s.oldboy.aop.SecondAspectAndFirstAdvice  : 
                                      invoked findById method
    2024-03-30T23:42:38.637+05:00  INFO 3464 --- [nio-8080-exec-5] s.oldboy.aop.SecondAspectAndFirstAdvice  : 
                                      invoked findById method 
                                                  in class spring.oldboy.service.UserService@5fd81b2c, with id 8
    
    Hibernate:
            ... sql запрос ...
    Hibernate:
            ... sql запрос ...

    2024-03-30T23:42:38.647+05:00  INFO 3464 --- [nio-8080-exec-5] s.oldboy.aop.SecondAspectAndFirstAdvice  : 
                                      after returning - invoked findById method in class 
                                                                spring.oldboy.service.UserService@5fd81b2c, 
                                      result Optional[UserReadDto(id=8, 
                                                                  username=Vasil_juk@mail.ru, 
                                                                  birthDate=2017-02-08, 
                                                                  firstname=Jukovsky, 
                                                                  lastname=Vasily, 
                                                                  image=null, 
                                                                  role=USER, 
                                                                  company=CompanyReadDto[id=2, 
                                                                                         name=Meta])]
    2024-03-30T23:42:38.655+05:00  INFO 3464 --- [nio-8080-exec-5] s.oldboy.aop.SecondAspectAndFirstAdvice  : 
                                      after (finally) - invoked findById method in class 
                                                                spring.oldboy.service.UserService@5fd81b2c
    
    Hibernate:
            ... sql запрос ...

Мы видим запуск advice-ов и видим результат их работы, естественно, поскольку у нас не было исключений, то @AfterThrowing 
advice не сработал.

________________________________________________________________________________________________________________________
#### Lesson 122 - Around - Advice ([@Around](https://www.javadoc.io/static/org.aspectj/aspectjrt/1.9.22/org/aspectj/lang/annotation/Around.html)).

Прекрасным примером Around advice может служить [TransactionInterceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/interceptor/TransactionInterceptor.html).
Если [изучить его базовые класс и интерфейсы](https://github.com/spring-projects/spring-framework/blob/main/spring-tx/src/main/java/org/springframework/transaction/interceptor/TransactionInterceptor.java), то мы увидим, что он расширяет [TransactionAspectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/interceptor/TransactionAspectSupport.html) и реализует [MethodInterceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/aopalliance/intercept/MethodInterceptor.html),
который в свою очередь расширяет [Interceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/aopalliance/intercept/Interceptor.html). Ну, а он, 
как ни странно расширяет интерфейс [Advice](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/aopalliance/aop/Advice.html), т.е. мы имеем интерфейс маркер, 
определяющий, что класс его реализующий может быть advice-ом, т.е. может хранить в себе логику, которая работает в АОП.

Вернемся в [TransactionInterceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/interceptor/TransactionInterceptor.html), его
основной метод [*.invoke(MethodInvocation invocation)](https://github.com/spring-projects/spring-framework/blob/main/spring-tx/src/main/java/org/springframework/transaction/interceptor/TransactionInterceptor.java#L112) - этот метод необходимо реализовать для выполнения дополнительных 
процедур до и после вызова. Наиболее интересна его внутренняя реализация и в частности метод 
[*.invokeWithinTransaction(Method method, @Nullable Class<?> targetClass, final InvocationCallback invocation)](https://github.com/spring-projects/spring-framework/blob/main/spring-tx/src/main/java/org/springframework/transaction/interceptor/TransactionAspectSupport.java#L347), класса
[TransactionAspectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/interceptor/TransactionAspectSupport.html),
внутренняя структура которого очень сложна, но наглядно содержит логику обработки перехватов и принцип работы 
Around advice-ов:

        . . . some code . . .
        Object retVal;
        try {
            /* 
            Это around advice, который вызывает следующий перехватчик 
            в цепочке. Обычно это приводит к вызову целевого объекта.
            */
            retVal = invocation.proceedWithInvocation();
        }
        catch (Throwable ex) {
            /* Исключение на случай ошибки целевого вызова */
            completeTransactionAfterThrowing(txInfo, ex);
            throw ex;
        }
        finally {
            cleanupTransactionInfo(txInfo);
        }
        . . . some code . . .

        commitTransactionAfterReturning(txInfo);
        return retVal;
        
        . . . some code . . .

Тут, в случае нормальной работы целевого метода, возвращаемое им значение сохраняется в retVal и возвращается, а 
транзакция коммитится, но если произошла ошибка в целевом методе (перехват исключения) транзакция завершается (помним, 
что мы рассматриваем в качестве примера перехватчик транзакций). Итак, если мы поймали исключение, то происходит откат 
(rollback) транзакции, в методе [*.completeTransactionAfterThrowing()](https://github.com/spring-projects/spring-framework/blob/main/spring-tx/src/main/java/org/springframework/transaction/interceptor/TransactionAspectSupport.java#L1069), а исключение пробрасывается дальше, чтобы логика 
других методов могли с ней работать.

В этом ключевое отличие Around advice-a, он полностью оборачивает своей логикой логику целевого метода, и фактически 
определяет его жизнедеятельность, даже может прервать его работу. Мы видели, что он запускает некие процессы ДО вызова
целевого метода, может прервать вызов этого метода исходя из неких условий (необязательно исключение), обрабатывает 
исключения в случае ошибки, и самое главное, имея доступ к итогам работы целевого метода, может скорректировать их ПОСЛЕ.

Напишем наш Around advice - [ThirdAspectAndAroundAdvice.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/aop/ThirdAspectAndAroundAdvice.java). Данный advice при определенной логике кода, может полностью 
заменить все наши предыдущие advice-ы описанные схемой см. [DOC/AdviceCallSequence.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/DOC/AdviceCallSequence.jpg). Смотреть комментарии в самом классе
советчике (advice-e). Обратить внимание на работу [ProceedingJoinPoint](https://www.javadoc.io/doc/org.aspectj/aspectjrt/1.7.2/org/aspectj/lang/ProceedingJoinPoint.html), 
т.к. если бы мы использовали обычный [JoinPoint](https://javadoc.io/doc/org.aspectj/aspectjweaver/1.9.2/org/aspectj/lang/JoinPoint.html) то не имели бы возможности вызвать функционал следующего в цепочке перехватчика, либо реального объекта.

Запускаем приложение и видим результат в консоли:

    2024-03-31T21:49:44.215+05:00  INFO 9180 --- [nio-8080-exec-5] s.oldboy.aop.SecondAspectAndFirstAdvice  : 
                BEFORE invoked findById method without param
    2024-03-31T21:49:44.219+05:00  INFO 9180 --- [nio-8080-exec-5] s.oldboy.aop.SecondAspectAndFirstAdvice  : 
                BEFORE invoked findById method in class spring.oldboy.service.UserService@2fb0f432, with id 10
    2024-03-31T21:49:44.219+05:00  INFO 9180 --- [nio-8080-exec-5] s.oldboy.aop.ThirdAspectAndAroundAdvice  : 
                AROUND before - invoked findById method in class spring.oldboy.service.UserService@2fb0f432, with id 10
       
    Hibernate:
    ... sql запрос ...

    2024-03-31T21:49:44.228+05:00  INFO 9180 --- [nio-8080-exec-5] s.oldboy.aop.ThirdAspectAndAroundAdvice  : 
                AROUND after returning - invoked findById method in class spring.oldboy.service.UserService@2fb0f432, 
                                         result Optional[UserReadDto(id=10, 
                                                                     username=Tolya1324@bk.ru, 
                                                                     birthDate=1999-02-18, 
                                                                     firstname=Tolya, 
                                                                     lastname=Dolgiy, 
                                                                     image=null, 
                                                                     role=USER, 
                                                                     company=CompanyReadDto[id=1, name=Google])]
    2024-03-31T21:49:44.238+05:00  INFO 9180 --- [nio-8080-exec-5] s.oldboy.aop.ThirdAspectAndAroundAdvice  : 
                AROUND after (finally) - invoked findById method in class spring.oldboy.service.UserService@2fb0f432
    2024-03-31T21:49:44.238+05:00  INFO 9180 --- [nio-8080-exec-5] s.oldboy.aop.SecondAspectAndFirstAdvice  : 
                AFTER RETURNING - invoked findById method in class spring.oldboy.service.UserService@2fb0f432, 
                                          result Optional[UserReadDto(id=10, 
                                                                      username=Tolya1324@bk.ru, 
                                                                      birthDate=1999-02-18, 
                                                                      firstname=Tolya, 
                                                                      lastname=Dolgiy, 
                                                                      image=null, 
                                                                      role=USER, 
                                                                      company=CompanyReadDto[id=1, name=Google])]
    2024-03-31T21:49:44.238+05:00  INFO 9180 --- [nio-8080-exec-5] s.oldboy.aop.SecondAspectAndFirstAdvice  : 
                AFTER (FINALLY) - invoked findById method in class spring.oldboy.service.UserService@2fb0f432
    
    Hibernate:
    ... sql запрос ...

Стоит обратить внимание, что порядок вызова advice-ов четко не соблюдается, но данный вопрос решается при помощи 
упорядочивания и расстановки приоритетов для advice-ов, см. материал ниже.

________________________________________________________________________________________________________________________
#### Lesson 123 - Best-Practices in AOP (Рекомендации по использованию механизма АОП).

Все точки среза pointcut-ы можно разделить на три группы:
- на уровне классов, когда срез анализируется и формируется исходя из заданного класса, они самые быстрые;
- на уровне методов (без конкретизации), когда сканируется вся структура приложения и внутри классов ищется 
соответствующий срез, это самый медленный тип pointcut-ов и желательно их объединять с другими срезами, для 
ограничения зоны сканирования;
- на уровне методов (с конкретизацией), когда точка среза прописывается максимально точно, начиная от класса, метода и 
до аргументов передаваемых в метод, в некоторых документах считается самым основным вариантом создания pointcut;

1. Технология АОП по возможности не должна затормаживать работу приложения (сервиса), поэтому лучшим способом формирования 
точек среза считается вариант, когда pointcut максимально конкретизирован и на уровне класса и на уровне методов и даже 
аргументов. Такое точное определение точки среза позволяет очень быстро внедряться advice-ам в нужную joinpoint.

2. Да, @Around advice очень мощный инструмент, но хорошим тоном считается создание и применение узкоспециализированного 
advice-a (советчика), который однозначно решает нашу проблему. Т.е. еще раз, в каждой конкретной ситуации применяется 
конкретный advice (@Before, @After и т.д.), а не вся огневая мощь @Around advice. Необходима минимальная инвазия, 
точечно и конкретно.

3. Желательно логику advice-ов разделять по отдельным классам Aspect-ам. Например, всю логику по логированию процессов в 
одном Aspect-те, логику обработки исключений, логику внедряющуюся в процесс безопасности и т.д. нужно выделять в 
отдельные для каждого подпроцесса Aspect-ы.

4. Разделение advice-ов по отдельным классам позволяет управлять порядком их запуска используя аннотацию [@Order(n)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html), где
n - это значение является необязательным и представляет собой значение заказа, определенное в интерфейсе Ordered.

Фактическое значение order можно интерпретировать как расстановку приоритетов, при этом первый объект (с наименьшим 
значением порядка) имеет наивысший приоритет. Значением по умолчанию является Ordered.LOWEST_PRECEDENCE, указывающее 
наименьший приоритет (проигрыш любому другому указанному значению порядка), Ordered.HIGHEST_PRECEDENCE константа для 
наивысшего значения приоритета.

Внедрив расстановку приоритетов, см. аннотации над [SecondAspectAndFirstAdvice.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/aop/SecondAspectAndFirstAdvice.java#L19) и [ThirdAspectAndAroundAdvice.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_24/src/main/java/spring/oldboy/aop/ThirdAspectAndAroundAdvice.java#L23), мы получим 
гарантированную последовательность запуска Advice-ов, каждый раз при обращении к целевому методу.

________________________________________________________________________________________________________________________
#### Дополнительный материал (ENG):
- [The AspectJ Programming Guide](https://eclipse.dev/aspectj/doc/released/progguide/index.html) ;
- [The AspectJ 5 Development Kit Developer's Notebook](https://eclipse.dev/aspectj/doc/released/adk15notebook/index.html) ;
- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html) ;

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