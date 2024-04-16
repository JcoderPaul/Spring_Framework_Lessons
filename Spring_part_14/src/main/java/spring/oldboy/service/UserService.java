package spring.oldboy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.oldboy.database.repository.company_repository.CompanyRepository;
import spring.oldboy.database.repository.user_repository.UserRepository;

/*
Используем Lombok аннотации для создания конструктора
с final полями. Если бы у нас были не final поля, то
мы воспользовались бы @AllArgsConstructor
*/
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

}
