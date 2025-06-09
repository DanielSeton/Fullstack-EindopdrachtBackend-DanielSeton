package com.eindopdracht.DJCorner.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private LocalDate date;
    private String website;
    private String ticketSite;

    public Show() { }

    public Show(String name, String location, LocalDate date, String website, String ticketSite) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.website = website;
        this.ticketSite = ticketSite;
    }

    public Long getId() {
        return id;
    }

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
