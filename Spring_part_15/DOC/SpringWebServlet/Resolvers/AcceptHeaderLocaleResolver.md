- [См. исходный материал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AcceptHeaderLocaleResolver.html)

---
### Class AcceptHeaderLocaleResolver

**Пакет:** [org.springframework.web.servlet.i18n](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/package-summary.html)

```
java.lang.Object
  org.springframework.web.servlet.i18n.AbstractLocaleResolver
    org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
```

**Все реализованные интерфейсы:** [LocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleResolver.html)

**См. так же:** [ServletRequest.getLocale()]()

```java
public class AcceptHeaderLocaleResolver
            extends AbstractLocaleResolver
```

---
Реализация [LocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleResolver.html), которая ищет соответствие между локалями в заголовке Accept-Language и списком настроенных поддерживаемых локалей.

Дополнительные сведения о сопоставлении поддерживаемых и запрошенных локалей см. в разделе [setSupportedLocales(List)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AcceptHeaderLocaleResolver.html#setSupportedLocales(java.util.List)).

**ПРИМЕЧАНИЕ.** Эта реализация не поддерживает [setLocale(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, java.util.Locale)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AcceptHeaderLocaleResolver.html#setLocale(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse,java.util.Locale)), поскольку заголовок Accept-Language можно изменить только путем изменения настроек локали клиента.

---
### Методы

- `List<Locale> getSupportedLocales()` - Получите настроенный список поддерживаемых локалей.
- `Locale resolveLocale(HttpServletRequest request)` - Разрешите текущую локаль с помощью данного запроса.
- `void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale)` - Установите текущую локаль на заданную.
- `void setSupportedLocales(List<Locale> locales)` - Настройте список поддерживаемых локалей для сравнения и сопоставления с запрошенными локалями (requested locales).

Чтобы поддерживаемый языковой стандарт считался совпадающим, он должен совпадать как по стране, так и по языку. Если вы
хотите поддерживать совпадение только по языку в качестве запасного варианта, вам необходимо явно настроить язык как
поддерживаемый языковой стандарт.

Например, если поддерживаются языковые стандарты ["de-DE", "en-US"], то запрос "en-GB" не будет соответствовать, как и
запрос "en". Если вы хотите поддерживать дополнительные локали для данного языка, например «en», необходимо добавить
его в список поддерживаемых локалей.

Если совпадений нет, то используется defaultLocale, если он настроен, или иным образом возвращается к ServletRequest.getLocale().

---
- Методы, унаследованные от класса [AbstractLocaleResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AbstractLocaleResolver.html): getDefaultLocale, setDefaultLocale
- Методы, унаследованные от класса [Object](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait
________________________________________________________________________________________________________________________
- [См. исходный материал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/i18n/AcceptHeaderLocaleResolver.html)
- [How Spring Boot Configures Locale Resolution](https://medium.com/@AlexanderObregon/how-spring-boot-configures-locale-resolution-cae3e402b0df)
- [Retrieve locale based on the Accept-Language in Spring Boot](https://stackoverflow.com/questions/55736861/retrieve-locale-based-on-the-accept-language-in-spring-boot)
- [REST API Localization with Spring Boot AcceptHeaderLocaleResolver](https://medium.com/@ialibrahim9/rest-api-localization-with-spring-boot-8697e6a8123c)
- [Spring Boot internationalization i18n: Step-by-step with examples](https://lokalise.com/blog/spring-boot-internationalization/)
- [Guide to Internationalization in Spring Boot](https://www.baeldung.com/spring-boot-internationalization)
- [Spring MVC Internationalization and Localization – Example](https://www.centron.de/en/tutorial/spring-mvc-internationalization-and-localization-example/)
