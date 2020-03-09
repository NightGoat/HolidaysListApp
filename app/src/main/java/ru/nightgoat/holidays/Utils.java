package ru.nightgoat.holidays;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utils {

    public static String getDateInNormalFormat(String sqlDate){
        DateTimeFormatter normalFormat = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTimeFormatter sqlFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime sqlDateTime = sqlFormat.parseDateTime(sqlDate);
        return normalFormat.print(sqlDateTime.getMillis());
    }
}
