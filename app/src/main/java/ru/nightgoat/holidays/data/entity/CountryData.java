package ru.nightgoat.holidays.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.nightgoat.holidays.domain.model.Country;

public class CountryData {

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("value")
    @Expose
    private String name;

    public static Country convertToEntity(CountryData item){
        return new Country(item.key, item.name);
    }
}
