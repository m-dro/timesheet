package pl.mirek.demo_timesheet.repository.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pl.mirek.demo_timesheet.model.project.Project;
import pl.mirek.demo_timesheet.model.user.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {	
	
	public User findUserByUsername(String username);
}
