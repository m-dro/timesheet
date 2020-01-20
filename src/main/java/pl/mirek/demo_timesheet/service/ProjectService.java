package pl.mirek.demo_timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.mirek.demo_timesheet.model.project.Project;
import pl.mirek.demo_timesheet.model.user.User;
import pl.mirek.demo_timesheet.repository.project.ProjectRepository;
import pl.mirek.demo_timesheet.repository.project.ProjectRepositoryImpl;
import pl.mirek.demo_timesheet.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
    ProjectRepository projectRepository;
    
    @Autowired
    ProjectRepositoryImpl projectRepositoryImpl;

    public List<Project> getAllProjects(){
        List<Project> projectList = new ArrayList<>();

        projectRepository.findAll().forEach(projectList::add);
//        Project p1 = new Project();
//        p1.setId(1);
//        p1.setClient("Warszawa");
//        p1.setCharacters(1200);
//        p1.setName("121.11.21");
//        LocalDateTime deadline = LocalDateTime.of(2020, Month.DECEMBER, 22, 15, 20);
//        p1.setDeadline(deadline);
//
//        Project p2 = new Project();
//        p2.setId(2);
//        p2.setClient("GUS");
//        p2.setCharacters(28000);
//        p2.setName("434.23.12");
//        LocalDateTime deadline2 = LocalDateTime.of(2019, Month.NOVEMBER, 29, 10, 20);
//        p2.setDeadline(deadline2);
//
//        Project p3 = new Project();
//        p3.setId(3);
//        p3.setClient("Sąd");
//        p3.setCharacters(5400);
//        p3.setName("512.12.19");
//        LocalDateTime deadline3 = LocalDateTime.of(2019, Month.NOVEMBER, 30, 11, 20);
//        p3.setDeadline(deadline3);
//
//        Project p4 = new Project();
//        p4.setId(4);
//        p4.setClient("Metro");
//        p4.setCharacters(54000);
//        p4.setName("54.11.19");
//        LocalDateTime deadline4 = LocalDateTime.of(2019, Month.DECEMBER, 1, 15, 20);
//        p4.setDeadline(deadline4);
//
//        projectList.add(p1);
//        projectList.add(p2);
//        projectList.add(p3);
//        projectList.add(p4);

        return projectList;
    }
    
    public List<Project> getProjectsForUser(String theUserName){
    	User thisUser = userRepository.findByUserName(theUserName);
    	List<Project> projects = new ArrayList<>();
    	thisUser.getProjects().forEach(projects::add);
    	
    	return projects;
    }
    
    public List<Project> getUserProjectsByUserId(int userID){
    	return projectRepositoryImpl.findUserProjectsByUserID(userID);
    }
    
    public User getUserByUsername(String username) {
    	return projectRepository.findUserByUsername(username);
    }

    public Optional<Project> getProjectById(int id){
        return projectRepository.findById(id);
    }

    @Transactional
    public void addProject(Project newProject, User user){
        projectRepository.save(newProject);
        userRepository.saveUserWithProject(user, newProject);
    }

    public void updateProject(Project updatedProject){
        projectRepository.save(updatedProject);
    }

    public void deleteProjectById(int id){
        projectRepository.deleteById(id);
    }
}
