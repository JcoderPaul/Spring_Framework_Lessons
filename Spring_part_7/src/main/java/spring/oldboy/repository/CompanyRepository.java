package spring.oldboy.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import spring.oldboy.entity.Company;
import spring.oldboy.pool.ConnectionPool;

import java.util.List;
import java.util.Optional;

/* Подключаем логер */
@Slf4j
@Repository
@Scope(BeanDefinition.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool pool1;
    private final List<ConnectionPool> pools;
    /* Перенос данной аннотации в конструктор прописан в lombok.config */
    @Value("${db.pool.size}")
    private final Integer poolSize;

    @PostConstruct
    /* Логируем данные */
    private void init() {
        log.warn("init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method...");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method...");
    }
}