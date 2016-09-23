package com.example.irakli.soplidange;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class AboutUs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);





        ImageView geopay = (ImageView) findViewById(R.id.geopay_id);
        geopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlGeopay = "http://geopay.ge/ge/";
                                Intent geopay = new Intent(Intent.ACTION_VIEW);
                                geopay.setData(Uri.parse(urlGeopay));
                                startActivity(geopay);

            }
        });
        ImageView enpard = (ImageView) findViewById(R.id.enpard_id);
        enpard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlEnpard = "http://enpard.ge/ge/";
                Intent enpard = new Intent(Intent.ACTION_VIEW);
                enpard.setData(Uri.parse(urlEnpard));
                startActivity(enpard);

            }
        });
        ImageView farmer = (ImageView) findViewById(R.id.farmer_id);
        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlFarmer = "https://www.gfa.ge/";
                Intent farmer = new Intent(Intent.ACTION_VIEW);
                farmer.setData(Uri.parse(urlFarmer));
                startActivity(farmer);

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
//            case R.id.check_list_id:
//                Intent checkoutActivityIntent = new Intent(getApplicationContext() ,CheckoutActivity.class);
//                startActivity(checkoutActivityIntent);
//                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
