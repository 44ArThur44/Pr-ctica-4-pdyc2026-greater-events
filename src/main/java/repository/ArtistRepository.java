package ar.edu.unnoba.pdyc2026com.example.events.repository;

import ar.edu.unnoba.pdyc2026com.example.events.model.Artist;
import ar.edu.unnoba.pdyc2026com.example.events.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByGenre(Genre genre);
}