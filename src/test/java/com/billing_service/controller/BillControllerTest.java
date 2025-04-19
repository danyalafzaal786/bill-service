package com.billing_service.controller;

import com.billing_service.dto.CalculateRequest;
import com.billing_service.dto.CalculateResponse;
import com.billing_service.service.BillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class BillControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(billController).build();
    }

    @Test
    void calculate() throws Exception {
        CalculateRequest request = new CalculateRequest();
        // Setup userRequest here with appropriate data

        CalculateResponse response = new CalculateResponse();
        response.setTotalBill(BigDecimal.TEN);
        response.setTotalDiscount(BigDecimal.TWO);
        response.setConversionRate(BigDecimal.ONE);
        response.setTotalPayableBillInTargetCurrency(BigDecimal.valueOf(400));
        response.setTargetCurrency("PKR");

        doReturn(response).when(billService).calculateNetPayableAmount(Mockito.any(CalculateRequest.class));

        mockMvc.perform(post("/api/calculate")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalBill").value(10))
                .andExpect(jsonPath("$.totalDiscount").value(2))
                .andExpect(jsonPath("$.conversionRate").value(1))
                .andExpect(jsonPath("$.totalPayableBillInTargetCurrency").value(400))
                .andExpect(jsonPath("$.targetCurrency").value("PKR"));
    }
}
