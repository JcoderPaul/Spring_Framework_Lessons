package spring.oldboy.config;

/*
Part 10: Lesson 58 - Создаем конфигурацию для нашего аудита
Part 10: Lesson 59 - Аннотируем нашу сущность занимающуюся аудитом как  @EnableEnversRepositories
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import spring.oldboy.SpringAppRunner;

import java.util.Optional;

@EnableJpaAuditing
/*
Подключаем механизм Hibernate Envers, а так же получаем доступ параметрам таблиц
аудируемых сущностей. В параметрах передадим наш базовый пакет: spring.oldboy.
В противном случае сканированию подвергнется только: spring/oldboy/config/condition.
См. DOC/DataEnvers/EnableEnversRepositories.txt
*/
@EnableEnversRepositories(basePackageClasses = SpringAppRunner.class)
@Configuration
public class AuditConfiguration {

    /*
    Для того чтобы передать данные в наши аудирующие поля
    нам нужен провайдер, в нашем случае передающий String,
    хотя туда могут улететь и ID и Email и т.д.
    */
    @Bean
    public AuditorAware<String> auditorAware() {
        /*
        В реальных приложениях мы в поля определяющие того, кто создал
        сущность или изменил ее, обычно, передаем данные из службы
        безопасности - SecurityContext.getCurrentUser().getEmail() или
        SecurityContext.getCurrentUser().getId(), но в нашем случае мы
        пока тренируемся и просто передаем объект String.

        AuditorAware функциональный интерфейс, передадим хардкодный текст.
        */
        return () -> Optional.of("spring/oldboy");
    }
}
