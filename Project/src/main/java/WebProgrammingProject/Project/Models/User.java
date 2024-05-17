package WebProgrammingProject.Project.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private int id;
        private String userName;
        private String password;
        private String role;
        public User(String userName, String password, String role){
            this.role= role;
            this.userName= userName;
            this.password= password;
        }
        

}
