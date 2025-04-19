package com.billing_service.dto;

import java.math.BigDecimal;

public class CalculateResponse {
    private BigDecimal totalBill;
    private BigDecimal totalDiscount;
    private BigDecimal conversionRate;
    private BigDecimal totalPayableBillInTargetCurrency;
    private String targetCurrency;

    public BigDecimal getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(BigDecimal totalBill) {
        this.totalBill = totalBill;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    public BigDecimal getTotalPayableBillInTargetCurrency() {
        return totalPayableBillInTargetCurrency;
    }

    public void setTotalPayableBillInTargetCurrency(BigDecimal totalPayableBillInTargetCurrency) {
        this.totalPayableBillInTargetCurrency = totalPayableBillInTargetCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public static CalculateResponse from(BigDecimal totalBill, BigDecimal totalDiscount, BigDecimal conversionRate, BigDecimal totalPayableBillInTargetCurrency, String targetCurrency) {
        CalculateResponse result = new CalculateResponse();
        result.totalBill = totalBill;
        result.totalDiscount = totalDiscount;
        result.conversionRate = conversionRate;
        result.totalPayableBillInTargetCurrency = totalPayableBillInTargetCurrency;
        result.targetCurrency = targetCurrency;
        return result;
    }
}
