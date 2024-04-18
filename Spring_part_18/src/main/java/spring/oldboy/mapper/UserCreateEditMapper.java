package spring.oldboy.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.oldboy.database.entity.Company;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.company_repository.CompanyRepository;
import spring.oldboy.dto.UserCreateEditDto;

import java.util.Optional;

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

    /* Метод позволяем создавать новую запись user, используется в методе *.create() слоя сервисов. */
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
    }

    /* Получаем данные о компании связанной с user */
    public Company getCompany(Integer companyId) {
        return Optional.ofNullable(companyId)
                .flatMap(id -> companyRepository.findById(id))
                .orElse(null);
    }
}
