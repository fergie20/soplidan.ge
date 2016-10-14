package com.example.irakli.soplidange.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.utils.CheckoutSingleton;

/**
 * Created by floyd on 9/9/2016.
 */
public class OrderedProductsFragment extends Fragment {
    TextView shippingCost;
    TextView totalPrice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orderedProductsFragment = inflater.inflate(R.layout.ordered_fragment_layout, container, false);

        shippingCost = (TextView) orderedProductsFragment.findViewById(R.id.shipping_cost_id);
        totalPrice = (TextView) orderedProductsFragment.findViewById(R.id.total_price_id);

        shippingCost.setTypeface(typeface());
        totalPrice.setTypeface(typeface());

        shippingCost.setText(CheckoutSingleton.getInstance().getValue("shipping_price"));
        totalPrice.setText(CheckoutSingleton.getInstance().getValue("total_price"));


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
