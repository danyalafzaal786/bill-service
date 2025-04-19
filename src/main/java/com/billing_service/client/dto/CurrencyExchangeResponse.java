package com.billing_service.client.dto;

import java.math.BigDecimal;

public class CurrencyExchangeResponse {
    private BigDecimal conversion_rate;

    public BigDecimal getConversion_rate() {
        return conversion_rate;
    }

    public void setConversion_rate(BigDecimal conversion_rate) {
        this.conversion_rate = conversion_rate;
    }
}
