package com.eindopdracht.DJCorner.controllers;

import com.eindopdracht.DJCorner.dtos.ShowPatchRequestDto;
import com.eindopdracht.DJCorner.dtos.ShowRequestDto;
import com.eindopdracht.DJCorner.dtos.ShowResponseDto;
import com.eindopdracht.DJCorner.helpers.UriHelper;
import com.eindopdracht.DJCorner.mappers.ShowMapper;
import com.eindopdracht.DJCorner.models.Show;
import com.eindopdracht.DJCorner.repositories.ShowRepository;
import com.eindopdracht.DJCorner.services.ShowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowRepository showRepository;

    public ShowController(ShowService showService, ShowRepository showRepository) {
        this.showService = showService;
        this.showRepository = showRepository;
    }

    @GetMapping
    public ResponseEntity<List<ShowResponseDto>> getAllShows() {
        return ResponseEntity.ok(showService.getShows());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponseDto> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(ShowMapper.toResponseDto(showService.getShowById(id)));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ShowResponseDto>> filterShows(
            @RequestParam(required = false) LocalDate before,
            @RequestParam(required = false) LocalDate after) {

        List<Show> shows;

        if (before != null) {
            shows = showRepository.findByDateBefore(before);
        } else if (after != null) {
            shows = showRepository.findByDateAfter(after);
        } else {
            shows = showRepository.findAll();
        }

        List<ShowResponseDto> result = shows.stream()
                .map(ShowMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(result);
    }


    @PostMapping
    public ResponseEntity<ShowResponseDto> createShow(@Valid @RequestBody ShowRequestDto showRequestDto) {
        Show show = this.showService.createShow(showRequestDto);
        ShowResponseDto showResponseDto = ShowMapper.toResponseDto(show);

        URI uri = UriHelper.buildResourceUri(show.getId());

        return ResponseEntity.created(uri).body(showResponseDto);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<ShowResponseDto> updateShow(@PathVariable Long id, @Valid @RequestBody ShowRequestDto showRequestDto) {
        ShowResponseDto showResponseDto = showService.updateShow(id, showRequestDto);

        return ResponseEntity.ok(showResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShowResponseDto> patchShow (@PathVariable Long id, @Valid @RequestBody ShowPatchRequestDto showPatchRequestDto) {
        ShowResponseDto updatedShow = showService.patchShow(id, showPatchRequestDto);

        return ResponseEntity.ok(updatedShow);
    }
}
