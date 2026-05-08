package com.example.riwiHU.controller;

import com.example.riwiHU.model.Venue;
import com.example.riwiHU.services.VenueServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venue")
public class VenueController {
    private final VenueServices venueServices;

    public VenueController(VenueServices venueServices) {
        this.venueServices = venueServices;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Venue saveVenue(@RequestBody Venue venue){
        return venueServices.createVenue(venue);
    }

    @GetMapping
    public List<Venue> getAllVenues(){
        return venueServices.getAll();
    }
}
