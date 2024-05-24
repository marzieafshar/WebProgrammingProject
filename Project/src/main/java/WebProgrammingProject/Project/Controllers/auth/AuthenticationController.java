package WebProgrammingProject.Project.Controllers.auth;


import WebProgrammingProject.Project.Controllers.Requests.AuthenticateRequest;
import WebProgrammingProject.Project.Controllers.Requests.RegisterRequest;
import WebProgrammingProject.Project.Controllers.Requests.TokenRequest;
import WebProgrammingProject.Project.Repositories.UserRepository;
import WebProgrammingProject.Project.Services.AuthenticationService;
import WebProgrammingProject.Project.Services.TokensService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}