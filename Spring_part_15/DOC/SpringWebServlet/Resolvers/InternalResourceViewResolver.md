- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/InternalResourceViewResolver.html)

---
### Class InternalResourceViewResolver

**Пакет:** [org.springframework.web.servlet.view](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/package-summary.html)

```
java.lang.Object
  org.springframework.context.support.ApplicationObjectSupport
    org.springframework.web.context.support.WebApplicationObjectSupport
      org.springframework.web.servlet.view.AbstractCachingViewResolver
        org.springframework.web.servlet.view.UrlBasedViewResolver
          org.springframework.web.servlet.view.InternalResourceViewResolver
```

**Все реализованные интерфейсы:** 
- [Aware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/Aware.html),
- [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContextAware.html),
- [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html),
- [ServletContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ServletContextAware.html),
- [ViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ViewResolver.html)

**См. так же:** 
- [UrlBasedViewResolver.setViewClass(java.lang.Class<?>)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#setViewClass(java.lang.Class)),
- [UrlBasedViewResolver.setPrefix(java.lang.String)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#setPrefix(java.lang.String)),
- [UrlBasedViewResolver.setSuffix(java.lang.String)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#setSuffix(java.lang.String)),
- [UrlBasedViewResolver.setRequestContextAttribute(java.lang.String)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#setRequestContextAttribute(java.lang.String)),
- [InternalResourceView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/InternalResourceView.html),
- [JstlView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/JstlView.html)

```java
public class InternalResourceViewResolver
                   extends UrlBasedViewResolver
```

---
#### Вложенные классы

Вложенные классы/интерфейсы, унаследованные от класса [AbstractCachingViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/AbstractCachingViewResolver.html#nested-class-summary): [AbstractCachingViewResolver.CacheFilter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/AbstractCachingViewResolver.CacheFilter.html)

---
- **Поля, унаследованные от класса [UrlBasedViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#field-summary):** [FORWARD_URL_PREFIX](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#FORWARD_URL_PREFIX), [REDIRECT_URL_PREFIX](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#REDIRECT_URL_PREFIX)
- **Поля, унаследованные от класса [AbstractCachingViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/AbstractCachingViewResolver.html#field-summary):** [DEFAULT_CACHE_LIMIT](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/AbstractCachingViewResolver.html#DEFAULT_CACHE_LIMIT)
- **Поля, унаследованные от класса [ApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#field-summary):** [logger](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#logger)
- **Поля, унаследованные от интерфейса [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#field-summary):** [HIGHEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#HIGHEST_PRECEDENCE), [LOWEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#LOWEST_PRECEDENCE)

---
#### Конструкторы

- `InternalResourceViewResolver()` - Устанавливает значение view class по умолчанию requiredViewClass(): по умолчанию InternalResourceView или, JstlView если присутствует JSTL API.
- `InternalResourceViewResolver(String prefix, String suffix)` - Удобный конструктор, позволяющий указывать prefix и suffix в качестве аргументов конструктора.

---
#### Методы

- `protected AbstractUrlBasedView buildView(String viewName)` - Создает новый экземпляр представления указанного класса представления (view) и настраивает его.
- `protected AbstractUrlBasedView instantiateView()` - Создайте экземпляр указанного класса представления (view).
- `protected Class<?> requiredViewClass()` - Верните требуемый тип представления (view) для этого преобразователя.
- `void setAlwaysInclude(boolean alwaysInclude)` - Укажите, следует ли всегда включать представление, а не пересылать его.

---
Удобный подкласс [UrlBasedViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html), поддерживающий [InternalResourceView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/InternalResourceView.html) (т. е. сервлеты и JSP-файлы) и подклассы, такие как [JstlView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/JstlView.html). Класс представления для всех представлений, генерируемых этим резолвером, можно указать с помощью [UrlBasedViewResolver.setViewClass(Class)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#setViewClass(java.lang.Class)). Подробности см. в документации [UrlBasedViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html). По умолчанию используется [InternalResourceView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/InternalResourceView.html) или [JstlView](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/JstlView.html), если доступен API JSTL.

Кстати, рекомендуется размещать JSP-файлы, которые служат только представлениями, в папке WEB-INF, чтобы скрыть их от прямого доступа (например, через введенный вручную URL). В этом случае доступ к ним будут иметь только контроллеры.

**Примечание:** При цепочке ViewResolver InternalResourceViewResolver всегда должен быть последним, поскольку он будет пытаться разрешить любое имя представления, независимо от того, существует ли базовый ресурс на самом деле.

---
- **Методы, унаследованные от класса [UrlBasedViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html#method-summary):** applyLifecycleMethods, canHandle, createView, getAttributesMap, getCacheKey, getContentType, getExposeContextBeansAsAttributes, getExposedContextBeanNames, getExposePathVariables, getOrder, getPrefix, getRedirectHosts, getRequestContextAttribute, getSuffix, getViewClass, getViewNames, initApplicationContext, isRedirectContextRelative, isRedirectHttp10Compatible, loadView, setAttributes, setAttributesMap, setContentType, setExposeContextBeansAsAttributes, setExposedContextBeanNames, setExposePathVariables, setOrder, setPrefix, setRedirectContextRelative, setRedirectHosts, setRedirectHttp10Compatible, setRequestContextAttribute, setSuffix, setViewClass, setViewNames
- **Методы, унаследованные от класса [AbstractCachingViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/AbstractCachingViewResolver.html#method-summary):** clearCache, getCacheFilter, getCacheLimit, isCache, isCacheUnresolved, removeFromCache, resolveViewName, setCache, setCacheFilter, setCacheLimit, setCacheUnresolved
- **Методы, унаследованные от класса [WebApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/WebApplicationObjectSupport.html#method-summary):** getServletContext, getTempDir, getWebApplicationContext, initApplicationContext, initServletContext,   isContextRequired, setServletContext
- **Методы, унаследованные от класса [ApplicationObjectSupport](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/ApplicationObjectSupport.html#method-summary):** getApplicationContext, getMessageSourceAccessor, obtainApplicationContext, requiredContextClass, setApplicationContext
- **Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary):** clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/InternalResourceViewResolver.html)

---
**Дополнительные материалы:**
- [Spring MVC - InternalResourceViewResolver Configuration](https://www.geeksforgeeks.org/springboot/spring-mvc-internal-resource-view-resolver-config/)
- [A Guide to the ViewResolver in Spring MVC](https://www.baeldung.com/spring-mvc-view-resolver-tutorial)
- [Spring MVC - Internal Resource View Resolver Example](https://www.tutorialspoint.com/springmvc/springmvc_internalresourceviewresolver.htm)
- [What does the InternalResourceViewResolver do in Spring MVC?](https://www.javacodegeeks.com/2017/08/internalresourceviewresolver-spring-mvc.html)
- [How View Resolver works in Spring MVC? [InternalResourceViewResolver Example]](https://javarevisited.blogspot.com/2017/08/what-does-internalresourceviewresolver-do-in-spring-mvc.html)
- [ViewResolver in Spring MVC](https://www.geeksforgeeks.org/springboot/viewresolver-in-spring-mvc/)
- [Spring MVC InternalResourceViewResolver Configuration Example](https://howtodoinjava.com/spring-mvc/spring-mvc-internalresourceviewresolver-configuration-example/)
