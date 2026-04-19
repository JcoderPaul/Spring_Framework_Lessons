- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/FormatterRegistry.html)

---
### Interface FormatterRegistry

**Пакет:** [org.springframework.format](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/package-summary.html)

**Все супер-интерфейсы (род.):** [ConverterRegistry](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/ConverterRegistry.html)

**Все известные реализующие классы (наслед.):** [DefaultFormattingConversionService](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/support/DefaultFormattingConversionService.html), [FormattingConversionService](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/support/FormattingConversionService.html)

```java
public interface FormatterRegistry
                         extends ConverterRegistry
```

---
#### Методы

- `void addFormatter(Formatter<?> formatter)` - Добавляет форматтер для форматирования полей определенного типа.
- `void addFormatterForFieldAnnotation(AnnotationFormatterFactory<? extends Annotation> annotationFormatterFactory)` - Добавляет средство форматирования в поля форматирования, помеченные определенной аннотацией формата.

- `void addFormatterForFieldType(Class<?> fieldType, Formatter<?> formatter)` - Добавляет средство форматирования для форматирования полей заданного типа. При печати, если тип форматтера T объявлен и fieldType не может быть присвоен T, будет предпринята попытка приведения к T перед делегированием formatter печати значения поля.

При синтаксическом анализе, если возвращаемый анализируемый объект formatter не может быть присвоен типу поля среды выполнения, перед возвратом значения анализируемого поля будет предпринята попытка приведения к типу поля.

- `void addFormatterForFieldType(Class<?> fieldType, Printer<?> printer, Parser<?> parser)` - Добавляет пару Принтер/Парсер для форматирования полей определенного типа. Форматер делегирует полномочия указанному printerдля печати и указанному parserдля синтаксического анализа.

При печати, если тип принтера T объявлен и fieldType не может быть присвоен T, будет предпринята попытка приведения к T перед делегированием printer печати значения поля. При синтаксическом анализе, если объект, возвращаемый анализатором, не может быть присвоен типу поля времени выполнения, перед возвратом проанализированного значения поля будет предпринята попытка приведения к типу поля.

- `void addParser(Parser<?> parser)` - Добавляет парсер для анализа полей определенного типа.
- `void addPrinter(Printer<?> printer)` - Добавляет принтер для печати полей определенного типа.

---
Методы, унаследованные от интерфейса org.springframework.core.convert.converter.ConverterRegistry: addConverter, addConverterFactory, removeConvertible

---
**Доп. материалы:**
- [Spring Field Formatting](https://docs.spring.io/spring-framework/reference/core/validation/format.html)
- [FormatterRegistry on GitHub](https://github.com/spring-projects/spring-framework/blob/main/spring-context/src/main/java/org/springframework/format/FormatterRegistry.java)
- [Uses of Interface org.springframework.format.FormatterRegistry](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/class-use/FormatterRegistry.html)
- [Java Examples for org.springframework.format.FormatterRegistry](https://www.javatips.net/api/org.springframework.format.formatterregistry)
- [Guide to Spring Type Conversions](https://www.baeldung.com/spring-type-conversions)
- [Spring Framework - FormatterRegistry Examples](https://www.logicbig.com/how-to/code-snippets/jcode-spring-framework-formatterregistry.html)
- [The Mechanics Behind How Spring Boot Configures Type Conversion in Forms](https://medium.com/@AlexanderObregon/the-mechanics-behind-how-spring-boot-configures-type-conversion-in-forms-b7ac2c2c9549)
