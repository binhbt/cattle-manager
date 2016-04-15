package com.leo.cattle.presentation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leo on 4/3/2016.
 */
public class DatimeUtil {
    public static Date fromString(String datime, String format){
        SimpleDateFormat  formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(datime);
            System.out.println(date);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static String fromDate(Date date, String format){
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        return df.format(format, date).toString();
    }
}
