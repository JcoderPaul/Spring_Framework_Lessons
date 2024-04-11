package spring.oldboy.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/*
Наш класс условий должен реализовывать интерфейс Condition,
и переопределять единственный метод *.matches(), который и
определит, будет ли загружаться в контекст JpaConfiguration
bean
*/
public class JpaCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        /*
        Попытаемся загрузить PostgreSQL драйвер, если это получается - true и JpaConfiguration
        помещается в контекст контейнер, если драйвер не загружен - false, и обратная ситуация.

        Похожую конфигурацию кода мы встречали при работе с аннотацией @Profile и если заглянуть
        внутри интерфейса Profile то мы увидим:

        ****************************************************************************************
        @Target({ElementType.TYPE, ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        @Documented
        @Conditional(ProfileCondition.class)
        public @interface Profile {
            String[] value();
        }
        ****************************************************************************************

        !!! Наша аннотация @Condition !!! Как мы знаем ею можно помечать TYPE, т.е. и интерфейсы
        тоже. А занырнув в ProfileCondition.class (см. выше) мы увидим очень похожую конструкцию
        проверяющую условия на true / false, только проверка идет на аннотацию @Profile. И там и
        там у нас есть доступ к ConditionContext и AnnotatedTypeMetadata.
        */
        try {
            context.getClassLoader().loadClass("org.postgresql.Driver");
            System.out.println("Driver is found!");
            /* Класс найден - true */
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!");
            /* Класс не найден - исключение - false */
            return false;
        }
    }
}
