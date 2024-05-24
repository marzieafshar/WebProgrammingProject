package WebProgrammingProject.Project.Controllers;

import WebProgrammingProject.Project.Services.UserAuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserAuthenticationController {
    UserAuthenticationService service = new UserAuthenticationService();

    @PostMapping("/register")
    public void register(){

    }

    @PostMapping("/login")
    public void login(){

    }
}
