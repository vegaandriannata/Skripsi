package com.example.aplikasi_interior;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("get_account_data.php")
    Call<Account> getAccountData(@Query("username") String username);
}