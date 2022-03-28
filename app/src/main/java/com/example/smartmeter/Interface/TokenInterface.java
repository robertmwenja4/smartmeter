package com.example.smartmeter.Interface;

import com.example.smartmeter.Models.TokenMode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TokenInterface {

    @GET("read.php")
    Call<TokenMode> getData(
            @Query("month") String month
    );
}
