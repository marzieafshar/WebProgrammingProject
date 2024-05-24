package WebProgrammingProject.Project.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenNumber;
    private String tokenString;
    private Date expireDate;
    private Long userId;
    public Token(){

    }
    public Token(String compact, Date expireDate) {
        this.tokenString = compact;
        this.expireDate = expireDate;
    }

    public Token(Long tokenNumber, String compact, Date expireDate) {
        this.tokenNumber = tokenNumber;
        this.tokenString = compact;
        this.expireDate = expireDate;
    }
}
