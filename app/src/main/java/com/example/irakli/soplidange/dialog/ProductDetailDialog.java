package com.example.irakli.soplidange.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irakli.soplidange.ProductsActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.utils.SingletonTest;
import com.example.irakli.soplidange.models.ProductModel;
import com.squareup.picasso.Picasso;

import net.wujingchao.android.view.SimpleTagImageView;

import java.text.NumberFormat;

/**
 * Created by GeoLab on 7/10/16.
 */
public class ProductDetailDialog extends DialogFragment {
    ImageView minusTextView;
    ImageView plusTextView;
    TextView quantityView;
    TextView sumView;
    String checkQuantity;
    TextView currentPrice;
    View rootView;
    ProductModel model;
    TextView outOfStockView;
    RelativeLayout plusMinusVisibleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.productdetaildialog, container, false);
        model = (ProductModel) getArguments().getSerializable("model");
        getDialog().setTitle(model.getCategories());
        StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
        currentPrice = (TextView) rootView.findViewById(R.id.dialog_current_price_id);



        SimpleTagImageView image = (SimpleTagImageView) rootView.findViewById(R.id.dialog_image_id);
        if(model.getList_discount()!=0) {
            Picasso.with(getActivity())
                    .load(model.getImg())
                    .centerInside()
                    .fit()
                    .into(image);
            image.setTagEnable(true);
            image.setTagText("sale: " + model.getList_discount_prc() + "%");




            currentPrice.setText("ფასი: " + model.getBase_price() + "GEL  " + model.getList_price() + "GEL", TextView.BufferType.SPANNABLE);
            Spannable spannable = (Spannable) currentPrice.getText();
            spannable.setSpan(STRIKE_THROUGH_SPAN, spannable.length()/2+4, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            currentPrice.setPaintFlags(currentPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            Picasso.with(getActivity())
                    .load(model.getImg())
                    .centerInside()
                    .fit()
                    .into(image);
            image.setTagEnable(false);
            currentPrice.setText( model.getList_price() + " GEL");
        }
        outOfStockView = (TextView) rootView.findViewById(R.id.dialog_out_of_stock_id);
        plusMinusVisibleView = (RelativeLayout) rootView.findViewById(R.id.dialog_plus_minus_view_id);
        if(model.getRecource()==0){
            outOfStockView.setVisibility(View.VISIBLE);
            plusMinusVisibleView.setVisibility(View.GONE);
        }



        quantityView = (TextView) rootView.findViewById(R.id.dialog_quantity_id);
        if(model.getRecource() > 0){
            quantityView.setText("1");
        }else{
            quantityView.setText("0");
        }
        TextView name = (TextView) rootView.findViewById(R.id.dialog_name_id);
        name.setText(model.getName());
        TextView price = (TextView) rootView.findViewById(R.id.dialog_price_id);
        price.setText(String.valueOf(model.getBase_price()) + " GEL");
        TextView description = (TextView) rootView.findViewById(R.id.dialog_description_id);
        description.setText(model.getDescription());
        Button addbutton = (Button) rootView.findViewById(R.id.dialog_addbutton_id);



        calculateSum();

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setQuontity(Integer.parseInt(quantityView.getText().toString()));

                SingletonTest.getInstance().addProduct(model.getId(), model);

                double newQuantity = Double.parseDouble(quantityView.getText().toString());
                SingletonTest.getInstance().addProduct(model.getId(), model);
                if (newQuantity <= 0) {

                    SingletonTest.getInstance().getCartMap().remove(model.getId());


                }
                Toast.makeText(getActivity(), "განახლდა კალათა", Toast.LENGTH_LONG).show();

//                ((ProductsActivity)getActivity()).updateListView();
                dismiss();
            }
        });



        return rootView;
    }
    private void calculateSum() {
        minusTextView = (ImageView) rootView.findViewById(R.id.dialog_minus_id);
        plusTextView = (ImageView) rootView.findViewById(R.id.dialog_plus_id);
        sumView = (TextView) rootView.findViewById(R.id.dialog_price_id);
        quantityView = (TextView) rootView.findViewById(R.id.dialog_quantity_id);
        if (SingletonTest.getInstance().getProduct(model.getId()) != null) {
            quantityView.setText(SingletonTest.getInstance().getProduct(model.getId()).getQuontity() + "");
        }
        double productPrice;
        int quantity;
        double sum;
        if(model.getBase_price()>0) {
            productPrice = model.getBase_price();
        }else {
            productPrice = model.getList_price();
        }
        checkQuantity = quantityView.getText().toString();
        quantity = Integer.parseInt((String) checkQuantity);
        sum = productPrice * quantity;

        NumberFormat nf = NumberFormat.getInstance(); // get instance
        nf.setMaximumFractionDigits(3); // set decimal places
        String s = nf.format(sum);

        sumView.setText(s+" GEL");


        plusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double productPrice;
                int quantity;
                double sum;
                if(model.getBase_price()>0) {
                    productPrice = model.getBase_price();
                }else {
                    productPrice = model.getList_price();
                }

                checkQuantity = quantityView.getText().toString();
                quantity = Integer.parseInt((String) checkQuantity);

                quantity++;
                if(quantity<=model.getRecource()) {

                    sum = productPrice * quantity;

                    NumberFormat nf = NumberFormat.getInstance(); // get instance
                    nf.setMaximumFractionDigits(3); // set decimal places
                    String s = nf.format(sum);

                    sumView.setText(s + " GEL");
                    quantityView.setText(quantity + "");

                }else{
                    quantity--;
                    sum = productPrice * quantity;

                    NumberFormat nf = NumberFormat.getInstance(); // get instance
                    nf.setMaximumFractionDigits(3); // set decimal places
                    String s = nf.format(sum);
                    Toast.makeText(getActivity(), "თქვენს მიერ არჩეული პროდუქტის რაოდენობა აღემატება მარაგს", Toast.LENGTH_LONG).show();

                    sumView.setText(s + " GEL");
                    quantityView.setText(quantity + "");

                }

            }
        });
        minusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double productPrice;
                int quantity;
                double sum;
                if(model.getBase_price()>0) {
                    productPrice = model.getBase_price();
                }else {
                    productPrice = model.getList_price();
                }
                checkQuantity = quantityView.getText().toString();
                quantity = Integer.parseInt((String) checkQuantity);

                if (quantity >= 2) {
                    quantity--;

                    sum = productPrice * quantity;

                    NumberFormat nf = NumberFormat.getInstance(); // get instance
                    nf.setMaximumFractionDigits(3); // set decimal places
                    String s = nf.format(sum);

                    sumView.setText(s+" GEL");

                    quantityView.setText(quantity + "");


                } else {
                    sumView.setText("0 GEL");
                    quantityView.setText("0");

                }

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ProductsActivity)getActivity()).updateListView();
    }
}
