package com.example.riwiHU.repository;

import com.example.riwiHU.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueInterface extends JpaRepository<Venue, Integer> {

}
