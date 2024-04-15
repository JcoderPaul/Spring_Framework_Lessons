package spring.oldboy.integration.database.repository.lesson_62;

/* Lesson 62 - тестирование приложения с H2 */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import spring.oldboy.database.entity.Role;
import spring.oldboy.database.repository.user_repository.UserRepository;
import spring.oldboy.integration.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;

@IT
/* Заполним таблицы БД */
@Sql({
        "classpath:sql_scripts/data.sql"
})
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void checkBatch() {
        var users = userRepository.findAll();
        userRepository.updateCompanyAndRole(users);
        System.out.println();
    }

    @Test
    void checkJdbcTemplate() {
        var users = userRepository.findAllByCompanyIdAndRole(1, Role.USER);
        /*
        Если тестировать данный метод без заполнения InMemory БД - H2, естественно она будет пустой
        (и тогда assertThat(users).hasSize(0);). Если мы захотим, что логично, заполнить таблицы БД,
        необходимо использовать аннотацию @Sql({"classpath:sql_scripts/data.sql"}) с параметрами, где
        указан, например, путь к заполняющей таблицы БД информации, у нас это SQL скрипт.
        */
        assertThat(users).hasSize(1);
    }

}
