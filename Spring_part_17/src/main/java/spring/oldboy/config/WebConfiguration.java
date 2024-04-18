package spring.oldboy.config;

/* Lesson 81 - Создание преобразователя данных (дат) */

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import org.springframework.data.convert.Jsr310Converters;

@Configuration
/*
Для того чтобы наш конфигурационный класс мог использоваться именно
для Web-конфигурации мы должны реализовать интерфейс WebMvcConfigurer.
В данном интерфейсе масса методов которые позволяют ДОконфигурировать
специфичным образом наше приложение.
*/
public class WebConfiguration implements WebMvcConfigurer {

    /*
    Теперь мы переопределяем форматер, в теле метода мы можем
    добавить и конвертор и форматер доступный из FormatterRegistry.

    Форматеры обычно используются для преобразования данных при
    переходе из локали в локаль (условно при смене языка, страны).

    Конверторы не имеют в своей реализации зависимости от локали,
    т.е. они нужны для конкретного преобразования одних данных в
    другие. Например, из обычной строки в дату.

    См. WebMvcConfigurer.txt
    */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        /*
        Мы описали 3-и возможных варианта преобразования данных переданных по HTTP,
        см. ReadMe.md текущего раздела. Наиболее предпочтителен 1-ый вариант, через
        настройки в application.yml.

        Для тестирования слоя контроллеров лучше выбрать один, поэтому в данном случае
        мы закомментируем код., хотя явного конфликта при организации всех трех вариантов
        одновременно при запуске тестового метода *.createControllerTest() мы не увидим.
        */
        // registry.addConverter(Jsr310Converters.StringToLocalDateConverter.INSTANCE);
    }
}
