package com.example.irakli.soplidange.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.irakli.soplidange.ExampleData.ExampleData;
import com.example.irakli.soplidange.ExampleData.ProductData;
import com.example.irakli.soplidange.ProductsActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.models.CategoryModel;
import com.squareup.picasso.Picasso;

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
        holder.categoryNameView.setText(ExampleData.categories[position]);

//        Picasso.with(context)
//                .load("http://i.imgur.com/DvpvklR.png")
//                .centerInside()
//                .fit()
//                .into( holder.categoryImageView);
        holder.categoryImageView.setImageResource(categoriesList.get(position).getCategory_image());

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "daechira" + position, Toast.LENGTH_LONG).show();
                int cat_id = categoriesList.get(position).getCategory_id();
                String category = categoriesList.get(position).getCategory();
                Intent transport = new Intent(context, ProductsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("category_id", cat_id);
                bundle.putString("category", category);
                transport.putExtra("categories", bundle);
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
        ImageView categoryImageView;
        LinearLayout click;


        public MyViewHolder(View itemView) {
            super(itemView);

            categoryNameView = (TextView) itemView.findViewById(R.id.recycler_item_id);
            categoryNameView.setTypeface(typeface());
            click = (LinearLayout) itemView.findViewById(R.id.card_view_id);
            categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_id);
        }
    }
    public Typeface typeface() {

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "BPG_GEL_Excelsior_Caps.ttf");

        return custom_font;
    }
}
