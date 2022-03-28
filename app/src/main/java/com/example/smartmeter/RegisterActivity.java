package com.example.smartmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeter.Interface.GeneralInterface;
import com.example.smartmeter.Models.UserRegister;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = RegisterActivity.class.getSimpleName();

    TextView tv1,tv2;
    Button mbtn1;
    EditText edt1, edt2,edt3,edt4;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tv1 = findViewById(R.id.already);
        tv1.setOnClickListener(this);
        mbtn1 = findViewById(R.id.register);
        mbtn1.setOnClickListener(this);
        edt1 = findViewById(R.id.username);
        edt2 = findViewById(R.id.email);
        edt3 = findViewById(R.id.password);
        edt4 = findViewById(R.id.phone);
        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.register:
                Registering();
                break;
            case R.id.already:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
    }

    private void Register() {

        String musername = edt1.getText().toString().trim();
        String memail = edt2.getText().toString().trim();
        String mpass = edt3.getText().toString().trim();

        //Check password feild is not empty
        if (TextUtils.isEmpty(edt3.getText().toString())) {
            edt3.setError("No Password Entered!");
            edt3.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(edt2.getText().toString())) {
            edt2.setError("No Email Provided!");
            edt2.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(memail).matches()) {
            edt2.setError("Enter a valid email");
            edt2.requestFocus();
            return;
        }

        //Check password feild is not empty
        if (TextUtils.isEmpty(edt4.getText().toString())) {
            edt4.setError("Enter phone number!");
            edt4.requestFocus();
            return;
        }

        long phone_number = Long.parseLong(edt4.getText().toString());
        progressBar.setVisibility(View.VISIBLE);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://smartmeter.co.ke/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
        GeneralInterface generalInterface = retrofit.create(GeneralInterface.class);
        Call<UserRegister> call = generalInterface.UserRegistration(musername,memail,phone_number,mpass);
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                if (response.code()!= 200){
                    Log.e(TAG,"Response "+response.message());
                    return;
                }
                if (response.isSuccessful()){
                    Log.i(TAG, "User created"+response.message());
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<UserRegister> call, Throwable t) {

            }
        });


    }
    private void Registering(){
        String musername = edt1.getText().toString().trim();
        String memail = edt2.getText().toString().trim();
        String mpass = edt3.getText().toString().trim();
        String mconpass = edt3.getText().toString().trim();

        //Check password feild is not empty
        if (TextUtils.isEmpty(edt3.getText().toString())) {
            edt3.setError("No Password Entered!");
            edt3.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(edt2.getText().toString())) {
            edt2.setError("No Email Provided!");
            edt2.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(memail).matches()) {
            edt2.setError("Enter a valid email");
            edt2.requestFocus();
            return;
        }

        //Check password feild is not empty
        if (TextUtils.isEmpty(edt4.getText().toString())) {
            edt4.setError("Enter phone number!");
            edt4.requestFocus();
            return;
        }

        long phone_number = Long.parseLong(edt4.getText().toString());
        progressBar.setVisibility(View.VISIBLE);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nameless-river-31582.herokuapp.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        GeneralInterface generalInterface = retrofit.create(GeneralInterface.class);
        Call<UserRegister> call = generalInterface.Registration(musername,memail,phone_number,mpass,mconpass);
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                if (response.code()!= 201){
                    Log.e(TAG,"Response "+response.message());
                    return;
                }
                if (response.isSuccessful()){
                    Log.i(TAG, "User created"+response.message());
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                else {
                    Toast.makeText(RegisterActivity.this, "User Registration Failed!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserRegister> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Response: " + t.getMessage());

            }
        });
    }
}