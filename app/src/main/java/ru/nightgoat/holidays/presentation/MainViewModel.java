package ru.nightgoat.holidays.presentation;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import ru.nightgoat.holidays.domain.model.Holiday;
import ru.nightgoat.holidays.domain.usecase.HolidayInteractor;

public class MainViewModel extends androidx.lifecycle.ViewModel implements LifecycleObserver {

    private HolidayInteractor interactor;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    MutableLiveData<List<Holiday>> holidaysLD = new MutableLiveData<>();

    MainViewModel(HolidayInteractor interactor) {
        this.interactor = interactor;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        compositeDisposable.add(
                interactor.getHolidays()
                        .subscribe(result -> {
                                    holidaysLD.postValue(result);
                                    Log.d("MainViewModel", "onStart: result is empty: " + result.isEmpty());
                                },
                                throwable -> Log.d("ViewModel", "onStart: " + throwable.getMessage())
                        )
        );
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
