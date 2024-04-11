package spring.oldboy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "db")
public record ImmutableDatabaseProperties(String username,
                                          String password,
                                          String driver,
                                          String url,
                                          String hosts,
                                          PoolProperties pool,
                                          List<PoolProperties> pools,
                                          Map<String, Object> properties) {

    public record PoolProperties(Integer size,
                                 Integer timeout) {
    }
}
