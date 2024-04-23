package spring.oldboy.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/* Помечаем файл конфигурации */
@Configuration
/*
Аннотация из Swagger-a, позволяющая настроить параметры
безопасности для его взаимодействия с Google OAuth2.0
Provider-ом
*/
@SecurityScheme(
        /* Даем имя и тип схемы безопасности */
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        /* Настраиваем flow */
        flows = @OAuthFlows(
                /*
                Указываем, что используем Authorization Code Flow и передаем данные
                для настройки URL, их можно извлечь из CommonOAuth2Provider-а
                */
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:8080/oauth2/authorization/google",
                        tokenUrl = "https://www.googleapis.com/oauth2/v4/token"
                )
        )
)
public class OpenApiConfiguration {
}
