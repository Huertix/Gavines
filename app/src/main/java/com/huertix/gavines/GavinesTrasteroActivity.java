package com.huertix.gavines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.huertix.gavines.LocationsEnum;
import com.huertix.gavines.R;
import com.huertix.gavines.helpers.DatabaseHelper;
import com.huertix.gavines.helpers.EventAdapter;
import com.huertix.gavines.models.Event;

import java.util.ArrayList;
import java.util.List;

public class GavinesTrasteroActivity extends Activity {

    private DatabaseHelper databaseHelper;
    private List<Event> events = new ArrayList<>();
    private EventAdapter adapterTrasteroRecibidor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);

        databaseHelper = new DatabaseHelper(this);

        events = databaseHelper.getAllEventsByLocation(LocationsEnum.GAVINES_TRASTERO.location());

        adapterTrasteroRecibidor = new EventAdapter(this,R.layout.location_list_view, events);

        ListView listEvents = (ListView) findViewById(R.id.list_view);

        listEvents.setAdapter(adapterTrasteroRecibidor);

        Intent intent = getIntent();
        String locationName = intent.getStringExtra("location_name");

        TextView locationNameTxt = (TextView) findViewById(R.id.location_name_txt);
        locationNameTxt.setText(locationName);

    }


}
