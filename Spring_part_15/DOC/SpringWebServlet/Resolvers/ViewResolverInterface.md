- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ViewResolver.html)

---
### Interface ViewResolver

**Пакет:** [org.springframework.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/package-summary.html)

**Все реализующие классы:** 
- [AbstractCachingViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/AbstractCachingViewResolver.html),
- [AbstractTemplateViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/AbstractTemplateViewResolver.html),
- [BeanNameViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/BeanNameViewResolver.html),
- [ContentNegotiatingViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/ContentNegotiatingViewResolver.html),
- [FreeMarkerViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/freemarker/FreeMarkerViewResolver.html),
- [GroovyMarkupViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/groovy/GroovyMarkupViewResolver.html),
- [InternalResourceViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/InternalResourceViewResolver.html),
- [ScriptTemplateViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/script/ScriptTemplateViewResolver.html),
- [UrlBasedViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/UrlBasedViewResolver.html),
- [ViewResolverComposite](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/ViewResolverComposite.html),
- [XsltViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/xslt/XsltViewResolver.html)

**См. так же:** 
- [InternalResourceViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/InternalResourceViewResolver.html),
- [ContentNegotiatingViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/ContentNegotiatingViewResolver.html),
- [BeanNameViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/BeanNameViewResolver.html)

```
  public interface ViewResolver
```

---
Интерфейс, который будет реализован объектами, которые могут разрешать (resolve) представления (views) по имени.

Состояние представления не меняется во время работы приложения, поэтому реализации могут свободно кэшировать представления.

Реализации поощряются для поддержки интернационализации, то есть разрешения локализованного представления.

---
#### Методы

- `View resolveViewName(String viewName, Locale locale)` - Разрешите данное представление по имени.

**ПРИМЕЧАНИЕ.** Чтобы разрешить цепочку ViewResolver, ViewResolver должен возвращать значение null, если в нем не определено представление с данным именем. Однако это не обязательно: некоторые ViewResolver всегда будут пытаться создать объекты View с заданным именем, не имея возможности вернуть значение null (скорее выдавая исключение, если создание представления не удалось).

*Параметры:* 
- viewName - имя представления для разрешения;
- locale - Локаль, в которой будет разрешено представление. ViewResolvers, поддерживающие интернационализацию, должны это учитывать.
*Возвращает:* объект View или, null если он не найден (необязательно, чтобы разрешить цепочку ViewResolver)
*Исключения:* Exception - если представление не может быть разрешено (обычно в случае проблем с созданием реального объекта представления)

---
- [См. оригинал (ENG):](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ViewResolver.html)

---
**Дополнительные материалы:**
- [View Resolvers](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-config/view-resolvers.html)
- [View Resolution](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/viewresolver.html)
- [A Guide to the ViewResolver in Spring MVC](https://www.baeldung.com/spring-mvc-view-resolver-tutorial)
- [ViewResolver in Spring MVC](https://www.geeksforgeeks.org/springboot/viewresolver-in-spring-mvc/)
- [Spring MVC ViewResolver](https://www.springcloud.io/post/2022-09/spring-mvc-viewresolver/#gsc.tab=0)
- [Chaining URL View resolvers in Spring MVC](https://blog.frankel.ch/chaining-url-view-resolvers-in-spring-mvc/)
- [ViewResolver.java](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/ViewResolver.java)
- [How View Resolver works in Spring MVC?](https://javarevisited.blogspot.com/2017/08/what-does-internalresourceviewresolver-do-in-spring-mvc.html)
- [What are View Resolvers in Spring?](https://www.h2kinfosys.com/blog/what-are-view-resolvers-in-spring/)
- [Spring MVC — Xml View Resolver | Code Factory](https://34codefactory.medium.com/spring-mvc-xml-view-resolver-code-factory-972cc5610e76)
- [Spring MVC — описание интерфейса ViewResolver](https://javastudy.ru/spring-mvc/spring-mvc-viewresolver/)
- [Spring MVC - Multiple Resolver Mapping](https://www.geeksforgeeks.org/java/spring-mvc-multiple-resolver-mapping/)
- [Introduction to Spring Web MVC framework](https://docs.spring.io/spring-framework/docs/3.0.x/reference/mvc.html)
