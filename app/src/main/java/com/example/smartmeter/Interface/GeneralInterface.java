package com.example.smartmeter.Interface;

import com.example.smartmeter.Models.UserRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeneralInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<UserRegister> UserRegistration(
            @Field("username") String muser,
            @Field("email") String memail,
            @Field("phone_number") long mphone,
            @Field("password") String mpass
    );
    @POST("login.php")
    Call<UserRegister> UserLogin(
            @Query("username") String uname,
            @Query("password") String upass
    );
    @POST("api/login")
    Call<UserRegister> login(
            @Query("email") String uname,
            @Query("password") String upass
    );
    @FormUrlEncoded
    @POST("api/register")
    Call<UserRegister> Registration(
            @Field("name") String muser,
            @Field("email") String memail,
            @Field("phone_number") long mphone,
            @Field("password") String mpass,
            @Field("password_confirmation") String conpass
    );
}
