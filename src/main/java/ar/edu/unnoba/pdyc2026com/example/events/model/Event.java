package ar.edu.unnoba.pdyc2026com.example.events.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    
    @Enumerated(EnumType.STRING)
    private EventState state = EventState.tentative;
    
    @ManyToMany
    @JoinTable(
        name = "event_artist",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> artists = new ArrayList<>();
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public EventState getState() { return state; }
    public void setState(EventState state) { this.state = state; }
    public List<Artist> getArtists() { return artists; }
    public void setArtists(List<Artist> artists) { this.artists = artists; }
}