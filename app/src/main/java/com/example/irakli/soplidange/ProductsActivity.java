package com.example.irakli.soplidange;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.ExampleData.ProductData;
import com.example.irakli.soplidange.adapters.CategoriesAdapter;
import com.example.irakli.soplidange.adapters.ProductsAdapter;
import com.example.irakli.soplidange.dialog.ProductDetailDialog;
import com.example.irakli.soplidange.models.CategoryModel;
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.AuthorizationParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    Toolbar toolbar;
    ImageView plus;
    ImageView minus;
    TextView quantityView;
    RecyclerView gridRecycler;
    private RequestQueue requestQueue;
    int category_id = -1;
    String category;
    String product;
    String description;
    String image_path;
    int product_id;
    int amount;
    double price;
    String status;
    String product_code;
    String json_url = "http://geolab.club/geolabwork/soplidan_ge/api/products?items_per_page=300&q=";
    String query="";
    TextView count_item;
    HashMap<Integer,ProductModel> count;



    private SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout layout;
    ProductsAdapter myAdapter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        gridRecycler = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
        initToolbar();
        initGridRecycleView();
        count = SingletonTest.getInstance().getCartMap();
        count_item = (TextView) findViewById(R.id.count_item);
        count_item.setText(count.size() + "");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addEvent = new Intent(ProductsActivity.this, CheckoutActivity.class);
                startActivity(addEvent);

            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(this);



        Bundle bundle = getIntent().getBundleExtra("categories");
        if(bundle!=null) {
            category_id = bundle.getInt("category_id");
            category = bundle.getString("category");


        }
        Intent intent = getIntent();
        if(intent!=null) {
            query = intent.getStringExtra("query");
        }

        onRefresh();




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
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {


                return false;
            }

            public boolean onQueryTextSubmit(String query) {

                Intent int_query = new Intent(getApplicationContext(), ProductsActivity.class);
                int_query.putExtra("query",query);
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
                super. onBackPressed();
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
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
         actionBar.setTitle("");
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
                            ArrayList<ProductModel> productModels = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject curObj = jsonArray.getJSONObject(i);
                                int main_category = curObj.getInt("main_category");
                                if(category_id == -1 ){

                                    product = curObj.getString("product");
                                    description = "some description blabla blaa.sndadncadbcjadbcakhbchkabdcf.";
                                    JSONObject main_pair = curObj.getJSONObject("main_pair");
                                    JSONObject detaild = main_pair.getJSONObject("detailed");
                                    image_path = detaild.getString("image_path");
                                    product_id = curObj.getInt("product_id");
                                    amount = curObj.getInt("amount");
                                    price = curObj.getDouble("price");
                                    status = curObj.getString("status");
                                    product_code = curObj.getString("product_code");

                                    ProductModel productModel = new ProductModel( category, product, description, image_path, product_id,amount,price,status,product_code);
                                    productModels.add(productModel);
                               }else if(category_id==main_category){
                                    product = curObj.getString("product");
                                    description = curObj.getString("product");
                                    JSONObject main_pair = curObj.getJSONObject("main_pair");
                                    JSONObject detaild = main_pair.getJSONObject("detailed");
                                    image_path = detaild.getString("image_path");
                                    product_id = curObj.getInt("product_id");
                                    amount = curObj.getInt("amount");
                                    price = curObj.getDouble("price");
                                    status = curObj.getString("status");
                                    product_code = curObj.getString("product_code");

                                    ProductModel productModel = new ProductModel( category, product, description, image_path, product_id,amount,price,status,product_code);
                                    productModels.add(productModel);
                                }


                            }
                            myAdapter = new ProductsAdapter(productModels, getApplicationContext());
                            myAdapter.setMyClickListener(new ProductsAdapter.MyClickListener() {
                                @Override
                                public void onClick(ProductModel model) {
                                    ProductDetailDialog dialog = new ProductDetailDialog();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("model",model);
                                    dialog.setArguments(bundle);
                                    dialog.show(getFragmentManager(),"dialog");
                                }
                            });
                            gridRecycler = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
                            gridRecycler.setAdapter(myAdapter);

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

        progressDialog = ProgressDialog.show(this, "", "Gtxovt Daelodot");
        requestQueue.add(jsonRequest);
    }
    @Override
    public void onRefresh() {
        if(category_id==-1){
            getJSONInfo(json_url+query);
        }else{
            getJSONInfo(json_url);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("OnResume");

        updateListView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("OnPause");
    }

    public void updateListView(){

        if(gridRecycler.getAdapter() != null)
            gridRecycler.getAdapter().notifyDataSetChanged();
    }
}
