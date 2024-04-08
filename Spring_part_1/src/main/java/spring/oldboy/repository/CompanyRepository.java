package spring.oldboy.repository;

import spring.oldboy.pool.StarterConnectionPool;

public class CompanyRepository {

    private final StarterConnectionPool starterConnectionPool;

    public CompanyRepository(StarterConnectionPool starterConnectionPool) {
        this.starterConnectionPool = starterConnectionPool;
    }
}
