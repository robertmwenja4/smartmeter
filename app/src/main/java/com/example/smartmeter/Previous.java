package com.example.smartmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.smartmeter.fragment.PreviousMonthsFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Previous extends AppCompatActivity {
    private String TAG = Previous.class.getSimpleName();

    RequestQueue queue;
    LineChart mChart;
    ArrayList<Entry> x;
    ArrayList<String> y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);


        x = new ArrayList<Entry>();
        y = new ArrayList<>();
        mChart = findViewById(R.id.line);
        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.getXAxis().setTextSize(15f);
        mChart.getAxisLeft().setTextSize(15f);

        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(true);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        Graph();
    }

    private void Graph() {
        String url = "http://smartmeter.co.ke/all.php";
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                String ferfa = data.getString("ferfa");
                                String consumption = data.getString("consumption");
                                String erc = data.getString("erc");
                                String rep = data.getString("rep");
                                String vat = data.getString("vat");
                                String fcc = data.getString("fcc");
                                String inflation = data.getString("inflation");
                                String warma = data.getString("warma");
                                String month = data.getString("month");

                                Float consumpt = Float.valueOf(consumption);
                                Float fer = Float.valueOf(ferfa);
                                Float er = Float.valueOf(erc);
                                Float rep1 = Float.valueOf(rep);
                                Float fcc1 = Float.valueOf(fcc);
                                Float vat1 = Float.valueOf(vat);
//								Float months = Float.valueOf(month);
                                Float inflation1 = Float.valueOf(inflation);

                                //calculate warma
                                Float warma1 = Float.valueOf(warma);
                                Float add = consumpt + fer + er + rep1 + fcc1 + vat1 + inflation1 + warma1;
                                x.add(new Entry(Integer.parseInt(String.valueOf(add)), i));
                                y.add(month);

                            }
                            LineDataSet set1 = new LineDataSet(x, "Cost");
                            set1.setColors(ColorTemplate.COLORFUL_COLORS);
                            set1.setLineWidth(1.5f);
                            set1.setCircleRadius(4f);
                            LineData data = new LineData((ILineDataSet) y, set1);
                            mChart.setData(data);
                            mChart.invalidate();
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