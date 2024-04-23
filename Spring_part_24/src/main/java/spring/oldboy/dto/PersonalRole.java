package spring.oldboy.dto;

import spring.oldboy.database.entity.Role;

public record PersonalRole(String firstname,
                           String lastname,
                           Role role) {
}
