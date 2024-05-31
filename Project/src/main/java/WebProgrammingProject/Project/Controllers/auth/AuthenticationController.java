package WebProgrammingProject.Project.Controllers.auth;

import WebProgrammingProject.Project.Controllers.Requests.AuthenticateRequest;
import WebProgrammingProject.Project.Controllers.Requests.RegisterRequest;
import WebProgrammingProject.Project.Controllers.Requests.TokenRequest;
import WebProgrammingProject.Project.Models.Role;
import WebProgrammingProject.Project.Models.User;
import WebProgrammingProject.Project.Repositories.UserRepository;
import WebProgrammingProject.Project.Services.AuthenticationService;
import WebProgrammingProject.Project.Services.TokensService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import static WebProgrammingProject.Project.Services.AuthenticationService.loggedInUser;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final TokensService tokensService;

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);




    @PostMapping("/users/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/users/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/user/api-tokens")
    public ResponseEntity<?> makeApiToken(@RequestBody TokenRequest tokenRequest){
        return (ResponseEntity<?>) ResponseEntity.ok(tokensService.makeApiToken(tokenRequest));
    }

    @GetMapping("/user/api-tokens")
    public ResponseEntity<?> getUserApiTokens(){
        return ResponseEntity.ok(tokensService.getUserApiTokens());
    }

    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers(){
        return (ResponseEntity<?>) ResponseEntity.ok(tokensService.listOfUsers());
    }

    @DeleteMapping("/user/api-tokens")
    @ResponseBody
    public ResponseEntity<?> deleteToken(@RequestParam String token){
        return ResponseEntity.ok(tokensService.deleteToken(token));
    }

    @PutMapping("/admin/users")
    private ResponseEntity<?> changeUserStatus(@RequestParam String username, @RequestParam boolean active){
        return ResponseEntity.ok(tokensService.changeUserStatus(username,active));
    }


}