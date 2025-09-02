package com.eindopdracht.DJCorner;

import com.eindopdracht.DJCorner.models.Show;
import com.eindopdracht.DJCorner.repositories.ShowRepository;
import com.eindopdracht.DJCorner.services.ShowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ShowControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ShowService showService;

    @Autowired
    private ShowRepository showRepository;

    private Show show1;
    private Show show2;

    @BeforeEach
    void setUp() {
        if(showRepository.count()>0) {
            showRepository.deleteAll();
        }

        show1 = new Show();
        show1.setName("Test Festival");
        show1.setLocation("Amsterdam, Netherlands");
        show1.setDate(LocalDate.of(2025, 10, 10));
        show1.setWebsite("https://testfestival.com");
        show1.setTicketSite("https://tickets.testfestival.com");

        show2 = new Show();
        show2.setName("Hardcore Fest");
        show2.setLocation("Rotterdam, Netherlands");
        show2.setDate(LocalDate.of(2025, 10, 10));
        show2.setWebsite("https://hardcorefest.com");
        show2.setTicketSite("https://tickets.hardcorefest.com");

        showRepository.save(show1);
        showRepository.save(show2);
    }

    @Test
    @DisplayName("Should create new Show")
    void createShow() throws Exception {

        String requestJson = """
                {
                    "name" : "Mega Piraten Festijn",
                    "location" : "Arnhem, Netherlands",
                    "date" : "2025-12-20",
                    "website" : "https://www.novi.nl/",
                    "ticketSite" : "https://www.novi.nl/"
                }
                """;

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/shows")
                    .contentType(APPLICATION_JSON)
                    .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mega Piraten Festijn"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Arnhem, Netherlands"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value("2025-12-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.website").value("https://www.novi.nl/"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketSite").value("https://www.novi.nl/"));
    }

    @Test
    @DisplayName("Should get all Shows")
    public void getShows() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/shows"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(show1.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Festival"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].location").value("Amsterdam, Netherlands"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value("2025-10-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].website").value("https://testfestival.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ticketSite").value("https://tickets.testfestival.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(show2.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Hardcore Fest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].location").value("Rotterdam, Netherlands"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date").value("2025-10-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].website").value("https://hardcorefest.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ticketSite").value("https://tickets.hardcorefest.com"));
    }
}
