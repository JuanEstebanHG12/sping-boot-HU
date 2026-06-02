package com.example.riwiHU.controller;


import com.example.riwiHU.dto.EventRequest;
import com.example.riwiHU.dto.EventResponse;
import com.example.riwiHU.dto.EventSummaryDTO;
import com.example.riwiHU.model.Events;
import com.example.riwiHU.services.EventServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "API para gestión de eventos con relaciones complejas y soft delete")
public class EventController {
    private final EventServices eventServices;

    public EventController(EventServices eventServices) {
        this.eventServices = eventServices;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los eventos", description = "Retorna lista completa de eventos activos (soft delete aplicado)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Eventos recuperados exitosamente")
    })
    public ResponseEntity<List<Events>> getAllEvents(){
        List<Events> eventResponseList = eventServices.getAllEvents();
        return ResponseEntity.ok(eventResponseList);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Obtener resumen de eventos paginado", description = "Retorna DTO optimizado con información plana (nombre, fecha, venue, ciudad) usando Slice para mejor rendimiento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resumen de eventos recuperado exitosamente")
    })
    public ResponseEntity<Slice<EventSummaryDTO>> getEventSummaries(
            @Parameter(description = "Número de página (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size
    ) {
        Slice<EventSummaryDTO> summaries = eventServices.getEventSummaries(page, size);
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/summaries/search")
    @Operation(summary = "Buscar eventos por ciudad y/o categoría", description = "Búsqueda insensible a mayúsculas y parcial por ciudad y categoría. Los parámetros son opcionales.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultados de búsqueda recuperados exitosamente")
    })
    public ResponseEntity<Slice<EventSummaryDTO>> searchEventSummaries(
            @Parameter(description = "Ciudad (búsqueda parcial e insensible a mayúsculas)") @RequestParam(required = false) String city,
            @Parameter(description = "Categoría (búsqueda parcial e insensible a mayúsculas)") @RequestParam(required = false) String category,
            @Parameter(description = "Número de página (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size
    ) {
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
        return ResponseEntity.ok(summaries);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo evento", description = "Crea un evento con venue y categorías asignadas. El soft delete se maneja automáticamente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evento creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EventResponse> createEvent(@RequestBody @Valid EventRequest eventRequest){
        EventResponse eventResponse = eventServices.createEvent(eventRequest);
        return ResponseEntity.status(201).body(eventResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete de evento", description = "Marca el evento como inactivo sin eliminarlo físicamente. @SQLRestriction filtra estos eventos automáticamente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evento desactivado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    public ResponseEntity<Void> softDeleteEvent(@PathVariable Integer id) {
        eventServices.softDeleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
