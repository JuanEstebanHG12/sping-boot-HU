package com.example.riwiHU.controller;

import com.example.riwiHU.dto.VenueRequest;
import com.example.riwiHU.dto.VenueResponse;
import com.example.riwiHU.model.Venue;
import com.example.riwiHU.services.VenueServices;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/venues")
public class VenueViewController {
    private final VenueServices venueServices;

    public VenueViewController(VenueServices venueServices) {
        this.venueServices = venueServices;
    }

    @GetMapping
    public String listVenues(Model model, @ModelAttribute("successMessage") String successMessage) {
        List<Venue> venues = venueServices.getAll();
        model.addAttribute("venues", venues);
        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        return "venues/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("venueRequest", new VenueRequest());
        return "venues/form";
    }

    @PostMapping
    public String createVenue(
            @Valid @ModelAttribute("venueRequest") VenueRequest venueRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "venues/form";
        }
        
        VenueResponse venueResponse = venueServices.createVenue(venueRequest);
        redirectAttributes.addFlashAttribute("successMessage", 
                "Lugar '" + venueResponse.nombre() + "' registrado exitosamente");
        
        return "redirect:/admin/venues";
    }
}
