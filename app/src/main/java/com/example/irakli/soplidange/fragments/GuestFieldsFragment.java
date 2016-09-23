package com.example.irakli.soplidange.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.irakli.soplidange.Payment;
import com.example.irakli.soplidange.ProductsActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.CheckoutSingleton;
import com.example.irakli.soplidange.utils.SingletonTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by floyd on 9/8/2016.
 */
public class GuestFieldsFragment extends android.support.v4.app.Fragment{

    private RadioGroup organizationRadioGroup;
    private RelativeLayout organizationLayout;
    EditText guest_name;
    EditText guest_last_name;
    EditText guest_mail;
    EditText guest_address;
    EditText guest_phone;
    EditText guest_card_id;
    EditText guest_organisation_name;
    EditText guest_organisation_code;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View guestFieldsFragment = inflater.inflate(R.layout.guest_fragment_layout, container, false);

        organizationRadioGroup = (RadioGroup) guestFieldsFragment.findViewById(R.id.organization_radio_group_id);
        organizationLayout = (RelativeLayout) guestFieldsFragment.findViewById(R.id.organization_fields_id);


        guest_name = (EditText) guestFieldsFragment.findViewById(R.id.guest_name);
        guest_last_name = (EditText) guestFieldsFragment.findViewById(R.id.guest_last_name);
        guest_mail = (EditText) guestFieldsFragment.findViewById(R.id.guest_mail);
        guest_address = (EditText) guestFieldsFragment.findViewById(R.id.guest_address);
        guest_phone = (EditText) guestFieldsFragment.findViewById(R.id.guest_phone);
        guest_card_id = (EditText) guestFieldsFragment.findViewById(R.id.guest_card_id);
        guest_organisation_name = (EditText) guestFieldsFragment.findViewById(R.id.guest_organisation_name);
        guest_organisation_code = (EditText) guestFieldsFragment.findViewById(R.id.guest_organisation_code);


        radioBtnChecked();
        return guestFieldsFragment;
    }

    private void radioBtnChecked(){
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
    }



    public void saveGuestInfo() {

        CheckoutSingleton.getInstance().addNewValue("guest_name", guest_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_last_name", guest_last_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_email", guest_mail.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_address", guest_address.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_phone", guest_phone.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_card_id", guest_card_id.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_organisation_name", guest_organisation_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_organisation_code", guest_organisation_code.getText().toString());
    }

    public void setError(){

        if(guest_name.getText().toString().length() < 2 ){
            guest_name.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");
            guest_name.requestFocus();
            return;
        }
        if(guest_last_name.getText().toString().length() < 3 ){
            guest_last_name.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            guest_last_name.requestFocus();
            return;
        }
        if(guest_mail.getText().toString().length() < 7 ){
            guest_mail.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            guest_mail.requestFocus();
            return;
        }

        if(guest_phone.getText().toString().length() < 9 ){
            guest_phone.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            guest_phone.requestFocus();
            return;
        }
        if(guest_card_id.getText().toString().length() != 11 ){
            guest_card_id.setError("გთხოვთ შეავსოთ აუცილებელი ველი!");

            guest_card_id.requestFocus();
            return;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        retryShared();
        checkConstaint();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveGuestInfo();
        saveShared ();
    }

    public void checkConstaint(){
        if(CheckoutSingleton.getInstance().getCartmap().containsKey("guest_name")){
            guest_name.setText(CheckoutSingleton.getInstance().getValue("guest_name"));
            guest_last_name.setText(CheckoutSingleton.getInstance().getValue("guest_last_name"));
            guest_mail.setText(CheckoutSingleton.getInstance().getValue("guest_email"));
            guest_phone.setText(CheckoutSingleton.getInstance().getValue("guest_phone"));
            guest_card_id.setText(CheckoutSingleton.getInstance().getValue("guest_card_id"));
            if(CheckoutSingleton.getInstance().getCartmap().containsKey("guest_address")){
                guest_address.setText(CheckoutSingleton.getInstance().getValue("guest_address"));
            }
            if(CheckoutSingleton.getInstance().getCartmap().containsKey("guest_organisation_name")){
                guest_organisation_name.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_name"));
            }
            if(CheckoutSingleton.getInstance().getCartmap().containsKey("guest_organisation_code")){
                guest_organisation_code.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_code"));
            }
        }
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

    public void retryShared(){
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("checkout", Context.MODE_PRIVATE);


        Gson gson = new Gson();
        String json = mPrefs.getString("checkoutObjects", "");

        Type typeOfHashMap = new TypeToken<HashMap<String, String>>() { }.getType();
        HashMap<String, String> newMap = gson.fromJson(json, typeOfHashMap);
        CheckoutSingleton.getInstance().setCart(newMap);
    }
}
