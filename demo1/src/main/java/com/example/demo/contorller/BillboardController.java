package com.example.demo.contorller;

import com.example.demo.Repo.BillboardRepo;
import com.example.demo.models.Billboard;
import com.example.demo.models.User;
import com.example.demo.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import com.example.demo.Repo.AddressRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BillboardController {
    @Autowired
    private BillboardService billboardService;

    @Autowired
    private BillboardRepo billboardRepo;

    @GetMapping("/pricing")
    public String Zakaz() {
        return "pricing";
    }

    @GetMapping("/bulletin")
    public String Bulletin(Model model) {
        String status = "online";
        billboardService.updateExpiredStatus();
        Iterable<Billboard> billboards = billboardRepo.findAll();
        model.addAttribute("billboards", billboards);
        return "map";
    }
    @PostMapping("/bulletin")
    public String toMain(
                    @AuthenticationPrincipal User user,
                    @RequestParam String address,
                    @RequestParam String price,
                    @RequestParam int month,
                    Model model) {
        String status = "online";
        int count = billboardRepo.countByStatusAndInWorkIsTrue(status, address);
        if(count >= 3){
            model.addAttribute("errorMessage", "Максимальное количество мест зарезервировано");
        }else if(count == 1){
            if (month == 1){
                for (int i = 0; i < month; i++){
                    LocalDate startDate1 = LocalDate.now().plusMonths(i+1).withDayOfMonth(1);
                    LocalDate endDate1 = startDate1.plusMonths(1).withDayOfMonth(1);
                    boolean inWork = true;
                    Billboard billboard = new Billboard(address, price, status, startDate1, endDate1, user, inWork);
                    billboardRepo.save(billboard);
                }
            } else if (month == 2) {
                for (int i1 = 0; i1 < month; i1++){
                    LocalDate startDate1 = LocalDate.now().plusMonths(i1+1).withDayOfMonth(1);
                    LocalDate endDate1 = startDate1.plusMonths(1).withDayOfMonth(1);
                    boolean inWork = true;
                    Billboard billboard = new Billboard(address, price, status, startDate1, endDate1, user, inWork);
                    billboardRepo.save(billboard);
                }
            }
        } else if (count == 2) {
            LocalDate startDate = LocalDate.now();
            LocalDate startDate1 = startDate.plusMonths(2).withDayOfMonth(1);
            LocalDate endDate1 = startDate1.plusMonths(1).withDayOfMonth(1);
            boolean inWork = false;
            Billboard billboard = new Billboard(address, price, status, startDate1, endDate1, user, inWork);
            billboardRepo.save(billboard);
        }else {
            if (month == 1){
                for (int i = 0; i < month; i++){
                    LocalDate startDate1 = LocalDate.now().plusMonths(i).withDayOfMonth(1);
                    LocalDate endDate1 = startDate1.plusMonths(1).withDayOfMonth(1);
                    boolean inWork = true;
                    Billboard billboard = new Billboard(address, price, status, startDate1, endDate1, user, inWork);
                    billboardRepo.save(billboard);
                }
            } else if (month == 2) {
                for (int i1 = 0; i1 < month; i1++){
                    LocalDate startDate1 = LocalDate.now().plusMonths(i1).withDayOfMonth(1);
                    LocalDate endDate1 = startDate1.plusMonths(1).withDayOfMonth(1);
                    boolean inWork = true;
                    Billboard billboard = new Billboard(address, price, status, startDate1, endDate1, user, inWork);
                    billboardRepo.save(billboard);
                }
            } else if (month == 3) {
                for (int i2 = 0; i2 < month; i2++){
                    LocalDate startDate1 = LocalDate.now().plusMonths(i2).withDayOfMonth(1);
                    LocalDate endDate1 = startDate1.plusMonths(1).withDayOfMonth(1);
                    boolean inWork = true;
                    Billboard billboard = new Billboard(address, price, status, startDate1, endDate1, user, inWork);
                    billboardRepo.save(billboard);
                }
            }
        }
        return "redirect:/bulletin";
    }
    @GetMapping("/BuyBulletin")
    public String BuyBulletin(Model model) {
        return "overall";
    }
}
