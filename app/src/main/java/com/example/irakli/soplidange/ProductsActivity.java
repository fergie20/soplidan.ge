package com.example.irakli.soplidange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
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

public class ProductsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView plus;
    ImageView minus;
    TextView quantityView;
    RecyclerView gridRecycler;
    private RequestQueue requestQueue;
    int category_id;
    String category;
    String product;
    String description;
    String image_path;
    int product_id;
    int amount;
    double price;
    String status;
    String product_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        initToolbar();
        initGridRecycleView();

<<<<<<< HEAD
        Bundle bundle = getIntent().getBundleExtra("categories");
        category_id = bundle.getInt("category_id");
        category = bundle.getString("category");

        getJSONInfo();

        plus = (ImageView) findViewById(R.id.grid_plus_id);
        minus = (ImageView) findViewById(R.id.grid_minus_id);
        quantityView = (TextView) findViewById(R.id.grid_text_id);
=======
        ArrayList <ProductModel> productModels = new ArrayList<>();

        for (int i = 0; i < ProductData.id.length; i++) {
            ProductModel productModel = new ProductModel(ProductData.categories[i],ProductData.name[i],ProductData.description[i],ProductData.img[i],ProductData.id[i],ProductData.recource[i],ProductData.price[i]);
            productModels.add(productModel);
        }
>>>>>>> origin/master

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super. onBackPressed();
                return true;
            case R.id.check_list_id:
                Intent checkoutActivityIntent = new Intent(getApplicationContext() ,CheckoutActivity.class);
                startActivity(checkoutActivityIntent);
                break;
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

    private void getJSONInfo() {
        String url = "http://geolab.club/geolabwork/soplidan_ge/api/products?items_per_page=300";

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
                                if(main_category==category_id){
                                    product = curObj.getString("product");
                                    description = "seo_name";
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
                            ProductsAdapter myAdapter = new ProductsAdapter(productModels, getApplicationContext());
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
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
        requestQueue.add(jsonRequest);
    }

}
