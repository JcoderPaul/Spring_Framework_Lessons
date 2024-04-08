package spring.oldboy.service;

import spring.oldboy.repository.CompanyRepository;
import spring.oldboy.repository.UserRepository;

/*
Чем меньше у класса (объекта) обязанностей, тем проще
его тестировать, модернизировать, или заменить на более
подходящий.
*/

public class UserService {

    /*
    Неправильный вариант от которого необходимо уходить, когда один
    объект управляет созданием другого объекта:

    *******************************************************************
    private final UserRepository userRepository = new UserRepository();
    *******************************************************************

    подобные зависимости должны поставляться извне, т.к. поставляемый
    (создаваемый объект) может зависеть от другого объекта (например
    ConnectionPool).

    Т.е. теперь мы должны точно знать, как создать ConnectionPool или
    знать откуда получить этот объект.
    */
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    /*
    Идеальным вариантом считается способ передачи зависимости через
    конструктор, извне, уже готовый объект требуемого класса.
    */
    public UserService(UserRepository userRepository,
                       CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
}