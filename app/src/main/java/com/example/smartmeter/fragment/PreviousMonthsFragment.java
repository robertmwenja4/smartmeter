package com.example.smartmeter.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartmeter.R;
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

public class PreviousMonthsFragment extends Fragment {

    private String TAG = PreviousMonthsFragment.class.getSimpleName();

    RequestQueue queue;
    LineChart mChart;
    ArrayList<Entry> x;
    ArrayList<String> y;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.previous_months_fragment, container, false);

        x = new ArrayList<Entry>();
        y = new ArrayList<>();
        queue = Volley.newRequestQueue(getContext());
        mChart = rootview.findViewById(R.id.line);
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


        return rootview;
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
                                int f = Math.round(add);
                                Log.i(TAG, "Response: "+add);
                                x.add(new Entry(f, i));
                                y.add(month);

                            }
                            LineDataSet set1 = new LineDataSet(x, "Cost");
                            set1.setColors(ColorTemplate.COLORFUL_COLORS);
                            set1.setLineWidth(1.5f);
                            set1.setCircleRadius(4f);
                            ArrayList<ILineDataSet> list = new ArrayList<>();
                            list.add(set1);
                            LineData data = new LineData(list);
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
