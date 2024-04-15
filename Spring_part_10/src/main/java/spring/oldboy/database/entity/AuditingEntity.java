package spring.oldboy.database.entity;

/* Lesson 58 - Аудит сущностей, пример абстрактного класса */

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

    Класс Instant - Мгновенная точка на линии времени.
    Этот класс моделирует одну мгновенную точку на временной шкале.
    Это можно использовать для записи меток времени событий в приложении.

    Диапазон мгновенных значений требует хранения числа, большего,
    чем long. Для этого в классе хранятся значения long, представляющие
    секунды эпохи, и int, представляющие наносекунды секунды, которые
    всегда будут находиться в диапазоне от 0 до 999 999 999. Секунды
    эпохи отсчитываются от стандартной эпохи Java 1970-01-01T00:00:00Z,
    где моменты после эпохи имеют положительные значения, а более ранние
    моменты имеют отрицательные значения. Как для эпохальной секунды, так
    и для наносекундной части, большее значение всегда находится позже
    на временной шкале, чем меньшее значение.
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
