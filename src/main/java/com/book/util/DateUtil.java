package com.bookmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
    public static String dateToString(Date date){
        return simpleDateFormat.format(date);
    }

    public static Date stringToDate(String dateString){
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
