package ru.nightgoat.holidays.domain.repository;

import java.util.List;

import io.reactivex.Single;
import ru.nightgoat.holidays.domain.model.Holiday;

public interface HolidayRepository {

    Single<List<Holiday>> getHolidays();
}
