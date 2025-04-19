package com.billing_service.service;

import com.billing_service.dto.CalculateRequest;
import com.billing_service.dto.Item;
import com.billing_service.enums.ItemCategory;
import com.billing_service.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateDiscountWithEmployeeRequest() {
        CalculateRequest request = new CalculateRequest();
        request.setUserType(UserType.EMPLOYEE);
        request.setItems(
                Arrays.asList(
                        new Item("item1", ItemCategory.NON_GROCERY, BigDecimal.valueOf(50)),
                        new Item("item2", ItemCategory.NON_GROCERY, BigDecimal.valueOf(146))
                )
        );

        BigDecimal discount = discountService.calculateDiscount(request);

        assertEquals(BigDecimal.valueOf(63.8), discount);
    }

    @Test
    void calculateDiscountWithAffiliateRequest() {
        CalculateRequest request = new CalculateRequest();
        request.setUserType(UserType.AFFILIATE);
        request.setItems(
                List.of(new Item("item1", ItemCategory.NON_GROCERY, BigDecimal.valueOf(50)))
        );

        BigDecimal discount = discountService.calculateDiscount(request);

        assertEquals(BigDecimal.valueOf(5.0), discount);
    }

    @Test
    void calculateDiscountWithCustomerRequest() {
        CalculateRequest request = new CalculateRequest();
        request.setUserType(UserType.CUSTOMER);
        request.setCustomerTenure(3);
        request.setItems(
                List.of(new Item("item1", ItemCategory.NON_GROCERY, BigDecimal.valueOf(50)))
        );

        BigDecimal discount = discountService.calculateDiscount(request);

        assertEquals(BigDecimal.valueOf(2.50).setScale(2, RoundingMode.DOWN), discount);
    }

    @Test
    void calculateDiscountWithCustomerRequestHavingAnYearTenure() {
        CalculateRequest request = new CalculateRequest();
        request.setUserType(UserType.CUSTOMER);
        request.setCustomerTenure(1);
        request.setItems(
                List.of(new Item("item1", ItemCategory.NON_GROCERY, BigDecimal.valueOf(50)))
        );

        BigDecimal discount = discountService.calculateDiscount(request);

        assertEquals(BigDecimal.ZERO.setScale(1, RoundingMode.DOWN), discount);
    }

    @Test
    void calculateDiscountWithGroceryItem() {
        CalculateRequest request = new CalculateRequest();
        request.setUserType(UserType.EMPLOYEE);
        request.setItems(
                List.of(new Item("item1", ItemCategory.GROCERY, BigDecimal.valueOf(250)))
        );

        BigDecimal discount = discountService.calculateDiscount(request);

        assertEquals(BigDecimal.TEN.setScale(1, RoundingMode.DOWN), discount);
    }
}
