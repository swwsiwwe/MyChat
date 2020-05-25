package com.company.utils;

import java.util.Calendar;

public class Time {
    public static String getTime(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+" "+(c.get(Calendar.HOUR_OF_DAY))+":"+c.get(Calendar.MINUTE);
    }
}
