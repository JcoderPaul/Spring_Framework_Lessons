package spring.oldboy.integration.service.lesson_34;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.oldboy.SpringAppRunner;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.service.CompanyService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
Данной аннотации достаточно, чтобы состыковать Junit5 с TestContextFramework
см. DOC/TestContextFramework/TestContextFramework.jpg
*/
@ExtendWith(SpringExtension.class)
/*
Так же нам нужно указать какой ApplicationContext мы будем использовать
см. DOC/TestContextFramework/ContextManagement.txt. Это наша основная
зависимость в SpringTest
*/
@ContextConfiguration(classes = SpringAppRunner.class,
                      initializers = ConfigDataApplicationContextInitializer.class)
/*
Теперь у нас @ExtendWith из Junit5 знает, как и с чем интегрироваться и предоставляет
нам ApplicationContext, а какой именно контекст мы сами указываем с помощью параметров
@ContextConfiguration.

!!!!!!
Однако мы в проекте используем настройку при помощи *.YML файлов, и наши bean-ы создаются
на основе настроек в *.YML файлах, а обычный Spring by default использует properties файлы
(не Spring Boot). По этому нам нужно явно указать откуда брать настройки наших bean-ов.

Большинство аннотаций, например @TestPropertySource, работают с properties файлами и
применение ее над нашим тестовым классом или над классом 'точка входа' SpringAppRunner
нам не помогут (с 'точкой входа' еще хуже, если вдруг мы начнем переписывать основной
код под тестовые классы).

Нам понадобится initializer. Поскольку мы уже используем ApplicationContext работающий
с *.YML, т.е. со всеми файлами свойств и окружением. Т.е. нам нужен этот контекст и мы
его получаем через инициализатор. Полученный нами инициализатор принадлежит пакету -
org.springframework.boot.test.context и предназначен специально для тестов, а так же
ищет *.YML файлы свойств.
!!!!!!
*/
public class CompanyServiceIT {
    /* Большую часть тестового кода перенесем из Unit - теста CompanyServiceTest.java */
    private static final Integer COMPANY_ID = 1;
    /*
    Поскольку это интеграционный тест нам ненужно создавать STUB объекты, мы можем сразу
    создать bean соответственно аннотировав его. И в ТЕСТАХ в принципе нормально
    аннотировать подобным образом поля, а не конструкторы.
    */
    @Autowired
    private CompanyService companyService;

    @Test
    void findById() {
        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
        /*
        Поскольку у нас тут нет MOCK-ов, то и проверки и верификации мы тут не
        используем, это тоже отличие от Unit - теста в CompanyServiceTest.java
        */
    }
}
