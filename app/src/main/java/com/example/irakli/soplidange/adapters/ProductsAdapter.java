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
import com.example.irakli.soplidange.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Irakli on 24.06.2016.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder>{

    ArrayList<ProductModel> productModels;
    Context context;

    public ProductsAdapter(ArrayList<ProductModel> productModels, Context context) {

        this.productModels = productModels;
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
        Picasso.with(context)
                .load(productModels.get(position).getImg())
                .into(holder.productImageView);
        double price = productModels.get(position).getPrice();
        holder.setModel(productModels.get(position));
        holder.productPriceView.setText( String.valueOf(price)+"GEL");
        holder.productNameView.setText(productModels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView productImageView;
        TextView productNameView;
        TextView productPriceView;
        ProductModel model;

        void setModel(ProductModel model) {
            this.model = model;
        }

        public MyViewHolder(View itemView) {
            super(itemView);

            productImageView = (ImageView) itemView.findViewById(R.id.image_id);
            productNameView = (TextView) itemView.findViewById(R.id.product_name_id);
            productPriceView = (TextView) itemView.findViewById(R.id.price_id);

            productImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.onClick((model));
                    }
                }
            });
        }
    }

    private MyClickListener listener;
    public void setMyClickListener(MyClickListener listener) {
        this.listener = listener;
    }
    public interface MyClickListener {
        void onClick(ProductModel model);
    }
}
