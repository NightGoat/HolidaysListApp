package ru.nightgoat.holidays.domain.model;

public class Holiday implements Comparable<Holiday>{
    public String date;
    public String localName;

    public Holiday(String date, String localName){
        this.date = date;
        this.localName = localName;
    }

    @Override
    public int compareTo(Holiday o) {
        return date.compareTo(o.date);
    }
}
