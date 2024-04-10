package spring.oldboy.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import spring.oldboy.entity.Company;
import spring.oldboy.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool pool1;
    private final List<ConnectionPool> pools;
    private final Integer poolSize;

    public CompanyRepository(ConnectionPool pool1,
                             List<ConnectionPool> pools,
                             @Value("${db.pool.size}") Integer poolSize) {
        this.pool1 = pool1;
        this.pools = pools;
        this.poolSize = poolSize;
    }

    @PostConstruct
    private void init() {
        System.out.println("init company repository");
    }

    /*
    В данном случае мы имитируем поиск компании в БД по ID и возвращаем ее ID
    (для примера достаточно), в данном случае просто создаем ее и все, мы же
    изучаем работу EventListener-ов, а не реализацию CRUD методов на реальной БД.
    */
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