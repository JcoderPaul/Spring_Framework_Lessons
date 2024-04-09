package spring.oldboy.pool;

/*
Если указать данный класс в resources/application.xml, без параметров,
то через рефлексию, используя default конструктор, будет создан Bean
этого класса и помещен в IoC Container, откуда методом *.getBean() мы
его извлекаем.
*/

public class StarterConnectionPool {
    // Код для создания пула соединений с БД
}
