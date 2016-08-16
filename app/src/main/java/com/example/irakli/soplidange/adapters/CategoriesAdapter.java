package com.example.irakli.soplidange.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irakli.soplidange.CheckoutActivity;
import com.example.irakli.soplidange.ProductsActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.models.CategoryModel;

import java.util.ArrayList;

/**
 * Created by Irakli on 23.06.2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>{

    private ArrayList<CategoryModel> categoriesList;
    private Context context;


    public CategoriesAdapter(ArrayList<CategoryModel> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.categoryNameView.setText( categoriesList.get(position).getCategory());
        holder.categoryNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "daechira" + position, Toast.LENGTH_LONG).show();
                int cat_id = (position);
                Intent transport = new Intent(context, ProductsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("cat_id", cat_id);
                transport.putExtra("Extra", bundle);
                transport.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(transport);
            }
        });

    }


    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameView;

        public MyViewHolder(View itemView) {
            super(itemView);

            categoryNameView = (TextView) itemView.findViewById(R.id.recycler_item_id);
        }
    }


}
