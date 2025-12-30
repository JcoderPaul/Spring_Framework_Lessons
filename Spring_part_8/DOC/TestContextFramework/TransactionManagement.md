### Управление транзакциями

В [фреймворке TestContext](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework.html) транзакции управляются с помощью [TransactionalTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/transaction/TransactionalTestExecutionListener.html), который настроен
по умолчанию, даже если явно аннотация [@TestExecutionListeners](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/TestExecutionListener.html) явно не объявлена в своем тестовом классе. Однако, чтобы активировать средства поддержки транзакций, необходимо сконфигурировать bean
PlatformTransactionManager в ApplicationContext, который загружается с семантикой аннотации @ContextConfiguration.

Кроме того, нужно объявить для ваших тестов аннотацию @Transactional из Spring либо на уровне класса, либо на
уровне метода.

---
### Транзакции, управляемые тестированием

Управляемые тестами транзакции – это транзакции, которые управляются декларативно при помощи
TransactionalTestExecutionListener или программно при помощи TestTransaction. Не следует путать такие транзакции
с транзакциями, управляемыми Spring (управляемыми непосредственно через фреймворк Spring в ApplicationContext,
загруженном для тестов) или транзакциями, управляемыми приложением (управляемыми программно в коде приложения,
который вызывается тестами).

Управляемые Spring и управляемые приложением транзакции обычно участвуют в управляемых тестами транзакциях.
Однако следует соблюдать осторожность, если транзакции, управляемые Spring или приложением, сконфигурированы
с использованием любого типа распространения, кроме REQUIRED или SUPPORTS.

---
**!!! Внимание !!!** 
**Упреждающее время ожидания и транзакции, управляемые тестами.**

Необходимо соблюдать бдительность при использовании любой формы упреждающего времени ожидания
из тестового фреймворка в сочетании с управляемыми тестами транзакциями Spring. В частности,
средства поддержки тестирования из Spring привязывают состояние транзакции к текущему потоку
(через переменную java.lang.ThreadLocal) перед вызовом текущего тестового метода. Если тестовый
фреймворк вызывает текущий метод тестирования в новом потоке для поддержки упреждающего времени
ожидания, то любые действия, выполняемые в текущем методе тестирования, не будут вызваны в
управляемой тестом транзакции. Следовательно, результат любых таких действий нельзя будет
откатить при транзакции, управляемой тестом. Напротив, такие действия будут зафиксированы в
постоянном хранилище – например, в реляционной базе данных – даже если управляемая тестами
транзакция будет правильно откачена Spring.

Случаи, в которых это может произойти, могут включать, помимо прочего, те, что приведены ниже:

- Поддержка аннотации @Test(timeout = …) и правила TimeOut из JUnit 4;
- Методы assertTimeoutPreemptively(…) из JUnit Jupite в классе org.junit.jupiter.api.Assertions;
- Поддержка аннотации @Test(timeOut = …) из TestNG;

---
### Активация и дезактивация транзакций

[Аннотирование тестового метода с помощью @Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html) приводит к тому, что тест запускается в транзакции, которая по умолчанию автоматически откатывается после завершения теста. [Если тестовый класс помечен аннотацией @Transactional](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring), то каждый тестовый метод в иерархии этого класса выполняется в рамках транзакции. Тестовые методы, не аннотированные @Transactional (на уровне класса или метода), не выполняются в рамках транзакции. Обратите внимание, что аннотация @Transactional не поддерживается для методов жизненного цикла теста – например, методов, аннотированных с помощью аннотаций @BeforeAll, @BeforeEach из JUnit Jupiter и т.д. Более того, тесты, помеченные аннотацией @Transactional, но имеющие атрибут propagation, установленный в NOT_SUPPORTED или NEVER, не выполняются в рамках транзакции.

**Таблица 1. Поддержка атрибутов, аннотированных @Transactional:**

|----------------------------------------|---------------------------------------------------------------------|
| Атрибут	                               | Поддерживается для транзакций, управляемых тестами                  |  
|----------------------------------------|---------------------------------------------------------------------|
| value и transactionManager             | да                                                                  |
| propagation                            | поддерживаются только Propagation.NOT_SUPPORTED и Propagation.NEVER |
| isolation                              | нет                                                                 | 
| timeout                                | нет                                                                 |
| readOnly                               | нет                                                                 |
| rollbackFor и rollbackForClassName     | нет: вместо этого используйте TestTransaction.flagForRollback()     |
| noRollbackFor и noRollbackForClassName | нет: вместо этого используйте TestTransaction.flagForCommit()       |

---
**!!! Внимание !!!**

Методы жизненного цикла на уровне методов – например, методы, аннотированные с помощью
@BeforeEach или @AfterEach из JUnit Jupiter – выполняются в рамках управляемой тестами
транзакции.

С другой стороны, методы жизненного цикла на уровне комплекта и класса – например, методы,
аннотированные @BeforeAll или @AfterAll из JUnit Jupiter и методы, аннотированные @BeforeSuite,
@AfterSuite, @BeforeClass или @AfterClass из TestNG – не выполняются в рамках управляемой
тестами транзакции.

Если нужно запустить код в методе жизненного цикла на уровне комплекта или класса в рамках
транзакции, то можно внедрить соответствующий PlatformTransactionManager в тестовый класс,
а затем использовать его с TransactionTemplate для программного управления транзакциями.

---
**!!! Обратите внимание !!!**

AbstractTransactionalJUnit4SpringContextTests и AbstractTransactionalTestNGSpringContextTests
предварительно настроены на поддержку транзакций на уровне классов.

В следующем примере продемонстрирован общий сценарий написания интеграционного теста для UserRepository на
основе Hibernate:

on Java:
```Java
  @SpringJUnitConfig(TestConfig.class)
  @Transactional
  class HibernateUserRepositoryTests {
  
      @Autowired
      HibernateUserRepository repository;
  
      @Autowired
      SessionFactory sessionFactory;
  
      JdbcTemplate jdbcTemplate;
  
      @Autowired
      void setDataSource(DataSource dataSource) {
          this.jdbcTemplate = new JdbcTemplate(dataSource);
      }
  
      @Test
      void createUser() {
          // отслеживаем начальное состояние в тестовой базе данных:
          final int count = countRowsInTable("user");
          User user = new User(...);
          repository.save(user);
          // Во избежание ложных срабатываний при тестировании требуется ручной сброс
          sessionFactory.getCurrentSession().flush();
          assertNumUsers(count + 1);
      }
  
      private int countRowsInTable(String tableName) {
          return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
      }
  
      private void assertNumUsers(int expected) {
          assertEquals("Number of rows in the [user] table.", expected, countRowsInTable("user"));
      }
  }
```

on Kotlin:
```Kotlin
  @SpringJUnitConfig(TestConfig::class)
  @Transactional
  class HibernateUserRepositoryTests {
      @Autowired
      lateinit var repository: HibernateUserRepository
  
      @Autowired
      lateinit var sessionFactory: SessionFactory
  
      lateinit var jdbcTemplate: JdbcTemplate
  
      @Autowired
      fun setDataSource(dataSource: DataSource) {
          this.jdbcTemplate = JdbcTemplate(dataSource)
      }
  
      @Test
      fun createUser() {
          // отслеживаем начальное состояние в тестовой базе данных:
          val count = countRowsInTable("user")
          val user = User()
          repository.save(user)
          // Во избежание ложных срабатываний при тестировании требуется ручной сброс
          sessionFactory.getCurrentSession().flush()
          assertNumUsers(count + 1)
      }
  
      private fun countRowsInTable(tableName: String): Int {
          return JdbcTestUtils.countRowsInTable(jdbcTemplate, tableName)
      }
  
      private fun assertNumUsers(expected: Int) {
          assertEquals("Number of rows in the [user] table.", expected, countRowsInTable("user"))
      }
  }
```

Обычно, нет необходимости очищать базу данных после выполнения метода createUser(), поскольку любые изменения,
внесенные в базу данных, автоматически откатываются TransactionalTestExecutionListener.

---
### Логика работы при откате и фиксации транзакций

По умолчанию тестовые транзакции автоматически откатываются после завершения теста; однако, логику работы, при
фиксации и откате транзакций, можно сконфигурировать декларативно с помощью аннотаций @Commit и @Rollback.

---
### Управление программными транзакциями

Вы можете взаимодействовать с управляемыми тестами транзакциями программно, используя статические методы в
TestTransaction. Например, можно использовать TestTransaction в тестовых методах, методах "перед" и "после"
для запуска или завершения текущей управляемой тестом транзакции или для конфигурирования текущей управляемой
тестом транзакции на откат или фиксацию. Поддержка TestTransaction автоматически доступна всегда, когда включен
слушатель TransactionalTestExecutionListener.

В следующем примере продемонстрированы [некоторые возможности TestTransaction](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/transaction/TestTransaction.html). Более подробную информацию [см. в TestTransaction](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/transaction/TestTransaction.html)

on Java:
```Java
  @ContextConfiguration(classes = TestConfig.class)
  public class ProgrammaticTransactionManagementTests extends
          AbstractTransactionalJUnit4SpringContextTests {
      @Test
      public void transactionalTest() {
          // подтверждаем начальное состояние в тестовой базе данных:
          assertNumUsers(2);
          deleteFromTables("user");
          // изменения в базе данных будут зафиксированы!
          TestTransaction.flagForCommit();
          TestTransaction.end();
          assertFalse(TestTransaction.isActive());
          assertNumUsers(0);
          TestTransaction.start();
          // выполняем другие действия с базой данных, которые будут
          // будут автоматически откачены после завершения теста...
      }
      protected void assertNumUsers(int expected) {
          assertEquals("Number of rows in the [user] table.", expected, countRowsInTable("user"));
      }
  }
```

on Kotlin:
```Java
  @ContextConfiguration(classes = [TestConfig::class])
  class ProgrammaticTransactionManagementTests : AbstractTransactionalJUnit4SpringContextTests() {
      @Test
      fun transactionalTest() {
          // подтверждаем начальное состояние в тестовой базе данных:
          assertNumUsers(2)
          deleteFromTables("user")
          // изменения в базе данных будут зафиксированы!
          TestTransaction.flagForCommit()
          TestTransaction.end()
          assertFalse(TestTransaction.isActive())
          assertNumUsers(0)
          TestTransaction.start()
          // выполняем другие действия с базой данных, которые будут
          // будут автоматически откачены после завершения теста...
      }
      protected fun assertNumUsers(expected: Int) {
          assertEquals("Number of rows in the [user] table.", expected, countRowsInTable("user"))
      }
  }
```

---
### Выполнение кода вне транзакции

Иногда может потребоваться выполнить определенный код перед или после транзакционного тестового метода, но вне
транзакционного контекста – например, для проверки начального состояния базы данных перед запуском теста или
для проверки ожидаемого поведения транзакционной фиксации после выполнения теста (если тест был сконфигурирован
на фиксацию транзакции).

TransactionalTestExecutionListener поддерживает аннотации [@BeforeTransaction](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/transaction/BeforeTransaction.html) и [@AfterTransaction](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/transaction/AfterTransaction.html) именно для
таких сценариев. Вы можете аннотировать любой void метод в тестовом классе или любой void метод по умолчанию
в тестовом интерфейсе одной из этих аннотаций, а слушатель TransactionalTestExecutionListener обеспечит, чтоб
ваш метод "перед транзакцией" или "после транзакции" был запущен в нужное время.

---
**!!! Внимание !!!** 

Любые методы "перед" (например, методы, помеченные аннотацией @BeforeEach из JUnit Jupiter)
и любые методы "после" (например, методы, помеченные аннотацией @AfterEach из JUnit Jupiter)
выполняются в рамках транзакции. Кроме того, методы, аннотированные с помощью аннотации
@BeforeTransaction или @AfterTransaction, не выполняются для тестовых методов, которые не
сконфигурированы на выполнение в рамках транзакции.

---
### Настройка диспетчера транзакций

TransactionalTestExecutionListener ожидает, что в ApplicationContext из Spring для теста будет определен bean
PlatformTransactionManager. Если существует несколько экземпляров PlatformTransactionManager в пределах
ApplicationContext теста, можно объявить квалификатор, используя аннотацию @Transactional("myTxMgr") или
@Transactional(transactionManager = "myTxMgr"), или же TransactionManagementConfigurer может быть реализован
классом, помеченным аннотацией @Configuration.

Документация по [TestContextTransactionUtils.retrieveTransactionManager()](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/transaction/TestContextTransactionUtils.html)
для ознакомления с более подробной информацией об алгоритме, используемом для поиска диспетчера транзакций в ApplicationContext теста:
- [Spring Boot features](https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/html/boot-features-testing.html);
- [Testing Spring Boot Applications](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html);
- [Failed to Load ApplicationContext for JUnit Test of Spring Controller](https://www.baeldung.com/spring-junit-failed-to-load-applicationcontext);

---
### Демонстрация всех аннотаций, связанных с транзакциями

В следующем примере на основе JUnit Jupiter отражен фиктивный сценарий интеграционного тестирования, в котором
выделены все аннотации, связанные с транзакциями. Пример не является демонстрацией передовой практики, а скорее
служит демонстрацией того, как можно использовать эти аннотации. Дополнительную информацию и примеры конфигурации
см. в разделе, посвященном поддержке аннотаций (DOC/TestContextFramework/TestsAnnotation.txt). Управление
транзакциями для аннотации @Sql содержит дополнительный пример, использующий аннотацию @Sql для выполнения
декларативного SQL-скрипта с семантикой отката транзакции по умолчанию.

В следующем примере показаны соответствующие аннотации:

on Java:
******************************************************************************************************************
@SpringJUnitConfig
@Transactional(transactionManager = "txMgr")
@Commit
class FictitiousTransactionalTest {
    @BeforeTransaction
    void verifyInitialDatabaseState() {
        // логика проверки достоверности начального состояния перед запуском транзакции
    }
    @BeforeEach
    void setUpTestDataWithinTransaction() {
        // устанавливаем тестовые данные в рамках транзакции
    }
    @Test
    // переопределяет настройку аннотации @Commit на уровне класса
    @Rollback
    void modifyDatabaseWithinTransaction() {
        // логика, использующая тестовые данные и изменяющая состояние базы данных
    }
    @AfterEach
    void tearDownWithinTransaction() {
        // запускаем логику "разрушения (tear down)" внутри транзакции
    }
    @AfterTransaction
    void verifyFinalDatabaseState() {
        // логика проверки достоверности конечного состояния после отката транзакции
    }
}
******************************************************************************************************************

on Kotlin:
******************************************************************************************************************
@SpringJUnitConfig
@Transactional(transactionManager = "txMgr")
@Commit
class FictitiousTransactionalTest {
    @BeforeTransaction
    fun verifyInitialDatabaseState() {
        // логика проверки достоверности начального состояния перед запуском транзакции
    }
    @BeforeEach
    fun setUpTestDataWithinTransaction() {
        // устанавливаем тестовые данные в рамках транзакции
    }
    @Test
    // переопределяет настройку аннотации @Commit на уровне класса
    @Rollback
    fun modifyDatabaseWithinTransaction() {
        // логика, использующая тестовые данные и изменяющая состояние базы данных
    }
    @AfterEach
    fun tearDownWithinTransaction() {
        // запускаем логику "разрушения (tear down)" внутри транзакции
    }
    @AfterTransaction
    fun verifyFinalDatabaseState() {
        // логика проверки достоверности конечного состояния после отката транзакции
    }
}
******************************************************************************************************************

!!! ВНИМАНИЕ !!! *** Как избежать ложных срабатываний при тестировании ORM-кода ***
                 Когда вы тестируете код приложения, который манипулирует состоянием сессии Hibernate или
                 контекста постоянства JPA, убедитесь, что сбрасываете базовую единицу работы в тестовых
                 методах, которые выполняют этот код.

                 Отсутствие сброса базовой единицы работы может привести к ложным срабатываниям:
                 Ваш тест будет проходиться, но в реальной производственной среде тот же код будет генерировать
                 исключение. Обратите внимание, что это относится к любой ORM-системе, которая хранит единицу
                 работы в памяти. В следующем тестовом примере на основе Hibernate один метод демонстрирует
                 ложное срабатывание, а другой метод правильно открывает результаты сброса сессии:

                 on Java:
                 *************************************************************************************************
                 // ...
                 @Autowired
                 SessionFactory sessionFactory;
                 @Transactional
                 @Test // ожидаемого исключения нет!
                 public void falsePositive() {
                     updateEntityInHibernateSession();
                     // Ложное срабатывание: исключение будет сгенерировано, как только сессия Hibernate
                     // будет окончательно сброшена (т.е. в производственном коде)
                 }
                 @Transactional
                 @Test(expected = ...)
                 public void updateWithSessionFlush() {
                     updateEntityInHibernateSession();
                     // Во избежание ложных срабатываний при тестировании требуется ручной сброс
                     sessionFactory.getCurrentSession().flush();
                 }
                 // ...
                 *************************************************************************************************

                 on Kotlin:
                 *************************************************************************************************
                 // ...
                 @Autowired
                 lateinit var sessionFactory: SessionFactory
                 @Transactional
                 @Test // ожидаемого исключения нет!
                 fun falsePositive() {
                     updateEntityInHibernateSession()
                     // Ложное срабатывание: исключение будет сгенерировано, как только сессия Hibernate
                     // будет окончательно сброшена (т.е. в производственном коде)
                 }
                 @Transactional
                 @Test(expected = ...)
                 fun updateWithSessionFlush() {
                     updateEntityInHibernateSession()
                     // Во избежание ложных срабатываний при тестировании требуется ручной сброс
                     sessionFactory.getCurrentSession().flush()
                 }
                 // ...
                 *************************************************************************************************

                 В следующем примере показаны методы сопоставления для JPA:

                 on Java:
                 *************************************************************************************************
                 // ...
                 @PersistenceContext
                 EntityManager entityManager;
                 @Transactional
                 @Test // ожидаемого исключения нет!
                 public void falsePositive() {
                     updateEntityInJpaPersistenceContext();
                     // Ложное срабатывание: исключение будет сгенерировано, как только
                     // EntityManager из JPA будет окончательно сброшен (т.е. в производственном коде)
                 }
                 @Transactional
                 @Test(expected = ...)
                 public void updateWithEntityManagerFlush() {
                     updateEntityInJpaPersistenceContext();
                     // Во избежание ложных срабатываний при тестировании требуется ручной сброс
                     entityManager.flush();
                 }
                 // ...
                 *************************************************************************************************

                 on Kotlin:
                 *************************************************************************************************
                 // ...
                 @PersistenceContext
                 lateinit var entityManager:EntityManager
                 @Transactional
                 @Test // ожидаемого исключения нет!
                 fun falsePositive() {
                     updateEntityInJpaPersistenceContext()
                     // Ложное срабатывание: исключение будет сгенерировано, как только
                     // EntityManager из JPA будет окончательно сброшен (т.е. в производственном коде)
                 }
                 @Transactional
                 @Test(expected = ...)
                 void updateWithEntityManagerFlush() {
                     updateEntityInJpaPersistenceContext()
                     // Во избежание ложных срабатываний при тестировании требуется ручной сброс
                     entityManager.flush()
                 }
                 // ...
                 *************************************************************************************************

!!! ВНИМАНИЕ !!! *** Тестирование обратных вызовов жизненного цикла ORM-сущностей ***
                 По аналогии с примечанием о том, как избежать ложных срабатываний при тестировании ORM-кода,
                 если ваше приложение использует обратные вызовы жизненного цикла сущностей (также известные
                 как слушатели сущностей), убедитесь, что в тестовых методах, выполняющих этот код, базовая
                 единица работы сбрасывается.

                 Невозможность сбросить или очистить базовую единицу работы может привести к тому, что
                 определенные обратные вызовы жизненного цикла не будут вызваны.

                 Например, при использовании JPA обратные вызовы с аннотациями @PostPersist, @PreUpdate и
                 @PostUpdate не будут совершены, если не будет вызвана функция entityManager.flush() после
                 сохранения или обновления сущности. Аналогично, если сущность уже прикреплена к текущей
                 единице работы (связана с текущим контекстом постоянства), попытка перезагрузить сущность
                 не приведет к обратному вызову аннотации @PostLoad, если перед попыткой перезагрузки сущности
                 не будет вызвана функция entityManager.clear().

                 В следующем примере показано, как сбросить EntityManager, чтобы обратные вызовы аннотации
                 @PostPersist были гарантированно совершены, когда сущность будет сохранена. Для сущности
                 Person, используемой в примере, был зарегистрирован слушатель сущностей с методом обратного
                 вызова, помеченным аннотацией @PostPersist.

                 on Java:
                 *************************************************************************************************
                 // ...
                 @Autowired
                 JpaPersonRepository repo;
                 @PersistenceContext
                 EntityManager entityManager;
                 @Transactional
                 @Test
                 void savePerson() {
                     // EntityManager#persist(...) приводит к @PrePersist, но не @PostPersist
                     repo.save(new Person("Jane"));
                     // Для осуществления обратного вызова с аннотацией @PostPersist требуется ручной сброс
                     entityManager.flush();
                     // Тестовый код, который использует обратный вызов с аннотацией @PostPersist
                     // был вызван...
                 }
                 // ...
                 *************************************************************************************************

                 on Kotlin:
                 *************************************************************************************************
                 // ...
                 @Autowired
                 lateinit var repo: JpaPersonRepository
                 @PersistenceContext
                 lateinit var entityManager: EntityManager
                 @Transactional
                 @Test
                 fun savePerson() {
                     // EntityManager#persist(...) приводит к @PrePersist, но не @PostPersist
                     repo.save(Person("Jane"))
                     // Для осуществления обратного вызова с аннотацией @PostPersist требуется ручной сброс
                     entityManager.flush()
                     // Тестовый код, который использует обратный вызов с аннотацией @PostPersist
                     // был вызван...
                 }
                 // ...
                 *************************************************************************************************

См. JpaEntityListenerTests в тестовом комплекте Spring Framework для ознакомления с рабочими примерами, в которых
используются все обратные вызовы жизненного цикла JPA:
https://github.com/spring-projects/spring-framework/blob/5.3.x/spring-test/src/test/java/org/springframework/test/context/junit/jupiter/orm/JpaEntityListenerTests.java
