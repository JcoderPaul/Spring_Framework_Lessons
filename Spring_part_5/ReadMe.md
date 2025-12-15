### Spring (Core) lessons part 5 - [Event Listeners](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/event/EventListener.html).

В [папке DOC](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_5/DOC) sql-скрипты и др. полезные файлы.

Док. для изучения:
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Пакет org.springframework.context.event](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/event/package-summary.html) - Классы поддержки для событий приложения (application events), например стандартных событий контекста. Поддерживается всеми основными реализациями контекста приложения (application context implementations);
- [Пакет org.springframework.context](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/package-summary.html) - Этот пакет основан на пакете bean-компонентов и добавляет поддержку источников сообщений и шаблона проектирования Observer, а также возможность объектам приложения получать ресурсы с использованием согласованного API;
Приложениям Spring нет необходимости явно зависеть от ApplicationContext или даже от функциональности BeanFactory. Одной из сильных сторон архитектуры Spring является то, что объекты приложений часто можно настраивать без какой-либо зависимости от API-интерфейсов Spring;

------------------------------------------------------------------------------------
Для начала проведем предварительную подготовку:

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/build.gradle) добавим необходимые нам зависимости: 

    /* Подключим Spring-core и Spring-context. */
    implementation 'org.springframework:spring-core:5.3.22'
    implementation 'org.springframework:spring-context:5.3.22'

Шаг 2. - подключаем Jakarta Annotation API:

    implementation 'jakarta.annotation:jakarta.annotation-api:1.3.5'

Шаг 3. - создаем (переносим из прошлого проекта) файл настроек [ApplicationConfiguration.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/config/ApplicationConfiguration.java),
в котором используя JAVA и аннотации настраиваем наши bean-ы.

------------------------------------------------------------------------------------
#### Lesson 23 - Event Listeners ([@EventListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/event/EventListener.html), [EventObject](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/EventObject.html), [ApplicationEventPublisher](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEventPublisher.html))

Spring «из коробки» предоставляет простой механизм работы с событиями, которые позволяют уменьшить 
связность компонентов системы. Событие, которое возникает в одной точке приложения, может быть 
перехвачено и обработано в любой другой части приложения благодаря таким сущностям как publisher 
и [eventListener](./DOC/EventListener.md).

События — одна из наиболее игнорируемых функций платформы, но также и одна из наиболее полезных. 
И, как и многие другие вещи в Spring, публикация событий — это одна из возможностей, предоставляемых 
ApplicationContext.

Есть несколько простых рекомендаций, которых следует придерживаться:
- Класс событий должен расширять [ApplicationEvent](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEvent.html), который наследуется от [EventObject](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/EventObject.html).
- Издатель должен внедрить объект [ApplicationEventPublisher](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEventPublisher.html).
- Слушатель должен реализовать интерфейс [ApplicationListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationListener.html).

Реализуем простую логику 'слушателя событий':
- Шаг 1. - Создадим папку - [entity](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_5/src/main/java/spring/oldboy/listener/entity), где будем хранить все сущности относящиеся к логике 'СОБЫТИЙ' - 'EVENTS';
- Шаг 2. - Создадим некий набор стандартных событий [AccessType.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/listener/entity/AccessType.java);
- Шаг 3. - Создадим сущность 'Событие' - [EntityEvent](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/listener/entity/EntityEvent.java), она расширяет EventObject см. комментарии. Фактически сущность 'СОБЫТИЕ' будет генерировать одно из 4-х возможных событий прописанных в [AccessType.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/listener/entity/AccessType.java); 
- Шаг 4. - Создадим слушатель событий [EntityListener.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/listener/entity/EntityListener.java);
- Шаг 5. - Пропишем в [CompanyService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/service/CompanyService.java) логику, которая будет генерировать 'СОБЫТИЕ' - 'EVENT', это метод *.findById();  

Для реализации примера создадим минимальный набор классов, который будет имитировать поиск в БД компании по ее ID:
- [Company.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/entity/Company.java) - сущность компания (в нашем примере имеет только ID, этого достаточно);
- [CompanyReadDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/dto/CompanyReadDto.java) - DTO класс, который в рабочем приложении, помогает взаимодействию двух ближайших слоев (Сервисов-Контроллеров или Контроллеров-Отображений) см. [концепцию MVC](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/tree/master/MVCPractice/DOC); 

В данном примере:
- [EntityEvent.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/listener/entity/EntityEvent.java) - будущий объект для прослушивания (наследует от [EventObject](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/EventObject.html));
- [EntityListener.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/listener/entity/EntityListener.java) - будущий слушатель событий, метод которого *.acceptEntity() будет реагировать на свершившееся событие;
- [CompanyService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_5/src/main/java/spring/oldboy/service/CompanyService.java) - класс метод *.findById(), которого будет прослушиваться на свершенное событие, он же будет издателем события;
