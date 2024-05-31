package WebProgrammingProject.Project.Repositories;


import WebProgrammingProject.Project.Models.TokenDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDTORepository extends JpaRepository<TokenDTO,Long> {

}
