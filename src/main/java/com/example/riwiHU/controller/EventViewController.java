package com.example.riwiHU.controller;

import com.example.riwiHU.dto.EventRequest;
import com.example.riwiHU.dto.EventResponse;
import com.example.riwiHU.model.Events;
import com.example.riwiHU.services.EventServices;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/events")
public class EventViewController {
    private final EventServices eventServices;

    public EventViewController(EventServices eventServices) {
        this.eventServices = eventServices;
    }

    @GetMapping
    public String listEvents(Model model, @ModelAttribute("successMessage") String successMessage) {
        List<Events> events = eventServices.getAllEvents();
        model.addAttribute("events", events);
        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        return "events/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("eventRequest", new EventRequest());
        return "events/form";
    }

    @PostMapping
    public String createEvent(
            @Valid @ModelAttribute("eventRequest") EventRequest eventRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "events/form";
        }
        
        EventResponse eventResponse = eventServices.createEvent(eventRequest);
        redirectAttributes.addFlashAttribute("successMessage", 
                "Evento '" + eventResponse.getNombre() + "' creado exitosamente");
        
        return "redirect:/admin/events";
    }
}
