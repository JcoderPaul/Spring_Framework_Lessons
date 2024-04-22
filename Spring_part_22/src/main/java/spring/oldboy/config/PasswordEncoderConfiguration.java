package spring.oldboy.config;

 /*
    Lesson 114 (из Lesson 105):

    Чтобы не возникало циклической зависимости в SecurityConfiguration.java
    вида см. DOC/SecurityConfigurationCycle.jpg вынесем энкодер в отдельный
    класс.

    Создаем bean, чтобы иметь доступ к
    PasswordEncoder-у. Возвращаем сервисный интерфейс PasswordEncoder
    для шифрования паролей. Предпочтительной реализацией является
    BCryptPasswordEncoder.
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
