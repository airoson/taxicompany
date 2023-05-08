package com.javatemplates.taxicompany.models.carmodel;

public enum Gearbox {
    AUTO("автомат"),
    MANUAL("механика");

    private String type;
    Gearbox(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Gearbox fromString(String gearbox){
        if(gearbox.trim().equalsIgnoreCase("автомат"))
            return Gearbox.AUTO;
        if(gearbox.trim().equalsIgnoreCase("механика"))
            return Gearbox.MANUAL;
        return null;
    }
}
