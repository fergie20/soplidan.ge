package com.example.irakli.soplidange.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.irakli.soplidange.R;

/**
 * Created by floyd on 9/8/2016.
 */
public class GuestFieldsFragment extends android.support.v4.app.Fragment{

    private RadioGroup organizationRadioGroup;
    private RelativeLayout organizationLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View guestFieldsFragment = inflater.inflate(R.layout.guest_fragment_layout, container, false);

        organizationRadioGroup = (RadioGroup) guestFieldsFragment.findViewById(R.id.organization_radio_group_id);
        organizationLayout = (RelativeLayout) guestFieldsFragment.findViewById(R.id.organization_fields_id);

        radioBtnChecked();
        return guestFieldsFragment;
    }

    private void radioBtnChecked(){
        organizationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yes_btn_id:
                        organizationLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.no_btn_id:
                        organizationLayout.setVisibility(View.GONE);
                }
            }
        });
    }

}
