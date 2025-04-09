package ru.volodin.vacation.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.volodin.vacation.service.CalculateService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculateController.class)
public class CalculateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculateService calculateService;

    @Test
    void testGetVacationCompensationEazy_success() throws Exception {
        Mockito.when(calculateService.getVacationCompensationEazy(60000f, 14))
                .thenReturn(2301.37f);

        mockMvc.perform(get("/vacation/calculate/ez")
                        .param("salary", "60000")
                        .param("dayCount", "14"))
                .andExpect(status().isOk())
                .andExpect(content().string("2301.37"));
    }

    @Test
    void testGetVacationCompensationEazy_badRequest() throws Exception {
        Mockito.when(calculateService.getVacationCompensationEazy(0f, 14))
                .thenThrow(new IllegalArgumentException("Неверно указана зарплата или дата."));

        mockMvc.perform(get("/vacation/calculate/ez")
                        .param("salary", "0")
                        .param("dayCount", "14"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Неверно указана зарплата или дата."));
    }

    @Test
    void testGetVacationCompensationHard_success() throws Exception {
        LocalDate start = LocalDate.of(2025, 5, 1);
        LocalDate finish = LocalDate.of(2025, 5, 14);

        Mockito.when(calculateService.getVacationCompensationHard(80000f, start, finish))
                .thenReturn(3012.50f);

        mockMvc.perform(get("/vacation/calculate/hard")
                        .param("salary", "80000")
                        .param("start", "2025-05-01")
                        .param("finish", "2025-05-14"))
                .andExpect(status().isOk())
                .andExpect(content().string("3012.5"));
    }

    @Test
    void testGetVacationCompensationHard_badRequest() throws Exception {
        LocalDate start = LocalDate.of(2025, 6, 10);
        LocalDate finish = LocalDate.of(2025, 6, 5);

        Mockito.when(calculateService.getVacationCompensationHard(50000f, start, finish))
                .thenThrow(new IllegalArgumentException("Неверно указана зарплата или дата."));

        mockMvc.perform(get("/vacation/calculate/hard")
                        .param("salary", "50000")
                        .param("start", "2025-06-10")
                        .param("finish", "2025-06-05"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Неверно указана зарплата или дата."));
    }
}
