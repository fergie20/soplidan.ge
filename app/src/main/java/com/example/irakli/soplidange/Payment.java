package com.example.irakli.soplidange;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.irakli.soplidange.fragments.DeliveryPlaceFragment;
import com.example.irakli.soplidange.fragments.DeliveryTermsFragment;
import com.example.irakli.soplidange.fragments.GuestFieldsFragment;
import com.example.irakli.soplidange.fragments.OrderedProductsFragment;
import com.example.irakli.soplidange.fragments.PaymentMethodsFragment;
import com.example.irakli.soplidange.utils.CheckoutSingleton;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    private Button nextBtn;
    private Button prevBtn;
    private ArrayList<Fragment> fragmentArrayList;
    private int counter = 0;
    GuestFieldsFragment guestFieldsFragment;
    DeliveryPlaceFragment deliveryPlaceFragment;
    DeliveryTermsFragment deliveryTermsFragment;
    PaymentMethodsFragment paymentMethodsFragment;
    OrderedProductsFragment orderedProductsFragment;
    RelativeLayout relativeLayout;
    boolean checkInvoiceRadioVisibility = false;
    boolean checkOrganizationVisibility = false;
    boolean checkSubVisibility = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initToolbar();

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }


        guestFieldsFragment = new GuestFieldsFragment();
        deliveryPlaceFragment = new DeliveryPlaceFragment();
        deliveryTermsFragment = new DeliveryTermsFragment();
        paymentMethodsFragment = new PaymentMethodsFragment();
        orderedProductsFragment = new OrderedProductsFragment();

        relativeLayout = (RelativeLayout) findViewById(R.id.organization_fields_id);

        fragmentArrayList = new ArrayList<>();

        nextBtn = (Button) findViewById(R.id.continue_btn_id);
        prevBtn = (Button) findViewById(R.id.previous_btn_id);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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
//        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
    }

    private void initToolbar() {
        Toolbar paymentToolbar = (Toolbar) findViewById(R.id.toolbar_payment_id);
        setSupportActionBar(paymentToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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

                switch (counter) {
                    case 0:
                        guestFieldsFragment.saveGuestInfo();
//                        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
                        if (!CheckoutSingleton.getInstance().getCartmap().containsKey("organization_group_checked")) {
                            CheckoutSingleton.getInstance().addNewValue("organization_group_checked", "no");
                        }
//                        System.out.println(CheckoutSingleton.getInstance().getValue("organization_group_checked"));
                        if (CheckoutSingleton.getInstance().getValue("guest_name").length() > 1 &&
                                CheckoutSingleton.getInstance().getValue("guest_last_name").length() > 2 &&
                                CheckoutSingleton.getInstance().getValue("guest_email").length() > 7 &&
                                CheckoutSingleton.getInstance().getValue("guest_email").contains("@") &&
                                CheckoutSingleton.getInstance().getValue("guest_email").contains(".") &&
                                !CheckoutSingleton.getInstance().getValue("guest_email").contains(" ") &&
                                CheckoutSingleton.getInstance().getValue("guest_mobile").length() == 9 &&
                                CheckoutSingleton.getInstance().getValue("guest_card_id").length() == 11) {

                            if (CheckoutSingleton.getInstance().getValue("organization_group_checked").equals("no")) {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                        .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                        .addToBackStack(null)
                                        .commit();
                                counter++;

                            } else if (CheckoutSingleton.getInstance().getValue("organization_group_checked").equals("yes") &&
                                    CheckoutSingleton.getInstance().getValue("guest_organisation_name").length() > 1 &&
                                    CheckoutSingleton.getInstance().getValue("guest_organisation_code").length() == 9) {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                        .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                        .addToBackStack(null)
                                        .commit();
                                counter++;
                            } else {
                                guestFieldsFragment.setError();
                            }

                        } else {
                            guestFieldsFragment.setError();
                        }
                        break;


                    case 1:
                        deliveryPlaceFragment.saveDeliveriInfo();
                        checkInvoiceRadioVisibility = deliveryPlaceFragment.getInvoiceRadioVisibility();
                        checkOrganizationVisibility = deliveryPlaceFragment.getOrganizationRadioVisibility();
                        checkSubVisibility = deliveryPlaceFragment.getSubOrganizationVisibility();
                        System.out.println(checkSubVisibility);

//                        System.out.println(CheckoutSingleton.getInstance().getValue("delivery_organization_group_checked"));
                        if (CheckoutSingleton.getInstance().getValue("delivery_name").length() > 1 &&
                                CheckoutSingleton.getInstance().getValue("delivery_last_name").length() > 2 &&
                                CheckoutSingleton.getInstance().getValue("delivery_address").length() > 2 &&
                                CheckoutSingleton.getInstance().getValue("delivery_phone").length() == 9 &&
                                CheckoutSingleton.getInstance().getValue("delivery_card_id").length() == 11 &&
                                !CheckoutSingleton.getInstance().getValue("spinCity").equals("0") &&
                                !CheckoutSingleton.getInstance().getValue("spinDistrict").equals("0")) {

                            // when invoice radio group is not visible
                            if (!checkInvoiceRadioVisibility) {
                                System.out.println(CheckoutSingleton.getInstance().getValue("delivery_organisation_name"));
                                System.out.println(CheckoutSingleton.getInstance().getValue("delivery_organisation_code"));
                                System.out.println(checkOrganizationVisibility);

                                if (!checkOrganizationVisibility) {
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                            .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                            .addToBackStack(null)
                                            .commit();
                                    counter++;
                                } else if (CheckoutSingleton.getInstance().getValue("delivery_organisation_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("delivery_organisation_code").length() == 9) {
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                            .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                            .addToBackStack(null)
                                            .commit();
                                    counter++;
                                }

                            } else {
                                deliveryPlaceFragment.setError();
                            }

                            // when invoice radio group is visible
                            if (checkInvoiceRadioVisibility) {

                                if (!checkOrganizationVisibility &&
                                        !checkSubVisibility &&
                                        CheckoutSingleton.getInstance().getValue("invoice_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_last_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_address").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_phone").length() > 8 &&
                                        !CheckoutSingleton.getInstance().getValue("invoiceSpinCity").equals("0") &&
                                        !CheckoutSingleton.getInstance().getValue("invoiceSpinDistrict").equals("0")) {

                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                            .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                            .addToBackStack(null)
                                            .commit();
                                    counter++;
                                } else if (checkOrganizationVisibility &&
                                        !checkSubVisibility &&
                                        CheckoutSingleton.getInstance().getValue("delivery_organisation_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("delivery_organisation_code").length() == 9 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_last_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_address").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_phone").length() > 1 &&
                                        !CheckoutSingleton.getInstance().getValue("invoiceSpinCity").equals("0") &&
                                        !CheckoutSingleton.getInstance().getValue("invoiceSpinDistrict").equals("0")) {

                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                            .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                            .addToBackStack(null)
                                            .commit();
                                    counter++;
                                } else if (!checkOrganizationVisibility &&
                                        checkSubVisibility &&
                                        CheckoutSingleton.getInstance().getValue("invoice_organisation_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_organisation_code").length() == 9 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_last_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_address").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_phone").length() > 1 &&
                                        !CheckoutSingleton.getInstance().getValue("invoiceSpinCity").equals("0") &&
                                        !CheckoutSingleton.getInstance().getValue("invoiceSpinDistrict").equals("0")) {

                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                            .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                            .addToBackStack(null)
                                            .commit();
                                    counter++;

                                } else if (checkOrganizationVisibility &&
                                        checkSubVisibility &&
                                        CheckoutSingleton.getInstance().getValue("delivery_organisation_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("delivery_organisation_code").length() == 9 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_organisation_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_organisation_code").length() == 9 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_last_name").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_address").length() > 1 &&
                                        CheckoutSingleton.getInstance().getValue("invoice_phone").length() > 1 &&
                                        !CheckoutSingleton.getInstance().getValue("invoiceSpinCity").equals("0") &&
                                        !CheckoutSingleton.getInstance().getValue("invoiceSpinDistrict").equals("0")) {

                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                            .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                            .addToBackStack(null)
                                            .commit();
                                    counter++;
                                }
                            } else {
                                deliveryPlaceFragment.setError();
                            }


                        } else {
                            deliveryPlaceFragment.setError();
                        }
                        break;


                    case 2:
                        if (CheckoutSingleton.getInstance().getCartmap().containsKey("order_time_radioButton")) {
                            if (CheckoutSingleton.getInstance().getValue("order_time_radioButton") != "") {
                                deliveryTermsFragment.saveShippingTotal();
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                        .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                        .addToBackStack(null)
                                        .commit();
                                counter++;
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "გთხოვთ მონიშნოთ თქვენთვის სასურველი დრო !", Toast.LENGTH_SHORT).show();

                        }
                        break;


                    case 3:
                        if (CheckoutSingleton.getInstance().getCartmap().containsKey("payment_radioButton")) {
                            paymentMethodsFragment.check();
                            if (CheckoutSingleton.getInstance().getValue("confirm").equals("Yes")) {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_left_enter)
                                        .replace(R.id.fragment_container_id, fragmentArrayList.get(counter), fragmentArrayList.get(counter).getTag())
                                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                        .addToBackStack(null)
                                        .commit();
                                counter++;
                            } else {
                                Toast.makeText(getApplicationContext(), "მონიშნეთ თუ თქვენ ეთანხმებით მომსახურების პირობებს !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "გთხოვთ მონიშნოთ თქვენთვის სასურველი გადახდის მეთოდი !", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        return;
                }

                if (counter == 4) {
                    return;
                }
            }
        });


    }
}
