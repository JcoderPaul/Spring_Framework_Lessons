### Вспомогательные классы фреймворка TestContext

[Spring TestContext Framework](https://docs.spring.io/spring-framework/docs/5.1.8.RELEASE/spring-framework-reference/testing.html) предлагает полную интеграцию с JUnit 4 через специальное средство выполнения (runner)
(поддерживается c JUnit 4.12 или выше). Помечая тестовые классы аннотацией @RunWith(SpringJUnit4ClassRunner.class)
или более коротким вариантом @RunWith(SpringRunner.class), разработчики могут реализовать стандартные модульные и
интеграционные тесты на основе JUnit 4 и одновременно использовать преимущества фреймворка TestContext:

- поддержка загрузки контекстов приложения;
- внедрение зависимостей в экземпляры тестов;
- транзакционное выполнение тестовых методов;
- и т.д.;

Если вам необходимо использовать Spring TestContext Framework с альтернативным средством выполнения (таким как
средство выполнения Parameterized из JUnit 4) или сторонними средствами выполнения (такими как MockitoJUnitRunner),
вы можете, по желанию, использовать средства поддержки Spring для правил JUnit вместо этого.

В следующем куске кода показаны минимальные требования, чтобы сконфигурировать тестовый класс для выполнения
с помощью специального Runner в Spring:

on JAVA:
```Java
    @RunWith(SpringRunner.class)
    @TestExecutionListeners({})
    public class SimpleTest {
        @Test
        public void testMethod() {
            // логика тестирования...
        }
    }
```

on Kotlin:
```Kotlin
    @RunWith(SpringRunner::class)
    @TestExecutionListeners
    class SimpleTest {
        @Test
        fun testMethod() {
            // логика тестирования...
        }
    }
```

В предыдущих примерах аннотация [@TestExecutionListeners](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-testexecutionlisteners.html) сконфигурирована с пустым списком, чтобы отключить слушателей по умолчанию, которые в противном случае потребовали бы конфигурирования ApplicationContext через аннотацию [@ContextConfiguration](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-contextconfiguration.html).

### Правила Spring JUnit 4

Пакет [org.springframework.test.context.junit4.rules](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit4/rules/package-summary.html) предоставляет следующие правила JUnit 4 (поддерживаются с JUnit 4.12 или выше):

- [SpringClassRule](https://docs.spring.io/spring-framework/docs/5.0.2.RELEASE/kdoc-api/spring-framework/org.springframework.test.context.junit4.rules/-spring-class-rule/index.html);
- [SpringMethodRule](https://docs.spring.io/spring-framework/docs/5.0.2.RELEASE/kdoc-api/spring-framework/org.springframework.test.context.junit4.rules/-spring-method-rule/index.html);

SpringClassRule – это TestRule из JUnit, поддерживающее функции Spring TestContext Framework на уровне классов,
а SpringMethodRule – это MethodRule из JUnit, поддерживающее функции Spring TestContext Framework на уровне
экземпляров и методов.

В отличие от SpringRunner, преимуществом средства поддержки JUnit на основе правил в Spring является то, что оно
независимо от любой реализации org.junit.runner.Runner и поэтому может быть объединено с существующими
альтернативными средствами выполнения (такими как Parameterized из JUnit 4) или сторонними средствами выполнения
(такими как MockitoJUnitRunner).

Для поддержки полной функциональности фреймворка TestContext необходимо объединить SpringClassRule с
SpringMethodRule. В следующем примере показан правильный способ объявления этих правил в интеграционном
тесте:

on JAVA:
```Java
    // Опционально задаем средство выполнения, не являющееся Runner из Spring, через @RunWith(...)
    @ContextConfiguration
    public class IntegrationTest {
        @ClassRule
        public static final SpringClassRule springClassRule = new SpringClassRule();
        @Rule
        public final SpringMethodRule springMethodRule = new SpringMethodRule();
        @Test
        public void testMethod() {
            // логика тестирования...
        }
    }
```

on Kotlin:
```Kotlin
    // Опционально задаем средство выполнения, не являющееся Runner из Spring, через @RunWith(...)
    @ContextConfiguration
    class IntegrationTest {
        @Rule
        val springMethodRule = SpringMethodRule()
        @Test
        fun testMethod() {
            // логика тестирования...
        }
        companion object {
            @ClassRule
            val springClassRule = SpringClassRule()
        }
    }
```

---
### Вспомогательные классы JUnit 4

Пакет [org.springframework.test.context.junit4](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit4/package-summary.html) предоставляет следующие вспомогательные классы для тестовых случаев на базе JUnit 4 (поддерживается c JUnit 4.12 или выше):

- [AbstractJUnit4SpringContextTests](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit4/AbstractJUnit4SpringContextTests.html);
- [AbstractTransactionalJUnit4SpringContextTests](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit4/AbstractTransactionalJUnit4SpringContextTests.html);

Где - AbstractJUnit4SpringContextTests – это абстрактный базовый тестовый класс, который интегрирует
      Spring TestContext Framework с явной поддержкой тестирования ApplicationContext в среду JUnit 4.
      Если вы расширите AbstractJUnit4SpringContextTests, то можете получить доступ к protected
      переменной экземпляра applicationContext, которую можно использовать для выполнения явного поиска
      bean-ов или для тестирования состояния контекста в целом.

Где - AbstractTransactionalJUnit4SpringContextTests – это абстрактное транзакционное расширение
      AbstractJUnit4SpringContextTests, которое добавляет некоторые удобные функции для доступа к JDBC.
      Этот класс ожидает, что в ApplicationContext будут определены bean-ы [javax.sql.DataSource](https://docs.oracle.com/javase/8/docs/api/javax/sql/DataSource.html) и bean
      [PlatformTransactionManager](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/PlatformTransactionManager.html).

      Если вы расширите AbstractTransactionalJUnit4SpringContextTests, то можете получить доступ к
      protected переменной экземпляра jdbcTemplate, которую можно использовать, чтобы выполнять
      SQL-инструкции для осуществления запросов к базе данных. Можно использовать такие запросы для
      подтверждения состояния базы данных как до, так и после выполнения кода приложения, связанного
      с базой данных, а Spring гарантированно обеспечит, что такие запросы будут выполнены в рамках
      той же транзакции, что и код приложения.

      При использовании в сочетании с ORM-инструментом следует избегать ложных срабатываний см.
      [TransactionManagement.md](./TransactionManagement.md). AbstractTransactionalJUnit4SpringContextTests
      также предоставляет вспомогательные методы, которые делегируют полномочия методам в JdbcTestUtils
      с помощью вышеупомянутого jdbcTemplate. Более того, AbstractTransactionalJUnit4SpringContextTests
      предоставляет метод executeSqlScript(..) для выполнения SQL-скриптов в отношении сконфигурированного
      DataSource.

---
**!!! Внимание !!!** 

Эти классы являются вспомогательным средством для расширения. Если вам не нужно, чтобы
ваши тестовые классы были привязаны к иерархии классов, специфичной для Spring, то можете
настроить свои собственные специальные тестовые классы с помощью аннотации
@RunWith(SpringRunner.class) или правил JUnit для Spring.

---
### SpringExtension для JUnit Jupiter

Spring TestContext Framework предлагает полную интеграцию с тестовым фреймворком JUnit Jupiter, представленным
в JUnit 5. Аннотируя классы тестов с помощью @ExtendWith(SpringExtension.class), вы можете реализовать стандартные
модульные и интеграционные тесты на основе JUnit Jupiter и одновременно пользоваться преимуществами фреймворка
TestContext, такими как:

- поддержка загрузки контекстов приложения;
- внедрение зависимостей в экземпляры тестов;
- транзакционное выполнение тестовых методов;
- и т.п.

Более того, благодаря богатому API-интерфейсу расширения в JUnit Jupiter, Spring предоставляет следующие
возможности сверх того набора функций, который Spring поддерживает для JUnit 4 и TestNG:
- Внедрение зависимостей для тестовых конструкторов, тестовых методов и методов обратного вызова жизненного
  цикла тестов.

- Полнофункциональная поддержка условного выполнения тестов на основе выражений [на языке SpEL](https://junit.org/junit5/docs/current/user-guide/#extensions-conditions), переменных среды,
  системных свойств и т.д. Более подробную информацию и примеры см. в документации по аннотациям @EnabledIf и @DisabledIf в разделе ["Аннотации из JUnit Jupiter для тестирования в Spring"](./TestAnnotationsJUnitJupiter.md).

- Специальные составные аннотации, которые объединяют аннотации из Spring и JUnit Jupiter. Более подробную
  информацию см. в примерах аннотаций [@TransactionalDevTestConfig и @TransactionalIntegrationTest](https://docs.spring.io/spring-framework/docs/5.0.x/spring-framework-reference/testing.html) в разделе
  ["Поддержка мета-аннотаций для тестирования"](./MetaAnnotationSupportForTesting.md).

В следующем куске кода показано, как сконфигурировать тестовый класс для использования SpringExtension в
сочетании с @ContextConfiguration:

on JAVA:
```Java
    // Даем команду JUnit Jupiter расширить тест средствами поддержки из Spring.
    @ExtendWith(SpringExtension.class)
    // Даем команду  Spring загрузить ApplicationContext из TestConfig.class
    @ContextConfiguration(classes = TestConfig.class)
    class SimpleTests {
        @Test
        void testMethod() {
            // логика тестирования...
        }
    }
```

on Kotlin:
```Kotlin
    / Даем команду JUnit Jupiter расширить тест средствами поддержки из Spring.
    @ExtendWith(SpringExtension::class)
    // Даем команду  Spring загрузить ApplicationContext из TestConfig.class
    @ContextConfiguration(classes = [TestConfig::class])
    class SimpleTests {
        @Test
        fun testMethod() {
            // логика тестирования...
        }
    }
```

Поскольку в JUnit 5 можно также использовать аннотации в качестве мета-аннотаций, Spring предоставляет составные
аннотации [@SpringJUnitConfig](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit/jupiter/SpringJUnitConfig.html) и [@SpringJUnitWebConfig](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-junit-jupiter.html) для упрощения конфигурации тестового ApplicationContext и JUnit Jupiter.

В следующем примере аннотация @SpringJUnitConfig используется для уменьшения объема конфигурации:

on JAVA:
```Java
    // Дает Spring команду зарегистрировать SpringExtension в JUnit
    // Jupiter и загрузить ApplicationContext из TestConfig.class
    @SpringJUnitConfig(TestConfig.class)
    class SimpleTests {
        @Test
        void testMethod() {
            // логика тестирования...
        }
    }
```

on Kotlin:
```Kotlin
    // Дает Spring команду зарегистрировать SpringExtension в JUnit
    // Jupiter и загрузить ApplicationContext из TestConfig.class
    @SpringJUnitConfig(TestConfig::class)
    class SimpleTests {
        @Test
        fun testMethod() {
            // логика тестирования...
        }
    }
```

Аналогично, в следующем примере аннотация @SpringJUnitWebConfig использована, чтобы создать WebApplicationContext
для использования с JUnit Jupiter:

on JAVA:
```Java
    // Дает Spring команду зарегистрировать SpringExtension в JUnit
    // Jupiter и загрузить WebApplicationContext из TestWebConfig.class
    @SpringJUnitWebConfig(TestWebConfig.class)
    class SimpleWebTests {
        @Test
        void testMethod() {
            // логика тестирования...
        }
    }
```

on Kotlin:
```Kotlin
    // Дает Spring команду зарегистрировать SpringExtension в JUnit
    // Jupiter и загрузить WebApplicationContext from TestWebConfig::class
    @SpringJUnitWebConfig(TestWebConfig::class)
    class SimpleWebTests {
        @Test
        fun testMethod() {
            // логика тестирования...
        }
    }
```

Более подробную информацию см. в документации по @SpringJUnitConfig и @SpringJUnitWebConfig в разделе
["Аннотации из JUnit Jupiter для тестирования в Spring"](./TestAnnotationsJUnitJupiter.md).

---
### Внедрение зависимостей с помощью SpringExtension

SpringExtension реализует API-интерфейс расширения ParameterResolver из JUnit Jupiter, что позволяет Spring
обеспечить внедрение зависимостей для тестовых конструкторов, тестовых методов и методов обратного вызова
жизненного цикла тестов.

В частности, SpringExtension может внедрять зависимости из ApplicationContext теста в тестовые конструкторы
и методы, аннотированные @BeforeAll, @AfterAll, @BeforeEach, @AfterEach, @Test, @RepeatedTest,
@ParameterizedTest и другими аннотациями.

---
### Внедрение зависимостей через конструктор

Если определенный параметр в конструкторе тестового класса из JUnit Jupiter имеет тип ApplicationContext
(или его подтип) или аннотирован или мета-аннотирован с помощью @Autowired, @Qualifier или @Value, то Spring
внедряет значение этого параметра с использованием соответствующего bean-a или значения из ApplicationContext
теста.

Spring также можно сконфигурировать на автоматическое обнаружение и связывание всех аргументов конструктора
тестового класса, если конструктор считается автоматически связываемым. Конструктор считается автоматически
связываемым, если выполняется одно из следующих условий (в порядке старшинства):

- Конструктор аннотирован @Autowired;
- Аннотация @TestConstructor присутствует или мета-присутствует для тестового класса с атрибутом autowireMode,
  установленным в ALL;
- Режим автоматического обнаружения и связывания тестового конструктора по умолчанию был изменен на ALL.

Подробнее об использовании аннотации [@TestConstructor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestConstructor.html) и о том, как изменить режим автоматического обнаружения и связывания глобального тестового конструктора, см.в разделе, посвященном аннотации @TestConstructor см. ниже, доп. см. [TestConstructor](./TestConstructor.md).

---
**!!! Внимание !!!** 

Если конструктор для тестового класса считается автоматически связываемым, Spring берет на
себя разрешение аргументов для всех параметров в конструкторе. Следовательно, никакой другой
ParameterResolver, зарегистрированный в JUnit Jupiter, не может разрешать параметры для такого
конструктора.

---
**!!! Внимание !!!** 

Внедрение зависимостей через конструктор для тестовых классов нельзя использовать в сочетании
со средствами поддержки аннотации @TestInstance(PER_CLASS) из JUnit Jupiter, если аннотация
@DirtiesContext используется для закрытия ApplicationContext теста перед или после тестовых
методов.

Причина в том, что аннотация @TestInstance(PER_CLASS) дает JUnit Jupiter команду кэшировать
экземпляр теста между вызовами тестового метода. Следовательно, тестовый экземпляр будет
сохранять ссылки на bean-ы, первоначально внедренные из ApplicationContext, который
впоследствии был закрыт. Поскольку конструктор для тестового класса в таких сценариях будет
вызван только один раз, внедрение зависимостей не повторится, и последующие тесты будут
взаимодействовать с bean-ами из закрытого ApplicationContext-a, что может привести к ошибкам.

Чтобы использовать аннотацию @DirtiesContext в режиме "перед тестовым методом" или
"после тестового метода" в сочетании с аннотацией @TestInstance(PER_CLASS), необходимо
сконфигурировать зависимости из Spring на их получение путем внедрения через поле или
сеттер, чтобы их можно было повторно внедрять между вызовами тестового метода.

В следующем примере Spring внедряет bean - OrderService из ApplicationContext, загруженного из TestConfig.class,
в конструктор OrderServiceIntegrationTests:

on Java:
```Java
    @SpringJUnitConfig(TestConfig.class)
    class OrderServiceIntegrationTests {
        private final OrderService orderService;
        @Autowired
        OrderServiceIntegrationTests(OrderService orderService) {
            this.orderService = orderService;
        }
        // тесты, использующие внедренную службу OrderService
    }
```

on Kotlin:
```Kotlin
    @SpringJUnitConfig(TestConfig::class)
    class OrderServiceIntegrationTests @Autowired constructor(private val orderService: OrderService){
        // тесты, использующие внедренную службу OrderService
    }
```

Обратите внимание, что эта функция позволяет тестовым зависимостям быть final и, следовательно, неизменяемыми.

Если свойство [spring.test.constructor.autowire.mode](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestConstructor.html) имеет значение all см. [@TestConstructor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestConstructor.html), можно пропустить объявление аннотации @Autowired для конструктора из предыдущего примера, в результате чего получим следующий код:

on JAVA:
```Java
    @SpringJUnitConfig(TestConfig.class)
    class OrderServiceIntegrationTests {
        private final OrderService orderService;
        OrderServiceIntegrationTests(OrderService orderService) {
            this.orderService = orderService;
        }
        // тесты, использующие внедренную службу OrderService
    }
```

on Kotlin:
```Kotlin
    @SpringJUnitConfig(TestConfig::class)
    class OrderServiceIntegrationTests(val orderService:OrderService) {
        // тесты, использующие внедренную службу OrderService
    }
```

---
### Внедрение зависимостей через метод

Если параметр тестового метода из JUnit Jupiter или метода обратного вызова жизненного цикла (LockBack) теста
имеет тип ApplicationContext (или его подтип) или аннотирован или мета-аннотирован с помощью аннотаций
@Autowired, @Qualifier или @Value, Spring внедряет значение для этого конкретного параметра с использованием
соответствующего bean-a из ApplicationContext теста.

В следующем примере Spring внедряет OrderService из ApplicationContext, загруженного из TestConfig.class, в
тестовый метод deleteOrder():

on JAVA:
```Java
    @SpringJUnitConfig(TestConfig.class)
    class OrderServiceIntegrationTests {
        @Test
        void deleteOrder(@Autowired OrderService orderService) {
            // используем orderService из ApplicationContext теста
        }
    }
```

on Kotlin:
```Kotlin
    @SpringJUnitConfig(TestConfig::class)
    class OrderServiceIntegrationTests {
        @Test
        fun deleteOrder(@Autowired orderService: OrderService) {
            // используем orderService из ApplicationContext теста
        }
    }
```

Благодаря надежности поддержки ParameterResolver в JUnit Jupiter, в один метод можно внедрить несколько
зависимостей, причем не только из Spring, но и из самого JUnit Jupiter или других сторонних расширений.

В следующем примере показано, как сделать так, чтобы Spring и JUnit Jupiter одновременно внедряли зависимости
в тестовый метод placeOrderRepeatedly():

on Java:
```Java
    @SpringJUnitConfig(TestConfig.class)
    class OrderServiceIntegrationTests {
        @RepeatedTest(10)
        void placeOrderRepeatedly(RepetitionInfo repetitionInfo,
                @Autowired OrderService orderService) {
            // используем orderService из ApplicationContext теста
            // и repetitionInfo из JUnit Jupiter
        }
    }
```

on Kotlin:
```Kotlin
    @SpringJUnitConfig(TestConfig::class)
    class OrderServiceIntegrationTests {
        @RepeatedTest(10)
        fun placeOrderRepeatedly(repetitionInfo:RepetitionInfo, @Autowired orderService:OrderService) {
            // используем orderService из ApplicationContext теста
            // и repetitionInfo из JUnit Jupiter
        }
    }
```

Обратите внимание, что использование аннотации [@RepeatedTest из JUnit Jupiter](https://www.baeldung.com/junit-5-repeated-test) позволяет тестовому методу получать доступ к RepetitionInfo.

---
### Конфигурация тестового класса с аннотацией @Nested

Spring TestContext Framework поддерживает использование связанных с тестами аннотаций для тестовых классов,
помеченных аннотацией [@Nested](https://www.baeldung.com/junit-5-nested-test-classes), в JUnit Jupiter, начиная с версии Spring Framework 5.0; однако до Spring
Framework 5.3 конфигурационные аннотации тестов на уровне классов не наследовались от вложенных классов,
как это происходит с суперклассами.

В Spring Framework 5.3 появилась полнофункциональная поддержка наследования конфигурации тестового класса
от вложенных классов, и такая конфигурация будет наследоваться по умолчанию. Чтобы изменить режим INHERIT
по умолчанию на режим OVERRIDE, можно пометить отдельный тестовый класс, аннотированный [@Nested](https://www.geeksforgeeks.org/advance-java/junit-5-nested-test-classes/), аннотацией
[@NestedTestConfiguration(EnclosingConfiguration.OVERRIDE)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/NestedTestConfiguration.html).

Явное объявление [@NestedTestConfiguration](https://docs.spring.io/spring-framework/docs/5.3.5/javadoc-api/index.html?org/springframework/test/context/NestedTestConfiguration.html) будет применяться к аннотированному тестовому классу, а также ко всем его подклассам и вложенным классам. Таким образом, можно аннотировать высокоуровневый тестовый класс с помощью аннотации @NestedTestConfiguration, и это будет применено ко всем его вложенным тестовым классам рекурсивно.

Для того чтобы команды разработчиков могли изменить значение по умолчанию на OVERRIDE – например, для
обеспечения совместимости с версиями Spring Framework 5.0-5.2 – режим по умолчанию можно изменить глобально
через системное свойство JVM или файл spring.properties в корне пути классов.

Подробности см. в заметке ["Изменение режима наследования объемлющей конфигурации по умолчанию"](./TestAnnotationsJUnitJupiter.md)

Хотя следующий пример "Hello World" очень упрощен, он показывает, как объявить конфигурацию для высокоуровневого
класса, которая наследуется его тестовыми классами, помеченными аннотацией @Nested. В этом конкретном примере
наследуется только конфигурационный класс TestConfig. Каждый вложенный тестовый класс предоставляет свой
собственный набор активных профилей, в результате чего для каждого вложенного тестового класса создается
отдельный ApplicationContext (подробнее см. раздел "Кэширование контекста"). Обратитесь к [списку поддерживаемых
аннотаций](./TestAnnotationsJUnitJupiter.md), чтобы узнать, какие аннотации могут быть унаследованы в тестовых классах с аннотацией @Nested.

on Java:
```Java
    @SpringJUnitConfig(TestConfig.class)
    class GreetingServiceTests {

        @Nested
        @ActiveProfiles("lang_en")
        class EnglishGreetings {
            @Test
            void hello(@Autowired GreetingService service) {
                assertThat(service.greetWorld()).isEqualTo("Hello World");
            }
        }

        @Nested
        @ActiveProfiles("lang_de")
        class GermanGreetings {
            @Test
            void hello(@Autowired GreetingService service) {
                assertThat(service.greetWorld()).isEqualTo("Hallo Welt");
            }
        }
    }
```

on Kotlin:
```Kotlin
    @SpringJUnitConfig(TestConfig::class)
    class GreetingServiceTests {
        @Nested
        @ActiveProfiles("lang_en")
        inner class EnglishGreetings {
            @Test
            fun hello(@Autowired service:GreetingService) {
                assertThat(service.greetWorld()).isEqualTo("Hello World")
            }
        }
        @Nested
        @ActiveProfiles("lang_de")
        inner class GermanGreetings {
            @Test
            fun hello(@Autowired service:GreetingService) {
                assertThat(service.greetWorld()).isEqualTo("Hallo Welt")
            }
        }
    }
```

---
### Вспомогательные классы TestNG

Пакет [org.springframework.test.context.testng](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/testng/package-summary.html) предоставляет следующие вспомогательные классы для случаев тестирования на основе TestNG:

- [AbstractTestNGSpringContextTests](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/testng/AbstractTestNGSpringContextTests.html);
- [AbstractTransactionalTestNGSpringContextTests](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/testng/AbstractTransactionalTestNGSpringContextTests.html);

Где, AbstractTestNGS SpringContextTests – это абстрактный базовый тестовый класс, который интегрирует
     Spring TestContext Framework с явной поддержкой тестирования ApplicationContext в среду TestNG.
     Если вы расширите AbstractTestNGSpringContextTests, то можете получить доступ к protected переменной
     экземпляра applicationContext, которую можно использовать для выполнения явного поиска bean-ов или
     для тестирования состояния контекста в целом.

Где, AbstractTransactionalTestNGSpringContextTests – это абстрактное транзакционное расширение
     AbstractTestNGSpringContextTests, которое добавляет некоторые удобные функции для доступа к JDBC.
     Этот класс ожидает, что в ApplicationContext будут определены bean javax.sql.DataSource и
     bean PlatformTransactionManager. Если вы расширите AbstractTransactionalTestNGSpringContextTests,
     то можете получить доступ к protected переменной экземпляра jdbcTemplate, которую можно использовать,
     чтобы выполнять SQL-инструкции для осуществления запросов к базе данных. Можно использовать такие
     запросы для подтверждения состояния базы данных как до, так и после выполнения кода приложения,
     связанного с базой данных, а Spring гарантированно обеспечит, что такие запросы будут выполнены в
     рамках той же транзакции, что и код приложения.

     При использовании в сочетании с ORM-инструментом следует избегать ложных срабатываний.
     AbstractTransactionalTestNGSpringContextTests также предоставляет вспомогательные методы, которые
     делегируют полномочия методам в JdbcTestUtils с помощью вышеупомянутого jdbcTemplate.
     Кроме того, AbstractTransactionalTestNGSpringContextTests предоставляет метод executeSqlScript(..)
     для выполнения SQL-скриптов в отношении сконфигурированного DataSource.

---
**!!! Внимание !!!**

Эти классы являются вспомогательным средством для расширения. Если вам не нужно, чтобы ваши
тестовые классы были привязаны к иерархии классов, специфичной для Spring, то можете
сконфигурировать свои собственные специальные тестовые классы с помощью аннотаций
@ContextConfiguration, @TestExecutionListeners и так далее, а также вручную инструментировать
свой тестовый класс с помощью TestContextManager.

Смотрите исходный код AbstractTestNGSpringContextTests в качестве примера того, как можно
инструментировать ваш тестовый класс.
