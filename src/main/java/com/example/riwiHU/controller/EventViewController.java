package com.example.riwiHU.controller;

import com.example.riwiHU.dto.EventRequest;
import com.example.riwiHU.dto.EventResponse;
import com.example.riwiHU.dto.EventSummaryDTO;
import com.example.riwiHU.model.Category;
import com.example.riwiHU.model.Events;
import com.example.riwiHU.model.Venue;
import com.example.riwiHU.services.CategoryServices;
import com.example.riwiHU.services.EventServices;
import com.example.riwiHU.services.VenueServices;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final VenueServices venueServices;
    private final CategoryServices categoryServices;

    public EventViewController(EventServices eventServices, VenueServices venueServices, CategoryServices categoryServices) {
        this.eventServices = eventServices;
        this.venueServices = venueServices;
        this.categoryServices = categoryServices;
    }

    @GetMapping
    public String listEvents(
            Model model,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @ModelAttribute("successMessage") String successMessage
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<EventSummaryDTO> summaries;
        
        if (city != null && !city.isEmpty() && category != null && !category.isEmpty()) {
            summaries = eventServices.getEventSummariesByCityAndCategory(city, category, page, size);
        } else if (city != null && !city.isEmpty()) {
            summaries = eventServices.getEventSummariesByCity(city, page, size);
        } else if (category != null && !category.isEmpty()) {
            summaries = eventServices.getEventSummariesByCategory(category, page, size);
        } else {
            summaries = eventServices.getEventSummaries(page, size);
        }
        
        model.addAttribute("summaries", summaries);
        model.addAttribute("city", city);
        model.addAttribute("category", category);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        
        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        
        return "events/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("eventRequest", new EventRequest());
        List<Venue> venues = venueServices.getAllVenues();
        List<Category> categories = categoryServices.getAllCategories();
        model.addAttribute("venues", venues);
        model.addAttribute("categories", categories);
        return "events/form";
    }

    @PostMapping
    public String createEvent(
            @Valid @ModelAttribute("eventRequest") EventRequest eventRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            List<Venue> venues = venueServices.getAllVenues();
            List<Category> categories = categoryServices.getAllCategories();
            model.addAttribute("venues", venues);
            model.addAttribute("categories", categories);
            return "events/form";
        }
        
        EventResponse eventResponse = eventServices.createEvent(eventRequest);
        redirectAttributes.addFlashAttribute("successMessage", 
                "Evento '" + eventResponse.getNombre() + "' creado exitosamente");
        
        return "redirect:/admin/events";
    }

    @PostMapping("/{id}/delete")
    public String softDeleteEvent(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        eventServices.softDeleteEvent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Evento desactivado exitosamente");
        return "redirect:/admin/events";
    }
}
