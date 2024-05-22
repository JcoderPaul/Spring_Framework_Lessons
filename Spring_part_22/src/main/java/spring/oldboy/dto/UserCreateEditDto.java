package spring.oldboy.dto;
/*
Lesson 89 - Применяем аннотации из пакета jakarta.validation к полям класса.
*/
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import spring.oldboy.database.entity.Role;
import spring.oldboy.validation.UserInfo;
import spring.oldboy.validation.group.CreateAction;

import java.time.LocalDate;

@Value
/*
Немного упростим себе жизнь на этапе тестирования. Данная аннотация создает внутренний тип,
содержащий строковые константы, содержащие имя поля для каждого поля. В качестве альтернативы
генерирует внутреннее перечисление со значениями перечисления, соответствующими каждому имени
поля.
*/
@FieldNameConstants
/*
Применим нашу самописную аннотацию для валидации полей firstname
и lastname, теперь аннотация @NotNull над этими полями не нужна.
*/
@UserInfo(groups = CreateAction.class)
public class UserCreateEditDto {

    /*
    Данная аннотация 'валидирует' - проверяет поле на
    правильность в ходе заполнения формы регистрации,
    а точнее в момент передачи данных в БД.

    см. DOC/JakartaBeanValidation
    */
    @Email
    String username;

    /*
    Lesson 105: Добавляем поле пароля. Аннотация показывает, что элемент
    не должен быть нулевым и должен содержать хотя бы один символ без
    пробелов. В нашем случае с параметром уточнения - на этапе создания.
    Т.к. на изменение - UPDATE - пароля мы будем использовать другой
    функционал, изменяющий не всего USER-a, а только пароль.
    */
    @NotBlank(groups = CreateAction.class)
    String rawPassword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    /*
    Lesson 89: Проверяем чтобы сведения из поля формы не было пустым,
    а также длинна заполненной строки лежала в диапазоне от 3-х до 64
    символов. Хотя в данном случае лучше бы подошла аннотация - @NotEmpty,
    как и для поля ниже.

    Lesson 90: Как только мы применим нашу кастомную аннотацию @UserInfo,
    текущая @NotNull уже не нужна.
    */
    // @NotNull
    @Size(min = 3, max = 64)
    String firstname;

    /*
    Lesson 90: Как только мы применим нашу кастомную аннотацию @UserInfo,
    текущая @NotNull уже не нужна.
    */
    // @NotNull
    String lastname;

    Role role;
    Integer companyId;
    /*
    Параметр нашей аватарки, имя поля в данном классе, должно совпадать
    с параметром name="image" в теге <input> наших форм регистрации
    (registration.html) и обновления-отображения (user.html) в
    resources/templates/user
    */
    MultipartFile image;
}
