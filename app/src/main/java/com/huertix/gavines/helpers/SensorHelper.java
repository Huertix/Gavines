package com.huertix.gavines.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.huertix.gavines.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SensorHelper {

    private final static String BASE_URL = "https://dweet.io/get/dweets/for/";
    private static final String TAG = "Gavines";


    private List<String> places = new ArrayList<>();

    private Context context;

    private DatabaseHelper databaseHelper;


    public SensorHelper(Context context) {
        this.context = context;

        databaseHelper = new DatabaseHelper(context);


        places.add("gavines-recibidor");
        places.add("gavines-trastero");
        places.add("trescantos-patio");
        places.add("trescantos-buhardilla");
    }


    private void fetchData(RequestQueue requestQueue, final String location) {
        String url = BASE_URL + location;

        Log.d(TAG, url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check the length of our response (to see if the user has any repos)
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {
                            Log.d(TAG, response.toString());
                            try {

                                for (int index=0;  index < response.getJSONArray("with").length(); index++ ) {

                                    JSONObject values = response.getJSONArray("with").getJSONObject(index);
                                    String date = values.getString("created").substring(0,19).replace("T"," ");

                                    JSONObject content = values.getJSONObject("content");
                                    Double tmp = content.getDouble("temperatura");
                                    Double humedity = content.getDouble("humedad");

                                    Event event = new Event(location, date, humedity, tmp);

                                    databaseHelper.createEvent(event);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast toast = Toast.makeText(context, "Empty Response", Toast.LENGTH_LONG);
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

    public void requestEvents(RequestQueue requestQueue) {

        for (String place : this.places) {
            this.fetchData(requestQueue, place);
        }

    }

}
