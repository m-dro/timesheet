package pl.mirek.demo_timesheet.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import pl.mirek.demo_timesheet.repository.project.ProjectRepository;
import pl.mirek.demo_timesheet.repository.user.RoleRepository;
import pl.mirek.demo_timesheet.repository.user.UserRepository;
import pl.mirek.demo_timesheet.model.project.Project;
import pl.mirek.demo_timesheet.model.user.Role;
import pl.mirek.demo_timesheet.model.user.User;
import pl.mirek.demo_timesheet.dto.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public User findByUserName(String userName) {
        // check the database if the user already exists
        return userRepository.findByUserName(userName);
    }


    @Transactional
    public void save(AppUser appUser) {
        User user = new User();
// assign user details to the user object
        user.setUserName(appUser.getUserName());
        user.setPassword(passwordEncoder.encode(appUser.getPassword()));
        user.setFirstName(appUser.getFirstName());
        user.setLastName(appUser.getLastName());
        user.setEmail(appUser.getEmail());
        user.setProjects(new ArrayList<Project>());
// give user default role of "translator"
        user.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_TRANSLATOR")));        
// save user in the database
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws
            UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new
                org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
