package com.example.irakli.soplidange.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.utils.AuthorizationParams;
import com.example.irakli.soplidange.utils.CheckoutSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by floyd on 9/9/2016.
 */
public class PaymentMethodsFragment extends Fragment {

    private TextView serviceView;
    private String url;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    RadioGroup radioGroup;
    CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View paymentMethodFragment = inflater.inflate(R.layout.payment_fragment_layout, container, false);
        url = "http://soplidan.ge/terms-and-conditions/";

        radioGroup = (RadioGroup) paymentMethodFragment.findViewById(R.id.payment_radio_id);
        checkBox = (CheckBox) paymentMethodFragment.findViewById(R.id.service_radio_btn_id);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    CheckoutSingleton.getInstance().addNewValue("confirm","Yes");
                }else{
                    CheckoutSingleton.getInstance().addNewValue("confirm","No");
                }

            }
        });



        getJSONInfo();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    if (btn.getId() == checkedId) {
                        String text = String.valueOf(btn.getText());
                        CheckoutSingleton.getInstance().addNewValue("payment_radioButton",text);
                        System.out.println(text + "iiii");
                        return;
                    }
                }
            }
        });

        serviceView = (TextView) paymentMethodFragment.findViewById(R.id.service_terms_id);
        serviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(Intent.ACTION_VIEW);
                serviceIntent.setData(Uri.parse(url));
                startActivity(serviceIntent);
            }
        });

        CheckoutSingleton.getInstance().getCartmap().remove("order_time_radioButton");



        return paymentMethodFragment;
    }

    private void getJSONInfo() {
        String url = "http://soplidan.ge/api/payments?items_per_page=80";

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = response.getJSONArray("payments");
                            RadioGroup.LayoutParams rprms;

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject curObj = jsonArray.getJSONObject(i);
                                String status = curObj.getString("status");
                                String shipping = curObj.getString("payment");

                                if (status.equals("A") && !curObj.getString("payment_id").equals("12")) {
                                    RadioButton radioButton = new RadioButton(getActivity());
                                    radioButton.setText(shipping);
                                    radioButton.setId(+i);
                                    rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                                    radioGroup.addView(radioButton, rprms);
                                }

                            }

                            progressDialog.cancel();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.cancel();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                String key = "Authorization";
                String encodedString = Base64.encodeToString(String.format("%s:%s", AuthorizationParams.USERNAME, AuthorizationParams.PASSWORD).getBytes(), Base64.NO_WRAP);
                String value = String.format("Basic %s", encodedString);
                System.out.println("Shit:   " + value + "  -  " + encodedString);
                map.put(key, value);
                return map;
            }
        };

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = ProgressDialog.show(getActivity(), "", "გთხოვთ დაელოდოთ...");
        requestQueue.add(jsonRequest);
    }

    @Override
    public void onStop() {
        super.onStop();
        CheckoutSingleton.getInstance().getCartmap().remove("order_time_radioButton");

    }

    public void check() {
        if(checkBox.isChecked()){
            CheckoutSingleton.getInstance().addNewValue("confirm","Yes");
        }else{
            CheckoutSingleton.getInstance().addNewValue("confirm","No");
        }
    }
}
