package com.example.irakli.soplidange;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.ExampleData.ExampleData;
import com.example.irakli.soplidange.adapters.CategoriesAdapter;
import com.example.irakli.soplidange.models.CategoryModel;
import com.example.irakli.soplidange.utils.AuthorizationParams;
import com.squareup.picasso.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<String> categoriesList;
    private RequestQueue requestQueue;
   // LinearLayout layout;
    CategoriesAdapter myAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initRecyclerView();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        getJSONInfo();




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

//                Intent int_query = new Intent(getApplicationContext(), ProductsActivity.class);
//                int_query.putExtra("query",newText);
//                startActivity(int_query);

                return false;
            }

            public boolean onQueryTextSubmit(String query) {

                Intent int_query = new Intent(getApplicationContext(), ProductsActivity.class);
                int_query.putExtra("query",query);
                startActivity(int_query);
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case R.id.check_list_id:
                    Intent intent1 = new Intent(getApplicationContext(), CheckoutActivity.class);
                    startActivity(intent1);
                    break;
            }
            return true;
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getJSONInfo() {
        String url = "http://geolab.club/geolabwork/soplidan_ge/api/categories?items_per_page=80";

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
                                CategoryModel categoryModel = new CategoryModel(category_id, category, status, position, product_count);
                                categoryModels.add(categoryModel);
                            }
                            myAdapter = new CategoriesAdapter(categoryModels, getApplicationContext());
                            recyclerView.setAdapter(myAdapter);
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
        getJSONInfo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

}