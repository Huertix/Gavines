package com.huertix.gavines.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huertix.gavines.R;
import com.huertix.gavines.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    Context context;
    List<Event> eventList = new ArrayList<>();
    int layoutResourceId;

    public EventAdapter(Context context, int layoutResourceId, List<Event> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.eventList = objects;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.location_list_view, parent, false);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView temperature = (TextView) rowView.findViewById(R.id.temperature);
        TextView humidity = (TextView) rowView.findViewById(R.id.humidity);
        Event currentEvent = eventList.get(position);
        date.setText(currentEvent.getDate());
        temperature.setText(String.valueOf(currentEvent.getTemperature()));
        humidity.setText(String.valueOf(currentEvent.getHumedity()));

        return rowView;

    }

}
