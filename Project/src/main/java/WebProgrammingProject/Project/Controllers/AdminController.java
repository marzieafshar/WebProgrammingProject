package WebProgrammingProject.Project.Controllers;


import WebProgrammingProject.Project.Services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    AdminService service = new AdminService();

    @PutMapping("/users")
    public void changeUserActiveStatus(){

    }

    @GetMapping("/users")
    public ResponseEntity getUsersList(){
        return null;
    }
}
