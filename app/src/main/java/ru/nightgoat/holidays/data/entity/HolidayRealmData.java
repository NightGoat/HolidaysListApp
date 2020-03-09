package ru.nightgoat.holidays.data.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import ru.nightgoat.holidays.domain.model.Holiday;

@RealmClass
public class HolidayRealmData implements RealmModel {

    private String date;

    @PrimaryKey
    private String holiday;

    HolidayRealmData(String date, String holiday){
        this.date = date;
        this.holiday = holiday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public HolidayRealmData(){

    }

    public static Holiday convertToEntity(HolidayRealmData item){
        return new Holiday(item.date, item.holiday);
    }

    public static HolidayRealmData convertToRealmData(HolidayData item){
        return new HolidayRealmData(item.date, item.localName);
    }
}
