package pl.mirek.demo_timesheet.repository.user;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import pl.mirek.demo_timesheet.model.user.Role;

@Repository
public class RoleRepository  {

    @Autowired
    @Qualifier("userEntityManager")
    private EntityManager entityManager;

    public Role findRoleByName(String theRoleName) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<Role> theQuery = currentSession.createQuery("from Role where name=:roleName", Role.class);
        theQuery.setParameter("roleName", theRoleName);

        Role theRole = null;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        return theRole;
    }
}
