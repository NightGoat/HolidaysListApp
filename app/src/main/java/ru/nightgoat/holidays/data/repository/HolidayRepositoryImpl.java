package ru.nightgoat.holidays.data.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.nightgoat.holidays.data.db.DbProvider;
import ru.nightgoat.holidays.data.entity.CountryData;
import ru.nightgoat.holidays.data.entity.HolidayData;
import ru.nightgoat.holidays.data.entity.HolidayRealmData;
import ru.nightgoat.holidays.data.network.API;
import ru.nightgoat.holidays.domain.model.Country;
import ru.nightgoat.holidays.domain.model.Holiday;
import ru.nightgoat.holidays.domain.repository.HolidayRepository;

public class HolidayRepositoryImpl implements HolidayRepository {

    private API api;
    private DbProvider<HolidayRealmData, List<Holiday>> dbRealm;

    public HolidayRepositoryImpl(API api, DbProvider<HolidayRealmData, List<Holiday>> dbRealm) {
        this.api = api;
        this.dbRealm = dbRealm;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Single<List<Holiday>> getHolidays(String country) {
        return api.loadHolidays(Calendar.getInstance().get(Calendar.YEAR),country)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(consumer -> {
                    for (HolidayData item : consumer) {
                        dbRealm.insert(HolidayRealmData.convertToRealmData(item));
                    }
                })
                .doOnError(throwable -> Log.e("HolidayRepositoryImpl", "getHolidays: ERROR" + throwable.getMessage()))
                .map(response ->
                        response.stream().map(HolidayData::convertToEntity)
                                        .collect(Collectors.toList()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Single<List<Country>> getCountries() {
        return api.loadCountries()
                .subscribeOn(Schedulers.io())
                .map(countryData -> countryData.stream().map(CountryData::convertToEntity)
                .collect(Collectors.toList()));
    }


}
