package spring.oldboy.mapper;

import org.springframework.stereotype.Component;
import spring.oldboy.database.entity.Company;
import spring.oldboy.dto.CompanyReadDto;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto map(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName()
        );
    }
}
