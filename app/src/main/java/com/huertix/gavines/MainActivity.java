package com.huertix.gavines;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = "Gavines";

    private String baseUrl = " https://dweet.io/get/dweets/for/";
    private Button recibidorBtn;
    private Button trasteroBtn;
    private Button trescantosPatioBtn;
    private Button trescantosBuhardillaBtn;
    private TextView recibidorTxt;
    private TextView trasteroTxt;
    private TextView trecantosPatioTxt;
    private TextView trecantosBuhardillaTxt;
    private TextView recibidorTxt1;
    private TextView trasteroTxt1;
    private TextView trecantosPatioTxt1;
    private TextView trecantosBuhardillaTxt1;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recibidorBtn = (Button) findViewById(R.id.recibidor_btn);
        trasteroBtn = (Button) findViewById(R.id.trastero_btn);
        trescantosPatioBtn = (Button) findViewById(R.id.trescantos_patio_btn);
        trescantosBuhardillaBtn = (Button) findViewById(R.id.trescantos_buhardilla_btn);
        recibidorTxt = (TextView) findViewById(R.id.recibidor_text_view);
        trasteroTxt = (TextView) findViewById(R.id.trastero_text_view);
        trecantosPatioTxt = (TextView) findViewById(R.id.trescantos_patio_text_view);
        trecantosBuhardillaTxt = (TextView) findViewById(R.id.trescantos_buhardilla_text_view);
        recibidorTxt1 = (TextView) findViewById(R.id.recibidor_text_view_1);
        trasteroTxt1 = (TextView) findViewById(R.id.trastero_text_view_1);
        trecantosPatioTxt1 = (TextView) findViewById(R.id.trescantos_patio_text_view_1);
        trecantosBuhardillaTxt1 = (TextView) findViewById(R.id.trescantos_buhardilla_text_view_1);


        recibidorBtn.setOnClickListener(this);
        trasteroBtn.setOnClickListener(this);
        trescantosPatioBtn.setOnClickListener(this);
        trescantosBuhardillaBtn.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this);

        recibidorBtn.callOnClick();
        trasteroBtn.callOnClick();
        trescantosPatioBtn.callOnClick();
        trescantosBuhardillaBtn.callOnClick();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.recibidor_btn:
                Log.d(TAG, "requested recibidor");
                updateValues("gavines-recibidor");
                recibidorTxt.setText("Loading...");
                recibidorTxt1.setText("");

                break;
            case R.id.trastero_btn:
                Log.d(TAG, "requested trastero");
                updateValues("gavines-trastero");
                trasteroTxt.setText("Loading...");
                trasteroTxt.setText("");

                break;
            case R.id.trescantos_patio_btn:
                Log.d(TAG, "requested trescantos patio");
                updateValues("trescantos-patio");
                trecantosPatioTxt.setText("Loading...");
                trecantosPatioTxt.setText("");

                break;

            case R.id.trescantos_buhardilla_btn:
                Log.d(TAG, "requested trescantos buhardilla");
                updateValues("trescantos-buhardilla");
                trecantosBuhardillaTxt.setText("Loading...");
                trecantosBuhardillaTxt.setText("");


                break;
        }
    }

    private void updateValues(final String location) {
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
                                JSONObject values = response.getJSONArray("with").getJSONObject(0);
                                JSONObject values1 = response.getJSONArray("with").getJSONObject(4);
                                String date = values.getString("created").substring(0,19).replace("T"," ");
                                String date1 = values1.getString("created").substring(0,19).replace("T"," ");
                                JSONObject content = values.getJSONObject("content");
                                JSONObject content1 = values.getJSONObject("content");
                                Double tmp = content.getDouble("temperatura");
                                Double tmp1 = content1.getDouble("temperatura");
                                Double humedad = content.getDouble("humedad");
                                Double humedad1 = content1.getDouble("humedad");

                                if (location.equals("gavines-recibidor")) {
                                    recibidorTxt.setText( date + " TMP: " + Double.toString(tmp) + " HUM: " + Double.toString(humedad) + "%");
                                    recibidorTxt1.setText( date1 + " TMP: " + Double.toString(tmp1) + " HUM: " + Double.toString(humedad1) + "%");
                                }

                                if (location.equals("gavines-trastero")) {
                                    trasteroTxt.setText( date + " TMP: " + Double.toString(tmp) + " HUM: " + Double.toString(humedad) + "%");
                                    trasteroTxt1.setText( date1 + " TMP: " + Double.toString(tmp1) + " HUM: " + Double.toString(humedad1) + "%");
                                }

                                if (location.equals("trescantos-patio")) {
                                    trecantosPatioTxt.setText( date + " TMP: " + Double.toString(tmp) + " HUM: " + Double.toString(humedad) + "%");
                                    trecantosPatioTxt1.setText( date1 + " TMP: " + Double.toString(tmp1) + " HUM: " + Double.toString(humedad1) + "%");

                                }

                                if (location.equals("trescantos-buhardilla")) {
                                    trecantosBuhardillaTxt.setText( date + " TMP: " + Double.toString(tmp) + " HUM: " + Double.toString(humedad) + "%");
                                    trecantosBuhardillaTxt1.setText( date1 + " TMP: " + Double.toString(tmp1) + " HUM: " + Double.toString(humedad1) + "%");

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
