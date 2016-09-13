package com.example.irakli.soplidange.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.irakli.soplidange.R;

/**
 * Created by floyd on 9/8/2016.
 */
public class DeliveryTermsFragment extends Fragment {

    TextView deliveryCost;
    TextView termsInfo1;
    TextView termsInfo2;
    TextView termsInfo3;
    TextView termsInfo4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deliveryTermsFragment = inflater.inflate(R.layout.delivery_terms_layout, container, false);

        deliveryCost = (TextView) deliveryTermsFragment.findViewById(R.id.delivery_cost_id);
        termsInfo1 = (TextView) deliveryTermsFragment.findViewById(R.id.terms_info_1_id);
        termsInfo2 = (TextView) deliveryTermsFragment.findViewById(R.id.terms_info_2_id);
        termsInfo3 = (TextView) deliveryTermsFragment.findViewById(R.id.terms_info_3_id);
        termsInfo4 = (TextView) deliveryTermsFragment.findViewById(R.id.terms_info_4_id);

        String dot = "<font color='#028bca'>\u25CF </font>";
        deliveryCost.setText(Html.fromHtml(dot + deliveryCost.getText()));
        termsInfo1.setText(Html.fromHtml(dot + termsInfo1.getText()));
        termsInfo2.setText(Html.fromHtml(dot + termsInfo2.getText()));
        termsInfo3.setText(Html.fromHtml(dot + termsInfo3.getText()));
        termsInfo4.setText(Html.fromHtml(dot + termsInfo4.getText()));

        return deliveryTermsFragment;
    }
}
