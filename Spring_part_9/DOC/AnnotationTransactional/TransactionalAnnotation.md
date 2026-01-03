**Исходная статья:** https://habr.com/ru/articles/532000/

---
### @Transactional в Spring

Аннотация [@Transactional находится в пакете org.springframework.transaction.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html), т.е. ее полное имя org.springframework.transaction.annotation.Transactional и является частью Spring Framework начиная с версии 1.2.

**!!! НЕ ПУТАТЬ с javax.transaction.Transactional !!!**

Собственная аннотация Spring предоставляет более расширенные возможности настройки, которые рассматриваются дальше.
Аннотацию javax.transaction.Transactional Spring также поддерживает, но лучше их не смешивать и, если создаете
приложение на Spring, использовать родные аннотации.

Для того чтобы добавить в проект пакет, требуется прописать зависимость:

Maven:
```Maven
 <dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-tx</artifactId>
 </dependency>
```

Gradle:
```Gradle
 compile group: 'org.springframework', name: 'spring-tx', version: '5.3.2'
```

Но с приходом Spring Boot так делать уже не требуется - этот пакет добавлен во все требуемые starter-ы как
транзитивная зависимость и, когда вы добавляете, например, spring-boot-starter-data-jpa, то spring-tx
подтягивается автоматически через spring-data-jpa.

---
### Инфраструктура для обработки @Transactional

До появления Spring Boot, чтобы включить поддержку @Transactional мы добавляли над конфигурацией аннотацию [@EnableTransactionManager](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/EnableTransactionManagement.html), которая является также частью пакета org.springframework.transaction.annotation.

Эта аннотация имеет следующие настройки:
- **proxyTargetClass(по умолчанию, false)** - будет ли прокси создаваться через CGLIB (true) или через interface-based proxies (false).

Обратите внимание, что, если поставить true, ВСЕ объекты Spring (beans) будут создаваться через CGLIB, не только те, что помечены @Transactional

- **mode (по умолчанию AdviceMode.PROXY)** - как будут применены Advise. Возможные варианты - AdviceMode.PROXY или AdviceMode.ASPECTJ.

Если выбрать AspectJ и корректно его настроить, то при компиляции будет сгенерирован код так, что тело метода будет уже обернуто кодом, управляющим транзакцией.

Если выбран AdviceMode.PROXY, то будет использован стандартный механизм создания proxy объектов. См. пример с [AspectJ](https://docs.spring.io/spring-framework/reference/core/aop/using-aspectj.html) и [TransactionalWithAspectJ](../TransactionalOnSpring/TransactionalWithAspectJ.md).

- **order** - указывает, когда будет применен advice.

  По умолчанию, LOWEST_PRECEDENCE - т.е. он будет вызван последним в цепочке advice. Это может быть важно, когда вы добавляете собственные advice, в которых есть работа с базой данных.

---
### Инфраструктура для обработки аннотации @Transactional

```Java
 @Target(ElementType.TYPE)
 @Retention(RetentionPolicy.RUNTIME)
 @Documented
 @Import(TransactionManagementConfigurationSelector.class)
 public @interface EnableTransactionManagement {
 ...
 }
```

Она содержит селектор TransactionManagementConfigurationSelector - работа которого будет рассмотрена ниже.

С приходом Spring Boot необходимость в аннотации @EnableTransactionManager отпала. Теперь это перешло в
ответственность Spring Boot. Он в свою очередь делает следующее - когда вы добавляете в проект зависимость
spring-boot-starter-..., то подтягивается транзитивная зависимость - spring-boot-autoconfigure, которая
содержит в файле spring.factories список авто-конфигураций.

Это файл: org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration, содержит конфигурацию,
которая будет загружена при подъеме контекста. Полный текст TransactionAutoConfiguration для удобства приведен
ниже:

```Java
 @Configuration(proxyBeanMethods = false)
 @ConditionalOnClass(PlatformTransactionManager.class)
 @AutoConfigureAfter({JtaAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
     DataSourceTransactionManagerAutoConfiguration.class, Neo4jDataAutoConfiguration.class})
 @EnableConfigurationProperties(TransactionProperties.class)
 public class TransactionAutoConfiguration {
 
   @Bean
   @ConditionalOnMissingBean
   public TransactionManagerCustomizers platformTransactionManagerCustomizers(
       ObjectProvider<PlatformTransactionManagerCustomizer<?>> customizers) {
     return new TransactionManagerCustomizers(customizers.orderedStream().collect(Collectors.toList()));
   }
 
   @Bean
   @ConditionalOnMissingBean
   @ConditionalOnSingleCandidate(ReactiveTransactionManager.class)
   public TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
     return TransactionalOperator.create(transactionManager);
   }
 
   @Configuration(proxyBeanMethods = false)
   @ConditionalOnSingleCandidate(PlatformTransactionManager.class)
   public static class TransactionTemplateConfiguration {
 
     @Bean
     @ConditionalOnMissingBean(TransactionOperations.class)
     public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
       return new TransactionTemplate(transactionManager);
     }
   }
 
   @Configuration(proxyBeanMethods = false)
   @ConditionalOnBean(TransactionManager.class)
   @ConditionalOnMissingBean(AbstractTransactionManagementConfiguration.class)
   public static class EnableTransactionManagementConfiguration {
 
     @Configuration(proxyBeanMethods = false)
     @EnableTransactionManagement(proxyTargetClass = false)
     @ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "false",
         matchIfMissing = false)
     public static class JdkDynamicAutoProxyConfiguration {
     }
 
     @Configuration(proxyBeanMethods = false)
     @EnableTransactionManagement(proxyTargetClass = true)
     @ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
         matchIfMissing = true)
     public static class CglibAutoProxyConfiguration {
     }
   }
 }
```

Отметим работу авто-конфигурации только в разрезе наиболее интересных моментов:
- Данная авто-конфигурация будет работать только, если в classpath есть класс PlatformTransactionManager,
  о работе которого будет написано ниже. Здесь же создается TransactionalOperator, используемый в реактивном
  стеке.

- Наиболее важной частью является статический класс EnableTransactionManagementConfiguration. Он содержит
  два подкласса, отличаются они только настройкой spring.aop.proxy-target-class:
  - если spring.aop.proxy-target-class = true, то применяется аннотация @EnableTransactionManagement(proxyTargetClass = true);
  - если spring.aop.proxy-target-class = false, то применяется аннотация @EnableTransactionManagement(proxyTargetClass = false);

Обратите внимание, что по умолчанию для всех современных spring-boot проектах spring.aop.proxy-target-class=true

Поэтому будет использован механизм CGLIB для создания proxy.

---
### Обработка аннотации @Transactional

И так повторимся, создается:
```Java
 @Target(ElementType.TYPE)
 @Retention(RetentionPolicy.RUNTIME)
 @Documented
 @Import(TransactionManagementConfigurationSelector.class)
 public @interface EnableTransactionManagement {
 ...
 }
```

Здесь используется аннотация @Import. Она обычно используется для обработки следующих трех типов компонентов
(component): @Configuration, ImportSelector, ImportBeanDefinitionRegistrar.

TransactionManagementConfigurationSelector, который здесь загружается, принадлежит к ImportSelector. При загрузке
контекста он, основываясь на настройках EnableTransactionManagement, определяет какие классы будут подгружаться
дальше. Ниже приведена часть кода TransactionManagementConfigurationSelector:

```Java
 public class TransactionManagementConfigurationSelector
     extends AdviceModeImportSelector<EnableTransactionManagement> {
 
   @Override
   protected String[] selectImports(AdviceMode adviceMode) {
     switch (adviceMode) {
       case PROXY:
         return new String[]{AutoProxyRegistrar.class.getName(),
             ProxyTransactionManagementConfiguration.class.getName()};
       case ASPECTJ:
         return new String[]{determineTransactionAspectClass()};
       default:
         return null;
     }
   }
 .....
```

Здесь используются следующие классы для конфигураций:

- **AutoProxyRegistrar** - класс для регистрации средств создания bean-ов.
- **ProxyTransactionManagementConfiguration** - класс для настройки ProxyTransactionManagement.

Посмотрим, что внутри ProxyTransactionManagementConfiguration:

```Java
 @Configuration(proxyBeanMethods = false)
 @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
 public class ProxyTransactionManagementConfiguration
     extends AbstractTransactionManagementConfiguration {
 
   @Bean(name = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME)
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor(
       TransactionAttributeSource transactionAttributeSource, TransactionInterceptor transactionInterceptor) {
 
     BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
     advisor.setTransactionAttributeSource(transactionAttributeSource);
     advisor.setAdvice(transactionInterceptor);
     if (this.enableTx != null) {
       advisor.setOrder(this.enableTx.<Integer>getNumber("order"));
     }
     return advisor;
   }
 
   @Bean
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public TransactionAttributeSource transactionAttributeSource() {
     return new AnnotationTransactionAttributeSource();
   }
 
   @Bean
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public TransactionInterceptor transactionInterceptor(TransactionAttributeSource transactionAttributeSource) {
     TransactionInterceptor interceptor = new TransactionInterceptor();
     interceptor.setTransactionAttributeSource(transactionAttributeSource);
     if (this.txManager != null) {
       interceptor.setTransactionManager(this.txManager);
     }
     return interceptor;
   }
 }
```

- **1** - Первым создается bean transactionAttributeSource из класса AnnotationTransactionAttributeSource. Он реализует
интерфейс TransactionAttributeSource:

```Java
 public interface TransactionAttributeSource {
 
   default boolean isCandidateClass(Class<?> targetClass) {
     return true;
   }
 
   @Nullable
   TransactionAttribute getTransactionAttribute(Method method,
                                                @Nullable Class<?> targetClass);
 }
```

Этот bean применяется для проверки: используется ли где-то @Transactional и для получения метаданных
(например, propagation) для аннотированных методов или классов.

- **2** - Дальше создается TransactionInterceptor, внутри которого и будет происходить магия транзакции, которая
рассматривается ниже.

- **3** - И наконец, создается BeanFactoryTransactionAttributeSourceAdvisor, внутри которого помещается
TransactionInterceptor.

BeanFactoryTransactionAttributeSourceAdvisor - это обычный PointcutAdvisor (который объединил в одну сущность
advisor and pointcut. Пример использования см.:

- https://programmersought.com/article/5633162993/
- https://mkyong.com/spring/spring-aop-example-pointcut-advisor/

Если вкратце описать принцип их действия, то pointcut определяет, какие классы и методы в них будут проксированы, а advice - какой код будет выполняться.

У нас, в качестве pointcut будет использован TransactionAttributeSource, а в качестве advice - TransactionInterceptor.

- **4** - Теперь на себя всю работу берет InfrastructureAdvisorAutoProxyCreator, который является BeanPostProcessor-ом
см. схему иерархии для класса InfrastructureAdvisorAutoProxyCreator:

![InfrastructureAdvisorAutoProxyCreator.jpg](../DOC/TransactionalInSpring/InfrastructureAdvisorAutoProxyCreator.jpg)

Обработка @Transactional выполняется по обычным правилам Spring и никакой особой магии здесь нет. Примерная схема
работы см.: 

![SchemeOfWorkTransactional.jpg](../DOC/TransactionalOnSpring/SchemeOfWorkTransactional.jpg)

---
### Обработка @Transactional

Прежде чем перейти к рассмотрению порядка обработки, давайте посмотрим, какие настройки предоставляет нам эта
аннотация. Здесь рассмотрена только часть из них. Остальные [можно посмотреть в документации](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html).

Наиболее интересные **настройки @Transactional**:
- **propagation** - способ "распространения" транзакций, выделяются следующие способы:
   - **MANDATORY** - если есть текущая активная транзакция - выполняется в ней, иначе выбрасывается исключение;
   - **NESTED** - выполняется внутри вложенной транзакции, если есть активная, если нет активной - то аналогично
              REQUIRED;
   - **NEVER** - выполняется вне транзакции, если есть активная - выбрасывается исключение;
   - **NOT_SUPPORTED** - выполняется вне транзакции - если есть активная, она приостанавливается;
   - **REQUIRED** - (значение по умолчанию) - если есть активная, то выполняется в ней, если нет, то создается новая
                транзакция;
   - **REQUIRES_NEW** - всегда создается новая транзакция, если есть активная - то она приостанавливается;
   - **SUPPORTS** - если есть активная - то выполняется в ней, если нет - то выполняется не транзакционно;

- Правила управления откатом:
   - **noRollbackFor и noRollbackForClassName** - определяет исключения, при которых транзакция НЕ будет откачена;
   - **rollbackFor и rollbackForClassName** - определяет исключения, при которых транзакция БУДЕТ откачена;

Перед тем как перейти к TransactionInterceptor, давайте вспомним, как мы работали с транзакциями до Spring,
пример:

```Java
 Connection connection = DriverManager.getConnection(...);
     try {
           connection.setAutoCommit(false);
           PreparedStatement firstStatement = connection.prepareStatement(...);
 
           firstStatement.executeUpdate();
 
           PreparedStatement secondStatement = connection.prepareStatement(...);
 
           secondStatement.executeUpdate();
           connection.commit();
     } catch (Exception e) {
           connection.rollback();
     }
```

Порядок работы был следующим:

- создаем соединение - DriverManager.getConnection(...);
- выполняем необходимые запросы;
- если не было ошибок - выполняем commit (connection.commit());
- если была ошибка - откатываем изменения;

Вернемся к TransactionInterceptor. Основным методом является - invoke, который делегирует работу родительскому
методу invokeWithinTransaction класса TransactionalAspectSupport, сокращенный код приведен ниже:

```Java
 protected Object invokeWithinTransaction(Method method,
                                          Class<?> targetClass,
                                          final InvocationCallback invocation)
 
   // получаем TransactionManager tm и TransactionAttribute txAttr
   // ...
 
   if (this.reactiveAdapterRegistry != null &&
       tm instanceof ReactiveTransactionManager) {
     //код для работы с реактивным стэком
     // ...
   }
 
 PlatformTransactionManager ptm = asPlatformTransactionManager(tm);
 final String joinpointIdentification =
   methodIdentification(method, targetClass, txAttr);
 
 if (txAttr == null ||
     !(ptm instanceof CallbackPreferringPlatformTransactionManager)) {
   // начинаем транзакцию, если нужно
   TransactionInfo txInfo = createTransactionIfNecessary(ptm, txAttr,
                                                         joinpointIdentification);
 
   Object retVal;
   try {
     // выполняем работу внутри транзакции
     retVal = invocation.proceedWithInvocation();
   } catch (Throwable ex) {
     // откатываемся, если нужно
     completeTransactionAfterThrowing(txInfo, ex);
     throw ex;
   } finally {
     // чистим ThreadLocal переменные
     cleanupTransactionInfo(txInfo);
   }
 
   if (retVal != null && vavrPresent && VavrDelegate.isVavrTry(retVal)) {
     //код для библиотеки vavr
     // ...
   }
 
   // выполняем commit, если не было ошибок
   commitTransactionAfterReturning(txInfo);
   return retVal;
 } else {
   // код для WebSphere
   // ...
 }
 }
```

Если посмотреть внимательно, то этот код повторяет предыдущую логику, ничего нового:

- получаем соединение/транзакцию - createTransactionIfNecessary;
- выполняем необходимые запросы - invocation.proceedWithInvocation;
- если не было ошибок - выполняем commitTransactionAfterReturning;
- если была ошибка - откатываем изменения - completeTransactionAfterThrowing;

Рассмотрим внимательно каждую часть:

**1. - Получение транзакции;**

```Java
 protected TransactionInfo createTransactionIfNecessary (PlatformTransactionManager tm,
                                                         TransactionAttribute txAttr,
                                                         final String joinpointIdentification) {
 
   if (txAttr != null && txAttr.getName() == null) {
     txAttr = new DelegatingTransactionAttribute(txAttr) {
       @Override
       public String getName() {
         return joinpointIdentification;
       }
     };
   }
 
   TransactionStatus status = null;
   if (txAttr != null) {
     if (tm != null) {
       status = tm.getTransaction(txAttr);
     }
   }
   return prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);
 }
```

Из важных частей здесь можно отметить:

- вызов transactionalManager.getTransaction(...) - работа с TransactionalManager, которая будет описана ниже;
- вызов prepareTransactionInfo, в котором выполняется txInfo.bindToThread();

Метод prepareTransactionInfo устанавливает TransactionInfo в ThreadLocal переменную transactionInfoHolder, из
которой теперь всегда можно получить статус транзакции через статический метод currentTransactionStatus

**2. - Управление откатом изменений;**

```Java
 protected void completeTransactionAfterThrowing(@Nullable TransactionInfo txInfo, Throwable ex) {
   if (txInfo != null && txInfo.getTransactionStatus() != null) {
     if (txInfo.transactionAttribute != null &&
         txInfo.transactionAttribute.rollbackOn(ex)) {
       try {
         txInfo.getTransactionManager()
           .rollback(txInfo.getTransactionStatus());
       } catch (Exception ex) {
         // ...
       }
     } else {
       try {
         txInfo.getTransactionManager()
           .commit(txInfo.getTransactionStatus());
       } catch (Exception ex) {
         // ...
       }
     }
   }
 }
```

Если транзакция активна, то проверяем, надо ли ее откатывать при этой ошибке, если не надо, то выполняем фиксацию
транзакции commit(txInfo.getTransactionStatus()). Проверка исключений выполняется в txInfo.transactionAttribute.rollbackOn(ex).
Выигрывает наиболее близкое по иерархии исключений требование.

**3. - Фиксация транзакции;**

```Java
 protected void commitTransactionAfterReturning(TransactionInfo txInfo) {
   if (txInfo != null &&
       txInfo.getTransactionStatus() != null) {
     txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
   }
 }
```

Просто выполняется фиксация, если транзакция активна.

---

- [TransactionManager - см. что это такое](../DOC/TransactionalInSpring/TransactionManager.md);
- [Как выводить логи транзакций](../DOC/TransactionalOnSpring/TransactionLogs.md);
- [Работа с транзакциями через TransactionTemplate](./TransactionTemplate.md);

---
### Обработка ошибок в HibernateTransactionManager

При работе с hibernate, даже если вы поймали ошибку и обработали ее, вы не сможете уже зафиксировать транзакцию,
так как она будет помечаться как rollbackOnly (в отличие от работы с JdbcTemplate, например).

**Почему происходит именно так?**

При работе с JPA вы не управляете последовательностью выполнения запросов и поэтому, если у вас произошла ошибка,
hibernate не может восстановить правильный контекст и единственное, что ему остается - пометить транзакцию как
rollbackOnly.

**Как это работает?**

Если hibernate ловит ошибку, внутри себя он вызывает:

```Java
 TransactionDriverControl().markRollbackOnly()
```

Который ставит флаг - rollbackOnly = true, даже если вы поймали эту ошибку и обработали. По логике Spring, если
нет исключения, то вызывается PlatformTransactionManager.commit. При вызове PlatformTransactionManager.commit
получаем статус транзакции, который внутри хранит флаг:

```Java
 unexpectedRollback = status.isGlobalRollbackOnly(); //true
 doCommit(status); //вызывается все равно
 
 if (unexpectedRollback) { //кидаем исключение
   throw new UnexpectedRollbackException(
     "Transaction silently rolled back because it has been marked as rollback-only");
 }
```

doCommit - все равно вызывается и мы могли бы ожидать, что хоть что-то зафиксируется, но hibernate не имеет теперь
консистентных данных, поэтому внутри hibernate есть такой код:

```Java
 @Override
 public void commit() {
   try {
     //хоть метод и называется commit, но включает в себя логику отката
     if ( rollbackOnly ) {
 
       try {
         rollback();
         //...
         return;
       }
       catch (RollbackException e) {
         throw e;
       }
       catch (RuntimeException e) {
         throw e;
       }
     }
 
     JdbcResourceLocalTransactionCoordinatorImpl.this.beforeCompletionCallback();
     jdbcResourceTransaction.commit();
     // ...
```

То есть ответственность за откат здесь переходит на сторону Hibernate, а не Spring, хотя Spring и не вызывает PlatformTransactionManager.rollback
