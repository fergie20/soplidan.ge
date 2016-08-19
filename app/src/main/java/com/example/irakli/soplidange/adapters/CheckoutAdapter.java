package com.example.irakli.soplidange.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.SingletonTest;
import com.example.irakli.soplidange.models.ProductModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

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
                .resize(100, 100)
                .centerCrop()
                .into(holder.productImageView);
        double price = cartMap.get(position).getPrice();
        holder.setMyModel(cartMap.get(position));


        holder.productPriceView.setText( String.valueOf(price)+" GEL");
        holder.productNameView.setText(cartMap.get(position).getName());
        holder.quantityView.setText(cartMap.get(position).getQuontity() + "");
    }

    @Override
    public int getItemCount() {
        return cartMap.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView productImageView;
        TextView productNameView;
        TextView productPriceView;
        TextView quantityView;
        ImageView plusView;
        ImageView minusView;
        private ProductModel model;
        private double oldPrice;
        public void setMyModel(ProductModel model ){
            this.model = model;
        }

        public MyViewHolder(View itemView) {
            super(itemView);

            productImageView = (ImageView) itemView.findViewById(R.id.checkout_image_id);
            productNameView = (TextView) itemView.findViewById(R.id.checkout_name_id);
            productPriceView = (TextView) itemView.findViewById(R.id.product_price_id);
            quantityView = (TextView) itemView.findViewById(R.id.quantity_id);
            plusView = (ImageView) itemView.findViewById(R.id.plus_id);
            minusView = (ImageView) itemView.findViewById(R.id.minus_id);

            plusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity;
                    quantity = Integer.parseInt((String) quantityView.getText().toString());

                    quantity++;
                    quantityView.setText(quantity + "");
                    model.setQuontity(Integer.parseInt(quantityView.getText().toString()));

                    SingletonTest.getInstance().addProduct(model.getId(), model);

                    oldPrice = model.getPrice();
                    listener.onClick(oldPrice);
                }
            });
            minusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity;
                    quantity = Integer.parseInt((String) quantityView.getText().toString());

                    if (quantity >= 1) {
                        quantity--;

                        quantityView.setText(quantity + "");
                        double newQuantity = Double.parseDouble(quantityView.getText().toString());
                        SingletonTest.getInstance().addProduct(model.getId(), model);
                        if (newQuantity <= 0) {
                            cartMap.remove(model);
                            notifyDataSetChanged();
                            SingletonTest.getInstance().getCartMap().remove(model.getId());

                            model.setQuontity(Integer.parseInt(quantityView.getText().toString()));

                            oldPrice = model.getPrice();
                            listener.onClick(-oldPrice);
                            return;
                        }

                        model.setQuontity(Integer.parseInt(quantityView.getText().toString()));

                        SingletonTest.getInstance().addProduct(model.getId(), model);
                        oldPrice = model.getPrice();
                        listener.onClick(-oldPrice);


                    }
                }
            });

        }

    }
    private MyClickListener listener;
    public void setMyPriceUpdateListener(MyClickListener listener){
        this.listener = listener;
    }
    public interface MyClickListener {
        void onClick(double price);
    }
}
