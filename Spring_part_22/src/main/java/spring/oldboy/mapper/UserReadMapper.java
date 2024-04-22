package spring.oldboy.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.oldboy.database.entity.User;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.dto.UserReadDto;

import java.util.Optional;

/* Будет bean-ом */
@Component
/* При наличии final полей в классе используем аннотацию ниже */
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto map(User object) {
        /*
        В зависимости от ситуации у нашего User может отсутствовать запись в поле
        компания, и такая ситуация требует обработки - мы либо возвращаем компанию,
        при наличии, в противном случае отдаем null.
        */
        CompanyReadDto company = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);

        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstname(),
                object.getLastname(),
                object.getImage(),
                object.getRole(),
                company
        );
    }
}
