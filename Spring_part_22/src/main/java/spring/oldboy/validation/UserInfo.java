package spring.oldboy.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import spring.oldboy.validation.impl.UserInfoValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
Аннотация @Constraint содержит наш обработчик ограничений или валидатор,
который мы должны написать. Пусть он будет обрабатывать поля firstname и
lastname класса UserCreateEditDto.java
*/
@Constraint(validatedBy = UserInfoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfo {
    /* Lesson 91: Любая самописная аннотация должна содержать эти поля */

    String message() default "Поля firstname или lastname должны быть заполнены!"; // Задали выводимое сообщение

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
