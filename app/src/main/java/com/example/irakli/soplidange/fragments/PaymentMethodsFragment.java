package com.example.irakli.soplidange.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irakli.soplidange.R;

/**
 * Created by floyd on 9/9/2016.
 */
public class PaymentMethodsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View paymentMethodFragment = inflater.inflate(R.layout.payment_fragment_layout, container, false);

        return paymentMethodFragment;
    }
}