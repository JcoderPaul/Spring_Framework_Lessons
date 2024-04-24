package spring.oldboy.logging.config;

import jakarta.annotation.PostConstruct;
import spring.oldboy.logging.aop.SecondAspectAndFirstAdvice;
import spring.oldboy.logging.aop.FirstAspect;
import spring.oldboy.logging.aop.ThirdAspectAndAroundAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Slf4j
/*
Аннотация указывает, что класс объявляет один или несколько методов @Bean и может
обрабатываться контейнером Spring для создания определений компонентов и запросов
на обслуживание для этих компонентов во время выполнения.
*/
@Configuration
/*
Включает поддержку аннотированных bean-компонентов @ConfigurationProperties. Bean-s
@ConfigurationProperties можно зарегистрировать стандартным способом (например, с
помощью методов @Bean) или, для удобства, указать непосредственно в этой аннотации.
*/
@EnableConfigurationProperties(LoggingProperties.class)
/*
@ConditionOn... условие, которое соответствует только тогда, когда указанные классы
находятся в пути к классам. Значение класса можно безопасно указать в классах
@Configuration, поскольку метаданные аннотации анализируются с помощью ASM перед
загрузкой класса. Если ссылку на класс нельзя использовать, можно использовать атрибут
строки имени.
*/
@ConditionalOnClass(LoggingProperties.class)
/*
@Conditional, который проверяет, имеют ли указанные свойства определенное значение.
По умолчанию свойства должны присутствовать в среде и не быть равными false. Атрибуты
hasValue() и matchIfMissing() допускают дальнейшую настройку. Атрибут hasValue() можно
использовать для указания значения, которое должно иметь свойство.
*/
@ConditionalOnProperty(prefix = "app.myfirst.logging",
                       name = "enabled",
                       havingValue = "true")
public class LoggingAutoConfiguration {

    /* Инициализируем нашу авто-конфигурацию */
    @PostConstruct
    void init() {
        log.info("LoggingAutoConfiguration initialized");
    }

    @Bean
    @ConditionalOnMissingBean
    public FirstAspect firstAspect() {
        return new FirstAspect();
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean
    public SecondAspectAndFirstAdvice secondAspect() {
        return new SecondAspectAndFirstAdvice();
    }

    @Bean
    @Order(2)
    @ConditionalOnMissingBean
    public ThirdAspectAndAroundAdvice thirdAspect() {
        return new ThirdAspectAndAroundAdvice();
    }

}
