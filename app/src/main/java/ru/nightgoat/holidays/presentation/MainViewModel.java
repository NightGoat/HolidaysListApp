package ru.nightgoat.holidays.presentation;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import ru.nightgoat.holidays.domain.model.Country;
import ru.nightgoat.holidays.domain.model.Holiday;
import ru.nightgoat.holidays.domain.usecase.HolidayInteractor;

public class MainViewModel extends androidx.lifecycle.ViewModel implements LifecycleObserver {

    private HolidayInteractor interactor;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    MutableLiveData<List<Holiday>> holidaysLD = new MutableLiveData<>();
    MutableLiveData<String[]> countriesLD = new MutableLiveData<>();

    private List<Country> countries;

    MainViewModel(HolidayInteractor interactor) {
        this.interactor = interactor;
        countries = new ArrayList<>();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        compositeDisposable.add(
                interactor.getCountries()
                        .map(countries -> {
                            this.countries = countries;
                            String[] array = new String[countries.size()];
                            for (int i = 0; i < countries.size(); i++) {
                                array[i] = countries.get(i).name;
                            }
                            return array;
                        })
                        .subscribe(array -> countriesLD.postValue(array),
                                throwable -> Log.e("MainViewModel", "OnStart: " + throwable.getMessage()))
        );
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    void getHolidays(String country){
        for (Country c: countries) {
            if (c.name.equalsIgnoreCase(country) || c.key.equalsIgnoreCase(country)) {
                compositeDisposable.add(
                        interactor.getHolidays(c.key)
                                .subscribe(result -> {
                                            holidaysLD.postValue(result);
                                            Log.d("MainViewModel", "onStart: result is empty: " + result.isEmpty());
                                        },
                                        throwable -> Log.e("ViewModel", "onStart: " + throwable.getMessage())
                                )
                );
            }
        }

    }
}
