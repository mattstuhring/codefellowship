package com.mattstuhring.codefellowship.controllers;

import com.mattstuhring.codefellowship.models.ApplicationUser;
import com.mattstuhring.codefellowship.models.ApplicationUserRepository;
import com.mattstuhring.codefellowship.models.Post;
import com.mattstuhring.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/posts")
    public String getAllSongs(Principal p, Model m) {
        ApplicationUser appUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", appUser);

        return "myprofile";
    }

    @PostMapping("/posts")
    public RedirectView addPost(Principal p, String body) {
        ApplicationUser appUser = applicationUserRepository.findByUsername(p.getName());
        Post post = new Post(body, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(LocalDateTime.now())), appUser);
        postRepository.save(post);

        return new RedirectView("/posts");
    }
}
