package com.example.aplikasi_interior;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
public class KonfirmasiPesananActivity extends AppCompatActivity {
    private TextView idBarangTextView;
    private TextView userIdTextView;
    private TextView orderDateTextView;
    private TextView totalOrderTextView;
    private TextView alamatTextView;
    private TextView qtyTextView;
    private TextView hargaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan);

        idBarangTextView = findViewById(R.id.id_barang);
        userIdTextView = findViewById(R.id.user_id);
        orderDateTextView = findViewById(R.id.order_date);
        totalOrderTextView = findViewById(R.id.total_order);
        alamatTextView = findViewById(R.id.alamat);
        qtyTextView = findViewById(R.id.qty);
        hargaTextView = findViewById(R.id.harga);

        // Retrieve the selected products and address from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<Product> selectedProducts = (ArrayList<Product>) extras.getSerializable("selectedProducts");
            String address = extras.getString("address");
            String totalOrder = extras.getString("totalOrder"); // Mengambil total order dari PemesananActivity

            // Set the values to the respective TextViews
            if (selectedProducts != null && !selectedProducts.isEmpty()) {
                Product product = selectedProducts.get(0);
                idBarangTextView.setText("ID Barang: " + product.getIdBrg());

                // Mengambil User ID dari SessionManager
                SessionManager sessionManager = new SessionManager(this);
                String userId = sessionManager.getUserId();
                userIdTextView.setText("User ID: " + userId);

                // Mendapatkan tanggal hari pemesanan secara otomatis
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String orderDate = dateFormat.format(calendar.getTime());
                orderDateTextView.setText("Order Date: " + orderDate);

                totalOrderTextView.setText("Total Order: " + totalOrder); // Menggunakan total order yang diambil dari PemesananActivity
                alamatTextView.setText("Alamat: " + address);
                qtyTextView.setText("Quantity: " + product.getQuantity());
                hargaTextView.setText("Harga: " + product.getPrice());
            }
        }
    }
}
