package ru.nightgoat.holidays.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import ru.nightgoat.holidays.R;
import ru.nightgoat.holidays.data.db.DbProvider;
import ru.nightgoat.holidays.data.db.RealmDbImpl;
import ru.nightgoat.holidays.data.entity.HolidayRealmData;
import ru.nightgoat.holidays.data.network.API;
import ru.nightgoat.holidays.data.network.RetrofitInit;
import ru.nightgoat.holidays.data.repository.HolidayRepositoryImpl;
import ru.nightgoat.holidays.domain.model.Holiday;
import ru.nightgoat.holidays.domain.repository.HolidayRepository;
import ru.nightgoat.holidays.domain.usecase.HolidayInteractor;

public class MainActivity extends AppCompatActivity {

    private HolidayAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        initRecycleView();

        viewModel.holidaysLD.observe(this, data -> adapter.setList(data));
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
