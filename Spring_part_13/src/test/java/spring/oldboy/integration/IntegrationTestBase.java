package spring.oldboy.integration;
/* Lesson 65 - подключение библиотеки TestContainers */
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import spring.oldboy.integration.annotation.IT;

@IT
@Sql({
        "classpath:sql_scripts/data.sql"
})
public abstract class IntegrationTestBase {

    /*
    Получаем наш контейнер, в параметрах передаем тэг нашей Docker БД.
    Обычно, значений по-умолчанию самого PostgreSQLContainer хватает,
    но их легко можно задать используя его же методы.
    */
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres");

    /*
    Запускаем наш контейнер. Хотя аннотация говорит о запуске контейнера
    перед каждым тестом, однако, если зайти в метод *.start(), то можно
    заметить, как ID контейнера определяется единожды для всех тестов:
        if (containerId != null) {
                return;
           }
    */
    @BeforeAll
    static void runContainer() {
        container.start();
    }

    /*
    Получаем свойства сгенерированные динамически при запуске
    тестов, нам нужен был URL, вот его мы и извлекаем.
    */
    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        /*
        DynamicPropertyRegistry - реестр, используемый с методами
        @DynamicPropertySource, чтобы они могли добавлять в среду
        свойства, имеющие динамически разрешаемые значения.
        */
        registry.add("spring.datasource.url", () -> container.getJdbcUrl());
    }
}