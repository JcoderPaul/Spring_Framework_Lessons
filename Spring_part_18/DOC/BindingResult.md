- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindingResult.html)

---
### Interface BindingResult

**Пакет:** [org.springframework.validation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/package-summary.html)

**Все супер-интерфейсы:** [Errors](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Errors.html)

**Все реализующие классы:** 
- [AbstractBindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/AbstractBindingResult.html),
- [AbstractPropertyBindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/AbstractPropertyBindingResult.html),
- [BeanPropertyBindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BeanPropertyBindingResult.html),
- [BindException](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindException.html),
- [DirectFieldBindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/DirectFieldBindingResult.html),
- [MapBindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/MapBindingResult.html),
- [MethodArgumentNotValidException](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/MethodArgumentNotValidException.html),
- [WebExchangeBindException](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/support/WebExchangeBindException.html)

```java
  public interface BindingResult extends Errors
```

Общий интерфейс, представляющий результаты привязки. Расширяет интерфейс ошибок для возможностей регистрации ошибок,
позволяя применять валидатор, а также добавляет анализ привязки и построение модели.

Служит держателем результатов для DataBinder, полученных с помощью метода DataBinder.getBindingResult(). Реализации
BindingResult также можно использовать напрямую, например, для вызова валидатора (например, как часть модульного теста).

См. так же:
- [DataBinder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/DataBinder.html)
- [Errors](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Errors.html)
- [Validator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Validator.html)
- [BeanPropertyBindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BeanPropertyBindingResult.html)
- [DirectFieldBindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/DirectFieldBindingResult.html)
- [MapBindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/MapBindingResult.html)

---
#### Поля

- `static final String MODEL_KEY_PREFIX` - Префикс имени экземпляра `BindingResult` в модели, за которым следует имя объекта.

Поля, унаследованные от интерфейса `Errors`:
- `static final String NESTED_PATH_SEPARATOR` - Разделитель между элементами пути во вложенном пути, например в «customer.name» или «customer.address.street». "." = то же, что и nested property separator в пакете beans.

---
#### Методы

- `void addError(ObjectError error)` - Добавить кастом ObjectError или FieldError в список ошибок.
- `PropertyEditor findEditor(String field, Class<?> valueType)` - Найдите редактор настраиваемых свойств для данного типа и свойства.
- `Map<String,Object> getModel()` - Возвращает карту модели для полученного состояния, предоставляя экземпляр `BindingResult` как « MODEL_KEY_PREFIX+ objectName», а сам объект — как «objectName».
- `PropertyEditorRegistry getPropertyEditorRegistry()` - Возвращает базовый PropertyEditorRegistry.
- `Object getRawFieldValue(String field)` - Извлеките необработанное значение поля для данного поля.
- `default String[] getSuppressedFields()` - Верните список полей, которые были подавлены во время процесса привязки.
- `Object getTarget()` - Верните обернутый целевой объект, который может быть bean-компонентом, объектом с общедоступными полями или картой — в зависимости от конкретной стратегии привязки.
- `default void recordFieldValue(String field, Class<?> type, Object value)` - Запишите заданное значение для указанного поля.
- `default void recordSuppressedField(String field)` - Отметьте указанное запрещенное поле как подавленное.
- `String[] resolveMessageCodes(String errorCode)` - Преобразуйте данный код ошибки в коды сообщений.
- `String[] resolveMessageCodes(String errorCode, String field)` - Преобразуйте данный код ошибки в коды сообщений для данного поля.

---
Методы, унаследованные от интерфейса [Errors](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Errors.html): addAllErrors, failOnError, getAllErrors,
getErrorCount, getFieldError, getFieldError, getFieldErrorCount, getFieldErrorCount, getFieldErrors, getFieldErrors, getFieldType, getFieldValue, getGlobalError, getGlobalErrorCount, getGlobalErrors, getNestedPath, getObjectName, hasErrors, hasFieldErrors, hasFieldErrors, hasGlobalErrors, popNestedPath, pushNestedPath, reject, rejectValue, setNestedPath, toString

---
См. доп. материалы:
- [Validation, Data Binding, and Type Conversion](https://docs.spring.io/spring-framework/docs/3.0.x/reference/validation.html)
- [Bind Custom Validation Message in Spring](https://www.baeldung.com/java-spring-bind-custom-validation-message)
- [What is the use of BindingResult interface in spring MVC?](https://stackoverflow.com/questions/10413886/what-is-the-use-of-bindingresult-interface-in-spring-mvc)
- [Spring BindingResult](https://zetcode.com/spring/bindingresult/)
- [Allow Errors/BindingResult after @RequestBody [SPR-7114] #11774](https://github.com/spring-projects/spring-framework/issues/11774)
- [Java Examples for org.springframework.validation.BindingResult](https://www.javatips.net/api/org.springframework.validation.bindingresult)
- [Showing Form Field Error Messages in Spring Boot Without Custom Templates](https://medium.com/@AlexanderObregon/showing-form-field-error-messages-in-spring-boot-without-custom-templates-517e3b41fbd7)
- [Validations in Spring Boot](https://dev.to/kamer/validations-in-spring-boot-6pk)
- [Spring Validation Example - Spring MVC Form Validator](https://www.digitalocean.com/community/tutorials/spring-validation-example-mvc-validator)
- [Validations in Spring Boot](https://medium.com/@himani.prasad016/validations-in-spring-boot-e9948aa6286b)
- [Spring REST BindingResult](https://www.cosmiclearn.com/spring_framework/rest_bindingresult.php)
