package com.example.smartmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeter.Interface.GeneralInterface;
import com.example.smartmeter.Models.User;
import com.example.smartmeter.Models.UserRegister;
import com.example.smartmeter.Models.UserResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

  public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
      private String TAG = LoginActivity.class.getSimpleName();

    Button mbtn;
    EditText memail, mpassword;
    TextView tv1,tv2,tv3;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        memail = findViewById(R.id.username);
        mpassword = findViewById(R.id.password);
        mbtn = findViewById(R.id.login);
        mbtn.setOnClickListener(this);
        progressBar = findViewById(R.id.progress);
        tv1 = findViewById(R.id.alreadylogin);
        tv1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login:
                logining();
                break;
            case R.id.alreadylogin:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
        }
    }

      @Override
      protected void onStart() {
          super.onStart();

          SessionManager sessionManager = new SessionManager(LoginActivity.this);
          int userId = sessionManager.getSession();
          if (userId != -1){
              Intent myIntent = new Intent(LoginActivity.this, Dashboard.class);
              myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
              LoginActivity.this.startActivity(myIntent);
          }
      }

      private void Login() {

        String email = memail.getText().toString().trim();
        String pass = mpassword.getText().toString().trim();

          if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
              memail.setError("Enter a valid email");
              memail.requestFocus();
              return;
          }

        progressBar.setVisibility(View.VISIBLE);
//        startActivity(new Intent(LoginActivity.this, Dashboard.class));
//        progressBar.setVisibility(View.GONE);
          HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
          OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://smartmeter.co.ke")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
        //Call Interface
        GeneralInterface generalInterface = retrofit.create(GeneralInterface.class);
        Call<UserRegister> call = generalInterface.UserLogin(email, pass);
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                if(response.code() != 200){
                    Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "Response: " + response.body().getUsername());
                if (email.equals(response.body().getEmail())){
                    User user1 = new User(12, response.body().getUsername());

                    SessionManager sessionManager = new SessionManager(LoginActivity.this);
                    sessionManager.saveSession(user1);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(LoginActivity.this, Dashboard.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    LoginActivity.this.startActivity(myIntent);
                }else {
                    Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
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
    private void logining(){
        String email = memail.getText().toString().trim();
        String pass = mpassword.getText().toString().trim();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            memail.setError("Enter a valid email");
            memail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
//        startActivity(new Intent(LoginActivity.this, Dashboard.class));
//        progressBar.setVisibility(View.GONE);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
         
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nameless-river-31582.herokuapp.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        //Call Interface
        GeneralInterface generalInterface = retrofit.create(GeneralInterface.class);
        Call<UserRegister> call = generalInterface.login(email, pass);
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                if(response.code() != 201){
                    Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "Response: " + response.body().getUsername());
                if (email.equals(response.body().getEmail())){
                    User user1 = new User(12, response.body().getUsername());

                    SessionManager sessionManager = new SessionManager(LoginActivity.this);
                    sessionManager.saveSession(user1);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(LoginActivity.this, Dashboard.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    LoginActivity.this.startActivity(myIntent);
                }else {
                    Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
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