package com.example.loginregister_mysql_volley.ui.account;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.loginregister_mysql_volley.SessionManager;
import com.example.loginregister_mysql_volley.R;
import com.example.loginregister_mysql_volley.VolleyConnection;
import com.example.loginregister_mysql_volley.DbContract;
import com.example.loginregister_mysql_volley.WelcomeActivity;

public class AccountFragment extends Fragment {

    private TextView usernameTextView, emailTextView;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        usernameTextView = view.findViewById(R.id.text_username);
        emailTextView = view.findViewById(R.id.text_email);
        sessionManager = new SessionManager(requireContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String username = sessionManager.getUsername();
        fetchEmailFromUsername(username);
        Button logoutButton = view.findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void fetchEmailFromUsername(final String username) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_CONNECTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String email = jsonObject.getString("email");

                            usernameTextView.setText(username);
                            emailTextView.setText(email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                return params;
            }
        };

        VolleyConnection.getInstance(requireContext()).addToRequestQueue(stringRequest);
    }
    private void logout() {
        sessionManager.clearSession(); // Clear session data
        Intent welcomeIntent = new Intent(requireContext(), WelcomeActivity.class);
        startActivity(welcomeIntent);
        requireActivity().finish(); // Optional: Close the current activity if desired
    }
}


