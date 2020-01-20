package pl.mirek.demo_timesheet.model.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mirek.demo_timesheet.model.user.User;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
@ToString(exclude="collaborators")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int characters;
    private String client;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
//    @JoinTable(name = "users_projects",
//    		joinColumns = @JoinColumn(name = "project_id", referencedColumnName="id"),
//    		inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"))
    private Collection<User> collaborators;
}
