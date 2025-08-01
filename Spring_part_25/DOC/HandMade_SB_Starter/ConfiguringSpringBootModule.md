См. исходники и дополнения (ENG): https://reflectoring.io/spring-boot-starter/
________________________________________________________________________________________________________________________
### Настройка модуля Spring Boot с помощью @ConfigurationProperties

Каждому приложению, определенного размера, при запуске требуются некоторые параметры. Эти параметры могут, например,
определять, к какой базе данных подключаться, какую локаль поддерживать или какой уровень ведения журнала применять.

Эти параметры должны быть внешними - https://reflectoring.io/externalize-configuration/, то есть нам не следует
включать их в развертываемый артефакт, а вместо этого предоставлять их в качестве аргумента командной строки или
файла конфигурации при запуске приложения.

Благодаря @ConfigurationProperties аннотации Spring boot предоставляет удобный способ доступа к таким параметрам из
кода приложения.

В этом руководстве подробно рассматриваются эта аннотация и показано, как использовать ее для настройки модуля
приложения Spring Boot.

Пример [кода на GitHub](https://github.com/thombergs/code-examples/tree/master/spring-boot/configuration)

________________________________________________________________________________________________________________________
#### Использование @ConfigurationProperties для настройки модуля

Представьте, что мы создаем в нашем приложении модуль - https://reflectoring.io/spring-boot-modules/, отвечающий за
отправку электронных писем. В локальных тестах мы не хотим, чтобы модуль фактически отправлял электронные письма,
поэтому нам нужен параметр, чтобы отключить эту функцию. Кроме того, мы хотим иметь возможность настраивать тему по
умолчанию для этих писем, чтобы мы могли быстро идентифицировать электронные письма в нашем почтовом ящике, отправленные
из тестовой среды.

Spring Boot предлагает множество различных вариантов передачи [подобных параметров в приложение](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config).

В этой статье мы решили создать application.properties файл с нужными нам параметрами:

      myapp.mail.enabled=true
      myapp.mail.default-subject=This is a Test

В нашем приложении мы теперь могли получить доступ к значениям этих свойств, [среди прочего, запрашивая Environment](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/Environment.html)
bean-компонент Spring или [используя аннотацию @Value](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Value.html).

Однако, есть более удобный и безопасный способ получить доступ к этим свойствам, создав класс с аннотацией
@ConfigurationProperties:

      @ConfigurationProperties(prefix = "myapp.mail")
      class MailModuleProperties {
      
        private Boolean enabled = Boolean.TRUE;
        private String defaultSubject;
      
        // getters / setters
      
      }

Основное использование @ConfigurationProperties довольно просто: мы предоставляем класс с полями для каждого внешнего
свойства, которое мы хотим захватить.

Обратите внимание на следующее:
- Определяет prefix, какие внешние свойства будут привязаны к полям класса.
- Имена свойств классов должны совпадать с именами внешних свойств в соответствии с правилами смягченной привязки
  Spring Boot.
- Мы можем определить значения по умолчанию, просто инициализируя поле значением.
- Сам класс может быть приватным для пакета.
- Поля классов должны иметь общедоступные установщики.

Если мы внедрим bean-компонент данного типа MailModuleProperties в другой bean-компонент, этот bean-компонент теперь
сможет получить доступ к значениям этих внешних параметров конфигурации типобезопасным способом. Однако, нам все равно
нужно @ConfigurationProperties сообщить Spring о нашем классе, чтобы он был загружен в контекст приложения.

________________________________________________________________________________________________________________________
#### Активация @ConfigurationProperties

Чтобы Spring Boot создал bean-компонент класса MailModuleProperties, нам необходимо добавить его в контекст приложения
одним из нескольких способов.

- Во-первых, мы можем просто сделать это частью сканирования компонента, добавив @Component аннотацию :

      @Component
      @ConfigurationProperties(prefix = "myapp.mail")
      class MailModuleProperties {
        // ...
      }

Очевидно, это работает только в том случае, если класс внутри пакета сканируется на наличие стереотипных аннотаций
Spring через @ComponentScan, которым по умолчанию является любой класс в структуре пакета ниже основного класса
приложения.

- Во-вторых, мы можем добиться того же результата, используя функцию конфигурации Java Spring :

      @Configuration
      class MailModuleConfiguration {
      
        @Bean
        public MailModuleProperties mailModuleProperties(){
          return new MailModuleProperties();
        }
      
      }

Пока класс MailModuleConfiguration сканируется приложением Spring Boot, у нас будет доступ к MailModuleProperties
bean-компоненту в контексте приложения.

- В-третьих, в качестве альтернативы мы можем использовать аннотацию @EnableConfigurationProperties, чтобы сделать наш
класс известным Spring Boot:

      @Configuration
      @EnableConfigurationProperties(MailModuleProperties.class)
      class MailModuleConfiguration {
      
      }

________________________________________________________________________________________________________________________
#### Как лучше всего активировать @ConfigurationProperties класс?

Все вышеперечисленные способы одинаково действительны. Однако я бы предложил сделать ваше приложение модульным и
предоставить каждому модулю собственный @ConfigurationProperties класс только с теми свойствами, которые ему
необходимы, как мы сделали для почтового модуля в приведенном выше коде. Это позволяет легко реорганизовать свойства в
одном модуле, не затрагивая другие модули.

По этой причине я бы не рекомендовал использовать @EnableConfigurationProperties в самом классе приложения, как
показано во многих других руководствах, а вместо этого использовать в @Configuration классе, специфичном для модуля,
который также может использовать видимость, частную для пакета, чтобы скрыть свойства от остальной части приложения.

________________________________________________________________________________________________________________________
#### Ошибка при работе с неконвертируемыми свойствами

Что произойдет, если мы определим свойство, application.properties которое невозможно правильно интерпретировать?
Скажем, мы предоставляем значение 'foo' нашего enabled свойства, которое ожидает логическое значение:

      myapp.mail.enabled=foo

По умолчанию Spring Boot откажется запускать приложение выкинув исключение:

      java.lang.IllegalArgumentException: Invalid boolean value 'foo'

Если по какой-либо причине мы не хотим, чтобы Spring Boot завершался сбоем в подобных случаях, мы можем установить
ignoreInvalidFields для параметра значение true (по умолчанию false):

      @ConfigurationProperties(prefix = "myapp.mail",
                               ignoreInvalidFields = true)
      class MailModuleProperties {
      
        private Boolean enabled = Boolean.TRUE;
      
        // getters / setters
      }

В этом случае Spring Boot установит для этого enabled поля значение по умолчанию, которое мы определили в коде Java.
Если мы не инициализируем поле в коде Java, оно будет null.

________________________________________________________________________________________________________________________
#### Сбой в неизвестных свойствах

Что произойдет, если мы предоставим в нашем application.properties файле определенные свойства, о которых наш
MailModuleProperties класс не знает?

      myapp.mail.enabled=true
      myapp.mail.default-subject=This is a Test
      myapp.mail.unknown-property=foo

По умолчанию Spring Boot просто игнорирует свойства, которые не могут быть привязаны к полю в @ConfigurationProperties
классе. Однако, мы можем захотеть прервать запуск, если в файле конфигурации есть свойство, которое фактически не
привязано к @ConfigurationProperties классу. Возможно, мы ранее использовали это свойство конфигурации, но с тех пор
оно было удалено, поэтому мы хотим, чтобы его application.properties также удалили из файла.

Если мы хотим, чтобы запуск завершался с ошибкой из-за неизвестных свойств, мы можем просто установить
ignoreUnknownFields параметр false (по умолчанию true):

      @ConfigurationProperties(prefix = "myapp.mail",
                               ignoreUnknownFields = false)
      class MailModuleProperties {
      
        private Boolean enabled = Boolean.TRUE;
        private String defaultSubject;
      
        // getters / setters
      }

Теперь при запуске приложения мы получим исключение, которое сообщает нам, что определенное свойство не может быть
привязано к полю в нашем MailModuleProperties классе, поскольку не было соответствующего поля:

      org.springframework.boot.context.properties.bind.UnboundConfigurationPropertiesException:
                                                                 The elements [myapp.mail.unknown-property] were left unbound.

________________________________________________________________________________________________________________________
#### Предупреждение об устаревании

Параметр ignoreUnknownFields станет устаревшим (https://github.com/spring-projects/spring-boot/issues/12601) в будущей
версии Spring Boot. Причина в том, что мы можем иметь два @ConfigurationProperties класса, привязанных к одному и тому
же пространству имен. Свойство может быть известно одному из этих классов и неизвестно другому, что приведет к сбою при
запуске, хотя у нас есть две совершенно допустимые конфигурации.

________________________________________________________________________________________________________________________
#### Проверка @ConfigurationProperties при запуске

Если мы хотим убедиться, что параметры, передаваемые в приложение параметрами конфигурации, действительны, мы можем
добавить аннотации проверки bean-компонента (https://reflectoring.io/bean-validation-with-spring-boot/) в поля и
@Validated аннотацию к самому классу:

      @ConfigurationProperties(prefix = "myapp.mail")
      @Validated
      class MailModuleProperties {
      
        @NotNull private Boolean enabled = Boolean.TRUE;
        @NotEmpty private String defaultSubject;
      
        // getters / setters
      }

Если мы сейчас забудем установить enabled свойство в нашем application.properties файле и оставим пустым, при запуске
defaultSubject мы получим: BindValidationException

      myapp.mail.default-subject=
        org.springframework.boot.context.properties.bind.validation.BindValidationException:
          Binding validation errors on myapp.mail
           - Field error in object 'myapp.mail' on field 'enabled': rejected value [null]; ...
           - Field error in object 'myapp.mail' on field 'defaultSubject': rejected value []; ...

Если нам нужна проверка, которая не поддерживается аннотациями проверки bean-компонента по умолчанию, мы можем создать
собственную аннотацию проверки bean-компонента см.
https://reflectoring.io/bean-validation-with-spring-boot/#implementing-a-custom-validator

И если наша логика проверки слишком специфична для проверки bean-компонента, мы можем реализовать ее в методе,
аннотированном @PostConstruct, который выдает исключение в случае неудачи проверки.

________________________________________________________________________________________________________________________
#### Свойства сложных типов (Complex Property Types)

Большинство параметров, которые мы хотим передать в наше приложение, представляют собой примитивные строки или числа.
Однако, в некоторых случаях у нас есть параметр, который мы хотели бы привязать к полю в нашем @ConfigurationProperty
классе, имеющему сложный тип данных, например список.

________________________________________________________________________________________________________________________
#### Списки и наборы (Lists and Sets)

Представьте, что нам нужно предоставить список SMTP-серверов нашему почтовому модулю. Мы можем просто добавить List поле
в наш MailModuleProperties класс:

        @ConfigurationProperties(prefix = "myapp.mail")
        class MailModuleProperties {
        
          private List<String> smtpServers;
        
          // getters / setters
        
        }

Spring Boot автоматически заполняет этот список, если мы используем в нашем файле application.properties обозначение
массива:

      myapp.mail.smtpServers[0]=server1
      myapp.mail.smtpServers[1]=server2

YAML имеет встроенную поддержку типов списков, поэтому, если мы application.yml вместо этого используем файл
конфигурации, мы будем лучше читать его для нас, людей:

      myapp:
        mail:
          smtp-servers:
            - server1
            - server2

Таким же образом мы можем привязать параметры к Set полям.

________________________________________________________________________________________________________________________
#### Durations

Spring Boot имеет встроенную поддержку анализа продолжительности из параметра конфигурации:

      @ConfigurationProperties(prefix = "myapp.mail")
      class MailModuleProperties {
      
        private Duration pauseBetweenMails;
      
        // getters / setters
      
      }

Эта продолжительность может быть указана либо в виде длинного значения, обозначающего миллисекунды, либо в текстовом,
удобочитаемом виде, включающем единицы измерения (одну из ns, us, ms, s, m, h, d):

      myapp.mail.pause-between-mails=5s

________________________________________________________________________________________________________________________
#### Размеры файлов

Аналогичным образом мы можем предоставить параметры конфигурации, определяющие размер файла см.
https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config :

        @ConfigurationProperties(prefix = "myapp.mail")
        class MailModuleProperties {
        
          private DataSize maxAttachmentSize;
        
          // getters / setters
        
        }

Тип DataSize предоставляется самой Spring Framework. Теперь мы можем указать параметр конфигурации размера файла как
длинный, чтобы указать количество байтов, или с единицей измерения (одной из B, KB, MB, GB, TB):

      myapp.mail.max-attachment-size=1MB

________________________________________________________________________________________________________________________
#### Пользовательские типы

В редких случаях нам может потребоваться проанализировать (спарсить) параметр конфигурации в объекте пользовательского
значения. Представьте, что мы хотим указать (гипотетический) максимальный вес вложения для электронного письма:

      myapp.mail.max-attachment-weight=5kg

Мы хотим привязать это свойство к полю нашего пользовательского типа Weight:

      @ConfigurationProperties(prefix = "myapp.mail")
      class MailModuleProperties {
      
        private Weight maxAttachmentWeight;
      
        // getters / setters
      }

Есть два облегченных варианта, позволяющих Spring Boot автоматически анализировать String ( '5kg') в объект типа Weight:
- класс Weight предоставляет конструктор, который принимает ('5kg') в качестве аргумента;
- класс Weight предоставляет статический метод valueOf, который принимает одну строку в качестве аргумента и возвращает
  Weight объект.

Если мы не можем предоставить конструктор или valueOf метод, мы вынуждены использовать немного более агрессивный вариант
создания собственного конвертера :

      class WeightConverter implements Converter<String, Weight> {
      
        @Override
        public Weight convert(String source) {
          // create and return a Weight object from the String
        }
      
      }

После того как мы создали наш конвертер, мы должны сообщить о нем Spring Boot:

      @Configuration
      class MailModuleConfiguration {
      
        @Bean
        @ConfigurationPropertiesBinding
        public WeightConverter weightConverter() {
          return new WeightConverter();
        }
      
      }

Важно добавить @ConfigurationPropertiesBinding аннотацию, чтобы сообщить Spring Boot, что этот преобразователь необходим
во время привязки свойств конфигурации.

________________________________________________________________________________________________________________________
#### Вложения электронной почты с весом?

Очевидно, что электронные письма не могут иметь «настоящие» вложения с весом. Я прекрасно это знаю. Однако мне было
сложно придумать пример для пользовательского типа конфигурации, поскольку это действительно редкий случай.

________________________________________________________________________________________________________________________
#### Использование процессора конфигурации Spring Boot для автозаполнения

Вам когда-нибудь хотелось иметь автозаполнение любого из встроенных параметров конфигурации Spring Boot? Или ваши
собственные свойства конфигурации? Spring Boot предоставляет процессор конфигурации, который собирает данные из всех
@ConfigurationProperties аннотаций, которые он находит в пути к классам, для создания файла JSON с некоторыми
метаданными.

IDE могут использовать этот файл JSON для предоставления таких функций, как автозаполнение. Все, что нам нужно сделать,
это добавить зависимость от процессора конфигурации в наш проект (нотация Gradle):

      dependencies {
        ...
        annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
      }

Когда мы создаем наш проект, процессор конфигурации теперь создает файл JSON, который выглядит примерно так:

      {
       "groups": [
         {
           "name": "myapp.mail",
           "type": "io.reflectoring.configuration.mail.MailModuleProperties",
           "sourceType": "io.reflectoring.configuration.mail.MailModuleProperties"
         }
       ],
       "properties": [
         {
           "name": "myapp.mail.enabled",
           "type": "java.lang.Boolean",
           "sourceType": "io.reflectoring.configuration.mail.MailModuleProperties",
           "defaultValue": true
         },
         {
           "name": "myapp.mail.default-subject",
           "type": "java.lang.String",
           "sourceType": "io.reflectoring.configuration.mail.MailModuleProperties"
         }
       ],
       "hints": []
      }

________________________________________________________________________________________________________________________
#### IntelliJ

Чтобы получить автозаполнение в IntelliJ, мы просто устанавливаем плагин Spring Assistant -
https://plugins.jetbrains.com/plugin/10229-spring-assistant. Если мы теперь нажмем CMD+Пробел в файле
application.properties или application.yml, мы получим всплывающее окно автозаполнения см.
![AutoCompletionInIntelliJ.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/DOC/HandMade_SB_Starter/pic/AutoCompletionInIntelliJ.jpg)

________________________________________________________________________________________________________________________
#### Пометка свойства конфигурации как устаревшего

Приятной особенностью процессора конфигурации является то, что он позволяет нам помечать свойства как устаревшие:

      @ConfigurationProperties(prefix = "myapp.mail")
      class MailModuleProperties {
      
        private String defaultSubject;
      
        @DeprecatedConfigurationProperty(
            reason = "not needed anymore",
            replacement = "none")
        public String getDefaultSubject(){
          return this.defaultSubject;
        }
      
        // setter
      
      }

Мы можем просто добавить @DeprecatedConfigurationProperty аннотацию в поле нашего @ConfigurationProperties класса, и
процессор конфигурации включит информацию об устаревании в метаданные:

      ...
      {
        "name": "myapp.mail.default-subject",
        "type": "java.lang.String",
        "sourceType": "io.reflectoring.configuration.mail.MailModuleProperties",
        "deprecated": true,
        "deprecation": {
          "reason": "not needed anymore",
          "replacement": "none"
        }
      }
      ...

Эта информация затем предоставляется нам при вводе в файл свойств (в данном случае IntelliJ) см.
![DeprecatedPropertyInIntelliJ.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_25/DOC/HandMade_SB_Starter/pic/DeprecatedPropertyInIntelliJ.jpg)

________________________________________________________________________________________________________________________
#### Заключение

Аннотация Spring Boot @ConfigurationProperties — это мощный инструмент для привязки параметров конфигурации к
типобезопасным полям в Java-компоненте.

Вместо того чтобы просто создавать один конфигурационный bean-компонент для нашего приложения, мы можем
воспользоваться этой функцией для создания отдельного конфигурационного bean-компонента для каждого из наших
модулей, что дает нам возможность развивать каждый модуль отдельно не только в коде, но и в конфигурации.