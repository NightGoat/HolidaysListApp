package ru.nightgoat.holidays.data.db;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.nightgoat.holidays.data.entity.HolidayRealmData;
import ru.nightgoat.holidays.domain.model.Holiday;

public class RealmDbImpl implements DbProvider<HolidayRealmData, List<Holiday>> {
    @Override
    public void insert(HolidayRealmData data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransactionAsync(realm1 -> realm1.insertOrUpdate(data));
//            realm.beginTransaction();
//            realm.insertOrUpdate(data);
//            realm.commitTransaction();
        }
    }

    @Override
    public void update(HolidayRealmData data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(trans -> realm.copyToRealmOrUpdate(data));
        }
    }

    @Override
    public void delete(HolidayRealmData data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(
                    trans ->
                            realm.where(HolidayRealmData.class)
                                    .contains("..", data.getHoliday())
                                    .findAll()
                                    .deleteAllFromRealm());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Holiday> select() {

        try (Realm realm = Realm.getDefaultInstance()) {
            final RealmResults<HolidayRealmData> results =
                    realm.where(HolidayRealmData.class)
                            .findAll();
            List<HolidayRealmData> items = realm.copyFromRealm(results);
            return items.stream()
                    .map(HolidayRealmData::convertToEntity)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            return null;
        }
    }
}
