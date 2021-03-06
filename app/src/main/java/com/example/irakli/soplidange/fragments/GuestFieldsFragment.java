package com.example.irakli.soplidange.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.utils.CheckoutSingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by floyd on 9/8/2016.
 */
public class GuestFieldsFragment extends android.support.v4.app.Fragment {

    private RadioGroup organizationRadioGroup;
    private RelativeLayout organizationLayout;
    EditText guest_name, guest_last_name, guest_mail, guest_address, guest_phone, guest_card_id, guest_organisation_name, guest_organisation_code;

    ImageView guestPersonView, guestEmailView, guestAddressView, guestPhoneView, guestCardView, guestOrganisationView, guestOrganisationCodeView;
    TextInputLayout textInputLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View guestFieldsFragment = inflater.inflate(R.layout.guest_fragment_layout, container, false);

        textInputLayout = (TextInputLayout) guestFieldsFragment.findViewById(R.id.delivery_field_organization_name_id);

        organizationRadioGroup = (RadioGroup) guestFieldsFragment.findViewById(R.id.organization_radio_group_id);
        organizationLayout = (RelativeLayout) guestFieldsFragment.findViewById(R.id.organization_fields_id);

        guestPersonView = (ImageView) guestFieldsFragment.findViewById(R.id.guest_person_image);
        guestEmailView = (ImageView) guestFieldsFragment.findViewById(R.id.guest_email_image);
        guestAddressView = (ImageView) guestFieldsFragment.findViewById(R.id.guest_address_image);
        guestPhoneView = (ImageView) guestFieldsFragment.findViewById(R.id.guest_phone_image);
        guestCardView = (ImageView) guestFieldsFragment.findViewById(R.id.guest_card_image);
        guestOrganisationView = (ImageView) guestFieldsFragment.findViewById(R.id.guest_organization_image);
        guestOrganisationCodeView = (ImageView) guestFieldsFragment.findViewById(R.id.guest_organisation_code_image);


        guest_organisation_code = (EditText) guestFieldsFragment.findViewById(R.id.guest_organisation_code);
        guest_organisation_name = (EditText) guestFieldsFragment.findViewById(R.id.guest_organisation_name);
        guest_card_id = (EditText) guestFieldsFragment.findViewById(R.id.guest_card_id);
        guest_phone = (EditText) guestFieldsFragment.findViewById(R.id.guest_phone);
        guest_address = (EditText) guestFieldsFragment.findViewById(R.id.guest_address);
        guest_mail = (EditText) guestFieldsFragment.findViewById(R.id.guest_mail);
        guest_last_name = (EditText) guestFieldsFragment.findViewById(R.id.guest_last_name);
        guest_name = (EditText) guestFieldsFragment.findViewById(R.id.guest_name);


        guest_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guestPersonView.setImageResource(b ? R.drawable.guest_person : R.drawable.guest_person_grey);

            }
        });


        guest_last_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guestPersonView.setImageResource(b ? R.drawable.guest_person : R.drawable.guest_person_grey);
            }
        });


        guest_mail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guestEmailView.setImageResource(b ? R.drawable.guest_email : R.drawable.guest_email_grey);
            }
        });


        guest_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guestAddressView.setImageResource(b ? R.drawable.guest_address : R.drawable.guest_address_grey);

            }
        });


        guest_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guestPhoneView.setImageResource(b ? R.drawable.guest_mobile : R.drawable.guest_mobile_grey);

            }
        });


        guest_card_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guestCardView.setImageResource(b ? R.drawable.guest_card : R.drawable.guest_card_grey);
            }
        });

        guest_organisation_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guestOrganisationView.setImageResource(b ? R.drawable.guest_organization : R.drawable.guest_organization_grey);
            }
        });

        guest_organisation_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guestOrganisationCodeView.setImageResource(b ? R.drawable.guest_org_code : R.drawable.guest_org_code_grey);
            }
        });


        radioBtnChecked();
        return guestFieldsFragment;
    }

    public void saveGuestInfo() {
        String allPrice = getActivity().getIntent().getExtras().getString("allPrice");

        CheckoutSingleton.getInstance().addNewValue("allPrice", allPrice);

        CheckoutSingleton.getInstance().addNewValue("guest_name", guest_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_last_name", guest_last_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_email", guest_mail.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_address", guest_address.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_mobile", guest_phone.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_card_id", guest_card_id.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_organisation_name", guest_organisation_name.getText().toString());
        CheckoutSingleton.getInstance().addNewValue("guest_organisation_code", guest_organisation_code.getText().toString());
    }

    private void radioBtnChecked() {
        organizationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
                switch (checkedId) {
                    case R.id.yes_btn_id:
//                        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
                        organizationLayout.setVisibility(View.VISIBLE);
                        CheckoutSingleton.getInstance().addNewValue("organization_group_checked", "yes");
//                        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
                        break;
                    case R.id.no_btn_id:
//                        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
                        CheckoutSingleton.getInstance().addNewValue("organization_group_checked", "no");
                        organizationLayout.setVisibility(View.GONE);
                }
            }
        });
    }


    public void setError() {

        if (guest_name.getText().toString().length() < 2) {
            guest_name.setError("გთხოვთ მიუთითეთ სახელი!");

            guest_name.requestFocus();
            guestPersonView.setImageResource(R.drawable.error_person);

            return;
        }
        if (guest_last_name.getText().toString().length() < 3) {
            guest_last_name.setError("გთხოვთ მიუთითეთ გვარი!");

            guest_last_name.requestFocus();
            guestPersonView.setImageResource(R.drawable.error_person);
            return;
        }
        if (guest_mail.getText().toString().length() <= 7 || !guest_mail.getText().toString().contains("@") || !guest_mail.getText().toString().contains(".") || guest_mail.getText().toString().contains(" ")) {
            guest_mail.setError("გთხოვთ მიუთითოთ სწორი ელ.ფოსტა!");
            guest_mail.requestFocus();
            guestEmailView.setImageResource(R.drawable.error_email);
            return;
        }

        if (guest_phone.getText().toString().length() != 9) {
            guest_phone.setError("გთხოვთ მიუთითეთ სწორი ფორმატით: 599XXXXXX!");

            guest_phone.requestFocus();
            guestPhoneView.setImageResource(R.drawable.error_phone);
            return;
        }

        if (guest_card_id.getText().toString().length() != 11) {
            guest_card_id.setError("გთხოვთ მიუთითეთ თქვენი ID!");

            guest_card_id.requestFocus();
            guestCardView.setImageResource(R.drawable.error_card);
            return;
        }

        if (CheckoutSingleton.getInstance().getValue("organization_group_checked").equals("yes") &&
                guest_organisation_name.getText().toString().length() < 2) {
            guest_organisation_name.setError("გთხოვთ მიუთითეთ ორგანიზაციის დასახელება");
            guest_organisation_name.requestFocus();
            return;
        }

//        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
//        System.out.println(guest_organisation_code.getText().toString().length());
        if (CheckoutSingleton.getInstance().getValue("organization_group_checked").equals("yes") &&
                guest_organisation_code.getText().toString().length() != 9) {
            guest_organisation_code.setError(getString(R.string.identification_code_error));
            guest_organisation_code.requestFocus();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
//        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
        retryShared();
        checkConstaint();
//        if (!CheckoutSingleton.getInstance().getCartmap().containsKey("organization_group_checked")){
//            CheckoutSingleton.getInstance().addNewValue("organization_group_checked", "no");
//        }
//        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
    }

    @Override
    public void onStop() {
        super.onStop();
        saveGuestInfo();
        saveShared();
    }

    public void checkConstaint() {
        if (CheckoutSingleton.getInstance().getCartmap().containsKey("guest_name")) {
            guest_name.setText(CheckoutSingleton.getInstance().getValue("guest_name"));
            guest_last_name.setText(CheckoutSingleton.getInstance().getValue("guest_last_name"));
            guest_mail.setText(CheckoutSingleton.getInstance().getValue("guest_email"));
            guest_phone.setText(CheckoutSingleton.getInstance().getValue("guest_mobile"));
            guest_card_id.setText(CheckoutSingleton.getInstance().getValue("guest_card_id"));
            if (CheckoutSingleton.getInstance().getCartmap().containsKey("guest_address")) {
                guest_address.setText(CheckoutSingleton.getInstance().getValue("guest_address"));
            }
            if (CheckoutSingleton.getInstance().getCartmap().containsKey("guest_organisation_name")) {
                guest_organisation_name.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_name"));
            }
            if (CheckoutSingleton.getInstance().getCartmap().containsKey("guest_organisation_code")) {
                guest_organisation_code.setText(CheckoutSingleton.getInstance().getValue("guest_organisation_code"));
            }
        }
    }


    public void saveShared() {
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("checkout", Context.MODE_PRIVATE);
        HashMap<String, String> cartMap;
        cartMap = CheckoutSingleton.getInstance().getCartmap();
        if (CheckoutSingleton.getInstance().getCartmap().containsKey("organization_group_checked")) {
            cartMap.remove("organization_group_checked");
        }
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartMap);
        prefsEditor.putString("checkoutObjects", json);
        prefsEditor.apply();
    }

    public void retryShared() {
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("checkout", Context.MODE_PRIVATE);


        Gson gson = new Gson();
        String json = mPrefs.getString("checkoutObjects", "");

        Type typeOfHashMap = new TypeToken<HashMap<String, String>>() {
        }.getType();
        HashMap<String, String> newMap = gson.fromJson(json, typeOfHashMap);
        CheckoutSingleton.getInstance().setCart(newMap);
    }

}
