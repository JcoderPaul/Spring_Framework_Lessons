package spring.oldboy.database.repository.company_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.oldboy.database.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("select c from Company c " +
            "join fetch c.locales cl " +
            "where c.name = :name2")
    Optional<Company> findByName(@Param("name2") String name);

    List<Company> findAllByNameContainingIgnoreCase(String fragment);
}
