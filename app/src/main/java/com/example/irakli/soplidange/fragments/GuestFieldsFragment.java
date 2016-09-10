package com.example.irakli.soplidange.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irakli.soplidange.R;

/**
 * Created by floyd on 9/8/2016.
 */
public class GuestFieldsFragment extends android.support.v4.app.Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View guestFieldsFragment = inflater.inflate(R.layout.guest_fragment_layout, container, false);


        return guestFieldsFragment;
    }
}
