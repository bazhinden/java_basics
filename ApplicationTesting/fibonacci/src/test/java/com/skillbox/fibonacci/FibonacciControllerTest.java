package com.skillbox.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class FibonacciControllerTest extends PostgresTestContainerInitializer {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FibonacciService fibonacciService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Тест получения числа Фибоначчи с допустимым индексом")
    public void testGetNumberWithValidIndex() throws Exception {
        int index = 5;
        FibonacciNumber fibonacciNumber = new FibonacciNumber(index, 5);

        when(fibonacciService.fibonacciNumber(index)).thenReturn(fibonacciNumber);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/fibonacci/{index}", index)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        FibonacciNumber responseNumber = objectMapper.readValue(result.getResponse().getContentAsString(), FibonacciNumber.class);
        assert responseNumber != null;
        assert responseNumber.getIndex() == index;
        assert responseNumber.getValue() == fibonacciNumber.getValue();

        verify(fibonacciService, times(1)).fibonacciNumber(index);
    }

    @Test
    @DisplayName("Тест получения числа Фибоначчи с недопустимым индексом")
    public void testGetNumberWithInvalidIndex() throws Exception {
        int index = -1;
        String errorMessage = "Индекс должен быть больше 0";

        when(fibonacciService.fibonacciNumber(index)).thenThrow(new IllegalArgumentException(errorMessage));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/fibonacci/{index}", index)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assert responseBody.contains(errorMessage);

        verify(fibonacciService, times(1)).fibonacciNumber(index);
    }
}


