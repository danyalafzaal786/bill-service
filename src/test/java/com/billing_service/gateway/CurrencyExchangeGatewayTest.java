package com.billing_service.gateway;

import com.billing_service.gateway.dto.CurrencyExchangeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

public class CurrencyExchangeGatewayTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    CurrencyExchangeGateway currencyExchangeGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Replace the actual RestTemplate with the mock
        ReflectionTestUtils.setField(currencyExchangeGateway, "restTemplate", restTemplate);
    }

    @Test
    void getExchangeRateWithValidResponse() {
        CurrencyExchangeResponse currencyExchangeResponse = new CurrencyExchangeResponse();
        currencyExchangeResponse.setConversion_rate(BigDecimal.TWO);

        doReturn(ResponseEntity.ok(currencyExchangeResponse))
                .when(restTemplate).getForEntity(anyString(), eq(CurrencyExchangeResponse.class));

        BigDecimal rate = currencyExchangeGateway.getExchangeRate("USD", "PKR");

        assertEquals(BigDecimal.TWO, rate);
    }

    @Test
    void getExchangeRateWithInvalidResponse() {
        doReturn(ResponseEntity.notFound().build())
                .when(restTemplate).getForEntity(anyString(), eq(CurrencyExchangeResponse.class));

        assertThrows(NullPointerException.class, () -> currencyExchangeGateway.getExchangeRate("USD", "PKR"));
    }
}
