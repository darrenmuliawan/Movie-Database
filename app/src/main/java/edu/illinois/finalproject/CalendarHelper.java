package edu.illinois.finalproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by darrenalexander on 12/10/17.
 */

public class CalendarHelper {
    /**
     * Set the date to Calendar object.
     * https://stackoverflow.com/questions/11791513/converting-string-to-calendar-
     * what-is-the-easiest-way
     * @param date formatted date (yyyy-mm-dd)
     * @return calendar
     * @throws ParseException
     */
    public static Calendar insertDateToCalendar(String date) throws ParseException {
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = curFormater.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObj);
        return calendar;
    }
}
