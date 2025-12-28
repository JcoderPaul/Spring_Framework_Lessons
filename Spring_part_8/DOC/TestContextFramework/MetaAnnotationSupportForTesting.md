### Поддержка мета-аннотаций для тестирования

Большинство аннотаций, связанных с тестированием, можно использовать в качестве мета-аннотаций для
создания собственных аннотаций и сокращения конфигурационного дублирования в тестовом комплекте.

Многие из аннотаций, предоставляемых Spring, могут быть использованы в качестве мета-аннотаций в
вашем собственном коде. Мета-аннотация – это аннотация, которая может быть применена к другой
аннотации.

Например, аннотация [@Service](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Service.html), мета-аннотируется с помощью [@Component](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html), как [показано в следующем примере](https://www.baeldung.com/spring-component-repository-service):

on Java:
```Java
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Component
    public @interface Service {
        // ...
    }
```

Можно использовать каждую из следующих аннотаций в качестве мета-аннотации в сочетании с фреймворком TestContext:

- [@BootstrapWith](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/BootstrapWith.html);
- [@ContextConfiguration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ContextConfiguration.html) - [оф. док.](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-contextconfiguration.html);
- [@ContextHierarchy](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ContextHierarchy.html) - [оф. док.](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-contexthierarchy.html);
- [@ActiveProfiles](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ActiveProfiles.html) - [оф. док.](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-activeprofiles.html) - [article and example](https://www.baeldung.com/spring-profiles);
- [@TestPropertySource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestPropertySource.html) - [оф. док.](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-testpropertysource.html) - [article and example](https://www.baeldung.com/spring-test-property-source);
- [@DirtiesContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/annotation/DirtiesContext.html) - [оф. док.](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-dirtiescontext.html) - [article and example](https://www.baeldung.com/spring-dirtiescontext);
- [@WebAppConfiguration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/web/WebAppConfiguration.html) - [оф. док.](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-webappconfiguration.html) - [article and example](https://www.baeldung.com/spring-webappconfiguration);
- [@TestExecutionListeners](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestExecutionListener.html) - [оф. док.](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-testexecutionlisteners.html) - [about config](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/tel-config.html) - [article and example](https://www.baeldung.com/spring-testexecutionlistener);
- [@Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html) - [оф. док.](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html) - [article and example](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring);
- @BeforeTransaction;
- @AfterTransaction;
- @Commit;
- @Rollback;
- @Sql;
- @SqlConfig;
- @SqlMergeMode;
- @SqlGroup;
- @Repeat (**поддерживается только на JUnit 4**);
- @Timed (**поддерживается только на JUnit 4**);
- @IfProfileValue (**поддерживается только на JUnit 4**);
- @ProfileValueSourceConfiguration (**поддерживается только на JUnit 4**);
- @SpringJUnitConfig (**поддерживается только на JUnit Jupiter**);
- @SpringJUnitWebConfig (**поддерживается только на JUnit Jupiter**);
- @TestConstructor (**поддерживается только на JUnit Jupiter**);
- @NestedTestConfiguration (**поддерживается только на JUnit Jupiter**);
- @EnabledIf (**поддерживается только на JUnit Jupiter**);
- @DisabledIf (**поддерживается только на JUnit Jupiter**);

Рассмотрим следующий пример:

on Java:
```Java
    @RunWith(SpringRunner.class)
    @ContextConfiguration({"/app-config.xml", "/test-data-access-config.xml"})
    @ActiveProfiles("dev")
    @Transactional
    public class OrderRepositoryTests { }
    
    @RunWith(SpringRunner.class)
    @ContextConfiguration({"/app-config.xml", "/test-data-access-config.xml"})
    @ActiveProfiles("dev")
    @Transactional
    public class UserRepositoryTests { }
```

on Kotlin:
```Kotlin
    @RunWith(SpringRunner::class)
    @ContextConfiguration("/app-config.xml", "/test-data-access-config.xml")
    @ActiveProfiles("dev")
    @Transactional
    class OrderRepositoryTests { }
    
    @RunWith(SpringRunner::class)
    @ContextConfiguration("/app-config.xml", "/test-data-access-config.xml")
    @ActiveProfiles("dev")
    @Transactional
    class UserRepositoryTests { }
```

Если обнаружится, что предыдущая конфигурация повторяется в нашем тестовом комплекте на базе JUnit 4, то можно
сократить дублирование, введя специальную составную аннотацию, которая централизует общую тестовую конфигурацию
для Spring, следующим образом:

on Java:
```Java
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @ContextConfiguration({"/app-config.xml", "/test-data-access-config.xml"})
    @ActiveProfiles("dev")
    @Transactional
    public @interface TransactionalDevTestConfig { }
```

on Kotlin:
```Kotlin
    @Target(AnnotationTarget.TYPE)
    @Retention(AnnotationRetention.RUNTIME)
    @ContextConfiguration("/app-config.xml", "/test-data-access-config.xml")
    @ActiveProfiles("dev")
    @Transactional
    annotation class TransactionalDevTestConfig { }
```

Затем можно использовать нашу специальную аннотацию @TransactionalDevTestConfig для упрощения конфигурации
отдельных тестовых классов на основе JUnit 4, как показано ниже:

on Java:
```Java
    @RunWith(SpringRunner.class)
    @TransactionalDevTestConfig
    public class OrderRepositoryTests { }
    
    @RunWith(SpringRunner.class)
    @TransactionalDevTestConfig
    public class UserRepositoryTests { }
```

on Kotlin:
```Kotlin
    @RunWith(SpringRunner::class)
    @TransactionalDevTestConfig
    class OrderRepositoryTests
    
    @RunWith(SpringRunner::class)
    @TransactionalDevTestConfig
    class UserRepositoryTests
```

Если мы пишем тесты, использующие JUnit Jupiter, то можно еще больше сократить дублирование кода, поскольку
аннотации в JUnit 5 могут также использоваться в качестве мета-аннотаций.

Рассмотрим следующий пример:

on Java:
```Java
    @ExtendWith(SpringExtension.class)
    @ContextConfiguration({"/app-config.xml", "/test-data-access-config.xml"})
    @ActiveProfiles("dev")
    @Transactional
    class OrderRepositoryTests { }
    
    @ExtendWith(SpringExtension.class)
    @ContextConfiguration({"/app-config.xml", "/test-data-access-config.xml"})
    @ActiveProfiles("dev")
    @Transactional
    class UserRepositoryTests { }
```

on Kotlin:
```Kotlin
    @ExtendWith(SpringExtension::class)
    @ContextConfiguration("/app-config.xml", "/test-data-access-config.xml")
    @ActiveProfiles("dev")
    @Transactional
    class OrderRepositoryTests { }
    
    @ExtendWith(SpringExtension::class)
    @ContextConfiguration("/app-config.xml", "/test-data-access-config.xml")
    @ActiveProfiles("dev")
    @Transactional
    class UserRepositoryTests { }
```

Если обнаружится, что предыдущая конфигурация повторяется в нашем тестовом комплекте на базе JUnit Jupiter,
то можно сократить дублирование, введя специальную составную аннотацию, которая централизует общую тестовую
конфигурацию для Spring и JUnit Jupiter, следующим образом:

on Java:
```Java
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @ExtendWith(SpringExtension.class)
    @ContextConfiguration({"/app-config.xml", "/test-data-access-config.xml"})
    @ActiveProfiles("dev")
    @Transactional
    public @interface TransactionalDevTestConfig { }
```

on Kotlin:
```Kotlin
    @Target(AnnotationTarget.TYPE)
    @Retention(AnnotationRetention.RUNTIME)
    @ExtendWith(SpringExtension::class)
    @ContextConfiguration("/app-config.xml", "/test-data-access-config.xml")
    @ActiveProfiles("dev")
    @Transactional
    annotation class TransactionalDevTestConfig { }
```

Затем можно использовать нашу специальную аннотацию @TransactionalDevTestConfig для упрощения конфигурации
отдельных тестовых классов на основе JUnit Jupite, как показано ниже:

on Java:
```Java
    @TransactionalDevTestConfig
    class OrderRepositoryTests { }
    
    @TransactionalDevTestConfig
    class UserRepositoryTests { }
```

on Kotlin:
```Kotlin
    @TransactionalDevTestConfig
    class OrderRepositoryTests { }
    
    @TransactionalDevTestConfig
    class UserRepositoryTests { }
```

Поскольку JUnit Jupiter поддерживает использование @Test, @RepeatedTest, ParameterizedTest и других в качестве
мета-аннотаций, можно также создавать собственные составные аннотации на уровне тестового метода. Например,
если нужно создать составную аннотацию, которая объединяет аннотации @Test и @Tag из JUnit Jupiter с аннотацией
@Transactional из Spring, можно создать аннотацию @TransactionalIntegrationTest следующим образом:

on Java:
```Java
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Transactional
    @Tag("integration-test") // org.junit.jupiter.api.Tag
    @Test // org.junit.jupiter.api.Test
    public @interface TransactionalIntegrationTest { }
```

on Kotlin:
```Kotlin
    @Target(AnnotationTarget.TYPE)
    @Retention(AnnotationRetention.RUNTIME)
    @Transactional
    @Tag("integration-test") // org.junit.jupiter.api.Tag
    @Test // org.junit.jupiter.api.Test
    annotation class TransactionalIntegrationTest { }
```

Затем можно использовать нашу специальную аннотацию @TransactionalIntegrationTest для упрощения конфигурации
отдельных тестовых методов на основе JUnit Jupiter следующим образом:

on Java:
```Java
    @TransactionalIntegrationTest
    void saveOrder() { }
    
    @TransactionalIntegrationTest
    void deleteOrder() { }
```

on Kotlin:
```Kotlin
    @TransactionalIntegrationTest
    fun saveOrder() { }
    
    @TransactionalIntegrationTest
    fun deleteOrder() { }
```

---
Более подробную информацию можно найти на: https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model
