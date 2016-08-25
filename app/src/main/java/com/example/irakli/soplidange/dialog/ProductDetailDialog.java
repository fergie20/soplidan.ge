package com.example.irakli.soplidange.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irakli.soplidange.ProductsActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.SingletonTest;
import com.example.irakli.soplidange.models.ProductModel;
import com.squareup.picasso.Picasso;

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
    View rootView;
    ProductModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.productdetaildialog, container, false);
        model = (ProductModel) getArguments().getSerializable("model");
        getDialog().setTitle(model.getCategories());
        ImageView image = (ImageView) rootView.findViewById(R.id.dialog_image_id);
        Picasso.with(getActivity())
                .load(model.getImg())
                .resize(300, 300)
                .centerCrop()
                .into(image);
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
                Toast.makeText(getActivity(), "ganaxlda kalata", Toast.LENGTH_LONG).show();

                ((ProductsActivity)getActivity()).updateListView();
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
                    Toast.makeText(getActivity(), "tqvens mier motxovnili produqtis raodenoba agemateba marags", Toast.LENGTH_LONG).show();

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
//        ((ProductsActivity)getActivity()).updateListView();
    }
}
