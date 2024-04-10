package spring.oldboy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import spring.oldboy.pool.ConnectionPool;
import spring.oldboy.repository.UserRepository;

@Configuration
/*
Поскольку работоспособность AppRunner.java опирается на настройки
и аннотации данного конфигурационного файла, а точнее на указания
файла свойств, от которого зависят тутошние bean-ы, и указания
папки для сканирования и поиска bean-ов, оставим их, тем более,
что они не противоречат и не мешают работе FirstSpringAppRunner.java.
*/
@PropertySource("classpath:properties_for_lesson_24/application.properties")
@ComponentScan(basePackages = "spring.oldboy")
public class ApplicationConfiguration {

    @Bean("pool2")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ConnectionPool pool2(@Value("${db.username}") String username) {
        return new ConnectionPool(username, 20);
    }

    @Bean
    public ConnectionPool pool3() {
        return new ConnectionPool("test-pool", 25);
    }

    @Bean
    public UserRepository userRepository2(ConnectionPool pool2) {
        return new UserRepository(pool2);
    }

    @Bean
    public UserRepository userRepository3() {
        return new UserRepository(pool3());
    }
}
