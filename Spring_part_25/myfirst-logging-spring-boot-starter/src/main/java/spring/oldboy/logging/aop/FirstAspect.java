package spring.oldboy.logging.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class FirstAspect {

    /*
    @within - проверяем аннотации на уровне классов см. DOC/AOP_Articles/AOP_in_SpringBoot.txt,
    т.е. например, на нашем уровне контроллеров есть 3-и класса помеченных как @Controller, вот
    именно на них, после сканирования всего приложения произойдет срез и внедрение сквозной
    логики.

    Еще раз, где в описании выражения есть знак '@' мы ищем аннотации для внедрения среза:
    @Controller, @Repository и т.д.
    */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    /*
    within - тут выражение БЕЗ знака '@', и мы уже проверяем имя класса, как всегда знак '*'
    означает что-то перед/после, например, User в UserService. Т.е. в данном примере нас
    интересуют все классы сервисного слоя, а они имеют названия заканчивающиеся на Service,
    см. spring/oldboy/service

    Так же, если будут классы находиться в подкаталогах, то выражение станет (лишняя точка):
    "within(spring.oldboy.service..*Service)"

    Естественно мы можем просто отдать в работу по срезу весь сервисный слой и тогда получим:
    "within(spring.oldboy.service.*)" или "within(spring.oldboy.service..*)"
    */
    @Pointcut("within(spring.oldboy.service.*Service)")
    public void isServiceLayer() {
    }

    /*
    В случае когда мы не можем задать условие по названию класса, или пакета, а тем более когда класс не аннотирован
    (классы слоя репозиториев необязательно аннотировать как @Repository), тем более, что это могут быть интерфейсы,
    а их реализация может лежать в другом месте приложения. В данном случае используют следующий синтаксис:

    this - работает с AOP proxy классом;
    target - работает с целевым объектом, исходным объектом класса, который обернут в proxy;

    В обоих предложенных вариантах (и target и this) мы ищем все классы реализующие интерфейс Repository -
    https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html

    @Pointcut("target(org.springframework.data.repository.Repository)")
    */
    @Pointcut("this(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer() {
    }

    /*
    @annotation - мы снова видим знак '@', и тут, мы ищем УЖЕ аннотированные МЕТОДЫ, например, методы помеченные
    как @GetMapping в нашем случае. Тут есть неудобство - Spring будет сканировать все bean-ы. Мы можем ввести
    ограничения используя знаки логики '&&' - и,  '||' - или,  '!' - не.

    В нашем случае мы знаем, что @GetMapping есть только на слое контроллеров. Вносим дополнительное условие -
    искать только среди контроллеров. И в данном случае произошло переиспользование одного pointcut-a в другом.
     */
    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    /*
    Мы можем искать методы по наличию тех или иных (.. любых) параметров в методе, в нашем примере мы хотим
    найти только те методы, где параметр Model идет первым, далее при наличии ',' мы можем указать есть ли
    еще параметры в методе:

    args - проверяем тип параметра метода, вариант с одним параметром - args(org.springframework.ui.Model) ;
    * - любой тип параметра, типы 2-ух из 3-х неизвестны - args(org.springframework.ui.Model,*,*) ;
    .. - 0 или любое количество параметров метода - args(org.springframework.ui.Model,..) ;

    И снова можем применить дополнительную фильтрацию (условия) если знаем какой областью приложения хотим
    ограничить внедрение сквозной логики.
    */
    @Pointcut("isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam() {
    }

    /*
    @args - в данном случае немного хитро, т.к. будут сканироваться аннотации над типом параметров методов, т.е.
    например у нас есть UserController:

    public String create(@ModelAttribute
                         @Validated ({Default.class, CreateAction.class})
                         UserCreateEditDto userCreateEditDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
    . . .
    }

    В нем есть параметр класса (типа, type) UserCreateEditDto и вот именно его аннотации и будет сканировать @args,
    а не аннотации над самим параметром userCreateEditDto, именно над типом :

    @Value
    @FieldNameConstants
    @UserInfo(groups = CreateAction.class)
    public class UserCreateEditDto {
    . . .
    }

    Например, мы хотим проверять только аннотации @UserInfo у первого параметра методов.

    Естественно в методе может быть масса параметров и их мы можем отметить, как:
    * - любой тип параметра ;
    .. - 0 или более параметров любого типа ;
    */
    @Pointcut("isControllerLayer() && @args(spring.oldboy.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation() {
    }

    /* bean - ищем bean-ы с конкретным именем, знак '*' тут работает, как и раньше */
    @Pointcut("bean(*Service)")
    public void isServiceLayerBean() {
    }

    /*
    Lesson 118:

    execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)

    Наиболее часто используемое выражение для определения jointpoint. При использовании выражения execution возможно
    указывать пакет, имя класса, название метода, видимость метода, тип возвращаемого объекта и тип аргументов, см.
    DOC/AOP_Articles/AOP_in_SpringBoot.txt

    Например:

    - execution(String com.package.subpackage.Classname.someMethod(..)) - определяет вызов метода someMethod класса
      com.package.subpackage.Classname с любым количеством аргументов и возвращающий строку ;

    - execution(* com.package.subpackage.Classname.*(..)) – вызов любого метода класса
      com.package.subpackage.Classname ;

    - execution(* someMethod(..)) – вызов метода с именем someMethod у любого класса ;

    В нашем пример мы ищем (вызываем): 'public' метод, возвращаемый методом тип нам не важен - '*', но можем указать
    и конкретно (Long, Integer и т.д.), далее указываем класс в котором может находиться метод -
    spring.oldboy.service.*Service, и наконец, через '.' мы прописываем метод с любым типом параметров, но можем и
    конкретизировать (Long, String и т.п.).
    */

    @Pointcut("execution(public * spring.oldboy.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }
}
