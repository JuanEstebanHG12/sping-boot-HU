package com.example.riwiHU.services;

import com.example.riwiHU.dto.VenueRequest;
import com.example.riwiHU.dto.VenueResponse;
import com.example.riwiHU.model.Venue;
import com.example.riwiHU.repository.GenericRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServices {

        private int nextId = 1;
//    private final VeneuInterface venueRepository;
//
//    public VenueServices(VeneuInterface veneuRepository) {
//        this.venueRepository = veneuRepository;
//    }

    private final GenericRepository<Venue, Integer> venueRepository;

    public VenueServices(GenericRepository<Venue, Integer> venueRepository) {
        this.venueRepository = venueRepository;
    }

    public VenueResponse createVenue(VenueRequest request) {
        Venue venue = new Venue();
        venue.setId(nextId++);
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
}
