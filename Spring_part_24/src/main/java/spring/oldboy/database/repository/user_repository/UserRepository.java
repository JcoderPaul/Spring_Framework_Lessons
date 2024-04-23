package spring.oldboy.database.repository.user_repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import spring.oldboy.database.entity.Role;
import spring.oldboy.database.entity.User;
import spring.oldboy.dto.PersonalInfoTwo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
                                        FilterUserRepository,
                                        RevisionRepository<User, Long, Integer> {

    @Query("select u from User u " +
            "where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllBy(String firstname, String lastname);

    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",
            nativeQuery = true)
    List<User> findAllByUsername(String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u " +
            "set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    Optional<User> findTopByOrderByIdDesc();

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(value = "select u from User u",
            countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);

    @Query(value = "SELECT firstname, " +
            "lastname, " +
            "birth_date birthDate " +
            "FROM users " +
            "WHERE company_id = :companyId",
            nativeQuery = true)
    List<PersonalInfoTwo> findAllByCompanyId(Integer companyId);

    Optional<User> findByUsername(String username);
}
