package com.mattstuhring.codefellowship.controllers;

import com.mattstuhring.codefellowship.models.ApplicationUser;
import com.mattstuhring.codefellowship.models.ApplicationUserRepository;
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
import java.util.ArrayList;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/users")
    public RedirectView createUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) {
        ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName, dateOfBirth, bio);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }



    @GetMapping("/login")
    public String getLoginPage(Principal p, Model m) {
        ApplicationUser appUser = null;

        if (p != null) {
            appUser = applicationUserRepository.findByUsername(p.getName());
        }

        m.addAttribute("user", appUser);

        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage(Principal p, Model m) {
        ApplicationUser appUser = null;

        if (p != null) {
            appUser = applicationUserRepository.findByUsername(p.getName());
        }

        m.addAttribute("user", appUser);

        return "signup";
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

    @GetMapping("/users/{id}")
    public String showOneUser(@PathVariable long id, Principal p, Model m) {
        m.addAttribute("viewedUser", applicationUserRepository.findById(id).get());
        m.addAttribute("userProfile", applicationUserRepository.findByUsername(p.getName()));
        return "userProfile";
    }
}
