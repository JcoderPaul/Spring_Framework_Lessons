package spring.oldboy.integration.database.repository.lesson_50;

/* Тесты для Lesson 50 */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.database.repository.company_repository.SecondCompanyRepository;
import spring.oldboy.integration.annotation.IT;

@IT
@RequiredArgsConstructor
/* Теперь мы не хотим вносить изменения в БД и комментируем аннотацию @Commit */
class SecondCompanyRepositoryTest {

    private final SecondCompanyRepository secondCompanyRepository;

    @Test
    void checkFindByQueryAnnotation() {

        /* В интерфейсе репозитария явно прописан запрос в аннотации @Query */
        secondCompanyRepository.findCompanyByName("google");
        /*
        Hibernate:
            select
                c1_0.id,
                l1_0.company_id,
                l1_0.lang,
                l1_0.description,
                c1_0.name
            from
                company c1_0
            join
                company_locales l1_0
                    on c1_0.id=l1_0.company_id
            where
                c1_0.name=?
        */

        secondCompanyRepository.findAllByNameContainingIgnoreCase("a");
    }

}