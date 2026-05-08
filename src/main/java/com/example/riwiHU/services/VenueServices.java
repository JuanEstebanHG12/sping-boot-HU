package com.example.riwiHU.services;

import com.example.riwiHU.model.Venue;
import com.example.riwiHU.repository.GenericRepository;
import com.example.riwiHU.repository.VeneuInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServices {

//    private final VeneuInterface venueRepository;
//
//    public VenueServices(VeneuInterface veneuRepository) {
//        this.venueRepository = veneuRepository;
//    }

    private final GenericRepository<Venue, Integer> venueRepository;

    public VenueServices(GenericRepository<Venue, Integer> venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue createVenue(Venue venue){
        return venueRepository.save(venue);
    }
    public List<Venue> getAll(){
        return venueRepository.findAll();
    }
}
