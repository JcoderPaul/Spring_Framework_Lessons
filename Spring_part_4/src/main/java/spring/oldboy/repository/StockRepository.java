package spring.oldboy.repository;

import org.springframework.beans.factory.annotation.Value;
import spring.oldboy.pool.StarterConnectionPool;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/*
Специально не помечаем данный класс, но его bean будет
создан исходя из настроек resources/application.xml

Поставим scope не singleton - он по умолчанию. И теперь
на каждый новый запрос, мы будем получать новый bean и
не из IoC контейнера.
*/
@Named("stockRep")
public class StockRepository{

    private final StarterConnectionPool pool1;
    private final List<StarterConnectionPool> pools;
    private final Integer poolSize;

    @Inject
    public StockRepository(StarterConnectionPool pool1,
                           List<StarterConnectionPool> pools,
                           @Value("${db.pool.size}") Integer poolSize) {
        this.pool1 = pool1;
        this.pools = pools;
        this.poolSize = poolSize;
    }

    public StarterConnectionPool getPool1() {
        return pool1;
    }

    public List<StarterConnectionPool> getPools() {
        return pools;
    }

    public Integer getPoolSize() {
        return poolSize;
    }
}