- [См. исходник (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/Validated.html)

---
### Annotation Interface Validated

**Пакет:** [org.springframework.validation.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/annotation/package-summary.html)

```java
  @Target({TYPE,METHOD,PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface Validated
```

Вариант [Valid JSR-303](https://beanvalidation.org/1.0/spec/), поддерживающий спецификацию групп проверки. Разработан для удобного использования с поддержкой
[Spring JSR-303](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/validation.html), но не специально для JSR-303. Можно 
использовать, например, с аргументами методов обработчика Spring MVC. Поддерживается посредством концепции подсказок проверки SmartValidator, при этом классы 
групп проверки действуют как объекты подсказок.

Также может использоваться с проверкой на уровне метода, указывая, что определенный класс должен быть проверен на
уровне метода (действуя как точка для соответствующего перехватчика проверки), но также, при необходимости, указывая
группы проверки для валидации на уровне метода в аннотированном классе. Применение этой аннотации на уровне метода
позволяет переопределить группы проверки для конкретного метода, но не служит в качестве точки; тем не менее, аннотация
уровня класса необходима для запуска проверки метода для конкретного bean-компонента. Также может использоваться в
качестве мета-аннотации для пользовательской стереотипной аннотации или пользовательской проверенной аннотации для
конкретной группы.

---
См. так же:
- [Validator.validate(Object, Class[])](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/validation/validator)
- [SmartValidator.validate(Object, org.springframework.validation.Errors, Object...)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/SmartValidator.html)
- [SpringValidatorAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/beanvalidation/SpringValidatorAdapter.html)
- [MethodValidationPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/beanvalidation/MethodValidationPostProcessor.html)

---
#### Дополнительный элемент

- `Class<?>[] value` - Укажите одну или несколько групп проверки, которые будут применяться к этапу проверки, начатому этой аннотацией. JSR-303 определяет группы проверки как пользовательские аннотации, которые приложение объявляет с единственной целью использования их в качестве типобезопасных аргументов группы, как это реализовано в [SpringValidatorAdapter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/beanvalidation/SpringValidatorAdapter.html).

Другие реализации [SmartValidator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/SmartValidator.html) могут поддерживать аргументы класса и другими способами.

---
**Доп. материалы:**
- [Spring Bean Validation - JSR-303 Annotations](https://www.geeksforgeeks.org/springboot/spring-bean-validation-jsr-303-annotations/)
- [Validation, Data Binding, and Type Conversion](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/validation.html)
- [Differences in @Valid and @Validated Annotations in Spring](https://www.baeldung.com/spring-valid-vs-validated)
- [Using JSR-303 with custom validation](https://stackoverflow.com/questions/10272000/using-jsr-303-with-custom-validation)
- [Bean validation and JSR 303](https://blog.frankel.ch/bean-validation-and-jsr-303/)
- [JSR 303 Reference Implementation Reference Guide 4.2.0.Final (PDF)](https://docs.hibernate.org/validator/4.2/reference/en-US/pdf/hibernate_validator_reference.pdf)
