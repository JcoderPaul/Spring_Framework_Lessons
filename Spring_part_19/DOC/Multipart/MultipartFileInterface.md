- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html)

---
### Interface MultipartFile

**Пакет:** [org.springframework.web.multipart](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/package-summary.html)

**Все супер-интерфейсы:** [InputStreamSource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamSource.html)

**Все известные реализующие классы:** 
- [MockMultipartFile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mock/web/MockMultipartFile.html).

**Так же изучить:** 
- [MultipartHttpServletRequest](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartHttpServletRequest.html),
- [MultipartResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartResolver.html)

```java
  public interface MultipartFile extends InputStreamSource
```

Представление загруженного файла, полученного в составном запросе. Содержимое файла либо хранится в памяти, либо
временно на диске. В любом случае пользователь несет ответственность за копирование содержимого файла в хранилище
уровня сеанса или постоянное хранилище по своему усмотрению. Временное хранилище будет очищено по окончании обработки
запроса.

---
#### Методы

- `byte[] getBytes()` - Возвращает содержимое файла в виде массива байтов.
- `String getContentType()` - Возвращает тип содержимого файла.

- `InputStream getInputStream()` - Возвращает InputStream для чтения содержимого файла. Пользователь несет ответственность за закрытие возвращенного потока.
  - *Указано:* getInputStream в интерфейсе [InputStreamSource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/InputStreamSource.html).
  - *Возвращает:* Содержимое файла в виде потока или пустой поток, если он пуст.
  - *Броски исключений:* [IOException](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/IOException.html) - в случае ошибок доступа (в случае сбоя временного хранилища).
  - *Смотреть также:*
    - [Resource.isReadable()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html#isReadable())
    - [Resource.isOpen()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html#isOpen())

- `String getName()` - Возвращает имя параметра в составной форме.

---
- `String getOriginalFilename()` - Возвращает исходное имя файла в файловой системе клиента. Он может содержать информацию о пути в зависимости от используемого браузера, но обычно он не содержится ни в одном другом браузере, кроме Opera.

*Примечание.* Имейте в виду, что это имя файла предоставляется клиентом и не должно использоваться вслепую. Помимо
отсутствия использования части каталога, имя файла также может содержать такие символы, как «..», и другие символы,
которые могут быть использованы злонамеренно. Не рекомендуется использовать это имя файла напрямую. Желательно создать
уникальный и сохранить его где-нибудь для справки, если необходимо.

*Возвращает:* исходное имя файла или пустая строка, если файл не был выбран в составной форме, или `null` если он не
определен или недоступен.

*Смотрите также:*
- [RFC 7578, раздел 4.2.](https://datatracker.ietf.org/doc/html/rfc7578#section-4.2)
- [Неограниченная загрузка файлов](https://owasp.org/www-community/vulnerabilities/Unrestricted_File_Upload)
---

- `default Resource getResource()` - Возвращает представление ресурса данного MultipartFile. Это можно использовать в качестве входных данных для RestTemplate или WebClient для предоставления длины
содержимого и имени файла вместе с InputStream.
- `long getSize()` - Возвращает размер файла в байтах.
- `boolean isEmpty()` - Возвращает значение, является ли загруженный файл пустым, то есть либо файл не был выбран в составной форме, либо выбранный файл не имеет содержимого.

---
- `void transferTo(File dest)`  - Переносит полученный файл в указанный файл назначения. Тут можно либо переместить файл в файловой системе, либо скопировать файл в файловую систему, либо сохранить содержимое, хранящееся в памяти, в файл назначения. Если целевой файл уже существует, он будет удален первым.

Если целевой файл был перемещен в файловой системе, эту операцию впоследствии нельзя будет вызвать снова. Поэтому вызовите этот метод только один раз, чтобы работать с любым механизмом хранения.

*ПРИМЕЧАНИЕ.* В зависимости от базового поставщика временное хранилище может зависеть от контейнера, включая базовый каталог для относительных мест назначения, указанных здесь (например, с помощью многочастной обработки сервлета). При абсолютном назначении целевой файл может быть переименован/перемещен из временного местоположения или заново скопирован, даже если временная копия уже существует.

*Параметры:* `dest` - файл назначения (обычно абсолютный)

*Броски исключений:*
- IOException - в случае ошибок чтения или записи;
- IllegalStateException- если файл уже был перемещен в файловую систему и больше не доступен для повторной передачи;

*Смотреть также:* [Part.write(String)](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/servlet/http/part#write-java.lang.String-)

---
- `default void transferTo(Path dest)` - Перенесите полученный файл в указанный файл назначения. Реализация по умолчанию просто копирует поток ввода файла.

---
**Доп. материал:**
- [Multipart](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/multipart-forms.html)
- [MultipartFile.java](https://github.com/spring-projects/spring-framework/blob/main/spring-web/src/main/java/org/springframework/web/multipart/MultipartFile.java)
- [Multipart Request Handling in Spring](https://www.baeldung.com/sprint-boot-multipart-requests)
- [Breaking Down the Multipart Upload Process within Spring Boot](https://medium.com/@AlexanderObregon/breaking-down-the-multipart-upload-process-within-spring-boot-9ad27fb4138f)
- [Multipart File upload Spring Boot](https://stackoverflow.com/questions/25699727/multipart-file-upload-spring-boot)
- [Spring Boot - File Handling](https://www.geeksforgeeks.org/java/spring-boot-file-handling/)
- [Uploading MultipartFile with Spring RestTemplate](https://www.baeldung.com/spring-rest-template-multipart-upload)
- [Java Examples for org.springframework.web.multipart.MultipartFile](https://www.javatips.net/api/org.springframework.web.multipart.multipartfile)
