package ar.edu.unnoba.pdyc2026com.example.events.repository;

import ar.edu.unnoba.pdyc2026com.example.events.model.Event;
import ar.edu.unnoba.pdyc2026com.example.events.model.EventState;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByState(EventState state);
}