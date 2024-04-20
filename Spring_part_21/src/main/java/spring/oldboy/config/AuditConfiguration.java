package spring.oldboy.config;

/*
Lesson 58 - Создаем конфигурацию для нашего аудита
Lesson 59 - Аннотируем нашу сущность занимающуюся аудитом как @EnableEnversRepositories
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Lesson 109: Извлекаем принципала и его данные из объекта аутентификации.
        1 - объект аутентификации может быть пустым, отсюда Optional;
        2 - Класс SecurityContextHolder имеет доступ к текущему контексту безопасности;
        3 - Метод *.getContext() всегда возвращает что-то (и никогда null - делает
            default реализацию если это необходимо);
        4 - Из полученного SecurityContext извлекаем объект Authentication см.
            DOC/SecurityContext.jpg;
        5 - Из объекта аутентификации получаем Principal, метод *.getPrincipal()
            возвращает Object, поэтому кастомизируем (приводим к типу) UserDetails,
            см. DOC/Authentication/UserDetails.txt;
        6 - Получаем username;
        7 - Полученные данные уходят в БД, для фиксации 'аудит профиля';
        */
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                             .map(authentication -> (UserDetails) authentication.getPrincipal())
                             .map(userDetails -> userDetails.getUsername());
    }
}
