package com.example.irakli.soplidange.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irakli.soplidange.R;

/**
 * Created by GeoLab on 7/10/16.
 */
public class NetworkDialog extends DialogFragment {

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.network_dialog, container, false);


        return rootView;
    }


}
