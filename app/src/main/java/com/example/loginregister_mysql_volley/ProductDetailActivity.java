package com.example.loginregister_mysql_volley;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Retrieve the product object passed from the adapter
        Product product = (Product) getIntent().getSerializableExtra("product");

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);

        if (product != null) {
            // Set the byte array image to the Product object
            product.setImageBytes(product.getImageBytes());

            // Set the product details to the views
            productImage.setImageBitmap(product.getImage());
            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
        }
    }
}
