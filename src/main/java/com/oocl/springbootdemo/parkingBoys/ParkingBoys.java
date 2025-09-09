package com.oocl.springbootdemo.parkingBoys;

import lombok.Data;

import java.util.Random;

@Data
public class ParkingBoys {
    private String id;
    private String name;

    public ParkingBoys(String name) {
        this.name = name;
        this.id = new Random(1000).toString();
    }
}
