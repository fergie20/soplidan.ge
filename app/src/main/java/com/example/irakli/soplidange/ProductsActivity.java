package com.example.irakli.soplidange;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irakli.soplidange.ExampleData.ProductData;
import com.example.irakli.soplidange.adapters.ProductsAdapter;
import com.example.irakli.soplidange.dialog.ProductDetailDialog;
import com.example.irakli.soplidange.models.ProductModel;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView plus;
    ImageView minus;
    TextView quantityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        initToolbar();
        initGridRecycleView();
        plus = (ImageView) findViewById(R.id.grid_plus_id);
        minus = (ImageView) findViewById(R.id.grid_minus_id);
        quantityView = (TextView) findViewById(R.id.grid_text_id);



        ArrayList <ProductModel> productModels = new ArrayList<>();

        for (int i = 0; i < ProductData.id.length; i++) {
            ProductModel productModel = new ProductModel(ProductData.categories[i],ProductData.name[i],ProductData.description[i],ProductData.img[i],ProductData.id[i],ProductData.recource[i],ProductData.price[i]);
            productModels.add(productModel);
        }

        ProductsAdapter productsAdapter = new ProductsAdapter(productModels, getApplicationContext());
        productsAdapter.setMyClickListener(new ProductsAdapter.MyClickListener() {
            @Override
            public void onClick(ProductModel model) {
                ProductDetailDialog dialog = new ProductDetailDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable("model",model);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(),"dialog");
            }
        });
        RecyclerView gridRecycler = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
        gridRecycler.setAdapter(productsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super. onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }

    private void initGridRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_grid_view_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

}
