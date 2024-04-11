package spring.oldboy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.entity.Company;
import spring.oldboy.listener.AccessType;
import spring.oldboy.listener.EntityEvent;
import spring.oldboy.repository.CrudRepository;

import java.util.Optional;

@Service
/*
И снова Lombok конструктор для final полей, будь у нас не final
поля мы бы воспользовались @AllArgsConstructor, для ознакомления
со структурой аннотации можно изучить ее код и документацию на
библиотеку Lombok
*/
@RequiredArgsConstructor
public class CompanyService {

    private final CrudRepository<Integer, Company> companyRepository;
    private final UserService userService;
    /*
    У нас готов Event, готов Listener для прослушки этого eventa-a.
    Теперь нам нужен код, который будет внедрен в бизнес логику и
    будет отправлять свершенное событие на обработку слушателю. Это
    происходит в методе *.findById(), условно в момент создания нашего
    DTO.

    Но до этого нам нужен объект ИЗДАТЕЛЬ (publisher), тот кто сообщает
    о свершившемся событии. В нашем распоряжении есть куча пост-процессоров
    и в частности Aware (которые внедряются в первую очередь) интерфейсы и
    ApplicationEventPublisher и его мы не будем получать через сеттер.
    Мы просто попросим его у контекста, как обычный внедряемый (inject) bean.

    На самом деле реализация ApplicationEventPublisher это сам же
    AnnotationConfigApplicationContext.
    */
    private final ApplicationEventPublisher eventPublisher;

    /*
    Как всегда, неизвестно есть ли объект по запрошенному ID и
    естественно из БД мы вернем не весь объект, а лишь ту его
    часть, что доступна по данному запросу исходя из уровня
    аутентификации. Поэтому возвращаемый класс Optional и не
    чистый искомый объект, а его представление DTO.
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
                    return new CompanyReadDto(entity.id());
                });
    }
}
