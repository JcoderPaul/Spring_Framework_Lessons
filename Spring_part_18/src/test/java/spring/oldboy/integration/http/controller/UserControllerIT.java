package spring.oldboy.integration.http.controller;

/*
Lesson 80 - Как писать тесты на уровне контроллеров, применение MockMvc
Lesson 81 - Применение в настройках проектах application.yml параметра
            конвертора дат в формате ISO, см. ниже.
*/

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import spring.oldboy.integration.IntegrationTestBase;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static spring.oldboy.dto.UserCreateEditDto.Fields.*;

/*
Для имитации HTTP запросов нам нужна авто-конфигурация MockMvc. Эта аннотация,
которую можно применить к тестовому классу для включения и настройки автоматической
настройки MockMvc. MockMvc в свою очередь - основная точка входа для поддержки
тестирования Spring MVC на стороне сервера.
*/
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    /* После добавления авто-конфигурационной аннотации мы можем внедрить технологию Mockito */
    private final MockMvc mockMvc;

    @Test
    void findAllControllerTest() throws Exception {
        /*
        Выполняем GET запрос, теперь нам доступен утиллитный класс
        билдеров MockMvcRequestBuilders, его и задействуем, передав
        в параметрах URL ссылку, см. MockMvc.txt
        */
        mockMvc.perform(get("/users"))
                /*
                Метод интерфейса ResultActions реагирующий
                на ожидания некоего известного результата
                */
                .andExpect(status().is2xxSuccessful()) // Ожидаем 200 статус
                .andExpect(view().name("user/users")) // Ожидаем конкретное отображение, проверяем view
                .andExpect(model().attributeExists("users")) // Ожидаем наличие атрибута, проверяем model
                .andExpect(model().attribute("users", hasSize(5))); // Сложный matcher для проверки атрибута
    }

    /* Тестируем метод *.create() уровня контроллеров */
    @Test
    void createControllerTest() throws Exception {
        /* Метод create у нас реализован через POST - реализуем его тут и передаем параметры */
        mockMvc.perform(post("/users")
                /* У нас POST запрос и параметры передаются в теле запроса */
                                .param(username, "test2@gmail.com")
                                .param(firstname, "Test2")
                                .param(lastname, "TestTest2")
                                .param(role, "ADMIN")
                                .param(companyId, "1")
                                /*
                                Lesson 81: на прошлом уроке мы избежали применения данной строки,
                                т.е. переданный параметр был пустым. В данном случае мы немного
                                доработали application.yml в разделе spring.date выставили формат
                                дат ISO. Естественно у нас есть возможность выставить date-time и
                                просто time.
                                */
                                .param(birthDate, "2024-01-01")
                )
                .andExpectAll(
                        /*
                        Мы не используем REST в полной мере и сам метод *.create() возвращает
                        у нас @ResponseStatus(HttpStatus.CREATED) т.е. статус 201 - создано,
                        хотя у нас идет перенаправление, т.е. redirect - статус 3**, или
                        status().is3xxRedirection().

                        Имеем противоречие - сгладим его грубо, пока, пусть возвращаемый методом
                        статус будет 2**, как мы и задумывали изначально.
                        */
                        status().is2xxSuccessful(),
                        /*
                        Но по факту у нас все же redirect с конкретным URL, вот паттерн этого
                        URL мы и проверим применив регулярное выражение. Мы же ожидаем, что
                        вернется страничка с номером ID сколь угодно большим, отсюда либо
                        "/users/*", либо более красивое "/users/{\\d+}" - это AntMatcher
                        см. AntPathMatcher.
                        */
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }
}