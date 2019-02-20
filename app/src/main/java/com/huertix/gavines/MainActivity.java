package com.huertix.gavines;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.huertix.gavines.helpers.DatabaseHelper;
import com.huertix.gavines.models.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener{


    public static final String TAG = "Gavines";

    private String baseUrl = " https://dweet.io/get/dweets/for/";


    private RequestQueue requestQueue;

    private DatabaseHelper databaseHelper;


    private Button gavinesRecibidorBtn;
    private Button gavinesTrasteroBtn;
    private Button trescPatioBtn;
    private Button trescBuharBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        databaseHelper = new DatabaseHelper(this);

        requestQueue = Volley.newRequestQueue(this);

        this.updateValues();

        gavinesRecibidorBtn = (Button) findViewById(R.id.gavines_recibidor_btn);
        gavinesTrasteroBtn = (Button) findViewById(R.id.gavines_trastero_btn);
        trescPatioBtn = (Button) findViewById(R.id.trescantos_patio_btn);
        trescBuharBtn = (Button) findViewById(R.id.trescantos_buhardilla_btn);

        gavinesRecibidorBtn.setOnClickListener(this);
        gavinesTrasteroBtn.setOnClickListener(this);
        trescPatioBtn.setOnClickListener(this);
        trescBuharBtn.setOnClickListener(this);

        gavinesRecibidorBtn.setText(LocationsEnum.GAVINES_RECIBIDOR.location());
        gavinesTrasteroBtn.setText(LocationsEnum.GAVINES_TRASTERO.location());
        trescPatioBtn.setText(LocationsEnum.TRESCANTOS_PATIO.location());
        trescBuharBtn.setText(LocationsEnum.TRESCANTOS_BUHARDILLA.location());

        updateTextViews();

    }


    public void updateTextViews() {
        TextView gavinesRecibidorTxT = (TextView) findViewById(R.id.gr_txt);

        TextView gavinesTrasteroTxt = (TextView) findViewById(R.id.gt_txt);

        TextView trescPatioTxt = (TextView) findViewById(R.id.tp_txt);

        TextView trescBuahTxt = (TextView) findViewById(R.id.tb_txt);



        Event gavinesRecibidorEvent = databaseHelper.getLastEventByLocation(LocationsEnum.GAVINES_RECIBIDOR.location());
        Event gavinesTrasteroEvent = databaseHelper.getLastEventByLocation(LocationsEnum.GAVINES_TRASTERO.location());
        Event trescPatioEvent = databaseHelper.getLastEventByLocation(LocationsEnum.TRESCANTOS_PATIO.location());
        Event trescBuahEvent = databaseHelper.getLastEventByLocation(LocationsEnum.TRESCANTOS_BUHARDILLA.location());

        gavinesRecibidorTxT.setText("Date: " + gavinesRecibidorEvent.getDate() + "\n"
                + "Tmp: " + gavinesRecibidorEvent.getTemperature() + "^\n"
                + "Humd: " + gavinesRecibidorEvent.getHumedity() + "%");


        gavinesTrasteroTxt.setText("Date: " + gavinesTrasteroEvent.getDate() + "\n"
                + "Tmp: " + gavinesTrasteroEvent.getTemperature() + "^\n"
                + "Humd: " + gavinesTrasteroEvent.getHumedity() + "%");


        trescPatioTxt.setText("Date: " + trescPatioEvent.getDate() + "\n"
                + "Tmp: " + trescPatioEvent.getTemperature() + "^\n"
                + "Humd: " + trescPatioEvent.getHumedity() + "%");


        trescBuahTxt.setText("Date: " + trescBuahEvent.getDate() + "\n"
                + "Tmp: " + trescBuahEvent.getTemperature() + "^\n"
                + "Humd: " + trescBuahEvent.getHumedity() + "%");


    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        String locationName = null;

        switch (v.getId())
        {
            case R.id.gavines_recibidor_btn:
                intent = new Intent(this, GavinesRecibidorActivity.class);
                locationName = LocationsEnum.GAVINES_RECIBIDOR.location();
                intent.putExtra("location_name", locationName);
                startActivity(intent);
                break;
            case R.id.gavines_trastero_btn:
                intent = new Intent(this, GavinesTrasteroActivity.class);
                locationName = LocationsEnum.GAVINES_TRASTERO.location();
                intent.putExtra("location_name", locationName);
                startActivity(intent);
                break;
            case R.id.trescantos_patio_btn:
                intent = new Intent(this, TresCantosPatioActivity.class);
                locationName = LocationsEnum.TRESCANTOS_PATIO.location();
                intent.putExtra("location_name", locationName);
                startActivity(intent);
                break;
            case R.id.trescantos_buhardilla_btn:
                intent = new Intent(this, TresCantosBuhardillaActivity.class);
                locationName = LocationsEnum.TRESCANTOS_BUHARDILLA.location();
                intent.putExtra("location_name", locationName);
                startActivity(intent);
                break;
        }
    }

    private void updateValues() {

        for (LocationsEnum location : LocationsEnum.values()) {
            this.requestEvents(location.location());
        }
    }

    private void requestEvents(final String location) {
        String url = baseUrl + location;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            Log.d(TAG, response.toString());
                            try {

                                int totalRecords = response.getJSONArray("with").length();

                                for (int index=0; index < totalRecords; index++) {

                                    JSONObject values = response.getJSONArray("with").getJSONObject(index);
                                    String date = values.getString("created").substring(0,19).replace("T"," ");
                                    JSONObject content = values.getJSONObject("content");
                                    Double tmp = content.getDouble("temperatura");
                                    Double humedad = content.getDouble("humedad");

                                    Event event = new Event(location, date, humedad, tmp);

                                    databaseHelper.createEvent(event);
                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Empty Response", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Error: " + error.getMessage());
                            // hide the progress dialog

                        }
                    }
        );


        requestQueue.add(jsonObjReq);

    }

}
