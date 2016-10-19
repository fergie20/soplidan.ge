package com.example.irakli.soplidange.dialog;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.ProductsActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.adapters.CategoriesAdapter;
import com.example.irakli.soplidange.custom.CTextView;
import com.example.irakli.soplidange.models.CategoryModel;
import com.example.irakli.soplidange.utils.AuthorizationParams;
import com.example.irakli.soplidange.utils.SingletonTest;
import com.example.irakli.soplidange.models.ProductModel;
import com.squareup.picasso.Picasso;

import net.wujingchao.android.view.SimpleTagImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeoLab on 7/10/16.
 */
public class ProductDetailDialog extends DialogFragment {
    ImageView minusTextView;
    ImageView plusTextView;
    TextView quantityView;
    CTextView sumView;
    String checkQuantity;
    TextView currentPrice;
    View rootView;
    ProductModel model;
    TextView outOfStockView;
    RelativeLayout plusMinusVisibleView;
    ImageView dismissDialog;
    private RequestQueue requestQueue;
    ProgressBar progressBar;
    ScrollView scrollView;
    String url = "http://soplidan.ge/api/products/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.productdetaildialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        model = (ProductModel) getArguments().getSerializable("model");
        StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
        currentPrice = (TextView) rootView.findViewById(R.id.dialog_current_price_id);
        dismissDialog = (ImageView) rootView.findViewById(R.id.dialog_dismiss_id);


        dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        SimpleTagImageView image = (SimpleTagImageView) rootView.findViewById(R.id.dialog_image_id);
        if(model.getList_discount()!=0) {
            Picasso.with(getActivity())
                    .load(model.getImg())
                    .centerInside()
                    .fit()
                    .into(image);
            image.setTagEnable(true);
            image.setTagText("sale: " + model.getList_discount_prc() + "%");

            currentPrice.setTypeface(typeface());

            currentPrice.setText("ფასი: " + model.getBase_price() + "¢  " + model.getList_price() + "¢", TextView.BufferType.SPANNABLE);
            Spannable spannable = (Spannable) currentPrice.getText();
            spannable.setSpan(STRIKE_THROUGH_SPAN, spannable.length()/2+4, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }else{
            Picasso.with(getActivity())
                    .load(model.getImg())
                    .centerInside()
                    .fit()
                    .into(image);
            image.setTagEnable(false);
            currentPrice.setTypeface(typeface());
            currentPrice.setText( model.getList_price() + " ¢");
        }
        outOfStockView = (TextView) rootView.findViewById(R.id.dialog_out_of_stock_id);
        Button addbutton = (Button) rootView.findViewById(R.id.dialog_addbutton_id);
        plusMinusVisibleView = (RelativeLayout) rootView.findViewById(R.id.dialog_plus_minus_view_id);
        if(model.getRecource()==0){
            outOfStockView.setVisibility(View.VISIBLE);
            addbutton.setVisibility(View.GONE);
            plusMinusVisibleView.setVisibility(View.GONE);
        }



        quantityView = (TextView) rootView.findViewById(R.id.dialog_quantity_id);
        if(model.getRecource() > 0){
            quantityView.setText("1");
        }else{
            quantityView.setText("0");
        }
        TextView name = (TextView) rootView.findViewById(R.id.dialog_name_id);
        name.setText(model.getName());
        TextView price = (TextView) rootView.findViewById(R.id.dialog_price_id);
        price.setText(String.valueOf(model.getBase_price()) + " ¢");

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);

        getJSONInfo(url+model.getId());









        calculateSum();

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setQuontity(Integer.parseInt(quantityView.getText().toString()));

                SingletonTest.getInstance().addProduct(model.getId(), model);

                double newQuantity = Double.parseDouble(quantityView.getText().toString());
                SingletonTest.getInstance().addProduct(model.getId(), model);
                if (newQuantity <= 0) {

                    SingletonTest.getInstance().getCartMap().remove(model.getId());


                }
                Toast.makeText(getActivity(), "განახლდა კალათა", Toast.LENGTH_SHORT).show();

//                ((ProductsActivity)getActivity()).updateListView();
                dismiss();
            }
        });



        return rootView;
    }
    private void calculateSum() {
        minusTextView = (ImageView) rootView.findViewById(R.id.dialog_minus_id);
        plusTextView = (ImageView) rootView.findViewById(R.id.dialog_plus_id);
        sumView = (CTextView) rootView.findViewById(R.id.dialog_price_id);
        quantityView = (TextView) rootView.findViewById(R.id.dialog_quantity_id);
        if (SingletonTest.getInstance().getProduct(model.getId()) != null) {
            quantityView.setText(SingletonTest.getInstance().getProduct(model.getId()).getQuontity() + "");
        }
        double productPrice;
        int quantity;
        double sum;
        if(model.getBase_price()>0) {
            productPrice = model.getBase_price();
        }else {
            productPrice = model.getList_price();
        }
        checkQuantity = quantityView.getText().toString();
        quantity = Integer.parseInt((String) checkQuantity);
        sum = productPrice * quantity;

        NumberFormat nf = NumberFormat.getInstance(); // get instance
        nf.setMaximumFractionDigits(3); // set decimal places
        String s = nf.format(sum);
        sumView.setTypeface(typeface());

        sumView.setText(s+" ¢");


        plusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double productPrice;
                int quantity;
                double sum;
                if(model.getBase_price()>0) {
                    productPrice = model.getBase_price();
                }else {
                    productPrice = model.getList_price();
                }

                checkQuantity = quantityView.getText().toString();
                quantity = Integer.parseInt((String) checkQuantity);

                quantity++;
                if(quantity<=model.getRecource()) {

                    sum = productPrice * quantity;

                    NumberFormat nf = NumberFormat.getInstance(); // get instance
                    nf.setMaximumFractionDigits(3); // set decimal places
                    String s = nf.format(sum);

                    sumView.setText(s + " ¢");
                    quantityView.setText(quantity + "");

                }else{
                    quantity--;
                    sum = productPrice * quantity;

                    NumberFormat nf = NumberFormat.getInstance(); // get instance
                    nf.setMaximumFractionDigits(3); // set decimal places
                    String s = nf.format(sum);
                    Toast.makeText(getActivity(), "თქვენს მიერ არჩეული პროდუქტის რაოდენობა აღემატება მარაგს", Toast.LENGTH_SHORT).show();

                    sumView.setText(s + " ¢");
                    quantityView.setText(quantity + "");

                }

            }
        });
        minusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double productPrice;
                int quantity;
                double sum;
                if(model.getBase_price()>0) {
                    productPrice = model.getBase_price();
                }else {
                    productPrice = model.getList_price();
                }
                checkQuantity = quantityView.getText().toString();
                quantity = Integer.parseInt((String) checkQuantity);

                if (quantity >= 2) {
                    quantity--;

                    sum = productPrice * quantity;

                    NumberFormat nf = NumberFormat.getInstance(); // get instance
                    nf.setMaximumFractionDigits(3); // set decimal places
                    String s = nf.format(sum);

                    sumView.setText(s+" ¢");

                    quantityView.setText(quantity + "");


                } else {
                    sumView.setText("0 ¢");
                    quantityView.setText("0");

                }

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ProductsActivity)getActivity()).updateListView();
    }

    private void getJSONInfo(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    String fullDesc = response.getString("full_description");

                    scrollView = (ScrollView) rootView.findViewById(R.id.dialog_scrol_id);

                    if(fullDesc.equals("")){
                        scrollView.setVisibility(View.GONE);
                    }

                    String str_without_html=Html.fromHtml(fullDesc).toString();





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

    public Typeface typeface(){

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "BPG_GEL_Excelsior_Caps.ttf");

       return custom_font;
    }

}
