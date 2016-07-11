package com.example.irakli.soplidange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.irakli.soplidange.ExampleData.ExampleData;
import com.example.irakli.soplidange.adapters.CategoriesAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<String> categoriesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initRecyclerView();

        categoriesList = new ArrayList<>();

        // Fetch example data
        for (int i = 0; i < ExampleData.categories.length; i++) {
            categoriesList.add(ExampleData.categories[i]);
        }

        CategoriesAdapter myAdapter = new CategoriesAdapter(categoriesList, getApplicationContext());
        recyclerView.setAdapter(myAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_id:
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.check_list_id:
                Intent intent1 = new Intent(getApplicationContext(), CheckoutActivity.class);
                startActivity(intent1);
        }
        return true;
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }



}
