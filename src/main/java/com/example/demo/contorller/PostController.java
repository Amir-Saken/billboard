package com.example.demo.contorller;

import com.example.demo.Repo.PostRepo;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PostController {
    int i;
    @Autowired
    private PostRepo postRepo;
    @GetMapping("/post")
    public String post(Model model){
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("title", "Posts");
        model.addAttribute("posts", posts);
        return "post-img";
    }
    @GetMapping("/post/add")
    public String postAdd(Model model){
        return "post-img-add";
    }
    @PostMapping("/post/add")
    public String postAddpost(
                            @AuthenticationPrincipal User user,
                            @RequestParam String title,
                            @RequestParam String anonce,
                            @RequestParam String fulltext, Model model) throws IOException {
        Post post = new Post(title, anonce, fulltext, user);
        postRepo.save(post);
        return "redirect:/post";
    }
    @GetMapping("/post/{id}")
    public String postDet(@PathVariable(value = "id") long id, Model model){
        if (!postRepo.existsById(id)){
            return "redirect:/post";
        }
        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        Post post1 = postRepo.findById(id).orElseThrow();
        postRepo.save(post1);
        return "post-det";
    }
    @GetMapping("/post/{id}/edit")
    public String postEdit(@PathVariable(value = "id") long id, Model model){
        if (!postRepo.existsById(id)){
            return "redirect:/post";
        }
        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "post-edit";
    }
    @PostMapping("/post/{id}/edit")
    public String postUpdpost(@PathVariable(value = "id") long id,
                              @RequestParam String title,
                              @RequestParam String anonce,
                              @RequestParam String fulltext, Model model){
        Post post = postRepo.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnonce(anonce);
        post.setFulltext(fulltext);
        postRepo.save(post);
        return "redirect:/post";
    }
    @PostMapping("/post/{id}/remove")
    public String postRemove(@PathVariable(value = "id") long id, Model model){
        Post post = postRepo.findById(id).orElseThrow();
        postRepo.delete(post);
        return "redirect:/post";
    }
}
