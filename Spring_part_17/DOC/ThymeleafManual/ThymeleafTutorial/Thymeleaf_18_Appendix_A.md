- [См. исходник (ENG)](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#appendix-a-expression-basic-objects)
- [Thymeleaf 3.1.5.RELEASE API](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/index.html)
- [Good Thymes Virtual Grocery on GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)

---
### Приложение A: Основные выражения

Некоторые объекты и переменные всегда доступны для вызова. Давайте посмотрим на них:

---
**Базовые объекты**

- **#ctx**: объект контекста.

Реализация [IContext](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/context/IContext.html) или [org.thymeleaf.context.IWebContext](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/context/IWebContext.html) в зависимости от нашей среды (standalone или web).

*Примечание `#vars` и `#root` являются синонимами для одного и того же объекта, но рекомендуется использовать `#ctx`.*

См. Javadoc API для класса [IContext](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/context/IContext.html):
```    
    ${#ctx.locale}
    ${#ctx.variableNames}
```

См. Javadoc API для класса [IWebContext](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/context/IWebContext.html):
```
    ${#ctx.request}
    ${#ctx.response}
    ${#ctx.session}
    ${#ctx.servletContext}
```

- **#locale**: прямой доступ к [Locale](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html), связанный с текущим запросом.

```
    ${#locale}
```

---
**Пространства имен веб-контекста для атрибутов `request/session` и т. д.**

При использовании Thymeleaf в веб-среде мы можем использовать ряд ярлыков для доступа к параметрам запроса, атрибутам
сеанса и атрибутам приложения:

**!!! 
Обратите внимание, что это не объекты контекста, а элементы Map, добавленные в контекст как переменные, поэтому мы
обращаемся к ним без `#`. В некотором роде они действуют как пространства имен 
!!!**

- **param**: для получения параметров запроса, `${param.foo}` является String[] со значениями параметра запроса foo, поэтому `${param.foo[0]}` обычно используется для получения первого значения.

```
    /* См. Javadoc API для класса org.thymeleaf.context.WebRequestParamsVariablesMap */

    ${param.foo}   // Возвращает строку [] со значениями параметра запроса 'foo'
    ${param.size()}
    ${param.isEmpty()}
    ${param.containsKey('foo')}
```

- **session**: для получения атрибутов сеанса.

```
    /* См. Javadoc API для класса org.thymeleaf.context.WebSessionVariablesMap */
    
    ${session.foo}   // Возвращает сеанс attribute 'foo'
    ${session.size()}
    ${session.isEmpty()}
    ${session.containsKey('foo')}
```

- **application**: для извлечения атрибутов контекста приложения/сервлета.

```
/* См. Javadoc API для класса org.thymeleaf.context.WebServletContextVariablesMap */

${application.foo}   // Получает ServletContext аттрибут 'foo'
${application.size()}
${application.isEmpty()}
${application.containsKey('foo')}
```

**!!! 
Обратите внимание: нет необходимости указывать пространство имен для доступа к атрибутам request (в отличие от
параметров `request`), поскольку все атрибуты `request` автоматически добавляются в контекст как переменные в корне
контекста
!!!**

Например, как:

```
    ${myRequestAttribute}
```

---
**Объекты веб-контекста**

Внутри веб-среды есть также прямой доступ к следующим объектам (обратите внимание, что это объекты, а не `maps/namespaces`):

- **#request**: прямой доступ к объекту [javax.servlet.http.HttpServletRequest](https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServletRequest.html), связанному с текущим запросом.

```
    ${#request.getAttribute('foo')}
    ${#request.getParameter('foo')}
    ${#request.getContextPath()}
    ${#request.getRequestName()}
```

- **#session**: прямой доступ к объекту [javax.servlet.http.HttpSession](https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSession.html), связанному с текущим запросом.

```
    ${#session.getAttribute('foo')}
    ${#session.id}
    ${#session.lastAccessedTime}
```

- **#servletContext**: прямой доступ к объекту [javax.servlet.ServletContext](https://docs.oracle.com/javaee/7/api/javax/servlet/ServletContext.html), связанному с текущим запросом.

```
    ${#servletContext.getAttribute('foo')}
    ${#servletContext.contextPath}
```

[См. далее →](../ThymeleafTutorial/Thymeleaf_19_Appendix_B.md)
