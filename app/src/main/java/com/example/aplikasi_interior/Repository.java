package com.example.aplikasi_interior;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private ApiService apiService;

    public Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.115.176/user_data/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void fetchAccountData(String username, final OnAccountDataReceivedListener listener) {
        Call<Account> call = apiService.getAccountData(username);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account = response.body();
                    listener.onAccountDataReceived(account);
                } else {
                    listener.onAccountDataError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                listener.onAccountDataError("Error: " + t.getMessage());
            }
        });
    }

    public interface OnAccountDataReceivedListener {
        void onAccountDataReceived(Account account);
        void onAccountDataError(String error);
    }
}
