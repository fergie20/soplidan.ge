package com.example.irakli.soplidange;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.irakli.soplidange.ExampleData.ExampleData;
import com.example.irakli.soplidange.adapters.ProductsAdapter;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<String> productImagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        initToolbar();
        initGridRecycleView();

        productImagesList = new ArrayList<>();
        for (int i = 0; i < ExampleData.productImages.length; i++) {
            productImagesList.add(ExampleData.productImages[i]);
        }

        ProductsAdapter productsAdapter = new ProductsAdapter(productImagesList, getApplicationContext());
        RecyclerView gridRecycler = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
        gridRecycler.setAdapter(productsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }

    private void initGridRecycleView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

}
