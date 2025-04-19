package com.billing_service.service;

import com.billing_service.dto.CalculateRequest;
import com.billing_service.dto.Item;
import com.billing_service.enums.ItemCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractMap;

import static java.util.stream.Collectors.*;

@Service
public class DiscountService {

    public BigDecimal calculateDiscount(CalculateRequest request) {
        AbstractMap.SimpleEntry<BigDecimal, BigDecimal> prices = request.getItems().stream().collect(
                teeing(
                        // Total price of all items
                        mapping(Item::getPrice, reducing(BigDecimal.ZERO, BigDecimal::add)),

                        // Total price of grocery items
                        filtering(item -> ItemCategory.GROCERY.equals(item.getCategory()),
                                mapping(Item::getPrice, reducing(BigDecimal.ZERO, BigDecimal::add))
                        ),

                        AbstractMap.SimpleEntry::new
                )
        );

        double discountPercentage = switch (request.getUserType()) {
            case EMPLOYEE -> 0.30;
            case AFFILIATE -> 0.10;
            case CUSTOMER -> request.getCustomerTenure() > 2 ? 0.05 : 0;
        };

        BigDecimal percentageDiscountAmount = prices.getKey().subtract(prices.getValue()).multiply(BigDecimal.valueOf(discountPercentage));
        // assuming flat discount rate rule applies to all currencies and not only to USD
        BigDecimal flatDiscount = prices.getKey().divide(BigDecimal.valueOf(100), 0, RoundingMode.DOWN).multiply(BigDecimal.valueOf(5));

        return percentageDiscountAmount.add(flatDiscount);
    }
}
