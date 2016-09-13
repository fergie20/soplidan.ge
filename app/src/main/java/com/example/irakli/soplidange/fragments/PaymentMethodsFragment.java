package com.example.irakli.soplidange.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.irakli.soplidange.R;

/**
 * Created by floyd on 9/9/2016.
 */
public class PaymentMethodsFragment extends Fragment {

    private TextView serviceView;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View paymentMethodFragment = inflater.inflate(R.layout.payment_fragment_layout, container, false);
        url = "http://soplidan.ge/terms-and-conditions/";

        serviceView = (TextView) paymentMethodFragment.findViewById(R.id.service_terms_id);
        serviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(Intent.ACTION_VIEW);
                serviceIntent.setData(Uri.parse(url));
                startActivity(serviceIntent);
            }
        });

        return paymentMethodFragment;
    }
}
