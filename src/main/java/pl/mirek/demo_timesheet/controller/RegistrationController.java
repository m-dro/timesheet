package pl.mirek.demo_timesheet.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.mirek.demo_timesheet.dto.AppUser;
import pl.mirek.demo_timesheet.model.user.User;
import pl.mirek.demo_timesheet.service.UserService;


@Controller
@RequestMapping("/")
public class RegistrationController {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }


    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/login")
    public String showMyLoginPage(Model theModel) {

        theModel.addAttribute("appUser", new AppUser());

        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model theModel){

        theModel.addAttribute("appUser", new AppUser());

        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(
            @Valid @ModelAttribute("appUser") AppUser appUser,
            BindingResult theBindingResult,
            Model theModel) {

        String userName = appUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()){
            return "register";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("appUser", new AppUser());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "register";
        }

        // create user account
        userService.save(appUser);
        theModel.addAttribute("registrationSuccess", "Thank you for registering. You can log in now using your username and password.");
        logger.info("Successfully created user: " + userName);

        return "login";
    }
}
