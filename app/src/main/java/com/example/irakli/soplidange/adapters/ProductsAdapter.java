package com.example.irakli.soplidange.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irakli.soplidange.ProductsActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.SingletonTest;
import com.example.irakli.soplidange.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

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
        holder.setSavedQuantity();
        holder.productPriceView.setText(String.valueOf(price)+"GEL");
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
        ImageView plus;
        ImageView minus;
        TextView quantityView;

        void setModel(ProductModel model) {
            this.model = model;
        }

        private void setSavedQuantity(){
            if (SingletonTest.getInstance().getProduct(model.getId()) != null) {
                quantityView.setText(SingletonTest.getInstance().getProduct(model.getId()).getQuontity() + "");
            }
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            plus = (ImageView) itemView.findViewById(R.id.grid_plus_id);
            minus = (ImageView) itemView.findViewById(R.id.grid_minus_id);
            quantityView = (TextView) itemView.findViewById(R.id.grid_text_id);

            productImageView = (ImageView) itemView.findViewById(R.id.image_id);
            productNameView = (TextView) itemView.findViewById(R.id.product_name_id);
            productPriceView = (TextView) itemView.findViewById(R.id.price_id);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity;
                    quantity = Integer.parseInt(quantityView.getText().toString());

                    quantity++;

                    if(quantity <= model.getRecource()){
                        model.setQuontity(quantity);
                        SingletonTest.getInstance().addProduct(model.getId(), model);
                        mycountListener.countClick();
                        Toast.makeText(context, "daemata kalatashi", Toast.LENGTH_LONG).show();

                        quantityView.setText(quantity + "");
                    }else{
                        quantity--;
                        model.setQuontity(quantity );
                        SingletonTest.getInstance().addProduct(model.getId(), model);
                        Toast.makeText(context, "tqvens mier motxovnili produqtis raodenoba agemateba marags", Toast.LENGTH_LONG).show();

                        quantityView.setText(quantity + "");

                    }


                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity;
                    quantity = Integer.parseInt((String) quantityView.getText().toString());


                    if (quantity >= 1) {
                        quantity--;

                        quantityView.setText(quantity + "");
                        model.setQuontity(Integer.parseInt(quantityView.getText().toString()));

                        int newQuantity = Integer.parseInt(quantityView.getText().toString());
                        SingletonTest.getInstance().addProduct(model.getId(), model);


                        if (newQuantity < 1) {

                            SingletonTest.getInstance().getCartMap().remove(model.getId());
                            mycountListener.countClick();

                        }
                        mycountListener.countClick();
                        Toast.makeText(context, "ganaxlda kalata", Toast.LENGTH_LONG).show();

                    } else {
                        quantityView.setText("0");

                    }

                }
            });

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

    private MyCountListener mycountListener;
    public void setMycountListener(MyCountListener mycountListener) {
        this.mycountListener = mycountListener;
    }
    public interface MyCountListener {
        void countClick();
    }
}
