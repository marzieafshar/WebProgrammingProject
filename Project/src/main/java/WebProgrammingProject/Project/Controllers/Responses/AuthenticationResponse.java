package WebProgrammingProject.Project.Controllers.Responses;


import WebProgrammingProject.Project.Models.TokenDTO;
import WebProgrammingProject.Project.Models.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthenticationResponse {
    private TokenDTO dto;
    private User user;

    public AuthenticationResponse(TokenDTO dto, User user) {
        this.dto = dto;
        this.user = user;
    }





}
