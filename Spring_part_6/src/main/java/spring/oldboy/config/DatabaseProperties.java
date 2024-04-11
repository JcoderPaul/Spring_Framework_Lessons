package spring.oldboy.config;
/*
Lesson 30: Создадим конфигурационный bean на основе application.yml

DatabaseProperties должен быть POJO и иметь геттеры и сеттеры к полям, а так же
конструктор без параметров. Поскольку Spring используя ReflectionAPI получает
доступ через конструктор без параметров и устанавливает значения полей.

Для уменьшения количества рутинного кода используем Lombok.

Для того чтобы передать Spring Boot соответствие данного класса и application.yml
мы должны тем или иным способом сделать из нашего DatabaseProperties bean и ...

Первый вариант - создаем соответствующий метод в JpaConfiguration.java, это
*.databaseProperties()
*/
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class DatabaseProperties {
    /*
    Перечислим все поля из application.yml соблюдая точность имен, и тогда
    Spring Boot сам преобразует *.yml формат в объект DatabaseProperties.
    */
    private String username;
    private String password;
    private String driver;
    private String url;
    private String hosts;
    /*
    Вложенность полей в application.yml имеет значение. Наш объект pool
    состоит из 2-ух полей - создадим класс для него, см. ниже.
    */
    private PoolProperties pool;
    /*
    Так же у нас есть список с именем 'pools' - создаем его, это коллекция
    List уже созданных PoolProperties. И снова, имя ключа в application.yml
    определяет имя списка.
    */
    private List<PoolProperties> pools;
    /*
    Мы также можем пойти в обратную сторону, например, в данном классе мы
    хотим создать коллекцию Map и иметь ее аналог в application.yml. Снова
    имя поля в классе и имя ключа в YAML файле должно совпадать. Структура
    Map простая key - String, value - Object.
    */
    private Map<String, Object> properties;

    /* Сделаем PoolProperties вложенным классом */
    @Data
    @NoArgsConstructor
    public static class PoolProperties {
        /* Нужные нам поля */
        private Integer size;
        private Integer timeout;
    }

    /*
    Стоит заметить что в application.yml есть строка не относящаяся к DB
    свойствам - это 'spring.profiles.active: development,qa' и соответственно,
    тут мы ее ни коим образом не описываем.
    */
}
