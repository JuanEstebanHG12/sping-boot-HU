package com.example.riwiHU.services;

import com.example.riwiHU.dto.EventRequest;
import com.example.riwiHU.dto.EventResponse;
import com.example.riwiHU.dto.EventSummaryDTO;
import com.example.riwiHU.model.Category;
import com.example.riwiHU.model.Events;
import com.example.riwiHU.model.Venue;
import com.example.riwiHU.repository.CategoryRepository;
import com.example.riwiHU.repository.EventsRepository;
import com.example.riwiHU.repository.VenueInterface;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class EventServices {
    private final EventsRepository eventsRepository;
    private final VenueInterface venueRepository;
    private final CategoryRepository categoryRepository;

    public EventServices(EventsRepository eventsRepository, VenueInterface venueRepository, CategoryRepository categoryRepository) {
        this.eventsRepository = eventsRepository;
        this.venueRepository = venueRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Events> getAllEvents(){
        return eventsRepository.findAll();
    }

    public Slice<EventSummaryDTO> getEventSummaries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventsRepository.findAllSummariesOrderByFechaDesc(pageable);
    }

    public Slice<EventSummaryDTO> getEventSummariesByCity(String city, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventsRepository.findSummariesByCity(city, pageable);
    }

    public Slice<EventSummaryDTO> getEventSummariesByCategory(String categoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventsRepository.findSummariesByCategory(categoryName, pageable);
    }

    public Slice<EventSummaryDTO> getEventSummariesByCityAndCategory(String city, String categoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventsRepository.findSummariesByCityAndCategory(city, categoryName, pageable);
    }

    public EventResponse createEvent(EventRequest eventRequest) {
        Events events = new Events();
        events.setNombre(eventRequest.getNombre());
        events.setDescription(eventRequest.getDescription());
        events.setFecha(eventRequest.getFecha() != null ? eventRequest.getFecha() : LocalDateTime.now());
        
        if (eventRequest.getVenueId() != null) {
            Venue venue = venueRepository.findById(eventRequest.getVenueId())
                    .orElseThrow(() -> new RuntimeException("Venue not found"));
            events.setVenue(venue);
        }
        
        if (eventRequest.getCategoryIds() != null && !eventRequest.getCategoryIds().isEmpty()) {
            Set<Category> categories = Set.copyOf(categoryRepository.findAllById(eventRequest.getCategoryIds()));
            events.setCategories(categories);
        }
        
        Events ev = eventsRepository.save(events);
        return new EventResponse(
                ev.getId(),
                ev.getNombre(),
                ev.getFecha(),
                ev.getDescription()
        );
    }

    public void softDeleteEvent(Integer id) {
        Events event = eventsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.softDelete();
        eventsRepository.save(event);
    }
}
