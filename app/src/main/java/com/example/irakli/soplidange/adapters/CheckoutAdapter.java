package com.example.irakli.soplidange.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.irakli.soplidange.MainActivity;
import com.example.irakli.soplidange.ProductsActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.SingletonTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

/**
 * Created by Irakli on 24.06.2016.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {

    ArrayList<ProductModel> cartMap;
    Context context;
    double price;

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
        Glide.with(context)
                .load(cartMap.get(position).getImg())
                .into(holder.productImageView);


        if (cartMap.get(position).getBase_price() > 0) {
            price = cartMap.get(position).getBase_price();

        } else {
            price = cartMap.get(position).getList_price();

        }

        holder.setMyModel(cartMap.get(position));



        holder.productPriceView.setText(String.valueOf(price) + " ¢");
        holder.productNameView.setText(cartMap.get(position).getName());
        holder.quantityView.setText(cartMap.get(position).getQuontity() + "");
    }

    @Override
    public int getItemCount() {
        return cartMap.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView deleteItem;

        ImageView productImageView;
        TextView productNameView;
        TextView productPriceView;
        TextView quantityView;
        ImageView plusView;
        ImageView minusView;
        private ProductModel model;
        private double oldPrice;

        public void setMyModel(ProductModel model) {
            this.model = model;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            deleteItem = (ImageView) itemView.findViewById(R.id.checkout_delete_item_id);
            productImageView = (ImageView) itemView.findViewById(R.id.checkout_image_id);
            productNameView = (TextView) itemView.findViewById(R.id.checkout_name_id);
            productPriceView = (TextView) itemView.findViewById(R.id.product_price_id);
            productPriceView.setTypeface(typeface());
            quantityView = (TextView) itemView.findViewById(R.id.quantity_id);
            plusView = (ImageView) itemView.findViewById(R.id.plus_id);
            minusView = (ImageView) itemView.findViewById(R.id.minus_id);

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartMap.remove(model);
                    notifyDataSetChanged();

                    int quont = Integer.parseInt(quantityView.getText().toString());
                    SingletonTest.getInstance().getCartMap().remove(model.getId());

                    Toast.makeText(context, "პროდუქტი წაიშალა", Toast.LENGTH_SHORT).show();

                    if (model.getBase_price() > 0) {
                        oldPrice = model.getBase_price();
                    } else {
                        oldPrice = model.getList_price();
                    }
                    listener.onClick(-oldPrice * quont);
                }
            });

            plusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity;
                    quantity = Integer.parseInt((String) quantityView.getText().toString());

                    quantity++;

                    if (quantity <= model.getRecource()) {

                        model.setQuontity(quantity);
                        SingletonTest.getInstance().addProduct(model.getId(), model);

                        if (model.getBase_price() > 0) {
                            oldPrice = model.getBase_price();
                        } else {
                            oldPrice = model.getList_price();
                        }
                        listener.onClick(oldPrice);
                        quantityView.setText(quantity + "");
                    } else {
                        quantity--;
                        model.setQuontity(quantity);
                        SingletonTest.getInstance().addProduct(model.getId(), model);

                        Toast.makeText(context, "თქვენს მიერ არჩეული პროდუქციის რაოდენობა აღემატება მარაგს", Toast.LENGTH_SHORT).show();
                        quantityView.setText(quantity + "");
                    }


                }
            });
            minusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity;
                    quantity = Integer.parseInt(quantityView.getText().toString());

                    if (quantity >= 1) {
                        quantity--;

                        quantityView.setText(quantity + "");
                        double newQuantity = Double.parseDouble(quantityView.getText().toString());
                        SingletonTest.getInstance().addProduct(model.getId(), model);
                        if (newQuantity <= 0) {
                            cartMap.remove(model);
                            notifyDataSetChanged();
                            SingletonTest.getInstance().getCartMap().remove(model.getId());

                            Toast.makeText(context, "პროდუქტი წაიშალა", Toast.LENGTH_SHORT).show();

                            model.setQuontity(Integer.parseInt(quantityView.getText().toString()));

                            if (model.getBase_price() > 0) {
                                oldPrice = model.getBase_price();
                            } else {
                                oldPrice = model.getList_price();
                            }

                            listener.onClick(-oldPrice);
                            return;
                        }

                        model.setQuontity(Integer.parseInt(quantityView.getText().toString()));

                        SingletonTest.getInstance().addProduct(model.getId(), model);
                        if (model.getBase_price() > 0) {
                            oldPrice = model.getBase_price();
                        } else {
                            oldPrice = model.getList_price();
                        }
                        listener.onClick(-oldPrice);
                    }
                }
            });

        }

    }

    private MyClickListener listener;

    public void setMyPriceUpdateListener(MyClickListener listener) {
        this.listener = listener;
    }

    public interface MyClickListener {
        void onClick(double price);
    }

    public Typeface typeface() {

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "BPG_GEL_Excelsior_Caps.ttf");

        return custom_font;
    }
}
