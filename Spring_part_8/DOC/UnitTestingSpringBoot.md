### Модульное тестирование с помощью Spring Boot

Модульные тесты составляют основу многих стратегий тестирования. Каждый проект Spring Boot, который мы
запускаем с помощью [Spring Initializr](https://start.spring.io/), имеет прочную основу для написания
модульных тестов. Настраивать практически нечего, так как Spring Boot Starter Test включает в себя все
необходимые строительные блоки.

Помимо включения и управления версией Spring Test, Spring Boot Starter включает и управляет версиями
следующих библиотек:

- JUnit 4/5;
- Mockito;
- Библиотеки проверки утверждений, такие как AssertJ, Hamcrest, JsonPath и т.п.;

О тестировании Spring приложений и о включенных во фреймворк Spring библиотеках тестирования можно
почитать тут: https://rieckpil.de/guide-to-testing-with-spring-boot-starter-test/ и конечно в официальной
документации: https://docs.spring.io/spring-framework/reference/testing.html

В большинстве случаев наши модульные тесты не нуждаются в какой-либо конкретной функции Spring Boot или
Spring Test, поскольку они будут полагаться исключительно на JUnit и Mockito.

Например, с помощью модульных тестов мы изолированно тестируем, например, наши Service классы и имитируем
каждого сотрудника тестируемого класса:

```Java
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;
    
    import java.math.BigDecimal;
    
    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.mockito.Mockito.when;
    
    /* Регистрируем (подключаем) Mockito extension */
    @ExtendWith(MockitoExtension.class)
    public class PricingServiceTest {
    
      /* Показываем Mockito, что данный объект нужно 'замокать' */
      @Mock
      private ProductVerifier mockedProductVerifier;
    
      /* Стандартная аннотация тестового метода */
      @Test
      public void shouldReturnCheapPriceWhenProductIsInStockOfCompetitor() {
        /* Указываем, какое логическое значение нужно вернуть */
        when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods"))
          .thenReturn(true);
    
        PricingService cut = new PricingService(mockedProductVerifier);
    
        assertEquals(new BigDecimal("99.99"), cut.calculatePrice("AirPods"));
      }
    }
```

Как видно из раздела import тестового класса выше, Spring вообще может не быть включенным. Следовательно, мы
можем применять методы, из модульного тестирования (Unit Tests) любого другого приложения Java. Именно по-этому
важно изучить основы JUnit 4/5 и Mockito, чтобы максимально использовать возможности модульного тестирования.

Для некоторых частей нашего приложения модульное тестирование не принесет особой пользы. Хорошими примерами для
этого являются уровень персистентности или тестирование HTTP-клиента. Тестируя такие части нашего приложения, мы
в конечном итоге почти копируете нашу же реализацию, поскольку нам приходится имитировать много взаимодействий с
другими классами.

Лучшим подходом здесь является работа с нарезанным контекстом Spring-а, который можно легко автоматически
настроить с помощью аннотаций теста Spring Boot.

---
### Тесты Spring Context

В дополнение к традиционным модульным тестам мы можем писать тесты с помощью Spring Boot, которые нацелены на
определенные части (фрагменты) вашего приложения. TestContext Spring фреймворка вместе с Spring Boot адаптирует
Spring контекст с достаточным количеством компонентов для конкретного теста.

Цель таких тестов - изолированно протестировать определенную часть нашего приложения без запуска всего приложения.
Это сокращает, как время выполнения теста, так и потребность в обширной настройке теста. Такие тесты не попадают
на 100% в категорию модульных или интеграционных тестов. Некоторые разработчики называют их модульными тестами,
потому что они тестируют, например, один контроллер изолированно. Другие разработчики относят их к интеграционным
тестам, поскольку в них задействована поддержка Spring.

Как бы мы их ни называли, необходимо убедиться, что у нас есть общее понимание (и лексикон) с коллегами по работе,
по крайней мере, в вашей команде разработчиков.

Spring Boot предлагает много аннотаций, позволяющих проверить различные части нашего приложения в отдельности:

- @JsonTest,
- @WebMvcTest,
- @DataMongoTest,
- @JdbcTestи т.д.

Все они автоматически настраивают фрагменты Spring TestContext и включают только компоненты Spring, относящиеся
к тестированию определенной части нашего приложения. Наиболее распространенные из этих аннотаций и их
использование, описано тут: https://rieckpil.de/spring-boot-test-slices-overview-and-usage/

Две наиболее важные аннотации (сначала изучите их):

- @WebMvcTest - применяется для эффективного тестирования нашего веб-слоя с помощью MockMvc;
- @DataJpaTest - применяется для эффективного тестирования нашего уровня персистентности;

Также доступны аннотации для других нишевых частей нашего приложения:

- @JsonTest - используется для проверки сериализации и десериализации JSON;
- @RestClientTest - используется чтобы протестировать RestTemplate;
- @DataMongoTest - используется для тестирования кода, связанного с MongoDB;

При их использовании важно понимать, какие компоненты входят в состав TestContext, а какие нет. Документация
JavaDoc каждой аннотации объясняет выполненную автоконфигурацию и цель.

Мы всегда можем расширить контекст автонастройки для своего теста, явно импортировав компоненты с помощью
@Import или определив дополнительные компоненты Spring Beans, используя @TestConfiguration:

```Java
@WebMvcTest(PublicController.class)
class PublicControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MeterRegistry meterRegistry;

  @MockBean
  private UserService userService;

  @TestConfiguration
  static class TestConfig {

    @Bean
    public MeterRegistry meterRegistry() {
      return new SimpleMeterRegistry();
    }
  }
}
```

Мы можем найти дополнительные методы устранения потенциальных исключений NoSuchBeanDefinitionException, с
которыми мы можем столкнуться при разработке таких тестов тут:
https://rieckpil.de/fix-no-qualifying-spring-bean-error-for-spring-boot-tests/
