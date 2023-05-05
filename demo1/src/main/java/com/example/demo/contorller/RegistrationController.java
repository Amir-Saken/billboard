package com.example.demo.contorller;

import com.example.demo.Repo.UserRepo;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/signup")
    public String registration(){
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(@ModelAttribute("user")User user, Model model){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null){
            model.addAttribute("message", "User exists");
            return "signup";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/signin";
    }

    @GetMapping("/signin")
    public String loginGet(Model model){
        return "signin";
    }
    @PostMapping("/signin")
    public String login(Model model){
        return "redirect:/index";
    }
}
