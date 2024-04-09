package spring.oldboy.service;

import org.springframework.stereotype.Service;
import spring.oldboy.entity.Company;
import spring.oldboy.repository.CrudRepository;
import spring.oldboy.repository.UserRepository;

/* Слой сервисов ставим соответствующую аннотацию семейства @Component */
@Service
public class UserService {

    private final UserRepository userRepository;
    /*
    Поскольку наш CompanyRepository после всех пост-процессоров станет proxy, то тут
    мы можем использовать только интерфейс от которого наследует CompanyRepository.

    Так же в контейнере могут находиться несколько CrudRepository наследников, и значит
    их придется искать по name (или ID), поэтому имя переданное в конструктор должно
    соответствовать имени нужного bean-a в IoC контейнере, например, как в application.xml:

    <bean id="companyRepository" class="spring.oldboy.repository.CompanyRepository"/>

    */
    private final CrudRepository<Integer, Company> companyRepository;

    public UserService(UserRepository userRepository,
                       CrudRepository<Integer, Company> companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
}