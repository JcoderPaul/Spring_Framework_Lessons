package spring.oldboy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import spring.oldboy.pool.ConnectionPool;
import spring.oldboy.repository.CrudRepository;
import spring.oldboy.repository.UserRepository;

@Configuration(proxyBeanMethods = true)
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "spring.oldboy",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "spring.oldboy\\..+Repository")
        })
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
