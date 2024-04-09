package spring.oldboy.bean_post_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)

/*
В отличие от InjectBean.java, тут мы планируем аннотировать не поля,
а сразу классы, поэтому параметр тип элемента - TYPE.

Суть этой аннотации будет в том, что любой класс помеченный ею, будет
перед вызовом его методов: открывать транзакцию - далее выполнятся
вызванный метод - и далее, после завершения метода, транзакция будет
закрываться.

Конечно, открытие и закрытие транзакции будет лишь эмуляция, но мы
в данном случае пишем тренировочный код и изучаем работу пост-процессоров.
*/
@Target(ElementType.TYPE)
public @interface MyOwnTransaction {
    /*
    Естественно, чтобы обрабатывать классы, помеченные данной аннотацией
    нам снова нужен пост-процессор с длинным названием, чтобы его никто
    без дела не трогал: MyOwnTransactionBeanPostProcessor.java
    */
}
