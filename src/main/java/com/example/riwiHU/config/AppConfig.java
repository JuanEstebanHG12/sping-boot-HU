package com.example.riwiHU.config;

import com.example.riwiHU.model.Events;
import com.example.riwiHU.model.Venue;
import com.example.riwiHU.repository.GenericMemoryRepositoryImpl;
import com.example.riwiHU.repository.GenericRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class AppConfig {

    @Bean
    public GenericRepository<Venue, Integer> venueRepository() {
        return new GenericMemoryRepositoryImpl<>(Venue::getId);
    }

//    @Bean
//    public CommandLineRunner seedData(VenueRepository venueRepository, EventsRepository eventsRepository) {
//        return args -> {
//            venueRepository.save(new Venue(1L, "Movistar Arena", "Bogota", "Diagonal 61C #26-36", 14000));
//            venueRepository.save(new Venue(2L, "Teatro Metropolitano", "Medellin", "Calle 41 #57-30", 1600));
//            venueRepository.save(new Venue(3L, "Centro de Eventos Valle del Pacifico", "Cali", "Autopista Cali-Yumbo", 12000));
//
//            eventsRepository.save(new Events(
//                    1L,
//                    "Tech Summit Eventify",
//                    "Conferencia interna sobre plataformas escalables.",
//                    LocalDateTime.of(2026, 6, 15, 9, 0),
//                    1L
//            ));
//            eventsRepository.save(new Events(
//                    2L,
//                    "Noche de Jazz",
//                    "Evento musical en formato de auditorio.",
//                    LocalDateTime.of(2026, 7, 20, 20, 0),
//                    2L
//            ));
//            eventsRepository.save(new Events(
//                    3L,
//                    "Expo Emprendimiento",
//                    "Feria para conectar marcas, aliados y asistentes.",
//                    LocalDateTime.of(2026, 8, 5, 10, 30),
//                    3L
//            ));
//        };
//    }
}
