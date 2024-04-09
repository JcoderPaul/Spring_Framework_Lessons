package spring.oldboy.bean_post_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
Поскольку наша аннотация нужна будет в RunTime, то RetentionPolicy.RUNTIME,
т.к. по умолчанию RetentionPolicy.CLASS. Аннотация @Target указывает над чем
будет использоваться наша аннотация - над классом (TYPE).
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DropBeanBeforeInit {
    /*
    Суть данной аннотации показать, что может быть
    при внедрении подмены до этапа инициализации bean-a
    */
}
