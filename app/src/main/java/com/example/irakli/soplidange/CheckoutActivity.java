package com.example.irakli.soplidange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irakli.soplidange.adapters.CheckoutAdapter;
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.SingletonTest;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckoutActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView productPriceView;
    ImageView minusTextView;
    ImageView plusTextView;
    TextView quantityView;
    TextView sumView;
    ProductModel model;
    RecyclerView recyclerView;
    HashMap<Integer, ProductModel> cartMap;
    Button payment;
    CheckoutAdapter myAdapter;
    final ArrayList<ProductModel> cartArray = new ArrayList<>();
    int chek = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        cartMap = SingletonTest.getInstance().getCartMap();

        initToolbar();
        initRecyclerView();
        TextView name = (TextView) findViewById(R.id.checkout_name_id);
        ImageView image = (ImageView) findViewById(R.id.checkout_image_id);


        initToolbar();
        toolbar.setTitle("კალათა");

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.checkout));
        }

        find();
        chek = getIntent().getIntExtra("checkboolean", 0);

        for (Integer integer : cartMap.keySet()) {
            cartArray.add(cartMap.get(integer));
        }
        if (cartArray.size() == 0) {
            Toast.makeText(this, "თქვენი კალათა ცარიელია", Toast.LENGTH_SHORT).show();
        }

         myAdapter = new CheckoutAdapter(cartArray, getApplicationContext());
        recyclerView.setAdapter(myAdapter);


        totalPrice = getTotalPrice(cartArray);

        payment = (Button) findViewById(R.id.checkout_payment_id);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, Payment.class);
                startActivity(intent);
            }
        });


        myAdapter.setMyPriceUpdateListener(new CheckoutAdapter.MyClickListener() {

            @Override
            public void onClick(double price) {
                NumberFormat nf = NumberFormat.getInstance(); // get instance
                nf.setMaximumFractionDigits(3); // set decimal places
                String s = nf.format(totalPrice + price);
                sumView.setText((s + " ¢"));
                totalPrice = totalPrice + price;
            }
        });
        NumberFormat nf = NumberFormat.getInstance(); // get instance
        nf.setMaximumFractionDigits(3); // set decimal places
        String s = nf.format(totalPrice);
        sumView.setText((s + " ¢"));
    }

    private double totalPrice;


    private double getTotalPrice(ArrayList<ProductModel> products) {
        double price = 0;
        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getBase_price() > 0) {
                price += products.get(i).getBase_price() * products.get(i).getQuontity();
            } else {
                price += products.get(i).getList_price() * products.get(i).getQuontity();
            }

        }

        return price;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_menu, menu);

//        MenuItem checkListIcon = menu.findItem(R.id.check_list_id);
//        checkListIcon.setVisible(false);

        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        saveShared();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ProductsActivity.check = chek;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                ProductsActivity.check = chek;
                super.onBackPressed();

                return true;
            case R.id.delete_id:
                cartMap.clear();
                SingletonTest.getInstance().getCartMap().clear();
                cartArray.clear();
                myAdapter = new CheckoutAdapter(cartArray, getApplicationContext());
                recyclerView.setAdapter(myAdapter);
                Toast.makeText(this, "თქვენი კალათა დაცარიელდა", Toast.LENGTH_SHORT).show();
                sumView.setText(0+" ¢");
                saveShared();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void find() {
        productPriceView = (TextView) findViewById(R.id.product_price_id);
        minusTextView = (ImageView) findViewById(R.id.minus_id);
        plusTextView = (ImageView) findViewById(R.id.plus_id);
        quantityView = (TextView) findViewById(R.id.quantity_id);
        sumView = (TextView) findViewById(R.id.sum_id);
        sumView.setTypeface(typeface());
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.checkout_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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
    public Typeface typeface(){

        Typeface custom_font = Typeface.createFromAsset(this.getAssets(),  "BPG_GEL_Excelsior_Caps.ttf");

        return custom_font;
    }
}
