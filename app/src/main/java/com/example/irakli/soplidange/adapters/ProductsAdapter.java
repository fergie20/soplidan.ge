package com.example.irakli.soplidange.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irakli.soplidange.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Irakli on 24.06.2016.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder>{

    ArrayList<String> productImagesList;
    Context context;

    public ProductsAdapter(ArrayList<String> productImagesList, Context context) {
        this.productImagesList = productImagesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_recycler_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(holder.productImageView);
//        holder.productImageView.setText(productImagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return productImagesList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView productImageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            productImageView = (ImageView) itemView.findViewById(R.id.recycler_grid_view_id);
        }
    }
}
