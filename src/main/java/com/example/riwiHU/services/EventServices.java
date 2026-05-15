package com.example.riwiHU.services;

import com.example.riwiHU.dto.EventRequest;
import com.example.riwiHU.dto.EventResponse;
import com.example.riwiHU.model.Events;
//import com.example.riwiHU.repository.GenericMemoryRepositoryImpl;
import com.example.riwiHU.repository.EventsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServices {
    private final EventsRepository eventsRepository;

    public EventServices(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }


    public List<Events> getAllEvents(){
        return eventsRepository.findAll();
    }

    public EventResponse createEvent (EventRequest eventRequest){
        Events events = new Events();
        events.setNombre(eventRequest.getNombre());
        events.setDescription(eventRequest.getDescription());
        events.setFecha(LocalDateTime.now());
        Events ev = eventsRepository.save(events);
        return new EventResponse(
                ev.getId(),
                ev.getNombre(),
                ev.getFecha(),
                ev.getDescription()
        );
    }
}
