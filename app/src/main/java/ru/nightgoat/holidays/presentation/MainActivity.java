package ru.nightgoat.holidays.presentation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.nightgoat.holidays.R;
import ru.nightgoat.holidays.data.db.DbProvider;
import ru.nightgoat.holidays.data.db.RealmDbImpl;
import ru.nightgoat.holidays.data.entity.HolidayRealmData;
import ru.nightgoat.holidays.data.network.API;
import ru.nightgoat.holidays.data.network.RetrofitInit;
import ru.nightgoat.holidays.data.repository.HolidayRepositoryImpl;
import ru.nightgoat.holidays.domain.model.Country;
import ru.nightgoat.holidays.domain.model.Holiday;
import ru.nightgoat.holidays.domain.repository.HolidayRepository;
import ru.nightgoat.holidays.domain.usecase.HolidayInteractor;

public class MainActivity extends AppCompatActivity {

    private HolidayAdapter adapter;
    private MainViewModel viewModel;
    private AutoCompleteTextView countryTV;
    private Button getBtn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewModel();
        initRecycleView();

        viewModel.holidaysLD.observe(this, data -> adapter.setList(data));
        viewModel.countriesLD.observe(this, data -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, data);
            countryTV.setAdapter(adapter);
        });
    }

    private void initViews() {
        countryTV = findViewById(R.id.countryTV);
        getBtn = findViewById(R.id.getBtn);
        getBtn.setOnClickListener(v -> {
            String country = countryTV.getText().toString();
            if (!country.isEmpty()) viewModel.getHolidays(country);
        });
    }

    private void initRecycleView() {
        adapter = new HolidayAdapter();
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void initViewModel() {
        API api = RetrofitInit.newApiInstance();
        DbProvider<HolidayRealmData, List<Holiday>> dbRealm = new RealmDbImpl();
        HolidayRepository repository = new HolidayRepositoryImpl(api, dbRealm);
        HolidayInteractor interactor = new HolidayInteractor(repository);

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(interactor)).get(MainViewModel.class);
        getLifecycle().addObserver(viewModel);
    }
}
