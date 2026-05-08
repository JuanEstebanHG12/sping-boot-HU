package com.example.riwiHU.controller;

import com.example.riwiHU.dto.ApiResponse;
import com.example.riwiHU.dto.VenueRequest;
import com.example.riwiHU.dto.VenueResponse;
import com.example.riwiHU.model.Venue;
import com.example.riwiHU.services.VenueServices;
import jakarta.validation.Valid;
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
    public ApiResponse<VenueResponse> saveVenue(@Valid @RequestBody VenueRequest venue){
        VenueResponse venueRes = venueServices.createVenue(venue);
        return new ApiResponse<>(
                true,
                "venue Creada",
                venueRes
        );
    }

    @GetMapping
    public List<Venue> getAllVenues(){
        return venueServices.getAll();
    }
}
