- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartResolver.html)

---
### Interface MultipartResolver

**Пакет:** [org.springframework.web.multipart](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/package-summary.html)

**Реализующие классы:** [StandardServletMultipartResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/support/StandardServletMultipartResolver.html)

**См. также:** 
- [MultipartHttpServletRequest](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartHttpServletRequest.html),
- [MultipartFile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html),
- [ByteArrayMultipartFileEditor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/support/ByteArrayMultipartFileEditor.html),
- [StringMultipartFileEditor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/support/StringMultipartFileEditor.html),
- [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html)

```java
           public interface MultipartResolver
```

---
Стратегический интерфейс для разрешения загрузки составных файлов в соответствии с [RFC 1867](https://www.ietf.org/rfc/rfc1867.txt). Реализации обычно можно использовать как в контексте приложения, так и автономно.

Spring предоставляет следующую конкретную реализацию:

- [StandardServletMultipartResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/support/StandardServletMultipartResolver.html) для Servlet Part API

Для Spring [DispatcherServlets](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) не используется реализация преобразователя по умолчанию, поскольку приложение может самостоятельно анализировать свои составные запросы. Чтобы определить реализацию, создайте bean-компонент с идентификатором «multipartResolver» в контексте приложения [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html). Такой преобразователь применяется ко всем запросам, обрабатываемым этим [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html).

Если [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) обнаружит составной запрос, он разрешит его через настроенный [MultipartResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartResolver.html) и передаст завернутый HttpServletRequest. Затем контроллеры могут передать данный запрос интерфейсу [MultipartHttpServletRequest](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartHttpServletRequest.html), который обеспечивает доступ к любому [MultipartFiles](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html).

Обратите внимание, что это приведение поддерживается только в случае фактического составного запроса.

```java
 public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("image");
            ...
 }
```

Вместо прямого доступа контроллеры команд или форм могут зарегистрировать [ByteArrayMultipartFileEditor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/support/ByteArrayMultipartFileEditor.html) или [StringMultipartFileEditor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/support/StringMultipartFileEditor.html) со своим связывателем данных, чтобы автоматически применять составное содержимое для
формирования свойств bean-компонента.

В качестве альтернативы использованию [MultipartResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartResolver.html) с [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html), [MultipartFilter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/support/MultipartFilter.html) можно зарегистрировать в web.xml. Он будет делегировать соответствующие функции [MultipartResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartResolver.html) в контексте корневого приложения. В основном это предназначено для приложений, которые не используют собственную веб-инфраструктуру MVC Spring.

**ПРИМЕЧАНИЕ.** Вряд ли когда-либо возникнет необходимость доступа к самому MultipartResolver из кода приложения. Он просто будет выполнять свою работу «за кулисами», делая [MultipartHttpServletRequests](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartHttpServletRequest.html) доступными для контроллеров.

---
#### Методы

- `void cleanupMultipart(MultipartHttpServletRequest request)` - Очистите все ресурсы, используемые для обработки составных частей, например хранилище для загруженных файлов.
- `boolean isMultipart(HttpServletRequest request)` - Определите, содержит ли данный запрос многочастный контент.
- `MultipartHttpServletRequest resolveMultipart(HttpServletRequest request)` - Разберите данный HTTP-запрос на составные файлы и параметры и оберните запрос внутри объекта, MultipartHttpServletRequest который обеспечивает доступ к файловым дескрипторам и делает содержащиеся параметры доступными через стандартные методы ServletRequest.

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartResolver.html)

---
**Дополнительные материалы:**
- [Multipart Resolver](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/multipart.html)
- [Breaking Down the Multipart Upload Process within Spring Boot](https://medium.com/@AlexanderObregon/breaking-down-the-multipart-upload-process-within-spring-boot-9ad27fb4138f)
- [File Upload with Spring MVC](https://www.baeldung.com/spring-file-upload)
- [Spring MVC Framework: MultipartResolver with PUT method](https://stackoverflow.com/questions/20373912/spring-mvc-framework-multipartresolver-with-put-method)
- [MultipartResolver.java](https://github.com/spring-projects/spring-framework/blob/main/spring-web/src/main/java/org/springframework/web/multipart/MultipartResolver.java)
- [MultipartResolver](https://codegym.cc/quests/lectures/en.questspring.level04.lecture11)
- [Spring MVC File Upload](https://www.geeksforgeeks.org/springboot/spring-mvc-file-upload/)
- [Multipart Form Parsing in Spring Boot with Mixed Data and Files](https://medium.com/@AlexanderObregon/multipart-form-parsing-in-spring-boot-with-mixed-data-and-files-d4db94bf6b05)
- [Java Examples for org.springframework.web.multipart.MultipartResolver](https://www.javatips.net/api/org.springframework.web.multipart.multipartresolver)
