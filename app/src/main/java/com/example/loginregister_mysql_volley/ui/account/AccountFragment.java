package com.example.loginregister_mysql_volley.ui.account;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.loginregister_mysql_volley.Account;
import com.example.loginregister_mysql_volley.WelcomeActivity;
import com.example.loginregister_mysql_volley.ui.account.AccountViewModel;
import com.example.loginregister_mysql_volley.R;

public class AccountFragment extends Fragment {
    private TextView usernameTextView, emailTextView;
    private Button logoutButton;
    private AccountViewModel accountViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        accountViewModel.loadAccountData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        usernameTextView = view.findViewById(R.id.text_username);
        emailTextView = view.findViewById(R.id.text_email);
        logoutButton = view.findViewById(R.id.btn_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish(); // Handle logout button click here
                // For example, start WelcomeActivity and finish current activity/fragment
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountViewModel.getAccount().observe(getViewLifecycleOwner(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if (account != null) {
                    usernameTextView.setText(account.getUsername());
                    emailTextView.setText(account.getEmail());
                }
            }
        });
    }
}

