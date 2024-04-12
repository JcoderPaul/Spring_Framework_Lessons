package spring.oldboy.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.entity.Company;
import spring.oldboy.listener.EntityEvent;
import spring.oldboy.repository.CrudRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/* Для обработки аннотаций @Mock и @InjectMocks */
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    /* Нам нужна переменная которая будет передаваться при создании новой Company */
    private static final Integer COMPANY_ID = 1;
    /*
    От классов: CompanyRepository, UserService и ApplicationEventPublisher зависит класс
    CompanyService поэтому мы помечаем их соответствующей аннотацией т.е. создаем для них
    mock объекты - заглушки.
    */
    @Mock
    private CrudRepository<Integer, Company> companyRepository;
    @Mock
    private UserService userService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    /*
    При unit-тестировании нам нужен экземпляр CompanyService для тестирования метода *.findById(). Однако,
    сам данный класс так же зависит от, CompanyRepository, UserService и ApplicationEventPublisher. Значит,
    нам их нужно откуда-то взять или создать на них некие удобоваримые заглушки ('замокать'), см. выше, а в
    наш CompanyService внедрить эти 'моки' см. примеры работы с Mockito (там-же раздел DOC):
    https://github.com/JcoderPaul/Junit5_Tests/tree/master/Junit5_Mockito_lesson_8
    https://github.com/JcoderPaul/Junit5_Tests/tree/master/Junit5_Mockito_lesson_9
    */
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {
        /*
        Метод ниже, возвращает STUB-объект, который используется mocks и spies для создания ответа
        (Answer) на вызовы методов во время тестов. Если читать строку, то получим, что мы просим
        Mockito вернуть (toBeReturn) - true, (when) когда мы вызовем у CompanyRepository метод
        *.findById() и передадим в него ID. Т.е Mockito поставит нам объект Company.
        */
        doReturn(Optional.of(new Company(COMPANY_ID)))
                .when(companyRepository).findById(COMPANY_ID);
        /* Теперь мы пытаемся получить mock-прокси объект */
        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);
        /* И проверяем его наличие, т.е. мы все же ожидаем его получить и если он существует, то */
        assertTrue(actualResult.isPresent());
        /*
        Создаем CompanyReadDto с заданным ID - т.е. берем ожидаемый
        результат с которым и будем сравнивать актуальный результат
        */
        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        /*
        Метод *.ifPresent() позволяет выполнить какое-то действие, если объект не пустой.

        Простой тест с OPTIONAL объектом мог бы выглядеть так:

        @Test
        public void givenOptionalWhenIfPresentWorksThenCorrect() {
            Optional<String> opt = Optional.of("oldboy");
            opt.ifPresent(name -> System.out.println(name.length()));
        }

        что мы и реализуем ниже, т.е. при наличии, актуального результата сравниваем его с
        ожидаемым на эквивалентность.
        */
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
        /*
        Mockito предоставляет массу методов для контроля над проведением тестов,
        а так же взаимодействия с результатами тестов в процессе. Это механизм
        verify - верификации.

        Тут мы проверяем отправлен ли был event (событие зафиксировано)
        */
        verify(eventPublisher).publishEvent(any(EntityEvent.class));
        verifyNoMoreInteractions(eventPublisher, userService);
    }
}