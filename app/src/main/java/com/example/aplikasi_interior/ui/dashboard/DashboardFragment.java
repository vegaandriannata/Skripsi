package com.example.aplikasi_interior.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.aplikasi_interior.SessionManager;
import com.example.aplikasi_interior.VolleyConnection;
import com.example.aplikasi_interior.CartProduct;
import com.example.aplikasi_interior.CartProductAdapter;
import com.example.aplikasi_interior.DbContract;
import com.example.aplikasi_interior.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private List<CartProduct> cartProductList;
    private CartProductAdapter cartProductAdapter;
    private ListView listViewCartProducts;
    private SessionManager sessionManager;
    private int currentUserId;
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        listViewCartProducts = view.findViewById(R.id.listViewCartProducts);
        cartProductList = new ArrayList<>();
        cartProductAdapter = new CartProductAdapter(getActivity(), cartProductList);
        listViewCartProducts.setAdapter(cartProductAdapter);

        sessionManager = new SessionManager(requireActivity());

        currentUserId = Integer.parseInt(sessionManager.getUserId());

        loadCartProducts();

        return view;
    }

    private void loadCartProducts() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, DbContract.SERVER_GET_CART, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            cartProductList.clear();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject cartProductObj = response.getJSONObject(i);

                                int id_brg = cartProductObj.getInt("id_brg");
                                int user_id = cartProductObj.getInt("user_id");
                                int qty = cartProductObj.getInt("qty");



                                if (user_id == currentUserId) { // Only add cart products if the user ID matches
                                    CartProduct cartProduct = new CartProduct(id_brg, user_id, qty);
                                    cartProductList.add(cartProduct);
                                }
                            }

                            cartProductAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Failed to load cart products.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Failed to connect to server.", Toast.LENGTH_SHORT).show();
                    }
                });

        VolleyConnection.getInstance(getActivity()).addToRequestQueue(request);
    }
}
