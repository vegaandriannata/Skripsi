package com.example.aplikasi_interior.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasi_interior.R;

import java.util.ArrayList;
import java.util.List;

import com.example.aplikasi_interior.Product;
import com.example.aplikasi_interior.ProductAdapter;
import com.example.aplikasi_interior.DbContract;
import com.example.aplikasi_interior.VolleyConnection;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Base64;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create a list of products
        productList = new ArrayList<>();

        // Fetch data from the database
        fetchDataFromDatabase();

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);
    }

    private void fetchDataFromDatabase() {
        String url = DbContract.SERVER_GET_PRODUCT;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("server_response");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject productObject = jsonArray.getJSONObject(i);
                                String name = productObject.getString("nama_brg");
                                String price = productObject.getString("harga_brg");
                                String imageString = productObject.getString("gambar");
                                String ket_brg = productObject.getString("ket_brg");
                                int stok = productObject.getInt("stok");
                                int id_brg = productObject.getInt("id_brg");
                                // Convert the image string to byte array
                                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

                                // Convert the byte array to Bitmap
                                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                                Product product = new Product(imageBitmap, name, price, ket_brg, stok, id_brg);
                                productList.add(product);
                            }
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        VolleyConnection.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest);
    }

}
