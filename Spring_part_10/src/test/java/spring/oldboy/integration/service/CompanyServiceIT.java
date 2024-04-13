package spring.oldboy.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.config.DatabaseProperties;
import spring.oldboy.dto.CompanyReadDto;
import spring.oldboy.integration.annotation.IT;
import spring.oldboy.service.CompanyService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class CompanyServiceIT {

    private static final Integer COMPANY_ID = 1;

    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById() {
        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}