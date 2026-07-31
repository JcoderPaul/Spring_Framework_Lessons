### Spring @Transactional с применением AspectJ

Если мы хотим использовать @Transactional аннотацию для частных методов, мы не можем использовать режим
прокси-сервера Spring по умолчанию — он работает только для общедоступных методов, которые вызываются из
клиентского кода.

Для самовызываемых методов нам нужно использовать AspectJ.

Это может быть тот случай, например, когда мы хотим сохранить входящие данные и отправить письмо, используя
эти данные, в рамках транзакции. Если сохранить данные не удалось, мы не хотим отправлять письмо. Если отправка
письма не удалась, мы не хотим потерять полученные данные. Следовательно, мы хотим сохранить данные в отдельной
транзакции, аннотируя наш метод saveData(..) с помощью @Transactional(propagation = Propagation.REQUIRES_NEW).

---
### Настройка Spring для использования AspectJ

В @Configuration классе нам просто нужно установить режим рекомендации AspectJ для конфигурации управления транзакциями:

```Java
    @Configuration
    @ComponentScan
    @EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
    class ApplicationConfiguration {
        // ...
    }
```

---
### Настройка Maven для запуска компилятора AspectJ

Далее нам нужно использовать компилятор AspectJ, поэтому давайте добавим необходимые зависимости:

```XML
    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.7.4</version>
        </dependency>
    
        <!-- In this example we use JPA for persistence. -->
        <dependency>
            <groupId>org.hibernate.javax.persistence</groupId>
            <artifactId>hibernate-jpa-2.1-api</artifactId>
            <version>1.0.0.Final</version>
        </dependency>
    
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>4.0.6.RELEASE</version>
        </dependency>
    </dependencies>
```

Сам компилятор AspectJ затем можно включить с помощью:

```XML
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>aspectj-maven-plugin</artifactId>
                <version>1.6</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <!-- <goal>test-compile</goal> -->
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <complianceLevel>1.7</complianceLevel>
                    <forceAjcCompile>true</forceAjcCompile>
                    <weaveDirectories>
                        <weaveDirectory>${project.build.outputDirectory}</weaveDirectory>
                    </weaveDirectories>
                    <aspectLibraries>
                        <aspectLibrary>
                            <groupId>org.springframework</groupId>
                            <artifactId>spring-aspects</artifactId>
                        </aspectLibrary>
                    </aspectLibraries>
                </configuration>
            </plugin>
        </plugin>
    </build>
```

Если мы не используем <forceAjcCompile> и не устанавливаем <weaveDirectory>AspectJ, он не будет включать код
транзакции в сгенерированный байт-код Groovy. Вместо этого он предупредит нас: «Источники не найдены» -
No sources found skipping aspectJ compile, пропуская компиляцию аспекта при запуске Maven. Это связано с тем,
что AspectJ-maven-plugin по умолчанию обрабатывает каталог src/main/java, но не src/main/groovy.

---
### Как выглядит код, сгенерированный AspectJ

Для полноты картины давайте посмотрим, как AspectJ обернул метод saveData(..) транзакцией. Используя декомпилятор
Procyon, мы можем увидеть:

```Java
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveData(final SomeData data) {
        ((AbstractTransactionAspect)AnnotationTransactionAspect.
        aspectOf()).
        ajc$around$org_springframework_transaction_aspectj_AbstractTransactionAspect$1$2a73e96c(
                (Object)this,
                (AroundClosure)new DataService$AjcClosure3(new Object[] { this, data }),
                DataService.ajc$tjp_1);
    }
```
