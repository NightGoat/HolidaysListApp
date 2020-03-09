package ru.nightgoat.holidays.data.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.nightgoat.holidays.data.entity.HolidayData;
import ru.nightgoat.holidays.domain.model.Holiday;

public interface API {

    @GET("v2/publicholidays/{year}/{country}")
    Single<List<HolidayData>> loadHolidays(@Path("year") int year, @Path("country") String country);
}
