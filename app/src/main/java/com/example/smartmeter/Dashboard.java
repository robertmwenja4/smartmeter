package com.example.smartmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeter.Models.User;
import com.example.smartmeter.fragment.AboutUsfragment;
import com.example.smartmeter.fragment.BuyTokenFragment;
import com.example.smartmeter.fragment.ContactUs;
import com.example.smartmeter.fragment.DataUsage;
import com.example.smartmeter.fragment.DeviceUsageFragment;
import com.example.smartmeter.fragment.Homefragment;
import com.example.smartmeter.fragment.PreviousMonthsFragment;
import com.example.smartmeter.fragment.Report;
import com.example.smartmeter.fragment.RequestProduct;
import com.example.smartmeter.fragment.RequestService;
import com.example.smartmeter.fragment.TotalUsage;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity implements View.OnCreateContextMenuListener{

    private DrawerLayout drawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    TextView tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tv2 = findViewById(R.id.nav_username);
        tv3 = findViewById(R.id.nav_mail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.about, R.id.contact, R.id.device,
                R.id.range, R.id.prices, R.id.previous, R.id.issues,
                R.id.repoproduct, R.id.service, R.id.totalusage
        ).setDrawerLayout(drawerLayout).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    public void Updateprofile(){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                Logout();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Logout() {
        SessionManager sessionManager = new SessionManager(Dashboard.this);
        sessionManager.removeSession();
        Intent myIntent = new Intent(Dashboard.this, LoginActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
        finish();
    }
    //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PreviousMonthsFragment()).commit();
//                //startActivity(new Intent(getApplicationContext(), Previous.class));
//                break;
//            case R.id.about:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsfragment()).commit();
//                break;
//            case R.id.contact:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactUs()).commit();
//                break;
//            case R.id.prices:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BuyTokenFragment()).commit();
//                break;
//            case R.id.device:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeviceUsageFragment()).commit();
//                break;
//            case R.id.range:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataUsage()).commit();
//                break;
//            case R.id.totalusage:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TotalUsage()).commit();
//                break;
//            case R.id.repoproduct:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RequestProduct()).commit();
//                break;
//            case R.id.issues:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Report()).commit();
//                break;
//            case R.id.service:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RequestService()).commit();
//                break;
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }
}