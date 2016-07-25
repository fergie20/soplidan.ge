package com.example.irakli.soplidange;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irakli.soplidange.adapters.CheckoutAdapter;
import com.example.irakli.soplidange.models.ProductModel;

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
    String checkQuantity;
    ProductModel model;
    RecyclerView recyclerView;
    HashMap<Integer,ProductModel> cartMap;


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

        find();

        final ArrayList<ProductModel> cartArray = new ArrayList<>();
        for (Integer integer : cartMap.keySet()) {
            cartArray.add(cartMap.get(integer));
        }

        CheckoutAdapter myAdapter = new CheckoutAdapter(cartArray, getApplicationContext());
        recyclerView.setAdapter(myAdapter);


        totalPrice = getTotalPrice(cartArray);


            myAdapter.setMyPriceUpdateListener(new CheckoutAdapter.MyClickListener() {

                @Override
                public void onClick(double price) {
                    NumberFormat nf = NumberFormat.getInstance(); // get instance
                    nf.setMaximumFractionDigits(3); // set decimal places
                    String s = nf.format(totalPrice + price);
                    sumView.setText((s + "GEL"));
                    totalPrice = totalPrice + price;
                }
            });
        NumberFormat nf = NumberFormat.getInstance(); // get instance
        nf.setMaximumFractionDigits(3); // set decimal places
        String s = nf.format(totalPrice );
        sumView.setText((s + "GEL"));
    }

    private double totalPrice;


    private double getTotalPrice(ArrayList<ProductModel> products){
        double price = 0;
        for (int i = 0; i < products.size(); i++) {
            price += products.get(i).getPrice() * products.get(i).getQuontity();

        }

        return price;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem checkListIcon = menu.findItem(R.id.check_list_id);
        checkListIcon.setVisible(false);

        return true;
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super. onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }


//    private void calculateSum( ) {
//        find();
//        String input = String.valueOf(quantityView.getText());
//        double productPrice = Double.parseDouble((String) productPriceView.getText());
//        if (!input.isEmpty()) {
//            int quontity = Integer.parseInt(input);
//
//            double sum = productPrice * quontity;
//            NumberFormat nf = NumberFormat.getInstance(); // get instance
//            nf.setMaximumFractionDigits(3); // set decimal places
//            String s = nf.format(sum);
//            sumView.setText(s + "");
//        } else {
//            quantityView.setText("0");
//        }
//
//        quantityView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                int productPrice =  Integer.parseInt((String) productPriceView.getText());
////                int quontity = Integer.parseInt((String) charSequence);
////                int sum = productPrice*quontity;
////                sumView.setText(sum+"");
////                Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_LONG).show();
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String input = String.valueOf(editable);
//                double productPrice = Double.parseDouble((String) productPriceView.getText());
//                if (editable != null && !input.isEmpty()) {
//                    int quontity = Integer.parseInt(input);
//
//                    double sum = productPrice * quontity;
//                    NumberFormat nf = NumberFormat.getInstance(); // get instance
//                    nf.setMaximumFractionDigits(3); // set decimal places
//                    String s = nf.format(sum);
//                    sumView.setText(s + "");
//                } else {
//                    quantityView.setText("0");
//                }
//                //Toast.makeText(getApplicationContext(), editable, Toast.LENGTH_LONG).show();
//            }
//        });
//
//        plusTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int productPrice;
//                int quantity;
//                checkQuantity = quantityView.getText().toString();
//                quantity = Integer.parseInt((String) checkQuantity);
//
//                quantity++;
//                // Toast.makeText(getApplicationContext(), "daechira" + checkQuantity, Toast.LENGTH_LONG).show();
//
//
//                quantityView.setText(quantity + "");
//
//
//            }
//        });
//        minusTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int productPrice;
//                int quantity;
//                checkQuantity = quantityView.getText().toString();
//                quantity = Integer.parseInt((String) checkQuantity);
//
//                if (quantity >= 2) {
//                    quantity--;
//
//                    quantityView.setText(quantity + "");
//
//
//                } else {
//                    sumView.setText("0");
//                    quantityView.setText("0");
//
//                }
//
//
//            }
//        });
//    }
    private void find(){
        productPriceView = (TextView) findViewById(R.id.product_price_id);
        minusTextView = (ImageView) findViewById(R.id.minus_id);
        plusTextView = (ImageView) findViewById(R.id.plus_id);
        quantityView = (TextView) findViewById(R.id.quantity_id);
        sumView = (TextView) findViewById(R.id.sum_id);
    }
    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.checkout_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
