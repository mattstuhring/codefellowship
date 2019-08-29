package com.mattstuhring.codefellowship.controllers;

import com.mattstuhring.codefellowship.models.ApplicationUser;
import com.mattstuhring.codefellowship.models.ApplicationUserRepository;
import com.mattstuhring.codefellowship.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/users")
    public RedirectView createUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) {
        ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName, Date.valueOf(dateOfBirth), bio);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }


    @GetMapping("/users")
    public String getAllUsers(Principal p, Model m) {
        ApplicationUser appUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", appUser);

        List<ApplicationUser> appUsers = applicationUserRepository.findAll();
        m.addAttribute("allUsers", appUsers);

        return "allUsers";
    }


    @GetMapping("/users/{id}")
    public String showOneUser(@PathVariable long id, Principal p, Model m) {
        m.addAttribute("viewedUser", applicationUserRepository.findById(id).get());
        m.addAttribute("user", applicationUserRepository.findByUsername(p.getName()));
        return "userProfile";
    }


    @PostMapping("/users/follow")
    public RedirectView followUser(String id, Principal p) {
        long followingAppUserId = Long.parseLong(id);

        ApplicationUser appUser = applicationUserRepository.findByUsername(p.getName());
        appUser.addFollowing(applicationUserRepository.findById(followingAppUserId).get());
        applicationUserRepository.save(appUser);

        return new RedirectView("/myprofile");
    }


    @GetMapping("/myprofile")
    public String getProfilePage(Principal p, Model m) {
        ApplicationUser appUser = null;

        if (p != null) {
            appUser = applicationUserRepository.findByUsername(p.getName());
        }

        m.addAttribute("user", appUser);

        return "myprofile";
    }
}
