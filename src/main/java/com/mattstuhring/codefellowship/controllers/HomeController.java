package com.mattstuhring.codefellowship.controllers;

import com.mattstuhring.codefellowship.models.ApplicationUser;
import com.mattstuhring.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/")
    public String getHome(Principal p, Model m) {
        ApplicationUser appUser = null;

        if (p != null) {
            appUser = applicationUserRepository.findByUsername(p.getName());
        }

        m.addAttribute("user", appUser);
        return "home";
    }
}
