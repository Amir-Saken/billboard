package com.example.demo.service;

import com.example.demo.Repo.BillboardRepo;
import com.example.demo.models.Billboard;
import com.example.demo.models.User;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

import javax.persistence.criteria.*;


@Service
public class BillboardService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BillboardRepo billboardRepo;

    public BillboardService(BillboardRepo billboardRepo) {
        this.billboardRepo = billboardRepo;
    }
    @Scheduled(cron = "* * * * * *")
    public void updateExpiredStatus() {
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
    public List<Billboard> searchItems(String username, String address, String price, String type, LocalDate startDate1, LocalDate endDate1, String status) {
        List<Billboard> searchResults = new ArrayList<>();

        if (address != null && !address.isEmpty()) {
            searchResults.addAll(billboardRepo.findByAddress(address));
        } else {
            searchResults.addAll(billboardRepo.findAll());
        }

        if (price != null && !price.isEmpty()) {
            List<Billboard> priceResults = billboardRepo.findByPrice(price);
            if (!searchResults.isEmpty()) {
                searchResults.retainAll(priceResults);
            } else {
                searchResults.addAll(priceResults);
            }
        }

        if (type != null && !type.isEmpty()) {
            List<Billboard> typeResults = billboardRepo.findByType(type);
            if (!searchResults.isEmpty()) {
                searchResults.retainAll(typeResults);
            } else {
                searchResults.addAll(typeResults);
            }
        }

        if (startDate1 != null && endDate1 != null) {
            List<Billboard> dateResults = billboardRepo.findByStartDateAndEndDate(startDate1, endDate1);
            if (!searchResults.isEmpty()) {
                searchResults.retainAll(dateResults);
            } else {
                searchResults.addAll(dateResults);
            }
        }

        if (status != null && !status.isEmpty()) {
            List<Billboard> statusResults = billboardRepo.findByStatus(status);
            if (!searchResults.isEmpty()) {
                searchResults.retainAll(statusResults);
            } else {
                searchResults.addAll(statusResults);
            }
        }

        return searchResults;
    }
//    public List<Billboard> searchItems(String username, String address, String price, String type, LocalDate startDate1, LocalDate endDate1, String status) {
//        List<Billboard> searchResults = new ArrayList<>();
//
//        if (address != null && !address.isEmpty()) {
//            searchResults.addAll(billboardRepo.findByAddress(address));
//        }
//
//        if (price != null && !price.isEmpty()) {
//            searchResults.retainAll(billboardRepo.findByPrice(price));
//        }
//
//        if (type != null && !type.isEmpty()) {
//            searchResults.retainAll(billboardRepo.findByType(type));
//        }
//
//        if (startDate1 != null && endDate1 != null) {
//            searchResults.retainAll(billboardRepo.findByStartDateAndEndDate(startDate1, endDate1));
//        }
//
//        if (status != null && !status.isEmpty()) {
//            searchResults.retainAll(billboardRepo.findByStatus(status));
//        }
//
//        return searchResults;
//    }
}
