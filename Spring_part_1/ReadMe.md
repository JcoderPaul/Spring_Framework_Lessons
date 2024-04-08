[Spring](https://docs.spring.io/spring-framework/reference/index.html) - это самый распространенный фреймворк для написания Java приложений. Первая версия вышла еще в далеком 
2003 году, после чего Spring претерпел очень много изменений и стал по-настоящему монструозным. 

Это повлекло разбиение его на множество отдельных модулей, например:
- [Spring Core](https://docs.spring.io/spring-framework/reference/core.html) - ядро фреймворка, на чем держатся все остальные модули.
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) - это тот же Spring, просто с возможностью авто-конфигурирования модулей. 
- [Spring Test](https://docs.spring.io/spring-framework/reference/testing.html#testing) - каждое приложение должно быть хорошо покрыто разными уровнями тестирования, и этот модуль 
предоставляет функционал для этого, поднимая Spring Context и сразу добавляя необходимые зависимости в проект.
- [Spring Data](https://docs.spring.io/spring-framework/reference/data-access.html) - модуль для работы с самыми распространенными базами данных, как реляционными, так и нереляционными (NoSQL).
- [Spring Web](https://docs.spring.io/spring-framework/reference/web.html) - написание веб приложений, в текущий момент времени все приложения общаются друг с другом по сети, 
поэтому Web - это неотъемлемая часть изучения каждого разработчика.
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html) - работа по сети заставляет улучшать авторизацию и аутентификацию в приложении, повышать его 
безопасность, а значит необходимо готовое решение из коробки, что и предоставляет нам Spring в виде модуля 
Security.

Каждый последующий раздел будет рассматривать части и целое таких разделов Spring-a, как:
- Spring Core;
- Spring Test;
- Spring Web;
- Spring Security;
- Spring Boot;
- Spring Data;
- Spring Validation;
- Spring AOP;

________________________________________________________________________________________________________________________
### Spring (Core) lessons part 1 - XML-based Configuration.

В папке [DOC](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_1/DOC) sql-скрипты и др. полезные файлы (статьи по IOC и DI).

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку:
Шаг 1 - в файле build.gradle добавим необходимые нам зависимости: 

    /* Подключим Spring-core и Spring-context. */
    implementation 'org.springframework:spring-core:5.3.22'
    implementation 'org.springframework:spring-context:5.3.22'

________________________________________________________________________________________________________________________

Spring Framework — универсальный фреймворк с открытым исходным кодом для Java-платформы.

Spring Framework обеспечивает комплексную модель разработки и конфигурации для современных
бизнес-приложений на Java — на любых платформах. Ключевой элемент Spring — поддержка
инфраструктуры на уровне приложения: основное внимание уделяется взаимосвязей отдельных 
частей бизнес-приложений, поэтому разработчики могут сосредоточиться на бизнес-логике без 
лишних настроек в зависимости от среды исполнения.

Spring Framework может быть рассмотрен как коллекция меньших фреймворков или фреймворков во
фреймворке. Большинство этих фреймворков может работать независимо друг от друга, однако они
обеспечивают большую функциональность при совместном их использовании. Эти фреймворки делятся
на структурные элементы типовых комплексных приложений:

- Inversion of Control-контейнер: конфигурирование компонентов приложений и управление жизненным
  циклом Java-объектов.
- Фреймворк аспектно-ориентированного программирования: работает с функциональностью, которая не
  может быть реализована возможностями объектно-ориентированного программирования на Java без потерь.
- Фреймворк доступа к данным: работает с системами управления реляционными базами данных на
  Java-платформе, используя JDBC- и ORM-средства и обеспечивая решения задач, которые повторяются в
  большом числе Java-based environments (окружения на основе Java).
- Фреймворк управления транзакциями: координация различных API управления транзакциями и инструментарий
  настраиваемого управления транзакциями для объектов Java.
- Фреймворк MVC: каркас, основанный на HTTP и сервлетах, предоставляющий множество возможностей для
  расширения и настройки (customization).
- Фреймворк удалённого доступа: конфигурируемая передача Java-объектов через сеть в стиле RPC,
  поддерживающая RMI, CORBA, HTTP-based протоколы, включая web-сервисы (SOAP).
- Фреймворк аутентификации и авторизации: конфигурируемый инструментарий процессов аутентификации и
  авторизации, поддерживающий много популярных и ставших индустриальными стандартами протоколов,
  инструментов, практик через дочерний проект Spring Security (ранее известный как Acegi).
- Фреймворк удалённого управления: конфигурируемое представление и управление Java-объектами для
  локальной или удалённой конфигурации с помощью JMX.
- Фреймворк работы с сообщениями: конфигурируемая регистрация объектов-слушателей сообщений для
  прозрачной обработки сообщений из очереди сообщений с помощью JMS, улучшенная отправка сообщений
  по стандарту JMS API.
- Тестирование: каркас, поддерживающий классы для написания модульных и интеграционных тестов.

Все рассмотренные вопросы данной части имеют практическое и понятийное значение для дальнейшего 
понимания работы экосистемы Spring-а. Некоторые уроки не имеют практической реализации в коде Java 
(3 и 8), но имеют краткое описание.

________________________________________________________________________________________________________________________
#### Lesson 1 - Схема зависимостей объектов в коде (простой пример)

Зависимости объектов см. [DOC/ObjectDependencies.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/DOC/ObjectDependencies.jpg) ([UserService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/service/UserService.java) зависит от [UserRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/repository/UserRepository.java),
который зависит от [Connection pool](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/pool/StarterConnectionPool.java)).

- [ClassDependenciesDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/lesson_1/ClassDependenciesDemo.java) - пример зависимости объектов;
- [IoCDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/lesson_1/IoCDemo.java) - пример инверсии контроля (IoC);

- [DOC/DI](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_1/DOC/DI) - что такое внедрение зависимостей;
- [DOC/IOC](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_1/DOC/IOC) - что такое инверсия контроля;
- [DOC/SpringAppScheme](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/DOC/SpringAppScheme/SpringAppScheme.jpg) - схема классического Spring приложения и способы конфигурирования Bean Definition;

________________________________________________________________________________________________________________________
#### Lesson 2 - Внедрение параметров в Bean-ы через конструктор при помощи application.xml

Создаем в папке ресурсов [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml) и уже там будем настраивать наши Bean-ы (Bean Definition).

Примеры, как настраивать Bean Definition при различных видах полей в конструкторах классах (чертежах) будущих bean-ов.

________________________________________________________________________________________________________________________
#### Lesson 3 - Внедрение одних Bean-ов в другие Bean-ы через конструктор при помощи application.xml

Внедрение ссылки на bean в поле другого bean-a:

        <constructor-arg index="1" name="login" type="java.lang.String" ref="login"/>

Внедрение ссылки на bean в List другого bean-a:

        <list>
            <value>--arg1=value1</value>
            <value>--arg2=value2</value>
            <ref bean="driver"/>
        </list>

Внедрение ссылки на bean в Map другого bean-a:
        
        <map>
          <entry key="url" value="postgresurl"/>
          <entry key="password" value="123"/>
          <entry key="driver" value-ref="driver"/>
        </map>

Поскольку все bean-ы по-умолчанию singleton-ы, то ссылки во всех случаях в пределах одного контекста
будут на один и тот же объект (bean).

________________________________________________________________________________________________________________________
#### Lesson 4 - factory-method

Допустим у нас [FirmRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/repository/FirmRepository.java) зависит от [AdvancedConnectionPool.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/pool/AdvancedConnectionPool.java) имеет приватный конструктор.
В [resources/application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml) мы прописываем эту зависимость:

    <bean id="firmRepository" class="spring.oldboy.repository.FirmRepository" factory-method="of">
            <constructor-arg ref="pool3"/>
    </bean>

- [FactoryInjectionDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/lesson_4/FactoryInjectionDemo.java) - демо приложение для отображения взаимодействия этих классов, без явного
создания и прописи зависимостей в самом приложении (т.е. создали классы, в *.xml прописали связь, пользуемся).

________________________________________________________________________________________________________________________
#### Lesson 5 - Внедрение параметров через сеттеры (свойства - property)

Класс [SetterInConnectionPool.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/pool/SetterInConnectionPool.java) имеет одно не final поле, вот его мы и сможем задавать через сеттер.
В файле [resources/application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml) создадим bean definition id="poolSetIn" в котором мы имеем возможность
задать содержимое нашего Map, как через конструктор, так и через сеттер (properties):

        <!-- Через конструктор передаем null -->
        <constructor-arg index="4" name="properties" type="java.util.Map">
            <null/>
        </constructor-arg>
        <!-- Задаем те же параметры через сеттер (как property), что передавали через конструктор -->
        <property name="properties">
            <map>
                <entry key="url" value="postgresurl"/>
                <entry key="password" value="123"/>
                <entry key="driver" value-ref="driver"/>
            </map>
        </property>

- [SetterInjectionDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/lesson_5/SetterInjectionDemo.java) - демо приложение для изучения варианта инъекции зависимости через сеттеры.

________________________________________________________________________________________________________________________
#### Lesson 6 - Beans Scope

- [Singleton Scope](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html#beans-factory-scopes-singleton) - Когда мы определяем bean-компонент как singleton (обычно это default - по-умолчанию),
контейнер создает один экземпляр этого bean-компонента. Все запросы для этого имени компонента будут возвращать 
один и тот же объект, который кэшируется. Любые изменения объекта будут отражены во всех ссылках на 
компонент. Эта область видимости является значением по умолчанию, если не указана другая область.

В итоге при запросе singleton bean-a spring берет наши bean definition и прогоняет их через всю цепочку
жизненного цикла [DOC/BeansLifeCycle/BeansLifeCycle.png](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/DOC/BeansLifeCycle/BeansLifeCycle.png) и далее кеширует полученный bean и возвращает его 
на любой запрос.

- [Prototype Scope](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html#beans-factory-scopes-prototype) - Bean с областью действия prototype (прототип) будет возвращать другой экземпляр 
каждый раз, когда он запрашивается из контейнера. Он определяется установкой прототипа значения в 
аннотацию [@Scope](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/Scope.html) в определении bean-компонента (или в resources/application.xml, как у нас).

В данной ситуации spring также обращается к bean definition и снова прогоняет bean по LifeCycle цепочке
и отдает его по требованию, но не кеширует, а каждый раз повторяет цикл.

Пример работы разной области видимости: [PrototypeBeansDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/lesson_6/PrototypeBeansDemo.java) (в [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml) bean с id="firmService" имеет scope="prototype")

________________________________________________________________________________________________________________________
#### Lesson 7 - Beans Initialisation and Destruction CallBacks

Spring поставляется с различными вариантами вызова метода инициализации. В общем, нам нужно 
инициализировать некоторые ресурсы во время создания bean-a. Мы можем добиться этого (см. 
[DOC/BeansLifeCycle/LifeCycleCallbacks.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/DOC/BeansLifeCycle/LifeCycleCallbacks.jpg)):
- аннотация [@PostConstruct](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/postconstruct-and-predestroy-annotations.html);
- внедрение интерфейс [org.springframework.beans.factory.InitializingBean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/InitializingBean.html) в наш Spring bean; 
- использование *.init() метода и настройки [resources/application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml); 

Мы можем выбрать удобный нам способ, однако, рекомендуется использовать аннотацию. Но для демонстрации мы
применим *.init() метод.

Как говорится, что создано рано или поздно будет уничтожено. Мы инициализируем наши ресурсы во время 
создания bean-компонента Spring. И естественно нам нужно закрыть все открытые ресурсы непосредственно 
перед уничтожением этого bean-компонента, весь код, связанный с очисткой, будет частью метода destroy. 
Мы можем добиться этого, тремя способами:
- аннотация [@PreDestroy](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/postconstruct-and-predestroy-annotations.html);
- реализовав интерфейс [org.springframework.beans.factory.DisposableBean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/DisposableBean.html) в нашем Spring bean-е;
- использование *.destroy() метода и настройки [resources/application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml); 

Попробуем всего помаленьку.

См. [resources/application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml):
       
    <bean id="initCallBackPool" name="intCbPool" class="spring.oldboy.pool.InitCallBackPool"
          init-method="init"
          destroy-method="destroy"
          scope="singleton">

________________________________________________________________________________________________________________________
#### !!! Важный момент !!! 
#### PreDestroy или Destruction CallBacks вызываются ТОЛЬКО у Singleton-bean-ов (НЕ у Prototype), т.к. у IoC контейнера нет ссылки на prototype bean-ы !!!
________________________________________________________________________________________________________________________

Естественно лучше использовать только одну из методик вызова обратных вызовов (или аннотация - предпочтительно, или 
наследование интерфейса, или методы *.init(), *.destroy())

________________________________________________________________________________________________________________________
#### Lesson 8 - Внедрение зависимостей из файла свойств (Injection from Properties Files)

Для использования такой возможности нам нужен файл из которого будут браться свойства, создадим: [application.properties](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.properties). 

Теперь в [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml) нам нужно указать ссылку на него, но в виде bean-a:

    <bean class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">
        <property name="locations" value="classpath:application.properties"/>
    </bean>

Однако, для упрощения жизни разработчикам есть еще вариант с использованием контекста:

    <context:property-placeholder location="classpath:application.properties"/>

И теперь нам доступен [Expression Language (EL)](https://docs.oracle.com/javaee/6/tutorial/doc/gjddd.html) — скриптовый язык выражений, который позволяет получить 
доступ к Java компонентам (JavaBeans) из JSP. Начиная с JSP 2.0 используется внутри JSP тегов для 
отделения Java кода от JSP для обеспечения лёгкого доступа к Java компонентам. 
Как пример см. [https://github.com/JcoderPaul/HTTP_Servlets_Java_EE](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE).

Но, Spring разработчики пошли дальше и создали [Spring Expression Language](https://docs.spring.io/spring-framework/reference/core/expressions.html) (кратко - ["SpEL"](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/expressions.html)) – это мощный 
язык выражений, который поддерживает запросы и манипуляции с графом объектов во время выполнения 
программы. И теперь он нам доступен. Синтаксис языка похож на EL, но предлагает дополнительные 
возможности, в первую очередь вызов методов и базовые функции шаблонизации строк.

Хотя существует несколько других языков выражений Java - OGNL, MVEL, JBoss EL и другие - язык выражений 
[Spring Expression Language](https://docs.spring.io/spring-framework/reference/core/expressions.html) был создан для того, чтобы дать сообществу Spring единый язык выражений с 
надлежащей поддержкой, который можно использовать во всех продуктах портфеля Spring. 

Язык выражений поддерживает следующую функциональность:
- Литеральные выражения (литералы);
- Булевы операторы и операторы отношений;
- Регулярные выражения;
- Класс-выражения;
- Доступ к свойствам, массивам, спискам и ассоциативным массивам;
- Обращение к методам;
- Операторы отношений;
- Присваивание;
- Вызов конструкторов;
- Ссылки на бины;
- Построение массива;
- Встраиваемые списки;
- Встраиваемые ассоциативные массивы;
- Тернарный оператор;
- Переменные;
- Определяемые пользователем функции;
- Проекция коллекций;
- Выборка коллекций;
- Шаблонные выражения.

************************************************************************************************************************
Документация: 
- [https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html](https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html)
- [https://docs.spring.io/spring-framework/reference/](https://docs.spring.io/spring-framework/reference/)
************************************************************************************************************************

Например (см. [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/resources/application.xml)):

        <constructor-arg index="4" name="properties" type="java.util.Map">
            <map>
            <entry key="url" value="postgresurl"/>
            <entry key="password" value="123"/>
            <entry key="driver" value="#{driver.substring(3)}"/>
            <entry key="test" value="#{driver.length() > 10}"/>
            <entry key="test1" value="#{driver.length() > T(Math).random() * 10}"/>
            <entry key="hosts" value="#{'${db.hosts}'.split(',')}"/>
            <entry key="currentUser" value="#{systemProperties['user.name']}"/>
            <entry key="currentUser" value="${user.name}"/>
            </map>
        </constructor-arg>

________________________________________________________________________________________________________________________
#### Lesson 9 - BeanFactoryPostProcessor

Точка расширения - [org.springframework.beans.factory.config.BeanFactoryPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html). Семантика этого 
интерфейса аналогична семантике [BeanPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html), с одним существенным отличием: 
[BeanFactoryPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html) работает с конфигурационными метаданными bean-ов. То есть IoC-контейнер Spring 
позволяет BeanFactoryPostProcessor считывать конфигурационные метаданные и потенциально изменять их до 
того, как контейнер создаст экземпляры каких-либо bean-ов, помимо экземпляров BeanFactoryPostProcessor.

Можно сконфигурировать несколько экземпляров BeanFactoryPostProcessor, а также контролировать порядок 
запуска этих экземпляров BeanFactoryPostProcessor, установив свойство order. Однако мы можем установить 
это свойство только в том случае, если BeanFactoryPostProcessor реализует интерфейс [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html). Если мы 
пишем свой собственный BeanFactoryPostProcessor, то следует рассмотреть реализацию интерфейса [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html) 
или [PriorityOrder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/PriorityOrdered.html).

Пост-процессор фабрики bean-ов автоматически запускается, если он объявляется внутри [ApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContext.html), 
для применения изменений к конфигурационным метаданным, определяющим контейнер. Spring содержит ряд 
предопределенных постпроцессоров фабрики bean-ов, такие, как [PropertyOverrideConfigurer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/PropertyOverrideConfigurer.html) и 
[PropertySourcesPlaceholderConfigurer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/PropertySourcesPlaceholderConfigurer.html). Также можно использовать специальный BeanFactoryPostProcessor, 
например, для регистрации специальных редакторов свойств.

ApplicationContext автоматически определяет все развернутые в нем beam-ы, которые реализуют интерфейс 
BeanFactoryPostProcessor. Он использует эти bean-ы в качестве постпроцессоров фабрики bean-в в 
соответствующий момент. Мы можем развернуть эти bean-ы постпроцессора так же, как и любые другие bean-ы.

- [bean_factory_pp](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_1/src/main/java/spring/oldboy/bean_factory_pp) - папка с фабричными пост-процессорами, для простой демонстрации.
- [BeanFactoryPPDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_1/src/main/java/spring/oldboy/lesson_9/BeanFactoryPPDemo.java) - демо-приложение, который позволяет увидеть, как меняя возвращаемый объект 
пост-процессорного метода [*.getOrder()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#getOrder()) можно изменить порядок вызова набора пост-процессоров.