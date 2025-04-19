package com.billing_service.dto;

import com.billing_service.enums.UserType;

import java.util.List;

public class CalculateRequest {
    private List<Item> items;
    private UserType userType;
    private int customerTenure;
    private String originalCurrency;
    private String targetCurrency;

    public List<Item> getItems() {
        return items;
    }

    public UserType getUserType() {
        return userType;
    }

    public int getCustomerTenure() {
        return customerTenure;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setCustomerTenure(int customerTenure) {
        this.customerTenure = customerTenure;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
