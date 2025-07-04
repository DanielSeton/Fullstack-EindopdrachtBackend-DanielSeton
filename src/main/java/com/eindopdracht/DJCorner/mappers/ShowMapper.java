package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.ShowRequestDto;
import com.eindopdracht.DJCorner.dtos.ShowResponseDto;
import com.eindopdracht.DJCorner.models.Show;

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

        showResponseDto.id = show.getId();
        showResponseDto.name = show.getName();
        showResponseDto.location = show.getLocation();
        showResponseDto.date = show.getDate();
        showResponseDto.website = show.getWebsite();
        showResponseDto.ticketSite = show.getTicketSite();

        return showResponseDto;
    }
}
