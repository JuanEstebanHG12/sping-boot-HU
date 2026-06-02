package com.example.riwiHU.services;

import com.example.riwiHU.dto.VenueRequest;
import com.example.riwiHU.dto.VenueResponse;
import com.example.riwiHU.model.Venue;
import com.example.riwiHU.repository.VenueInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServices {

    private final VenueInterface venueRepository;

    public VenueServices(VenueInterface venueRepository) {
        this.venueRepository = venueRepository;
    }


    public VenueResponse createVenue(VenueRequest request) {
        Venue venue = new Venue();
        venue.setNombre(request.getNombre());
        venue.setCapacidad(request.getCapacidad());
        venue.setDirection(request.getDirection());
        Venue ven = venueRepository.save(venue);
        return new VenueResponse(
                ven.getId(),
                ven.getNombre(),
                ven.getDirection(),
                ven.getCapacidad()
        );
    }

    public List<Venue> getAll() {
        return venueRepository.findAll();
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }
}
