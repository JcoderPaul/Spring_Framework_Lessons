package spring.oldboy.database.pool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component("pool1")
@RequiredArgsConstructor
@Getter
public class ConnectionPool {
    /*
    Однако тут есть хитрость. Если заглянуть в прошлую версию данного класса, например:
    ...\Spring_part_5\src\main\java\spring\oldboy\pool\ConnectionPool.java, то можно
    увидеть, что аннотации над полями напрямую прописаны в конструкторе.

    В данном случае, то что мы пытаемся внедрить в поля через аннотации, в конструктор,
    созданный через @RequiredArgsConstructor не пройдет без создания и настройки файла
    конфигурации: lombok.config, где мы прописываем, какие аннотации и инъекции следует
    продлить в конструктор через Lombok. Важно указать полный путь к используемым Spring
    аннотациям.
    */
    @Value("${db.username}")
    private final String username;
    @Value("${db.pool.size}")
    private final Integer poolSize;

    @PostConstruct
    private void init() {
        log.info("Init connection pool");
    }

    @PreDestroy
    private void destroy() {
        log.info("Clean connection pool");
    }
}
