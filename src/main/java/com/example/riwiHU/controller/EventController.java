package com.example.riwiHU.controller;


import com.example.riwiHU.dto.EventRequest;
import com.example.riwiHU.dto.EventResponse;
import com.example.riwiHU.model.Events;
import com.example.riwiHU.services.EventServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventServices eventServices;

    public EventController(EventServices eventServices) {
        this.eventServices = eventServices;
    }

    @GetMapping
    public ResponseEntity<List<Events>> getAllEvents(){
        List<Events> eventResponseList = eventServices.getAllEvents();
        return ResponseEntity.ok(eventResponseList);
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody @Valid EventRequest eventRequest){
        EventResponse eventResponse = eventServices.createEvent(eventRequest);
        return ResponseEntity.status(201).body(eventResponse);
    }
}
