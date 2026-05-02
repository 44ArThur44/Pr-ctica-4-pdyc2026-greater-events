package ar.edu.unnoba.pdyc2026com.example.events.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;  // ← ADICIONE ESTA LINHA
import java.util.ArrayList;
import java.util.List;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private boolean active = true;
    
    @ManyToMany(mappedBy = "artists")
    @JsonIgnore 
    private List<Event> events = new ArrayList<>();
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
}