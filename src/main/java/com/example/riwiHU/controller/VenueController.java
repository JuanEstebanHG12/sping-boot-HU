package com.example.riwiHU.controller;

import com.example.riwiHU.dto.VenueRequest;
import com.example.riwiHU.dto.VenueResponse;
import com.example.riwiHU.model.Venue;
import com.example.riwiHU.services.VenueServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<VenueResponse> saveVenue(@Valid @RequestBody VenueRequest venue){
        VenueResponse venueRes = venueServices.createVenue(venue);
        return ResponseEntity.status(201).body(venueRes);
    }

    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues(){
        List<Venue> venueList = venueServices.getAll();
        return ResponseEntity.ok(venueList);
    }
}
