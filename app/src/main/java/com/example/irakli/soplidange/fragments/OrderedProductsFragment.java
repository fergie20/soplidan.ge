package com.example.irakli.soplidange.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irakli.soplidange.AboutUs;
import com.example.irakli.soplidange.MainActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.utils.CheckoutSingleton;

import java.text.DecimalFormat;

/**
 * Created by floyd on 9/9/2016.
 */
public class OrderedProductsFragment extends Fragment {
    TextView shippingCost, totalPrice, promoCodePrice, salePromo, theWholeOf;
    EditText promoCode;
    Button check_promo_button;
    Double totalSum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orderedProductsFragment = inflater.inflate(R.layout.ordered_fragment_layout, container, false);

        shippingCost = (TextView) orderedProductsFragment.findViewById(R.id.shipping_cost_id);
        totalPrice = (TextView) orderedProductsFragment.findViewById(R.id.total_price_id);
        check_promo_button = (Button) orderedProductsFragment.findViewById(R.id.check_promo_button);
        promoCode = (EditText) orderedProductsFragment.findViewById(R.id.promo_code);
        promoCodePrice = (TextView) orderedProductsFragment.findViewById(R.id.promo_price_id);
        salePromo = (TextView) orderedProductsFragment.findViewById(R.id.sale_id);
        theWholeOf = (TextView) orderedProductsFragment.findViewById(R.id.totalSum_id);

        totalSum = Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price"));


        shippingCost.setTypeface(typeface());
        totalPrice.setTypeface(typeface());
        promoCodePrice.setTypeface(typeface());

        shippingCost.setText(CheckoutSingleton.getInstance().getValue("shipping_price")+" ¢");
        totalPrice.setText(totalSum+" ¢",TextView.BufferType.SPANNABLE);


        promoCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        promoCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    check_promo_button.setVisibility(View.VISIBLE);
                }else{

                    totalPrice.setText(CheckoutSingleton.getInstance().getValue("total_price")+" ¢");
                    totalSum = Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price"));
                    check_promo_button.setVisibility(View.GONE);
                    promoCodePrice.setVisibility(View.GONE);
                    salePromo.setVisibility(View.GONE);
                    theWholeOf.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                check_promo_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        switch (editable.toString()) {

                            case "TBC10":

                                if(Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) >= 50){
                                    span();
                                    totalSum = Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) - (Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) * 10 / 100);
                                    promoCodePrice.setVisibility(View.VISIBLE);
                                    salePromo.setVisibility(View.VISIBLE);
                                    theWholeOf.setVisibility(View.GONE);
                                    salePromo.setText("სულ ჯამი    -10%");

                                    promoCodePrice.setText(new DecimalFormat("##.##").format(totalSum)+" ¢");
                                    Toast.makeText(getActivity(), "თქვენ მიიღეთ 10%-იანი ფასდაკლება", Toast.LENGTH_LONG).show();
                                    check_promo_button.setClickable(false);
                                }else {
                                    Toast.makeText(getActivity(), "იმისათვის რომ გაგიაქტიურდეთ პრომო კოდი, თქვენი კალათის ღირებულება უნდა არემატებოდეს 50 ლარს", Toast.LENGTH_LONG).show();
                                    promoCodePrice.setVisibility(View.GONE);
                                    theWholeOf.setVisibility(View.VISIBLE);
                                }

                                break;
                            case "HBG":
                                if(Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) >= 50){
                                    span();
                                    totalSum = Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) - (Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) * 10 / 100);
                                    promoCodePrice.setVisibility(View.VISIBLE);
                                    salePromo.setVisibility(View.VISIBLE);
                                    theWholeOf.setVisibility(View.GONE);
                                    salePromo.setText("სულ ჯამი    -10%");
                                    promoCodePrice.setText(new DecimalFormat("##.##").format(totalSum)+" ¢");
                                    Toast.makeText(getActivity(), "თქვენ მიიღეთ 10%-იანი ფასდაკლება", Toast.LENGTH_LONG).show();
                                    check_promo_button.setClickable(false);
                                }else {
                                    Toast.makeText(getActivity(), "იმისათვის რომ გაგიაქტიურდეთ პრომო კოდი, თქვენი კალათის ღირებულება უნდა არემატებოდეს 50 ლარს", Toast.LENGTH_LONG).show();
                                    promoCodePrice.setVisibility(View.GONE);
                                    theWholeOf.setVisibility(View.VISIBLE);
                                }

                                break;
                            case "BR":
                                if(Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) >= 50){
                                    span();
                                    totalSum = Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) - (Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) * 10 / 100);
                                    promoCodePrice.setVisibility(View.VISIBLE);
                                    salePromo.setVisibility(View.VISIBLE);
                                    theWholeOf.setVisibility(View.GONE);
                                    salePromo.setText("სულ ჯამი    -10%");
                                    promoCodePrice.setText(new DecimalFormat("##.##").format(totalSum)+" ¢");
                                    Toast.makeText(getActivity(), "თქვენ მიიღეთ 10%-იანი ფასდაკლება", Toast.LENGTH_LONG).show();
                                    check_promo_button.setClickable(false);
                                }else {
                                    Toast.makeText(getActivity(), "იმისათვის რომ გაგიაქტიურდეთ პრომო კოდი, თქვენი კალათის ღირებულება უნდა არემატებოდეს 50 ლარს", Toast.LENGTH_LONG).show();
                                    promoCodePrice.setVisibility(View.GONE);
                                    theWholeOf.setVisibility(View.VISIBLE);
                                }

                                break;
                            case "HBG15":
                                if(Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) >= 100){
                                    span();
                                    totalSum = Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) - (Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price")) * 15 / 100);
                                    promoCodePrice.setVisibility(View.VISIBLE);
                                    salePromo.setVisibility(View.VISIBLE);
                                    theWholeOf.setVisibility(View.GONE);
                                    salePromo.setText("სულ ჯამი    -15%");
                                    promoCodePrice.setText(new DecimalFormat("##.##").format(totalSum)+" ¢");
                                    Toast.makeText(getActivity(), "თქვენ მიიღეთ 15%-იანი ფასდაკლება", Toast.LENGTH_LONG).show();
                                    check_promo_button.setClickable(false);
                                }else {
                                    Toast.makeText(getActivity(), "იმისათვის რომ გაგიაქტიურდეთ პრომო კოდი, თქვენი კალათის ღირებულება უნდა არემატებოდეს 100 ლარს", Toast.LENGTH_LONG).show();
                                    promoCodePrice.setVisibility(View.GONE);
                                    theWholeOf.setVisibility(View.VISIBLE);
                                }

                                break;

                            default:
                                Toast.makeText(getActivity(), "პრომო კოდი არასწორია", Toast.LENGTH_LONG).show();
                                salePromo.setVisibility(View.GONE);
                                theWholeOf.setVisibility(View.VISIBLE);
                                promoCodePrice.setVisibility(View.GONE);
                                totalPrice.setText(CheckoutSingleton.getInstance().getValue("total_price")+" ¢");
                                break;
                        }
                    }
                });

            }
        });
        return orderedProductsFragment;
    }

    @Override
    public void onStop() {
        super.onStop();
        CheckoutSingleton.getInstance().getCartmap().remove("payment_radioButton");
    }
    public Typeface typeface(){

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "BPG_GEL_Excelsior_Caps.ttf");

        return custom_font;
    }
    public void span(){
        StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
        Spannable spannable = (Spannable) totalPrice.getText();
        spannable.setSpan(STRIKE_THROUGH_SPAN, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
