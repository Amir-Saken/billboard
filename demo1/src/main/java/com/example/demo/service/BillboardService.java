package com.example.demo.service;

import com.example.demo.Repo.BillboardRepo;
import com.example.demo.models.Billboard;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;
import java.time.LocalDate;
import org.slf4j.Logger;



@Service
public class BillboardService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BillboardRepo billboardRepo;

    public BillboardService(BillboardRepo billboardRepo) {
        this.billboardRepo = billboardRepo;
    }

//    @Scheduled(cron = "0 0 0 * * *")
    public void updateExpiredStatus() {
        logger.info("Scheduled method started...");
        Iterable<Billboard> billboards = billboardRepo.findAll();
        LocalDate currentDate = LocalDate.now();

        for (Billboard billboard : billboards) {
            if (billboard.getEndDate1().isBefore(currentDate)) {
                billboard.setStatus("expired");
                billboardRepo.save(billboard);
                logger.info("Billboard {} expired", billboard.getId());
            }
        }
    }
//    public void updateInWorkByAddress() {
//        List<String> addresses = billboardRepo.findDistinctAddressByStatusAndInWorkIsTrue("online");
//        for (String address : addresses) {
//            int count = billboardRepo.countByAddressAndStatusAndInWorkIsTrue(address, "online");
//            if (count >= 3) {
//                billboardRepo.updateInWorkByAddressAndStatus(address, "online");
//            }
//        }
//    }

}
