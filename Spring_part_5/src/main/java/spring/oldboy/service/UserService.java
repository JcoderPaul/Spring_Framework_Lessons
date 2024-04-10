package spring.oldboy.service;

import org.springframework.stereotype.Service;
import spring.oldboy.entity.Company;
import spring.oldboy.repository.CrudRepository;
import spring.oldboy.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;

    public UserService(UserRepository userRepository,
                       CrudRepository<Integer, Company> companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
}
