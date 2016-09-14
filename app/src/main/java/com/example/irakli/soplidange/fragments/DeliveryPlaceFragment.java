package com.example.irakli.soplidange.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.irakli.soplidange.R;

import static com.example.irakli.soplidange.R.id.organization_radio_group_id;


/**
 * Created by floyd on 9/8/2016.
 */
public class DeliveryPlaceFragment extends Fragment {

    private LinearLayout newAddressLayout;
    private RelativeLayout organizationLayout;
    private RelativeLayout deliveryRadioLayout;
    private RadioGroup deliveryRadioGroup;
    private RadioGroup radioGroup;
    private RadioGroup organizationRadioGroup;

    Spinner spinCity;
    Spinner spinDistrict;
    Spinner invoiceSpinCity;
    Spinner invoiceSpinDistrict;
    ScrollView scrollView;
    EditText invoiceGuestField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deliveryFragment = inflater.inflate(R.layout.delivery_fragment_layout, container, false);

        radioGroup = (RadioGroup) deliveryFragment.findViewById(R.id.radio_group_id);
        organizationRadioGroup = (RadioGroup) deliveryFragment.findViewById(R.id.organization_radio_group_id);
        organizationLayout = (RelativeLayout) deliveryFragment.findViewById(R.id.organization_fields_id);
        deliveryRadioLayout = (RelativeLayout) deliveryFragment.findViewById(R.id.delivery_organization_fields_id);
        deliveryRadioGroup = (RadioGroup) deliveryFragment.findViewById(R.id.delivery_radio_group_id);
        invoiceGuestField = (EditText) deliveryFragment.findViewById(R.id.invoice_guest_field_name);


        newAddressLayout = (LinearLayout) deliveryFragment.findViewById(R.id.new_address_fields_id);
        scrollView = (ScrollView) deliveryFragment.findViewById(R.id.delivery_scroll_view_id);


        spinCity = (Spinner) deliveryFragment.findViewById(R.id.spinCity);//fetch the spinner from layout file
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(deliveryFragment.getContext(),
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
        spinDistrict = (Spinner) deliveryFragment.findViewById(R.id.spinDistrict);//fetch the spinner from layout file
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(deliveryFragment.getContext(),
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

        invoiceSpinCity = (Spinner) deliveryFragment.findViewById(R.id.invoice_spinCity);//fetch the spinner from layout file
        ArrayAdapter<String> invoiceCityAdapter = new ArrayAdapter<String>(deliveryFragment.getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.city_array));//setting the country_array to spinner
        invoiceCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        invoiceSpinCity.setAdapter(invoiceCityAdapter);
//if you want to set any action you can do in this listener
        invoiceSpinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        invoiceSpinDistrict = (Spinner) deliveryFragment.findViewById(R.id.invoice_spinDistrict);//fetch the spinner from layout file
        ArrayAdapter<String> invoiceDistrictAdapter = new ArrayAdapter<String>(deliveryFragment.getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.district_array));//setting the country_array to spinner
        invoiceDistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        invoiceSpinDistrict.setAdapter(invoiceDistrictAdapter);
//if you want to set any action you can do in this listener
        invoiceSpinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        radioBtnChecked();

        return deliveryFragment;
    }

    private void radioBtnChecked() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_btn_yes:
                        newAddressLayout.setVisibility(View.GONE);
                        break;
                    case R.id.radio_btn_no:
                        newAddressLayout.setVisibility(View.VISIBLE);
//                        scrollView.addView(newAddressLayout);

                        invoiceGuestField.requestFocus();
                        break;

                }
            }
        });

        organizationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yes_btn_id:
                        organizationLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.no_btn_id:
                        organizationLayout.setVisibility(View.GONE);
                }
            }
        });

        deliveryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yes_btn_delivery_id:
                        deliveryRadioLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.no_btn_delivery_id:
                        deliveryRadioLayout.setVisibility(View.GONE);
                }
            }
        });

    }

}
