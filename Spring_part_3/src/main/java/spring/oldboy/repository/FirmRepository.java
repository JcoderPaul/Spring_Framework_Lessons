package spring.oldboy.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import spring.oldboy.entity.Company;
import spring.oldboy.pool.StarterConnectionPool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/*
Специально не помечаем данный класс, но его bean будет
создан исходя из настроек resources/application.xml

Поставим scope не singleton - он по умолчанию. И теперь
на каждый новый запрос, мы будем получать новый bean и
не из IoC контейнера.
*/
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FirmRepository implements CrudRepository<Integer, Company> {

    private final StarterConnectionPool pool1;
    private final List<StarterConnectionPool> pools;
    private final Integer poolSize;

    public FirmRepository(StarterConnectionPool pool1,
                          List<StarterConnectionPool> pools,
                          @Value("${db.pool.size}") Integer poolSize) {
        this.pool1 = pool1;
        this.pools = pools;
        this.poolSize = poolSize;
    }

    @PostConstruct
    private void init() {
        System.out.println("init company repository");
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