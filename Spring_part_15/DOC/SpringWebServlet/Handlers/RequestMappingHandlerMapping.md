- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.html)

---
### Class RequestMappingHandlerMapping

**Пакет:** [org.springframework.web.servlet.mvc.method.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/package-summary.html)

```
java.lang.Object
  org.springframework.context.support.ApplicationObjectSupport
    org.springframework.web.context.support.WebApplicationObjectSupport
      org.springframework.web.servlet.handler.AbstractHandlerMapping
        org.springframework.web.servlet.handler.AbstractHandlerMethodMapping<RequestMappingInfo>
          org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping
            org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
```

**Реализованные интерфейсы:** 
- [Aware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/Aware.html),
- [BeanNameAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/BeanNameAware.html),
- [InitializingBean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/InitializingBean.html),
- [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContextAware.html),
- [EmbeddedValueResolverAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/EmbeddedValueResolverAware.html),
- [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html),
- [ServletContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ServletContextAware.html),
- [MatchableHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/MatchableHandlerMapping.html),
- [HandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html)

```java
public class RequestMappingHandlerMapping
                     extends RequestMappingInfoHandlerMapping
                                     implements MatchableHandlerMapping, EmbeddedValueResolverAware
```

---
Создает [RequestMappingInfo](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/RequestMappingInfo.html) экземпляры из аннотаций уровня типа и метода [@RequestMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html) в [@Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html) классах.

**Примечание об устаревании:** В версии 5.2.4 useSuffixPatternMatch и useRegisteredSuffixPatternMatch были объявлены
                           устаревшими, чтобы препятствовать использованию расширений путей для сопоставления запросов и
                           согласования содержимого (с аналогичными исключениями в ContentNegotiationManagerFactoryBean).

---
### Поля

- Поля, унаследованные от класса [AbstractHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html#field-summary): [mappingsLogger](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html#mappingsLogger)
- Поля, унаследованные от класса [ApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#field-summary): [logger](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#logger)
- Поля, унаследованные от интерфейса [HandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#field-summary): BEST_MATCHING_HANDLER_ATTRIBUTE, BEST_MATCHING_PATTERN_ATTRIBUTE, INTROSPECT_TYPE_LEVEL_MAPPING, LOOKUP_PATH, MATRIX_VARIABLES_ATTRIBUTE, PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE, URI_TEMPLATE_VARIABLES_ATTRIBUTE
- Поля, унаследованные от интерфейса [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#field-summary): HIGHEST_PRECEDENCE, LOWEST_PRECEDENCE

---
### Методы

- `void afterPropertiesSet()` - Обнаруживает методы обработчика при инициализации.
- `protected RequestMappingInfo createRequestMappingInfo(RequestMapping requestMapping, RequestCondition<?> customCondition)` - Создайте RequestMappingInfo из предоставленной @RequestMapping аннотации, которая является либо непосредственно объявленной аннотацией, мета-аннотацией, либо синтезированным результатом слияния атрибутов аннотации в иерархии аннотаций.
- `RequestMappingInfo.BuilderConfiguration getBuilderConfiguration()` - Получите файл RequestMappingInfo.BuilderConfiguration, который может отражать его внутреннюю конфигурацию HandlerMapping и может использоваться для установки RequestMappingInfo.Builder.options(RequestMappingInfo.BuilderConfiguration).
- `ContentNegotiationManager getContentNegotiationManager()` - Верните настроенный ContentNegotiationManager.
- `protected RequestCondition<?> getCustomMethodCondition(Method method)` - Предоставьте пользовательское условие запроса на уровне метода.
- `protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType)` - Предоставьте пользовательское условие запроса на уровне типа.
- `List<String> getFileExtensions()` - **Устарело. По состоянию на 5.2.4.**
- `protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType)` - Использует аннотации @ на уровне метода и типа RequestMapping для создания RequestMappingInfo.
- `Map<String,Predicate<Class<?>>> getPathPrefixes()` - Настроенный префикс пути представляет собой доступную только для чтения и, возможно, пустую карту.
- `protected CorsConfiguration initCorsConfiguration(Object handler, Method method, RequestMappingInfo mappingInfo)` - Извлеките и верните конфигурацию CORS для сопоставления.
- `protected boolean isHandler(Class<?> beanType)` - Является ли данный тип обработчиком с методами обработчика.
- `RequestMatchResult match(HttpServletRequest request, String pattern)` - Определите, соответствует ли запрос заданному шаблону.
- `protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping)` - Зарегистрируйте метод-обработчик и его уникальное сопоставление.
- `void registerMapping(RequestMappingInfo mapping, Object handler, Method method)` - Зарегистрируйте данное сопоставление.
- `protected String[] resolveEmbeddedValuesInPatterns(String[] patterns)` - Разрешите значения заполнителей в заданном массиве шаблонов.
- `void setContentNegotiationManager(ContentNegotiationManager contentNegotiationManager)` - Установите ContentNegotiationManager для использования для определения запрошенных типов мультимедиа.
- `void setEmbeddedValueResolver(StringValueResolver resolver)` - Установите StringValueResolver, который будет использоваться для разрешения встроенных значений определений.
- `void setPathPrefixes(Map<String, Predicate<Class<?>>> prefixes)` - Настройте префиксы путей для применения к методам контроллера.
- `void setPatternParser(PathPatternParser patternParser)` - Установите параметр PathPatternParser для анализа patterns для сопоставления URL-пути.
- `void setUseRegisteredSuffixPatternMatch(boolean useRegisteredSuffixPatternMatch)` - **Устарело. По состоянию на 5.2.4.**
- `void setUseSuffixPatternMatch(boolean useSuffixPatternMatch)` - **Устарело. По состоянию на 5.2.4.**
- `void setUseTrailingSlashMatch(boolean useTrailingSlashMatch)` - **Устарело. По состоянию на 6.0**, см. PathPatternParser.setMatchOptionalTrailingSeparator(boolean)
- `boolean useRegisteredSuffixPatternMatch()` - **Устарело. По состоянию на 5.2.4.**
- `boolean useSuffixPatternMatch()` - **Устарело. По состоянию на 5.2.4.**
- `boolean useTrailingSlashMatch()` - Соответствовать ли URL-адресам независимо от наличия косой черты в конце.

---
- Методы, унаследованные от класса [RequestMappingInfoHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/RequestMappingInfoHandlerMapping.html#method-summary): getDirectPaths, getHandlerInternal, getMappingComparator, getMappingPathPatterns, getMatchingMapping, handleMatch, handleNoMatch

- Методы, унаследованные от класса [AbstractHandlerMethodMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMethodMapping.html#method-summary): createHandlerMethod, detectHandlerMethods, getCandidateBeanNames, getCorsConfiguration, getHandlerMethods, getHandlerMethodsForMappingName, getNamingStrategy, handlerMethodsInitialized, hasCorsConfigurationSource, initHandlerMethods, lookupHandlerMethod, processCandidateBean, setDetectHandlerMethodsInAncestorContexts, setHandlerMethodMappingNamingStrategy, unregisterMapping

- Методы, унаследованные от класса [AbstractHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html#method-summary):
adaptInterceptor, detectMappedInterceptors, extendInterceptors, formatMappingName, getAdaptedInterceptors, getCorsConfigurationSource, getCorsHandlerExecutionChain, getCorsProcessor, getDefaultHandler, getHandler, getHandlerExecutionChain, getMappedInterceptors, getOrder, getPathMatcher, getPatternParser, getUrlPathHelper, initApplicationContext, initInterceptors, initLookupPath, setAlwaysUseFullPath, setBeanName, setCorsConfigurations, setCorsConfigurationSource, setCorsProcessor, setDefaultHandler, setInterceptors, setOrder, setPathMatcher, setRemoveSemicolonContent, setUrlDecode, setUrlPathHelper, usesPathPatterns

- Методы, унаследованные от класса [WebApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/WebApplicationObjectSupport.html#method-summary): getServletContext, getTempDir, getWebApplicationContext, initApplicationContext, initServletContext, isContextRequired, setServletContext

- Методы, унаследованные от класса [ApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#method-summary):
getApplicationContext, getMessageSourceAccessor, obtainApplicationContext, requiredContextClass, setApplicationContext

- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

- Методы, унаследованные от интерфейса [HandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#method-summary): getHandler, usesPathPatterns

- Методы, унаследованные от интерфейса [MatchableHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/MatchableHandlerMapping.html#method-summary): getPatternParser

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.html)

---
**Сопутствующие материалы:**
- [The Mechanics of Request Mapping in Spring Boot](https://medium.com/@AlexanderObregon/the-mechanics-of-request-mapping-in-spring-boot-92d1065cc0ad)
- [HandlerAdapters in Spring MVC](https://www.baeldung.com/spring-mvc-handler-adapters)
- [How to add RequestMappingHandlerMapping and ResourceHandlers to a springMVC configuration class](https://stackoverflow.com/questions/30402453/how-to-add-requestmappinghandlermapping-and-resourcehandlers-to-a-springmvc-conf)
- [Spring MVC – Customizing RequestMappingHandlerMapping](https://www.javacodegeeks.com/2013/01/spring-mvc-customizing-requestmappinghandlermapping.html)
- [Guide to Spring Handler Mappings](https://www.baeldung.com/spring-handler-mappings)
- [HandlerMapping in Spring MVC](https://www.springcloud.io/post/2022-08/spring-mvc-handlermapping/#gsc.tab=0)
- [Spring MVC - RequestMappingHandlerMapping Examples](https://www.logicbig.com/how-to/code-snippets/jcode-spring-mvc-requestmappinghandlermapping.html)
