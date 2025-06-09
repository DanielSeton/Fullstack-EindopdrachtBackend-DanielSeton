package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByDateAfter(LocalDate date);
    List<Show> findByDateBefore(LocalDate date);
}
