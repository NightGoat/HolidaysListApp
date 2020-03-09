package ru.nightgoat.holidays.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.nightgoat.holidays.R;
import ru.nightgoat.holidays.domain.model.Holiday;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.ViewHolder> {

    private ArrayList<Holiday> holidays= new ArrayList<>();

    void setList(List<Holiday> data) {
        holidays.clear();
        holidays.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(holidays.get(position));
    }

    @Override
    public int getItemCount() {
        return holidays.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView holidayDate;
        private TextView holidayName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            holidayDate = itemView.findViewById(R.id.holidayDate);
            holidayName = itemView.findViewById(R.id.holidayName);
        }

        void bind(Holiday item){
            holidayDate.setText(item.date);
            holidayName.setText(item.localName);
        }
    }
}
