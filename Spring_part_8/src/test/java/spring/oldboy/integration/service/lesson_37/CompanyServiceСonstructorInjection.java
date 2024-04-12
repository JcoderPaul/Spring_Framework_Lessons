package spring.oldboy.integration.service.lesson_37;

/* Пример работы тестов к урокам 37 - 38 */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import spring.oldboy.config.DatabaseProperties;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.integration.annotation.IT;
import spring.oldboy.service.CompanyService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/* Используем нашу самописную аннотацию */
@IT
/* Используем аннотацию Lombok для создания конструктора */
@RequiredArgsConstructor
/*
Урок 37. - И самое главное, указываем режим автосвязки для тестового конструктора.

Урок 38. - Конечно, можно применить файл свойств и выставить тот же флаг -
автосвязка bean-ов через spring.properties. Для демонстрации работы нужно
закомментировать аннотацию @TestConstructor если файл spring.properties и
имеет нужные настройки.
*/
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CompanyServiceСonstructorInjection {

    private static final Integer COMPANY_ID = 1;
    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById() {
        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }

    /*
    В данном случае это не тест, а скорее демонстрация того, что часть данных из
    application.yml была переписана данными из application-test.yml в части параметров
    для базы данных.
    */
    @Test
    void getRightDataBaseParamTest(){
        assertEquals(databaseProperties.getPassword(), "test");
        assertEquals(databaseProperties.getUsername(), "test");
    }
}
