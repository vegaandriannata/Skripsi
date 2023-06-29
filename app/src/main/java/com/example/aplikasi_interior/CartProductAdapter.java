package com.example.aplikasi_interior;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aplikasi_interior.CartProduct;
import com.example.aplikasi_interior.R;

import java.util.List;

public class CartProductAdapter extends ArrayAdapter<CartProduct> {

    private Context context;
    private List<CartProduct> cartProductList;

    public CartProductAdapter(Context context, List<CartProduct> cartProductList) {
        super(context, 0, cartProductList);
        this.context = context;
        this.cartProductList = cartProductList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_cart_product, parent, false);
        }

        CartProduct cartProduct = cartProductList.get(position);

        TextView textViewIdBrg = view.findViewById(R.id.textViewIdBrg);
        TextView textViewUserId = view.findViewById(R.id.textViewUserId);
        TextView textViewQty = view.findViewById(R.id.textViewQty);

        textViewIdBrg.setText("ID Barang: " + String.valueOf(cartProduct.getId_brg()));
        textViewUserId.setText("User ID: " + String.valueOf(cartProduct.getUser_id()));
        textViewQty.setText("Qty: " + String.valueOf(cartProduct.getQty()));

        return view;
    }
}
