package com.example.demo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Prototype {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Prototype() {

    }
    @Column
    public String Address,type;
    @Column
    public int price;

    @OneToMany(mappedBy = "prototype", cascade = CascadeType.ALL)
    public List<Billboard> list=new ArrayList<>();

    public List<Billboard> getList() {
        return list;
    }

    public Prototype(String address, int price) {
        Address = address;
        this.price = price;
        this.list = new LinkedList<>();
    }
}
