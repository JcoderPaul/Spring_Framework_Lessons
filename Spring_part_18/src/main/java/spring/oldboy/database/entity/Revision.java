package spring.oldboy.database.entity;

/* Lesson_59 - Исследуем механизм Spring Envers */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/* Классические аннотации для сущности. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
/*
Помечаем единственную сущность, на все наше приложение,
отслеживающую изменения состояний и связи других сущностей.
*/
@RevisionEntity
public class Revision {

    /*
    В данной сущности может быть масса полей, НО САМЫЕ ВАЖНЫЕ из них указаны
    ниже и они являются обязательными, а самое главное должны быть аннотированы
    специальным образом см. DOC/DataEnvers/LoggingDataForRevisions.txt
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*
    Аннотация отмечает свойство, которое будет содержать номер ревизии в объекте
    ревизии, см. RevisionListener. Значения этого свойства должны образовывать
    строго возрастающую последовательность чисел. Значение этого свойства не будет
    устанавливаться Envers. В большинстве случаев это должен быть автоматически
    сгенерированный основной идентификатор, присвоенный базой данных.
    */
    @RevisionNumber
    private Integer id;

    /*
    Аннотация отмечает свойство, которое будет содержать временную метку редакции в
    объекте ревизии, см. RevisionListener. Значение этого свойства будет автоматически
    установлено Envers.
    */
    @RevisionTimestamp
    private Long timestamp;
}
