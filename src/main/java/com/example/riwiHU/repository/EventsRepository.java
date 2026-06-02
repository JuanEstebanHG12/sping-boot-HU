package com.example.riwiHU.repository;

import com.example.riwiHU.dto.EventSummaryDTO;
import com.example.riwiHU.model.Events;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Integer> {
    
    @EntityGraph(attributePaths = {"venue", "categories"})
    Slice<Events> findAllByOrderByFechaDesc(org.springframework.data.domain.Pageable pageable);
    
    @EntityGraph(attributePaths = {"venue", "categories"})
    Slice<Events> findByVenueCityContainingIgnoreCase(String city, org.springframework.data.domain.Pageable pageable);
    
    @EntityGraph(attributePaths = {"venue", "categories"})
    Slice<Events> findByCategoriesNameContainingIgnoreCase(String categoryName, org.springframework.data.domain.Pageable pageable);
    
    @EntityGraph(attributePaths = {"venue", "categories"})
    Slice<Events> findByVenueCityContainingIgnoreCaseAndCategoriesNameContainingIgnoreCase(
        String city, String categoryName, org.springframework.data.domain.Pageable pageable);
    
    @EntityGraph(attributePaths = {"venue", "categories"})
    Slice<Events> findByFechaBetweenOrderByFechaDesc(
        LocalDateTime startDate, LocalDateTime endDate, org.springframework.data.domain.Pageable pageable);
    
    @EntityGraph(attributePaths = {"venue", "categories"})
    Slice<Events> findByVenueCapacidadGreaterThanEqualOrderByFechaDesc(
        int minCapacity, org.springframework.data.domain.Pageable pageable);
    
    @Query("SELECT new com.example.riwiHU.dto.EventSummaryDTO(e.id, e.nombre, e.fecha, e.venue.nombre, e.venue.city) " +
           "FROM Events e " +
           "ORDER BY e.fecha DESC")
    Slice<EventSummaryDTO> findAllSummariesOrderByFechaDesc(org.springframework.data.domain.Pageable pageable);
    
    @Query("SELECT new com.example.riwiHU.dto.EventSummaryDTO(e.id, e.nombre, e.fecha, e.venue.nombre, e.venue.city) " +
           "FROM Events e " +
           "WHERE LOWER(e.venue.city) LIKE LOWER(CONCAT('%', :city, '%')) " +
           "ORDER BY e.fecha DESC")
    Slice<EventSummaryDTO> findSummariesByCity(@Param("city") String city, org.springframework.data.domain.Pageable pageable);
    
    @Query("SELECT new com.example.riwiHU.dto.EventSummaryDTO(e.id, e.nombre, e.fecha, e.venue.nombre, e.venue.city) " +
           "FROM Events e " +
           "JOIN e.categories c " +
           "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%')) " +
           "ORDER BY e.fecha DESC")
    Slice<EventSummaryDTO> findSummariesByCategory(@Param("categoryName") String categoryName, org.springframework.data.domain.Pageable pageable);
    
    @Query("SELECT new com.example.riwiHU.dto.EventSummaryDTO(e.id, e.nombre, e.fecha, e.venue.nombre, e.venue.city) " +
           "FROM Events e " +
           "WHERE LOWER(e.venue.city) LIKE LOWER(CONCAT('%', :city, '%')) " +
           "AND (:categoryName IS NULL OR EXISTS (SELECT 1 FROM e.categories c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%')))) " +
           "ORDER BY e.fecha DESC")
    Slice<EventSummaryDTO> findSummariesByCityAndCategory(
        @Param("city") String city, 
        @Param("categoryName") String categoryName, 
        org.springframework.data.domain.Pageable pageable);
}
