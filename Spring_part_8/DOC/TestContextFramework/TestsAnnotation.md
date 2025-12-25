### Тестовые аннотации

Spring Framework содержит следующий набор аннотаций, специфичных для Spring, которые можно использовать в ваших
модульных и интеграционных тестах в сочетании с фреймворком TestContext. Смотрите соответствующую документацию
для получения дополнительной информации, включая значения атрибутов по умолчанию, псевдонимы атрибутов и другие
сведения.

Для тестирования в Spring существуют следующие аннотации:

- [@BootstrapWith](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/BootstrapWith.html) – это аннотация на уровне класса, которую можно использовать для конфигурирования способа начальной загрузки Spring TestContext Framework. В частности, можно использовать @BootstrapWith для задания специального [TestContextBootstrapper](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestContextBootstrapper.html).

- [@ContextConfiguration](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-contextconfiguration.html) - Аннотация @ContextConfiguration определяет метаданные на уровне класса, которые используются для определения того, как загружать и конфигурировать ApplicationContext для интеграционных тестов.
 
В частности, @ContextConfiguration объявляет locations ресурсов контекста приложения или компонентные classes, используемые для загрузки контекста.

Местоположения ресурсов обычно представляет собой XML-файлы конфигурации или скрипты Groovy, расположенные в classpath, в то время как компонентные классы обычно являются
классами с аннотацией @Configuration. Однако местоположения ресурсов могут также ссылаться на файлы и скрипты в файловой системе, а компонентные классы могут быть
классами, аннотированными @Component, @Service и так далее.

В следующем примере показана аннотация @ContextConfiguration, которая ссылается на XML-файл:

on Java:
```Java
  @ContextConfiguration("/test-config.xml")
  class XmlApplicationContextTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration("/test-config.xml")
  class XmlApplicationContextTests {
      // тело класса...
  }
```

В следующем примере показана аннотация @ContextConfiguration, уже ссылается на класс:

on Java:
```Java
  @ContextConfiguration(classes = TestConfig.class)
  class ConfigClassApplicationContextTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration(classes = [TestConfig::class])
  class ConfigClassApplicationContextTests {
      // тело класса...
  }
```

В качестве альтернативы или в дополнение к объявлению поиска местоположений ресурсов или классов компонентов,
можно использовать @ContextConfiguration для объявления классов ApplicationContextInitializer. В следующем
примере показан случай, когда объявляем класс-инициализатор:

on Java:
```Java
  @ContextConfiguration(initializers = CustomContextInitializer.class)
  class ContextInitializerTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration(initializers = [CustomContextInitializer::class])
  class ContextInitializerTests {
      // тело класса...
  }
```

Также можно использовать аннотацию @ContextConfiguration для объявления стратегии ContextLoader.

Обратите внимание, зачастую не требуется явно конфигурировать загрузчик, поскольку загрузчик по умолчанию
поддерживает initializers и либо locations ресурсов, либо компонентные classes.

В следующем примере используется как местоположение, так и загрузчик:

on Java:
```Java
  @ContextConfiguration(locations = "/test-context.xml", loader = CustomContextLoader.class)
  class CustomLoaderXmlApplicationContextTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration("/test-context.xml", loader = CustomContextLoader::class)
  class CustomLoaderXmlApplicationContextTests {
      // тело класса...
  }
```

---
**!!! Внимание !!!** 

@ContextConfiguration обеспечивает поддержку наследования местоположений ресурсов или
конфигурационных классов, а также инициализаторов контекста, которые объявляются
суперклассами или объемлющими классами.

См. документацию [по @ContextConfiguration для получения более подробных сведений](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ContextConfiguration.html)

---
- **[@WebAppConfiguration](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-webappconfiguration.html)** - это аннотация на уровне класса, которую можно использовать, чтобы объявить, что загружаемым ApplicationContext для интеграционного теста должен быть WebApplicationContext. Само наличие аннотации @WebAppConfiguration в тестовом классе обеспечивает, что для теста будет загружен WebApplicationContext с использованием **значения по умолчанию "file:src/main/webapp"** для пути к корню веб-приложения (то есть для основного пути ресурсов).

Основной путь ресурсов используется "за кулисами" для создания [MockServletContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mock/web/MockServletContext.html), который служит в качестве ServletContext для [WebApplicationContext](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/ctx-management/web.html) теста.

В следующем примере показано, как использовать аннотацию @WebAppConfiguration:

on Java:
```Java
  @ContextConfiguration
  @WebAppConfiguration
  class WebAppTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration
  @WebAppConfiguration
  class WebAppTests {
      // тело класса...
  }
```

Чтобы переопределить значение по умолчанию, можно задать другой основной путь к ресурсам с помощью явного
атрибута value. Поддерживаются префиксы ресурсов classpath: и file: Если префикс ресурса не задан,
предполагается, что путём является ресурс файловой системы.

В следующем примере показано, как задать ресурс classpath:

on Java:
```Java
  @ContextConfiguration
  @WebAppConfiguration("classpath:test-web-resources")
  class WebAppTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
@ContextConfiguration
@WebAppConfiguration("classpath:test-web-resources")
class WebAppTests {
    // тело класса...
}
```

Обратите внимание, что аннотацию [@WebAppConfiguration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/WebApplicationContext.html) необходимо использовать либо в сочетании с аннотацией [@ContextConfiguration](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-contextconfiguration.html) в рамках одного тестового класса, либо в рамках иерархии тестовых классов. Более подробную информацию см. [в документации по аннотации @WebAppConfiguration](https://docs.spring.io/spring-framework/docs/6.0.3/javadoc-api/org/springframework/test/context/web/WebAppConfiguration.html).

---
- **[@ContextHierarchy](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-contexthierarchy.html)** - это аннотация на уровне класса, которая используется для определения иерархии экземпляров ApplicationContext для интеграционных тестов. Аннотацию @ContextHierarchy необходимо объявлять с использованием списка из одного или нескольких экземпляров аннотации                   @ContextConfiguration, каждый из которых определяет уровень в иерархии контекста.

В следующих примерах показано использование аннотации @ContextHierarchy в рамках одного тестового класса
(аннотацию @ContextHierarchy также можно использовать в рамках иерархии тестовых классов):

on Java:
```Java
  @ContextHierarchy({
      @ContextConfiguration("/parent-config.xml"),
      @ContextConfiguration("/child-config.xml")
  })
  class ContextHierarchyTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextHierarchy(
      ContextConfiguration("/parent-config.xml"),
      ContextConfiguration("/child-config.xml"))
  class ContextHierarchyTests {
      // тело класса...
  }
```

on Java:
```Java
  @WebAppConfiguration
  @ContextHierarchy({
      @ContextConfiguration(classes = AppConfig.class),
      @ContextConfiguration(classes = WebConfig.class)
  })
  class WebIntegrationTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @WebAppConfiguration
  @ContextHierarchy(
          ContextConfiguration(classes = [AppConfig::class]),
          ContextConfiguration(classes = [WebConfig::class]))
  class WebIntegrationTests {
      // тело класса...
  }
```

Если требуется объединить или переопределить конфигурацию для данного уровня иерархии контекста в иерархии
тестовых классов, то необходимо явным образом присвоить этому уровню имя, передав одинаковое значение атрибуту
name в аннотации @ContextConfiguration для каждого соответствующего уровня иерархии классов.

Дополнительные примеры см. [в документации по аннотации @ContextHierarchy](https://docs.spring.io/spring-framework/docs/6.0.3/javadoc-api/org/springframework/test/context/ContextHierarchy.html).

---
- **[@ActiveProfiles](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-activeprofiles.html)** - это аннотация на уровне класса, которая используется для объявления того, какие профили определения bean-ов должны быть активны при загрузке ApplicationContext для интеграционного теста.

В следующем примере показано, что профиль dev должен быть активным:

on Java (Указываем, что профиль dev должен быть активным):
```Java
  @ContextConfiguration
  @ActiveProfiles("dev")
  class DeveloperTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration
  @ActiveProfiles("dev")
  class DeveloperTests {
      // тело класса...
  }
```

В следующем примере показано, что оба профиля – dev и integration – должны быть активны:

on Java (Указываем, что профили dev и integration должны быть активны):
```Java
  @ContextConfiguration
  @ActiveProfiles({"dev", "integration"})
  class DeveloperIntegrationTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration
  @ActiveProfiles(["dev", "integration"])
  class DeveloperIntegrationTests {
      // тело класса...
  }
```

---
**!!! Внимание !!!** 

Аннотация @ActiveProfiles обеспечивает поддержку наследования активных профилей определения
bean-ов, объявленных суперклассами и объемлющими классами по умолчанию. Вы также можете
программно разрешать профили определения активных bean-ов, реализовав кастомный
[ActiveProfilesResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ActiveProfilesResolver.html) и зарегистрировав его с помощью атрибута resolver аннотации
@ActiveProfiles.

См. документацию по [аннотации @ActiveProfiles для ознакомления с примерами и получения более подробной информации](https://docs.spring.io/spring-framework/docs/6.0.3/javadoc-api/org/springframework/test/context/ActiveProfiles.html)

---
- **[@TestPropertySource](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-testpropertysource.html)** - это аннотация на уровне класса, которую можно использовать для конфигурирования местоположений файлов свойств и встраиваемых свойств, которые будут добавлены в комплект PropertySources в Environment для ApplicationContext, загружаемого для интеграционного теста.

В следующем примере показано, как объявлять файл свойств из classpath:

on Java (Получаем свойства из test.properties в корне classpath):
```Java
  @ContextConfiguration
  @TestPropertySource("/test.properties")
  class MyIntegrationTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration
  @TestPropertySource("/test.properties")
  class MyIntegrationTests {
      // тело класса...
  }
```

В следующем примере показано, как объявлять встраиваемые свойства:

on Java (Объявляем свойства timezone и port):
```Java
  @ContextConfiguration
  @TestPropertySource(properties = { "timezone = GMT", "port: 4242" })
  class MyIntegrationTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration
  @TestPropertySource(properties = ["timezone = GMT", "port: 4242"])
  class MyIntegrationTests {
      // тело класса...
  }
```

---
- **[@DynamicPropertySource](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-dynamicpropertysource.html)** - это аннотация на уровне метода, которую можно использовать для регистрации динамических свойств, добавляемых в комплект PropertySources в Environment для ApplicationContext, загружаемого для интеграционного теста.

Динамические свойства полезны, если значения свойств заранее неизвестны – например, если свойствами управляет внешний ресурс, как в случае с контейнером, управляемым проектом Testcontainers.

В следующем примере показано, как зарегистрировать динамическое свойство:

on Java по шагам:

- Помечаем static метод аннотацией @DynamicPropertySource;
- Принимаем в качестве аргумента DynamicPropertyRegistry;
- Регистрируем динамическое свойство server.port, которое будет получено с сервера отложено;

```Java
  @ContextConfiguration
  class MyIntegrationTests {
      static MyExternalServer server = // ...
      @DynamicPropertySource
      static void dynamicProperties(DynamicPropertyRegistry registry) {
          registry.add("server.port", server::getPort);
      }
      // tests ...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration
  class MyIntegrationTests {
      companion object {
          @JvmStatic
          val server: MyExternalServer = // ...
          @DynamicPropertySource
          @JvmStatic
          fun dynamicProperties(registry: DynamicPropertyRegistry) {
              registry.add("server.port", server::getPort)
          }
      }
      // tests ...
  }
```

---
- **[@DirtiesContext](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-dirtiescontext.html)** - Аннотация @DirtiesContext указывает, что базовый ApplicationContext из Spring был "загрязнен" во время выполнения теста (то есть тест изменил или испортил его каким-то образом – например, изменив состояние bean-a-одиночки) и его необходимо закрыть. Если контекст приложения помечен
как "грязный", то он удаляется из кэша системы тестирования и закрывается. Как следствие, базовый контейнер Spring перестраивается под любой последующий тест, которому в обязательном порядке требуется контекст с теми же конфигурационными метаданными.

Аннотацию [@DirtiesContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/annotation/DirtiesContext.html) можно использовать как на уровне класса, так и на уровне метода в рамках одного класса или иерархии классов. В таких сценариях ApplicationContext помечается как "грязный" перед или после любого такого аннотированного метода, а также перед или после текущего тестового класса, в зависимости от сконфигурированных
methodMode и classMode.

В следующих примерах объясняется, в каких случаях контекст может быть "загрязнен" при различных сценариях конфигурации:

Перед текущим тестовым классом, если объявлен для класса с помощью режима класса, установленного в BEFORE_CLASS.

on Java ("Грязный" контекст перед текущим тестовым классом):
```Java
  @DirtiesContext(classMode = BEFORE_CLASS)
  class FreshContextTests {
      // некоторые тесты, для которых требуется новый контейнер Spring
  }
```

on Kotlin:
```Kotlin
  @DirtiesContext(classMode = BEFORE_CLASS)
  class FreshContextTests {
      // некоторые тесты, для которых требуется новый контейнер Spring
  }
```

После текущего тестового класса, если объявлен для класса, режим класса которого установлен в AFTER_CLASS (т.е. режим класса по умолчанию).

on Java ("Грязный" контекст после текущего тестового класса):
```Java
  @DirtiesContext
  class ContextDirtyingTests {
      // некоторые тесты, которые приводят к загрязнению контейнера Spring
  }
```

on Kotlin:
```Kotlin
  @DirtiesContext
  class ContextDirtyingTests {
      // некоторые тесты, которые приводят к загрязнению контейнера Spring
  }
```

Перед каждым тестовым методом в текущем тестовом классе, если объявлен для класса, режим класса которого установлен в BEFORE_EACH_TEST_METHOD.

on Java ("Грязный" контекст перед каждым тестовым методом):
```Java
  @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
  class FreshContextTests {
      // некоторые тесты, для которых требуется новый контейнер Spring
  }
```

on Kotlin:
```Kotlin
  @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
  class FreshContextTests {
      // некоторые тесты, для которых требуется новый контейнер Spring
  }
```

После каждого тестового метода в текущем тестовом классе, если объявлен для класса, режим класса которого установлен в AFTER_EACH_TEST_METHOD.

on Java ("Грязный" контекст после каждого тестового метода):
```Java
  @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
  class ContextDirtyingTests {
      // некоторые тесты, которые приводят к загрязнению контейнера Spring
  }
```

on Kotlin:
```Kotlin
  @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
  class ContextDirtyingTests {
      // некоторые тесты, которые приводят к загрязнению контейнера Spring
  }
```

Перед текущим тестом, если объявлен для метода, режим метода которого установлен в BEFORE_METHOD.

on Java ("Грязный" контекст перед текущим тестовым методом):
```Java
  @DirtiesContext(methodMode = BEFORE_METHOD)
  @Test
  void testProcessWhichRequiresFreshAppCtx() {
      // некоторая логика, которой требуется новый контейнер Spring
  }
```

on Kotlin:
```Kotlin
  @DirtiesContext(methodMode = BEFORE_METHOD)
  @Test
  fun testProcessWhichRequiresFreshAppCtx() {
      // некоторая логика, которой требуется новый контейнер Spring
  }
```

После текущего теста, если объявлен в методе, режим метода которого установлен в AFTER_METHOD (т.е. режим метода по умолчанию).

on Java ("Грязный" контекст после текущего тестового метода):
```Java
  @DirtiesContext
  @Test
  void testProcessWhichDirtiesAppCtx() {
      // некоторая логика, которая приводит к загрязнению контейнера Spring
  }
```

on Kotlin:
```Kotlin
  @DirtiesContext
  @Test
  fun testProcessWhichDirtiesAppCtx() {
      // некоторая логика, которая приводит к загрязнению контейнера Spring
  }
```

Если вы используете аннотацию @DirtiesContext в тесте, контекст которого сконфигурирован как часть иерархии
контекстов с помощью аннотации @ContextHierarchy, то можно использовать флаг hierarchyMode, чтобы управлять
процессом очистки контекстного кэша. По умолчанию для очистки контекстного кэша используется алгоритм полного
перебора вариантов, затрагивающий не только текущий уровень, но и все другие контекстные иерархии, имеющие
общий с текущим тестом контекст-предок.

Все экземпляры ApplicationContext, находящиеся в ветви иерархии общего контекста-предка, удаляются из контекстного
кэша и закрываются. Если алгоритм полного перебора вариантов является излишним для конкретного случая использования,
то можно задать более простой алгоритм текущего уровня, как показано в следующем примере.

on Java (Используем алгоритм текущего уровня):
```Java
  @ContextHierarchy({
      @ContextConfiguration("/parent-config.xml"),
      @ContextConfiguration("/child-config.xml")
  })
  class BaseTests {
      // тело класса...
  }
  class ExtendedTests extends BaseTests {
      @Test
      @DirtiesContext(hierarchyMode = CURRENT_LEVEL)
      void test() {
          // некоторая логика, которая приводит к загрязнению дочернего контекста
      }
  }
```

on Kotlin:
```Kotlin
  @ContextHierarchy(
      ContextConfiguration("/parent-config.xml"),
      ContextConfiguration("/child-config.xml"))
  open class BaseTests {
      // тело класса...
  }
  class ExtendedTests : BaseTests() {
      @Test
      @DirtiesContext(hierarchyMode = CURRENT_LEVEL)
      fun test() {
          // некоторая логика, которая приводит к загрязнению дочернего контекста
      }
  }
```

Более подробную информацию об алгоритмах EXHAUSTIVE и CURRENT_LEVEL см. [в документации по "DirtiesContext.HierarchyMode"](https://docs.spring.io/spring-framework/docs/6.0.3/javadoc-api/org/springframework/test/annotation/DirtiesContext.HierarchyMode.html)

---
- **[@TestExecutionListeners](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-testexecutionlisteners.html)** - используется для того, чтобы регистрировать слушателей для определенного тестового класса, его подклассов и вложенных классов. Если требуется зарегистрировать слушателя глобально, то следует регистрировать его через механизм автоматического обнаружения,                           описанный в разделе [Конфигурация TestExecutionListener](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/tel-config.html).

В следующем примере показано, как зарегистрировать две реализации [TestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestExecutionListener.html):

on Java (Регистрируем две реализации TestExecutionListener):
```Java
  @ContextConfiguration
  @TestExecutionListeners({CustomTestExecutionListener.class, AnotherTestExecutionListener.class})
  class CustomTestExecutionListenerTests {
      // тело класса...
  }
```

on Kotlin:
```Kotlin
  @ContextConfiguration
  @TestExecutionListeners(CustomTestExecutionListener::class, AnotherTestExecutionListener::class)
  class CustomTestExecutionListenerTests {
      // тело класса...
  }
```

По умолчанию аннотация @TestExecutionListeners обеспечивает поддержку наследования слушателей от суперклассов или
объемлющих классов. См. документацию по аннотации [@TestExecutionListeners для ознакомления с примером и получения
подробной информации](https://docs.spring.io/spring-framework/docs/6.0.3/javadoc-api/org/springframework/test/context/TestExecutionListeners.html)

---
- **[@RecordApplicationEvents](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-recordapplicationevents.html)** - это аннотация на уровне класса, которая используется для того, чтобы дать Spring TestContext Framework-у команду записывать все события приложения, которые публикуются в ApplicationContext во время выполнения единственного теста.

Доступ к записанным событиям можно получить через ApplicationEvents API в рамках тестов.

См. документацию [по @RecordApplicationEvents"](https://docs.spring.io/spring-framework/docs/6.0.3/javadoc-api/org/springframework/test/context/event/RecordApplicationEvents.html)
и [ApplicationEvents](./ApplicationEvents.md)

---
- **[@Commit](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-commit.html)** - данная аннотация указывает, что транзакция для транзакционного метода тестирования должна быть зафиксирована после завершения метода тестирования. Можно использовать аннотацию [@Commit](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/annotation/Commit.html) в качестве прямой замены аннотации [@Rollback(false)](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-rollback.html), чтобы более явно передать суть кода. Аналогично аннотации [@Rollback](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/annotation/Rollback.html), аннотацию @Commit также можно объявить как аннотацию на уровне класса или метода.

В следующем примере показано, как использовать аннотацию @Commit:

on Java (Фиксируем результат теста в базе данных):
```Java
 @Commit
 @Test
 void testProcessWithoutRollback() {
     // ...
 }
```

on Kotlin:
```Java
 @Commit
 @Test
 fun testProcessWithoutRollback() {
     // ...
 }
```

---
- **[@Rollback](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-rollback.html)** - данная аннотация указывает, нужно ли откатывать транзакцию для транзакционного тестового метода после завершения тестового метода. Если установлено в true, то транзакция откатывается. В противном случае транзакция фиксируется (см. также @Commit). Откат для интеграционных тестов в Spring TestContext Framework по умолчанию имеет значение true, даже если аннотация [@Rollback](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/annotation/Rollback.html) не объявлена явно.

Если аннотация @Rollback объявляется как аннотация на уровне класса, то она определяет семантику отката по
умолчанию для всех тестовых методов в иерархии тестового класса. Если аннотация @Rollback объявлена на уровне
метода, она определяет семантику отката для конкретного метода тестирования, потенциально переопределяя
семантику аннотации @Rollback или @Commit на уровне класса.

В следующем примере результат тестового метода не откатывается (то есть результат фиксируется в базе данных):

on Java (Не откатываем результат):
```Java
 @Rollback(false)
 @Test
 void testProcessWithoutRollback() {
     // ...
 }
```

on Kotlin:
```Kotlin
 @Rollback(false)
 @Test
 fun testProcessWithoutRollback() {
     // ...
 }
```

---
- **[@BeforeTransaction](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-beforetransaction.html)** - данная аннотация указывает, что аннотированный void метод следует выполнять до начала транзакции в случае тестовых методов, сконфигурированных на выполнение в рамках транзакции через аннотацию [@Transactional из Spring](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html). Методы, помеченные аннотацией [@BeforeTransaction](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/transaction/BeforeTransaction.html), не обязательно должны быть public, и они могут быть объявлены в методах интерфейса с реализацией по умолчанию на базе Java 8.

В следующем примере показано, как использовать аннотацию @BeforeTransaction:

on Java (Выполняем этот метод перед транзакцией):
```Java
 @BeforeTransaction
 void beforeTransaction() {
     // логика, которая должна выполняться до начала транзакции
 }
```

on Kotlin:
```Java
 @BeforeTransaction
 fun beforeTransaction() {
      // логика, которая должна выполняться до начала транзакции
 }
```

---
- **[@AfterTransaction](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-aftertransaction.html)** - данная аннотация указывает, что аннотированный void метод следует выполнять после завершения транзакции в случае тестовых методов, сконфигурированных на выполнение в рамках транзакции через аннотацию [@Transactional из Spring](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html). Методы, помеченные аннотацией [@AfterTransaction](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/transaction/AfterTransaction.html), не обязательно должны быть public, и они могут быть объявлены в методах интерфейса с реализацией по умолчанию на базе Java 8.

on Java (Выполняем этот метод после транзакции):
```Java
 @AfterTransaction
 void afterTransaction() {
     // логика, которая будет выполняться после завершения транзакции
 }
```

on Kotlin:
```Kotlin
 @AfterTransaction
 fun afterTransaction() {
     // логика, которая будет выполняться после завершения транзакции
 }
```

---
- **[@Sql](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-sql.html)** - данная аннотация используется для [аннотирования тестового класса](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/executing-sql.html) или тестового метода для
[конфигурирования SQL-скриптов](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/jdbc/Sql.html), которые будут выполняться в отношении заданной базы данных во время интеграционных тестов.

В следующем примере показано, как её использовать:

on Java (Выполняем два скрипта для этого теста):
```Java
 @Test
 @Sql({"/test-schema.sql", "/test-user-data.sql"})
 void userTest() {
     // выполняем код, который использует тестовую схему и тестовые данные
 }
```

on Kotlin:
```Kotlin
 @Test
 @Sql("/test-schema.sql", "/test-user-data.sql")
 fun userTest() {
     // выполняем код, который использует тестовую схему и тестовые данные
 }
```

---
- **[@SqlConfig](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-sqlconfig.html)** - данная [аннотация определяет метаданные](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/jdbc/SqlConfig.html), которые используются для определения того, как анализировать и запускать SQL-скрипты, настроенные с помощью аннотации @Sql.

В следующем примере показано, как её использовать:

on Java (Устанавливаем префикс комментария и разделитель в SQL-скриптах):
```Java
 @Test
 @Sql(
     scripts = "/test-user-data.sql",
     config = @SqlConfig(commentPrefix = "`", separator = "@@")
 )
 void userTest() {
     // выполняем код, который использует тестовые данные
 }
```

on Kotlin:
```Kotlin
 @Test
 @Sql("/test-user-data.sql", config = SqlConfig(commentPrefix = "`", separator = "@@"))
 fun userTest() {
     // выполняем код, который использует тестовые данные
 }
```

---
- **[@SqlMergeMode](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-sqlmergemode.html)** - данная аннотация используется для аннотирования тестового класса или тестового метода, чтобы сконфигурировать объединение объявлений аннотации @Sql на уровне метода с объявлениями аннотации @Sql на уровне класса.

Если аннотация [@SqlMergeMode](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/jdbc/SqlMergeMode.html) не была объявлена для тестового класса или тестового метода, то по
умолчанию будет использоваться режим объединения OVERRIDE. В режиме OVERRIDE объявления аннотации @Sql на уровне метода будут эффективно переопределять объявления аннотации @Sql на уровне класса.

**!!! Обратите внимание !!!** Объявление аннотации @SqlMergeMode на уровне метода переопределяет объявление на уровне класса.

В следующем примере показано, как использовать аннотацию @SqlMergeMode на уровне класса:

on Java (Устанавливаем режим объединения аннотаций @Sql в MERGE для всех тестовых методов в классе):
```Java
 @SpringJUnitConfig(TestConfig.class)
 @Sql("/test-schema.sql")
 @SqlMergeMode(MERGE)
 class UserTests {
     @Test
     @Sql("/user-test-data-001.sql")
     void standardUserProfile() {
         // выполняем код, который задействует тестовый набор данных 001
     }
 }
```

on Kotlin:
```Kotlin
 @SpringJUnitConfig(TestConfig::class)
 @Sql("/test-schema.sql")
 @SqlMergeMode(MERGE)
 class UserTests {
     @Test
     @Sql("/user-test-data-001.sql")
     fun standardUserProfile() {
         // выполняем код, который задействует тестовый набор данных 001
     }
 }
```

В следующем примере показано, как использовать аннотацию @SqlMergeMode на уровне метода:

on Java (Устанавливаем режим объединения аннотаций @Sql в MERGE для конкретного тестового метода):
```Java
 @SpringJUnitConfig(TestConfig.class)
 @Sql("/test-schema.sql")
 class UserTests {
     @Test
     @Sql("/user-test-data-001.sql")
     @SqlMergeMode(MERGE)
     void standardUserProfile() {
         // выполняем код, который задействует тестовый набор данных 001
     }
 }
```

on Kotlin:
```Kotlin
 @SpringJUnitConfig(TestConfig::class)
 @Sql("/test-schema.sql")
 class UserTests {
     @Test
     @Sql("/user-test-data-001.sql")
     @SqlMergeMode(MERGE)
     fun standardUserProfile() {
         // выполняем код, который задействует тестовый набор данных 001
     }
 }
```

---
- **[@SqlGroup](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-sqlgroup.html)** - это контейнерная аннотация, которая объединяет несколько аннотаций @Sql. Можно использовать аннотацию [@SqlGroup](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/jdbc/SqlGroup.html) для объявления нескольких вложенных аннотаций @Sql или использовать её в сочетании со средствами поддержки повторяющихся аннотаций из Java 8, где аннотацию @Sql можно объявлять несколько раз для одного класса или метода, неявно генерируя эту контейнерную аннотацию.

В следующем примере показано, как объявить SQL-группу:

on Java (Объявляем группу SQL-скриптов):
```Java
 @Test
 @SqlGroup({
     @Sql(scripts = "/test-schema.sql", config = @SqlConfig(commentPrefix = "`")),
     @Sql("/test-user-data.sql")
 })
 void userTest() {
     // выполняем код, использующий тестовую схему и тестовые данные
 }
```

on Kotlin:
```Kotlin
 @Test
 @SqlGroup(
     Sql("/test-schema.sql", config = SqlConfig(commentPrefix = "`")),
     Sql("/test-user-data.sql"))
 fun userTest() {
     // выполняем код, использующий тестовую схему и тестовые данные
 }
```
