- [См. исх. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/BeanNameUrlHandlerMapping.html)

---
### Class BeanNameUrlHandlerMapping

**Пакет:** [org.springframework.web.servlet.handler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/package-summary.html)

```
java.lang.Object
  org.springframework.context.support.ApplicationObjectSupport
    org.springframework.web.context.support.WebApplicationObjectSupport
      org.springframework.web.servlet.handler.AbstractHandlerMapping
        org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
          org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping
            org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping
```

**Реализованные интерфейсы:**
- [Aware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/Aware.html),
- [BeanNameAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/BeanNameAware.html),
- [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContextAware.html),
- [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html),
- [ServletContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ServletContextAware.html),
- [MatchableHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/MatchableHandlerMapping.html),
- [HandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html)

```java
  public class BeanNameUrlHandlerMapping extends AbstractDetectingUrlHandlerMapping
```

---
Реализация интерфейса [HandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html), который сопоставляет URL-адреса с bean-компонентами с именами, начинающимися с косой черты («/»), аналогично тому, как Struts сопоставляет URL-адреса с именами действий.

Это реализация [DispatcherServlet-а](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) по умолчанию, используемая [RequestMappingHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.html). Альтернативно, [SimpleUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/SimpleUrlHandlerMapping.html) позволяет декларативно настраивать сопоставление обработчиков.

Сопоставление URL-адреса с именем компонента. Таким образом, входящий URL-адрес «/foo» будет сопоставляться с
обработчиком с именем «/foo» или с «/foo/foo2» в случае нескольких сопоставлений с одним обработчиком.

Поддерживается прямое совпадение (при наличии «/test» -> зарегистрированном «/test») и совпадениях «*» (при
указании «/test» -> зарегистрированном «/t*»).

Подробную информацию о параметрах шаблона см. в документации [PathPattern](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html).

См. так же - [SimpleUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/SimpleUrlHandlerMapping.html)

---
### Поля

- Поля, унаследованные от класса [org.springframework.web.servlet.handler.AbstractHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html#field-summary): [mappingsLogger](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html#mappingsLogger)
- Поля, унаследованные от класса [org.springframework.context.support.ApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#field-summary): [logger](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#logger)
- Поля, унаследованные от интерфейса [org.springframework.web.servlet.HandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#field-summary):
  - [API_VERSION_ATTRIBUTE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#API_VERSION_ATTRIBUTE),
  - [BEST_MATCHING_HANDLER_ATTRIBUTE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#BEST_MATCHING_HANDLER_ATTRIBUTE),
  - [BEST_MATCHING_PATTERN_ATTRIBUTE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#BEST_MATCHING_PATTERN_ATTRIBUTE),
  - [INTROSPECT_TYPE_LEVEL_MAPPING](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#INTROSPECT_TYPE_LEVEL_MAPPING),
  - [LOOKUP_PATH](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#LOOKUP_PATH),
  - [MATRIX_VARIABLES_ATTRIBUTE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#MATRIX_VARIABLES_ATTRIBUTE),
  - [PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE),
  - [PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE),
  - [URI_TEMPLATE_VARIABLES_ATTRIBUTE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#URI_TEMPLATE_VARIABLES_ATTRIBUTE)
- Поля, унаследованные от интерфейса org.springframework.core.Ordered:
  - [HIGHEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#HIGHEST_PRECEDENCE),
  - [LOWEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#LOWEST_PRECEDENCE)

---
### Методы

- `protected String[] determineUrlsForHandler(String beanName)` - Проверяет имя и псевдонимы данного компонента на наличие URL-адресов, начинающихся с «/».

---
- Методы, унаследованные от класса [org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractDetectingUrlHandlerMapping.html#method-summary): detectHandlers, initApplicationContext, setDetectHandlersInAncestorContexts
- Методы, унаследованные от класса [org.springframework.web.servlet.handler.AbstractUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractUrlHandlerMapping.html#method-summary): buildPathExposingHandler, exposePathWithinMapping, exposeUriTemplateVariables, getHandlerInternal, getHandlerMap, getPathPatternHandlerMap, getRootHandler, lookupHandler, lookupHandler, match, registerHandler, registerHandler, setLazyInitHandlers, setPatternParser, setRootHandler, setUseTrailingSlashMatch, pportsTypeLevelMappings, useTrailingSlashMatch, validateHandler
- Методы, унаследованные от класса [org.springframework.web.servlet.handler.AbstractHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html#method-summary): adaptInterceptor, detectMappedInterceptors, extendInterceptors, formatMappingName, getAdaptedInterceptors,
getCorsConfiguration, getCorsConfigurationSource, getCorsHandlerExecutionChain, getCorsProcessor, getDefaultHandler, getHandler, getHandlerExecutionChain, getMappedInterceptors, getOrder, getPathMatcher, getPatternParser, getUrlPathHelper, hasCorsConfigurationSource, initInterceptors, initLookupPath, setAlwaysUseFullPath, setBeanName, setCorsConfigurations, setCorsConfigurationSource, setCorsProcessor, setDefaultHandler, setInterceptors, setOrder, setPathMatcher, setRemoveSemicolonContent, setUrlDecode, setUrlPathHelper, usesPathPatterns
- Методы, унаследованные от класса [org.springframework.web.context.support.WebApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/WebApplicationObjectSupport.html#method-summary): getServletContext, getTempDir, getWebApplicationContext, initApplicationContext, initServletContext, isContextRequired, setServletContext
- Методы, унаследованные от класса [org.springframework.context.support.ApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#method-summary): getApplicationContext, getMessageSourceAccessor, obtainApplicationContext, requiredContextClass, setApplicationContext
- Методы, унаследованные от класса [java.lang.Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait
- Методы, унаследованные от интерфейса [org.springframework.web.servlet.HandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html#method-summary): getHandler, usesPathPatterns
- Методы, унаследованные от интерфейса [org.springframework.web.servlet.handler.MatchableHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/MatchableHandlerMapping.html#method-summary): getPatternParser

---
- [См. исх. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/BeanNameUrlHandlerMapping.html)
