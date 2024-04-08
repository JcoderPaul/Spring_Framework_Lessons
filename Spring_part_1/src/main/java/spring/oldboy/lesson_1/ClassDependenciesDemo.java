package spring.oldboy.lesson_1;
/*
Пример зависимостей объектов. Чуть подробней DOC/DI/DI_step_by_step.txt.
Проблема не так сильно видна пока у нас 1-3 связных объекта, но в реальном
приложении таких связей гораздо больше и уследить за их взаимным
пересечением (создание, внедрение и т.д) становится сложно.
*/
import spring.oldboy.pool.StarterConnectionPool;
import spring.oldboy.repository.CompanyRepository;
import spring.oldboy.repository.UserRepository;
import spring.oldboy.service.UserService;

public class ClassDependenciesDemo {
    public static void main(String[] args) {
        /* ConnectionPool взаимодействует с БД см. схему DOC/ObjectDependencies.jpg */
        StarterConnectionPool starterConnectionPool = new StarterConnectionPool();
        /* ...Repository не могут функционировать без объекта ConnectionPool */
        UserRepository userRepository = new UserRepository(starterConnectionPool);
        CompanyRepository companyRepository = new CompanyRepository(starterConnectionPool);
        /* Объект UserService не может работать без объектов ...Repository */
        UserService userService = new UserService(userRepository, companyRepository);
    }
}
