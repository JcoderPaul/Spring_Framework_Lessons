package spring.oldboy.dto;

import org.springframework.beans.factory.annotation.Value;

public interface PersonalInfoTwo {

    String getFirstname();

    String getLastname();

    String getBirthDate();

    /*
    Особенность применения интерфейсов в проекциях сущностей в том, что
    мы можем запрашивать синтегрированные из полей данные с применением
    синтаксиса SpEL и аннотации @Value, которую мы используем для внедрения
    свойств.

    В данном случае, terget - это текущая сущность PersonalInfoTwo и мы
    обращаемся к ней, хотя, тут, могли использоваться любые bean-ы из
    нашего приложения.

    См. Part 11: DOC/ArticleAboutProjection/SpringDataJPAProjections.txt
    */
    @Value("#{target.firstname + ' ' + target.lastname}")
    String getFullName();
}
