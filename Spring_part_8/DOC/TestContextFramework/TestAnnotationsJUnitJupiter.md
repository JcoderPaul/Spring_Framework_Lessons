### Тестовые аннотации JUnit Jupiter

Следующие аннотации поддерживаются при использовании в сочетании с SpringExtension и JUnit Jupiter
(то есть моделью программирования в JUnit 5):

---
- **[@SpringJUnitConfig](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit/jupiter/SpringJUnitConfig.html)** - это составная аннотация, которая объединяет @ExtendWith(SpringExtension.class) из JUnit Jupiter с @ContextConfiguration из Spring TestContext Framework. Её можно использовать на уровне класса в качестве замены @ContextConfiguration. Что касается                    параметров конфигурации, единственное различие между аннотациями @ContextConfiguration и @SpringJUnitConfig заключается в том, что в аннотации @SpringJUnitConfig классы компонентов могут быть объявлены с атрибутом value.

В следующем примере показано, как использовать аннотацию @SpringJUnitConfig для задания конфигурационного класса:

on Java (Задаем конфигурационный класс):
```Java
  @SpringJUnitConfig(TestConfig.class)
  class ConfigurationClassJUnitJupiterSpringTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @SpringJUnitConfig(TestConfig::class)
  class ConfigurationClassJUnitJupiterSpringTests {
      // тело класса...
  }
```

В следующем примере показано, как использовать аннотацию @SpringJUnitConfig для задания местоположения
конфигурационного файла:

on Java (Задаем местоположение конфигурационного файла):
```Java
  @SpringJUnitConfig(locations = "/test-config.xml")
  class XmlJUnitJupiterSpringTests {
      // тело класса...
  }
```

on Kotlin:
```Java
  @SpringJUnitConfig(locations = ["/test-config.xml"])
  class XmlJUnitJupiterSpringTests {
      // тело класса...
  }
```

Более подробную информацию см. документацию по [@SpringJUnitConfig](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/junit/jupiter/SpringJUnitConfig.html) и [@ContextConfiguration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ContextConfiguration.html).

---
- **[@SpringJUnitWebConfig](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit/jupiter/web/SpringJUnitWebConfig.html)** - это составная аннотация, которая объединяет аннотацию @ExtendWith(SpringExtension.class) из JUnit Jupiter с аннотациями @ContextConfiguration и @WebAppConfiguration из Spring TestContext Framework. Её можно использовать на уровне класса в качестве замены
аннотаций @ContextConfiguration и @WebAppConfiguration.

Что касается параметров конфигурации, единственное различие между аннотациями @ContextConfiguration и @SpringJUnitWebConfig заключается в том, что в аннотации @SpringJUnitWebConfig компонентные классы могут быть объявлены с использованием атрибута value.

Кроме того, переопределить атрибут value из аннотации @WebAppConfiguration можно исключительно с помощью атрибута resourcePath в аннотации @SpringJUnitWebConfig.

В следующем примере показано, как использовать аннотацию @SpringJUnitWebConfig для задания конфигурационного класса:

on Java (Задаем конфигурационный класс):
```Java
  @SpringJUnitWebConfig(TestConfig.class)
  class ConfigurationClassJUnitJupiterSpringWebTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @SpringJUnitWebConfig(TestConfig::class)
  class ConfigurationClassJUnitJupiterSpringWebTests {
      // тело класса...
  }
```

В следующем примере показано, как использовать аннотацию @SpringJUnitWebConfig для задания местоположения файла конфигурации:

on Java (Задаем местоположение конфигурационного файла):
```Java
  @SpringJUnitWebConfig(locations = "/test-config.xml")
  class XmlJUnitJupiterSpringWebTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @SpringJUnitWebConfig(locations = ["/test-config.xml"])
  class XmlJUnitJupiterSpringWebTests {
      // тело класса...
  }
```

Более подробную информацию см. в документации по:
- [@SpringJUnitWebConfig](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/junit/jupiter/web/SpringJUnitWebConfig.html);
- [@ContextConfiguration](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/ContextConfiguration.html);
- [@WebAppConfiguration](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/web/WebAppConfiguration.html);

---
- **[@TestConstructor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestConstructor.html)** - это аннотация уровня типа, которая используется для настройки того, как параметры конструктора тестового класса будут автоматически определены и связаны из компонентов в ApplicationContext теста.

Если аннотация @TestConstructor не присутствует или присутствует через мета-аннотацию в тестовом классе,
будет использоваться режим автоматического обнаружения и связывания тестового конструктора по умолчанию.
Подробнее о том, как изменить режим по умолчанию, см. предупреждение ниже.

**Обратите внимание, что локальное объявление аннотации @Autowired для конструктора имеет приоритет над
аннотацией @TestConstructor и режимом по умолчанию.**

---
**!!! ВНИМАНИЕ !!!** 

Изменение режима автоматического обнаружения и связывания тестового конструктора по умолчанию.
Режим автоматического обнаружения и связывания тестового конструктора по умолчанию можно изменить,
установив системное свойство spring.test.constructor.autowire.mode из JVM в значение all. Кроме
того, режим по умолчанию можно установить через механизм SpringProperties.

Начиная со Spring Framework 5.3, режим по умолчанию также может быть сконфигурирован как параметр
конфигурации JUnit Platform.

Если свойство spring.test.constructor.autowire.mode не установлено, конструкторы тестовых классов
не будут автоматически обнаруживаться и связываться.

---
**!!! ВНИМАНИЕ !!!** 

Начиная со Spring Framework 5.2, аннотация @TestConstructor поддерживается только в сочетании со
SpringExtension для использования с JUnit Jupiter.

Обратите внимание, что SpringExtension часто регистрируется автоматически – например, при
использовании аннотаций @SpringJUnitConfig и @SpringJUnitWebConfig или различных связанных
с тестированием аннотаций из Spring Boot Test.

---
- **[@NestedTestConfiguration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/NestedTestConfiguration.html)** - это аннотация на уровне типов, которая используется для настройки того, каким образом тестовые конфигурационные аннотации в Spring обрабатываются внутри иерархии классов для внутренних тестовых классов.

Если аннотация @NestedTestConfiguration не присутствует или присутствует через мета-аннотацию в тестовом классе,
в иерархии его супертипов или в иерархии его объемлющих классов, то будет использоваться режим наследования
объемлющей конфигурации по умолчанию. Подробнее о том, как изменить режим по умолчанию, см. предупреждение ниже.

---
**!!! ВНИМАНИЕ !!!** 

Изменение режима наследования объемлющей конфигурации по умолчанию.
По умолчанию режим наследования объемлющей конфигурации - INHERIT, но его можно изменить,
установив системное свойство spring.test.enclosing.configuration из JVM в OVERRIDE. Как
вариант, режим по умолчанию можно установить через механизм SpringProperties (см.
DOC/TestContextFramework/SpringProperties.txt).

---
[Spring TestContext Framework](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework.html) поддерживает семантику аннотации @NestedTestConfiguration для следующих аннотаций:

- @BootstrapWith;
- @ContextConfiguration;
- @WebAppConfiguration;
- @ContextHierarchy;
- @ActiveProfiles;
- @TestPropertySource;
- @DynamicPropertySource;
- @DirtiesContext;
- @TestExecutionListeners;
- @RecordApplicationEvents;
- @Transactional;
- @Commit;
- @Rollback;
- @Sql;
- @SqlConfig;
- @SqlMergeMode;
- @TestConstructor;

---
**!!! ВНИМАНИЕ !!!** 

Использование аннотации @NestedTestConfiguration обычно имеет смысл только в сочетании с
помеченными аннотацией @Nested тестовыми классами из JUnit Jupiter; однако могут существовать
и другие тестовые фреймворки с поддержкой Spring и вложенных тестовых классов, которые
используют эту аннотацию (см. DOC/TestContextFramework/HelperClassesOfTestContext.txt).

---
- **[@EnabledIf](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit/jupiter/EnabledIf.html)** - данная аннотация используется для сигнализации о том, что аннотированный тестовый класс из JUnit Jupiter или тестовый метод активирован и должен выполняться, если указанное expression оказывается true.

В частности, если выражение имеет значение Boolean.TRUE или String равна true (случай игнорирования),
тест активируется. При применении на уровне класса, все методы тестирования внутри этого класса
также автоматически активируются по умолчанию.

Выражения могут иметь любую из следующих форм:
- Выражение на языке Spring Expression Language (SpEL).
  Пример: **@EnabledIf("#{systemProperties['os.name'].toLowerCase().contains('mac')}")**
- Плейсхолдер для свойства, доступного в Environment фреймворка Spring.
  Пример: **@EnabledIf("${smoke.tests.enabled}")**
- Текстовый литерал.
  Пример: **@EnabledIf("true")**

Обратите внимание, что текстовый литерал, который не является результатом динамического разрешения плейсхолдера
свойства, имеет нулевую практическую ценность, поскольку аннотация @EnabledIf("false") эквивалентна аннотации
@Disabled, а аннотация @EnabledIf("true") логически бессмысленна.

Можно использовать аннотацию @EnabledIf в качестве мета-аннотации для создания специальных составных аннотаций.
Например, можно создать специальную аннотацию @EnabledOnMac следующим образом:

on Java:
```Java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@EnabledIf(
    expression = "#{systemProperties['os.name'].toLowerCase().contains('mac')}",
    reason = "Enabled on Mac OS"
)
public @interface EnabledOnMac {}
```

on Kotlin:
```Kotlin
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@EnabledIf(
        expression = "#{systemProperties['os.name'].toLowerCase().contains('mac')}",
        reason = "Enabled on Mac OS"
)
annotation class EnabledOnMac {}
```

---
**!!! ВНИМАНИЕ !!!** 

Аннотация @EnabledOnMac служит исключительно в качестве примера того, что это возможно.
Если у вас именно такой случай, воспользуйтесь встроенной поддержкой аннотации
@EnabledOnOs(MAC) в JUnit Jupiter.

---
**!!! ВНИМАНИЕ !!!** 

Начиная с JUnit 5.7, JUnit Jupiter также имеет условную аннотацию под названием @EnabledIf.
Таким образом, если вам нужно использовать поддержку аннотации @EnabledIf в Spring, убедитесь,
что импортируете тип аннотации из правильного пакета.

---
- **[@DisabledIf](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit/jupiter/DisabledIf.html)** - данная аннотация используется для сигнализации о том, что аннотированный тестовый класс из JUnit Jupiter или тестовый метод запрещен и не должен выполняться, если предоставленное выражение оказывается true.

В частности, если выражение имеет значение Boolean.TRUE или String равна true (случай игнорирования),
тест запрещается. При применении на уровне класса все тестовые методы в этом классе также
автоматически запрещаются.

Выражения могут иметь любую из следующих форм:
- Выражение на языке Spring Expression Language (SpEL).
  Пример: **@DisabledIf("#{systemProperties['os.name'].toLowerCase().contains('mac')}")**
- Плейсхолдер для свойства, доступного в Environment фреймоврка Spring.
  Пример: **@DisabledIf("${smoke.tests.disabled}")**
- Текстовый литерал.
  Пример: **@DisabledIf("true")**

Обратите внимание, однако, что текстовый литерал, который не является результатом динамического разрешения
плейсхолдера свойства, имеет нулевую практическую ценность, поскольку аннотация @DisabledIf("true") эквивалентна
аннотации @Disabled, а аннотация @DisabledIf("false") логически бессмысленна.

Можно использовать аннотацию @DisabledIf в качестве мета-аннотации для создания специальных составных аннотаций.
Например, можно создать специальную аннотацию @DisabledOnMac следующим образом:

on Java:
```Java
  @Target({ElementType.TYPE, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @DisabledIf(
      expression = "#{systemProperties['os.name'].toLowerCase().contains('mac')}",
      reason = "Disabled on Mac OS"
  )
  public @interface DisabledOnMac {}
```

on Kotlin:
```Kotlin
  @Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
  @Retention(AnnotationRetention.RUNTIME)
  @DisabledIf(
          expression = "#{systemProperties['os.name'].toLowerCase().contains('mac')}",
          reason = "Disabled on Mac OS"
  )
  annotation class DisabledOnMac {}
```

---
**!!! ВНИМАНИЕ !!!** 

Аннотация @DisabledOnMac служит исключительно в качестве примера того, что это возможно.
Если у вас именно такой случай, воспользуйтесь встроенной поддержкой аннотации
@DisabledOnOs(MAC) в JUnit Jupiter.

---
**!!! ВНИМАНИЕ !!!** 
Начиная с JUnit 5.7, JUnit Jupiter также имеет условную аннотацию под названием @DisabledIf.
Таким образом, если вам нужно использовать поддержку аннотации @DisabledIf в Spring, убедитесь,
что импортируете тип аннотации из правильного пакета.
