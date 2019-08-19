package com.accenture.flowershop.be.util;

import java.math.BigDecimal;

public class Filter {
    private String findString;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;

    public Filter() {
    }

    public Filter(Filter filter) {
        this.findString = filter.getFindString();
        this.priceFrom = filter.getPriceFrom();
        this.priceTo = filter.getPriceTo();
    }

    public Filter(String findString, BigDecimal priceFrom, BigDecimal priceTo) {
        this.findString = findString;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
    }

    public String getFindString() {
        return findString;
    }

    public void setFindString(String findString) {
        this.findString = findString;
    }

    public BigDecimal getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(BigDecimal priceFrom) {
        this.priceFrom = priceFrom;
    }

    public BigDecimal getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(BigDecimal priceTo) {
        this.priceTo = priceTo;
    }
}
