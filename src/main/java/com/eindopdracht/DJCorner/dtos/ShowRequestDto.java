package com.eindopdracht.DJCorner.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class ShowRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotEmpty
    private LocalDate date;
    @NotBlank
    private String website;
    private String ticketSite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTicketSite() {
        return ticketSite;
    }

    public void setTicketSite(String ticketSite) {
        this.ticketSite = ticketSite;
    }
}
