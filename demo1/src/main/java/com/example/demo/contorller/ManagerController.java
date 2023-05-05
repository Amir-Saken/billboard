package com.example.demo.contorller;

import com.example.demo.Repo.UserRepo;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ManagerController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/manager")
    public String managerPage(Model model,Authentication authentication){
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "manager";
    }
}
