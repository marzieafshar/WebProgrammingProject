package WebProgrammingProject.Project.Services;


import WebProgrammingProject.Project.Config.JWTService;
import WebProgrammingProject.Project.Controllers.Requests.TokenRequest;
import WebProgrammingProject.Project.Models.Role;
import WebProgrammingProject.Project.Models.Token;
import WebProgrammingProject.Project.Models.TokenDTO;
import WebProgrammingProject.Project.Repositories.TokenDTORepository;
import WebProgrammingProject.Project.Repositories.TokenRepository;
import WebProgrammingProject.Project.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static WebProgrammingProject.Project.Services.AuthenticationService.loggedInUser;

@Service
@RequiredArgsConstructor
public class TokensService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenDTORepository tokenDTORepository;
    @Autowired
    private JWTService jwtService;
    public ResponseEntity<?> listOfUsers(){
        try {
            if(loggedInUser.getRole().equals(Role.USER))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You are not Admin!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No user has logged in yet.");
        }

        return ResponseEntity.ok(userRepository.findAll());
    }


    public ResponseEntity<?> makeApiToken(TokenRequest request){
        try {
            Token token = jwtService.customToken(loggedInUser, request);
            token.setUserId(loggedInUser.getId());
            token.setTokenNumber(request.getName());
            tokenRepository.save(token);
            TokenDTO dto = new TokenDTO(token.getTokenNumber(), token.getTokenString(), token.getExpireDate());
            tokenDTORepository.save(dto);
            return ResponseEntity.ok(dto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please Login first!");
        }

    }

    public ResponseEntity<?> getUserApiTokens(){
        List<Token> token= tokenRepository.findTokenByUserId(loggedInUser.getId());
        List<TokenDTO> dtos = tokenDTORepository.findAll();
        List<TokenDTO> last = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < token.toArray().length; i++) {
            for (int j = 0; j < dtos.toArray().length; j++) {
                if(token.get(i).getTokenString().equals(dtos.get(j).getTokenString())) {
                    last.add(new TokenDTO(dtos.get(j).getTokenNumber(), "API***", dtos.get(j).getExpireDate()));
                    flag = true;
                    break;
                }
            }
            if(!flag){
                last.add(new TokenDTO(token.get(i).getTokenNumber(),"API***",token.get(i).getExpireDate() ));
            }
            flag = false;


        }
        return ResponseEntity.ok(last);
    }
}
