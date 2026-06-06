- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamSource.html)

---
### Interface InputStreamSource

**Пакет:** [org.springframework.core.io](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/package-summary.html)

**Все известные под-интерфейсы:** 
- [ContextResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ContextResource.html),
- [HttpResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/resource/HttpResource.html),
- [HttpResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/resource/HttpResource.html),
- [MultipartFile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html),
- [Resource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html),
- [WritableResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/WritableResource.html)

**Все известные реализующие классы:** 
- [AbstractFileResolvingResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/AbstractFileResolvingResource.html),
- [AbstractResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/AbstractResource.html),
- [ByteArrayResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ByteArrayResource.html),
- [ClassPathResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ClassPathResource.html),
- [DefaultResourceLoader.ClassPathContextResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/DefaultResourceLoader.ClassPathContextResource.html),
- [DescriptiveResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/DescriptiveResource.html),
- [EncodedResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/support/EncodedResource.html),
- [FileSystemResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/FileSystemResource.html),
- [FileUrlResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/FileUrlResource.html),
- [InputStreamResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamResource.html),
- [MockMultipartFile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mock/web/MockMultipartFile.html),
- [ModuleResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ModuleResource.html),
- [PathResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/PathResource.html),
- [ServletContextResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/ServletContextResource.html),
- [TransformedResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/resource/TransformedResource.html),
- [TransformedResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/resource/TransformedResource.html),
- [UrlResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/UrlResource.html),
- [VfsResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/VfsResource.html)

**Так же изучить:** 
- [InputStream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/InputStream.html),
- [Resource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html),
- [InputStreamResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamResource.html),
- [ByteArrayResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ByteArrayResource.html)

---

```java
  @FunctionalInterface
  public interface InputStreamSource
```

---
Простой интерфейс для объектов, которые являются источниками для [InputStream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/InputStream.html). Это базовый интерфейс для более
расширенного интерфейса [ресурсов Spring](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html).

Для одноразовых потоков [InputStreamResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamResource.html) можно использовать для любого заданного InputStream. Spring [ByteArrayResource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ByteArrayResource.html) или любая реализация файлового ресурса может использоваться в качестве конкретного экземпляра, позволяя читать базовый поток контента несколько раз. Это делает этот интерфейс полезным, например, в качестве абстрактного источника контента для почтовых вложений.

---
### Методы

- `InputStream getInputStream()` - Возвращает InputStream содержимое базового ресурса. Ожидается, что каждый вызов создает новый поток.

Это требование особенно важно, если вы рассматриваете такой API, как JavaMail, который должен иметь возможность читать
поток несколько раз при создании почтовых вложений. Для такого варианта использования требуется, чтобы каждый
getInputStream() вызов возвращал свежий поток.

**Возвращает:** Входной поток для базового ресурса (не должен быть null)

**Броски исключений:**
- [FileNotFoundException](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileNotFoundException.html) - если базовый ресурс не существует;
- [IOException](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/IOException.html) - если поток контента не удалось открыть;

**Смотрите также:**
- [Resource.isReadable()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html#isReadable())
- [Resource.isOpen()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html#isOpen())

---
**Доп. материал:**
- [Java Examples for org.springframework.core.io.InputStreamSource](https://www.javatips.net/api/org.springframework.core.io.inputstreamsource)
- [Uses of Interface org.springframework.core.io.InputStreamSource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/class-use/InputStreamSource.html)
- [Java InputStream To DataHandler Example](https://www.javacodegeeks.com/java-inputstream-to-datahandler-example.html)
- [Java InputStream Tutorial with Examples](https://o7planning.org/13527/java-inputstream)
