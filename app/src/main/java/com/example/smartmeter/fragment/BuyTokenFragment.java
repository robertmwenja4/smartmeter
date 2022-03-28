package com.example.smartmeter.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartmeter.Interface.TokenInterface;
import com.example.smartmeter.Models.TokenMode;
import com.example.smartmeter.R;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuyTokenFragment extends Fragment implements View.OnClickListener{

    Spinner spinner,spinner1;
    TextView tv1, tv2,tv3,tv4;
    EditText edt1, edt2;
    private RequestQueue queue;

    public BuyTokenFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.buytokenfragment, container, false);
        spinner = rootview.findViewById(R.id.spinner1);
        spinner1 = rootview.findViewById(R.id.spinner2);
        tv1 = rootview.findViewById(R.id.kwh);
        tv2 = rootview.findViewById(R.id.ksh);
        tv3 = rootview.findViewById(R.id.calculate1);
        tv3.setOnClickListener(this);
        tv4 = rootview.findViewById(R.id.calculate2);
        tv4.setOnClickListener(this);
        edt1 = rootview.findViewById(R.id.amount);
        edt2 = rootview.findViewById(R.id.units);
        queue = Volley.newRequestQueue(getContext());
        GetMonths();


        return rootview;
    }

    @Override
    public void onClick(View v) {
        
        switch (v.getId()){
            case R.id.calculate1:
                CalcUnits();
                break;
            case R.id.calculate2:
                CalcCost();
                break;
        }
    }

    private void CalcCost() {

        if (TextUtils.isEmpty(edt2.getText().toString())){
            edt2.setError("Enter Units");
            edt2.requestFocus();
            return;

        }
        String months = spinner1.getSelectedItem().toString();
        final double watts = Double.parseDouble(edt2.getText().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://smartmeter.co.ke/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
        TokenInterface tokenInterface = retrofit.create(TokenInterface.class);
        Call<TokenMode> call = tokenInterface.getData(months);
        call.enqueue(new Callback<TokenMode>() {
            @Override
            public void onResponse(Call<TokenMode> call, retrofit2.Response<TokenMode> response) {
                if (response.code()!= 200){
                    Toast.makeText(getContext(), "Not Working", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Works"+response.body().getConsumption(), Toast.LENGTH_SHORT).show();
                double fcc = response.body().getFcc();
                double ferfa = response.body().getFerfa();
                double inflation = response.body().getInflation();
                double warma = response.body().getWarma();
                double erc = response.body().getErc();
                double rep = response.body().getRep();
                double vat = response.body().getVat();
                double consumption = response.body().getConsumption();
                DecimalFormat df = new DecimalFormat("###.##");
                DecimalFormat df1 = new DecimalFormat("###.#");
                double total = fcc+ferfa+inflation+warma+consumption+erc+rep+vat;
                double kenya = watts * total;
                String kPH = String.valueOf(df1.format(kenya));
                tv2.setText("Ksh "+kPH);
                tv2.setTextColor(Color.RED);
            }

            @Override
            public void onFailure(Call<TokenMode> call, Throwable t) {

            }
        });


    }

    private void CalcUnits() {
        if (TextUtils.isEmpty(edt1.getText().toString())){
            edt1.setError("Cost is Required");
            edt1.requestFocus();
            return;

        }
        String months = spinner.getSelectedItem().toString();
        final double price = Double.parseDouble(edt1.getText().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://smartmeter.co.ke/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();

        TokenInterface tokenInterface = retrofit.create(TokenInterface.class);
        Call<TokenMode> call = tokenInterface.getData(months);
        call.enqueue(new Callback<TokenMode>() {
            @Override
            public void onResponse(Call<TokenMode> call, retrofit2.Response<TokenMode> response) {
                if (response.code()!= 200){
                    Toast.makeText(getContext(), "Not Working", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Works"+response.body().getConsumption(), Toast.LENGTH_SHORT).show();
                double fcc = response.body().getFcc();
                double ferfa = response.body().getFerfa();
                double inflation = response.body().getInflation();
                double warma = response.body().getWarma();
                double erc = response.body().getErc();
                double rep = response.body().getRep();
                double vat = response.body().getVat();
                double consumption = response.body().getConsumption();
                DecimalFormat df = new DecimalFormat("###.##");
                DecimalFormat df1 = new DecimalFormat("###.#");
                double total = fcc+ferfa+inflation+warma+consumption+erc+rep+vat;
                double kilowatt = price/total;
                double calcwarma = warma*kilowatt;
                String kPH = String.valueOf(df1.format(kilowatt));
                tv1.setText(kPH +" units");
                tv1.setTextColor(Color.RED);
            }

            @Override
            public void onFailure(Call<TokenMode> call, Throwable t) {

            }
        });
    }

    private void GetMonths(){
        String url = "http://smartmeter.co.ke/month.php";
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Months");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        String months = data.getString("month");
//                        Toast.makeText(getContext(), "Values are "+months, Toast.LENGTH_SHORT).show();
                        ArrayList<String> contacts = new ArrayList<>();
                            contacts.add(months);
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, contacts);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapter);
                        spinner1.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(obj);

    }
}
