package com.example.irakli.soplidange;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.irakli.soplidange.fragments.DeliveryPlaceFragment;
import com.example.irakli.soplidange.fragments.DeliveryTermsFragment;
import com.example.irakli.soplidange.fragments.GuestFieldsFragment;
import com.example.irakli.soplidange.fragments.OrderedProductsFragment;
import com.example.irakli.soplidange.fragments.PaymentMethodsFragment;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    private Button nextBtn;
    private ArrayList<Fragment> fragmentArrayList;
    private int counter = 0;
    GuestFieldsFragment guestFieldsFragment;
    DeliveryPlaceFragment deliveryPlaceFragment;
    DeliveryTermsFragment deliveryTermsFragment;
    PaymentMethodsFragment paymentMethodsFragment;
    OrderedProductsFragment orderedProductsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initToolbar();

        guestFieldsFragment = new GuestFieldsFragment();
        deliveryPlaceFragment = new DeliveryPlaceFragment();
        deliveryTermsFragment = new DeliveryTermsFragment();
        paymentMethodsFragment = new PaymentMethodsFragment();
        orderedProductsFragment = new OrderedProductsFragment();

        fragmentArrayList = new ArrayList<>();

        nextBtn = (Button) findViewById(R.id.continue_btn_id);

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

    private void initToolbar(){
        Toolbar paymentToolbar = (Toolbar) findViewById(R.id.toolbar_payment_id);
        setSupportActionBar(paymentToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
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
                if (counter == 4) {
                    return;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit();

                counter++;
            }
        });
    }

}
