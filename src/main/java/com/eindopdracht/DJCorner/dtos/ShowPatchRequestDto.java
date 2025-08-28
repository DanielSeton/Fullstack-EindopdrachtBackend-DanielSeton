package com.eindopdracht.DJCorner.dtos;

import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public class ShowPatchRequestDto {
    private String name;
    private String location;
    private LocalDate date;
    @URL
    private String website;
    @URL
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
