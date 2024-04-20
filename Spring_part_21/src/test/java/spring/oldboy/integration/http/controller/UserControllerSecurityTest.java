package spring.oldboy.integration.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import spring.oldboy.database.entity.Role;
import spring.oldboy.integration.IntegrationTestBase;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerSecurityTest extends IntegrationTestBase {

    /* После добавления авто-конфигурационной аннотации мы можем внедрить технологию Mockito */
    private final MockMvc mockMvc;

    /* Перед каждым тестовым методом мы будем инициировать тестового пользователя */
    @BeforeEach
    void initTestUser() {
        /* Создаем нашего тестового User-a и коллекцию его Role-ей */
        List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
        User testUser = new User("initTest@testMail.com",
                "big_test",
                roles);
         /*
         Создаем тестовый аутентификационный токен, в его параметры загружаем
         нашего тестового пользователя, именно его будет отслеживать тестовый
         фильтр.
         */
        TestingAuthenticationToken authenticationToken =
                new TestingAuthenticationToken(testUser,
                        testUser.getPassword(),
                        roles);
         /*
         Создаем пустой контекст безопасности используя
         SecurityContextHolder см. DOC/SecurityContext.jpg
         */
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        /* Загружаем в контекст наш тестовый токен */
        securityContext.setAuthentication(authenticationToken);
        /* Загружаем получившийся контекст в хранителя контекста безопасности */
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findAllControllerTest() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

}