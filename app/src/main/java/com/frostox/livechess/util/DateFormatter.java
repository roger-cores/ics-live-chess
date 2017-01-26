package com.frostox.livechess.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by roger on 10/24/2016.
 */
public class DateFormatter {

    public static String formatDateToFirebase(Date date){
        return new SimpleDateFormat("dd/mm/yyyy").format(date);
    }

    public static Date parseDateFromFirebase(String date){
        try {
            return new SimpleDateFormat("dd/mm/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String formateDateRange(Date start, Date end) {


        SimpleDateFormat date, month, dateAndMonth, year;

        date = new SimpleDateFormat("dd");
        month = new SimpleDateFormat("MMM");
        dateAndMonth = new SimpleDateFormat("dd MMM");
        year = new SimpleDateFormat("yy");


        Calendar firstDate, secondDate, now;
        firstDate = Calendar.getInstance();
        secondDate = Calendar.getInstance();
        now = Calendar.getInstance();

        firstDate.setTime(start);
        secondDate.setTime(end);

        if ((firstDate.get(Calendar.MONTH) == secondDate.get(Calendar.MONTH)) && (firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR))) {
            if(now.get(Calendar.YEAR) == firstDate.get(Calendar.YEAR)){
                return date.format(firstDate.getTime()) + " - " + date.format(secondDate.getTime()) + " " + month.format(secondDate.getTime());
            } else {
                return date.format(firstDate.getTime()) + " - " + date.format(secondDate.getTime()) + " " + month.format(secondDate.getTime()) + " '" + year.format(firstDate.getTime());
            }

        } else {
            if(now.get(Calendar.YEAR) == firstDate.get(Calendar.YEAR) && now.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR)) {
                return dateAndMonth.format(firstDate.getTime()) + " - " + dateAndMonth.format(secondDate.getTime());
            } else {
                return dateAndMonth.format(firstDate.getTime()) + " '" + year.format(firstDate.getTime()) + " - " + dateAndMonth.format(secondDate.getTime()) + " '" + year.format(secondDate.getTime());
            }
        }
    }
}
