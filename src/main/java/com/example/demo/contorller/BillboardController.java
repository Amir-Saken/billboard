package com.example.demo.contorller;

import com.example.demo.Repo.BillboardRepo;
import com.example.demo.Repo.PrototypeRepo;
import com.example.demo.models.Billboard;
import com.example.demo.models.Prototype;
import com.example.demo.models.User;
import com.example.demo.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import com.example.demo.Repo.AddressRepository;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BillboardController {
    @Autowired
    private BillboardRepo billboardRepo;
    @Autowired
    private PrototypeRepo prototypeRepo;
    @Autowired
    private HttpSession session;
    @Autowired
    private BillboardService billboardService;
    @GetMapping("/pricing")
    public String Zakaz() {
        return "pricing";
    }
    String type="small";
    @PostMapping("/pricing")
    public String goToBuy(@RequestParam String type){
        this.type=type;
        return "redirect:/bulletin";
    }

    @GetMapping("/bulletin")
    public String Bulletin(Model model) {
        billboardService.updateExpiredStatus();
        Iterable<Billboard> billboards = billboardRepo.findAll();
        Iterable<Prototype> prototypes = prototypeRepo.findAll();
        ArrayList<Prototype> prototypesInSite=new ArrayList<>();
        for (Prototype proto:
                prototypes) {
            if (proto.type.equals(type)){
                prototypesInSite.add(proto);
            }
        }
        for (Billboard billboard : billboards) {
            String address = billboard.getAddress();
            for (Prototype prototype : prototypesInSite) {
                if (!prototype.getList().contains(billboard)&&prototype.Address.equals(address)&&!billboard.getStatus().equals("reject")&&!billboard.getStatus().equals("expired")) {
                    prototype.getList().add(billboard);
                    break;
                }
            }
        }
        model.addAttribute("errorMessage","");
        model.addAttribute("billboards", billboards);
        model.addAttribute("prototypes", prototypesInSite);

        return "map";
    }


    @PostMapping("/bulletin")
    public String toMain(@AuthenticationPrincipal User user,
                         @RequestParam String address,
                         @RequestParam String price,
                         @RequestParam Long proId,
                         @RequestParam String type,
                         @RequestParam String start,
                         @RequestParam String end,
                         Model model) {
        Iterable<Billboard> billboards1 = billboardRepo.findAll();
        Iterable<Prototype> prototypes1 = prototypeRepo.findAll();
        ArrayList<Prototype> prototypesInSite1=new ArrayList<>();
        for (Prototype proto:
                prototypes1) {
            if (proto.type.equals(type)){
                prototypesInSite1.add(proto);
            }
        }
        for (Billboard billboard : billboards1) {
            String address1 = billboard.getAddress();
            for (Prototype prototype : prototypesInSite1) {
                if (!prototype.getList().contains(billboard)&&prototype.Address.equals(address1)&&!billboard.getStatus().equals("reject")&&!billboard.getStatus().equals("expired")) {
                    prototype.getList().add(billboard);
                    break;
                }
            }
        }

        model.addAttribute("billboards", billboards1);
        model.addAttribute("prototypes", prototypesInSite1);
        if (start.length()==0||end.length()==0){
            model.addAttribute("errorMessage","Error: fill in all fields");
            return "map";
        }
        System.out.println(session.getAttribute("prototypes"));
        LocalDate startDate = LocalDate.of(Integer.valueOf(start.substring(0,4)), Integer.valueOf(start.substring(5,7)), 1);
        LocalDate endDate = LocalDate.of(Integer.valueOf(end.substring(0,4)), Integer.valueOf(end.substring(5,7)), 1);
        if (endDate.isBefore(startDate)){
            model.addAttribute("errorMessage","Finish date must be after start date");
            return "map";
        }

        if (startDate.isBefore(LocalDate.now())){
            model.addAttribute("errorMessage","Start date must be after current date");
            return "map";
        }
        long monthsBetween = ChronoUnit.MONTHS.between(
                startDate,
                endDate);
        if (monthsBetween>12){
            model.addAttribute("errorMessage","Maximum month amount reached pleas select 12 or least");
            return "map";
        }
        if (endDate.isAfter(LocalDate.now().plusMonths(13))){
            model.addAttribute("errorMessage","The maximum rental period is 12 months");
            return "map";
        }
        Iterable<Billboard> billboards = billboardRepo.findAll();
        Iterable<Prototype> prototypes = prototypeRepo.findAll();
        ArrayList<Prototype> prototypesInSite=new ArrayList<>();
        for (Prototype proto:
                prototypes) {
            if (proto.type.equals(type)){
                prototypesInSite.add(proto);
            }
        }
        for (Billboard billboard : billboards) {
            String address1 = billboard.getAddress();
            for (Prototype prototype : prototypesInSite) {
                if (!prototype.getList().contains(billboard)&&prototype.Address.equals(address1)&&!billboard.getStatus().equals("reject")&&!billboard.getStatus().equals("expired")) {
                    prototype.getList().add(billboard);
                    break;
                }
            }
        }
        Optional<Prototype> prototypeOptional = prototypeRepo.findById(proId);
        Prototype prototype = prototypeOptional.get();
        for (Billboard bill:
                prototype.list) {
            if(endDate.isEqual(bill.getEndDate1())||(startDate.isEqual(bill.getStartDate1())||(startDate.isAfter(bill.getStartDate1())&&startDate.isBefore(bill.getEndDate1()))||(endDate.isAfter(bill.getStartDate1())&&endDate.isBefore(bill.getEndDate1())))){
                model.addAttribute("errorMessage","Your range intersects with another");
                return "map";
            }
        }
        if (prototype.list.size() >= 11) {
            model.addAttribute("errorMessage", "Maximum number of seats reserved");
        } else {
            String status = "onReview";
            boolean inWork = true;
            Billboard billboard = new Billboard(address, price, status, startDate, endDate, user, inWork);
            billboard.setType(type);
            billboardRepo.save(billboard);
            prototype.list.add(billboard);
            prototypeRepo.save(prototype);
        }
        model.addAttribute("errorMessage","");
        return "map";
    }

    @GetMapping("/BuyBulletin")
    public String BuyBulletin(Model model) {
        return "overall";
    }
}