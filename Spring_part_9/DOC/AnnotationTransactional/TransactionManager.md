### TransactionManager

На данный момент TransactionManager, маркировочный интерфейс, не содержащий никаких методов. Его наследуют
ReactiveTransactionManager и PlatformTransactionManager.

Рассмотрим внимательней последний:

```Java
  public interface PlatformTransactionManager extends TransactionManager {
  
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;
  
    void commit(TransactionStatus status) throws TransactionException;
  
    void rollback(TransactionStatus status) throws TransactionException;
  }
```

Интерфейс содержит всего 3 метода - создание транзакции, commit и rollback.

Spring предоставляет абстрактный класс AbstractPlatformTransactionManager, который реализует требования по
propagation. Наследники этого класса по-разному реализуют его абстрактные методы, так как это довольно сильно
зависит от используемой технологии.

Стоит отметить, что в своей работе AbstractPlatformTransactionManager и его подклассы активно используют
org.springframework.transaction.support.TransactionSynchronizationManage для синхронизации и хранения
метаинформации, включая connection.

Хранение информации осуществляется в наборе статических ThreadLocal переменных:
```Java
  private static final ThreadLocal<Map<Object, Object>> resources = new NamedThreadLocal<>("Transactional resources");
  
  private static final ThreadLocal<Set<TransactionSynchronization>> synchronizations = new NamedThreadLocal<>("Transaction synchronizations");
  
  private static final ThreadLocal<String> currentTransactionName = new NamedThreadLocal<>("Current transaction name");
  
  private static final ThreadLocal<Boolean> currentTransactionReadOnly = new NamedThreadLocal<>("Current transaction read-only status");
  
  private static final ThreadLocal<Integer> currentTransactionIsolationLevel = new NamedThreadLocal<>("Current transaction isolation level");
  
  private static final ThreadLocal<Boolean> actualTransactionActive = new NamedThreadLocal<>("Actual transaction active");
```
