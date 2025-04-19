package com.billing_service.gateway;

import com.billing_service.gateway.dto.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class CurrencyExchangeGateway {
    @Value("${currency.exchange.api.url}")
    private String currencyExchangeApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("exchangeRates")
    public BigDecimal getExchangeRate(String from, String to) {
        return Objects.requireNonNull(restTemplate.getForEntity(
                String.format("%s/pair/%s/%s", currencyExchangeApiUrl, from, to),
                CurrencyExchangeResponse.class
        ).getBody()).getConversion_rate();
    }
}
