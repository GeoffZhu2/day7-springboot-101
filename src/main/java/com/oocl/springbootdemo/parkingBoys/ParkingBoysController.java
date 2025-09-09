package com.oocl.springbootdemo.parkingBoys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("parking-boys")
public class ParkingBoysController {
    @PostMapping
    public ParkingBoys addParkingBoys() {
        return new ParkingBoys("Tom");
    }

    @GetMapping
    public ParkingBoys queryAllParkingBoys() {
        return new ParkingBoys("Tom");
    }

}