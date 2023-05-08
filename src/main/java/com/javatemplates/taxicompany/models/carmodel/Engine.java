package com.javatemplates.taxicompany.models.carmodel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Engine {
    PETROL("Бензин"),
    DIESEL("Дизель"),
    GAS("Газ");

    private String type;
    Engine(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Engine fromString(String engine){
        if(engine.trim().equalsIgnoreCase("Бензин"))
            return Engine.PETROL;
        if(engine.trim().equalsIgnoreCase("Дизель"))
            return Engine.DIESEL;
        if(engine.trim().equalsIgnoreCase("Газ"))
            return Engine.GAS;
        log.error("Unknown engine type " + engine);
        return null;
    }
}
