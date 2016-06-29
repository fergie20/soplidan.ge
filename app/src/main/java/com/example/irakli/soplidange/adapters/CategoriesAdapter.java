package com.example.irakli.soplidange.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.irakli.soplidange.R;

import java.util.ArrayList;

/**
 * Created by Irakli on 23.06.2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>{

    private ArrayList<String> categoriesList;
    private Context context;


    public CategoriesAdapter(ArrayList<String> categoriesList, Context context) {
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.categoryNameView.setText(categoriesList.get(position));
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
