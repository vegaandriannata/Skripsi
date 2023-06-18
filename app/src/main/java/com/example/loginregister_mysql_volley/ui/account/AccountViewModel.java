package com.example.loginregister_mysql_volley.ui.account;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loginregister_mysql_volley.Account;
import com.example.loginregister_mysql_volley.ApiService;
import com.example.loginregister_mysql_volley.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountViewModel extends ViewModel {
    private MutableLiveData<Account> accountData;
    private ApiService apiService;

    public AccountViewModel() {
        accountData = new MutableLiveData<>();
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public LiveData<Account> getAccount() {
        return accountData;
    }

    public void loadAccountData() {

        // Get the current username from the logged-in user or from your session management
        String username = "vega";

        Call<Account> call = apiService.getAccountData(username);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account = response.body();
                    accountData.setValue(account);
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                // Handle network error
            }
        });
    }
}
