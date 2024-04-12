package spring.oldboy.integration.annotation;
/*
Создадим свою аннотацию для интеграционных тестов, которая будет
объединять все те классические аннотации, что мы использовали в
других классах, например, в CompanyServiceTestProfileIT.java

Такой способ действий называется мета-аннотированием, см.
DOC/TestContextFramework/MetaAnnotationSupportForTesting.txt
*/
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import spring.oldboy.integration.TestApplicationRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@SpringBootTest(classes = TestApplicationRunner.class)
public @interface IT {
}
