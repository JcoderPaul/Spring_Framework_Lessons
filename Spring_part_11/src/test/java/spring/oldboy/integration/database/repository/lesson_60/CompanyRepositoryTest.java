package spring.oldboy.integration.database.repository.lesson_60;

/* Lesson 60 - проверяем JDBC метод. */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.database.entity.Role;

import spring.oldboy.database.repository.user_repository.UserRepository;
import spring.oldboy.dto.PersonalInfo;
import spring.oldboy.integration.annotation.IT;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class CompanyRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void checkJdbcTemplate() {
        List<PersonalInfo> users = userRepository.findAllByCompanyIdAndRole(1, Role.USER);
        assertThat(users).hasSize(1);
        /* Извлечем для наглядности */
        users.forEach(System.out::println);
    }

}