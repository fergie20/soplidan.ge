package com.example.irakli.soplidange.fragments;

import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
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
import com.example.irakli.soplidange.utils.CheckoutSingleton;

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


    EditText delivery_name;
    EditText delivery_last_name;
    EditText delivery_phone;
    EditText delivery_address;
    EditText delivery_email;
    EditText delivery_card_id;
    EditText delivery_organisation_name;
    EditText delivery_organisation_code;
    EditText invoice_name;
    EditText invoice_last_name;
    EditText invoice_phone;
    EditText invoice_address;
    EditText invoice_organisation_name;
    EditText invoice_organisation_code;
    boolean checkVisibility = false;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deliveryFragment = inflater.inflate(R.layout.delivery_fragment_layout, container, false);

        radioGroup = (RadioGroup) deliveryFragment.findViewById(R.id.radio_group_id);
        organizationRadioGroup = (RadioGroup) deliveryFragment.findViewById(R.id.organization_radio_group_id);
        organizationLayout = (RelativeLayout) deliveryFragment.findViewById(R.id.organization_fields_id);
        deliveryRadioLayout = (RelativeLayout) deliveryFragment.findViewById(R.id.delivery_organization_fields_id);
        deliveryRadioGroup = (RadioGroup) deliveryFragment.findViewById(R.id.delivery_radio_group_id);



        delivery_name = (EditText) deliveryFragment.findViewById(R.id.delivery_name);
        delivery_name.setText(CheckoutSingleton.getInstance().getValue("guest_name"));


        delivery_last_name = (EditText) deliveryFragment.findViewById(R.id.delivery_last_name);
        delivery_last_name.setText(CheckoutSingleton.getInstance().getValue("guest_last_name"));

        delivery_phone = (EditText) deliveryFragment.findViewById(R.id.delivery_phone);
        delivery_phone.setText(CheckoutSingleton.getInstance().getValue("guest_phone"));

        delivery_address = (EditText) deliveryFragment.findViewById(R.id.delivery_address);
        delivery_address.setText(CheckoutSingleton.getInstance().getValue("guest_address"));

        delivery_email = (EditText) deliveryFragment.findViewById(R.id.delivery_email);
        delivery_email.setText(CheckoutSingleton.getInstance().getValue("guest_email"));

        delivery_card_id = (EditText) deliveryFragment.findViewById(R.id.delivery_card_id);
        delivery_card_id.setText(CheckoutSingleton.getInstance().getValue("guest_card_id"));

        delivery_organisation_name = (EditText) deliveryFragment.findViewById(R.id.delivery_organisation_name);
        delivery_organisation_name.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_name"));

        delivery_organisation_code = (EditText) deliveryFragment.findViewById(R.id.delivery_organisation_code);
        delivery_organisation_code.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_code"));


        invoice_name = (EditText) deliveryFragment.findViewById(R.id.invoice_name);
        invoice_name.setText(CheckoutSingleton.getInstance().getValue("guest_name"));

        invoice_last_name = (EditText) deliveryFragment.findViewById(R.id.invoice_last_name);
        invoice_last_name.setText(CheckoutSingleton.getInstance().getValue("guest_last_name"));

        invoice_phone = (EditText) deliveryFragment.findViewById(R.id.invoice_phone);
        invoice_phone.setText(CheckoutSingleton.getInstance().getValue("guest_phone"));

        invoice_address = (EditText) deliveryFragment.findViewById(R.id.invoice_address);
        invoice_address.setText(CheckoutSingleton.getInstance().getValue("guest_address"));


        invoice_organisation_name = (EditText) deliveryFragment.findViewById(R.id.invoice_organisation_name);
        invoice_organisation_name.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_name"));

        invoice_organisation_code = (EditText) deliveryFragment.findViewById(R.id.invoice_organisation_code);
        invoice_organisation_code.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_code"));





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

    @Override
    public void onStop() {
        super.onStop();
        saveDeliveriInfo();
    }

    public void saveDeliveriInfo() {


        CheckoutSingleton.getInstance().addNewValue("delivery_name", delivery_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("delivery_last_name", delivery_last_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("delivery_email", delivery_email.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("delivery_address", delivery_address.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("delivery_phone", delivery_phone.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("delivery_card_id", delivery_card_id.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("delivery_organisation_name", delivery_organisation_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("delivery_organisation_code", delivery_organisation_code.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("spinCity", spinCity.getSelectedItem().toString());
        CheckoutSingleton.getInstance().addNewValue("spinDistrict", spinDistrict.getSelectedItem().toString());

        CheckoutSingleton.getInstance().addNewValue("invoice_name", invoice_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_last_name", invoice_last_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_address", invoice_address.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_phone", invoice_phone.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_organisation_name", invoice_organisation_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_organisation_code", invoice_organisation_code.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoiceSpinCity", invoiceSpinCity.getSelectedItem().toString());
        CheckoutSingleton.getInstance().addNewValue("invoiceSpinDistrict", invoiceSpinDistrict.getSelectedItem().toString());
    }

    private void radioBtnChecked() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_btn_yes:
                        checkVisibility=false;
                        newAddressLayout.setVisibility(View.GONE);
                        break;
                    case R.id.radio_btn_no:
                        newAddressLayout.setVisibility(View.VISIBLE);
                        checkVisibility=true;
                        invoice_name.requestFocus();
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
    public void setError(){

        if(delivery_name.getText().toString().length() == 0 ){
            delivery_name.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");
            delivery_name.requestFocus();
            return;
        }
        if(delivery_last_name.getText().toString().length() == 0 ){
            delivery_last_name.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            delivery_last_name.requestFocus();
            return;
        }
        if(delivery_email.getText().toString().length() == 0 ){
            delivery_email.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            delivery_email.requestFocus();
            return;
        }
        if(delivery_phone.getText().toString().length() == 0 ){
            delivery_phone.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            delivery_phone.requestFocus();
            return;
        }
        if(delivery_address.getText().toString().length() == 0 ){
            delivery_address.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            delivery_address.requestFocus();
            return;
        }
        if(delivery_card_id.getText().toString().length() == 0 ){
            delivery_card_id.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            delivery_card_id.requestFocus();
            return;
        }
    }
    public void visibleSetError(){
        if( invoice_name.getText().toString().length() == 0 ){
            invoice_name.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");
            invoice_name.requestFocus();
            return;
        }
        if( invoice_last_name.getText().toString().length() == 0 ){
            invoice_last_name.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");
            invoice_last_name.requestFocus();
            return;
        }
        if( invoice_address.getText().toString().length() == 0 ){
            invoice_address.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");
            invoice_address.requestFocus();
            return;
        }
        if( invoice_phone.getText().toString().length() == 0 ){
            invoice_phone.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");
            invoice_phone.requestFocus();
            return;
        }
    }
    public Boolean setCheckVisibility(){
        return checkVisibility;
    }

}
