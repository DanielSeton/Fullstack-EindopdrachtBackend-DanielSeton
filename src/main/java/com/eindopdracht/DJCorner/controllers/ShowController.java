package com.eindopdracht.DJCorner.controllers;

import com.eindopdracht.DJCorner.models.Show;
import com.eindopdracht.DJCorner.repositories.ShowRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(this.showRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        Optional<Show> show = showRepository.findById(id);

        if (show.isPresent()) {
            return ResponseEntity.ok(show.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/before")
    public ResponseEntity<List<Show>> getShowsBefore(@RequestParam LocalDate date) {
        return ResponseEntity.ok(this.showRepository.findByDateBefore(date));
    }

    @GetMapping("/after")
    public ResponseEntity<List<Show>> getShowsAfter(@RequestParam LocalDate date) {
        return ResponseEntity.ok(this.showRepository.findByDateAfter(date));
    }



    @PostMapping
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        return ResponseEntity.ok(this.showRepository.save(show));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Show> deleteShow(@PathVariable Long id) {
        Optional<Show> show = showRepository.findById(id);

        if (show.isPresent()){
            this.showRepository.delete(show.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody Show newShow) {
        Optional<Show> show = showRepository.findById(id);

        if (show.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            Show show1 = show.get();

            show1.setName(newShow.getName());
            show1.setLocation(newShow.getLocation());
            show1.setDate(newShow.getDate());
            show1.setWebsite(newShow.getWebsite());
            show1.setTicketSite(newShow.getTicketSite());

            Show returnShow = showRepository.save(show1);

            return ResponseEntity.ok().body(returnShow);
        }
    }



    @PatchMapping("/{id}")
    public ResponseEntity<Show> patchShow(@PathVariable Long id, @RequestBody Show newShow) {
        Optional<Show> show = showRepository.findById(id);

        if (show.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Show show1 = show.get();
            if (newShow.getName() != null) {
                show1.setName(newShow.getName());
            }
            if (newShow.getLocation() != null) {
                show1.setLocation(newShow.getLocation());
            }
            if (newShow.getDate() != null) {
                show1.setDate(newShow.getDate());
            }
            if (newShow.getWebsite() != null) {
                show1.setWebsite(newShow.getWebsite());
            }
            if (newShow.getTicketSite() != null) {
                show1.setTicketSite(newShow.getTicketSite());
            }


            Show returnShow = showRepository.save(show1);
            return ResponseEntity.ok().body(returnShow);
        }
    }
}
