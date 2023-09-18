package com.example.demo.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Billboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String address, price, status, startDate, endDate,type;
    @Column
    private LocalDate startDate1;
    @Column
    private LocalDate endDate1;

    public void setInteractionDay(LocalDate interactionDay) {
        this.interactionDay = interactionDay;
    }

    public LocalDate getInteractionDay() {
        return interactionDay;
    }

    @Column
    private LocalDate interactionDay;

    Boolean inWork;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prototype_id")
    private Prototype prototype;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User client;

    public Billboard() {

    }

    public Boolean getInWork() {
        return inWork;
    }

    public LocalDate getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(LocalDate startDate1) {
        this.startDate1 = startDate1;
    }

    public LocalDate getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(LocalDate endDate1) {
        this.endDate1 = endDate1;
    }

    public void setInWork(Boolean inWork) {
        this.inWork = inWork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public User getClient() {
        return client;
    }
    public String getClientName(){
        return client != null ? client.getUsername() : "<none>";
    }

    public void setClient(User client) {
        this.client = client;
    }



    public Billboard(String address, String price, String status, LocalDate startDate1, LocalDate endDate1, User client, boolean inWork) {
        this.address = address;
        this.price = price;
        this.status = status;
        this.startDate1 = startDate1;
        this.endDate1 = endDate1;
        this.client = client;
        this.inWork = inWork;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
