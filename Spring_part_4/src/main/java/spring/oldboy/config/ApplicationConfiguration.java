package spring.oldboy.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Component;
import spring.oldboy.pool.StarterConnectionPool;
import spring.oldboy.repository.CrudRepository;
import spring.oldboy.repository.UserRepository;
import spring.web_demo_config.WebConfiguration;

/* Импортируем настройки из .xml файла */
@ImportResource("classpath:application.xml")
/*
Тут мы указываем другие конфигурационные классы, для подключения к нашему приложению,
мы можем тут указать конфигурации, которые сами автоматически не сканируем. Т.е. наше
приложение разрослось на столько, что нам нужно иметь дело с компонентами из самых
разных мест, подключать различные структуры пакетов и модули, созданные нами и третьими
лицами. В этом случае добавление всего в контекст может привести к возникновению
конфликтов по поводу того, какой компонент использовать.
*/
@Import(WebConfiguration.class)
@Configuration(proxyBeanMethods = true)
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "spring.oldboy",
                        useDefaultFilters = false,
                        includeFilters = {
                @Filter(type = FilterType.ANNOTATION, value = Component.class),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
                @Filter(type = FilterType.REGEX, pattern = "spring.oldboy\\..+Repository")
        })
/*
Поскольку наш конфигурационный файл обычный bean (компонент), то инъекцию зависимостей
(внедрение других bean-ов) мы можем ввести и через поля, и через конструктор, и через
сеттеры.
*/
public class ApplicationConfiguration {

        /*
        Создаем bean на основе метода и bean будет иметь ID = "pool2",
        поскольку имя метода автоматически становится именем bean-a,
        то в параметр его можно было и не передавать.
        */
        @Bean("pool2")
        /*
        С работой области видимости мы уже сталкивались и тут мы задаем,
        что данный bean будет singleton, т.е. попадет в IoC контейнер.
        */
        @Scope(BeanDefinition.SCOPE_SINGLETON)
        /* Вариант внедрения через @Value из application.properties */
        public StarterConnectionPool pool2(@Value("${db.username}") String username) {
                return new StarterConnectionPool(username, 20);
        }

        /*
        Еще раз, мы можем из набора однотипных bean-ов выбрать конкретный используя,
        как в нашем случае @Qualifier("pool2") или как в нашем же случае имя совпадающее
        с именем bean-a. И тот и другой вариант будет работать независимо от другого.
        */
        @Bean
        /*
        Тут мы имеем массив строк и тут работают операции: |, !, &
        Данная аннотация позволяет разделить создание bean-ов по
        профилю: разработки (применения). В зависимости от активного
        профиля можно instance-ровать различные реализации одного и
        того же bean-a, а также присваивать различные значения свойствам
        приложения.
        */
        @Profile("prod")
        public UserRepository userRepositoryTwo(@Qualifier("pool2") StarterConnectionPool pool2) {
                return new UserRepository(pool2);
        }

        /*
        Демонстрация еще одного способа внедрения одного bean-a в другой внутри одного
        config класса:
        - у нас есть poolThree();
        - у нас есть userRepositoryThree();
        - внедрение происходит сразу в возвращаемый bean - new UserRepository(poolThree());
        */
        @Bean
        public StarterConnectionPool poolThree() {
                return new StarterConnectionPool("test-pool", 25);
        }

        @Bean
        public UserRepository userRepositoryThree() {
                /*
                Мы даже можем сделать так и ссылка каждой из переменных будет
                вести на один и тот же singleton bean. Поскольку при текущих
                настройках @Configuration тут используется CGLib proxy, т.к.
                по-умолчанию @Configuration(proxyBeanMethods = true).

                Как только мы поставим false - @Configuration(proxyBeanMethods = false)
                такое внедрение, что мы тут применили не будет возможным. Это легко
                проверить установив данный параметр.

                Т.е. данный метод внедрения зависимостей будет работать только при
                @Configuration(proxyBeanMethods = true) или просто @Configuration.
                */
                StarterConnectionPool connectionPool1 = poolThree();
                StarterConnectionPool connectionPool2 = poolThree();
                StarterConnectionPool connectionPool3 = poolThree();

                return new UserRepository(poolThree());
        }
}
