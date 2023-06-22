package com.example.aplikasi_interior;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class PemesananActivity extends AppCompatActivity {
    private RecyclerView selectedProductsRecyclerView;
    private SelectedProductAdapter selectedProductAdapter;
    private List<Product> selectedProducts;
    private TextView totalOrderTextView;
    private EditText addressInput;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        selectedProducts = new ArrayList<>();

        // Retrieve the selected products from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedProducts = (List<Product>) extras.getSerializable("selectedProducts");
        }

        selectedProductsRecyclerView = findViewById(R.id.selected_products_recycler_view);
        selectedProductAdapter = new SelectedProductAdapter(selectedProducts);
        selectedProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedProductsRecyclerView.setAdapter(selectedProductAdapter);

        totalOrderTextView = findViewById(R.id.total_order);
        addressInput = findViewById(R.id.address_input);
        checkoutButton = findViewById(R.id.checkout_button);

        updateTotalOrder();

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressInput.getText().toString();

                // Create intent to start KonfirmasiPesananActivity
                Intent intent = new Intent(PemesananActivity.this, KonfirmasiPesananActivity.class);
                intent.putExtra("selectedProducts", new ArrayList<>(selectedProducts)); // Mengonversi ke ArrayList
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });
    }

    private void updateTotalOrder() {
        double total = 0;
        for (Product product : selectedProducts) {
            double price = Double.parseDouble(product.getPrice().replace("Rp", "").replace(",", ""));
            int quantity = product.getQuantity();
            total += price * quantity;
        }

        String formattedTotal = formatPrice(total);
        String totalOrderText = "Total Order: " + formattedTotal;
        totalOrderTextView.setText(totalOrderText);
    }

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "Rp" + formatter.format(price);
    }
}