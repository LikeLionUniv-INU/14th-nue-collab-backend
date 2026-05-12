package com.sinsal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SinSalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsRequiredCodeWhenBirthDateIsMissing() throws Exception {
        mockMvc.perform(get("/api/sinsals"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("SINSAL_001"))
                .andExpect(jsonPath("$.message").value("생년월일은 필수입니다."));
    }

    @Test
    void returnsInvalidFormatCodeWhenBirthDateFormatIsInvalid() throws Exception {
        mockMvc.perform(get("/api/sinsals").param("birthDate", "20020412"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("SINSAL_002"))
                .andExpect(jsonPath("$.message").value("생년월일 형식이 올바르지 않습니다. (예: 2002-04-12)"));
    }

    @Test
    void returnsNotPastCodeWhenBirthDateIsNotPast() throws Exception {
        mockMvc.perform(get("/api/sinsals").param("birthDate", LocalDate.now().toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("SINSAL_003"))
                .andExpect(jsonPath("$.message").value("생년월일은 과거 날짜여야 합니다."));
    }
}
