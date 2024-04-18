package spring.oldboy.validation.impl;

/* Lesson 91: Пишем свой обработчик валидатор-констрейнтов */

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import spring.oldboy.database.repository.company_repository.CompanyRepository;
import spring.oldboy.dto.UserCreateEditDto;
import spring.oldboy.validation.UserInfo;

/*
Все классы реализующие ConstraintValidator сразу становятся Spring bean-ами
и аннотировать их как @Component не обязательно, но мы сделаем это для
наглядности
*/
@Component
/*
И как в любой bean мы можем внедрять нужные нам зависимости. Данную аннотацию
применили для внедрения CompanyRepository, хотя он нигде в коде не используется,
просто демонстрация, что данный код будет работать, даже если удалить аннотацию
@Component.
*/
@RequiredArgsConstructor
/*
Lesson 91: Наш кастомный валидатор должен реализовывать ConstraintValidator,
параметризуем мы его соответственно: сначала наша аннотация, затем класс над
которым эта аннотация будет стоять.
*/
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {

    private final CompanyRepository companyRepository;

    /*
    Метод *.initialize(A constraintAnnotation) нам реализовывать не обязательно, а
    вот метод *.isValid() нужно.
    */
    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        /* Проверяем есть ли текст в нужных нам полях, все в одном методе */
        return StringUtils.hasText(value.getFirstname()) || StringUtils.hasText(value.getLastname());
    }
}