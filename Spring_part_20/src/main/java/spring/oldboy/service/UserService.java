package spring.oldboy.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.UserRepository;
import spring.oldboy.dto.UserCreateEditDto;
import spring.oldboy.dto.UserFilterDto;
import spring.oldboy.dto.UserReadDto;
import spring.oldboy.mapper.UserCreateEditMapper;
import spring.oldboy.mapper.UserReadMapper;

// import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collections;
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
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final ImageService imageService;

    public Page<UserReadDto> findAll(UserFilterDto filter, Pageable pageable) {

        return userRepository.findAllByFilterAndPage(filter, pageable)
                .map(userReadMapper::map);
    }

    /*
    Lesson 87 - Метод передает на уровень репозиториев критерии фильтрации
    наших User-ов и получив список отфильтрованных записей User-ов возвращает
    его (их) на уровень контроллеров в виде списка UserReadDto. Никакой
    'пагинации' - разбития на страницы тут не происходит.
    */
    public List<UserReadDto> findAll(UserFilterDto filter) {
        return userRepository.findAllByFilter(filter).stream()
                                                     .map(userReadMapper::map)
                                                     .toList();
    }
    /*
    Нам нужен список всех User-ов в определенном
    виде, который определяется классом UserReadDto.
    */
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                                       .map(userReadMapper::map)
                                       .toList();
    }
    /*
    Lesson 108: Перенесем аннотацию с уровня контролеров сюда.

    @PreAuthorize("hasAuthority('ADMIN')")
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
                Как и ранее, полученный userDto нам нужно преобразовать в User, а
                это отдельный маппер, у нас это UserCreateEditMapper. Но, так же
                нам нужно загрузить (в 'профиль') в соответствующее поле записи
                картинку-аватарку для конкретного user-a если она была передана в
                форме регистрации.
                */
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCreateEditMapper.map(dto);
                })
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
                             .map(entity -> {
                                    uploadImage(userDto.getImage());
                                    return userCreateEditMapper.map(userDto, entity);
                             })
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

    /* Обслуживающий метод, для загрузки изображения */
    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        /*
        Если поле image не пустое, т.е. пользователь приложения пытается загрузить картинку
        через форму, то идет обращение к классу ImageService и через его метод *.upload()
        записывается в заранее определенное в ImageService место, в нашем случае, на
        локальной машине. Естественно нет аватарки - нет записи/перезаписи.
        */
        if (!image.isEmpty()) {
            imageService.uploadAvatar(image.getOriginalFilename(), image.getInputStream());
        }
    }

    /* Метод извлекающий аватарку из БД. Для наглядности разбит на шаги. */
    public Optional<byte[]> findAvatar(Long id) {
                /*
                Сначала находим user-a по ID, если такой user
                есть, в худшем случае дальше может пойти null
                завернутый в Optional.
                */
        Optional<User> step_1 = userRepository.findById(id);
                /*
                Теперь достаем у user-a название аватарки, если она у
                user-a есть, и если user таки есть, в худшем случае
                дальше пойдет null завернутый в Optional.
                */
        Optional<String> step_2 = step_1.map(user -> user.getImage());
                /*
                Проверяем есть ли текстовая ссылка на аватарку, если есть - true
                и обернутый в Optional String ссылки идет дальше, в худшем случае,
                дальше пойдет Optional.empty
                */
        Optional<String> step_3 = step_2.filter(imagePath -> StringUtils.hasText(imagePath));
                /*
                Разница между map() и flatMap() в том, что map() изменяет только "распакованные"
                или простые-не-optional, значения "упакованных" в Optional контейнер объектов, а
                flatMap() перед изменением самостоятельно их "распаковывает", т.е. если у нас,
                например Optional<Optional<String>>, и проводит манипуляции, либо возвращает -
                Optional.empty.
                */
        Optional<byte[]> final_step = step_3.flatMap(imagePath -> imageService.getAvatar(imagePath));
        return final_step;
    }

    /* Lesson 102 - Нам нужен объект класса UserDetails */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* Находим user-a по имени */
        return userRepository.findByUsername(username)
                /*
                А вот тут, чуть хитрее, т.к. мы будем использовать
                Spring реализацию класса User, а не нашу.
                */
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

}
