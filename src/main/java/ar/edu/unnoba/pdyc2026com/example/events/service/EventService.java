package ar.edu.unnoba.pdyc2026com.example.events.service;

import ar.edu.unnoba.pdyc2026com.example.events.model.*;
import ar.edu.unnoba.pdyc2026com.example.events.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private ArtistRepository artistRepository;
    
    // Artists
    public List<Artist> getAllArtists(Genre genre) {
        if (genre != null) return artistRepository.findByGenre(genre);
        return artistRepository.findAll();
    }
    
    public Artist getArtist(Long id) {
        return artistRepository.findById(id).orElseThrow();
    }
    
    public Artist createArtist(String name, Genre genre) {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setGenre(genre);
        return artistRepository.save(artist);
    }
    
    public Artist updateArtist(Long id, String name, Genre genre) {
        Artist artist = getArtist(id);
        artist.setName(name);
        artist.setGenre(genre);
        return artistRepository.save(artist);
    }
    
    public void deleteArtist(Long id) {
        Artist artist = getArtist(id);
        if (!artist.getEvents().isEmpty()) {
            artist.setActive(false);
            artistRepository.save(artist);
        } else {
            artistRepository.delete(artist);
        }
    }
    
    // Events
    public List<Event> getAllEvents(EventState state) {
        if (state != null) return eventRepository.findByState(state);
        return eventRepository.findAll();
    }
    
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }
    
    public Event createEvent(String name, LocalDate startDate, String description) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Erro: Não é possível criar evento com data passada");
        }
        Event event = new Event();
        event.setName(name);
        event.setStartDate(startDate);
        event.setDescription(description);
        return eventRepository.save(event);
    }
    
    public Event updateEvent(Long id, String name, LocalDate startDate, String description) {
        Event event = getEvent(id);
        if (event.getState() != EventState.tentative) {
            throw new RuntimeException("Erro: Evento não está em estado tentative");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Erro: Não é possível alterar para data passada");
        }
        event.setName(name);
        event.setStartDate(startDate);
        event.setDescription(description);
        return eventRepository.save(event);
    }
    
    public void deleteEvent(Long id) {
        Event event = getEvent(id);
        if (event.getState() != EventState.tentative) {
            throw new RuntimeException("Só é possível deletar eventos tentative");
        }
        eventRepository.delete(event);
    }
    
    public void addArtistToEvent(Long eventId, Long artistId) {
        Event event = getEvent(eventId);
        Artist artist = artistRepository.findById(artistId).orElseThrow();
        if (event.getState() != EventState.tentative) {
            throw new RuntimeException("Só é possível adicionar artistas em eventos tentative");
        }
        if (!artist.isActive()) {
            throw new RuntimeException("Artista está desativado");
        }
        event.getArtists().add(artist);
        eventRepository.save(event);
    }
    
    public void removeArtistFromEvent(Long eventId, Long artistId) {
        Event event = getEvent(eventId);
        if (event.getState() != EventState.tentative) {
            throw new RuntimeException("Só é possível remover artistas de eventos tentative");
        }
        event.getArtists().removeIf(a -> a.getId().equals(artistId));
        eventRepository.save(event);
    }
    
    public void confirmEvent(Long id) {
        Event event = getEvent(id);
        if (event.getState() != EventState.tentative) {
            throw new RuntimeException("Erro: Só é possível confirmar eventos tentative");
        }
        if (event.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Erro: Não é possível confirmar evento com data passada");
        }
        event.setState(EventState.confirmed);
        eventRepository.save(event);
    }
    
    public void rescheduleEvent(Long id, LocalDate newDate) {
        Event event = getEvent(id);
        if (event.getState() != EventState.confirmed && event.getState() != EventState.rescheduled) {
            throw new RuntimeException("Erro: Só é possível reprogramar eventos confirmed ou rescheduled");
        }
        if (event.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Erro: Não é possível reprogramar evento já realizado");
        }
        if (newDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Erro: Nova data deve ser futura");
        }
        event.setStartDate(newDate);
        event.setState(EventState.rescheduled);
        eventRepository.save(event);
    }
    
    public void cancelEvent(Long id) {
        Event event = getEvent(id);
        if (event.getState() != EventState.confirmed && event.getState() != EventState.rescheduled) {
            throw new RuntimeException("Só é possível cancelar eventos confirmed ou rescheduled");
        }
        event.setState(EventState.cancelled);
        eventRepository.save(event);
    }
}