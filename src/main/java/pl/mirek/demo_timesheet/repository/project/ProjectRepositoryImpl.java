package pl.mirek.demo_timesheet.repository.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import pl.mirek.demo_timesheet.model.project.Project;
import pl.mirek.demo_timesheet.model.user.User;

public class ProjectRepositoryImpl {

	 @Autowired
	    @Qualifier("userEntityManager")
	    private EntityManager entityManager;


	    public List<Project> findUserProjectsByUserID(int userID) {
	        
	        Session currentSession = entityManager.unwrap(Session.class);
	        List<Project> userProjects = new ArrayList<>();
	        Query<User> theQuery = currentSession.createQuery("from User where id=:userID", User.class);
	        theQuery.setParameter("userID", userID);
	        
	        User theUser = null;
	        try {
	            theUser = theQuery.getSingleResult();
	        } catch (Exception e) {
	            return null;
	        }

	        theUser.getProjects().forEach(userProjects::add);
	        
	        return userProjects;
	    }
	    
	    public User findUserByUsername(String username) {
	    	Session currentSession = entityManager.unwrap(Session.class);
	    	Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
	    	theQuery.setParameter("uName", username);
	    	
	    	 User theUser = null;
		        try {
		            theUser = theQuery.getSingleResult();
		        } catch (Exception e) {
		            return null;
		        }
		     return theUser;
	    	
	    }
	    
	    public void save(Project theProject) {
	        Session currentSession = entityManager.unwrap(Session.class);

	        currentSession.saveOrUpdate(theProject);
	        
	    }
		
}
