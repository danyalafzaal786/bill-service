package com.billing_service.service;

import com.billing_service.client.CurrencyExchangeClient;
import com.billing_service.dto.CalculateRequest;
import com.billing_service.dto.CalculateResponse;
import com.billing_service.dto.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillService {
    private final DiscountService discountService;
    private final CurrencyExchangeClient currencyExchangeClient;

    public BillService(DiscountService discountService, CurrencyExchangeClient currencyExchangeClient) {
        this.discountService = discountService;
        this.currencyExchangeClient = currencyExchangeClient;
    }

    public CalculateResponse calculateNetPayableAmount(CalculateRequest request) {
        BigDecimal totalBill = request.getItems().stream()
                .map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDiscount = discountService.calculateDiscount(request);
        BigDecimal rate = currencyExchangeClient.getExchangeRate(request.getOriginalCurrency(),
                request.getTargetCurrency());

        return CalculateResponse.from(
                totalBill, totalDiscount, rate, totalBill.subtract(totalDiscount).multiply(rate), request.getTargetCurrency()
        );
    }
}
