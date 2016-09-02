package com.example.irakli.soplidange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Payment extends AppCompatActivity {
    Spinner spinCity;
    Spinner spinDistrict;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        spinCity= (Spinner) findViewById(R.id.spinCity);//fetch the spinner from layout file
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.city_array));//setting the country_array to spinner
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCity.setAdapter(cityAdapter);
//if you want to set any action you can do in this listener
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinDistrict= (Spinner) findViewById(R.id.spinDistrict);//fetch the spinner from layout file
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.district_array));//setting the country_array to spinner
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDistrict.setAdapter(districtAdapter);
//if you want to set any action you can do in this listener
        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}
