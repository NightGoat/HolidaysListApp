package ru.nightgoat.holidays.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.nightgoat.holidays.domain.usecase.HolidayInteractor;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private HolidayInteractor interactor;

    MainViewModelFactory(HolidayInteractor interactor) {
        this.interactor = interactor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MainViewModel.class) {
            return (T) new MainViewModel(interactor);
        }
        return super.create(modelClass);
    }
}
