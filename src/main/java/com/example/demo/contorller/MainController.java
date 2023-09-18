package com.example.demo.contorller;


import com.example.demo.Repo.PostRepo;
import com.example.demo.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private PostRepo postRepo;

    @GetMapping("/")
    public String greeting(Model model) {
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Main");
        return "index";
    }
    @GetMapping("/info")
    public String users(Model model){
        return "info";
    }
    @GetMapping("/main")
    public String main(Model model){
        return "indexMain";
    }

}
