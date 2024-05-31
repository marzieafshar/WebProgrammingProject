package WebProgrammingProject.Project.Services;

import WebProgrammingProject.Project.Config.JWTService;
import WebProgrammingProject.Project.Controllers.Requests.AuthenticateRequest;
import WebProgrammingProject.Project.Controllers.Requests.RegisterRequest;
import WebProgrammingProject.Project.Controllers.Responses.ApiResponse;
import WebProgrammingProject.Project.Controllers.Responses.AuthenticationResponse;
import WebProgrammingProject.Project.Models.Role;
import WebProgrammingProject.Project.Models.Token;
import WebProgrammingProject.Project.Models.TokenDTO;
import WebProgrammingProject.Project.Models.User;
import WebProgrammingProject.Project.Repositories.TokenDTORepository;
import WebProgrammingProject.Project.Repositories.TokenRepository;
import WebProgrammingProject.Project.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import WebProgrammingProject.Project.Models.Token;
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public static User loggedInUser;
    @Autowired
    private TokenDTORepository tokenDTORepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AdminUserService adminUserService;

    public ResponseEntity<?> register(RegisterRequest request) {
        if(request.getFirstName()==null || request.getFirstName().isEmpty())
            return new ResponseEntity(new ApiResponse(false, "First name is required!", 400, null),
                    HttpStatus.BAD_REQUEST);
        if(request.getLastName()==null || request.getLastName().isEmpty())
            return new ResponseEntity(new ApiResponse(false, "Last name is required!", 400, null),
                    HttpStatus.BAD_REQUEST);
        if(request.getEmail()==null || request.getEmail().isEmpty())
            return new ResponseEntity(new ApiResponse(false, "Email is required!", 400, null),
                    HttpStatus.BAD_REQUEST);
        if(request.getPassword()==null || request.getPassword().isEmpty())
            return new ResponseEntity(new ApiResponse(false, "Password is required!", 400, null),
                    HttpStatus.BAD_REQUEST);
        if(userRepository.existsByEmail(request.getEmail()))
            return new ResponseEntity(new ApiResponse(false, "Email is already taken!", 400, null),
                    HttpStatus.BAD_REQUEST);
        if(request.getEmail().equals(adminUserService.getAdminUsername()))
            return new ResponseEntity(new ApiResponse(false, "This email can not be use!", 400, null),
                    HttpStatus.BAD_REQUEST);

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return  ResponseEntity.ok(user);
    }
    public User makeAdmin(){

        String email = adminUserService.getAdminUsername();
        String password = adminUserService.getAdminPassword();
        String firstname = adminUserService.getAdminFirstName();
        String lastname = adminUserService.getAdminLastName();

        User adminUser = new User();
        adminUser.setStatus(true);
        adminUser.setRole(Role.ADMIN);
        adminUser.setEmail(email);
        adminUser.setPassword(password);
        adminUser.setFirstName(firstname);
        adminUser.setLastName(lastname);
        return adminUser;
    }

    public ResponseEntity<?> authenticate(AuthenticateRequest request) {

        if (request.getEmail() == null || request.getEmail().isEmpty())
            return new ResponseEntity<>(new ApiResponse(false, "Email is required!", 400, null),
                    HttpStatus.BAD_REQUEST);

        if (request.getPassword() == null || request.getPassword().isEmpty())
            return new ResponseEntity<>(new ApiResponse(false, "Password is required!", 400, null),
                    HttpStatus.BAD_REQUEST);

        if(request.getEmail().equals(adminUserService.getAdminUsername())
                && request.getPassword().equals(adminUserService.getAdminPassword())){
            loggedInUser = makeAdmin();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("welcome admin!");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            if(user.getStatus()){
                loggedInUser = user;
                var jwtToken = jwtService.generateToken(user);
                tokenRepository.save(jwtToken);
                TokenDTO dto = new TokenDTO(jwtToken.getTokenNumber(), "API***", jwtToken.getExpireDate());
                tokenDTORepository.save(dto);
                return  ResponseEntity.ok(new AuthenticationResponse(dto , user));
            }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("your account dose not enabled!");
        } catch (Exception e) {
            return new ResponseEntity(new ApiResponse(false, "Invalid email or password", 401, null),
                    HttpStatus.UNAUTHORIZED);
        }

        
    }
}
