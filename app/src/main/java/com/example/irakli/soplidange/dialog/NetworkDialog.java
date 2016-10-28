package com.example.irakli.soplidange.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.irakli.soplidange.MainActivity;
import com.example.irakli.soplidange.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by GeoLab on 7/10/16.
 */
public class NetworkDialog extends DialogFragment {
    TextView tryAgain,noInt,noIntDesc,loadingText;
    GifImageView loadingGif;
    boolean check = false;


    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.network_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        tryAgain = (TextView) rootView.findViewById(R.id.try_again_id);
        noInt = (TextView) rootView.findViewById(R.id.no_internet_id);
        noIntDesc = (TextView) rootView.findViewById(R.id.no_internet_desc_id);
        loadingGif = (GifImageView) rootView.findViewById(R.id.loading_gif_id);
        loadingText = (TextView) rootView.findViewById(R.id.loading_text_id);


        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = false;
                check();
                ((MainActivity)getActivity()).getJSONInfo();


            }
        });
        return rootView;
    }

public void check(){
    if(check){
        noInt.setVisibility(View.GONE);
        noIntDesc.setVisibility(View.GONE);
        loadingText.setVisibility(View.VISIBLE);
        loadingGif.setVisibility(View.VISIBLE);
    }else{
        noInt.setVisibility(View.VISIBLE);
        noIntDesc.setVisibility(View.VISIBLE);
        loadingText.setVisibility(View.GONE);
        loadingGif.setVisibility(View.GONE);
    }
}
}
