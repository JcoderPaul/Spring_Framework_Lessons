- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/DefaultRequestToViewNameTranslator.html)

---
### Class DefaultRequestToViewNameTranslator

**Пакет:** [org.springframework.web.servlet.view](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/package-summary.html)

```
java.lang.Object
  org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator
```

**Все реализованные интерфейсы:** [RequestToViewNameTranslator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/RequestToViewNameTranslator.html)

**См. также:** 
- [RequestToViewNameTranslator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/RequestToViewNameTranslator.html),
- [ViewResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ViewResolver.html)

```java
public class DefaultRequestToViewNameTranslator
        extends Object
          implements RequestToViewNameTranslator
```

---
[RequestToViewNameTranslator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/RequestToViewNameTranslator.html), который просто преобразует URI входящего запроса в имя представления.

Может быть явно определен как bean-компонент viewNameTranslator в контексте [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html). В противном случае будет использоваться простой экземпляр по умолчанию.

Преобразование по умолчанию просто удаляет начальную и конечную косую черту, а также расширение файла URI и возвращает результат в виде имени представления с настроенным [префиксом](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/DefaultRequestToViewNameTranslator.html#setPrefix(java.lang.String)) и [суффиксом](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/DefaultRequestToViewNameTranslator.html#setSuffix(java.lang.String)), добавленным 
соответствующим образом.

Удаление косой черты и расширения файла можно отключить с помощью свойств [stripLeadingSlash](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/DefaultRequestToViewNameTranslator.html#setStripLeadingSlash(boolean)) и [stripExtension](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/DefaultRequestToViewNameTranslator.html#setStripExtension(boolean)) соответственно.

Ниже вы найдете несколько примеров запроса на просмотр перевода имени:
- http://localhost:8080/gamecast/display.html » display
- http://localhost:8080/gamecast/displayShoppingCart.html » displayShoppingCart
- http://localhost:8080/gamecast/admin/index.html » admin/index

---
#### Методы

- `String getViewName(HttpServletRequest request)` - Преобразует URI запроса входящего запроса HttpServletRequest в имя представления на основе настроенных параметров.
- `void setPrefix(String prefix)` - Установите префикс, который будет добавляться к сгенерированным именам представлений.
- `void setSeparator(String separator)` - Установите значение, которое заменит '/' в качестве разделителя в имени представления.
- `void setStripExtension(boolean stripExtension)` - Установите, следует ли удалять расширения файлов из URI при создании имени представления.
- `void setStripLeadingSlash(boolean stripLeadingSlash)` - Установите, следует ли удалять начальные косые черты из URI при создании имени представления.
- `void setStripTrailingSlash(boolean stripTrailingSlash)` - Установите, следует ли удалять конечные косые черты из URI при создании имени представления.
- `void setSuffix(String suffix)` - Установите суффикс для добавления к созданным именам представлений.
- `protected String transformPath(String lookupPath)` - Преобразуйте URI запроса (в контексте веб-приложения), удалив косые черты и расширения и заменив разделитель по мере необходимости.

---
Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/DefaultRequestToViewNameTranslator.html)

---
**Доп. материалы:**
- [Spring MVC - Auto translation of view name](https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/view-name-translator.html)
- [Convention over configuration support](https://docs.huihoo.com/spring/3.0.x/en-us/ch15s10.html)
- [The Front Controller That Stays Out Of Your Way](https://zalt.me/blog/2026/01/front-controller-stealth)
- [Web MVC framework](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html)
- [Guide to Spring Handler Mappings](https://www.baeldung.com/spring-handler-mappings)
