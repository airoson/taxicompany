package com.javatemplates.taxicompany.models.carmodel;

public class StringDay {
    public static String getDesc(int price){
        if((price / 10) % 10 == 1)
            return "дней";
        int lastDigit = price % 10;
        if(lastDigit % 10 == 4 ||
                lastDigit % 10 == 3 ||
                lastDigit % 10 == 2)
            return "дня";
        if(lastDigit % 10 == 1)
            return "день";
        return "дней";
    }
}
