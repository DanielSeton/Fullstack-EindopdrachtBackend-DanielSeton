package com.eindopdracht.DJCorner.controllers;

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
import java.util.Optional;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public ResponseEntity<List<ShowResponseDto>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponseDto> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(ShowMapper.toResponseDto(this.showService.getSingleShow(id)));
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

    @PatchMapping
    public ResponseEntity<ShowResponseDto> patchShow (@PathVariable Long id, @Valid @RequestBody ShowRequestDto showRequestDto) {
        ShowResponseDto updatedShow = showService.patchShow(id, showRequestDto);

        return ResponseEntity.ok(updatedShow);
    }
}
