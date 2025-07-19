См. исходники и дополнения (RUS): https://struchkov.dev/blog/ru/create-spring-boot-starter/
________________________________________________________________________________________________________________________
### Создаем свой Spring Boot Starter

Один из мощных механизмов Spring Boot — возможность использования "стартеров" для быстрой настройки нового проекта с
предварительно сконфигурированными зависимостями. Стартеры Spring Boot — это, предварительно упакованные наборы
зависимостей и сконфигурированных бинов для обеспечения определённой функциональности. Например, доступа к базе данных
или безопасности.

Возьмём мою библиотеку [GodFather Telegram](https://github.com/Godfather-Bots), которая позволяет создавать ботов для Telegram. Без стартера вам пришлось бы
создать 15-ть и более bean-ов:
- бин для принятия входящих событий от телеграма;
- бин для отправки сообщений;
- бин для построения сценария бота;
и множество инфраструктурных бинов...

Без подробной документации не обойтись.

Стартер даёт возможность разработчику библиотеки произвести начальную конфигурацию и определить инфраструктурные бины.
А пользователю библиотеки остаётся подключить зависимость и начинать писать бизнес-код, не думая о том, как получить
сообщение от Телеграма, или как его отправить пользователю.

Все эти бины сконфигурированы мной, как разработчиком стартера.

У клиентов стартера остаётся возможность переопределить внутренние бины, если сценарии использования выходят за рамки
дефолтных настроек. Также разработчик стартера может вынести множество настроек в файл application.properties.

В этой статье мы рассмотрим, как создать собственный стартер Spring Boot. Обсудим некоторые лучшие практики и советы по
созданию.

Демо проект: [Godfather-Bots/telegram-bot-spring-boot-starter](https://git.struchkov.dev/Godfather-Bots/telegram-bot-spring-boot-starter);

________________________________________________________________________________________________________________________
#### Создаем свой стартер

- Шаг 1. - Написание стартера будем рассматривать на примере моей библиотеки для создания ботов GodFather Telegram.
Для начала определимся с groupId и artifactId. Все официальные стартеры придерживаются следующей схемы именования:
- spring-boot-starter-*, где, * - это конкретный тип приложения.

Сторонние стартеры не должны начинаться с spring-boot, поскольку они зарезервированы для официальных стартеров от
разработчиков Spring. Сторонний стартер обычно начинается с названия проекта. Например, мой стартер называется:
telegram-bot-spring-boot-starter.

Можно реализовать стартер как дополнительный maven-модуль основного проекта, или как отдельный проект. Я предпочитаю
делать отдельный проект. Поэтому создаём пустой SpringBoot проект. Убедитесь, что в pom.xml присутствуют следующие
зависимости:

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

Добавьте зависимости вашей библиотеки. В моём случае их три:

        <dependency>
            <groupId>dev.struchkov.godfather.telegram</groupId>
            <artifactId>telegram-consumer-simple</artifactId>
            <version>${telegram.bot.version}</version>
        </dependency>
        <dependency>
            <groupId>dev.struchkov.godfather.telegram</groupId>
            <artifactId>telegram-core-simple</artifactId>
            <version>${telegram.bot.version}</version>
        </dependency>
        <dependency>
            <groupId>dev.struchkov.godfather.telegram</groupId>
            <artifactId>telegram-sender-simple</artifactId>
            <version>${telegram.bot.version}</version>
        </dependency>

- Шаг 2. - Теперь создадим обычный класс конфигурации Spring с нужными бинами:

        @Configuration
        public class TelegramBotAutoconfiguration {
        
            @Bean(AUTORESPONDER_EXECUTORS_SERVICE)
            public ExecutorService executorService(
                    TelegramBotAutoresponderProperty autoresponderProperty
            ) {
                return Executors.newFixedThreadPool(autoresponderProperty.getThreads());
            }
        
            ... ... ... ... ...
        
            @Bean
            public TelegramService telegramService(TelegramConnect telegramConnect) {
                return new TelegramServiceImpl(telegramConnect);
            }
        
        }

Смысла описывать всю конфигурацию нет, это просто бины необходимые для работы библиотеки.

Пока это ничем не примечательный модуль с обычным классом конфигурации, что делает его стартером? Стартером его сделает
особый файл, создадим его.

- Шаг 3. - В папке resources создаём папку META-INF, в ней папку spring, и в ней файл
org.springframework.boot.autoconfigure.AutoConfiguration.imports см. 

![META_INF.SPRING.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/DOC/CustomSpringBootStarter/image/META_INF.SPRING.jpg)

Этот файл должен содержать перечисление конфигураций. В моём случае это одна конфигурация. Каждую конфигурацию указывайте
с новой строки.

        dev.struchkov.godfather.telegram.starter.config.TelegramBotAutoconfiguration

________________________________________________________________________________________________________________________
!!! Особенности !!! Если вы используете Spring Boot 2.7 и ниже, то необходимо создать папку META-INF и в ней файл
                    spring.factories.

           Подробнее об этом читайте в "Spring Boot 3.0 Migration Guide" -
           https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide#auto-configuration-files
________________________________________________________________________________________________________________________

SpringBoot автоматически найдёт этот файл, возьмёт перечисленные конфигурации и создаст указанные в них бины, добавив их
в контекст. Так класс конфигурации превращается в класс автоконфигурации. Вот и вся магия.

________________________________________________________________________________________________________________________
#### Классы свойств

- Шаг 4. - Если вашей библиотеке требуются какие-то конфигурационные переменные, стоит позволить передавать их через
application.yml. Например, в моей библиотеке это информация данные для подключения к Telegram: токен и имя бота. Для
этого создайте класс и аннотируйте его @ConfigurationProperties или добавьте существующий через конфигурацию:

        @Configuration
        public class TelegramBotPropertyConfiguration {
        
            @Bean
            @ConfigurationProperties("telegram.bot")
            public TelegramBotConfig telegramConfig() {
                return new TelegramBotConfig();
            }
        
        }

- Шаг 5. - В pom.xml добавим зависимость, которая генерирует метаданные о классах приложения, аннотированных
@ConfigurationProperties.

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

Этот процессор аннотаций создаст файл META-INF/spring-configuration-metadata.json, который содержит метаданные о
параметрах конфигурации в классе TelegramBotConfig. Эти метаданные включают Javadoc о полях, поэтому убедитесь,
что Javadoc понятен.

В Idea плагин Spring Assistant будет читать метаданные и обеспечивать подсказки для этих свойств:
![IDEA_Screen_ConfigFile.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/DOC/CustomSpringBootStarter/image/IDEA_Screen_ConfigFile.jpg)

Также мы можем добавить некоторые свойства вручную, создав файл META-INF/additional-spring-configuration-metadata.json:

        {
          "properties": [
            {
              "name": "telegram.bot.enable",
              "type": "java.lang.Boolean",
              "description": "Enables or disables Telegram Bot."
            }
          ]
        }

Процессор аннотаций автоматически объединит содержимое этого файла с автоматически созданным файлом см.
![Display_config.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/DOC/CustomSpringBootStarter/image/Display_config.jpg).

________________________________________________________________________________________________________________________
#### Conditionals

- Шаг 6. - Добавим больше вариаций конфигураций стартеру. На данный момент при добавлении стартера поднимаются все бины.
Но чаще всего нужны конкретные бины в зависимости от уже имеющихся бинов или указанных значений свойств (property).
Например, в моем случае нет смысла поднимать бины, если пользователь не указал переменные подключения к боту в
application.yml, потому что всё завязано на них.

Для решения этой проблемы существуют аннотации @Conditional...

________________________________________________________________________________________________________________________
#### @ConditionalOnProperty

- Шаг 6.1. - Воспользуемся аннотацией @ConditionalOnProperty, которая проверяет наличие заполнения конкретных свойств в
application.yml.

        @Bean
        @ConfigurationProperties("telegram.bot")
        @ConditionalOnProperty(prefix = "telegram.bot",
                               name = "token")
        public TelegramBotConfig telegramConfig() {
            return new TelegramBotConfig();
        }

Бин TelegramBotConfig будет создаваться, только если указаны property - telegram.bot.token. Используя параметр аннотации
value, можно указать конкретные значения property. В моем случае важно само наличие токена.

________________________________________________________________________________________________________________________
#### @ConditionalOnBean

- Шаг 6.2. - Также воспользуемся @ConditionalOnBean, которая создаёт бин, когда в BeanFactory присутствует указанный в
@ConditionalOnBean бин.

        @Bean
        @ConditionalOnBean(TelegramBotConfig.class)
        public TelegramDefaultConnect telegramDefaultConnect(TelegramBotConfig telegramConfig) {
            return new TelegramDefaultConnect(telegramConfig);
        }

@ConditionalOnMissingBean - позволяет создавать бин, если указанный бин отсутствует в BeanFactory.

________________________________________________________________________________________________________________________
#### @ConditionalOnClass

Создаёт бин, только если указанный класс есть в classpath. И противоположный ему @ConditionalOnMissingClass.

________________________________________________________________________________________________________________________
#### Несколько классов авто-конфигурации

При добавлении нескольких классов автоконфигурации и использовании аннотаций @ConditionalOnBean вы столкнётесь с
неожиданным поведением — условие не будет отрабатывать и бин не будет создаваться. Это происходит, потому что Spring
берёт конфигурации в произвольном порядке, и может сначала попробовать поднять бин помеченный @ConditionalOnBean,
хотя бин в условии будет создан позже.

Порядок классов автоконфигураций в файле никак не влияет на порядок создания бинов.

!!! Чтобы этого избежать, используйте аннотации @AutoConfigureAfter или @AutoConfigureBefore над классом конфигурации,
которые явно задают порядок создания бинов !!!

________________________________________________________________________________________________________________________
#### Опциональные бины

Еще одним полезным классом является ObjectProvider, который позволяет передавать необязательные бины. Похоже на логику
работы Optional.

        @Bean
        @ConfigurationProperties("telegram.bot")
        @ConditionalOnProperty(prefix = "telegram.bot",
                               name = "token")
        public TelegramBotConfig telegramConfig(ObjectProvider<ProxyConfig> proxyConfigProvider) {
        
            final TelegramBotConfig telegramBotConfig = new TelegramBotConfig();
        
            final ProxyConfig proxyConfig = proxyConfigProvider.getIfAvailable();
            if (proxyConfig != null) {
                telegramBotConfig.setProxyConfig(proxyConfig);
            }
        
            return telegramBotConfig;
        }

Бин ProxyConfig является опциональным. Если его не будет, мы всё равно хотим создать TelegramBotConfig. Если не
использовать ObjectProvider, то приложение упадёт при старте, если бина ProxyConfig не будет.

________________________________________________________________________________________________________________________
#### Бины по умолчанию

Мы не можем позволить пользователю переопределять наши бины, но предоставлять дефолтные бины. Для этого воспользуемся
аннотацией @ConditionalOnMissingBean

        @Bean
        @ConditionalOnMissingBean(PersonSettingRepository.class)
        public PersonSettingRepository personSettingRepository() {
            return new PersonSettingLocalRepository();
        }

Обратите внимание, что в @ConditionalOnMissingBean мы указываем тот же класс, что и возвращаем. Таким образом, если
пользователь стартера определит бин PersonSettingRepository, то дефолтный бин из авто-конфигурации не будет создан.

________________________________________________________________________________________________________________________
#### Улучшаем время запуска

- Шаг 6.3. - Классы конфигурации тоже можно аннотировать @Conditional.... И для каждого класса авто-конфигурации Spring
Boot оценивает условия, указанные в аннотации @Conditional..., чтобы решить, загружать ли авто-конфигурацию и все
необходимые в ней бины. В зависимости от размера, и количества стартеров, в приложении Spring Boot, это может быть очень
дорогой операцией и повлиять на время запуска.

Существует ещё один процессор аннотаций, который генерирует метаданные об условиях всех авто-конфигураций. Spring Boot
считывает эти метаданные во время запуска и отфильтровывает конфигурации, условия которых не выполняются, без
необходимости проверять эти классы.

Чтобы метаданные генерировались, нужно добавить процессор аннотаций в стартовый модуль:

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure-processor</artifactId>
            <optional>true</optional>
        </dependency>

Во время сборки метаданные будут сгенерированы в файл META-INF/spring-autoconfigure-metadata.properties, который будет
выглядеть примерно так:

        dev.struchkov.godfather.telegram.starter.config.TelegramBotAutoconfiguration=
        dev.struchkov.godfather.telegram.starter.config.TelegramBotAutoconfiguration.AutoConfigureAfter=
                                                    dev.struchkov.godfather.telegram.starter.config.TelegramBotDataConfiguration
        dev.struchkov.godfather.telegram.starter.config.TelegramBotAutoconfiguration.ConditionalOnBean=
                                                                 dev.struchkov.godfather.telegram.simple.core.TelegramConnectBot
        dev.struchkov.godfather.telegram.starter.config.TelegramBotDataConfiguration=
        dev.struchkov.godfather.telegram.starter.config.TelegramBotDataConfiguration.AutoConfigureAfter=
                                                dev.struchkov.godfather.telegram.starter.config.TelegramBotPropertyConfiguration

________________________________________________________________________________________________________________________
#### Использование стартера

- Шаг 7. - Теперь, когда стартер готов, мы можем его добавить в наше другое приложение:

        <dependency>
            <groupId>dev.struchkov.godfather.telegram</groupId>
            <artifactId>telegram-bot-spring-boot-starter</artifactId>
            <version>${godfather.telegram.version}</version>
        </dependency>

Теперь нам доступны все сконфигурированные бины.

________________________________________________________________________________________________________________________
#### ИТОГ

Выделить определённые функции в модуль стартер, чтобы использовать их в любом приложении Spring Boot, дело нескольких
простых шагов:
- Шаг 1. - Создайте автоконфигурацию.
- Шаг 2. - Сделайте её настраиваемой.
- Шаг 3. - Добавьте процессоры аннотаций, чтобы автоматически генерировать метаданные для повышения производительности
           и удобства использования.

________________________________________________________________________________________________________________________
#### Документация

- [Spring Core Features](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html)
- [Annotation Interface @Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html)
- [Annotation Interface @Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)
- [Spring Boot 3.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Annotation Interface @ConfigurationProperties](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/context/properties/ConfigurationProperties.html)
- [Annotation Interface @ConditionalOnProperty](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.html)
- [Annotation Interface @ConditionalOnBean](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnBean.html)
- [Annotation Interface @ConditionalOnMissingBean](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnMissingBean.html)
- [Annotation Interface @ConditionalOnClass](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnClass.html)
- [Annotation Interface @AutoConfigureAfter](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/AutoConfigureAfter.html)
- [Annotation Interface @AutoConfigureBefore](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/AutoConfigureBefore.html)
- [Class ProxyConfig](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/framework/ProxyConfig.html)
- [Interface ObjectProvider<T>](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/ObjectProvider.html)