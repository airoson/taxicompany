package com.javatemplates.taxicompany.models.carmodel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Rate {
    ECONOMY("эконом"),
    COMFORT("комфорт"),
    COMFORT_PLUS("комфорт+"),
    BUSINESS("бизнес"),
    PREMIUM("премиум");
    private String rate;

    public String getRate() {
        return rate;
    }

    Rate(String rate) {
        this.rate = rate;
    }

    public static Rate fromString(String rate){
        if(rate.trim().equalsIgnoreCase("эконом"))
            return Rate.ECONOMY;
        if(rate.trim().equalsIgnoreCase("комфорт"))
            return Rate.COMFORT;
        if(rate.trim().equalsIgnoreCase("комфорт+"))
            return Rate.COMFORT_PLUS;
        if(rate.trim().equalsIgnoreCase("бизнес"))
            return Rate.BUSINESS;
        if(rate.trim().equalsIgnoreCase("премиум"))
            return Rate.PREMIUM;
        log.error("Wrong string to convert to Rate: " + rate);
        return null;
    }
}
