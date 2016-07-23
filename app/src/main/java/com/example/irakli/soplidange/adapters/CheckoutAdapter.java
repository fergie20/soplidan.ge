package com.example.irakli.soplidange.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Irakli on 24.06.2016.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder>{

    ArrayList<ProductModel> cartMap;
    Context context;

    public CheckoutAdapter(ArrayList<ProductModel> cartMap, Context context) {

        this.cartMap = cartMap;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Picasso.with(context)
                .load(cartMap.get(position).getImg())
                .into(holder.productImageView);
        double price = cartMap.get(position).getPrice();
        holder.productPriceView.setText( String.valueOf(price)+"GEL");
        holder.productNameView.setText(cartMap.get(position).getName());
        holder.quantityView.setText(cartMap.get(position).getQuontity() + "");
        holder.minusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity;
                quantity = Integer.parseInt((String) holder.quantityView.getText().toString());

                if (quantity >= 2) {
                    quantity--;

                    holder.quantityView.setText(quantity + "");


                } else {
                    holder.quantityView.setText("0");

                }
            }
        });
        holder.plusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity;
                quantity = Integer.parseInt((String) holder.quantityView.getText().toString());

                quantity++;
                // Toast.makeText(getApplicationContext(), "daechira" + checkQuantity, Toast.LENGTH_LONG).show();


                holder.quantityView.setText(quantity + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartMap.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView productImageView;
        TextView productNameView;
        TextView productPriceView;
        EditText quantityView;
        ImageView plusView;
        ImageView minusView;


        public MyViewHolder(View itemView) {
            super(itemView);

            productImageView = (ImageView) itemView.findViewById(R.id.checkout_image_id);
            productNameView = (TextView) itemView.findViewById(R.id.checkout_name_id);
            productPriceView = (TextView) itemView.findViewById(R.id.product_price_id);
            quantityView = (EditText) itemView.findViewById(R.id.quantity_id);
            plusView = (ImageView) itemView.findViewById(R.id.plus_id);
            minusView = (ImageView) itemView.findViewById(R.id.minus_id);

        }
    }
}
