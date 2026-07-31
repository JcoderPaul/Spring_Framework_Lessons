### Логирование транзакций

Spring выполняет логирование практически всех действий с транзакциями: для этого надо включить уровень логирования
DEBUG для пакета org.springframework.transaction, и если используете hibernate - org.hibernate.transaction.

Пример части **application.yml** или **application.properties**:
```Java
   logging:
      level:
         org.springframework.orm.jpa: DEBUG
         org.springframework.transaction: DEBUG
```

Также можно узнать, что происходит с транзакциями программно, обращаясь к ThreadLocal переменным, например:
- [TransactionSynchronizationManager.isActualTransactionActive()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/support/TransactionSynchronizationManager.html#isActualTransactionActive());
- [TransactionAspectSupport.currentTransactionStatus()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/interceptor/TransactionAspectSupport.html#currentTransactionStatus());
