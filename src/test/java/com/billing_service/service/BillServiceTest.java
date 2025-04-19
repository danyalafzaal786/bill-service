package com.billing_service.service;

import com.billing_service.gateway.CurrencyExchangeGateway;
import com.billing_service.dto.CalculateRequest;
import com.billing_service.dto.CalculateResponse;
import com.billing_service.dto.Item;
import com.billing_service.enums.ItemCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

public class BillServiceTest {

    @Mock
    private DiscountService discountService;

    @Mock
    private CurrencyExchangeGateway currencyExchangeGateway;

    @InjectMocks
    private BillService billService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateNetPayableAmount() {
        CalculateRequest request = new CalculateRequest();
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("PKR");
        request.setItems(
                Arrays.asList(
                        new Item("item1", ItemCategory.NON_GROCERY, BigDecimal.valueOf(50)),
                        new Item("item2", ItemCategory.NON_GROCERY, BigDecimal.valueOf(146))
                )
        );

        doReturn(BigDecimal.TEN).when(discountService).calculateDiscount(any(CalculateRequest.class));
        doReturn(BigDecimal.TWO).when(currencyExchangeGateway).getExchangeRate(anyString(), anyString());

        CalculateResponse response = billService.calculateNetPayableAmount(request);

        assertEquals(BigDecimal.valueOf(196), response.getTotalBill());
        assertEquals(BigDecimal.TEN, response.getTotalDiscount());
        assertEquals(BigDecimal.TWO, response.getConversionRate());
        assertEquals(BigDecimal.valueOf(372), response.getTotalPayableBillInTargetCurrency());
        assertEquals("PKR", response.getTargetCurrency());
    }
}
