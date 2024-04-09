package spring.oldboy.bean_post_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
Поскольку наша аннотация нужна будет в RunTime, то RetentionPolicy.RUNTIME,
т.к. по умолчанию RetentionPolicy.CLASS. Аннотация @Target указывает над чем
будет использоваться наша аннотация - над полем (FIELD).
*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectBean {
    /*
    Нам понадобится bean пост-процессор, который будет смотреть аннотации над
    полями bean-ов и если такое поле есть, то он будет искать в контексте
    требуемый bean и внедрять его.

    Т.е. если мы аннотируем данной аннотацией поле:

    @InjectBean
    private StarterConnectionPool starterConnectionPool;

    То наш самописный пост-процессор найдет bean класса StarterConnectionPool и
    сделает инъекцию в данное поле.
    */
}
