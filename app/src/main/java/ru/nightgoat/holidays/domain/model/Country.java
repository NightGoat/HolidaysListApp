package ru.nightgoat.holidays.domain.model;

public class Country implements Comparable<Country> {

    public String key;
    public String name;

    public Country(String key, String name){
        this.key = key;
        this.name = name;
    }

    @Override
    public int compareTo(Country o) {
        return name.compareTo(o.name);
    }
}
