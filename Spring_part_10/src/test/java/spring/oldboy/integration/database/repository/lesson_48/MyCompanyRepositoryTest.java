package spring.oldboy.integration.database.repository.lesson_48;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.database.repository.company_repository.MyCompanyRepository;
import spring.oldboy.integration.annotation.IT;

@IT
@RequiredArgsConstructor
/* Теперь мы не хотим вносить изменения в БД и комментируем аннотацию @Commit */
class MyCompanyRepositoryTest {

    private final MyCompanyRepository myCompanyRepository;

    @Test
    void checkFindByQueries() {
        /* Попробуем из базы достать Google с большой буквы */
        myCompanyRepository.findByName("Google");
        /*
        В консоли:
                select
                c1_0.id,
                c1_0.name
            from
                company c1_0
            where
                c1_0.name=?
        */

        /* И найдем все компании имеющие в названии букву 'а' в любом регистре */
        myCompanyRepository.findAllByNameContainingIgnoreCase("a");
        /*
        В консоли:
                select
                    c1_0.id,
                    c1_0.name
                from
                    company c1_0
                where
                    upper(c1_0.name) like upper(?) escape '\'
        */
        myCompanyRepository.findCompanyByName("google");
        /*
        В данном случае в методе репозитария используется
        аннотация @Param для уточнения передаваемого параметра.
        */
        myCompanyRepository.findCompanyByNameWithParam("Meta");
    }

}