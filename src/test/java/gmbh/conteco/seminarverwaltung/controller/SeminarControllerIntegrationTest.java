package gmbh.conteco.seminarverwaltung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gmbh.conteco.seminarverwaltung.domain.SeminarArt;
import gmbh.conteco.seminarverwaltung.domain.SeminarStatus;
import gmbh.conteco.seminarverwaltung.domain.User;
import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeminarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void prepareDozent() {
        User probe = new User();
        probe.setUsername("Vanessa");

        if (userRepository.findOne(Example.of(probe)).isEmpty()) {
            User dozent = new User();
            dozent.setUsername("Vanessa");
            dozent.setIsDozent(true);
            userRepository.save(dozent);
        }
    }

    @Test
    void shouldReturnBadRequest_whenDozentIsMissing() throws Exception {
        SeminarDto dto = new SeminarDto(
                UUID.randomUUID(),
                "S1005",
                "Nicht erlaubter Dozent",
                "unbekannt", // ← kein User vorhanden
                SeminarStatus.GEPLANT,
                SeminarArt.ONLINE,
                0,
                0,
                "Wien",
                "https://example.org",
                LocalDateTime.of(2025, 6, 1, 9, 0),
                LocalDateTime.of(2025, 6, 3, 16, 0)
        );

        mockMvc.perform(post("/api/seminare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Dozent nicht gefunden oder User ist kein Dozent"));
    }
}
