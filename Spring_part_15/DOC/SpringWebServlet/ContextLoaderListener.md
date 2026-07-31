- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoaderListener.html)

---
### Class ContextLoaderListener

**Пакет:** [org.springframework.web.context](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/package-summary.html)

```
java.lang.Object
  org.springframework.web.context.ContextLoader
    org.springframework.web.context.ContextLoaderListener
```    

**Все реализованные интерфейсы:** 
- [ServletContextListener](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/servlet/servletcontextlistener),
- [EventListener](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/EventListener.html)

**См. также:** 
- [ContextLoader.setContextInitializers(org.springframework.context.ApplicationContextInitializer<?>...)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoader.html#setContextInitializers(org.springframework.context.ApplicationContextInitializer...)),
- [WebApplicationInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/WebApplicationInitializer.html)

```java
public class ContextLoaderListener
           extends ContextLoader
                implements ServletContextListener
```

---
Прослушиватель начальной загрузки для запуска и завершения работы корневого Spring [WebApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/WebApplicationContext.html). Просто делегирует [ContextLoader](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoader.html), а также [ContextCleanupListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextCleanupListener.html).

ContextLoaderListener поддерживает внедрение корневого контекста веб-приложения через конструктор [ContextLoaderListener(WebApplicationContext)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoaderListener.html#%3Cinit%3E(org.springframework.web.context.WebApplicationContext)), что позволяет программно настраивать инициализаторы
сервлетов.

Примеры использования см. в разделе [WebApplicationInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/WebApplicationInitializer.html).

---
Поля, унаследованные от класса [ContextLoader](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoader.html#field-summary): CONFIG_LOCATION_PARAM, CONTEXT_CLASS_PARAM, CONTEXT_ID_PARAM, CONTEXT_INITIALIZER_CLASSES_PARAM, GLOBAL_INITIALIZER_CLASSES_PARAM

---
#### Конструкторы

- `ContextLoaderListener()` - Создайте новый ContextLoaderListener контекст, который создаст контекст веб-приложения на основе контекстных параметров сервлета «contextClass» и «contextConfigLocation».

Подробную информацию о значениях по умолчанию для каждого из них см. в документации суперкласса ContextLoader.

Этот конструктор обычно используется при объявлении ContextLoaderListener как <прослушивателя> в файле web.xml, где требуется конструктор без аргументов.

Созданный контекст приложения будет зарегистрирован в ServletContext под именем атрибута WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, а контекст приложения Spring
будет закрыт при вызове метода жизненного цикла contextDestroyed(jakarta.servlet.ServletContextEvent) для этого прослушивателя.

Смотрите также:
- [ContextLoader](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoader.html),
- [ContextLoaderListener(WebApplicationContext)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoaderListener.html#%3Cinit%3E(org.springframework.web.context.WebApplicationContext)),
- [contextInitialized(ServletContextEvent)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoaderListener.html#contextInitialized(jakarta.servlet.ServletContextEvent)),
- [contextDestroyed(ServletContextEvent)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoaderListener.html#contextDestroyed(jakarta.servlet.ServletContextEvent))

---
- `ContextLoaderListener(WebApplicationContext context)` - Создайте новый ContextLoaderListener с заданным контекстом приложения.

Этот конструктор полезен в инициализаторах сервлетов, где регистрация прослушивателей на основе экземпляров возможна через API ServletContext.addListener(java.lang.String).

Контекст может быть обновлен, а может и не быть обновлен. 

Если он:
- (а) - является реализацией конфигурируемого WebApplicationContext;
- (б) - еще не был обновлен (рекомендуемый подход);

То произойдет следующее:
- Если данному контексту еще не присвоен идентификатор, он будет присвоен ему.
- Объекты ServletContext и ServletConfig будут делегированы контексту приложения.
- Будет вызван ContextLoader.customizeContext(jakarta.servlet.ServletContext, org.springframework.web.context.ConfigurableWebApplicationContext);
- Будут применены любые ApplicationContextInitializer, org.springframework.context.ApplicationContextInitializer, ApplicationContextInitializer, указанные в параметре инициализации «contextInitializerClasses».
- Будет вызвано *.refresh()

Если контекст уже обновлен или не реализует ConfigurationWebApplicationContext, ничего из вышеперечисленного не произойдет при условии, что пользователь выполнил эти действия (или нет) в соответствии со своими конкретными потребностями.

Примеры использования см. в разделе WebApplicationInitializer.

В любом случае данный контекст приложения будет зарегистрирован в ServletContext под именем атрибута WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, а контекст приложения Spring будет закрыт,
когда метод жизненного цикла contextDestroyed(jakarta.servlet.ServletContextEvent) вызывается в этом прослушивателе.

- Параметры: context - контекст приложения для управления;
- Смотреть также: contextInitialized (ServletContextEvent), contextDestroyed (ServletContextEvent)

---
#### Методы

- `void contextDestroyed(ServletContextEvent event)` - Закройте контекст корневого веб-приложения.
- `void contextInitialized(ServletContextEvent event)` - Инициализируйте контекст корневого веб-приложения.

---
- Методы, унаследованные от класса [ContextLoader](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoader.html#method-summary): closeWebApplicationContext, configureAndRefreshWebApplicationContext, createWebApplicationContext, customizeContext, determineContextClass, determineContextInitializerClasses, getCurrentWebApplicationContext, initWebApplicationContext, loadParentContext, setContextInitializers
- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoaderListener.html)

---
**Доп. материалы:**
- [Spring Web Contexts](https://www.baeldung.com/spring-web-contexts)
- [Adding ContextLoaderListener to web.xml in Spring MVC](https://stackoverflow.com/questions/11014782/adding-contextloaderlistener-to-web-xml-in-spring-mvc)
- [ContextLoaderListener vs DispatcherServlet](https://www.geeksforgeeks.org/advance-java/contextloaderlistener-vs-dispatcherservlet/)
- [What is the ContextLoaderListener in Spring MVC? What does it do?](https://javarevisited.blogspot.com/2024/02/what-is-contextloaderlistener-in-spring.html)
- [web.xml vs Initializer with Spring](https://www.baeldung.com/spring-xml-vs-java-config)
- [Context loader in Spring](https://www.waitingforcode.com/spring-framework/context-loader-in-spring/read)
- [ContextLoaderListener in Spring MVC](https://www.concretepage.com/spring/spring-mvc/contextloaderlistener-spring-mvc-example)
