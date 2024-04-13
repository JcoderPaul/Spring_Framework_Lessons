package spring.oldboy.integration.database.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import spring.oldboy.database.entity.Company;
import spring.oldboy.integration.annotation.IT;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
Для наших интеграционных тестов применяем самописную (мета-аннотацию)
из предыдущей части .../Spring_Lessons/Spring_part_8/...
*/
@IT
@RequiredArgsConstructor
/*
Для автоматизации управления транзакциями используем аннотацию из пакета
org.springframework.transaction.annotation.Transactional, теперь она будет
управлять жизненным циклом транзакций. Т.е. ее функционал открывает и
закрывает транзакции, как перед началом тестового метода, так и в конце
оного соответственно используя механизм транзакционного контекста.
*/
@Transactional
/*
Однако, у нас тестовый функционал, но его действия вполне реальны, т.е. мы
легко можем внести изменения в рабочую БД. По этому весь механизм работы
beforeTest..., а скорее afterTest... методов заточен на rollback, т.е. откат
проведенных в тестах операций - by Default. И естественно, мы хотим чистить
все изменения внесенные каждым тестовым методом в БД, отсюда по умолчанию
используется аннотация @Rollback.

Однако у нас могут быть другие цели, например, мы сами реализовали 'прорывной'
механизм чистки изменений внесенных в БД нашими тестами и для этого нам нужно
коммитить тестовые транзакции. Для этого используется аннотация @Commit - она
изменяет дефолтное поведение и коммитит транзакции тестов после выполнения
каждого теста.
*/

/*
Для проверки работы этих аннотаций можно раскомментировать их
поочереди при запуске теста saveCompanyToBase() и посмотреть,
какие изменения будут внесены (или нет) в БД при использовании
оных.

@Rollback
@Commit
*/
class CompanyRepositoryTest {

    /*
    Авто-конфигурация Hibernate позволяет нам использовать
    EntityManager (@PersistenceUnit) и EntityManagerFactory
    (@PersistenceContext) как bean-ы.
    */
    private final EntityManager entityManager;

    @Test
    void findById() {
        /*
        Используя EntityManager получим из БД компанию с ID = 1, в нашей БД под данным ID находится
        Google, см. DOC/SqlScripts/LoadData.sql

        Если запустить этот тест в DEBUG-е, то сразу легко увидеть, что entityManager будет proxy,
        поскольку он обертка вокруг нашего соединения с базой данных. И proxy нам нужен для того,
        чтобы работать с ThreadLocal и динамического получения его из EntityManagerFactory.

        Пример и комментарии см.:
        https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_practice

        Если данный тест запустить без аннотации @Transactional над тестовым классом, то мы получим:
        LazyInitializationException, ситуация очень похожа на ту, что мы видели в SimpleRepository.java
        в разделе Hibernate_practice см. ссылку выше. Т.е. получилась ситуация когда мы закрыли сессию
        и транзакцию еще до получения локалей сущности Company, поскольку настройка fetch = LAZY, то
        данные не были получены. Значит нам нужно сохранять транзакцию и сессию до получения всех
        связанных данных.
        */
        Company company = entityManager.find(Company.class, 1);
        /* Проверяем, что такой ID содержит данные */
        assertNotNull(company);
        /* Компания под таким ID содержит две локали */
        assertThat(company.getLocales()).hasSize(2);
    }

    /* Естественно перед запуском теста в БД компании с name = "Apple" быть не должно */
    @Test
    void saveCompanyToBase() {
        var company = Company.builder()
                .name("Apple")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);
        assertNotNull(company.getId());
    }
}