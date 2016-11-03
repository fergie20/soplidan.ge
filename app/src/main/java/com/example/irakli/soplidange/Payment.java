package com.example.irakli.soplidange;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.ExampleData.ExampleData;
import com.example.irakli.soplidange.adapters.CategoriesAdapter;
import com.example.irakli.soplidange.fragments.DeliveryPlaceFragment;
import com.example.irakli.soplidange.fragments.DeliveryTermsFragment;
import com.example.irakli.soplidange.fragments.GuestFieldsFragment;
import com.example.irakli.soplidange.fragments.OrderedProductsFragment;
import com.example.irakli.soplidange.fragments.PaymentMethodsFragment;
import com.example.irakli.soplidange.models.CategoryModel;
import com.example.irakli.soplidange.utils.AuthorizationParams;
import com.example.irakli.soplidange.utils.CheckoutSingleton;
import com.example.irakli.soplidange.utils.SingletonTest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Payment extends AppCompatActivity {

    private Button nextBtn;
    private Button prevBtn;
    private ArrayList<Fragment> fragmentArrayList;
    private int counter = 0;
    GuestFieldsFragment guestFieldsFragment;
    DeliveryPlaceFragment deliveryPlaceFragment;
    DeliveryTermsFragment deliveryTermsFragment;
    PaymentMethodsFragment paymentMethodsFragment;
    OrderedProductsFragment orderedProductsFragment;
    RelativeLayout relativeLayout;
    boolean checkInvoiceRadioVisibility = false;
    boolean checkOrganizationVisibility = false;
    boolean checkSubVisibility = false;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initToolbar();

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }


        guestFieldsFragment = new GuestFieldsFragment();
        deliveryPlaceFragment = new DeliveryPlaceFragment();
        deliveryTermsFragment = new DeliveryTermsFragment();
        paymentMethodsFragment = new PaymentMethodsFragment();
        orderedProductsFragment = new OrderedProductsFragment();

        relativeLayout = (RelativeLayout) findViewById(R.id.organization_fields_id);

        fragmentArrayList = new ArrayList<>();

        nextBtn = (Button) findViewById(R.id.continue_btn_id);
        prevBtn = (Button) findViewById(R.id.previous_btn_id);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_id, guestFieldsFragment, guestFieldsFragment.getTag())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();

        changeFragment();



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        counter--;
    }

    private void initToolbar() {
        Toolbar paymentToolbar = (Toolbar) findViewById(R.id.toolbar_payment_id);
        setSupportActionBar(paymentToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                back();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeFragment() {
        fragmentArrayList.add(deliveryPlaceFragment);
        fragmentArrayList.add(deliveryTermsFragment);
        fragmentArrayList.add(paymentMethodsFragment);
        fragmentArrayList.add(orderedProductsFragment);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

                        switch (counter) {
                            case 0:
                                guestFieldsFragment.saveGuestInfo();
//                        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
                                if (!CheckoutSingleton.getInstance().getCartmap().containsKey("organization_group_checked")) {
                                    CheckoutSingleton.getInstance().addNewValue("organization_group_checked", "no");
                                }
//                        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
                                if (CheckoutSingleton.getInstance().getValue("guest_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("guest_last_name").length() > 2 &&
                                        CheckoutSingleton.getInstance().getValue("guest_email").length() > 7 &&
                                        CheckoutSingleton.getInstance().getValue("guest_email").contains("@") &&
                                        CheckoutSingleton.getInstance().getValue("guest_email").contains(".") &&
                                        !CheckoutSingleton.getInstance().getValue("guest_email").contains(" ") &&
                                        CheckoutSingleton.getInstance().getValue("guest_mobile").length() == 9 &&
                                        CheckoutSingleton.getInstance().getValue("guest_card_id").length() == 11) {

                                    if (CheckoutSingleton.getInstance().getValue("organization_group_checked").equals("no")) {
                                        getSupportFragmentManager()
                                                .beginTransaction()
                                                .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                .addToBackStack(null)
                                                .commit();
                                        counter++;

                                    } else if (CheckoutSingleton.getInstance().getValue("organization_group_checked").equals("yes") &&
                                            CheckoutSingleton.getInstance().getValue("guest_organisation_name").length() > 1 &&
                                            CheckoutSingleton.getInstance().getValue("guest_organisation_code").length() == 9) {
                                        getSupportFragmentManager()
                                                .beginTransaction()
                                                .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                .addToBackStack(null)
                                                .commit();
                                        counter++;
                                    } else {
                                        guestFieldsFragment.setError();
                                    }

                                } else {
                                    guestFieldsFragment.setError();
                                }
                                break;


                            case 1:
                                deliveryPlaceFragment.saveDeliveriInfo();
                                checkInvoiceRadioVisibility = deliveryPlaceFragment.getInvoiceRadioVisibility();
                                checkOrganizationVisibility = deliveryPlaceFragment.getOrganizationRadioVisibility();
                                checkSubVisibility = deliveryPlaceFragment.getSubOrganizationVisibility();
                                System.out.println(checkSubVisibility);

//                        System.out.println(CheckoutSingleton.getInstance().getValue("delivery_organization_group_checked"));
                                if (CheckoutSingleton.getInstance().getValue("delivery_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("delivery_last_name").length() > 2 &&
                                        CheckoutSingleton.getInstance().getValue("delivery_address").length() > 2 &&
                                        CheckoutSingleton.getInstance().getValue("delivery_phone").length() == 9 &&
                                        CheckoutSingleton.getInstance().getValue("delivery_card_id").length() == 11 &&
                                        !CheckoutSingleton.getInstance().getValue("spinCity").equals("0") &&
                                        !CheckoutSingleton.getInstance().getValue("spinDistrict").equals("0")) {

                                    // when invoice radio group is not visible
                                    if (!checkInvoiceRadioVisibility) {
                                        System.out.println(CheckoutSingleton.getInstance().getValue("delivery_organisation_name"));
                                        System.out.println(CheckoutSingleton.getInstance().getValue("delivery_organisation_code"));
                                        System.out.println(checkOrganizationVisibility);

                                        if (!checkOrganizationVisibility) {
                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                    .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                    .addToBackStack(null)
                                                    .commit();
                                            counter++;
                                        } else if (CheckoutSingleton.getInstance().getValue("delivery_organisation_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("delivery_organisation_code").length() == 9) {
                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                    .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                    .addToBackStack(null)
                                                    .commit();
                                            counter++;
                                        }

                                    } else {
                                        deliveryPlaceFragment.setError();
                                    }

                                    // when invoice radio group is visible
                                    if (checkInvoiceRadioVisibility) {

                                        if (!checkOrganizationVisibility &&
                                                !checkSubVisibility &&
                                                CheckoutSingleton.getInstance().getValue("invoice_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_last_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_address").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_phone").length() > 8 &&
                                                !CheckoutSingleton.getInstance().getValue("invoiceSpinCity").equals("0") &&
                                                !CheckoutSingleton.getInstance().getValue("invoiceSpinDistrict").equals("0")) {

                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                    .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                    .addToBackStack(null)
                                                    .commit();
                                            counter++;
                                        } else if (checkOrganizationVisibility &&
                                                !checkSubVisibility &&
                                                CheckoutSingleton.getInstance().getValue("delivery_organisation_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("delivery_organisation_code").length() == 9 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_last_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_address").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_phone").length() > 1 &&
                                                !CheckoutSingleton.getInstance().getValue("invoiceSpinCity").equals("0") &&
                                                !CheckoutSingleton.getInstance().getValue("invoiceSpinDistrict").equals("0")) {

                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                    .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                    .addToBackStack(null)
                                                    .commit();
                                            counter++;
                                        } else if (!checkOrganizationVisibility &&
                                                checkSubVisibility &&
                                                CheckoutSingleton.getInstance().getValue("invoice_organisation_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_organisation_code").length() == 9 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_last_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_address").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_phone").length() > 1 &&
                                                !CheckoutSingleton.getInstance().getValue("invoiceSpinCity").equals("0") &&
                                                !CheckoutSingleton.getInstance().getValue("invoiceSpinDistrict").equals("0")) {

                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                    .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                    .addToBackStack(null)
                                                    .commit();
                                            counter++;

                                        } else if (checkOrganizationVisibility &&
                                                checkSubVisibility &&
                                                CheckoutSingleton.getInstance().getValue("delivery_organisation_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("delivery_organisation_code").length() == 9 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_organisation_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_organisation_code").length() == 9 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_last_name").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_address").length() > 1 &&
                                                CheckoutSingleton.getInstance().getValue("invoice_phone").length() > 1 &&
                                                !CheckoutSingleton.getInstance().getValue("invoiceSpinCity").equals("0") &&
                                                !CheckoutSingleton.getInstance().getValue("invoiceSpinDistrict").equals("0")) {

                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                    .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                    .addToBackStack(null)
                                                    .commit();
                                            counter++;
                                        }
                                    } else {
                                        deliveryPlaceFragment.setError();
                                    }


                                } else {
                                    deliveryPlaceFragment.setError();
                                }
                                break;


                            case 2:
                                if (CheckoutSingleton.getInstance().getCartmap().containsKey("order_time_radioButton")) {
                                    if (CheckoutSingleton.getInstance().getValue("order_time_radioButton") != "") {
                                        deliveryTermsFragment.saveShippingTotal();
                                        getSupportFragmentManager()
                                                .beginTransaction()
                                                .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                                .addToBackStack(null)
                                                .commit();
                                        counter++;
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "გთხოვთ მონიშნოთ თქვენთვის სასურველი დრო !", Toast.LENGTH_SHORT).show();

                                }
                                break;


                            case 3:
                                if (CheckoutSingleton.getInstance().getCartmap().containsKey("payment_radioButton")) {
                                    paymentMethodsFragment.check();
                                    if (CheckoutSingleton.getInstance().getValue("confirm").equals("Yes")) {
                                        getSupportFragmentManager()
                                                .beginTransaction()
                                                .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                                .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                                .addToBackStack(null)
                                                .commit();
                                        counter++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "მონიშნეთ თუ თქვენ ეთანხმებით მომსახურების პირობებს !", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "გთხოვთ მონიშნოთ თქვენთვის სასურველი გადახდის მეთოდი !", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 4:

                                /////////////////

                                JSONObject jsonObject = new JSONObject();


                                JSONObject productsDetail = new JSONObject();



                                JSONObject products = new JSONObject();

//        Set<Integer> keys = SingletonTest.getInstance().getCartMap().keySet();
//        for(Integer key: keys){
//            System.out.println("key "+key);
//        }
//
//        for (int i = 0; i < keys.size(); i++) {
//            System.out.println("quon "+SingletonTest.getInstance().getCartMap().get(keys.toArray()[i]).getQuontity());
//
//        }


                                try {

                                    Set<Integer> keys = SingletonTest.getInstance().getCartMap().keySet();
                                    for (int i = 0; i < keys.size(); i++) {
                                        productsDetail.put("product_id", keys.toArray()[i]);
                                        productsDetail.put("amount",SingletonTest.getInstance().getCartMap().get(keys.toArray()[i]).getQuontity());

                                        System.out.println(productsDetail+"detail before");
                                        System.out.println(products +"products before");

                                        products.put(String.valueOf(keys.toArray()[i]),productsDetail);
                                        productsDetail.remove("i");

                                        System.out.println(products +"products after");
                                        System.out.println(productsDetail+"detail after");

                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONObject user_data_detail = new JSONObject();
                                try {
                                    user_data_detail.put("email",CheckoutSingleton.getInstance().getCartmap().get("guest_email"));
                                    user_data_detail.put("firstname",CheckoutSingleton.getInstance().getCartmap().get("guest_name"));
                                    user_data_detail.put("lastname",CheckoutSingleton.getInstance().getCartmap().get("guest_last_name"));
                                    user_data_detail.put("s_firstname",CheckoutSingleton.getInstance().getCartmap().get("delivery_name"));
                                    user_data_detail.put("s_lastname",CheckoutSingleton.getInstance().getCartmap().get("delivery_last_name"));
                                    user_data_detail.put("s_country","Georgia");
                                    user_data_detail.put("s_city",CheckoutSingleton.getInstance().getCartmap().get("spinCity"));
                                    user_data_detail.put("s_state",CheckoutSingleton.getInstance().getCartmap().get("spinDistrict"));
                                    user_data_detail.put("s_zipcode","");
                                    user_data_detail.put("s_address",CheckoutSingleton.getInstance().getCartmap().get("delivery_address"));
                                    user_data_detail.put("b_firstname",CheckoutSingleton.getInstance().getCartmap().get("invoice_name"));
                                    user_data_detail.put("b_lastname",CheckoutSingleton.getInstance().getCartmap().get("invoice_last_name"));
                                    user_data_detail.put("b_country","Georgia");
                                    user_data_detail.put("b_city",CheckoutSingleton.getInstance().getCartmap().get("invoiceSpinCity"));
                                    user_data_detail.put("b_state",CheckoutSingleton.getInstance().getCartmap().get("invoiceSpinDistrict"));
                                    user_data_detail.put("b_zipcode","");
                                    user_data_detail.put("b_address",CheckoutSingleton.getInstance().getCartmap().get("invoice_address"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                try {
                                    jsonObject.put("user_id",0);
                                    jsonObject.put("payment_id",Integer.parseInt(CheckoutSingleton.getInstance().getCartmap().get("payment_radioButton")) );
                                    jsonObject.put("shipping_id",Integer.parseInt(CheckoutSingleton.getInstance().getCartmap().get("order_time_radioButton")));
                                    jsonObject.put("products",products);
                                    jsonObject.put("user_data",user_data_detail);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                System.out.println(jsonObject+"    jsopp");

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);


                            default:
                                return;
                        }

//                        if (counter == 5) {
//                            return;
//                        }


                    } else {
                        View parentLayout = findViewById(R.id.fragment_container_id);
                        Snackbar.make(parentLayout, "არაა ინტერნეტი!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("გადატვირთვა", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        changeFragment();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                                .show();
                    }



            }
        });


    }

    public void back() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            onBackPressed();

        } else {
            View parentLayout = findViewById(R.id.fragment_container_id);
            Snackbar.make(parentLayout, "არაა ინტერნეტი!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("გადატვირთვა", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                                onBackPressed();
                            }
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                    .show();
        }

    }
    public void getJSONInfo() {
        String url = "http://soplidan.ge/api/stores/1/orders";

        JSONObject jsonObject = new JSONObject();


        JSONObject productsDetail = new JSONObject();



        JSONObject products = new JSONObject();

//        Set<Integer> keys = SingletonTest.getInstance().getCartMap().keySet();
//        for(Integer key: keys){
//            System.out.println("key "+key);
//        }
//
//        for (int i = 0; i < keys.size(); i++) {
//            System.out.println("quon "+SingletonTest.getInstance().getCartMap().get(keys.toArray()[i]).getQuontity());
//
//        }


        try {

            Set<Integer> keys = SingletonTest.getInstance().getCartMap().keySet();
            for (int i = 0; i < keys.size(); i++) {
                productsDetail.put("product_id", keys.toArray()[i]);
                productsDetail.put("amount",SingletonTest.getInstance().getCartMap().get(keys.toArray()[i]).getQuontity());

                products.put(String.valueOf(keys.toArray()[i]),productsDetail);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    productsDetail.remove("i");
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            jsonObject.put("user_id",0);
            jsonObject.put("payment_id",CheckoutSingleton.getInstance().getCartmap().get("payment_radioButton"));
            jsonObject.put("shipping_id",CheckoutSingleton.getInstance().getCartmap().get("order_time_radioButton"));
            jsonObject.put("products",products);
        } catch (JSONException e) {
            e.printStackTrace();
        }






        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (url,  new JSONObject((Map) jsonObject), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;

                        try {

                            jsonArray = response.getJSONArray("categories");
                            ArrayList<CategoryModel> categoryModels = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject curObj = jsonArray.getJSONObject(i);
                                int category_id = curObj.getInt("category_id");
                                String category = curObj.getString("category");
                                String status = curObj.getString("status");
                                int position = curObj.getInt("position");
                                int product_count = curObj.getInt("product_count");
                                if (status.equals("A")) {
                                    CategoryModel categoryModel = new CategoryModel(category_id, category, status, position, product_count, ExampleData.productImages[i]);
                                    categoryModels.add(categoryModel);
                                }

                            }




                            ArrayList<CategoryModel> foradaptermodels = new ArrayList<>();

                            HashMap<Integer,CategoryModel> hashPosition = new HashMap<>();

                            for (int i = 0; i < categoryModels.size(); i++) {
                                hashPosition.put(categoryModels.get(i).getPosition(),categoryModels.get(i));
                            }



                            for (int i = 0; i < categoryModels.size(); i++) {

                                int keyOfMax = Collections.max(
                                        hashPosition.entrySet(),
                                        new Comparator<Map.Entry<Integer,CategoryModel>>(){
                                            @Override
                                            public int compare(Map.Entry<Integer, CategoryModel> o1, Map.Entry<Integer, CategoryModel> o2) {
                                                return o1.getKey() < o2.getKey()? 1:-1;
                                            }
                                        }).getKey();


                                foradaptermodels.add(hashPosition.get(keyOfMax));

                                hashPosition.remove(keyOfMax);

                                System.out.println("position = " +keyOfMax);

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

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        progressDialog = ProgressDialog.show(this, "", "გთხოვთ დაელოდოთ...");
        requestQueue.add(jsonRequest);
    }
}