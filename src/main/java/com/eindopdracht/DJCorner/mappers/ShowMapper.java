package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.ShowRequestDto;
import com.eindopdracht.DJCorner.dtos.ShowResponseDto;
import com.eindopdracht.DJCorner.models.Show;
import org.springframework.stereotype.Component;

@Component
public class ShowMapper {

    public static Show toEntity(ShowRequestDto showRequestDto) {
        Show show = new Show();

        show.setName(showRequestDto.getName());
        show.setLocation(showRequestDto.getLocation());
        show.setDate(showRequestDto.getDate());
        show.setWebsite(showRequestDto.getWebsite());
        show.setTicketSite(showRequestDto.getTicketSite());

        return show;
    }

    public static ShowResponseDto toResponseDto(Show show) {
        ShowResponseDto showResponseDto = new ShowResponseDto();

        showResponseDto.setId(show.getId());
        showResponseDto.setName(show.getName());
        showResponseDto.setLocation(show.getLocation());
        showResponseDto.setDate(show.getDate());
        showResponseDto.setWebsite(show.getWebsite());
        showResponseDto.setTicketSite(show.getTicketSite());

        return showResponseDto;
    }
}
