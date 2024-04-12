package spring.oldboy.integration.service.lesson_34;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.service.CompanyService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
В предыдущем примере CompanyServiceIT.java мы использовали развернутый или избыточный пример
настройки подключения Junit5 к TestContextFramework. Если заглянуть в документацию по Spring Boot
в раздел по внешней настройке:
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.external-config
Под пунктом 12 мы найдем - 'атрибуты свойств в ваших тестах' и применяемую ниже аннотацию.

Если зайти внутрь данной аннотации, то увидим очень похожие, примененные в прошлом примере аннотации:
@ExtendWith(SpringExtension.class), а также @BootstrapWith(SpringBootTestContextBootstrapper.class),
который в свою очередь занимается:
- Использует SpringBootContextLoader в качестве загрузчика контекста по умолчанию.
- !!! При необходимости автоматически ищет @SpringBootConfiguration !!!
- Позволяет определить (custom Environment) пользовательскую среду getProperties(Class).
- Обеспечивает поддержку различных режимов веб-среды (webEnvironment modes).

Фактически данная аннотация делает много работы по-умолчанию (т.е. все то, что мы настраивали руками
в предыдущем примере CompanyServiceIT.java). Например, она сама ищет аннотацию @SpringBootApplication
над нашим SpringAppRunner.java в classpath проекта.
*/
@SpringBootTest
public class CompanyServiceSecondTestIT {

    private static final Integer COMPANY_ID = 1;

    @Autowired
    private CompanyService companyService;

    @Test
    void findById() {
        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}
