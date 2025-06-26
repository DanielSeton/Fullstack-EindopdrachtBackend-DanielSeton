package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.ShowRequestDto;
import com.eindopdracht.DJCorner.dtos.ShowResponseDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.ShowMapper;
import com.eindopdracht.DJCorner.models.Show;
import com.eindopdracht.DJCorner.repositories.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Show createShow(ShowRequestDto showRequestDto) {
        return this.showRepository.save(ShowMapper.toEntity(showRequestDto));
    }

    public List<ShowResponseDto> getAllShows() {
        List<Show> showList = showRepository.findAll();
        List<ShowResponseDto> showResponseDtoList = new ArrayList<>();

        for (Show show : showList) {
            ShowResponseDto showResponseDto = ShowMapper.toResponseDto(show);
            showResponseDtoList.add(showResponseDto);
        }

        return showResponseDtoList;
    }

    public Show getSingleShow(Long id) {
        return this.showRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object with id: " + id + " not found"));
    }

    public void deleteShow(Long id) {
        Optional<Show> showOptional = this.showRepository.findById(id);

        if (showOptional.isEmpty()) {
            throw new ResourceNotFoundException("Object with id: " + id + " not found");
        }

        showRepository.deleteById(id);
    }

    public ShowResponseDto updateShow(Long id, ShowRequestDto showRequestDto) {
        Show show = showRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object with id: " + id + " not found"));

        show.setName(showRequestDto.getName());
        show.setLocation(showRequestDto.getLocation());
        show.setDate(showRequestDto.getDate());
        show.setWebsite(showRequestDto.getWebsite());
        show.setTicketSite(showRequestDto.getTicketSite());

        Show updatedShow = this.showRepository.save(show);

        return ShowMapper.toResponseDto(updatedShow);
    }

    public ShowResponseDto patchShow(Long id, ShowRequestDto showRequestDto) {
        Show show = showRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object with id: " + id + " not found"));

        if (showRequestDto.getName() != null) {
            show.setName(showRequestDto.getName());
        }
        if (showRequestDto.getLocation() != null) {
            show.setLocation(showRequestDto.getLocation());
        }
        if (showRequestDto.getDate() != null) {
            show.setDate(showRequestDto.getDate());
        }
        if (showRequestDto.getWebsite() != null) {
            show.setWebsite(showRequestDto.getWebsite());
        }
        if (showRequestDto.getTicketSite() != null) {
            show.setTicketSite(showRequestDto.getTicketSite());
        }

        Show returnShow = showRepository.save(show);
        return ShowMapper.toResponseDto(returnShow);
    }
}
