### Spring (Core) lessons part 2 - Annotation-based Configuration.

В папке DOC sql-скрипты и др. полезные файлы.

Док. для изучения: 
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework)

________________________________________________________________________________________________________________________
Для начала проведем предварительную подготовку:

Шаг 1. - в файле build.gradle добавим необходимые нам зависимости: 

    /* Подключим Spring-core и Spring-context. */
    implementation 'org.springframework:spring-core:5.3.22'
    implementation 'org.springframework:spring-context:5.3.22'

Шаг 2. - подключаем Jakarta Annotation API:

    implementation 'jakarta.annotation:jakarta.annotation-api:1.3.5'

Шаг 3. - для того, чтобы обрабатывать аннотации, мы добавляем в наш ApplicationContext служебный bean, он используется для внутренней реализации жизненного цикла bean-ов:

    <bean class="org.springframework.context.annotation.CommonAnnotationBeanPostProcessor"/>

Однако, для упрощения жизни разработчика стал использоваться:
    
    <context:annotation-config/>

Который добавляет еще 4-и важных bean-a (см. DOC/Context/Context_Annotation_Config.jpg): 

________________________________________________________________________________________________________________________
#### Lesson 10 - [@PostConstruct и @PreDestroy](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/postconstruct-and-predestroy-annotations.html)

У нашего initCallBackPool в application.xml уберем:
    
    init-method="init"
    destroy-method="destroy"

Поскольку мы добавили зависимость 'jakarta.annotation' мы можем аннотировать нужные нам методы класса 
(bean) InitCallBackPool.java.

________________________________________________________________________________________________________________________
#### Lesson 11 - [BeansPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html) (теория).

И так полный жизненный цикл Bean-ов состоит из (см. DOC/Context/Bean_LifeCycle_With_PostProcessors.jpg):
- Представления [Bean Definition](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanDefinition.html)s, которые поступают в [IoC Container](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/beans.html) (передача чертежа в контейнер);
- IoC Container направляет 'конструктор bean-a' в [BeanFactoryPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html) (пост-процессоров может быть 
много и они необходимы, чтобы добавить дополнительные параметры к нашему 'bean чертежу', что называется - 
докрутить до кондиции);
- Далее в работу вступает сортировщик 'bean чертежей' - SortedBeanDefinitions. Поскольку bean-ы связанны,
то сортировщик отвечает за правильную последовательность создания bean-ов. Т.е. не должно возникнуть 
ситуации, когда bean зависимый от другого bean-a вдруг был создан раньше того от которого зависит. 
- После окончания сортировки, 'чертежи bean-ов' по одному (в простом цикле) поступают на этап 
инициализации будущих bean-ов.
- Вызывается конструктор bean-a согласно указанного класса;
- Далее вызываются соответствующие сеттеры;
- ПЕРЕД ИНИЦИАЛИЗАЦИЕЙ используется [Before Initialisation PostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html) метод - поскольку мы уже вызвали и 
конструктор и сеттеры, то в пост-процессор попадает bean, а не его чертеж (bean definition). Т.е. на
данном этапе мы уже видим зависимости нашего bean-a и можем внести нужные дополнительные изменения 
(внести зависимости, обработать аннотации);
- После обработки всех пост-процессоров вызывается InitializationCallBacks или последний 
пост-процессор [@PostConstruct](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/annotation/PostConstruct.html);
- После, у тех же самых bean-ов с тем же именем вызывается [After Initialisation PostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html) метод. На данном 
этапе (ТОЛЬКО НА ДАННОМ ЭТАПЕ) могут происходить подмена одних bean-ов другими, создание прокси и т.д.

Чертеж bean-a содержит четкие зависимости на bean-ы конкретных классов, значит собирая его, перед 
этапом инициализации, нежелательно внедрять всякого рода прокси и заниматься подменой одних bean-ов 
на другие (при неумелом использовании такого подхода можно получить каскад исключений). Именно по 
этому окончательную доводку (докрут, донастройку) bean-a производят на этапе AfterInitialisation;

- ТОЛЬКО после этого мы получаем (в [IoC контейнере](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/beans.html)) наши bean-ы И ТОЛЬКО ЕСЛИ ЭТО SINGLETON он остается 
в IoC контейнере. В случае если это другая область [видимости (scope)](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/beans.html#beans-factory-scopes) то bean сразу возвращается по месту 
требования.
- Если наш bean [singleton scope](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/beans.html#beans-factory-scopes-singleton), то он остается в IoC контейнере и при завершении (закрытии) контекста
вызывается [Destruction CallBack](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/beans.html#beans-factory-lifecycle-disposablebean) (наша [@PreDestroy](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/annotation/PreDestroy.html)).

!!! А поскольку все пост-процессоры тоже bean-ы, то они проходят весь это процесс, НО САМЫМИ ПЕРВЫМИ !!!

________________________________________________________________________________________________________________________
#### Lesson 12 - Custom Bean PostProcessor (практика).

Постепенный переход от *.XML конфигурирования bean-ов, к настройке их при помощи аннотаций.

Для начала создадим свои аннотации:
- [InjectBean.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/InjectBean.java) - данная аннотация помечает поле в которое необходимо внедрить зависимость. 
- [DropBeanBeforeInit.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/DropBeanBeforeInit.java) - данная аннотация помечает класс целиком, ее обрабатывает наш самописный пост-процессор,
который наследует от BeanPostProcessor-a и реализует метод [*.postProcessBeforeInitialization()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html#postProcessBeforeInitialization(java.lang.Object,java.lang.String)), т.е. пытается внедрить
зависимости до этапа инициализации bean-a. При грубом вмешательстве в процесс создания bean-a, а затем при попытке
его получения можно словить исключение, что и демонстрирует - [DropBeanFactoryExceptionDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/lesson_12/DropBeanFactoryExceptionDemo.java)
- [MyOwnTransaction.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/MyOwnTransaction.java) - данная аннотация очень похожа на предыдущую, однако, в ней, внедрение зависимостей в виде
proxy и т.п. происходит в уже проинициализированный bean, т.е. в работу идет метод - [*.postProcessAfterInitialization()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html#postProcessAfterInitialization(java.lang.Object,java.lang.String)).

Для обработки, каждой нашей аннотации написан свой пост-процессор с реализацией требуемых методов:
- [InjectBeanPostProcessor.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/InjectBeanPostProcessor.java) - для [InjectBean.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/InjectBean.java) (внимательно изучить комментарии внутри класса, 
для понимания происходящего 'волшебства');
- [DropBeanBeforeInitialisationPostProcessor.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/DropBeanBeforeInitialisationPostProcessor.java) - для [DropBeanBeforeInit.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/DropBeanBeforeInit.java);
- [MyOwnTransactionBeanPostProcessor.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/MyOwnTransactionBeanPostProcessor.java) - для [MyOwnTransaction.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/MyOwnTransaction.java);

Для демонстрации работы пост-процессоров см.:
- [MyInjectBeanDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/lesson_12/MyInjectBeanDemo.java) - мини-демонстрация работы интерфейса [InjectBean.java (@InjectBean)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/InjectBean.java) и пост-процессора 
[InjectBeanPostProcessor.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/InjectBeanPostProcessor.java), который и будет внедрять зависимость вместо [application.xml](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/resources/application.xml) в [UserRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/repository/UserRepository.java) bean,
лучше исследовать в debug-е;
- [DropBeanFactoryExceptionDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/lesson_12/DropBeanFactoryExceptionDemo.java) - демонстрация падения [BeanFactory](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/BeanFactory.html) при некорректном внедрении зависимостей 
на этапе пред-инициализации см. [DOC/Context/Bean_LifeCycle_With_PostProcessors.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/DOC/Context/Bean_LifeCycle_With_PostProcessors.jpg) (см. комментарии)
- [MyOwnTransactionDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/lesson_12/MyOwnTransactionDemo.java) - демонстрация работы метода после инициализации bean-a.
- [AuditingAnnotationDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/lesson_12/AuditingAnnotationDemo.java) - демонстрация работы аннотации [@Auditing](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/Auditing.java) и обработчика [AuditingBeanPostProcessor.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/bean_post_processor/AuditingBeanPostProcessor.java), 
а точнее одновременной работы двух аннотаций над одним классом [CompanyRepository](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/repository/CompanyRepository.java).

Не забываем прописывать наши пост-процессоры и bean-ы в *.XML, т.к. пока мы еще полностью не перешли на аннотирование 
наших bean-ов.

Еще раз, повторимся, все наши пост-процессоры подписаны на интерфейс [BeanPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html) и должны переопределить 
его методы:
- [*.postProcessBeforeInitialization](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html#postProcessBeforeInitialization(java.lang.Object,java.lang.String));
- [*.postProcessAfterInitialization](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html#postProcessAfterInitialization(java.lang.Object,java.lang.String));

Каждый bean-чертеж (bean definition) последовательно проходит этапы (см. [DOC/Context/Bean_LifeCycle_With_PostProcessors.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/DOC/Context/Bean_LifeCycle_With_PostProcessors.jpg)):
- Вызов конструктора;
- Вызов сеттеров;
- Обработку в bean-post-processor-before-initialisation (и это *.postProcessBeforeInitialization методы) 
всех пост-процессоров в [IoC контейнере](https://docs.spring.io/spring-framework/reference/core/beans.html). Т.е. если у нас есть 5-ть пост-процессоров с 'before' методами, 
каждый bean перед инициализацией будет обработан, каждым из 5-и 'before' методов;
- Вызов Initialization CallBack - обратные вызовы на этапе инициализации - на выходе мы уже получаем готовые bean-ы;
- Обработку в bean-post-processor-after-initialisation (и это *.postProcessAfterInitialization методы)
всех пост-процессоров в IoC контейнере. Т.е. полная копия ситуации работы post-processor-ов с bean-ом перед 
initialization-call-back;

________________________________________________________________________________________________________________________
#### Lesson 13 - аннотации @Autowired, @Value, @Qualifier

- [AutowiredQualifierDemo.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/lesson_13/AutowiredQualifierDemo.java) - приложение-демонстратор обращения к bean-ам в которые инъекция зависимостей 
пошла через аннотации [@Autowired](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired.html) ([@Resource](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/resource.html)), [@Value](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/value-annotations.html), [@Qualifier](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired-qualifiers.html) см. классы и комментарии в них:
- [StockRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/repository/StockRepository.java) - внедрение зависимости в поле при помощи вышеуказанных аннотаций;
- [StockSetRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/repository/StockSetRepository.java) - внедрение зависимостей при аннотировании сеттера;
- [StockAllInjectRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_2/src/main/java/spring/oldboy/repository/StockAllInjectRepository.java) - аннотирование коллекций при внедрении зависимостей;

________________________________________________________________________________________________________________________
#### [Continued in the next section...](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_3)