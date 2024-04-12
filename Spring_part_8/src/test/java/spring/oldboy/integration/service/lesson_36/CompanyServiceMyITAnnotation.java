package spring.oldboy.integration.service.lesson_36;
/*
Задействуем нашу самописную аннотацию @IT, объединяющую в себе другие аннотации:
- @Target(ElementType.TYPE)
- @Retention(RetentionPolicy.RUNTIME)
- @ActiveProfiles("test")
- @SpringBootTest
которые мы применяли в CompanyServiceTestProfileIT.java, логику происходящего
см. DOC/TestContextFramework/MetaAnnotationSupportForTesting.txt

*/
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spring.oldboy.config.DatabaseProperties;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.integration.annotation.IT;
import spring.oldboy.service.CompanyService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@IT
public class CompanyServiceMyITAnnotation {

    private static final Integer COMPANY_ID = 1;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private DatabaseProperties databaseProperties;

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
