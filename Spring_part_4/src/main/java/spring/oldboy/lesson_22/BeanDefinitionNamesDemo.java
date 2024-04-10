package spring.oldboy.lesson_22;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.oldboy.config.ApplicationConfiguration;

public class BeanDefinitionNamesDemo {
    public static void main(String[] args) {

        /*
        Получаем чистый контекст. Это нужно для того, чтобы программно
        загрузить сначала файл конфигурации, затем задать активные профили,
        и только после создать все bean-ы согласно заданным профилям.

        Если сделать загрузку контейнера bean-ми, как мы это делали ранее,
        т.е. сразу в конструктор передавали наш ApplicationConfiguration.class,
        то к моменту получения Environment все уже будет сделано и наш
        *.refresh() не сработает. Т.к. уже созданные bean-ы не будут создаваться
        повторно (Жизненный цикл готовых bean-ов дважды запустить нельзя).
        */
        try (AnnotationConfigApplicationContext myContext =
                     new AnnotationConfigApplicationContext()) {
            /* И вот только теперь мы регистрируем файл конфигурации */
            myContext.register(ApplicationConfiguration.class);
            /* Получаем Environment и задаем активные профили */
            myContext.getEnvironment().setActiveProfiles("prod");
            /* Обновляем контекст */
            myContext.refresh();
            /*
            WebConfiguration bean в нашем списке bean-ов не будет, т.к.
            конфигурация web не активна исходя из настроек профилей.
            */

        /*
        В зависимости от настроек spring.profiles.active в файле application.properties
        мы можем получить различный набор собранных в IoC контейнер bean-ов.
        */

        String[] beanCollection =
                myContext.getBeanDefinitionNames();
            for (String prn: beanCollection) {
                System.out.println(prn);
            }

        }
    }
}
