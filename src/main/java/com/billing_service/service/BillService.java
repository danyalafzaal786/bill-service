package com.billing_service.service;

import com.billing_service.gateway.CurrencyExchangeGateway;
import com.billing_service.dto.CalculateRequest;
import com.billing_service.dto.CalculateResponse;
import com.billing_service.dto.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillService {
    private final DiscountService discountService;
    private final CurrencyExchangeGateway currencyExchangeGateway;

    public BillService(DiscountService discountService, CurrencyExchangeGateway currencyExchangeGateway) {
        this.discountService = discountService;
        this.currencyExchangeGateway = currencyExchangeGateway;
    }

    public CalculateResponse calculateNetPayableAmount(CalculateRequest request) {
        BigDecimal totalBill = request.getItems().stream()
                .map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDiscount = discountService.calculateDiscount(request);
        BigDecimal rate = currencyExchangeGateway.getExchangeRate(request.getOriginalCurrency(),
                request.getTargetCurrency());

        return CalculateResponse.from(
                totalBill, totalDiscount, rate, totalBill.subtract(totalDiscount).multiply(rate), request.getTargetCurrency()
        );
    }
}
