package spring.oldboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static spring.oldboy.database.entity.Role.ADMIN;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        Lesson 111: разрешаем CSRF защиту, комментируем .disable():

        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
        */
                /* Lesson 107: Усложняем авторизацию, добавляем права доступа */
                    http.authorizeHttpRequests(auth -> auth
                        /* К адресам ресурсов в матчере доступ имеют все аутентифицированные */
                        .requestMatchers(antMatcher("/login"),
                                         antMatcher("/users/registration"),
                                         antMatcher("/v3/api-docs/**"),
                                         antMatcher("/swagger-ui/**"))
                                .permitAll()
                        /*
                        К адресу ресурса на удаление записей из БД, фильтрацию,
                        постраничную выборку имеет доступ только ADMIN
                        */
                        .requestMatchers(antMatcher("/users/{\\d+}/delete"),
                                         antMatcher("/users/filter"),
                                         antMatcher("/users/pagination"))
                                .hasAuthority(ADMIN.getAuthority())
                        /* К адресу ресурса с префиксом '/admin' имеет доступ только ADMIN */
                        .requestMatchers(antMatcher("/admin/**"))
                                .hasAuthority(ADMIN.getAuthority())
                        .anyRequest()
                        .authenticated())

                .logout(logout -> logout.logoutUrl("/logout")
                                        .logoutSuccessUrl("/login")
                                        .deleteCookies("JSESSIONID"))
                /*
                Lesson 104:
                Для тестирования разных фильтров используем режим
                комментирования. Закомментируем форму Login и откроем
                аутентификацию при помощи HttpBasicAuthentication,
                затем снова подключим страницу Login.

                .httpBasic(Customizer.withDefaults());

                Lesson 107:
                Удаляем метод .permitAll() теперь все разрешения
                идут через фильтр авторизации см. выше.
                */
                .formLogin(login -> login.loginPage("/login")
                                         .defaultSuccessUrl("/users"));


        return http.build();
    }

    /*
    Lesson 105: Создаем bean, чтобы иметь доступ к PasswordEncoder-у.
    Возвращаем сервисный интерфейс PasswordEncoder для шифрования паролей.
    Предпочтительной реализацией является BCryptPasswordEncoder.
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
