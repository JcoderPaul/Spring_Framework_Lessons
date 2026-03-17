[См. оригинал статьи (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html)

---
### DispatcherServlet

```
public class DispatcherServlet extends FrameworkServlet
```

**Пакет:** [org.springframework.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/package-summary.html)

```
java.lang.Object
  jakarta.servlet.GenericServlet
    jakarta.servlet.http.HttpServlet
      org.springframework.web.servlet.HttpServletBean
        org.springframework.web.servlet.FrameworkServlet
          org.springframework.web.servlet.DispatcherServlet
```

**Все реализованные интерфейсы:** 
- [Servlet](https://jakarta.ee/specifications/servlet/6.0/apidocs/jakarta.servlet/jakarta/servlet/servlet),
- [ServletConfig](https://jakarta.ee/specifications/servlet/6.0/apidocs/jakarta.servlet/jakarta/servlet/servletconfig),
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html),
- [Aware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/Aware.html),
- [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContextAware.html),
- [EnvironmentAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/EnvironmentAware.html),
- [EnvironmentCapable](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/EnvironmentCapable.html)

---
Центральный диспетчер для обработчиков/контроллеров HTTP-запросов, например, для контроллеров веб-интерфейса или
экспортеров удаленных служб на основе HTTP. Отправляет зарегистрированным обработчикам для обработки веб-запроса,
обеспечивая удобные средства сопоставления и обработки исключений.

Этот сервлет очень гибок: его можно использовать практически с любым рабочим процессом, установив соответствующие
классы адаптеров. Он предлагает следующую функциональность, которая отличает его от других веб-платформ MVC,
управляемых запросами:

- Он основан на механизме конфигурации JavaBeans.

- Он может использовать любую [`HandlerMapping`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html) реализацию — предварительно созданную или предоставленную как часть приложения — для управления маршрутизацией запросов к объектам-обработчикам. По умолчанию это [`BeanNameUrlHandlerMapping`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/BeanNameUrlHandlerMapping.html) и [`RequestMappingHandlerMapping`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.html). Объекты `HandlerMapping` могут быть определены как bean-компоненты в контексте приложения сервлета, реализуя интерфейс `HandlerMapping`, переопределяя `HandlerMapping` по умолчанию, если он присутствует. `HandlerMappings` может быть присвоено любое имя компонента (они проверяются по типу).

- Он может использовать любой [`HandlerAdapter`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerAdapter.html); это позволяет использовать любой интерфейс обработчика. Адаптерами по умолчанию являются [`HttpRequestHandlerAdapter`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/HttpRequestHandlerAdapter.html), [`SimpleControllerHandlerAdapter`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/SimpleControllerHandlerAdapter.html), для Spring [`HttpRequestHandler`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/HttpRequestHandler.html) и [`Controller`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/Controller.html) интерфейсов соответственно. [`RequestMappingHandlerAdapter`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerAdapter.html) также будет зарегистрирован по умолчанию. Объекты `HandlerAdapter` можно добавлять как bean-компоненты в контексте приложения, переопределяя `HandlerAdapter` по умолчанию. Как и `HandlerMappings`, `HandlerAdapters` может быть присвоено любое имя компонента (они проверяются по типу).

- Стратегию разрешения исключений диспетчера можно указать с помощью [`HandlerExceptionResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExceptionResolver.html), например, сопоставить определенные исключения со страницами ошибок. По умолчанию: [`ExceptionHandlerExceptionResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ExceptionHandlerExceptionResolver.html), [`ResponseStatusExceptionResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/annotation/ResponseStatusExceptionResolver.html), и [`DefaultHandlerExceptionResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html). Эти `HandlerExceptionResolvers` можно переопределить
  через контекст приложения. `HandlerExceptionResolver` может быть присвоено любое имя компонента (они проверяются по типу).

- Его стратегия разрешения представлений может быть указана с помощью [`ViewResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ViewResolver.html) реализации, преобразующей символические имена представлений в объекты `View`. По умолчанию [`InternalResourceViewResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/InternalResourceViewResolver.html). Объекты `ViewResolver` можно добавлять как bean-компоненты в контексте приложения, переопределяя `ViewResolver` по умолчанию. `ViewResolvers` можно присвоить любое имя компонента (они проверяются по типу).

- Если представление (view) или имя [`View`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/View.html) не указано пользователем, то настроенный запрос [`RequestToViewNameTranslator`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/RequestToViewNameTranslator.html) преобразует текущий запрос в имя представления. Соответствующее имя компонента — «viewNameTranslator»; значение по умолчанию [`DefaultRequestToViewNameTranslator`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/DefaultRequestToViewNameTranslator.html).

- Стратегия диспетчера по разрешению составных запросов определяется реализацией [`MultipartResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartResolver.html). Включена реализация стандартной многочастной обработки сервлетов. Имя компонента MultipartResolver — «multipartResolver»; по умолчанию нет.

- Его стратегия разрешения локали определяется файлом [`LocaleResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleResolver.html). Готовые реализации работают через HTTP-заголовок принятия, файл cookie или сеанс. Имя компонента LocaleResolver — «localeResolver»; по умолчанию [`AcceptHeaderLocaleResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AcceptHeaderLocaleResolver.html).

- Стратегия разрешения его тем (theme) определяется файлом `ThemeResolver`. Включены реализации для фиксированной темы, а
  также для хранения файлов cookie и сеансов. Имя компонента ThemeResolver — «themeResolver»; по умолчанию `FixedThemeResolver`.
  Поддержка тем (theme) устарела с версии 6.0 и не имеет прямой замены.

**ПРИМЕЧАНИЕ. Аннотация @RequestMapping будет обработана только в том случае, если соответствующая HandlerMapping (для
            аннотаций уровня типа) и/или HandlerAdapter (для аннотаций уровня метода) присутствует в диспетчере.** Это
            настройка по умолчанию. Однако если вы определяете пользовательский HandlerMappings или HandlerAdapters,
            вам необходимо убедиться, что соответствующий пользовательский RequestMappingHandlerMappingи /или
            RequestMappingHandlerAdapter также определен — при условии, что вы собираетесь использовать @RequestMapping.

**Веб-приложение может определить любое количество DispatcherServlets.** Каждый [`ContextLoaderListener`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoaderListener.html) сервлет будет работать в своем собственном пространстве имен, загружая собственный контекст приложения с сопоставлениями, обработчиками и т. д.
Совместным будет только корневой контекст приложения, загруженный, если таковой имеется.

DispatcherServlet может быть внедрен с контекстом веб-приложения, а не создавать свой собственный внутри. Это полезно в
средах Servlet 3.0+, которые поддерживают программную регистрацию экземпляров сервлетов.

Подробности о [DispatcherServlet (WebApplicationContext)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html#%3Cinit%3E(org.springframework.web.context.WebApplicationContext)) смотрите в детали конструктора ниже:

---
### Конструкторы

- `DispatcherServlet()` - Создайте новый DispatcherServlet, который создаст собственный внутренний контекст веб-приложения
                        на основе значений по умолчанию и значений, предоставленных через параметры инициализации
                        сервлета.

- `DispatcherServlet(WebApplicationContext webApplicationContext)` - Создайте новый DispatcherServlet с заданным контекстом веб-приложения.

---
### Детали конструкторов

- `public DispatcherServlet()` - Создайте новый DispatcherServlet, который создаст собственный внутренний контекст
                               веб-приложения на основе значений по умолчанию и значений, предоставленных через
                               параметры инициализации сервлета.

Обычно используется в средах Servlet 2.5 или более ранних версиях, где единственным вариантом регистрации сервлета
является *web.xml* использование конструктора без аргументов.

Вызов FrameworkServlet.setContextConfigLocation(java.lang.String)(init-param 'contextConfigLocation') будет определять,
какие файлы XML будут загружены по умолчанию XmlWebApplicationContext.

Вызов FrameworkServlet.setContextClass(java.lang.Class<?>)(init-param 'contextClass') переопределяет значение по
умолчанию XmlWebApplicationContext и позволяет указать альтернативный класс, например AnnotationConfigWebApplicationContext.

Вызов FrameworkServlet.setContextInitializerClasses(java.lang.String)(init-param 'contextInitializerClasses') указывает,
какие ApplicationContextInitializer классы следует использовать для дальнейшей настройки внутреннего контекста приложения
перед обновлением().

- `public DispatcherServlet(WebApplicationContext webApplicationContext)` - Создайте новый DispatcherServlet с заданным контекстом веб-приложения.

Этот конструктор полезен в средах сервлетов, где регистрация сервлетов на основе экземпляров возможна через
ServletContext.addServlet(java.lang.String, java.lang.String)API.

Использование этого конструктора указывает, что следующие свойства/параметры инициализации будут игнорироваться:
- FrameworkServlet.setContextClass(Class)/ 'Класс контекста'
- FrameworkServlet.setContextConfigLocation(String)/ 'контекстконфиглокация'
- FrameworkServlet.setContextAttribute(String)/ 'атрибут контекста'
- FrameworkServlet.setNamespace(String)/ 'пространство имен'

Данный контекст веб-приложения может быть обновлен или еще не обновлен. Если он еще не был обновлен (рекомендуемый
подход), произойдет следующее:
- Если данный контекст еще не имеет родительского контекста, корневой контекст приложения будет установлен как родительский.
- Если данному контексту еще не присвоен идентификатор, он будет присвоен ему.
- ServletContext и ServletConfig объекты будут делегированы в контекст приложения.
- FrameworkServlet.postProcessWebApplicationContext (org.springframework.web.context.ConfigurableWebApplicationContext) будет вызван
- Будут применены любые ApplicationContextInitializer значения, указанные в параметре инициализации contextInitializerClasses
  или в свойстве .FrameworkServlet.setContextInitializers(org.springframework.context.ApplicationContextInitializer<?>...)
- refresh() будет вызван, если контекст реализует ConfigurableApplicationContext

Если контекст уже был обновлен, ничего из вышеперечисленного не произойдет, при условии, что пользователь выполнил эти
действия (или нет) в соответствии со своими конкретными потребностями.

См. WebApplicationInitializer. примеры использования.

Параметры: webApplicationContext- контекст для использования;

Смотрите также:
- FrameworkServlet.initWebApplicationContext()
- FrameworkServlet.configureAndRefreshWebApplicationContext(org.springframework.web.context.ConfigurableWebApplicationContext)
- WebApplicationInitializer

---
### Поля и атрибуты

- `static final String EXCEPTION_ATTRIBUTE` - Имя атрибута запроса, который предоставляет исключение, разрешенное с помощью, HandlerExceptionResolver но представление не было отображено.
- `static final String FLASH_MAP_MANAGER_ATTRIBUTE` - Имя атрибута запроса, содержащего файл FlashMapManager.
- `static final String FLASH_MAP_MANAGER_BEAN_NAME` - Общеизвестное имя объекта FlashMapManager в фабрике компонентов для этого пространства имен.
- `static final String HANDLER_ADAPTER_BEAN_NAME` - Общеизвестное имя объекта HandlerAdapter в фабрике компонентов для этого пространства имен.
- `static final String HANDLER_EXCEPTION_RESOLVER_BEAN_NAME` - Общеизвестное имя объекта HandlerExceptionResolver в фабрике компонентов для этого пространства имен.
- `static final String HANDLER_MAPPING_BEAN_NAME` - Общеизвестное имя объекта HandlerMapping в фабрике компонентов для этого пространства имен.
- `static final String INPUT_FLASH_MAP_ATTRIBUTE` - Имя атрибута запроса, который содержит атрибуты флэш-памяти, доступные только для чтения, Map<String,?> с «входными» флэш-атрибутами, сохраненными предыдущим запросом, если таковые имеются.
- `static final String LOCALE_RESOLVER_ATTRIBUTE` - Атрибут запроса для хранения текущего LocaleResolver, доступного для просмотра представлениями.
- `static final String LOCALE_RESOLVER_BEAN_NAME` - Общеизвестное имя объекта LocaleResolver в фабрике компонентов для этого пространства имен.
- `static final String MULTIPART_RESOLVER_BEAN_NAME` - Общеизвестное имя объекта MultipartResolver в фабрике компонентов для этого пространства имен.
- `static final String OUTPUT_FLASH_MAP_ATTRIBUTE` - Имя атрибута запроса, который содержит «выходные данные» FlashMap с атрибутами, которые необходимо сохранить для последующего запроса.
- `static final String PAGE_NOT_FOUND_LOG_CATEGORY` - Категория журнала, которая будет использоваться, если для запроса не найден сопоставленный обработчик.
- `protected static final Log pageNotFoundLogger` - Дополнительный регистратор, который будет использоваться, если для запроса не найден сопоставленный обработчик.
- `static final String REQUEST_TO_VIEW_NAME_TRANSLATOR_BEAN_NAME` - Общеизвестное имя объекта RequestToViewNameTranslator в фабрике компонентов для этого пространства имен.
- `static final String THEME_RESOLVER_ATTRIBUTE` - Устарело. Начиная с версии 6.0, без прямой замены.
- `static final String THEME_RESOLVER_BEAN_NAME` - Устарело. Начиная с версии 6.0, без прямой замены.
- `static final String THEME_SOURCE_ATTRIBUTE` - Устарело. Начиная с версии 6.0, без прямой замены.
- `static final String VIEW_RESOLVER_BEAN_NAME` - Общеизвестное имя объекта ViewResolver в фабрике компонентов для этого пространства имен.
- `static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE` - Атрибут запроса для хранения текущего контекста веб-приложения.

---
Поля, унаследованные от класса org.springframework.web.servlet.FrameworkServlet: DEFAULT_CONTEXT_CLASS, DEFAULT_NAMESPACE_SUFFIX, SERVLET_CONTEXT_PREFIX
Поля, унаследованные от класса org.springframework.web.servlet.HttpServletBean: logger
Поля, унаследованные от класса jakarta.servlet.http.HttpServlet: LEGACY_DO_HEAD

---
### Методы

- `protected LocaleContext buildLocaleContext(HttpServletRequest request)` - Создайте LocaleContext для данного запроса, предоставляя основной языковой стандарт запроса как текущий языковой стандарт.
- `protected HttpServletRequest checkMultipart(HttpServletRequest request)` - Преобразуйте запрос в составной запрос и сделайте доступным составной преобразователь.
- `protected void cleanupMultipart(HttpServletRequest request)` - Очистите все ресурсы, используемые данным составным запросом (если таковые имеются).
- `protected Object createDefaultStrategy(ApplicationContext context, Class<?> clazz)` - Создайте стратегию по умолчанию.
- `protected void doDispatch(HttpServletRequest request, HttpServletResponse response)` - Обработайте фактическую отправку обработчику.
- `protected void doService(HttpServletRequest request, HttpServletResponse response)` - Предоставляет атрибуты запроса, специфичные для DispatcherServlet, и делегирует их doDispatch(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse) для фактической диспетчеризации.
- `protected <T> List<T> getDefaultStrategies(ApplicationContext context, Class<T> strategyInterface)` - Создайте список объектов стратегии по умолчанию для данного интерфейса стратегии.
- `protected <T> T getDefaultStrategy(ApplicationContext context, Class<T> strategyInterface)` - Возвращает объект стратегии по умолчанию для данного интерфейса стратегии.
- `protected String getDefaultViewName(HttpServletRequest request)` - Переведите предоставленный запрос в имя представления по умолчанию.
- `protected HandlerExecutionChain getHandler(HttpServletRequest request)` - Верните HandlerExecutionChain для этого запроса.
- `protected HandlerAdapter getHandlerAdapter(Object handler)` - Верните HandlerAdapter для этого объекта-обработчика.
- `final List<HandlerMapping> getHandlerMappings()` - Возвращает настроенные HandlerMapping bean-компоненты, которые были обнаружены по типу в WebApplicationContext или инициализированы на основе набора стратегий по умолчанию из DispatcherServlet.properties.
- `final MultipartResolver getMultipartResolver()` - Получите MultipartResolver этого сервлета, если таковой имеется.
- `final ThemeSource getThemeSource()` - Устарело.
- `protected void initStrategies(ApplicationContext context)` - Инициализируйте объекты стратегии, которые использует этот сервлет.
- `protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response)` - Обработчик не найден → установите соответствующий статус ответа HTTP.
- `protected void onRefresh(ApplicationContext context)` - Эта реализация вызывает: initStrategies(org.springframework.context.ApplicationContext).
- `protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)` - Определите ошибку ModelAndView с помощью зарегистрированных HandlerExceptionResolvers.
- `protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response)` - Отобразите данный ModelAndView.
- `protected View resolveViewName(String viewName, Map<String,Object> model, Locale locale, HttpServletRequest request)` - Преобразуйте данное имя представления в объект View (подлежащий визуализации).
- `void setCleanupAfterInclude(boolean cleanupAfterInclude)` - Установите, выполнять ли очистку атрибутов запроса после запроса на включение, то есть сбрасывать ли исходное состояние всех атрибутов запроса после обработки DispatcherServlet в запросе на включение.
- `void setDetectAllHandlerAdapters(boolean detectAllHandlerAdapters) - Установите, следует ли обнаруживать все bean-компоненты HandlerAdapter в контексте этого сервлета.
- `void setDetectAllHandlerExceptionResolvers(boolean detectAllHandlerExceptionResolvers)` - Установите, следует ли обнаруживать все bean-компоненты HandlerExceptionResolver в контексте этого сервлета.
- `void setDetectAllHandlerMappings(boolean detectAllHandlerMappings)` - Установите, следует ли обнаруживать все bean-компоненты HandlerMapping в контексте этого сервлета.
- `void setDetectAllViewResolvers(boolean detectAllViewResolvers)` - Установите, следует ли обнаруживать все bean-компоненты ViewResolver в контексте этого сервлета.
- `void setThrowExceptionIfNoHandlerFound(boolean throwExceptionIfNoHandlerFound)` - Установите, следует ли создавать исключение NoHandlerFoundException, если для этого запроса не найден обработчик.

---
- **Методы, унаследованные от класса org.springframework.web.servlet.FrameworkServlet:** applyInitializers,
buildRequestAttributes, configureAndRefreshWebApplicationContext, createWebApplicationContext, createWebApplicationContext,
destroy, doDelete, doGet, doOptions, doPost, doPut, doTrace, findWebApplicationContext, getContextAttribute,
getContextClass, getContextConfigLocation, getContextId, getNamespace, getServletContextAttributeName,
getUsernameForRequest, getWebApplicationContext, initFrameworkServlet, initServletBean, initWebApplicationContext,
isEnableLoggingRequestDetails, onApplicationEvent, postProcessWebApplicationContext, processRequest, refresh, service,
setApplicationContext, setContextAttribute, setContextClass, setContextConfigLocation, setContextId,
setContextInitializerClasses, setContextInitializers, setDispatchOptionsRequest, setDispatchTraceRequest,
setEnableLoggingRequestDetails, setNamespace, setPublishContext, setPublishEvents, setThreadContextInheritable
- **Методы, унаследованные от класса org.springframework.web.servlet.HttpServletBean:** addRequiredProperty, createEnvironment,
getEnvironment, getServletName, init, initBeanWrapper, setEnvironment
- **Методы, унаследованные от класса jakarta.servlet.http.HttpServlet:** doHead, getLastModified, init, service
- **Методы, унаследованные от класса jakarta.servlet.GenericServlet:** getInitParameter, getInitParameterNames, getServletConfig, getServletContext, getServletInfo, log
- **Методы, унаследованные от класса java.lang.Object:** clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
**См. так же:** 
- [HttpRequestHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/HttpRequestHandler.html),
- [Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/Controller.html),
- [ContextLoaderListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ContextLoaderListener.html),
- [Serialized Form](https://docs.spring.io/spring-framework/docs/current/javadoc-api/serialized-form.html#org.springframework.web.servlet.DispatcherServlet)

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html)
