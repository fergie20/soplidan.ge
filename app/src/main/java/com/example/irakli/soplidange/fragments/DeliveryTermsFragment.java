package com.example.irakli.soplidange.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.utils.AuthorizationParams;
import com.example.irakli.soplidange.utils.CheckoutSingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by floyd on 9/8/2016.
 */
public class DeliveryTermsFragment extends Fragment {


    TextView termsInfo1;
    TextView termsInfo2;
    TextView termsInfo3;
    TextView termsInfo4;
    RadioGroup radioGroup;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    TextView deliveryPrice, deliveryCost;
    double totalPrice;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deliveryTermsFragment = inflater.inflate(R.layout.delivery_terms_layout, container, false);


        deliveryCost = (TextView) deliveryTermsFragment.findViewById(R.id.delivery_cost_id);
        deliveryCost.setTypeface(typeface());
        deliveryPrice = (TextView) deliveryTermsFragment.findViewById(R.id.delivery_price_id);
        deliveryPrice.setTypeface(typeface());
        termsInfo1 = (TextView) deliveryTermsFragment.findViewById(R.id.terms_info_1_id);
        termsInfo2 = (TextView) deliveryTermsFragment.findViewById(R.id.terms_info_2_id);
        termsInfo3 = (TextView) deliveryTermsFragment.findViewById(R.id.terms_info_3_id);
        termsInfo4 = (TextView) deliveryTermsFragment.findViewById(R.id.terms_info_4_id);
        radioGroup = (RadioGroup) deliveryTermsFragment.findViewById(R.id.radioGroup_id);

        getJSONInfo();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    if (btn.getId() == checkedId) {
                        String text = String.valueOf(btn.getText());
                        CheckoutSingleton.getInstance().addNewValue("order_time_radioButton", text);
                        System.out.println(text + "iiii");
                        return;
                    }
                }
            }
        });

        String dot = "<font color='#028bca'>\u25CF </font>";


        totalPrice = Double.parseDouble(CheckoutSingleton.getInstance().getValue("allPrice"));


        if (CheckoutSingleton.getInstance().getValue("spinDistrict").equals("16") ||
                CheckoutSingleton.getInstance().getValue("spinDistrict").equals("29") ||
                CheckoutSingleton.getInstance().getValue("spinDistrict").equals("30")) {
            deliveryPrice.setText("ტრანსპორტირების ღირებულება 8 ¢");

            double z = 8 + Double.parseDouble(CheckoutSingleton.getInstance().getValue("allPrice"));
            deliveryCost.setText(Html.fromHtml(dot + " სულ: " + CheckoutSingleton.getInstance().getValue("allPrice") + " ¢ + 8 ¢ = " + z + " ¢"));


        } else {
            if (totalPrice < 30.0) {
                deliveryPrice.setText("ტრანსპორტირების ღირებულება 3 ¢");
                double x = 3 + Double.parseDouble(CheckoutSingleton.getInstance().getValue("allPrice"));
                deliveryCost.setText(Html.fromHtml(dot + " სულ: " + CheckoutSingleton.getInstance().getValue("allPrice") + " ¢ + 3 ¢ = " + x + " ¢"));
            } else {
                deliveryPrice.setText("ტრანსპორტირების ღირებულება 0 ¢");
                double y = 0 + Double.parseDouble(CheckoutSingleton.getInstance().getValue("allPrice"));
                deliveryCost.setText(Html.fromHtml(dot + " სულ: " + CheckoutSingleton.getInstance().getValue("allPrice") + " ¢ + 0 ¢ = " + y + " ¢"));
            }
        }
        termsInfo1.setText(Html.fromHtml(dot + termsInfo1.getText()));
        termsInfo2.setText(Html.fromHtml(dot + termsInfo2.getText()));
        termsInfo3.setText(Html.fromHtml(dot + termsInfo3.getText()));
        termsInfo4.setText(Html.fromHtml(dot + termsInfo4.getText()));

        return deliveryTermsFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        retryShared();
    }

    private void getJSONInfo() {
        String url = "http://soplidan.ge/api/shippings?items_per_page=80";

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = response.getJSONArray("shippings");
                            RadioGroup.LayoutParams rprms;

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject curObj = jsonArray.getJSONObject(i);
                                String status = curObj.getString("status");
                                String shipping = curObj.getString("shipping");

                                if (status.equals("A")) {
                                    RadioButton radioButton = new RadioButton(getActivity());
                                    radioButton.setText(shipping);
                                    radioButton.setId(+i);
                                    rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                                    radioGroup.addView(radioButton, rprms);
                                }

                            }

                            progressDialog.cancel();

                        } catch (JSONException e) {
                            progressDialog.cancel();
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

    public void retryShared() {
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("checkout", Context.MODE_PRIVATE);


        Gson gson = new Gson();
        String json = mPrefs.getString("checkoutObjects", "");

        Type typeOfHashMap = new TypeToken<HashMap<String, String>>() {
        }.getType();
        HashMap<String, String> newMap = gson.fromJson(json, typeOfHashMap);
        CheckoutSingleton.getInstance().setCart(newMap);
    }

    public Typeface typeface() {

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "BPG_GEL_Excelsior_Caps.ttf");

        return custom_font;
    }

    public void saveShippingTotal() {


        if (CheckoutSingleton.getInstance().getValue("spinDistrict").equals("16") ||
                CheckoutSingleton.getInstance().getValue("spinDistrict").equals("29") ||
                CheckoutSingleton.getInstance().getValue("spinDistrict").equals("30")) {
            deliveryPrice.setText("ტრანსპორტირების ღირებულება 8 ¢");

            double z = 8 + Double.parseDouble(CheckoutSingleton.getInstance().getValue("allPrice"));
            CheckoutSingleton.getInstance().addNewValue("shipping_price", "8 ¢");
            CheckoutSingleton.getInstance().addNewValue("total_price", String.valueOf(z) + " ¢");


        } else {
            if (totalPrice < 30.0) {
                deliveryPrice.setText("ტრანსპორტირების ღირებულება 3 ¢");
                double x = 3 + Double.parseDouble(CheckoutSingleton.getInstance().getValue("allPrice"));
                CheckoutSingleton.getInstance().addNewValue("shipping_price", "3 ¢");
                CheckoutSingleton.getInstance().addNewValue("total_price", String.valueOf(x) + " ¢");
            } else {
                deliveryPrice.setText("ტრანსპორტირების ღირებულება 0 ¢");
                double y = 0 + Double.parseDouble(CheckoutSingleton.getInstance().getValue("allPrice"));
                CheckoutSingleton.getInstance().addNewValue("shipping_price", "0 ¢");
                CheckoutSingleton.getInstance().addNewValue("total_price", String.valueOf(y) + " ¢");
            }
        }
    }
}
