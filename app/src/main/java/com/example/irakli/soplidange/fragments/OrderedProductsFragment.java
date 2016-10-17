package com.example.irakli.soplidange.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.utils.CheckoutSingleton;

/**
 * Created by floyd on 9/9/2016.
 */
public class OrderedProductsFragment extends Fragment {
    TextView shippingCost, totalPrice;
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

        totalSum = Double.parseDouble(CheckoutSingleton.getInstance().getValue("total_price"));

        promoCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    check_promo_button.setVisibility(View.VISIBLE);
                }else{
                    check_promo_button.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                check_promo_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if((editable.toString().equals("TBC10")||(editable.toString().equals("HBG")||(editable.toString().equals("BR")||(editable.toString().equals("HBG15")){
//                        }


                        if((editable.toString().equals("TBC10")&&totalSum>50)||(editable.toString().equals("HBG")&&totalSum>50)||(editable.toString().equals("BR")&&totalSum>50)){
                            Toast.makeText(getActivity(), "swori kodia", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getActivity(), "პრომო კოდი არასწორია", Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

        shippingCost.setTypeface(typeface());
        totalPrice.setTypeface(typeface());

        shippingCost.setText(CheckoutSingleton.getInstance().getValue("shipping_price")+"¢");
        totalPrice.setText(totalSum+"¢");


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
}
