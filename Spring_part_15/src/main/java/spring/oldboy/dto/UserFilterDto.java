package spring.oldboy.dto;

import java.time.LocalDate;

public record UserFilterDto(String firstname,
                            String lastname,
                            LocalDate birthDate) {
}
