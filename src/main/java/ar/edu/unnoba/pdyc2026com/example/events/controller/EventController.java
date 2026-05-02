package ar.edu.unnoba.pdyc2026com.example.events.controller;

import ar.edu.unnoba.pdyc2026com.example.events.model.*;
import ar.edu.unnoba.pdyc2026com.example.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class EventController {
    
    @Autowired
    private EventService eventService;
    
    @GetMapping("/artists")
    public List<Artist> getArtists(@RequestParam(required = false) Genre genre) {
        return eventService.getAllArtists(genre);
    }
    
    @GetMapping("/artists/{id}")
    public Artist getArtist(@PathVariable Long id) {
        return eventService.getArtist(id);
    }
    
    @PostMapping("/artists")
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Map<String, String> body) {
        return eventService.createArtist(
            body.get("name"),
            Genre.valueOf(body.get("genre"))
        );
    }
    
    @PutMapping("/artists/{id}")
    public Artist updateArtist(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return eventService.updateArtist(id, body.get("name"), Genre.valueOf(body.get("genre")));
    }
    
    @DeleteMapping("/artists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        eventService.deleteArtist(id);
    }
    
    @GetMapping("/events")
    public List<Map<String, Object>> getEvents(@RequestParam(required = false) EventState state) {
        List<Event> events = eventService.getAllEvents(state);
        return events.stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("name", e.getName());
            map.put("startDate", e.getStartDate());
            map.put("state", e.getState());
            map.put("artistsCount", e.getArtists().size());
            return map;
        }).toList();
    }
    
    @GetMapping("/events/{id}")
    public Event getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }
    
    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Map<String, String> body) {
        return eventService.createEvent(
            body.get("name"),
            LocalDate.parse(body.get("start_date")),
            body.get("description")
        );
    }
    
    @PutMapping("/events/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return eventService.updateEvent(
            id,
            body.get("name"),
            LocalDate.parse(body.get("start_date")),
            body.get("description")
        );
    }
    
    @DeleteMapping("/events/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
    
    @PostMapping("/events/{id}/artists")
    public void addArtistToEvent(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        eventService.addArtistToEvent(id, body.get("artist_id"));
    }
    
    @DeleteMapping("/events/{id}/artist/{artistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArtistFromEvent(@PathVariable Long id, @PathVariable Long artistId) {
        eventService.removeArtistFromEvent(id, artistId);
    }
    
    @PutMapping("/events/{id}/confirmed")
    public void confirmEvent(@PathVariable Long id) {
        eventService.confirmEvent(id);
    }
    
    @PutMapping("/events/{id}/rescheduled")
    public void rescheduleEvent(@PathVariable Long id, @RequestBody Map<String, String> body) {
        eventService.rescheduleEvent(id, LocalDate.parse(body.get("start_date")));
    }
    
    @PutMapping("/events/{id}/canceled")
    public void cancelEvent(@PathVariable Long id) {
        eventService.cancelEvent(id);
    }
}