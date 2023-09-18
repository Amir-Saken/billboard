package com.example.demo.contorller;

import com.example.demo.Repo.BillboardRepo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.models.Billboard;
import com.example.demo.models.User;
import com.example.demo.service.BillboardService;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyPageController {

    @Autowired
    private BillboardRepo billboardRepo;
    @Autowired
    private BillboardService billboardService;
    @GetMapping("/myPage")
    public String mypage(Model model){
        billboardService.updateExpiredStatus();
//        String status1 = "accept";
//        String status2 = "expired";
//        String status3 = "reject";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long clientId = ((User) authentication.getPrincipal()).getId();
        String username = authentication.getName();
        List<Billboard> billboardAll = billboardRepo.findByClientId(clientId);
//        List<Billboard> billboardsOn = billboardRepo.findByClientIdAndStatus(clientId, status1);
//        List<Billboard> billboardsEX = billboardRepo.findByClientIdAndStatus(clientId, status2);
//        List<Billboard> billboardsRE = billboardRepo.findByClientIdAndStatus(clientId, status3);
        model.addAttribute("billboardAll", billboardAll);
//        model.addAttribute("billboardsOn", billboardsOn);
//        model.addAttribute("billboardsEx", billboardsEX);
//        model.addAttribute("billboardsRE", billboardsRE);
        model.addAttribute("username", username);
        return "profile";
    }
}
