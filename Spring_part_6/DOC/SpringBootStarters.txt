### SpringBoot Starters

Стартеры - это набор удобных дескрипторов зависимостей, которые мы можем включить в свое приложение.
Мы получаем универсальный набор для всех необходимых нам Spring и связанных с ними технологий без
необходимости искать примеры кода и копировать и вставлять множество дескрипторов зависимостей.

Например, если мы хотим начать использовать Spring и JPA для доступа к базе данных - включаем в ваш
проект зависимость spring-boot-starter-data-jpa, и все.

Стартеры содержат множество зависимостей, которые необходимы нам для быстрого запуска проекта
с согласованным, поддерживаемым набором управляемых переходных зависимостей.

Все официальные стартеры следуют определенной схеме именования: spring-boot-starter-***, где *** -
это конкретный тип приложения. Эта структура наименования логична, и призвана помочь нам, когда нужно
найти требующийся стартер. Интеграция Maven в среду разработки IDE позволяет быстро искать зависимости
по имени, набрав в редакторе "spring-boot-starter" для получения полного списка.

Сторонние стартеры не должны начинаться с spring-boot, поскольку они зарезервированы для официальных
артефактов Spring Boot. Сторонний стартер обычно начинается с названия проекта. Например, сторонний
стартер проекта Kafka называться: spring-kafka.

Spring Boot предоставляет следующие стартеры приложений в группе org.springframework.boot:

- spring-boot-starter - Core starter, включающий поддержку авто-конфигурации, логирование и YAML

- spring-boot-starter-activemq - Starter для JMS обмена сообщениями, используя Apache ActiveMQ

- spring-boot-starter-amqp - Starter для использования Spring AMQP и Rabbit MQ

- spring-boot-starter-aop - Starter для аспектно-ориентированного программирования с Spring AOP и AspectJ

- spring-boot-starter-artemis - Starter для JMS обмена сообщениями, используя Apache Artemis

- spring-boot-starter-batch - Starter для использования Spring Batch

- spring-boot-starter-cache - Starter для использования поддержки кэширования Spring Framework

- spring-boot-starter-cloud-connectors - Starter для использования Spring Cloud Connectors которые облегчают
                                         подключение к сервисам в облачных платформах, таких как Cloud Foundry
                                         и Heroku. Устарело - используйте Java CFEnv

- spring-boot-starter-data-cassandra - Starter для использования распределенной базы данных Cassandra и
                                       Spring Data Cassandra

- spring-boot-starter-data-cassandra-reactive - Starter для использования распределенной базы данных
                                                Cassandra и Spring Data Cassandra Reactive

- spring-boot-starter-data-couchbase - Starter для использования документно-ориентированной базы данных
                                       Couchbase и Spring Data Couchbase

- spring-boot-starter-data-couchbase-reactive - Starter для использования документно-ориентированной базы
                                                данных Couchbase и Spring Data Couchbase Reactive

- spring-boot-starter-data-elasticsearch - Starter для использования поискового и аналитического движка
                                           Elasticsearch и Spring Data Elasticsearch

- spring-boot-starter-data-jdbc - Starter для использования Spring Data JDBC

- spring-boot-starter-data-jpa - Starter для использования Spring Data JPA с Hibernate

- spring-boot-starter-data-ldap - Starter для использования Spring Data LDAP

- spring-boot-starter-data-mongodb - Starter для использования документно-ориентированной базы данных
                                     MongoDB и Spring Data MongoDB

- spring-boot-starter-data-mongodb-reactive - Starter для использования документно-ориентированной базы
                                              данных MongoDB и Spring Data MongoDB Reactive

- spring-boot-starter-data-neo4j - Starter для использования графовой базы данных Neo4j и Spring Data Neo4j

- spring-boot-starter-data-redis - Starter для использования ключ-значение хранилища Redis с Spring Data Redis
                                   и Lettuce клиентом

- spring-boot-starter-data-redis-reactive - Starter для использования ключ-значение хранилища
                                            Redis с Spring Data Redis reactive и Lettuce клиентом

- spring-boot-starter-data-rest - Starter для представления Spring Data репозиториев через REST,
                                  используя Spring Data REST

- spring-boot-starter-data-solr - Starter для использования поисковой платформы Apache Solr с Spring Data Solr

- spring-boot-starter-freemarker - Starter для создания MVC web приложений, используя FreeMarker views

- spring-boot-starter-groovy-templates - Starter для создания MVC web приложений, используя Groovy Templates views

- spring-boot-starter-hateoas - Starter для создания hypermedia-based RESTful web-приложений с
                                Spring MVC и Spring HATEOAS

- spring-boot-starter-integration - Starter для использования Spring Integration

- spring-boot-starter-jdbc - Starter для использования JDBC с HikariCP пулом соединений

- spring-boot-starter-jersey - Starter для создания RESTful web-приложений, используя JAX-RS и Jersey.
                               Альтернатива spring-boot-starter-web

- spring-boot-starter-jooq - Starter для использования jOOQ для доступа к SQL базам данных.
                             Альтернатива spring-boot-starter-data-jpa или spring-boot-starter-jdbc

- spring-boot-starter-json - Starter для чтения и записи json

- spring-boot-starter-jta-atomikos - Starter для JTA транзакций, используя Atomikos

- spring-boot-starter-jta-bitronix - Starter для JTA транзакций, используя Bitronix

- spring-boot-starter-mail - Starter для использования Java Mail и Spring Framework поддержки отправки email

- spring-boot-starter-mustache - Starter для создания web приложений, используя Mustache views

- spring-boot-starter-oauth2-client - Starter для использования возможностей Spring Security
                                      OAuth2/OpenID Connect клиента

- spring-boot-starter-oauth2-resource-server - Starter для использования возможностей Spring Security
                                               OAuth2 ресурс сервера

- spring-boot-starter-quartz - Starter для использования Quartz планировщика

- spring-boot-starter-rsocket - Starter для создания RSocket клиентов и серверов.

- spring-boot-starter-security - Starter для использования Spring Security

- spring-boot-starter-test -  Starter для тестирования Spring Boot приложений с библиотеками JUnit,
                              Hamcrest и Mockito

- spring-boot-starter-thymeleaf - Starter для создания MVC web приложений, используя Thymeleaf views

- spring-boot-starter-validation - Starter для использования Java Bean Validation с Hibernate Validator

- spring-boot-starter-web - Starter для создания web, включая RESTful, приложений, используя Spring MVC.
                            Использует Tomcat как встроенный контейнер по умолчанию

- spring-boot-starter-web-services - Starter для использования Spring Web Services

- spring-boot-starter-webflux - Starter для создания WebFlux приложений, используя Spring Framework и
                                поддержку Reactive Web

- spring-boot-starter-websocket - Starter для создания WebSocket приложений, используя Spring Framework и
                                  поддержку WebSocket

---
В дополнение к стартерам приложений можно использовать следующий стартер для добавления компонентов,
готовых к работе:

-spring-boot-starter-actuator - Starter для использования Spring Boot Actuator, который предоставляет
                                возможности готовые для production среды, чтобы помочь нам мониторить
                                и управлять нашим приложением

---
Spring Boot также включает в себя следующие стартеры, которые можно использовать, если мы хотим исключить
или поменять конкретные технические аспекты:

- spring-boot-starter-jetty - Starter для использования Jetty в качестве встроенного servlet контейнера.
                              Альтернатива spring-boot-starter-tomcat

- spring-boot-starter-log4j2 - Starter для использования Log4j2 для логирования.
                               Альтернатива spring-boot-starter-logging

- spring-boot-starter-logging - Starter для логирования, используя Logback.
                                Стартер логирования по умолчанию

- spring-boot-starter-reactor-netty - Starter для использования Reactor Netty в качестве встроенного
                                      reactive HTTP сервера.

- spring-boot-starter-tomcat - Starter для использования Tomcat в качестве встроенного servlet контейнера.
                               Servlet контейнер стартер по умолчанию, используемый в spring-boot-starter-web

- spring-boot-starter-undertow - Starter для использования Undertow в качестве встроенного servlet контейнера.
                                 Альтернатива spring-boot-starter-tomcat

---
См. https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.build-systems.starters
