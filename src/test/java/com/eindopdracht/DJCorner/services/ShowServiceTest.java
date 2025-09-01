package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.ShowPatchRequestDto;
import com.eindopdracht.DJCorner.dtos.ShowRequestDto;
import com.eindopdracht.DJCorner.dtos.ShowResponseDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.models.Show;
import com.eindopdracht.DJCorner.repositories.ShowRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowServiceTest {

    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private ShowService showService;

    private Show show;
    private Show show1;

    @BeforeEach
    void setUp() {
        show = new Show();
        show.setId(1L);
        show.setLocation("test location");
        show.setName("test name");
        show.setDate(LocalDate.of(2025, 2, 19));
        show.setWebsite("test website");
        show.setTicketSite("test ticket site");

        show1 = new Show();
        show1.setId(2L);
        show1.setLocation("test location");
        show1.setName("other test name");
        show1.setDate(LocalDate.of(2025, 2, 19));
        show1.setWebsite("new test website");
        show1.setTicketSite("new test ticket site");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should create new Show")
    void createShow() {
        // Arrange
        ShowRequestDto requestDto = new ShowRequestDto();
        requestDto.setName("New Show");
        requestDto.setLocation("New Location");
        requestDto.setDate(LocalDate.of(2025, 12, 24));
        requestDto.setWebsite("newshow.com");
        requestDto.setTicketSite("tickets.newshow.com");

        Show savedShow = new Show();
        savedShow.setName(requestDto.getName());
        savedShow.setLocation(requestDto.getLocation());
        savedShow.setDate(requestDto.getDate());
        savedShow.setWebsite(requestDto.getWebsite());
        savedShow.setTicketSite(requestDto.getTicketSite());

        when(showRepository.save(any(Show.class))).thenReturn(savedShow);

        // Act
        Show result = showService.createShow(requestDto);

        // Assert
        assertEquals("New Show", result.getName());
        assertEquals("New Location", result.getLocation());
        assertEquals(LocalDate.of(2025, 12, 24), result.getDate());
        assertEquals("newshow.com", result.getWebsite());
        assertEquals("tickets.newshow.com", result.getTicketSite());

        ArgumentCaptor<Show> showCaptor = ArgumentCaptor.forClass(Show.class);
        verify(showRepository).save(showCaptor.capture());
        Show captured = showCaptor.getValue();

        assertEquals("New Show", captured.getName());
        assertEquals("New Location", captured.getLocation());
        assertEquals(LocalDate.of(2025, 12, 24), captured.getDate());
        assertEquals("newshow.com", captured.getWebsite());
        assertEquals("tickets.newshow.com", captured.getTicketSite());
    }

    @Test
    @DisplayName("Should get all Shows")
    void getShows() {
        // Arrange
        when(showRepository.findAll()).thenReturn(List.of(show, show1));

        // Act
        List<ShowResponseDto> result = showService.getShows();

        // Assert
        assertEquals(2, result.size());

        ShowResponseDto dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("test location", dto1.getLocation());
        assertEquals("test name", dto1.getName());
        assertEquals(LocalDate.of(2025, 2, 19), dto1.getDate());
        assertEquals("test website", dto1.getWebsite());
        assertEquals("test ticket site", dto1.getTicketSite());

        ShowResponseDto dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("test location", dto2.getLocation());
        assertEquals("other test name", dto2.getName());
        assertEquals(LocalDate.of(2025, 2, 19), dto2.getDate());
        assertEquals("new test website", dto2.getWebsite());
        assertEquals("new test ticket site", dto2.getTicketSite());
    }

    @Test
    @DisplayName("Should get Show by id")
    void getShowById() {
        // Arrange
        when(showRepository.findById(1L)).thenReturn(Optional.of(show));

        // Act
        Show result = showService.getShowById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("test location", result.getLocation());
        assertEquals("test name", result.getName());
        assertEquals(LocalDate.of(2025, 2, 19), result.getDate());
        assertEquals("test website", result.getWebsite());
        assertEquals("test ticket site", result.getTicketSite());
    }

    @Test
    @DisplayName("Should throw ResourceNotFound if Show not found when fetching")
    void getShowById_ResourceNotFound() {
        // Arrange
        when(showRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
                showService.getShowById(1L);
        });
    }

    @Test
    @DisplayName("Should delete Show")
    void deleteShow() {
        // Arrange
        when(showRepository.findById(1L)).thenReturn(Optional.of(show));

        // Act
        showService.deleteShow(1L);

        // Assert
        verify(showRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFound if Show not found when deleting")
    void deleteShow_ResourceNotFound() {
        // Arrange
        when(showRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            showService.deleteShow(1L);
        });
    }

    @Test
    @DisplayName("Should update Show")
    void updateShow() {
        // Arrange
        ShowRequestDto updateDto = new ShowRequestDto();
        updateDto.setName("Updated Name");
        updateDto.setLocation("Updated Location");
        updateDto.setDate(LocalDate.of(2026, 1, 1));
        updateDto.setWebsite("updatedwebsite.com");
        updateDto.setTicketSite("updatedticketsite.com");

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(showRepository.save(show)).thenReturn(show);

        // Act
        ShowResponseDto result = showService.updateShow(1L, updateDto);

        // Assert
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Location", result.getLocation());
        assertEquals(LocalDate.of(2026, 1, 1), result.getDate());
        assertEquals("updatedwebsite.com", result.getWebsite());
        assertEquals("updatedticketsite.com", result.getTicketSite());
    }

    @Test
    @DisplayName("Should throw ResourceNotFound if Show not found when updating")
    void updateShow_ResourceNotFound() {
        // Arrange
        ShowRequestDto dummyDto = new ShowRequestDto();
        dummyDto.setName("placeholder");

        when(showRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            showService.updateShow(1L, dummyDto);
        });
    }

    @Test
    @DisplayName("Should patch Show")
    void patchShow() {
        // Arrange
        ShowPatchRequestDto patchDto = new ShowPatchRequestDto();
        patchDto.setName("Updated Name");
        patchDto.setLocation(null);
        patchDto.setDate(LocalDate.of(2026, 1, 1));
        patchDto.setWebsite("updatedwebsite.com");
        patchDto.setTicketSite("updatedticketsite.com");

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(showRepository.save(show)).thenReturn(show);

        // Act
        ShowResponseDto result = showService.patchShow(1L, patchDto);

        // Assert
        assertEquals("Updated Name", result.getName());
        assertEquals("test location", result.getLocation());
        assertEquals(LocalDate.of(2026, 1, 1), result.getDate());
        assertEquals("updatedwebsite.com", result.getWebsite());
        assertEquals("updatedticketsite.com", result.getTicketSite());
    }

    @Test
    @DisplayName("Should only patch Show location")
    void patchShowLocation() {
        // Arrange
        ShowPatchRequestDto patchDto = new ShowPatchRequestDto();
        patchDto.setName(null);
        patchDto.setLocation("Patched location");
        patchDto.setDate(null);
        patchDto.setWebsite(null);
        patchDto.setTicketSite(null);

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(showRepository.save(show)).thenReturn(show);

        // Act
        ShowResponseDto result = showService.patchShow(1L, patchDto);

        // Assert
        assertEquals("test name", result.getName());
        assertEquals("Patched location", result.getLocation());
        assertEquals(LocalDate.of(2025, 2, 19), result.getDate());
        assertEquals("test website", result.getWebsite());
        assertEquals("test ticket site", result.getTicketSite());
    }

    @Test
    @DisplayName("Should throw ResourceNotFound if Show not found when patching")
    void patchShow_ResourceNotFound() {
        // Arrange
        ShowPatchRequestDto dummyDto = new ShowPatchRequestDto();
        dummyDto.setName("placeholder");

        when(showRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            showService.patchShow(1L, dummyDto);
        });
    }
}