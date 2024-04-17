package spring.oldboy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.oldboy.database.repository.user_repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import spring.oldboy.dto.UserCreateEditDto;
import spring.oldboy.dto.UserReadDto;
import spring.oldboy.mapper.UserCreateEditMapper;
import spring.oldboy.mapper.UserReadMapper;

import java.util.List;
import java.util.Optional;

/*
Используем Lombok аннотации для создания конструктора с final
полями. Если бы у нас были не final поля, то мы воспользовались
бы @AllArgsConstructor
*/
@Service
@RequiredArgsConstructor
/*
Указываем на то, что методы нашего класса транзакционны, но переданный
параметр 'readOnly = true' позволяет оптимизировать те запросы к БД,
которые read-only, т.е. не вносят изменений в БД.
*/
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    /*
    Нам нужен список пользователей в определенном виде,
    который определяется классом UserReadDto.
    */
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                                       .map(userReadMapper::map)
                                       .toList();
    }
    /*
    В базе данных может и не быть пользователя с конкретным ID.
    По этому применяем Optional. А поскольку нам не нужен весь
    User, а лишь ограниченный классом UserReadDto набор данных
    его мы и запрашиваем.
    */
    public Optional<UserReadDto> findById(Long id) {
        /*
        Метод нашего UserRepository - Optional<T> findById(ID id) возвращает
        'кота Шреденгера', т.е. мы не знаем есть ли User под таким ID или нет,
        у объектов класса Optional есть свой метод *.map(), который принимает
        в качестве параметра маппер, в нашем случае это UserReadMapper.

        По факту первый метод *.map() если значение присутствует, возвращает
        Optional параметр, описывающий (как будто с помощью ofNullable) результат
        применения данной функции сопоставления к значению, в противном случае
        возвращает пустой Optional параметр.

        Если функция сопоставления возвращает нулевой результат, этот метод
        возвращает пустой Optional параметр.

        Параметры: Mapper – функция сопоставления, применяемая к значению, если
        оно присутствует.

        Возвращает: Optional параметр, описывающий результат применения функции
        сопоставления к значению этого Optional параметра, если значение
        присутствует, и в противном случае пустой Optional параметр.

        Профессионал с опытом сразу поймет, что тут происходит, но новичку нужна
        более подробная раскладка:
        1. Нам нужно вернуть Optional<UserReadDto>. Optional - это 'коробка' в
           которой может быть, а может и не быть объект класса UserReadDto.
        2. Метод userRepository.findById(id) возвращает Optional<User>.
        3. Первый метод *.map() при текущем переданном в него параметре возвращает
           Optional<UserReadDto>, поскольку он выглядит как:
           public <U> Optional<U> map(Function<? super T, ? extends U> mapper), т.е.
           то что нам и нужно.
        4. Второй метод *.map() тот, что принимает объект User по ID возвращает либо
           UserReadDto, либо ничего не возвращает.
        В итоге в 'коробке' Optional либо будет лежать найденный UserReadDto, либо
        'коробка' будет пуста.
        */
        return userRepository.findById(id)
                             .map(object -> userReadMapper.map(object));
    }

    /*
    Первые два запроса на чтение данных из БД, являются read-only,
    а вот текущий вносит изменения в данные и значит его нужно
    отдельно пометить как @Transactional без параметра. Тоже будет
    и с другими методами, которые вносят изменения в БД.
    */
    @Transactional
    /*
    Тут мы создаем сущность и должны вернуть именно нечто конкретное,
    например UserReadDto или пробросить ошибку если таковая возникла.
    */
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                /*
                Полученный userDto нам нужно преобразовать в User, а
                это отдельный маппер, у нас это UserCreateEditMapper
                */
                .map(userCreateEditMapper::map)
                /* Сохраняем полученную сущность */
                .map(userRepository::saveAndFlush)
                /* Из сохраненной сущности снова получаем UserReadDto */
                .map(userReadMapper::map)
                /*
                Если что-то пошло не так, на любом из этапов бросаем
                исключение и отлавливаем ее на уровне контроллеров
                */
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        /*
        Поскольку мы работаем с конкретным user-ом определенным по
        ID, посему мы пытаемся его получить с уровня DAO/Repository
        */
        return userRepository.findById(id)
                              /* Если данные получены вносим изменения */
                             .map(entity -> userCreateEditMapper.map(userDto, entity))
                              /* Сохраняем изменения и фиксируем их */
                             .map(userRepository::saveAndFlush)
                              /* Возвращаем получившееся UserReadDto */
                             .map(object -> userReadMapper.map(object));
    }

    /* В случае с удалением мы захотим узнать успешно ли прошла операция, отсюда boolean */
    @Transactional
    public boolean delete(Long id) {
        /* И снова, поиск user по ID, в случае успеха удаляем и возвертаем true */
        return userRepository.findById(id)
                .map(entity -> {
                    /* Метод из интерфейса CrudRepository */
                    userRepository.delete(entity);
                    /*
                    Метод из интерфейса JpaRepository. Здесь необходим, чтобы тесты на данный метод отработали
                    действительно корректно, поскольку при тестировании обычно происходит rollback, а не commit
                    тестируемых операций. Т.е. если данный метод применять 'в бою' он отработает нормально, но
                    вот для тестов, именно тут, мы явно прописываем *.flush()
                    */
                    userRepository.flush();
                    /*
                    Недостаток метода *.delete() в том, что он возвращает void,
                    а нам нужно булева переменная, поэтому возвращаем ее сами.
                    */
                    return true;
                })
                /* В случае неудачи возвертаем false */
                .orElse(false);
    }

}
