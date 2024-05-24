package WebProgrammingProject.Project.Repositories;



import WebProgrammingProject.Project.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findTokenByTokenString(String token);

    List<Token> findTokenByUserId(Long userId);

}
