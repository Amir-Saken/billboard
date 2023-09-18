package com.example.demo.contorller;

import com.example.demo.Repo.BillboardRepo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.models.Billboard;
import com.example.demo.models.User;
import com.example.demo.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ManagerController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    BillboardRepo billboardRepo;
    @Autowired
    private BillboardService billboardService;


    @GetMapping("/users")
    public String managerPage(Model model,Authentication authentication){
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "manager";
    }
    @GetMapping("/request")
    public String Request(Model model){
        billboardService.updateExpiredStatus();
        Iterable<Billboard> billboards=billboardRepo.findAll();
        ArrayList<Billboard> managerBills=new ArrayList<>();
        for (Billboard bill:
             billboards) {
            if(bill.getStatus().trim().equals("onReview")){
                managerBills.add(bill);
            }
        }
        model.addAttribute("managerBills",managerBills);
//        System.out.println(managerBills.toString());
        return "request";
    }
    @PostMapping("/request")
    public String PostRequest(@RequestParam Long id,
                              @RequestParam String button){
        Optional<Billboard> bill=billboardRepo.findById(id);
        Billboard bill1=bill.get();
        bill1.setStatus(button);
        bill1.setInteractionDay(LocalDate.now());
        billboardRepo.save(bill1);
        return "redirect:/request";
    }

    @GetMapping("/orders")
    public String Orders(Model model){
        billboardService.updateExpiredStatus();
        Iterable<Billboard> billboards=billboardRepo.findAll();
        ArrayList<Billboard> notManagerBills=new ArrayList<>();
        for (Billboard bill:
                billboards) {
            if(!bill.getStatus().trim().equals("onReview")){
                notManagerBills.add(bill);
            }
        }
        model.addAttribute("notManagerBills",notManagerBills);
        return "orders";
    }

    @PostMapping("/searchOrd")
    public String searchOrd(@RequestParam(value = "username", required = false) String username,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "price", required = false) String price,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "startDate", required = false) String startDate,
                            @RequestParam(value = "endDate", required = false) String endDate,
                            @RequestParam(value = "status", required = false) String status,
                            Model model) {
        LocalDate startDate1 = null;
        if (startDate != null && !startDate.isEmpty()) {
            startDate1 = LocalDate.of(Integer.parseInt(startDate.substring(0, 4)), Integer.parseInt(startDate.substring(5, 7)), 1);
        }
        LocalDate endDate1 = null;
        if (endDate != null && !endDate.isEmpty()) {
            endDate1 = LocalDate.of(Integer.parseInt(endDate.substring(0, 4)), Integer.parseInt(endDate.substring(5, 7)), 1);
        }
        List<Billboard> searchResults = billboardService.searchItems(username, address, price, type, startDate1, endDate1, status);
        model.addAttribute("searchResults", searchResults);
        return "orders";
    }
}
