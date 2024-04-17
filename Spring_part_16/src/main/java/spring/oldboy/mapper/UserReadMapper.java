package spring.oldboy.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.oldboy.database.entity.Company;
import spring.oldboy.database.entity.User;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.dto.UserReadDto;

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
        CompanyReadDto company = null;
        Company objectCompany = object.getCompany();
        if (objectCompany != null) {
            CompanyReadDto map = companyReadMapper.map(objectCompany);
            if (map != null) company = map;
        }
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstname(),
                object.getLastname(),
                object.getRole(),
                company
        );
    }
}
