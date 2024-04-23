package spring.oldboy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import spring.oldboy.service.UserService;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static spring.oldboy.database.entity.Role.ADMIN;

@Configuration
@EnableWebSecurity
/*
Lesson 114: Мы внедрили UserService и мы могли добавить
конструктор, но использовали удобство Lomboc-a
*/
@RequiredArgsConstructor
public class SecurityConfiguration {

    /*
    Lesson 114: UserService нам нужен, чтобы получить из БД через
    userService.loadUserByUsername(email) нужного нам user-a если
    он есть в ней. Это позволить обойти различия в данных Principal
    полученных из БД и из сервиса авторизации Google.
    */
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                    http.authorizeHttpRequests(auth -> auth
                        /* К адресам ресурсов в матчере доступ имеют все аутентифицированные */
                        .requestMatchers(antMatcher("/login"),
                                         antMatcher("/users/registration"),
                                         antMatcher("/v3/api-docs/**"),
                                         antMatcher("/swagger-ui/**"),
                                         antMatcher("/users"))
                                .permitAll()
                        /*
                        К адресу ресурса на удаление записей из БД, фильтрацию,
                        постраничную выборку имеет доступ только ADMIN
                        */
                        .requestMatchers(antMatcher("/users/{\\d+}/delete"))
                                .hasAuthority(ADMIN.getAuthority())
                        /* К адресу ресурса с префиксом '/admin' имеет доступ только ADMIN */
                        .requestMatchers(antMatcher("/admin/**"))
                                .hasAuthority(ADMIN.getAuthority())
                        .anyRequest()
                        .authenticated())
                /* См. предыдущие уроки */
                .logout(logout -> logout.logoutUrl("/logout")
                                        .logoutSuccessUrl("/login")
                                        .deleteCookies("JSESSIONID"))

                .formLogin(login -> login.loginPage("/login")
                                         .defaultSuccessUrl("/users", true))
                /* Добавляем авторизацию через Google */
                .oauth2Login(oauthConfig -> oauthConfig.loginPage("/login")
                                                       .defaultSuccessUrl("/users",true)
                        /* Мы должны предоставить реализацию OAuth2UserService */
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())));

        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            /* Извлекаем email из userRequest */
            String email = userRequest.getIdToken().getClaim("email");
            /*
            Как вариант, тут можно создать user-a, если он не зарегистрирован в БД - userService.create.
            Но на данном этапе мы извлекаем существующего пользователя из таблицы users нашей БД с его
            Authorities (не из Google сервиса), предполагая что он есть.
            */
            UserDetails userDetails = userService.loadUserByUsername(email);
            /* Мы должны вернуть DefaultOidcUser - создаем его из полученного UserDetails и idToken-a */
            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            /*
            Теперь самое сложное - вернуть прокси, который в случае обращения к UserDetails
            вернет его, а в случае обращения к OidcUser вернет уже его реализацию. И тут очень
            к стати нам то, что и тот и другой интерфейсы и мы можем использовать динамический
            прокси.
            */
            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser, args));
        };
    }
}
