package com.example.irakli.soplidange;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.irakli.soplidange.utils.CheckoutSingleton;

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
    boolean checkButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initToolbar();

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.payment));
        }



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
                    if(counter == 0) {
                        guestFieldsFragment.saveGuestInfo();
                      if(CheckoutSingleton.getInstance().getValue("guest_name").length() > 0 &&
                                CheckoutSingleton.getInstance().getValue("guest_last_name").length() >0 &&
                                CheckoutSingleton.getInstance().getValue("guest_email").length() >0 &&
                                CheckoutSingleton.getInstance().getValue("guest_phone").length() >0 &&
                                CheckoutSingleton.getInstance().getValue("guest_card_id").length() >0){
                          getSupportFragmentManager()
                                  .beginTransaction()
                                  .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                  .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                  .addToBackStack(null)
                                  .commit();

                          counter++;
                      }else{
                          guestFieldsFragment.setError();}
                      }
                    if(counter == 2) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                .addToBackStack(null)
                                .commit();

                           if( CheckoutSingleton.getInstance().getValue("delivery_name").length() >0 &&
                            CheckoutSingleton.getInstance().getValue("delivery_last_name").length() >0 &&
                            CheckoutSingleton.getInstance().getValue("delivery_address").length() >0 &&
                            CheckoutSingleton.getInstance().getValue("delivery_phone").length() >0 &&
                            CheckoutSingleton.getInstance().getValue("delivery_card_id").length() >0 ){
                               getSupportFragmentManager()
                                       .beginTransaction()
                                       .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                       .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                       .addToBackStack(null)
                                       .commit();
                           }else{
                               deliveryPlaceFragment.setError();
                           }

                        }


                    if (counter == 4) {
                        return;
                      }


//
//                    if (CheckoutSingleton.getInstance().getValue("guest_name").length() > 0 &&
//                            CheckoutSingleton.getInstance().getValue("guest_last_name").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("guest_email").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("guest_phone").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("guest_card_id").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("delivery_name").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("delivery_last_name").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("delivery_address").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("delivery_phone").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("delivery_card_id").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("invoice_name").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("invoice_last_name").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("invoice_address").length() >0 &&
//                            CheckoutSingleton.getInstance().getValue("invoice_phone").length() >0)
//
//                    {





                }
            });


        }
    }
