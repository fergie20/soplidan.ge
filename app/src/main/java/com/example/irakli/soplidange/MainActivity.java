package com.example.irakli.soplidange;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.soplidange.adapters.CategoriesAdapter;
import com.example.irakli.soplidange.models.CategoryModel;
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.AuthorizationParams;
import com.example.irakli.soplidange.utils.SingletonTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    private RequestQueue requestQueue;
    // LinearLayout layout;
    CategoriesAdapter myAdapter;
    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog progressDialog;
    TextView count_item;
    HashMap<Integer, ProductModel> count;
    int checkShared = 0;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initRecyclerView();

        navigationView = (NavigationView) findViewById(R.id.navigation_view_id);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    case R.id.blog_id:
                        String urlBlog = "http://soplidan.ge/%E1%83%91%E1%83%9A%E1%83%9D%E1%83%92%E1%83%98/";
                        Intent blog = new Intent(Intent.ACTION_VIEW);
                        blog.setData(Uri.parse(urlBlog));
                        startActivity(blog);
                        return true;

                    case R.id.about_us_id:
                        String urlAbout = "http://soplidan.ge/%E1%83%91%E1%83%9A%E1%83%9D%E1%83%92%E1%83%98/";
                        Intent about = new Intent(Intent.ACTION_VIEW);
                        about.setData(Uri.parse(urlAbout));
                        startActivity(about);
                        return true;


                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_id);
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };

        actionBarDrawerToggle.syncState();



        isNetworkAvailable();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addEvent = new Intent(MainActivity.this, CheckoutActivity.class);
                startActivity(addEvent);

            }
        });

//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
//        mSwipeRefreshLayout.setOnRefreshListener(this);

        getJSONInfo();
        count();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_id).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
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
                int_query.putExtra("query", query);
                startActivity(int_query);
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//                case R.id.check_list_id:
//                    Intent intent1 = new Intent(getApplicationContext(), CheckoutActivity.class);
//                    startActivity(intent1);
//                    break;
//            }
//            return true;
//    }

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
        String url = "http://soplidan.ge/api/categories?items_per_page=80";

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
        progressDialog = ProgressDialog.show(this, "", "გთხოვთ დაელოდოთ...");
        requestQueue.add(jsonRequest);
    }

    //
//    @Override
//    public void onRefresh() {
//        getJSONInfo();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);
//    }
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

    @Override
    public void onResume() {
        super.onResume();
        isNetworkAvailable();
        System.out.println("OnResume");
        count();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
        } else {
            Toast.makeText(this, "ინფორმაციის ჩამოსატვირთად საჭიროა ინტერნეტთან წვდომა", Toast.LENGTH_LONG).show();
//            NetworkDialog dialog = new NetworkDialog();
//            dialog.show(getFragmentManager(), "dialog");

        }
    }

}