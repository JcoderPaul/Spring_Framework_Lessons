package spring.oldboy.integration.database.repository.lesson_46;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;
import spring.oldboy.database.entity.Company;
import spring.oldboy.integration.annotation.IT;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
Для наших интеграционных тестов применяем самописную (мета-аннотацию)
из предыдущей части .../Spring_Lessons/Spring_part_8/...
*/
@IT
@RequiredArgsConstructor
/*
Уберем аннотацию @Transactional - изучим 'ручное управление' транзакциями. При этом помним,
что в прошлый раз (CompanyRepositoryTest.java), без данной аннотации, мы словили исключение,
т.к. три строки кода не выполнялись в одной транзакции (из-за настроек fetch). Естественно
сейчас такого не будет см. код ниже.
*/
class CompanyRepositoryTemplateTest {

    /*
    Авто-конфигурация Hibernate позволяет нам использовать
    EntityManager (@PersistenceUnit) и EntityManagerFactory
    (@PersistenceContext) как bean-ы.
    */
    private final EntityManager entityManager;
    /*
    В TransactionalAutoConfiguration произойдет настройка TransactionTemplate
    (т.е. он уже есть в контексте) и значит мы можем его использовать в наших
    тестах как зависимость, см. док.:
    - DOC/TransactionInSpring/TransactionInSpring.txt ;
    - DOC/TransactionInSpring/TransactionInSpring.txt ;
    */
    private final TransactionTemplate transactionTemplate;

    @Test
    void findById() {
        /* Применим один из методов TransactionTemplate */
        transactionTemplate.executeWithoutResult(tx -> {
            var company = entityManager.find(Company.class, 1);
            assertNotNull(company);
            assertThat(company.getLocales()).hasSize(2);
        });
    }

    @Test
    void saveCompanyToBase() {
        transactionTemplate.execute(tx -> {
            Company company = Company.builder()
                    .name("Apple")
                    .locales(Map.of(
                            "ru", "Apple описание",
                            "en", "Apple description"
                    ))
                    .build();
            entityManager.persist(company);
            assertNotNull(company.getId());
            return null;
        });
    }
}