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
        int index = 0;
        boolean flag = false;
        for (int i = 0; i < token.toArray().length; i++) {
            for (int j = 0; j < dtos.toArray().length; j++) {
                if(token.get(i).getTokenString().equals(dtos.get(j).getTokenString())) {
                    last.add(index, dtos.get(j));
                    last.get(index).setTokenString("API***");
                    index++;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                last.add(index, new TokenDTO(token.get(i).getTokenNumber(),"API***",token.get(i).getExpireDate() ));
                index++;
            }
            flag = false;


        }
        return ResponseEntity.ok(last);
    }


    public ResponseEntity<?> deleteToken(String tokenString){
        List<Token> token= tokenRepository.findTokenByUserId(loggedInUser.getId());
        List<TokenDTO> dtos = tokenDTORepository.findAll();
        boolean flag = false;
        for (int i = 0; i < token.toArray().length; i++) {
            if(token.get(i).getTokenString().equals(tokenString)){
                tokenRepository.delete(token.get(i));
                flag = true;
                for (int j = 0; j < dtos.toArray().length; j++) {
                    if(token.get(i).getTokenNumber().equals(dtos.get(j).getTokenNumber())){
                        tokenDTORepository.delete(dtos.get(j));
                    }

                }
            }

        }

        for (int i = 0; i < dtos.toArray().length; i++) {
            if(dtos.get(i).getTokenString().equals(tokenString)){
                tokenDTORepository.delete(dtos.get(i));
                System.out.println("dto removed");
                flag = true;
            }
        }
        System.out.println(token);
        System.out.println(dtos);
        if(flag)
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("{" +
                            "deleted"+
                            "}");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("token not found!");
        }

    }
}
