package com.example.fayed_mid2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String logName = "Fayed";
    TextView tempTv;
    TextView humidityTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(logName,"MAIN Activity onCreate method");


        Spinner cityChosen = (Spinner) findViewById(R.id.cities);

        Button pickDataBttn = (Button) findViewById(R.id.pickDataBttn);
        Button goToSecondABttn = (Button) findViewById(R.id.goToSecondABttn);
        TextView dataTv = (TextView) findViewById(R.id.dataTv);
        tempTv = (TextView) findViewById(R.id.tempTv);
        humidityTv = (TextView) findViewById(R.id.humidityTv);

        cityChosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = parent.getItemAtPosition(position).toString();
                String key = "ceae73f848981fda58066979faec2f2e";
                // we"ll make HTTP request to this URL to retrieve weather conditions
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid="+key+"&units=metric";
                weather(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar c = Calendar.getInstance();
        DateFormat txtDate = DateFormat.getDateInstance();

        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dataTv.setText("Date picked: "+ txtDate.format(c.getTime()));
            }
        };

        pickDataBttn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        goToSecondABttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MySQLite.class));
            }
        });

    }

    public void weather (String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(logName,"Response received");
                Log.d(logName,response.toString());
                try {


                    // for nested JSON
                    JSONObject JSONClouds = response.getJSONObject("main");
                    double temp = JSONClouds.getDouble("temp");
                    tempTv.setText("Temp: "+String.valueOf(temp));
                    Log.d(logName,"Temp: "+String.valueOf(temp));

                    int humidity = JSONClouds.getInt("humidity");
                    humidityTv.setText("Humidity: "+String.valueOf(humidity));
                    Log.d(logName,"Humidity: "+String.valueOf(humidity));

                } catch (JSONException e){
                    e.printStackTrace();
                    Log.d(logName+"-error",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }
}