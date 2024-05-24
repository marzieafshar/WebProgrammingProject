package WebProgrammingProject.Project.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Builder
public class TokenDTO {

    @Id
    private Long tokenNumber;
    private String tokenString;
    private Date expireDate;
    public TokenDTO(Long tokenNumber, String compact, Date expireDate) {
        this.tokenNumber = tokenNumber;
        this.tokenString = compact;
        this.expireDate = expireDate;
    }

    public TokenDTO() {

    }
}
