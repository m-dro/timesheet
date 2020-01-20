package pl.mirek.demo_timesheet.repository.user;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.mirek.demo_timesheet.model.project.Project;
import pl.mirek.demo_timesheet.model.user.User;

@Repository
public class UserRepository {

    // need to inject the session factory
    @Autowired
    @Qualifier("userEntityManager")
    private EntityManager entityManager;


    public User findByUserName(String theUserName) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
        theQuery.setParameter("uName", theUserName);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    public void save(User theUser) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(theUser);
    }
    
    public void saveUserWithProject(User theUser, Project project) {
        Session currentSession = entityManager.unwrap(Session.class);

        theUser.getProjects().add(project);
        currentSession.saveOrUpdate(theUser);
    }

}
