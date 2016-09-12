package com.example.irakli.soplidange;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.adapters.ProductsAdapter;
import com.example.irakli.soplidange.dialog.ProductDetailDialog;
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.AuthorizationParams;
import com.example.irakli.soplidange.utils.SingletonTest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView plus;
    ImageView minus;
    TextView quantityView;
    RecyclerView gridRecycler;
    private RequestQueue requestQueue;
    int category_id = -1;
    Double list_discount = 0.0;
    int list_discount_prc = 0;
    String category;
    String image_path;
    String json_url = "http://soplidan.ge/api/products?items_per_page=300&q=";
    String query = "";
    TextView count_item;
    HashMap<Integer, ProductModel> count;
    List<ProductModel> productModels = new ArrayList<>();
    CollapsingToolbarLayout collapsingToolbarLayout;
    ActionBar actionBar;
    FloatingActionButton fab;
    ImageView productCategoryImage;

    public static int check = 0;

    //  private SwipeRefreshLayout mSwipeRefreshLayout;
    ProductsAdapter myAdapter;
    private ProgressDialog progressDialog;
    private Parcelable recyclerViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        gridRecycler = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
        myAdapter = new ProductsAdapter(productModels, getApplicationContext());

        initGridRecycleView();
        initToolbar();
        isNetworkAvailable();



        count();



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addEvent = new Intent(ProductsActivity.this, CheckoutActivity.class);
                addEvent.putExtra("checkboolean",1);
                startActivity(addEvent);

            }
        });
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
//        mSwipeRefreshLayout.setOnRefreshListener(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.product_collapsingToolbar_id);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        productCategoryImage = (ImageView) findViewById(R.id.product_category_image);


        Bundle bundle = getIntent().getBundleExtra("categories");
        if (bundle != null) {
            category_id = bundle.getInt("category_id");
            category = bundle.getString("category");
            actionBar.setTitle(category);




            final float scale = getResources().getDisplayMetrics().density;
            int width  = (int) (250 * scale);
            int height = (int) (140 * scale);

            collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
            final Typeface tf = Typeface.createFromAsset(this.getAssets(), "BPG_Anna.ttf");
            collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
            collapsingToolbarLayout.setExpandedTitleTypeface(tf);





            Window window = this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            switch(category_id) {
                case 34:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.sunflowerToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.sunflowerToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.sunflowerToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_sunflower);

                    break;
                case 33:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.fokiToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.fokiToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.fokiToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_fokismonasteri);

                    break;
                case 32:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.bioToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.bioToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.bioToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_biobostneuli);

                    break;
                case 30:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.puriToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.puriToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.puriToolbar)));
                    productCategoryImage.setImageResource(R.drawable.pur);

                    break;
                case 29:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.saxlebiToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.saxlebiToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.saxlebiToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_mwsaxli);

                    break;
                case 28:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.chaiToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.chaiToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.chaiToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_chai);

                    break;
                case 27:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.kalataToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.kalataToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.kalataToolbar)));
                    break;
                case 26:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.sunelebiToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.sunelebiToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.sunelebiToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_saweblebi);

                    break;
                case 24:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.sxvadasxvaToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.sxvadasxvaToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.sxvadasxvaToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_sxvadasxva);

                    break;
                case 23:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.rdzeToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.rdzeToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.rdzeToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_kvercxi);

                    break;
                case 21:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.xiliToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.xiliToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.xiliToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_xili);

                    break;
                case 20:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.mwniliToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.mwniliToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.mwniliToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cow_mwnili);

                    break;
                case 12:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.bostneuliToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.bostneuliToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.bostneuliToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_bostneuli);

                    break;
                case 11:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.sasmeliToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.sasmeliToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.sasmeliToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_sasmeli);

                    break;
                case 9:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.zetiToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.zetiToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.zetiToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_dzmari);

                    break;
                case 6:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.nugbariToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.nugbariToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.nugbariToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_nugbari);

                    break;
                case 5:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.xorciToolbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.xorciToolbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.xorciToolbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_xorceuli);

                    break;

                default:
//                    toolbar.setBackgroundColor(this.getResources().getColor(R.color.xiliStatusbar));
                    collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.xiliStatusbar));
                    fab.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.xiliStatusbar)));
                    productCategoryImage.setImageResource(R.drawable.cov_xili);

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                switch(category_id) {
                    case 34:
                        window.setStatusBarColor(this.getResources().getColor(R.color.sunflowerStatusBar));
                        break;
                    case 33:
                        window.setStatusBarColor(this.getResources().getColor(R.color.fokiStatusbar));
                        break;
                    case 32:
                        window.setStatusBarColor(this.getResources().getColor(R.color.bioStatusbar));
                        break;
                    case 30:
                        window.setStatusBarColor(this.getResources().getColor(R.color.puriStatusBar));
                        break;
                    case 29:
                        window.setStatusBarColor(this.getResources().getColor(R.color.saxlebiStatusbar));
                        break;
                    case 28:
                        window.setStatusBarColor(this.getResources().getColor(R.color.chaiStatusbar));
                        break;
                    case 27:
                        window.setStatusBarColor(this.getResources().getColor(R.color.kalataStatusBar));
                        break;
                    case 26:
                        window.setStatusBarColor(this.getResources().getColor(R.color.sunelebiStatusbar));
                        break;
                    case 24:
                        window.setStatusBarColor(this.getResources().getColor(R.color.sxvadasxvaStatusbar));
                        break;
                    case 23:
                        window.setStatusBarColor(this.getResources().getColor(R.color.rdzeStatusBar));
                        break;
                    case 21:
                        window.setStatusBarColor(this.getResources().getColor(R.color.xiliStatusbar));
                        break;
                    case 20:
                        window.setStatusBarColor(this.getResources().getColor(R.color.mwniliStatusbar));
                        break;
                    case 12:
                        window.setStatusBarColor(this.getResources().getColor(R.color.bostneuliStatusBar));
                        break;
                    case 11:
                        window.setStatusBarColor(this.getResources().getColor(R.color.sasmeliStatusbar));
                        break;
                    case 9:
                        window.setStatusBarColor(this.getResources().getColor(R.color.zetiStatusbar));
                        break;
                    case 6:
                        window.setStatusBarColor(this.getResources().getColor(R.color.nugbariStatusBar));
                        break;
                    case 5:
                        window.setStatusBarColor(this.getResources().getColor(R.color.xorciStatusbar));
                        break;

                    default:
                        window.setStatusBarColor(this.getResources().getColor(R.color.xiliToolbar));
                }
            }

            System.out.println(category);
        }
        Intent intent = getIntent();
        if (intent != null) {
            query = intent.getStringExtra("query");
        }

        //onRefresh();

        if (category_id == -1) {
            getJSONInfo(json_url + query);
        } else {
            getJSONInfo(json_url);
        }


        plus = (ImageView) findViewById(R.id.grid_plus_id);
        minus = (ImageView) findViewById(R.id.grid_minus_id);
        quantityView = (TextView) findViewById(R.id.grid_text_id);
//        new Task().execute();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_id)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {


                return false;
            }

            public boolean onQueryTextSubmit(String query) {

                Intent int_query = new Intent(getApplicationContext(), ProductsActivity.class);
                int_query.putExtra("query", query);
                startActivity(int_query);
                finish();
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
//            case R.id.check_list_id:
//                Intent checkoutActivityIntent = new Intent(getApplicationContext() ,CheckoutActivity.class);
//                startActivity(checkoutActivityIntent);
//                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void initGridRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public void getJSONInfo(String url) {


        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = response.getJSONArray("products");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject curObj = jsonArray.getJSONObject(i);
                                int main_category = curObj.getInt("main_category");
                                if (category_id == -1) {

                                    String product = curObj.getString("product");
                                    String description = "some description blabla blaa.sndadncadbcjadbcakhbchkabdcf.";
                                    if (curObj.has("main_pair")) {
                                        JSONObject main_pair = curObj.getJSONObject("main_pair");
                                        if (main_pair.has("detailed")) {
                                            JSONObject detaild = main_pair.getJSONObject("detailed");
                                            image_path = detaild.getString("image_path");
                                        } else {
                                            JSONObject detaild = main_pair.getJSONObject("icon");
                                            image_path = detaild.getString("image_path");
                                        }
                                    } else {
                                        image_path = "http://soplidan.ge/images/detailed/1/shavi_chai.png?t=1468584369";
                                    }
                                    int product_id = curObj.getInt("product_id");
                                    int amount = curObj.getInt("amount");
                                    Double price = curObj.getDouble("list_price");
                                    String status = curObj.getString("status");
                                    String product_code = curObj.getString("product_code");
                                    Double base_price = curObj.getDouble("base_price");
                                    if (curObj.has("list_discount")) {
                                        list_discount = curObj.getDouble("list_discount");
                                        list_discount_prc = curObj.getInt("list_discount_prc");
                                    }

                                    ProductModel productModel = new ProductModel(category, product, description, image_path, product_id, amount, price, status, product_code, base_price, list_discount, list_discount_prc);
                                    productModels.add(productModel);
                                    if(productModels.size()==0){
                                        Toast.makeText(getApplicationContext(), "არაფერი მოიძებნა", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (category_id == main_category) {
                                    String product = curObj.getString("product");
                                    String description = curObj.getString("product");

                                    if (curObj.has("main_pair")) {
                                        JSONObject main_pair = curObj.getJSONObject("main_pair");
                                        if (main_pair.has("detailed")) {
                                            JSONObject detaild = main_pair.getJSONObject("detailed");
                                            image_path = detaild.getString("image_path");
                                        } else {
                                            JSONObject detaild = main_pair.getJSONObject("icon");
                                            image_path = detaild.getString("image_path");
                                        }
                                    } else {
                                        image_path = "http://soplidan.ge/images/detailed/1/shavi_chai.png?t=1468584369";
                                    }

                                    int product_id = curObj.getInt("product_id");
                                    int amount = curObj.getInt("amount");
                                    Double price = curObj.getDouble("list_price");
                                    String status = curObj.getString("status");
                                    String product_code = curObj.getString("product_code");
                                    Double base_price = curObj.getDouble("base_price");


                                    if (curObj.has("list_discount")) {
                                        list_discount = curObj.getDouble("list_discount");
                                        list_discount_prc = curObj.getInt("list_discount_prc");
                                    }
                                    ProductModel productModel = new ProductModel(category, product, description, image_path, product_id, amount, price, status, product_code, base_price, list_discount, list_discount_prc);
                                    productModels.add(productModel);
                                }


                            }

                            myAdapter.setMyClickListener(new ProductsAdapter.MyClickListener() {

                                @Override
                                public void onClick(ProductModel model) {
                                    ProductDetailDialog dialog = new ProductDetailDialog();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("model", model);
                                    dialog.setArguments(bundle);
                                    dialog.show(getFragmentManager(), "dialog");

                                }
                            });
                            myAdapter.setMycountListener(new ProductsAdapter.MyCountListener() {
                                @Override
                                public void countClick() {
                                    count();
                                }
                            });
                            gridRecycler = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
                            gridRecycler.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
                            recyclerViewState = gridRecycler.getLayoutManager().onSaveInstanceState();

                            gridRecycler.getLayoutManager().onRestoreInstanceState(recyclerViewState);

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
//    @Override
//    public void onRefresh() {
//        if(category_id==-1){
//            getJSONInfo(json_url+query);
//        }else{
//            getJSONInfo(json_url);
//        }
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);
//    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("OnResume");

        updateListView();
        isNetworkAvailable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("OnPause");
    }


    @Override
    protected void onStop() {
        super.onStop();

        saveShared();

    }


    public void updateListView() {



        if (check == 1){
            gridRecycler.setAdapter(myAdapter);
            check = 0;
        }else{
            myAdapter.notifyDataSetChanged();
        }

        count();
    }

    public void count() {
        count_item = (TextView) findViewById(R.id.count_item);
        count = SingletonTest.getInstance().getCartMap();
        int count_sum = 0;
        ArrayList<ProductModel> cartMap = new ArrayList<>();

        Iterator myVeryOwnIterator = count.keySet().iterator();
        if (count.size() > 0) {
            while (myVeryOwnIterator.hasNext()) {
                int key = (int) myVeryOwnIterator.next();
                ProductModel value = count.get(key);
                cartMap.add(value);
            }


            for (int i = 0; i < cartMap.size(); i++) {
                count_sum += cartMap.get(i).getQuontity();
            }
            count_item.setText(count_sum + "");
        } else {
            count_item.setText(count_sum + "");
        }
    }

    public void isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
        } else {
            Toast.makeText(this, "ინფორმაციის ჩამოსატვირთად საჭიროა ინტერნეტ კავშირი", Toast.LENGTH_SHORT).show();

        }
    }

    public void saveShared (){

        SharedPreferences mPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);



        HashMap<Integer,ProductModel> cartMap;

        cartMap = SingletonTest.getInstance().getCartMap();

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartMap);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();
    }
    private void makeCollapsingToolbarLayoutLooksGood(CollapsingToolbarLayout collapsingToolbarLayout) {
        try {
            final Field field = collapsingToolbarLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            field.setAccessible(true);

            final Object object = field.get(collapsingToolbarLayout);
            final Field tpf = object.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            ((TextPaint) tpf.get(object)).setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf"));
            ((TextPaint) tpf.get(object)).setColor(getResources().getColor(R.color.colorAccent));
        } catch (Exception ignored) {
        }
    }
}
