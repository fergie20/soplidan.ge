package com.example.irakli.soplidange;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView productPriceView;
    TextView minusTextView;
    TextView plusTextView;
    TextView quantityView;
    TextView sumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

//    private void calculateSum() {
//        productPriceView = (TextView) findViewById(R.id.product_price_id);
//        minusTextView = (TextView) findViewById(R.id.minus_id);
//        plusTextView = (TextView) findViewById(R.id.plus_id);
//        quantityView = (TextView) findViewById(R.id.quantity_id);
//        sumView = (TextView) findViewById(R.id.sum_id);
//
//        plusTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int productPrice;
//                int productPriceMultiplied = 0;
//
//                productPrice = Integer.parseInt((String) productPriceView.getText());
//
//                if (productPriceMultiplied == 0){
//                    productPriceMultiplied = productPrice * 2;
//                }else {
//                    productPriceMultiplied += productPrice;
//                }
//
//                sumView.setText(productPriceMultiplied);
//            }
//        });
//
//    }

}
