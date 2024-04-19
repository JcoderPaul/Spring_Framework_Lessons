package spring.oldboy.config;

/* Lesson 103 - Настраиваем параметры безопасности и страницу Login */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    /*
    Всю документацию по методам класса HttpSecurity см.
    DOC/NewWebSecurityConfigurer/HttpSecurityClass.txt,
    полный разворот в оригинальной док., ссылка дана в
    начале документа.

    Lesson 103: Простая конфигурация файла с отключенным
    csrf и почти максимальным доступом к приложению после
    аутентификации.

    Ниже в комментарии приведен пример конфигурации класса
    работавшая до версии Spring 5.7.0 см.
    DOC/NewWebSecurityConfigurer:

     @Configuration
     public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable()
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .formLogin(login -> login.loginPage("/login")
                                             .defaultSuccessUrl("/users")
                                             .permitAll());
            }
     }

    В более новых версиях Spring-a используется немного
    другой метод конфигурирования цепочки фильтров см.ниже:
    */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                /*
                Lesson 106:
                Настроим фильтр Logout, укажем куда нужно обратиться (endpoint) для
                перехода к форме логаута, настроим переход - какую страницу нужно
                отобразить пользователю в случае Logout-a. Хотя cookies чистятся
                автоматически мы и это можем настроить - удалим JSESSIONID.

                Более подробно см. HttpSecurityClass.txt (полная ENG. док. по методам)
                */
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

                */

                .formLogin(login -> login.loginPage("/login")
                                         .defaultSuccessUrl("/users")
                                         .permitAll());

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
