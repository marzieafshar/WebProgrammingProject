package WebProgrammingProject.Project.Repositories;

import WebProgrammingProject.Project.Models.Role;
import WebProgrammingProject.Project.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    User findUserByEmail(String email);

    User findUserById(Long id);

    boolean existsByEmail(String email);




}
