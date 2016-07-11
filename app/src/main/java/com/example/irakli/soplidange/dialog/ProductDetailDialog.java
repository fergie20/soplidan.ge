package com.example.irakli.soplidange.dialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irakli.soplidange.CheckoutActivity;
import com.example.irakli.soplidange.R;
import com.example.irakli.soplidange.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

/**
 * Created by GeoLab on 7/10/16.
 */
public class ProductDetailDialog extends DialogFragment {
    ImageView minusTextView;
    ImageView plusTextView;
    EditText quantityView;
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
        price.setText(String.valueOf(model.getPrice()) + " GEL");
        TextView description = (TextView) rootView.findViewById(R.id.dialog_description_id);
        description.setText(model.getDescription());
        Button addbutton = (Button) rootView.findViewById(R.id.dialog_addbutton_id);

        calculateSum();

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CheckoutActivity.class);

                i.putExtra("model",model);
                startActivity(i);
            }
        });



        return rootView;
    }
    private void calculateSum() {
        minusTextView = (ImageView) rootView.findViewById(R.id.dialog_minus_id);
        plusTextView = (ImageView) rootView.findViewById(R.id.dialog_plus_id);
        quantityView = (EditText) rootView.findViewById(R.id.dialog_quantity_id);
        sumView = (TextView) rootView.findViewById(R.id.dialog_price_id);

        quantityView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                int productPrice =  Integer.parseInt((String) productPriceView.getText());
//                int quontity = Integer.parseInt((String) charSequence);
//                int sum = productPrice*quontity;
//                sumView.setText(sum+"");
//                Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_LONG).show();


            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = String.valueOf(editable);
                if (editable != null && !input.isEmpty()) {
                    int quontity = Integer.parseInt(input);

                    double sum = model.getPrice() * quontity;
                    NumberFormat nf = NumberFormat.getInstance(); // get instance
                    nf.setMaximumFractionDigits(3); // set decimal places
                    String s = nf.format(sum);
                    sumView.setText(s+" GEL");
                } else {
                    quantityView.setText("0");
                }
                //Toast.makeText(getApplicationContext(), editable, Toast.LENGTH_LONG).show();
            }
        });

        plusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productPrice;
                int quantity;
                checkQuantity = quantityView.getText().toString();
                quantity = Integer.parseInt((String) checkQuantity);

                quantity++;
                // Toast.makeText(getApplicationContext(), "daechira" + checkQuantity, Toast.LENGTH_LONG).show();


                quantityView.setText(quantity + "");


            }
        });
        minusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productPrice;
                int quantity;
                checkQuantity = quantityView.getText().toString();
                quantity = Integer.parseInt((String) checkQuantity);

                if (quantity >= 2) {
                    quantity--;

                    quantityView.setText(quantity + "");


                } else {
                    sumView.setText("0");
                    quantityView.setText("0");

                }


            }
        });

    }
}
