package com.example.irakli.soplidange.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.utils.CheckoutSingleton;
import com.google.gson.Gson;

import java.util.HashMap;


/**
 * Created by floyd on 9/8/2016.
 */
public class DeliveryPlaceFragment extends Fragment {

    private LinearLayout newAddressLayout;
    private RelativeLayout organizationLayout,deliveryRadioLayout;
    private RadioGroup deliveryRadioGroup,radioGroup,organizationRadioGroup;

    Spinner spinCity,spinDistrict,invoiceSpinCity,invoiceSpinDistrict;

    ScrollView scrollView;

    EditText delivery_name,delivery_last_name,delivery_phone,delivery_address,delivery_email,delivery_card_id,delivery_organisation_name,
    delivery_organisation_code, invoice_last_name, invoice_name,invoice_phone,invoice_address,invoice_organisation_name,invoice_organisation_code;

    ImageView delivery_name_image,delivery_phone_image,delivery_address_image,delivery_email_image,delivery_card_id_image,delivery_organisation_name_image,
            delivery_organisation_code_image, invoice_name_image,invoice_phone_image,invoice_address_image,invoice_organisation_name_image,invoice_organisation_code_image;

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
        delivery_last_name = (EditText) deliveryFragment.findViewById(R.id.delivery_last_name);
        delivery_phone = (EditText) deliveryFragment.findViewById(R.id.delivery_phone);
        delivery_address = (EditText) deliveryFragment.findViewById(R.id.delivery_address);
        delivery_email = (EditText) deliveryFragment.findViewById(R.id.delivery_email);
        delivery_card_id = (EditText) deliveryFragment.findViewById(R.id.delivery_card_id);
        delivery_organisation_name = (EditText) deliveryFragment.findViewById(R.id.delivery_organisation_name);
        delivery_organisation_code = (EditText) deliveryFragment.findViewById(R.id.delivery_organisation_code);

        invoice_name = (EditText) deliveryFragment.findViewById(R.id.invoice_name);
        invoice_last_name = (EditText) deliveryFragment.findViewById(R.id.invoice_last_name);
        invoice_phone = (EditText) deliveryFragment.findViewById(R.id.invoice_phone);
        invoice_address = (EditText) deliveryFragment.findViewById(R.id.invoice_address);
        invoice_organisation_name = (EditText) deliveryFragment.findViewById(R.id.invoice_organisation_name);
        invoice_organisation_code = (EditText) deliveryFragment.findViewById(R.id.invoice_organisation_code);
        fillEditText();

        delivery_name_image = (ImageView) deliveryFragment.findViewById(R.id.delivery_person_image);
        delivery_phone_image = (ImageView) deliveryFragment.findViewById(R.id.delivery_phone_image);
        delivery_address_image = (ImageView) deliveryFragment.findViewById(R.id.delivery_address_image);
        delivery_email_image = (ImageView) deliveryFragment.findViewById(R.id.delivery_email_image);
        delivery_card_id_image = (ImageView) deliveryFragment.findViewById(R.id.delivery_card_image);
        delivery_organisation_name_image = (ImageView) deliveryFragment.findViewById(R.id.delivery_organisation_image);
        delivery_organisation_code_image = (ImageView) deliveryFragment.findViewById(R.id.delivery_organisation_code_image);

        invoice_name_image = (ImageView) deliveryFragment.findViewById(R.id.invoice_person_image);
        invoice_phone_image = (ImageView) deliveryFragment.findViewById(R.id.invoice_phone_image);
        invoice_address_image = (ImageView) deliveryFragment.findViewById(R.id.invoice_address_image);
        invoice_organisation_name_image = (ImageView) deliveryFragment.findViewById(R.id.invoice_organisation_image);
        invoice_organisation_code_image = (ImageView) deliveryFragment.findViewById(R.id.invoice_organisation_code_image);

        spinCity = (Spinner) deliveryFragment.findViewById(R.id.spinCity);
        spinDistrict = (Spinner) deliveryFragment.findViewById(R.id.spinDistrict);
        invoiceSpinCity = (Spinner) deliveryFragment.findViewById(R.id.invoice_spinCity);
        invoiceSpinDistrict = (Spinner) deliveryFragment.findViewById(R.id.invoice_spinDistrict);




        delivery_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_name_image.setImageResource(b ? R.drawable.guest_person : R.drawable.guest_person_grey);
            }
        });
        delivery_last_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_name_image.setImageResource(b ? R.drawable.guest_person : R.drawable.guest_person_grey);
            }
        });
        delivery_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_email_image.setImageResource(b ? R.drawable.guest_email : R.drawable.guest_email_grey);
            }
        });
        delivery_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_address_image.setImageResource(b ? R.drawable.guest_address : R.drawable.guest_address_grey);
            }
        });
        delivery_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_phone_image.setImageResource(b ? R.drawable.guest_mobile : R.drawable.guest_mobile_grey);
            }
        });
        delivery_card_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_card_id_image.setImageResource(b ? R.drawable.guest_card : R.drawable.guest_card_grey);
            }
        });
        delivery_organisation_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_organisation_name_image.setImageResource(b ? R.drawable.guest_organization : R.drawable.guest_organization_grey);
            }
        });
        delivery_organisation_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_organisation_code_image.setImageResource(b ? R.drawable.guest_org_code : R.drawable.guest_org_code_grey);
            }
        });
        invoice_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                invoice_name_image.setImageResource(b ? R.drawable.guest_person : R.drawable.guest_person_grey);
            }
        });
        invoice_last_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                invoice_name_image.setImageResource(b ? R.drawable.guest_person : R.drawable.guest_person_grey);
            }
        });
        invoice_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                invoice_phone_image.setImageResource(b ? R.drawable.guest_mobile : R.drawable.guest_mobile_grey);
            }
        });
        invoice_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                invoice_address_image.setImageResource(b ? R.drawable.guest_address : R.drawable.guest_address_grey);
            }
        });
        invoice_organisation_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                invoice_organisation_name_image.setImageResource(b ? R.drawable.guest_organization : R.drawable.guest_organization_grey);
            }
        });
        invoice_organisation_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                invoice_organisation_code_image.setImageResource(b ? R.drawable.guest_org_code : R.drawable.guest_org_code_grey);
            }
        });




        spinCity.setFocusableInTouchMode(true);
        spinDistrict.setFocusableInTouchMode(true);
        invoiceSpinCity.setFocusableInTouchMode(true);
        invoiceSpinDistrict.setFocusableInTouchMode(true);

        spinCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                delivery_address_image.setImageResource(b ? R.drawable.guest_address : R.drawable.guest_address_grey);
            }
        });
        spinDistrict.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                delivery_address_image.setImageResource(b ? R.drawable.guest_address : R.drawable.guest_address_grey);
            }
        });
        invoiceSpinCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                invoice_address_image.setImageResource(b ? R.drawable.guest_address : R.drawable.guest_address_grey);
            }
        });
        invoiceSpinDistrict.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                invoice_address_image.setImageResource(b ? R.drawable.guest_address : R.drawable.guest_address_grey);
            }
        });


        newAddressLayout = (LinearLayout) deliveryFragment.findViewById(R.id.new_address_fields_id);
        scrollView = (ScrollView) deliveryFragment.findViewById(R.id.delivery_scroll_view_id);

        //fetch the spinner from layout file
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(deliveryFragment.getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.city_array));//setting the country_array to spinner
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCity.setAdapter(cityAdapter);
        if (CheckoutSingleton.getInstance().getCartmap().containsKey("spinCity")){
            spinCity.setSelection(Integer.parseInt(CheckoutSingleton.getInstance().getValue("spinCity")));

        }


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
        //fetch the spinner from layout file
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(deliveryFragment.getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.district_array));//setting the country_array to spinner
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDistrict.setAdapter(districtAdapter);
        if (CheckoutSingleton.getInstance().getCartmap().containsKey("spinDistrict")){
            spinDistrict.setSelection(Integer.parseInt(CheckoutSingleton.getInstance().getValue("spinDistrict")));

        }


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

        //fetch the spinner from layout file
        ArrayAdapter<String> invoiceCityAdapter = new ArrayAdapter<String>(deliveryFragment.getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.city_array));//setting the country_array to spinner
        invoiceCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        invoiceSpinCity.setAdapter(invoiceCityAdapter);
        if (CheckoutSingleton.getInstance().getCartmap().containsKey("invoiceSpinCity")){
            invoiceSpinCity.setSelection(Integer.parseInt(CheckoutSingleton.getInstance().getValue("invoiceSpinCity")));

        }

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
        //fetch the spinner from layout file
        ArrayAdapter<String> invoiceDistrictAdapter = new ArrayAdapter<String>(deliveryFragment.getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.district_array));//setting the country_array to spinner
        invoiceDistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        invoiceSpinDistrict.setAdapter(invoiceDistrictAdapter);
        if (CheckoutSingleton.getInstance().getCartmap().containsKey("spinDistrict")){
            invoiceSpinDistrict.setSelection(Integer.parseInt(CheckoutSingleton.getInstance().getValue("spinDistrict")));

        }

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
        saveShared ();

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
        CheckoutSingleton.getInstance().addNewValue("spinCity", String.valueOf(spinCity.getSelectedItemId()));
        CheckoutSingleton.getInstance().addNewValue("spinDistrict", String.valueOf(spinDistrict.getSelectedItemId()));

        CheckoutSingleton.getInstance().addNewValue("invoice_name", invoice_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_last_name", invoice_last_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_address", invoice_address.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_phone", invoice_phone.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_organisation_name", invoice_organisation_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoice_organisation_code", invoice_organisation_code.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("invoiceSpinCity", String.valueOf(invoiceSpinCity.getSelectedItemId()));
        CheckoutSingleton.getInstance().addNewValue("invoiceSpinDistrict", String.valueOf(invoiceSpinDistrict.getSelectedItemId()));
    }

    public void fillEditText(){
        delivery_name.setText(CheckoutSingleton.getInstance().getValue("guest_name"));
        delivery_phone.setText(CheckoutSingleton.getInstance().getValue("guest_mobile"));
        delivery_last_name.setText(CheckoutSingleton.getInstance().getValue("guest_last_name"));
        delivery_address.setText(CheckoutSingleton.getInstance().getValue("guest_address"));
        delivery_email.setText(CheckoutSingleton.getInstance().getValue("guest_email"));
        delivery_card_id.setText(CheckoutSingleton.getInstance().getValue("guest_card_id"));
        delivery_organisation_name.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_name"));
        delivery_organisation_code.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_code"));

        invoice_name.setText(CheckoutSingleton.getInstance().getValue("guest_name"));
        invoice_last_name.setText(CheckoutSingleton.getInstance().getValue("guest_last_name"));
        invoice_phone.setText(CheckoutSingleton.getInstance().getValue("guest_mobile"));
        invoice_address.setText(CheckoutSingleton.getInstance().getValue("guest_address"));
        invoice_organisation_name.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_name"));
        invoice_organisation_code.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_code"));

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

        if(delivery_name.getText().toString().length() < 2 ){
            delivery_name.setError("გთხოვთ მიუთითეთ სახელი!");

            delivery_name.requestFocus();
            delivery_name_image.setImageResource(R.drawable.error_person);
            return;
        }
        if(delivery_last_name.getText().toString().length() < 3 ){
            delivery_last_name.setError("გთხოვთ მიუთითეთ გვარი!");

            delivery_last_name.requestFocus();
            delivery_name_image.setImageResource(R.drawable.error_person);
            return;
        }

        if(delivery_phone.getText().toString().length() < 9 ){
            delivery_phone.setError("გთხოვთ მიუთითეთ ტელეფონი!");
            delivery_phone.requestFocus();
            delivery_phone_image.setImageResource(R.drawable.error_phone);
            return;
        }
        if(delivery_address.getText().toString().length() < 3 ){
            delivery_address.setError("გთხოვთ მიუთითეთ მისამართი!");

            delivery_address.requestFocus();
            delivery_address_image.setImageResource(R.drawable.error_address);
            return;
        }
        if(spinCity.getSelectedItem().equals("- ქალაქი -") ){
            delivery_address.setError("აირჩიეთ ქალაქი!");

            delivery_address.requestFocus();
            delivery_address_image.setImageResource(R.drawable.error_address);
            return;
        }
        if(spinDistrict.getSelectedItem().equals("- აირჩიეთ უბანი -")){
            delivery_address.setError("აირჩიეთ უბანი!");

            delivery_address.requestFocus();
            delivery_address_image.setImageResource(R.drawable.error_address);
            return;
        }
        if(delivery_card_id.getText().toString().length() != 11 ){
            delivery_card_id.setError("გთხოვთ მიუთითეთ თქვენი ID!");

            delivery_card_id.requestFocus();
            delivery_card_id_image.setImageResource(R.drawable.error_card);
            return;
        }
    }
    public void visibleSetError(){
        if( invoice_name.getText().toString().length() < 2 ){
            invoice_name.setError("გთხოვთ მიუთითეთ სახელი!");


            invoice_name.requestFocus();
            invoice_name_image.setImageResource(R.drawable.error_person);
            return;
        }
        if( invoice_last_name.getText().toString().length() < 3 ){
            invoice_last_name.setError("გთხოვთ მიუთითეთ გვარი!");

            invoice_last_name.requestFocus();
            invoice_name_image.setImageResource(R.drawable.error_person);
            return;
        }
        if( invoice_address.getText().toString().length() < 3 ){
            invoice_address.setError("გთხოვთ მიუთითეთ მისამართი!");

            invoice_address.requestFocus();
            invoice_address_image.setImageResource(R.drawable.error_address);
            return;
        }

         if(invoiceSpinCity.getSelectedItem().equals("- ქალაქი -") ){
            invoice_address.setError("აირჩიეთ ქალაქი!");

            invoice_address.requestFocus();
             invoice_address_image.setImageResource(R.drawable.error_address);
            return;
        }
        if( invoiceSpinDistrict.getSelectedItem().equals("- აირჩიეთ უბანი -")){
            invoice_address.setError("აირჩიეთ უბანი!");

            invoice_address.requestFocus();
            invoice_address_image.setImageResource(R.drawable.error_address);
            return;
        }
        if( invoice_phone.getText().toString().length() < 9 ){
            invoice_phone.setError("გთხოვთ მიუთითეთ ტელეფონი!");

            invoice_phone.requestFocus();
            invoice_phone_image.setImageResource(R.drawable.error_phone);
            return;
        }
    }
    public Boolean setCheckVisibility(){
        return checkVisibility;
    }
    public void saveShared (){

        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("checkout", Context.MODE_PRIVATE);
        HashMap<String,String> cartMap;
        cartMap = CheckoutSingleton.getInstance().getCartmap();
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartMap);
        prefsEditor.putString("checkoutObjects", json);
        prefsEditor.apply();
    }

}
