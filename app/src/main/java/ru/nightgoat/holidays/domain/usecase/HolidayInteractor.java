package ru.nightgoat.holidays.domain.usecase;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import ru.nightgoat.holidays.Utils;
import ru.nightgoat.holidays.domain.model.Holiday;
import ru.nightgoat.holidays.domain.repository.HolidayRepository;

public class HolidayInteractor {

    private HolidayRepository repository;

    public HolidayInteractor(HolidayRepository repository){
        this.repository = repository;
    }

    public Single<List<Holiday>> getHolidays() {
        return repository.getHolidays()
                .map(holidays -> {
                    Collections.sort(holidays);
                    for (Holiday h: holidays) {
                        h.date = Utils.getDateInNormalFormat(h.date);
                    }
                    return holidays;
                });
    }
}
