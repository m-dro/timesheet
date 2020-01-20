package pl.mirek.demo_timesheet.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mirek.demo_timesheet.model.project.Project;

import javax.persistence.*;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
@ToString(exclude = "projects")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_projects",
    		joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"),
    		inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName="id"))
    private Collection<Project> projects;

    public User(String userName, String password, String firstName, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String userName, String password, String firstName, String lastName, String email,
                Collection<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }
    
    public User(String userName, String password, String firstName, String lastName, String email,
            Collection<Role> roles, List<Project> projects) {
    this.userName = userName;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.projects = projects;
    }

}
