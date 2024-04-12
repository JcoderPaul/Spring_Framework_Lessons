package spring.oldboy.integration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import spring.oldboy.pool.ConnectionPool;

@TestConfiguration
public class TestApplicationRunner {

    @SpyBean(name = "pool1")
    private ConnectionPool pool1;
}
