- [См. исходную инф. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PathVariable.html)

---
### Annotation Interface PathVariable

---
**Пакет:** [org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html)

```java
  @Target(PARAMETER)
  @Retention(RUNTIME)
  @Documented
  public @interface PathVariable
```

Аннотация, указывающая, что параметр метода должен быть привязан к переменной шаблона URI.
Поддерживается для RequestMapping аннотированных методов-обработчиков.

Если параметр метода равен, Map<String, String> то Map заполняется всеми именами и значениями переменных пути.

---
### Дополнительные параметры

- `String name` - Имя переменной пути для привязки;
- `boolean required` - Требуется ли переменная пути. По умолчанию установлено значение true, что приводит к выдаче
                     исключения, если переменная пути отсутствует во входящем запросе. Переключите это значение на
                     false, если в данном случае вы предпочитаете null или Java 8.java.util.Optional, например,
                     в ModelAttribute методе, который служит для разных запросов.
- `String value` - Псевдоним для name();

---
- [См. исходную инф. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PathVariable.html)
- [Spring @PathVariable Annotation](https://www.baeldung.com/spring-pathvariable)
- [Spring Boot - @PathVariable and @RequestParam Annotations](https://www.geeksforgeeks.org/springboot/spring-boot-pathvariable-and-requestparam-annotations/)
- [Spring @PathVariable Annotation](https://www.geeksforgeeks.org/advance-java/spring-pathvariable-annotation/)
- [Spring @RequestParam vs @PathVariable Annotations](https://www.baeldung.com/spring-requestparam-vs-pathvariable)
