package WebProgrammingProject.Project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AdminUserService {

    @Autowired
    private Environment environment;

    public String getAdminUsername() {
        return environment.getProperty("admin.email");
    }

    public String getAdminPassword() {
        return environment.getProperty("admin.password");
    }

    public String getAdminFirstName(){
        return environment.getProperty("admin.first-name");
    }

    public String getAdminLastName(){
        return environment.getProperty("admin.last-name");
    }
}