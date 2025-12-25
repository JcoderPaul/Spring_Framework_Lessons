### TestExecutionListener

Spring предоставляет следующие реализации TestExecutionListener, которые регистрируются по умолчанию, точно в
следующем порядке:

- [ServletTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/web/ServletTestExecutionListener.html): Конфигурирует объекты-имитации API-интерфейса сервлетов для WebApplicationContext.

- [DirtiesContextBeforeModesTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/support/DirtiesContextBeforeModesTestExecutionListener.html): Обрабатывает аннотацию [@DirtiesContext](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-dirtiescontext.html) для режимов "перед".

- [ApplicationEventsTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/ApplicationEventsTestExecutionListener.html): Обеспечивает поддержку [ApplicationEvents](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/application-events.html).

- [DependencyInjectionTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/support/DependencyInjectionTestExecutionListener.html): Обеспечивает внедрение зависимостей для тестового экземпляра.

- [DirtiesContextTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/support/DirtiesContextTestExecutionListener.html): Обрабатывает аннотацию [@DirtiesContext](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-dirtiescontext.html) для режимов "после".

- [TransactionalTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/transaction/TransactionalTestExecutionListener.html): Обеспечивает транзакционное выполнение тестов с семантикой отката по умолчанию.

- [SqlScriptsTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/jdbc/SqlScriptsTestExecutionListener.html): Выполняет SQL-скрипты, сконфигурированные с помощью аннотации [@Sql](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-sql.html).

- [EventPublishingTestExecutionListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/EventPublishingTestExecutionListener.html): Публикует cобытия выполнения теста в ApplicationContext.

---
### Регистрация реализаций TestExecutionListener

Вы можете зарегистрировать реализации TestExecutionListener для тестового класса и его подклассов с помощью
аннотации @TestExecutionListeners. Подробности и примеры смотрите в подразделе, посвященном поддержке аннотаций
(DOC/TestContextFramework/TestsAnnotation.txt и DOC/TestContextFramework/TestAnnotationsJUnitJupiter.txt), и
официальной документации по аннотации [@TestExecutionListeners](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/TestExecutionListeners.html)

---
### Автоматическое обнаружение реализаций TestExecutionListener по умолчанию

Регистрация реализаций TestExecutionListener с помощью аннотации @TestExecutionListeners подходит для специальных
слушателей, которые используются в ограниченных сценариях тестирования. Однако регистрация может стать
затруднительной, если специальный слушатель необходимо использовать во всем тестовом комплекте. Эта проблема
решается благодаря поддержке автоматического обнаружения реализаций TestExecutionListener по умолчанию через
механизм SpringFactoriesLoader.

В частности, модуль spring-test объявляет все реализации TestExecutionListener по умолчанию в ключе
org.springframework.test.context.TestExecutionListener в файле свойств META-INF/spring.factories.
Сторонние фреймворки и разработчики могут вносить свои собственные реализации TestExecutionListener
в список слушателей по умолчанию таким же образом через собственный файл свойств META-INF/spring.factories.

---
### Упорядочивание реализаций TestExecutionListener

Если фреймворк TestContext обнаруживает реализации TestExecutionListener по умолчанию через вышеупомянутый
механизм SpringFactoriesLoader, созданные экземпляры слушателей сортируются с помощью AnnotationAwareOrderComparator
из Spring, который учитывает интерфейс Ordered из Spring и аннотацию @Order для упорядочивания.

AbstractTestExecutionListener и все реализации TestExecutionListener по умолчанию, предоставляемые Spring,
реализуют Ordered с соответствующими значениями. Поэтому сторонним фреймворкам и разработчикам следует
удостовериться, что их реализации TestExecutionListener по умолчанию регистрируются в правильном порядке,
реализуя Ordered или объявляя аннотацию @Order.

Смотрите документацию по методам getOrder() основных реализаций TestExecutionListener по умолчанию для получения
[более подробной информации о том, какие значения присваиваются каждому основному слушателю](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/TestExecutionListener.html)

Spring предоставляет следующие готовые реализации (все из которых реализуют Ordered):

- ServletTestExecutionListener;
- DirtiesContextBeforeModesTestExecutionListener;
- ApplicationEventsTestExecutionListener;
- DependencyInjectionTestExecutionListener;
- DirtiesContextTestExecutionListener;
- TransactionalTestExecutionListener;
- SqlScriptsTestExecutionListener;
- EventPublishingTestExecutionListener;

---
### Объединение реализаций TestExecutionListener

Если специальный слушатель TestExecutionListener зарегистрирован через аннотацию @TestExecutionListeners,
слушатели по умолчанию не регистрируются. В наиболее распространенных сценариях тестирования это фактически
вынуждает разработчика вручную объявлять все слушатели по умолчанию в дополнение к любым специальным слушателям.

Следующий листинг демонстрирует этот стиль конфигурации:

on Java:

```Java
  @ContextConfiguration
  @TestExecutionListeners({
      MyCustomTestExecutionListener.class,
      ServletTestExecutionListener.class,
      DirtiesContextBeforeModesTestExecutionListener.class,
      DependencyInjectionTestExecutionListener.class,
      DirtiesContextTestExecutionListener.class,
      TransactionalTestExecutionListener.class,
      SqlScriptsTestExecutionListener.class
  })
  class MyTest {
      // тело класса...
  }
```

on Kotlin:

```Kotlin
  @ContextConfiguration
  @TestExecutionListeners(
      MyCustomTestExecutionListener::class,
      ServletTestExecutionListener::class,
      DirtiesContextBeforeModesTestExecutionListener::class,
      DependencyInjectionTestExecutionListener::class,
      DirtiesContextTestExecutionListener::class,
      TransactionalTestExecutionListener::class,
      SqlScriptsTestExecutionListener::class
  )
  class MyTest {
      // тело класса...
  }
```

Сложность этого подхода заключается в том, что он требует от разработчика точного знания того, какие слушатели
зарегистрированы по умолчанию. Более того, набор слушателей по умолчанию может меняться от версии к версии –
например, слушатель **SqlScriptsTestExecutionListener** был представлен в **Spring Framework 4.1**, а
**DirtiesContextBeforeModesTestExecutionListener** был представлен в **Spring Framework 4.2**. Более того, сторонние
фреймворки, такие, как **Spring Boot** и **Spring Security**, регистрируют свои собственные реализации
TestExecutionListener по умолчанию, используя вышеупомянутый механизм автоматического обнаружения.

Чтобы избежать необходимости знать и повторно объявлять все слушатели по умолчанию, можно установить атрибут
mergeMode аннотации @TestExecutionListeners в MergeMode.MERGE_WITH_DEFAULTS. MERGE_WITH_DEFAULTS - указывает,
что локально объявленные слушатели должны быть объединены со слушателями по умолчанию. Алгоритм объединения
обеспечивает удаление дубликатов из списка и сортировку полученного набора объединенных слушателей в соответствии
с семантикой AnnotationAwareOrderComparator.

Если слушатель реализует Ordered или помечен аннотацией @Order, он может влиять на позицию, в которой он
объединяется с слушателями по умолчанию. В противном случае локально объявленные слушатели добавляются к списку
слушателей по умолчанию при объединении.

Например, если класс MyCustomTestExecutionListener в предыдущем примере сконфигурирует свое значение order
(например, 500) так, что оно будет меньше значения очередности ServletTestExecutionListener (которое равняется
1000), то MyCustomTestExecutionListener может быть автоматически объединен со списком значений по умолчанию
перед ServletTestExecutionListener, а предыдущий пример можно будет заменить следующим:

on Java:

```Java
@ContextConfiguration
@TestExecutionListeners(
    listeners = MyCustomTestExecutionListener.class,
    mergeMode = MERGE_WITH_DEFAULTS
)
class MyTest {
    // тело класса...
}
```

on Kotlin:

```Kotlin
@ContextConfiguration
@TestExecutionListeners(
        listeners = [MyCustomTestExecutionListener::class],
        mergeMode = MERGE_WITH_DEFAULTS
)
class MyTest {
    // тело класса...
}
```
