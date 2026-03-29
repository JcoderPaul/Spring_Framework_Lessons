- [См. ориг. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html)

---
### Interface HandlerMapping

**Пакет:** [org.springframework.web.servlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/package-summary.html)

**Суперинтерфейсы:** [MatchableHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/MatchableHandlerMapping.html)

**Все реализующие классы:** 
- [AbstractDetectingUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractDetectingUrlHandlerMapping.html),
- [AbstractHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html),
- [AbstractHandlerMethodMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMethodMapping.html),
- [AbstractUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractUrlHandlerMapping.html),
- [BeanNameUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/BeanNameUrlHandlerMapping.html),
- [RequestMappingHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.html),
- [RequestMappingInfoHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/RequestMappingInfoHandlerMapping.html),
- [RouterFunctionMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/function/support/RouterFunctionMapping.html),
- [SimpleUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/SimpleUrlHandlerMapping.html),
- [WebSocketHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/server/support/WebSocketHandlerMapping.html)

```
  public interface HandlerMapping
```

---
Интерфейс, реализуемый объектами, определяющими сопоставление (mapping) между запросами и объектами-обработчиками.

Этот класс может быть реализован разработчиками приложений, хотя в этом нет необходимости, поскольку
как [BeanNameUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/BeanNameUrlHandlerMapping.html) так и [RequestMappingHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.html) включены в состав фреймворка. Первый вариант
используется по умолчанию, если в контексте приложения не зарегистрирован ни один компонент HandlerMapping.

Реализации HandlerMapping могут поддерживать сопоставленные перехватчики, но это не обязательно. Обработчик всегда будет
заключен в [HandlerExecutionChain](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExecutionChain.html) экземпляр, возможно, сопровождаемый некоторыми [HandlerInterceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html) экземплярами.
DispatcherServlet сначала вызовет каждый preHandle метод HandlerInterceptor в заданном порядке, а затем вызовет сам обработчик, если все preHandle методы вернули значение true.

Возможность параметризации этого сопоставления — мощная и необычная возможность этой среды MVC. Например, можно написать
собственное сопоставление на основе состояния сеанса, состояния cookie или многих других переменных. Никакая другая
структура MVC не кажется столь же гибкой.

ПРИМЕЧАНИЕ. Реализации могут реализовать [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html) интерфейс, позволяющий указать порядок сортировки и, следовательно, приоритет для применения DispatcherServlet. Неупорядоченные экземпляры имеют самый низкий приоритет.

---
См. так же: 
- [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html),
- [AbstractHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html),
- [BeanNameUrlHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/BeanNameUrlHandlerMapping.html),
- [RequestMappingHandlerMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.html)

---
### Поля

- `static final String BEST_MATCHING_HANDLER_ATTRIBUTE` - Имя атрибута HttpServletRequest, содержащего сопоставленный обработчик для шаблона наилучшего соответствия.
- `static final String BEST_MATCHING_PATTERN_ATTRIBUTE` - Имя атрибута HttpServletRequest, который содержит наилучший шаблон соответствия в сопоставлении обработчика.
- `static final String INTROSPECT_TYPE_LEVEL_MAPPING` - Имя логического HttpServletRequest атрибута, указывающего, следует ли проверять сопоставления на уровне типа.
- `static final String LOOKUP_PATH` - Устарело. Начиная с 5.3 в пользу UrlPathHelper.PATH_ATTRIBUTE и ServletRequestPathUtils.PATH_ATTRIBUTE.
- `static final String MATRIX_VARIABLES_ATTRIBUTE` - Имя атрибута HttpServletRequest, который содержит карту с именами переменных URI и соответствующую карту MultiValueMap переменных матрицы URI для каждой из них.
- `static final String PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE` - Имя атрибута HttpServletRequest, который содержит путь в сопоставлении обработчика, в случае совпадения с шаблоном, или полный соответствующий URI (обычно в сопоставлении DispatcherServlet), иначе.
- `static final String PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE` - Имя атрибута HttpServletRequest, содержащего набор производимых MediaTypes, применимых к сопоставленному обработчику.
- `static final String URI_TEMPLATE_VARIABLES_ATTRIBUTE` - Имя атрибута HttpServletRequest, содержащего карту шаблонов URI, сопоставляющую имена переменных значениям.

---
### Методы

- `HandlerExecutionChain getHandler(HttpServletRequest request)` - Верните обработчик и все перехватчики для этого запроса.
- `default boolean usesPathPatterns()` - Включено ли для этого экземпляра HandlerMapping использование анализируемых PathPatterns; в этом случае DispatcherServlet автоматически анализирует RequestPath, чтобы сделать его доступным для доступа в HandlerMappings, HandlerInterceptors и другие компоненты.

---
- [См. ориг. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html)

---
**Сопутствующие материалы:**
- [Guide to Spring Handler Mappings](https://www.baeldung.com/spring-handler-mappings)
- [HandlerMapping in Spring MVC](https://www.springcloud.io/post/2022-08/spring-mvc-handlermapping/#gsc.tab=0)
- [Spring MVC — Handler Mapping. Описание интерфейса HandlerMapping](https://javastudy.ru/spring-mvc/spring-mvc-handler-mapping/)
