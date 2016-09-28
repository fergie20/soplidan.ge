package com.example.irakli.soplidange;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
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
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.AuthorizationParams;
import com.example.irakli.soplidange.utils.SingletonTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton categoriesFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusBar();

        if (checkShared == 0) {
            retryShared();
        }

        initToolbar();
        initRecyclerView();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_id);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setContentScrimColor(this.getResources().getColor(R.color.colorPrimary));


        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "BPG_Anna.ttf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
        collapsingToolbarLayout.setTitle("");


        navigationView = (NavigationView) findViewById(R.id.navigation_view_id);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.info:
                        Intent info = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(info);
                        return true;
                    case R.id.blog:
                        String urlAbout = "http://soplidan.ge/%E1%83%91%E1%83%9A%E1%83%9D%E1%83%92%E1%83%98/";
                        Intent about = new Intent(Intent.ACTION_VIEW);
                        about.setData(Uri.parse(urlAbout));
                        startActivity(about);
                        return true;
                    case R.id.fb:
                        String fbUrl = "https://www.facebook.com/soplidan/";
                        Intent fbAbout = new Intent(Intent.ACTION_VIEW);
                        fbAbout.setData(Uri.parse(fbUrl));
                        startActivity(fbAbout);
                        return true;
                    case R.id.web:
                        String webUrl = "http://soplidan.ge/";
                        Intent webintent = new Intent(Intent.ACTION_VIEW);
                        webintent.setData(Uri.parse(webUrl));
                        startActivity(webintent);
                        return true;
                    case R.id.email:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "info@soplidan.ge", null));

                        startActivity(Intent.createChooser(emailIntent, "გააგზავნეთ ელ.ფოსტა"));
                        return true;
                    case R.id.phone:
                        Intent phone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+995557470441"));
                        startActivity(phone);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {


            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
//                                burger();


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                    statusbar();
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);


        actionBarDrawerToggle.syncState();


        isNetworkAvailable();

        categoriesFab = (FloatingActionButton) findViewById(R.id.fab);
        categoriesFab.setOnClickListener(new View.OnClickListener() {
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
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView soplidan = (TextView) findViewById(R.id.soplidan_id);
                soplidan.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                TextView soplidan = (TextView) findViewById(R.id.soplidan_id);
                soplidan.setVisibility(View.VISIBLE);
                return false;
            }
        });


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


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }


    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
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
                                if (status.equals("A")) {
                                    CategoryModel categoryModel = new CategoryModel(category_id, category, status, position, product_count, ExampleData.productImages[i]);
                                    categoryModels.add(categoryModel);
                                }

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

    public void count() {
        count_item = (TextView) findViewById(R.id.count_item);
        count = SingletonTest.getInstance().getCartMap();
        int count_sum = 0;
        ArrayList<ProductModel> cartMap = new ArrayList<>();

        if (SingletonTest.getInstance().getNumberOfItems() == 0) {
            categoriesFab.hide();
            categoriesFab.animate().translationY(categoriesFab.getHeight() + 16).setInterpolator(new AccelerateInterpolator(2)).start();

        } else {
            categoriesFab.show();
            categoriesFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        categoriesFab.show();
                        categoriesFab.animate().setStartDelay(300);
                    }

                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 || dy < 0 && categoriesFab.isShown()) {
                        categoriesFab.animate().setStartDelay(0);
                        categoriesFab.hide();
                    }

                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }

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
        saveShared();
    }

    public void isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
        } else {
            Toast.makeText(this, "ინფორმაციის ჩამოსატვირთად საჭიროა ინტერნეტთან წვდომა", Toast.LENGTH_SHORT).show();
//            NetworkDialog dialog = new NetworkDialog();
//            dialog.show(getFragmentManager(), "dialog");

        }
    }

    public void saveShared() {

        SharedPreferences mPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);


        HashMap<Integer, ProductModel> cartMap;

        cartMap = SingletonTest.getInstance().getCartMap();

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartMap);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();
    }

    public void retryShared() {
        SharedPreferences mPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);


        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");

        Type typeOfHashMap = new TypeToken<HashMap<Integer, ProductModel>>() {
        }.getType();
        HashMap<Integer, ProductModel> newMap = gson.fromJson(json, typeOfHashMap);
        SingletonTest.getInstance().setCart(newMap);
    }

    public void statusBar() {
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }


}