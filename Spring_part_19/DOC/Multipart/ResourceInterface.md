- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html)

---
### Interface Resource

**Пакет:** [org.springframework.core.io](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/package-summary.html)

**Все супер-интерфейсы:** [InputStreamSource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamSource.html)

**Все известные под-интерфейсы:** 
- [ContextResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ContextResource.html),
- [HttpResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/resource/HttpResource.html),
- [HttpResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/resource/HttpResource.html),
- [WritableResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/WritableResource.html)

**Все известные реализующие классы:**
- [AbstractFileResolvingResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/AbstractFileResolvingResource.html),
- [AbstractResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/AbstractResource.html),
- [ByteArrayResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ByteArrayResource.html),
- [ClassPathResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ClassPathResource.html),
- [DefaultResourceLoader.ClassPathContextResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/DefaultResourceLoader.ClassPathContextResource.html),
- [DescriptiveResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/DescriptiveResource.html),
- [FileSystemResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/FileSystemResource.html),
- [FileUrlResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/FileUrlResource.html),
- [InputStreamResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamResource.html),
- [ModuleResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ModuleResource.html),
- [PathResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/PathResource.html),
- [ServletContextResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/ServletContextResource.html),
- [TransformedResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/resource/TransformedResource.html),
- [TransformedResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/resource/TransformedResource.html),
- [UrlResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/UrlResource.html),
- [VfsResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/VfsResource.html)

**Изучить так же и:** 
- [InputStreamSource.getInputStream()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamSource.html#getInputStream()),
- [getURL()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html#getURL()),
- [getURI()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html#getURI()),
- [getFile()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html#getFile()),
- [WritableResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/WritableResource.html),
- [ContextResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ContextResource.html),
- [UrlResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/UrlResource.html),
- [FileUrlResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/FileUrlResource.html),
- [FileSystemResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/FileSystemResource.html),
- [ClassPathResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ClassPathResource.html),
- [ByteArrayResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ByteArrayResource.html),
- [InputStreamResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamResource.html)

```
  public interface Resource extends InputStreamSource
```

Интерфейс для дескриптора ресурса, который абстрагируется от фактического типа базового ресурса, такого как ресурс файла
или пути к классу. Входной поток можно открыть для каждого ресурса, если он существует в физической форме, но для
определенных ресурсов можно просто вернуть URL-адрес или дескриптор файла. Фактическое поведение зависит от реализации.

---
#### Методы

- `long contentLength()` - Определяет длину контента для этого ресурса.
- `Resource createRelative(String relativePath)` - Создает ресурс относительно этого ресурса.
- `boolean exists()` - Определяет, существует ли этот ресурс на самом деле в физической форме. Этот метод выполняет окончательную проверку существования, тогда как наличие дескриптора Resource гарантирует только действительный дескриптор дескриптора.
- `default byte[] getContentAsByteArray()` - Возвращает содержимое этого ресурса в виде массива байтов.
- `default String getContentAsString(Charset charset)` - Возвращает содержимое этого ресурса в виде строки, используя указанную кодировку.
- `String getDescription()` - Возвращает описание этого ресурса, которое будет использоваться для вывода ошибок при работе с ресурсом.
- `File getFile()` - Возвращает дескриптор файла для этого ресурса.
- `String getFilename()` - Определяет имя файла для этого ресурса — обычно это последняя часть пути, например "myfile.txt".
- `URI getURI()` - Возвращает дескриптор URI для этого ресурса.
- `URL getURL()` - Возвращает дескриптор URL-адреса этого ресурса.
- `default boolean isFile()` - Определяет, представляет ли этот ресурс файл в файловой системе. Значение true настоятельно предполагает (но не гарантирует), что getFile() вызов будет успешным. Метод консервативен - false по умолчанию.
- `default boolean isOpen()` - Указывает, представляет ли этот ресурс дескриптор открытого потока. Если true, входной поток не может быть прочитан несколько раз и должен быть прочитан и закрыт во избежание утечек
ресурсов. Будет false для типичных дескрипторов ресурсов.
- `default boolean isReadable()` - Указывает, можно ли прочитать непустое содержимое этого ресурса через InputStreamSource.getInputStream(). Будет true для типичных существующих дескрипторов ресурсов, поскольку это строго подразумевает exists() семантику начиная с версии 5.1.

```
  Обратите внимание, что фактическое чтение контента все равно может завершиться
  неудачей при попытке. Однако значение false является точным указанием на то, что
  содержимое ресурса не может быть прочитано.
```

- `long lastModified()` - Определяет временную метку последнего изменения для этого ресурса.

---
- `default ReadableByteChannel readableChannel()` - Возвращает [ReadableByteChannel](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/channels/ReadableByteChannel.html). Ожидается, что каждый вызов создает новый канал.

Реализация по умолчанию возвращает [Channels.newChannel(InputStream)](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/channels/Channels.html#newChannel(java.io.InputStream)) - результат [InputStreamSource.getInputStream()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamSource.html#getInputStream()).

*Возвращает:* байтовый канал для базового ресурса (не должен быть null)
*Броски исключений:*
- [FileNotFoundException](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileNotFoundException.html) - если базовый ресурс не существует;
- [IOException](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/IOException.html) - если канал контента не удалось открыть;

---
**Доп. материал:**
- [Resources](https://docs.spring.io/spring-framework/reference/core/resources.html)
- [The Resource interface](https://docs.spring.io/spring-framework/docs/2.5.x/reference/resources.html)
- [Loading Resources in Spring Boot](https://www.danvega.dev/blog/loading-spring-resources)
- [Working with Resources in Spring](https://springframework.guru/working-with-resources-in-spring/)
- [Accessing Resources In Spring A Comprehensive Guide](https://innovationforge.in/2023/01/04/Accessing-Resources-in-Spring-A-Comprehensive-Guide.html)
- [Handle Resources in Spring using Resouce, ResourceLoader and ResourceLoaderAware](https://jstobigdata.com/spring/handle-resources-in-spring/)
- [Load a Resource as a String in Spring](https://www.baeldung.com/spring-load-resource-as-string)
- [Create Custom Resource Loader for Spring Framework](https://bitshifted.co/blog/custom-resource-loader-spring-framework/)
