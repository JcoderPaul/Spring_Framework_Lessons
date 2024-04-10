package spring.web_demo_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration

/*
Данный bean или данная конфигурация активируется только при
включенном профиле 'web', например в файле application.properties
ключ - spring.profiles.active.

Или при запуске метода
*/
@Profile("web")
public class WebConfiguration {
    /*
    Настройка нашего web - приложения: http, сервлеты и т.д.
    см. https://github.com/JcoderPaul/HTTP_Servlets_Java_EE
    */
}
