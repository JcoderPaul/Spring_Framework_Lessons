- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/RequestToViewNameTranslator.html)

---
### Interface RequestToViewNameTranslator

**Пакет:** [org.springframework.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/package-summary.html)

**Реализующие классы:** [DefaultRequestToViewNameTranslator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/DefaultRequestToViewNameTranslator.html)

```java
  public interface RequestToViewNameTranslator
```

---
Интерфейс стратегии для преобразования входящего HttpServletRequest в логическое имя представления, когда имя представления не указано явно.

---
#### Методы

- `String getViewName(HttpServletRequest request)` - Переведите данное значение HttpServletRequest в имя представления.

Где, 
*Параметры:* request - входящий HttpServletRequest, предоставляющий контекст, из которого должно быть разрешено имя представления;
*Возвращает:* имя представления или, null если значение по умолчанию не найдено;
*Исключения:* Exception- если перевод имени представления не удался;

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/RequestToViewNameTranslator.html)

---
**Доп.материалы:**
- [Spring 2.5: New Features in Spring MVC](https://www.infoq.com/articles/spring-2.5-ii-spring-mvc/)
- [Spring MVC - Auto translation of view name](https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/view-name-translator.html)
- [Spring View Resolver](https://studytrails.com/2016/09/15/spring-mvc-view-resolver/)
- [Spring View Manipulation Vulnerability](https://www.veracode.com/blog/secure-development/spring-view-manipulation-vulnerability/)
- [Web MVC framework](https://docs.spring.io/spring-framework/docs/3.0.3.RELEASE/reference/html/mvc.html)
