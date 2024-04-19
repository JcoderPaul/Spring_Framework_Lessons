package spring.oldboy.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.oldboy.database.entity.Company;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.company_repository.CompanyRepository;
import spring.oldboy.dto.UserCreateEditDto;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final CompanyRepository companyRepository;

    /*
    Метод позволяем копировать полученные данные в старую
    запись user, используется в методе *.update() слоя сервисов.
    */
    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    /*
    Метод позволяем создавать новую запись user,
    используется в методе *.create() слоя сервисов.
    */
    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);

        return user;
    }

    /* Копируем поля полученные через форму (или URL) в user */
    private void copy(UserCreateEditDto object, User user) {
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));

        /*
        Основная идея тут - проверить наличие аватара у записи из users и
        отобразить ее или дать возможность загрузить или обновить, но никак
        не удалить.

        Мы предполагаем, что передаваемое значение MultipartFile может быть
        null, поэтому используем метод *.ofNullable(), чтобы не поймать NPE.
        */
        Optional.ofNullable(object.getImage())
                /*
                Метод filter() просто проверяет значение и возвращает Optional с объектом того же типа,
                если оно соответствует заданному предикату. И вот тут самое интересное:
                - Если multipartFile пришел из HTML формы без картинки, он, как это не странно, не пустой,
                что легко увидеть, если запустить приложение в пошаговом режиме. Или вернее так, Spring
                возвращает пустой прокси.
                - Далее мы пытаемся понять пустой ли multipartFile применив метод isEmpty(), который
                вернет либо true, либо false, в зависимости от наполнения, см.
                DOC/Multipart/MultipartFileInterface.txt.

                Помним, что все это происходит в методе filter(), который, в свою очередь, вернет
                неизмененный multipartFile, если проверка условий предиката будет - true, или
                Optional.empty, если та же проверка вернет - false.

                - Методом not предиката превращаем true в FALSE, в случае если multipartFile пустой и
                метод isEmpty() вернул - true, т.е. мы не можем из multipartFile вытащить нужный нам
                *.getOriginalFilename(). В этом случае filter вернет Optional.empty и метод ifPresent(),
                который позволяет нам выполнить код над объектом внутри Optional, если он не null,
                останется не у дел.
                - Методом not предиката превращаем false в TRUE, в случае если multipartFile не пустой
                и метод isEmpty() вернул - false. Вот теперь мы имеем возможность применить к
                multipartFile метод *.getOriginalFilename(), который возвращает исходное имя файла в
                файловой системе клиента и, его мы через SET передаем в User.

                Нужно понимать, что тут, в одной строке, через точки и в скобках применяются методы 3-х
                (как минимум, не считая User и UserCreateEditDto) разных классов: Optional.filter(),
                MultipartFile.isEmpty() и Predicate.not()
                */
                .filter(not(multipartFile -> multipartFile.isEmpty()))
                /*
                Передаем изображение в поле соответствующей записи, при создании
                user-a и при его редактировании.
                */
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }

    /* Получаем данные о компании связанной с user */
    public Company getCompany(Integer companyId) {
        return Optional.ofNullable(companyId)
                .flatMap(id -> companyRepository.findById(id))
                .orElse(null);
    }
}
