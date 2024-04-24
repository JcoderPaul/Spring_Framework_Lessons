package spring.oldboy.logging.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Data
@NoArgsConstructor
/* Помечаем наш конфигурационный файл и задаем префикс для properties.yaml */
@ConfigurationProperties(prefix = "app.myfirst.logging")
public class LoggingProperties {
    /* Добавляем демонстрационные свойства: логирование разрешено/нет и позволим задавать уровень логирования */
    /**
     * to enable myfirst-starter logging aop on service layer
     */
    private boolean enabled;
    private String level;

    /* Для того чтобы увидеть, что наши свойства проинициализировались с нужными значениями создадим метод */
    @PostConstruct
    void init() {
        log.info("Logging properties initialized: {}", this);
    }
}