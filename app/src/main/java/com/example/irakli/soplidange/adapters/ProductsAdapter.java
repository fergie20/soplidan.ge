package com.example.irakli.soplidange.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.models.ProductModel;
import com.example.irakli.soplidange.utils.SingletonTest;
import com.squareup.picasso.Picasso;


import net.wujingchao.android.view.SimpleTagImageView;

import java.util.List;

/**
 * Created by Irakli on 24.06.2016.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    List<ProductModel> productModels;
    Context context;
    StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();


    public ProductsAdapter(List<ProductModel> productModels, Context context) {
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
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        int pos = getItemViewType(position);
        ProductModel product = productModels.get(pos);
        if (productModels.get(pos).getImg() == null) {

            holder.productImageView.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(productModels.get(pos).getImg())
                    .centerCrop()
                    .fitCenter()
                    .into(holder.productImageView);
//            Picasso.with(context)
//                    .load(productModels.get(pos).getImg())
//                    .into(holder.productImageView);
        }

        if (productModels.get(pos).getList_discount() == 0) {
            holder.productImageView.setTagEnable(false);
            holder.productPriceView.setText(String.valueOf(product.getList_price()) + " ¢");

        } else {
            holder.productImageView.setTagEnable(true);
            holder.productImageView.setTagBackgroundColor(Color.parseColor("#9eff0900"));
            holder.productImageView.setTagText("sale " + productModels.get(pos).getList_discount_prc() + "%");
            holder.productPriceView.setText(String.valueOf(product.getBase_price()) + " ¢");

            holder.productPriceView.setText(productModels.get(pos).getBase_price() + " ¢  " + productModels.get(pos).getList_price() + " ¢", TextView.BufferType.SPANNABLE);
            Spannable spannable = (Spannable) holder.productPriceView.getText();

            spannable.setSpan(STRIKE_THROUGH_SPAN, spannable.length() / 2 + 1, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (productModels.get(pos).getRecource() == 0) {
            holder.outOfStockView.setVisibility(View.VISIBLE);
            holder.plusMinusView.setVisibility(View.GONE);
        }


        holder.setModel(product);
        holder.setSavedQuantity();
        holder.productNameView.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        SimpleTagImageView productImageView;
        TextView productNameView;
        TextView productPriceView;
        ProductModel model;
        ImageView plus;
        ImageView minus;
        TextView quantityView;
        RecyclerView recyclerView;
        RelativeLayout plusMinusView;
        TextView outOfStockView;

        void setModel(ProductModel model) {
            this.model = model;
        }

        private void setSavedQuantity() {
            if (SingletonTest.getInstance().getProduct(model.getId()) != null) {
                quantityView.setText(SingletonTest.getInstance().getProduct(model.getId()).getQuontity() + "");
            }
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            plus = (ImageView) itemView.findViewById(R.id.grid_plus_id);
            minus = (ImageView) itemView.findViewById(R.id.grid_minus_id);
            quantityView = (TextView) itemView.findViewById(R.id.grid_text_id);

            productImageView = (SimpleTagImageView) itemView.findViewById(R.id.image_id);
            productNameView = (TextView) itemView.findViewById(R.id.product_name_id);
            productPriceView = (TextView) itemView.findViewById(R.id.price_id);
            productPriceView.setTypeface(typeface());

            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_grid_view_id);
            plusMinusView = (RelativeLayout) itemView.findViewById(R.id.invisible_plus_minus_id);
            outOfStockView = (TextView) itemView.findViewById(R.id.out_of_stock_id);


            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity;
                    quantity = Integer.parseInt(quantityView.getText().toString());

                    quantity++;

                    if (quantity <= model.getRecource()) {
                        model.setQuontity(quantity);
                        SingletonTest.getInstance().addProduct(model.getId(), model);
                        mycountListener.countClick();
                        Toast.makeText(context, "დაემატა კალათაში", Toast.LENGTH_SHORT).show();


                        quantityView.setText(quantity + "");
                    } else {
                        quantity--;
//                        model.setQuontity(quantity );
//                        SingletonTest.getInstance().addProduct(model.getId(), model);
                        Toast.makeText(context, "თქვენს მიერ არჩეული პროდუქციის რაოდენობა აღემატება მარაგს", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(context, "განახლდა კალათა", Toast.LENGTH_SHORT).show();

                    } else {
                        quantityView.setText("0");

                    }

                }
            });

            productImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
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

    public Typeface typeface() {

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "BPG_GEL_Excelsior_Caps.ttf");

        return custom_font;
    }
}
