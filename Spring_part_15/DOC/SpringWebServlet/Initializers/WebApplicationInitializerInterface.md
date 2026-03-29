- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/WebApplicationInitializer.html)

---
### Interface WebApplicationInitializer

**Пакет:** [org.springframework.web](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/package-summary.html)

**Реализующие классы:** 
- [AbstractAnnotationConfigDispatcherServletInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/support/AbstractAnnotationConfigDispatcherServletInitializer.html),
- [AbstractContextLoaderInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/AbstractContextLoaderInitializer.html),
- [AbstractDispatcherServletInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/support/AbstractDispatcherServletInitializer.html),
- [AbstractReactiveWebInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/server/adapter/AbstractReactiveWebInitializer.html)

**См. также:** 
- [SpringServletContainerInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/SpringServletContainerInitializer.html),
- [AbstractContextLoaderInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/AbstractContextLoaderInitializer.html),
- [AbstractDispatcherServletInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/support/AbstractDispatcherServletInitializer.html),
- [AbstractAnnotationConfigDispatcherServletInitializer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/support/AbstractAnnotationConfigDispatcherServletInitializer.html)

```
  public interface WebApplicationInitializer
```

---
Интерфейс, который должен быть реализован в средах сервлетов для программной настройки ServletContext - в отличие от
традиционного подхода на основе web.xml (или, возможно, в сочетании с ним).

Реализации этого SPI будут автоматически обнаружены SpringServletContainerInitializer, который автоматически загружается
любым контейнером сервлетов. Подробную информацию об этом механизме начальной загрузки см. в документации [Javadoc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/SpringServletContainerInitializer.html)

---
#### Пример

#### Традиционный подход на основе XML

Большинству пользователей Spring, создающих веб-приложение, необходимо зарегистрировать DispatcherServlet Spring. Для
справки, в WEB-INF/web.xml это обычно делается следующим образом:

```xml
 <servlet>
   <servlet-name>dispatcher</servlet-name>
   <servlet-class>
     org.springframework.web.servlet.DispatcherServlet
   </servlet-class>
   <init-param>
     <param-name>contextConfigLocation</param-name>
     <param-value>/WEB-INF/spring/dispatcher-config.xml</param-value>
   </init-param>
   <load-on-startup>1</load-on-startup>
 </servlet>

 <servlet-mapping>
   <servlet-name>dispatcher</servlet-name>
   <url-pattern>/</url-pattern>
 </servlet-mapping>
```

#### Подход на основе кода с использованием WebApplicationInitializer.

Вот эквивалентная логика регистрации DispatcherServlet в стиле WebApplicationInitializer:

```java
 public class MyWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
      XmlWebApplicationContext appContext = new XmlWebApplicationContext();
      appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

      ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(appContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/");
    }
 }
```

В качестве альтернативы вышесказанному вы также можете расширить объект AbstractDispatcherServletInitializer. Как вы
можете видеть, благодаря методу ServletContext.addServlet(java.lang.String, java.lang.String) контейнера сервлетов мы
фактически регистрируем экземпляр DispatcherServlet, и это означает, что DispatcherServlet теперь можно рассматривать
как любой другой объект - в этом случае получает внедрение конструктора контекста приложения.

Этот стиль одновременно проще и лаконичнее. Не нужно беспокоиться о параметрах инициализации и т. д., только обычные
свойства в стиле JavaBean и аргументы конструктора. Вы можете создавать контексты приложений Spring и работать с ними
по мере необходимости, прежде чем внедрять их в DispatcherServlet.

Большинство основных компонентов Spring Web были обновлены для поддержки этого стиля регистрации. Вы обнаружите, что
DispatcherServlet, FrameworkServlet, ContextLoaderListener и DelegatingFilterProxy теперь поддерживают аргументы
конструктора. Даже если компонент (например, не Spring, другой сторонний) не был специально обновлен для использования в
WebApplicationInitializers, его все равно можно использовать в любом случае.

API ServletContext позволяет программно устанавливать параметры инициализации, контекста и т.д.

#### Подход к настройке, полностью основанный на коде

В приведенном выше примере WEB-INF/web.xml был успешно заменен кодом в форме WebApplicationInitializer, но фактическая конфигурация Spring dispatcher-config.xml 
осталась на основе XML. WebApplicationInitializer идеально подходит для использования с классами @Configuration на основе кода Spring. Полную информацию см. в 
документации [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html), но в 
следующем примере демонстрируется рефакторинг для использования Spring AnnotationConfigWebApplicationContext вместо XmlWebApplicationContext и определяемых 
пользователем классов @Configuration AppConfig и DispatcherConfig вместо XML-файлов Spring.

Этот пример также немного выходит за рамки приведенных выше и демонстрирует типичную конфигурацию «корневого» контекста
приложения и регистрацию ContextLoaderListener:

```java
public class MyWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
      // Create the 'root' Spring application context
      AnnotationConfigWebApplicationContext rootContext =
        new AnnotationConfigWebApplicationContext();
      rootContext.register(AppConfig.class);

      // Manage the lifecycle of the root application context
      container.addListener(new ContextLoaderListener(rootContext));

      // Create the dispatcher servlet's Spring application context
      AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
      dispatcherContext.register(DispatcherConfig.class);

      // Register and map the dispatcher servlet
      ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/");
    }
 }
```

В качестве альтернативы вышесказанному вы также можете расширить AbstractAnnotationConfigDispatcherServletInitializer.
Помните, что реализации WebApplicationInitializer обнаруживаются автоматически, поэтому вы можете упаковать их в свое
приложение по своему усмотрению.

#### Порядок выполнения WebApplicationInitializer

Реализации WebApplicationInitializer могут дополнительно быть аннотированы на уровне класса аннотацией @Order Spring или
могут реализовывать интерфейс Ordered Spring. Если это так, инициализаторы будут упорядочены до вызова. Это предоставляет
пользователям механизм, позволяющий гарантировать порядок, в котором происходит инициализация контейнера сервлетов.
Ожидается, что эта функция будет использоваться редко, поскольку типичные приложения, скорее всего, централизуют всю
инициализацию контейнера в одном WebApplicationInitializer.

---
### Методы

- `void onStartup(ServletContext servletContext)` - Настройте данное ServletContext с помощью любых сервлетов, фильтров, контекстных параметров прослушивателей и атрибутов, необходимых для инициализации этого веб-приложения.

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/WebApplicationInitializer.html)

---
**Сопутствующие материалы:**
- [Spring WebApplicationInitializer with Example](https://www.geeksforgeeks.org/java/spring-webapplicationinitializer-with-example/)
- [web.xml vs Initializer with Spring](https://www.baeldung.com/spring-xml-vs-java-config)
- [Spring WebApplicationInitializer](https://stackoverflow.com/questions/35971082/spring-webapplicationinitializer)
- [Spring MVC - WebApplicationInitializer Examples](https://www.logicbig.com/how-to/code-snippets/jcode-spring-mvc-webapplicationinitializer.html)
- [Servlet Config](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/container-config.html)
- [How to Register a Servlet in Java](https://www.baeldung.com/register-servlet)
