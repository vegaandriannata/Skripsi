package com.example.aplikasi_interior;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;
import com.example.aplikasi_interior.DbContract;
import com.example.aplikasi_interior.VolleyConnection;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Button;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

import com.example.aplikasi_interior.DbContract;
import com.example.aplikasi_interior.VolleyConnection;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productKet;
    private TextView productStock;
    private List<Product> selectedProducts;
    private TextView tvQuantity;
    private int quantity = 1;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Inisialisasi list produk yang dipilih
        selectedProducts = new ArrayList<>();

        // Retrieve the product object passed from the adapter
        product = (Product) getIntent().getSerializableExtra("product");

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productKet = findViewById(R.id.product_ket);
        productStock = findViewById(R.id.product_stok);
        tvQuantity = findViewById(R.id.tvQuantity);

        if (product != null) {
            // Set the byte array image to the Product object
            product.setImageBytes(product.getImageBytes());

            // Set the product details to the views
            productImage.setImageBitmap(product.getImage());
            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
            productKet.setText(product.getKetBrg());
            productStock.setText("Stok: " + String.valueOf(product.getStok()));
        }

        // Button Beli click listener
        Button buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tambahkan produk ke daftar produk yang dipilih
                selectedProducts.add(product);
                product.setQuantity(quantity);
                // Buka PemesananActivity dengan mengirim daftar produk yang dipilih
                Intent intent = new Intent(ProductDetailActivity.this, PemesananActivity.class);
                intent.putExtra("selectedProducts", (Serializable) selectedProducts);
                startActivity(intent);
            }
        });

        // Ikon keranjang click listener
        ImageView cartButton = findViewById(R.id.add_to_cart_button);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    public void decreaseQuantity(View view) {
        if (quantity > 1) {
            quantity--;
            tvQuantity.setText(String.valueOf(quantity));
        }
    }

    public void increaseQuantity(View view) {
        if (quantity < product.getStok()) {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        }
    }

    private void addToCart() {
        // Mendapatkan ID barang dan user ID
        int idBrg = product.getIdBrg();
        SessionManager sessionManager = new SessionManager(ProductDetailActivity.this);
        String userId = sessionManager.getUserId();
        String qty = String.valueOf(quantity);

        // Membuat permintaan POST menggunakan StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_POST_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getJSONArray("server_response")
                                    .getJSONObject(0)
                                    .getString("status");

                            if (status.equals("OK")) {
                                Toast.makeText(ProductDetailActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProductDetailActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductDetailActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductDetailActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Menyiapkan parameter yang akan dikirim dalam permintaan
                Map<String, String> params = new HashMap<>();
                params.put("id_brg", String.valueOf(idBrg));
                params.put("user_id", userId);
                params.put("qty", qty);
                return params;
            }
        };

        // Menambahkan permintaan ke RequestQueue menggunakan VolleyConnection
        VolleyConnection.getInstance(ProductDetailActivity.this).addToRequestQueue(stringRequest);
    }
}
