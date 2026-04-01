- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleResolver.html)

---
### Interface LocaleResolver

**Пакет:** [org.springframework.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/package-summary.html)

**Суперинтерфейс:** [LocaleContextResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleContextResolver.html)

**Реализующие классы:** 
- [AbstractLocaleContextResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AbstractLocaleContextResolver.html),
- [AbstractLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AbstractLocaleResolver.html),
- [AcceptHeaderLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AcceptHeaderLocaleResolver.html),
- [CookieLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/CookieLocaleResolver.html),
- [FixedLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/FixedLocaleResolver.html),
- [SessionLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/SessionLocaleResolver.html)

**См. так же:** 
- [LocaleContextResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleContextResolver.html),
- [LocaleContextHolder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/i18n/LocaleContextHolder.html),
- [RequestContext.getLocale()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/support/RequestContext.html#getLocale()),
- [RequestContextUtils.getLocale(jakarta.servlet.http.HttpServletRequest)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/support/RequestContextUtils.html#getLocale(jakarta.servlet.http.HttpServletRequest))

```
  public interface LocaleResolver
```

---
Интерфейс для веб-стратегий разрешения локали, который позволяет как разрешать локали посредством запроса, так и изменять локаль посредством запроса и ответа.

Этот интерфейс допускает реализации на основе запроса, сеанса, файлов cookie и т. д. Реализация по умолчанию — [AcceptHeaderLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AcceptHeaderLocaleResolver.html), просто используя локаль запроса, предоставленную соответствующим заголовком HTTP.

Используйте [RequestContext.getLocale()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/support/RequestContext.html#getLocale()) для получения текущей локали в контроллерах или представлениях, независимо от фактической стратегии разрешения.

**ПРИМЕЧАНИЕ.** Начиная с Spring 4.0 существует расширенный стратегический интерфейс под названием LocaleContextResolver, позволяющий разрешать объект LocaleContext, потенциально включая связанную информацию о часовом поясе.

Реализации resolver-a, предоставленные Spring, реализуют расширенный интерфейс LocaleContextResolver там, где это необходимо.

---
#### Методы

- `Locale resolveLocale(HttpServletRequest request)` - Разрешите текущую локаль с помощью данного запроса. В любом случае можно вернуть локаль по умолчанию в качестве запасного варианта.

Где, параметры: 
- request- запрос на разрешение локали для;
Возвращает:
- текущая локаль (никогда null);

- `void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale)` - Установите текущую локаль на заданную.

Где, параметры: 
- request - запрос, который будет использоваться для изменения локали;
- response - ответ, который будет использоваться для изменения локали;
- locale - новая локаль или null очистить локаль;
Исключения:
- UnsupportedOperationException - если реализация LocaleResolver не поддерживает динамическое изменение локали;

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleResolver.html)

---
**Дополнительные материалы:**
- [Locale](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/localeresolver.html)
- [How Spring Boot Configures Locale Resolution](https://medium.com/@AlexanderObregon/how-spring-boot-configures-locale-resolution-cae3e402b0df)
- [Guide to Internationalization in Spring Boot](https://www.baeldung.com/spring-boot-internationalization)
- [REST API Localization with Spring Boot AcceptHeaderLocaleResolver](https://medium.com/@ialibrahim9/rest-api-localization-with-spring-boot-8697e6a8123c)
- [Spring LocalResolver based on query parameter 'lang=en'?](https://stackoverflow.com/questions/51860856/spring-localresolver-based-on-query-parameter-lang-en)
- [Spring Boot internationalization i18n: Step-by-step with examples](https://lokalise.com/blog/spring-boot-internationalization/)
- [Spring MVC - Internationalization (i18n) and Localization (L10n)](https://www.geeksforgeeks.org/springboot/spring-mvc-internationalization-i18n-and-localization-l10n/)
- [Spring Boot - Internationalization](https://www.tutorialspoint.com/spring_boot/spring_boot_internationalization.htm)
