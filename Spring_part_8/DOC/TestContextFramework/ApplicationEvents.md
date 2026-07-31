### События приложения

Начиная с версии Spring Framework 5.3.3, фреймворк TestContext обеспечивает поддержку записи событий приложения,
опубликованных в ApplicationContext, для того, чтобы можно было в отношении этих событий выполнять утверждения в
тестах. Все события, опубликованные во время выполнения одного теста, доступны через API-интерфейс
ApplicationEvents, который позволяет обрабатывать события как java.util.Stream.

Чтобы использовать ApplicationEvents в своих тестах, сделайте следующее:

- Убедитесь, что ваш тестовый класс аннотирован или мета-аннотирован с помощью @RecordApplicationEvents;
- Убедитесь, что ApplicationEventsTestExecutionListener зарегистрирован;

  Обратите внимание, что ApplicationEventsTestExecutionListener зарегистрирован по умолчанию, а регистрировать
  его вручную нужно только в том случае, если у вас имеется специальная конфигурация через аннотацию
  @TestExecutionListeners, которая не включает слушателей по умолчанию.

- Аннотируйте поле типа ApplicationEvents с помощью аннотации @Autowired и используйте этот экземпляр
  ApplicationEvents в ваших тестовых методах и методах жизненного цикла (например, методы, помеченные
  аннотациями @BeforeEach и @AfterEach, в JUnit Jupiter);

- При использовании расширения SpringExtension для JUnit Jupiter можно объявить параметр метода типа
  ApplicationEvents в тестовом методе или методе жизненного цикла в качестве альтернативы полю,
  аннотированному @Autowired, в тестовом классе;

Следующий тестовый класс использует SpringExtension для JUnit Jupiter и AssertJ для утверждения типов событий
приложения, публикуемых при вызове метода в компоненте, управляемом Spring:

on Java по шагам:

- Аннотируем тестовый класс с помощью @RecordApplicationEvents;
- Внедряем экземпляр ApplicationEvents для текущего теста;
- Используем API-интерфейс ApplicationEvents, чтобы подсчитать, сколько событий OrderSubmitted было опубликовано;

```Java
@SpringJUnitConfig(/* ... */)
@RecordApplicationEvents
class OrderServiceTests {
    @Autowired
    OrderService orderService;
    @Autowired
    ApplicationEvents events;
    @Test
    void submitOrder() {
        // Вызываем метод в OrderService, который публикует событие
        orderService.submitOrder(new Order(/* ... */));
        // Проверяем, чтоб событие OrderSubmitted было опубликовано
        long numEvents = events.stream(OrderSubmitted.class).count();
        assertThat(numEvents).isEqualTo(1);
    }
}
```

on Kotlin:

```Kotlin
@SpringJUnitConfig(/* ... */)
@RecordApplicationEvents
class OrderServiceTests {
    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var events: ApplicationEvents
    @Test
    fun submitOrder() {
        // Вызываем метод в OrderService, который публикует событие
        orderService.submitOrder(Order(/* ... */))
        // Проверяем, чтоб событие OrderSubmitted было опубликовано
        val numEvents = events.stream(OrderSubmitted::class).count()
        assertThat(numEvents).isEqualTo(1)
    }
}
```

См. документацию [по ApplicationEvents для получения дополнительной информации об API-интерфейсе ApplicationEvents](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/test/context/event/ApplicationEvents.html)

---
### События выполнения теста

Слушатель EventPublishingTestExecutionListener, представленный в Spring Framework 5.2, предлагает альтернативный
подход к реализации специального TestExecutionListener. Компоненты в ApplicationContext теста могут прослушивать
следующие события, публикуемые EventPublishingTestExecutionListener, каждое из которых соответствует методу в
API-интерфейсе TestExecutionListener:

- BeforeTestClassEvent;
- PrepareTestInstanceEvent;
- BeforeTestMethodEvent;
- BeforeTestExecutionEvent;
- AfterTestExecutionEvent;
- AfterTestMethodEvent;
- AfterTestClassEvent;

Эти события могут быть потреблены по различным причинам, например, для сброса bean-ов-имитаций или отслеживания
выполнения тестов. Одним из преимуществ потребления событий выполнения теста вместо реализации специального
TestExecutionListener является то, что события выполнения теста могут быть потреблены любым bean-ом Spring,
зарегистрированным в тестовом ApplicationContext, и такие bean-ы могут напрямую воспользоваться внедрением
зависимостей и другими функциями ApplicationContext.

В отличие от этого, TestExecutionListener не является bean-ом в ApplicationContext.

---
**!!! ВНИМАНИЕ !!!** 

Слушатель EventPublishingTestExecutionListener зарегистрирован по умолчанию; однако он публикует
события только в том случае, если ApplicationContext уже загружен. Это предотвращает ненужную
или слишком раннюю загрузку ApplicationContext.

Следовательно, событие BeforeTestClassEvent будет опубликовано только после того, как
ApplicationContext будет загружен другим TestExecutionListener. Например, при наборе реализаций
TestExecutionListener, зарегистрированных по умолчанию, событие BeforeTestClassEvent не будет
опубликовано для первого тестового класса, который использует определенный тестовый
ApplicationContext, но событие BeforeTestClassEvent будет опубликовано для любого последующего
тестового класса в том же тестовом комплекте, который использует тот же тестовый
ApplicationContext, поскольку контекст уже будет загружен при выполнении последующих тестовых
классов (если контекст не был удален из ContextCache через аннотацию @DirtiesContext или политику
вытеснения при достижении максимального размера).

Если нужно, чтобы событие BeforeTestClassEvent всегда публиковалось для каждого тестового
класса, то необходимо зарегистрировать TestExecutionListener, который загружает
ApplicationContext в обратном вызове beforeTestClass, и этот TestExecutionListener должен
быть зарегистрирован перед EventPublishingTestExecutionListener.

Аналогично, если аннотация @DirtiesContext используется для удаления ApplicationContext из
кэша контекста после последнего тестового метода в данном тестовом классе, событие
AfterTestClassEvent не будет опубликовано для этого тестового класса.

---
Для того чтобы прослушивать события выполнения теста, bean Spring может выбрать реализацию интерфейса
org.springframework.context.ApplicationListener. Кроме того, методы слушателей могут быть аннотированы
@EventListener и настроены на прослушивание одного из конкретных типов событий, перечисленных выше.

В связи с популярностью этого подхода, Spring предоставляет следующие специальные аннотации [@EventListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/event/EventListener.html)
для упрощения регистрации слушателей событий выполнения теста. Эти аннотации находятся в пакете
[org.springframework.test.context.event.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/annotation/package-summary.html):

- [@BeforeTestClass](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/annotation/BeforeTestClass.html);
- [@PrepareTestInstance](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/annotation/PrepareTestInstance.html);
- [@BeforeTestMethod](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/annotation/BeforeTestMethod.html);
- [@BeforeTestExecution](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/annotation/BeforeTestExecution.html);
- [@AfterTestExecution](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/annotation/AfterTestExecution.html);
- [@AfterTestMethod](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/annotation/AfterTestMethod.html);
- [@AfterTestClass](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/event/annotation/AfterTestClass.html);

---
### Обработка исключений

По умолчанию, если слушатель событий выполнения теста генерирует исключение при потреблении события, это
исключение передается в используемый базовый тестовый фреймворк (например, JUnit или TestNG). Например,
если потребление события BeforeTestMethodEvent приводит к возникновению исключения, то соответствующий
тестовый метод в результате этого исключения завершится ошибкой. И наоборот, если асинхронный слушатель
событий выполнения теста генерирует исключение, оно не распространяется к базовому тестовому фреймворку.

---
### Асинхронные слушатели

Если нужно, чтобы определенный слушатель событий выполнения теста обрабатывал события асинхронно, то можно
использовать обычную поддержку аннотации @Async из Spring.

Для получения более подробной информации об асинхронной обработке исключений изучать документацию по
[@EventListener на уровне класса](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/event/EventListener.html).
