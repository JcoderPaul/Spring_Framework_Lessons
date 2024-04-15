package spring.oldboy.database.entity;

/* Part 10: Lesson 58 - Аудит сущностей, пример абстрактного класса */

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/*
Создадим абстрактный класс от которого будут наследоваться
другие классы (сущности) нуждающиеся в аудите своих изменений

Задаем геттеры и сеттеры через аннотации Lombok-a
*/
@Getter
@Setter
/*
Данная аннотация нужна, чтобы наследники могли наследовать
поля родителя См. DOC/MappedSuperclass.txt
*/
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity<T extends Serializable> implements BaseEntity<T> {

    /*
    Добавим поля:
    - createdAt - когда была создана сущность (запись);
    - modifiedAt - когда была изменена сущность (запись);
    - createdBy - кто создал сущность (запись);
    - modifiedBy - кто изменял сущность;
    */
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;

    /*
    В реальных приложениях тут могут быть любые классы, а
    не только String, поскольку внесший изменение объект
    может фиксироваться по-разному, это может быть емайл,
    имя поля, вообще все что угодно, полученное из модуля
    безопасности.
    */
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;
}
