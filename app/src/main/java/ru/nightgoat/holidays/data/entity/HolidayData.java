package ru.nightgoat.holidays.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.nightgoat.holidays.domain.model.Holiday;

public class HolidayData {

    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("localName")
    @Expose
    String localName;

    public static Holiday convertToEntity(HolidayData item){
        return new Holiday(item.date, item.localName);
    }
}
