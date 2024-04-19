package spring.oldboy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.oldboy.database.repository.company_repository.CompanyRepository;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.mapper.listener.AccessType;
import spring.oldboy.mapper.listener.EntityEvent;
import spring.oldboy.mapper.CompanyReadMapper;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
/*
И снова Lombok конструктор для final полей, будь у нас не final
поля мы бы воспользовались @AllArgsConstructor, для ознакомления
со структурой аннотации можно изучить ее код и документацию на
библиотеку Lombok
*/
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyReadMapper companyReadMapper;
    private final ApplicationEventPublisher eventPublisher;

    /*
    Как всегда, неизвестно есть ли объект по запрошенному ID и естественно из БД
    мы вернем не весь объект, а лишь ту его часть, что доступна по данному запросу
    исходя из уровня аутентификации. Поэтому возвращаемый класс Optional и не чистый
    искомый объект, а его представление DTO.

    Применим аннотацию @Transactional только к методу. Если аннотировать весь класс
    данным образом, то для всех методов данного класса будут автоматически открываться
    транзакции и в коде тестов создаваться прокси объектов, см. в режиме отладки с
    аннотацией и без (если ни класс, ни методы его не аннотированы @Transactional, в
    тестах будут создаваться объекты этих классов, а не прокси - т.к. Spring не умеет
    переписывать классы для своих нужд)
    */
    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                /* Получили результат по запросу и вернули ReadDto */
                .map(entity -> {
                    /*
                    Публикуем событие, используя метод *.publishEvent() и передаем в него
                    нашу сущность, по которой прошло событие и тип доступа из Enum (какое
                    событие произошло: чтение, запись, удаление ...)
                    */
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return companyReadMapper.map(entity);
                });
    }

    public List<CompanyReadDto> findAll() {
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }
}
