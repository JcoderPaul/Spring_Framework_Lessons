- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html)

---
### Class DefaultHandlerExceptionResolver

**Пакет:** [org.springframework.web.servlet.mvc.support](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/package-summary.html)

```
java.lang.Object
  org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver
    org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
```

**Все реализованные интерфейсы:** 
- [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html),
- [HandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExceptionResolver.html)

**См. так же:** 
- [ResponseEntityExceptionHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html)

```java
public class DefaultHandlerExceptionResolver
            extends AbstractHandlerExceptionResolver
```

Реализация по умолчанию интерфейса [HandlerExceptionResolver](HandlerExceptionResolver), разрешающая стандартные исключения Spring MVC и
преобразующая их в соответствующие коды состояния HTTP.

Этот преобразователь исключений включен по умолчанию в общем Spring [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html).

---
#### Поддерживаемые исключения

| Exception	                              | HTTP Status Code                |
|-----------------------------------------|---------------------------------|
| HttpRequestMethodNotSupportedException  | 405 (SC_METHOD_NOT_ALLOWED)     | 
| HttpMediaTypeNotSupportedException      | 415 (SC_UNSUPPORTED_MEDIA_TYPE) | 
| HttpMediaTypeNotAcceptableException     | 406 (SC_NOT_ACCEPTABLE)         | 
| MissingPathVariableException            | 500 (SC_INTERNAL_SERVER_ERROR)  | 
| MissingServletRequestParameterException | 400 (SC_BAD_REQUEST)            | 
| MissingServletRequestPartException      | 400 (SC_BAD_REQUEST)            | 
| ServletRequestBindingException          | 400 (SC_BAD_REQUEST)            | 
| ConversionNotSupportedException         | 500 (SC_INTERNAL_SERVER_ERROR)  | 
| TypeMismatchException                   | 400 (SC_BAD_REQUEST)            | 
| HttpMessageNotReadableException         | 400 (SC_BAD_REQUEST)            | 
| HttpMessageNotWritableException         | 500 (SC_INTERNAL_SERVER_ERROR)  | 
| MethodArgumentNotValidException         | 400 (SC_BAD_REQUEST)            | 
| [MethodValidationException](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/method/MethodValidationException.html)               | 500 (SC_INTERNAL_SERVER_ERROR)  | 
| [HandlerMethodValidationException](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/method/annotation/HandlerMethodValidationException.html)        | 400 (SC_BAD_REQUEST)            | 
| NoHandlerFoundException                 | 404 (SC_NOT_FOUND)              | 
| NoResourceFoundException                | 404 (SC_NOT_FOUND)              | 
| AsyncRequestTimeoutException            | 503 (SC_SERVICE_UNAVAILABLE)    | 
| AsyncRequestNotUsableException          | Not applicable                  |

---
#### Поля

- `static final String PAGE_NOT_FOUND_LOG_CATEGORY` - Категория журнала, которая будет использоваться, если для запроса не найден сопоставленный обработчик.
- `protected static final Log pageNotFoundLogger` - Дополнительный регистратор, который будет использоваться, если для запроса не найден сопоставленный обработчик.

---
- Поля, унаследованные от класса [AbstractHandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerExceptionResolver.html#field-summary): [logger](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerExceptionResolver.html#logger)

- Поля, унаследованные от интерфейса [Ordered](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#field-summary): [HIGHEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#HIGHEST_PRECEDENCE), [LOWEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#LOWEST_PRECEDENCE)

---
#### Конструкторы ***

- [DefaultHandlerExceptionResolver()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html#%3Cinit%3E()) - Устанавливает [порядок](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerExceptionResolver.html#setOrder(int)) [Ordered.LOWEST_PRECEDENCE](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/Ordered.html#LOWEST_PRECEDENCE).

---
#### Методы

- `protected @Nullable ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)` - Фактически разрешите данное исключение, возникшее во время выполнения обработчика, вернув ModelAndView при необходимости значение, представляющее конкретную страницу ошибки.
- `protected ModelAndView handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда истекло время ожидания асинхронного запроса.
- `protected ModelAndView handleBindException(BindException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Начиная с версии 6.0, поскольку ModelAttributeMethodProcessor теперь вместо этого создается MethodArgumentNotValidException подкласс.
- `protected ModelAndView handleConversionNotSupported(ConversionNotSupportedException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда WebDataBinder преобразование не может произойти.
- `protected ModelAndView handleErrorResponse(ErrorResponse errorResponse, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработка ErrorResponse исключения.
- `protected ModelAndView handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда не найдены преобразователи сообщений, приемлемые для клиента (выраженные через Accept заголовок).
- `protected ModelAndView handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда не были найдены преобразователи сообщений для содержимого PUT или POST.
- `protected ModelAndView handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда конвертер сообщений не может прочитать HTTP-запрос.
- `protected ModelAndView handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда преобразователь сообщений не может записать HTTP-запрос.
- `protected ModelAndView handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда для метода HTTP не найден обработчик.
- `protected ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда аргумент, помеченный знаком @Valid или, RequestBody или RequestPart проходит проверку.
- `protected ModelAndView handleMissingPathVariable(MissingPathVariableException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда объявленная переменная пути не соответствует ни одной извлеченной переменной URI.
- `protected ModelAndView handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда требуемый параметр отсутствует.
- `protected ModelAndView handleMissingServletRequestPartException(MissingServletRequestPartException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда @RequestPart, a MultipartFile или jakarta.servlet.http.Part аргумент необходимы, но отсутствуют.
- `protected ModelAndView handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда во время отправки обработчик не был найден.
- `protected ModelAndView handleServletRequestBindingException(ServletRequestBindingException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда возникает неисправимое исключение привязки.
- `protected ModelAndView handleTypeMismatch(TypeMismatchException ex, HttpServletRequest request, HttpServletResponse response, Object handler)` - Обработайте случай, когда WebDataBinder возникает ошибка преобразования.
- `protected void sendServerError(Exception ex, HttpServletRequest request, HttpServletResponse response)` - Вызван для отправки ошибки сервера.

---
- Методы, унаследованные от класса [AbstractHandlerExceptionResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerExceptionResolver.html#method-summary): buildLogMessage, getOrder, hasHandlerMappings, logException, prepareResponse, preventCaching, resolveException, setMappedHandlerClasses, setMappedHandlers, setOrder, setPreventResponseCaching, setWarnLogCategory, shouldApplyTo

- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#method-summary): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html)
- [How to customize DefaultHandlerExceptionResolver logic?](https://stackoverflow.com/questions/53974000/how-to-customize-defaulthandlerexceptionresolver-logic)
- [DefaultHandlerExceptionResolver.java](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.java)
- [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [HandlerExceptionResolvers](https://medium.com/@yjfutfut/handlerexceptionresolvers-4232723cdcc4)
- [SpringMVC exception handling system in-depth analysis](https://www.springcloud.io/post/2022-02/springmvc-exception/#gsc.tab=0)
- [Configuring global exception-handling in Spring MVC](https://steveliles.github.io/configuring_global_exception_handling_in_spring_mvc.html)
- [DefaultHandlerExceptionResolver.java](https://www.javatips.net/api/spring-framework-master/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.java)
- [Exception Handling for REST with Spring 3.2](https://www.javacodegeeks.com/2013/02/exception-handling-for-rest-with-spring-3-2.html)
- [Customizing Spring default HandlerExceptionResolvers functionality](https://www.logicbig.com/how-to/spring-mvc/spring-customizing-default-error-resolver.html)
- [Handle Spring Security Exceptions With @ExceptionHandler](https://www.baeldung.com/spring-security-exceptionhandler)
- [Complete Guide to Exception Handling in Spring Boot](https://reflectoring.io/spring-boot-exception-handling/)
