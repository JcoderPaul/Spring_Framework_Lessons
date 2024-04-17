package spring.oldboy.dto;

import lombok.Value;
import spring.oldboy.database.entity.Role;

import java.time.LocalDate;

@Value
public class UserReadDto {
    Long id;
    String username;
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
    CompanyReadDto company;
}
