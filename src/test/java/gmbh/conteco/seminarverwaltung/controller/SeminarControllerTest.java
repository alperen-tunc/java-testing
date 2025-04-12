package gmbh.conteco.seminarverwaltung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gmbh.conteco.seminarverwaltung.domain.SeminarArt;
import gmbh.conteco.seminarverwaltung.domain.SeminarStatus;
import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminartagDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;
import gmbh.conteco.seminarverwaltung.service.SeminarService;
import gmbh.conteco.seminarverwaltung.service.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeminarController.class)
class SeminarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SeminarService seminarService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllSeminare() throws Exception {
        SeminarMitTagenDto dto = new SeminarMitTagenDto(
                UUID.randomUUID(),
                "S1003",
                "LLM-Integration: LangChain, Haystack & Co.",
                "Oliver",
                SeminarStatus.GEPLANT,
                SeminarArt.ONLINE,
                0,
                0,
                null,
                "https://www.conteco.gmbh/llm-integration-langchain-haystack-co/",
                List.of(new SeminartagDto(LocalDate.of(2025, 6, 10),
                        LocalTime.of(9, 0),
                        LocalTime.of(16, 0)))
        );

        when(seminarService.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/seminare"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].seminarId").value("S1003"))
                .andExpect(jsonPath("$[0].titel").value("LLM-Integration: LangChain, Haystack & Co."))
                .andExpect(jsonPath("$[0].seminartage[0].tagDatum").value("2025-06-10"))
                .andExpect(jsonPath("$[0].seminartage[0].startUhrzeit").value("09:00:00"))
                .andExpect(jsonPath("$[0].seminartage[0].endUhrzeit").value("16:00:00"));
    }

    @Test
    void shouldReturnBadRequest_whenDozentIsInvalid() throws Exception {
        SeminarDto dto = new SeminarDto(
                UUID.randomUUID(),
                "S1002",
                "LLMs in Unternehmen: Chancen & Risiken",
                "nicht_existierender_dozent", // ← unbekannter Benutzername
                SeminarStatus.GEPLANT,
                SeminarArt.ONLINE,
                0,
                0,
                null,
                "https://www.conteco.gmbh/llms-in-unternehmen-chancen-risiken/",
                LocalDateTime.of(2025, 5, 1, 0, 0),
                LocalDateTime.of(2025, 5, 3, 0, 0)
        );

        // 💥 ApiException auslösen, wenn SeminarService.create() aufgerufen wird
        when(seminarService.create(any())).thenThrow(new ApiException("Dozent nicht gefunden oder User ist kein Dozent"));

        mockMvc.perform(post("/api/seminare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Dozent nicht gefunden oder User ist kein Dozent"));
    }
}