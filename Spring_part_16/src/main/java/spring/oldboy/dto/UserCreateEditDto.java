package spring.oldboy.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import spring.oldboy.database.entity.Role;

import java.time.LocalDate;

@Value
/*
Немного упростим себе жизнь на этапе тестирования.
Данная аннотация создает внутренний тип, содержащий
строковые константы, содержащие имя поля для каждого
поля. В качестве альтернативы генерирует внутреннее
перечисление со значениями перечисления,
соответствующими каждому имени поля.
*/
@FieldNameConstants
public class UserCreateEditDto {
    String username;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
    Integer companyId;
}
