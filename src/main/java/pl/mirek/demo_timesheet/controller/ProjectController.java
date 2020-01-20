package pl.mirek.demo_timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mirek.demo_timesheet.model.project.Project;
import pl.mirek.demo_timesheet.model.user.User;
import pl.mirek.demo_timesheet.service.ProjectService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

//    @GetMapping("/projects")
//    public String getAllProjects(Model model){
//        List<Project> projects = new ArrayList<>();
//        projectService.getAllProjects().forEach(projects::add);
//        Collections.reverse(projects);
//        projectService.getAllProjects().forEach(System.out::println);
//        model.addAttribute("projects", projects);
//        model.addAttribute("project", new Project());
//        return "projects";
//    }
    
    @GetMapping("/projects")
    public String getUserProjects(Model model) {
    	
    	User user = this.getCurrentUser();
    	System.out.println(user);
    	
    	List<Project> projects = projectService.getProjectsForUser(user.getUserName());
    	Collections.reverse(projects);
    	
    	model.addAttribute("projects", projects);
    	model.addAttribute("project", new Project());
    	return "projects";
    }

    public String getProjectById(Model model, int id){
        Project project = null;
        if(projectService.getProjectById(id).isPresent()){
            project = projectService.getProjectById(id).get();
        } else {
            System.out.println("============ No such project =============");
        };
        model.addAttribute("project", project);
        return "project";
    }

    @GetMapping("/projects/new")
    public String showAddProjectForm(Model model){
        model.addAttribute("project", new Project());

        return "new";
    }


    @PostMapping("/projects/new")
    public String addProject(@ModelAttribute("project") Project newProject){
    	User user = this.getCurrentUser();
    	projectService.addProject(newProject, user);
        
        
        return "redirect:/projects";
    }

    @GetMapping("/projects/update")
    public String updateProject(@RequestParam("projectID") int id, Model model){
        Project project = null; // to be refactored later to avoid code duplication
        if(projectService.getProjectById(id).isPresent()){
            project = projectService.getProjectById(id).get();
        } else {
            System.out.println("============ No such project =============");
        };
        model.addAttribute("project", project);

        return "update";
    }

    @PostMapping("/projects/update")
    public String updateProject(@ModelAttribute("project") Project updatedProject){
        projectService.updateProject(updatedProject);

        return "redirect:/projects";
    }

    @GetMapping("/projects/delete")
    public String deleteProjectById(@RequestParam("projectID") int id){
        projectService.deleteProjectById(id);

        return "redirect:/projects";
    }
    

    
    public User getCurrentUser() {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String username;
    		if (principal instanceof UserDetails) {
    			username = ((UserDetails)principal).getUsername();
    		} else {
    			username = principal.toString();
    		}
    		System.out.println("USERNAME IS: " + username);
    	User user = projectService.getUserByUsername(username);
    	
    	return user;
    }


}
