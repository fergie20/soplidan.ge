package com.example.irakli.soplidange.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.AuthorizationParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeoLab on 10/17/16.
 */

public class CategoryDetailDialog extends DialogFragment {

    private RequestQueue requestQueue;
    ProgressBar progressBar;
    ScrollView scrollView;
    String url = "http://soplidan.ge/api/categories/";
    ImageView dismissDialog;
    String category_name;
    int category_id;
    TextView categoryNameView;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.category_detail_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        category_name = (String) getArguments().getSerializable("category_name");
        category_id = (Integer) getArguments().getSerializable("category_id");
        dismissDialog = (ImageView) rootView.findViewById(R.id.dialog_dismiss_id);
        categoryNameView = (TextView) rootView.findViewById(R.id.category_name);
        categoryNameView.setText(category_name);


        dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);


        getJSONInfo(url+category_id);

        return rootView;
    }


    private void getJSONInfo(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    String fullDesc = response.getString("description");

//                    scrollView = (ScrollView) rootView.findViewById(R.id.dialog_scrol_id);

//                    if(fullDesc.equals("")){
//                        scrollView.setVisibility(View.GONE);
//                    }

                    String str_without_html= Html.fromHtml(fullDesc).toString();

                    TextView description = (TextView) rootView.findViewById(R.id.dialog_description_id);
                    description.setText(str_without_html);

                    progressBar.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                }

        ){
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
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);
        requestQueue.add(jsonObjectRequest);
    }

}
