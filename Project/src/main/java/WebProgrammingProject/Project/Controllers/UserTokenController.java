package WebProgrammingProject.Project.Controllers;

import WebProgrammingProject.Project.Services.UserTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserTokenController {

    UserTokenService service = new UserTokenService();

    @PostMapping("/api-tokens")
    public void createAPIToken(){

    }

    @GetMapping("/api-tokens")
    public ResponseEntity getAPITokensList(){
        return null;
    }

    @DeleteMapping("/api-tokens")
    public void expireAPIToken(){

    }
}
