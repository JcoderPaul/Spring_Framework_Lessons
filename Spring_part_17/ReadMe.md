### Spring Boot lessons part 17 - Thymeleaf Starter

В [папке DOC sql-скрипты](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/DOC) и др. полезные файлы.

Док. (ссылки) для изучения:
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) ;
- [Spring Framework 6.1.5 Documentation](https://spring.io/projects/spring-framework) ;
- [Spring Framework 3.2.x Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/index.html) ;
- [Getting Started Guides](https://spring.io/guides) ;
- [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html) ;

---------------------------------------------------------------------------------------------------------------
Для начала проведем предварительную подготовку (первые 6-ть шагов из предыдущих частей, некоторые пропущены):

Шаг 1. - в файле [build.gradle](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/build.gradle) добавим необходимые plugin-ы: 

    /* 
       Плагин Spring Boot добавляет необходимые задачи в Gradle 
       и имеет обширную взаимосвязь с другими plugin-ами.
    */
    id 'org.springframework.boot' version '3.1.3'
    /* 
       Менеджер зависимостей позволяет решать проблемы несовместимости 
       различных версий и модулей Spring-а
    */
    id "io.spring.dependency-management" version '1.0.11.RELEASE'
    /* Подключим Lombok */
    id "io.freefair.lombok" version "8.3"

Шаг 2. - подключаем Spring Boot starter:

    /* 
       Подключим Spring Boot Starter он включает поддержку 
       авто-конфигурации, логирование и YAML
    */
    implementation 'org.springframework.boot:spring-boot-starter'

Шаг 3. - подключаем блок тестирования (Spring Boot Starter Test) 
(он будет активен на этапе тестирования):

    testImplementation 'org.springframework.boot:spring-boot-starter-test'

Шаг 4. - автоматически Gradle создал тестовую зависимость на Junit5
(мы можем использовать как Junit4, так и TestNG):

    test {
        useJUnitPlatform()
    }

Шаг 5. - подключим блок для работы с БД (Spring Boot Starter Data Jpa):

    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

Для работы с PostgreSQL подключим и его зависимости:

    implementation 'org.postgresql:postgresql'

Шаг 6. - Для использования средств подобных Hibernate ENVERS:

    implementation 'org.springframework.data:spring-data-envers'

Шаг 7. - Подключим миграционный фреймворк Liquibase:

    implementation 'org.liquibase:liquibase-core'

Шаг 8. - Подключаем Wed - Starter:

    implementation 'org.springframework.boot:spring-boot-starter-web'

Шаг 9. - Поскольку сейчас мы начинаем изучение Thymeleaf, то нам нужно его подключить:

    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'

Хотя мы уже не используем явно *.JSP страницы, мы все же оставим зависимость Jasper-a:

    implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'

---------------------------------------------------------------------------------------------------------------
Template engine (Механизм (движок) шаблонов, шаблонизатор) - используются, когда мы хотим быстро создавать 
веб-приложения, разделенные на различные компоненты. Шаблоны также позволяют быстро отображать серверные данные, 
которые необходимо передать приложению.

Например, мы хотим иметь (отобразить) на странице пользователя такие компоненты, как тело, навигация, нижний колонтитул, 
панель мониторинга и т. д. Тут нам и помогут шаблонизаторы.

Механизмы шаблонов в основном используются для серверных приложений, которые запускаются только на одном сервере и не 
созданы как API. Известные шаблонизаторы: Ejs, Jade, Pug, Mustache, HandlebarsJS, Jinja2, Blade, [Thymeleaf для Spring](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/DOC/ThymeleafManual) 
приложений и т.д.

Когда мы создаем серверное приложение с помощью механизма шаблонов, механизм шаблонов заменяет переменные в файле 
шаблона фактическими значениями и отображает это значение клиенту. Это упрощает создание нашего web приложения.

Если очень грубо, то процесс использования шаблонизатора можно разделить на 5-ть шагов:
- Шаг 1. Установка или настройка шаблонизатора;
- Шаг 2. Настройка механизма просмотра;
- Шаг 3. Настройка папки просмотра, в которой находятся наши шаблоны. Например, шаблон для генерации главной страницы, 
далее шаблон, который вернет запрошенные данные со стороны сервера для немедленного отображения на веб-странице.
- Шаг 4. Настройка маршрутов, т.е. создание маршрутов для нашей домашней страницы, страницы пользователя и т.д.
- Шаг 5. Настройка шаблонов файлов представления, когда мы передали пользовательские данные со стороны сервера, нам 
нужно сразу же отобразить их в интерфейсе приложения (или на web-странице).

При настройке отображений в нашем приложении мы будем использовать шаблонизатор [Thymeleaf см. DOC/ThymeleafManual](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/DOC/ThymeleafManual)

---------------------------------------------------------------------------------------------------------------
#### Lesson 82 - Подключение Thymeleaf-Starter

[Зависимость мы подключили](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/build.gradle#L22), теперь нужно подготовить папки и файлы отображения. Класс ThymeleafProperties содержит
префикс "spring.thymeleaf" и поля свойств для настройки, которые мы можем использовать по-умолчанию, это место 
хранения файлов отображения и их суффикс, что мы и сделаем.

Создаем в папке ресурсов папку [templates](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/src/main/resources/templates) и переносим туда наши [JSP файлы из папки webapp](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_16/src/main/webapp), а саму папку webapp удаляем, 
т.к. она нам больше не нужна. При этом наши перенесенный файлы отображения меняют свое расширение на *.HTML

Дорабатываем наши HTML файлы, как было указано в [DOC/ThymeleafManual/ThymeleafTutorial/Thymeleaf_3_Usage_Text.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/ThymeleafManual/ThymeleafTutorial/Thymeleaf_3_Usage_Text.txt),
нам нужно в шапке каждого HTML (или в корневом теге <html> страницы) файла проставить атрибут:

    <html lang="en" xmlns:th="http://www.thymeleaf.org">

Что мы и делаем. Данная магическая строка позволяет обращаться к атрибутам Thymeleaf на наших страницах через префикс
или нотацию th:*

Внесем изменения во все файлы HTML сообразно коду специфичному Thymeleaf. Начнем с [/templates/greeting/hello.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/greeting/hello.html).
Все комментарии к изменениям и работе кода под нотацией th:* описаны внутри HTML файлов. В файле 
[resources/templates/greeting/bye.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/greeting/bye.html) нам придется запросить переменную уже из сессионной области видимости, поэтому
мы и обращаемся к ней находясь под нотацией th:text см. файл.

Теперь проверяем, что получилось - запускаем [SpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/SpringAppRunner.java) и в браузере обращаемся к странице Hello.html 
(с переданными параметрами через ?): [http://localhost:8080/api/v1/hello?username=PaulJCoder](http://localhost:8080/api/v1/hello?username=PaulJCoder). 

Видим возвращенный рез.:
    
    Hello PaulJCoder!
    
    Hello PaulJCoder!

Теперь обращаемся к странице [bye.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/greeting/bye.html): [http://localhost:8080/api/v1/bye ](http://localhost:8080/api/v1/bye )

И получаем в ответ:

    Bye PaulJCoder !

---------------------------------------------------------------------------------------------------------------
#### Lesson 83 - CRUD-View-Layer - CRUD операции на уровне отображения. UPDATE (EDIT) данных.

Ниже мы будем создавать некие шаблоны, которые в зависимости от запроса пользователя будут возвращать ему ту или иную 
информацию, при этом все будет проходить динамически (ведь запросы могут быть разными), а вот ответы на запросы будут
придерживаться некоего шаблона отображения. Поскольку мы изучаем и используем [Thymeleaf](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/SpringBoot_and_Thymeleaf.txt), то естественно в наших *.html
файлах будет использоваться [нотация th:...](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/ThymeleafManual/ThymeleafTutorial/Thymeleaf_3_Usage_Text.txt#L34), показывающая где в html коде используется динамическая составляющая.

- Шаг.1 Создадим файл отображающий результат запроса 'найди-всех-user-ов' или *.findAll() из класса [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java), т.е. слой 
контролеров - это будет [resources/templates/user/users.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users.html). Основная его задача вывести список users см. комментарии в 
коде HTML страницы. 

Что бы обратиться к этому отображению запускаем наше приложение - [SpringAppRunner.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/SpringAppRunner.java), предварительно заполнив базу 
данными из - [resources/sql_scripts/data.sql](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/test/resources/sql_scripts/data.sql) и только теперь в браузере вбиваем - [http://localhost:8080/users](http://localhost:8080/users). Результат
на экране, как и ожидалось - список активных ссылок на user-ов состоящих из ID (${user.id}.) + email (${user.username})

В режиме разработчика видно, что наш блок 'div' был в цикле повторен 5-ть раз - количество user-ов в БД см. 
[DOC/WorkingApp/PageUsersDevMode.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/WorkingApp/PageUsersDevMode.jpg). Если мы попытаемся перейти по сформированным ссылкам, то естественно не сможем 
этого сделать, т.к. данного отображения еще нет. 

Сделаем его.

- Шаг.2 Создадим отображение для конкретного пользователя найденного по ID. Можно создать просто страницу с минимальным 
функционалом, но, мы объединим функционал 'простого отображения' и 'формы изменения данных' - view and update. У нас 
есть метод *.update() в [UserController-е](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java) и его 'endpoint' имеет вид '/{id}/update' 
________________________________________________________________________________________________________________________
Endpoint - это конечная точка или адрес веб-сервиса, который определяет, каким образом клиентские приложения могут 
получать доступ к его функциональности. Веб-сервисы используют конечные точки для предоставления информации или 
выполнения операций по запросу клиента. Endpoint может быть сетевым адресом ресурса, таким как URL, или определенной 
командой, которую веб-сервис должен выполнить.

См. полный материал (RUS): [DOC/Endpoint.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/Endpoint.txt)
________________________________________________________________________________________________________________________

Полностью endpoint выглядит как /users/{id}/update, где есть динамическая составляющая {id}, переименуем ее и передадим 
более наглядно, как (userId=${user.id}), при этом он является ссылкой и поэтому все выражение находится в @{...} см. 
[DOC/ThymeleafManual/ThymeleafTutorial/Thymeleaf_4_Standard_Expression_Syntax.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/ThymeleafManual/ThymeleafTutorial/Thymeleaf_4_Standard_Expression_Syntax.txt). У нас классическая форма, но, 
поскольку мы используем Thymeleaf, то должны описать всю динамику в его нотациях:

    <form th:action="@{/users/{userId}/update(userId=${user.id})}" method="post" th:object="${user}">
        <label for="username">Username:
            <input id="username" type="text" name="username" th:value="*{username}">
        </label><br>
        . . .
        <label for="firstname">Firstname:
        <input id="firstname" type="text" name="firstname" th:value="*{firstname}">
        </label><br>

Выражение th:action="@{/users/{userId}/update(userId=${user.id})}" направляет форму к POST запросу /users, в то время 
как выражение th:object="${user} описывает модель объекта для сбора данных. Поля формы, выраженные в th:value="* {id}" 
и т.д. см. [resources/templates/user/user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/user.html), соответствуют полям объекта [user](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/entity/User.java). В данном случае значение в поле
th:value могло иметь вид "{user.id}". Но поскольку в данной форме к полям этого объекта обращаются многократно, мы 
решили оптимизировать код и выделить сам полученный объект в виде th:object="${user}, а обращение к его полям в динамике
запроса-и-вывода-данных использовали синтаксис th:value="*{username}".

Это то, за что отвечает контроллер, модель и представление для отображения формы. Теперь давайте рассмотрим процесс 
отправки формы. Как уже отмечалось выше, форма отправляется на '/users', используя POST. Метод *.update() получает объект 
user, который был заполнен в форме. Затем он добавляет этот объект в модель для того, чтобы отправленные данные могли 
быть отображены в представлении [users.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users.html) строкой. 

    <a th:href="@{'/users/' + ${user.id}}" th:text="|${user.id}. ${user.username}|">User</a>

Имя тега 'label for="username"' должно совпадать с ID тега 'input'. Мы разделили тегами 'label' все поля объекта user, и
соответствующим образом их именовали (согласно имени поля объекта).

________________________________________________________________________________________________________________________
!!! HTML Формы !!!

Для связи поля и его названия используется тег 'label', внутри которого вставляется текст. Чтобы связать 'label' и 
'input' используется один из двух вариантов. Они равнозначны, поэтому можете использовать тот, который удобен в 
конкретной ситуации.

- Связь по идентификатору. Для этого тегу 'input' устанавливается уникальный 'id'. Для тега 'label' устанавливается 
атрибут 'for', значением которого является идентификатор ввода.

        <form>
          <label for="name">Ваше имя</label>
          <input id="name" type="text">
        </form>

- Вложение 'input' внутрь 'label'. Такой способ помогает избавиться от указания идентификаторов, но может немного 
усложнить процесс стилизации.

        <form>
          <label>
            Ваше имя
            <input type="text">
          </label>
        </form>

Важно: все элементы, которые доступны пользователю для заполнения должны иметь тег 'label'. Это элементы 'input' и 
'textarea'. Это справедливо даже в случае визуального отсутствия подписи к полю.
________________________________________________________________________________________________________________________

Две самые интересные части:
- Первая это выбор Role через RadioButton в цикле-отображения внутри тега 'div':

        <div th:each="role : ${roles}">
            <input th:id="${role}" type="radio" name="role" th:value="${role}" th:checked="${role == user.role}">
            <label th:for="${role}" th:text="${role}">Role name</label>
        </div>

- Вторая это список компаний в которых может работать user см. полный код и комментарии в 
[resources/templates/user/user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/user.html):

        <label for="company">Company:
            <select id="company" name="companyId">
                <option th:each="company : ${companies}"
                        <!-- То, что уходит серверу в запросе -->
                        th:value="${company.id()}"
                        th:selected="${company.id() == user.company.id()}"
                        <!-- То, что возвращается пользователю на запрос --> 
                        th:text="${company.name()}">Company
                </option>
            </select>
        </label>

Мы внесли необходимые корректировки в [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java) и [CompanyService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/service/CompanyService.java), чтобы иметь возможность обращаться к нужным 
объектам и извлекать их данные. Все манипуляции по изменениям на каждом слое приложения отдельно.
________________________________________________________________________________________________________________________
!!! Необходимо помнить и перепроверять !!! На данный момент наше приложение, хоть и простое, но уже разбито на слои со 
своей логикой. Приложение связанно с БД и может быть подключено при помощи настроек к разным структурно похожим БД, но 
по-разному наполненным. Т.е. ошибки в работе приложения (даже после удачно пройденных тестов) могут всплывать и 
находится даже там где их вроде и быть не может - на любом слое, как в самом приложении, так и в структуре БД (в ее 
таблицах и записях, отсутствие необходимых данных в конкретном поле таблицы может привести к ошибке, хотя казалось, что 
все записи и поля заполнены).
________________________________________________________________________________________________________________________

- Шаг 3. Проверяем работу наших страниц [user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/user.html) и [users.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users.html), 
запускаем приложение и выбираем одну из записей на стр. [users.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users.html), переходим по ней и попадаем в форму на странице 
[user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/user.html) вносим явные изменения и нажимаем кнопку UPDATE. В базе данных мы можем наблюдать внесенные изменения, в том
числе, в полях modified_at и modified_by:

        Hibernate:
            insert
            into
            users_aud
                (revtype,birth_date,company_id,firstname,lastname,role,username,rev,id)
            values
                (?,?,?,?,?,?,?,?,?)

Значит система аудита, что мы изучали ранее подключена и работает.

---------------------------------------------------------------------------------------------------------------
#### Lesson 84 - CRUD-View-Layer - CRUD операции на уровне отображения. REMOVE (DELETE) данных.

Мы научились отображать данные на HTML страничке в динамическом формате, теперь удалим выбранного user-а. Для этого нам
понадобится форма удаления. Ее мы можем объединить с формой отображения данных user-а просто добавив еще одну кнопку.
    
    <form th:action="@{/users/{userId}/delete(userId=${user.id})}"
          th:if="${user.role.name() == 'ADMIN'}"
          method="post">
        <button type="submit">DELETE</button>
    </form>

В данной форме кнопка DELETE будет видна только если статус нашего user-a ADMIN. При нажатии на кнопку удаления в работу 
включится метод [*.delete()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java#L200) из [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java):

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id){

     /* код для удаления записи user-a с выбранным ID */

     return "redirect:/users";
    }

После удаления записи из БД произойдет перенаправление на стр. [users.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users.html) с которой мы пришли на стр. [user.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/user.html) и мы 
сможем увидеть обновленный список user-ов.  

---------------------------------------------------------------------------------------------------------------
#### Lesson 85 - CRUD-View-Layer - CRUD операции на уровне отображения. CREATE данных.

Займемся созданием или внесением данных в БД - C_reate - первая буква в акрониме CRUD. Для внесения новых данных 
(создания) нам понадобится новая форма отображения очень похожая на форму user.html, но с другим маппингом. Так же в 
классе [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java) нам понадобится метод [*.registration()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java#L134), который будем вносить новые данные на нижележащий уровень
приложения и далее в БД, см. [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java)

- Шаг.1 - Создаем файл отображения для регистрации [resources/templates/user/registration.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/registration.html) и методы для обработки 
такого запроса в [UserController - *.registration()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java#L134).
- Шаг 2 - Прописываем в файл registration.html простую формой регистрации.
- Шаг 3 - В форме аутентификации [login.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/login.html) добавим кнопку регистрации, которая перенаправит нас на [registration.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/registration.html)
________________________________________________________________________________________________________________________
Еще раз повторим пройденное. У нас есть уровень контроллеров, например [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java) или [LoginController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/LoginController.java) в них есть 
методы которые обрабатывают запрос от пользователя приложения и не просто запрос, а web-запрос. В текущей настройке, мы 
стали использовать [Thymeleaf](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/ThymeleafManual/ThymeleafTutorial/AboutThymeleaf_1.txt), у нас в [папке 'templates'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/src/main/resources/templates) лежит [папка 'user'](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/src/main/resources/templates/user) и всё взаимодействие 'приложение-пользователь'
завязано на неё (в данном примере). 

К примеру если пользователь хочет залогиниться в нашем приложении, он должен обратится к странице - [http://localhost:8080/login](http://localhost:8080/login),
при этом запрос будет перенаправлен контролеру [LoginController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/LoginController.java) и его методу с соответствующим методом HTML запроса, 
например GET:

    /* 
    Мы в браузере при запущенном приложении набрали http://localhost:8080/login, т.е. запросили страницу 
    позволяющую нам залогиниться, теперь метод должен вернуть нам на наш запрос некую страницу отображения. 
    У нас это login.html и она находится в папке resources/templates/user/login.html, но мы можем указать
    "user/login" см. ниже.
    */

    /* Методом GET запрашиваем у приложения некое действия через web - хотим получить страницу login */
    @GetMapping("/login") 
    public String loginPage() {
        /* 
        login.html страницу вернет пользователю данный метод на 
        запрос в браузере, из папки resources/templates/user 
        */
        return "user/login";
    }

________________________________________________________________________________________________________________________
#### Lesson 86 - CRUD-View-Layer - CRUD операции на уровне отображения. REDIRECT при CREATE данных (Теоретический урок).

При заполнении формы (особенно если она большая) можно совершить ошибку или может произойти сбой, и тогда возникнет 
ситуация, когда данные не будут обработаны сервером и не будут внесены в БД. Т.е. нам придется заполнять форму 
регистрации еще раз (а форма большая и нам лень). Мы при разработке формы и обработке запроса можем сделать так, чтобы
данные в форме при сбое (ошибке) сохранялись и мы только внесли нужные правки, а не заполняли форму с нуля. Реализуем 
такой функционал (теоретически). Реализовать подобный функционал можно 3-мя способами (очень отличающимися по 
возможностям):
- 1 - Полный redirect с конкатенацией параметров:
    
        return "redirect:/users/registration?username=" + user.getUsername + "...";

В данном случае мы должны будем в строке переадресации перечислить все параметры user-a, т.е. передать в запрос длинный
URL. Недостаток данного метода в том, что максимальная длина URL составляет 2048 символов, т.е. может возникнуть 
ситуация когда мы не сможем передать URL > 2048 символов. И естественно оформление такого return-a штука сомнительная, 
хоть и рабочая.

- 2 - Использование [RedirectAttributes](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/RedirectAttributes.html):

        @PostMapping
        public String create(@ModelAttribute UserCreateEditDto user,
                                             RedirectAttributes redirectAttributes) {
                   redirectAttributes.addAttribute("username", user.getUsername());
                   redirectAttributes.addAttribute("firstname", user.getFirstname());
                   . . .
                   redirectAttributes.addAttribute("...", user.get...());
                   . . .       
                   return "redirect:/users/registration";
        }

В данном случае мы загружаем параметры user-a через [RedirectAttributes](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/RedirectAttributes.html). Из плюсов у нас нет конкатенации строки, но мы 
должны будем все же прописать все передаваемые параметры (поля нашей модели [UserCreateEditDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/dto/UserCreateEditDto.java) user).

- 3 - Использование RedirectAttributes с методом *.addFlashAttribute():

        @PostMapping
        public String create(@ModelAttribute UserCreateEditDto user,
                                             RedirectAttributes redirectAttributes) {
    
                   redirectAttributes.addFlashAttribute("user", user);
                   return "redirect:/users/registration";
        }

В данном случае метод *.addFlashAttribute() работает хитрее, он воспользуется возможностями сессии. Он поместит данные 
объекта User в сессию. Для того чтобы у нас не было проблем с именованием атрибутов мы в методе регистрации пропишем 
аннотацию с параметром "user", для полной однозначности имени передаваемого атрибута модели (параметра):
        
        @GetMapping("/registration")
        public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        }

________________________________________________________________________________________________________________________
#### Lesson 87 - CRUD-View-Layer - Фильтрация запроса (Filter-Query).

Фильтрация нужна для того чтобы при запросе, например, списка user-ов мы не возвращали полный список оных из БД, т.к. их 
может быть очень много, а вернули только нужную нам часть, которая соответствует определенным условиям: имени, фамилии 
или дате рождения, как вариант. 

По шагам это будет выглядеть так (снизу вверх см. [Spring_MVC.jpg](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_15/DOC/Spring_MVC.jpg)):
- Шаг 1. Создадим [UserFilterDto.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/dto/UserFilterDto.java) в котором будет 3-и поля, именно по ним мы и будем фильтровать;
- Шаг 2. В классе [FilterUserRepositoryImpl.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java) передадим в метод [*.findAllByFilter(UserFilterDto filter)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java#L95) параметр filter;
- Шаг 3. В классе [UserService](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/service/UserService.java) перегрузим метод [*.findAll](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/service/UserService.java#L49) передав в него параметр [UserFilterDto](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/dto/UserFilterDto.java) filter;
- Шаг 4. В классе [UserController](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java), в метод [*.findAll()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java#L65) кроме модели, передаем еще и UserFilterDto filter;
    
        @GetMapping
        public String findAll(Model model, UserFilterDto filter) {
          model.addAttribute("users", userService.findAll(filter));
          return "user/users";
        }

Прописываем логику на стороне сервера от слоя к слою (из метода в метод). Теперь необходимо, чтобы данные на стороне 
пользователя отображались согласно выбранной фильтрации данных. 

- Шаг 5. Создадим страницу с формой фильтрации и отображением результатов в [resources/templates/user/users_with_filter.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users_with_filter.html);

        <form action="/users" method="get">
            <label for="firstname">Firstname:
                <input id="firstname" type="text" name="firstname" value="">
            </label>
            <label for="lastname">Lastname:
                <input id="lastname" type="text" name="lastname" value="">
            </label>
            <label for="birthDate">Birthdate:
                <input id="birthDate" type="date" name="birthDate">
            </label>
            <button type="submit">Filter</button>
        </form>

В форме явно указан метод запроса GET, хотя мы его могли опустить. В тегах 'input' есть обязательная переменная id и 
необязательная value, это и есть связка: атрибут=значение, которые передаются в URL GET запроса. 

Например : http://localhost:8080/users/filter?firstname=Alex&lastname=Volkov&birthDate=2024-02-13 , где явно видно после 
'/users?': firstname=Alex, lastname=Volkov, birthDate=2024-02-13.

Именно эти значения из слоя Контролеров попадают на слой Сервисов, в наш [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/service/UserService.java) в его перегруженный метод
[*.findAll(UserFilterDto filter)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/service/UserService.java#L49). И уже со слоя Сервисов на слой Репозиториев в имплементацию интерфейса 
[FilterUserRepositoryImpl.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java) в метод [*.findAllByFilter(UserFilterDto filter)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java#L95), где и происходит окончательная обработка
запроса, т.е. фильтрация данных из [таблицы 'users'](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/test/resources/sql_scripts/data.sql#L15) нашей БД и дальнейший возврат полученного результата по слоям вверх 
к слою отображения.

________________________________________________________________________________________________________________________
!!! Примечание !!! 

При изучении материала по [Hibernate](https://github.com/JcoderPaul/Hibernate_Lessons) мы столкнулись с [Criteria API](https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_5/DOC/CriteriaAPIShortView.txt) 
и [QueryDSL](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_6)
это инструментарий который сильно упрощает жизнь программисту при обращении с запросами к БД. И если в простом JAVA 
приложении обе технологии работают без нареканий, то при подключении к Spring-у выше 5.0, QueryDSL начинает вести себя 
весьма девиантно. Поэтому методы работающие с БД нашего слоя репозиториев используют технологию Criteria API, а в ней 
есть свои особенности, в частности синтаксис запросов к БД. 

Мы помним из курса по [SQL](https://github.com/JcoderPaul/My_Little_SQL_Guide), что у нас есть подстановочные знаки, 
например: '%', который может замещать собой любой символ. Именно его мы используем в методе фильтрации на уровне 
репозиториев: 

        if (filter.firstname() != null) {
            predicates.add(cb.like(user.get("firstname"), "%" + filter.firstname() + "%"));
        }

В данном случае параметры полученные UserFilterDto фильтром из URL запроса могут, например, содержать только один символ,
скажем букву 'а', т.е. firstname=а и/или lastname=а. Если использовать технологию QueryDsl, то там запрос формируется 
по-другому и, проблем с одним (несколькими) символами переданными из формы фильтрации в URL не возникает, а вот при 
работе с Criteria API мы должны строго следовать синтаксису SQL:

    -- Получить из таблицы 'users' БД все записи у которых поле firstname содержит хотя бы один символ 'а'  
    SELECT * FROM users WHERE firstname LIKE '%a%';

Что мы и реализовали в методе [getPredicates()](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java#L297):

        private static List<Predicate> getPredicates(UserFilterDto criteriaFilter,
                                                     CriteriaBuilder criteriaBuilder,
                                                     Root<User> userRoot) {

        List<Predicate> predicates = new ArrayList<>();
        
        if (criteriaFilter.firstname() != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("firstname"),
                    "%" + criteriaFilter.firstname() + "%"));
        }
        
        . . . some code . . .

        return predicates;
    }

________________________________________________________________________________________________________________________

- Шаг 6. Запускаем приложение, обращаемся к нему по адресу: [http://localhost:8080/users/filter](http://localhost:8080/users/filter), проверяем как работает фильтр;

________________________________________________________________________________________________________________________
#### Lesson 88 - Постраничное получение и вывод информации (Pagination).

При работе с постраничной разбивкой можно пойти несколькими путями, как показало мое исследование данного вопроса. 
Реализуем один из вариантов, будет грубо и многостранично (не оптимально, с точки зрения повторяющегося кода). И так,
мы имеем условно 4-и слоя (более грубо - 3-и см. [MVC_Chart_with_comment.jpeg](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/MVC_Chart_with_comment.jpeg)): отображения - тот, что общается с 
пользователем приложения, далее, контроллеры - тот, что обрабатывает запросы пользователя, сервисы - тот, что работает 
со слоем контроллеров и слоем репозиториев, репозитории - тот, что получает данные из БД. 

Как я понял, создание одних слоев более толстыми, а других более тонкими, с точки зрения насыщенности бизнес логикой до
сих пор остается вопросом спорным и сугубо индивидуальным мнением каждого программиста. Однако, некий консенсус все же 
есть см. [MVC Concept.txt](https://github.com/JcoderPaul/HTTP_Servlets_Java_EE/blob/master/MVCPractice/DOC/MVC%20Concept.txt)

В нашем случае параметры для фильтрации и разбиения на страницы при выводе информации задаются пользователем на странице
взаимодействия (или отображения): [users_with_filter.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users_with_filter.html) и [users_with_pagination.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users_with_pagination.html). При первом обращении к приложению
для фильтра и разбивки на страницы используются параметры заданные по-умолчанию. При фильтрации просто исходя из логики
кода, а при пагинации, исходя из особенностей настройки обработчика аргументов постраничной разбивки см.
[PageableHandlerMethodArgumentResolverSupport](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/web/PageableHandlerMethodArgumentResolverSupport.html) и [DOC/PageFilterSort/SpringBootPageFilter.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/PageFilterSort/SpringBootPageFilter.txt)

Для того чтобы наглядно понять, как ведет себя наше приложение на уровне контроллеров в [UserController.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/http/controller/UserController.java) мы создали
перегруженный метод *.findAll() который принимает разное количество переменных и отображает чуть отличающиеся страницы
отображения: 
- [users.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users.html) - вариант без возможности фильтрации и разбивки на страницы, вывод данных одним списком;
- [users_with_filter.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users_with_filter.html) - вариант с возможностью фильтрации, что утяжеляет код на уровне репозиториев;
- [users_with_pagination.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users_with_pagination.html) - вариант с фильтрацией и разбивкой по страницам, еще сильнее утяжеляет код на уровне 
репозиториев, хотя облегчить его легко, но мы оставим так, чтобы иметь возможность комментировать наши шаги при 
написании кода;

Шаг 1. - Определяем какие атрибуты мы передадим в модель.

Шаг 2. - Формируем набор данных для заполнения атрибутов, в данном примере фильтр остался без изменений, поскольку для
фильтрации и пагинации мы используем Criteria API, то по-другому фильтр можно назвать - 'критерии поиска'.

Шаг 3. - На запрос пользователя нам нужно возвращать данные из таблицы users, но не все, а отфильтрованные и разбитые на 
страницы. Для этого мы написали [PagePaginationResponse.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/dto/PagePaginationResponse.java). 

Он содержит два поля: 
- список возвращаемых записей; 
- метаданные - вложенный класс, который содержит важные сведения о разбивке: 
  - page - номер выводимой страницы; 
  - size - количество записей из БД на одну страницу;
  - totalElements - количество записей всего.

Для применения функционала разбивки на страницы выводимых данных мы воспользовались возможностями интерфейсов [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) 
и [Page](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html) из [пакета org.springframework.data.domain](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html), читать [DOC/PageFilterSort/SpringBootPageFilter.txt](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/DOC/PageFilterSort/SpringBootPageFilter.txt).

Шаг 4. - Немного доработали нашу форму фильтрации, получив - [users_with_pagination.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users_with_pagination.html), добавили два поля: page и 
size - параметры для работы класса [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html), причем первоначальные их значения задаются Spring-ом по-умолчанию и только
после взаимодействия с пользователем (который их меняет в форме UI) они поступают, как и параметры фильтра на слой 
сервисов.

Шаг 5. - На слое сервисов [UserService.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/service/UserService.java) мы еще расширяем (перегружаем) метод [*.findAll(UserFilterDto filter, Pageable pageable)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/service/UserService.java#L37),
который теперь возвращает подсписок [Page](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html), полученный с уровня репозиториев.

Шаг 6. - На слое репозиториев мы фильтруем и разбиваем на страницы наши записи из таблицы users БД. Если оперировать 
понятиями 'толстый' и 'глупый', то слои контролеров и отображений у нас получились тонкими и глупыми (вроде), а вот 
Модель или слои сервисов и репозиториев толстые (хотя особой бизнес логики мы еще не разрабатывали). См. код: 
[FilterUserRepositoryImpl.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java) метод [*.findAllByFilterAndPage(UserFilterDto filter, Pageable pageable)](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/repository/user_repository/FilterUserRepositoryImpl.java#L188).

Шаг 7. - Возвращаем отфильтрованные и разбитые на страницы данные по слоям вверх к слою отображения.

________________________________________________________________________________________________________________________
См. док., по Thymeleaf: [DOC/ThymeleafManual](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/DOC/ThymeleafManual)

См. док., по классам и интерфейсам применяемым при фильтрации и пагинации: [DOC/PageFilterSort](https://github.com/JcoderPaul/Spring_Framework_Lessons/tree/master/Spring_part_17/DOC/PageFilterSort)
________________________________________________________________________________________________________________________
См. официальные [Guides](https://spring.io/guides):
- [Getting Started Guides](https://spring.io/guides) - Эти руководства, рассчитанные на 15–30 минут, содержат быстрые
  практические инструкции по созданию «Hello World» для любой задачи разработки с помощью Spring. В большинстве случаев
  единственными необходимыми требованиями являются JDK и текстовый редактор.
- [Topical Guides](https://spring.io/guides#topicals) - Тематические руководства предназначенные для прочтения и
  понимания за час или меньше, содержит более широкий или субъективный контент, чем руководство по началу работы.
- [Tutorials](https://spring.io/guides#tutorials) - Эти учебники, рассчитанные на 2–3 часа, обеспечивают более глубокое
  контекстное изучение тем разработки корпоративных приложений, что позволяет вам подготовиться к внедрению реальных
  решений.
________________________________________________________________________________________________________________________
- [Spring Projects на GitHub](https://github.com/spring-projects) ;
________________________________________________________________________________________________________________________